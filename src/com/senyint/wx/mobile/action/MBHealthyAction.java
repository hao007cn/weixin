package com.senyint.wx.mobile.action;

import java.util.List;

import javax.persistence.JoinColumn;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.senyint.wx.common.dao.WikisDao;
import com.senyint.wx.common.dao.WikisTypeDao;
import com.senyint.wx.common.entity.Wikis;
import com.senyint.wx.common.entity.WikisType;
import com.senyint.wx.common.web.Operate;
import com.senyint.wx.mobile.utils.HtmlRegexpUtil;

@Controller
@RequestMapping(value = "/healthy")
public class MBHealthyAction {
	@Autowired
	private WikisDao wikisDao;
	
	@Autowired
	private WikisTypeDao wikisTypeDao;
	
	/**
	 * 获取健康百科类别列表
	 * @param model
	 * @return
	 */
	@Operate(desc="健康百科-首页")
	@RequestMapping( method = RequestMethod.GET)
	public String getWikisTypeList(Model model){
		//设置查询
		Conjunction con = Restrictions.and();
		con.add(Restrictions.eq("enabled",  true));
		con.add(Restrictions.eq("deleteFlg",false));
		List<WikisType> healthyTypeList=  wikisTypeDao.findByCriteria(0, 1000, "sort acs",false,con); 
		model.addAttribute("healthyTypeList", healthyTypeList);
		if(healthyTypeList.size()==0){
			WikisType wikisType = new WikisType();
			wikisType.setEnabled(true);
			model.addAttribute("wikisType", wikisType);
		}
		return "healthy";
	}
	
	/**
	 * 健康百科类别查找健康百科列表
	 * @param id
	 * @param model
	 * @param request
	 * @return
	 */
	@Operate(desc="健康百科-列表页")
	@RequestMapping( value = "/healthylist")
	public String gethealthyListByhealthyTypeId(String id ,Model model ,HttpServletRequest request){
		WikisType wikisType = wikisTypeDao.findByPoid(Integer.parseInt(id));
		Conjunction con = Restrictions.and();
		con.add(Restrictions.eq("wikisType",  wikisType));
		con.add(Restrictions.eq("deleteFlg",  false));
		int datasum= wikisDao.fetchCountByCriteria(false, con);
		String healthyCategoryName = wikisType.getName();
		model.addAttribute("healthyCategoryName", healthyCategoryName);
		model.addAttribute("datasum", datasum);
		model.addAttribute("id", id);
		return "healthyList";
	}
	
	/**
	 * 百科下拉分页加载		
	* @param model
	* @param response
	* @param request
	* @param session
	* @param redirectAttributes
	* @return
	 */
	@Operate(desc="健康百科-列表页-下拉分页")
	@ResponseBody
	@RequestMapping(value="/append",method = RequestMethod.POST)
	public String appendDate(ModelMap model,HttpServletResponse response,HttpServletRequest request,
			HttpSession session, RedirectAttributes redirectAttributes,String id) {
		WikisType wikisType = wikisTypeDao.findByPoid(Integer.parseInt(id));
		Conjunction con = Restrictions.and();
		con.add(Restrictions.eq("wikisType",  wikisType));
		con.add(Restrictions.eq("deleteFlg",  false));
		String pageNum= request.getParameter("pageNum");
		List< Wikis> healthyList = wikisDao.findByCriteria(Integer.parseInt(pageNum),10,"publishdate desc",false,con);
		StringBuffer outhtml= new StringBuffer("");
		if(healthyList != null && healthyList.size() > 0){
			for(Wikis wikis : healthyList){
				//过滤html标签 同时去除换行符
				String tempContent = "";
				if(wikis.getContent()!=null){
					 tempContent= HtmlRegexpUtil.filterHtml(wikis.getContent()).replaceAll("\r|\n", "").trim();
				}
				
				//截取
				int index = (tempContent.length() <= 20 ?  tempContent.length() : 20) ;
				String content = null;
				if(index ==20){
					 content = tempContent.substring(0,index)+ ".  .  .";
				}else{
					 content = tempContent.substring(0,index);
				}
				wikis.setContent(content);
			}
			
		
		
		for(Wikis wikis :healthyList){
			outhtml.append("<a  href=\"" + request.getContextPath() + "/healthy/showHealthy?id=" + wikis.getPoid() +"\" class = list-group-item ");
			outhtml.append("<table>");
			outhtml.append("<tr> <td colspan=\"2\">");
			outhtml.append("<div style=\"width: 80px\">");
//			outhtml.append("<img src=\"static/image/bs.jpg\" class=\"img-rounded\" width=\"100%\" />");
			outhtml.append("</div>");
			outhtml.append("</td>");
			outhtml.append("<td>");
			outhtml.append("<div style=\"margin: 10px; font-weight: 100;\">" + wikis.getTitle() + "</div>");
			outhtml.append("<div style=\"margin: 10px; font-weight: 100;\">");
			outhtml.append("<h4>");
			outhtml.append("<small>" + wikis.getContent() + "</small>");
			outhtml.append("</div>");
			outhtml.append("</td>");
			outhtml.append("</tr>");
			outhtml.append("</table>");
			outhtml.append("</a>");
			outhtml.append("");
			outhtml.append("");
			outhtml.append("");
		}
	}
		return outhtml.toString();
	}
	/**健康百科详细页
	 * @param id
	 * @param model
	 * @return
	 */
	@Operate(desc="健康百科-详细页")
	@RequestMapping( value = "showHealthy", method = RequestMethod.GET)
	public String showHealthy(String id, Model model){
		Wikis healthy = wikisDao.findByPoid( Integer.parseInt(id));
		healthy.setWikisCategory(healthy.getWikisType().getName());
		model.addAttribute("healthy", healthy);
		return "healthyWith";
	}
	
	
	public WikisDao getWikisDao() {
		return wikisDao;
	}

	public void setWikisDao(WikisDao wikisDao) {
		this.wikisDao = wikisDao;
	}

	public WikisTypeDao getWikisTypeDao() {
		return wikisTypeDao;
	}

	public void setWikisTypeDao(WikisTypeDao wikisTypeDao) {
		this.wikisTypeDao = wikisTypeDao;
	}
	
	
}
