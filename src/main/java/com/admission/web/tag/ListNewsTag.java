package com.admission.web.tag;

import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.admission.dto.PageInfo;
import com.admission.entity.News;
import com.admission.service.NewsService;

public class ListNewsTag extends TagSupport {
	private static Logger logger = Logger.getLogger(ListNewsTag.class);

	private static final long serialVersionUID = 1L;

	private String var;
	private WebApplicationContext webApplication;
	private Iterator<News> newsIterator;
	private List<News> newsList;
	
	public String getVar() {
		return var;
	}
	
	public void setVar(String var) {
		this.var = var;
	}

	@Override
	public int doStartTag() throws JspException {
		newsList = null;
		newsIterator = null;
		
		if(webApplication == null) {
			webApplication = ContextLoader.getCurrentWebApplicationContext();
		}
		
		try {
			NewsService newsService = (NewsService)webApplication.getBean("newsService");
			PageInfo pageInfo = new PageInfo();
			pageInfo.setPageNumber(1);
			pageInfo.setPageSize(0);
			newsList = newsService.findNews(pageInfo);
			if(newsList != null && newsList.size() > 0) {
				newsIterator = newsList.iterator();
			}
			
			if(newsIterator == null || !newsIterator.hasNext()) {
				return SKIP_BODY;
			} else {
				pageContext.setAttribute(var, newsIterator.next());
				return EVAL_BODY_INCLUDE;
			}
		} catch(Exception e) {
			logger.debug("Fetch news failed", e);
			return SKIP_BODY;
		}
	}

	@Override
	public int doAfterBody() throws JspException {
		if(newsIterator == null || !newsIterator.hasNext()) {
			return SKIP_BODY;
		} else {
			pageContext.setAttribute(var, newsIterator.next());
			return EVAL_BODY_AGAIN;
		}
	}

	@Override
	public int doEndTag() throws JspException {
		newsList = null;
		newsIterator = null;
		
		return EVAL_PAGE;
	}
}
