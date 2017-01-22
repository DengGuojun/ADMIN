package com.lpmas.admin.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.admin.bean.AdminMenuInfoBean;
import com.lpmas.admin.business.AdminMenuInfoBusiness;
import com.lpmas.admin.config.AdminConfig;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.util.JsonKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;

/**
 * Servlet implementation class AdminMenuInfoTreeList
 */
@WebServlet("/admin/AdminMenuInfoTreeList.do")
public class AdminMenuInfoTreeList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminMenuInfoTreeList() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int menuId = ParamKit.getIntParameter(request, "menuId", 0);
		String goAction = ParamKit.getParameter(request, "goAction", "");

		AdminMenuInfoBusiness business = new AdminMenuInfoBusiness();
		String treeStr = buildTree(menuId, business);

		if (goAction.equals("ajax")) {
			HttpResponseKit.printMessage(response, treeStr, HttpResponseKit.CT_JSON);
		} else {
			request.setAttribute("TreeStr", treeStr);

			String path = AdminConfig.PAGE_PATH + "AdminMenuInfoTreeList.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(path);
			rd.forward(request, response);
		}
	}

	private String buildTree(int menuId, AdminMenuInfoBusiness business) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<AdminMenuInfoBean> list = business.getAdminMenuInfoListByParentMenuId(menuId,
				AdminConfig.MENU_TYPE_DICTIONARY);

		for (int i = 0; i < list.size(); i++) {
			AdminMenuInfoBean bean = list.get(i);
			if (bean.getStatus() == Constants.STATUS_VALID) {
				Map<String, Object> map = new HashMap<String, Object>();

				map.put("id", bean.getMenuId());
				map.put("name", bean.getMenuName());

				List<AdminMenuInfoBean> childList = business.getAdminMenuInfoListByParentMenuId(bean.getMenuId(),
						AdminConfig.MENU_TYPE_DICTIONARY);

				boolean hasChild = !childList.isEmpty();

				map.put("size", childList.size());
				map.put("isParent", hasChild);
				map.put("open", false);

				result.add(map);
			}
		}
		return JsonKit.toJson(result);
	}
}
