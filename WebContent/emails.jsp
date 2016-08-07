<%@page import="emails.EmailBean"%>
<%@page import="users.UserBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Email Page</title>
<style>
* {
	margin: 0;
}

html, body {
	height: 100%;
	overflow: hidden;
}

#top {
	position: fixed;
	width: 100%;
	height: 100px; /* See #wrapper bottom value */
	background: #000;
	/* 	background: #ddd; */
	padding-left: 20px;
	padding-bottom: 10px;
	padding-left: 20px
}

#wrapper {
	position: absolute;
	width: 100%;
	bottom: 0;
	top: 110px; /* compensate #top height */
}

#left {
	position: absolute;
	background: #000;
	height: 100%;
	overflow: auto;
	padding: 6px;
	overflow: auto;
}

#right {
	position: absolute;
	right: 0;
	width: 79%; /* compensate #left width */
	bottom: 0;
	padding-left: 20px;
	height: 100%;
}

#page {
	position: absolute;
	top: 0px; /* header height */
	bottom: 30px; /* bottom height */
	width: 100%;
	overflow: scroll;
	top: 40px;
}

#footer {
	background: #fff;
	position: fixed;
	width: 80%;
	bottom: 0;
	padding-left: -5px;
	height: 30px;
}

#sidebar ul {
	padding: 0px;
	list-style-type: none;
	margin: 5px;
	vertical-align: middle;
}

#sidebar li a {
	display: block;
	padding: 16px;
	color: #000;
	background-color: #ffffff;
	text-decoration: none;
}

#sidebar ul li:after {
	content: "";
	display: block;
	height: 1px;
	margin: 1px;
}

#sidebar li a:hover {
	background-color: #cccccc;
}

/* #recTable tr td {
	height: 25px;
	border: none;
	background: #f2f2f2;
} */

/* td {
	padding: 0.5em 1em;
	border-bottom: 1px solid #ccc;
} */
table {
	border-collapse: collapse;
}

#recTable tr {
	background-color: #eee;
	border-top: 1px solid #fff;
}

#recTable tr:hover {
	background-color: #ccc;
}

#recTable th {
	background-color: #fff;
}

#recTable th, td {
	padding: 3px 5px;
}

#recTable td:hover {
	cursor: pointer;
}
</style>
<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
<script src="http://code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>


<script>
	function trashInboxRecord() {
		if (confirm("Are you sure you want to trash these emails?") == true) {
			document.Inbox.action = "trashEmail.action";
			document.Inbox.submit();
		}
	}
	function trashRecord() {
		if (confirm("Are you sure you want to trash these emails?") == true) {
			document.sent.action = "trashEmail.action";
			document.sent.submit();
		}
	}
	function deleteRecord() {
		if (confirm("Are you sure you want to permanently delete these emails?") == true) {
			document.deleteTrash.action = "deleteEmail.action";
			document.deleteTrash.submit();
		}
	}
</script>
<script type="text/javascript">
	function logout() {

		if (confirm("Are you sure you want to logout?") == true) {
<%System.out.println("Inside deleteRecord()");%>
	document.log.action = "logout.action";
			document.log.submit();
		}
	}
</script>

</head>
<body>


	<div id=top>
		<table width="100%">
			<tr style="margin: auto; float: center">
				<td width="30%">
					<div style="display: inline-block;">
						<div
							style="margin: 0; padding: 0; float: left; display: inline-block;">
							<img src="emailService.png" alt="" width="100" height="100" />
						</div>
					</div>
				</td>

				<td width="55%">
					<div class="wrapper" style="margin: auto;">
						<input type="text" style="width: 350px; height: 22px;"
							placeholder="Search for email" />
						<button style="height: 24px;">
							<a class="link" href="#search" data-link="search">Search</a>
						</button>
					</div>
				</td>

				<td width="15%"><span
					style="display: inline-block; width: 80px; vertical-align: top;">

						<s:form name="log" method="post">
							<button style="width: 80px;" onclick="logout()">Logout</button>
						</s:form>
				</span></td>
			</tr>
		</table>
	</div>

	<div id=wrapper>

		<div id=left style="width: 20%">
			<div id="sidebar">
				<ul>
					<li><a class="link" href="#compose" data-link="compose">Compose</a></li>
					<li><a class="link" href="#inbox" data-link="inbox">Inbox</a></li>
					<li><a class="link" href="#sent" data-link="sent">Sent
							Mail</a></li>
					<li><a class="link" href="#trash" data-link="trash">Trash</a></li>
					<li><a class="link" href="#view" data-link="view">View All
							Users</a></li>
					<li><a class="link" href="#profile" data-link="profile">Profile</a></li>
				</ul>
			</div>
		</div>

		<script>
			$(document).ready(function() {
				$('.viewEmails').hide();
				$('.viewEmails[data-link=inbox]').show();
			});
			$('.link')
					.click(
							function() {
								$('.viewEmails').hide();
								$(
										'.viewEmails[data-link='
												+ $(this).data('link') + ']')
										.show();
							});
		</script>
		<div id=right>


			<div id=page>
				<div class="viewEmails" data-link="compose" align="center"
					style="margin: 25px; padding-left: 20px">
					<div
						style="width: 550px; margin-bottom: 25px; background: #595959;">
						<h2>
							<font color="#fff">Compose Email</font>
						</h2>
					</div>
					<s:form action="sendEmail" method="post">
						<s:textfield name="emailBean.recipent" style="width:400px;"
							label="To" />
						<s:textfield name="emailBean.emailSubject" style="width:400px;"
							label="Subject" />
						<s:label label="Description" />
						<s:textarea name="emailBean.emailContent"
							style="width:400px; height:250px;  max-width: 480px;" />

						<s:submit style="width:120px; height:24px" value="Send" />
						<s:reset name="reset" value="Reset"></s:reset>
					</s:form>

				</div>

				<div class="viewEmails" data-link="inbox" align="center"
					style="margin: 40px; padding-left: 20px">
					<s:form name="Inbox" method="post">
						<table id=recTable style="width: 85%">
							<tbody>
								<%
									ArrayList<EmailBean> emailList = (ArrayList) request.getAttribute("emails");
										if (emailList != null && emailList.size() != 0) {
								%>
								<div
									style="width: 85%; margin-bottom: 25px; background: #595959;">
									<h2>
										<font color="#fff">Inbox</font>
									</h2>
								</div>
								<tr align="left" style="font-style: bold;">
									<th style="width: 8%"><input type="button" value="Trash"
										onclick="trashInboxRecord()"></th>
									<th style="width: 25%">Sender</th>
									<th style="width: 40%">Subject</th>
									<th style="width: 20%">Time</th>
								</tr>
								<%
									for (EmailBean email : emailList) {
												int id = email.getId();
								%>
								<tr>
									<td><input type="checkbox" value="<%=id%>"
										name="trashEmail"></td>
									<td><%=email.getSender()%></td>
									<td><%=email.getEmailSubject()%></td>
									<td><%=email.getDatetime()%></td>
								</tr>
								<%
									}
										} else {
								%>
								<div
									style="width: 85%; margin-bottom: 25px; background: #595959;">
									<h2>
										<font color="#fff">No emails in Inbox</font>
									</h2>
								</div>

								<%
									}
								%>

							</tbody>
						</table>
					</s:form>
				</div>

				<div class="viewEmails" data-link="sent" align="center"
					style="margin: 40px; padding-left: 20px">
					<s:form name="sent" method="post">
						<table id=recTable style="width: 85%">
							<tbody>
								<%
									ArrayList<EmailBean> emailList = (ArrayList) request.getAttribute("sentEmails");
										if (emailList != null && emailList.size() != 0) {
								%>
								<div
									style="width: 85%; margin-bottom: 25px; background: #595959;">
									<h2>
										<font color="#fff">Sent Emails</font>
									</h2>
								</div>
								<tr align="left" style="font-style: bold;">
									<th style="width: 8%"><input type="button" value="Trash"
										onclick="trashRecord()"></th>
									<th style="width: 25%">Recipent</th>
									<th style="width: 40%">Subject</th>
									<th style="width: 20%">Time</th>
								</tr>
								<%
									for (EmailBean email : emailList) {
												int id = email.getId();
								%>
								<tr>
									<td><input type="checkbox" value="<%=id%>"
										name="trashEmail"></td>
									<td><%=email.getRecipent()%></td>
									<td><%=email.getEmailSubject()%></td>
									<td><%=email.getDatetime()%></td>
								</tr>
								<%
									}
										} else {
								%>
								<div
									style="width: 85%; margin-bottom: 25px; background: #595959;">
									<h2>
										<font color="#fff">No sent emails</font>
									</h2>
								</div>
								<%
									}
								%>

							</tbody>
						</table>
					</s:form>
				</div>

				<div class="viewEmails" data-link="trash" align="center"
					style="margin: 40px; padding-left: 20px">
					<s:form name="deleteTrash" method="post">
						<table id=recTable style="width: 85%">
							<tbody>
								<%
									ArrayList<EmailBean> emailList = (ArrayList) request.getAttribute("trashEmails");
										if (emailList != null && emailList.size() != 0) {
								%>
								<div
									style="width: 85%; margin-bottom: 25px; background: #595959;">
									<h2>
										<font color="#fff">Trash Emails</font>
									</h2>
								</div>
								<tr align="left" style="font-style: bold;">
									<th style="width: 8%"><input type="button" value="Delete"
										onclick="deleteRecord()"></th>
									<th style="width: 25%">Sender</th>
									<th style="width: 40%">Subject</th>
									<th style="width: 20%">Time</th>
								</tr>
								<%
									for (EmailBean email : emailList) {
												int id = email.getId();
								%>
								<tr>
									<td><input type="checkbox" value="<%=id%>"
										name="deleteEmail"></td>
									<td><%=email.getSender()%></td>
									<td><%=email.getEmailSubject()%></td>
									<td><%=email.getDatetime()%></td>
								</tr>
								<%
									}
										} else {
								%>
								<div
									style="width: 85%; margin-bottom: 25px; background: #595959;">
									<h2>
										<font color="#fff">No Trash Emails</font>
									</h2>
								</div>
								<%
									}
								%>

							</tbody>
						</table>
					</s:form>
				</div>

				<div class="viewEmails" data-link="view" align="center"
					style="margin: 40px; padding-left: 20px">
					<s:form name="frm" method="post">
						<table id=recTable style="width: 65%">
							<tbody>
								<%
									ArrayList<UserBean> userList = (ArrayList) request.getAttribute("userList");
										if (userList != null && userList.size() != 0) {
								%>
								<div
									style="width: 65%; margin-bottom: 25px; background: #595959;">
									<h2>
										<font color="#fff">All Users in Email Service</font>
									</h2>
								</div>
								<tr align="left" style="font-style: bold;">
									<th style="width: 60%">Name</th>
									<th style="width: 40%">Email</th>
								</tr>
								<%
									for (UserBean user : userList) {
								%>
								<tr>
									<td height="25"><%=(user.getFname() + " " + user.getLname())%></td>
									<td><%=user.getEmail()%></td>
								</tr>
								<%
									}
										} else {
								%>
								<div
									style="width: 85%; margin-bottom: 25px; background: #595959;">
									<h2>
										<font color="#fff">View feature not implemented yet</font>
									</h2>
								</div>
								<%
									}
								%>
							</tbody>
						</table>
					</s:form>
				</div>

				<div class="viewEmails" data-link="profile" align="center"
					style="margin: 40px; padding-left: 20px">
					<%
						UserBean user = (UserBean) request.getAttribute("currentUser");
					%>

					<div style="width: 65%; margin-bottom: 25px; background: #595959;">
						<h2>
							<font color="#fff">Edit Profile</font>
					</div>

					<s:form action="editprofile" method="post">
						<s:textfield name="registerBean.lname"
							value="%{userBean.getUname()}" label="Username" readonly="true" />
						<s:textfield name="registerBean.lname"
							value="%{userBean.getEmail()}" label="Email" readonly="true" />

						<s:textfield name="registerBean.fname"
							value="%{userBean.getFname()}" label="First Name" />
						<s:textfield name="registerBean.lname"
							value="%{userBean.getLname()}" label="Last Name" />
						<s:password name="registerBean.password"
							value="%{userBean.getPassword()}" label="Password" />
						<s:submit value="Update Profile" />
					</s:form>
					<!-- Yet to be implemented -->
				</div>

				<div class="viewEmails" data-link="search" align="center"
					style="margin: 40px; padding-left: 20px">


					<s:form name="search" method="post">
						<table id=recTable style="width: 85%">
							<tbody>
								<%
									ArrayList<EmailBean> emailList = (ArrayList) request.getAttribute("search");
										if (emailList != null && emailList.size() != 0) {
								%>
								<div
									style="width: 85%; margin-bottom: 25px; background: #595959;">
									<h2>
										<font color="#fff">Inbox</font>
									</h2>
								</div>
								<tr align="left" style="font-style: bold;">
									<th style="width: 8%"><input type="button" value="Trash"
										onclick="trashInboxRecord()"></th>
									<th style="width: 25%">Sender</th>
									<th style="width: 40%">Subject</th>
									<th style="width: 20%">Time</th>
								</tr>
								<%
									for (EmailBean email : emailList) {
												int id = email.getId();
								%>
								<tr>
									<td><input type="checkbox" value="<%=id%>"
										name="trashEmail"></td>
									<td><%=email.getSender()%></td>
									<td><%=email.getEmailSubject()%></td>
									<td><%=email.getDatetime()%></td>
								</tr>
								<%
									}
										} else {
								%>
								<div
									style="width: 85%; margin-bottom: 25px; background: #595959;">
									<h2>
										<font color="#fff">Search feature not yet implemented
											yet</font>
									</h2>
								</div>

								<%
									}
								%>

							</tbody>
						</table>
					</s:form>

				</div>

				<div id=footer style="background: #000;">
					<center>
						<font color="#fff">Developed by Amitkumar Sandesara</font>
					</center>
				</div>
			</div>
		</div>
	</div>
</body>
</html>