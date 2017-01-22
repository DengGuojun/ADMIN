<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.bean.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.admin.bean.*"  %>
<%@ page import="com.lpmas.admin.business.*"  %>
<%@ page import="com.lpmas.admin.config.*"  %>
<% 
AdminMenuInfoBean bean = (AdminMenuInfoBean)request.getAttribute("MenuInfo");
AdminMenuInfoBean parentBean = (AdminMenuInfoBean)request.getAttribute("ParentMenuInfo");
List<AdminOperationInfoBean> operationList = (List<AdminOperationInfoBean>)request.getAttribute("OperationList");

AdminResourceInfoBean resourceBean = (AdminResourceInfoBean)request.getAttribute("ResourceInfo");
Integer operationId = (Integer)request.getAttribute("OperationId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单管理</title>
<%@ include file="../include/header.jsp" %>
<script language="JavaScript" src="/js/fancyBox/jquery.fancybox.js"></script>
<link rel="stylesheet" href="/js/fancyBox/jquery.fancybox.css" type="text/css" media="screen" />
<script type='text/javascript'>
$(document).ready(function() {
	$("#fancybox-manual-a").click(
		function() {
			$.fancybox.open({
				href : '/admin/AdminMenuInfoTreeList.do?fromTag=modal&callbackFun=callbackFunParentMenu',
				type : 'iframe',
				width : 560,
				minHeight : 500
		});
	});
});
function callbackFunParentMenu(menuId, menuName) {
	jQuery("#parentMenuId").val(menuId);
	jQuery("#parentMenuName").val(menuName);
}

function clearParentMenu(menuId, menuName) {
	jQuery("#parentMenuId").val("0");
	jQuery("#parentMenuName").val("");
}

$(document).ready(function() {
	$("#fancybox-manual-b").click(
		function() {
			$.fancybox.open({
				href : '/admin/AdminResourceInfoList.do?fromTag=modal&callbackFun=callbackFun',
				type : 'iframe',
				width : 560,
				maxHeight : 600,
				minHeight : 600,
				autoScale : false
		});
	});
});
function callbackFun(resourceId, resourceName) {
	jQuery("#resourceId").val(resourceId);
	jQuery("#resourceName").val(resourceName);
}

function clearResource(){
	jQuery("#resourceId").val("");
	jQuery("#resourceName").val("");
}
</script>
</head>
<body class="article_bg">
<p class="article_tit">菜单管理</p>
<form id="formData" name="formData" method="post" action="AdminMenuInfoManage.do" onsubmit="javascript:return checkForm('formData');">
  <input type="hidden" name="menuId" id="menuId" value="<%=bean.getMenuId() %>"/>
  <div class="modify_form">
    <p>
      <em class="int_label">菜单代码：</em>
      <input type="text" name="menuCode" id="menuCode" size="50" maxlength="50" value="<%=bean.getMenuCode() %>" checkStr="菜单代码;code;true;;50"/><em><span>*</span></em>
    </p>  
    <p>
      <em class="int_label">菜单名称：</em>
      <input type="text" name="menuName" id="menuName" size="50" maxlength="100" value="<%=bean.getMenuName() %>" checkStr="菜单名称;txt;true;;100"/><em><span>*</span></em>
    </p>  
    <p>
      <em class="int_label">上级菜单：</em>
      <input type="text" name="parentMenuName" id="parentMenuName" size="30" value="<%=parentBean.getMenuName() %>" checkStr="上级菜单;txt;true;;100"/>
      <input type="hidden" name="parentMenuId" id="parentMenuId" value="<%=bean.getParentMenuId() %>" checkStr="上级菜单;txt;true;;100"/>
      <input id="fancybox-manual-a" type="button" class="search_btn_group" value="浏览..." />
      <input type="button" class="search_btn_group" value="清空" onclick="javascript:clearParentMenu();" />
      <em><span>*</span></em>
    </p>  
    <p>
      <em class="int_label">菜单类型：</em>
       <select name="menuType" id="menuType">
        <%for(StatusBean<Integer, String> statusBean:AdminConfig.MENU_TYPE_LIST){ %>
          <option value="<%=statusBean.getStatus() %>" <%=(statusBean.getStatus()==bean.getMenuType())?"selected":"" %>><%=statusBean.getValue() %></option>
        <%} %>
        </select>
    </p>  
    <p>
      <em class="int_label">菜单链接：</em>
      <input type="text" name="menuUrl" id="menuUrl" size="80" maxlength="200" value="<%=bean.getMenuUrl() %>" checkStr="菜单链接;txt;false;;200"/>
    </p>  
    <p>
      <em class="int_label">菜单权限：</em>
      <input type="text" name="resourceName" id="resourceName" size="20" value="<%=resourceBean.getResourceName() %>"/>
      <input type="hidden" name="resourceId" id="resourceId" value="<%=resourceBean.getResourceId() %>"/>
      <input id="fancybox-manual-b" type="button" class="search_btn_group" value="浏览..." />
      <input type="button" class="search_btn_group" value="清空" onclick="javascript:clearResource();" />
      <select name="operationId" id="operationId" onchange="javascript:buildMenuPrivilege();">
      	<option></option>
      	<%for(AdminOperationInfoBean operationBean:operationList){ %>
      	<option value="<%=operationBean.getOperationId() %>" <%=(operationBean.getOperationId()==operationId)?" selected":""%>><%=operationBean.getOperationName() %></option>
      	<%} %>
      </select>
    </p>  
    <p>
      <em class="int_label">菜单描述：</em>
      <input type="text" name="menuDesc" id="menuDesc" size="100" maxlength="200" value="<%=bean.getMenuDesc() %>" checkStr="资源名称;txt;false;;200"/>
    </p>  
    <p>
      <em class="int_label">优先级：</em>
      <select name="priority" id="priority">
      <%for(int i=1;i<=50;i++){ %>
      	<option value="<%=i %>" <%=(i==bean.getPriority())?"selected":"" %>><%=i %></option>
      <%} %>
      </select>
      <em><span>*</span></em>
    </p>  
    <p>
      <em class="int_label">是否显示：</em>
      <input type="checkbox" name="isDisplay" id="isDisplay" value="<%=Constants.STATUS_VALID %>" <%=(bean.getIsDisplay()==Constants.STATUS_VALID)?"checked":"" %>/>
    </p>
    <p>
      <em class="int_label">状态：</em>
      <input type="checkbox" name="status" id="status" value="<%=Constants.STATUS_VALID %>" <%=(bean.getStatus()==Constants.STATUS_VALID)?"checked":"" %>/>
    </p> 
    <p class="p_top_border">
      <em class="int_label">备注：</em>
      <textarea type="text" name="memo" id="memo" rows="3" cols="60"><%=bean.getMemo() %></textarea>
    </p>  
  </div>
  <div class="div_center"><input type="submit" name="button" id="button" class="modifysubbtn" value="提交" /></div>
</form>
</body>
</html>