<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.framework.bean.*"  %>
<%@ page import="com.lpmas.framework.page.*"  %>
<%@ page import="com.lpmas.framework.util.*"  %>
<%@ page import="com.lpmas.framework.web.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.declare.admin.bean.*"  %>
<%@ page import="com.lpmas.declare.admin.business.*"  %>
<%@ page import="com.lpmas.declare.admin.config.*"  %>
<%
List<AdminRoleInfoBean> list = (List<AdminRoleInfoBean>)request.getAttribute("RoleList");
PageBean PAGE_BEAN = (PageBean)request.getAttribute("PageResult");
List<String[]> COND_LIST = (List<String[]>)request.getAttribute("CondList");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色列表</title>
<%@ include file="../include/header.jsp" %>
<link href="<%=STATIC_URL %>/css/main.css" type="text/css" rel="stylesheet" />
<script type='text/javascript' src="<%=STATIC_URL %>/js/jquery.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/common.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/ui.js"></script>
</head>
<body class="article_bg">
<p class="article_tit">角色列表</p>
<form name="formSearch" method="post" action="AdminRoleInfoList.do">
  <div class="search_form">
    <em class="em1">角色名称：</em>
    <input type="text" name="roleName" id="roleName" value="<%=ParamKit.getParameter(request, "roleName", "") %>" size="20"/>
    <em class="em1">角色状态：</em>
    <select name="status" id="status">
    	<%
    	int status = ParamKit.getIntParameter(request, "status", Constants.STATUS_VALID);
    	for(StatusBean<Integer, String> statusBean:Constants.STATUS_LIST){ %>
          <option value="<%=statusBean.getStatus() %>" <%=(statusBean.getStatus()==status)?"selected":"" %>><%=statusBean.getValue() %></option>
    <%} %>
    </select>
    <input name="" type="submit" class="search_btn_sub" value="查询"/>
  </div>
  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_style">
    <tr>
      <th>角色ID</th>
      <th>角色名称</th>
      <th>状态</th>
      <th>操作</th>
    </tr>
    <%
    for(AdminRoleInfoBean bean:list){ 
    %>
    <tr>
      <td><%=bean.getRoleId() %></td>
      <td><%=bean.getRoleName() %></td>
      <td><%=MapKit.getValueFromMap(bean.getStatus(), Constants.STATUS_MAP) %></td>
      <td align="center"><a href="/declare/admin/AdminRoleInfoManage.do?roleId=<%=bean.getRoleId() %>">修改</a></td>
    </tr>
    <%} %>
  </table>
</form>
<ul class="page_info">
<li class="page_left_btn">
  <input type="button" name="button" id="button" value="新建" onclick="javascript:location.href='AdminRoleInfoManage.do'">
</li>
<%@ include file="../include/page.jsp" %>
</ul>
</body>
</html>