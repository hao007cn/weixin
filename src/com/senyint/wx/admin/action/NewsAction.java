package com.senyint.wx.admin.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.senyint.common.web.ajax.GridParam;
import com.senyint.common.web.ajax.GridResult;
import com.senyint.wx.common.dao.NewsDao;
import com.senyint.wx.common.entity.News;
import com.senyint.wx.common.utils.ArgumentUtil;
import com.senyint.wx.common.web.Operate;

@Controller
@RequestMapping(value = "/news")
public class NewsAction extends SupportAction {
	@Autowired
	private NewsDao newsDao;

	/**
	 * 新聞消息跳轉頁面
	 * 
	 * @author gjp senyint (Dalian) Co., Ltd.
	 * 
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@Operate(desc = "新闻消息页面")
	@RequestMapping(method = RequestMethod.GET)
	public String index(ModelMap model, HttpServletRequest request,
			HttpSession session) {

		return "news";
	}

	@ResponseBody
	@RequestMapping(value = "loadpage", method = RequestMethod.POST)
	public GridResult loadPage(GridParam gridParam, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		Conjunction con = Restrictions.and();
		int records = newsDao.fetchCountByCriteria(false,con);
		List<News> list = newsDao.findByCriteria(gridParam.getStartRow(),
				gridParam.getRows(),"createDate desc", false,con);

		return gridResult(gridParam, records, list);

	}

	/**
	 * 保存方法
	 * 
	 * @author gjp senyint (Dalian) Co., Ltd.
	 * 
	 * @param news
	 * @param request
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@Operate(desc = "保存新闻消息")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(News news, HttpServletRequest request,
			HttpSession session, RedirectAttributes redirectAttributes) {
		if (news.getPoid() == null) {
			news.setCreateDate(ArgumentUtil.getSysDate());
			news.setCreateUserId(getLoginUserInfo().getPoid().toString());
			news.setCreateUserName(getLoginUserInfo().getName());
			newsDao.makePersistent(news);
		} else {
			News temp = newsDao.findByPoid(news.getPoid());
			temp.setContent(news.getContent());
			temp.setTitle(news.getTitle());
			newsDao.makePersistent(temp);
		}
		
		redirectAttributes.addFlashAttribute("message", "保存成功");
		return "redirect:/admin/news";
	}

	/**
	 * 根據id獲得一條數據 
	 * )
	 * 
	 * @author gjp senyint (Dalian) Co., Ltd.
	 * 
	 * @param poid
	 * @param request
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getdata", method = RequestMethod.POST)
	public News getData(Integer poid, HttpServletRequest request,
			HttpSession session) {
		News news = newsDao.findByPoid(poid);
		return news;
	}

	/**
	 * 刪除方法 
	 * 
	 * @author gjp senyint (Dalian) Co., Ltd.
	 * 
	 * @param poid
	 * @param request
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "del", method = RequestMethod.POST)
	public String delete(Integer poid, HttpServletRequest request,
			HttpSession session, RedirectAttributes redirectAttributes) {
		News news = newsDao.findByPoid(poid);
		newsDao.remove(news);
		redirectAttributes.addFlashAttribute("message", "删除成功");
		return "redirect:/admin/news";
	}
}
