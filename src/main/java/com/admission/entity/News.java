package com.admission.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import com.admission.util.TimeUtil;

@Entity
@Table(name = "t_news")
@XmlRootElement
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
public class News  extends BaseEntity {
	private static final long serialVersionUID = 2086040663326998265L;

	private Integer id;
	private String title;
	private String content;
	private boolean top;
	private String topStr;
	private int seq;
	private Timestamp createTime;
	private String createTimeStr;
	private Timestamp topTime;
	private String topTimeStr;
	
	public News() {
		
	}

	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "content")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "top")
	public boolean isTop() {
		return top;
	}

	public void setTop(boolean top) {
		this.top = top;
		setTopStr(top ? "置顶" : "");
	}

	@Column(name = "seq")
	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	@Column(name = "create_time")
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
		if(createTime != null)
			setCreateTimeStr(TimeUtil.getStringTimestamp(createTime));
	}

	@Column(name = "top_time")
	public Timestamp getTopTime() {
		return topTime;
	}

	public void setTopTime(Timestamp topTime) {
		this.topTime = topTime;
		if(topTime != null)
			setTopTimeStr(TimeUtil.getStringTimestamp(topTime));
		else
			setTopTimeStr("");
	}

	@Transient
	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	@Transient
	public String getTopTimeStr() {
		return topTimeStr;
	}

	public void setTopTimeStr(String topTimeStr) {
		this.topTimeStr = topTimeStr;
	}

	@Transient
	public String getTopStr() {
		return topStr;
	}

	public void setTopStr(String topStr) {
		this.topStr = topStr;
	}

}
