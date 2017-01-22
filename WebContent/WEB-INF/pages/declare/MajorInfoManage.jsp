<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.framework.bean.*"  %>
<%@ page import="com.lpmas.framework.page.*"  %>
<%@ page import="com.lpmas.framework.util.*"  %>
<%@ page import="com.lpmas.framework.web.*"  %>
<%@ page import="com.lpmas.declare.admin.bean.*"  %>
<%@ page import="com.lpmas.declare.admin.business.*"  %>
<%@ page import="com.lpmas.declare.admin.config.*"  %>
<%
	PageBean PAGE_BEAN = (PageBean)request.getAttribute("PageResult");
	MajorInfoBean bean = (MajorInfoBean)request.getAttribute("MajorInfoBean");
	List<String[]> COND_LIST = (List<String[]>)request.getAttribute("CondList");	
	int majorId = bean.getMajorId();
	List<MajorTypeBean> majorTypeList = (List<MajorTypeBean>)request.getAttribute("MajorTypeList") ;
	String readOnly = ParamKit.getParameter(request, "readOnly","false").trim();
	request.setAttribute("readOnly", readOnly);
%>
<%@ include file="../include/header.jsp" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>专业信息管理</title>
<link href="<%=STATIC_URL %>/css/main.css" type="text/css" rel="stylesheet" />
<script type='text/javascript' src="<%=STATIC_URL %>/js/jquery.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/common.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/ui.js"></script>
</head>
<body class="article_bg">
<div class="article_tit">
	<a href="javascript:history.back()" ><img src="<%=STATIC_URL %>/images/back_forward.jpg"/></a>
	<ul class="art_nav">
		<li><a href="MajorInfoList.do">专业信息列表</a>&nbsp;>&nbsp;</li>
		<% if(majorId > 0) {%>
		<li><%=bean.getMajorName() %>&nbsp;>&nbsp;</li>
		<li>修改专业信息</li>
		<%}else{ %>
		<li>新建专业信息</li>
		<%}%>
	</ul> 
</div>
	<form id="formData" name="formData" method="post" action="MajorInfoManage.do" onsubmit="javascript:return checkForm('formData');">
	<input type="hidden" name="majorId" id="majorId" value="<%=majorId %>"/>
	<div class="modify_form">
		<p>
			<em class="int_label">专业信息名称:</em>
			 <input type="text" name="majorName" id="majorName" size="20" maxlength="50" value="<%=bean.getMajorName() %>" checkStr="专业名称;txt;true;;50"/><em><span>*</span></em>
		</p>
		<p>
	    <em class="int_label">状态：</em>
        <select  name="status" id="status" >
      	<%for(StatusBean<Integer, String> statusBean : Constants.STATUS_LIST){ %><option value="<%=statusBean.getStatus() %>" <%=(statusBean.getStatus().equals(bean.getStatus()))?"selected":"" %>><%=statusBean.getValue() %></option><%} %>
        </select>
	    </p>	
	    <p>	    
	    	<em class="int_label">专业类型：</em>    
	      	<select name="typeId" id="typeId">
	      		<%for(MajorTypeBean majorTypeBean : majorTypeList){ %><option value="<%=majorTypeBean.getMajorId() %>" <%=(majorTypeBean.getMajorId()==bean.getTypeId())?"selected":"" %>><%=majorTypeBean.getMajorName() %></option><%} %>
	      	</select><em><span>*</span></em>	       
	    </p>	 
	     <p class="p_top_border">
	      <em class="int_label">备注：</em>
	      <textarea name="memo" id="memo" cols="60" rows="3" checkStr="备注;txt;false;;1000"><%=bean.getMemo() %></textarea>
	    </p>		
	</div>
	  <div class="div_center">
	  	<input type="submit" name="submit" id="submit" class="modifysubbtn" value="提交" />
	  	<input type="button" name="cancel" id="cancel" value="取消" onclick="javascript:history.back()">
	  </div>
	</form>
</body>
<script>
$(document).ready(function() {
	var readonly = '${readOnly}';
	if(readonly=='true') {
		disablePageElement();
	}
});
</script>
</html>