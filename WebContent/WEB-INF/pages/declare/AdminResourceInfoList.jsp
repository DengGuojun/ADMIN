<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.framework.bean.*"  %>
<%@ page import="com.lpmas.framework.page.*"  %>
<%@ page import="com.lpmas.framework.util.*"  %>
<%@ page import="com.lpmas.framework.web.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.declare.admin.bean.*"  %>
<%@ page import="com.lpmas.declare.admin.business.*"  %>
<%@ page import="com.lpmas.declare.admin.config.*"  %>
<%
List<AdminResourceInfoBean> list = (List<AdminResourceInfoBean>)request.getAttribute("ResourceList");
PageBean PAGE_BEAN = (PageBean)request.getAttribute("PageResult");
List<String[]> COND_LIST = (List<String[]>)request.getAttribute("CondList");
HashMap<String, String> typeMap = (HashMap<String, String>)request.getAttribute("TypeMap");
List<AdminResourceTypeBean> typeList = (List<AdminResourceTypeBean>)request.getAttribute("TypeList");
String fromTag = ParamKit.getParameter(request, "fromTag","");
String callbackFun = ParamKit.getParameter(request, "callbackFun", "callbackFun");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>资源列表</title>
<%@ include file="../include/header.jsp" %>
<link href="<%=STATIC_URL %>/css/main.css" type="text/css" rel="stylesheet" />
<script type='text/javascript' src="<%=STATIC_URL %>/js/jquery.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/common.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/ui.js"></script>
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
    <input name="" type="submit" class="search_btn_sub" value="查询"/>
  </div>
  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_style">
    <tr>
      <th>资源ID</th>
      <th>资源名称</th>
      <th>资源类型</th>
      <th>操作</th>
    </tr>
    <%
    for(AdminResourceInfoBean bean:list){ 
    %>
    <tr>
      <td><%=bean.getResourceId() %></td>
      <td><%=bean.getResourceName() %></td>
      <td><%=MapKit.getValueFromMap(bean.getTypeId(), typeMap) %></td>
      <td align="center">
      <%if(fromTag.equals("modal")){ %>
      <a href="#" onclick="javascript:callbackTo('<%=bean.getResourceId()%>','<%=bean.getResourceName()%>')">选择</a>
      <%}else{ %>
      <a href="/declare/admin/AdminResourceInfoManage.do?resourceId=<%=bean.getResourceId() %>">修改</a><%} %>
      </td>
    </tr>
    <%} %>
  </table>
</form>
<ul class="page_info">
<li class="page_left_btn">
  <input type="button" name="button" id="button" value="新建" onclick="javascript:location.href='AdminResourceInfoManage.do'">
</li>
<%@ include file="../include/page.jsp" %>
</ul>
</body>
</html>