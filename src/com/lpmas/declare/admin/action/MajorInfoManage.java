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

import com.lpmas.declare.admin.bean.MajorInfoBean;
import com.lpmas.declare.admin.bean.MajorTypeBean;
import com.lpmas.declare.admin.business.MajorInfoBusiness;
import com.lpmas.declare.admin.business.MajorTypeBusiness;
import com.lpmas.declare.admin.config.DeclareAdminConfig;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.framework.web.ReturnMessageBean;

/**
 * Servlet implementation class MajorInfoManage
 */
@WebServlet("/declare/admin/MajorInfoManage.do")
public class MajorInfoManage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(MajorInfoManage.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MajorInfoManage() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int majorId = ParamKit.getIntParameter(request, "majorId", 0);

		MajorInfoBean bean = new MajorInfoBean();
		MajorInfoBusiness business = new MajorInfoBusiness();
		MajorTypeBusiness majorTypeBusiness = new MajorTypeBusiness();
		if (majorId > 0) {
			bean = business.getMajorInfoByKey(majorId);
		} else {
			bean.setStatus(Constants.STATUS_VALID);
		}
		List<MajorTypeBean> majorTypeList = majorTypeBusiness.getMajorTypeAllList();
		request.setAttribute("MajorInfoBean", bean);
		request.setAttribute("MajorTypeList", majorTypeList);
		RequestDispatcher rd = this.getServletContext()
				.getRequestDispatcher(DeclareAdminConfig.PAGE_PATH + "/MajorInfoManage.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		MajorInfoBean bean = new MajorInfoBean();
		try {
			bean = BeanKit.request2Bean(request, MajorInfoBean.class);
			MajorInfoBusiness business = new MajorInfoBusiness();
			// ReturnMessageBean messageBean = business.verifyMajorType(bean);
			// if (StringKit.isValid(messageBean.getMessage())) {
			// HttpResponseKit.alertMessage(response, messageBean.getMessage(),
			// HttpResponseKit.ACTION_HISTORY_BACK);
			// return;
			// }
			int result = 0;

			if (bean.getMajorId() > 0) {
				ReturnMessageBean messageBean = business.verifyModifyMajorInfo(bean);
				if (StringKit.isValid(messageBean.getMessage())) {
					HttpResponseKit.alertMessage(response, messageBean.getMessage(),
							HttpResponseKit.ACTION_HISTORY_BACK);
					return;
				}
				result = business.updateMajorInfo(bean);
			} else {

				ReturnMessageBean messageBean = business.verifyAddMajorInfo(bean);
				if (StringKit.isValid(messageBean.getMessage())) {
					HttpResponseKit.alertMessage(response, messageBean.getMessage(),
							HttpResponseKit.ACTION_HISTORY_BACK);
					return;
				}
				result = business.addMajorInfo(bean);
			}

			if (result > 0) {
				HttpResponseKit.alertMessage(response, "处理成功", "/declare/admin/MajorInfoList.do");
			} else {
				HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
			}
		} catch (Exception e) {
			log.error("", e);
		}
	}

}
