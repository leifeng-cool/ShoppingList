package com.shoppinglist.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbHelper {
	private static final String driver = "com.mysql.jdbc.Driver";
	private static final String database = "test";
	private static final String url = "jdbc:mysql://127.0.0.1:3306/"+database 
						+"?useUnicode=true&amp;characterEncoding=UTF-8";
	private static final String username = "root";
	private static final String password = "root";
	private static Connection conn = null;
	
	static{
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//单例模式下返回数据库连接对象
	public static Connection getConnection() throws SQLException {
		if (conn==null) {
			conn = DriverManager.getConnection(url, username, password);
			return conn;
		}
		return conn;
	}
	
	public static void main(String[] args) {
		try {
			Connection conn = DbHelper.getConnection();
			if (conn!=null) {
				System.out.println("数据库连接成功！");
			} else {
				System.out.println("数据库连接失败！");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	 public static void closeConn(ResultSet rs,PreparedStatement pstmt,Connection conn){
		 try {
			if (rs!=null) {
				rs.close();
			} 
			 if (pstmt!=null) {
				pstmt.close();
			}
			 if (conn!=null) {
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
