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

import com.lpmas.admin.bean.AdminGroupInfoBean;
import com.lpmas.admin.business.AdminGroupInfoBusiness;
import com.lpmas.admin.business.AdminRoleGroupBusiness;
import com.lpmas.admin.business.AdminUserHelper;
import com.lpmas.admin.config.AdminConfig;
import com.lpmas.admin.config.AdminResource;
import com.lpmas.admin.config.OperationConfig;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;

/**
 * Servlet implementation class AdminGroupInfoManage
 */
@WebServlet("/admin/AdminGroupInfoManage.do")
public class AdminGroupInfoManage extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(AdminGroupInfoManage.class);
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminGroupInfoManage() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminUserHelper adminHelper = new AdminUserHelper(request, response);

		int groupId = ParamKit.getIntParameter(request, "groupId", 0);
		AdminGroupInfoBean bean = new AdminGroupInfoBean();
		if (groupId > 0) {
			if (!adminHelper.checkPermission(AdminResource.ADMIN_GROUP, OperationConfig.UPDATE)) {
				return;
			}

			AdminGroupInfoBusiness business = new AdminGroupInfoBusiness();
			bean = business.getAdminGroupInfoByKey(groupId);
		} else {
			if (!adminHelper.checkPermission(AdminResource.ADMIN_GROUP, OperationConfig.CREATE)) {
				return;
			}

			bean.setStatus(Constants.STATUS_VALID);
		}

		request.setAttribute("GroupInfo", bean);

//		屏蔽用户组关联角色功能
//		AdminRoleInfoBusiness roleBusiness = new AdminRoleInfoBusiness();
//		request.setAttribute("RoleList", roleBusiness.getAdminRoleInfoAllList());
//		if (groupId > 0) {
//			AdminRoleGroupBusiness roleGroupBusiness = new AdminRoleGroupBusiness();
//			request.setAttribute("RoleSet", roleGroupBusiness.getRoleSetByGroupId(groupId));
//		} else {
//			request.setAttribute("RoleSet", new HashSet<Integer>());
//		}
		
		String path = AdminConfig.PAGE_PATH + "AdminGroupInfoManage.jsp";
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

		AdminGroupInfoBean bean = new AdminGroupInfoBean();
		try {
			bean = BeanKit.request2Bean(request, AdminGroupInfoBean.class);
			AdminGroupInfoBusiness business = new AdminGroupInfoBusiness();

			int groupId = 0;
			int result = 0;
			if (bean.getGroupId() > 0) {
				if (!adminHelper.checkPermission(AdminResource.ADMIN_GROUP, OperationConfig.UPDATE)) {
					return;
				}
				result = business.updateAdminGroupInfo(bean);

				groupId = bean.getGroupId();
			} else {
				if (!adminHelper.checkPermission(AdminResource.ADMIN_GROUP, OperationConfig.CREATE)) {
					return;
				}
				result = business.addAdminGroupInfo(bean);

				groupId = result;
			}

			// 处理角色
			int[] roleIds = ParamKit.getIntParameters(request, "roleId", 0);
			AdminRoleGroupBusiness groupRoleBusiness = new AdminRoleGroupBusiness();
			groupRoleBusiness.saveAdminRoleGroup(groupId, roleIds);

			if (result > 0) {
				HttpResponseKit.alertMessage(response, "处理成功", "/admin/AdminGroupInfoList.do");
			} else {
				HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
			}
		} catch (Exception e) {
			log.error("{}", e.toString());
		}
	}

}
