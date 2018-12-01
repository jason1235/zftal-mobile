package com.zfsoft.mobile.file.servlet;

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
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.log4j.Logger;

import com.common.WebServiceConf;
import com.oa.service.IOaMobileService;
import com.zfsoft.hrm.core.util.Byte_File_Object;
import com.zfsoft.untils.CodeUtil;
import com.zfsoft.util.base.StringUtil;

public class UpdateZwByIDServlet extends HttpServlet {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(UpdateZwByIDServlet.class);
	
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
        String yhm = null;
        String id = null;
        String sign = null;
        String apptoken = null;
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
                    if(paramName.equals("yhm")){
                    	yhm = fValue.toString();
                    }else if(paramName.equals("id")){
                    	id = fValue.toString();
                    }else if(paramName.equals("sign")){
                    	sign = fValue.toString();
                    }else if(paramName.equals("apptoken")){
                    	apptoken = fValue.toString();
                    }
                } else { // 获取上传文件的值  
                	yhm       		= CodeUtil.decode(yhm, apptoken);
                	id      		= CodeUtil.decode(id, apptoken);
                	sign       		= CodeUtil.decode(sign, apptoken);
                	logger.error("进入金格上传方法");
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
                        
//                        String filePath = request.getSession().getServletContext().getRealPath("/") + "jinge"; 
//            			filePath = filePath.replace("\\", "/");
//            			File newFile = new File(filePath);
//            			if (!newFile.exists()) {
//            				newFile.mkdir();
//            			}
//            			
//            			File outFile = new File(filePath, filename);
//            			if (!outFile.exists()) {
//            				outFile.createNewFile();
//            			} else {
//            				outFile.delete();
//            				outFile.createNewFile();
//            			}
//            			
//            			FileOutputStream fileOutputStream = new FileOutputStream(outFile);
            			if(content != null){
        					logger.error("金格文件传输："+"yhm="+yhm+",id="+id+",sign="+sign);
            				String result = "";
            				// 调用WebService
            				JaxWsProxyFactoryBean oafactory = new JaxWsProxyFactoryBean();
            				oafactory.setServiceClass(IOaMobileService.class);
            				oafactory.setAddress(WebServiceConf.SERVICE_OASERVICE);// 接口地址

            				IOaMobileService service = (IOaMobileService) oafactory.create();
            				result = service.updateZwByID(yhm, id, content, sign);
            				//result = service.updateZwByID("999", "14950918824222847690", content, "AZyN+oDk3/ylldP8LQFDFQ==");
        					logger.error("金格文件传输返回为："+result);
            				
//            				fileOutputStream.write(content, 0, content.length);
//            				fileOutputStream.close();
            			}else{
            				out = response.getWriter();  
            	            out.print("{\"success\":\"false\", \"msg\":\"文件未传输\"}");  
            	            out.close();
            			}
            			is.close();
                    }  
                }  
            }  
        } catch (Exception e) {  
            e.printStackTrace(); 
            logger.error(e);
            logger.error("memo upload------: exception");  
            out = response.getWriter();  
            out.print("{\"success\":\"false\", \"msg\":\"操作失败\"}");  
            out.close();
        }  
		
		
        // 数据处理  
        try {  
            out = response.getWriter();  
            out.print("{\"success\":\"true\", \"msg\":\"操作成功\"}");  
            out.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
	}
}
