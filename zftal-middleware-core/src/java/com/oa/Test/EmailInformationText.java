package com.oa.Test;



import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.activation.DataHandler;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.common.WebServiceConf;
import com.oa.service.DownLoadBean;
import com.oa.service.FileModel;
import com.oa.service.IOaMobileService;
import com.util.JwMD5Util;
import com.util.SelectItems;
import com.util.WebServiceUtil;
import com.util.encode.XmltoString;

public class EmailInformationText{
//	@Autowired
//private IEmailInformationXMLService OaMobileService;
	
	
//	public void testNews() throws Exception{
	
//	public String getXmlList(){
//
//		return OaMobileService.getMailListByType("843","1", 1, 10,"eNDW0Lp3RfYX2qpI1+pfeg==");
//		return OaMobileService.getMailInfoById("843", "135113913065326747","eNDW0Lp3RfYX2qpI1+pfeg==");
		

	//		return OaMobileService.deleteMailByID("843","135113913065326747","1","eNDW0Lp3RfYX2qpI1+pfeg==");
//		
//		return OaMobileService.updateMailByID("843","135113913065326747" ,"eNDW0Lp3RfYX2qpI1+pfeg==");
//		
//		return OaMobileService.sendMail("","561","徐星华","徐星华","2-561","","","呵呵规范","","1","g5/p6kt4Osh1ztbcoW4+3Q==");
//
//		return OaMobileService.getFirstDepInfo("843" ,"eNDW0Lp3RfYX2qpI1+pfeg==");
//		
//		return OaMobileService.getDepAndUserByDepNum("843",@WebParam(name = "depnum") String depnum ,@WebParam(name = "depname") String depname ,@WebParam(name = "sum") String sum ,@WebParam(name = "sign") String sign);
//		
//		return OaMobileService.getDepAndUserInfoForSearch("843","肖飞","eNDW0Lp3RfYX2qpI1+pfeg==");
//	
//		return OaMobileService.getNewMailCount("843" ,"eNDW0Lp3RfYX2qpI1+pfeg==");
//		
//		return OaMobileService.saveToDraft("","858",
//		        "","",
//		        "","",
//		       "","",
//		        "","tBsMaBdrdxrrAwdAo29mlg==");
////		
//		return OaMobileService.getDraftInfoById(@WebParam(name = "yhm") String name ,@WebParam(name = "yjid") String yjid,@WebParam(name = "sign") String sign);
//		return OaMobileService.getAddressInfo(@WebParam(name = "yhm")
//				java.lang.String yhm, @WebParam(name = "yhid")
//				java.lang.String yhid, @WebParam(name = "sign")
//				java.lang.String sign);
//
//		return OaMobileService.getTodoTaskList(@WebParam(name = "yhm")
//				java.lang.String yhm, @WebParam(name = "start")
//				int start, @WebParam(name = "size")
//				int size, @WebParam(name = "sign")
//				java.lang.String sign);
//
//		return OaMobileService.addSchedule(@WebParam(name = "yhm")
//				java.lang.String yhm, @WebParam(name = "zt")
//				java.lang.String zt, @WebParam(name = "date")
//				java.lang.String date, @WebParam(name = "starttime")
//				java.lang.String starttime, @WebParam(name = "endtime")
//				java.lang.String endtime, @WebParam(name = "content")
//				java.lang.String content, @WebParam(name = "sign")
//				java.lang.String sign);
//
//		return OaMobileService.getSharePerson(@WebParam(name = "yhm")
//				java.lang.String yhm, @WebParam(name = "sign")
//				java.lang.String sign);
//
//				public java.lang.String getNoticeList(@WebParam(name = "yhm")
//				java.lang.String yhm, @WebParam(name = "start")
//				int start, @WebParam(name = "size")
//				int size, @WebParam(name = "sign")
//				java.lang.String sign);
//
//		return OaMobileService.getAddressList(@WebParam(name = "yhm")
//				java.lang.String yhm, @WebParam(name = "updatetime")
//				java.lang.String updatetime, @WebParam(name = "sign")
//				java.lang.String sign);
//
//		return OaMobileService.deleteSchedule(@WebParam(name = "yhm")
//				java.lang.String yhm, @WebParam(name = "id")
//				java.lang.String id, @WebParam(name = "sign")
//				java.lang.String sign);
//
//		return OaMobileService.getScheduleList(@WebParam(name = "yhm")
//				java.lang.String yhm, @WebParam(name = "yhid")
//				java.lang.String yhid, @WebParam(name = "start")
//				int start, @WebParam(name = "size")
//				int size, @WebParam(name = "sign")
//				java.lang.String sign);
//
//		return OaMobileService.getScheduleInfo(@WebParam(name = "yhm")
//				java.lang.String yhm, @WebParam(name = "id")
//				java.lang.String id, @WebParam(name = "sign")
//				java.lang.String sign);
//
//		return OaMobileService.getNewConferenceList(@WebParam(name = "yhm")
//				java.lang.String yhm, @WebParam(name = "type")
//				java.lang.String type, @WebParam(name = "start")
//				int start, @WebParam(name = "size")
//				int size, @WebParam(name = "sign")
//				java.lang.String sign);
//
//		return OaMobileService.getConferenceInfo(@WebParam(name = "yhm")
//				java.lang.String yhm, @WebParam(name = "id")
//				java.lang.String id, @WebParam(name = "sign")
//				java.lang.String sign);
//
//		return OaMobileService.getNoticeInfo(@WebParam(name = "yhm")
//				java.lang.String yhm, @WebParam(name = "id")
//				java.lang.String id, @WebParam(name = "sign")
//				java.lang.String sign);
//
//		return OaMobileService.getSignKey(@WebParam(name = "key")
//				java.lang.String key);
//		return OaMobileService.checkLogin("561", "pnQwCDEdbRI/ECV+2fCMrQ==");
//	}
//
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
////	
	public static void main(String[] args) {

		//纯cxf调用方式
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		
//		factory.setAddress(WebServiceConf.SERVICE_OASERVICE);//接口地址
		factory.setAddress("http://10.71.32.106:8080/zfwebservice/oaMobileService");//接口地址
		factory.setServiceClass(IOaMobileService.class);
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//
		IOaMobileService service = (IOaMobileService) factory.create();
	/*	String	str=service.getAddressList("459", "2013-06-20 14:49:49", "DmEYFHZiHVQ=");
		System.out.println("Server said: " +str );*/
//		System.out.println("Server said: " + service.doSubmitFlow("459", "135417550750035674", "1234567890", "Gh9yA11jguylldP8LQFDFQ=="));
//		System.out.println("Server said: " + service.checkLogin("561", "pnQwCDEdbRI/ECV+2fCMrQ=="));
//		System.out.println("Server said: " + service.checkLogin("459", "ONpNU5xv6zc="));
//		System.out.println("Server said: " + service.doSubmitFlow("459","135530381300016909","135530381234355355","部门领导审核","123123123123","Gh9yA11jguylldP8LQFDFQ=="));
//		System.out.println("Server said: " + service.getNoticeInfo("459","135458664184356235","Gh9yA11jguylldP8LQFDFQ=="));
//		FileModel file = service.getFileModel("135270916381293478");
//		System.out.println("Server said: " + file.getName());
//		System.out.println("Server said: " + service.getTodoTaskList("736", 1, 10, "sE02ufznT95qBC9AfZ2drg=="));
//		System.out.println("Server said: " + service.addSchedule("736", "123313", "2012-11-06", "2012-11-06", "2012-11-07", "1111", "sE02ufznT95qBC9AfZ2drg=="));
//		System.out.println("Server said: " + service.sendMail("843", "843", 1, 10, "eNDW0Lp3RfYX2qpI1+pfeg=="));
//		System.out.println("Server said: " + service.sendMail("135208065198210462", "846", "张会超", "张会超",  "2-846", "", "", "测试", "测试", "3", "eNDW0Lp3RfZqBC9AfZ2drg=="));
//	
//		DataHandler dataHandler =file.getDataHandler();
//		 try{    InputStream is = dataHandler.getInputStream();  
//		         FileOutputStream fos = new FileOutputStream("../zfmobile_port/WebRoot/WEB-INF/upload/"+"135270916381293478");  
//		         byte[] bytes = new byte[2048];  
//		         int len = 0;  
//		         while((len = is.read(bytes))!=-1){  
//		             fos.write(bytes, 0, len);  
//		         }  
//		         fos.flush();  
//		         fos.close();  
//		         is.close(); 
//		 }catch(IOException e){
//			 e.printStackTrace();
//		 }
//
		String	str=service.getNoticType("080211421", "yG1R+dnAq7VjsesxN+lU7w==");
		System.out.println("Server said: " +str );
	/*String	str=WebServiceUtil.createServiceOa().getNoticeList("01009",
				Integer.valueOf(1), Integer.valueOf(0), JwMD5Util.md5Ecode2("01009"));
		 System.out.println("Server said: " +str );*/
//		 String zfxml = "";
//			DownLoadBean bean = new DownLoadBean();
//			try {
//				// 调用WebService
//				JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//				factory.setServiceClass(IOaMobileService.class);
//				factory.setAddress("http://10.71.32.132:8888/zfwebservice/oaMobileService");//接口地址
//
//				IOaMobileService service = (IOaMobileService) factory.create();
//				FileModel file = service.getFileModel("135458664195317624");
//				if(file != null){
//					DataHandler dataHandler = file.getDataHandler();
//					InputStream is = dataHandler.getInputStream();
//					FileOutputStream fos = new FileOutputStream("../zfmobile_port/WebRoot/upload/" + "135458664195317624");//附件下周存放地址
//					//存放流
//					byte[] bytes = new byte[2048];
//					int len = 0;
//					while ((len = is.read(bytes)) != -1) {
//						fos.write(bytes, 0, len);
//					}
//					fos.flush();
//					fos.close();
//					is.close();
//					//将信息存入BEAN 组合成XML
//					bean.setAdjunctId("134398812266053160");
//					bean.setDownLoadURL(WebServiceConf.DOWNLOADURL+"135458664195317624");
//					bean.setFileName(file.getName());
//					
//					String[] output = new String[]{"fileName","adjunctId","downLoadURL"};//需要输出的字段
//					List indexlist = new ArrayList();
//					indexlist.add(bean);
//					zfxml =  XmltoString.xmlToStringNew(output, SelectItems.getReflectObjPropertyValue(indexlist, DownLoadBean.class,output), "DOWNLOAD");
//				
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			System.out.println("Server said: " +zfxml);
//	
	}

}
