package com.lpmas.declare.admin.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.declare.admin.bean.MajorInfoBean;
import com.lpmas.declare.admin.bean.MajorTypeBean;
import com.lpmas.declare.admin.business.MajorInfoBusiness;
import com.lpmas.declare.admin.business.MajorTypeBusiness;
import com.lpmas.declare.admin.config.DeclareAdminConfig;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.MapKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.ParamKit;

/**
 * Servlet implementation class MajorInfoList
 */
@WebServlet("/declare/admin/MajorInfoList.do")
public class MajorInfoList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MajorInfoList() {
		super();
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
		// 分页信息
		int pageNum = ParamKit.getIntParameter(request, "pageNum", DeclareAdminConfig.DEFAULT_PAGE_NUM);
		int pageSize = ParamKit.getIntParameter(request, "pageSize", DeclareAdminConfig.DEFAULT_PAGE_SIZE);
		PageBean pageBean = new PageBean(pageNum, pageSize);

		// 查询条件参数
		HashMap<String, String> condMap = new HashMap<String, String>();
		
		String majorName = ParamKit.getParameter(request, "majorName", "").trim();
		if (StringKit.isValid(majorName)) {
			condMap.put("majorName", majorName);
		}

		String status = ParamKit.getParameter(request, "status", String.valueOf(Constants.STATUS_VALID)).trim();
		if (StringKit.isValid(status)) {
			condMap.put("status", status);
		}
		// 初始化业务类
		MajorInfoBusiness business = new MajorInfoBusiness();
		PageResultBean<MajorInfoBean> result = business.getMajorInfoPageListByMap(condMap, pageBean);
		
		//获取节目分类的map majorId -- majorTypeName
		MajorTypeBusiness majorTypeBusiness = new MajorTypeBusiness() ;
		List<MajorTypeBean> list  = majorTypeBusiness.getMajorTypeAllList() ;
		Map<Integer,String> majorTypeMap = new HashMap<Integer,String>() ;
		for(MajorTypeBean bean : list){
			majorTypeMap.put(bean.getMajorId(), bean.getMajorName()) ;
		}
		// 页面数据绑定
		request.setAttribute("majorTypeMap", majorTypeMap); 
		request.setAttribute("MajorInfoList", result.getRecordList());
		pageBean.init(pageNum, pageSize, result.getTotalRecordNumber());
		request.setAttribute("PageResult", pageBean);
		request.setAttribute("CondList", MapKit.map2List(condMap));

		// 页面转发
		RequestDispatcher rd = request.getRequestDispatcher(DeclareAdminConfig.PAGE_PATH + "MajorInfoList.jsp");
		rd.forward(request, response);
	}
}
