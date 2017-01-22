<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.bean.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.admin.bean.*"  %>
<% 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>密码修改</title>
<%@ include file="../include/header.jsp" %>
<script>
	function checkThisForm(){
		var loginPassword=$('#loginPassword').val();
		var confirmLoginPassword=$('#confirmLoginPassword').val();
		if(!$.trim(loginPassword)){
			alert('新密码不能为空');
			return false;
		}
		var flag=false;
		if(/([a-z]|[A-Z]|[0-9]){6,12}/.test(loginPassword)){
			var len=loginPassword.length;
			if(loginPassword.replace(/[a-z]|[A-Z]/,"").length<len && loginPassword.replace(/[0-9]/,"").length<len){
				flag=true;
			}
		}
		if(flag){
			if(loginPassword != confirmLoginPassword){
				alert('确定密码与新密码不一致');
				return false;
			}
		}else{
			alert('新密码的格式不符，请输入包含数字和字母6到12位的密码');
			return false;
		}
		return checkForm(formData);
	}
</script>

</head>
<body class="article_bg">
<p class="article_tit">密码修改</p>
<form id="formData" name="formData" method="post" action="MyPasswordChange.do" onsubmit="javascript:return checkThisForm('formData');">
  <div class="modify_form">
    <p>
      <em class="int_label">密码：</em>
      <input type="password" name="loginPassword" id="loginPassword" value="" size="20" maxlength="20" checkStr="密码;txt;true;;20"/><em><span>*</span></em>
    </p>
    <p>
      <em class="int_label">确认密码：</em>
      <input type="password" name="confirmLoginPassword" id="confirmLoginPassword" value="" size="20" maxlength="20" checkStr="确认密码;txt;true;;20"/><em><span>*</span></em>
    </p>
  </div>
  <div class="div_center"><input type="submit" name="button" id="button" class="modifysubbtn" value="提交" /></div>
</form>
</body>
</html>