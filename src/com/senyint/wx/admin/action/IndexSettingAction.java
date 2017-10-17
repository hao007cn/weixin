/**
 * IndexSettingAction.java
 * com.senyint.wx.admin.action
 * Function: TODO ADD FUNCTION
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2014年11月21日 		gjp
 *
 * Copyright (c) 2014, senyint All Rights Reserved.
 */
/**
 * @File: IndexSettingAction.java
 * @Package com.senyint.wx.admin.action
 * @Description: TODO Describe the File
 *
 * @Company: senyint (Dalian) Co., Ltd
 * @author   gjp
 * @date     2014年11月21日
 * @version  1.0
 */

package com.senyint.wx.admin.action;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.senyint.wx.admin.dao.IndexSettingDao;
import com.senyint.wx.common.entity.IndexSetting;
import com.senyint.wx.common.utils.ArgumentUtil;
import com.senyint.wx.common.web.Operate;

/**
 * ClassName:IndexSettingAction
 *
 * @author   gjp
 * @version  
 * @since    Ver 1.1
 * @Date	 2014年11月21日
 *
 * @see 	 
 */
@Controller
@RequestMapping(value = "/indexSetting")
public class IndexSettingAction {

	@Autowired
	private IndexSettingDao indexSettingDao;

	/**
	 * 首页设置跳转
	 * 
	 * @author gjp senyint (Dalian) Co., Ltd.
	 * 
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@Operate(desc = "首页设置")
	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model, HttpServletRequest request,
			HttpSession session) {
		List<IndexSetting> list = indexSettingDao.findIndexSetting();
		model.addAttribute("indexlist", list);
		return "indexSetting";
	}

	/**
	 * 修改方法 
	 * 
	 * @author gjp senyint (Dalian) Co., Ltd.
	 * 
	 * @param flag
	 * @param request
	 * @param session
	 * @return
	 */
	@Operate(desc = "保存首页设置")
	@RequestMapping(value = "/updateView", method = RequestMethod.POST)
	public String updateView(String flag, HttpServletRequest request,
			HttpSession session) {
		String poid = request.getParameter("poid");
		if (!poid.isEmpty()) {
			IndexSetting indexSetting = indexSettingDao.findByPoid(Integer
					.parseInt(poid));
			indexSetting.setImg_view(flag);
			indexSettingDao.makePersistent(indexSetting);
		}
		return "redirect:/admin/indexSetting";
	}

	/**
	 * 删除轮播画面 
	 * 
	 * @author gjp senyint (Dalian) Co., Ltd.
	 * 
	 * @param poid
	 * @param request
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@Operate(desc = "删除轮播画面")
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public String del(Integer poid, HttpServletRequest request,
			HttpSession session, RedirectAttributes redirectAttributes) {
		IndexSetting indexSetting = indexSettingDao.findByPoid(poid);
		String delPath = request.getServletContext().getRealPath("/")
				+ "mobile" + File.separatorChar + "static" + File.separatorChar
				+ "image" + File.separatorChar + indexSetting.getVirtual_name();
		File file = new File(delPath);
		if (file.delete()) {
			indexSettingDao.remove(indexSetting);
			redirectAttributes.addFlashAttribute("message", "删除成功");
		} else {
			redirectAttributes.addFlashAttribute("message", "删除失败");
		}
		return "redirect:/admin/indexSetting";
	}

	/**
	 * 新增轮播画面 
	 * 
	 * @author gjp senyint (Dalian) Co., Ltd.
	 * 
	 * @param file
	 * @param request
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@Operate(desc = "新增轮播画面")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(
			@RequestParam(value = "img_url", required = false) MultipartFile file,
			HttpServletRequest request, HttpSession session,
			RedirectAttributes redirectAttributes) {
		IndexSetting is = new IndexSetting();
		try {
			request.setCharacterEncoding("UTF-8");
			String savePath = request.getServletContext().getRealPath("/")
					+ "mobile" + File.separatorChar + "static"
					+ File.separatorChar + "image";
			String loadPath = "/"
					+ request.getContextPath().substring(1,
							request.getContextPath().length())
					+ "/mobile/static/image";
			savePath = new String(savePath.getBytes("iso-8859-1"), "UTF-8");
			File f1 = new File(savePath);
			if (!f1.exists()) {
				f1.mkdirs();
			}

			if (!file.isEmpty()) {
				String name = file.getOriginalFilename();
				String[] nameall = name.split("\\.");
				int index = indexSettingDao.getMaxSequence();
				is.setImg_sequence(index + 1);
				is.setImg_title(request.getParameter("img_title"));
				is.setImg_content(request.getParameter("img_content"));
				is.setImg_view(request.getParameter("img_view") == null ? "off"
						: request.getParameter("img_view"));
				String virtual_name = new SimpleDateFormat("yyyyMMddHHmmssSSS")
						.format(ArgumentUtil.getSysDate());
				is.setVirtual_name(virtual_name + "." + nameall[1]);
				is.setImg_url(loadPath + "/" + virtual_name + "." + nameall[1]);
				File files = new File(savePath + File.separatorChar
						+ virtual_name + "." + nameall[1]);
				if (files.exists()) {
					redirectAttributes.addFlashAttribute("message", "文件重复");
					return "redirect:/admin/indexSetting";
				}
				file.transferTo(files);
				indexSettingDao.makePersistent(is);
				redirectAttributes.addFlashAttribute("message", "添加成功");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "redirect:/admin/indexSetting";
	}

	/**
	 * 左右移动位置 
	 * 
	 * @author gjp senyint (Dalian) Co., Ltd.
	 * 
	 * @param request
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/leftRightMove", method = RequestMethod.POST)
	public String leftRightMove(HttpServletRequest request,
			HttpSession session, RedirectAttributes redirectAttributes) {
		int sqe = request.getParameter("sqe").isEmpty() ? 0 : Integer
				.parseInt(request.getParameter("sqe"));
		// 0==left,1==right
		String move = request.getParameter("move");
		List<IndexSetting> list = indexSettingDao.findIndexSetting();
		List<IndexSetting> tempList = new ArrayList<IndexSetting>();
		if (move.equals("0")) {
			// 向左移动
			for (int i = 0; i < list.size(); i++) {
				IndexSetting indexSetting = list.get(i);
				if (i == 0 && indexSetting.getImg_sequence() == sqe) {
					// 如果向左移动是第一个则直接返回页面
					return "redirect:/admin/indexSetting";
				}
				if (indexSetting.getImg_sequence() == sqe) {
					// 取出前一个
					int tempindex = list.get(i - 1).getImg_sequence();
					int tempindex2 = indexSetting.getImg_sequence();
					IndexSetting isleft = list.get(i - 1);
					isleft.setImg_sequence(tempindex2);
					indexSetting.setImg_sequence(tempindex);
					tempList.add(isleft);
					tempList.add(indexSetting);
					indexSettingDao.makePersistent(tempList);
					return "redirect:/admin/indexSetting";
				}
			}

		}
		if (move.equals("1")) {
			for (int i = 0; i < list.size(); i++) {
				IndexSetting indexSetting = list.get(i);
				if (indexSetting.getImg_sequence() == sqe
						&& i != (list.size() - 1)) {
					// 取出后一个
					int tempindex = list.get(i + 1).getImg_sequence();
					int tempindex2 = indexSetting.getImg_sequence();
					IndexSetting isright = list.get(i + 1);
					isright.setImg_sequence(tempindex2);
					indexSetting.setImg_sequence(tempindex);
					tempList.add(isright);
					tempList.add(indexSetting);
					indexSettingDao.makePersistent(tempList);
					return "redirect:/admin/indexSetting";
				}

			}
		}
		return "redirect:/admin/indexSetting";
	}
}
