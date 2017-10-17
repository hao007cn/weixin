/**
 * IndexSetting.java
 * com.senyint.wx.common.entity
 * Function: TODO ADD FUNCTION
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2014年11月21日 		gjp
 *
 * Copyright (c) 2014, senyint All Rights Reserved.
*/
/**
* @File: IndexSetting.java
* @Package com.senyint.wx.common.entity
* @Description: TODO Describe the File
*
* @Company: senyint (Dalian) Co., Ltd
* @author   gjp
* @date     2014年11月21日
* @version  1.0
*/

package com.senyint.wx.common.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.senyint.core.entity.persistence.SurrogateKeyObject;

/**
 * ClassName:IndexSetting
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   gjp
 * @version  
 * @since    Ver 1.1
 * @Date	 2014年11月21日
 *
 * @see 	 
 */
/**
 * @Type: IndexSetting 首页轮播图片设置
 * @Description: TODO  Describe the Type
 *
 * @Company: senyint (Dalian) Co., Ltd
 * @author   gjp
 * @date     2014年11月21日
 * @version  1.0
 *
 */
@org.hibernate.annotations.Cache(usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name="index_setting")
@org.hibernate.annotations.BatchSize(size = 20)
public class IndexSetting extends SurrogateKeyObject{

	/**
	* @Fields serialVersionUID : TODO  Describe the Fields
	*/
	private static final long serialVersionUID = 1L;
	
	private String img_title;
	private String img_url;
	private String img_content;
	private String img_view;
	private Integer img_sequence;
	private String virtual_name;
	/**
	 * @return 图片标题
	 */
	@Column(name = "img_title_", length = 50)
	public String getImg_title() {
		return img_title;
	}
	/**
	 * @param 图片标题
	 */
	public void setImg_title(String img_title) {
		this.img_title = img_title;
	}
	/**
	 * @return 图片地址
	 */
	@Column(name = "img_url_")
	public String getImg_url() {
		return img_url;
	}
	/**
	 * @param 图片地址
	 */
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}
	/**
	 * @return 图片备注
	 */
	@Column(name = "img_content_", length = 200)
	public String getImg_content() {
		return img_content;
	}
	/**
	 * @param 图片备注
	 */
	public void setImg_content(String img_content) {
		this.img_content = img_content;
	}
	
	/**
	 * @return the img_view
	 */
	@Column(name = "img_view_", length = 10)
	public String getImg_view() {
		return img_view;
	}
	/**
	 * @param img_view the img_view to set
	 */
	public void setImg_view(String img_view) {
		this.img_view = img_view;
	}
	/**
	/**
	 * @return 临时文件名
	 */
	@Column(name = "virtual_name_", length = 80)
	public String getVirtual_name() {
		return virtual_name;
	}
	/**
	 * @return the img_sequence
	 */
	@Column(name = "img_sequence_", length = 11)
	public Integer getImg_sequence() {
		return img_sequence;
	}
	/**
	 * @param img_sequence the img_sequence to set
	 */
	public void setImg_sequence(Integer img_sequence) {
		this.img_sequence = img_sequence;
	}
	/**
	 * @param 临时文件名
	 */
	public void setVirtual_name(String virtual_name) {
		this.virtual_name = virtual_name;
	}
	
	
}
