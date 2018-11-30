package com.zfsoft.mobile.file.servlet;

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

import com.common.Config;
import com.google.gson.Gson;
import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.core.util.Byte_File_Object;
import com.zfsoft.mobile.common.service.IMobileCommonService;
import com.zfsoft.mobile.servlet.entity.ResultEntity;
import com.zfsoft.mobile.webservices.entity.MemoDB;
import com.zfsoft.mobile.webservices.entity.MemoPictureEntity;
import com.zfsoft.untils.CodeUtil;
import com.zfsoft.util.base.StringUtil;

public class MemoServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -989016530111136418L;

	private static Logger logger = Logger.getLogger(MemoServlet.class);
	
	public MemoServlet(){
		
	}
	
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
        //Map map = new HashMap();  
        FileItemFactory factory = new DiskFileItemFactory();  
        ServletFileUpload upload = new ServletFileUpload(factory);  
        List<FileItem> items = new ArrayList();  
        boolean isRight = false;
        String creatTime = null;
        String memoTitle = null;
        String memoCatalogId = null;
        String userName = null;
        String contentFlag = null;
        String memoFileName = null;
        String picturePath = null;
        String apptoken  = null;
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
                    if(paramName.equals("createTime")){
                    	creatTime = fValue.toString();
                    }else if(paramName.equals("memoTitle")){
                    	memoTitle = fValue.toString();
                    }else if(paramName.equals("memoCatalogId")){
                    	memoCatalogId = fValue.toString();
                    }else if(paramName.equals("userName")){
                    	userName = fValue.toString();
                    }else if(paramName.equals("contentFlag")){
                    	contentFlag = fValue.toString();
                    }else if(paramName.equals("picturePath")){
                    	picturePath = fValue.toString();
                    }else if(paramName.equals("apptoken")){
                    	apptoken = fValue.toString();
                    }
                    //logger.error("------备忘录上传中上传的是文本不应该上传文本-----");
                } else { // 获取上传文件的值  
                	
                	creatTime       = CodeUtil.decode(creatTime, apptoken);
                	memoTitle       = CodeUtil.decode(memoTitle, apptoken);
                	memoCatalogId   = CodeUtil.decode(memoCatalogId, apptoken);
                	userName        = CodeUtil.decode(userName, apptoken);
                	contentFlag     = CodeUtil.decode(contentFlag, apptoken);
                	picturePath     = CodeUtil.decode(picturePath, apptoken);
                	
                	if(StringUtil.isEmpty(creatTime)){
                		logger.error("memo upload ------:the createtime is null-----");
                	}
                	logger.error("进入备忘录else");
                	paramName = fItem.getFieldName();//userfile  
                    fValue = fItem.getInputStream(); 
                    //String filename = paramName + ".jpg";
                    String filename = fItem.getName();//路径  
                    if(!StringUtil.isEmpty(filename)) {
                    	
                    	InputStream is = fItem.getInputStream();
                    	byte[] content = null;
                        int size = is.available();
                        if(is != null && size != 0){
                        	content = Byte_File_Object.getBytesFromFile(is);
                        }
                        
                		memoFileName = filename;
                		
                		
                		String filePath = request.getSession().getServletContext().getRealPath("/") + "memoFile"; 
                		filePath = filePath.replace("\\", "/");
                		File outFile;
                		MemoPictureEntity pictureEnity;
                		int count = 0;
                		if(!StringUtil.isEmpty(picturePath)){
                			String[] picturePathList = picturePath.split(",");
                			if(picturePathList != null && picturePathList.length > 0){
                				for(String picPath : picturePathList){
                					outFile = new File(filePath, picPath);
                					if (!outFile.exists()) {
                						out = response.getWriter();  
                						out.print("{\"success\":\"flase\", \"msg\":\"图片未上传至服务器\"}");  
                						out.close(); 
                					}else{
                						pictureEnity = new MemoPictureEntity();
                						pictureEnity.setMemoFileName(memoFileName);
                						pictureEnity.setPictureContent(Byte_File_Object.getBytesFromFile(outFile));
                						pictureEnity.setTitle(picPath);
                						pictureEnity.setPicturePath("memoFile/"+picPath);
                						count = mobileCommonService.getMemoPicture(pictureEnity);
                						if(count == 0){
                							mobileCommonService.insertMemoPicture(pictureEnity);
                						}
                					}
                					
                				}
                			}
                		}
                		
                		
                		MemoDB memoDB = new MemoDB();
                        memoDB.setCreateTime(creatTime);
                        memoDB.setMemoFileName(filename);
                        memoDB.setMemoContent(content);
                        memoDB.setUserName(userName);
                        memoDB.setMemoTitle(memoTitle);
                        memoDB.setMemoCatalogId(memoCatalogId);
                        memoDB.setContentFlag(contentFlag);
                        memoDB.setMemoPath("memoFile/"+filename);
                        MemoDB memoDBQuery = new MemoDB();
                        memoDBQuery.setMemoFileName(filename);
                        PageList<MemoDB> memoList = mobileCommonService.getMyMemoList(memoDBQuery);
                        MemoDB memoEntity = memoList != null && memoList.size() > 0 ? memoList.get(0) : null;
                		if(memoEntity == null){
                			mobileCommonService.insertMemo(memoDB);
                		}else{
                			mobileCommonService.updateMemo(memoDB);
                		}
                		if((memoEntity != null && memoEntity.getMemoContent() != null)
                				|| content != null){
                			//备忘录文件夹不存在则创建文件
                			filePath = filePath.replace("\\", "/");
                			File newFile = new File(filePath);
                			if (!newFile.exists()) {
                				newFile.mkdir();
                			}
                			
                			outFile = new File(filePath, filename);
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
                				fileOutputStream.write(memoEntity.getMemoContent(), 0, memoEntity.getMemoContent().length);
                			}
                			fileOutputStream.close();
                			
                			
                		}
                        
                        
                        
                        isRight = true;
                        
                        
                    }  
                }  
            }  
        } catch (Exception e) {  
            //e.printStackTrace(); 
        	logger.error("memo upload exception:");  
            logger.error(e,e.fillInStackTrace());
        }  
        // 数据处理  
        try {  
            out = response.getWriter();  
            String outStr = null;
            Gson gson = new Gson();
            ResultEntity<String> result = null;
            if(Config.getString("isNewVersion", "no").equals("yes") && isRight){
            	result = new ResultEntity<String>(1, "成功！", null);
            	outStr = gson.toJson(result);
            }else if(Config.getString("isNewVersion", "no").equals("yes") && !isRight){
            	result = new ResultEntity<String>(0, "有异常", "存在异常，请确定上传的文件类型为'.rtfd'!");
            	outStr = gson.toJson(result);
            }else if(Config.getString("isNewVersion", "no").equals("no") && isRight){
            	outStr = "{\"success\":\"true\", \"msg\":\"memo upload success\"}";
            }else{
            	outStr = "{\"success\":\"flase\", \"msg\":\"there is exception，or the file is not the tye,which is '.rtfd'.\"}";  
            }
            out.write(outStr);
            out.flush();
            out.close();
//            out.print(isRight == true ? "{\"success\":\"true\", \"msg\":\"memo upload success\"}" : 
//            				"{\"success\":\"flase\", \"msg\":\"there is exception，or the file is not the tye,which is '.rtfd'.\"}");  
//            out.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
	}
}
