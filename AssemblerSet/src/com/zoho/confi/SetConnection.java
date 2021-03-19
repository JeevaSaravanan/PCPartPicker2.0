package com.zoho.confi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SetConnection {
	Connection connect;
	public Connection connectDb()throws Exception{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect=(Connection) DriverManager.getConnection(Configuration.dbname, Configuration.uname , Configuration.psw);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return connect;
	}
}