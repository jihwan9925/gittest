package com.jdbc.model.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BoardDTO {

	private int boardNo;
	private String boardTitle;
	private String boardContent;
	private String boardWriter;
	//원래는 작성자는 회원이어야하기 때문에 회원테이블에서 가져와야하고, 자료형도 member여야한다.
	private Date boardDate;
	private List<BoardComment> comments=new ArrayList();
	//원래는 작성자는 회원이어야하기 때문에 회원테이블에서 가져와야하고, 자료형도 member여야한다.

	public BoardDTO() {
		// TODO Auto-generated constructor stub
	}

	public BoardDTO(int boardNo, String boardTitle, String boardCotent, String boardWriter, Date boardDate) {
		super();
		this.boardNo = boardNo;
		this.boardTitle = boardTitle;
		this.boardContent = boardCotent;
		this.boardWriter = boardWriter;
		this.boardDate = boardDate;
	}

	public int getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}

	public String getBoardTitle() {
		return boardTitle;
	}

	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}

	public String getBoardContent() {
		return boardContent;
	}

	public void setBoardContent(String boardCotent) {
		this.boardContent = boardCotent;
	}

	public String getBoardWriter() {
		return boardWriter;
	}

	public void setBoardWriter(String boardWriter) {
		this.boardWriter = boardWriter;
	}

	public Date getBoardDate() {
		return boardDate;
	}

	public void setBoardDate(Date boardDate) {
		this.boardDate = boardDate;
	}

	public List<BoardComment> getComments() {
		return comments;
	}

	public void setComments(List<BoardComment> comments) {
		this.comments = comments;
	}

	@Override
	public int hashCode() {
		return Objects.hash(boardContent, boardDate, boardNo, boardTitle, boardWriter);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BoardDTO other = (BoardDTO) obj;
		return Objects.equals(boardContent, other.boardContent) && Objects.equals(boardDate, other.boardDate)
				&& boardNo == other.boardNo && Objects.equals(boardTitle, other.boardTitle)
				&& Objects.equals(boardWriter, other.boardWriter);
	}

	@Override
	public String toString() {
		return "BoardDTO [boardNo=" + boardNo + ", boardTitle=" + boardTitle + ", boardContent=" + boardContent
				+ ", boardWriter=" + boardWriter + ", boardDate=" + boardDate + ", comments=" + comments + "]";
	}

	

	
}
