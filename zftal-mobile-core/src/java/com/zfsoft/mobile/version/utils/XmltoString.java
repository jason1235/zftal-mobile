/**    
 * 文件名：XmltoString.java    
 *    
 * 版本信息：    
 * 日期：Nov 12, 2009    
 * Copyright 足下 Corporation 2009     
 * 版权所有    
 *    
 */
package com.zfsoft.mobile.version.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**    
 *     
 * 项目名称：zfoa    
 * 类名称：XmltoString    
 * 类描述：    
 * 创建人：zhoujiawei    
 * 创建时间：Nov 12, 2009 4:54:37 PM    
 * 修改人：zhoujiawei    
 * 修改时间：Nov 12, 2009 4:54:37 PM    
 * 修改备注：    
 * @version     
 *     
 */
public class XmltoString {
	
	/**
	 * <p>Description: 将列表对象转成XML格式字符串</p>
	 * @param output -- 输出的对象字段名 数组
	 * @param list --  输入的对象字段hashmap列表---HashMap<String,String>类型的
	 * @param lx --对象po名称
	 * @return String
	 *
	 * @since 2012-4-19 下午04:43:50
	 * @author xuxinghua
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String xmlToString(String []output,List list,String lx)
	{
		StringBuffer msg=new StringBuffer();
		msg.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?> <ZFSOFT>");
		
		Iterator iterator=list.iterator();
		while(iterator.hasNext())
		{
			HashMap<String,String> hash=new HashMap<String,String>();
			hash=(HashMap<String,String>)iterator.next();
			msg.append("<"+lx.toUpperCase()+">");
			
		    for(int i=0;i<output.length;i++)
		    {
		    	msg.append("<"+output[i].toUpperCase()+"><![CDATA[");
		    	msg.append(hash.get(output[i])==null?"":hash.get(output[i]));
		    	msg.append("]]></"+output[i].toUpperCase()+">");
		    }
			msg.append("</"+lx+">");
			
		}
		msg.append("</ZFSOFT>");
		
		return msg.toString();
	}
	
	/**
	 * <p>Description: 将列表对象转成XML格式字符串</p>
	 * @param output -- 输出的对象字段名 数组
	 * @param list --  输入的对象字段hashmap列表---HashMap<String,String>类型的
	 * @param lx --对象po名称
	 * @param zfMap --- xml主节点zfsoft 的属性 key value的map
	 * @return String
	 *
	 * @since 2012-4-19 下午04:43:50
	 * @author xuxinghua
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "null" })
	public static String xmlToString(String []output,List list,String lx,Map zfMap)
	{
		StringBuffer msg=new StringBuffer();
		msg.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?> ");
		String zf = "<ZFSOFT>";
		if(zfMap!=null || zfMap.size()>0){//zfsoft 这个主类里面有关键字
			zf = "<ZFSOFT ";
			Iterator ite=zfMap.entrySet().iterator();
			while(ite.hasNext()){	
				Map.Entry entry = (Map.Entry)ite.next();
				Object key = entry.getKey();
				Object value = entry.getValue();
				zf += " "+key+"=\"" + value +"\"";
			}
			zf += ">";
		}else{
			//不处理
		}
		
		msg.append(zf);
		
		Iterator iterator=list.iterator();
		while(iterator.hasNext())
		{
			HashMap<String,String> hash=new HashMap<String,String>();
			hash=(HashMap<String,String>)iterator.next();
			msg.append("<"+lx.toUpperCase()+">");
			
		    for(int i=0;i<output.length;i++)
		    {
		    	msg.append("<"+output[i].toUpperCase()+"><![CDATA[");
		    	msg.append(hash.get(output[i])==null?"":hash.get(output[i]));
		    	msg.append("]]></"+output[i].toUpperCase()+">");
		    }
			msg.append("</"+lx+">");
			
		}
		msg.append("</ZFSOFT>");
		
		return msg.toString();
	}
	/**
	 * <p>Description: 将列表对象转成XML格式字符串</p>
	 * @param output -- 输出的对象字段名 数组
	 * @param list --  输入的对象字段hashmap列表---HashMap<String,String>类型的
	 * @param lx --对象po名称
	 * @return String
	 *
	 * @since 2012-4-19 下午04:43:50
	 * @author xuxinghua
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String xmlToStringNew(String []output,List list,String lx)
	{
		StringBuffer msg=new StringBuffer();
		msg.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?> <ZFSOFT>");
		
		Iterator iterator=list.iterator();
		while(iterator.hasNext())
		{
			HashMap<String,String> hash=new HashMap<String,String>();
			hash=(HashMap<String,String>)iterator.next();
			msg.append("<"+lx.toUpperCase()+">");
			
		    for(int i=0;i<output.length;i++)
		    {
		    	msg.append("<"+output[i].toUpperCase()+">");
		    	if(output[i].toUpperCase().equals("URL")){
		    		msg.append("<![CDATA[");
		    	}
		    	msg.append(hash.get(output[i])==null?"":hash.get(output[i]));
		    	if(output[i].toUpperCase().equals("URL")){
		    		msg.append("]]>");
		    	}
		    	msg.append("</"+output[i].toUpperCase()+">");
		    }
			msg.append("</"+lx+">");
			
		}
		msg.append("</ZFSOFT>");
		
		return msg.toString();
	}
	/**
	 * <p>Description: 将列表对象转成XML格式字符串</p>
	 * @param output -- 输出的对象字段名 数组
	 * @param list --  输入的对象字段hashmap列表---HashMap<String,String>类型的
	 * @param lx --对象po名称
	 * @param zfMap --- xml主节点zfsoft 的属性 key value的map
	 * @return String
	 *
	 * @since 2012-4-19 下午04:43:50
	 * @author xuxinghua
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "null" })
	public static String xmlToStringNew(String []output,List list,String lx,Map zfMap)
	{
		StringBuffer msg=new StringBuffer();
		msg.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?> ");
		String zf = "<ZFSOFT>";
		if(zfMap!=null || zfMap.size()>0){//zfsoft 这个主类里面有关键字
			zf = "<ZFSOFT ";
			Iterator ite=zfMap.entrySet().iterator();
			while(ite.hasNext()){	
				Map.Entry entry = (Map.Entry)ite.next();
				Object key = entry.getKey();
				Object value = entry.getValue();
				zf += " "+key+"=\"" + value +"\"";
			}
			zf += ">";
		}else{
			//不处理
		}
		
		msg.append(zf);
		
		Iterator iterator=list.iterator();
		while(iterator.hasNext())
		{
			HashMap<String,String> hash=new HashMap<String,String>();
			hash=(HashMap<String,String>)iterator.next();
			msg.append("<"+lx.toUpperCase()+">");
			
		    for(int i=0;i<output.length;i++)
		    {
		    	msg.append("<"+output[i].toUpperCase()+"><![CDATA[");
		    	msg.append(hash.get(output[i])==null?"":hash.get(output[i]));
		    	msg.append("]]></"+output[i].toUpperCase()+">");
		    }
			msg.append("</"+lx+">");
			
		}
		msg.append("</ZFSOFT>");
		
		return msg.toString();
	}
}
