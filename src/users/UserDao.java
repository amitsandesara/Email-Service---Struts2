package users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DbConnection;
import emails.EmailBean;

public class UserDao {

	public static UserBean authenticate(String uname, String password) throws ClassNotFoundException, SQLException {
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet rst = null;

		try {

			connection = DbConnection.getConnection();
			String sql = "SELECT username, email, firstname, lastname, password FROM users WHERE username = ? AND password = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, uname);
			preparedStatement.setString(2, password);

			rst = preparedStatement.executeQuery();

			while (rst.next()) {
				UserBean userBean = new UserBean();
				userBean.setUname(rst.getString(1));
				userBean.setEmail(rst.getString(2));
				userBean.setFname(rst.getString(3));
				userBean.setLname(rst.getString(4));
				userBean.setPassword(rst.getString(5));
				return userBean;
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			connection.close();
		}
		return null;
	}

	public static boolean insertUser(UserBean bean) throws ClassNotFoundException, SQLException {
		boolean flag = false;
		PreparedStatement preparedStatement = null;
		Connection connection = null;

		try {
			connection = DbConnection.getConnection();
			String sql = "Insert into users (firstname, lastname, email, username, password) values (?,?,?,?,?)";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, bean.getFname());
			preparedStatement.setString(2, bean.getLname());
			preparedStatement.setString(3, bean.getEmail());
			preparedStatement.setString(4, bean.getUname());
			preparedStatement.setString(5, bean.getPassword());

			boolean exists = findOne(bean.getEmail(), bean.getUname());
			if (!exists) {
				int count = preparedStatement.executeUpdate();

				if (count == 1)
					flag = true;

				return flag;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}

		return flag;
	}

	public static boolean findAllUsers() throws ClassNotFoundException, SQLException {
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet rst = null;
		ArrayList<UserBean> userList = new ArrayList<>();
		try {
			connection = DbConnection.getConnection();
			String sql = "SELECT username, firstname, lastname, email, password FROM users";
			preparedStatement = connection.prepareStatement(sql);
			rst = preparedStatement.executeQuery();
			int count = 0;

			while (rst.next()) {
				UserBean user = new UserBean();
				user.setUname(rst.getString(1));
				user.setFname(rst.getString(2));
				user.setLname(rst.getString(3));
				user.setEmail(rst.getString(4));
				user.setPassword(rst.getString(5));
				userList.add(user);
				count++;
			}

			System.out.println("Total users in database for current user: " + count);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			connection.close();
		}

		return false;
	}

	public static boolean findOne(String email, String username) throws ClassNotFoundException, SQLException {
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet result = null;
		try {
			connection = DbConnection.getConnection();
			String sql = "SELECT count(*) FROM users WHERE email = ? or username = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, username);
			result = preparedStatement.executeQuery();
			int count = 0;

			while (result.next()) {
				count = result.getInt(1);
			}

			if (count == 1)
				return true;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			connection.close();
		}

		return false;
	}

	// public static boolean insertEmail(EmailBean bean) throws
	// ClassNotFoundException, FileNotFoundException, SQLException {
	// boolean flag = false;
	//
	//// File fBlob = new File(bean.getAttachment());
	//// FileInputStream is = new FileInputStream(fBlob);
	// PreparedStatement preparedStatement = null;
	// Connection connection = null;
	// ResultSet result = null;
	// try {
	// connection = DbConnection.getConnection();
	// String sql = "Insert into email (recipent, emailSubject, emailContent,
	// sender ) values (?,?,?,?)";
	// preparedStatement = connection.prepareStatement(sql);
	// preparedStatement.setString(1, bean.getRecipent().toString());
	// preparedStatement.setString(2, bean.getEmailSubject());
	// preparedStatement.setString(3, bean.getEmailContent());
	//
	// preparedStatement.setString(4, "Test");
	// result = preparedStatement.executeQuery();
	// int count = 0;
	//
	// if (result.next()) {
	// count = result.getInt(1);
	// System.out.println(count);
	// }
	// if (count == 1)
	// flag = true;
	//
	// return flag;
	//
	// } catch (SQLException e) {
	// e.printStackTrace();
	// } finally {
	// connection.close();
	// }
	//
	// return flag;
	// }

	public static ArrayList<UserBean> getAllUsers() throws ClassNotFoundException, SQLException {
		Connection connection = null;
		ArrayList<UserBean> userlist = new ArrayList<>();
		ResultSet rst = null;

		try {
			connection = DbConnection.getConnection();
			PreparedStatement preparedStatement;
			String sql = "Select username, password, email, firstname, lastname From users";
			preparedStatement = connection.prepareStatement(sql);
			rst = preparedStatement.executeQuery(sql);
			int count = 0;
			while (rst.next()) {
				UserBean user = new UserBean();
				user.setUname(rst.getString(1));
				user.setPassword(rst.getString(2));
				user.setEmail(rst.getString(3));
				user.setFname(rst.getString(4));
				user.setLname(rst.getString(5));
				userlist.add(user);
				count++;
			}
			System.out.println("Total users in database: " + count);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}

		return userlist;
	}
}
