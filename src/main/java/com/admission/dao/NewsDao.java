package com.admission.dao;

import java.util.List;

import com.admission.dto.PageInfo;
import com.admission.entity.News;

public interface NewsDao extends AbstractDao<News> {
	public List<News> findTop();
	
	public List<News> findAll(PageInfo info);
}
