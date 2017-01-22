package com.lpmas.declare.admin.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.declare.admin.business.DeclareInfoBusiness;
import com.lpmas.declare.admin.business.FarmerContactInfoBusiness;
import com.lpmas.declare.admin.business.FarmerIndustryInfoBusiness;
import com.lpmas.declare.admin.business.FarmerInfoBusiness;
import com.lpmas.declare.admin.business.FarmerJobInfoBusiness;
import com.lpmas.declare.admin.business.FarmerSkillInfoBusiness;
import com.lpmas.declare.admin.config.DeclareAdminConfig;
import com.lpmas.declare.admin.handler.DeclareReportHandler;
import com.lpmas.declare.bean.DeclareInfoBean;
import com.lpmas.declare.bean.FarmerContactInfoBean;
import com.lpmas.declare.bean.FarmerIndustryInfoBean;
import com.lpmas.declare.bean.FarmerInfoBean;
import com.lpmas.declare.bean.FarmerJobInfoBean;
import com.lpmas.declare.bean.FarmerSkillInfoBean;
import com.lpmas.declare.config.DeclareInfoConfig;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.web.HttpResponseKit;

@WebServlet("/declare/admin/DeclareInfoRecommendInsert.do")
public class DeclareInfoRecommendInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(DeclareInfoRecommendInsert.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeclareInfoRecommendInsert() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request
				.getRequestDispatcher(DeclareAdminConfig.PAGE_PATH + "DeclareInfoRecommendInsert.jsp");
		rd.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int declareId = 0;
		int resultFarmerInfo = 0;
		int resultFarmerContactInfo = 0;
		int resultMongo = 0;
		DeclareInfoBusiness declareInfoBusiness = new DeclareInfoBusiness();
		FarmerInfoBusiness farmerInfoBusiness = new FarmerInfoBusiness();
		FarmerContactInfoBusiness farmerContactInfoBusiness = new FarmerContactInfoBusiness();
		// 映射Bean
		DeclareInfoBean declareInfoBean = BeanKit.request2Bean(request, DeclareInfoBean.class);
		FarmerInfoBean farmerInfoBean = BeanKit.request2Bean(request, FarmerInfoBean.class);
		FarmerContactInfoBean farmerContactInfoBean = BeanKit.request2Bean(request, FarmerContactInfoBean.class);
		// 新建申报信息
		declareInfoBean.setDeclareStatus(DeclareInfoConfig.DECLARE_STATUS_NOT_SUBMIT);
		declareInfoBean.setDeclareCategory(DeclareInfoConfig.DECLARE_CATEGORY_SPARE);
		declareInfoBean.setUserId(Constants.STATUS_NOT_VALID);
		declareInfoBean.setStatus(Constants.STATUS_VALID);
		// declareInfoBean.setCreateUser();
		declareId = declareInfoBusiness.addDeclareInfo(declareInfoBean);
		if (declareId <= 0) {
			HttpResponseKit.alertMessage(response, "操作失败", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		// 新建农民信息
		// farmerInfoBean.setCreateUser();
		farmerInfoBean.setStatus(Constants.STATUS_VALID);
		farmerInfoBean.setDeclareId(declareId);
		resultFarmerInfo = farmerInfoBusiness.addFarmerInfo(farmerInfoBean);
		// 新建联系信息
		// farmerContactInfoBean.setCreateUser();
		farmerContactInfoBean.setStatus(Constants.STATUS_VALID);
		farmerContactInfoBean.setDeclareId(declareId);
		resultFarmerContactInfo = farmerContactInfoBusiness.addFarmerContactInfo(farmerContactInfoBean);
		// 新建工作表
		FarmerJobInfoBean farmerJobInfoBean = new FarmerJobInfoBean();
		farmerJobInfoBean.setStatus(Constants.STATUS_VALID);
		farmerJobInfoBean.setDeclareId(declareId);
		FarmerJobInfoBusiness farmerJobInfoBusiness = new FarmerJobInfoBusiness();
		farmerJobInfoBusiness.addFarmerJobInfo(farmerJobInfoBean);
		// 新建技能表
		FarmerSkillInfoBean farmerSkillInfoBean = new FarmerSkillInfoBean();
		farmerSkillInfoBean.setStatus(Constants.STATUS_VALID);
		farmerSkillInfoBean.setDeclareId(declareId);
		FarmerSkillInfoBusiness farmerSkillInfoBusiness = new FarmerSkillInfoBusiness();
		farmerSkillInfoBusiness.addFarmerSkillInfo(farmerSkillInfoBean);
		// 新建产业表
		FarmerIndustryInfoBean farmerIndustryInfoBean = new FarmerIndustryInfoBean();
		farmerIndustryInfoBean.setStatus(Constants.STATUS_VALID);
		farmerIndustryInfoBean.setDeclareId(declareId);
		FarmerIndustryInfoBusiness farmerIndustryInfoBusiness = new FarmerIndustryInfoBusiness();
		farmerIndustryInfoBusiness.addFarmerIndustryInfo(farmerIndustryInfoBean);

		// 插入mongo
		DeclareReportHandler handler = new DeclareReportHandler();
		try {
			declareInfoBean.setDeclareId(declareId);
			handler.createDeclareReport(declareInfoBean);
			resultMongo = Constants.STATUS_VALID;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("总表记录创建失败", e);
		}
		if (resultMongo <= 0 || resultFarmerInfo < 0 || resultFarmerContactInfo < 0) {
			HttpResponseKit.alertMessage(response, "操作失败", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		} else {
			HttpResponseKit.alertMessage(response, "处理成功", "/declare/admin/DeclareInfoRecommendList.do");
		}

	}

}
