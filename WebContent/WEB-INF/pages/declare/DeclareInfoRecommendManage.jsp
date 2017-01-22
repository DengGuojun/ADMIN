<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.lpmas.framework.web.ParamKit"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.framework.util.*"%>
<%@ page import="com.lpmas.framework.bean.StatusBean" %>
<%@page import="com.lpmas.constant.user.GenderConfig"%>
<%@ page import="com.lpmas.declare.config.*"  %>
<%@ page import="com.lpmas.declare.bean.*"  %>
<%@page import="com.lpmas.declare.admin.config.*" %>
<% 
    DeclareReportBean bean = (DeclareReportBean)request.getAttribute("DeclareReportBean");
	int declareId = ParamKit.getIntParameter(request, "declareId", 0);
	List<IndustryTypeBean> industryTypeList = (List<IndustryTypeBean>)request.getAttribute("IndustryTypeList");
	List<JobTypeBean> jobTypeList = (List<JobTypeBean>)request.getAttribute("JobTypeList");
	DeclareImageBean imageBean = (DeclareImageBean) request.getAttribute("DeclareImageBean");
	int declareType = ParamKit.getIntParameter(request, "declareType", 0);
%>
<%@ include file="../include/header.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>信息申报表</title>
	<link href="<%=STATIC_URL %>/css/main.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="../js/common.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>/js/jquery.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>/js/common.js"></script>
</head>
<body class="article_bg">
	<div class="article_tit">
		<a href="javascript:history.back()" ><img src="<%=STATIC_URL %>/images/back_forward.jpg"/></a> 
		<ul class="art_nav">
			<li><a href="DeclareInfoRecommendList.do">对象推荐</a>&nbsp;>&nbsp;</li>			
			<li><%=bean.getUserName() %>&nbsp;>&nbsp;</li>
			<li><%=DeclareInfoConfig.DECLARE_TYPE_MAP.get(bean.getDeclareType())%>申报表</li>			
		</ul>
	</div>
	<form id="formData" name="formData" method="post" action="DeclareInfoRecommendManage.do" onsubmit="javascript:return checkForm('formData');">
	            <input type="hidden" name="declareId" id="declareId" value="<%=bean.getDeclareId() %>">
	            <input type="hidden" name="status" id="status" value="<%=bean.getStatus()%>">
	          <p>类型变更</p>
								<select class="form-control" name="declareType" id="declareType">									
									<%for(StatusBean<Integer,String> statusBean : DeclareInfoConfig.DECLARE_TYPE_LIST){ %>
									<option value="<%=statusBean.getStatus()%>" <%=(statusBean.getStatus() == bean.getDeclareType()) ? "selected" : ""%>><%=statusBean.getValue() %></option>
									<%} %>
								</select>
         <p>基本信息</p>
		<div class="modify_form">
			<p>
				<em class="int_label">姓名：</em>
				<input size="50" id="userName" name="userName" type="text" value="<%=bean.getUserName() %>" />
			</p>
			<p>
				<em class="int_label">性别：</em>
				<select class="form-control" name="userGender" id="userGender">
								<option value="">请选择</option>
								<% int userGender = bean.getUserGender(); %>
								 <%for(StatusBean<Integer,String> statusBean : GenderConfig.GENDER_LIST){ %>
									<option value="<%=statusBean.getStatus()%>" <%=(statusBean.getStatus()==userGender) ? "selected" : ""%>><%=statusBean.getValue() %></option>
								<%} %>
		        </select>
			</p>
			<p>
				<em class="int_label">图片：</em>
				<%if(bean!=null&&!bean.getImagePath().equals("")){ %>
	                                <img id="img_preview" src="<%=imageBean.getImagePath() %>"  />
	                                <%}else{ %>
	                                <img id="img_preview" src="" class="plus-img" style="top:44px !important" />
	                                 <%} %>
									<input type="file" id="file" name="file" accept="image/*" onchange="up();" />
							<input type="hidden" id="imagePath" name="imagePath" value="<%=imageBean.getImagePath()%>"/>
				            <input type="hidden" id="imageType" name="imageType" value="<%=DeclareImageInfoConfig.IMG_TYPE_ONE_INCH%>"/>
			</p>
			<p>
				<em class="int_label">出生日期：</em>
				<input type="date" class="form-control" id="userBirthday" name="userBirthday" value="<%=bean.getUserBirthday() !=null ? DateKit.formatDate(bean.getUserBirthday(), DateKit.DEFAULT_DATE_FORMAT):"" %>"  autocomplete="off"/>
			</p>
			<p>
				<em class="int_label">民族：</em>
				<select class="form-control" name="nationality" id="nationality">
								<option value="">请选择</option>
								<% String nationality = bean.getNationality(); %>
								 <%for(StatusBean<String,String> statusBean : NationalityConfig.NATIONALITY_LIST){ %>
									<option value="<%=statusBean.getStatus()%>" <%=(statusBean.getStatus().equals(nationality)) ? "selected" : ""%>><%=statusBean.getValue() %></option>
								<%} %>
				</select>
			</p>
			<p>
				<em class="int_label">文化程度：</em>
				<select class="form-control" name="education" id="education">
								<option value="">请选择</option>
								<% String education = bean.getEducation(); %>
								 <%for(StatusBean<String,String> statusBean : FarmerInfoConfig.EDUCATION_LEVEL_LIST){ %>
									<option value="<%=statusBean.getStatus()%>" <%=(statusBean.getStatus().equals(education)) ? "selected" : ""%>><%=statusBean.getValue() %></option>
								<%} %>
								</select>
			</p>
			<p>
				<em class="int_label">身份证号：</em>
				<input size="50" id="identityNumber" name="identityNumber" type="text" value="<%=bean.getIdentityNumber() %>" />
			</p>
				<p>
				<em class="int_label">专业：</em>
				<input size="50" id="major" name="major" type="text" value="<%=bean.getMajor() %>" />
			</p>
			<p>
				<em class="int_label">政治面貌：</em>
				<select class="form-control" name="politicalStatus" id="politicalStatus">
								<option value="">请选择</option>
								<% String politicalStatus = bean.getPoliticalStatus(); %>
								 <%for(StatusBean<String,String> statusBean : FarmerInfoConfig.POLITICAL_STATUS_LIST){ %>
									<option value="<%=statusBean.getStatus()%>" <%=(statusBean.getStatus().equals(politicalStatus)) ? "selected" : ""%>><%=statusBean.getValue() %></option>
								<%} %>
								</select>
			</p>					
		</div>
		<p>联系信息</p>
		<div class="modify_form">
			<p>
				<em class="int_label">手机号码：</em>
				<input size="50" type="number" name="userMobile" id="userMobile" value="<%=bean.getUserMobile() %>" />
			</p>
			<p>
				<em class="int_label">电子邮箱：</em>
				<input size="50" name="userEmail" id="userEmail" type="text" value="<%=bean.getUserEmail()%>" />
			</p>
			<p>
				<em class="int_label">QQ号：</em>
				<input size="50" type="number" name="userQq" id="userQq" value="<%=bean.getUserQq()%>" />
			</p>
			<p>
				<em class="int_label">微信号：</em>
				<input size="50" type="text" name="userWechat" id="userWechat" value="<%=bean.getUserWechat()%>" />
			</p>
			<p>
				<em class="int_label">户籍所在地：</em>
							<input type="hidden" name="perProvince" id="perProvince" value="<%=bean.getProvince()%>"/>
							<input type="hidden" name="perCity" id="perCity" value="<%=bean.getCity()%>"/>
							<input type="hidden" name="perRegion" id="perRegion" value="<%=bean.getRegion()%>"/>
							<div class="col-md-9">
								<div class="row">
									<div class="col-md-4">
										<select class="form-control" id="personProvince" >
											<option>请选择</option>
										</select>
									</div>
									<div class="col-md-4">
										<select class="form-control" id="personCity" >
											<option>请选择</option>
										</select>
									</div>
									<div class="col-md-4">
										<select class="form-control" id="personRegion" >
											<option>请选择</option>
										</select>
									</div>
									<input type="hidden" id="queryCity1" />
									<input type="hidden" id="queryRegion1" />
								</div>
							</div>
			</p>
			<p>
				<em class="int_label">通讯地址：</em>
				<input size="50" name="address" id="address" type="text" value="<%=bean.getAddress()%>" />
			</p>		
		</div>
        <p>申请培训信息</p>
		<div class="modify_form">
		             <%
						//获取人员类别列表
						List<StatusBean<String, String>> PERSONNEL_CATEGORY_LIST = new ArrayList<StatusBean<String, String>>();
						if(declareType == 2 || bean.getDeclareType() == DeclareInfoConfig.DECLARE_TYPE_PRODUCT_FARMER){
							PERSONNEL_CATEGORY_LIST = FarmerInfoConfig.PRODUCT_FARMER_PERSONNEL_CATEGORY_LIST;
						}else if(declareType == 1 || bean.getDeclareType() == DeclareInfoConfig.DECLARE_TYPE_YOUNG_FARMER){
							PERSONNEL_CATEGORY_LIST = FarmerInfoConfig.YOUNG_FARMER_PERSONNEL_CATEGORY_LIST;
							
						}else if(declareType == 5 || bean.getDeclareType() == DeclareInfoConfig.DECLARE_TYPE_LEADER_FARMER){
							PERSONNEL_CATEGORY_LIST = FarmerInfoConfig.LEADER_FARMER_PERSONNEL_CATEGORY_LIST;
						}
						%>
		    <p>
				<em class="int_label">人员类别：</em>
				<select class="form-control" name="farmerType" id="farmerType">
									<option value="">请选择</option>									
									<% String farmerType = bean.getFarmerType(); %>
									<%for(StatusBean<String,String> statusBean : PERSONNEL_CATEGORY_LIST){ %>
									<option value="<%=statusBean.getStatus()%>" <%=(statusBean.getStatus().equals(farmerType)) ? "selected" : ""%>><%=statusBean.getValue() %></option>
									<%} %>
								</select>
			</p>

		    <p>
				<em class="int_label">申请方式:</em>
				<select class="form-control" name="applyType" id="applyType">
									<option value="">请选择</option>
									<% String applyType = bean.getApplyType(); %>
									<%for(StatusBean<String,String> statusBean : DeclareInfoConfig.APPLY_TYPE_LIST){ %>
									<option value="<%=statusBean.getStatus()%>" <%=(statusBean.getStatus().equals(applyType)) ? "selected" : ""%>><%=statusBean.getValue() %></option>
									<%} %>
								</select>
			</p>
			<p>
				<em class="int_label">是否参加过新型职业农民培训：</em>
				<select class="form-control" name="isTrained" id="isTrained" onchange="isTrainedChange()">
									<option value="">请选择</option>
									<option value="<%=Constants.STATUS_VALID %>" <%=(bean.getIsTrained()==Constants.STATUS_VALID)?"selected":"" %>>是</option>
									<option value="<%=Constants.STATUS_NOT_VALID %>" <%=(bean.getIsTrained()==Constants.STATUS_NOT_VALID && bean.getDeclareId()>0)?"selected":"" %>>否</option>
								</select>
			</p>
			<p>
				<em class="int_label">参加其它农业培训：( 次／年)</em>
				<input type="number" class="form-control" name="otherTrainingTime" id="otherTrainingTime" value="<%=bean.getDeclareId() >0 ? bean.getOtherTrainingTime() : ""%>"/>
			</p>

			<p>
			<em class="int_label">是否获得新型职业农民资格证</em>
			<input type="checkbox" name="hasNewTypeCertification" id="hasNewTypeCertification" <%=Constants.STATUS_VALID == bean.getHasNewTypeCertification()? "checked" : ""%> value="<%=Constants.STATUS_VALID%>"/>
			</p>
			<p>
				<em class="int_label">认定等级:</em>
				<select class="form-control" name="certificationGrade" id="certificationGrade">
										<option value="">请选择</option>
										<% String certificationGrade = bean.getCertificationGrade(); %>
										<%for(StatusBean<String,String> statusBean : FarmerInfoConfig.CERTIFICATION_LEVEL_LIST){ %>
										<option value="<%=statusBean.getStatus()%>" <%=(statusBean.getStatus().equals(certificationGrade)) ? "selected" : ""%>><%=statusBean.getValue() %></option>
										<%} %>
				</select>
			</p>
			<p>
				<em class="int_label">认定时间:</em>
				<input type="date" class="form-control" id="certificationDate" name="certificationDate" value="<%=bean.getCertificationDate() !=null ? DateKit.formatDate(bean.getCertificationDate(), DateKit.DEFAULT_DATE_FORMAT):"" %>"  autocomplete="off"/>
			</p>
			<p>
				<em class="int_label">认定部门:</em>
				<input type="text" class="form-control" id="certificationDepartment" name="certificationDepartment" value="<%=bean.getCertificationDepartment() %>"/>
			</p>

			<p>
				<em class="int_label">农民技术等级或职称证书 ：</em>
				<select class="form-control" name="certificationTitle" id="certificationTitle">
									<option value="">请选择</option>
									<% String certificationTitle = bean.getCertificationTitle(); %>
									<%for(StatusBean<String,String> statusBean : FarmerInfoConfig.FARMER_TITLE_LIST){ %>
									<option value="<%=statusBean.getStatus()%>" <%=(statusBean.getStatus().equals(certificationTitle)) ? "selected" : ""%>><%=statusBean.getValue() %></option>
									<%} %>
								</select>
			</p>

			<p>
			<em class="int_label">未获得证书：</em>
			<input type="checkbox" name="hasNoCertification" id="hasNoCertification" <%=Constants.STATUS_VALID == bean.getHasNoCertification() ? "checked" : ""%> value="<%=Constants.STATUS_VALID%>" />
			</p>			
			<p>
			<em class="int_label">新型职业农民培训证书</em>
			<input type="checkbox" name="hasNewTypeTrainingCertification" id="hasNewTypeTrainingCertification" <%=Constants.STATUS_VALID == bean.getHasNewTypeTrainingCertification() ? "checked" : ""%> value="<%=Constants.STATUS_VALID%>"/>
			</p>			
			<p>
			<em class="int_label">国家职业资格证书</em>
			<input type="checkbox" name="hasNationalCertification" id="hasNationalCertification" <%=Constants.STATUS_VALID == bean.getHasNationalCertification() ? "checked" : ""%> value="<%=Constants.STATUS_VALID%>"/>
			</p>
					
		</div>

		<p>生产经营状况</p>
		<div class="modify_form">
		

		    <p>
				<em class="int_label">工作地点 ：</em>
							<input type="hidden" name="selProvince" id="selProvince" value="<%=bean.getJobProvince()%>"/>
							<input type="hidden" name="selCity" id="selCity" value="<%=bean.getJobCity()%>"/>
							<input type="hidden" name="selRegion" id="selRegion" value="<%=bean.getJobRegion()%>"/>
							<div class="col-md-9">
								<div class="row">
									<div class="col-md-4">
										<select class="form-control" id="selectProvince">
											<option>请选择</option>
										</select>
									</div>
									<div class="col-md-4">
										<select class="form-control" id="selectCity">
											<option>请选择</option>
										</select>
									</div>
									<div class="col-md-4">
										<select class="form-control" id="selectRegion">
											<option>请选择</option>
										</select>
									</div>
									<input type="hidden" id="queryCity" />
									<input type="hidden" id="queryRegion" />
								</div>
							</div>
			</p>
			<p>
				<em class="int_label">从业工种:</em>
				<select class="jobType form-control" id="jobType" name="jobType" >
								<option>请选择</option>
								<%for(JobTypeBean jobTypeBean: jobTypeList){ %>
									<option value="<%=jobTypeBean.getTypeId()%>" <%=jobTypeBean.getTypeId() == bean.getJobType() ? "selected" : ""%>><%=jobTypeBean.getTypeName() %></option>
								<%} %>
								</select>
			</p>
			<p>
				<em class="int_label">从事岗位:</em>
				 <input type="hidden" class="originalJobId" id= "originalJobId" value="<%=bean.getJobName() %>">
                               <select class="job form-control" id="job" name="job" ></select>
			</p>
			<p>
				<em class="int_label">服务规模:</em>
				<input type="text" class="form-control" id="serviceScale" name="serviceScale" value="<%=bean.getServiceScale() %>"/>
			</p>
			<p>
				<em class="int_label">从业年限:</em>
				<input type="number" name="experience" id="experience" value="<%=bean.getExperience() >0 ? bean.getExperience() : ""%>" class="form-control">
			</p>
			<p>
				<em class="int_label">个人从事该工种/岗位年收入（万）:</em>
				<input type="number" class="form-control" name="income" id="income" value="<%=bean.getIncome() > 0 ? bean.getIncome() : ""%>" />
			</p>


			<p>是否登记注册？：</p>
			<select class="form-control" name="hasRegisted" id="hasRegisted">
									<option value="">请选择</option>
									<option value="<%=Constants.STATUS_VALID %>" <%=(bean.getHasRegisted()==Constants.STATUS_VALID)?"selected":"" %>>是</option>
									<option value="<%=Constants.STATUS_NOT_VALID %>" <%=(bean.getHasRegisted()==Constants.STATUS_NOT_VALID && bean.getDeclareId()>0)?"selected":"" %>>否</option>
								</select>

			<p>
				<em class="int_label">是否是示范性家庭农场？：</em>
				<select class="form-control" name="isExampleFamilyFarm" id="isExampleFamilyFarm" onclick="isHidden()">
									<option value="">请选择</option>
									<option value="<%=Constants.STATUS_VALID %>" <%=(bean.getIsExampleFamilyFarm()==Constants.STATUS_VALID)?"selected":"" %>>是</option>
									<option value="<%=Constants.STATUS_NOT_VALID %>" <%=(bean.getIsExampleFamilyFarm()==Constants.STATUS_NOT_VALID && bean.getDeclareId()>0)?"selected":"" %>>否</option>
								</select>
			</p>
			<p>
				<em class="int_label">示范性农场级别</em>
                        <select class="form-control" name="exampleFarmLevel" id="exampleFarmLevel">
									<option value="">请选择</option>
									<% String exampleFarmLevel = bean.getExampleFarmLevel(); %>
									<%for(StatusBean<String,String> statusBean : FarmerIndustryInfoConfig.FARM_LEVEL_LIST){ %>
									<option value="<%=statusBean.getStatus()%>" <%=(statusBean.getStatus().equals(exampleFarmLevel)) ? "selected" : ""%>><%=statusBean.getValue() %></option>
									<%} %>
								</select>
		    </p>
			<p>
				<em class="int_label">家庭农场类型:</em>
				<select class="form-control" name="familyFarmType" id="familyFarmType">
									<option value="">请选择</option>
									<% String familyFarmType = bean.getFamilyFarmType(); %>
									<%for(StatusBean<String,String> statusBean : FarmerIndustryInfoConfig.FAMILY_FARM_TYPE_LIST){ %>
									<option value="<%=statusBean.getStatus()%>" <%=(statusBean.getStatus().equals(familyFarmType)) ? "selected" : ""%>><%=statusBean.getValue() %></option>
									<%} %>									
			   </select>
			</p>
			<p>
				<em class="int_label">土地经营规模(亩) ：</em>
				<input class="form-control" name="farmLandScale" id="farmLandScale"  value="<%=bean.getFarmLandScale() >0 ? bean.getFarmLandScale() : "" %>"  type="number"/>
			</p>
			<p>
				<em class="int_label">家庭人口：</em>
				<input class="form-control" name="familyPerson" id="familyPerson"  value="<%=bean.getFamilyWorkingPerson() > 0 ? bean.getFamilyPerson() : "" %>"  type="number"/>
			</p>
			<p>
				<em class="int_label">家庭从事产业人数:</em>
				<input class="form-control" name="familyWorkingPerson" id="familyWorkingPerson"  value="<%=bean.getFamilyWorkingPerson() >0 ? bean.getFamilyWorkingPerson() : "" %>"  type="number"/>
			</p>
			<p>
				<em class="int_label">上年度家庭收入（万元）:</em>
				<input type="number" class="form-control" name="lastYearFamilyIncome" id="lastYearFamilyIncome" value="<%=bean.getLastYearFamilyIncome() > 0 ? bean.getLastYearFamilyIncome() : ""%>" />
			</p>
			 <p>
				<em class="int_label">产业所在地 ：</em>
							<input type="hidden" name="selProvince" id="selProvince" value="<%=bean.getIndustryProvince()%>"/>
							<input type="hidden" name="selCity" id="selCity" value="<%=bean.getIndustryCity()%>"/>
							<input type="hidden" name="selRegion" id="selRegion" value="<%=bean.getIndustryRegion()%>"/>
							<div class="col-md-9">
								<div class="row">
									<div class="col-md-4">
										<select class="form-control" id="selectProvince">
											<option>请选择</option>
										</select>
									</div>
									<div class="col-md-4">
										<select class="form-control" id="selectCity">
											<option>请选择</option>
										</select>
									</div>
									<div class="col-md-4">
										<select class="form-control" id="selectRegion">
											<option>请选择</option>
										</select>
									</div>
									<input type="hidden" id="queryCity" />
									<input type="hidden" id="queryRegion" />
								</div>
							</div>
			</p>
			
			<p>主体产业</p>
			<p>
				<em class="int_label">主体产业1:</em>
				<select class="industryType form-control" id="industryTypeId1" name="industryTypeId1" >
								<option>请选择</option>
								<%for(IndustryTypeBean industryTypeBean: industryTypeList){ %>
									<option value="<%=industryTypeBean.getTypeId()%>" <%=industryTypeBean.getTypeId() == bean.getIndustryTypeId1() ? "selected" : ""%>><%=industryTypeBean.getTypeName() %></option>
								<%} %>
								</select>
			</p>
			<p>
				<em class="int_label">产业名称1:</em>
				<input type="hidden" class="originalIndustryId" value="<%=bean.getIndustryId1() %>">
                <select class="industry form-control" id="industryId1" name="industryId1" ></select>
			</p>
			<p>
				<em class="int_label">产业规模:</em>
				<input type="number" class="form-control" name="industryScale1" id="industryScale1" value="<%=bean.getIndustryScale1() > 0 ? bean.getIndustryScale1() : "" %>" />
			</p>
			<p>
				<em class="int_label">单位:</em>
			<select class="form-control" id="industryUnit1" name="industryTypeUnit1" >
				                    <option value="亩" <%=bean.getIndustryUnit1().equals("亩") ? "selected" : ""%>>亩</option>
				                    <option value="只/羽/头" <%=bean.getIndustryUnit1().equals("只/羽/头") ? "selected" : ""%>>只/羽/头</option>
				                    <option value="公顷" <%=bean.getIndustryUnit1().equals("公顷") ? "selected" : ""%>>公顷</option>
                                  </select>
             </p>
			<p>
				<em class="int_label">从事年限:</em>
				<input class="form-control" type="number" name="experience1" id="experience1" value="<%=bean.getExperience1() > 0 ? bean.getExperience1() : "" %>">
			</p>

			<p>
				<em class="int_label">主体产业2:</em>
				<select class="form-control industryType" id="industryTypeId2" name="industryTypeId2" >
								<option>请选择</option>
								<%for(IndustryTypeBean industryTypeBean: industryTypeList){ %>
									<option value="<%=industryTypeBean.getTypeId()%>" <%=industryTypeBean.getTypeId() == bean.getIndustryTypeId2() ? "selected" : ""%>><%=industryTypeBean.getTypeName() %></option>
								<%} %>
								</select>
			</p>
			<p>
				<em class="int_label">产业名称2:</em>
				<input type="hidden" class="originalIndustryId" value="<%=bean.getIndustryId2() %>">
                               <select class="form-control industry" id="industryId2" name="industryId2" ></select>
			</p>
			<p>
				<em class="int_label">产业规模:</em>
				<input class="form-control"  type="number" name="industryScale2" id="industryScale2" value="<%=bean.getIndustryScale2() > 0 ? bean.getIndustryScale2() : ""%>" />
			</p>
			<p>
				<em class="int_label">单位:</em>
				 <select class="form-control" id="industryUnit2" name="industryTypeUnit2" >
				            <option value="亩" <%=bean.getIndustryUnit2().equals("亩") ? "selected" : ""%>>亩</option>
				            <option value="只/羽/头" <%=bean.getIndustryUnit2().equals("只/羽/头") ? "selected" : ""%>>只/羽/头</option>
				            <option value="公顷" <%=bean.getIndustryUnit2().equals("公顷") ? "selected" : ""%>>公顷</option>
                            </select>
			</p>
			<p>
				<em class="int_label">从事年限:</em>
				<input class="form-control" type="number" name="experience2" id="experience2" value="<%=bean.getExperience2() >0 ? bean.getExperience2() : ""%>">
			</p>

			<p>
				<em class="int_label">主体产业3:</em>
				<select class="form-control industryType" id="industryTypeId3" name="industryTypeId3" >
								<option>请选择</option>
								<%for(IndustryTypeBean industryTypeBean: industryTypeList){ %>
									<option value="<%=industryTypeBean.getTypeId()%>" <%=industryTypeBean.getTypeId() == bean.getIndustryTypeId3() ? "selected" : ""%>><%=industryTypeBean.getTypeName() %></option>
								<%} %>
								</select>
			</p>
			<p>
				<em class="int_label">产业名称3:</em>
				<input type="hidden" class="originalIndustryId" value="<%=bean.getIndustryId3() %>">
                               <select class="form-control industry" id="industryId3" name="industryId3" ></select>
			</p>
			<p>
				<em class="int_label">产业规模:</em>
				<input class="form-control"  type="number" name="industryScale3" id="industryScale3" value="<%=bean.getIndustryScale3() > 0 ? bean.getIndustryScale3() : ""%>" />
			</p>
			<p>
				<em class="int_label">单位:</em>
				<select class="form-control" id="industryUnit3" name="industryTypeUnit3" >
				            <option value="亩" <%=bean.getIndustryUnit3().equals("亩") ? "selected" : ""%>>亩</option>
				            <option value="只/羽/头" <%=bean.getIndustryUnit3().equals("只/羽/头") ? "selected" : ""%>>只/羽/头</option>
				            <option value="公顷" <%=bean.getIndustryUnit3().equals("公顷") ? "selected" : ""%>>公顷</option>
                            </select>
			</p>
			<p>
				<em class="int_label">从事年限:</em>
				<input class="form-control" type="number" name="experience3" id="experience3" value="<%=bean.getExperience3() >0 ? bean.getExperience3() : ""%>">
			</p>

			<p>
				<em class="int_label">上年度产业收入（万元）:</em>
				<input type="number" class="form-control" name="lastYearIncome" id="lastYearIncome" value="<%=bean.getLastYearIncome() > 0 ? bean.getLastYearIncome() : ""%>" />
			</p>

			
			<p>
				<em class="int_label">地区类型:</em>
				<select class="form-control" name="areaType" id="areaType">
								<option>请选择</option>
								<%for(StatusBean<Integer,String> statusBean : FarmerIndustryInfoConfig.AREA_TYPE_LIST){ %>
									<option value="<%=statusBean.getStatus()%>" <%=(statusBean.getStatus()== bean.getAreaType()) ? "selected" : ""%>><%=statusBean.getValue() %></option>
								<%} %>
								</select>
			</p>
			<p>
				<em class="int_label">经济区域类型:</em>
				<select class="form-control" name="economicAreaType" id="economicAreaType">
								<option>请选择</option>
								<%for(StatusBean<Integer,String> statusBean : FarmerIndustryInfoConfig.ECONOMIC_AREA_TYPE_LIST){ %>
									<option value="<%=statusBean.getStatus()%>" <%=(statusBean.getStatus()== bean.getEconomicAreaType()) ? "selected" : ""%>><%=statusBean.getValue() %></option>
								<%} %>
								</select>
			</p>
	
		</div>

		<div class="div_center">
		     <input type="submit" name="submit" id="submit" class="js-save" value="保存" />
		     <input type="button" name="button" id="button" value="提交" class="js-submit">		    
		</div>
		</form>
</body>
<script>
$(document).ready(function() {

	$("#farmerType").trigger('change');
	$("#industryTypeId1").on("change",function(){
         var typeId = $(this).find("option:selected").val();
         seletCate1(typeId,$(this));
     });
	$("#industryTypeId1").trigger('change');
     function seletCate1(typeId,element){
        	$.ajax({
    	        type: 'get',
    	        url: "/declare/admin/IndustryInfoJsonList.do?typeId="+typeId,
    	        dataType: 'json',
    	        success: function(data){
    	        		var industry=data.result;
    	        		child ="";
                 for(var j=0;j< industry.length;j++){
                	 	var originalId = element.parent().parent().find(".originalIndustryId").val();
                	 	if(industry[j].industryId == originalId){
                	 		child += "<option value='" + industry[j].industryId  +"' selected>"+ industry[j].industryName + "</option>"
                	 	}else{
                	 		child += "<option value='" + industry[j].industryId  +"'>"+ industry[j].industryName + "</option>"
                	 	}
                 }
                 $("#industryId1").html(child);
    	        },
    	        error: function(){
    	            return;
    	        }
    	    });
     }
     $("#industryTypeId2").on("change",function(){
         var typeId = $(this).find("option:selected").val();
         seletCate2(typeId,$(this));
     });
	$("#industryTypeId2").trigger('change');
     function seletCate2(typeId,element){
        	$.ajax({
    	        type: 'get',
    	        url: "/declare/admin/IndustryInfoJsonList.do?typeId="+typeId,
    	        dataType: 'json',
    	        success: function(data){
    	        		var industry=data.result;
    	        		child ="";
                 for(var j=0;j< industry.length;j++){
                	 	var originalId = element.parent().parent().find(".originalIndustryId").val();
                	 	if(industry[j].industryId == originalId){
                	 		child += "<option value='" + industry[j].industryId  +"' selected>"+ industry[j].industryName + "</option>"
                	 	}else{
                	 		child += "<option value='" + industry[j].industryId  +"'>"+ industry[j].industryName + "</option>"
                	 	}
                 }
                 $("#industryId2").html(child);
    	        },
    	        error: function(){
    	            return;
    	        }
    	    });
     }
     $("#industryTypeId3").on("change",function(){
         var typeId = $(this).find("option:selected").val();
         seletCate3(typeId,$(this));
     });
	$("#industryTypeId3").trigger('change');
     function seletCate3(typeId,element){
        	$.ajax({
    	        type: 'get',
    	        url: "/declare/admin/IndustryInfoJsonList.do?typeId="+typeId,
    	        dataType: 'json',
    	        success: function(data){
    	        		var industry=data.result;
    	        		child ="";
                 for(var j=0;j< industry.length;j++){
                	 	var originalId = element.parent().parent().find(".originalIndustryId").val();
                	 	if(industry[j].industryId == originalId){
                	 		child += "<option value='" + industry[j].industryId  +"' selected>"+ industry[j].industryName + "</option>"
                	 	}else{
                	 		child += "<option value='" + industry[j].industryId  +"'>"+ industry[j].industryName + "</option>"
                	 	}
                 }
                 $("#industryId3").html(child);
    	        },
    	        error: function(){
    	            return;
    	        }
    	    });
     }
     $("#jobType").on("change",function(){
	      var typeId = $(this).find("option:selected").val();
	      seletCate(typeId,$(this));
	  });
	  $("#jobType").trigger('change');

	  function seletCate(typeId,element){
		  $.ajax({
 	        type: 'get',
 	        url: "/declare/admin/JobInfoJsonList.do?typeId="+typeId,
 	        dataType: 'json',
 	        success: function(data){
 	        		var jobs=data.result;
 	        		child ="";
               for(var j=0;j< jobs.length;j++){
             	 	var originalId = element.parent().parent().find(".originalJobId").val();
             	 	if(jobs[j].jobId == originalId){
             	 		child += "<option value='" + jobs[j].jobId  +"' selected>"+ jobs[j].jobName + "</option>"
             	 	}else{
             	 		child += "<option value='" + jobs[j].jobId  +"'>"+ jobs[j].jobName + "</option>"
             	 	}
              }
              $("#job").html(child);
 	        },
 	        error: function(){
 	            return;
 	        }
		});
	  }		     
     
 });
$(function(){    
    $('.js-submit').click(function(){
		/* if($('.js-save.selected').length != $('.js-save').length){
			alert("请先保存");
			return;
		} */
		location.href='DeclareInfoRecommendAcceptCommit.do?declareId=<%=bean.getDeclareId() %>&action=<%=DeclareInfoRecommendConfig.COMMIT_ACTION_SUBMIT%>';
	});
	
	$('.js-save').click(function(){
		if($(this).hasClass('selected')){
		}else{
			$(this).addClass('selected');
		}
	});
	
})
function up() {
	$('#loading').show();
    var file_data = $('#file')[0].files; // for multiple files
    //验证文件大小，文件类型
    var file = file_data[0];
    var size = file.size;
    var maxSize = <%=DeclareImageAdminConfig.MAX_SIZE%>;
    if(size>maxSize){
    	alert("文件大小超过限制");
    	return;
    }
    var name = (file.name).toLowerCase();
    if(!(name.lastIndexOf('jpg')>0||name.lastIndexOf('jpeg')>0||name.lastIndexOf('png')>0)){
    	alert("图片类型错误，请上传JPG或者PNG图片");
    	return;
    }
    //文件压缩
    var canvas = document.createElement('canvas');
    var ctx = canvas.getContext('2d');
    var scale = 0;
    var path = window.URL.createObjectURL(file);
    var imgData = "";
    var imgType = file.type;
    //判定压缩比
    if(size<=1024*1024){
    	scale = 1;
    }else{
    	scale = 0.5;
    }
    
    var image = new Image();
    image.onload = function() {
        if (image.width != canvas.width) {
            canvas.width = image.width;
        }
        if (image.height != canvas.height) {
            canvas.height = image.height;
        }
        ctx.clearRect(image,0, 0, canvas.width, canvas.height);
        ctx.drawImage(image, 0, 0);
        imgData = canvas.toDataURL(imgType,scale);
        window.URL.revokeObjectURL(path);
        
      //提交
        for (var i = 0; i < file_data.length; i++) {
            $.ajax({
                url: '/declare/admin/DeclareImageManage.do?declareType=<%=ParamKit.getIntParameter(request, "declareType", 0) %>',
                data: {
                	imgData:imgData,
                	imgType:imgType
                },
                type: 'POST',
                success: function (data) {
                    console.log(data);
                    if(data.code==200){
                        var url=data.message;
                        var img_main = $('#img_main');
                        var img_preview = $('#img_preview');
                        $('#imagePath').val(url);
                        
                        img_preview.attr("src",url);
                        img_preview.attr("width","86");
                        img_preview.attr("height","129");
                        
                        img_main.attr("src",url);
                        img_main.removeClass("plus-img");
                        img_main.addClass("upload-img");
                        img_main.removeAttr('style');
                        img_main.attr('style',"top:0px");
                    }else{
                    	alert(data.message);
                    	$('#loading').hide();
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    console.log(errorThrown);
                    alert(errorThrown);
                    $('#loading').hide();
                }
            });
        }
    }
    image.src=path;
    //文件压缩结束
    
    $('#img_preview')[0].onload = function() {
    	$('#loading').hide();
    }
}


</script>
</html>