package com.zfsoft.mobile.common.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.zfsoft.common.system.BaseHolder;
import com.zfsoft.util.base.StringUtil;

public class FileUntils {
	
	
	public static String GetFileSize(File file){
	    String size = ""; 
	    if(file.exists() && file.isFile()){
	    long fileS = file.length();
	      DecimalFormat df = new DecimalFormat("#.00"); 
	           if (fileS < 1024) {
	               size = df.format((double) fileS) + "BT";
	           } else if (fileS < 1048576) {
	               size = df.format((double) fileS / 1024) + "KB";
	           } else if (fileS < 1073741824) {
	               size = df.format((double) fileS / 1048576) + "MB";
	           } else {
	               size = df.format((double) fileS / 1073741824) +"GB";
	           }
	    }
	    return size;
	   }
	
	public static String encodeURL(String url){
		 try {
		 url = URLEncoder.encode(url, "utf-8").replaceAll("//+", "%20");
		 url = url.replaceAll("%3A", ":").replaceAll("%2F", "/").replaceAll("%25", "%");
		 } catch (UnsupportedEncodingException e) {
		 e.printStackTrace();
		 return null;
		 }
		 return url;
		 }

	/**
	 * 获取所有系统中选择图标列表路径
	 * @param name  	把名称为name的图标排到最前面
	 * @param keyWord   根据keyWord关键词搜索所有图标列表
	 * @return
	 */
	public static ArrayList<String> getImagesPathList(String name,String keyWord){
		String entityFileName = null;
		ArrayList<String> sortPathList = new ArrayList<String>();
		ArrayList<String> pathList = new ArrayList<String>();
		//File dir = new File(path);
		
		String iconPath = "icoimages";
		HttpServletRequest request = ServletActionContext.getRequest();
		String filepath = request.getSession().getServletContext().getRealPath("/") + iconPath;
		filepath = filepath.replace("\\", "/");
		File newFile = new File(filepath);
		if (!newFile.exists()) {
			newFile.mkdir();
		}
		File[] files = newFile.listFiles(); // 该文件目录下文件全部放入数组
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				String fileName = files[i].getName();
				/*if (files[i].isDirectory()) { // 判断是文件还是文件夹
					getFileList(files[i].getAbsolutePath()); // 获取文件绝对路径
				} */
				if(!StringUtil.isEmpty(name)){
					if(name.equals(fileName)){
						if(!StringUtil.isEmpty(keyWord)){
							if(fileName.indexOf(keyWord) != -1){
								entityFileName = iconPath + "/"+ fileName;
							}
						}else{
							entityFileName = iconPath + "/"+ fileName;
						}
						continue;
					}else{
						if(!StringUtil.isEmpty(keyWord)){
							if(fileName.indexOf(keyWord) != -1){
								pathList.add(iconPath + "/"+ fileName);
							}
						}else{
							pathList.add(iconPath + "/"+ fileName);
						}
						
					}
				}else{
					if(!StringUtil.isEmpty(keyWord)){
						if(fileName.indexOf(keyWord) != -1){
							pathList.add(iconPath + "/"+ fileName);
						}
					}else{
						pathList.add(iconPath + "/"+ fileName);
					}
					/*if (files[i].isDirectory()) { // 判断是文件还是文件夹
					getFileList(files[i].getAbsolutePath()); // 获取文件绝对路径
				} */
					
					
				}
					
			}

		}
		
		if(!StringUtil.isEmpty(entityFileName)){
			sortPathList.add(entityFileName);
			sortPathList.addAll(pathList);
			return sortPathList;
		}else{
			return pathList;
		}
	}
	/**
	 * 获取所有系统中选择图标列表名称
	 * @param name  	把名称为name的图标排到最前面
	 * @param keyWord   根据keyWord关键词搜索所有图标列表
	 * @return
	 */
	public static ArrayList<String> getImagesNameList(String name,String keyWord){
		String entityFileName = null;
		ArrayList<String> sortPathList = new ArrayList<String>();
		ArrayList<String> pathList = new ArrayList<String>();
		//File dir = new File(path);
		
		String iconPath = "icoimages";
		HttpServletRequest request = ServletActionContext.getRequest();
		String filepath = request.getSession().getServletContext().getRealPath("/") + iconPath;
		filepath = filepath.replace("\\", "/");
		File newFile = new File(filepath);
		if (!newFile.exists()) {
			newFile.mkdir();
		}
		File[] files = newFile.listFiles(); // 该文件目录下文件全部放入数组
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				String fileName = files[i].getName();
				if(!StringUtil.isEmpty(name)){
					if(name.equals(fileName)){
						if(!StringUtil.isEmpty(keyWord)){
							if(fileName.indexOf(keyWord) != -1){
								entityFileName = fileName;
							}
						}else{
							entityFileName = fileName;
						}
						continue;
					}else{
						if(!StringUtil.isEmpty(keyWord)){
							if(fileName.indexOf(keyWord) != -1){
								pathList.add(fileName);
							}
						}else{
							pathList.add(fileName);
						}
						
					}
				}else{
					if(!StringUtil.isEmpty(keyWord)){
						if(fileName.indexOf(keyWord) != -1){
							pathList.add(fileName);
						}
					}else{
						pathList.add(fileName);
					}
					/*if (files[i].isDirectory()) { // 判断是文件还是文件夹
					getFileList(files[i].getAbsolutePath()); // 获取文件绝对路径
				} */
					
				}
			}

		}
		if(!StringUtil.isEmpty(entityFileName)){
			sortPathList.add(entityFileName);
			sortPathList.addAll(pathList);
			return sortPathList;
		}else{
			return pathList;
		}
	}
	
	/**
	 *删除服务器上路径下的文件
	 *@param  realpath
	 *@param filedir
	 *@author yangbilin
	 * 
	 */
	public void deletePic(String realpath,String filedir){
		String picpath = realpath+ filedir;
		picpath = picpath.replace("\\", "/");//防止linux系统无法识别“\”路径
		File file = new File(picpath);
		if(file!=null&&file.isFile()){
			if(file.exists()){
				file.delete();
			}
		}
	}
	
	/**
	 * 获取服务器访问路径
	 * 如：http://10.71.33.201:8090/zftal-mobile/
	 * @return String 
	 */
	public String getImageHost() {
		String url = BaseHolder.getPropertiesValue("suploadPath");
		if (url == null) {
			return "/";
		}
        url = url.replace("\\", "/");
        if (!url.endsWith("/")) {
        	url += "/";
        }
		return url;
	}
	
}
