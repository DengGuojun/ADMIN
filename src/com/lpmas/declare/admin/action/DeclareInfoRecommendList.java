package com.lpmas.declare.admin.action;

import java.io.IOException;
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
import com.lpmas.declare.bean.DeclareReportBean;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.MapKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.ParamKit;

@WebServlet("/declare/admin/DeclareInfoRecommendList.do")
public class DeclareInfoRecommendList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeclareInfoRecommendList() {
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
		condMap.put("userId", String.valueOf(Constants.STATUS_NOT_VALID));
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		HashMap<String, List<String>> scopeMap = new HashMap<String, List<String>>();
		// 从Mongo中获取相应的数据
		DeclareReportBusiness business = new DeclareReportBusiness();
		try {
			PageResultBean<DeclareReportBean> result = business.getDeclareReportPageListByMap(condMap, scopeMap,
					pageBean);

			request.setAttribute("DeclareReportList", result.getRecordList());
			// 初始化分页数据
			pageBean.init(pageNum, pageSize, result.getTotalRecordNumber());
			request.setAttribute("PageResult", pageBean);
			request.setAttribute("CondList", MapKit.map2List(condMap));

		} catch (Exception e) {
			e.printStackTrace();
		}

		RequestDispatcher rd = request
				.getRequestDispatcher(DeclareAdminConfig.PAGE_PATH + "DeclareInfoRecommendList.jsp");
		rd.forward(request, response);
	}

}
