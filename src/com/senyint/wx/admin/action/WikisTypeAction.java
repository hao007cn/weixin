package com.senyint.wx.admin.action;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.senyint.common.web.ajax.AjaxResult;
import com.senyint.wx.common.dao.WikisDao;
import com.senyint.wx.common.dao.WikisTypeDao;
import com.senyint.wx.common.entity.Wikis;
import com.senyint.wx.common.entity.WikisType;
import com.senyint.wx.common.web.Constants;
import com.senyint.wx.common.web.Operate;
@Controller
@RequestMapping(value="/wikisType")
public class WikisTypeAction extends SupportAction{
	
	@Autowired
	private WikisTypeDao wikisTypeDao;
	@Autowired
	private WikisDao wikisDao;
	/**
	 * 页面初始化
	 * @param model
	 * @param request
	 * @return
	 */
	@Operate(desc = "百科类型页面")
	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model, HttpServletRequest request){
		Conjunction con  =  Restrictions.and();
		con.add(Restrictions.eq("deleteFlg", false));
		List<WikisType> list =  wikisTypeDao.findByCriteria(0, 10000, "sort acs", false,con);
		model.addAttribute("wikisTypeList",list);
		return "wikisType";
	}
	
	
	/**
	 * 保存
	 * @param model
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@Operate(desc = "保存百科类型")
	@RequestMapping(value="/save", method= RequestMethod.POST)
	public String save(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes ){
		String[] ids = request.getParameterValues("poid");
		String[] names = request.getParameterValues("name");
		String[] sorts = request.getParameterValues("sort");
		String[] enableds = request.getParameterValues("enabled");
		String[] remarks = request.getParameterValues("remarks");
		String[] backcolors = request.getParameterValues("backcolor");
		String[] icons = request.getParameterValues("icon");
		
		 if(ids != null && ids.length > 0){
			 List<WikisType> WikisTypeList =  new ArrayList<WikisType>();
			 for(int i = 0 ; i < ids.length ; i ++){
				 if( StringUtils.isEmpty(names[i])){
					 continue;
				 }
				 String id = ids[i];
				 WikisType wikisType;
				 //判断是更新还是插入新增
				 if(StringUtils.isNotBlank(id)){  //更新
					 wikisType =  wikisTypeDao.findByPoid(Integer.parseInt(id));
					 if(wikisType == null){
						 wikisType =  new WikisType();
					 }
				 }else{
					 wikisType = new WikisType();
				 }
				 
				 //排序号
				 if(StringUtils.isEmpty(sorts[i])){
					 wikisType.setSort(0);
				 }else{
					 wikisType.setSort(Integer.parseInt(sorts[i]));
				 }
				
				 wikisType.setEnabled( Constants.SWITCH_SHOW.equals(enableds[i]));
				 wikisType.setName(names[i]);
				 wikisType.setBackColor(backcolors[i]);
				 wikisType.setIcon(icons[i]);
				 wikisType.setRemark(remarks[i]);
				 WikisTypeList.add(wikisType);
			 }
			 //保存
			 wikisTypeDao.makePersistent(WikisTypeList);
		 }
			redirectAttributes.addFlashAttribute("message","操作成功");	
			return	"redirect:/admin/wikisType";
	}
	
	
	/**
	 * 删除类别
	 * @param wikisId
	 * @param model
	 * @param request
	 * @return
	 */
	@Operate(desc="删除百科类型")
	@ResponseBody
	@RequestMapping(value="/delete")
	public AjaxResult delete(String id, RedirectAttributes redirectAttributes){
		if(StringUtils.isNotBlank(id)){
			String[] ids = id.split(",");
			List<WikisType> wikisTypeList = new ArrayList<WikisType>();
			for( String idTemp : ids){
				if( StringUtils.isNotBlank(idTemp) ){
					WikisType wikisType = wikisTypeDao.findByPoid(Integer.parseInt(idTemp));
					wikisType.setDeleteFlg(true);
					wikisTypeList.add(wikisType);
					Conjunction con = Restrictions.and();
					con.add(Restrictions.eq("wikisType", wikisType));
					List<Wikis> wikislist = wikisDao.findByCriteria(false, con);
					for(Wikis wikis : wikislist){
						wikis.setDeleteFlg(true);
					}
					wikisDao.makePersistent(wikislist);
				}
			}
			try {
				wikisTypeDao.makePersistent(wikisTypeList);
				//wikisTypeDao.remove(wikisTypeList);
				return ajaxSuccess("删除成功！");
			} catch (Exception e) {
				return ajaxFail("有百科信息关联，不可删除！！");
			}
		}else{
			return ajaxFail("请选择记录！");
		}
	}
	
	public WikisTypeDao getWikisTypeDao() {
		return wikisTypeDao;
	}

	public void setWikisTypeDao(WikisTypeDao wikisTypeDao) {
		this.wikisTypeDao = wikisTypeDao;
	}


	public WikisDao getWikisDao() {
		return wikisDao;
	}


	public void setWikisDao(WikisDao wikisDao) {
		this.wikisDao = wikisDao;
	}
	
}
