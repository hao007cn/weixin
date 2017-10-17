package com.senyint.wx.admin.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
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
import com.senyint.wx.admin.dao.ResourceDao;
import com.senyint.wx.admin.entity.Resource;
import com.senyint.wx.common.web.Operate;

/**
 * 菜单管理Action
 * 
 * @author sunzhi
 *
 */
@Controller
@RequestMapping(value = "/sysmenu")
public class SysMenuAction extends SupportAction {
	
	public static final Integer ROOT_ID = 1;
	
	@Autowired
	private ResourceDao resourceDao;
	
	/**
	 * 初始页面
	 * 
	 * @return
	 */
	@Operate(desc="菜单管理初始页面")
	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model, HttpServletRequest request) {
		
		List<Resource> resList = new ArrayList<Resource>();
		Resource resource = resourceDao.findByPoid(ROOT_ID);
		if (resource != null && resource.getChildren() != null) {
			resList = resource.getChildren();
		}
		model.addAttribute("resList", resList);
		
		return "sysMenu";
	}

	@Operate(desc="菜单明细")
	@ResponseBody
	@RequestMapping(value = "/detail")
	public Resource detail(Integer poid) {
		
		return resourceDao.findByPoid(poid);
	}

	/**
	 * 菜单管理初始页面
	 * 
	 * @param request
	 * @return
	 */
	@Operate(desc="菜单管理初始页面")
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public AjaxResult update(/*Resource res, */HttpServletRequest request) {
		
		//resourceDao.makePersistent(res);
		
		ObjectMapper mapper = new ObjectMapper();
		Resource resParam = null;
		
		Resource root = resourceDao.findByPoid(ROOT_ID);
		
		try {
			resParam = mapper.readValue(request.getParameter("json"), Resource.class);
			
			List<Resource> menu = resParam.getChildren();
			int i = 0;
			List<Resource> entities = new ArrayList<Resource>();
			for (Resource res : menu) {
				res = getResourceEntity(res);
				if (res != null) {
					res.setSort(i);
					res.setParent(root);
					entities.add(res);
				}
				i++;
			}
			
			resourceDao.makePersistent(entities);
		} catch (JsonParseException e) {
			return ajaxFail("数据格式不正确！");
		} catch (JsonMappingException e) {
			return ajaxFail("数据格式不正确！");
		} catch (IOException e) {
			return ajaxFail("数据格式不正确！");
		}
		
		return ajaxSuccess("保存成功！");
	}
	
	/**
	 * 私有方法，用于递归获取resource树完整值
	 * 
	 * @param res
	 * @return
	 */
	private Resource getResourceEntity(Resource res) {
		if (res.getPoid() != null) {
			Resource resEntity = resourceDao.findByPoid(res.getPoid());
			resEntity.setChildren(null);
			if (res.getChildren() != null && res.getChildren().size() > 0) {
				List<Resource> children = new ArrayList<Resource>();
				int i = 0;
				for (Resource subRes : res.getChildren()) {
					subRes = getResourceEntity(subRes);
					if (subRes != null) {
						subRes.setSort(i);
						children.add(subRes);
					}
					i ++;
				}
				resEntity.setChildren(children);
			}
			return resEntity;
		} else {
			return null;
		}
	}

	/**
	 * 树形加载
	 * 
	 * @return
	 */
	@Operate(desc="树形加载")
	@ResponseBody
	@RequestMapping(value = "/stree", method = RequestMethod.POST)
	public Resource stree() {
		Resource root = resourceDao.findByPoid(ROOT_ID);
		return root;
	}

	/**
	 * 保存菜单
	 * 
	 * @param res
	 * @param redirectAttributes
	 * @return
	 */
	@Operate(desc="保存菜单")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Resource res, RedirectAttributes redirectAttributes) {
		Resource parent = resourceDao.findByPoid(res.getParentId());
		int sort = 0;
		if (parent.getChildren() != null) {
			boolean found = false;
			if (res.getPosition() != null) {
				if (res.getPosition() == -1) {
					sort = 0;
					found = true;
					for(Resource child : parent.getChildren()) {
						child.setSort(child.getSort() + 1);
					}
				} else {
					for(Resource child : parent.getChildren()) {
						if (child.getPoid() == res.getPosition()) {
							sort = child.getSort() + 1;
							found = true;
						}
						if (found) {
							child.setSort(child.getSort() + 1);
						}
					}
				}
			}
		}
		
		res.setSort(sort);
		
		if (res.getPoid() == null) {
			if (StringUtils.isNotBlank(res.getResKey())) {
				Conjunction con = Restrictions.and();
				con.add(Restrictions.eq("resKey", res.getResKey()));
				int count = this.resourceDao.fetchCountByCriteria(false, con);
				if (count > 1) {
					redirectAttributes.addFlashAttribute("errMsg", "key重复，保存不成功！");
					return "redirect:/admin/sysmenu";
				}
			}
			
			List<Resource> resList = new ArrayList<Resource>();
			parent.getChildren();
			resList.add(parent);
			
			res.setParent(parent);
			resList.add(res);
			resourceDao.makePersistent(resList);
		} else {
			res.setParent(parent);
			Resource resOld = this.resourceDao.findByPoid(res.getPoid());
			
			if (StringUtils.isNotBlank(res.getResKey())) {
				if (!StringUtils.equals(resOld.getResKey(), res.getResKey())) {
					Conjunction con = Restrictions.and();
					con.add(Restrictions.eq("resKey", res.getResKey()));
					int count = this.resourceDao.fetchCountByCriteria(false, con);
					if (count > 1) {
						redirectAttributes.addFlashAttribute("errMsg", "key重复，保存不成功！");
						return "redirect:/admin/sysmenu";
					}
				}
			}
			
			resOld.setParent(parent);
			resOld.setName(res.getName());
			resOld.setDesc(res.getDesc());
			resOld.setResClass(res.getResClass());
			resOld.setResKey(res.getResKey());
			resOld.setResUrl(res.getResUrl());
			resOld.setSort(res.getSort());
			resourceDao.makePersistent(resOld);
		}
		
		redirectAttributes.addFlashAttribute("message", "保存成功！");
		
		return "redirect:/admin/sysmenu";
	}
	
	/**
	 * ajax验证方法
	 * 
	 * @param fieldId
	 * @param fieldValue
	 * @param request
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkkey", method = RequestMethod.GET)
	public List<Object> checkKey(String fieldId, String fieldValue,
			HttpServletRequest request, HttpSession session) {
		List<Object> list = new ArrayList<Object>();
		if (StringUtils.isNotBlank(fieldValue)) {
			Conjunction con = Restrictions.and();
			con.add(Restrictions.eq("resKey", fieldValue));
			int count = this.resourceDao.fetchCountByCriteria(false, con);
			if (count <= 0) {
				list.add(fieldId);
				list.add(true);
			} else {
				list.add(fieldId);
				list.add(false);
			}
		}
		return list;
	}
	
	/**
	 * ajax验证方法
	 * 
	 * @param fieldId
	 * @param fieldValue
	 * @param request
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "keycount", method = RequestMethod.POST)
	public Integer keycount(HttpServletRequest request, HttpSession session) {
		String poid = request.getParameter("poid");
		String key = request.getParameter("key");
		int count = 0;
		if (StringUtils.isNotBlank(key)) {
			Conjunction con = Restrictions.and();
			con.add(Restrictions.eq("resKey", key));
			if (StringUtils.isNotBlank(poid)) {
				con.add(Restrictions.not(Restrictions.idEq(Integer.parseInt(poid))));
			}
			count = this.resourceDao.fetchCountByCriteria(false, con);
		}
		return count;
	}
	
	/**
	 * 删除菜单
	 * 
	 * @param poid
	 * @return
	 */
	@RequestMapping(value = "delete")
	public String delete(Integer poid, RedirectAttributes redirectAttributes) {
		Resource res = this.resourceDao.findByPoid(poid);
		if (res != null) {
			res.removeAllRoles();
			this.resourceDao.makeTransient(res);
		}
		
		redirectAttributes.addFlashAttribute("message", "删除成功！");
		return "redirect:/admin/sysmenu";
	}
}
