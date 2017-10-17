package com.senyint.wx.mobile.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

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
import com.senyint.common.web.Constants;
import com.senyint.wx.common.dao.VDepartmentDao;
import com.senyint.wx.common.dao.VDoctorDao;
import com.senyint.wx.common.dao.VScheduleDao;
import com.senyint.wx.common.entity.Schedule;
import com.senyint.wx.common.entity.VDepartment;
import com.senyint.wx.common.entity.VSchedule;
import com.senyint.wx.common.web.Operate;

/**
 * @author wangchao
 *
 */
@Controller
@RequestMapping(value = "/scheduleIndex")
public class MBVScheduleAction {
	@Autowired
	private VScheduleDao vScheduleDao;
	
	@Autowired
	private VDepartmentDao vDepartmentDao;
	
	@Autowired
	private VDoctorDao vDoctorDao;
	
	
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
		return "selfService/scheduleSelect/vScheduleIndex";
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
		//获取当月1号起所有专家排班时间
		List scheduleDays = getScheduleDays(year,month,"1");
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
					outhtml.append("<td class=\"day\">"+days[i]);
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
		List<java.sql.Date> list = vScheduleDao.getScheduleDays(false,param);
		List scheduleDays = new ArrayList();
		//day
		for(java.sql.Date items : list){
			//Date scheduleDay = (Date) items[0];
			Calendar cld = Calendar.getInstance();
			cld.setTime(items);
			scheduleDays.add(cld.get(Calendar.DAY_OF_MONTH));
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
//		Date dateStart = DateUtils.strToDate(paramDate,"yyyy-MM-dd");
//		Date dateEnd = DateUtils.getFinallyDate(dateStart);
//		Map <String , Object> map = new HashMap<String,Object>();
//		map.put("dateStart", dateStart);
//		map.put("dateEnd", dateEnd);
		//String queryDate  = request.getParameter("queryDate");
		//获取一天的排班医生
		List<VSchedule> docSchedules = (List<VSchedule>) getDocSchedules(Integer.parseInt(pageNum), 20,false, paramDate);
		StringBuffer outhtml= new StringBuffer("");
		for(VSchedule vSchedule : docSchedules){
//			int doctorId = vSchedule.getDoctorId();
			int departmentId = vSchedule.getDepartmentId();
			//String jobtitle = vDoctorDao.findByPoid(doctorId).getJobTitle();
			VDepartment vDepartment = vDepartmentDao.findByPoid(departmentId);
			String departmentName = "";
			if(vDepartment != null){
				departmentName = vDepartment.getName();
			}
			outhtml.append("<tr>");
//			outhtml.append("<td>" + vSchedule.getNumberType() +"</td>");
			outhtml.append("<td>" + vSchedule.getDoctorName() +"</td>");
			outhtml.append("<td>" + departmentName +"</td>");
			if(vSchedule.getIsAllday()){
				outhtml.append("<td colspan=\"2\">"+vSchedule.getAlldayCounts() + "&nbsp&nbsp(全天)" +"</td>");
			}else{
				outhtml.append("<td>" + vSchedule.getAmCounts() +"</td>");
				outhtml.append("<td>" + vSchedule.getPmCounts() +"</td>");
			}
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
	public List<VSchedule> getDocSchedules(int firstResult, int maxResult,boolean cache,String queryDate){
		
		//获取某天的排班医生
		List< Object[]> listObj = vScheduleDao.getScheduleList(firstResult,maxResult,cache,queryDate);
		//某天医生排班列表
		//List<Schedule> doctorList = new ArrayList();
		//某天排班的医生
		List<Integer> doctorList1 = new ArrayList();
		for(Object[] items : listObj){
			Schedule schedule = new Schedule();
				Integer doctorId=(Integer)items[0];	
			//Doctor doc = doctorDao.findByPoid(doctorId);
			//schedule.setDoctor(doc);
//			String datetemp = (String)items[1];
//			schedule.setDate(DateUtils.strToDate(datetemp, "yyyy-MM-dd"));
			doctorList1.add(doctorId);
		}
		
		Conjunction con =  Restrictions.and();
		con.add(Restrictions.eq("date",DateUtils.strToDate(queryDate, "yyyy-MM-dd")));
		con.add(Restrictions.not(Restrictions.eq("numberType",Constants.outNumberType)));
		//con.add(Restrictions.le("date",dateRang.get("dateEnd")));
		//包含doctor的列表
		con.add(Restrictions.in("doctorId", doctorList1));
		List<VSchedule> slist = this.vScheduleDao.findByCriteria( false, con);
		Map<String, VSchedule> docSchedules = new TreeMap<String, VSchedule>();
		//sc.getDepartmentId() + "_" + sc.getDoctorId() 防止一个医生有多个科室标
		for (VSchedule sc : slist) {
			//第二次
			if (docSchedules.containsKey(sc.getDepartmentId() + "_" + sc.getDoctorId())) {
				VSchedule schTmp = docSchedules.get(String.valueOf(sc.getDoctorId()));
				if(schTmp !=null){
					if ("上午".equals(sc.getSchedules())) {
						schTmp.setAmCounts(schTmp.getAmCounts() + sc.getLimitFee());
						sc.setIsAllday(false);
					} else if ("下午".equals(sc.getSchedules())) {
						schTmp.setPmCounts(schTmp.getPmCounts() + sc.getLimitFee());
						sc.setIsAllday(false);
					}else if("全日".equals(sc.getSchedules())){
						//schTmp.setPmCounts(schTmp.getallCounts() + sc.getLimitNums());
						sc.setIsAllday(true);
					}
					docSchedules.put(sc.getDepartmentId() + "_" + sc.getDoctorId(), schTmp);
				}
			} else {
				//第一次
				if ("上午".equals(sc.getSchedules())) {
					sc.setAmCounts(sc.getLimitFee());
					sc.setPmCounts(0);
					sc.setIsAllday(false);
					
				} else if ("下午".equals(sc.getSchedules())) {
					sc.setAmCounts(0);
					sc.setPmCounts(sc.getLimitFee());
					sc.setIsAllday(false);
				}else if("全日".equals(sc.getSchedules())){
					sc.setAmCounts(0);
					sc.setPmCounts(0);
					sc.setAlldayCounts(sc.getLimitFee());
					sc.setIsAllday(true);
				}
				docSchedules.put(sc.getDepartmentId() + "_" + sc.getDoctorId(), sc);
			}
		} 
		List<VSchedule> docSchedules1 = new ArrayList<VSchedule>();
		 for(Entry<String, VSchedule> entry : docSchedules.entrySet()) {
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
//		param.put("dateStart", DateUtils.strToDate(paramDate, "yyyy-MM-dd"));
//		param.put("dateEnd", DateUtils.strToDate(endTime, "yyyy-MM-dd hh:mm:ss"));
		int datasum = vScheduleDao.getDocCountsInday(false,paramDate);
		model.addAttribute("datasum", datasum);
		//传递查询条件
		model.addAttribute("paramDate", paramDate);
		model.addAttribute("endTime", endTime);
		return "selfService/scheduleSelect/vScheduleList";
	}

	public VScheduleDao getvScheduleDao() {
		return vScheduleDao;
	}

	public void setvScheduleDao(VScheduleDao vScheduleDao) {
		this.vScheduleDao = vScheduleDao;
	}

	public VDepartmentDao getvDepartmentDao() {
		return vDepartmentDao;
	}

	public void setvDepartmentDao(VDepartmentDao vDepartmentDao) {
		this.vDepartmentDao = vDepartmentDao;
	}

	public VDoctorDao getvDoctorDao() {
		return vDoctorDao;
	}

	public void setvDoctorDao(VDoctorDao vDoctorDao) {
		this.vDoctorDao = vDoctorDao;
	}
	
}
