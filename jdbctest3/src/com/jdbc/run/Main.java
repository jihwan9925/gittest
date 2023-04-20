package com.jdbc.run;

import com.jdbc.controller.BoardController;

public class Main {

	public static void main(String[] args) {
		new BoardController().mainMenu();
		System.out.println("내가 추가한 구문");
	}

}
