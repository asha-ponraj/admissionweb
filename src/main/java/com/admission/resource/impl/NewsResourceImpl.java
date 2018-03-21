package com.admission.resource.impl;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.core.Context;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.admission.dto.DataGridTO;
import com.admission.dto.JsonResponse;
import com.admission.dto.NewsOverviewTO;
import com.admission.dto.PageInfo;
import com.admission.entity.News;
import com.admission.resource.NewsResource;
import com.admission.service.NewsService;

@WebService(endpointInterface = "com.admission.resource.NewsResource")
public class NewsResourceImpl implements NewsResource {
	private static Logger log = Logger.getLogger(NewsResourceImpl.class);

	@Context 
	private MessageContext context;

	@Autowired
	private NewsService newsService;

	@Override
	public JsonResponse createNews(News news) {
		JsonResponse res = new JsonResponse();
		
		try {
			news = newsService.createNews(news);
			res.setResult("ok");
			res.setData(NewsOverviewTO.from(news));
		} catch (Exception e) {
			log.debug("create news fail", e);
			res.setResult("添加新闻失败: " + e.getMessage());
		}

		return res;
	}

	@Override
	public JsonResponse updateNews(News news) {
		JsonResponse res = new JsonResponse();
		
		try {
			news = newsService.updateNews(news);
			res.setResult("ok");
			res.setData(NewsOverviewTO.from(news));
		} catch (Exception e) {
			log.debug("update news fail", e);
			res.setResult("编辑新闻失败: " + e.getMessage());
		}

		return res;
	}
	
	@Override
	public JsonResponse deleteNews(int id) {
		JsonResponse res = new JsonResponse();
		
		try {
			newsService.deleteNews(id);
			res.setResult("ok");
		} catch (Exception e) {
			log.debug("delete news fail", e);
			res.setResult("删除新闻失败: " + e.getMessage());
		}

		return res;
	}


	@Override
	public JsonResponse findNews(PageInfo info) {
		JsonResponse res = new JsonResponse();
		
		try {
			if(info == null)
				info = new PageInfo();
			
			List<News> newss = newsService.findNews(info);
			DataGridTO dgto = new DataGridTO();
			dgto.setTotal(info.getTotal());
			if(newss != null && newss.size() > 0) {
				for(News tn : newss) {
					dgto.getRows().add(NewsOverviewTO.from(tn));
				}
			}
			res.setResult("ok");
			res.setData(dgto);
		} catch (Throwable t) {
			log.debug("find application fail", t);
			res.setResult("查询新闻出错: " + t.getMessage());
		}
		return res;
	}

	@Override
	public JsonResponse getNews(int id) {
		JsonResponse res = new JsonResponse();
		
		try {
			News tn = newsService.findNews(id);
			if(tn == null) {
				res.setResult("新闻不存在");
			} else {
				res.setResult("ok");
				res.setData(tn);
			}
		} catch (Throwable t) {
			log.debug("set timespace fail", t);
			res.setResult("获取新闻错误: " + t.getMessage());
		}
		return res;
	}
}
