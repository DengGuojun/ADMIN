<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.lpmas.framework.util.*"  %>
<%@ page import="com.lpmas.framework.web.*"  %>
<%@ page import="com.lpmas.admin.config.*"  %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Trendy EC</title>
<link type="image/x-icon" rel="shortcut icon" href="css/favicon.ico" /> 
</head>
<%
 String goAction = ParamKit.getParameter(request, "goAction", ""); 
 if(!StringKit.isValid(goAction)){
	 goAction = "system";
 }
%>
<frameset rows="99,*,5" framespacing="0" frameborder="no" border="0" bordercolor="#fff">
  <frame src="/admin/AdminTopFrame.do?goAction=<%=goAction%>" name="logoFrame" frameborder="no" scrolling="NO" noresize >
  <frameset cols="200,*" framespacing="0" frameborder="no" border="0" bordercolor="#fff">
    <frame src="/admin/AdminMenuFrame.do" name="navFrame" frameborder="no" scrolling="AUTO">
    <frame src="/include/welcome.html" name="conFrame" frameborder="no">
  </frameset>
  <frame src="/include/footer.html" name="bottomFrame" frameborder="no" scrolling="NO" noresize >
</frameset>
<noframes>
</noframes>
</html>

