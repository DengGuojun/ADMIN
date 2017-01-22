package com.lpmas.declare.admin.action;

import java.io.IOException;

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
import com.lpmas.declare.admin.config.DeclareInfoRecommendConfig;
import com.lpmas.declare.admin.handler.DeclareReportHandler;
import com.lpmas.declare.bean.DeclareInfoBean;
import com.lpmas.declare.bean.FarmerContactInfoBean;
import com.lpmas.declare.bean.FarmerIndustryInfoBean;
import com.lpmas.declare.bean.FarmerInfoBean;
import com.lpmas.declare.bean.FarmerJobInfoBean;
import com.lpmas.declare.bean.FarmerSkillInfoBean;
import com.lpmas.declare.config.DeclareInfoConfig;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;

@WebServlet("/declare/admin/DeclareInfoRecommendAcceptCommit.do")
public class DeclareInfoRecommendAcceptCommit extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(DeclareInfoRecommendAcceptCommit.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeclareInfoRecommendAcceptCommit() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int declareId = ParamKit.getIntParameter(request, "declareId", 0);
		if (declareId <= 0) {
			HttpResponseKit.alertMessage(response, "申报ID非法", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		// 根据申报ID查询申报信息
		DeclareInfoBusiness declareInfoBusiness = new DeclareInfoBusiness();
		DeclareInfoBean declareInfoBean = declareInfoBusiness.getDeclareInfoByKey(declareId);
		if (declareInfoBean == null) {
			HttpResponseKit.alertMessage(response, "申报信息不存在", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		int result = 0;
		int resultMongo = 0;
		String action = ParamKit.getParameter(request, "action", "");
		String modelType = ParamKit.getParameter(request, "modelType", "");
		if (action.equals(DeclareInfoRecommendConfig.COMMIT_ACTION_SUBMIT)) {
			// 检查当前状态是否已经被改变
			if (!declareInfoBean.getDeclareStatus().equals(DeclareInfoConfig.DECLARE_STATUS_NOT_SUBMIT)
					&& !declareInfoBean.getDeclareStatus().equals(DeclareInfoConfig.DECLARE_STATUS_NOT_APPROVE)
					&& !declareInfoBean.getDeclareStatus().equals(DeclareInfoConfig.DECLARE_STATUS_REJECT)) {
				HttpResponseKit.alertMessage(response, "页面已过期，请刷新重试", "/declare/admin/DeclareInfoRecommendList.do");
				return;
			}
			declareInfoBean.setDeclareStatus(DeclareInfoConfig.DECLARE_STATUS_SUBMIT);
			// declareInfoBean.setModifyUser();
			result = declareInfoBusiness.updateDeclareInfo(declareInfoBean);
			// 插入mongo
			DeclareReportHandler handler = new DeclareReportHandler();
			try {
				handler.createDeclareReport(declareInfoBean);
				resultMongo = Constants.STATUS_VALID;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("总表记录创建失败", e);
			}
			if (resultMongo <= 0 || result < 0) {
				HttpResponseKit.alertMessage(response, "操作失败", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			} else {
				HttpResponseKit.alertMessage(response, "处理成功", "/declare/admin/DeclareInfoRecommendList.do");
			}
		} else if (action.equals(DeclareInfoRecommendConfig.COMMIT_ACTION_REJECT)) {
			// 检查当前状态是否已经被改变
			if (!declareInfoBean.getDeclareStatus().equals(DeclareInfoConfig.DECLARE_STATUS_SUBMIT)
					&& !declareInfoBean.getDeclareStatus().equals(DeclareInfoConfig.DECLARE_STATUS_APPROVE)
					&& !declareInfoBean.getDeclareStatus().equals(DeclareInfoConfig.DECLARE_STATUS_CITY_APPROVE)) {
				HttpResponseKit.alertMessage(response, "页面已过期，请刷新重试", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			declareInfoBean.setDeclareStatus(DeclareInfoConfig.DECLARE_STATUS_REJECT);
			// declareInfoBean.setModifyUser();
			result = declareInfoBusiness.updateDeclareInfo(declareInfoBean);
			// 插入mongo
			DeclareReportHandler handler = new DeclareReportHandler();
			try {
				handler.createDeclareReport(declareInfoBean);
				resultMongo = Constants.STATUS_VALID;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("总表记录创建失败", e);
			}
			if (resultMongo <= 0 || result < 0) {
				HttpResponseKit.alertMessage(response, "操作失败", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			} else {
				HttpResponseKit.alertMessage(response, "处理成功",
						"/declare/admin/DeclareInfoReviewList.do?modelType=" + modelType);
			}
		} else if (action.equals(DeclareInfoRecommendConfig.COMMIT_ACTION_DELETE)) {
			// 检查当前状态是否已经被改变
			if (!declareInfoBean.getDeclareStatus().equals(DeclareInfoConfig.DECLARE_STATUS_SUBMIT)
					&& !declareInfoBean.getDeclareStatus().equals(DeclareInfoConfig.DECLARE_STATUS_APPROVE)
					&& !declareInfoBean.getDeclareStatus().equals(DeclareInfoConfig.DECLARE_STATUS_CITY_APPROVE)) {
				HttpResponseKit.alertMessage(response, "页面已过期，请刷新重试", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			declareInfoBean.setStatus(Constants.STATUS_NOT_VALID);
			// declareInfoBean.setModifyUser();
			result = declareInfoBusiness.updateDeclareInfo(declareInfoBean);
			FarmerInfoBusiness farmerInfoBusiness = new FarmerInfoBusiness();
			FarmerContactInfoBusiness farmerContactInfoBusiness = new FarmerContactInfoBusiness();
			FarmerJobInfoBusiness farmerJobInfoBusiness = new FarmerJobInfoBusiness();
			FarmerSkillInfoBusiness farmerSkillInfoBusiness = new FarmerSkillInfoBusiness();
			FarmerIndustryInfoBusiness farmerIndustryInfoBusiness = new FarmerIndustryInfoBusiness();
			FarmerInfoBean farmerInfoBean = farmerInfoBusiness.getFarmerInfoByKey(declareId);
			FarmerContactInfoBean farmerContactInfoBean = farmerContactInfoBusiness
					.getFarmerContactInfoByKey(declareId);
			FarmerJobInfoBean farmerJobInfoBean = farmerJobInfoBusiness.getFarmerJobInfoByKey(declareId);
			FarmerSkillInfoBean farmerSkillInfoBean = farmerSkillInfoBusiness.getFarmerSkillInfoByKey(declareId);
			FarmerIndustryInfoBean farmerIndustryInfoBean = farmerIndustryInfoBusiness
					.getFarmerIndustryInfoByKey(declareId);
			farmerInfoBean.setStatus(Constants.STATUS_NOT_VALID);
			farmerContactInfoBean.setStatus(Constants.STATUS_NOT_VALID);
			farmerJobInfoBean.setStatus(Constants.STATUS_NOT_VALID);
			farmerSkillInfoBean.setStatus(Constants.STATUS_NOT_VALID);
			farmerIndustryInfoBean.setStatus(Constants.STATUS_NOT_VALID);
			int resultFarmerInfo = farmerInfoBusiness.updateFarmerInfo(farmerInfoBean);
			int resultFarmerContactInfo = farmerContactInfoBusiness.updateFarmerContactInfo(farmerContactInfoBean);
			int resultFarmerJobInfo = farmerJobInfoBusiness.updateFarmerJobInfo(farmerJobInfoBean);
			int resultFarmerSkillInfo = farmerSkillInfoBusiness.updateFarmerSkillInfo(farmerSkillInfoBean);
			int resultFarmerIndustryInfo = farmerIndustryInfoBusiness.updateFarmerIndustryInfo(farmerIndustryInfoBean);
			// 插入mongo
			DeclareReportHandler handler = new DeclareReportHandler();
			try {
				handler.createDeclareReport(declareInfoBean);
				resultMongo = Constants.STATUS_VALID;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("总表记录创建失败", e);
			}
			if (resultMongo <= 0 || result < 0 || resultFarmerInfo < 0 || resultFarmerContactInfo < 0
					|| resultFarmerJobInfo < 0 || resultFarmerSkillInfo < 0 || resultFarmerIndustryInfo < 0) {
				HttpResponseKit.alertMessage(response, "操作失败", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			} else {
				HttpResponseKit.alertMessage(response, "处理成功",
						"/declare/admin/DeclareInfoReviewList.do?modelType=" + modelType);
			}
		} else if (action.equals(DeclareInfoRecommendConfig.COMMIT_ACTION_APPROVE)) {
			// 检查当前状态是否已经被改变
			if (!declareInfoBean.getDeclareStatus().equals(DeclareInfoConfig.DECLARE_STATUS_SUBMIT)
					&& !declareInfoBean.getDeclareStatus().equals(DeclareInfoConfig.DECLARE_STATUS_CITY_APPROVE)) {
				HttpResponseKit.alertMessage(response, "页面已过期，请刷新重试", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			declareInfoBean.setDeclareStatus(DeclareInfoConfig.DECLARE_STATUS_APPROVE);
			// declareInfoBean.setDeclareStatus(DeclareInfoConfig.DECLARE_STATUS_CITY_APPROVE);
			// declareInfoBean.setModifyUser();
			result = declareInfoBusiness.updateDeclareInfo(declareInfoBean);
			// 插入mongo
			DeclareReportHandler handler = new DeclareReportHandler();
			try {
				handler.createDeclareReport(declareInfoBean);
				resultMongo = Constants.STATUS_VALID;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("总表记录创建失败", e);
			}
			if (resultMongo <= 0 || result < 0) {
				HttpResponseKit.alertMessage(response, "操作失败", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			} else {
				HttpResponseKit.alertMessage(response, "处理成功",
						"/declare/admin/DeclareInfoReviewList.do?modelType=" + modelType);
			}
		} else if (action.equals(DeclareInfoRecommendConfig.COMMIT_ACTION_NOT_APPROVE)) {
			// 检查当前状态是否已经被改变
			if (!declareInfoBean.getDeclareStatus().equals(DeclareInfoConfig.DECLARE_STATUS_SUBMIT)
					&& !declareInfoBean.getDeclareStatus().equals(DeclareInfoConfig.DECLARE_STATUS_CITY_APPROVE)) {
				HttpResponseKit.alertMessage(response, "页面已过期，请刷新重试", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			declareInfoBean.setDeclareStatus(DeclareInfoConfig.DECLARE_STATUS_NOT_APPROVE);
			// declareInfoBean.setDeclareStatus(DeclareInfoConfig.DECLARE_STATUS_CITY_APPROVE);
			// declareInfoBean.setModifyUser();
			result = declareInfoBusiness.updateDeclareInfo(declareInfoBean);
			// 插入mongo
			DeclareReportHandler handler = new DeclareReportHandler();
			try {
				handler.createDeclareReport(declareInfoBean);
				resultMongo = Constants.STATUS_VALID;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("总表记录创建失败", e);
			}
			if (resultMongo <= 0 || result < 0) {
				HttpResponseKit.alertMessage(response, "操作失败", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			} else {
				HttpResponseKit.alertMessage(response, "处理成功",
						"/declare/admin/DeclareInfoReviewList.do?modelType=" + modelType);
			}
		} else if (action.equals(DeclareInfoRecommendConfig.COMMIT_ACTION_CHANGE)) {
			// 检查当前状态是否已经被改变
			if (!declareInfoBean.getDeclareStatus().equals(DeclareInfoConfig.DECLARE_STATUS_APPROVE)) {
				HttpResponseKit.alertMessage(response, "页面已过期，请刷新重试", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			if (declareInfoBean.getDeclareCategory().equals(DeclareInfoConfig.DECLARE_CATEGORY_PRESENT)) {
				declareInfoBean.setDeclareCategory(DeclareInfoConfig.DECLARE_CATEGORY_SPARE);
			} else {
				declareInfoBean.setDeclareCategory(DeclareInfoConfig.DECLARE_CATEGORY_PRESENT);
			}
			// declareInfoBean.setModifyUser();
			result = declareInfoBusiness.updateDeclareInfo(declareInfoBean);
			// 插入mongo
			DeclareReportHandler handler = new DeclareReportHandler();
			try {
				handler.createDeclareReport(declareInfoBean);
				resultMongo = Constants.STATUS_VALID;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("总表记录创建失败", e);
			}
			if (resultMongo <= 0 || result < 0) {
				HttpResponseKit.alertMessage(response, "操作失败", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			} else {
				HttpResponseKit.alertMessage(response, "处理成功",
						"/declare/admin/DeclareInfoReviewList.do?modelType=" + modelType);
			}
		} else {
			HttpResponseKit.alertMessage(response, "提交操作非法", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

	}

}
