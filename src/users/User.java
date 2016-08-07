package users;

import java.sql.SQLException;

public class User {
	
	private UserBean registerBean;
	
	public String execute() throws ClassNotFoundException, SQLException {
		boolean flag = UserDao.insertUser(registerBean);
		if(flag)
			return "ok";
		return "error";
	}
	
	public UserBean getRegisterBean() {
		return registerBean;
	}
	
	public void setRegisterBean(UserBean registerBean) {
		this.registerBean = registerBean;
	}

}
