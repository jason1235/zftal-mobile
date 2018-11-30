package com.zfsoft.mobile.zjtjykt;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.OMNode;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.mobile.servlet.entity.ListEntity;
import com.zfsoft.mobile.servlet.entity.ResultEntity;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.CodeUtil;
import com.zfsoft.util.base.StringUtil;

/**
 * 浙江同济一卡通
 * @author liucb
 */
public class ZjtjYktAction extends HrmAction{
	public static final String URL = "http://10.10.6.232:805/ThirdWebservice.asmx?wsdl";
	public static final String TNS = "http://www.hzsun.com/";
	public static final String SignIn = "http://www.hzsun.com/SignIn";
	public static final String ReportLoss = "http://www.hzsun.com/ReportLoss";
	public static final String GetAccDBMoney = "http://www.hzsun.com/GetAccDBMoney";
	
	
	public static void main(String[] args) {
		oaOnlyLogin("http://10.71.33.67:8080/zftal-mobile/webservice/oa/EmailInformationXMLService?wsdl", "http://service.oa.com/checkLogin", "http://service.oa.com/", "999", "999");
		
	    //Map<String,String> map = signIn(URL,SignIn,TNS);
		//reportLoss(URL, ReportLoss, TNS, "1005", map.get("stanum"), "4", "z11020040812", "040812", map.get("ThirdType"), map.get("Secret1"), map.get("Secret2"));
		//getAccDBMoney(URL, GetAccDBMoney, TNS, "z11020040812", "4", "1",map.get("ThirdType"), map.get("Secret1"), map.get("Secret2"));
	}
	
	/**
	 * 一卡通挂失
	 */
	//测试 http://10.10.0.173:8080/zftal-mobile/zjtjYkt/zjtjYkt_yktReportLoss.html?sIDNo=1WWXql6Phtmr0qxnJq3CLw%3D%3D&apptoken=279918667d511faca2138fc6012ffff1&username=a0btoH%2BFO4AeN7XketNcag%3D%3D&password=FjJHgaNfMZc%3D
	public void yktReportLoss(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String username=null;
		String sIDNo=null;//卡户识别号
		String password = null;//卡户密码
		String apptoken = null;

		try{
			Gson gson = new Gson();
			PrintWriter out = response.getWriter();
			
			username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
			sIDNo = new String(request.getParameter("sIDNo").getBytes("ISO8859-1"), "UTF-8");
	   	    password = new String(request.getParameter("password").getBytes("ISO8859-1"), "UTF-8");
		 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			
			if(!ApptokenUtils.compare(apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			try {
				username        = CodeUtil.decode(username, apptoken);
				sIDNo  			= CodeUtil.decode(sIDNo, apptoken);
				password  	    = CodeUtil.decode(password, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			//签到
			Map<String,String> signInResults = signIn(URL,SignIn,TNS);
			String stanum = (signInResults.get("stanum")==null? "":signInResults.get("stanum"));//一卡通操作站点
			String ThirdType = (signInResults.get("ThirdType")==null? "":signInResults.get("ThirdType"));//第三方类型
			String Secret1 = (signInResults.get("Secret1")==null? "":signInResults.get("Secret1"));//加密因子1
			String Secret2 = (signInResults.get("Secret2")==null? "":signInResults.get("Secret2"));//加密因子2
			
			//根据签到的返回结果挂失
			Map<String, String> reportLossResult = reportLoss(URL, ReportLoss, TNS, "1005", stanum, "4", sIDNo, password, ThirdType, Secret1, Secret2);
			String reportLossResultStatus = (reportLossResult.get("ReportLossResult")==null? "":reportLossResult.get("ReportLossResult"));//挂失返回结果
			ResultEntity<String> result = null;
			
			if("1".equals(reportLossResultStatus)){
				result = new ResultEntity<String>(1,"卡户挂失成功",reportLossResultStatus);
			}else if("10404204".equals(reportLossResultStatus)){
				result = new ResultEntity<String>(1,"卡户挂失失败",reportLossResultStatus);
			}else{
				result = new ResultEntity<String>(1,"操作失败",reportLossResultStatus);
			}
	        out.write(gson.toJson(result));
	        out.flush();
	        out.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * 获取卡户余额
	 */
	//测试 http://10.10.0.173:8080/zftal-mobile/zjtjYkt/zjtjYkt_getAccDBMoney.html?sIDNo=1WWXql6Phtmr0qxnJq3CLw%3D%3D&apptoken=279918667d511faca2138fc6012ffff1&username=a0btoH%2BFO4AeN7XketNcag%3D%3D
	public void getAccDBMoney(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String username=null;
		String sIDNo=null;//卡户识别号
		String apptoken = null;

		try{
			Gson gson = new Gson();
			PrintWriter out = response.getWriter();
			
			username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
			sIDNo = new String(request.getParameter("sIDNo").getBytes("ISO8859-1"), "UTF-8");
		 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			
			if(!ApptokenUtils.compare(apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			try {
				username        = CodeUtil.decode(username, apptoken);
				sIDNo  			= CodeUtil.decode(sIDNo, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			//签到
			Map<String,String> signInResults = signIn(URL,SignIn,TNS);
			String stanum = (signInResults.get("stanum")==null? "":signInResults.get("stanum"));//一卡通操作站点
			String ThirdType = (signInResults.get("ThirdType")==null? "":signInResults.get("ThirdType"));//第三方类型
			String Secret1 = (signInResults.get("Secret1")==null? "":signInResults.get("Secret1"));//加密因子1
			String Secret2 = (signInResults.get("Secret2")==null? "":signInResults.get("Secret2"));//加密因子2
			
			//获取一卡通余额
			Map<String, String> getAccDBMoneyResult = getAccDBMoney(URL, GetAccDBMoney, TNS, sIDNo,"4", "1",ThirdType,Secret1,Secret2);
			String getAccDBMoneyResultMoney = (getAccDBMoneyResult.get("nMoney")==null? "":getAccDBMoneyResult.get("nMoney"));//卡户余额返回结果
			ResultEntity<String> result = new ResultEntity<String>(1, getAccDBMoneyResultMoney,getAccDBMoneyResultMoney);
		
	        out.write(gson.toJson(result));
	        out.flush();
	        out.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * 添加请求头
	 * @param serviceClient
	 * @param nameSpace
	 * @param thirdTypeStr
	 * @param secret1Str
	 * @param secret2Str
	 */
	private static void addHeaders(ServiceClient serviceClient,String nameSpace,String thirdTypeStr,String secret1Str,String secret2Str){  
	    OMFactory omFactory = OMAbstractFactory.getOMFactory();  
	    OMNamespace omNS=omFactory.createOMNamespace(nameSpace,"hzsun");  
	      
	    OMElement head = omFactory.createOMElement("SecurityHeader", omNS);  
	          
	    OMElement ThirdType = omFactory.createOMElement("ThirdType", omNS);  
	    ThirdType.addChild(omFactory.createOMText(ThirdType, thirdTypeStr));  
	    head.addChild(ThirdType);  
	          
	    OMElement Secret1 = omFactory.createOMElement("Secret1", omNS);  
	    Secret1.addChild(omFactory.createOMText(Secret1, secret1Str));  
	    head.addChild(Secret1);  
	    
	    OMElement Secret2 = omFactory.createOMElement("Secret2", omNS);  
	    Secret2.addChild(omFactory.createOMText(Secret2, secret2Str));  
	    head.addChild(Secret2);  
	          
	    System.out.println(head.toString());
	    serviceClient.addHeader(head);  
	}  
	
	/**
	 * 签到
	 * @param signInUrl webservice地址
	 * @param actionUrl namespace+方法名
	 * @param nameSpace 命名空间
	 * @return
	 */
	public static Map<String,String> signIn(String signInUrl,String actionUrl,String nameSpace){
		Map<String,String> map = new HashMap<String,String>();
		
		try{
			Options options = new Options();  
	        // 指定调用WebService的URL    
	        EndpointReference targetEPR = new EndpointReference(signInUrl);  
	        options.setTo(targetEPR);  
	        options.setAction(actionUrl);    

	        ServiceClient sender = new ServiceClient();  
	        sender.setOptions(options); 

	        OMFactory fac = OMAbstractFactory.getOMFactory();  
	        
	        // 命名空间，有时命名空间不增加没事，不过最好加上，因为有时有事，你懂的    
	        OMNamespace omNs = fac.createOMNamespace(nameSpace, "hzsun");  

	        OMElement method = fac.createOMElement("SignIn", omNs);  
	        method.build();  

	        OMElement result = sender.sendReceive(method); 
	        
	        System.out.println(result.toString());
	        
	        map = getResults(result);
	        /*System.out.println(map.get("SignInResult"));
	        System.out.println(map.get("stanum"));
	        System.out.println(map.get("ThirdType"));
	        System.out.println(map.get("Secret1"));
	        System.out.println(map.get("Secret2"));*/
		 } catch (AxisFault axisFault) {  
	            axisFault.printStackTrace();  
	     }
		return map;
	}
	
	/**
	 * 一卡通挂失
	 * @param reportLossUrl webservice地址
	 * @param actionUrl namespace+方法名
	 * @param nameSpace 命名空间
	 * @param operatorNumStr 操作员号
	 * @param stanumStr 站点号
	 * @param nIDTypeStr 识别号类别
	 * @param sIDNoStr  卡号
	 * @param passwordStr 密码
	 * @param thirdTypeStr 第三方类型
	 * @param secret1Str 加密因子1
	 * @param secret2Str 加密因子2
	 * @return
	 */
	public static Map<String,String> reportLoss(String reportLossUrl,String actionUrl,String nameSpace,String operatorNumStr,String stanumStr,String nIDTypeStr,String sIDNoStr,String passwordStr,String thirdTypeStr,String secret1Str,String secret2Str){
		Map<String,String> map = new HashMap<String,String>();
        
		try{
			 Options options = new Options();  
		        // 指定调用WebService的URL    
		        EndpointReference targetEPR = new EndpointReference(reportLossUrl);  
		        options.setTo(targetEPR);  
		        options.setAction(actionUrl);    

		        ServiceClient sender = new ServiceClient();  
		        sender.setOptions(options); 
		       
		        //添加请求头
		        addHeaders(sender,nameSpace,thirdTypeStr,secret1Str,secret2Str);

		        OMFactory fac = OMAbstractFactory.getOMFactory();  
		        // 命名空间，有时命名空间不增加没事，不过最好加上，因为有时有事，你懂的    
		        OMNamespace omNs = fac.createOMNamespace(nameSpace, "hzsun");  

		        OMElement method = fac.createOMElement("ReportLoss", omNs); 
		        
		        OMElement operatorNum = fac.createOMElement("operatorNum", omNs);
		        operatorNum.addChild(fac.createOMText(operatorNum, operatorNumStr)); 
		        method.addChild(operatorNum);
		        
		        OMElement stanum = fac.createOMElement("stanum", omNs);
		        stanum.addChild(fac.createOMText(stanum, stanumStr)); 
		        method.addChild(stanum);
		        
		        OMElement sIDNo = fac.createOMElement("sIDNo", omNs);
		        sIDNo.addChild(fac.createOMText(sIDNo, sIDNoStr)); 
		        method.addChild(sIDNo);
		        
		        OMElement nIDType = fac.createOMElement("nIDType", omNs);
		        nIDType.addChild(fac.createOMText(nIDType, nIDTypeStr)); 
		        method.addChild(nIDType);
		        
		        OMElement password = fac.createOMElement("password", omNs);
		        password.addChild(fac.createOMText(password,passwordStr)); 
		        method.addChild(password);
		        
		        method.build();  

		        OMElement result = sender.sendReceive(method);  

		        System.out.println(result);
		        
		        map = getResults(result);
		        //System.out.println(map.get("ReportLossResult"));
		} catch (AxisFault axisFault) {  
            axisFault.printStackTrace();  
        }
        
		return map;
	}
	
	
	/**
	 * 获取卡户余额
	 * @param GetAccDBMoney webservice地址
	 * @param actionUrl namespace+方法名
	 * @param nameSpace 命名空间
	 * @param sIDNoStr 卡号
	 * @param nEWalletNum 钱包号  传1
	 * @param nIDTypeStr 识别号类别
	 * @param thirdTypeStr 第三方类型
	 * @param secret1Str 加密因子1
	 * @param secret2Str 加密因子2
	 * @return
	 */
	public static Map<String,String> getAccDBMoney(String getAccDBMoneyUrl,String actionUrl,String nameSpace,String sIDNoStr,String nIDTypeStr,String nEWalletNumStr,String thirdTypeStr,String secret1Str,String secret2Str){
		Map<String,String> map = new HashMap<String,String>();
        
		try{
			 Options options = new Options();  
		        // 指定调用WebService的URL    
		        EndpointReference targetEPR = new EndpointReference(getAccDBMoneyUrl);  
		        options.setTo(targetEPR);  
		        options.setAction(actionUrl);    

		        ServiceClient sender = new ServiceClient();  
		        sender.setOptions(options); 

		        //添加请求头
		        addHeaders(sender,nameSpace,thirdTypeStr,secret1Str,secret2Str);
		        
		        OMFactory fac = OMAbstractFactory.getOMFactory();  
		        // 命名空间，有时命名空间不增加没事，不过最好加上，因为有时有事，你懂的    
		        OMNamespace omNs = fac.createOMNamespace(nameSpace, "hzsun");  

		        OMElement method = fac.createOMElement("GetAccDBMoney", omNs); 
		        
		        OMElement sIDNo = fac.createOMElement("sIDNo", omNs);
		        sIDNo.addChild(fac.createOMText(sIDNo, sIDNoStr)); 
		        method.addChild(sIDNo);
		        
		        OMElement nIDType = fac.createOMElement("nIDType", omNs);
		        nIDType.addChild(fac.createOMText(nIDType, nIDTypeStr)); 
		        method.addChild(nIDType);
		        
		        OMElement nEWalletNum = fac.createOMElement("nEWalletNum", omNs);
		        nEWalletNum.addChild(fac.createOMText(nEWalletNum, nEWalletNumStr)); 
		        method.addChild(nEWalletNum);
		        
		        method.build();  

		        OMElement result = sender.sendReceive(method);  

		        System.out.println(result);
		        
		        map = getResults(result);
		        //System.out.println(map.get("ReportLossResult"));
		} catch (AxisFault axisFault) {  
            axisFault.printStackTrace();  
        }
        
		return map;
	}
	
	
	/**
	 * 解析返回结果
	 * @param element
	 * @return
	 */
	public static Map<String,String> getResults(OMElement element) {
	    if (element == null) {
	       return null;
	    }
	    Iterator iter = element.getChildElements();
	    Map<String,String> map = new HashMap<String,String>();
	    while (iter.hasNext()) {
	        OMNode omNode = (OMNode) iter.next();
	        if (omNode.getType() == OMNode.ELEMENT_NODE) {
	            OMElement omElement = (OMElement) omNode;
	            String key = omElement.getLocalName().trim();
	            String value = omElement.getText().trim();
	            map.put(key, value);
	       }
	   }
	   return map;
	 }
	
	
	
	/**
	 * oa登录
	 */
	public static Map<String,String> oaOnlyLogin(String oaLoginUrl,String actionUrl,String nameSpace,String yhmStr,String signStr){
		Map<String,String> map = new HashMap<String,String>();
        
		try{
			    Options options = new Options();  
		        // 指定调用WebService的URL    
		        EndpointReference targetEPR = new EndpointReference(oaLoginUrl);  
		        options.setTo(targetEPR);  
		        options.setAction(actionUrl);    

		        ServiceClient sender = new ServiceClient();  
		        sender.setOptions(options); 

		        //添加请求头
		        //addHeaders(sender,nameSpace,yhmStr,signStr,apptokenStr);
		        
		        OMFactory fac = OMAbstractFactory.getOMFactory();  
		        // 命名空间，有时命名空间不增加没事，不过最好加上，因为有时有事，你懂的    
		        OMNamespace omNs = fac.createOMNamespace(nameSpace, "oa");  

		        OMElement method = fac.createOMElement("checkLogin", omNs); 
		        
		        OMElement yhm = fac.createOMElement("yhm", omNs);
		        yhm.addChild(fac.createOMText(yhm, yhmStr)); 
		        method.addChild(yhm);
		        
		        OMElement sign = fac.createOMElement("sign", omNs);
		        sign.addChild(fac.createOMText(sign, signStr)); 
		        method.addChild(sign);
		        
		        method.build();  

		        OMElement result = sender.sendReceive(method);  

		        System.out.println(result);
		        
		        //map = getResults(result);
		        //System.out.println(map.get("ReportLossResult"));
		} catch (AxisFault axisFault) {  
            axisFault.printStackTrace();  
        }
        
		return map;
	}
	
}
