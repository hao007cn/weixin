package com.senyint.wx.mobile.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.senyint.core.dao.exception.AppRuntimeException;
import com.senyint.wx.common.dao.AttentionDao;
import com.senyint.wx.common.dao.DepartmentDao;
import com.senyint.wx.common.dao.DoctorDao;
import com.senyint.wx.common.dao.OrderDao;
import com.senyint.wx.common.dao.ScheduleDao;
import com.senyint.wx.common.dao.UserRelationDao;
import com.senyint.wx.common.dao.VDepartmentDao;
import com.senyint.wx.common.dao.VDoctorDao;
import com.senyint.wx.common.dao.VRegNumberDao;
import com.senyint.wx.common.entity.Department;
import com.senyint.wx.common.entity.Doctor;
import com.senyint.wx.common.entity.ForegroundUser;
import com.senyint.wx.common.entity.Schedule;
import com.senyint.wx.common.entity.UserRelation;
import com.senyint.wx.common.service.OrderService;
import com.senyint.wx.common.utils.ArgumentUtil;
import com.senyint.wx.common.web.Operate;
import com.senyint.wx.mobile.service.ForegroundRegistrationService;

@Controller
@RequestMapping(value = "/book")
public class MBBookAction extends MBSupportAction {
	@Autowired
	private DepartmentDao departmentDao;
	
	@Autowired
	private DoctorDao doctorDao;
	
	@Autowired
	private ScheduleDao scheduleDao;

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private UserRelationDao userRelationDao;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private AttentionDao attentionDao;
	@Autowired
	public VRegNumberDao vRegNumberDao;
	@Autowired
	public VDepartmentDao vDepartmentDao;
	@Autowired
	public VDoctorDao vDoctorDao;

	@Autowired
	private ForegroundRegistrationService foregroundRegistrationService;
	

	/**
	* The method <code> index </code> .
	* 预约挂号首页
	* 
	* @author sunzhi Senyint (Dalian) Co., Ltd.
	* 
	* @param model
	* @return
	*/
	@Operate(desc="预约挂号-首页")
	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		return areaList(model);
	}
	
	/**
	* The method <code> areaList </code> .
	* 院区列表
	* 
	* @author sunzhi Senyint (Dalian) Co., Ltd.
	* 
	* @return
	*/
	@Operate(desc="预约挂号-院区列表")
	@RequestMapping(value = "/areaList")
	public String areaList(Model model) {
		
		List<Department> departmentList = this.departmentDao.findByPoid(1).getChildren();
		model.addAttribute("departmentList", departmentList);
		
		return "selfService/bookRegister/areaList";
	}


	/**
	* The method <code> deptlist </code> .
	* 科室列表
	* 
	* @author sunzhi Senyint (Dalian) Co., Ltd.
	* 
	* @param model
	* @return
	*/
	@Operate(desc="预约挂号-科室列表")
	@RequestMapping(value = "/deptlist")
	public String deptlist(String departmentId, Model model) {
		Department department = departmentDao.findByPoid(Integer.parseInt(departmentId));
		model.addAttribute("departmentList", department.getChildren());
		
		/*
		Conjunction con = Restrictions.and();
		con.add(Restrictions.eq("enabled", true));
		List<DepartmentType> departmentTypeList = departmentTypeDao.findByCriteria(0, 1000, "", false, con);
		
		con = Restrictions.and();
		con.add(Restrictions.eq("enabled", true));
		con.add(Restrictions.eq("orderabled", true));
		con.add(Restrictions.gt("id", 1));
		con.add(Restrictions.eq("deleteFlg", false));
		List<Department> departmentList = departmentDao.findByCriteria(0, 1000, "", false, con);
		model.addAttribute("departmentTypeList", departmentTypeList );
		model.addAttribute("departmentList", departmentList);*/
		
		return "selfService/bookRegister/departmentList";
	}

	/**
	* The method <code> doctorlist </code> .
	* 专家列表
	* 
	* @author sunzhi Senyint (Dalian) Co., Ltd.
	* 
	* @param departmentId
	* @param model
	* @return
	*/
	@Operate(desc="预约挂号-专家列表")
	@RequestMapping(value = "/doctorlist")
	public String doctorlist(Model model, HttpServletRequest request) {
		String departmentId = request.getParameter("departmentId");
		
		Department department = departmentDao.findByPoid(Integer.parseInt(departmentId));
		//部门名称
		String departmentName = department.getName();
//		Conjunction con = Restrictions.and();
//		con.add(Restrictions.eq("department", department));
//		List<Doctor> doctorList = doctorDao.findByCriteria(0, 1000, "", false, con);
//		Map<String, Object> params = new HashMap<String, Object>();
//		List<Doctor> doctorList = doctorDao.fetchDoctorCount(params);
		model.addAttribute("doctorList", department.getDoctors());
		model.addAttribute("departmentName", departmentName);
		return "selfService/bookRegister/doctorList";
	}

	/**
	 * 医生详情
	 * 
	 * @param poid
	 * @param model
	 * @return
	 */
	@Operate(desc="医生详情")
	@RequestMapping(value = "/doctor/{poid}")
	public String doctor(@PathVariable("poid") String poid, Model model) {
		Doctor doctor = null;
		if(StringUtils.isNotEmpty(poid)) {
			try {
				doctor = this.doctorDao.findByPoid(Integer.parseInt(poid));
			} catch(Exception e) {
			}
		}
		
		if (doctor == null) {
			model.addAttribute("errMsg", "该医生不存在！");
			
			return "selfService/bookRegister/doctorDetail";
		}
		
		model.addAttribute("doctor", doctor);

		Conjunction con = Restrictions.and();
		con.add(Restrictions.eq("doctor.poid", Integer.parseInt(poid)));
		con.add(Restrictions.ge("date", ArgumentUtil.getSysDate()));
		int count = scheduleDao.fetchCountByCriteria(false, con);
		model.addAttribute("pageSum", count);
		
		Conjunction attcon = Restrictions.and();
		attcon.add(Restrictions.eq("user.poid", getLoginUserId()));
		attcon.add(Restrictions.eq("doctor.poid", Integer.parseInt(poid)));
		int attCount = attentionDao.fetchCountByCriteria(false, attcon);
		model.addAttribute("attCount", attCount);
		
		Integer patientCount = this.orderDao.findDoctorPatientCount(Integer.parseInt(poid));
		model.addAttribute("patientCount", patientCount);
		
		return "selfService/bookRegister/doctorDetail";
	}
	

	/**
	 * 专家详情-排班信息
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@Operate(desc="专家详情-排班信息")
	@RequestMapping(value = "/schedule")
	public String schedule(Model model, HttpServletRequest request) {
		try {
			String pageNum = request.getParameter("pageNum");
			String doctorId = request.getParameter("doctorId");
			Conjunction con = Restrictions.and();
			con.add(Restrictions.eq("doctor.poid", Integer.parseInt(doctorId)));
			con.add(Restrictions.ge("date", ArgumentUtil.getSysDate()));
			List<Schedule> list = scheduleDao.findByCriteria(Integer.parseInt(pageNum), 10, "date asc, ap asc", false, con);
			
			model.addAttribute("scheduleList", list);
		} catch(Exception e) {
			model.addAttribute("errMsg", "数据加载异常！");
		}
		
		return "selfService/bookRegister/schedule";
	}
	
	/**
	 * 预约
	 * 
	 * @return
	 * @throws Exception 
	 */
	@Operate(desc="预约确认")
	@RequestMapping(value = "/go", method = RequestMethod.POST)
	public String goBook(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) throws Exception {
		// 排班id
		Integer departmentId = Integer.parseInt(request.getParameter("departmentId"));
		String departmentName = request.getParameter("departmentName");
		Integer doctorId = Integer.parseInt(request.getParameter("doctorId"));
		String doctorName = request.getParameter("doctorName");
		String snumber = request.getParameter("snumber");
		String serialNo = request.getParameter("serialNo");
		String date = request.getParameter("date");
		String week = request.getParameter("week");
		String schedules = request.getParameter("schedules");
		String limitNums = request.getParameter("limitNums");
		String fee = request.getParameter("fee");
		String regDate = request.getParameter("regDate");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		
//		VDepartment department = this.vDepartmentDao.findByPoid(departmentId);
		
		/*ForegroundUser cuser = this.foregroundUserDao.findByPoid(getLoginUserId());
		if (cuser.getLocked()) {
			model.addAttribute("errMsg", "抱歉，您的账号已被封，不能进行预约！被封原因：" + cuser.getLockedReason());
			
			return "selfService/bookRegister/bookResult";
		}
		
		model.addAttribute("department", department);
		
		/*Conjunction con = Restrictions.and(Restrictions.eq("snumber", snumber));
		con.add(Restrictions.eq("serialNo", serialNo));
		List<VRegNumber> regNumberList = this.vRegNumberDao.findByCriteria(0, 1000, "", false, con);
		if (regNumberList != null && regNumberList.size() > 0) {
			VRegNumber vRegNumber = regNumberList.get(0);
			
			model.addAttribute("regNumber", vRegNumber);
		} else {
			redirectAttributes.addAttribute("departmentId", departmentId);
			redirectAttributes.addAttribute("doctorId", doctorId);
			redirectAttributes.addAttribute("snumber", snumber);
			redirectAttributes.addAttribute("errMsg", "该号已被预约！");
			return "redirect:/numberlist";
		}*/
		
		// 常用就诊人列表
		Conjunction con = Restrictions.and();
		con.add(Restrictions.eq("patientId", getLoginUserInfo()
				.getPatietId()));
		List<UserRelation> list = userRelationDao.findByCriteria(false, con);
		List<ForegroundUser> foregroundUserList = foregroundRegistrationService
				.batchFindForegroundUserById(list);
		if (foregroundUserList == null ) {
			foregroundUserList = new ArrayList<ForegroundUser>();
		}
		foregroundUserList.add(getLoginUserInfo());
		
		model.addAttribute("userList", foregroundUserList);
		
		model.addAttribute("departmentId", departmentId);
		model.addAttribute("departmentName", departmentName);
		model.addAttribute("doctorId", doctorId);
		model.addAttribute("doctorName", doctorName);
		model.addAttribute("snumber", snumber);
		model.addAttribute("serialNo", serialNo);
		model.addAttribute("date", date);
		model.addAttribute("week", week);
		model.addAttribute("schedules", schedules);
		model.addAttribute("limitNums", limitNums);
		model.addAttribute("fee", fee);
		model.addAttribute("regDate", regDate);
		model.addAttribute("startTime", startTime);
		model.addAttribute("endTime", endTime);
		
		return "selfService/bookRegister/bookConfirm";
	}
	
	/**
	 * 预约
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@Operate(desc="预约挂号-创建订单")
	@RequestMapping(value = "/do", method = RequestMethod.POST)
	public String doBook(Model model, HttpServletRequest request) {
		try {
//			// 排班id
//			Integer sid = Integer.parseInt(request.getParameter("sid"));
//			// 就诊人id
//			Integer uid = Integer.parseInt(request.getParameter("uid"));
//			// 健康卡号
//			String hcardId = request.getParameter("hcardId");
			
			
//			Map<String, String> resMap = this.orderService.createOrder(sid,
//					uid, hcardId, this.foregroundUserDao
//							.findByPoid(getLoginUserInfo().getPoid()));
			
			String patient = request.getParameter("patient");
			String[] patients = patient.split("_");
			String patientId = patients[0];
			String patientName = patients[1];
			
			// 预约挂号
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("departmentId", request.getParameter("departmentId"));
			params.put("departmentName", request.getParameter("departmentName"));
			params.put("doctorId", request.getParameter("doctorId"));
			params.put("doctorName", request.getParameter("doctorName"));
			params.put("snumber", request.getParameter("snumber"));
			params.put("serialNo", request.getParameter("serialNo"));
			params.put("date", request.getParameter("date"));
			params.put("startTime", request.getParameter("startTime"));
			params.put("endTime", request.getParameter("endTime"));
			params.put("fee", request.getParameter("fee"));
			params.put("patientId", patientId);
			params.put("patientName", patientName);
			
			params.put("currentUser", getLoginUserInfo());
			
			Map<String, String> resMap = this.orderService.createOrder(params);
			
			if ("1".equals(resMap.get("resKey"))) {
				
				model.addAttribute("message", "预约成功，请及时到医院取号就诊！");
			} else {
				
				model.addAttribute("errMsg", resMap.get("message"));
			}
			
		} catch(AppRuntimeException e) {
			
			model.addAttribute("errMsg", e.getExplanation());
		} catch(Exception e) {
			
			model.addAttribute("errMsg", "数据异常，预约失败！");
		}
		
		return "selfService/bookRegister/bookResult";
	}
	
	/**
	 * 预约须知
	 * 
	 * @return
	 */
	@Operate(desc="预约须知")
	@ResponseBody
	@RequestMapping(value = "/notice", method = RequestMethod.POST)
	public String notice() {
		
		return "selfService/bookRegister/bookNotice";
	}
}
