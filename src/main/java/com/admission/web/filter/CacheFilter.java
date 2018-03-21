package com.admission.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.admission.util.StrUtil;

/**
 * 该过滤器主要负责监视来自浏览器的请求，针对那些不能被浏览器缓存的请求，输出结果时强制IE浏览器取消缓存
 *
 * @author chenbaofeng
 */
public class CacheFilter implements Filter {
	public static final String COOKIEID = "qy_cookie_id";
	public static final String REMOTECLIENTIP = "remote_client_ip";
	
	/*记录所有不允许被缓存的请求扩展名*/
//	private Set<String> extensions = new HashSet<String>();

	@Override
	public void init(FilterConfig fc) throws ServletException {
		//提取配置参数extensions的值，将其中以逗号分隔的扩展名分析出来记录set
//		String strExts = fc.getInitParameter("extensions");
//		if(strExts != null && strExts.length() > 0) {
//			String [] exts = strExts.split(",");
//			if(exts != null && exts.length > 0) {
//				for(String te : exts) {
//					extensions.add(te);
//				}
//			}
//		}
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;

		String cookieId = null;
		boolean needAdd = true;
		Cookie[] cs = req.getCookies();
		if(cs != null) {
			for(Cookie tc : cs) {
				if(tc.getName().equalsIgnoreCase(COOKIEID)) {
					cookieId = tc.getValue();
					if(cookieId != null) {
						cookieId = cookieId.trim();
					}
					break;
				}
			}
		}
		
		if(cookieId != null && cookieId.length() > 0) {
			needAdd = false;
		} else {
			cookieId = StrUtil.getUUID();
		}
		req.setAttribute(COOKIEID, cookieId);
		
		// 将某些不可以被IE浏览器缓存的请求设置为缓存无效，强制要求IE对这些请求的结果取消缓存
//		String uri = req.getRequestURI();
//		int pos = uri.lastIndexOf(".");
//		if(pos != -1 && pos < uri.length() - 1) {
//			String ext = uri.substring(pos + 1);
//			if(extensions.contains(ext)) {
				res.setHeader("Expires", "Mon, 26 Jul 1997 05:00:00 GMT");
				res.setHeader("Cache-Control", "no-cache, must-revalidate");
//			}
//		}
		
		chain.doFilter(request, response);
		
		if(needAdd) {
			Cookie c = new Cookie(COOKIEID, cookieId);
			c.setMaxAge(Integer.MAX_VALUE);
			
			res.addCookie(c);
		}
	}

}
