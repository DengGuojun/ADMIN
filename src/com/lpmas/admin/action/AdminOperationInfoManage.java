package com.lpmas.admin.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.admin.bean.AdminOperationInfoBean;
import com.lpmas.admin.business.AdminOperationInfoBusiness;
import com.lpmas.admin.business.AdminUserHelper;
import com.lpmas.admin.config.AdminConfig;
import com.lpmas.admin.config.AdminResource;
import com.lpmas.admin.config.OperationConfig;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;

/**
 * Servlet implementation class AdminOperationInfoManage
 */
@WebServlet("/admin/AdminOperationInfoManage.do")
public class AdminOperationInfoManage extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(AdminOperationInfoManage.class);
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminOperationInfoManage() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminUserHelper adminHelper = new AdminUserHelper(request, response);

		int operationId = ParamKit.getIntParameter(request, "operationId", 0);
		AdminOperationInfoBean bean = new AdminOperationInfoBean();
		if (operationId > 0) {
			if (!adminHelper.checkPermission(AdminResource.ADMIN_OPERATION, OperationConfig.UPDATE)) {
				return;
			}
			AdminOperationInfoBusiness business = new AdminOperationInfoBusiness();
			bean = business.getAdminOperationInfoByKey(operationId);
		} else {
			if (!adminHelper.checkPermission(AdminResource.ADMIN_OPERATION, OperationConfig.CREATE)) {
				return;
			}
		}

		request.setAttribute("OperationInfo", bean);

		String path = AdminConfig.PAGE_PATH + "AdminOperationInfoManage.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		AdminUserHelper adminHelper = new AdminUserHelper(request, response);

		AdminOperationInfoBean bean = new AdminOperationInfoBean();
		try {
			bean = BeanKit.request2Bean(request, AdminOperationInfoBean.class);
			AdminOperationInfoBusiness business = new AdminOperationInfoBusiness();

			int result = 0;
			if (bean.getOperationId() > 0) {
				if (!adminHelper.checkPermission(AdminResource.ADMIN_OPERATION, OperationConfig.UPDATE)) {
					return;
				}
				result = business.updateAdminOperationInfo(bean);
			} else {
				if (!adminHelper.checkPermission(AdminResource.ADMIN_OPERATION, OperationConfig.CREATE)) {
					return;
				}
				result = business.addAdminOperationInfo(bean);
			}

			if (result > 0) {
				HttpResponseKit.alertMessage(response, "处理成功", "/admin/AdminOperationInfoList.do");
			} else {
				HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
			}
		} catch (Exception e) {
			log.error("{}", e.toString());
		}
	}

}
