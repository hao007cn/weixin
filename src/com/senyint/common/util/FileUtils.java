package com.senyint.common.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 对File 操作的 utils 类
 * 
 * @author 
 * 
 */
public class FileUtils {
	/**
	 * The method <code> deleteFile </code> .
	 * 删除单个文件
	 *
	 * @author  sunzhi Senyint (Dalian) Co., Ltd.
	 *
	 * @param sPath 被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * The method <code> deleteDirectory </code> .
	 * 删除目录（文件夹）以及目录下的文件
	 *
	 * @author  sunzhi Senyint (Dalian) Co., Ltd.
	 *
	 * @param sPath 被删除目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory(String sPath) {
		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} // 删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag)
			return false;
		// 删除当前目录
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * The method <code> DeleteFolder </code> 
	 * 根据路径删除指定的目录或文件，无论存在与否
	 * 
	 * @author sunzhi Senyint (Dalian) Co., Ltd.
	 * 
	 * @param sPath
	 *            要删除的目录或文件
	 * @return 删除成功返回 true，否则返回 false。
	 */
	public static boolean deleteFolder(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 判断目录或文件是否存在
		if (!file.exists()) { // 不存在返回 false
			return flag;
		} else {
			// 判断是否为文件
			if (file.isFile()) { // 为文件时调用删除文件方法
				return deleteFile(sPath);
			} else { // 为目录时调用删除目录方法
				return deleteDirectory(sPath);
			}
		}
	}
	
	/**
	 * The method <code> file2Byte </code> .
	 * File to Byte
	 *
	 * @author  sunzhi Senyint (Dalian) Co., Ltd.
	 *
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static byte[] file2Byte(File file) throws IOException {
		
		FileInputStream fis = new FileInputStream(file);
		byte[] b = new byte[fis.available()];
		fis.read(b);
		fis.close();
		
		return b;
	}
	
	/**
	 * The method <code> byte2File </code> .
	 * byte to file
	 *
	 * @author  sunzhi Senyint (Dalian) Co., Ltd.
	 *
	 * @param bytes
	 * @return
	 * @throws IOException
	 */
	public static File byte2File(byte[] bytes, String filePath) throws IOException {
		
		File f = new File(filePath);
		FileOutputStream fos = new FileOutputStream(f);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		
		bos.write(bytes);
		
		return f;
	}

	/**
	 * The method <code> uploadFile </code> 。
	 * 复制文件
	 * 
	 * @author sunzhi Senyint (Dalian) Co., Ltd.
	 * 
	 * @param file
	 *            上传的文件
	 * @param newPath
	 *            目的地址
	 * @throws IOException
	 */
	public static void copyFile(File file, String newPath) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		FileOutputStream fos = new FileOutputStream(newPath);
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = fis.read(buffer)) > 0) {
			fos.write(buffer, 0, len);
		}
		fos.close();
		fis.close();
	}
	
	/**
	 * The method <code> copyFile </code> .
	 * 复制文件
	 *
	 * @author  sunzhi Senyint (Dalian) Co., Ltd.
	 *
	 * @param orgPath 原文件路径
	 * @param newPath 新文件路径
	 * @throws IOException
	 */
	public static void copyFile(String orgPath, String newPath) throws IOException {
		FileInputStream fis = new FileInputStream(orgPath);
		FileOutputStream fos = new FileOutputStream(newPath);
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = fis.read(buffer)) > 0) {
			fos.write(buffer, 0, len);
		}
		fos.close();
		fis.close();
	}
	
	/**
	* The method <code> copyRemoteFile </code> .
	* 保存网络文件
	* 
	* @author sunzhi Senyint (Dalian) Co., Ltd.
	* 
	* @param urlStr 网络地址
	* @param newPath 目标路径
	* @throws Exception
	*/
	public static void copyRemoteFile(String urlStr, String newPath)
			throws Exception {
		URL url = null;
		InputStream is = null;
		OutputStream os = null;
		URLConnection con = null;

		url = new URL(urlStr);
		con = url.openConnection();
		con.setConnectTimeout(5 * 1000);
		is = con.getInputStream();
		byte[] bs = new byte[1024];
		int len;
		
		os = new FileOutputStream(newPath);
		
		while ((len = is.read(bs)) != -1) {
			os.write(bs, 0, len);
		}

		os.flush();
		os.close();
		is.close();
	}
}
