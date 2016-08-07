package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.opensymphony.xwork2.ActionSupport;

public class DbConnection extends ActionSupport {

	private static final long serialVersionUID = 7105518868910596440L;
	private static Connection connection;

	public static Connection getConnection() throws ClassNotFoundException {

		connection = null;
		try {
			System.out.println("Connecting to Database...");
			String URL = "jdbc:mysql://localhost:3306/struts2";
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(URL, "root", "root");

			System.out.println("Connected to Database successfully...");
			return connection;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

}
