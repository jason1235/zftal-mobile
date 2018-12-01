package com.zfsoft.wechat.common.utils;
import java.io.ByteArrayOutputStream; 
import java.io.DataInputStream; 
import java.io.File; 
import java.io.FileInputStream; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.util.ArrayList; 
import java.util.List; 
import java.net.HttpURLConnection; 
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;


public class UploadFileUtil {

     //每个post参数之间的分隔 
    static final String BOUNDARY = "----CODEFormBoundarySMFEtUYQG6r5B920";  // 定义数据分隔线  
    private String urlStr;  // 连接的地址
    private List<String[]> strParams; // 文字post项集 strParams 1:key 2:value  
    private List<String[]> fileParams; // 文件的post项集 fileParams 1:fileField, 2.fileName, 3.fileType, 4.filePath  
 
  
     
    public UploadFileUtil(String urlStr, List<String[]> strParams, List<String[]> fileParams) { 
        this.urlStr = urlStr; 
        this.strParams = strParams; 
        this.fileParams = fileParams; 
 
    } 
 
     
    public String sendPost() throws Exception {    
    	HttpsURLConnection hc = null;  //http连接器
        ByteArrayOutputStream bos = null;//byte输出流，用来读取服务器返回的信息   
        InputStream is = null;//输入流，用来读取服务器返回的信息  
        byte[] res = null;//保存服务器返回的信息的byte数组
        try { 
        	
        	// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new CertificateTrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(urlStr); 
            hc = (HttpsURLConnection)url.openConnection(); 
            hc.setSSLSocketFactory(ssf);
            hc.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY); 
            hc.setRequestProperty("Charsert", "UTF-8"); 
            // 发送POST请求必须设置如下两行 
            hc.setDoOutput(true); 
            hc.setDoInput(true); 
            hc.setUseCaches(false); 
            hc.setRequestMethod("POST"); 
 
            OutputStream dout = hc.getOutputStream(); 
            ////1.先写文字形式的post流 
            //头 
            String boundary = BOUNDARY; 
            //中 
            StringBuffer resSB = new StringBuffer("\r\n"); 
            //尾 
            String endBoundary = "\r\n--" + boundary + "--\r\n"; 
            //strParams 1:key 2:value 
            if(strParams != null){ 
                for(String[] parsm : strParams){ 
                    String key = parsm[0]; 
                    String value = parsm[1]; 
                    resSB.append("Content-Disposition: form-data; name=\"").append(key).append("\"\r\n").append("\r\n").append(value).append("\r\n").append("--").append(boundary).append("\r\n"); 
                } 
            } 
            String boundaryMessage = resSB.toString(); 
             
            //写出流 
            dout.write( ("--"+boundary+boundaryMessage).getBytes("utf-8") ); 
             
            //2.再写文件开式的post流 
            //fileParams 1:fileField, 2.fileName, 3.fileType, 4.filePath 
            resSB = new StringBuffer(); 
            if(fileParams != null){ 
                for(int i=0, num=fileParams.size(); i<num; i++){ 
                    String[] parsm = fileParams.get(i); 
                    String fileField = parsm[0]; 
                    String fileName = parsm[1]; 
                    String fileType = parsm[2]; 
                    String filePath = parsm[3]; 
                     
                    resSB.append("Content-Disposition: form-data; name=\"").append(fileField).append("\"; filename=\"").append(fileName).append("\"\r\n").append("Content-Type: ").append(fileType).append("\r\n\r\n"); 
                     
                    dout.write( resSB.toString().getBytes("utf-8") ); 
                     
                    //开始写文件 
                    File file = new File(filePath); 
                    DataInputStream in = new DataInputStream(new FileInputStream(file)); 
                    int bytes = 0; 
                    byte[] bufferOut = new byte[1024 * 5]; 
                    while ((bytes = in.read(bufferOut)) != -1) { 
                        dout.write(bufferOut, 0, bytes); 
                    } 
                     
                    if(i<num-1){ 
                        dout.write( endBoundary.getBytes("utf-8") ); 
                    } 
                     
                    in.close(); 
                } 
                 
            } 
             
            //3.最后写结尾 
            dout.write( endBoundary.getBytes("utf-8") );    
            dout.close();  

            int ch;  
            is = hc.getInputStream();    
            bos = new ByteArrayOutputStream(); 
            while ((ch = is.read()) != -1) { 
                bos.write(ch); 
            } 
            return bos.toString(); 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } finally { 
            try { 
                if (bos != null) 
                    bos.close();  
                if (is != null) 
                    is.close();  
            } catch (Exception e2) { 
                e2.printStackTrace(); 
            } 
        } 
        return null; 
    }  
  
//	public static void main(String[] args) throws Exception {
//		List<String[]> strParams =new ArrayList<String[]>();
//		strParams.add(new String[]{"classId","C393FE11C4DC8E46E040007F01003F39"});
//		List<String[]> fileParams = new ArrayList<String[]>();
//		fileParams.add(new String[]{"file","111.png","file","D://111.png"});
//		
//		System.out.println("---------------"+new UploadFileUtil("http://10.71.32.115:8080/zftal-hrm/baseinfo/infoClassData_dataImport.html", strParams, fileParams).sendPost());
//	}

}