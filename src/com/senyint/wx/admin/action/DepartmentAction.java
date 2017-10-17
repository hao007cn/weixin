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
import com.senyint.wx.common.dao.DepartmentDao;
import com.senyint.wx.common.dao.DepartmentTypeDao;
import com.senyint.wx.common.entity.Department;
import com.senyint.wx.common.entity.DepartmentType;
import com.senyint.wx.common.web.Operate;

/**
 * 科室Action
 * 
 * @author sunzhi
 * 
 */
@Controller
@RequestMapping(value = "/department")
public class DepartmentAction extends SupportAction {

	public static final Integer ROOT_ID = 1;

	@Autowired
	private DepartmentDao departmentDao;

	@Autowired
	private DepartmentTypeDao departmentTypeDao;

	/**
	 * 初始页面
	 * 
	 * @return
	 */
	@Operate(desc = "科室列表页面")
	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model, HttpServletRequest request) {

		List<Department> deptList = departmentDao.findByCriteria(0, 10000,
				"sort asc", false);
		if (deptList != null && deptList.size() > 0) {
			for (Department dept : deptList) {
				if (dept.getPoid() == ROOT_ID) {
					deptList.remove(dept);
					break;
				}
			}
		}

		List<DepartmentType> departmentTypeList = departmentTypeDao
				.findByCriteria(0, 10000, "sort asc", true,
						Restrictions.eq("enabled", true));

		model.addAttribute("departmentList", deptList);
		model.addAttribute("departmentTypeList", departmentTypeList);

		return "department";
	}

	/**
	 * 加载子树
	 * 
	 * @return
	 */
	@Operate(desc = "科室子树加载")
	@ResponseBody
	@RequestMapping(value = "/tree")
	public List<Department> deptTree(Integer poid) {
		if (poid != null) {
			Department department = departmentDao.findByPoid(poid);
			if (department != null && department.getChildren() != null) {
				return department.getChildren();
			}
		}

		return new ArrayList<Department>();
	}

	/**
	 * 科室类别树
	 * 
	 * @return
	 */
	@Operate(desc = "科室类别树加载")
	@ResponseBody
	@RequestMapping(value = "/ttree")
	public Department deptTypeTree(Integer poid) {
		if (poid != null) {
			return getTypeTree(departmentDao.findByPoid(poid));
		}

		Department department = departmentDao.findByPoid(ROOT_ID);

		return getTypeTree(department);
	}

	private Department getTypeTree(Department dept) {
		if (dept == null || !dept.isParentFlg()) {
			return null;
		} else {
			Department deptNew = new Department();
			deptNew.setPoid(dept.getPoid());
			deptNew.setName(dept.getName());
			deptNew.setDeleteFlg(dept.getDeleteFlg());
			deptNew.setEnabled(dept.isEnabled());
			deptNew.setParentId(dept.getParentId());
			deptNew.setIcon(dept.getIcon());
			deptNew.setParentFlg(dept.isParentFlg());
			deptNew.setParentName(dept.getParentName());
			deptNew.setSort(dept.getSort());

			if (dept.getChildren() != null && dept.getChildren().size() > 0) {
				List<Department> children = new ArrayList<Department>();
				for (Department child : dept.getChildren()) {
					Department childNew = getTypeTree(child);
					if (childNew != null) {
						children.add(childNew);
					}
				}

				deptNew.setChildren(children);
			}

			return deptNew;
		}
	}

	/**
	 * 全树加载
	 * 
	 * @return
	 */
	@Operate(desc = "全树加载")
	@ResponseBody
	@RequestMapping(value = "/stree")
	public Object deptTreeAll(HttpServletRequest request) {
		String poid = request.getParameter("poid");
		if (poid == null) {
			Department department = departmentDao.findByPoid(ROOT_ID);

			return department;
		} else {
			Department department = departmentDao.findByPoid(Integer
					.parseInt(poid));
			if (department.getChildren() != null
					&& department.getChildren().size() > 0) {
				return department.getChildren();
			}
			return null;
		}
	}

	/**
	 * ajax方法，返回json字符串
	 * 
	 * 
	 * @return
	 */
	@Operate(desc = "科室列表")
	@ResponseBody
	@RequestMapping(value = "/list")
	public List<Department> list(String id) {
		List<Department> list = null;
		if (StringUtils.isBlank(id)) {
			list = departmentDao.findByCriteria(0, 10000, "sort asc", false);
			if (list != null && list.size() > 0) {
				for (Department dept : list) {
					if (dept.getPoid() == ROOT_ID) {
						list.remove(dept);
						break;
					}
				}
			}
		} else {
			Department department = departmentDao.findByPoid(Integer
					.parseInt(id));
			if (department != null && department.getChildren() != null) {
				list = department.getChildren();
			}
		}

		if (list == null) {
			list = new ArrayList<Department>();
		}

		return list;
	}

	/**
	 * ajax方法，返回json字符串
	 * 
	 * @return
	 */
	@Operate(desc = "科室详情")
	@ResponseBody
	@RequestMapping(value = "/detail")
	public AjaxResult detail(String id) {
		Department department = null;
		if (StringUtils.isNotEmpty(id)) {
			department = departmentDao.findByPoid(Integer.parseInt(id));
		}
		return ajaxSuccess("加载成功！", department);
	}

	/**
	 * 保存
	 * 
	 * @param department
	 * @param request
	 * @return
	 */
	@Operate(desc = "保存科室")
	@ResponseBody
	@RequestMapping(value = "/save")
	public AjaxResult save(Department department, HttpServletRequest request) {
		String parentId = request.getParameter("parentId");
		// 未选择上级科室则不处理
		if (StringUtils.isBlank(parentId)) {
			return ajaxFail("请选择上级科室！");
		}

		// 上级科室不存在则不处理
		Department parent = departmentDao.findByPoid(department.getParentId());
		if (parent == null) {
			return ajaxFail("数据异常，上级科室不存在！");
		}

		department.setParent(parent);

		DepartmentType type = departmentTypeDao.findByPoid(department
				.getTypeId());
		department.setType(type);

		if (department.getPoid() != null) {// 更新
			Department departmentOld = departmentDao.findByPoid(department
					.getPoid());
			if (departmentOld != null) {
				departmentOld.setDesc(department.getDesc());
				departmentOld.setName(department.getName());
				departmentOld.setSort(department.getSort());
				departmentOld.setIcon(department.getIcon());
				departmentOld.setEnabled(department.isEnabled());
				departmentOld.setParent(department.getParent());
				departmentOld.setType(department.getType());
				departmentOld.setParentFlg(department.isParentFlg());

				departmentDao.makePersistent(departmentOld);
			} else {
				departmentDao.makePersistent(department);
			}
		} else {
			departmentDao.makePersistent(department);
		}

		return ajaxSuccess("保存成功！");
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@Operate(desc = "删除科室")
	@ResponseBody
	@RequestMapping(value = "/delete")
	public AjaxResult delete(String id) {
		if (StringUtils.isNotBlank(id)) {
			String[] ids = id.split(",");
			List<Department> deptList = new ArrayList<Department>();
			for (String idTmp : ids) {
				if (StringUtils.isNotBlank(idTmp)) {
					Department dept = departmentDao.findByPoid(Integer
							.parseInt(idTmp));
					// setDeptDel(dept);
					deptList.add(dept);
				}
			}
			try {
				departmentDao.remove(deptList);
			} catch (Exception e) {
				return ajaxFail("有子科室或者有其他关联数据，不可删除！");
			}
		} else {
			return ajaxFail("请选择记录！");
		}

		return ajaxSuccess("删除成功！");
	}

	// private void setDeptDel(Department dept) {
	// dept.setDeleteFlg(true);
	// if (dept.getChildren() != null && dept.getChildren().size() > 0) {
	// for (Department deptChild : dept.getChildren()) {
	// setDeptDel(deptChild);
	// }
	// }
	// }

	/**
	 * 保存
	 * 
	 * @return
	 */
	@Operate(desc = "批量保存科室")
	@ResponseBody
	@RequestMapping(value = "/savebatch")
	public AjaxResult savebatch(HttpServletRequest request) {
		// 取得id序列 逗号分隔
		String ids = request.getParameter("ids");
		// 取得sort序列 逗号分隔,顺序与ids相同
		String sorts = request.getParameter("sorts");
		String icons = request.getParameter("icons");
		// 取得启用序列 逗号分隔,顺序与ids相同
		String enableds = request.getParameter("enableds");
		// 取得能否预约序列 逗号分隔,顺序与ids相同
		String orderableds = request.getParameter("orderableds");
		if (StringUtils.isNotBlank(ids)) {
			String[] idarr = ids.split(",");
			String[] sortarr = sorts.split(",");
			String[] iconarr = icons.split(",");
			String[] enabledarr = enableds.split(",");
			String[] orderabledarr = orderableds.split(",");
			List<Department> deptList = new ArrayList<Department>();
			for (int i = 0; i < idarr.length; i++) {
				String idstr = idarr[i];
				if (StringUtils.isNotBlank(idstr)) {
					Department dept = departmentDao.findByPoid(Integer
							.parseInt(idstr));
					deptList.add(dept);
					if (dept != null) {
						// 显示顺序
						int sort = 0;
						try {
							sort = Integer.parseInt(sortarr[i]);
						} catch (Exception e) {
							sort = 0;
						}
						dept.setSort(sort);

						String icon = "";
						try {
							icon = iconarr[i];
						} catch (Exception e) {
							icon = "";
						}
						dept.setIcon(icon);

						// 启用标识
						dept.setEnabled(!"0".equals(enabledarr[i]));

						// 启用标识
						dept.setOrderabled(!"0".equals(orderabledarr[i]));
					}
				}
			}

			departmentDao.makePersistent(deptList);
		} else {
			return ajaxFail("没有需要保存的数据！");
		}

		return ajaxSuccess("保存成功！");
	}

	/**
	 * 树形菜单拖动排序
	 * @param department
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/movesort")
	public String movesort(Department department, HttpServletRequest request) {
		int numFlag = 0;
		 Department departmentT = departmentDao.findByPoid(department.getTargetPoid());//目标分类信息
		 Department departmentM = departmentDao.findByPoid(department.getPoid());//拖动分类信息

		 if(department.getMoveType().equals("prev")){ 
			 Conjunction con =  Restrictions.and();
			 con.add(Restrictions.eq("parent.poid", departmentT.getParentId()));
			 con.add(Restrictions.gt("sort", departmentT.getSort()));
			 con.add(Restrictions.lt("sort", departmentM.getSort()));
			 List<Department> list= departmentDao.findByCriteria(false,con);
			 int sort_id = departmentT.getSort() + 1;
			 departmentM.setSort(departmentT.getSort());
			 departmentT.setSort(sort_id);
			 List<Department> tempList=new ArrayList<Department>();
			 for (int i = 0; i < list.size(); i++) {
				 Department dep=list.get(i);
				 if(!(dep.getPoid()).equals(departmentM.getPoid())&&!(dep.getPoid()).equals(departmentT.getPoid())){
					 dep.setSort(dep.getSort()+1);
					 tempList.add(dep);
				 }
			}
			list.add(departmentM);
			list.add(departmentT);
			List<Department> listTemp= departmentDao.makePersistent(list);
			numFlag=listTemp.size();
			if(numFlag>0)
			{
				return "true";
			}
		 }else {
			 Conjunction con =  Restrictions.and();
			 con.add(Restrictions.eq("parent.poid", departmentT.getParentId()));
			 con.add(Restrictions.gt("sort", departmentM.getSort()));
			 con.add(Restrictions.lt("sort", departmentT.getSort()));
			 List<Department> list= departmentDao.findByCriteria(false,con);
			 int sort_id = departmentT.getSort();
			 departmentM.setSort(sort_id);
			 departmentT.setSort(sort_id-1);
			 List<Department> tempList=new ArrayList<Department>();
			 for (int i = 0; i < list.size(); i++) {
				 Department dep=list.get(i);
				 if(!(dep.getPoid()).equals(departmentM.getPoid())&&!(dep.getPoid()).equals(departmentT.getPoid())){
					 dep.setSort(dep.getSort()-1);
					 tempList.add(dep);
				 }
			}
			 	list.add(departmentM);
				list.add(departmentT);
				List<Department> listTemp= departmentDao.makePersistent(list);
				numFlag=listTemp.size();
				if(numFlag>0)
				{
					return "true";
				}
		}
		return "false";
	}
}
