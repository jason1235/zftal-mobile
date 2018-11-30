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
import com.zfsoft.hrm.core.util.Byte_File_Object;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.util.base.StringUtil;

public class MemoPictureServlet  extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -989016530111136418L;

	private static Logger logger = Logger.getLogger(MemoPictureServlet.class);
	
	public MemoPictureServlet(){
		
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
        String apptoken = null;
        String filename = null;
        String picturePath = null;
        try {  
            items = upload.parseRequest(request);  
            // 得到所有的文件  
            Iterator<FileItem> it = items.iterator();  
            while (it.hasNext()) {  
                FileItem fItem = (FileItem) it.next();  
                if (fItem.isFormField()) { // 普通文本框的值  
                	String paramName = "";  
                    Object fValue = null;  
                	paramName = fItem.getFieldName();  
                    fValue = fItem.getString("UTF-8");  
                    if(paramName.equals("apptoken")){
                    	apptoken = fValue.toString();
                    }
                } else { // 获取上传文件的值  
                	if(!ApptokenUtils.compare(apptoken)){
                		out = response.getWriter();  
                        out.print("{\"success\":\"flase\", \"msg\":\"apptoken error.\"}");  
                        out.close();
                	}
                	logger.error("进入备忘录else");
                    //String filename = paramName + ".jpg";
                    filename = fItem.getName();//路径  
                    if(!StringUtil.isEmpty(filename)) {
                    	
                    	InputStream is = fItem.getInputStream();
                    	byte[] content = null;
                        int size = is.available();
                        if(is != null && size != 0){
                        	content = Byte_File_Object.getBytesFromFile(is);
                        }
                        
                        String filePath = request.getSession().getServletContext().getRealPath("/") + "memoFile"; 
                		filePath = filePath.replace("\\", "/");
                		picturePath = filePath  + filename;
                        File outFile = new File(filePath, filename);
            			if (!outFile.exists()) {
            				outFile.createNewFile();
            			}
            			FileOutputStream fileOutputStream = new FileOutputStream(outFile);
            			if(content != null){
            				fileOutputStream.write(content, 0, content.length);
            			}
            			is.close();
            			fileOutputStream.close();
                        
                        isRight = true;
                    }  
                }  
            }  
        } catch (Exception e) {  
            //e.printStackTrace(); 
        	logger.error("memo picture upload-exception:");  
            logger.error(e,e.fillInStackTrace());
        }  
        // 数据处理  
        try {  
            out = response.getWriter();  
            out.print(isRight == true ? "{\"success\":\"true\"," +
            				" \"msg\":\"memo upload success\"," +
		            		"\"path\":\"" + Config.getString("suploadPath") + "/memoFile/" +  filename + "\""+
		            		"}" : 
            				"{" +
            				"\"success\":\"flase\", " +
            				"\"msg\":\"there is exception，or the file is not the tye,which is not picture.\"}"
            				);  
            out.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
	}
}
