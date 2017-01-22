<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.util.*"  %>
<%@ page import="com.lpmas.framework.web.*"  %>
<%@ page import="com.lpmas.admin.business.*"  %>
<%@ page import="com.lpmas.admin.bean.*"  %>
<%@ page import="com.lpmas.admin.config.*"  %>
<%
AdminUserHelper adminHelper = new AdminUserHelper(request, response);
List<AdminMenuInfoBean> list = (List<AdminMenuInfoBean>)request.getAttribute("MenuList");

String defaultChoice = "";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Trendy EC</title>
<%@ include file="../include/header.jsp" %>
</head>
<body class="header_nav_bg">
<div class="header_logo"></div>
<ul class="header_nav" id="header_nav">
<%for(AdminMenuInfoBean bean:list){ %>
<li <%=defaultChoice.equalsIgnoreCase(bean.getMenuCode())?"class=\"li1\"":""%> >
	<a href="/admin/AdminMenuFrame.do?menuId=<%=bean.getMenuId() %>" target="navFrame"><%=bean.getMenuName() %></a></li>
<%} %>
<li class="li3" id='quitLink'>您好，<%=adminHelper.getAdminUserInfo().getAdminUserName() %>，欢迎使用cms后台  ｜  <a href="/admin/MyPasswordChange.do" target="conFrame">修改密码</a> ｜  <a href="/Logout.do" target="_top">退出</a></li>
</ul>
</body>
</html>
