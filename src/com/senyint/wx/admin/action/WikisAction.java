package com.senyint.wx.admin.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.senyint.common.util.DateUtils;
import com.senyint.common.web.ajax.GridParam;
import com.senyint.common.web.ajax.GridResult;
import com.senyint.wx.common.dao.WikisDao;
import com.senyint.wx.common.dao.WikisTypeDao;
import com.senyint.wx.common.entity.Wikis;
import com.senyint.wx.common.entity.WikisType;
import com.senyint.wx.common.utils.ArgumentUtil;
import com.senyint.wx.common.web.Operate;

@Controller
@RequestMapping(value="/wikis")
public class WikisAction extends SupportAction {
	
	@Autowired
	private WikisDao wikisDao ;
	
	@Autowired
	private WikisTypeDao wikisTypeDao;
	
	//跳转页初始化
	@RequestMapping()
	public String index(Model model,HttpServletRequest request){
		//获取科室类别列表
		Conjunction con =  Restrictions.and();
		con.add(Restrictions.eq("deleteFlg",false));
		List<WikisType> wikisTypeList = wikisTypeDao.findByCriteria(false, con);
		model.addAttribute("wikisTypeList", wikisTypeList);
		return "wikis";
	}
	
	/**
	 * 百科列表
	 * @param gridParam
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@Operate(desc = "百科列表")
	@RequestMapping(value = "/lodePage", method = RequestMethod.POST)
	@ResponseBody
	public GridResult lodePage (GridParam gridParam, HttpServletRequest request, HttpServletResponse response,
			HttpSession session){
		Conjunction con = Restrictions.and();
		//标题
		String title = request.getParameter("title");
		//开始日期
		String startDate = request.getParameter("startDate");
		//结束日期
		String endDate = request.getParameter("endDate");
		//百科类型
		String wikisCategoryId = request.getParameter("wikisCategoryId");
		if(StringUtils.isNotBlank(title)){
			con.add(Restrictions.eq("title", title));
		}
		if(StringUtils.isNotBlank(wikisCategoryId)){
			WikisType wikisType =  wikisTypeDao.findByPoid(Integer.parseInt(wikisCategoryId));
			con.add(Restrictions.eq("wikisType",  wikisType));
			
		}else{
//			WikisType wikisType =  new WikisType();
//			wikisType.setDeleteFlg(false);
//			con.add(Restrictions.eq("wikisType",  wikisType));
		}
		if(StringUtils.isNotBlank(startDate)){
			con.add(Restrictions.ge("publishdate", DateUtils.strToDate(startDate, "")));
		}
		if(StringUtils.isNotBlank(endDate)){
			con.add(Restrictions.le("publishdate",DateUtils.strToDate(endDate, "")));
		}
		con.add(Restrictions.eq("deleteFlg",false));
		//查询总条数
		int records=wikisDao.fetchCountByCriteria(false, con);
		List<Wikis> wikisList = wikisDao.findByCriteria(gridParam.getStartRow(), gridParam.getRows(), "publishdate desc", false, con);
		return gridResult(gridParam,records,wikisList);
	}
	
	
	//保存
	@RequestMapping(value = "save",method = RequestMethod.POST)
	public String save(Wikis wikis,HttpServletRequest request, HttpSession session,RedirectAttributes redirectAttributes){
		//提交保存
		if(wikis.getPoid() == null){
			wikis.setPublishdate(ArgumentUtil.getSysDate());
			wikis.setPublisher(getLoginUsername());
			WikisType wikisType =  wikisTypeDao.findByPoid(wikis.getWikisTypeId());
			wikis.setWikisType(wikisType);
			wikisDao.makePersistent(wikis);
		}else{
			//修改操作
			Wikis tempWikis = new Wikis();
			tempWikis =  wikisDao.findByPoid(wikis.getPoid());
			tempWikis.setContent(wikis.getContent());
			tempWikis.setPublisher(getLoginUsername());
			tempWikis.setRemark(wikis.getRemark());
			tempWikis.setTitle(wikis.getTitle());
			//tempWikis.setWikisTypeId(wikis.getWikisTypeId());
			WikisType wikisType = new WikisType();
			wikisType.setPoid(wikis.getWikisTypeId());
			tempWikis.setWikisType(wikisType);
			wikisDao.makePersistent(tempWikis);
		}
		redirectAttributes.addFlashAttribute("message","保存成功");
		return "redirect:/admin/wikis";
	}
	
	/**
	 * 根据id获取一条数据
	 * @param poid
	 * @param request
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getdata", method = RequestMethod.POST)
	public Wikis getData(String poid, HttpServletRequest request,
			HttpSession session){
		Wikis wikis = wikisDao.findByPoid(Integer.parseInt(poid));
		return wikis;
	}
	
	/**删除一条记录
	 * @param poid
	 * @param request
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "del" , method = RequestMethod.POST)
	public String delete(Integer poid, HttpServletRequest request,
			HttpSession session, RedirectAttributes redirectAttributes){
			Wikis wikis = wikisDao.findByPoid(poid);
			wikis.setDeleteFlg(true);
			wikisDao.makePersistent(wikis);
			redirectAttributes.addFlashAttribute("message", "删除成功");
			return "redirect:/admin/wikis";
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
