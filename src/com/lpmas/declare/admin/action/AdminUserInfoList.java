package com.lpmas.declare.admin.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.declare.admin.bean.AdminRoleInfoBean;
import com.lpmas.declare.admin.bean.AdminUserInfoBean;
import com.lpmas.declare.admin.business.AdminRoleInfoBusiness;
import com.lpmas.declare.admin.business.AdminRoleUserBusiness;
import com.lpmas.declare.admin.business.AdminUserInfoBusiness;
import com.lpmas.declare.admin.config.DeclareAdminConfig;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.MapKit;
import com.lpmas.framework.web.ParamKit;

/**
 * Servlet implementation class AdminUserInfoList
 */
@WebServlet("/declare/admin/AdminUserInfoList.do")
public class AdminUserInfoList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminUserInfoList() {
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

		// 处理查询条件
		HashMap<String, String> condMap = ParamKit.getParameterMap(request, "adminUserName");
		String status = ParamKit.getParameter(request, "status", String.valueOf(Constants.STATUS_VALID));
		condMap.put("status", status);

		AdminUserInfoBusiness business = new AdminUserInfoBusiness();
		PageResultBean<AdminUserInfoBean> result = business.getAdminUserInfoPageListByMap(condMap, pageBean);
		// 查询用户角色
		Map<Integer, String> userRoleNameMap = new HashMap<Integer, String>();
		AdminRoleUserBusiness roleUserBusiness = new AdminRoleUserBusiness();
		AdminRoleInfoBusiness roleBusiness = new AdminRoleInfoBusiness();
		for (AdminUserInfoBean userInfoBean : result.getRecordList()) {
			int roleId = roleUserBusiness.getAdminRoleUserListByUserId(userInfoBean.getUserId()).get(0).getRoleId();
			AdminRoleInfoBean roleInfoBean = roleBusiness.getAdminRoleInfoByKey(roleId);
			userRoleNameMap.put(userInfoBean.getUserId(), roleInfoBean.getRoleName());
		}

		request.setAttribute("UserRoleNameMap", userRoleNameMap);
		request.setAttribute("UserList", result);
		pageBean.init(pageNum, pageSize, result.getTotalRecordNumber());
		request.setAttribute("PageResult", pageBean);
		request.setAttribute("CondList", MapKit.map2List(condMap));

		RequestDispatcher rd = request.getRequestDispatcher(DeclareAdminConfig.PAGE_PATH + "AdminUserInfoList.jsp");
		rd.forward(request, response);
	}

}
