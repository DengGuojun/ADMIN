package com.lpmas.declare.admin.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.declare.admin.bean.AdminResourceTypeBean;
import com.lpmas.declare.admin.business.AdminResourceTypeBusiness;
import com.lpmas.declare.admin.config.DeclareAdminConfig;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;

/**
 * Servlet implementation class AdminResourceTypeManage
 */
@WebServlet("/declare/admin/AdminResourceTypeManage.do")
public class AdminResourceTypeManage extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(AdminResourceTypeManage.class);
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminResourceTypeManage() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int typeId = ParamKit.getIntParameter(request, "typeId", 0);
		AdminResourceTypeBean bean = new AdminResourceTypeBean();
		if (typeId > 0) {
//			if (!adminHelper.checkPermission(AdminResource.ADMIN_RESOURCE_TYPE, OperationConfig.UPDATE)) {
//				return;
//			}
			AdminResourceTypeBusiness business = new AdminResourceTypeBusiness();
			bean = business.getAdminResourceTypeByKey(typeId);
		} else {
//			if (!adminHelper.checkPermission(AdminResource.ADMIN_RESOURCE_TYPE, OperationConfig.CREATE)) {
//				return;
//			}
		}

		request.setAttribute("TypeInfo", bean);

		RequestDispatcher rd = request.getRequestDispatcher(DeclareAdminConfig.PAGE_PATH + "AdminResourceTypeManage.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		AdminResourceTypeBean bean = new AdminResourceTypeBean();
		try {
			bean = BeanKit.request2Bean(request, AdminResourceTypeBean.class);
			AdminResourceTypeBusiness business = new AdminResourceTypeBusiness();

			int result = 0;
			if (bean.getTypeId() > 0) {
				result = business.updateAdminResourceType(bean);
			} else {
				result = business.addAdminResourceType(bean);
			}

			if (result > 0) {
				HttpResponseKit.alertMessage(response, "处理成功", "/declare/admin/AdminResourceTypeList.do");
			} else {
				HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
			}
		} catch (Exception e) {
			log.error("{}", e.toString());
		}
	}

}
