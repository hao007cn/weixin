package com.senyint.wx.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.senyint.core.entity.component.BaseEntity;

/**
 * 新闻活动po
 * 
 * @author sunzhi
 *
 */
@org.hibernate.annotations.Cache(usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "news")
public class News extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1636920054520366518L;
	
	
	private String title;
	
	private String content;

	@Column(name = "title_")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name = "content_")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
