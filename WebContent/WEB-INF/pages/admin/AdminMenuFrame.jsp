<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.admin.config.*"  %>
<%@ page import="com.lpmas.admin.business.*"  %>
<%@ page import="com.lpmas.admin.bean.*"  %>
<%
AdminUserHelper adminHelper = new AdminUserHelper(request);
List<AdminMenuInfoBean> rootList = (List<AdminMenuInfoBean>)request.getAttribute("MenuRootList");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Trendy EC</title>
<%@ include file="../include/header.jsp" %>
</head>
<%if(rootList.size()==0){
%>
<body class="aside_no_bg">
<%}
else{
%>
<body class="aside_bg">
<%}%>
<ul class="aside_nav" id="aside_nav">
<%  String target = "conFrame";
for(AdminMenuInfoBean rootBean : rootList){%>
<li>
<p class="tit"><em>&nbsp;</em><span><%=rootBean.getMenuName() %></span></p>
<p>
<%
List<AdminMenuInfoBean> itemList = (List<AdminMenuInfoBean>)request.getAttribute("MenuItemList_"+rootBean.getMenuId());
for(AdminMenuInfoBean itemBean : itemList){
	String menuUrl = itemBean.getMenuUrl();
	if(itemBean.getMenuUrl()!=null && itemBean.getMenuUrl().indexOf("http://") == -1){//if none, inherit parent menu url
		menuUrl = rootBean.getMenuUrl() + itemBean.getMenuUrl();
	}
	if(menuUrl.contains("ProgramInfoFrame.do")) target = "_blank";
%>
<a href="<%=menuUrl%>" target="<%=target%>"><%=itemBean.getMenuName() %></a>
<%} %>
</p>
</li>
<%} %>
</ul>

</body>
</html>
