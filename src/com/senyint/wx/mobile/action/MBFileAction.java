package com.senyint.wx.mobile.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.senyint.common.util.FileUtils;
import com.senyint.wx.common.utils.PropertyUtil;
import com.senyint.wx.common.web.Constants;

/**
 * 文件管理Action
 * 
 * @author sunzhi
 * 
 */
@Controller
@RequestMapping(value = "/file")
public class MBFileAction {
	static final String REMOTE_PREX = "http://172.16.99.30:81/Web/images/";
	static final String NEW_REMOTE_PREX = "http://10.1.2.30:81/Web/images/";
	
	@RequestMapping("/{type}/{id}")
	public String file(@PathVariable("type") String type, @PathVariable("id") String id,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		java.io.BufferedInputStream bis = null;
		java.io.BufferedOutputStream bos = null;
		
		String path = "";
		String fileName = "";
		String solutePath = "";
		if (Constants.FILE_TYPE_DOCTOR.equals(type)) {
			path = PropertyUtil.getSysVal(Constants.DOCTOR_PIC_DIR_KEY, true);
			fileName = Constants.DOCTOR_PIC_PREFIX + id;
			solutePath = path + File.separator + fileName;
			File f = new File(solutePath);
			if (!f.exists()) {
				// 从远程下载
				String rname = request.getParameter("rname");
				try {
					rname = rname.replaceAll(PropertyUtil.getSysVal(Constants.IMAGE_SERVER_IP_OLD, true), PropertyUtil.getSysVal(Constants.IMAGE_SERVER_IP_NEW, true));
					FileUtils.copyRemoteFile(rname, solutePath);
				} catch (Exception e) {
				}
				f = new File(solutePath);
				if (!f.exists()) {
					solutePath = PropertyUtil.getSysVal(Constants.DOCTOR_PIC_DEFAULT, true);
					fileName = "default";
				}
			}
			// 
			
		} else if (Constants.FILE_TYPE_ADMIN.equals(type)) {
			path = PropertyUtil.getSysVal(Constants.ADMIN_PIC_DIR_KEY,true);
			fileName = Constants.ADMIN_PIC_PREFIX + id;
			solutePath = path + File.separator + fileName;
			File f = new File(solutePath);
			if (!f.exists()) {
				solutePath = PropertyUtil.getSysVal(Constants.ADMIN_PIC_DEFAULT,true);
				fileName = "default";
			}
		} else if (Constants.FILE_TYPE_USER.equals(type)) {
			path = PropertyUtil.getSysVal(Constants.USER_PIC_DIR_KEY,true);
			fileName = Constants.USER_PIC_PREFIX + id;
			solutePath = path + File.separator + fileName;
			File f = new File(solutePath);
			if (!f.exists()) {
				solutePath = PropertyUtil.getSysVal(Constants.USER_PIC_DEFAULT,true);
				fileName = "default";
			}
		} else {
			solutePath = PropertyUtil.getSysVal(Constants.PIC_DEFAULT_KEY,true);
			fileName = "default";
		}
		
		try {
			long fileLength = new File(solutePath).length();
			response.setContentType("application/x-msdownload;");
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(fileName.getBytes("utf-8"), "ISO8859-1"));
			response.setHeader("Content-Length", String.valueOf(fileLength));
			bis = new BufferedInputStream(new FileInputStream(solutePath));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println(REMOTE_PREX.replaceAll("172.16.99.30:81", "10.1.2.30"));
	}
}
