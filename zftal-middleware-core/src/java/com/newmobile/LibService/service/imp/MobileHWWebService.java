package com.newmobile.LibService.service.imp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebParam;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.common.Config;
import com.newmobile.LibService.CXFService.Arrival;
import com.newmobile.LibService.CXFService.Book;
import com.newmobile.LibService.CXFService.Circ;
import com.newmobile.LibService.CXFService.Debt;
import com.newmobile.LibService.CXFService.LibServiceDelegate;
import com.newmobile.LibService.CXFService.NoAuthorityException_Exception;
import com.newmobile.LibService.CXFService.Reader;
import com.newmobile.LibService.CXFService.SystemException_Exception;
import com.newmobile.LibService.service.IMobileHWWebService;
import com.util.encode.MD5Util;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.CodeUtil;

@Service
@Component(value = "mobileHWWebService")
public class MobileHWWebService implements IMobileHWWebService {
	private static Logger logger = Logger.getLogger(MobileHWWebService.class);
	private final String infromation=Config.getString("mobile.infromation");
	
	
	
	@Override
	public String getreader(String userName, String apptoken) {
		
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  		= CodeUtil.decode(userName, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String str=null;
		if(infromation.equals("0")){
			logger.error("读者信息查询:"+"userName="+userName);
			}
		// TODO Auto-generated method stub
		JaxWsProxyFactoryBean  factoryBean=new JaxWsProxyFactoryBean();
        factoryBean.getInInterceptors().add(new LoggingInInterceptor());
        factoryBean.getOutInterceptors().add(new LoggingOutInterceptor());
        factoryBean.setServiceClass(LibServiceDelegate.class);
        String libraryURL = Config.getString("libraryURL", "http://202.117.255.187:8081/HWWebService/LibServicePort?wsdl");
        factoryBean.setAddress(libraryURL);
        LibServiceDelegate impl=(LibServiceDelegate) factoryBean.create();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
		String currentTime = df.format(new Date());
		String verri = null;
		String key = Config.getString("libraryKEY", "huiwen");
		try {
			verri = MD5Util.md5Encode(userName+key+currentTime);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(infromation.equals("0")){
			logger.error("currentTime---"+currentTime+",verri---"+verri);
		}
        //try {
        	//com.newmobile.LibService.CXFService.Reader reader = impl.getReader(userName, currentTime, verri, 1, userName);
        	com.newmobile.LibService.CXFService.Reader reader = new Reader();
        	reader.setName("黎**");
        	reader.setRegisterDay("2016-08-03");
        	reader.setReaderCertId("测试");
        	reader.setCertId("1131321020");
        	reader.setReaderCertId("51310.0.0");
        	reader.setDept("学工办");
        	reader.setGrade("一年级");
        	reader.setReaderType("数学系");
        	reader.setMaxLendQuantity(52);
        	reader.setLendQuantity(41);
        	reader.setDebt(15.0f);
        	reader.setVoltFlagName("未违章");
        	reader.setLimitFlagName("未限制");
        	reader.setCertFlagName("挂失");
        	JSONObject jsonObject = JSONObject.fromObject(reader);
        	JSONObject object = new JSONObject();
        	JSONObject objectkey = new JSONObject();
        	objectkey.put("name", "读者姓名");
        	objectkey.put("registerDay", "注册日期");
        	objectkey.put("readerType", "读者类型名称");
        	objectkey.put("certId", "读者证件号");
        	objectkey.put("readerCertId", "读者条码号");
        	objectkey.put("dept", "单位");
        	objectkey.put("grade", "年级");
        	objectkey.put("readerDept", "系别名称");
        	objectkey.put("maxLendQuantity", "最大借阅册数");
        	objectkey.put("lendQuantity", "读者累计借阅册数");
        	objectkey.put("debt", "欠款金额");
        	objectkey.put("voltFlagName", "违章状态名称");
        	objectkey.put("limitFlagName", "限制状态名称");
        	objectkey.put("certFlagName", "证件状态名称");
        	object.put("value", jsonObject);
        	object.put("key", objectkey);
        	
        	str= object.toString();
        	//aaa = "reader---:"+ reader.getBeginDate()+reader.getCertId();
        	if(infromation.equals("0")){
        		logger.error("读者信息查询结果:"+str);
    		}
		/*} catch (NoAuthorityException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return str;
	}

	@Override
	public String getBook(String userName, String apptoken, String type,
			String value) {
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  	= CodeUtil.decode(userName, apptoken);
			type  		= CodeUtil.decode(type, apptoken);
			value  		= CodeUtil.decode(value, apptoken);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String str=null;
		if(infromation.equals("0")){
			logger.error("馆藏图书查询获取："+"userName="+userName+"type="+type+"value="+value+"apptoken="+apptoken);
			}
		// TODO Auto-generated method stub
		JaxWsProxyFactoryBean  factoryBean=new JaxWsProxyFactoryBean();
        factoryBean.getInInterceptors().add(new LoggingInInterceptor());
        factoryBean.getOutInterceptors().add(new LoggingOutInterceptor());
        factoryBean.setServiceClass(LibServiceDelegate.class);
        String libraryURL = Config.getString("libraryURL", "http://202.117.255.187:8081/HWWebService/LibServicePort?wsdl");
        factoryBean.setAddress(libraryURL);
        LibServiceDelegate impl=(LibServiceDelegate) factoryBean.create();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
		String currentTime = df.format(new Date());
		String verri = null;
		String key = Config.getString("libraryKEY", "huiwen");
		try {
			verri = MD5Util.md5Encode(userName+key+currentTime);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(infromation.equals("0")){
			logger.error("currentTime---"+currentTime+",verri---"+verri);
		}
       /* try {
        	com.newmobile.LibService.CXFService.Book book = impl.getBook(userName, currentTime, verri, Integer.parseInt(type), value);*/
        	com.newmobile.LibService.CXFService.Book book = new Book();
        	book.setAuthor("阳章");
        	book.setTitle("java开发");
        	book.setPublisher("人大出版社");
        	book.setLocationName("图书馆");
        	book.setStateName("已借出");
        	book.setPrice(6.0f);
        	book.setBarcode("210213");
        	book.setPropNo("213213");
        	book.setCallNo("21231");
        	
        	JSONObject jsonObject = JSONObject.fromObject(book);
        	JSONObject object = new JSONObject();
        	JSONObject objectkey = new JSONObject();
        	objectkey.put("title", "书名");
        	objectkey.put("author", "责任者");
        	objectkey.put("publisher", "出版社");
        	objectkey.put("locationName", "藏书地名称");
        	objectkey.put("stateName", "书刊状态名称");
        	objectkey.put("price", "定价");
        	objectkey.put("barcode", "条码号");
        	objectkey.put("propNo", "财产号");
        	objectkey.put("callNo", "索书号");
        	object.put("value", jsonObject);
        	object.put("key", objectkey);
        	
        	str= object.toString();
        	//aaa = "reader---:"+ reader.getBeginDate()+reader.getCertId();
        	if(infromation.equals("0")){
        		logger.error("馆藏图书查询获取返回为："+str);
    		}
		/*} catch (NoAuthorityException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return str;
	}

	@Override
	public String getCircs(String userName,String certId, String apptoken) {
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  	= CodeUtil.decode(userName, apptoken);
			certId  	= CodeUtil.decode(certId, apptoken);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String str=null;
		if(infromation.equals("0")){
			logger.error("读者借阅信息查询获取："+"userName="+userName+"certId="+certId+"apptoken="+apptoken);
			}
		/*JaxWsProxyFactoryBean  factoryBean=new JaxWsProxyFactoryBean();
        factoryBean.getInInterceptors().add(new LoggingInInterceptor());
        factoryBean.getOutInterceptors().add(new LoggingOutInterceptor());
        factoryBean.setServiceClass(LibServiceDelegate.class);
        String libraryURL = Config.getString("libraryURL", "http://202.117.255.187:8081/HWWebService/LibServicePort?wsdl");
        factoryBean.setAddress(libraryURL);
        LibServiceDelegate impl=(LibServiceDelegate) factoryBean.create();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
		String currentTime = df.format(new Date());
		String verri = null;
		String key = Config.getString("libraryKEY", "huiwen");
		List<Circ> Circs = new ArrayList<Circ>();
		try {
			verri = MD5Util.md5Encode(userName+key+currentTime);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(infromation.equals("0")){
			logger.error("currentTime---"+currentTime+",verri---"+verri);
		}
        try {
        	Circs = impl.getCircs(userName, currentTime, verri, certId);
        	//str= JSONArray.fromObject(Circs).toString();
        	//aaa = "reader---:"+ reader.getBeginDate()+reader.getCertId();
        	if(infromation.equals("0")){
        		logger.error("读者借阅信息查询获取返回为："+str);
    		}
		} catch (NoAuthorityException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		ArrayList<Circ> Circs = new ArrayList<Circ>();
		Circ one = new Circ();
		one.setCertId("2001020049");
		one.setName("黄晓波");
		//one.setBarcode("0351561515");
		one.setPropNo("0023521");
		one.setPrice(11.5F);
		one.setTitle("android系统编程(华思远版本)");
		one.setLendDate("2016-06-20");
		one.setDueDay("2016-07-23");
		one.setRenewTimes(2);
		one.setAuthor("华思远");
		Circs.add(one);
		Circ two = new Circ();
		two.setCertId("2001020049");
		two.setName("黄晓波");
		two.setBarcode("http://10.71.32.117:8088//zftal-mobile/assets/i/portal/zzjf.png");
		two.setPropNo("05656");
		two.setPrice(13.8F);
		two.setTitle("ios系统编程(阳章版本)");
		two.setLendDate("2016-06-13");
		two.setDueDay("2016-06-16");
		two.setRenewTimes(1);
		two.setAuthor("阳章");
		Circs.add(two);
		JSONArray jsonObject = JSONArray.fromObject(Circs);
    	JSONObject object = new JSONObject();
    	JSONObject objectkey = new JSONObject();
    	objectkey.put("title", "title");
    	objectkey.put("author", "author");
    	objectkey.put("lendDate", "lendDate");
    	objectkey.put("dueDay", "dueDay");
    	objectkey.put("imageURL", "imageURL");//暂无
    	objectkey.put("barcode", "barcode");
    	objectkey.put("propNo", "propNo");
    	object.put("value", jsonObject);
    	object.put("key", objectkey);
    	str= object.toString();
		if(infromation.equals("0")){
    		logger.error("读者借阅信息查询获取返回为："+str);
		}
		return str;
	}

	@Override
	public String getDebts(String userName, String certId, String apptoken) {
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  	= CodeUtil.decode(userName, apptoken);
			certId  	= CodeUtil.decode(certId, apptoken);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String str=null;
		if(infromation.equals("0")){
			logger.error("读者超期欠款信息查询获取："+"userName="+userName+"certId="+certId+"apptoken="+apptoken);
			}
		// TODO Auto-generated method stub
		/*JaxWsProxyFactoryBean  factoryBean=new JaxWsProxyFactoryBean();
        factoryBean.getInInterceptors().add(new LoggingInInterceptor());
        factoryBean.getOutInterceptors().add(new LoggingOutInterceptor());
        factoryBean.setServiceClass(LibServiceDelegate.class);
        String libraryURL = Config.getString("libraryURL", "http://202.117.255.187:8081/HWWebService/LibServicePort?wsdl");
        factoryBean.setAddress(libraryURL);
        LibServiceDelegate impl=(LibServiceDelegate) factoryBean.create();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
		String currentTime = df.format(new Date());
		String verri = null;
		String key = Config.getString("libraryKEY", "huiwen");
		try {
			verri = MD5Util.md5Encode(userName+key+currentTime);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(infromation.equals("0")){
			logger.error("currentTime---"+currentTime+",verri---"+verri);
		}
		List<Debt> Debts = new ArrayList<Debt>();
        try {
        	Debts = impl.getDebts(userName, currentTime, verri, certId);
        	str= JSONArray.fromObject(Debts).toString();
        	//aaa = "reader---:"+ reader.getBeginDate()+reader.getCertId();
        	if(infromation.equals("0")){
        		logger.error("读者超期欠款信息查询获取返回为："+str);
    		}
		} catch (NoAuthorityException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		List<Debt> Debts = new ArrayList<Debt>();
		Debt one = new Debt();
		one.setCertId("2001020049");
		one.setDebtDealFlagName("未处理");
		one.setDueFineAmount(5.6F);
		one.setFineAmount(3.8f);
		one.setLendDate("2016-06-21");
		one.setLocationF("阳章家");
		one.setPropNo("12132");
		one.setRetDate("2016-06-22");
		one.setName("黄晓波");
		
		Debt two = new Debt();
		two.setCertId("2001020049");
		two.setDebtDealFlagName("已处理");
		two.setDueFineAmount(9.6F);
		two.setFineAmount(4.8f);
		two.setLendDate("2016-06-21");
		two.setLocationF("华思远家");
		two.setPropNo("46546");
		two.setRetDate("2016-06-22");
		two.setName("黄晓波");
		Debts.add(one);
		Debts.add(two);
		
		JSONArray jsonObject = JSONArray.fromObject(Debts);
    	JSONObject object = new JSONObject();
    	JSONObject objectkey = new JSONObject();
    	objectkey.put("certId", "读者证件号");
    	objectkey.put("name", "读者姓名");
    	objectkey.put("lendDate", "借书日期");
    	objectkey.put("retDate", "还书日期");
    	objectkey.put("dueFineAmount", "应罚款额");
    	objectkey.put("fineAmount", "实罚款额");
    	objectkey.put("debtDealFlagName", "处理状态名称");
    	objectkey.put("locationF", "图书馆藏地");
    	objectkey.put("propNo", "图书财产号");
    	object.put("value", jsonObject);
    	object.put("key", objectkey);
    	str= object.toString();
		return str;
	}

	@Override
	public String getPregArrivals(String userName, String certId,
			String apptoken) {
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  	= CodeUtil.decode(userName, apptoken);
			certId  	= CodeUtil.decode(certId, apptoken);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String str=null;
		if(infromation.equals("0")){
			logger.error("读者预约到书信息查询获取："+"userName="+userName+"certId="+certId+"apptoken="+apptoken);
			}
		// TODO Auto-generated method stub
		/*JaxWsProxyFactoryBean  factoryBean=new JaxWsProxyFactoryBean();
        factoryBean.getInInterceptors().add(new LoggingInInterceptor());
        factoryBean.getOutInterceptors().add(new LoggingOutInterceptor());
        factoryBean.setServiceClass(LibServiceDelegate.class);
        String libraryURL = Config.getString("libraryURL", "http://202.117.255.187:8081/HWWebService/LibServicePort?wsdl");
        factoryBean.setAddress(libraryURL);
        LibServiceDelegate impl=(LibServiceDelegate) factoryBean.create();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
		String currentTime = df.format(new Date());
		String verri = null;
		String key = Config.getString("libraryKEY", "huiwen");
		try {
			verri = MD5Util.md5Encode(userName+key+currentTime);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(infromation.equals("0")){
			logger.error("currentTime---"+currentTime+",verri---"+verri);
		}
		List<Arrival> Arrivals = new ArrayList<Arrival>();
        try {
        	Arrivals = impl.getPregArrivals(userName, currentTime, verri, certId);
        	str= JSONArray.fromObject(Arrivals).toString();
        	//aaa = "reader---:"+ reader.getBeginDate()+reader.getCertId();
        	if(infromation.equals("0")){
        		logger.error("读者预约到书信息查询获取返回为："+str) ;
    		}
		} catch (NoAuthorityException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		List<Arrival> Arrivals = new ArrayList<Arrival>();
		Arrival one = new Arrival();
		one.setBarcode("1651616");
		one.setTitle("原子弹开发与研究(华思远版)");
		one.setArrivalDate("2016-06-24");
		one.setKeepEndDate("2016-07-10");
		Arrival two = new Arrival();
		two.setBarcode("1651616");
		two.setTitle("相对论开发与研究(华思远版)");
		two.setArrivalDate("2016-06-24");
		two.setKeepEndDate("2016-07-10");
		Arrivals.add(one);
		Arrivals.add(two);
		JSONArray jsonObject = JSONArray.fromObject(Arrivals);
    	JSONObject object = new JSONObject();
    	JSONObject objectkey = new JSONObject();
    	objectkey.put("title", "title");
    	objectkey.put("barcode", "barcode");
    	objectkey.put("arrivalDate", "arrivalDate");
    	objectkey.put("keepEndDate", "keepEndDate");
    	objectkey.put("imageURL", "imageURL");//暂无
    	object.put("value", jsonObject);
    	object.put("key", objectkey);
    	str= object.toString();
		return str;
	}

	public static void main(String[] args) {
		List<Debt> Debts = new ArrayList<Debt>();
		Debt one = new Debt();
		one.setCertId("2001020049");
		one.setDebtDealFlagName("未处理");
		one.setDueFineAmount(5.6F);
		one.setFineAmount(3.8f);
		one.setLendDate("2016-06-21");
		one.setLocationF("阳章家");
		one.setPropNo("12132");
		one.setRetDate("2016-06-22");
		one.setName("黄晓波");
		
		Debt two = new Debt();
		two.setCertId("2001020049");
		two.setDebtDealFlagName("已处理");
		two.setDueFineAmount(9.6F);
		two.setFineAmount(4.8f);
		two.setLendDate("2016-06-21");
		two.setLocationF("华思远家");
		two.setPropNo("46546");
		two.setRetDate("2016-06-22");
		two.setName("黄晓波");
		Debts.add(one);
		Debts.add(two);
		
		JSONArray jsonObject = JSONArray.fromObject(Debts);
		System.out.println(jsonObject.toString());
	}
}
