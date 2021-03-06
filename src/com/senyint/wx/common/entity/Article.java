package com.senyint.wx.common.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.senyint.core.entity.component.BaseEntity;

/**
 * @author wangchao
 *
 */
@org.hibernate.annotations.Cache(usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name= "article")
@org.hibernate.annotations.BatchSize(size = 20)
public class Article extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 内容
	 */
	private String content;
	
	/**
	 * 类别
	 */
	private ArticleType articleType;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 发布日期
	 */
	private Date publishdate;
	
	/**
	 * 发布人
	 */
	private String publisher;
	
	/**
	 * 类别id
	 */
	private Integer	articleTypeId;
	
	/**
	 * 类别名称
	 */
	private String articleTypeName;
	
	/**
	 * 页面传参用， 照片地址
	 * 
	 */
	private String picPath;

	
	@Column(name= "title_",length = 20)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name= "content_")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "arcticletypeId_")
	public ArticleType getArticleType() {
		return articleType;
	}

	public void setArticleType(ArticleType articleType) {
		this.articleType = articleType;
	}
	
	@Column(name = "remark_", length= 100 )
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Temporal(TemporalType.DATE)
	//@JsonSerialize(using = JsonDateFormat.class)
	@Column(name= "publishdate_" )
	public Date getPublishdate() {
		return publishdate;
	}

	public void setPublishdate(Date publishdate) {
		this.publishdate = publishdate;
	}

	@Column(name="publisher_")
	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	@Transient
	public Integer getArticleTypeId() {
		if(articleType != null){
			this.articleTypeId = this.articleType.getPoid();
		}
		return articleTypeId;
	}

	public void setArticleTypeId(Integer articleTypeId) {
		this.articleTypeId = articleTypeId;
	}

	@Transient
	public String getArticleTypeName() {
		if(articleType != null ){
			this.articleTypeName = articleType.getName();
		}
		return articleTypeName;
	}

	public void setArticleTypeName(String articleTypeName) {
		this.articleTypeName = articleTypeName;
	}
	
	@Column( name = "titleimg_" )
	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	
	
}