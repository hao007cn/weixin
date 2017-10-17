package com.senyint.wx.admin.action;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.senyint.wx.admin.dao.ResourceDao;
import com.senyint.wx.admin.dao.UserDao;
import com.senyint.wx.admin.entity.Resource;
import com.senyint.wx.admin.entity.Role;
import com.senyint.wx.admin.entity.User;
import com.senyint.wx.common.dao.AccessStatisticsDao;
import com.senyint.wx.common.entity.AccessStatistics;
import com.senyint.wx.common.utils.ArgumentUtil;
import com.senyint.wx.common.web.Constants;

@Controller
@RequestMapping(value="/index")
public class IndexAction extends SupportAction{
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ResourceDao resourceDao;
	
	@Autowired
	private AccessStatisticsDao accessStatisticsDao;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(ModelMap model, HttpServletRequest request,HttpServletResponse response,HttpSession session) {
		if (getLoginUserInfo() == null) {
			return "redirect:/admin/login";
		}
		
		Object object = model.get("userRemember");
		
		if(object!=null) {
			User user = (User)object;
			Cookie cookiename = new Cookie("LoginUserName", user.getUsername());
			Cookie cookiepas = new Cookie("LoginUserPass", user.getPassword());
			cookiename.setMaxAge(3600);
			cookiename.setPath("/");
			cookiepas.setMaxAge(3600);
			cookiepas.setPath("/");
			response.addCookie(cookiename);
			response.addCookie(cookiepas);
		}
		
		nav(session);
		//微信用户统计
		AccessStatistics ast= new AccessStatistics();
		ast.setVisit_module(Constants.AS_LIS_KEY);
		ast.setVisit_time(ArgumentUtil.getSysDate());
		//今日lis访问总数
		int lisCount=accessStatisticsDao.getAccessDateAndTypeCount(ast);
		ast.setVisit_module(Constants.AS_PACS_KEY);
		//今日pacs访问总数
		int pacsCount=accessStatisticsDao.getAccessDateAndTypeCount(ast);
		ast.setVisit_time(null);
		//pacs访问总数
		int pacsSum=accessStatisticsDao.getAccessTypeCount(ast);
		ast.setVisit_module(Constants.AS_LIS_KEY);
		//lis访问总数
		int lisSum=accessStatisticsDao.getAccessTypeCount(ast);
		model.addAttribute("lisCount", lisCount);
		model.addAttribute("pacsCount", pacsCount);
		model.addAttribute("todayCount", lisCount+pacsCount);
		model.addAttribute("pacsSum", pacsSum);
		model.addAttribute("lisSum", lisSum);
		model.addAttribute("totalCount", pacsSum+lisSum);
		
		return "index";
	}
	
	
	private void nav(HttpSession session) {
		Resource rootReource  = (Resource) session.getAttribute(Constants.ADMIN_NAV_SESSION);
		if(rootReource == null){
			User user = userDao.findByPoid(getLoginUserInfo().getPoid());
			Set<Integer> enabledResource = new HashSet<Integer>();
			for (Role role : user.getRoles()) {
				for (Resource resource : role.getResources()) {
					if(!enabledResource.contains(resource.getPoid())){
						enabledResource.add(resource.getPoid());
					}
				}
			}
			rootReource =  resourceDao.findRootResource(); 
			wrapNav(rootReource,enabledResource);
			session.setAttribute(Constants.ADMIN_NAV_SESSION, rootReource);
		}
	}
	
	private void wrapNav(Resource root,Set<Integer> enabledResource){
		if(root.hasChildren()){
			List<Resource> childrens = root.getChildren();
			for (Resource resource : childrens) {
				resource.setDisplay(enabledResource.contains(resource.getPoid()));
				if(resource.hasChildren()){
					wrapNav(resource,enabledResource);
				}
			}
		}
	}
	
}
