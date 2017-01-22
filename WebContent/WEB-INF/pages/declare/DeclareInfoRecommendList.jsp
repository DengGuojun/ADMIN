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
<%
	List<DeclareReportBean> list = (List<DeclareReportBean>)request.getAttribute("DeclareReportList");
	PageBean PAGE_BEAN = (PageBean)request.getAttribute("PageResult");
	List<String[]> COND_LIST = (List<String[]>)request.getAttribute("CondList");
%>
<%@ include file="../include/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>对象推荐</title>
<link href="<%=STATIC_URL %>/css/main.css" type="text/css" rel="stylesheet" />
<script type='text/javascript' src="<%=STATIC_URL %>/js/jquery.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/common.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/ui.js"></script>
</head>
<body class="article_bg">
<p class="article_tit">对象推荐</p>
<form name="formSearch" method="post" action="DeclareInfoRecommendList.do">
<div class="search_form">
  	<em class="em1">姓名：</em>
    <input type="text" name="userName" id="userName" value="<%=ParamKit.getParameter(request, "userName", "") %>" size="20"/>
    <em class="em1">身份证号：</em>
    <input type="text" name="identityNumber" id="identityNumber" value="<%=ParamKit.getParameter(request, "identityNumber", "") %>" size="20"/>
    <em class="em1">手机号：</em>
    <input type="text" name="userMobile" id="userMobile" value="<%=ParamKit.getParameter(request, "userMobile", "") %>" size="20"/>
    <input name="" type="submit" class="search_btn_sub" value="查询"/>
  </div>
  <table width="100%" border="0"  cellpadding="0" class="table_style">
    <tr>
      <th>姓名</th>
      <th>身份证号</th>
      <th>性别</th>
      <th>手机号</th>
      <th>类型</th>
      <th>状态</th>
      <th>操作</th>
    </tr>
    <%
    for(DeclareReportBean bean:list){%> 
    <tr>
      <td><%=bean.getUserName() %></td>
      <td><%=bean.getIdentityNumber() %></td>
      <td><%=MapKit.getValueFromMap(bean.getUserGender(), GenderConfig.GENDER_MAP) %></td>
      <td><%=bean.getUserMobile() %></td>
      <td><%=DeclareInfoConfig.DECLARE_TYPE_MAP.get(bean.getDeclareType())%></td>
      <td><%=DeclareInfoConfig.DECLARE_STATUS_MAP.get(bean.getDeclareStatus())%></td>
      <td align="center">
      	<a href="/declare/admin/DeclareInfoRecommendManage.do?declareId=<%=bean.getDeclareId()%>">填写申报表</a> 
      </td>
    </tr>	
    <%} %>
  </table>
</form>
<ul class="page_info">
<li class="page_left_btn">
  	<input type="button" name="button" id="button" value="新增本级推荐" onclick="javascript:location.href='DeclareInfoRecommendInsert.do'">
</li>
<%@ include file="../include/page.jsp" %>
</ul>
</body>
</html>