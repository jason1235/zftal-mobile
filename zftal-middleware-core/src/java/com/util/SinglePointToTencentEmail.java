package com.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import net.sf.json.JSONObject;


/**
 * <p>Description: 单点登录到腾讯企业邮箱</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: zfsoft.com </p>
 *
 * @since 2015-2-6 下午03:22:58
 * @author yangz
 * @version 1.0
 */
public class SinglePointToTencentEmail {
	//地址
	private static final String host = "exmail.qq.com";
	//端口
	private static final String port = "443";
	//学校管理员账户
	private static final String app_id = "zjjcxy";
	//接口key
	private static final String app_secret = "d339e8868f1bbf8a0cd62b83189033b3";
	//拼接邮箱账号
	private static final String app_email="@zjjcxy.cn";

	/**
	 * <p>Description: 调用接口的方法</p>
	 * @param url      地址
	 * @param par      参数
	 * @return
	 *
	 * @since 2015-2-6 下午03:25:41
	 * @author yangz
	 */
	public static String getResponse(String url, String par) {
		String result = "";

		HttpURLConnection httpConn = null;
		try {
			URL urlObject = new URL(url);
			HttpURLConnection.setFollowRedirects(true);
			httpConn = (HttpURLConnection) urlObject.openConnection();
			httpConn.setRequestProperty("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
			httpConn.setRequestProperty("Host",host);
			httpConn.setRequestMethod("POST");
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			System.out.println(par);
		//	if (!Tool.isNull(par)) {
				httpConn.getOutputStream().write(par.getBytes("UTF-8"));
		//	}
				
			InputStreamReader isr = new InputStreamReader(httpConn
					.getInputStream(), "UTF-8");
			BufferedReader in = new BufferedReader(isr);
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				result += inputLine;
			}
			in.close();
		} catch (Exception e) {

			try {
				InputStreamReader isr = new InputStreamReader(httpConn
						.getErrorStream(), "GBK");
				BufferedReader in = new BufferedReader(isr);
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					result += inputLine;
				}
				in.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			e.printStackTrace();
		}

		return result;
	}

	/**
	 * <p>Description: 得到Token</p>
	 * @return
	 *
	 * @since 2015-2-6 下午03:25:56
	 * @author yangz
	 */
	public static String getAccessToken() {

		String url = "https://" + host + ":" + port + "/cgi-bin/token";
		String resultJson = getResponse(url,
				"grant_type=client_credentials&client_id=" + app_id
						+ "&client_secret=" + app_secret);
		JSONObject jsonObject = JSONObject.fromObject(resultJson);

		@SuppressWarnings("unchecked")
		HashMap<String, String> pojoValue = (HashMap<String, String>) JSONObject
				.toBean(jsonObject, HashMap.class);
		return pojoValue.get("access_token");
	}

	/**
	 * <p>Description: 得到Authkey</p>
	 * @param email    个人的邮箱地址
	 * @return
	 * @throws UnsupportedEncodingException
	 *
	 * @since 2015-2-6 下午03:26:09
	 * @author yangz
	 */
	public static String getAuthkey(String email)
			throws UnsupportedEncodingException {

		String accessToken = getAccessToken();

		String url = "http://" + "openapi.exmail.qq.com" + ":" + "12211"
				+ "/openapi/mail/authkey";

		String resultJson = getResponse(url, "access_token=" + accessToken
				+ "&alias=" + java.net.URLEncoder.encode(email, "utf-8"));

		JSONObject jsonObject = JSONObject.fromObject(resultJson);
		HashMap<?, ?> pojoValue = (HashMap<?, ?>) JSONObject.toBean(jsonObject,
				HashMap.class);
		return String.valueOf(pojoValue.get("auth_key"));

	}
	
	/**
	 * <p>Description: 单点登录到QQ企业邮箱</p>
	 * @param username
	 * @return
	 *
	 * @since 2015-2-9 上午09:27:22
	 * @author yangz
	 */
	public static String getUrl(String username) {
		//https://exmail.qq.com/cgi-bin/login?fun=bizopenssologin
		//https://mail.st.nuc.edu.cn/cgi-bin/login?fun=bizopenssologin
		//&method=bizauth&agent=<agent>&user=<email>&ticket=<ticket>
		try {
			String bizYhm = username;
			if (null != bizYhm && bizYhm.indexOf("@") == -1) {
				/*
				if("student".equals(ssobean.getJsName())){
					bizYhm = bizYhm + "@stu.yznu.cn";
				}else{
					bizYhm = bizYhm + "@yznu.cn";
				}
				*/
				bizYhm = bizYhm + app_email;
			}
			String ticket = getAuthkey(bizYhm);
			String url = "https://exmail.qq.com/cgi-bin/login?fun=bizopenssologin";
			/*
			if("student".equals(ssobean.getJsName())){
				ticket = getAuthkey2(bizYhm);
				url += "&method=bizauth&agent="+app_id2+"&user="+bizYhm+"&ticket="+ticket;
			}else{
				ticket = getAuthkey(bizYhm);
				url += "&method=bizauth&agent="+app_id+"&user="+bizYhm+"&ticket="+ticket;
			}
			*/
			url += "&method=bizauth&agent="+app_id+"&user="+bizYhm+"&ticket="+ticket;
			//url = "login?service="+URLEncoder.encode(url, "utf-8");
			//System.out.println("Bizmail:"+url);
			return url;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
