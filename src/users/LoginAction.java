package users;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import emails.EmailAction;
import emails.EmailBean;
import emails.EmailDao;

public class LoginAction extends ActionSupport implements SessionAware, ServletRequestAware {

	private static final long serialVersionUID = -6883964319141744758L;
	private UserBean userBean;
	private EmailBean emailBean;
	private HttpServletRequest request = null;
	private Map<String, String> map;

	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	public String execute() {
		System.out.println("Inside execute() of LoginAction.class");
		return SUCCESS;

	}

	public String validLogin() {
		try {
			UserBean user = UserDao.authenticate(userBean.getUname(), userBean.getPassword());
			this.setUserBean(user);
			if (user != null) {
				map.put("currentUser.uname", user.getUname());
				map.put("currentUser.email", user.getEmail());
				map.put("currentUser.password", user.getPassword());
				map.put("currentUser.fname", user.getFname());
				map.put("currentUser.lname", user.getLname());

				request.setAttribute("currentUser", user);
				request.setAttribute("users", getAllUsers());
				userBean = user;
				EmailAction emailAction = new EmailAction();
				ArrayList<EmailBean> emailList = emailAction.getInboxEmails(user.getEmail());
				request.setAttribute("emails", emailList);

				ArrayList<EmailBean> sentEmailList = EmailDao.getSentEmails(user.getEmail());
				request.setAttribute("sentEmails", sentEmailList);

				ArrayList<EmailBean> trashEmailList = EmailDao.getTrashEmails(user.getEmail());
				request.setAttribute("trashEmails", trashEmailList);

			}
			return SUCCESS;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return NONE;
	}

	public String refresh() throws SQLException {
		String email = getUserBean().getEmail();
		EmailAction emailAction = new EmailAction();
		ArrayList<EmailBean> emailList = emailAction.getInboxEmails(email);
		request.setAttribute("emails", emailList);

		ArrayList<EmailBean> sentEmailList = EmailDao.getSentEmails(email);
		request.setAttribute("sentEmails", sentEmailList);

		ArrayList<EmailBean> trashEmailList = EmailDao.getTrashEmails(email);
		request.setAttribute("trashEmails", trashEmailList);

		return "ok";
	}

	public String getAllUsers() {
		try {
			ArrayList<UserBean> userList = UserDao.getAllUsers();
			request.setAttribute("userList", userList);

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return "ok";
	}

	public String trashEmail() throws SQLException {
		EmailDao dao = new EmailDao();
		String ids[] = request.getParameterValues("trashEmail");
		for (int i = 0; i < ids.length; i++) {
			dao.trashEmail(ids[i]);
		}
		String email = (String) map.get("currentUser.email");
		EmailAction emailAction = new EmailAction();
		ArrayList<EmailBean> emailList = emailAction.getInboxEmails(email);
		request.setAttribute("emails", emailList);
		ArrayList<EmailBean> sentEmailList = EmailDao.getSentEmails(email);
		request.setAttribute("sentEmails", sentEmailList);

		ArrayList<EmailBean> trashEmailList = EmailDao.getTrashEmails(email);
		request.setAttribute("trashEmails", trashEmailList);
		return "ok";
	}

	public String deleteEmail() throws SQLException {

		EmailDao dao = new EmailDao();
		String ids[] = request.getParameterValues("deleteEmail");
		for (int i = 0; i < ids.length; i++) {
			dao.deleteEmail(ids[i]);
		}
		String email = (String) map.get("currentUser.email");
		EmailAction emailAction = new EmailAction();
		ArrayList<EmailBean> emailList = emailAction.getInboxEmails(email);
		request.setAttribute("emails", emailList);

		ArrayList<EmailBean> sentEmailList = EmailDao.getSentEmails(email);
		request.setAttribute("sentEmails", sentEmailList);

		ArrayList<EmailBean> trashEmailList = EmailDao.getTrashEmails(email);
		request.setAttribute("trashEmails", trashEmailList);
		return "ok";
	}

	public void viewTrashEmail() {

	}

	public String sendEmail() throws SQLException, ClassNotFoundException {

		boolean exists = EmailDao.findRecipent(emailBean.getRecipent());
		if (exists) {
			EmailAction emailAction = new EmailAction();
			String email = (String) map.get("currentUser.email");
			emailBean.setSender(email);
			String result = emailAction.sendEmail(emailBean);
			return "ok";
		}
		else
			return "fail";
	}

	public String updateProfile() {

		return "ok";
	}

	public void validatevalidLogin() {
		System.out.println("Inside validate");
		if (userBean.getUname().equals(null) || userBean.getUname().length() == 0) {
			System.out.println("Username empty");
			addFieldError("userBean.uname", "Username cannot be empty");
		}
		if (userBean.getPassword().length() == 0 || userBean.getPassword().equals(null)) {
			System.out.println("Password empty");
			addFieldError("userBean.password", "Password cannot be empty");
		}
	}

	public void validateregister() {
		System.out.println("Inside validate register");
		if (userBean.getUname().equals(null) || userBean.getUname().length() == 0) {
			System.out.println("Username empty");
			addFieldError("userBean.uname", "Username cannot be empty");
		}
		if (userBean.getPassword().length() == 0 || userBean.getPassword().equals(null)) {
			System.out.println("Password empty");
			addFieldError("userBean.password", "Password cannot be empty");
		}
	}

	public void setSession(Map map) {
		this.map = map;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public EmailBean getEmailBean() {
		return emailBean;
	}

	public void setEmailBean(EmailBean emailBean) {
		this.emailBean = emailBean;
	}

	public String logout() {
		System.out.println("Inside logout of LoginAction class");
		if (map.containsKey("currentUser.uname")) {
			map.remove("currentUser.uname");
			map.remove("currentUser.email");
			map.remove("currentUser.password");
		}
		request.getSession(false);
		return SUCCESS;
	}

}
