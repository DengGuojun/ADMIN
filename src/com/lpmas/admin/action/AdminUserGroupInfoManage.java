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

import com.lpmas.admin.business.AdminGroupInfoBusiness;
import com.lpmas.admin.business.AdminGroupUserBusiness;
import com.lpmas.admin.business.AdminUserHelper;
import com.lpmas.admin.config.AdminConfig;
import com.lpmas.admin.config.AdminResource;
import com.lpmas.admin.config.OperationConfig;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;

@WebServlet("/admin/AdminUserGroupInfoManage.do")
public class AdminUserGroupInfoManage extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(AdminUserInfoManage.class);
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		AdminUserHelper adminHelper = new AdminUserHelper(request, response);
		if (!adminHelper.checkPermission(AdminResource.ADMIN_USER, OperationConfig.UPDATE)) {
			return;
		}
		int userId = ParamKit.getIntParameter(request, "userId", 0);
		
		// 处理用户组
		AdminGroupInfoBusiness groupBusiness = new AdminGroupInfoBusiness();
		request.setAttribute("GroupList", groupBusiness.getAdminGroupInfoValidList());
		if (userId > 0) {
			AdminGroupUserBusiness guBusiness = new AdminGroupUserBusiness();
			request.setAttribute("GroupSet", guBusiness.getGroupSetByUserId(userId));
		} else {
			request.setAttribute("GroupSet", new HashSet<Integer>());
		}

		String path = AdminConfig.PAGE_PATH + "AdminUserGroupInfoManage.jsp";
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
		
		// 处理用户组
		int[] groupIds = ParamKit.getIntParameters(request, "groupId", 0);
		AdminGroupUserBusiness guBusiness = new AdminGroupUserBusiness();
		boolean isSucAdminGroupUser = guBusiness.saveAdminGroupUser(userId, groupIds);

		if (isSucAdminGroupUser) {
			HttpResponseKit.alertMessage(response, "处理成功", "/admin/AdminUserGroupInfoManage.do?userId="+userId);
		} else {
			HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
		}
	}

}
