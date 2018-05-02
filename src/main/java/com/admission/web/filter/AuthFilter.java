package com.admission.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.admission.entity.User;
import com.admission.web.config.WebProfile;

public class AuthFilter implements Filter {
	private static String systemError = "{\"result\":\"please login first!\",\"data\":\"\"}";
	
	private String restUrlPrefix;

    /**
     * Default constructor. 
     */
    public AuthFilter() {
     
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		HttpSession session = req.getSession();
		User user = (User)session.getAttribute(WebProfile.SESSION_ADMINUSER);
		
		String uri = req.getRequestURI();
		boolean isRestRequest = false;
		if(uri.startsWith(restUrlPrefix))
			isRestRequest = true;
		
		if(user == null) {
			if(isRestRequest){
				response.getOutputStream().print(systemError);
			}
			else
				res.sendRedirect(req.getContextPath() + "/adminlogin.jsp");
		} else {
			chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		 restUrlPrefix = fConfig.getServletContext().getContextPath() + "/rest/";
	}

}
