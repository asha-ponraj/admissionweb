package com.admission.service;

import java.util.List;

import com.admission.dto.PageInfo;
import com.admission.entity.News;

public interface NewsService {
	public News createNews(News news) throws Exception;
	
	public News updateNews(News news) throws Exception;
	
	public void deleteNews(int id) throws Exception;
	
	public News findNews(int id) throws Exception;
	
	public List<News> findTopNews() throws Exception;
	
	public List<News> findNews(PageInfo pageInfo);
}
