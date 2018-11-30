package com.zfsoft.globalweb.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

public class JSONUtils {

	public static String xml2json(String xml) {
		try {
			XMLSerializer serializer = new XMLSerializer();
			return serializer.read(xml).toString();
		} catch (Exception e) {
			return "[]";
		}
		
	}
	
	public static String obj2json(Object obj) {
		if (obj != null) {
			if (obj instanceof List || obj instanceof Object[]) {
				return JSONArray.fromObject(obj).toString();
			} else {
				return JSONObject.fromObject(obj).toString();
			}
		} else {
			return "{}";
		}
		//return JSONArray.fromObject(obj).toString();
	}
	
	public static String obj2json2(Object obj) {
		if (obj instanceof List || obj instanceof Object[]) {
			return JSONArray.fromObject(obj).toString();
		}
		return JSONObject.fromObject(obj).toString();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><AppTypeItemList><AppItemType>"+
"	<module>                    "+
"    <id>jw-kb</id>             "+
"    <type>native</type>           "+
"    <name>我的课表</name>          "+
"		    <icon></icon>       "+
"		    <url></url>         "+
"		    <bak></bak>         "+
"<moduletype>jw</moduletype>    "+
" </module>                     "+
"	 <module>                   "+
"    <id>jw-ydxk</id>           "+
"    <type>web</type>              "+
"    <name>移动选课</name>          "+
"		    <icon></icon>       "+
"		    <url>http://</url>  "+
"		    <bak></bak>         "+
"<moduletype>jw</moduletype>    "+
"</module>                      "+
"</AppItemType>                 "+
"</AppTypeItemList>";*/
		String xml = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"+
  " <soap:Body> "+
  "    <ns2:getTicket2Response xmlns:ns2=\"http://service.ca.webservice.zfsoft.com/\"> "+
  "       <return><![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?><ResultInfo><code>1</code><message>用户名或密码错误。</message></ResultInfo>]]></return> "+
  "    </ns2:getTicket2Response> "+
  " </soap:Body> "+
  " </soap:Envelope> ";
		String test = xml2json(xml);
		System.out.println(xml2json(xml));
		Map<String, String> map = new HashMap<String, String>();
		map.put("aaa", "aav");
		map.put("bbb", "bbv");
		System.out.println(obj2json2(map));
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		list.add(map);
		list.add(map);
		System.out.println(obj2json2(list));
	}

}
