package com.lpmas.declare.admin.action;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.declare.admin.bean.AdminResourceInfoBean;
import com.lpmas.declare.admin.business.AdminResourceInfoBusiness;
import com.lpmas.declare.admin.business.AdminResourceTypeBusiness;
import com.lpmas.declare.admin.config.DeclareAdminConfig;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.MapKit;
import com.lpmas.framework.web.ParamKit;

/**
 * Servlet implementation class AdminResourceInfoList
 */
@WebServlet("/declare/admin/AdminResourceInfoList.do")
public class AdminResourceInfoList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminResourceInfoList() {
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

		int pageNum = ParamKit.getIntParameter(request, "pageNum", 1);
		int pageSize = ParamKit.getIntParameter(request, "pageSize", 20);
		PageBean pageBean = new PageBean(pageNum, pageSize);

		// 处理查询条件
		HashMap<String, String> condMap = ParamKit.getParameterMap(request, "resourceName,fromTag");
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));

		AdminResourceInfoBusiness business = new AdminResourceInfoBusiness();
		PageResultBean<AdminResourceInfoBean> result = business.getAdminResourceInfoPageListByMap(condMap, pageBean);

		AdminResourceTypeBusiness typeBusiness = new AdminResourceTypeBusiness();
		request.setAttribute("TypeMap", typeBusiness.getAdminResourceTypeAllMap());
		request.setAttribute("TypeList", typeBusiness.getAdminResourceTypeAllList());
		
		request.setAttribute("ResourceList", result.getRecordList());
		pageBean.init(pageNum, pageSize, result.getTotalRecordNumber());
		request.setAttribute("PageResult", pageBean);
		request.setAttribute("CondList", MapKit.map2List(condMap));

		RequestDispatcher rd = request.getRequestDispatcher(DeclareAdminConfig.PAGE_PATH + "AdminResourceInfoList.jsp");
		rd.forward(request, response);
	}

}
