<%--
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
--%>
<%@ page contentType="text/html;charset=utf-8" errorPage="error.jsp" %>
<%@ page import = "java.io.*" %>
<%
	request.setCharacterEncoding("UTF-8");
	String fileName = request.getParameter("file");
	String filePath = request.getParameter("path");
	String data_path = filePath + "/" + fileName;

	if( fileName != null && !fileName.equals("") )
	{

		// clear cache.
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "private"); 
		response.setDateHeader("Expires", 0);

		// check ContentType by extention
		int i = fileName.lastIndexOf('.');
		String extention  = fileName.substring( i , fileName.length() );
		if( extention.equals(".xls") )
		{
			response.setContentType("application/excel; charset=Shift_Jis");
		}
		else if( extention.equals(".csv") )
		{
			response.setContentType("text/comma-separated-values; charset=Shift_Jis");
		}
		else
		{
			response.setContentType("text/html; charset=Shift_Jis");
		}

		// show dialog
		response.setHeader("Content-Disposition", "attachment; filename=" + new String((fileName.trim()).getBytes("MS932"),"ISO-8859-1" ) );

		BufferedInputStream  input  = new BufferedInputStream(new FileInputStream(data_path));
		BufferedOutputStream output = new BufferedOutputStream(response.getOutputStream());
		try
		{
			// start transfer
			int x;
			while ( (x = input.read()) >= 0 )
			{
				output.write(x);
			}
		}
		catch (Exception e)
		{
		}
		finally
		{
			input.close();
			output.close();
			
			// Add 2007/04/04 Y.Muni
			out.clear();
			out = pageContext.pushBody();
		}
	}
%>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<body>
</body>
</html>
