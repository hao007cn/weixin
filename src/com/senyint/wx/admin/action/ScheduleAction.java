package com.senyint.wx.admin.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.senyint.common.util.DateUtils;
import com.senyint.common.web.ajax.AjaxResult;
import com.senyint.common.web.ajax.GridParam;
import com.senyint.common.web.ajax.GridResult;
import com.senyint.wx.common.dao.DepartmentDao;
import com.senyint.wx.common.dao.DoctorDao;
import com.senyint.wx.common.dao.ScheduleDao;
import com.senyint.wx.common.entity.Department;
import com.senyint.wx.common.entity.Doctor;
import com.senyint.wx.common.entity.Schedule;
import com.senyint.wx.common.utils.ArgumentUtil;
import com.senyint.wx.common.web.Operate;

/**
 * 排班Action
 * 
 * @author sunzhi
 *
 */
@Controller
@RequestMapping(value = "/schedule")
public class ScheduleAction extends SupportAction {
	
	@Autowired
	private DoctorDao doctorDao;
	
	@Autowired
	private ScheduleDao scheduleDao;
	
	@Autowired
	private DepartmentDao departmentDao;
	
	/**
	 * 排班主页
	 * 
	 * @return
	 */
	@Operate(desc="排班主页")
	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		
		List<Doctor> doctorList = doctorDao.findByCriteria(false, Restrictions.eq("enabled", true));
		
		model.addAttribute("doctorList", doctorList);
		
		model.addAttribute("dateStart", ArgumentUtil.getSysDate());
		
		return "scheduling";
	}
	
	/**
	 * 专家列表
	 * @param model
	 * @param request
	 * @return
	 */
	@ResponseBody 
	@RequestMapping(value = "/getDoctorList" ,method = RequestMethod.POST)
	public List<Doctor> getDoctorList(Model model, HttpServletRequest request){
		String departmentId = request.getParameter("departmentId");
//		Department department = this.departmentDao.findByPoid(Integer.valueOf(departmentId));
//		Conjunction con =  Restrictions.and();
//		con.add(Restrictions.eq("enabled", true));
//		con.add(Restrictions.eq("department",department));
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("departmentId", departmentId);
		params.put("enabled", true);
		List<Doctor> doctorList = doctorDao.findDoctorList(0, 1000, "doctor.name asc", false, params);
		return doctorList;
	}

	/**
	 * 加载排班Grid
	 * 
	 * @param gp grid传来参数
	 * @param request
	 * @return
	 */
	@Operate(desc = "加载排班Grid")
	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public GridResult list(GridParam gp, HttpServletRequest request) {
		
		// 条件
		Map<String, Object> params = new HashMap<String, Object>();
		
		if (StringUtils.isNotBlank(request.getParameter("name"))) {
			params.put("doctorName", request.getParameter("name"));
		}
		
		if (StringUtils.isNotBlank(request.getParameter("departmentId"))) {
			params.put("departmentId", request.getParameter("departmentId"));
		}
		
		if (StringUtils.isNotBlank(request.getParameter("dateStart"))) {
			params.put("dateStart", request.getParameter("dateStart"));
		}
		
		if (StringUtils.isNotBlank(request.getParameter("dateEnd"))) {
			params.put("dateEnd", request.getParameter("dateEnd"));
		}
		
		// 查询条数
//		int totalCount = this.scheduleDao.fetchCountByCriteria(false, con);
//		
////		scheduleDao.
//		
//		// 列表结果
//		List<Schedule> scheduleList = this.scheduleDao.findByCriteria(gp.getStartRow(), gp.getRows(), "date asc", false, con);
		
		int totalCount = this.scheduleDao.findCount(params);
		
		List<Schedule> scheduleList = this.scheduleDao.findList(gp.getStartRow(), gp.getRows(), params);
		
		// 返回数据
		return gridResult(gp, totalCount, scheduleList);
	}

	/**
	 * 删除排班
	 * 
	 * @param 排班id
	 * @param request
	 * @return
	 */
	@Operate(desc = "删除排班")
	@ResponseBody
	@RequestMapping(value = "/delete")
	public AjaxResult delete(String id) {
		
		if (StringUtils.isBlank(id)) {
			return ajaxFail("请选择记录！");
		}
		
		String[] idarr = id.split(",");
		List<Schedule> scheduleList = new ArrayList<Schedule>();
		Date now = ArgumentUtil.getSysDate();
		for (String idtmp : idarr) {
			if (StringUtils.isNotBlank(idtmp)) {
				Schedule schedule = this.scheduleDao.findByPoid(Integer.valueOf(idtmp));
				if (now.compareTo(schedule.getDate()) <= 0) {//历史排班不删除
					scheduleList.add(schedule);
				}
			}
		}
		
		if (scheduleList.size() > 0) {
			this.scheduleDao.makeTransient(scheduleList);
		}
		
		return ajaxSuccess("删除成功！");
	}
	
	/**
	 * 保存排班
	 * 
	 * @return ajax 结果
	 */
	@Operate(desc = "保存排班")
	@ResponseBody
	@RequestMapping(value = "/save")
	public AjaxResult save(HttpServletRequest request) {
		Schedule schedule = new Schedule();
		String[] weekArr = request.getParameterValues("weekArr");
		if (weekArr == null) {
			weekArr = request.getParameterValues("weekArr[]");
		}
		schedule.setWeekArr(weekArr);
		schedule.setDateStart(DateUtils.strToDate(request.getParameter("dateStart"), "yyyy-MM-dd"));
		schedule.setDateEnd(DateUtils.strToDate(request.getParameter("dateEnd"), "yyyy-MM-dd"));
		// 科室
		String departmentId = request.getParameter("departmentId");
		Department department = this.departmentDao.findByPoid(Integer.parseInt(departmentId));
		schedule.setDepartment(department);
		// 专家
		String[] doctorIds = request.getParameterValues("doctorIds");
		String amCounts = request.getParameter("amCounts");
		String pmCounts = request.getParameter("pmCounts");
		
		if(StringUtils.isNotBlank(amCounts)){
			schedule.setAmCount(Integer.valueOf(amCounts));
		}
		if(StringUtils.isNotBlank(pmCounts)){
			schedule.setPmCount(Integer.valueOf(pmCounts));
		}
		
		if (doctorIds == null) {
			doctorIds = request.getParameterValues("doctorIds[]");
		}
		schedule.setDoctorIds(doctorIds);

		String[] aps = request.getParameterValues("aps");
		if (aps == null) {
			aps = request.getParameterValues("aps[]");
		}
		schedule.setDoctorIds(doctorIds);
		schedule.setAps(aps);
		//?schedule.setCounts(Integer.valueOf(request.getParameter("counts")));
		schedule.setLeftCounts(schedule.getCounts());
		
		String errMsg = "";
		
		// 检查参数
		if (schedule.getDateStart() == null
				|| schedule.getDateEnd() == null) {
				errMsg += "请正确选择日期!";
			}
		if (schedule.getWeekArr() == null
			|| schedule.getWeekArr().length <= 0) {
			errMsg += "\n请选择星期!";
		}
		if (schedule.getDoctorIds() == null
				|| schedule.getDoctorIds().length <= 0) {
			errMsg += "\n请选择专家!";
		}
		if (schedule.getAps() == null
				|| schedule.getAps().length <= 0) {
			errMsg += "\n请选择上下午!";
		}
		if (StringUtils.isNotBlank(errMsg)) {
			return ajaxFail(errMsg);
		}
		
		// 周
//		String[] weekArr = schedule.getWeeks().split(",");
		
		// 不可对过去日期排班
		if (schedule.getDateStart().compareTo(ArgumentUtil.getSysDate()) < 0) {
			schedule.setDateStart(DateUtils.getStartDate(ArgumentUtil.getSysDate()));
		}
		
		// 开始日期
		Calendar start = Calendar.getInstance();
		start.setTime(schedule.getDateStart());
		// 结束日期
		Calendar end = Calendar.getInstance();
		end.setTime(schedule.getDateEnd());
		
		List<Doctor> docList = new ArrayList<Doctor>();
		for (String docId : schedule.getDoctorIds()) {
			if (StringUtils.isNotBlank(docId)) {
				Doctor doc = new Doctor();
				doc.setPoid(Integer.valueOf(docId));
				
				docList.add(doc);
			}
		}
		
		// 专家列表
		List<Doctor> doctorList = doctorDao.findByPoid(docList, false);
		
		// 用于存放排班明细
		List<Schedule> scheduleList = new ArrayList<Schedule>();
		Date optDate = ArgumentUtil.getSysDate();
		
		// 循环日期
		while(start.compareTo(end) <= 0) {
			// 星期过渡
			if (ArrayUtils.contains(weekArr,
					String.valueOf(start.get(Calendar.DAY_OF_WEEK)))) {
				// 循环专家
				for (Doctor doc : doctorList) {
					for (String ap : schedule.getAps()) {
						// 查询该医生该日期是否已有排班。
						Conjunction con = Restrictions.and();
						con.add(Restrictions.eq("doctor.poid", doc.getPoid()));
						con.add(Restrictions.eq("date", start.getTime()));
						con.add(Restrictions.eq("ap", ap));
						int count = scheduleDao.fetchCountByCriteria(false, con);
						
						if (count > 0) { // 排班已存在，跳过该条，处理下一条
							continue;
						}
						
						// 新排班
						Schedule scheduleNew = new Schedule();
						scheduleNew.setDate(start.getTime());
						scheduleNew.setDepartment(schedule.getDepartment());
						scheduleNew.setDoctor(doc);
						scheduleNew.setAp(ap);
						//
						if(ap.equals("am")){
							scheduleNew.setCounts(schedule.getAmCount());
						}else{
							scheduleNew.setCounts(schedule.getPmCount());
						}
						scheduleNew.setLeftCounts(scheduleNew.getCounts());
						scheduleNew.setCreateDate(optDate);
						scheduleNew.setCreateUserId(String.valueOf(getLoginUserId()));
						scheduleNew.setCreateUserName(getLoginUserRealName());
						scheduleNew.setModifyDate(optDate);
						scheduleNew.setModifyUserId(String.valueOf(getLoginUserId()));
						scheduleNew.setModifyUserName(getLoginUserRealName());

						
						scheduleList.add(scheduleNew);
					}
				}
			}
			
			start.set(Calendar.DATE, start.get(Calendar.DATE) + 1);
		}
		
		scheduleDao.makePersistent(scheduleList);
		
		return ajaxSuccess("保存成功！");
	}
}
