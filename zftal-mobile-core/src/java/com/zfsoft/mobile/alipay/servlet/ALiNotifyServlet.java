package com.zfsoft.mobile.alipay.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.zfsoft.mobile.alipay.entity.AlipaySignEntity;
import com.zfsoft.mobile.alipay.entity.BizContent;
import com.zfsoft.mobile.alipay.util.AlipayConfig;
import com.zfsoft.mobile.alipay.util.AlipayNotify;
import com.zfsoft.mobile.alipay.util.OrderInfoUtil2_0;
import com.zfsoft.mobile.servlet.entity.MyPortalEntity;
import com.zfsoft.mobile.servlet.entity.ResultEntity;
import com.zfsoft.untils.CodeUtil;
import com.zfsoft.util.base.StringUtil;



public class ALiNotifyServlet extends HttpServlet {
	/** 商户私钥，pkcs8格式 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	
	public static String getBodyString(BufferedReader br) {
		  String inputLine;
		       String str = "";
		     try {
		       while ((inputLine = br.readLine()) != null) {
		        str += inputLine;
		       }
		       br.close();
		     } catch (IOException e) {
		       System.out.println("IOException: " + e);
		     }
		     return str;
		 }

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	   String uri = request.getRequestURI();
	   
	   //request.setCharacterEncoding("UTF-8");
	   boolean flag = uri.contains("?") ? true : false;
	   String path = "";
	   if(flag){
		   path = uri.substring(uri.lastIndexOf("/"),uri.lastIndexOf("?"));
	   }else{
		   path = uri.substring(uri.lastIndexOf("/"),uri.length());
	   }
	   
	   if(path.equals("/getSign")){
		   Map<String,String>  map = new HashMap<String, String>();
		   String apptoken 	  = request.getParameter("apptoken");
		   String biz_content_android = request.getParameter("biz_content_android");
		   String data = new String(request.getParameter("data").getBytes("ISO8859-1"), "UTF-8");
		   //String data        = java.net.URLDecoder.decode(request.getParameter("data"), "UTF-8");
		   Gson gson = new Gson();
		   AlipaySignEntity signEntity = null;
		   if(StringUtil.isEmpty(data)){
			   ResultEntity result = new ResultEntity<AlipaySignEntity>(0, "参数传值出错！", signEntity);
			   PrintWriter out;
			   response.setHeader("Content-type", "text/html;charset=UTF-8");
			   out = response.getWriter();  
			   out.print(gson.toJson(result));  
			   out.close();
		   }
		   try {
			   data  = CodeUtil.decode(data, apptoken);
			   if(!StringUtil.isEmpty(biz_content_android)){
				   biz_content_android  = CodeUtil.decode(biz_content_android, apptoken);
			   }
			} catch (Exception e) {
			   log("获取sign参数解析异常:");
			   log(e.getMessage(), e.fillInStackTrace());
			   ResultEntity result = new ResultEntity<AlipaySignEntity>(0, "参数解析异常！", signEntity);
			   PrintWriter out;
			   out = response.getWriter();  
			   out.print(gson.toJson(result));  
			   out.close();
			}
		   log("------data-----start:");
		   log(data);
		   log("------data-----end");
		   AlipaySignEntity dataEntity = gson.fromJson(data, AlipaySignEntity.class);
		   String app_id 	  = dataEntity.getApp_id();
		   map.put("app_id", app_id);
		   System.out.println("app_id:"+app_id);
		   BizContent biz_content = dataEntity.getBiz_content();
		   response.setHeader("Content-type", "text/html;charset=UTF-8");
		   PrintWriter out;
		   out = response.getWriter();  
		   if(biz_content == null){
			   ResultEntity result = new ResultEntity<AlipaySignEntity>(0, "参数传值出错！", signEntity);
			   out.print(gson.toJson(result));  
			   out.close();
		   }
		   //biz_content = new String(biz_content.getBytes("iso8859-1"),"UTF-8");
		   System.out.println("biz_content:"+biz_content);
		   //String biz_content = URLDecoder.decode(request.getParameter("biz_content"),"UTF-8");
		   map.put(
				   "biz_content", 
				   StringUtil.isEmpty(biz_content_android) ? biz_content.toString() : biz_content_android
				   );
		   String charset = dataEntity.getCharset();
		   System.out.println("charset:"+charset);
		   map.put("charset", charset);
		   String method = dataEntity.getMethod();
		   System.out.println("method:"+method);
		   map.put("method", method);
		   String sign_type = dataEntity.getSign_type();
		   System.out.println("sign_type:"+sign_type);
		   map.put("sign_type", sign_type);
		   String notify_url = dataEntity.getNotify_url();
		   System.out.println("notify_url:"+notify_url);
		   map.put("notify_url", notify_url);
		   String timestamp = dataEntity.getTimestamp();
		   System.out.println("timestamp:"+timestamp);
		   map.put("timestamp", timestamp);
		   String version = dataEntity.getVersion();
		   System.out.println("version:"+version);
		   map.put("version", version);
		   
		   if(StringUtil.isEmpty(app_id) || biz_content == null
				   || StringUtil.isEmpty(charset) || StringUtil.isEmpty(method)
				   || StringUtil.isEmpty(sign_type) || StringUtil.isEmpty(notify_url)
				   || StringUtil.isEmpty(timestamp) || StringUtil.isEmpty(version)){
			   ResultEntity result = new ResultEntity<AlipaySignEntity>(0, "参数传值出错！", signEntity);
			   out.print(gson.toJson(result));  
			   out.close();
		   }
		   
//		   app_id 		= java.net.URLDecoder.decode(app_id, "UTF-8");
//		   biz_content  = java.net.URLDecoder.decode(biz_content, "UTF-8");
//		   charset 		= java.net.URLDecoder.decode(charset, "UTF-8");
//		   method 		= java.net.URLDecoder.decode(method, "UTF-8");
//		   sign_type 	= java.net.URLDecoder.decode(sign_type, "UTF-8");
//		   notify_url 	= java.net.URLDecoder.decode(notify_url, "UTF-8");
//		   timestamp 	= java.net.URLDecoder.decode(timestamp, "UTF-8");
//		   version 		= java.net.URLDecoder.decode(version, "UTF-8");
		   
		   signEntity = new AlipaySignEntity();
		   
//		   try {
//			   app_id 		= CodeUtil.decode(app_id, apptoken);
//			   biz_content  = CodeUtil.decode(biz_content, apptoken);
//			   charset 		= CodeUtil.decode(charset, apptoken);
//			   method 		= CodeUtil.decode(method, apptoken);
//			   sign_type 	= CodeUtil.decode(sign_type, apptoken);
//			   notify_url 	= CodeUtil.decode(notify_url, apptoken);
//			   timestamp 	= CodeUtil.decode(timestamp, apptoken);
//			   version 		= CodeUtil.decode(version, apptoken);
//		   } catch (Exception e) {
//			   log("获取sign参数解析异常:");
//			   log(e.getMessage(), e.fillInStackTrace());
//			   ResultEntity result = new ResultEntity<AlipaySignEntity>(0, "参数解析异常！", signEntity);
//			   PrintWriter out;
//			   out = response.getWriter();  
//			   out.print(gson.toJson(result));  
//			   out.close();
//		   }
		   signEntity.setApp_id(app_id);
		   signEntity.setBiz_content(biz_content);
		   signEntity.setCharset(charset);
		   signEntity.setMethod(method);
		   signEntity.setSign_type(sign_type);
		   signEntity.setNotify_url(notify_url);
		   signEntity.setTimestamp(timestamp);
		   signEntity.setVersion(version);
		   
		   String sign = OrderInfoUtil2_0.getSign(map, AlipayConfig.private_key);
		   signEntity.setSign(sign);
		   System.out.println("sign:"+sign);
		   signEntity.setOrderInfoEncoded(signEntity.toString());
		   ResultEntity result = new ResultEntity<AlipaySignEntity>(1, "成功", signEntity);
		   out.print(gson.toJson(result));  
		   out.close();
	   }else if(path.equals("/rsaCheckContent")){
		   /*String content 	  = request.getParameter("content");
		   String sign 	      = request.getParameter("sign");
		   String publicKey   = request.getParameter("publicKey");
		   String charset 	  = request.getParameter("charset");
		   try {
				boolean check = AlipaySignature.rsaCheckContent(content, sign, publicKey, charset);
				PrintWriter out;
			    out = response.getWriter();
			    out = response.getWriter();  
			    out.print("{\"check\":\""+check+"\"}");  
			    out.close();
			} catch (AlipayApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		   
	   }else if(path.equals("/receive_notify")){//请不要修改或删除
		   //System.out.println("aaaaaa");
			//获取支付宝POST过来反馈信息
			Map<String,String> params = new HashMap<String,String>();
			Map requestParams = request.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
				params.put(name, valueStr);
			}
			
			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
			//商户订单号

			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

			//支付宝交易号

			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

			//交易状态
			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

			if(AlipayNotify.verify(params)){//验证成功
				//////////////////////////////////////////////////////////////////////////////////////////
				//请在这里加上商户的业务逻辑程序代码

				//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
				
				if(trade_status.equals("TRADE_FINISHED")){
					//判断该笔订单是否在商户网站中已经做过处理
						//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
						//如果有做过处理，不执行商户的业务程序
						
					//注意：
					//该种交易状态只在两种情况下出现
					//1、开通了普通即时到账，买家付款成功后。
					//2、开通了高级即时到账，从该笔交易成功时间算起，过了签约时的可退款时限（如：三个月以内可退款、一年以内可退款等）后。
				} else if (trade_status.equals("TRADE_SUCCESS")){
					//判断该笔订单是否在商户网站中已经做过处理
						//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
						//如果有做过处理，不执行商户的业务程序
						
					//注意：
					//该种交易状态只在一种情况下出现——开通了高级即时到账，买家付款成功后。
				}

				//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
					
				System.out.println("success");	//请不要修改或删除

				//////////////////////////////////////////////////////////////////////////////////////////
			}else{//验证失败
				System.out.println("fail");
			}
		}
		   
	}
	
	
	

	
	
}
