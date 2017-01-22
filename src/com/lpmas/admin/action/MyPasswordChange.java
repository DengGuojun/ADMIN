package com.lpmas.admin.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.admin.bean.AdminUserInfoBean;
import com.lpmas.admin.business.AdminUserHelper;
import com.lpmas.admin.business.AdminUserInfoBusiness;
import com.lpmas.admin.config.AdminConfig;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;

/**
 * Servlet implementation class MyPasswordChange
 */
@WebServlet("/admin/MyPasswordChange.do")
public class MyPasswordChange extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MyPasswordChange() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = AdminConfig.PAGE_PATH + "MyPasswordChange.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminHelper = new AdminUserHelper(request, response);

		String loginPassword = ParamKit.getParameter(request, "loginPassword");
		String confirmLoginPassword = ParamKit.getParameter(request, "confirmLoginPassword");

		if (!StringKit.isValid(loginPassword) || !StringKit.isValid(confirmLoginPassword)) {
			HttpResponseKit.alertMessage(response, "密码不能为空！", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		if (!loginPassword.equals(confirmLoginPassword)) {
			HttpResponseKit.alertMessage(response, "密码和确认密码不相同！", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		AdminUserInfoBusiness business = new AdminUserInfoBusiness();
		AdminUserInfoBean bean = business.getAdminUserInfoByKey(adminHelper.getAdminUserId());
		String cryptoPassword = business.getCryptoPassword(loginPassword);
		bean.setLoginPassword(cryptoPassword);

		int result = business.updateAdminUserInfo(bean);

		if (result > 0) {
			HttpResponseKit.alertMessage(response, "处理成功", "/admin/MyPasswordChange.do");
		} else {
			HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
		}
	}

}
