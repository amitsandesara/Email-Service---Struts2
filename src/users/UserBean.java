package users;

public class UserBean {

	private String fname;
	private String lname;
	private String email;
	private String uname;
	private String password;

	public String getFname() {
		return fname;
	}

	public String getLname() {
		return lname;
	}

	public String getUname() {
		return uname;
	}

	public String getPassword() {
		return password;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "UserBean [fname=" + fname + ", lname=" + lname + ", email=" + email + ", uname=" + uname + ", password="
				+ password + "]";
	}
}
