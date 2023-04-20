package com.jdbc.model.sevice;

import static com.jdbc.common.JDBCTemplate.close;
import static com.jdbc.common.JDBCTemplate.getConnection;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.jdbc.model.dao.BoardDAO;
import com.jdbc.model.dto.BoardDTO;

public class BoardService {
	
	private BoardDAO dao=new BoardDAO();
	//sevice 생성 순서
	//1.배열(List),하나의 값(int:출력확인)인지 따라 자료형을 달리 정하기
	//2.Connection conn=[JDBCTemplate.]getConnection() : JDBCTemplate는 static메소드이므로 import하면 생략가능
	//3.자료형 변수명=dao.메소드명(conn,[추가변수]) : dao의 메소드를 실행시켜 원하는 값을 도출하여 자료형에 맞게 저장
	//4.[JDBCTemplate.]close(conn) : JDBCTemplate는 static메소드이므로 import하면 생략가능
	public List<BoardDTO> selectAll(){ //
		Connection conn=getConnection();
		List<BoardDTO> boards=dao.selectAll(conn); //boards에 전체조회값 저장
		for(BoardDTO b : boards) {
			b.setComments(dao.selectBoardComment(conn, b.getBoardNo())); //게시물당 댓글 저장
		}//boards의 댓글부분이 있으면 해당번째의 게시물의 댓글란으로 저장
		close(conn);
		return boards;
	}
	
	public List<BoardDTO> searchBoard(Map param){
		Connection conn=getConnection();
		List<BoardDTO> boards=dao.searchBoard(conn,param);
		//조회할 내용을 받아 결과값을 게시물의 댓글란에 저장시켜 게시물 반환
		close(conn);
		return boards;
	}
	
}
