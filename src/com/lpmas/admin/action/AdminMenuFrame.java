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

import com.lpmas.admin.bean.AdminMenuInfoBean;
import com.lpmas.admin.business.AdminUserHelper;
import com.lpmas.admin.cache.AdminMenuInfoCache;
import com.lpmas.admin.cache.AdminPrivilegeInfoCache;
import com.lpmas.admin.config.AdminConfig;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.web.ParamKit;

/**
 * Servlet implementation class AdminMenuFrame
 */
@WebServlet("/admin/AdminMenuFrame.do")
public class AdminMenuFrame extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminMenuFrame() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int menuId = ParamKit.getIntParameter(request, "menuId", -1);
		AdminMenuInfoCache cache = new AdminMenuInfoCache();
		List<AdminMenuInfoBean> list = cache.getAdminMenuInfoListByParentMenuId(menuId,
				AdminConfig.MENU_TYPE_DICTIONARY);
		AdminUserHelper userHelper = new AdminUserHelper(request, response);

		AdminPrivilegeInfoCache privilegeCache = new AdminPrivilegeInfoCache();
		HashSet<String> privilegeSet = privilegeCache.getAdminPrivilegeSetByUserId(userHelper.getAdminUserId());
		List<AdminMenuInfoBean> mList = new ArrayList<AdminMenuInfoBean>();// 判断权限后的菜单list
		for (AdminMenuInfoBean bean : list) {
			if (bean.getStatus() == Constants.STATUS_VALID && bean.getIsDisplay() == Constants.STATUS_VALID) {// 判断目录状态和是否显示
				List<AdminMenuInfoBean> itemList = cache.getAdminMenuInfoListByParentMenuId(bean.getMenuId(),
						AdminConfig.MENU_TYPE_ITEM);
				List<AdminMenuInfoBean> iList = new ArrayList<AdminMenuInfoBean>();// 判断权限后的子菜单list
				for (AdminMenuInfoBean itemBean : itemList) {
					if (itemBean.getStatus() == Constants.STATUS_VALID && itemBean.getIsDisplay() == Constants.STATUS_VALID) {// 判断菜单状态和是否显示
						if (userHelper.isSuperAdminUser() || privilegeSet.contains(itemBean.getMenuPrivilege())) {
							iList.add(itemBean);
						}
					}
				}

				if (iList.size() > 0) {
					request.setAttribute("MenuItemList_" + bean.getMenuId(), iList);
					mList.add(bean);
				}
			}
		}
		request.setAttribute("MenuRootList", mList);

		String path = AdminConfig.PAGE_PATH + "/AdminMenuFrame.jsp";
		RequestDispatcher rd = this.getServletContext().getRequestDispatcher(path);
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

}
