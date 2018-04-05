package com.admission.web.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLConnection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.admission.util.Profile;

/**
 * Servlet implementation class ValidateCodeImageServlet
 */
public class ResourceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResourceServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		String uri = req.getRequestURI();
		int pos = uri.lastIndexOf("/");
		
		String filename = uri.substring(pos + 1);
		File file = null;

		file = new File(Profile.getResourcePath(), filename);
		
		
		if(!file.exists()) {
			res.setStatus(404);
			return;
		}
		
		res.setContentType(URLConnection.guessContentTypeFromName(filename));
		
		FileInputStream fis = new FileInputStream(file);
		org.apache.commons.io.IOUtils.copy(fis, res.getOutputStream());
	    res.flushBuffer();
	    fis.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	
}
