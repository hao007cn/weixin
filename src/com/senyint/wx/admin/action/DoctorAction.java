package com.senyint.wx.admin.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.senyint.common.util.FileUtils;
import com.senyint.common.web.ajax.AjaxResult;
import com.senyint.common.web.ajax.GridParam;
import com.senyint.common.web.ajax.GridResult;
import com.senyint.wx.admin.utils.AdminWebUtils;
import com.senyint.wx.common.dao.DepartmentDao;
import com.senyint.wx.common.dao.DoctorDao;
import com.senyint.wx.common.dao.JobTitleDao;
import com.senyint.wx.common.entity.Department;
import com.senyint.wx.common.entity.Doctor;
import com.senyint.wx.common.entity.JobTitle;
import com.senyint.wx.common.utils.ArgumentUtil;
import com.senyint.wx.common.utils.PropertyUtil;
import com.senyint.wx.common.web.Constants;
import com.senyint.wx.common.web.Operate;

/**
 * 专家Action
 * 
 * @author sunzhi
 *
 */
@Controller
@RequestMapping(value = "/doctor")
public class DoctorAction extends SupportAction {
	
	@Autowired
	private DoctorDao doctorDao;
	
	@Autowired
	private DepartmentDao departmentDao;
	
	@Autowired
	private JobTitleDao jobTitleDao;
	
	/**
	 * 专家列表页面
	 * 
	 * @return
	 */
	@Operate(desc = "专家列表页面")
	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		Conjunction con = Restrictions.and();
		con.add(Restrictions.eq("enabled", true));
		con.add(Restrictions.eq("deleteFlg", false));
		List<JobTitle> jobTitleList = jobTitleDao.findByCriteria(false, con);
		model.addAttribute("jobTitleList", jobTitleList);
		return "doctor";
	}

	/**
	 * 加载专家Grid
	 * 
	 * @param gp grid传来参数
	 * @param request
	 * @return
	 */
	@Operate(desc = "加载专家Grid")
	@ResponseBody
	@RequestMapping(value = "/list")
	public GridResult list(GridParam gp, HttpServletRequest request) {
		// 条件
		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("enabled", true);
		
		if (StringUtils.isNotBlank(request.getParameter("name"))) {
			params.put("name", request.getParameter("name"));
		}
		
		if (StringUtils.isNotBlank(request.getParameter("departmentId"))) {
			params.put("departmentId", request.getParameter("departmentId"));
		}
		int totalCount = doctorDao.fetchDoctorCount(false, params);
		
		// 条件
//		Conjunction con = Restrictions.and();
//		
//		if (StringUtils.isNotBlank(request.getParameter("name"))) {
//			con.add(Restrictions.like("name", "%" + request.getParameter("name") + "%"));
//		}
//		
//		if (StringUtils.isNotBlank(request.getParameter("departmentId"))) {
//			con.add(Restrictions.eq("department.poid", Integer.valueOf(request.getParameter("departmentId"))));
//		}
//		
//		// 查询条件
//		int totalCount = this.doctorDao.fetchCountByCriteria(false, con);
		
		// 列表结果
//		List<Doctor> docList = this.doctorDao.findByCriteria(gp.getStartRow(), gp.getRows(), "poid desc", false, con);
		List<Doctor> docList = this.doctorDao.findDoctorList(gp.getStartRow(), gp.getRows(), "doctor.name asc", false, params);
		
		// 返回数据
		return gridResult(gp, totalCount, docList);
	}

	/**
	 * 专家详情(json)
	 * 
	 * @param 专家id
	 * @param request
	 * @return
	 */
	@Operate(desc = "专家详情(json)")
	@ResponseBody
	@RequestMapping(value = "/detail")
	public Doctor detail(Integer id) {
		
		return this.doctorDao.findByPoid(id);
	}

	/**
	 * 删除专家
	 * 
	 * @param 专家id
	 * @param request
	 * @return
	 */
	@Operate(desc = "删除专家")
	@ResponseBody
	@RequestMapping(value = "/delete")
	public AjaxResult delete(String id) {
		
		if (StringUtils.isBlank(id)) {
			return ajaxFail("请选择记录！");
		}
		
		String[] idarr = id.split(",");
		List<Doctor> docList = new ArrayList<Doctor>();
		for (String idtmp : idarr) {
			if (StringUtils.isNotBlank(idtmp)) {
				Doctor theDoctor = this.doctorDao.findByPoid(Integer.valueOf(idtmp));
//				theDoctor.removeAllDepartments();
				docList.add(theDoctor);
			}
		}
		
		if (docList.size() > 0) {
			this.doctorDao.makeTransient(docList);
		}
		
		return ajaxSuccess("删除成功！");
	}
	
	/**
	 * 保存专家
	 * 
	 * @return ajax 结果
	 * @throws IOException 
	 */
	@Operate(desc = "保存专家")
	@ResponseBody
	@RequestMapping(value = "/save")
	public AjaxResult save(Doctor doctor, HttpServletRequest request) throws IOException {
		
		String picPath = doctor.getPicPath();
		
		if (doctor.getFee() != null) {
//			doctor.setFee(doctor.getFee().setScale(2, BigDecimal.ROUND_HALF_UP));
		}
		
		if (doctor.getPoid() != null) { // 更新
			Doctor doctorOld = this.doctorDao.findByPoid(doctor.getPoid());
			if (doctorOld != null) {
				doctorOld.setName(doctor.getName());
//				doctorOld.setLetters(doctor.getLetters());
				doctorOld.setSex(doctor.getSex());
				doctorOld.setPosition(doctor.getPosition());
				doctorOld.setJobTitle(doctor.getJobTitle());
				doctorOld.setFee(doctor.getFee());
				doctorOld.setOutCallTime(doctor.getOutCallTime());
//				doctorOld.setEnabled(doctor.isEnabled());
				doctorOld.setDesc(doctor.getDesc());
				doctorOld.setRemarks(doctor.getRemarks());
//				doctorOld.setDepartment(doctor.getDepartment());
				
//				doctorOld.removeAllDepartments();
				// 所属科室
				String deptIds = doctor.getDepartmentId();
				String[] deptIdarr = deptIds.split(",");
				for (String deptId : deptIdarr) {
					if (StringUtils.isNotBlank(deptId)) {
						Department department = this.departmentDao.findByPoid(Integer.parseInt(deptId));
//						doctorOld.addDepartment(department);
					}
				}
				doctor = this.doctorDao.makePersistent(doctorOld);
			} else {
				
				// 所属科室
				String deptIds = doctor.getDepartmentId();
				String[] deptIdarr = deptIds.split(",");
				for (String deptId : deptIdarr) {
					if (StringUtils.isNotBlank(deptId)) {
						Department department = this.departmentDao.findByPoid(Integer.parseInt(deptId));
//						doctor.addDepartment(department);
					}
				}
				doctor = this.doctorDao.makePersistent(doctor);
			}
		} else {
			
			// 所属科室
			String deptIds = doctor.getDepartmentId();
			String[] deptIdarr = deptIds.split(",");
			for (String deptId : deptIdarr) {
				if (StringUtils.isNotBlank(deptId)) {
					Department department = this.departmentDao.findByPoid(Integer.parseInt(deptId));
//					doctor.addDepartment(department);
				}
			}
			doctor = this.doctorDao.makePersistent(doctor);
		}
		
		// 保存照片
		if (StringUtils.isNotBlank(picPath)) {
			String tarPath = PropertyUtil.getSysVal(Constants.DOCTOR_PIC_DIR_KEY,true)
					+ File.separator + Constants.DOCTOR_PIC_PREFIX
					+ doctor.getPoid();
			String startPath = PropertyUtil.getSysVal(Constants.DOCTOR_PIC_DIR_KEY,true)+ File.separator;
			//-----------如果没有目录则新建目录
			File file = new File(startPath);
			if(file!=null&&!file.exists()){
				file.mkdirs();
			}
			//------------新建目录结束
			//------------初始化default照片
			String rootDir = AdminWebUtils.getContextRealPath();
			String defaultPath = PropertyUtil.getSysVal(Constants.DOCTOR_PIC_DEFAULT,true);
			File defaultFile = new File(defaultPath);
			if(defaultFile!=null&&!defaultFile.exists())
			{
				String defaultPhoto=rootDir+"assets"+"\\"+"images"+"\\"+"default";
				defaultPhoto=defaultPhoto.replaceAll("\\\\", "\\" + File.separator).replaceAll("/", "\\" + File.separator);
				File defaultPhotoFile= new File(defaultPhoto);
				FileUtils.copyFile(defaultPhotoFile, startPath+ File.separator+"default");
			}
			
			picPath = picPath.replaceAll("\\\\", "\\" + File.separator).replaceAll("/", "\\" + File.separator);
			picPath = rootDir + picPath;
			
			File pic = new File(picPath);
			if (pic.exists()) {
				FileUtils.copyFile(pic, tarPath);
			}
		}
		
		return ajaxSuccess("保存成功！");
	}

	/**
	 * 上传专家照片
	 * 
	 * @return 
	 * @throws IOException 
	 */
	@Operate(desc = "上传保存专家")
	@RequestMapping(value = "/upload")
	public String upload(@RequestParam("photo") MultipartFile file,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// 处理照片
		String resFlg = "0";
		String msg = "上传失败！";
		String picPath = "";
		String urlPath = "";
		if (file != null && !file.isEmpty()) {
			String fileName = Constants.DOCTOR_PIC_PREFIX + ArgumentUtil.getSysDate().getTime();
			String rootDir = AdminWebUtils.getSessionTmpDir(request.getSession());
			picPath = rootDir + File.separator + fileName;
			File tarFile = new File(picPath);
			try {
				//String fileName = file.getOriginalFilename();
				//String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
				file.transferTo(tarFile);
				resFlg = "1";
				msg = "上传成功！";
			} catch (IOException e) {
				resFlg = "0";
				msg = "上传失败！";
			}
			
			urlPath = AdminWebUtils.getSessionTmpDirUrl() + "/" + fileName;
		}
		
		String str = "<script>parent.callback('" + resFlg + "','" + msg + "','"
				+ urlPath + "')</script>";
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write(str);
		out.flush();
		out.close();
		
		return null;
	}
	/**
	 * 删除专家照片
	 * 
	 * @return 
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value = "/deletephoto")
	public String deletePhoto(String id) {
		String tarPath = PropertyUtil.getSysVal(Constants.DOCTOR_PIC_DIR_KEY,true)
				+ File.separator + Constants.DOCTOR_PIC_PREFIX
				+ id;
		File delFile = new File(tarPath);
		delFile.delete();
		return "true";
	}

	public JobTitleDao getJobTitleDao() {
		return jobTitleDao;
	}

	public void setJobTitleDao(JobTitleDao jobTitleDao) {
		this.jobTitleDao = jobTitleDao;
	}
	
	
}
