<%@ page import = "jp.co.daifuku.bluedog.sheetwriter.SheetWriterUtils" %>
<%@ page contentType="text/html;charset=utf-8" errorPage="error.jsp" %>
<%
	String sessionId = request.getSession().getId();
	String file = "";
	String path = "";

	if( sessionId != null && !sessionId.equals("") ) 
	{
		request.setCharacterEncoding("UTF-8");
		file = request.getParameter("file");
		path = jp.co.daifuku.bluedog.sheetwriter.SheetWriterUtils.getSessionTempDir( request );
	}
%> 
<html> 
<head> 
<script>
if ( window.opener != null)
{ 
window.opener.dialogWindow = null;
}
window.resizeTo(1, 1);
window.moveTo(-100, -100);
function submitDl()
{
<%
	if ( sessionId != null && !sessionId.equals("")
	    && file != null && !file.equals("") )
	{
%>
	var frm  = document.forms[0];
	frm.target = "_self";
	frm.submit();
<%
	}
	else
	{
%>
	alert("can't download due to session time-out errors.");
	window.close();
<%
	}
%>
}

</script>
</head> 
<body onLoad="submitDl()">
<form action="SheetDownLoad.jsp" name="form" method="post">
<input type="hidden" name="file" value="<%= file %>">
<input type="hidden" name="path" value="<%= path %>">
</form>
</body> 
</html> 