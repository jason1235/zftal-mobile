package com.zfsoft.wechat.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 通用工具
 * @author Administrator
 *
 */
public class WeChatCommonUtil {
	private static Logger log = LoggerFactory.getLogger(WeChatCommonUtil.class);
	
	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		log.info("httpsRequest START");
		log.info("####REQUEST_URL: " + requestUrl);
		JSONObject jsonObject = null;
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new CertificateTrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(ssf);
			conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			
			// 设置请求方式（GET/POST）
			conn.setRequestMethod(requestMethod);

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.flush();
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}

			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
			
		} catch (ConnectException ce) {
			log.error("WeChat server connection timed out", ce);
		} catch (Exception e) {
			log.error("https request error:{}", e);
		}
		log.info("####REQUEST_RESULT: " + jsonObject.toString());
		log.info("httpsRequest END");
		return jsonObject;
	}
	
	/**
	 * 发起http请求并获取结果
	 * 
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
		log.info("httpsRequest START");
		log.info("####REQUEST_URL: " + requestUrl);
		JSONObject jsonObject = null;
		try {
			

			URL url = new URL(requestUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			//conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			
			// 设置请求方式（GET/POST）
			conn.setRequestMethod(requestMethod);

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.flush();
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}

			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
			
		} catch (ConnectException ce) {
			log.error("WeChat server connection timed out", ce);
			ce.printStackTrace();
		} catch (Exception e) {
			log.error("https request error:{}", e);
			e.printStackTrace();
		}
		//log.info("####REQUEST_RESULT: " + jsonObject.toString());
		log.info("httpsRequest END");
		return jsonObject;
	}
	
	public static void main(String[] args) {
//		System.getProperties().put("proxySet", "true");
//		System.getProperties().put("proxyHost", "10.71.19.195");
//		System.getProperties().put("proxyPort", "3125");
		String url = "https://api.mp.qq.com/cgi-bin/token?appid=200448796&secret=dI4ugawJpueqYyGT";
		String url2 = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxe83023a145c7f2cf&secret=37b714eb9e125a47b890a7f12c5e9f09";
		String menu = "https://api.mp.qq.com/cgi-bin/menu/get?access_token=fd9b289cd9385c31829e3949ba8799ce";
		String urll = "http://10.71.33.72:8088/zftal-mobile/commonHttp/commonHttp_personDocumentInformation.html?username=UkoCrVZl4qI%3D&apptoken=864b77ead925642362447b6cc7371aba&strKey=UkoCrVZl4qI%3D&informationName=UkoCrVZl4qI%3D&informationId=zfgXXTauj4f%2B9P%2FogO84k%2F4ICTibt73ibviJ4ky1DSw2SbWDOD%2BuaQ%3D%3D";
		JSONObject obj = httpsRequest(menu, "GET", null);
		JSONObject obj1 = httpRequest(urll, "GET", null);
		if (obj != null) {
			System.out.println("结果：" + obj1.toString());
		}
	}
	


}