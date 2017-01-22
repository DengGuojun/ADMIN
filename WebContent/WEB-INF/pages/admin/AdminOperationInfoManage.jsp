<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.bean.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.admin.bean.*"  %>
<% 
AdminOperationInfoBean bean = (AdminOperationInfoBean)request.getAttribute("OperationInfo");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>操作管理</title>
<%@ include file="../include/header.jsp" %>
</head>
<body class="article_bg">
<p class="article_tit">操作管理</p>
<form id="formData" name="formData" method="post" action="AdminOperationInfoManage.do" onsubmit="javascript:return checkForm('formData');">
  <input type="hidden" name="operationId" id="operationId" value="<%=bean.getOperationId() %>"/>
  <div class="modify_form">
    <p>
      <em class="int_label">操作代码：</em>
      <input type="text" name="operationCode" id="operationCode" value="<%=bean.getOperationCode() %>" size="20" maxlength="20" checkStr="操作代码;code;true;;20"/><em><span>*</span></em>
    </p>  
    <p>
      <em class="int_label">操作名称：</em>
      <input type="text" name="operationName" id="operationName" value="<%=bean.getOperationName() %>" size="50" maxlength="100" checkStr="操作名称;txt;true;;100"/><em><span>*</span></em>
    </p> 
  </div>
  <div class="div_center"><input type="submit" name="button" id="button" class="modifysubbtn" value="提交" /></div>
</form>
</body>
</html>