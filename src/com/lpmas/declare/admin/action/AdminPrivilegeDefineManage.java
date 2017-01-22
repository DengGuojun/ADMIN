package com.lpmas.declare.admin.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.declare.admin.bean.AdminPrivilegeDefineBean;
import com.lpmas.declare.admin.bean.AdminResourceInfoBean;
import com.lpmas.declare.admin.business.AdminOperationInfoBusiness;
import com.lpmas.declare.admin.business.AdminPrivilegeDefineBusiness;
import com.lpmas.declare.admin.business.AdminResourceInfoBusiness;
import com.lpmas.declare.admin.config.DeclareAdminConfig;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;

/**
 * Servlet implementation class AdminPrivilegeDefineManage
 */
@WebServlet("/declare/admin/AdminPrivilegeDefineManage.do")
public class AdminPrivilegeDefineManage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminPrivilegeDefineManage() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int resourceId = ParamKit.getIntParameter(request, "resourceId", 0);
		List<AdminPrivilegeDefineBean> list = new ArrayList<AdminPrivilegeDefineBean>();
		if (resourceId > 0) {
			AdminPrivilegeDefineBusiness business = new AdminPrivilegeDefineBusiness();
			list = business.getAdminPrivilegeDefineListByResourceId(resourceId);
		}
		request.setAttribute("PrivilegeList", list);
		HashMap<Integer, AdminPrivilegeDefineBean> map = new HashMap<Integer, AdminPrivilegeDefineBean>();
		for (AdminPrivilegeDefineBean bean : list) {
			map.put(bean.getOperationId(), bean);
		}
		request.setAttribute("PrivilegeMap", map);

		AdminOperationInfoBusiness operBusiness = new AdminOperationInfoBusiness();
		request.setAttribute("OperationList", operBusiness.getAdminOperationInfoAllList());

		AdminResourceInfoBean resourceInfoBean = new AdminResourceInfoBean();
		if (resourceId > 0) {
			AdminResourceInfoBusiness resourceBusiness = new AdminResourceInfoBusiness();
			resourceInfoBean = resourceBusiness.getAdminResourceInfoByKey(resourceId);
		}
		request.setAttribute("ResourceInfo", resourceInfoBean);

		
		RequestDispatcher rd = request.getRequestDispatcher(DeclareAdminConfig.PAGE_PATH
				+ "AdminPrivilegeDefineManage.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {

		int resourceId = ParamKit.getIntParameter(request, "resourceId", 0);
		String[] operationIds = ParamKit.getArrayParameter(request, "operationId");
		
		if(resourceId == 0){
			HttpResponseKit.alertMessage(response, "请先选择资源", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		List<AdminPrivilegeDefineBean> list = new ArrayList<AdminPrivilegeDefineBean>();
		if (operationIds != null) {
			for (int i = 0; i < operationIds.length; i++) {
				AdminPrivilegeDefineBean bean = new AdminPrivilegeDefineBean();
				bean.setResourceId(resourceId);
				bean.setOperationId(Integer.parseInt(operationIds[i]));
				list.add(bean);
			}
		}

		AdminPrivilegeDefineBusiness business = new AdminPrivilegeDefineBusiness();
		boolean result = business.saveAdminPrivilegeDefine(resourceId, list);
		if (result) {
			HttpResponseKit.alertMessage(response, "处理成功", "/declare/admin/AdminPrivilegeDefineManage.do?resourceId="
					+ resourceId);
		} else {
			HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
		}

	}

}
