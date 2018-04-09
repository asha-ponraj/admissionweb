package com.admission.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.admission.entity.News;
import com.admission.service.NewsService;

public class NewsDetailTag extends TagSupport {
	private static Logger logger = Logger.getLogger(NewsDetailTag.class);

	private static final long serialVersionUID = 1L;

	private WebApplicationContext webApplication;
	
	private int newsId;
	private String var;
	
	public int getNewsId() {
		return newsId;
	}
	
	public void setNewsId(int newsId) {
		this.newsId = newsId;
	}
	
	public String getVar() {
		return var;
	}
	
	public void setVar(String var) {
		this.var = var;
	}
	
	@Override
	public int doStartTag() throws JspException {
		if(webApplication == null) {
			webApplication = ContextLoader.getCurrentWebApplicationContext();
		}
		
		try {
			NewsService newsService = (NewsService)webApplication.getBean("newsService");
			News tNews = newsService.findNews(newsId);
			if(tNews != null) {
				pageContext.setAttribute(var, tNews);
			} else {
				pageContext.setAttribute(var, null);
			}
			
			return EVAL_BODY_INCLUDE;
		} catch(Exception e) {
			logger.debug("Fetch news failed", e);
		}
		
		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		
		return EVAL_PAGE;
	}
}
