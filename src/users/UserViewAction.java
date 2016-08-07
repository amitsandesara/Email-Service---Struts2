package users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

public class UserViewAction {

	public String execute(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		String user = (String) session.getAttribute("user");
		if(user == "valid")
			return "ok";
		else {
			return "fail";
		}
	}
}
