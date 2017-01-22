package com.lpmas.admin.filter;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashSet;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.admin.business.AdminUserHelper;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.util.SetKit;
import com.lpmas.framework.util.StringKit;

/**
 * Servlet Filter implementation class AdminAuthorityFilter
 */
public class AdminAuthorityFilter implements Filter {
	private static Logger log = LoggerFactory.getLogger(AdminAuthorityFilter.class);
	private static HashSet<String> excludeUriSet = null;
	private static String logonUrl = "";

	/**
	 * Default constructor.
	 */
	public AdminAuthorityFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		AdminUserHelper adminHelper = new AdminUserHelper(httpRequest, httpResponse);
		if (!excludeUriSet.contains(httpRequest.getRequestURI())) {
			if (!adminHelper.isAdminUserLogon()) {
				String returnUrl = httpRequest.getRequestURL().toString();
				if (StringKit.isValid(httpRequest.getQueryString())) {
					returnUrl = returnUrl + "?" + httpRequest.getQueryString();
				}
				returnUrl = URLEncoder.encode(returnUrl, Constants.ENCODING_UNICODE);
				httpResponse.sendRedirect(logonUrl + "?returnUrl=" + returnUrl);
				return;
			}
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// 不验证登录的Url
		String excludeUri = fConfig.getInitParameter("excludeUri");
		excludeUriSet = SetKit.string2Set(excludeUri, ";");

		// 登录跳转Url
		logonUrl = fConfig.getInitParameter("logonUrl");
	}

}
