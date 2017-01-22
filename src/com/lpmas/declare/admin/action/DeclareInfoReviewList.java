package com.lpmas.declare.admin.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.declare.admin.business.DeclareReportBusiness;
import com.lpmas.declare.admin.config.DeclareAdminConfig;
import com.lpmas.declare.admin.config.DeclareInfoRecommendConfig;
import com.lpmas.declare.bean.DeclareReportBean;
import com.lpmas.declare.config.DeclareInfoConfig;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.MapKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;

@WebServlet("/declare/admin/DeclareInfoReviewList.do")
public class DeclareInfoReviewList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeclareInfoReviewList() {
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
		int modelType = ParamKit.getIntParameter(request, "modelType", 0);
		// 初始化页面分页参数
		int pageNum = ParamKit.getIntParameter(request, "pageNum", DeclareAdminConfig.DEFAULT_PAGE_NUM);
		int pageSize = ParamKit.getIntParameter(request, "pageSize", DeclareAdminConfig.DEFAULT_PAGE_SIZE);
		PageBean pageBean = new PageBean(pageNum, pageSize);
		HashMap<String, String> condMap = new HashMap<String, String>();
		String userName = ParamKit.getParameter(request, "userName", "").trim();
		if (StringKit.isValid(userName)) {
			condMap.put("userName", userName);
		}
		String userMobile = ParamKit.getParameter(request, "userMobile", "").trim();
		if (StringKit.isValid(userMobile)) {
			condMap.put("userMobile", userMobile);
		}
		String identityNumber = ParamKit.getParameter(request, "identityNumber", "").trim();
		if (StringKit.isValid(identityNumber)) {
			condMap.put("identityNumber", identityNumber);
		}
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		HashMap<String, List<String>> scopeMap = new HashMap<String, List<String>>();
		if (modelType == DeclareInfoRecommendConfig.TYPE_VERIFY) {
			// 从Mongo中获取相应的数据
			DeclareReportBusiness business = new DeclareReportBusiness();
			try {

				List<String> reviewStatusList = new ArrayList<String>();
				reviewStatusList.add(DeclareInfoConfig.DECLARE_STATUS_SUBMIT);
				reviewStatusList.add(DeclareInfoConfig.DECLARE_STATUS_CITY_APPROVE);
				scopeMap.put("declareStatus", reviewStatusList);
				PageResultBean<DeclareReportBean> result = business.getDeclareReportPageListByMap(condMap, scopeMap,
						pageBean);

				request.setAttribute("DeclareReportList", result.getRecordList());
				// 初始化分页数据
				pageBean.init(pageNum, pageSize, result.getTotalRecordNumber());
				request.setAttribute("PageResult", pageBean);
				request.setAttribute("CondList", MapKit.map2List(condMap));
				request.setAttribute("modelType", modelType);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (modelType == DeclareInfoRecommendConfig.TYPE_MANAGE) {
			// 从Mongo中获取相应的数据
			DeclareReportBusiness business = new DeclareReportBusiness();
			try {
				String declareStatus = ParamKit.getParameter(request, "declareStatus", "").trim();
				if (StringKit.isValid(declareStatus)) {
					condMap.put("declareStatus", declareStatus);
				}
				String province = ParamKit.getParameter(request, "province", "").trim();
				if (StringKit.isValid(province)) {
					condMap.put("province", province);
				}
				String city = ParamKit.getParameter(request, "city", "").trim();
				if (StringKit.isValid(city)) {
					condMap.put("city", city);
				}
				String region = ParamKit.getParameter(request, "region", "").trim();
				if (StringKit.isValid(region)) {
					condMap.put("region", region);
				}
				List<String> reviewStatusList = new ArrayList<String>();
				reviewStatusList.add(DeclareInfoConfig.DECLARE_STATUS_SUBMIT);
				reviewStatusList.add(DeclareInfoConfig.DECLARE_STATUS_CITY_APPROVE);
				reviewStatusList.add(DeclareInfoConfig.DECLARE_STATUS_APPROVE);
				scopeMap.put("declareStatus", reviewStatusList);
				PageResultBean<DeclareReportBean> result = business.getDeclareReportPageListByMap(condMap, scopeMap,
						pageBean);

				request.setAttribute("DeclareReportList", result.getRecordList());
				// 初始化分页数据
				pageBean.init(pageNum, pageSize, result.getTotalRecordNumber());
				request.setAttribute("PageResult", pageBean);
				request.setAttribute("CondList", MapKit.map2List(condMap));
				request.setAttribute("modelType", modelType);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (modelType == DeclareInfoRecommendConfig.TYPE_CLASSIFY) {
			// 从Mongo中获取相应的数据
			DeclareReportBusiness business = new DeclareReportBusiness();
			try {
				condMap.put("declareStatus", DeclareInfoConfig.DECLARE_STATUS_APPROVE);
				String province = ParamKit.getParameter(request, "province", "").trim();
				if (StringKit.isValid(province)) {
					condMap.put("province", province);
				}
				String city = ParamKit.getParameter(request, "city", "").trim();
				if (StringKit.isValid(city)) {
					condMap.put("city", city);
				}
				String region = ParamKit.getParameter(request, "region", "").trim();
				if (StringKit.isValid(region)) {
					condMap.put("region", region);
				}
				PageResultBean<DeclareReportBean> result = business.getDeclareReportPageListByMap(condMap, scopeMap,
						pageBean);

				request.setAttribute("DeclareReportList", result.getRecordList());
				// 初始化分页数据
				pageBean.init(pageNum, pageSize, result.getTotalRecordNumber());
				request.setAttribute("PageResult", pageBean);
				request.setAttribute("CondList", MapKit.map2List(condMap));
				request.setAttribute("modelType", modelType);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			HttpResponseKit.alertMessage(response, "页面操作非法", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		RequestDispatcher rd = request.getRequestDispatcher(DeclareAdminConfig.PAGE_PATH + "DeclareInfoReviewList.jsp");
		rd.forward(request, response);

	}

}
