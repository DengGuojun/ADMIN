<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.lpmas.framework.web.*"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>用户登录-Trendy EC</title>
<%@ include file="../include/header.jsp" %>
</head>
<body  class="header_bg">
<div class="login_logo"></div>
<div class="login_form">
<div class="login_con">
  <form name="formLogon" method="post" action="/Logon.do">
  	<p><%=ParamKit.getParameter(request, "returnUrl", "") %></p>
    <input type="hidden" name="returnUrl" id="returnUrl" value="<%=ParamKit.getParameter(request, "returnUrl", "") %>"/>
    <ul>
         <li class="login_con_tit">CMS系统后台登录</li>
         <li class="login_form_li" >
         	<em>用户名</em><input name="loginId" type="text" id="loginId"/>
         </li>
         <li  class="login_form_li">
         	<em>密&nbsp;&nbsp;   码</em><input type="password" name="loginPassword" id="loginPassword"/>
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
