<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.bean.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.admin.bean.*"  %>
<% 
AdminGroupInfoBean bean = (AdminGroupInfoBean)request.getAttribute("GroupInfo");

//List<AdminRoleInfoBean> roleList = (List<AdminRoleInfoBean>)request.getAttribute("RoleList");
//HashSet<String> roleSet = (HashSet<String>)request.getAttribute("RoleSet");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户组管理</title>
<%@ include file="../include/header.jsp" %>
</head>
<body class="article_bg">
<p class="article_tit">用户组管理</p>
<form id="formData" name="formData" method="post" action="AdminGroupInfoManage.do" onsubmit="javascript:return checkForm('formData');">
  <input type="hidden" name="groupId" id="groupId" value="<%=bean.getGroupId() %>"/>
  <div class="modify_form">   
    <p>
      <em class="int_label">用户组名称：</em>
      <input type="text" name="groupName" id="groupName" value="<%=bean.getGroupName() %>" checkStr="用户组名称;txt;true;;100"/><em><span>*</span></em>
    </p>
    <p>
      <em class="int_label">状态：</em>
      <input type="checkbox" name="status" id="status" value="<%=Constants.STATUS_VALID %>" <%=(bean.getStatus()==Constants.STATUS_VALID)?"checked":"" %>/>
    </p>
     
    <p class="p_top_border">
      <em class="int_label">备注：</em>
      <textarea type="text" name="memo" id="memo" rows="3" cols="60" checkStr="备注;txt;false;;1000"><%=bean.getMemo() %></textarea>
    </p>
  </div>
  <div class="div_center"><input type="submit" name="button" id="button" class="modifysubbtn" value="提交" /></div>
</form>
</body>
</html>