package com.lpmas.admin.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
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
import com.lpmas.admin.bean.AdminPrivilegeInfoBean;
import com.lpmas.admin.bean.AdminResourceInfoBean;
import com.lpmas.admin.bean.AdminResourceTypeBean;
import com.lpmas.admin.bean.AdminRoleInfoBean;
import com.lpmas.admin.business.AdminOperationInfoBusiness;
import com.lpmas.admin.business.AdminPrivilegeDefineBusiness;
import com.lpmas.admin.business.AdminPrivilegeInfoBusiness;
import com.lpmas.admin.business.AdminResourceInfoBusiness;
import com.lpmas.admin.business.AdminResourceTypeBusiness;
import com.lpmas.admin.business.AdminRoleInfoBusiness;
import com.lpmas.admin.business.AdminUserHelper;
import com.lpmas.admin.config.AdminConfig;
import com.lpmas.admin.config.AdminResource;
import com.lpmas.admin.config.OperationConfig;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;

/**
 * Servlet implementation class AdminRoleInfoManage
 */
@WebServlet("/admin/AdminRoleInfoManage.do")
public class AdminRoleInfoManage extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(AdminRoleInfoManage.class);
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminRoleInfoManage() {
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
		int roleId = ParamKit.getIntParameter(request, "roleId", 0);
		AdminRoleInfoBean bean = new AdminRoleInfoBean();
		if (roleId > 0) {
			if (!adminHelper.checkPermission(AdminResource.ADMIN_ROLE, OperationConfig.UPDATE)) {
				return;
			}
			AdminRoleInfoBusiness business = new AdminRoleInfoBusiness();
			bean = business.getAdminRoleInfoByKey(roleId);
		} else {
			if (!adminHelper.checkPermission(AdminResource.ADMIN_ROLE, OperationConfig.CREATE)) {
				return;
			}
			bean.setStatus(Constants.STATUS_VALID);
		}
		request.setAttribute("RoleInfo", bean);

		// 权限列表
		AdminResourceTypeBusiness typeBusiness = new AdminResourceTypeBusiness();
		List<AdminResourceTypeBean> typeList = typeBusiness.getAdminResourceTypeAllList();
		request.setAttribute("TypeList", typeList);

		AdminResourceInfoBusiness resourceBusiness = new AdminResourceInfoBusiness();
		AdminPrivilegeDefineBusiness defineBusiness = new AdminPrivilegeDefineBusiness();
		for (AdminResourceTypeBean typeBean : typeList) {
			List<AdminResourceInfoBean> resourceList = resourceBusiness
					.getAdminResourceInfoListByType(typeBean.getTypeId());
			request.setAttribute("ResourceList_" + typeBean.getTypeId(), resourceList);

			for (AdminResourceInfoBean resourceBean : resourceList) {
				List<AdminPrivilegeDefineBean> defineList = defineBusiness
						.getAdminPrivilegeDefineListByResourceId(resourceBean.getResourceId());
				request.setAttribute("DefineList_" + typeBean.getTypeId() + "_" + resourceBean.getResourceId(),
						defineList);
			}
		}

		// 角色权限
		AdminPrivilegeInfoBusiness privilegeBusiness = new AdminPrivilegeInfoBusiness();
		List<AdminPrivilegeInfoBean> privilegeList = new ArrayList<AdminPrivilegeInfoBean>();
		if (roleId > 0) {
			privilegeList = privilegeBusiness.getAdminPrivilegeInfoListByRoleId(roleId);
		}
		HashSet<String> privilegeSet = new HashSet<String>();
		for (AdminPrivilegeInfoBean privilegeBean : privilegeList) {
			privilegeSet.add(privilegeBean.getResourceId() + "_" + privilegeBean.getOperationId());
		}
		request.setAttribute("PrivilegeSet", privilegeSet);

		AdminOperationInfoBusiness operationBusiness = new AdminOperationInfoBusiness();
		request.setAttribute("OperationMap", operationBusiness.getAdminOperationNameAllMap());

		String path = AdminConfig.PAGE_PATH + "AdminRoleInfoManage.jsp";
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
		AdminRoleInfoBean bean = new AdminRoleInfoBean();
		try {
			bean = BeanKit.request2Bean(request, AdminRoleInfoBean.class);
			AdminRoleInfoBusiness business = new AdminRoleInfoBusiness();

			int roleId = 0;
			int result = 0;
			if (bean.getRoleId() > 0) {
				if (!adminHelper.checkPermission(AdminResource.ADMIN_ROLE, OperationConfig.UPDATE)) {
					return;
				}

				result = business.updateAdminRoleInfo(bean);
				roleId = bean.getRoleId();
			} else {
				if (!adminHelper.checkPermission(AdminResource.ADMIN_ROLE, OperationConfig.CREATE)) {
					return;
				}

				result = business.addAdminRoleInfo(bean);
				roleId = result;
			}

			// 更新角色权限
			AdminPrivilegeInfoBusiness privilegeBusiness = new AdminPrivilegeInfoBusiness();
			List<AdminPrivilegeInfoBean> privilegeList = new ArrayList<AdminPrivilegeInfoBean>();
			String[] privilegeArray = ParamKit.getArrayParameter(request, "privilegeKey");
			if (privilegeArray != null) {
				for (String privilegeStr : privilegeArray) {
					String[] str = privilegeStr.split("_");
					AdminPrivilegeInfoBean privilegeBean = new AdminPrivilegeInfoBean();
					privilegeBean.setRoleId(roleId);
					privilegeBean.setResourceId(Integer.parseInt(str[0]));
					privilegeBean.setOperationId(Integer.parseInt(str[1]));

					privilegeList.add(privilegeBean);
				}
			}
			log.debug("[AUDIT] : (update role) userId : " + adminHelper.getAdminUserId() + " privilegeArray: "
					+ privilegeArray);
			privilegeBusiness.saveAdminPrivilegeInfo(roleId, privilegeList);

			if (result > 0) {
				HttpResponseKit.alertMessage(response, "处理成功", "/admin/AdminRoleInfoList.do");
			} else {
				HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
			}
		} catch (Exception e) {
			log.error("{}", e.toString());
		}
	}

}
