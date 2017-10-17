package com.senyint.wx.admin.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.senyint.wx.common.dao.DepartmentTypeDao;
import com.senyint.wx.common.entity.DepartmentType;

@Controller
@RequestMapping(value = "/depttype")
public class DepartmentTypeAction {
	
	@Autowired
	private DepartmentTypeDao departmentTypeDao;
	
	/**
	 * 初始页面
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping()
	public String index(Model model, HttpServletRequest request) {
		List<DepartmentType> list = departmentTypeDao.findByCriteria(0, 1000, "sort asc",false);
		model.addAttribute("departmentTypeList", list);
		
		return "departmentType";
	}
	
	/**
	 * 保存
	 * 
	 * @param model
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="/save")
	public String save(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String[] ids = request.getParameterValues("poid");
		String[] sorts = request.getParameterValues("sort");
		String[] names = request.getParameterValues("name");
		String[] icons = request.getParameterValues("icons");
		String[] enableds = request.getParameterValues("enabled");
		String[] remarkses = request.getParameterValues("remarks");
		if(ids != null && ids.length > 0) {
			List<DepartmentType> deptTypeList = new ArrayList<DepartmentType>();
			for (int i = 0; i < ids.length; i ++) {
				if (StringUtils.isEmpty(names[i])) {
					continue;
				}
				String id = ids[i];
				DepartmentType departmentType;
				if (StringUtils.isNotBlank(id)) {// 更新
					departmentType = this.departmentTypeDao.findByPoid(Integer.parseInt(id));
					if (departmentType == null) {
						departmentType = new DepartmentType();
					}
				} else {
					departmentType = new DepartmentType();
				}
				
				if (StringUtils.isEmpty(sorts[i])) {
					departmentType.setSort(0);
				} else {
					departmentType.setSort(Integer.parseInt(sorts[i]));
				}
				
				departmentType.setEnabled(!"0".equals(enableds[i]));
				departmentType.setName(names[i]);
				departmentType.setIcons(icons[i]);
				departmentType.setRemarks(remarkses[i]);
				
				deptTypeList.add(departmentType);
			}
			departmentTypeDao.makePersistent(deptTypeList);
		}
		redirectAttributes.addFlashAttribute("message", "操作成功！");
		return "redirect:/admin/depttype";
	}
	
	/**
	 * 	删除
	 * 
	 * @param id 类型id
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="/delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		if (StringUtils.isNotBlank(id)) {
			DepartmentType departmentType = new DepartmentType();
			departmentType.setPoid(Integer.parseInt(id));
			try {
				departmentTypeDao.remove(departmentType);
				redirectAttributes.addFlashAttribute("message", "操作成功！");
			} catch(Exception e) {
				redirectAttributes.addFlashAttribute("errMsg", "有科室关联，不可删除！");
			}
		}
		return "redirect:/admin/depttype";
	}
}
