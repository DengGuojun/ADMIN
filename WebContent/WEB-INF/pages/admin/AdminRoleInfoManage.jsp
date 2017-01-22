<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.bean.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.framework.util.*"  %>
<%@ page import="com.lpmas.admin.bean.*"  %>
<% 
AdminRoleInfoBean bean = (AdminRoleInfoBean)request.getAttribute("RoleInfo");

List<AdminResourceTypeBean> typeList = (List<AdminResourceTypeBean>)request.getAttribute("TypeList");
HashMap<Integer,String> operationMap = (HashMap<Integer,String>)request.getAttribute("OperationMap");
HashSet<String> privilegeSet = (HashSet<String>)request.getAttribute("PrivilegeSet");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色管理</title>
<%@ include file="../include/header.jsp" %>
</head>
<body class="article_bg">
<p class="article_tit">角色管理</p>
<form id="formData" name="formData" method="post" action="AdminRoleInfoManage.do" onsubmit="javascript:return checkForm('formData');">
  <input type="hidden" name="roleId" id="roleId" value="<%=bean.getRoleId() %>"/>
  <div class="modify_form">  
    <p>
      <em class="int_label">角色名称：</em>
      <input type="text" name="roleName" id="roleName" size="30" maxlength="100" value="<%=bean.getRoleName() %>" checkStr="角色名称;txt;true;;100"/><em><span>*</span></em>
    </p>
    <p>
      <em class="int_label">状态：</em>
	  <input type="checkbox" name="status" id="status" value="<%=Constants.STATUS_VALID %>" <%=(bean.getStatus()==Constants.STATUS_VALID)?"checked":"" %>/>
    </p>
    <div>
       <em class="int_label">权限：</em>
       <div class="int_multi_box">
       <%for(AdminResourceTypeBean typeBean : typeList){ %>       
         <font><%=typeBean.getTypeName() %>：</font><br/>
       <%
       List<AdminResourceInfoBean> resourceList = (List<AdminResourceInfoBean>)request.getAttribute("ResourceList_" + typeBean.getTypeId());
       for(AdminResourceInfoBean resourceBean:resourceList){
       %>
       <span><%=resourceBean.getResourceName() %></span>       <%
       List<AdminPrivilegeDefineBean> defineList = (List<AdminPrivilegeDefineBean>)request.getAttribute("DefineList_" + typeBean.getTypeId() + "_" + resourceBean.getResourceId());
       for(AdminPrivilegeDefineBean defineBean:defineList){
    	   String privilegeKey = resourceBean.getResourceId() + "_" + defineBean.getOperationId();
       %>
       <input type="checkbox" id="privilegeKey" name="privilegeKey" value="<%=privilegeKey %>" <%=(privilegeSet.contains(privilegeKey))?"checked":"" %>/><%=MapKit.getValueFromMap(defineBean.getOperationId(), operationMap) %><%} %>
       <br/><%} %><br/>
       <%} %>
       </div>
    </div>
    <p>
      <em class="int_label">备注：</em>
      <textarea type="text" name="memo" id="memo" rows="3" cols="60" checkStr="备注;txt;false;;1000"><%=bean.getMemo() %></textarea>
    </p> 
  </div>
  <div class="div_center"><input type="submit" name="button" id="button" class="modifysubbtn" value="提交" /></div>
</form>
</body>
</html>