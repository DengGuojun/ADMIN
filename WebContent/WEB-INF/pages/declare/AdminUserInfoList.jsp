<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.framework.bean.*"  %>
<%@ page import="com.lpmas.framework.util.*"  %>
<%@ page import="com.lpmas.framework.page.*"  %>
<%@ page import="com.lpmas.framework.web.*"  %>
<%@ page import="com.lpmas.declare.admin.config.*"  %>
<%@ page import="com.lpmas.declare.admin.bean.*"  %>
<%@ page import="com.lpmas.declare.admin.business.*"  %>
<%
	PageResultBean<AdminUserInfoBean> result = (PageResultBean<AdminUserInfoBean>)request.getAttribute("UserList");
	List<AdminUserInfoBean> list = result.getRecordList();
	Map<Integer, String> userRoleNameMap = (Map<Integer, String>)request.getAttribute("UserRoleNameMap");

	PageBean PAGE_BEAN = (PageBean)request.getAttribute("PageResult");
	List<String[]> COND_LIST = (List<String[]>)request.getAttribute("CondList");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户列表</title>
<%@ include file="../include/header.jsp" %>
<link href="<%=STATIC_URL %>/css/main.css" type="text/css" rel="stylesheet" />
<script type='text/javascript' src="<%=STATIC_URL %>/js/jquery.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/common.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/ui.js"></script>
</head>
<body class="article_bg">
<p class="article_tit">用户列表</p>
<form name="formSearch" method="post" action="AdminUserInfoList.do">
  <div class="search_form">
  	<em class="em1">登录名：</em><input type="text" id="loginId" name="loginId" value="<%=ParamKit.getParameter(request, "loginId", "") %>"/>
    <em class="em1">联系人：</em><input type="text" id="adminUserName" name="adminUserName" value="<%=ParamKit.getParameter(request, "adminUserName", "") %>"/>
    <em class="em1">用户状态：</em>
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
      <th>联系人</th>
      <th>性别</th>
      <th>出生日期</th>
      <th>部门/职务</th>
      <th>联系电话</th>
      <th>手机</th>
      <th>传真</th>
      <th>电子邮箱</th>
      <th>登录名</th>
      <th>登录名状态</th>
      <th>角色</th>
      <th>操作</th>
    </tr>
    <%
    for(AdminUserInfoBean bean:list){ 
    %>
    <tr>    
   		<td><%=bean.getAdminUserName() %></td>
   	   	<td><%=bean.getAdminUserGender() %></td>
   	   	<td><%=bean.getAdminUserBirthday() %></td>
   	   	<td><%=bean.getAdminUserPose() %></td>
   	   	<td><%=bean.getAdminUserPhone() %></td>
   	   	<td><%=bean.getAdminUserTelephone() %></td>
   	   	<td><%=bean.getAdminUserFax() %></td>
   	   	<td><%=bean.getAdminUserEmail()%></td>
      	<td><%=bean.getLoginId() %></td>
      	<td><%=MapKit.getValueFromMap(bean.getStatus(),Constants.STATUS_MAP) %></td>
      	<td><%=MapKit.getValueFromMap(bean.getUserId(), userRoleNameMap) %></td>
      	<td><a href="AdminUserInfoManage.do?userId=<%=bean.getUserId() %>">编辑</a></td>
    </tr>
    <%} %>
  </table>
</form>
<ul class="page_info">
<li class="page_left_btn">
 <input type="button" name="button" id="button" value="新建" onclick="javascript:location.href='AdminUserInfoManage.do'">
</li>
<%@ include file="../include/page.jsp" %>
</ul>
</body>
</html>