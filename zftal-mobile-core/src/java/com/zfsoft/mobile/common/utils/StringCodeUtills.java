package com.zfsoft.mobile.common.utils;

import org.apache.commons.lang.StringEscapeUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.google.gson.JsonArray;
import com.zfsoft.weibo.weibo4j.org.json.JSONObject;

public class StringCodeUtills {

	   /**
     * 替换一个字符串中的某些指定字符
     * @param strData String 原始字符串
     * @param regex String 要替换的字符串
     * @param replacement String 替代字符串
     * @return String 替换后的字符串
     */
    public static String replaceString(String strData, String regex,
            String replacement)
    {
        if (strData == null)
        {
            return null;
        }
        int index;
        index = strData.indexOf(regex);
        String strNew = "";
        if (index >= 0)
        {
            while (index >= 0)
            {
                strNew += strData.substring(0, index) + replacement;
                strData = strData.substring(index + regex.length());
                index = strData.indexOf(regex);
            }
            strNew += strData;
            return strNew;
        }
        return strData;
    }
 
    /**
     * 替换字符串中特殊字符
     */
  public static String encodeString(String strData)
    {
        if (strData == null)
        {
            return "";
        }
        strData = replaceString(strData, "&", "&amp;");
        strData = replaceString(strData, "<", "&lt;");
        strData = replaceString(strData, ">", "&gt;");
        strData = replaceString(strData, "&apos;", "&apos;");
        strData = replaceString(strData, "\"", "&quot;");
        return strData;
    }
 
    /**
     * 还原字符串中特殊字符
     */
   public static String decodeString(String strData)
    {
        strData = replaceString(strData, "&lt;", "<");
        strData = replaceString(strData, "&gt;", ">");
        strData = replaceString(strData, "&apos;", "&apos;");
        strData = replaceString(strData, "&quot;", "\"");
        strData = replaceString(strData, "&amp;", "&");
        return strData;
    }
   
   public static void main(String[] args) {
	   try {
	   StringBuilder sb = new StringBuilder();
	   sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><zfsoft pagesize=\"11\" start=\"2\" size=\"10\"><news>")
	   	 .append("<title><![CDATA[新疆班学生观看“致敬白衣天使&#8226精彩故事--双百优双十佳医生护士颁奖典礼”]]></title></news></zfsoft>");
	   StringCodeUtills utils = new StringCodeUtills();
	   String xml = sb.toString();
	   Document document = null;
			document = DocumentHelper.parseText(xml);
       Element elementTemplate = document.getRootElement();  
       Element ResultInfo = (Element)elementTemplate.selectSingleNode("//news"); 
       Element code = (Element)elementTemplate.selectSingleNode("//title"); 
       String aaa = code.getText();
       System.out.println(aaa);

		if(ResultInfo != null && code != null){
			String errorCode = elementTemplate.elementText("title");
			if(errorCode != null && !errorCode.equals("")){
				System.out.println(errorCode);
			}
				
		}
	   } catch (Exception e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
	   }
   }
   
}
