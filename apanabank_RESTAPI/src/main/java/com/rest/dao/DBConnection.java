package com.rest.dao;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mysql.jdbc.Connection;

public class DBConnection {
	static String driverClassName = "com.mysql.jdbc.Driver";
	static String url = "jdbc:mysql://localhost/apana_bank";
	static String userName = "root";
	static String pwd = "pass";
	private static Connection con;

	public static Connection getConnection() {
		try {
			System.out.println("Initializing db connection");
			Class c = Class.forName(driverClassName);
			System.out.println("driver class:"+c);
			con = (Connection) DriverManager.getConnection(url, userName, pwd);
			
		} catch (Exception e) {
			Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, e);
			e.printStackTrace();
		}
		return con;
	}
	
	public static void closeConnection(){
		try {
			System.out.println("closing connection");
			con.close();
			System.out.println("========Connection closed======");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
