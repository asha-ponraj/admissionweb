package com.admission.web.filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLConnection;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.admission.util.Profile;

public class StyleFilter implements Filter {
	private static final Logger log = Logger.getLogger(StyleFilter.class);
	
	private ServletContext context;

	@Override
	public void init(FilterConfig cfg) throws ServletException {
		context = cfg.getServletContext();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		String uri = req.getRequestURI();
		int pos = uri.lastIndexOf("/");
		
		String filename = uri.substring(pos + 1);
		File file = null;

		file = new File(Profile.getStylePath(), filename);
		
		
		if(!file.exists()) {
			file = new File(context.getRealPath(uri.substring(Profile.CONTEXT_PATH.length() + 1)));
		}
		log.debug("Get style file: " + file.getAbsolutePath());
		
		if(file.exists()) {
			res.setContentType(URLConnection.guessContentTypeFromName(filename));
			
			FileInputStream fis = new FileInputStream(file);
			org.apache.commons.io.IOUtils.copy(fis, res.getOutputStream());
		    res.flushBuffer();
		    fis.close();
		} else {
			res.setStatus(404);
		}
	}

	@Override
	public void destroy() {

	}

}
