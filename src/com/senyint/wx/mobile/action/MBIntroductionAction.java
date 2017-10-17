package com.senyint.wx.mobile.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.senyint.wx.common.dao.DepartmentDao;
import com.senyint.wx.common.dao.DepartmentTypeDao;
import com.senyint.wx.common.dao.DoctorDao;
import com.senyint.wx.common.entity.Department;
import com.senyint.wx.common.entity.Doctor;
@Controller
@RequestMapping( value = "/introduction")
public class MBIntroductionAction {	
	@Autowired
	private DepartmentDao departmentDao;

	@Autowired
	private DepartmentTypeDao departmentTypeDao;
	
	@Autowired
	private DoctorDao doctorDao;
	
	@RequestMapping( method = RequestMethod.GET )
	public String getAreaList(Model model) {
		
		List<Department> departmentList = this.departmentDao.findByPoid(1).getChildren();
		model.addAttribute("departmentList", departmentList);
		
		return "areaList";
	}
	
	/**
	 * 获取科室列表
	 * @param model
	 * @return
	 */
	@RequestMapping(value="deptList", method = RequestMethod.POST )
	public String getdepartmentList(String departmentId, Model model){
		Department department = departmentDao.findByPoid(Integer.parseInt(departmentId));
		model.addAttribute("departmentList", department.getChildren());
		
		return "introduction";
	}
	
	
	@RequestMapping( value = "/doctorList", method = RequestMethod.POST )
	public String getDoctorList(String departmentId, Model model){
		Department department = departmentDao.findByPoid(Integer.parseInt(departmentId));
		//部门名称
		String departmentName = department.getName();
		String departmentDesc = department.getDesc();
//		Conjunction con = Restrictions.and();
//		con.add(Restrictions.eq("department", department));
		//List<Doctor> doctorList = doctorDao.findByCriteria(0, 1000, "", false, con);
		//model.addAttribute("doctorList", doctorList);
//		int datasum = doctorDao.fetchCountByCriteria(false, con);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("enabled", true);
		params.put("departmentId", departmentId);
		int datasum = doctorDao.fetchDoctorCount(false, params);
		model.addAttribute("departmentName", departmentName);
		model.addAttribute("departmentId",departmentId);
		model.addAttribute("departmentDesc", departmentDesc);
		model.addAttribute("datasum",datasum);
		return "doctorIntroducedList";
	}
	
	/**医生下拉列表
	 * @param model
	 * @param response
	 * @param request
	 * @param session
	 * @param redirectAttributes
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/append",method = RequestMethod.POST)
	public String appenddate(ModelMap model,HttpServletResponse response,HttpServletRequest request,
			HttpSession session, RedirectAttributes redirectAttributes,String id){
		String pageNum= request.getParameter("pageNum");
//		Department department = departmentDao.findByPoid(Integer.parseInt(id));
		//部门名称
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("enabled", true);
		params.put("departmentId", id);
		List<Doctor> doctorList = doctorDao.findDoctorList(Integer.parseInt(pageNum), 10, "doctor.name asc", false, params);
//		Conjunction con = Restrictions.and();
//		con.add(Restrictions.eq("department", department));
//		List<Doctor> doctorList = doctorDao.findByCriteria(Integer.parseInt(pageNum), 10,"",false, con);
		StringBuffer outhtml= new StringBuffer("");
		for(Doctor doctor : doctorList){
			outhtml.append(" <a href=\" " + request.getContextPath() +"/introduction/doctor?doctorId="+ doctor.getPoid() +"&departmentId="+id+"\" class=\"list-group-item \">");
			outhtml.append("<table> <tr> <td colspan=\"2\">");
			outhtml.append("<div style=\"width: 80px; margin-right: 10px;\">");
			outhtml.append("<img src=\" " + request.getContextPath() +"/file/doctor/" + doctor.getPoid()+ "\" class=\"img-rounded\" width=\"100%\" />");
			outhtml.append("</div>");
			outhtml.append("<td style=\"font-size: 15px; color: #383838; font-weight: 100;\">");
			outhtml.append("<div style=\"margin: 10px\">姓名：" +doctor.getName() +"</div>");
			outhtml.append("<div style=\"margin: 10px\">职称：" +doctor.getJobTitle() +"</div>");
			outhtml.append("<div style=\"margin: 10p\">出诊时间：" +doctor.getOutCallTime() +"</div>");
			outhtml.append("</td>");
			outhtml.append("</tr> </table> </a>");
		}
		return outhtml.toString();
	}
	
	
	
	@RequestMapping( value = "/doctor", method = RequestMethod.GET)
	public String getDoctor(String departmentId, String doctorId, Model model){
		Doctor doctor = doctorDao.findByPoid(Integer.parseInt(doctorId));
		Department department = departmentDao.findByPoid(Integer.parseInt(departmentId));
		model.addAttribute("doctor", doctor);
		model.addAttribute("departmentName", department.getName());
		return "doctorWith";
	}
	
	public String getDoctorListByDepartmentId(String id){
		return null;
	}
	
	public DepartmentDao getDepartmentDao() {
		return departmentDao;
	}

	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	public DepartmentTypeDao getDepartmentTypeDao() {
		return departmentTypeDao;
	}

	public void setDepartmentTypeDao(DepartmentTypeDao departmentTypeDao) {
		this.departmentTypeDao = departmentTypeDao;
	}

	public DoctorDao getDoctorDao() {
		return doctorDao;
	}

	public void setDoctorDao(DoctorDao doctorDao) {
		this.doctorDao = doctorDao;
	}
	

}
