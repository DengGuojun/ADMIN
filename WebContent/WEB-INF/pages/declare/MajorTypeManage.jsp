<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.framework.bean.*"  %>
<%@ page import="com.lpmas.framework.page.*"  %>
<%@ page import="com.lpmas.framework.util.*"  %>
<%@ page import="com.lpmas.framework.web.*"  %>
 <%@ page import="com.lpmas.declare.admin.bean.*"  %>
<%@ page import="com.lpmas.declare.config.*"  %>
<%@ page import="com.lpmas.declare.bean.*"  %>
<%
	List<String[]> COND_LIST = (List<String[]>)request.getAttribute("CondList");
	MajorTypeBean bean = (MajorTypeBean)request.getAttribute("MajorTypeBean");
	int majorId = bean.getMajorId() ;
%>
<%@ include file="../include/header.jsp" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>专业类型管理</title>
<link href="<%=STATIC_URL %>/css/main.css" type="text/css" rel="stylesheet" />
<script type='text/javascript' src="<%=STATIC_URL %>/js/jquery.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/common.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/ui.js"></script>
</head>
<body class="article_bg">
<div class="article_tit">
	<a href="javascript:history.back()" ><img src="<%=STATIC_URL %>/images/back_forward.jpg"/></a>
	<ul class="art_nav">
		<li><a href="MajorTypeList.do">专业类型列表</a>&nbsp;>&nbsp;</li>
		<% if(majorId > 0) {%>
		<li><%=bean.getMajorName() %>&nbsp;>&nbsp;</li>
		<li>修改专业类型信息</li>
		<%}else{ %>
		<li>新建专业类型信息</li>
		<%}%>
	</ul> 
</div>
	<form id="formData" name="formData" method="post" action="MajorTypeManage.do" onsubmit="javascript:return checkForm('formData');">
	<input type="hidden" name="majorId" id="majorId" value="<%=majorId %>"/>
	<div class="modify_form">
		<p>
			<em class="int_label">专业类型名称:</em>
			 <input type="text" name="majorName" id="majorName" size="20" maxlength="50" value="<%=bean.getMajorName() %>" checkStr="专业名称;txt;true;;50"/><em><span>*</span></em> 
		</p>
		<p>
	      <em class="int_label">有效状态：</em>
	      <select name="status" id="status">
    	<%
    	int status = ParamKit.getIntParameter(request, "status", Constants.STATUS_VALID);
    	for(StatusBean<Integer, String> statusBean:Constants.STATUS_LIST){ %>
        <option value="<%=statusBean.getStatus() %>" <%=(statusBean.getStatus()==status)?"selected":"" %>><%=statusBean.getValue() %></option>
    <%} %>
    </select>
	    </p>
	     <p>
    	   <em class="int_label">地区：</em>
    	   
    	   <input type="hidden" name="province" id="province" value="<%=bean.getProvince()%>"/>
    	   <input type="hidden" name="city" id="city" value="<%=bean.getCity()%>"/>
    	   <input type="hidden" name="region" id="region" value="<%=bean.getRegion()%>"/>
    
       <select class="form-control" name="selectProvince" id="selectProvince" onchange="showCity()" style="width:100px">
   	   </select>
       <select class="form-control" name="selectProvince" id="selectCity" onchange="showRegion()" style="width:100px">
       </select>
       <select class="form-control" name="selectProvince" id="selectRegion" onchange="setRegion()" style="width:100px">
       </select>
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
	showProvince();
});
function showProvince(){
	$.ajax({
        type: 'POST',
        dataType:'jsonp',
        url: "http://www.lpmas.com/m/ProvinceList.action?jsoncallback=provinceData",
        success: function(data){
        },
        error: function(){
            return;
        }
    });
} 

function provinceData(data){
	var sel = $("#selectProvince"); 
	sel.empty();   

	var city = $("#selectCity") ;
	city.empty() ;  //清除城市下拉框
	
	var region = $("#selectRegion") ;
	region.empty() ;  //清除区域下拉框

	sel.append("<option value =''>国家</option>");
	
	var items=data.content.provinceList;
    	if(items != null) {
	   	for(var i =0;i<items.length;i++) {
	      	var item=items[i];
	      	var fixProvince = "<%=bean.getProvince()%>";
	      	if(fixProvince == item.provinceName){
	      		sel.append("<option value = '"+item.provinceId+"' selected>"+item.provinceName+"</option>");
	      	}else{
	      		sel.append("<option value = '"+item.provinceId+"'>"+item.provinceName+"</option>");
	      	}
	    };
	    if(fixProvince != ""){
	    		showCity();
	    }
    } else{
   		sel.empty();  
    }
}

function showCity(){
	var provinceId = $("#selectProvince").val();
	alert("provinceId " + provinceId) ;
	var provinceName = $("#selectProvince  option:selected").text();
	
	$("#city").val("") ; //清空隐藏域的值
	$("#region").val("") ; //清空隐藏域的值
	if("国家" != provinceName ){
	$("#province").val(provinceName);//设置隐藏域的值 province
	}
	$.ajax({
        type: 'POST',
        dataType:'jsonp',
        url: "http://www.lpmas.com/m/CityList.action?provinceId="+provinceId+"&jsoncallback=cityData",
        success: function(data){
        },
        error: function(){
            return;
        }
    });
} 

function cityData(data){
	var sel = $("#selectCity");  
	sel.empty();  
	
	var region = $("#selectRegion") ;
	region.empty() ;  //清除区域下拉框
	
	sel.append("<option value = ''></option>");
	var items=data.content.cityList;
    	if(items != null) {
	   	for(var i =0;i<items.length;i++) {
	      	var item=items[i];
	      	var fixCity = "<%=bean.getCity()%>";
	      	if(fixCity == item.cityName){
	      		sel.append("<option value = '"+item.cityId+"' selected>"+item.cityName+"</option>");
	      	}else{
	      		sel.append("<option value = '"+item.cityId+"'>"+item.cityName+"</option>");
	      	}
	    };
	    if(fixCity != ""){
	    		showRegion();
	    }
    } else{
   		sel.empty();  
    }
}

function showRegion(){
	var cityId = $("#selectCity").val();
	var cityName = $("#selectCity  option:selected").text();
	$("#city").val(cityName);
	$("region").val("") ; 
	
	$.ajax({
        type: 'POST',
        dataType:'jsonp',
        url: "http://www.lpmas.com/m/RegionList.action?cityId="+cityId+"&jsoncallback=regionData",
        success: function(data){
        },
        error: function(){
            return;
        }
    });
} 

function regionData(data){
	var sel = $("#selectRegion");  
	sel.empty();  
	sel.append("<option value = ''></option>");
	var items=data.content.regionList;
    	if(items != null) {
	   	for(var i =0;i<items.length;i++) {
	      	var item=items[i];
	      	var fixRegion = "<%=bean.getRegion()%>";
	      	if(fixRegion == item.regionName){
	      		sel.append("<option value = '"+item.regionId+"' selected>"+item.regionName+"</option>");
	      		isFix = true;
	      	}else{
	      		sel.append("<option value = '"+item.regionId+"'>"+item.regionName+"</option>");
	      	}
	    };
    } else{
   		sel.empty();  
    }
}
function setRegion(){
	var regionName = $("#selectRegion  option:selected").text();
	$("#region").val(regionName);
}
</script>
</html>