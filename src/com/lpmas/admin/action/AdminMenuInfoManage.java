package com.lpmas.admin.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.admin.bean.AdminMenuInfoBean;
import com.lpmas.admin.bean.AdminOperationInfoBean;
import com.lpmas.admin.bean.AdminPrivilegeDefineBean;
import com.lpmas.admin.bean.AdminResourceInfoBean;
import com.lpmas.admin.business.AdminMenuInfoBusiness;
import com.lpmas.admin.business.AdminOperationInfoBusiness;
import com.lpmas.admin.business.AdminPrivilegeDefineBusiness;
import com.lpmas.admin.business.AdminResourceInfoBusiness;
import com.lpmas.admin.business.AdminUserHelper;
import com.lpmas.admin.business.AdminUtil;
import com.lpmas.admin.config.AdminConfig;
import com.lpmas.admin.config.AdminResource;
import com.lpmas.admin.config.OperationConfig;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;

/**
 * Servlet implementation class AdminMenuInfoManage
 */
@WebServlet("/admin/AdminMenuInfoManage.do")
public class AdminMenuInfoManage extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(AdminMenuInfoManage.class);
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminMenuInfoManage() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminUserHelper adminHelper = new AdminUserHelper(request, response);

		int menuId = ParamKit.getIntParameter(request, "menuId", 0);
		AdminMenuInfoBean bean = new AdminMenuInfoBean();
		if (menuId > 0) {
			if (!adminHelper.checkPermission(AdminResource.ADMIN_MENU, OperationConfig.UPDATE)) {
				return;
			}

			AdminMenuInfoBusiness business = new AdminMenuInfoBusiness();
			bean = business.getAdminMenuInfoByKey(menuId);
		} else {
			if (!adminHelper.checkPermission(AdminResource.ADMIN_MENU, OperationConfig.CREATE)) {
				return;
			}
			bean.setIsDisplay(Constants.STATUS_VALID);
			bean.setStatus(Constants.STATUS_VALID);
		}

		request.setAttribute("MenuInfo", bean);

		AdminMenuInfoBean parentBean = new AdminMenuInfoBean();
		if (bean.getParentMenuId() > 0) {
			AdminMenuInfoBusiness business = new AdminMenuInfoBusiness();
			parentBean = business.getAdminMenuInfoByKey(bean.getParentMenuId());
		}
		request.setAttribute("ParentMenuInfo", parentBean);

		AdminOperationInfoBusiness operationBusiness = new AdminOperationInfoBusiness();
		List<AdminOperationInfoBean> operationList = operationBusiness.getAdminOperationInfoAllList();
		request.setAttribute("OperationList", operationList);

		// 处理权限串
		AdminResourceInfoBean resourceBean = new AdminResourceInfoBean();
		int operationId = 0;
		if (StringKit.isValid(bean.getMenuPrivilege())) {
			int[] privilegeArray = AdminUtil.parsePrivilegeCode(bean.getMenuPrivilege());
			int resourceId = privilegeArray[0];
			AdminResourceInfoBusiness resourceBusiness = new AdminResourceInfoBusiness();
			resourceBean = resourceBusiness.getAdminResourceInfoByKey(resourceId);

			operationId = privilegeArray[1];
		}
		request.setAttribute("ResourceInfo", resourceBean);
		request.setAttribute("OperationId", operationId);

		String path = AdminConfig.PAGE_PATH + "AdminMenuInfoManage.jsp";
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

		AdminMenuInfoBean bean = new AdminMenuInfoBean();
		try {
			bean = BeanKit.request2Bean(request, AdminMenuInfoBean.class);
			// 判断权限设置是否正确
			if (bean.getMenuType() == AdminConfig.MENU_TYPE_ITEM) {
				int resourceId = ParamKit.getIntParameter(request, "resourceId", 0);
				int operationId = ParamKit.getIntParameter(request, "operationId", 0);
				if (!checkMenuPrivilege(resourceId, operationId)) {
					HttpResponseKit.alertMessage(response, "请设置正确的菜单权限", HttpResponseKit.ACTION_HISTORY_BACK);
					return;
				}
				bean.setMenuPrivilege(AdminUtil.getPrivilegeCode(resourceId, operationId));
			} else {// 目录类型清空menuPrivilege
				bean.setMenuPrivilege("");
			}

			AdminMenuInfoBusiness business = new AdminMenuInfoBusiness();
			if (business.isDuplicateMenuCode(bean.getMenuId(), bean.getMenuCode())) {// 检查是否有重复的code
				HttpResponseKit.alertMessage(response, "菜单代码重复", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}

			int result = 0;
			if (bean.getMenuId() > 0) {
				if (!adminHelper.checkPermission(AdminResource.ADMIN_MENU, OperationConfig.UPDATE)) {
					return;
				}
				bean.setModifyUser(adminHelper.getAdminUserId());
				result = business.updateAdminMenuInfo(bean);
			} else {
				if (!adminHelper.checkPermission(AdminResource.ADMIN_MENU, OperationConfig.CREATE)) {
					return;
				}
				bean.setCreateUser(adminHelper.getAdminUserId());
				result = business.addAdminMenuInfo(bean);
			}

			if (result > 0) {
				HttpResponseKit.alertMessage(response, "处理成功", "/admin/AdminMenuInfoList.do");
			} else {
				HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
			}
		} catch (Exception e) {
			log.error("", e);
		}
	}

	private boolean checkMenuPrivilege(int resourceId, int operationId) {
		if (resourceId > 0 && operationId > 0) {
			AdminPrivilegeDefineBusiness business = new AdminPrivilegeDefineBusiness();
			AdminPrivilegeDefineBean bean = business.getAdminPrivilegeDefineByKey(resourceId, operationId);
			if (bean == null) {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}
}
