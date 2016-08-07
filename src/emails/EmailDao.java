package emails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DbConnection;
import users.UserBean;

public class EmailDao {

	public static boolean insertEmail(EmailBean emailBean) throws SQLException {
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		// ResultSet rst = null;
		boolean flag = false;
		try {
			connection = DbConnection.getConnection();
			String sql = "Insert into email (recipent, emailSubject, emailContent, sender) values (?,?,?,?)";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, emailBean.getRecipent());
			preparedStatement.setString(2, emailBean.getEmailSubject());
			preparedStatement.setString(3, emailBean.getEmailContent());
			preparedStatement.setString(4, emailBean.getSender());
			int count = preparedStatement.executeUpdate();

			if (count == 1)
				flag = true;

			return flag;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		return flag;
	}

	public static ArrayList<EmailBean> getInboxEmails(String userEmail) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ArrayList<EmailBean> emailList = new ArrayList<>();
		ResultSet rst = null;
		try {
			connection = DbConnection.getConnection();
			String sql = "SELECT recipent, sender, emailSubject, emailContent, created, id FROM email WHERE recipent = ? and isTrash='0' order by created desc";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, userEmail);
			rst = preparedStatement.executeQuery();
			int count = 0;
			while (rst.next()) {
				EmailBean email = new EmailBean();
				email.setRecipent(rst.getString(1));
				email.setSender(rst.getString(2));
				email.setEmailSubject(rst.getString(3));
				email.setEmailContent(rst.getString(4));
				email.setDatetime(rst.getString(5));
				email.setId(rst.getInt(6));
				emailList.add(email);
				count++;
			}
			System.out.println("Total email in database for current user: " + count);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}

		return emailList;
	}

	public static ArrayList<EmailBean> getSentEmails(String userEmail) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ArrayList<EmailBean> emailList = new ArrayList<>();
		ResultSet rst = null;
		try {
			connection = DbConnection.getConnection();
			String sql = "SELECT recipent, sender, emailSubject, emailContent, created, id FROM email WHERE sender = ? and isTrash='0' order by created desc";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, userEmail);
			rst = preparedStatement.executeQuery();
			int count = 0;
			while (rst.next()) {
				EmailBean email = new EmailBean();
				email.setRecipent(rst.getString(1));
				email.setSender(rst.getString(2));
				email.setEmailSubject(rst.getString(3));
				email.setEmailContent(rst.getString(4));
				email.setDatetime(rst.getString(5));
				email.setId(rst.getInt(6));
				emailList.add(email);
				count++;
			}
			System.out.println("Total email in database sent by current user: " + count);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}

		return emailList;
	}

	public static ArrayList<EmailBean> getTrashEmails(String userEmail) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ArrayList<EmailBean> emailList = new ArrayList<>();
		ResultSet rst = null;
		try {
			connection = DbConnection.getConnection();
			String sql = "SELECT recipent, sender, emailSubject, emailContent, created, id FROM email WHERE recipent = ? or sender = ? and isTrash='1' order by created desc";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, userEmail);
			preparedStatement.setString(2, userEmail);
			rst = preparedStatement.executeQuery();
			int count = 0;
			while (rst.next()) {
				EmailBean email = new EmailBean();
				email.setRecipent(rst.getString(1));
				email.setSender(rst.getString(2));
				email.setEmailSubject(rst.getString(3));
				email.setEmailContent(rst.getString(4));
				email.setDatetime(rst.getString(5));
				email.setId(rst.getInt(6));
				emailList.add(email);
				count++;
			}
			System.out.println("Total email in database for current user: " + count);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}

		return emailList;
	}

	public boolean trashEmail(String id) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int result = 0;
		try {
			connection = DbConnection.getConnection();
			String sql = "UPDATE email SET isTrash='1' where id = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, id);
			result = preparedStatement.executeUpdate();
			if (result > 0)
				return true;
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		return false;
	}

	public boolean deleteEmail(String id) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int result = 0;
		try {
			int id1 = Integer.valueOf(id);
			connection = DbConnection.getConnection();
			String sql = "DELETE from email where id = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id1);
			result = preparedStatement.executeUpdate();
			if (result > 0)
				return true;
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		return false;
	}

	public static boolean findRecipent(String recipent) throws ClassNotFoundException, SQLException {
		
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet rst = null;
		boolean flag = false;
		try {
			
			connection = DbConnection.getConnection();
			String sql = "SELECT count(*) FROM users WHERE email = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, recipent);
			rst = preparedStatement.executeQuery();
			int count = 0;

			while (rst.next()) {
				count = rst.getInt(1);
			}

			if (count == 1)
				flag = true;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			connection.close();
		}
		return flag;
	}

}
