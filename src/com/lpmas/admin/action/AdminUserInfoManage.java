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

import com.lpmas.admin.bean.AdminUserInfoBean;
import com.lpmas.admin.business.AdminUserHelper;
import com.lpmas.admin.business.AdminUserInfoBusiness;
import com.lpmas.admin.config.AdminConfig;
import com.lpmas.admin.config.AdminResource;
import com.lpmas.admin.config.OperationConfig;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;

/**
 * Servlet implementation class AdminUserInfoManage
 */
@WebServlet("/admin/AdminUserInfoManage.do")
public class AdminUserInfoManage extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(AdminUserInfoManage.class);
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminUserInfoManage() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminUserHelper adminHelper = new AdminUserHelper(request, response);
		int userId = ParamKit.getIntParameter(request, "userId", 0);
		AdminUserInfoBean bean = new AdminUserInfoBean();
		if (userId > 0) {
			if (!adminHelper.checkPermission(AdminResource.ADMIN_USER, OperationConfig.UPDATE)) {
				return;
			}
			AdminUserInfoBusiness business = new AdminUserInfoBusiness();
			bean = business.getAdminUserInfoByKey(userId);
		} else {
			if (!adminHelper.checkPermission(AdminResource.ADMIN_USER, OperationConfig.CREATE)) {
				return;
			}
			bean.setStatus(Constants.STATUS_VALID);
		}

		request.setAttribute("UserInfo", bean);

//		// 处理用户组
//		AdminGroupInfoBusiness groupBusiness = new AdminGroupInfoBusiness();
//		request.setAttribute("GroupList", groupBusiness.getAdminGroupInfoAllList());
//		if (userId > 0) {
//			AdminGroupUserBusiness guBusiness = new AdminGroupUserBusiness();
//			request.setAttribute("GroupSet", guBusiness.getGroupSetByUserId(userId));
//		} else {
//			request.setAttribute("GroupSet", new HashSet<Integer>());
//		}
//
//		// 处理用户角色
//		AdminRoleInfoBusiness roleBusiness = new AdminRoleInfoBusiness();
//		request.setAttribute("RoleList", roleBusiness.getAdminRoleInfoAllList());
//		if (userId > 0) {
//			AdminRoleUserBusiness ruBusiness = new AdminRoleUserBusiness();
//			request.setAttribute("RoleSet", ruBusiness.getRoleSetByUserId(userId));
//		} else {
//			request.setAttribute("RoleSet", new HashSet<Integer>());
//		}

		String path = AdminConfig.PAGE_PATH + "AdminUserInfoManage.jsp";
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
		AdminUserInfoBean bean = new AdminUserInfoBean();
		try {
			bean = BeanKit.request2Bean(request, AdminUserInfoBean.class);
			if (bean.getUserId() > 0) {
				if (!adminHelper.checkPermission(AdminResource.ADMIN_USER, OperationConfig.UPDATE)) {
					return;
				}
			} else {
				if (!adminHelper.checkPermission(AdminResource.ADMIN_USER, OperationConfig.CREATE)) {
					return;
				}
			}

			AdminUserInfoBusiness business = new AdminUserInfoBusiness();
			int userId = 0;

			// 判断用户登录ID是否已经存在
			if (business.isDuplicateLoginId(bean.getUserId(), bean.getLoginId())) {
				HttpResponseKit.alertMessage(response, "已存在相同的登录ID！", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			
			if (StringKit.isValid(bean.getLoginPassword())) {// 如果密码不为空，修改用户的密码
				String loginPassword=bean.getLoginPassword();
				int length=loginPassword.length();
				if(!loginPassword.matches("([a-z]|[A-Z]|[0-9]){6,12}")||loginPassword.replaceAll("([a-z]|[A-Z])", "").length()==length||loginPassword.replaceAll("([0-9])", "").length()==length){
					HttpResponseKit.alertMessage(response, "新密码格式不对，正确格式包含字母和数字6到12位", HttpResponseKit.ACTION_HISTORY_BACK);
					return;
				}
			}else if (bean.getUserId() <= 0) {
				HttpResponseKit.alertMessage(response, "密码不能为空", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}

			int result = 0;
			if (bean.getUserId() > 0) {
				AdminUserInfoBean infoBean = business.getAdminUserInfoByKey(bean.getUserId());
//				bean.setLoginId(infoBean.getLoginId());
				if (StringKit.isValid(bean.getLoginPassword())) {// 如果密码不为空，修改用户的密码
					bean.setLoginPassword(business.getCryptoPassword(bean.getLoginPassword()));
				} else {// 如果密码为空，不修改
					bean.setLoginPassword(infoBean.getLoginPassword());
				}
				result = business.updateAdminUserInfo(bean);

				userId = bean.getUserId();
			} else {
				bean.setLoginPassword(business.getCryptoPassword(bean.getLoginPassword()));
				result = business.addAdminUserInfo(bean);

				userId = result;
			}

//			// 处理用户组
//			int[] groupIds = ParamKit.getIntParameters(request, "groupId", 0);
//			AdminGroupUserBusiness guBusiness = new AdminGroupUserBusiness();
//			guBusiness.saveAdminGroupUser(userId, groupIds);
//
//			// 处理角色
//			int[] roleIds = ParamKit.getIntParameters(request, "roleId", 0);
//			AdminRoleUserBusiness ruBusiness = new AdminRoleUserBusiness();
//			ruBusiness.saveAdminRoleUser(userId, roleIds);

			if (result > 0) {
				HttpResponseKit.alertMessage(response, "处理成功", "/admin/AdminUserInfoList.do");
			} else {
				HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
			}
		} catch (Exception e) {
			log.error("{}", e.toString());
		}
	}

}
