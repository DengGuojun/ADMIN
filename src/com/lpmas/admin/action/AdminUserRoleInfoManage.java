package com.lpmas.admin.action;

import java.io.IOException;
import java.util.HashSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.admin.business.AdminRoleInfoBusiness;
import com.lpmas.admin.business.AdminRoleUserBusiness;
import com.lpmas.admin.business.AdminUserHelper;
import com.lpmas.admin.config.AdminConfig;
import com.lpmas.admin.config.AdminResource;
import com.lpmas.admin.config.OperationConfig;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;

@WebServlet("/admin/AdminUserRoleInfoManage.do")
public class AdminUserRoleInfoManage extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(AdminUserInfoManage.class);
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		AdminUserHelper adminHelper = new AdminUserHelper(request, response);
		if (!adminHelper.checkPermission(AdminResource.ADMIN_USER, OperationConfig.UPDATE)) {
			return;
		}
		int userId = ParamKit.getIntParameter(request, "userId", 0);

		// 处理用户角色
		AdminRoleInfoBusiness roleBusiness = new AdminRoleInfoBusiness();
		request.setAttribute("RoleList", roleBusiness.getAdminRoleInfoValidList());
		if (userId > 0) {
			AdminRoleUserBusiness ruBusiness = new AdminRoleUserBusiness();
			request.setAttribute("RoleSet", ruBusiness.getRoleSetByUserId(userId));
		} else {
			request.setAttribute("RoleSet", new HashSet<Integer>());
		}

		String path = AdminConfig.PAGE_PATH + "AdminUserRoleInfoManage.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminUserHelper adminHelper = new AdminUserHelper(request, response);
		if (!adminHelper.checkPermission(AdminResource.ADMIN_USER, OperationConfig.UPDATE)) {
			return;
		}

		int userId = ParamKit.getIntParameter(request, "userId", 0);

		// 处理角色
		int[] roleIds = ParamKit.getIntParameters(request, "roleId", 0);
		log.debug("[AUDIT] : (update userRole) userId : " + adminHelper.getAdminUserId() + " roleIds: "+roleIds);
		
		AdminRoleUserBusiness ruBusiness = new AdminRoleUserBusiness();
		boolean isSucAdminRoleUser = ruBusiness.saveAdminRoleUser(userId, roleIds);

		if (isSucAdminRoleUser) {
			HttpResponseKit.alertMessage(response, "处理成功", "/admin/AdminUserRoleInfoManage.do?userId="+userId);
		} else {
			HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
		}
	}

}
