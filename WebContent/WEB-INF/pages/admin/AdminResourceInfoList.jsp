<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.util.*"  %>
<%@ page import="com.lpmas.framework.page.*"  %>
<%@ page import="com.lpmas.framework.web.*"  %>
<%@ page import="com.lpmas.admin.bean.*"  %>
<%@ page import="com.lpmas.admin.business.*"  %>
<%@ page import="com.lpmas.admin.config.*"  %>
<%
AdminUserHelper adminHelper = (AdminUserHelper)request.getAttribute("AdminUserHelper");
List<AdminResourceInfoBean> list = (List<AdminResourceInfoBean>)request.getAttribute("ResourceList");
HashMap<String, String> typeMap = (HashMap<String, String>)request.getAttribute("TypeMap");
List<AdminResourceTypeBean> typeList = (List<AdminResourceTypeBean>)request.getAttribute("TypeList");

PageBean PAGE_BEAN = (PageBean)request.getAttribute("PageResult");
List<String[]> COND_LIST = (List<String[]>)request.getAttribute("CondList");

String fromTag = ParamKit.getParameter(request, "fromTag","");
String callbackFun = ParamKit.getParameter(request, "callbackFun", "callbackFun");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>资源列表</title>
<%@ include file="../include/header.jsp" %>
<script language="javascript">
function callbackTo(resourceId,resourceName){
	self.parent.<%=callbackFun %>(resourceId,resourceName);
	try{ self.parent.jQuery.fancybox.close(); }catch(e){
		console.log(e);
	}
    try{ jQuery.fancybox.close(); }catch(e){console.log(e);}
}
</script>
</head>
<body class="article_bg">
<p class="article_tit">资源列表</p>
<form name="formSearch" method="post" action="AdminResourceInfoList.do">
  <div class="search_form">
    <em class="em1">资源类型：</em>
    <select name="typeId">
      <option></option>
    <%for(AdminResourceTypeBean typeBean:typeList){ %>  
      <option value="<%=typeBean.getTypeId() %>" <%=(typeBean.getTypeId()==ParamKit.getIntParameter(request, "typeId", 0))?"selected":"" %>><%=typeBean.getTypeName() %></option><%} %>
    </select>
    <em class="em1">资源名称：</em>
    <input type="text" name="resourceName" id="resourceName" value="<%=ParamKit.getParameter(request, "resourceName", "") %>" size="20"/>
    <input type="hidden" name="fromTag" id="fromTag" value="<%=fromTag %>" />
    <input name="" type="submit" class="search_btn_sub" value="查询"/>
  </div>
  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_style">
    <tr>
      <th>资源代码</th>
      <th>资源名称</th>
      <th>资源类型</th>
      <th>操作</th>
    </tr>
    <%
    int rowCount = 0;
    for(AdminResourceInfoBean bean:list){ 
    %>
    <tr>
      <td align="left"><%=bean.getResourceCode() %></td>
      <td align="left"><%=bean.getResourceName() %></td>
      <td><%=MapKit.getValueFromMap(bean.getTypeId(), typeMap) %></td>
      <td align="center">
      <%if(fromTag.equals("modal")){ %>
      <a href="#" onclick="javascript:callbackTo('<%=bean.getResourceId()%>','<%=bean.getResourceName()%>')">选择</a>
      <%}else{ %>
      <%if(adminHelper.hasPermission(AdminResource.ADMIN_RESOURCE, OperationConfig.SEARCH)){ %><a href="/admin/AdminResourceInfoManage.do?resourceId=<%=bean.getResourceId() %>">修改</a><%} %>
      <%} %>
      </td>
    </tr>
    <%} %>
  </table>
</form>
<ul class="page_info">
<li class="page_left_btn">
  <%if(adminHelper.hasPermission(AdminResource.ADMIN_RESOURCE, OperationConfig.CREATE)){ %><input type="button" name="button" id="button" value="新建" onclick="javascript:location.href='AdminResourceInfoManage.do'"><%} %>
</li>
<%@ include file="../include/page.jsp" %>
</ul>
</body>
</html>