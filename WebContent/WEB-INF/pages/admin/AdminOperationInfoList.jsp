<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.admin.bean.*"  %>
<%@ page import="com.lpmas.admin.business.*"  %>
<%@ page import="com.lpmas.admin.config.*"  %>
<%
AdminUserHelper adminHelper = (AdminUserHelper)request.getAttribute("AdminUserHelper");
List<AdminOperationInfoBean> list = (List<AdminOperationInfoBean>)request.getAttribute("OperationList");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>操作列表</title>
<%@ include file="../include/header.jsp" %>
</head>
<body class="article_bg">
<p class="article_tit">操作列表</p>
<form name="formSearch" method="post" action="AdminOperationInfoList.do">
  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_style">
    <tr>
      <th>操作代码</th>
      <th>操作名称</th>
      <th>操作</th>
    </tr>
    <%
    int rowCount = 0;
    for(AdminOperationInfoBean bean:list){ 
    %>
    <tr>    
      <td><%=bean.getOperationCode() %></td>
      <td><%=bean.getOperationName() %></td>
      <td align="center"><%if(adminHelper.hasPermission(AdminResource.ADMIN_OPERATION, OperationConfig.SEARCH)){ %><a href="/admin/AdminOperationInfoManage.do?operationId=<%=bean.getOperationId() %>">修改</a><%} %></td>
    </tr>
    <%} %>
  </table>
<ul class="page_info">
<li class="page_left_btn">
  <%if(adminHelper.hasPermission(AdminResource.ADMIN_OPERATION, OperationConfig.CREATE)){ %><input type="button" name="button" id="button" value="新建" onclick="javascript:location.href='AdminOperationInfoManage.do'"><%} %>
</li>
</ul>
</form>
</body>
</html>