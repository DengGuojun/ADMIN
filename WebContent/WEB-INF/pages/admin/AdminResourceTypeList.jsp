<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.admin.bean.*"  %>
<%@ page import="com.lpmas.admin.business.*"  %>
<%@ page import="com.lpmas.admin.config.*"  %>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.util.*"  %>
<%@ page import="com.lpmas.framework.page.*"  %>
<%@ page import="com.lpmas.framework.web.*"  %>
<%
AdminUserHelper adminHelper = (AdminUserHelper)request.getAttribute("AdminUserHelper");
List<AdminResourceTypeBean> list = (List<AdminResourceTypeBean>)request.getAttribute("TypeList");

PageBean PAGE_BEAN = (PageBean)request.getAttribute("PageResult");
List<String[]> COND_LIST = (List<String[]>)request.getAttribute("CondList");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>资源类型列表</title>
<%@ include file="../include/header.jsp" %>
</head>
<body class="article_bg">
<p class="article_tit">资源类型列表</p>
<form name="formSearch" method="post" action="AdminResourceTypeList.do">
  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_style">
    <tr>
      <th>类型ID</th>
      <th>类型名称</th>
      <th>操作</th>
    </tr>
    <%
    int rowCount = 0;
    for(AdminResourceTypeBean bean:list){ 
    %>
    <tr>
      <td><%=bean.getTypeId() %></td>
      <td><%=bean.getTypeName() %></td>
      <td align="center"><%if(adminHelper.hasPermission(AdminResource.ADMIN_RESOURCE_TYPE, OperationConfig.SEARCH)){ %><a href="/admin/AdminResourceTypeManage.do?typeId=<%=bean.getTypeId() %>">修改</a><%} %></td>
    </tr>
    <%} %>
  </table>
</form>
<ul class="page_info">
<li class="page_left_btn">
  <%if(adminHelper.hasPermission(AdminResource.ADMIN_RESOURCE_TYPE, OperationConfig.CREATE)){ %><input type="button" name="button" id="button" value="新建" onclick="javascript:location.href='AdminResourceTypeManage.do'"><%} %>
</li>
<%@ include file="../include/page.jsp" %>
</ul>
</body>
</html>