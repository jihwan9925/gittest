package com.jdbc.controller;

import java.util.List;
import java.util.Map;

import com.jdbc.model.dto.BoardDTO;
import com.jdbc.model.sevice.BoardService;
import com.jdbc.view.MainView;

public class BoardController {
	
	BoardService service=new BoardService();

	public void mainMenu() {
		new MainView().mainMenu();
	}
	//controller 생성순서
	//1.자료형 변수명=service.메소드명 : service에서 구현한 값을 불러와 저장한다.
	//2.new MainView().메소드명(변수명) : 출력하는것은 view의 영역이기 때문에 view에 보내고, 그 값을 가져온다.
	public List<BoardDTO> selectAll(){
		List<BoardDTO> boards=service.selectAll();
		//DB에서 board로 저장
		new MainView().printAll(boards);
		//저장한 board(List)를 안내문구와 함께 출력
		return boards;
	}
	public void searchBoard() {
		//검색할 항목(컬럼명), 검색어
		Map param=new MainView().inputSearch();
		//어떤 종류의 문자인지, 찾는 글자가 어떤 글자인지 저장
		List<BoardDTO> boards=service.searchBoard(param);
		//조회할 내용을 받아 결과값을 게시물의 댓글란에 저장시켜 게시물 반환
		new MainView().printBoard(boards);
		//안내구문과 함께 {게시물댓글수 [해당하는 게시물 출력값]}을 출력
	}
	
}
