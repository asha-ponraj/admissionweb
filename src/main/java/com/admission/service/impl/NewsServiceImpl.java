package com.admission.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.admission.dto.PageInfo;
import com.admission.entity.News;
import com.admission.service.NewsService;
import com.admission.util.TimeUtil;

import com.admission.dao.NewsDao;

@Transactional(propagation=Propagation.REQUIRED)
@Service("newsService")
public class NewsServiceImpl implements NewsService {

	@Autowired
	private NewsDao newsDao;

	@Override
	public News createNews(News news) throws Exception {
		News tn = newsDao.findByProperty("title", news.getTitle());
		if(tn != null)
			throw new Exception("同标题的新闻已经存在了");
		
		news.setCreateTime(TimeUtil.getCurTime());
		if(news.isTop())
			news.setTopTime(news.getCreateTime());
		
		int seq = (int)newsDao.queryMax("seq") + 1;
		news.setSeq(seq);
		
		newsDao.save(news);
		
		return news;
	}

	@Override
	public News updateNews(News news) throws Exception {
		News tn = newsDao.findById(news.getId());
		if(tn == null)
			throw new Exception("新闻不存在");
		
		tn.setTitle(news.getTitle());
		tn.setContent(news.getContent());
		tn.setSeq(news.getSeq());
		tn.setTop(news.isTop());
		if(news.isTop())
			tn.setTopTime(TimeUtil.getCurTime());
		else
			tn.setTopTime(null);
		
		newsDao.saveOrUpdate(tn);
		
		return tn;
	}

	@Override
	public void deleteNews(int id) throws Exception {
		News tn = newsDao.findById(id);
		if(tn == null)
			throw new Exception("新闻不存在");
		
		newsDao.delete(tn);
	}

	@Override
	public News findNews(int id) throws Exception {
		return newsDao.findById(id);
	}

	@Override
	public List<News> findTopNews() throws Exception {
		return newsDao.findTop();
	}

	@Override
	public List<News> findNews(PageInfo pageInfo) {
		return newsDao.findAll(pageInfo);
	}
	
}
