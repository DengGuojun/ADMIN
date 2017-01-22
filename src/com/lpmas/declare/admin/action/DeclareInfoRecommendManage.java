package com.lpmas.declare.admin.action;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.declare.admin.business.DeclareImageBusiness;
import com.lpmas.declare.admin.business.DeclareInfoBusiness;
import com.lpmas.declare.admin.business.DeclareReportBusiness;
import com.lpmas.declare.admin.business.FarmerContactInfoBusiness;
import com.lpmas.declare.admin.business.FarmerIndustryInfoBusiness;
import com.lpmas.declare.admin.business.FarmerInfoBusiness;
import com.lpmas.declare.admin.business.FarmerJobInfoBusiness;
import com.lpmas.declare.admin.business.FarmerSkillInfoBusiness;
import com.lpmas.declare.admin.business.IndustryTypeBusiness;
import com.lpmas.declare.admin.business.JobTypeBusiness;
import com.lpmas.declare.admin.business.NationalCertificationBusiness;
import com.lpmas.declare.admin.config.DeclareAdminConfig;
import com.lpmas.declare.admin.handler.DeclareReportHandler;
import com.lpmas.declare.bean.DeclareImageBean;
import com.lpmas.declare.bean.DeclareInfoBean;
import com.lpmas.declare.bean.DeclareReportBean;
import com.lpmas.declare.bean.FarmerContactInfoBean;
import com.lpmas.declare.bean.FarmerIndustryInfoBean;
import com.lpmas.declare.bean.FarmerInfoBean;
import com.lpmas.declare.bean.FarmerJobInfoBean;
import com.lpmas.declare.bean.FarmerSkillInfoBean;
import com.lpmas.declare.bean.IndustryTypeBean;
import com.lpmas.declare.bean.JobTypeBean;
import com.lpmas.declare.bean.NationalCertificationBean;
import com.lpmas.declare.config.DeclareImageInfoConfig;
import com.lpmas.declare.config.DeclareInfoConfig;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.util.ListKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;

@WebServlet("/declare/admin/DeclareInfoRecommendManage.do")
public class DeclareInfoRecommendManage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(DeclareInfoRecommendManage.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeclareInfoRecommendManage() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int declareId = ParamKit.getIntParameter(request, "declareId", 0);
		if (declareId == 0) {
			HttpResponseKit.alertMessage(response, "申报ID缺失!", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		// 查出对应的BEAN
		DeclareReportBusiness business = new DeclareReportBusiness();
		DeclareReportBean declareReportBean;
		try {
			declareReportBean = business.getDeclareReportByKey(String.valueOf(declareId));
			// 放到页面
			request.setAttribute("DeclareReportBean", declareReportBean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 图片
		DeclareImageBusiness declareImageBusiness = new DeclareImageBusiness();
		DeclareImageBean imageBean = declareImageBusiness.getDeclareImageByKey(declareId,
				DeclareImageInfoConfig.IMG_TYPE_ONE_INCH);
		if (imageBean == null) {
			imageBean = new DeclareImageBean();
		}
		request.setAttribute("DeclareImageBean", imageBean);
		// 获取产业类型列表
		IndustryTypeBusiness industryTypeBusiness = new IndustryTypeBusiness();
		List<IndustryTypeBean> industryTypeList = industryTypeBusiness.getIndustryTypeAllList();
		request.setAttribute("IndustryTypeList", industryTypeList);

		// 获取从业工种
		JobTypeBusiness jobTypeBusiness = new JobTypeBusiness();
		List<JobTypeBean> jobTypeList = jobTypeBusiness.getJobTypeAllList();
		request.setAttribute("JobTypeList", jobTypeList);

		// 获取国家资格证书数据
		NationalCertificationBusiness nationalCertificationBusiness = new NationalCertificationBusiness();
		List<NationalCertificationBean> ncList = nationalCertificationBusiness.getNationalCertificationAllList();
		request.setAttribute("NationalCertificationList", ncList);
		Map<Integer, String> nationalCertificationMap = ListKit.list2Map(ncList, "certificateId", "certificateName");
		request.setAttribute("NationalCertificationMap", nationalCertificationMap);

		// 请求转发
		RequestDispatcher rd = request
				.getRequestDispatcher(DeclareAdminConfig.PAGE_PATH + "DeclareInfoRecommendManage.jsp");
		rd.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int resultDeclareInfo = 0;
		int resultFarmerInfo = 0;
		int resultFarmerContactInfo = 0;
		int resultFarmerJobInfo = 0;
		int resultFarmerSkillInfo = 0;
		int resultFarmerIndustryInfo = 0;
		int resultMongo = 0;
		DeclareInfoBusiness declareInfoBusiness = new DeclareInfoBusiness();
		FarmerInfoBusiness farmerInfoBusiness = new FarmerInfoBusiness();
		FarmerContactInfoBusiness farmerContactInfoBusiness = new FarmerContactInfoBusiness();
		FarmerJobInfoBusiness farmerJobInfoBusiness = new FarmerJobInfoBusiness();
		FarmerSkillInfoBusiness farmerSkillInfoBusiness = new FarmerSkillInfoBusiness();
		FarmerIndustryInfoBusiness farmerIndustryInfoBusiness = new FarmerIndustryInfoBusiness();
		// 映射Bean
		DeclareInfoBean declareInfoBean = BeanKit.request2Bean(request, DeclareInfoBean.class);
		FarmerInfoBean farmerInfoBean = BeanKit.request2Bean(request, FarmerInfoBean.class);
		FarmerContactInfoBean farmerContactInfoBean = BeanKit.request2Bean(request, FarmerContactInfoBean.class);
		FarmerJobInfoBean farmerJobInfoBean = BeanKit.request2Bean(request, FarmerJobInfoBean.class);
		FarmerSkillInfoBean farmerSkillInfoBean = BeanKit.request2Bean(request, FarmerSkillInfoBean.class);
		FarmerIndustryInfoBean farmerIndustryInfoBean = BeanKit.request2Bean(request, FarmerIndustryInfoBean.class);
		if (declareInfoBean.getDeclareId() > 0) {
			// 检验是否已经是提交状态
			if (DeclareInfoConfig.DECLARE_STATUS_SUBMIT.equals(declareInfoBean.getDeclareStatus())
					|| DeclareInfoConfig.DECLARE_STATUS_CITY_APPROVE.equals(declareInfoBean.getDeclareStatus())
					|| DeclareInfoConfig.DECLARE_STATUS_APPROVE.equals(declareInfoBean.getDeclareStatus())) {
				HttpResponseKit.alertMessage(response, "已经是提交状态，不能修改!", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			// declareInfoBean.setModifyUser();
			declareInfoBean.setDeclareStatus(DeclareInfoConfig.DECLARE_STATUS_NOT_SUBMIT);
			declareInfoBean.setDeclareCategory(DeclareInfoConfig.DECLARE_CATEGORY_SPARE);
			declareInfoBean.setUserId(Constants.STATUS_NOT_VALID);
			resultDeclareInfo = declareInfoBusiness.updateDeclareInfo(declareInfoBean);
			resultFarmerInfo = farmerInfoBusiness.updateFarmerInfo(farmerInfoBean);
			resultFarmerContactInfo = farmerContactInfoBusiness.updateFarmerContactInfo(farmerContactInfoBean);
			resultFarmerJobInfo = farmerJobInfoBusiness.updateFarmerJobInfo(farmerJobInfoBean);
			resultFarmerSkillInfo = farmerSkillInfoBusiness.updateFarmerSkillInfo(farmerSkillInfoBean);
			resultFarmerIndustryInfo = farmerIndustryInfoBusiness.updateFarmerIndustryInfo(farmerIndustryInfoBean);
			// 插入mongo
			DeclareReportHandler handler = new DeclareReportHandler();
			try {
				handler.createDeclareReport(declareInfoBean);
				resultMongo = Constants.STATUS_VALID;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("总表记录创建失败", e);
			}
			if (resultMongo <= 0 || resultFarmerInfo < 0 || resultFarmerContactInfo < 0 || resultDeclareInfo < 0
					|| resultFarmerJobInfo < 0 || resultFarmerSkillInfo < 0 || resultFarmerIndustryInfo < 0) {
				HttpResponseKit.alertMessage(response, "操作失败", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			} else {
				HttpResponseKit.alertMessage(response, "处理成功", HttpResponseKit.ACTION_HISTORY_BACK);
			}
		} else {
			HttpResponseKit.alertMessage(response, "申报ID不合法", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

	}

}
