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

import com.senyint.common.web.ajax.AjaxResult;
import com.senyint.common.web.ajax.GridParam;
import com.senyint.common.web.ajax.GridResult;
import com.senyint.wx.admin.dao.ResourceDao;
import com.senyint.wx.admin.dao.RoleDao;
import com.senyint.wx.admin.dao.UserDao;
import com.senyint.wx.admin.entity.Resource;
import com.senyint.wx.admin.entity.Role;
import com.senyint.wx.admin.entity.User;
import com.senyint.wx.common.web.Operate;

/**
 * 角色Action
 * 
 * @author sunzhi
 *
 */
@Controller
@RequestMapping(value = "/sysrole")
public class SysRoleAction extends SupportAction {
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ResourceDao resourceDao;
	
	/**
	 * 角色列表页面
	 * 
	 * @return
	 */
	@Operate(desc = "角色列表页面")
	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		// 条件
		Conjunction con = Restrictions.and();
		con.add(Restrictions.eq("enabled", true));
		
		List<User> userList = this.userDao.findByCriteria(false, con);
		
		model.addAttribute("userList", userList);
		return "sysRoles";
	}

	/**
	 * 加载角色Grid
	 * 
	 * @param gp grid传来参数
	 * @param request
	 * @return
	 */
	@Operate(desc = "加载角色Grid")
	@ResponseBody
	@RequestMapping(value = "/list")
	public GridResult list(GridParam gp, HttpServletRequest request) {
		
		// 条件
		Conjunction con = Restrictions.and();
		
		if (StringUtils.isNotBlank(request.getParameter("name"))) {
			con.add(Restrictions.like("name", "%" + request.getParameter("name") + "%"));
		}
		
		// 查询条件
		int totalCount = this.roleDao.fetchCountByCriteria(false, con);
		
		// 列表结果
		List<Role> roleList = this.roleDao.findByCriteria(gp.getStartRow(), gp.getRows(), "poid desc", false, con);
		
		// 返回数据
		return gridResult(gp, totalCount, roleList);
	}

	/**
	 * 角色详情(json)
	 * 
	 * @param 角色id
	 * @param request
	 * @return
	 */
	@Operate(desc = "角色详情(json)")
	@ResponseBody
	@RequestMapping(value = "/detail")
	public Role detail(Integer id) {
		
		return this.roleDao.findByPoid(id);
	}

	/**
	 * 删除角色
	 * 
	 * @param 角色id
	 * @param request
	 * @return
	 */
	@Operate(desc = "删除角色")
	@ResponseBody
	@RequestMapping(value = "/delete")
	public AjaxResult delete(String id) {
		
		if (StringUtils.isBlank(id)) {
			return ajaxFail("请选择记录！");
		}
		
		String[] idarr = id.split(",");
		List<Role> roleList = new ArrayList<Role>();
		for (String idtmp : idarr) {
			if (StringUtils.isNotBlank(idtmp)) {
				Role role = this.roleDao.findByPoid(Integer.valueOf(idtmp));
				role.removeAllOwners();
				role.setResources(null);
				roleList.add(role);
			}
		}
		
		if (roleList.size() > 0) {
			this.roleDao.makeTransient(roleList);
		}
		
		return ajaxSuccess("删除成功！");
	}
	

	/**
	 * 保存角色
	 * 
	 * @return ajax 结果
	 */
	@Operate(desc = "保存角色")
	@ResponseBody
	@RequestMapping(value = "/save")
	public AjaxResult save(HttpServletRequest request) {
		// 页面参数
		// 用户
		String[] userIds = request.getParameterValues("userIds");
		if (userIds == null) {
			userIds = request.getParameterValues("userIds[]");
		}
		
		// 资源
		String resIdsStr = request.getParameter("resIds");
		String[] resIds = null;
		if (StringUtils.isNotBlank(resIdsStr)) {
			resIds = resIdsStr.split(",");
		}
		
		// 获取所有user的实体实例
		List<User> owners = new ArrayList<User>();
		if (userIds != null && userIds.length > 0) {
			for (String poidstr : userIds) {
				if (StringUtils.isNotBlank(poidstr)) {
					owners.add(this.userDao.findByPoid(Integer.parseInt(poidstr)));
				}
			}
		}

		// 获取所有资源的实体实例
		List<Resource> resources = new ArrayList<Resource>();
		if (resIds != null && resIds.length > 0) {
			for (String poidstr : resIds) {
				if (StringUtils.isNotBlank(poidstr)) {
					resources.add(this.resourceDao.findByPoid(Integer.parseInt(poidstr)));
				}
			}
		}
		
		// 页面其他参数
		String poid = request.getParameter("poid");
		String name = request.getParameter("name");
		String desc = request.getParameter("desc");

		Role role = new Role();
		
		if (StringUtils.isNotBlank(poid)) { // 更新
			role = this.roleDao.findByPoid(Integer.parseInt(poid));
			if (role != null) {
				role.setName(name);
				role.setDesc(desc);
				role.removeAllOwners();// 先清空，再添加
				for (User user : owners) {
					role.addOwner(user);
				}
				
				role.setResources(resources);
				
				this.roleDao.makePersistent(role);
			} else {
				role = new Role();
				role.setName(name);
				role.setDesc(desc);
				role.setOwners(owners);
				role.setResources(resources);
				this.roleDao.makePersistent(role);
			}
		} else {
			role.setName(name);
			role.setDesc(desc);
			role.setOwners(owners);
			role.setResources(resources);
			this.roleDao.makePersistent(role);
		}
		
		return ajaxSuccess("保存成功！");
	}
}
