package com.lpmas.admin.action;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.admin.bean.AdminGroupInfoBean;
import com.lpmas.admin.business.AdminGroupInfoBusiness;
import com.lpmas.admin.business.AdminUserHelper;
import com.lpmas.admin.config.AdminConfig;
import com.lpmas.admin.config.AdminResource;
import com.lpmas.admin.config.OperationConfig;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.MapKit;
import com.lpmas.framework.web.ParamKit;

/**
 * Servlet implementation class AdminGroupInfoList
 */
@WebServlet("/admin/AdminGroupInfoList.do")
public class AdminGroupInfoList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminGroupInfoList() {
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
		AdminUserHelper adminHelper = new AdminUserHelper(request, response);
		if (!adminHelper.checkPermission(AdminResource.ADMIN_GROUP, OperationConfig.SEARCH)) {
			return;
		}

		int pageNum = ParamKit.getIntParameter(request, "pageNum", 1);
		int pageSize = ParamKit.getIntParameter(request, "pageSize", 20);
		PageBean pageBean = new PageBean(pageNum, pageSize);

		// 处理查询条件
		String condStr = "groupName";
		HashMap<String, String> condMap = ParamKit.getParameterMap(request, condStr);
		
		String status = ParamKit.getParameter(request, "status", Constants.STATUS_VALID+"");
		condMap.put("status", status);

		AdminGroupInfoBusiness business = new AdminGroupInfoBusiness();
		PageResultBean<AdminGroupInfoBean> result = business.getAdminGroupInfoPageListByMap(condMap, pageBean);
		request.setAttribute("GroupList", result.getRecordList());

		pageBean.init(pageNum, pageSize, result.getTotalRecordNumber());
		request.setAttribute("PageResult", pageBean);

		request.setAttribute("CondList", MapKit.map2List(condMap));

		request.setAttribute("AdminUserHelper", adminHelper);

		String path = AdminConfig.PAGE_PATH + "AdminGroupInfoList.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}

}
