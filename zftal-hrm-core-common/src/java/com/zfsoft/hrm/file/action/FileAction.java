package com.zfsoft.hrm.file.action;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.core.util.Byte_File_Object;
import com.zfsoft.hrm.file.dto.FileProp;
import com.zfsoft.hrm.file.dto.Progress;
import com.zfsoft.hrm.file.entity.ImageCutPropertyBean;
import com.zfsoft.hrm.file.entity.ImageDB;
import com.zfsoft.hrm.file.util.FilePathUtil;
import com.zfsoft.hrm.file.util.ImageCut;
import com.zfsoft.hrm.file.util.ImageDBUtil;
import com.zfsoft.hrm.file.util.UploadFileUtil;
import com.zfsoft.util.base.StringUtil;
/**
 * 
 * @author 沈鲁威 Patrick Shen
 * @since 2012-8-9
 * @version V1.0.0
 */
public class FileAction extends HrmAction {  

	private static final long serialVersionUID = 6649027352616232244L;  
    
    private String fileName;
    
    private String filetype;
    
    private String fileGuid;
    
    private FileProp fileProp;
    
	public FileProp getFileProp() {
		return fileProp;
	}

	public void setFileProp(FileProp fileProp) {
		this.fileProp = fileProp;
	}
    
	public String getFileGuid() {
		return fileGuid;
	}

	public void setFileGuid(String fileGuid) {
		this.fileGuid = fileGuid;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
	public String imgCut(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//HttpServletResponse response = ServletActionContext.getResponse();
		ImageCutPropertyBean imageCutBean = new ImageCutPropertyBean();
		imageCutBean.setX(request.getParameter("x"));
		imageCutBean.setY(request.getParameter("y"));
		imageCutBean.setW(request.getParameter("w"));
		imageCutBean.setH(request.getParameter("h"));
		imageCutBean.setOldImgPath(request.getParameter("oldImgPath"));
		imageCutBean.setOldImgRoot(request.getParameter("imgRoot"));
		imageCutBean.setOldImgExt(request.getParameter("imgFileExt"));
		String imageName = request.getParameter("imageName");
		
		String width = null;
		String height = null;
		File file = new File(ServletActionContext.getServletContext().getRealPath("/")+"homeimages/"+imageName);// 读入文件
		Image img;
		try {
			img = ImageIO.read(file);
			width = String.valueOf(img.getWidth(null));    // 得到源图宽
			height = String.valueOf(img.getHeight(null));
			//String fileName = imageDB.getFileName();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}      // 构造Image对象
		
		imageCutBean.setWidth(width);
		imageCutBean.setHeight(height);
		String guid = request.getParameter("guid");
		String webAppPath = ServletActionContext.getServletContext().getRealPath("/");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
		String imgFileId = formatter.format(new Date());
		/*String imgName = imageCutBean.getOldImgRoot() + imgFileId + System.currentTimeMillis() + "."
				+ imageCutBean.getOldImgExt();
		System.out.println("im:" + imgName);*/
		//String createImgPath = webAppPath + imgName;
		String createImgPath = webAppPath + request.getParameter("oldImgPath");
		webAppPath += imageCutBean.getOldImgPath();
		ImageCut.abscut(webAppPath, createImgPath,imageCutBean);
		String createMinImgPath = createImgPath.replace("homeimages", "minimages");
		copyFile(ServletActionContext.getServletContext().getRealPath("/")+"homeimages/"+imageName, createMinImgPath);
		ImageDBUtil.updateDIleToDB(imageName, guid);
		
		setSuccessMessage("操作成功");
		/*Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		this.getValueStack().set("data", map);*/
		
		return DATA;
		/*this.getValueStack().set("tag", "1");
		this.getValueStack().set("oldImgPath", imageCutBean.getOldImgPath());
		this.getValueStack().set("imgFileExt", imageCutBean.getOldImgExt());
		this.getValueStack().set("imgRoot", imageCutBean.getOldImgRoot());
		this.getValueStack().set("imgName", imgName);
		this.getValueStack().set("width", imageCutBean.getWidth());
		this.getValueStack().set("height", imageCutBean.getHeight());
		return "personalInfo";*/
	}
	
	public void copyFile(String oldPath, String newPath) { 
		try { 
		int bytesum = 0; 
		int byteread = 0; 
			File oldfile = new File(oldPath); 
		if (oldfile.exists()) { //文件存在时 
			InputStream inStream = new FileInputStream(oldPath); //读入原文件 
			FileOutputStream fs = new FileOutputStream(newPath); 
			byte[] buffer = new byte[1444]; 
			int length; 
			while ( (byteread = inStream.read(buffer)) != -1) { 
				bytesum += byteread; //字节数 文件大小 
				System.out.println(bytesum); 
				fs.write(buffer, 0, byteread); 
			} 
			inStream.close(); 
			
			
			
		} 
		} 
		catch (Exception e) { 
			System.out.println("复制单个文件操作出错"); 
			e.printStackTrace(); 

		} 

	}
	
	
	public String imgcrop(){
		HttpServletRequest request = getRequest();
		String tag = request.getParameter("tag");
		String oldImgPath = request.getParameter("oldImgPath");
		String imgFileExt = request.getParameter("imgFileExt");
		String imgRoot = request.getParameter("imgRoot");
		String width = null;
		String height = null;
		String imageName = request.getParameter("imageName");
		String proportion = request.getParameter("proportion");
		
		/*	File file = new File(ServletActionContext.getServletContext().getRealPath("/")+"homeimages/"+imageName);// 读入文件
			Image img;
			try {
				img = ImageIO.read(file);
				width = String.valueOf(img.getWidth(null));    // 得到源图宽
				height = String.valueOf(img.getHeight(null));
				//String fileName = imageDB.getFileName();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}      // 构造Image对象
*/		
		getValueStack().set("tag", tag);
		getValueStack().set("oldImgPath", oldImgPath);
		getValueStack().set("imgFileExt", imgFileExt);
		getValueStack().set("imgRoot", imgRoot);
		getValueStack().set("width", width);
		getValueStack().set("height", height);
		getValueStack().set("imageName", imageName);
		getValueStack().set("proportion", proportion);
		
		return "imgcrop";
	}
	
	public String cutsuccess(){
		HttpServletRequest request = getRequest();
		String fileName = request.getParameter("fileName");
		String guid 	= request.getParameter("guid");
		ImageDBUtil.updateDIleToDB(fileName, guid);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		map.put("guid", guid);
		
		this.getValueStack().set("data", map);
		return DATA;
		
	}

	/**
	 * 上传图片页面
	 * @return page view
	 */
	public String list() {
		String path = getRequest().getSession().getServletContext().getRealPath("/");
		path=FilePathUtil.resetPath(path);
		if(fileName==null||("").equals(fileName)){
			path+="upload"+File.separator+"default.jpg";
		}else{
			path+="upload"+File.separator+fileName;
		}
		return "list";
	}
	
	/**
	 * 初始化图片上传，生成进度对象
	 * @return page view
	 */
	public String startImageUp() {
		String[] suffixs=new String[]{"jpg","gif","png","bmp"};
		//获取文件后缀名
		try{
			UploadFileUtil.checkedFileName(fileName, suffixs);
		}catch(Exception e){
			this.setErrorMessage("文件类型不支持,仅限于"+Arrays.toString(suffixs));
			this.getValueStack().set("data", getMessage());
			return DATA;
		}
		// 缓存progress对象的key值
		String key = Integer.toString(getRequest().hashCode());
		// 新建当前上传文件的进度信息对象
		Progress p = new Progress();
		// 缓存progress对象
		UploadFileUtil.keyMap.put(key, p);

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("suffix", fileName.substring(fileName.lastIndexOf("."), fileName.length()));
		map.put("imageName", fileName.substring(fileName.lastIndexOf("\\")+1, fileName.length()));
		map.put("success", true);
		map.put("key", key);
		
		this.getValueStack().set("data", map);
		return DATA;
	}
	/**
	 * 初始化文件上传，生成进度对象
	 * @return page view
	 */
	public String startFileUp() {
		//String[] suffixs=new String[]{"doc","docx","txt","xls","xlsx","jpg","gif","png","bmp","pdf","apk"};
		//String[] suffixs=new String[]{"apk"};
		String[] suffixs=filetype.split(",");
		//获取文件后缀名
		try{
			UploadFileUtil.checkedFileName(fileName, suffixs);
		}catch(Exception e){
			this.setErrorMessage("文件类型不支持,仅限于"+Arrays.toString(suffixs));
			this.getValueStack().set("data", getMessage());
			return DATA;
		}
		// 缓存progress对象的key值
		String key = Integer.toString(getRequest().hashCode());
		// 新建当前上传文件的进度信息对象
		Progress p = new Progress();
		// 缓存progress对象
		UploadFileUtil.keyMap.put(key, p);

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		map.put("key", key);
		this.getValueStack().set("data", map);
		return DATA;
	}
	
	/**
	 * 上传图片
	 * @return page view
	 * @throws IOException 
	 */
	public String uploadimage() throws IOException {
		fileProp.setMaxSize(fileProp.getMaxSize()*1024);
		fileProp.setFileType("image");
		fileProp.setKey(getRequest().getParameter("key"));
		//fileName = System.currentTimeMillis() + fileName.substring(fileName.lastIndexOf("\\")+1, fileName.length());
		try {
			 UploadFileUtil.upload(getRequest(), getResponse(),fileProp,fileName);
		} catch (IOException e) {
			LOG.error("上传文件发生异常,错误原因 : " + e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * 上传附件
	 * @return page view
	 * @throws IOException 
	 */
	public String uploadattachement() throws IOException {
		fileProp.setMaxSize(fileProp.getMaxSize()*1024*1024);
		fileProp.setKey(getRequest().getParameter("key"));
		try {
			UploadFileUtil.upload(getRequest(), getResponse(),fileProp,fileName);
		} catch (IOException e) {
			LOG.error("上传文件发生异常,错误原因 : " + e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * 向客户端返回进度
	 * @return
	 */
	public String readProgress() {
		String key = getRequest().getParameter("key");

		Progress p=UploadFileUtil.keyMap.get(key);
		Map<String,Object> map = new HashMap<String,Object>();
		if(p!=null&&(p.isExceptionMaxSize()|| p.isComplete())){
			UploadFileUtil.keyMap.remove(key);
			
			
		}
		
		int height = 0;
		String[] suffixs=new String[]{"jpg","gif","png","bmp"};
		//获取文件后缀名
		boolean isImage = true;//是否是图片类型
		try{
			UploadFileUtil.checkedFileName(p.getFileName(), suffixs);
		}catch(Exception e){
			isImage = false;
		}
		File file = null;
		if(isImage){
			file = new File(ServletActionContext.getServletContext().getRealPath("/")+"homeimages/"+fileName);// 读入文件
		}else{
			file = new File(ServletActionContext.getServletContext().getRealPath("/")+"homeimages/"+p.getFileName());// 读入文件
		}
		Image img;
		try {
			img = ImageIO.read(file);
			height = img.getHeight(null);
			//String fileName = imageDB.getFileName();
		} catch (IOException e) {
			e.printStackTrace();
		}    
		
		
		map.put("success", true);
		map.put("height", height);
		map.put("key", key);
		map.put("len", p.getCurrentLength());
		map.put("total", p.getLength());
		map.put("fileName", isImage ? fileName : p.getFileName());
		map.put("guid", p.getFileGuid());
		map.put("exceptionMaxSize", p.isExceptionMaxSize());
		map.put("errorMessage", p.getErrorMessage());
		map.put("isComplete", p.isComplete());
		this.getValueStack().set("data", map);
		
		return DATA;
	}
	
	/**
	 * 获取图片
	 * @return
	 * @throws IOException
	 */
	public String image() throws IOException {
		ImageDB imageDB;
		if("undefined".equals(fileGuid)||StringUtil.isEmpty(fileGuid)){
			imageDB=ImageDBUtil.getImageDBByGuid("default_"+Type.IMAGE.toLowerCase());
			showPic(imageDB,Type.IMAGE);
			return null;
		}else{
			imageDB=ImageDBUtil.getImageDBByGuid(fileGuid);
			showPic(imageDB,Type.IMAGE);
		}
		return null;
	}
	/**
	 * 获取照片
	 * @return
	 * @throws IOException
	 */
	public String photo() throws IOException {
		
		ImageDB imageDB;
		if("undefined".equals(fileGuid)||StringUtil.isEmpty(fileGuid)){
			imageDB=ImageDBUtil.getImageDBByGuid("default_"+Type.PHOTO.toLowerCase());
			showPic(imageDB,Type.PHOTO);
			return null;
		}else{
			imageDB=ImageDBUtil.getImageDBByGuid(fileGuid);
			showPic(imageDB,Type.PHOTO);
		}
		
		return null;
	}
	
	public void showPic(ImageDB imageDB,String type) throws IOException{
		String path = getRequest().getSession().getServletContext().getRealPath("/");
		if(imageDB==null){
			path+="upload"+File.separator+"default_"+type.toLowerCase()+".jpg";
			getResponse().getOutputStream().write(Byte_File_Object.getBytesFromFile(new File(path)));
		}else{
			if(StringUtil.isEmpty(imageDB.getPath())){
				getResponse().getOutputStream().write(imageDB.getFileContent());
			}else{
				if(imageDB.getFileContent()==null&&!StringUtil.isEmpty(imageDB.getPath())){
					path+="upload"+File.separator+imageDB.getGuid()+"."+imageDB.getSuffixs();
					getResponse().getOutputStream().write(Byte_File_Object.getBytesFromFile(new File(path)));
				}else{
					getResponse().getOutputStream().write(imageDB.getFileContent());
				}
			}
		}
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	public String getFiletype() {
		return filetype;
	}
	
}
