package com.zfsoft.mobile.ballot.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.http.HttpServletRequest;


//从数据库下载图片到指定路径
public class CommonUtils {

	public String getDownPic(String filePath,byte[] content,HttpServletRequest request) throws IOException {
		 System.out.println(request.getSession().getServletContext().getRealPath("/"));
		 String[] sss = filePath.split("/");
		 File file = new File(request.getSession().getServletContext().getRealPath("/") + sss[0]);
		 filePath = request.getSession().getServletContext().getRealPath("/") + filePath;
		 File outFile = new File(filePath);
		 //判断路径是否存在，如果不存在就创建一个
     	 if (!file.exists()) { 
     		file.mkdirs();
     	 }
         
     	 if (outFile.exists()) { 
      		return "1";
      	 }
     	 
		FileImageOutputStream imageOutput = null;
		imageOutput  = new FileImageOutputStream(outFile);  
		imageOutput.write(content, 0, content.length);  
		imageOutput.close(); 
		
		return "1";
	}
	
	
	
	 /** 
     * 获得指定文件的byte数组 
     */ 
	public static byte[] getBytes(String filePath){  
        byte[] buffer = null;  
        try {  
            File file = new File(filePath);  
            FileInputStream fis = new FileInputStream(file);  
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);  
            byte[] b = new byte[1000];  
            int n;  
            while ((n = fis.read(b)) != -1) {  
                bos.write(b, 0, n);  
            }  
            fis.close();  
            bos.close();  
            buffer = bos.toByteArray();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return buffer;  
    }
	
	public int getWeek(Date time){
		SimpleDateFormat format = new  SimpleDateFormat("yyyy-MM-dd" );  
		Calendar c = Calendar.getInstance();  
		try {
			c.setTime(format.parse(format.format(time)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		int  dayForWeek = 0 ;  
		if (c.get(Calendar.DAY_OF_WEEK) == 1 ){  
		dayForWeek = 7 ;  
		}else {  
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1 ;  
		}  
		//System.out.println("星期几"+dayForWeek);   
		return dayForWeek;
	}
	
}
