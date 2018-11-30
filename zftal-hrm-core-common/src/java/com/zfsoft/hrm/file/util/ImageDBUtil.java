package com.zfsoft.hrm.file.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.apache.struts2.ServletActionContext;

import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.common.system.BaseHolder;
import com.zfsoft.hrm.core.util.Byte_File_Object;
import com.zfsoft.hrm.file.dao.daointerface.IImageDBDao;
import com.zfsoft.hrm.file.entity.ImageDB;
import com.zfsoft.util.base.StringUtil;

public class ImageDBUtil {
	
	/**
	 * 保存到文件夹并返回保存的路径
	 * @param item
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static ImageDB saveFileToFolder(String fileName, InputStream inputStream, String path) throws IOException {
	 	// 上传临时路径   
		byte[] content=Byte_File_Object.getBytesFromFile(inputStream);
		IImageDBDao imageDBDao=(IImageDBDao)SpringHolder.getBean("imageDBDao");
		ImageDB ImageDB=new ImageDB();
		ImageDB.setFileName(fileName);
		ImageDB.setUploadTime(new Date());
		ImageDB.setFileSize(new Long(content.length));
		ImageDB.setPath(path);
		ImageDB.setSuffixs(UploadFileUtil.checkedFileName(fileName));
		imageDBDao.insert(ImageDB);
		return ImageDB;
	}
	/**
	 * 保存到数据库并
	 * @return
	 * @throws ClassNotFoundExceptaion 
	 */
	public static ImageDB saveFileToDB(File item) throws Exception{
		IImageDBDao imageDBDao=(IImageDBDao)SpringHolder.getBean("imageDBDao");
		byte[] content=Byte_File_Object.getBytesFromFile(item);
		ImageDB ImageDB=new ImageDB();
		ImageDB.setFileName(item.getName());
		ImageDB.setUploadTime(new Date());
		ImageDB.setFileSize(new Long(content.length));
		ImageDB.setFileContent(content);
		ImageDB.setSuffixs(UploadFileUtil.checkedFileName(item.getName()));
		imageDBDao.insert(ImageDB);
		return ImageDB;
	}
	public static ImageDB saveFileToDB(String fileName, InputStream inputStream) {
		IImageDBDao imageDBDao=(IImageDBDao)SpringHolder.getBean("imageDBDao");
		byte[] content=Byte_File_Object.getBytesFromFile(inputStream);
		ImageDB ImageDB=new ImageDB();
		ImageDB.setFileName(fileName);
		ImageDB.setUploadTime(new Date());
		ImageDB.setFileSize(new Long(content.length));
		ImageDB.setFileContent(content);
		ImageDB.setSuffixs(UploadFileUtil.checkedFileName(fileName));
		imageDBDao.insert(ImageDB);
		return ImageDB;
	}
	public static ImageDB saveFileToDB(String fileName, byte[] content) {
		IImageDBDao imageDBDao=(IImageDBDao)SpringHolder.getBean("imageDBDao");
		ImageDB ImageDB=new ImageDB();
		ImageDB.setFileName(fileName);
		ImageDB.setUploadTime(new Date());
		ImageDB.setFileSize(new Long(content.length));
		ImageDB.setFileContent(content);
		ImageDB.setSuffixs(UploadFileUtil.checkedFileName(fileName));
		imageDBDao.insert(ImageDB);
		return ImageDB;
	}
	/**
	 * 
	 * @return
	 * @throws ClassNotFoundException 
	 */
	public static ImageDB updateFileToDB(File item, ImageDB imageDB) {
		IImageDBDao imageDBDao=(IImageDBDao)SpringHolder.getBean("imageDBDao");
		byte[] content=Byte_File_Object.getBytesFromFile(item);
		imageDB.setFileName(item.getName());
		imageDB.setUploadTime(new Date());
		imageDB.setFileSize(new Long(content.length));
		imageDB.setFileContent(content);
		imageDB.setSuffixs(UploadFileUtil.checkedFileName(item.getName()));
		imageDBDao.update(imageDB);
		return imageDBDao.findById(imageDB.getGuid());
	}
	
	public static ImageDB updateDIleToDB(String fileName, String guid){
		IImageDBDao imageDBDao=(IImageDBDao)SpringHolder.getBean("imageDBDao");
		String uploadPath = "home" + BaseHolder.getPropertiesValue("uploadPath");
		File file=new File(ServletActionContext.getServletContext().getRealPath("/")+ uploadPath+"/" + fileName);
		byte[] content=Byte_File_Object.getBytesFromFile(file);
		ImageDB imageDB = getImageDBByGuid(guid);
		imageDB.setFileName(fileName);
		imageDB.setUploadTime(new Date());
		imageDB.setFileSize(new Long(content.length));
		imageDB.setFileContent(content);
		imageDB.setSuffixs(UploadFileUtil.checkedFileName(fileName));
		imageDBDao.update(imageDB);
		return imageDBDao.findById(imageDB.getGuid());
	}
	
	public static ImageDB updateFileToDB(String fileName,byte[] content, ImageDB imageDB) {
		IImageDBDao imageDBDao=(IImageDBDao)SpringHolder.getBean("imageDBDao");
		imageDB.setFileName(fileName);
		imageDB.setUploadTime(new Date());
		imageDB.setFileSize(new Long(content.length));
		imageDB.setFileContent(content);
		imageDB.setSuffixs(UploadFileUtil.checkedFileName(fileName));
		imageDBDao.update(imageDB);
		return imageDBDao.findById(imageDB.getGuid());
	}
	public static ImageDB updateFileToDB(String fileName,InputStream inputStream, ImageDB imageDB) {
		IImageDBDao imageDBDao=(IImageDBDao)SpringHolder.getBean("imageDBDao");
		byte[] content=Byte_File_Object.getBytesFromFile(inputStream);
		imageDB.setFileName(fileName);
		imageDB.setUploadTime(new Date());
		imageDB.setFileSize(new Long(content.length));
		imageDB.setFileContent(content);
		imageDB.setSuffixs(UploadFileUtil.checkedFileName(fileName));
		imageDBDao.update(imageDB);
		return imageDBDao.findById(imageDB.getGuid());
	}
	
	public static String deleteFileToDB(String imageDBId){
		if(StringUtil.isEmpty(imageDBId)){
			return null;
		}
		IImageDBDao imageDBDao=(IImageDBDao)SpringHolder.getBean("imageDBDao");
		ImageDB ImageDB=imageDBDao.findById(imageDBId);
		if(ImageDB==null)return null;
		if(ImageDB.getPath()==null||"".equals(ImageDB.getPath())){
			imageDBDao.delete(ImageDB);
		}else{
			File file=new File(ImageDB.getPath()+"/"+ImageDB.getGuid()+"."+ImageDB.getSuffixs());
			file.delete();
			imageDBDao.delete(ImageDB);
		}
		return null;
	}
	
	public static String deleteFileToDB(String imageDBId,String path){
		if(StringUtil.isEmpty(imageDBId)){
			return null;
		}
		IImageDBDao imageDBDao=(IImageDBDao)SpringHolder.getBean("imageDBDao");
		ImageDB ImageDB=imageDBDao.findById(imageDBId);
		if(ImageDB==null)return null;
		if(path==null||"".equals(path)){
			imageDBDao.delete(ImageDB);
		}else{
			//File file=new File(ImageDB.getPath()+"/"+ImageDB.getGuid()+"."+ImageDB.getSuffixs());
			File file=new File(path);
			if(file.exists()){
				file.delete();
			}
			imageDBDao.delete(ImageDB);
		}
		return null;
	}

	public static ImageDB getImageDBByGuid(String imageDBId) {
		IImageDBDao imageDBDao=(IImageDBDao)SpringHolder.getBean("imageDBDao");
		return imageDBDao.findById(imageDBId);		
	}

}
