<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.lpmas.framework.web.*"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>用户登录-Trendy EC</title>
<%@ include file="../include/header.jsp" %>
<script>
	function checkPassword(){
		if(!$.trim($('#loginPassword').val())){
			alert('请输入旧密码');
			$('#loginPassword').focus();
			return false;
		}
		var newloginPassword=$('#newloginPassword').val();
		var confirmLoginPassword=$('#confirmLoginPassword').val();
		if(!$.trim(newloginPassword)){
			alert('新密码不能为空');
			return false;
		}
		var flag=false;
		if(/([a-z]|[A-Z]|[0-9]){6,12}/.test(newloginPassword)){
			var len=newloginPassword.length;
			if(newloginPassword.replace(/[a-z]|[A-Z]/,"").length<len && newloginPassword.replace(/[0-9]/,"").length<len){
				flag=true;
			}
		}
		if(flag){
			if(newloginPassword != confirmLoginPassword){
				alert('确定密码与新密码不一致');
				return false;
			}
		}else{
			alert('新密码的格式不符，请输入包含数字和字母6到12位的密码');
			return false;
		}
		return true;
	}
</script>
</head>
<body  class="header_bg">
<div class="login_logo"></div>
<div class="login_form">
<div class="login_con">
  <form name="formChangePassword" method="post" action="/MyPasswordChangeAndReLogon.do" onsubmit="javascript:return checkPassword();">
    	 <input type="hidden" name="userId" type="text" id="userId" value="<%=request.getAttribute("userId")%>" />
         <input type="hidden" name="returnUrl" type="text" id="returnUrl" value="<%=ParamKit.getParameter(request, "returnUrl","") %>" />
      <ul>
         <li class="login_con_tit">CMS系统后台登录-重新设置密码(<span style="color:red;">密码已过期</span>)</li>
         <li  class="login_form_li">
         	<em>旧密码</em><input type="password" name="loginPassword" id="loginPassword" value="" size="20" maxlength="20"/>
         </li>
         <li  class="login_form_li">
         	<em>新密码</em><input type="password" name="newloginPassword" id="newloginPassword" value="" size="20" maxlength="20" checkStr="密码;txt;true;;20"/>
         </li>
         <li  class="login_form_li">
         	<em>确认密码</em> <input type="password" name="confirmLoginPassword" id="confirmLoginPassword" value="" size="20" maxlength="20" checkStr="确认密码;txt;true;;20"/>
         </li>
         <li class="login_btn_sub">
         	<input name="" type="submit"  value="登 录" onmousedown="this.className='clickbtn'"  />
         </li>
     </ul>
 </form>
 </div>
</div>
<div class="footer_border"></div>
</body>
</html>
