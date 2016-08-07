package emails;

import java.sql.SQLException;

import com.opensymphony.xwork2.ActionSupport;

public class SendEmailAction extends ActionSupport {

	private static final long serialVersionUID = 6064955481460203284L;
	private EmailBean emailBean;

	public void validate() {
		System.out.println("Inside validate recipent");
		if (emailBean.getRecipent().equals(null) || emailBean.getRecipent().length() == 0) {
			System.out.println("Recipent cannot be empty");
			addFieldError("emailBean.recipent", "Username cannot be empty");
		}
		boolean exists = false;;
		try {
			exists = EmailDao.findRecipent(emailBean.getRecipent());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		if(! exists){
			addFieldError("emailBean.recipent", "Recipent email does not exists");	
		}
	}

//	public String sendEmail() throws SQLException {
//		LoginAction loginAction = new LoginAction();
//		return loginAction.sendEmail(emailBean);
//	}

	public EmailBean getEmailBean() {
		return emailBean;
	}

	public void setEmailBean(EmailBean emailBean) {
		this.emailBean = emailBean;
	}

}
