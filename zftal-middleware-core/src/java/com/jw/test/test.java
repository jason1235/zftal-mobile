package com.jw.test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jw.httpUtils.HttpRequest;
import com.newmobile.login.service.IWSSerService;
import com.util.encode.MD5Util;

public class test {
	
	public static void main(String[] args) {  
		boolean a = false;
		List<String> ddd = new ArrayList<String>();
				
				try {
					String time = String.valueOf(System.currentTimeMillis());
					time = time.substring(0, time.length() - 3);
					System.out.println(time);
					String svpn_pwd = MD5Util.md5Encode(time + "test0814" + "admin123" + "sangfor.vpn");
//					System.out.println(
//							"https://vpntest.zjedu.gov.cn/por/login_custom_psw.csp?svpn_name=test0814&timestamp=" +
//							time +
//							"&svpn_psw=" +  svpn_pwd +
//							"&redirect_url=" + 
//							java.net.URLEncoder.encode(
//									"vpntest1.zjedu.gov.cn/WebReport/ReportServer?formlet=gis.frm"
//									, "UTF-8"
//									)
//							);
					
					System.out.println(
							"https://vpntest.zjedu.gov.cn/por/login_custom_psw.csp?svpn_name=test0814&timestamp=" +
							time +
							"&svpn_psw=" +  svpn_pwd +
							"&redirect_url=" + 
							java.net.URLEncoder.encode(
									"vpntest2.zjedu.gov.cn/oa2016/sso/zfca?redirectUrl=/Project/ZjJytOA/MOA/WorkflowTodoList.mhtml&ticket=ST-19-u1F0AyhGwQajaQKhE2om-zfca&caUserName=wangxd"
									, "UTF-8"
									)
							);
					
					
					System.out.println(
							java.net.URLEncoder.encode(
									"自考考生免考课程证明",
									"utf-8"
									)
							);
					String test = "自考考生免考课程证明";
					String aaa = new String(test.getBytes("iso-8859-1"),"utf-8");
					System.out.println(aaa);
					String Str = "卫";
					byte[] bytes = Str.getBytes("ISO8859-1");//""里面的参数为需要转化的编码，一般是ISO8859-1 
					String str = new String(bytes,"utf-8");//转化为utf-8编码
					System.out.println(str);
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
    }  

}






