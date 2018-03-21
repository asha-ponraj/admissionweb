package com.admission.dto;

import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlRootElement;

import com.admission.entity.News;

@XmlRootElement
public class NewsOverviewTO {
	private Integer id;
	private String title;
	private boolean top;
	private String topStr;
	private int seq;
	private Timestamp createTime;
	private String createTimeStr;
	private Timestamp topTime;
	private String topTimeStr;
	
	public static NewsOverviewTO from(News news) {
		NewsOverviewTO newsTo = new NewsOverviewTO();
		
		newsTo.setId(news.getId());
		newsTo.setTitle(news.getTitle());
		newsTo.setTop(news.isTop());
		newsTo.setCreateTime(news.getCreateTime());
		newsTo.setCreateTimeStr(news.getCreateTimeStr());
		newsTo.setTopStr(news.getTopStr());
		newsTo.setTopTime(news.getTopTime());
		newsTo.setTopTimeStr(news.getTopTimeStr());
		
		return newsTo;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public boolean isTop() {
		return top;
	}
	public void setTop(boolean top) {
		this.top = top;
	}
	public String getTopStr() {
		return topStr;
	}
	public void setTopStr(String topStr) {
		this.topStr = topStr;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public Timestamp getTopTime() {
		return topTime;
	}
	public void setTopTime(Timestamp topTime) {
		this.topTime = topTime;
	}
	public String getTopTimeStr() {
		return topTimeStr;
	}
	public void setTopTimeStr(String topTimeStr) {
		this.topTimeStr = topTimeStr;
	}

}
