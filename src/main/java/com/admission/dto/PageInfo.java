package com.admission.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.admission.util.StrUtil;

@XmlRootElement
public class PageInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/*分页时的当前页数*/
	private int pageNumber = 1;
	
	/*分页时每页显示的记录数*/
	private int pageSize = 20;
	
	/*分页时的排序字段名*/
	private String sortName;
	
	/*分页时的排序顺序*/
	private String sortOrder = "asc";
	
	/*分页查询时的总页数*/
	private int total;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = StrUtil.trimString(sortName);
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = StrUtil.trimString(sortOrder);
	}
}
