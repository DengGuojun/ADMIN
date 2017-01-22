<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.util.*"  %>
<%@ page import="com.lpmas.framework.web.*"  %>
<%@ page import="com.lpmas.declare.admin.bean.*"  %>
<%@ page import="com.lpmas.declare.admin.business.*"  %>
<%@ page import="com.lpmas.declare.admin.config.*"  %>
<%
List<AdminPrivilegeDefineBean> list = (List<AdminPrivilegeDefineBean>)request.getAttribute("PrivilegeList");
List<AdminOperationInfoBean> operationList = (List<AdminOperationInfoBean>)request.getAttribute("OperationList");
AdminResourceInfoBean resourceInfoBean = (AdminResourceInfoBean)request.getAttribute("ResourceInfo");
HashMap<Integer, AdminPrivilegeDefineBean> privilegeMap = (HashMap<Integer, AdminPrivilegeDefineBean>)request.getAttribute("PrivilegeMap");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>权限定义列表</title>
<%@ include file="../include/header.jsp" %>
<link href="<%=STATIC_URL %>/css/main.css" type="text/css" rel="stylesheet" />
<script type='text/javascript' src="<%=STATIC_URL %>/js/jquery.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/common.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/ui.js"></script>
<script language="JavaScript" src="<%=STATIC_URL %>/js/fancyBox/jquery.fancybox.js"></script>
<link rel="stylesheet" href="<%=STATIC_URL %>/js/fancyBox/jquery.fancybox.css" type="text/css" media="screen" />
<script type='text/javascript'>
$(document).ready(function() {
	$("#fancybox-manual-a").click(
		function() {
			$.fancybox.open({
				href : '/declare/admin/AdminResourceInfoList.do?fromTag=modal&callbackFun=callbackFun',
				type : 'iframe',
				width : 560,
				minHeight : 500,
				autoScale : false
		});
	});
});
function callbackFun(resourceId, resourceName) {
	jQuery("#resourceId").val(resourceId);
	jQuery("#resourceName").val(resourceName);
}
</script>
</head>
<body class="article_bg">
<p class="article_tit">权限定义列表</p>
<form name="formSearch" method="get" action="AdminPrivilegeDefineManage.do">
  <div class="search_form">
    <em class="em1">资源：</em>
    <input type="text" name="resourceName" id="resourceName" value="<%=resourceInfoBean.getResourceName() %>" size="50"/>
    <input type="hidden" name="resourceId" id="resourceId" value="<%=resourceInfoBean.getResourceId() %>"/>
    <input id="fancybox-manual-a" type="button" class="search_btn_sub" value="浏览..." />
    <input name="" type="submit" class="search_btn_sub" value="查询"/>
  </div>
</form>
<form name="formData" method="post" action="AdminPrivilegeDefineManage.do">
  <input type="hidden" name="resourceId" id="resourceId" value="<%=ParamKit.getIntParameter(request, "resourceId", 0) %>"/>
  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_style">
    <tr>
      <th>选择</th>
      <th>操作</th>
    </tr>
    <%
    for(AdminOperationInfoBean operationBean:operationList){
    	AdminPrivilegeDefineBean bean = new AdminPrivilegeDefineBean();
    	boolean check = false;
    	if(privilegeMap.containsKey(operationBean.getOperationId())){
    		bean = privilegeMap.get(operationBean.getOperationId());
    		check = true;
    	}
    %>
    <tr>
      <td><input type="checkbox" name="operationId" id="operationId" value="<%=operationBean.getOperationId() %>" <%=check?"checked":"" %>/></td>
      <td><%=operationBean.getOperationName() %></td>
    </tr>
    <%} %>
  </table>
</form>
<ul class="page_info">
<li class="page_left_btn">
  <input type="button" name="button" id="button" value="提交" onclick="javascript:formData.submit();">
</li>
</ul>
</body>
</html>