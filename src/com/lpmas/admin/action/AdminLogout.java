package com.lpmas.admin.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.admin.business.AdminUserHelper;
import com.lpmas.admin.cache.AdminPrivilegeInfoCache;
import com.lpmas.admin.config.AdminConfig;
import com.lpmas.framework.web.CookiesKit;

/**
 * Servlet implementation class AdminLogout
 */
@WebServlet("/Logout.do")
public class AdminLogout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminLogout() {
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
		AdminUserHelper adminHelper = new AdminUserHelper(request);
		// 清除memcached的记录，用户权限
		AdminPrivilegeInfoCache privilegeCache = new AdminPrivilegeInfoCache();
		privilegeCache.refreshAdminPrivilegeSetByUserId(adminHelper.getAdminUserId());
		privilegeCache.refreshAdminPrivilegeCodeSetByUserId(adminHelper.getAdminUserId());

		CookiesKit.deleteCookie(response, AdminConfig.ADMIN_USER_KEY, AdminConfig.ADMIN_DOMAIN);
		response.sendRedirect("/Logon.do");
	}

}
