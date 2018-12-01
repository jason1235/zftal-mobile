package com.common;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * <p>
 * Description: 生成XML文件
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * <p>
 * Company: zfsoft.com
 * </p>
 * 
 * @since 2014-10-20 下午02:09:16
 * @author yangz
 * @version 1.0
 */
public class JavaXML {

	public static String BuildXMLDocBack(List list, List list1) {

		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("zfsoft");

		for (int i = 0; i < list.size(); i++) {
			String name = (String) list.get(i);
			String type = (String) list1.get(i);
			Element book = root.addElement("tabname");
			// book.addAttribute("price", "108");
			// book.addElement("name").setText("Java编程思想");

			book.addElement("name").setText(name);
			book.addElement("type").setText(type);
		}

		OutputFormat format = OutputFormat.createCompactFormat(); // createPrettyPrint()
		// 层次格式化
		StringWriter writer = new StringWriter();
		XMLWriter output = new XMLWriter(writer, format);
		try {
			output.write(doc);
			writer.close();
			output.close();
			// System.out.println(writer.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return writer.toString();
	}

	/**
	 * 返回xml
	 * 
	 * @param list
	 * @return
	 */
	public static String BuildXMLDoc(List list, List list1) {

		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("zfsoft");
		for (int i = 0; i < list.size(); i++) {
			String name = (String) list.get(i);
			String type = (String) list1.get(i);
			Element book = root.addElement("type");
			// book.addAttribute("price", "108");
			// book.addElement("name").setText("Java编程思想");

			book.addElement("mc").setText(name);
			book.addElement("bh").setText(type);
		}
		/*
		 * book.addElement("author").setText("Bruce Eckel"); book =
		 * root.addElement("book"); book.addAttribute("price", "52");
		 * book.addElement("name").setText("Effective Java");
		 * book.addElement("author").setText("Joshua Bloch"); book =
		 * root.addElement("book"); book.addAttribute("price", "118");
		 * book.addElement("name").setText("Java 7入门经典");
		 * book.addElement("author").setText("Ivor Horton");
		 */
		OutputFormat format = OutputFormat.createCompactFormat(); // createPrettyPrint()
		// 层次格式化
		StringWriter writer = new StringWriter();
		XMLWriter output = new XMLWriter(writer, format);
		try {
			output.write(doc);
			writer.close();
			output.close();
			// System.out.println(writer.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return writer.toString();
	}

	/**
	 * 返回xml
	 * 
	 * @param list
	 * @return
	 */
	public static String BuildXMLDocByNoticeTopType(List list, List list1,
			List list2) {
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("zfsoft");
		for (int i = 0; i < list.size(); i++) {
			String name = (String) list.get(i);
			String type = (String) list1.get(i);
			String status = (String) list2.get(i);
			Element book = root.addElement("tabname");
			// book.addAttribute("price", "108");
			// book.addElement("name").setText("Java编程思想");

			book.addElement("name").setText(name);
			book.addElement("type").setText(type);
			book.addElement("status").setText(status);
		}
		/*
		 * book.addElement("author").setText("Bruce Eckel"); book =
		 * root.addElement("book"); book.addAttribute("price", "52");
		 * book.addElement("name").setText("Effective Java");
		 * book.addElement("author").setText("Joshua Bloch"); book =
		 * root.addElement("book"); book.addAttribute("price", "118");
		 * book.addElement("name").setText("Java 7入门经典");
		 * book.addElement("author").setText("Ivor Horton");
		 */
		OutputFormat format = OutputFormat.createCompactFormat(); // createPrettyPrint()
		// 层次格式化
		StringWriter writer = new StringWriter();
		XMLWriter output = new XMLWriter(writer, format);
		try {
			output.write(doc);
			writer.close();
			output.close();
			// System.out.println(writer.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return writer.toString();
	}

	public static String BuildXMLDocLmId(List list) {

		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("zfsoft");

		for (int i = 0; i < list.size(); i++) {
			String name = (String) list.get(i);
			Element book = root.addElement("type");
			book.addElement("name").setText(name);
		}

		OutputFormat format = OutputFormat.createCompactFormat(); // createPrettyPrint()
		// 层次格式化
		StringWriter writer = new StringWriter();
		XMLWriter output = new XMLWriter(writer, format);
		try {
			output.write(doc);
			writer.close();
			output.close();
			// System.out.println(writer.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return writer.toString();
	}

	/**
	 * <p>Description: 拼装错误信息XML</p>
	 * @param msg
	 * @param msg1
	 * @return
	 *
	 * @since 2015-2-4 下午03:51:35
	 * @author yangz
	 */
	public static String BuildXMLReturnErro(String msg, String msg1) {
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("zfsoft");
		Element book = root.addElement("error");

		book.addElement("msg").setText(msg);
		book.addElement("code").setText(msg1);
		OutputFormat format = OutputFormat.createCompactFormat(); // createPrettyPrint()
		// 层次格式化
		StringWriter writer = new StringWriter();
		XMLWriter output = new XMLWriter(writer, format);
		try {
			output.write(doc);
			writer.close();
			output.close();
			// System.out.println(writer.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return writer.toString();
	}
	
	/**
	 * <p>Description: 返回通知公告子类型</p>
	 * @param list
	 * @param list1
	 * @param Label
	 * @return
	 *
	 * @since 2015-2-9 上午09:11:16
	 * @author yangz
	 */
	public static String BuildXMLForBackMhNoticeList(List list, List list1,String Label) {

		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("zfsoft");
		for (int i = 0; i < list.size(); i++) {
			String name = (String) list.get(i);
			String type = (String) list1.get(i);
			Element book = root.addElement("tabname");
			// book.addAttribute("price", "108");
			// book.addElement("name").setText("Java编程思想");

			book.addElement("name").setText(name);
			book.addElement("type").setText(type);
			book.addElement("dataparsemethod").setText(Label);
		}
		
		OutputFormat format = OutputFormat.createCompactFormat(); // createPrettyPrint()
		// 层次格式化
		StringWriter writer = new StringWriter();
		XMLWriter output = new XMLWriter(writer, format);
		try {
			output.write(doc);
			writer.close();
			output.close();
			// System.out.println(writer.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return writer.toString();
	}
	
	
	
	
	public static String BuildXMLForBackMhNewNoticeList(List list, List list1,List list2) {

		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("zfsoft");
		for (int i = 0; i < list.size(); i++) {
			String name = (String) list.get(i);
			String type = (String) list1.get(i);
			String stauts = (String) list2.get(i);
			Element book = root.addElement("tabname");
			// book.addAttribute("price", "108");
			// book.addElement("name").setText("Java编程思想");

			book.addElement("name").setText(name);
			book.addElement("type").setText(type);
			book.addElement("dataparsemethod").setText(stauts);
		}
		
		OutputFormat format = OutputFormat.createCompactFormat(); // createPrettyPrint()
		// 层次格式化
		StringWriter writer = new StringWriter();
		XMLWriter output = new XMLWriter(writer, format);
		try {
			output.write(doc);
			writer.close();
			output.close();
			// System.out.println(writer.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return writer.toString();
	}
	
	/**
	 * <p>Description: 将JSON转成XML</p>
	 * @param array
	 * @return
	 *
	 * @since 2015-3-4 下午03:29:04
	 * @author yangz
	 */
	public static String BuildXMLForJson(JSONArray array) {

	
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("data");
		for (int i = 2; i < array.size(); i++) {
			Element book = root.addElement("msg");
			JSONObject jo = (JSONObject) array.get(i);
			book.addElement("yhsj").setText(jo.get("xhsk").toString());
			book.addElement("dzzh").setText(jo.get("dzzh").toString());
			book.addElement("sm").setText(jo.get("ztm").toString());
			book.addElement("wjsj").setText(jo.get("jcsk").toString());
			}
		
		OutputFormat format = OutputFormat.createCompactFormat(); // createPrettyPrint()
		// 层次格式化
		StringWriter writer = new StringWriter();
		XMLWriter output = new XMLWriter(writer, format);
		try {
			output.write(doc);
			writer.close();
			output.close();
			// System.out.println(writer.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return writer.toString();
	}
}
