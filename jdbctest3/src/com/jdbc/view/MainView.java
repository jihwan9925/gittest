package com.jdbc.view;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.jdbc.controller.BoardController;
import com.jdbc.model.dto.BoardDTO;

public class MainView {
	
	BoardController controller=new BoardController();

	public void mainMenu() {
		Scanner sc=new Scanner(System.in);
		System.out.println("==== 게시글 조회하기 ====");
		System.out.println("1. 게시글 전체 조회하기");
		System.out.println("2. 게시글 항목(제목,내용,작성자)으로 조회하기");
		System.out.println("0. 프로그램 종료");
		System.out.print("메뉴선택 : ");
		int cho=sc.nextInt();
		switch(cho) {
		case 1 : controller.selectAll();break;
		case 2 : controller.searchBoard();break;
		case 0 : System.out.println("프로그램을 종료합니다");return;
		default : System.out.println("다시 선택하세요");break;
		}
	}
	
	
	public void printAll(List<BoardDTO> boards) {
		System.out.println("==== 조회결과 ====");
		
		boards.forEach((b)->System.out.println(""+b.getComments().size()+b));
		System.out.println("================");
	}
	
	public Map inputSearch() {
		Scanner sc =new Scanner(System.in);
		System.out.println("==== 게시글 항목별검색 ====");
		System.out.print("1. 제목 2. 내용 3. 작성자 : ");
		int colcho=sc.nextInt();
		sc.nextLine();
		String col="";
		switch(colcho) {
			case 1 : col="board_title";break;
			case 2 : col="board_content";break;
			case 3 : col="board_writer";break;
			//map형식으로 저장하려할 때 key값의 변별력을 주기 위해서 switch문을 사용한다
		}
		System.out.print("검색어 : ");
		String keyword=sc.nextLine();
		return Map.of("col",col,"keyword",keyword);
		//어떤 종류의 문자인지, 찾는 글자가 어떤 글자인지 저장
	}
	
	public void printBoard(List<BoardDTO> boards) {
		System.out.println("====== 게시글 리스트 ======");
		if(boards.isEmpty()) {
			System.out.println("조회된 게시글이 없습니다");
		}else {
			boards.forEach(b->System.out.println(""+b.getComments().size()+b));
			//b.getComments().size() : 댓글수량 출력(ex:1BoardDTO) , b : 해당하는 배열을 순서대로 출력
		}
		System.out.println("=======================");
	}
	
}
