package com.senyint.wx.mobile.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.senyint.common.util.DateUtils;
import com.senyint.wx.common.dao.VDepartmentDao;
import com.senyint.wx.common.dao.VDepartmentTypeDao;
import com.senyint.wx.common.dao.VDoctorDao;
import com.senyint.wx.common.dao.VRegNumberDao;
import com.senyint.wx.common.dao.VScheduleDao;
import com.senyint.wx.common.entity.VDepartment;
import com.senyint.wx.common.entity.VDepartmentType;
import com.senyint.wx.common.entity.VDoctor;
import com.senyint.wx.common.entity.VRegNumber;
import com.senyint.wx.common.entity.VSchedule;

@Controller
@RequestMapping( value = "/vintroduction")
public class MBVIntroductionAction {	

	@Autowired
	public VDepartmentDao vDepartmentDao;
	@Autowired
	public VDepartmentTypeDao vDepartmentTypeDao;
	@Autowired
	public VDoctorDao vDoctorDao;
	@Autowired
	public VScheduleDao vScheduleDao;
	@Autowired
	public VRegNumberDao vRegNumberDao;
	
	@RequestMapping( method = RequestMethod.GET )
	public String getAreaList(Model model) {
		
		return "areaListV";
	}
	
	/**
	 * 获取科室列表
	 * @param model
	 * @return
	 */
	@RequestMapping(value="deptList", method = RequestMethod.POST )
	public String getdepartmentList(String area, Model model){
		Conjunction con = Restrictions.and();
		
		if(("1").equals(area))
		{
			con.add(Restrictions.ne("area", "2"));
			con.add(Restrictions.ne("area", "3"));
			con.add(Restrictions.ne("area", "4"));
		}else {
			con.add(Restrictions.eq("area", area));
		}
		con.add(Restrictions.eq("enabled","1"));
		List<VDepartmentType> listDepartmentTypes = vDepartmentTypeDao.findDistinctDepartmentType(area);
		List<VDepartment> listDepartment  = vDepartmentDao.findByCriteria(0, 20000, "sort asc", false,con);
		model.addAttribute("listDepartmentTypes", listDepartmentTypes);
		model.addAttribute("listDepartment", listDepartment);
		return "introductionV";
	}
	
	/**
	 * 取得医生列表
	 * @param departmentId
	 * @param departmentName
	 * @param model
	 * @return
	 */
	@RequestMapping( value = "/doctorList", method = RequestMethod.POST )
	public String getDoctorList(String departmentId,String departmentName, Model model){
		Conjunction con = Restrictions.and();
		con.add(Restrictions.eq("departmentId", Integer.parseInt(departmentId)));
		con.add(Restrictions.eq("enabled",1));
		List<VDoctor> vDoctorList = vDoctorDao.findByCriteria(false, con);
		model.addAttribute("departmentName", departmentName);
		model.addAttribute("departmentId", departmentId);
		model.addAttribute("doctorList", vDoctorList);

		return "doctorListV";
	}
	/**
	 * 取得医生挂号预约
	 * @param departmentId
	 * @param doctorId
	 * @param model
	 * @return
	 */
	@RequestMapping( value = "/doctorSchedule", method = RequestMethod.POST )
	public String getDoctorSchedule(String departmentId,String doctorId, Model model, HttpServletRequest request){
		Conjunction condor = Restrictions.and();
		condor.add(Restrictions.eq("departmentId", Integer.parseInt(departmentId)));
		condor.add(Restrictions.eq("did", Integer.parseInt(doctorId)));
		condor.add(Restrictions.eq("enabled",1));
		List<VDoctor> doctors= vDoctorDao.findByCriteria(false, condor);
		Conjunction con = Restrictions.and();
		con.add(Restrictions.eq("departmentId", Integer.parseInt(departmentId)));
		con.add(Restrictions.eq("doctorId", Integer.parseInt(doctorId)));
		
		List<VSchedule> scheduleList = vScheduleDao.findByCriteria(0, 1000, "date asc", false,con);
		if(doctors.size()>0){
			model.addAttribute("vDoctor", doctors.get(0));
			model.addAttribute("doctorName", doctors.get(0).getName());
		}
		model.addAttribute("scheduleList", scheduleList);

		model.addAttribute("departmentId", departmentId);
		model.addAttribute("departmentName", request.getParameter("departmentName"));
		model.addAttribute("doctorId", doctorId);
		return "doctorDetailV";
	}
	
	/**
	* The method <code> getNumberList </code> .
	* 取得某天所有号段
	* 
	* @author sunzhi Senyint (Dalian) Co., Ltd.
	* 
	* @param departmentId
	* @param doctorId
	* @param snumber
	* @param model
	* @return
	*/
	@RequestMapping( value = "/numberlist", method = RequestMethod.POST )
	public String getNumberList(String departmentId, String doctorId, String snumber, Model model, HttpServletRequest request) {
		
		String dateStr = request.getParameter("date");
		Conjunction con = Restrictions.and(Restrictions.eq("number", snumber));
		con.add(Restrictions.eq("date", DateUtils.strToDate(dateStr, DateUtils.FORMAT_yyyy_MM_dd)));
		con.add(Restrictions.eq("orderFlg", 1));
		
		List<VRegNumber> regNumberList = vRegNumberDao.findByCriteria(0, 1000, "startTime asc", false, con);
		
		model.addAttribute("regNumberList", regNumberList);

		model.addAttribute("departmentId", departmentId);
		model.addAttribute("departmentName", request.getParameter("departmentName"));
		model.addAttribute("doctorId", doctorId);
		model.addAttribute("doctorName", request.getParameter("doctorName"));
		
		model.addAttribute("snumber", snumber);
		model.addAttribute("date", request.getParameter("date"));
		model.addAttribute("week", request.getParameter("week"));
		model.addAttribute("schedules", request.getParameter("schedules"));
		model.addAttribute("limitNums", request.getParameter("limitNums"));
		model.addAttribute("fee", request.getParameter("fee"));
		return "regNumberListV";
	}
}
