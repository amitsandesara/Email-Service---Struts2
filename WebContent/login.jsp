<%@ taglib uri="/struts-tags" prefix="s"%>

<s:actionerror />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>StrutsEmail</title>
<style>
a {
	color: #000;
	text-decoration: none;
}
</style>
</head>
<body>


	<center>
		<h1 style="margin: 40px">Login to Continue</h1>
	</center>

	<table>
		<s:form action="loginCheck"
			style="text-align:center; margin: 0 auto; ">

			<tr>
				<td><s:textfield
						style="height:25px; text-align:center; font-size:14px" type="text"
						placeholder="Username" name="userBean.uname" /></td>

			</tr>
			<tr>
				<td><s:textfield
						style="height:25px;text-align:center; font-size:14px"
						type="password" placeholder="Password" name="userBean.password" /></td>
				<br />
			</tr>
			<tr>
				<td style="margin: auto"><s:submit
						style="height:25px; width: 100px" value="Sign In"></s:submit></td>
			</tr>

		</s:form>

		<center>
			<h3>or</h3>
		</center>

		<center>
			<button
				style="align: center; height: 25px; background-color: #f2f2f2; width: 120px">
				<a href="register.jsp">Register</a>
			</button>
		</center>
	</table>

</body>
</html>