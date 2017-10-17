package com.senyint.wx.admin.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.senyint.common.web.ajax.GridParam;
import com.senyint.common.web.ajax.GridResult;
import com.senyint.wx.common.dao.JobTitleDao;
import com.senyint.wx.common.entity.JobTitle;
import com.senyint.wx.common.web.Operate;

@Controller
@RequestMapping(value = "/jobTitle")
public class JobTitleAction extends SupportAction{
	
	@Autowired
	private JobTitleDao jobTitleDao;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(){
		return "jobTitle";
	}
	
	/**
	 * 
	 * @param gridParam
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@Operate( desc = "参数数据加载页")
	@ResponseBody
	@RequestMapping(value = "/loadpage" , method = RequestMethod.POST)
	public GridResult loadPage(GridParam gridParam, HttpServletRequest request,
			HttpServletResponse response, HttpSession session){
		
		Conjunction con =  Restrictions.and();
		String jobTitleName = request.getParameter("name");
		if(StringUtils.isNotBlank(jobTitleName)){
			con.add(Restrictions.like("name", jobTitleName, MatchMode.ANYWHERE));
		}
		
		con.add(Restrictions.eq("deleteFlg", false));
		int records = jobTitleDao.fetchCountByCriteria(false, con);
		List<JobTitle> argyList = jobTitleDao.findByCriteria(gridParam.getStartRow(), gridParam.getRows(), "name desc", false,con); 
		return gridResult(gridParam,records,argyList);
		
	}


	//保存
	@RequestMapping(value = "/save",method = RequestMethod.POST)
	public String save(JobTitle jobTitle,HttpServletRequest request, HttpSession session,RedirectAttributes redirectAttributes){
		//提交保存
		if(jobTitle.getPoid() == null){
			jobTitleDao.makePersistent(jobTitle);
		}else{
			//修改操作
			JobTitle titleTemp = new JobTitle();
			titleTemp =  jobTitleDao.findByPoid(jobTitle.getPoid());
			titleTemp.setEnabled(jobTitle.isEnabled());
			titleTemp.setFee(jobTitle.getFee());
			titleTemp.setName(jobTitle.getName());
			jobTitleDao.makePersistent(titleTemp);
		}
		redirectAttributes.addFlashAttribute("message","保存成功");
		return "redirect:/admin/jobTitle";
	}
	
	/**
	 * 根据id获取一条数据
	 * @param poid
	 * @param request
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getdata", method = RequestMethod.POST)
	public JobTitle getData(String poid, HttpServletRequest request,
			HttpSession session){
		JobTitle jobTitle = jobTitleDao.findByPoid(Integer.parseInt(poid));
		return jobTitle;
	}
	
	/**删除一条记录
	 * @param poid
	 * @param request
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/del" , method = RequestMethod.POST)
	public String delete(Integer poid, HttpServletRequest request,
			HttpSession session, RedirectAttributes redirectAttributes){
			JobTitle jobTitle = jobTitleDao.findByPoid(poid);
			jobTitle.setDeleteFlg(true);
			jobTitleDao.makePersistent(jobTitle);
			redirectAttributes.addFlashAttribute("message", "删除成功");
			return "redirect:/admin/jobTitle";
	}
	
	
	
	
	
	
	public JobTitleDao getJobTitleDao() {
		return jobTitleDao;
	}


	public void setJobTitleDao(JobTitleDao jobTitleDao) {
		this.jobTitleDao = jobTitleDao;
	}
	
	
}
