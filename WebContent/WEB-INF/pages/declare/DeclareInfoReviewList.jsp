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
<%@ page import="com.lpmas.declare.admin.config.*"  %>
<%
	List<DeclareReportBean> list = (List<DeclareReportBean>)request.getAttribute("DeclareReportList");
	PageBean PAGE_BEAN = (PageBean)request.getAttribute("PageResult");
	List<String[]> COND_LIST = (List<String[]>)request.getAttribute("CondList");
	int modelType = ParamKit.getIntParameter(request, "modelType", 0);
%>
<%@ include file="../include/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=MapKit.getValueFromMap(modelType, DeclareInfoRecommendConfig.MODEL_TYPE_MAP) %></title>
<link href="<%=STATIC_URL %>/css/main.css" type="text/css" rel="stylesheet" />
<script type='text/javascript' src="<%=STATIC_URL %>/js/jquery.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/common.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/ui.js"></script>
</head>
<body class="article_bg">
<p class="article_tit"><%=MapKit.getValueFromMap(modelType, DeclareInfoRecommendConfig.MODEL_TYPE_MAP) %></p>
<form name="formSearch" method="post" action="DeclareInfoReviewList.do">
<input type="hidden" name="modelType" id="modelType" value="<%=modelType%>">
<div class="search_form">
    <%if(modelType != DeclareInfoRecommendConfig.TYPE_VERIFY){ %>
     <em class="em1">省：</em>
    <select name="province" id="province">
    <option value="" >全部</option>
    </select>
     <em class="em1">市：</em>
    <select name="city" id="city">
    <option value="" >全部</option>
    </select>
     <em class="em1">县：</em>
    <select name="region" id="region">
    <option value="" >全部</option>
    </select>
     <%}%>
  	<em class="em1">姓名：</em>
    <input type="text" name="userName" id="userName" value="<%=ParamKit.getParameter(request, "userName", "") %>" size="20"/>
    <em class="em1">身份证号：</em>
    <input type="text" name="identityNumber" id="identityNumber" value="<%=ParamKit.getParameter(request, "identityNumber", "") %>" size="20"/>
    <em class="em1">手机号：</em>
    <input type="text" name="userMobile" id="userMobile" value="<%=ParamKit.getParameter(request, "userMobile", "") %>" size="20"/>
     <%if(modelType == DeclareInfoRecommendConfig.TYPE_MANAGE){ %>
     <em class="em1">审核状态：</em>
    <select name="declareStatus" id="declareStatus">
    <option value="" >全部</option>
    	<%
    	String status = ParamKit.getParameter(request, "declareStatus", "");
    	for(StatusBean<String, String> statusBean:DeclareInfoRecommendConfig.REVIEW_STATUS_LIST){ %>
        <option value="<%=statusBean.getStatus() %>" <%=(statusBean.getStatus().equals(status))?"selected":"" %>><%=statusBean.getValue() %></option>
    <%} %>
    </select>
     <%} %>
    <input name="" type="submit" class="search_btn_sub" value="查询"/>
  </div>
  <table width="100%" border="0"  cellpadding="0" class="table_style">
    <tr>
      <th>行号</th>
      <th>姓名</th>
      <th>性别</th>
      <th>文化程度</th>
      <th>身份证号</th>      
      <th>手机号</th>
      <th>人员类别</th>
      <th>申请方式</th>
      <th>地区</th>
      <%if(modelType == DeclareInfoRecommendConfig.TYPE_CLASSIFY){ %>
      <th>培育类型</th>
      <%} else{%>
      <th>审核状态</th>
      <%} %>
      <%if(modelType == DeclareInfoRecommendConfig.TYPE_VERIFY){ %>
      <th></th>
      <%}else if(modelType == DeclareInfoRecommendConfig.TYPE_CLASSIFY){ %>
      <th>对象类别</th>
      <%} %>
      <th>操作</th>
    </tr>
    <% int i=0;
    for(DeclareReportBean bean:list){%> 
    <tr>
      <td><%=(PAGE_BEAN.getCurrentPageNumber()-1)*20 + ++i %></td>
      <td><%=bean.getUserName() %></td>
      <td><%=MapKit.getValueFromMap(bean.getUserGender(), GenderConfig.GENDER_MAP) %></td>
      <td><%=MapKit.getValueFromMap(bean.getEducation(), FarmerInfoConfig.EDUCATION_LEVEL_MAP) %></td>
      <td><%=bean.getIdentityNumber() %></td>
      <td><%=bean.getUserMobile() %></td>
      <%if (bean.getDeclareType() == DeclareInfoConfig.DECLARE_TYPE_YOUNG_FARMER) {%>
      <td><%=FarmerInfoConfig.YOUNG_FARMER_PERSONNEL_CATEGORY_MAP.get(bean.getFarmerType()) %></td>
	  <%} else if (bean.getDeclareType() == DeclareInfoConfig.DECLARE_TYPE_PRODUCT_FARMER) {%>
	  <td><%=FarmerInfoConfig.PRODUCT_FARMER_PERSONNEL_CATEGORY_MAP.get(bean.getFarmerType()) %></td>
	  <%}else if (bean.getDeclareType() == DeclareInfoConfig.DECLARE_TYPE_LEADER_FARMER) {%>
	  <td><%=FarmerInfoConfig.LEADER_FARMER_PERSONNEL_CATEGORY_MAP.get(bean.getFarmerType()) %></td>
	  <%} else {%>
	  <td></td>
	  <%} %>
	  <%if (bean.getUserId() == Constants.STATUS_NOT_VALID) {%>
      <td>本级推荐</td>
      <%} else {%>
      <td>个人申报</td>
	  <%} %>
      <td><%=bean.getProvince()%>/<%=bean.getCity()%>/<%=bean.getRegion()%></td>
      <%if(modelType == DeclareInfoRecommendConfig.TYPE_CLASSIFY){ %>
      <td><%=MapKit.getValueFromMap(bean.getDeclareType(), DeclareInfoConfig.DECLARE_TYPE_MAP) %></td>
      <%} else{%>
      <td><%=MapKit.getValueFromMap(bean.getDeclareStatus(), DeclareInfoRecommendConfig.REVIEW_STATUS_MAP) %></td>
      <%} %>
       <%if(modelType == DeclareInfoRecommendConfig.TYPE_VERIFY){ %>
       <td align="center">
      	<a href="/declare/admin/DeclareInfoRecommendManage.do?declareId=<%=bean.getDeclareId()%>">申报表</a> 
      </td>
      <td align="center">
      	<a href="/declare/admin/DeclareInfoRecommendAcceptCommit.do?declareId=<%=bean.getDeclareId()%>&action=<%=DeclareInfoRecommendConfig.COMMIT_ACTION_APPROVE%>&modelType=<%=modelType%>">通过</a> 
      	<a href="/declare/admin/DeclareInfoRecommendAcceptCommit.do?declareId=<%=bean.getDeclareId()%>&action=<%=DeclareInfoRecommendConfig.COMMIT_ACTION_NOT_APPROVE%>&modelType=<%=modelType%>">不通过</a> 
      </td>
      <%}else if(modelType == DeclareInfoRecommendConfig.TYPE_MANAGE){ %>
      <td align="center">
      	<a href="/declare/admin/DeclareInfoRecommendAcceptCommit.do?declareId=<%=bean.getDeclareId()%>&action=<%=DeclareInfoRecommendConfig.COMMIT_ACTION_REJECT%>&modelType=<%=modelType%>">驳回</a> 
      	<a href="/declare/admin/DeclareInfoRecommendAcceptCommit.do?declareId=<%=bean.getDeclareId()%>&action=<%=DeclareInfoRecommendConfig.COMMIT_ACTION_DELETE%>&modelType=<%=modelType%>">删除</a> 
      </td>
      <%} else if(modelType == DeclareInfoRecommendConfig.TYPE_CLASSIFY){ %>
      <td><%=MapKit.getValueFromMap(bean.getDeclareCategory(), DeclareInfoConfig.DECLARE_CATEGORY_MAP) %></td>
      <td align="center">
      	<a href="/declare/admin/DeclareInfoRecommendAcceptCommit.do?declareId=<%=bean.getDeclareId()%>&action=<%=DeclareInfoRecommendConfig.COMMIT_ACTION_CHANGE%>&modelType=<%=modelType%>">转换类型</a> 
      </td>
      <%} %>
    </tr>	
    <%} %>
  </table>
</form>
<ul class="page_info">
<%@ include file="../include/page.jsp" %>
</ul>
</body>
</html>