package com.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;



/**
 * <p>Description: 从配置的XML里面读取数据</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: zfsoft.com </p>
 *
 * @since 2014-10-20 下午06:52:49
 * @author yangz
 * @version 1.0
 */
public class XMLParser {
	private static final String BACK_CONF_FILE = "/HouTaiTab.xml";
	private static String absolutePath;
	
	/**
	 * <p>Description: 得到本地的XML</p>
	 * @return
	 *
	 * @since 2014-10-20 下午06:53:19
	 * @author yangz
	 */
	protected static String getConfigFile(){
		return BACK_CONF_FILE;
	}
	
	
	/**
	 * <p>Description: 返回读取的XML数据</p>
	 * @return
	 *
	 * @since 2014-10-20 下午06:53:08
	 * @author yangz
	 */
	public static List ReadXMl() {
		List temp =null;
		 try {
		 absolutePath = XMLParser.class.getResource(getConfigFile()).getFile();

		 List list =getTicketResult(absolutePath);
		
		    return list;
		
		 } catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	          catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return temp;
	}
	
	
	/**
	 * <p>Description: 解析XML</p>
	 * @param xml
	 * @return
	 * @throws DocumentException
	 * @throws FileNotFoundException
	 *
	 * @since 2014-10-20 下午06:53:50
	 * @author yangz
	 */
	@SuppressWarnings("rawtypes")
	public static List getTicketResult(String xml) throws DocumentException,FileNotFoundException  
	{
		SAXReader reader = new SAXReader();
		
	//	ByteArrayInputStream stream = new ByteArrayInputStream(xml.getBytes());
	    FileInputStream in = new FileInputStream(xml);
		Document doc = reader.read(in);
		Element root = doc.getRootElement();
		Element foo;
		List<String> list = new ArrayList<String>();


		for (Iterator i = root.elementIterator("tabname"); i.hasNext();) 
		{
			foo = (Element) i.next();
			
			String nm = foo.elementText("name");
			list.add(nm);
		
		}
		return list;
	}
}
