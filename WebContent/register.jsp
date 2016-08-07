<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<title>Register</title>
<body>
<h2>Enter the following details</h2>
	<s:form action="register" >
		<s:textfield name="registerBean.fname" label="First Name" />
		<s:textfield name="registerBean.lname" label="Last Name" />
		<s:textfield name="registerBean.email" label="Email" />
		<s:textfield name="registerBean.uname" label="Username" />
		<s:password name="registerBean.password" label="Password" />
		<s:submit value="Sign Up" />
	</s:form>
</body>

</html>