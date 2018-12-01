package com.zfsoft.mobile.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClientUtil {

	public static String getResponse(String method,String url, String par) {
		String result = "";
		HttpURLConnection httpConn = null;
		try {
			System.out.println(url);
			URL urlObject = new URL(url);
			httpConn = (HttpURLConnection) urlObject.openConnection();
			httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			httpConn.setRequestMethod(method);
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);

			if (par != null) {
				httpConn.getOutputStream().write(par.getBytes());
			}
			System.out.println(par);
			InputStreamReader isr = new InputStreamReader(httpConn.getInputStream(), "UTF-8");
			BufferedReader in = new BufferedReader(isr);
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				result += inputLine;
			}
			in.close();

		} catch (Exception e) {
			e.printStackTrace();

			InputStreamReader isr = null;
			try {
				if (httpConn != null) {
					isr = new InputStreamReader(httpConn.getErrorStream(), "utf-8");
					BufferedReader in = new BufferedReader(isr);
					String inputLine;
					while ((inputLine = in.readLine()) != null) {
						result += inputLine;
					}
					httpConn.disconnect();
					System.out.println(result);
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}finally{
			if (httpConn != null) {
				httpConn.disconnect();
			}
		}

		return result;
	}

}
