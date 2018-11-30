package com.mobileWebUrl.test;



import com.common.Config;
import com.util.SinglePointToTencentEmail;

public class mobiletest {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	/*	String WebViewURL = Config.getString("url.ydxx");//设置WebView对应url
		System.out.print(WebViewURL);*/
	//	System.setProperty("javax.net.ssl.trustStore", "C:/Program Files/Java/jdk1.7.0_71/jre/lib/security/cacerts");
		String username ="shijin";//@zjjcxy.cn
		String zs = Config.getString("url.zs");
		System.setProperty("javax.net.ssl.trustStore", zs);
		System.getProperties().setProperty( "http.proxyHost", "10.71.19.195" );
	    System.getProperties().setProperty( "http.proxyPort","3125" );
	    System.getProperties().setProperty( "https.proxyHost", "10.71.19.195" );
	    System.getProperties().setProperty( "https.proxyPort","3125" );
		String webViewURL = SinglePointToTencentEmail.getUrl(username);
		System.out.print(webViewURL);
		
	
	}
}
