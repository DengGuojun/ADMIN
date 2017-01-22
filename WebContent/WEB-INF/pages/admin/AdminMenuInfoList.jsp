<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.bean.*"  %>
<%@ page import="com.lpmas.framework.page.*"  %>
<%@ page import="com.lpmas.framework.util.*"  %>
<%@ page import="com.lpmas.framework.web.*"  %>
<%@ page import="com.lpmas.admin.bean.*"  %>
<%@ page import="com.lpmas.admin.business.*"  %>
<%@ page import="com.lpmas.admin.config.*"  %>
<%
AdminUserHelper adminHelper = (AdminUserHelper)request.getAttribute("AdminUserHelper");
List<AdminMenuInfoBean> list = (List<AdminMenuInfoBean>)request.getAttribute("MenuInfoList");

PageBean PAGE_BEAN = (PageBean)request.getAttribute("PageResult");
List<String[]> COND_LIST = (List<String[]>)request.getAttribute("CondList");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单列表</title>
<%@ include file="../include/header.jsp" %>
</head>
<body class="article_bg">
<p class="article_tit">菜单列表</p>
<form name="formSearch" method="post" action="AdminMenuInfoList.do">
  <div class="search_form">
    <em class="em1">菜单名称：</em>
    <input type="text" name="menuName" id="menuName" value="<%=ParamKit.getParameter(request, "menuName", "") %>" size="20"/>
    <em class="em1">菜单代码：</em>
    <input type="text" name="menuCode" id="menuCode" value="<%=ParamKit.getParameter(request, "menuCode", "") %>" size="20"/>
    <em class="em1">菜单类型：</em>
    <select name="menuType" id="menuType">
    	<option></option>
    	<%
    	int menuType = ParamKit.getIntParameter(request, "menuType", 0);
    	for(StatusBean<Integer, String> statusBean:AdminConfig.MENU_TYPE_LIST){ %>
          <option value="<%=statusBean.getStatus() %>" <%=(statusBean.getStatus()==menuType)?"selected":"" %>><%=statusBean.getValue() %></option>
        <%} %>
    </select>
    <input name="" type="submit" class="search_btn_sub" value="查询"/>
	<%if(adminHelper.hasPermission(AdminResource.ADMIN_MENU, OperationConfig.CREATE)){ %>
	<input type="button" name="button" id="button"  class="search_btn_sub" value="新建" onclick="javascript:location.href='AdminMenuInfoManage.do'">
	<%} %>
  </div>
  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_style">
    <tr>
      <th>菜单代码</th>
      <th>菜单名称</th>
      <th>菜单类型</th>
      <th>操作</th>
    </tr>
    <%
    int rowCount = 0;
    for(AdminMenuInfoBean bean:list){ 
    %>
    <tr>
      <td><%=bean.getMenuCode() %></td>
      <td><%=bean.getMenuName() %></td>
      <td><%=MapKit.getValueFromMap(bean.getMenuType(), AdminConfig.MENU_TYPE_MAP) %></td>
      <td align="center"><%if(adminHelper.hasPermission(AdminResource.ADMIN_MENU, OperationConfig.SEARCH)){ %><a href="/admin/AdminMenuInfoManage.do?menuId=<%=bean.getMenuId() %>">修改</a><%} %></td>
    </tr>
    <%} %>
  </table>
</form>
<ul class="page_info">
<%@ include file="../include/page.jsp" %>
</ul>
</body>
</html>