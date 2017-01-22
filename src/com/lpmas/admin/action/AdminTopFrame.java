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

import com.lpmas.admin.bean.AdminMenuInfoBean;
import com.lpmas.admin.business.AdminUserHelper;
import com.lpmas.admin.cache.AdminMenuInfoCache;
import com.lpmas.admin.cache.AdminPrivilegeInfoCache;
import com.lpmas.admin.config.AdminConfig;
import com.lpmas.framework.config.Constants;

/**
 * Servlet implementation class AdminTopFrame
 */
@WebServlet("/admin/AdminTopFrame.do")
public class AdminTopFrame extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(AdminTopFrame.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminTopFrame() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminMenuInfoCache cache = new AdminMenuInfoCache();
		List<AdminMenuInfoBean> rootList = cache.getAdminMenuInfoListByParentMenuCode(AdminConfig.MENU_ROOT_CODE);
		List<AdminMenuInfoBean> rList = new ArrayList<AdminMenuInfoBean>();

		AdminUserHelper userHelper = new AdminUserHelper(request, response);

		AdminPrivilegeInfoCache privilegeCache = new AdminPrivilegeInfoCache();
		HashSet<String> privilegeSet = privilegeCache.getAdminPrivilegeSetByUserId(userHelper.getAdminUserId());

		for (AdminMenuInfoBean bean : rootList) {
			if (bean.getStatus() == Constants.STATUS_VALID && bean.getIsDisplay() == Constants.STATUS_VALID) {
				if (userHelper.isSuperAdminUser() || hasPrivilege(bean.getMenuId(), privilegeSet, cache)) {
					rList.add(bean);
				}
			}
		}

		request.setAttribute("MenuList", rList);

		String path = AdminConfig.PAGE_PATH + "AdminTopFrame.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		// TODO Auto-generated method stub
	}

	private boolean hasPrivilege(int menuId, HashSet<String> privilegeSet, AdminMenuInfoCache cache) {
		List<AdminMenuInfoBean> list = cache.getAdminMenuInfoListByParentMenuId(menuId);
		for (AdminMenuInfoBean bean : list) {
			if (bean.getStatus() == Constants.STATUS_VALID && bean.getIsDisplay() == Constants.STATUS_VALID) {
				if (bean.getMenuType() == AdminConfig.MENU_TYPE_DICTIONARY) {
					if (hasPrivilege(bean.getMenuId(), privilegeSet, cache)) {
						return true;
					}
				} else {
					if (privilegeSet.contains(bean.getMenuPrivilege())) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
