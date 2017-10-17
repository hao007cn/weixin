package com.senyint.wx.mobile.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

import com.senyint.common.util.DateUtils;
import com.senyint.wx.common.dao.DoctorDao;
import com.senyint.wx.common.dao.ScheduleDao;
import com.senyint.wx.common.entity.Doctor;
import com.senyint.wx.common.entity.Schedule;
import com.senyint.wx.common.web.Operate;

/**
 * @author wangchao
 *
 */
@Controller
@RequestMapping(value = "/scheduleIndex_back")
public class MBScheduleAction {
	@Autowired
	private	ScheduleDao scheduleDao;
	@Autowired
	private DoctorDao doctorDao;
	
	
	/**
	 * 排班日历索引页
	 * @param model
	 * @return
	 */
	@Operate(desc="排班查询-首页")
	@RequestMapping(method = RequestMethod.GET)
	public String scheduleIndex(Model model) {
		GregorianCalendar currentDay = new GregorianCalendar();
		int month=currentDay.get(Calendar.MONTH);
		int year= currentDay.get(Calendar.YEAR);
		model.addAttribute("month", month+1);
		model.addAttribute("year", year);
		return "selfService/scheduleSelect/scheduleIndex";
	}

	/**
	 * 创建排班日历
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/createCarlendar" , method = RequestMethod.POST )
	@ResponseBody
	public Map createCarlendar(HttpServletRequest request){
		Map map = new HashMap();
		String year= request.getParameter("year");
		String month= request.getParameter("month");
		String day= request.getParameter("day");
		//获取当月某天到月末所有专家排班时间
		List scheduleDays = getScheduleDays(year,month,day);
		//日历初始化
		String outhtml = initCalendar(year,month,day);
		map.put("scheduleDays", scheduleDays);
		map.put("outhtml", outhtml);
		return map;
	}
	
	
	/**
	 * 日历初始化
	 * @return
	 */
	public String initCalendar(String year, String month, String day) {
		String days[] = new String[42];
		for (int i = 0; i < 42; i++) {
			days[i]="";
		}
		Calendar thisMonth=Calendar.getInstance();
		thisMonth.set(Calendar.MONTH, Integer.valueOf(month)-1);
		thisMonth.set(Calendar.YEAR, Integer.valueOf(year));
		//设置周末为第一天
		thisMonth.setFirstDayOfWeek(Calendar.SUNDAY);
		thisMonth.set(Calendar.DAY_OF_MONTH,1);
		//获得每月第一天是周几
		int firstIndex=thisMonth.get(Calendar.DAY_OF_WEEK)-1;
		int maxIndex=thisMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
		for(int i=0;i<maxIndex;i++){
			days[firstIndex+i]=String.valueOf(i+1);
		}
		StringBuffer outhtml= new StringBuffer("");
		for(int j=0;j<6;j++) {
			outhtml.append("<tr>");
			 for(int i=j*7;i<(j+1)*7;i++){
				
				if((i-firstIndex+1)== Integer.valueOf(day)){
					 outhtml.append("<td class=\"day active\">"+days[i]);
				}else{
					 outhtml.append("<td class=\"day\">" + days[i]);
				}
				 outhtml.append("</td>");
			}
			outhtml.append("</tr>");
		}
		return outhtml.toString();
	}
	
	/**获取当月某天到月末所有专家排班时间
	 * @param year 年
	 * @param month 月
	 * @param day 天
	 * @return
	 */
	public List getScheduleDays(String year, String month,String day){
		Calendar thisMonth=Calendar.getInstance();
		thisMonth.set(Calendar.MONTH, (Integer.parseInt(month)-1));
		thisMonth.set(Calendar.YEAR, Integer.parseInt(year) );
		thisMonth.setFirstDayOfWeek(Calendar.SUNDAY);
		int maxIndex=thisMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		if (month.length() < 2){
			month = "0" + month;
		}
		if(StringUtils.isEmpty(day)){
			day = 0 + String.valueOf(1);
		}
		////////////////////////////////////????
		String startDay = year + "-" + month + "-" + day;
		String endDay =  year + "-" + month +"-"+ maxIndex;
		//criteria.setProjection(Projections.groupProperty("age"))
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("dateStart", startDay);
		param.put("dateEnd", endDay);
		//获取某一个月排班天集合
		List< Object[] > list = scheduleDao.getScheduleDays(param);
		List scheduleDays = new ArrayList();
		//day
		for(Object[] items : list){
			Double scheduleDay = (Double) items[0];
			scheduleDays.add(scheduleDay);
		}
		return scheduleDays;
	}
	
	/**
	 * 添加医生排班数据
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "append" , method=RequestMethod.POST)
	@ResponseBody
	public String appendDate(Model model, HttpServletRequest request){
		String pageNum= request.getParameter("pageNum");
		String paramDate= request.getParameter("paramDate");
		//String endTime= request.getParameter("endTime");
		Date dateStart = DateUtils.strToDate(paramDate,"yyyy-MM-dd");
		Date dateEnd = DateUtils.getFinallyDate(dateStart);
		Map <String , Object> map = new HashMap<String,Object>();
		map.put("dateStart", dateStart);
		map.put("dateEnd", dateEnd);
		//获取一天的排班医生
		List<Schedule> docSchedules = (List<Schedule>) getDocSchedules(Integer.parseInt(pageNum), 10, map);
		StringBuffer outhtml= new StringBuffer("");
		for(Schedule schedule : docSchedules){
			outhtml.append("<tr>");
			outhtml.append("<td>" + schedule.getDepartmentName() +"</td>");
			outhtml.append("<td>" + schedule.getDoctor().getJobTitle() +"</td>");
			outhtml.append("<td>" + schedule.getDoctor().getName() +"</td>");
			outhtml.append("<td>" + schedule.getAmCount() +"</td>");
			outhtml.append("<td>" + schedule.getPmCount() +"</td>");
			outhtml.append("</tr>");
		}
		return outhtml.toString();
	}
	/**
	 * 获取一天排班医生
	 * @param firstResult
	 * @param maxResult
	 * @param dateRang
	 * @return
	 */
	public List<Schedule> getDocSchedules(int firstResult, int maxResult,Map <String , Object> dateRang){
		
		//获取某天的排班医生
		List< Object[]> listObj = scheduleDao.getScheduleList(firstResult,maxResult,dateRang,"");
		//某天医生排班列表
		//List<Schedule> doctorList = new ArrayList();
		//某天排班的医生
		List<Doctor> doctorList1 = new ArrayList();
		for(Object[] items : listObj){
			Schedule schedule = new Schedule();
			Integer doctorId = (Integer)items[0];
			Doctor doc = doctorDao.findByPoid(doctorId);
			schedule.setDoctor(doc);
			String datetemp = (String)items[1];
			schedule.setDate(DateUtils.strToDate(datetemp, "yyyy-MM-dd"));
			doctorList1.add(schedule.getDoctor());
		}
		
		Conjunction con =  Restrictions.and();
		con.add(Restrictions.ge("date",dateRang.get("dateStart")));
		con.add(Restrictions.le("date",dateRang.get("dateEnd")));
		//包含doctor的列表
		con.add(Restrictions.in("doctor", doctorList1));
		List<Schedule> slist = this.scheduleDao.findByCriteria( false, con);

		Map<Doctor, Schedule> docSchedules = new HashMap<Doctor, Schedule>();
		for (Schedule sc : slist) {
			//第二次
			if (docSchedules.containsKey(sc.getDoctor())) {
				Schedule schTmp = docSchedules.get(sc.getDoctor());
				if ("am".equals(sc.getAp())) {
					schTmp.setAmCount(schTmp.getAmCount() + sc.getCounts());
				} else if ("pm".equals(sc.getAp())) {
					schTmp.setPmCount(schTmp.getPmCount() + sc.getCounts());
				}
				docSchedules.put(sc.getDoctor(), schTmp);
			} else {
				//第一次
				if ("am".equals(sc.getAp())) {
					sc.setAmCount(sc.getCounts());
					sc.setPmCount(0);
				} else if ("pm".equals(sc.getAp())) {
					sc.setAmCount(0);
					sc.setPmCount(sc.getCounts());
				}
				docSchedules.put(sc.getDoctor(), sc);
			}
		} 
		List<Schedule> docSchedules1 = new ArrayList<Schedule>();
		 for(Entry<Doctor, Schedule> entry : docSchedules.entrySet()) {
			 docSchedules1.add(entry.getValue());
			}
		return docSchedules1;
	}
	
	/**
	 * 某日排班医生初始化列表页面
	 * @param paramDate
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/scheduleList" , method = RequestMethod.GET)
	public String index(String paramDate , Model model){
		String endTime = paramDate + " 23:59:59";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("dateStart", DateUtils.strToDate(paramDate, "yyyy-MM-dd"));
		param.put("dateEnd", DateUtils.strToDate(endTime, "yyyy-MM-dd hh:mm:ss"));
		int datasum = scheduleDao.getDocCountsInday(param);
		model.addAttribute("datasum", datasum);
		//传递查询条件
		model.addAttribute("paramDate", paramDate);
		model.addAttribute("endTime", endTime);
		return "selfService/scheduleSelect/scheduleList";
	}
	
	/**
	 * 获取某一天doctorId的数量
	 * @param startDay
	 * @param endDate
	 * @param doctorId
	 * @param object
	 * @return
	 */
	private int getSum(String startDay ,String endDate, int doctorId,Object object){
		Conjunction con = Restrictions.and();
		con.add(Restrictions.ge("date", DateUtils.strToDate(startDay, "yyyy-MM-dd")));
		con.add(Restrictions.le("date",DateUtils.strToDate(endDate, "yyyy-MM-dd hh:mm:ss")));
		con.add(Restrictions.eq("doctor", object));
		int sum = scheduleDao.fetchCountByCriteria(false, con);
		return sum;
	}
	
	/**去除重复元素
	 * @param li
	 * @return
	 */
	public List<String> getNewList(List<String> li) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < li.size(); i++) {
			String str = li.get(i); // 获取传入集合对象的每一个元素
			if (!list.contains(str)) { // 查看新集合中是否有指定的元素，如果没有则加入
				list.add(str);
			}
		}
		return list; // 返回集合
	}
	
	public ScheduleDao getScheduleDao() {
		return scheduleDao;
	}

	public void setScheduleDao(ScheduleDao scheduleDao) {
		this.scheduleDao = scheduleDao;
	}

	public DoctorDao getDoctorDao() {
		return doctorDao;
	}

	public void setDoctorDao(DoctorDao doctorDao) {
		this.doctorDao = doctorDao;
	}
}
