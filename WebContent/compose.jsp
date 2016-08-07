<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Compose Email</title>
<script>
	function closeSelf() {
		self.close();
		return true;
	}
</script>
</head>
<body>

	<div style="display: block">
	
		<s:form action="sendEmail" method="post" style="float:left">
			<s:textfield name="emailBean.recipent" style="width:400px;" label="To" />
			<s:textfield name="emailBean.emailSubject" style="width:400px;"
				label="Subject" />
			<s:label label="Description" />
			<s:textarea name="emailBean.emailContent"
				style="width:400px; height:300px;  max-width: 480px;" />
			<%-- <s:file name="attachment" label="Attachment"
				accept="text/html,text/plain" />
 --%>
			<s:submit style="width:120px; height:24px" value="Send"
				onclick="closeSelf()" />
			<s:reset name="reset" value="Reset"></s:reset>
		</s:form>
	</div>
</body>
</html>