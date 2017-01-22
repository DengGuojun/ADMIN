package com.lpmas.admin.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.admin.bean.AdminPrivilegeDefineBean;
import com.lpmas.admin.bean.AdminResourceInfoBean;
import com.lpmas.admin.business.AdminOperationInfoBusiness;
import com.lpmas.admin.business.AdminPrivilegeDefineBusiness;
import com.lpmas.admin.business.AdminResourceInfoBusiness;
import com.lpmas.admin.business.AdminUserHelper;
import com.lpmas.admin.config.AdminConfig;
import com.lpmas.admin.config.AdminResource;
import com.lpmas.admin.config.OperationConfig;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;

/**
 * Servlet implementation class AdminPrivilegeDefineManage
 */
@WebServlet("/admin/AdminPrivilegeDefineManage.do")
public class AdminPrivilegeDefineManage extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(AdminPrivilegeDefineManage.class);
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminPrivilegeDefineManage() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminUserHelper adminHelper = new AdminUserHelper(request, response);
		if (!adminHelper.checkPermission(AdminResource.ADMIN_PRIVILEGE_DEFINE, OperationConfig.SEARCH)) {
			return;
		}

		int resourceId = ParamKit.getIntParameter(request, "resourceId", 0);
		List<AdminPrivilegeDefineBean> list = new ArrayList<AdminPrivilegeDefineBean>();
		if (resourceId > 0) {
			AdminPrivilegeDefineBusiness business = new AdminPrivilegeDefineBusiness();
			list = business.getAdminPrivilegeDefineListByResourceId(resourceId);
		}
		request.setAttribute("PrivilegeList", list);
		HashMap<Integer, AdminPrivilegeDefineBean> map = new HashMap<Integer, AdminPrivilegeDefineBean>();
		for (AdminPrivilegeDefineBean bean : list) {
			map.put(bean.getOperationId(), bean);
		}
		request.setAttribute("PrivilegeMap", map);

		AdminOperationInfoBusiness operBusiness = new AdminOperationInfoBusiness();
		request.setAttribute("OperationList", operBusiness.getAdminOperationInfoAllList());

		AdminResourceInfoBean resourceInfoBean = new AdminResourceInfoBean();
		if (resourceId > 0) {
			AdminResourceInfoBusiness resourceBusiness = new AdminResourceInfoBusiness();
			resourceInfoBean = resourceBusiness.getAdminResourceInfoByKey(resourceId);
		}
		request.setAttribute("ResourceInfo", resourceInfoBean);

		request.setAttribute("AdminUserHelper", adminHelper);

		String path = AdminConfig.PAGE_PATH + "AdminPrivilegeDefineManage.jsp";
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
		if (!adminHelper.checkPermission(AdminResource.ADMIN_PRIVILEGE_DEFINE, OperationConfig.UPDATE)) {
			return;
		}

		int resourceId = ParamKit.getIntParameter(request, "resourceId", 0);
		String[] operationIds = ParamKit.getArrayParameter(request, "operationId");
		String[] privilegeDesc = ParamKit.getArrayParameter(request, "privilegeDesc");

		List<AdminPrivilegeDefineBean> list = new ArrayList<AdminPrivilegeDefineBean>();
		if (operationIds != null) {
			for (int i = 0; i < operationIds.length; i++) {
				AdminPrivilegeDefineBean bean = new AdminPrivilegeDefineBean();
				bean.setResourceId(resourceId);
				bean.setOperationId(Integer.parseInt(operationIds[i]));
				bean.setPrivilegeDesc(privilegeDesc[i]);

				list.add(bean);
			}
		}

		AdminPrivilegeDefineBusiness business = new AdminPrivilegeDefineBusiness();
		boolean result = business.saveAdminPrivilegeDefine(resourceId, list);
		if (result) {
			HttpResponseKit.alertMessage(response, "处理成功", "/admin/AdminPrivilegeDefineManage.do?resourceId="
					+ resourceId);
		} else {
			HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
		}

	}

}
