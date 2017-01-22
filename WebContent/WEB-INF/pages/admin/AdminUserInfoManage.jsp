<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.bean.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.admin.bean.*"  %>
<%@ page import="com.lpmas.admin.config.*"  %>
<%@ page import="com.lpmas.framework.util.JsonKit"  %>
<% 
AdminUserInfoBean bean = (AdminUserInfoBean)request.getAttribute("UserInfo");
int userId = bean.getUserId();

boolean updateFlag = bean.getUserId() > 0;

List<AdminGroupInfoBean> groupList = (List<AdminGroupInfoBean>)request.getAttribute("GroupList");
List<AdminRoleInfoBean> roleList = (List<AdminRoleInfoBean>)request.getAttribute("RoleList");
HashSet<Integer> roleSet = (HashSet<Integer>)request.getAttribute("RoleSet");
HashSet<Integer> groupSet = (HashSet<Integer>)request.getAttribute("GroupSet");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理</title>
<%@ include file="../include/header.jsp" %>
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
  <%if(userId > 0){%>
    <%@ include file="AdminUserInfoNav.jsp"%>
  <%}%>
<div class="article_tit">
用户信息管理
</div>
<form id="formData" name="formData" method="post" action="AdminUserInfoManage.do" onsubmit="javascript:return checkThisForm('formData');">
  <input type="hidden" name="userId" id="userId" value="<%=bean.getUserId() %>"/>
  <div class="modify_form">
    <p>
      <em class="int_label">登录ID：</em>
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
      <em class="int_label">用户名：</em>
      <input type="text" name="adminUserName" id="adminUserName" size="30" maxlength="100" value="<%=bean.getAdminUserName() %>" checkStr="用户名;txt;true;;100"/><em><span>*</span></em>
    </p> 
    <p>
      <em class="int_label">用户类型：</em>      
      	<select name="adminUserType" id="adminUserType">
      	<%for(StatusBean<Integer, String> userType:AdminConfig.ADMIN_TYPE_LIST){ %>
      	  <option value="<%=userType.getStatus() %>" <%=(userType.getStatus()==bean.getAdminUserType())?"selected":"" %>><%=userType.getValue() %></option><%} %>
      	</select>
    </p> 
    <p>
      <em class="int_label">用户职位：</em>
      <input type="text" name="adminUserPose" id="adminUserPose" size="30" maxlength="100" value="<%=bean.getAdminUserPose() %>" checkStr="用户职位;txt;false;;100"/>
    </p>
    <p>
      <em class="int_label">用户部门：</em>
      <input type="text" name="adminUserDepartment" id="adminUserDepartment" size="30" maxlength="100" value="<%=bean.getAdminUserDepartment() %>" checkStr="用户部门;txt;false;;100"/>
    </p>
    <p>
      <em class="int_label">用户公司：</em>
      <input type="text" name="adminUserCompany" id="adminUserCompany" size="30" maxlength="100" value="<%=bean.getAdminUserCompany() %>" checkStr="用户公司;txt;false;;100"/>
    </p>
    <p>
      <em class="int_label">用户电话：</em>
      <input type="text" name="adminUserTelephone" id="adminUserTelephone" size="20" maxlength="20" value="<%=bean.getAdminUserTelephone() %>" checkStr="用户电话;txt;false;;20"/>
    </p>
    <p>
      <em class="int_label">用户手机：</em>
      <input type="text" name="adminUserMobile" id="adminUserMobile" size="20" maxlength="20" value="<%=bean.getAdminUserMobile() %>" checkStr="用户手机;digit;false;;20"/>
    </p>
    <p>
      <em class="int_label">邮箱：</em>
      <input type="text" name="adminUserEmail" id="adminUserEmail" size="30" maxlength="50" value="<%=bean.getAdminUserEmail() %>" class="int_txt" checkStr="邮箱;mail;false;;50"/>
    </p>    
    <p>
      <em class="int_label">状态：</em>
      <input type="checkbox" name="status" id="status" value="<%=Constants.STATUS_VALID %>" <%=(bean.getStatus()==Constants.STATUS_VALID)?"checked":"" %>/>
    </p>
    <p class="p_top_border">
      <em class="int_label">备注：</em>
      <textarea type="text" name="memo" id="memo" cols="60" rows="3" checkStr="备注;txt;false;;1000"><%=bean.getMemo() %></textarea>
    </p>
  </div>
  <div class="div_center"><input type="submit" name="button" id="button" class="modifysubbtn" value="提交" /></div>
</form>
</body>
</html>