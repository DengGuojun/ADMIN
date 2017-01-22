package com.lpmas.admin.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.admin.bean.AdminUserInfoBean;
import com.lpmas.admin.business.AdminLogonUtil;
import com.lpmas.admin.business.AdminUserInfoBusiness;
import com.lpmas.admin.config.AdminConfig;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.CookiesKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;

/**
 * Servlet implementation class AdminLogon
 */
@WebServlet("/Logon.do")
public class AdminLogon extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminLogon() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = AdminConfig.PAGE_PATH + "AdminLogon.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String loginId = ParamKit.getParameter(request, "loginId", "");
		String loginPassword = ParamKit.getParameter(request, "loginPassword", "");

		if (!StringKit.isVerified(loginId)) {
			HttpResponseKit.alertMessage(response, "用户ID为空或者含有非法字符！", HttpResponseKit.ACTION_HISTORY_BACK);
		}

		if (!StringKit.isVerified(loginPassword)) {
			HttpResponseKit.alertMessage(response, "用户密码为空或者含有非法字符！", HttpResponseKit.ACTION_HISTORY_BACK);
		}

		AdminUserInfoBusiness business = new AdminUserInfoBusiness();
		AdminUserInfoBean bean = business.isValidAdminUser(loginId, loginPassword);
		if (bean != null) {
			// 记录Cookies
			String cookieStr = AdminLogonUtil.encryptLogonSign(bean.getUserId());
			CookiesKit.setCookie(response, AdminConfig.ADMIN_USER_KEY, cookieStr, AdminConfig.ADMIN_DOMAIN);

			// 跳转
			String returnUrl = ParamKit.getParameter(request, "returnUrl");
			if (StringKit.isValid(returnUrl)) {
				response.sendRedirect(returnUrl);
			} else {
				response.sendRedirect("/index.do");
			}
		} else {
			HttpResponseKit.alertMessage(response, "用户ID或密码错误！", HttpResponseKit.ACTION_HISTORY_BACK);
		}
	}
}
