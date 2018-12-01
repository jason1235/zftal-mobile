package com.zfsoft.mobile.suggest.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.hrm.core.util.Byte_File_Object;
import com.zfsoft.mobile.suggest.entity.SuggestEntity;
import com.zfsoft.mobile.suggest.entity.suggestPictureEntity;
import com.zfsoft.mobile.suggest.service.ISuggestService;
import com.zfsoft.untils.CodeUtil;
import com.zfsoft.util.base.StringUtil;

public class SuggestServlet extends HttpServlet {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(SuggestServlet.class);
	
	public void destroy() {
		  super.destroy(); 
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	   throws ServletException, IOException {
	   doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	   throws ServletException, IOException {
		
		PrintWriter out = null;  
        response.setContentType("text/html;charset=UTF-8");  
        FileItemFactory factory = new DiskFileItemFactory();  
        ServletFileUpload upload = new ServletFileUpload(factory);  
        List<FileItem> items = new ArrayList();  
        boolean isRight = false;
        String userName = null;
        String schoolCode = null;
        String versionNumber = null;
        String telephone = null;
        String qq = null;
        String textContent = null;
        suggestPictureEntity pictureEntity = null;
        String suggestId = null;
        String apptoken = null;
        SuggestEntity suggestEntity = new SuggestEntity();
        ISuggestService suggestService = (ISuggestService)SpringHolder.getBean("suggestService");
        try {  
            items = upload.parseRequest(request);  
            // 得到所有的文件  
            Iterator<FileItem> it = items.iterator();  
            while (it.hasNext()) {  
                FileItem fItem = (FileItem) it.next();  
                String paramName = "";  
                Object fValue = null;  
                if (fItem.isFormField()) { // 普通文本框的值  
                	paramName = fItem.getFieldName();  
                    fValue = fItem.getString("UTF-8");  
                    if(paramName.equals("schoolCode")){
                    	schoolCode = fValue.toString();
                    }else if(paramName.equals("versionNumber")){
                    	versionNumber = fValue.toString();
                    }else if(paramName.equals("userName")){
                    	userName = fValue.toString();
                    }else if(paramName.equals("telephone")){
                    	telephone = fValue.toString();
                    }else if(paramName.equals("qq")){
                    	qq = fValue.toString();
                    }else if(paramName.equals("apptoken")){
                    	apptoken = fValue.toString();
                    }else if(paramName.equals("textContent")){
                    	textContent = fValue.toString();
                    	qq       		= CodeUtil.decode(qq, apptoken);
                    	schoolCode      = CodeUtil.decode(schoolCode, apptoken);
                    	telephone       = CodeUtil.decode(telephone, apptoken);
                    	versionNumber   = CodeUtil.decode(versionNumber, apptoken);
                    	userName        = CodeUtil.decode(userName, apptoken);
                    	suggestEntity.setQq(qq);
                    	suggestEntity.setSchoolCode(schoolCode);
                    	suggestEntity.setTelephone(telephone);
                    	suggestEntity.setTextContent(textContent);
                    	suggestEntity.setVersionNumber(versionNumber);
                    	suggestEntity.setUserName(userName);
                    	suggestService.insertSuggestMain(suggestEntity);
                    	suggestId = suggestEntity.getId();
                    }
                } else { // 获取上传文件的值  
                	logger.error("进入建议反馈图片上传方法");
                	paramName = fItem.getFieldName();//userfile  
                    fValue = fItem.getInputStream(); 
                    String filename = fItem.getName();//路径  
                    if(!StringUtil.isEmpty(filename)) {  
                        InputStream is = fItem.getInputStream();  
                        
                        byte[] content = null;
                        int size = is.available();
                        if(is != null && size != 0){
                        	content = Byte_File_Object.getBytesFromFile(is);
                        }
                        pictureEntity = new suggestPictureEntity();
                        pictureEntity.setSuggestId(suggestId);
                        pictureEntity.setPictureContent(content);
                        pictureEntity.setTitle(filename);
                        
                        String filePath = request.getSession().getServletContext().getRealPath("/") + "suggestPicture"; 
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
            			
            			FileOutputStream fileOutputStream = new FileOutputStream(outFile);
            			if(content != null){
            				fileOutputStream.write(content, 0, content.length);
            			}else{
            				fileOutputStream.write(pictureEntity.getPictureContent(), 0, pictureEntity.getPictureContent().length);
            			}
            			fileOutputStream.close();
            			is.close();
            			pictureEntity.setPicturePath("suggestPicture/"+filename);
            			
            			suggestService.insertSuggestPicture(pictureEntity);
            			
                        
                    }  
                }  
            }  
        } catch (Exception e) {  
            e.printStackTrace(); 
            logger.error(e);
            logger.error("memo upload------: exception");  
            out = response.getWriter();  
            out.print("{\"success\":\"false\", \"msg\":\"意见反馈失败\"}");  
            out.close();
        }  
		
		
        // 数据处理  
        try {  
            out = response.getWriter();  
            out.print("{\"success\":\"true\", \"msg\":\"意见反馈成功\"}");  
            out.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
	}
}
