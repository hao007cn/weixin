package com.senyint.wx.admin.action;

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

import com.senyint.common.util.DateUtils;
import com.senyint.common.web.ajax.GridParam;
import com.senyint.common.web.ajax.GridResult;
import com.senyint.wx.admin.service.ArticleService;
import com.senyint.wx.admin.service.ArticleTypeService;
import com.senyint.wx.common.dao.ArticleDao;
import com.senyint.wx.common.dao.ArticleTypeDao;
import com.senyint.wx.common.entity.ArticleType;
import com.senyint.wx.common.entity.WikisType;
import com.senyint.wx.common.web.Constants;
import com.senyint.wx.common.web.Operate;

@Controller
@RequestMapping(value="/article")
public class ArticleAction extends SupportAction {
	@Autowired
	private ArticleDao articleDao;
	
	@Autowired
	private ArticleTypeDao articleTypeDao;
	
	@Autowired
	private ArticleService articleService;
	
	@Operate(desc = "文章列表")
	@RequestMapping()
	public String index(Model model,HttpServletRequest request){
		//文章管理默认几级菜单管理
		List<ArticleType> articleTypeList = articleService.getArticleList(Constants.PARENT_FLAG_DEFAUT);
		model.addAttribute("articleTypeList", articleTypeList);
		return "article";
	}
	
//	
//	/**
//	 * 百科列表
//	 * @param gridParam
//	 * @param request
//	 * @param response
//	 * @param session
//	 * @return
//	 */
//	@Operate(desc = "文章列表")
//	@RequestMapping(value = "/lodePage", method = RequestMethod.POST)
//	@ResponseBody
//	public GridResult lodePage (GridParam gridParam, HttpServletRequest request, HttpServletResponse response,
//			HttpSession session){
//		Conjunction con = Restrictions.and();
//		//标题
//		String title = request.getParameter("title");
//		//开始日期
//		String startDate = request.getParameter("startDate");
//		//结束日期
//		String endDate = request.getParameter("endDate");
//		//百科类型
//		//String wikisCategoryId = request.getParameter("wikisCategoryId");
//		String articleTypeId = request.getParameter("articleTypeId");
//		
//		if(StringUtils.isNotBlank(title)){
//			con.add(Restrictions.eq("title", title));
//		}
//		if(StringUtils.isNotBlank(articleTypeId)){
//			ArticleType articleType =  articleTypeDao.findByPoid(Integer.parseInt(articleTypeId));
//			//con.add(Restrictions.eq("wikisType",  articleType));
//			con.add(Restrictions.eq("articleType",  articleType));
//			
//		}else{
////			WikisType wikisType =  new WikisType();
////			wikisType.setDeleteFlg(false);
////			con.add(Restrictions.eq("wikisType",  wikisType));
//		}
//		if(StringUtils.isNotBlank(startDate)){
//			con.add(Restrictions.ge("publishdate", DateUtils.strToDate(startDate, "")));
//		}
//		if(StringUtils.isNotBlank(endDate)){
//			con.add(Restrictions.le("publishdate",DateUtils.strToDate(endDate, "")));
//		}
//		con.add(Restrictions.eq("deleteFlg",false));
//		//查询总条数
//		int records=wikisDao.fetchCountByCriteria(false, con);
//		List<Wikis> wikisList = wikisDao.findByCriteria(gridParam.getStartRow(), gridParam.getRows(), "publishdate desc", false, con);
//		return gridResult(gridParam,records,wikisList);
//	}
	
//	
//	//保存
//	@RequestMapping(value = "save",method = RequestMethod.POST)
//	public String save(Wikis wikis,HttpServletRequest request, HttpSession session,RedirectAttributes redirectAttributes){
//		//提交保存
//		if(wikis.getPoid() == null){
//			wikis.setPublishdate(ArgumentUtil.getSysDate());
//			wikis.setPublisher(getLoginUsername());
//			WikisType wikisType =  wikisTypeDao.findByPoid(wikis.getWikisTypeId());
//			wikis.setWikisType(wikisType);
//			wikisDao.makePersistent(wikis);
//		}else{
//			//修改操作
//			Wikis tempWikis = new Wikis();
//			tempWikis =  wikisDao.findByPoid(wikis.getPoid());
//			tempWikis.setContent(wikis.getContent());
//			tempWikis.setPublisher(getLoginUsername());
//			tempWikis.setRemark(wikis.getRemark());
//			tempWikis.setTitle(wikis.getTitle());
//			//tempWikis.setWikisTypeId(wikis.getWikisTypeId());
//			WikisType wikisType = new WikisType();
//			wikisType.setPoid(wikis.getWikisTypeId());
//			tempWikis.setWikisType(wikisType);
//			wikisDao.makePersistent(tempWikis);
//		}
//		redirectAttributes.addFlashAttribute("message","保存成功");
//		return "redirect:/admin/wikis";
//	}
//	
//	/**
//	 * 根据id获取一条数据
//	 * @param poid
//	 * @param request
//	 * @param session
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping(value = "getdata", method = RequestMethod.POST)
//	public Wikis getData(String poid, HttpServletRequest request,
//			HttpSession session){
//		Wikis wikis = wikisDao.findByPoid(Integer.parseInt(poid));
//		return wikis;
//	}
//	
//	/**删除一条记录
//	 * @param poid
//	 * @param request
//	 * @param session
//	 * @param redirectAttributes
//	 * @return
//	 */
//	@RequestMapping(value = "del" , method = RequestMethod.POST)
//	public String delete(Integer poid, HttpServletRequest request,
//			HttpSession session, RedirectAttributes redirectAttributes){
//			Wikis wikis = wikisDao.findByPoid(poid);
//			wikis.setDeleteFlg(true);
//			wikisDao.makePersistent(wikis);
//			redirectAttributes.addFlashAttribute("message", "删除成功");
//			return "redirect:/admin/wikis";
//	}
//	
}
