package com.jdbc.common;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {

	public static Connection getConnection() {
		//connection 생성,드라이버로드 , 주소생성,계정정보,오토커밋해제 구조
		Properties driver=new Properties();
		Connection conn=null;
		String path=JDBCTemplate.class.getResource("/driver.properties").getPath();
		try(FileReader fr=new FileReader(path)) {
//			driver.load(new FileReader(path)); 자동으로 스트림을 닫아주는 줄 알았지만 수동으로 닫아야해서
//			try에 쓰면 try가 끝나고 알아서 닫는다.
			driver.load(fr);
			//driver에 파일 경로를 불러오는 구문 
			Class.forName(driver.getProperty("driver"));
			//driver의 저장된 driver항목의 주소를 가져오기
			conn=DriverManager.getConnection(driver.getProperty("url"),
					driver.getProperty("user"),driver.getProperty("pw"));
			conn.setAutoCommit(false);
		}catch(IOException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	//기본적인 틀은 같아서 누가 사용하는지(conn,stmt,rs) 어떤기능을 사용하는지(커밋,롤백) 숙지하기
	public static void close(Connection conn) {
		try {
			if(conn!=null && !conn.isClosed()) conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public static void close(Statement stmt) {
		try {
			if(stmt!=null && !stmt.isClosed()) stmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public static void close(ResultSet rs) {
		try {
			if(rs!=null && !rs.isClosed()) rs.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void commit(Connection conn) {
		try {
			if(conn!=null && !conn.isClosed()) conn.commit();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void rollback(Connection conn) {
		try {
			if(conn!=null && !conn.isClosed()) conn.rollback();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
}
