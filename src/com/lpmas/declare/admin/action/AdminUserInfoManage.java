package com.lpmas.declare.admin.action;

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

import com.lpmas.declare.admin.bean.AdminRoleInfoBean;
import com.lpmas.declare.admin.bean.AdminRoleUserBean;
import com.lpmas.declare.admin.bean.AdminUserInfoBean;
import com.lpmas.declare.admin.business.AdminRoleInfoBusiness;
import com.lpmas.declare.admin.business.AdminRoleUserBusiness;
import com.lpmas.declare.admin.business.AdminUserInfoBusiness;
import com.lpmas.declare.admin.config.DeclareAdminConfig;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;

/**
 * Servlet implementation class AdminUserInfoManage
 */
@WebServlet("/declare/admin/AdminUserInfoManage.do")
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
		int userId = ParamKit.getIntParameter(request, "userId", 0);
		AdminUserInfoBean bean = new AdminUserInfoBean();
		AdminRoleUserBean roleUserBean = new AdminRoleUserBean();
		if (userId > 0) {
			AdminUserInfoBusiness business = new AdminUserInfoBusiness();
			bean = business.getAdminUserInfoByKey(userId);
			// 查询用户当前角色
			AdminRoleUserBusiness roleUserBusiness = new AdminRoleUserBusiness();
			List<AdminRoleUserBean> roleUserList = roleUserBusiness.getAdminRoleUserListByUserId(userId);
			roleUserBean = roleUserList.get(0);

		} else {
			bean.setStatus(Constants.STATUS_VALID);
		}

		// 查询角色列表
		AdminRoleInfoBusiness roleBusiness = new AdminRoleInfoBusiness();
		List<AdminRoleInfoBean> roleList = roleBusiness.getAdminRoleInfoAllList();

		request.setAttribute("UserInfo", bean);
		request.setAttribute("RoleList", roleList);
		request.setAttribute("RoleUser", roleUserBean);

		RequestDispatcher rd = request.getRequestDispatcher(DeclareAdminConfig.PAGE_PATH + "AdminUserInfoManage.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		int roleId = ParamKit.getIntParameter(request, "roleId", 0);
		AdminUserInfoBean bean = new AdminUserInfoBean();
		AdminRoleUserBean roleUserBean = new AdminRoleUserBean();
		try {
			bean = BeanKit.request2Bean(request, AdminUserInfoBean.class);
			if (bean.getUserId() > 0) {
				// if (!adminHelper.checkPermission(AdminResource.ADMIN_USER,
				// OperationConfig.UPDATE)) {
				// return;
				// }
			} else {
				// if (!adminHelper.checkPermission(AdminResource.ADMIN_USER,
				// OperationConfig.CREATE)) {
				// return;
				// }
			}

			AdminUserInfoBusiness business = new AdminUserInfoBusiness();

			// 判断用户登录ID是否已经存在
			// if (business.isDuplicateLoginId(bean.getUserId(),
			// bean.getLoginId())) {
			// HttpResponseKit.alertMessage(response, "已存在相同的登录ID！",
			// HttpResponseKit.ACTION_HISTORY_BACK);
			// return;
			// }

			if (StringKit.isValid(bean.getLoginPassword())) {// 如果密码不为空，修改用户的密码
				String loginPassword = bean.getLoginPassword();
				int length = loginPassword.length();
				if (!loginPassword.matches("([a-z]|[A-Z]|[0-9]){6,12}")
						|| loginPassword.replaceAll("([a-z]|[A-Z])", "").length() == length
						|| loginPassword.replaceAll("([0-9])", "").length() == length) {
					HttpResponseKit.alertMessage(response, "新密码格式不对，正确格式包含字母和数字6到12位",
							HttpResponseKit.ACTION_HISTORY_BACK);
					return;
				}
			} else if (bean.getUserId() <= 0) {
				HttpResponseKit.alertMessage(response, "密码不能为空", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}

			AdminRoleUserBusiness roleUserBusiness = new AdminRoleUserBusiness();
			int result = 0;
			if (bean.getUserId() > 0) {
				AdminUserInfoBean infoBean = business.getAdminUserInfoByKey(bean.getUserId());
				if (StringKit.isValid(bean.getLoginPassword())) {// 如果密码不为空，修改用户的密码
					bean.setLoginPassword(business.getCryptoPassword(bean.getLoginPassword()));
				} else {// 如果密码为空，不修改
					bean.setLoginPassword(infoBean.getLoginPassword());
				}
				result = business.updateAdminUserInfo(bean);
				// 更新角色用户关系
				List<AdminRoleUserBean> roleUserList = roleUserBusiness.getAdminRoleUserListByUserId(bean.getUserId());
				roleUserBean = roleUserList.get(0);
				if (roleUserBean.getRoleId() != roleId) {
					roleUserBean.setRoleId(roleId);
					roleUserBusiness.updateAdminRoleUser(roleUserBean);
				}

			} else {
				bean.setLoginPassword(business.getCryptoPassword(bean.getLoginPassword()));
				result = business.addAdminUserInfo(bean);
				roleUserBean.setUserId(result);
				roleUserBean.setRoleId(roleId);
				roleUserBusiness.addAdminRoleUser(roleUserBean);
			}

			if (result > 0) {
				HttpResponseKit.alertMessage(response, "处理成功", "/declare/admin/AdminUserInfoList.do");
			} else {
				HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
			}
		} catch (Exception e) {
			log.error("{}", e.toString());
		}
	}

}
