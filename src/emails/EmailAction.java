package emails;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class EmailAction extends ActionSupport implements SessionAware, ServletRequestAware {

	private static final long serialVersionUID = 6533287070997288754L;
	private EmailBean emailBean;
	private HttpServletRequest request = null;
	private Map<String, String> map;

	public String execute() {
		// TO-DO
		return NONE;
	}

	public EmailBean getRegisterBean() {
		return emailBean;
	}

	public void setRegisterBean(EmailBean emailBean) {
		this.emailBean = emailBean;
	}

	public ArrayList<EmailBean> getInboxEmails(String email) throws SQLException {
		System.out.println("Email ID is:" + email);
		ArrayList<EmailBean> emailList = EmailDao.getInboxEmails(email);
		return emailList;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setSession(Map map) {
		this.map = map;
	}
	
	public String sendEmail(EmailBean emailBean) throws SQLException{
		if(EmailDao.insertEmail(emailBean))
			return "ok";
		return "error";
	}
	
}
