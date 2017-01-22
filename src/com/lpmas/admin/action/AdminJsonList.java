package com.lpmas.admin.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.admin.bean.AdminResourceInfoBean;
import com.lpmas.admin.business.AdminResourceInfoBusiness;
import com.lpmas.framework.util.JsonKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;

/**
 * Servlet implementation class AdminJsonList
 */
@WebServlet("/admin/AdminJsonList.do")
public class AdminJsonList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminJsonList() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		String goAction = ParamKit.getParameter(request, "goAction", "");
		if (goAction.equals("resourceList")) {
			displayResourceListByType(request, response);
		}
	}

	private void displayResourceListByType(HttpServletRequest request, HttpServletResponse response) {
		int typeId = ParamKit.getIntParameter(request, "typeId", 0);
		AdminResourceInfoBusiness business = new AdminResourceInfoBusiness();
		List<AdminResourceInfoBean> list = business.getAdminResourceInfoListByType(typeId);
		HttpResponseKit.printMessage(response, JsonKit.toJson(list));
	}

}
