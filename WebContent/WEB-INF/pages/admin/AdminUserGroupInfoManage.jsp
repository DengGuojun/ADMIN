<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.bean.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.admin.bean.*"  %>
<%@ page import="com.lpmas.admin.config.*"  %>
<%@ page import="com.lpmas.framework.util.JsonKit"  %>
<%@ page import="com.lpmas.framework.web.ParamKit"  %>
<% 
AdminUserInfoBean bean = (AdminUserInfoBean)request.getAttribute("UserInfo");
int userId = ParamKit.getIntParameter(request, "userId", 0);

List<AdminGroupInfoBean> groupList = (List<AdminGroupInfoBean>)request.getAttribute("GroupList");
HashSet<Integer> groupSet = (HashSet<Integer>)request.getAttribute("GroupSet");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理</title>
<%@ include file="../include/header.jsp" %>
</head>
<body class="article_bg">
<%@ include file="AdminUserInfoNav.jsp"%>
<div class="article_tit">用户用户组管理</div>
<form id="formData" name="formData" method="post" action="AdminUserGroupInfoManage.do" onsubmit="javascript:return checkForm('formData');">
  <input type="hidden" name="userId" id="userId" value="<%=userId%>"/>
  <div class="modify_form">   
    <p>
      <em class="int_label">所属用户组：</em>
      <em class="int_multi_box">
      <%for(AdminGroupInfoBean groupBean:groupList){ %>
      <input type="checkbox" name="groupId" id="groupId" value="<%=groupBean.getGroupId() %>" <%=(groupSet.contains(groupBean.getGroupId()))?"checked":"" %>/><%=groupBean.getGroupName() %>
      <%} %>
      </em>
    </p>
  </div>
  <div class="div_center"><input type="submit" name="button" id="button" class="modifysubbtn" value="提交" /></div>
</form>
</body>
</html>