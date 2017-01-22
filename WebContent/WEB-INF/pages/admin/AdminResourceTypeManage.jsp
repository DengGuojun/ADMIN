<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.bean.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.admin.bean.*"  %>
<% 
AdminResourceTypeBean bean = (AdminResourceTypeBean)request.getAttribute("TypeInfo");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>资源类型管理</title>
<%@ include file="../include/header.jsp" %>
</head>
<body class="article_bg">
<p class="article_tit">资源类型管理</p>
<form id="formData" name="formData" method="post" action="AdminResourceTypeManage.do" onsubmit="javascript:return checkForm('formData');">
  <input type="hidden" name="typeId" id="typeId" value="<%=bean.getTypeId() %>"/>
  <div class="modify_form">
    <p>
      <em class="int_label">资源类型：</em>
      <input type="text" name="typeName" id="typeName" value="<%=bean.getTypeName() %>" size="50" maxlength="100" checkStr="资源类型;txt;true;;100"/><em><span>*</span></em>
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