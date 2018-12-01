package com.zfsoft.hrm.file.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.zfsoft.common.system.BaseHolder;
import com.zfsoft.common.system.SubSystemHolder;
import com.zfsoft.hrm.core.util.Byte_File_Object;
import com.zfsoft.hrm.file.dto.FileProp;
import com.zfsoft.hrm.file.dto.Progress;
import com.zfsoft.hrm.file.entity.Attachement;
import com.zfsoft.hrm.file.entity.ImageDB;
import com.zfsoft.util.base.StringUtil;

/**
 * upload file
 * 
 * @author scott.Cgi
 */
public class UploadFileUtil {
	
	private static final Logger LOG = Logger.getLogger(UploadFileUtil.class);
	
	public static Map<String,Progress> keyMap=new HashMap<String,Progress>();
	private static String[] excludeEndChars=new String[]{":","?"};
	
	public static String checkedFileName(String fileName,String... limitSuffixs){
		if(fileName.lastIndexOf(".")<0)
			throw new RuntimeException("文件没有后缀名");
		else{
			String endName=fileName.substring(fileName.lastIndexOf(".")+1);
			endName=endName.toLowerCase();
			for(String endChar:excludeEndChars){
				if(endName.toLowerCase().indexOf(endChar)>0){
					throw new RuntimeException("后缀名不能含有非法字符\""+endChar+"\"");
				}
			}
			if(limitSuffixs.length<=0){
				return endName;
			}
			for(String suffix:limitSuffixs){
				if(suffix.toLowerCase().equals(endName)){
					return suffix;
				}
			}
			throw new RuntimeException("非法的后缀");
		}
	}
	/**
	 * 上传文件
	 * 
	 * @param request
	 *            http request
	 * @param response
	 *            htp response
	 * @throws IOException
	 *             IOException
	 */
	@SuppressWarnings("unchecked")
	public static void upload(HttpServletRequest request,
			HttpServletResponse response,FileProp fileProp,String fileName) throws IOException {
		LOG.info("客户端提交类型: " + request.getContentType());
		if (request.getContentType() == null) {
			throw new IOException(
					"the request doesn't contain a multipart/form-data stream");
		}

		Progress p = UploadFileUtil.keyMap.get(fileProp.getKey());
		
		if(request.getContentLength() > fileProp.getMaxSize()){
			p.setErrorMessage("文件大小过大，限制"+fileProp.getLimitDes());
			p.setComplete(true);
			p.setExceptionMaxSize(true);
			return;
		}
		// 设置上传文件总大小
		p.setLength(request.getContentLength());
		
		LOG.info("上传文件大小为 : " + p.getLength());
		// 上传临时路径
		String path = request.getSession().getServletContext().getRealPath("/");
		path=FilePathUtil.resetPath(path);
		path += "upload/";
		
		LOG.info("上传临时路径 : " + path);
		// 设置上传工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		factory.setRepository(new File(path));
		// 阀值,超过这个值才会写到临时目录
		factory.setSizeThreshold(1024 * 1024 * 10);
		
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		// 最大上传限制
		upload.setSizeMax(fileProp.getMaxSize());
		
		// 设置监听器监听上传进度
		upload.setProgressListener(p);

		try {
			LOG.info("解析上传文件....");
			List<FileItem> items = upload.parseRequest(request);
			// 20150707 add start
			boolean isCreateImage = false;
			boolean isCreateMinImage = false;
			boolean isCreateHomeImage = false;
			//String uploadPath = SubSystemHolder.getPropertiesValue("uploadPath");
			//String uploadSwitch = SubSystemHolder.getPropertiesValue("uploadSwitch");
			String uploadPath = BaseHolder.getPropertiesValue("uploadPath");
			String uploadSwitch = BaseHolder.getPropertiesValue("uploadSwitch");
			String minUploadPath = "min" + uploadPath;
			String homeUploadPath = "home" + uploadPath;
			//String minUploadSwitch = SubSystemHolder.getPropertiesValue("minUploadSwitch");
			File newFile;
			File outFile;
			String tempPath = "";
			String minTempPath = "";
			String homeTempPath = "";
			
			if (StringUtils.isEmpty(uploadSwitch) || StringUtils.isEmpty(uploadPath)) {
				isCreateImage = false;
			} else {
				if ("on".equals(uploadSwitch)) {
					isCreateImage = true;
					uploadPath = uploadPath.replace("\\", "/");
					if (!uploadPath.endsWith("/")) {
						uploadPath += "/";
					}
					
					tempPath = request.getSession().getServletContext().getRealPath("/") + uploadPath;
				} else {
					isCreateImage = false;
				}
			}
			//以下一段判断是否有缩略图存储
			if (StringUtils.isEmpty(minUploadPath) || StringUtils.isEmpty(minUploadPath)) {
				isCreateMinImage = false;
			} else {
				if ("on".equals(uploadSwitch)) {
					isCreateMinImage = true;
					minUploadPath = minUploadPath.replace("\\", "/");
					if (!minUploadPath.endsWith("/")) {
						minUploadPath += "/";
					}
					
					minTempPath = request.getSession().getServletContext().getRealPath("/") + minUploadPath;
				} else {
					isCreateMinImage = false;
				}
			}
			//以下一段判断是否有首页缩略图
			if (StringUtils.isEmpty(homeUploadPath) || StringUtils.isEmpty(homeUploadPath)) {
				isCreateHomeImage = false;
			} else {
				if ("on".equals(uploadSwitch)) {
					isCreateHomeImage = true;
					homeUploadPath = homeUploadPath.replace("\\", "/");
					if (!homeUploadPath.endsWith("/")) {
						homeUploadPath += "/";
					}
					
					homeTempPath = request.getSession().getServletContext().getRealPath("/") + homeUploadPath;
				} else {
					isCreateHomeImage = false;
				}
			}
			
			
			// 20150707 add end

			LOG.info("上传数据...");
			for (FileItem item : items) {
				//String fileName = item.getName();
				if(StringUtil.isEmpty(fileName)||item.getInputStream()==null||StringUtil.isEmpty(item.getFieldName())){
					continue;
				}
				if(!item.getFieldName().equals(fileProp.getCurrentName())){
					continue;
				}
				fileName=fileName.replace("\\", "/");
				if (fileName.lastIndexOf("/") >= 0) {
					fileName = fileName.substring(fileName.lastIndexOf("/"));
				}
				if(request.getAttribute("fileGuid")!=null){
					fileProp.setFileId(request.getAttribute("fileGuid").toString());
				}
				String guid = saveToDB(fileProp, item, fileName);
				
				p.setFileName(fileName);
				
				p.setFileGuid(guid);
				
				// 20150707 add start
				if ("image".equalsIgnoreCase(fileProp.getFileType()) && isCreateImage) {
					newFile = new File(tempPath);
					if (!newFile.exists()) {
						newFile.mkdir();
					}
					
					outFile = new File(tempPath, fileName);
					if (!outFile.exists()) {
						outFile.createNewFile();
					} else {
						outFile.delete();
						outFile.createNewFile();
					}
					
					ImageIO.setUseCache(false);
					byte[] content = Byte_File_Object.getBytesFromFile(item.getInputStream());
					ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content);
					BufferedImage newImage = ImageIO.read(byteArrayInputStream);
					ImageIO.write(newImage, UploadFileUtil.checkedFileName(fileName), outFile);
					
					//对存在项目的图片进行处理
					
					
				}
				//对应用中心和门户缩略图进行存储
				/*if ("image".equalsIgnoreCase(fileProp.getFileType()) && isCreateMinImage) {
					newFile = new File(minTempPath);
					if (!newFile.exists()) {
						newFile.mkdir();
					}
					
					outFile = new File(minTempPath, fileName);
					if (!outFile.exists()) {
						outFile.createNewFile();
					} else {
						outFile.delete();
						outFile.createNewFile();
					}
					String inPath = tempPath+fileName;
					inPath.replace("\\", "/");
					impressImage(tempPath+fileName, minTempPath+fileName, 80, 80);
					ImageIO.setUseCache(false);
					byte[] content = Byte_File_Object.getBytesFromFile(item.getInputStream());
					ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content);
					BufferedImage newImage = ImageIO.read(byteArrayInputStream);
					ImageIO.write(newImage, UploadFileUtil.checkedFileName(fileName), outFile);
					BufferedImage img = ImageIO.read(byteArrayInputStream);      // 构造Image对象
					BufferedImage bi = new BufferedImage(80, 80, BufferedImage.TYPE_INT_RGB);     
			        Graphics2D g2 = (Graphics2D)bi.getGraphics();   
			        g2.setBackground(Color.WHITE); 
			        g2.clearRect(0, 0, 80, 80);     
			        g2.drawImage(img, 0, 0, 80, 80, null); // 绘制缩小后的图
			        File destFile = new File(minTempPath+fileName);
			        if(destFile.exists()){
			        	destFile.delete();
			        }
					FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流
					JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
					encoder.encode(bi); // JPEG编码
					out.close();
					
					ImageIO.setUseCache(false);
					byte[] content = Byte_File_Object.getBytesFromFile(item.getInputStream());
					ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content);
					BufferedImage newImage = ImageIO.read(byteArrayInputStream);
					ImageIO.write(newImage, UploadFileUtil.checkedFileName(fileName), outFile);
					BufferedImage img = ImageIO.read(byteArrayInputStream);      // 构造Image对象
					
					int width = img.getWidth(null);
					int height = img.getHeight(null);  // 得到源图长
					int w=120;
					//if (width / height < w / h) {
						//resizeByWidth(w);
					int h = (int) (height * w / width);
					} else {
						//resizeByHeight(h);
						w = (int) (width * h / height);
					}
					BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);     
			        Graphics2D g2 = (Graphics2D)bi.getGraphics();     
			        g2.setBackground(Color.WHITE);     
			        g2.clearRect(0, 0, w, h);     
			        g2.drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图
			        File destFile = new File(minTempPath+fileName);
			        if(destFile.exists()){
			        	destFile.delete();
			        }
					FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流
					// 可以正常实现bmp、png、gif转jpg
					//JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
					//encoder.encode(bi); // JPEG编码
					out.close();
					
					
					//再次把图片压缩成圆形图片
					BufferedImage bi1 = ImageIO.read(new File(minTempPath+fileName));

					// 根据需要是否使用 BufferedImage.TYPE_INT_ARGB
					BufferedImage bi2 = new BufferedImage(bi1.getWidth(), bi1.getHeight(),
							BufferedImage.TYPE_INT_RGB);

					Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, bi1.getWidth(), bi1
							.getHeight());

					Graphics2D g3 = bi2.createGraphics();
					Color color= new Color(1.0F, 0.75F, 0.0F, 0.45F);
			        //g2.setBackground(Color.WHITE); 
			        g2.setBackground(color); 
					g3.fill(new Rectangle(bi2.getWidth(), bi2.getHeight()));
					g3.setClip(shape);
					// 使用 setRenderingHint 设置抗锯齿
					g3.drawImage(bi1, 0, 0, null);
					g3.dispose();

					try {
						ImageIO.write(bi2, "jpg", new File(minTempPath+fileName));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				//对资讯缩略图进行存储
				if ("image".equalsIgnoreCase(fileProp.getFileType()) && isCreateHomeImage) {
					newFile = new File(homeTempPath);
					if (!newFile.exists()) {
						newFile.mkdir();
					}
					
					outFile = new File(homeTempPath, fileName);
					if (!outFile.exists()) {
						outFile.createNewFile();
					} else {
						outFile.delete();
						outFile.createNewFile();
					}
					String inPath = tempPath+fileName;
					inPath.replace("\\", "/");
					impressImage(tempPath+fileName, minTempPath+fileName, 80, 80);
					ImageIO.setUseCache(false);
					byte[] content = Byte_File_Object.getBytesFromFile(item.getInputStream());
					ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content);
					BufferedImage newImage = ImageIO.read(byteArrayInputStream);
					ImageIO.write(newImage, UploadFileUtil.checkedFileName(fileName), outFile);
					BufferedImage img = ImageIO.read(byteArrayInputStream);      // 构造Image对象
					
					int width = img.getWidth(null);
					int height = img.getHeight(null);  // 得到源图长
					int w=380;
					//if (width / height < w / h) {
						//resizeByWidth(w);
					int h = (int) (height * w / width);
					} else {
						//resizeByHeight(h);
						w = (int) (width * h / height);
					}
					BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);     
			        Graphics2D g2 = (Graphics2D)bi.getGraphics();     
			        g2.setBackground(Color.WHITE);     
			        g2.clearRect(0, 0, w, h);     
			        g2.drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图
			        File destFile = new File(homeTempPath+fileName);
			        if(destFile.exists()){
			        	destFile.delete();
			        }
					FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流
					// 可以正常实现bmp、png、gif转jpg
					JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
					encoder.encode(bi); // JPEG编码
					out.close();
					
				}*/
				
				
				
				
				// 20150707 add end
				
				LOG.info("完成上传文件!");

				item.delete();
				LOG.info("删除临时文件!");

				p.setComplete(true);
				LOG.info("更新progress对象状态为完成状态!");
				return;
			}
			p.setComplete(true);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("上传文件出现异常, 错误原因 : " + e.getMessage());
			// 发生错误,进度信息对象设置为完成状态
			p.setComplete(true);
			
			UploadFileUtil.keyMap.remove(fileProp.getKey());
		}
	}
	
	/**
	 * 图片压缩
	 * @throws IOException 
	 */
	public static void impressImage(String inPath, String outPath, int outWidth, int outHeight) throws IOException{
		File file = new File(inPath);// 读入文件
		Image img = ImageIO.read(file);      // 构造Image对象
		int width = img.getWidth(null);    // 得到源图宽
		int height = img.getHeight(null);  // 得到源图长
		
		BufferedImage bi = new BufferedImage(outWidth, outHeight, BufferedImage.TYPE_INT_RGB);     
        Graphics2D g2 = (Graphics2D)bi.getGraphics();     
        g2.setBackground(Color.WHITE);     
        g2.clearRect(0, 0, outWidth, outHeight);     
        g2.drawImage(img, 0, 0, outWidth, outHeight, null); // 绘制缩小后的图
        File destFile = new File(outPath);
		FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流
		// 可以正常实现bmp、png、gif转jpg
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		encoder.encode(bi); // JPEG编码
		out.close();
	}
	

	private static String saveToDB(FileProp fileProp,FileItem item,String fileName) throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		if(fileProp.getFileType().equalsIgnoreCase("image")){
			byte[] content=Byte_File_Object.getBytesFromFile(item.getInputStream());
			ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(content);
			try {
				ImageIO.setUseCache(false);
				BufferedImage newImage = ImageIO.read(byteArrayInputStream);
				Image croppedImage = null;
				if(croppedImage==null){
					croppedImage = Toolkit.getDefaultToolkit().createImage(newImage.getSource());
				}
				/*if(newImage.getWidth()>fileProp.getWidth()*2){
					
					Double nHeight=new Double(fileProp.getWidth()*2)/new Double(newImage.getWidth())*newImage.getHeight();
					// 压缩
					croppedImage = croppedImage.getScaledInstance(fileProp.getWidth()*2, nHeight.intValue(),Image.SCALE_DEFAULT);
					// 绘制缩小后的图
					BufferedImage tag = new BufferedImage(fileProp.getWidth()*2, nHeight.intValue(),BufferedImage.TYPE_INT_RGB);
					Graphics g = tag.getGraphics();
					g.drawImage(croppedImage, 0, 0, null); 
					g.dispose();
					ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
					// 输出到字节流
					ImageIO.write(tag, UploadFileUtil.checkedFileName(fileName), outputStream);
					content=outputStream.toByteArray();
				}*/
				
				if(newImage.getWidth()>600){
					
					Double nHeight=new Double(500)/new Double(newImage.getWidth())*newImage.getHeight();
					// 压缩
					croppedImage = croppedImage.getScaledInstance(500, nHeight.intValue(),Image.SCALE_DEFAULT);
					// 绘制缩小后的图
					BufferedImage tag = new BufferedImage(500, nHeight.intValue(),BufferedImage.TYPE_INT_RGB);
					Graphics g = tag.getGraphics();
					g.drawImage(croppedImage, 0, 0, null); 
					g.dispose();
					ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
					// 输出到字节流
					ImageIO.write(tag, UploadFileUtil.checkedFileName(fileName), outputStream);
					content=outputStream.toByteArray();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			String uploadPath = BaseHolder.getPropertiesValue("uploadPath");
			String uploadSwitch = BaseHolder.getPropertiesValue("uploadSwitch");
			String minUploadPath = request.getSession().getServletContext().getRealPath("/") + "min" + uploadPath +"/" + fileName;
			String homeUploadPath = request.getSession().getServletContext().getRealPath("/") + "home" + uploadPath + "/" + fileName;
			String minPath = request.getSession().getServletContext().getRealPath("/") + "min" + uploadPath;
			String homePath = request.getSession().getServletContext().getRealPath("/") + "home" + uploadPath;
			File minFileDir = new File(minPath);
			if (!minFileDir.exists()) {
				minFileDir.mkdir();
			}
			File homeFileDir = new File(homePath);
			if (!homeFileDir.exists()) {
				homeFileDir.mkdir();
			}
			
			File outFile = new File(minPath, fileName);
			if (!outFile.exists()) {
				outFile.createNewFile();
			} else {
				outFile.delete();
				outFile.createNewFile();
			}
			
			File homeFile = new File(homePath, fileName);
			if (!homeFile.exists()) {
				homeFile.createNewFile();
			} else {
				homeFile.delete();
				homeFile.createNewFile();
			}
			
			
			if (!StringUtils.isEmpty(uploadSwitch) || !StringUtils.isEmpty(uploadPath)) {
				FileImageOutputStream imageOutput = null;
				FileImageOutputStream minImageOutput = null;
				imageOutput  = new FileImageOutputStream(new File(minUploadPath));  
				imageOutput.write(content, 0, content.length);  
				imageOutput.close();  
				minImageOutput  = new FileImageOutputStream(new File(homeUploadPath));  
				minImageOutput.write(content, 0, content.length);  
				minImageOutput.close();  
			}
            
            
			ImageDB imageDB;
			if(!StringUtil.isEmpty(fileProp.getFileId())){
				imageDB=ImageDBUtil.getImageDBByGuid(fileProp.getFileId());
				if(imageDB!=null){
					imageDB=ImageDBUtil.updateFileToDB(item.getName(),content, imageDB);
				}else{
					imageDB=ImageDBUtil.saveFileToDB(item.getName(),content);
				}
			}else{
				imageDB = ImageDBUtil.saveFileToDB(fileName,content);
			}
			return imageDB.getGuid();
		}else if(fileName.endsWith("mp4") || fileName.endsWith("avi") || fileName.endsWith("wmv")){
			Attachement attachement;
			String vedio = BaseHolder.getPropertiesValue("vedio", "vedioFile");
			String vedioPath = request.getSession().getServletContext().getRealPath("/")  + vedio;
			File minFileDir = new File(vedioPath);
			if (!minFileDir.exists()) {
				minFileDir.mkdir();
			}
			File vedioFile = new File(vedioPath, fileName);
			if (!vedioFile.exists()) {
				vedioFile.createNewFile();
			} else {
				vedioFile.delete();
				vedioFile.createNewFile();
			}
			FileOutputStream fop= new FileOutputStream(vedioFile);
			byte[] content=Byte_File_Object.getBytesFromFile(item.getInputStream());
			fop.write(content);
			fop.flush();
			fop.close();
			Movie2Flv.getInstace().tran(fileName);
			if(!StringUtil.isEmpty(fileProp.getFileId())){
				attachement=AttachementUtil.getAttachementByGuid(fileProp.getFileId());
				attachement=AttachementUtil.updateAttachement(item.getName(),item.getInputStream(), attachement);
			}
			else{
				attachement = AttachementUtil.saveAttachement(fileName,item.getInputStream());
			}
			return attachement.getGuId();
		}
		else{
			Attachement attachement;
			if(!StringUtil.isEmpty(fileProp.getFileId())){
				attachement=AttachementUtil.getAttachementByGuid(fileProp.getFileId());
				if(attachement!=null){
					attachement=AttachementUtil.updateAttachement(item.getName(),item.getInputStream(), attachement);
				}else{
					attachement=AttachementUtil.saveAttachement(item.getName(),item.getInputStream());
				}
			}else{
				attachement = AttachementUtil.saveAttachement(fileName,item.getInputStream());
			}
			return attachement.getGuId();
		}
	}
}