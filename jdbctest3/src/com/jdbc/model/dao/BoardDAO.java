package com.jdbc.model.dao;

import static com.jdbc.common.JDBCTemplate.close;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.jdbc.model.dto.BoardComment;
import com.jdbc.model.dto.BoardDTO;

public class BoardDAO {

	private Properties sql=new Properties();
	//초기화 블럭 : 파일에 있는 주소,계정정보,등등을 가져오기위해 경로를 입력하여 연결하는 과정
	//			반드시 실행되어할 구문이기 때문에 초기화블럭 사용
	{
		String path=BoardDAO.class
				.getResource("/sql/board/board_sql.properties").getPath();
		//BoardDAO와 같은 패키지에있고, 경로가 ~~~인 자원(getResource)을 출력(getPath)
//		System.out.println(path);
		try(FileReader fr=new FileReader(path);) {
			sql.load(fr);
			//스트림처럼 사용하려면 상위클래스를 생성하고 사용해야한다고 이해하기
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	//dao저장 구문
	//01.PreparedStatement pstmt=null;
	//02.ResultSet rs=null : 리턴값(SELECT)이 있으면 사용, 아닐 시 int result=0;
	//03.List<BoardDTO> boards=new ArrayList() : 리턴값(SELECT)이 있으면 사용, 아닐 시 생략
	//04.String sql=this.sql.getProperty("selectBoardAll") : 파일경로(sql)에 해당구문 찾아서 대입
	//05.try-catch문
	//06.pstmt=conn.prepareStatement(sql);
	//07.pstmt.setInt(1, boardNo) : sql문에 ?와같은 대입라 값이 있으면 기입
	//08.rs=pstmt.executeQuery()/result=pstmt.executeUpdate() : 리턴값있으면 전자,아니면 후자
	//09.while(rs.next())/if(rs.next()) : 리턴값있으면 전자,아니면 후자
	//10.리턴값.add(DB->dto저장 메소드(rs)) : 미리 만든 저장메소드를 이용해서 리턴값에 저장
	//11.finally구문에 close(rs.pstmt) : JDBCTemplate의 메소드이며 생략하여 사용함(import필요)
	public List<BoardDTO> selectAll(Connection conn){
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<BoardDTO> boards=new ArrayList();
		String sql=this.sql.getProperty("selectBoardAll"); 
		//불러온파일의 selectBoardAll구문을 가져온다
		try {
			pstmt=conn.prepareStatement(sql);
			//해당문법을 사용하기위해 대입한다
			rs=pstmt.executeQuery();
			//리턴값을 rs에 저장
			while(rs.next()) {
//				//임시  (댓글을 처리하는 방법이 2가지 있다 1.dao에서 처리 2.service에서 처리)
//				BoardDTO b=getBoard(rs);
//				//게시물 값을 b에 저장
//				b.setComments(selectBoardComment(conn,b.getBoardNo()));
//				//게시물에 comment를 List형태로 저장한 것을 board의 comment에 저장
//				//----------------------댓글저장구문------------------
//				boards.add(b);
//				//---------------댓글과 결합한 게시물 저장---------------
				boards.add(getBoard(rs));
			}
			//저장된 값을 boards(List)에 저장
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return boards;
	}
	public List<BoardComment> selectBoardComment(Connection conn,int boardNo){
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<BoardComment> comments=new ArrayList();
		String sql=this.sql.getProperty("selectBoardCommentByNo");
		//게시물번호와 같은 번호를 가진 댓글테이블 조회
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			//조회하기위한 게시물번호를 입력하기 위한 로직
			rs=pstmt.executeQuery();
			while(rs.next()) comments.add(getComments(rs));
			//저장한 값을 comments에 저장
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return comments;
	}
	public List<BoardDTO> searchBoard(Connection conn, Map param){
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<BoardDTO> boards=new ArrayList();
		String sql=this.sql.getProperty("selectBoardByCol");
		sql=sql.replace("#COL", (String)param.get("col"));
		//param에 저장한 key값인 "col"(조회할 종류중 하나)를 대입하여 #COL에 치환
		//sql문의 조건문에 유동적인 값을 넣고싶으면 ?가 아닌 임의의 텍스트로 사용해야지 row값에 싱글쿼탠션이 안붙는다
		//EX : WHERE ? LIKE ? -> WHERE '?' LIKE ? //물음표가 문자가 되어 재기능을 하지못한다.
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, "%"+(String)param.get("keyword")+"%");
			//물음표(조회할 문자)에 param의 keyword(value)에서 문자를 받아와 대입
			rs=pstmt.executeQuery();
			while(rs.next()) {
				BoardDTO b=getBoard(rs);
				//게시물 값을 b에 저장
				b.setComments(selectBoardComment(conn,b.getBoardNo()));
				//게시물에 comment를 List형태로 저장한 것을 board의 comment에 저장
				//----------------------댓글저장구문------------------
				boards.add(b);
				//---------------댓글과 결합한 게시물 저장---------------
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return boards;
	}
	
	////////////////////////////////DB->dto저장 구문////////////////////////////////////
	//1. dao에 기입한다.
	//2. throws를 사용해야한다. (+private)
	//3. dto생성->DB의ROW값 dto에 넣기->반환하기 순으로 이루어져있다.
	private BoardComment getComments(ResultSet rs) throws SQLException{
		BoardComment bc=new BoardComment(); 
		//DB에 내용을 종류별로("comment_no":sql의 컬럼값) 저장해서 board에 넣는과정
		bc.setCommentNo(rs.getInt("comment_no"));
		bc.setBoardContent(rs.getString("comment_content"));
		bc.setCommentWriter(rs.getString("comment_writer"));
		bc.setCommentDate(rs.getDate("comment_date"));
		return bc;
	}
	
	private BoardDTO getBoard(ResultSet rs) throws SQLException{
		BoardDTO b=new BoardDTO();
		b.setBoardNo(rs.getInt("board_no"));
		b.setBoardTitle(rs.getString("board_title"));
		b.setBoardContent(rs.getString("board_content"));
		b.setBoardWriter(rs.getString("board_Writer"));
		b.setBoardDate(rs.getDate("board_date"));
		return b;
	}
	
	
}
