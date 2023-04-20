package com.jdbc.model.dto;

import java.sql.Date;
import java.util.Objects;

public class BoardComment {

	private int CommentNo;
	private String boardContent;
	private String commentWriter;
	private Date commentDate;
	
	public BoardComment() {
		// TODO Auto-generated constructor stub
	}

	public BoardComment(int commentNo, String boardContent, String commentWriter, Date commentDate) {
		super();
		CommentNo = commentNo;
		this.boardContent = boardContent;
		this.commentWriter = commentWriter;
		this.commentDate = commentDate;
	}

	public int getCommentNo() {
		return CommentNo;
	}

	public void setCommentNo(int commentNo) {
		CommentNo = commentNo;
	}

	public String getBoardContent() {
		return boardContent;
	}

	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}

	public String getCommentWriter() {
		return commentWriter;
	}

	public void setCommentWriter(String commentWriter) {
		this.commentWriter = commentWriter;
	}

	public Date getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}

	@Override
	public String toString() {
		return "BoardComment [CommentNo=" + CommentNo + ", boardContent=" + boardContent + ", commentWriter="
				+ commentWriter + ", commentDate=" + commentDate + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(CommentNo, boardContent, commentDate, commentWriter);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BoardComment other = (BoardComment) obj;
		return CommentNo == other.CommentNo && Objects.equals(boardContent, other.boardContent)
				&& Objects.equals(commentDate, other.commentDate) && Objects.equals(commentWriter, other.commentWriter);
	}
	
	
}
