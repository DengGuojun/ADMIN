<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.bean.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.admin.bean.*"  %>
<% 
AdminResourceInfoBean bean = (AdminResourceInfoBean)request.getAttribute("ResourceInfo");
List<AdminResourceTypeBean> typeList = (List<AdminResourceTypeBean>)request.getAttribute("TypeList");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>资源管理</title>
<%@ include file="../include/header.jsp" %>
</head>
<body class="article_bg">
<p class="article_tit">资源管理</p>
<form id="formData" name="formData" method="post" action="AdminResourceInfoManage.do" onsubmit="javascript:return checkForm('formData');">
  <input type="hidden" name="resourceId" id="resourceId" value="<%=bean.getResourceId() %>"/>
  <input type="hidden" name="status" id="status" value="<%=bean.getStatus() %>"/>
  <div class="modify_form">
    <p>
      <em class="int_label">资源代码：</em>
      <input type="text" name="resourceCode" id="resourceCode" size="30" maxlength="30" value="<%=bean.getResourceCode() %>" checkStr="资源代码;code;true;;30"/><em><span>* (唯一)</span></em>
    </p>  
    <p>
      <em class="int_label">资源名称：</em>
      <input type="text" name="resourceName" id="resourceName" size="50" maxlength="100" value="<%=bean.getResourceName() %>" checkStr="资源名称;txt;true;;100"/><em><span>*</span></em>
    </p>  
    <p>
      <em class="int_label">资源类型：</em>
        <select name="typeId" id="typeId">
        <%for(AdminResourceTypeBean typeBean:typeList){ %>
          <option value="<%=typeBean.getTypeId() %>" <%=(typeBean.getTypeId()==bean.getTypeId())?"selected":"" %>><%=typeBean.getTypeName() %></option>
        <%} %>
        </select>
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