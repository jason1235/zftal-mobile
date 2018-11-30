package com.zfsoft.mobile.file.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.common.system.BaseHolder;
import com.zfsoft.hrm.core.util.Byte_File_Object;
import com.zfsoft.hrm.file.entity.ImageDB;
import com.zfsoft.mobile.common.service.IMobileCommonService;
import com.zfsoft.mobile.servlet.entity.ResultEntity;
import com.zfsoft.util.base.StringUtil;

public class FileServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6589776692359806525L;
	
	private static Logger logger = Logger.getLogger(FileServlet.class);
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	   throws ServletException, IOException {
	   doPost(request, response);
	}
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	   throws ServletException, IOException {
		
		PrintWriter out = null;  
        response.setContentType("text/html;charset=UTF-8");  
        String uri = request.getRequestURI();
	    boolean flag = uri.contains("?") ? true : false;
	    boolean hasPath = false;
	    String endPath = "";
	    if(flag){
	    	endPath = uri.substring(uri.lastIndexOf("/"),uri.lastIndexOf("?"));
	    	hasPath = true;
	    }else{
	    	endPath = uri.substring(uri.lastIndexOf("/"),uri.length());
	    	hasPath = true;
	    }
        
        String time = request.getParameter("time");
        
        //Map map = new HashMap();  
        FileItemFactory factory = new DiskFileItemFactory();  
        ServletFileUpload upload = new ServletFileUpload(factory);  
        List<FileItem> items = new ArrayList();  
        boolean isImage = false;
        try {  
            items = upload.parseRequest(request);  
            // 得到所有的文件  
            Iterator<FileItem> it = items.iterator();  
            IMobileCommonService mobileCommonService = (IMobileCommonService)SpringHolder.getBean("mobileCommonService"); 
            while (it.hasNext()) {  
                FileItem fItem = (FileItem) it.next();  
                String paramName = "";  
                Object fValue = null;  
                if (fItem.isFormField()) { // 普通文本框的值  
                	paramName = fItem.getFieldName();  
                    fValue = fItem.getString("UTF-8");  
                    logger.error("------图片上传中上传的是文本不应该上传文本-----");
                } else { // 获取上传文件的值  
                	logger.error("进入图片else");
                	paramName = fItem.getFieldName();//userfile  
                    fValue = fItem.getInputStream(); 
                    //String filename = paramName + ".jpg";
                    String filename = fItem.getName();//路径  
                    if(!StringUtil.isEmpty(filename)) {  
                        InputStream is = fItem.getInputStream();  
                        
                        byte[] content = Byte_File_Object.getBytesFromFile(is);
                        ImageDB ImageDB=new ImageDB();
                		ImageDB.setFileName(filename);
                		ImageDB.setFileContent(content);
                		ImageDB.setOpUser(paramName);
                		
                		String path = BaseHolder.getPropertiesValue("MyPicture","headPicture");
                		ImageDB.setPath(path+"/"+filename);
                		
                		List<ImageDB> imageList = mobileCommonService.getMyPicture(paramName);
                		ImageDB image = imageList != null && imageList.size() > 0 ? imageList.get(0) : null;
                		if(image == null){
                			mobileCommonService.insertMyPicture(ImageDB);
                		}else{
                			mobileCommonService.updateMyPicture(ImageDB);
                		}
                		
                		//图片文件夹不存在则创建文件
                        String filePath = request.getSession().getServletContext().getRealPath("/") + path; 
                        filePath = filePath.replace("\\", "/");
                        File newFile = new File(filePath);
                		if (!newFile.exists()) {
                			newFile.mkdir();
                		}
                		
                        File outFile = new File(filePath, filename);
                        if (!outFile.exists()) {
    						outFile.createNewFile();
	    				} else {
	    					outFile.delete();
	    					outFile.createNewFile();
	    				}
                       /* ImageIO.setUseCache(false);
        				ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content);
        				
        				
        				BufferedImage img = ImageIO.read(byteArrayInputStream);      // 构造Image对象
        				int width = img.getWidth();
        				int height = img.getHeight();
    					BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);     
    			        Graphics2D g2 = (Graphics2D)bi.getGraphics();     
    			        //g2.setBackground(Color.WHITE);     
    			        g2.clearRect(0, 0, width, height);     
    			        g2.drawImage(img, 0, 0, width, height, null); // 绘制缩小后的图
    			        File destFile = new File(filePath + "/" + filename);
    			        if(destFile.exists()){
    			        	destFile.delete();
    			        }
    					FileOutputStream outStream = new FileOutputStream(destFile); // 输出到文件流
    					JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(outStream);
    					encoder.encode(bi); // JPEG编码
    					//outStream.flush();
    					outStream.close();*/
        				
        				
    					FileImageOutputStream imageOutput = null;
    					imageOutput  = new FileImageOutputStream(outFile);  
    					imageOutput.write(content, 0, content.length);  
    					imageOutput.close();  
        				
        				/*BufferedImage newImage = ImageIO.read(byteArrayInputStream);
        		        File destFile = new File(filePath + "/" + filename);
        		        if(destFile.exists()){
        		        	destFile.delete();
        		        }
        				
        				ImageIO.write(newImage, UploadFileUtil.checkedFileName(filename), destFile);*/
        				isImage = true;
                    }  
                }  
            }  
        } catch (Exception e) {  
            e.printStackTrace(); 
            logger.error(e);
            logger.error("上传图片出错");  
        }  
        // 数据处理  
        try {  
        	out = response.getWriter();  
        	if(hasPath && endPath.equals("/upload")){
        		ResultEntity result;
        		if(isImage){
        			result = new ResultEntity<String>(1, "图片上传成功！", "");
        		}else{
        			result = new ResultEntity<String>(0, "上传有异常，或图片上传中上传的是文本不应该上传文本！", "");
        		}
        		Gson gson = new Gson();
        		out.print(gson.toJson(result));
        	}else{
        		out.print(isImage == true ? "{\"success\":\"true\", \"msg\":\"图片上传成功\"}" : "{\"success\":\"flase\", \"msg\":\"上传有异常，或图片上传中上传的是文本不应该上传文本\"}");  
        	}
            out.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
	}

}
