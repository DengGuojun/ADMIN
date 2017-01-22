<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.lpmas.framework.config.*"%>
<%@ page import="com.lpmas.framework.bean.*"%>
<%@ page import="com.lpmas.framework.page.*"%>
<%@ page import="com.lpmas.framework.util.*"%>
<%@ page import="com.lpmas.framework.web.*"%>
<%@page import="com.lpmas.constant.user.GenderConfig"%>
<%@ page import="com.lpmas.declare.config.*"  %>
<%@ page import="com.lpmas.declare.bean.*"  %>
<%@ page import="com.lpmas.declare.business.*"  %>
<% 
DeclareInfoHelper declareInfoHelper = new DeclareInfoHelper();
%>
<%@ include file="../include/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>推荐信息</title>
<link href="<%=STATIC_URL %>/css/main.css" type="text/css" rel="stylesheet" />
<script type='text/javascript' src="<%=STATIC_URL %>/js/jquery.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/common.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/ui.js"></script>
</head>
<body class="article_bg">
<p class="article_tit">推荐信息</p>
 <form id="formData" name="formData" method="post" action="DeclareInfoRecommendInsert.do" onsubmit="javascript:return checkForm('formData');">
	  <div class="modify_form">
	    <p>
	      <em class="int_label"><span>*</span>姓名：</em>
	      <input type="text" id="userName" name="userName" size="20" maxlength="50" checkStr="姓名;txt;true;;50"/>
	    </p>	
	     <p>
	      <em class="int_label"><span>*</span>身份证号：</em>
	      <input type="text" id="identityNumber" name="identityNumber" size="20" maxlength="50" checkStr="身份证号;txt;true;;50"/>
	    </p>
	     <p>
	      <em class="int_label"><span>*</span>手机号：</em>
	      <input type="number" name="userMobile" id="userMobile" size="20" maxlength="50" checkStr="手机号;txt;true;;50"/>
	    </p>    
	    <p>
	      <em class="int_label"><span>*</span>年份：</em>
	      <em><%=declareInfoHelper.getDeclareYear() %></em>
	      <input type="hidden" name="declareYear" id="declareYear" value="<%=declareInfoHelper.getDeclareYear() %>"/>
	    </p> 
	   <p>
				<em class="int_label"><span>*</span>申报类型：</em> 
				<select name="declareType" id="declareType">
					<%
						for (StatusBean<Integer,String> statusBean : DeclareInfoConfig.DECLARE_TYPE_LIST) {
					%>
					<option value="<%=statusBean.getStatus()%>"><%=statusBean.getValue()%></option>
					<%
						}
					%>
				</select>
		</p>	    
	  </div>
	  <div class="div_center">
	  	<input type="submit" name="submit" id="submit" class="modifysubbtn" value="保存" />
	  	<input type="button" name="cancel" id="cancel" value="取消" onclick="javascript:history.back()">
	  </div>
	</form>
</body>
</html>