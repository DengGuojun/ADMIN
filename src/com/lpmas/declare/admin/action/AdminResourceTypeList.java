package com.lpmas.declare.admin.action;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.declare.admin.bean.AdminResourceTypeBean;
import com.lpmas.declare.admin.business.AdminResourceTypeBusiness;
import com.lpmas.declare.admin.config.DeclareAdminConfig;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.MapKit;
import com.lpmas.framework.web.ParamKit;

/**
 * Servlet implementation class AdminResourceTypeList
 */
@WebServlet("/declare/admin/AdminResourceTypeList.do")
public class AdminResourceTypeList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminResourceTypeList() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {

		int pageNum = ParamKit.getIntParameter(request, "pageNum", 1);
		int pageSize = ParamKit.getIntParameter(request, "pageSize", 20);
		PageBean pageBean = new PageBean(pageNum, pageSize);

		String condStr = "";
		HashMap<String, String> condMap = ParamKit.getParameterMap(request, condStr);

		AdminResourceTypeBusiness business = new AdminResourceTypeBusiness();
		PageResultBean<AdminResourceTypeBean> result = business.getAdminResourceTypePageListByMap(condMap, pageBean);
		request.setAttribute("TypeList", result.getRecordList());

		pageBean.init(pageNum, pageSize, result.getTotalRecordNumber());
		request.setAttribute("PageResult", pageBean);

		request.setAttribute("CondList", MapKit.map2List(condMap));


		RequestDispatcher rd = request.getRequestDispatcher(DeclareAdminConfig.PAGE_PATH + "AdminResourceTypeList.jsp");
		rd.forward(request, response);
	}

}
