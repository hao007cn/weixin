package com.senyint.wx.mobile.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.senyint.common.web.ajax.AjaxResult;
import com.senyint.wx.common.dao.AttentionDao;
import com.senyint.wx.common.dao.DoctorDao;
import com.senyint.wx.common.dao.HisAccessDao;
import com.senyint.wx.common.dao.OrderDao;
import com.senyint.wx.common.entity.Attention;
import com.senyint.wx.common.utils.ArgumentUtil;
import com.senyint.wx.common.web.Operate;

@Controller
@RequestMapping( value = "/attention")
public class MBAttentionAction extends MBSupportAction {
	
	@Autowired
	private AttentionDao attentionDao;
	
	@Autowired
	private DoctorDao doctorDao;
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private HisAccessDao hisAccessDao;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		Conjunction con = Restrictions.and();
		con.add(Restrictions.eq("user.poid", getLoginUserId()));
		
		int totalCount = attentionDao.fetchCountByCriteria(false, con);
		model.addAttribute("pageSum", totalCount);
		return "attentionList";
	}

	@RequestMapping(value="/list")
	public String list(ModelMap model, HttpServletRequest request) {
		String pageNum= request.getParameter("pageNum");
		
		Conjunction con = Restrictions.and();
		con.add(Restrictions.eq("user.poid", getLoginUserId()));
		
		List<Attention> list = attentionDao.findByCriteria(Integer.parseInt(pageNum), 10, "poid desc", false, con);
		
		if (list != null && list.size() > 0) {
			for (Attention att : list) {
				Integer patientCount = this.orderDao.findDoctorPatientCount(att.getDoctor().getPoid());
				att.getDoctor().setPatientCount(patientCount);
			}
		}
		
		model.addAttribute("attList", list);
		
		return "attentionAjaxList";
	}

	@Operate(desc="关注")
	@ResponseBody
	@RequestMapping(value="/do")
	public AjaxResult doAttend(Integer id) {
		
	/*	Conjunction con = Restrictions.and();
		con.add(Restrictions.eq("user.poid", getLoginUserId()));
		con.add(Restrictions.eq("doctor.poid", id));
		int count = attentionDao.fetchCountByCriteria(false, con);
		
		if (count <= 0) {
			ForegroundUser fuser = foregroundUserDao.findByPoid(getLoginUserId());
			Doctor doctor = doctorDao.findByPoid(id);
			
			Attention attention = new Attention();
			attention.setDoctor(doctor);
			attention.setUser(fuser);
			
			attention.setCreateDate(ArgumentUtil.getSysDate());
			attention.setCreateUserName(getLoginUsername());
			
			this.attentionDao.makePersistent(attention);
		}*/
		
		return ajaxSuccess("关注成功！");
	}

	@Operate(desc="取消关注")
	@ResponseBody
	@RequestMapping(value="/cancel")
	public AjaxResult cancelAttend(Integer id) {
		Conjunction con = Restrictions.and();
		con.add(Restrictions.eq("user.poid", getLoginUserId()));
		con.add(Restrictions.eq("doctor.poid", id));
		List<Attention> attList = attentionDao.findByCriteria(false, con);
		if (attList != null && attList.size() > 0) {
			this.attentionDao.makeTransient(attList);
		}
		
		return ajaxSuccess("取消成功！");
	}
}
