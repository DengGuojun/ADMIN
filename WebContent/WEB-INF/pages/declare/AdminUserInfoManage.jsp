<%@page import="com.lpmas.constant.user.GenderConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.bean.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.declare.admin.bean.*"  %>
<%@ page import="com.lpmas.declare.admin.config.*"  %>
<%@ page import="com.lpmas.framework.util.JsonKit"  %>
<%@ page import="com.lpmas.constant.*"  %>
<% 
	AdminUserInfoBean bean = (AdminUserInfoBean)request.getAttribute("UserInfo");
	AdminRoleUserBean roleUserBean = (AdminRoleUserBean)request.getAttribute("RoleUser");
	List<AdminRoleInfoBean> roleList = (List<AdminRoleInfoBean>)request.getAttribute("RoleList");
	int userId = bean.getUserId();
	boolean updateFlag = bean.getUserId() > 0;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理</title>
<%@ include file="../include/header.jsp" %>
<link href="<%=STATIC_URL %>/css/main.css" type="text/css" rel="stylesheet" />
<script type='text/javascript' src="<%=STATIC_URL %>/js/jquery.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/common.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/ui.js"></script>
<script type="text/javascript" src="<%=STATIC_URL%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
function isCheckLoginId(str){
  var re=/^[a-zA-Z0-9_\.@]+?$/;
  return re.test(str);
}

function checkThisForm(form){
	var loginId = $("#loginId").val();
	if(!isCheckLoginId(loginId)){
		alert('登陆ID只能录入字母、数字、下划线、小数点、"@"！');
		return false;
	}
	var updateFlag=$('#userId').val() > 0;
	var loginPassword=$.trim($('#loginPassword').val());
	if(!loginPassword&&!updateFlag){
		alert('密码不能为空');
		$('#loginPassword').focus();
		return false;
	}
	if(loginPassword){
		var flag=false;
		if(/([a-z]|[A-Z]|[0-9]){6,12}/.test(loginPassword)){
			var len=loginPassword.length;
			if(loginPassword.replace(/[a-z]|[A-Z]/,"").length<len && loginPassword.replace(/[0-9]/,"").length<len){
				flag=true;
			}
		}
		if(!flag){
			alert('新密码的格式不符，请输入包含数字和字母6到12位的密码');
			return false;
		}
	}
	return checkForm(form);
}
</script>

</head>
<body class="article_bg">
<div class="article_tit">
用户信息管理
</div>
<form id="formData" name="formData" method="post" action="AdminUserInfoManage.do" onsubmit="javascript:return checkThisForm('formData');">
  <input type="hidden" name="userId" id="userId" value="<%=bean.getUserId() %>"/>
  <div class="modify_form">
  	<p>
      <em class="int_label">用户名：</em>
      <input type="text" name="adminUserName" id="adminUserName" size="30" maxlength="50" value="<%=bean.getAdminUserName() %>" checkStr="用户名;txt;true;;100"/><em><span>*</span></em>
    </p> 
    <p>
      <em class="int_label">性别：</em>
      <select name="adminUserGender" id="adminUserGender">
      	<%for(StatusBean<Integer, String> gender:GenderConfig.GENDER_LIST){ %>
      	  <option value="<%=gender.getStatus() %>" <%=(gender.getStatus()==bean.getAdminUserGender())?"selected":"" %>><%=gender.getValue() %></option><%} %>
      	</select>
    </p> 
    <p>
    	   <em class="int_label">地区：</em>
    	   <input type="hidden" name="country" id="country" value="中国"/>
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
    <p>
      <em class="int_label">出生日期：</em>
      <input type="text" name="adminUserBirthday" id="adminUserBirthday" size="30" maxlength="50" value="<%=bean.getAdminUserBirthday() != null ? bean.getAdminUserBirthday() : "" %>" checkStr="出生日期;txt;false;;50" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
    </p> 
    <p>
      <em class="int_label">部门职位：</em>
      <input type="text" name="adminUserPose" id="adminUserPose" size="30" maxlength="100" value="<%=bean.getAdminUserPose() %>" checkStr="用户职位;txt;false;;100"/>
    </p>
    <p>
      <em class="int_label">联系电话：</em>
      <input type="text" name="adminUserPhone" id="adminUserPhone" size="30" maxlength="50" value="<%=bean.getAdminUserPhone() %>" checkStr="联系电话;txt;false;;50"/>
    </p>
    <p>
      <em class="int_label">手机：</em>
      <input type="text" name="adminUserTelephone" id="adminUserTelephone" size="30" maxlength="50" value="<%=bean.getAdminUserTelephone() %>" checkStr="手机;txt;false;;50"/>
    </p>
    <p>
      <em class="int_label">传真：</em>
      <input type="text" name="adminUserFax" id="adminUserFax" size="30" maxlength="50" value="<%=bean.getAdminUserFax() %>" checkStr="传真;txt;false;;50"/>
    </p>
    <p>
      <em class="int_label">邮件：</em>
      <input type="text" name="adminUserEmail" id="adminUserEmail" size="30" maxlength="50" value="<%=bean.getAdminUserEmail() %>" checkStr="传真;txt;false;;50"/>
    </p>
    <p>
      <em class="int_label">备注：</em>
      <input type="text" name="memo" id="memo" size="30" maxlength="50" value="<%=bean.getMemo() %>" checkStr="备注;txt;false;;500"/>
    </p>
    <p>
      <em class="int_label">登录名：</em>
      <input type="text" name="loginId" id="loginId" size="30" maxlength="100" value="<%=bean.getLoginId() %>"/><em><span>*</span></em>
    </p>
    <%if(updateFlag){ %>
    <p>
      <em class="int_label">登录密码：</em>
      <input type="password" name="loginPassword" id="loginPassword" size="20" maxlength="20" value="" checkStr="登录密码;txt;false;;20"/><em><span>为空则不修改用户密码</span></em>
    </p>
    <%}else{ %>
    <p>
      <em class="int_label">登录密码：</em>
      <input type="password" name="loginPassword" id="loginPassword" size="20" maxlength="20" value="" checkStr="登录密码;txt;true;;20"/><em><span>*</span></em>
    </p>
    <%} %>
    <p>
      <em class="int_label">登录账号状态：</em>
      <select  name="status" id="status" >
      	<%for(StatusBean<Integer, String> statusBean : Constants.STATUS_LIST){ %><option value="<%=statusBean.getStatus() %>" <%=(statusBean.getStatus().equals(bean.getStatus()))?"selected":"" %>><%=statusBean.getValue() %></option><%} %>
       </select>
    </p>
    <p>
    	<em class="int_label">角色：</em>
    <select name="roleId" id="roleId" >
      	<%for(AdminRoleInfoBean roleBean : roleList){ %><option value="<%=roleBean.getRoleId()%>" <%=(roleBean.getRoleId() == roleUserBean.getRoleId())?"selected":"" %>><%=roleBean.getRoleName() %></option><%} %>
     </select>
     <em><span>*</span></em>
    </p>
  </div>
  <div class="div_center"><input type="submit" name="button" id="button" class="modifysubbtn" value="提交" /></div>
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
	sel.append("<option value = ''></option>");
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
	var provinceName = $("#selectProvince  option:selected").text();
	$("#province").val(provinceName);
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