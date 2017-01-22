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

import com.lpmas.admin.bean.AdminResourceInfoBean;
import com.lpmas.admin.business.AdminResourceInfoBusiness;
import com.lpmas.admin.business.AdminResourceTypeBusiness;
import com.lpmas.admin.business.AdminUserHelper;
import com.lpmas.admin.config.AdminConfig;
import com.lpmas.admin.config.AdminResource;
import com.lpmas.admin.config.OperationConfig;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;

/**
 * Servlet implementation class AdminResourceInfoManage
 */
@WebServlet("/admin/AdminResourceInfoManage.do")
public class AdminResourceInfoManage extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(AdminResourceInfoManage.class);
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminResourceInfoManage() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminHelper = new AdminUserHelper(request, response);
		int resourceId = ParamKit.getIntParameter(request, "resourceId", 0);
		AdminResourceInfoBean bean = new AdminResourceInfoBean();
		if (resourceId > 0) {
			if (!adminHelper.checkPermission(AdminResource.ADMIN_RESOURCE, OperationConfig.UPDATE)) {
				return;
			}
			AdminResourceInfoBusiness business = new AdminResourceInfoBusiness();
			bean = business.getAdminResourceInfoByKey(resourceId);
		} else {
			if (!adminHelper.checkPermission(AdminResource.ADMIN_RESOURCE, OperationConfig.CREATE)) {
				return;
			}
			bean.setStatus(Constants.STATUS_VALID);
		}
		request.setAttribute("ResourceInfo", bean);

		AdminResourceTypeBusiness typeBusiness = new AdminResourceTypeBusiness();
		request.setAttribute("TypeList", typeBusiness.getAdminResourceTypeAllList());

		String path = AdminConfig.PAGE_PATH + "AdminResourceInfoManage.jsp";
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
		AdminResourceInfoBean bean = new AdminResourceInfoBean();
		try {
			bean = BeanKit.request2Bean(request, AdminResourceInfoBean.class);
			AdminResourceInfoBusiness business = new AdminResourceInfoBusiness();

			int result = 0;
			if (business.isDuplicateResourceCode(bean.getResourceId(), bean.getResourceCode())) {
				HttpResponseKit.alertMessage(response, "资源代码重复！", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}

			if (bean.getResourceId() > 0) {
				if (!adminHelper.checkPermission(AdminResource.ADMIN_RESOURCE, OperationConfig.UPDATE)) {
					return;
				}
				result = business.updateAdminResourceInfo(bean);
			} else {
				if (!adminHelper.checkPermission(AdminResource.ADMIN_RESOURCE, OperationConfig.CREATE)) {
					return;
				}
				result = business.addAdminResourceInfo(bean);
			}

			if (result > 0) {
				HttpResponseKit.alertMessage(response, "处理成功",
						"/admin/AdminResourceInfoList.do?typeId=" + bean.getTypeId());
			} else {
				HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
			}
		} catch (Exception e) {
			log.error("{}", e.toString());
		}
	}

}
