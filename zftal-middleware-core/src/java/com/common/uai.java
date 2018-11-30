package com.common;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class uai {
	
	private static HashMap numList;

	public static HashMap parse(String xml) throws DocumentException 
	{
		SAXReader reader = new SAXReader();
		ByteArrayInputStream stream = new ByteArrayInputStream(xml.getBytes());
		Document doc = reader.read(stream);
		Element root = doc.getRootElement();
		Element foo;
		

		for (Iterator i = root.elementIterator("uai"); i.hasNext();) 
		{
			foo = (Element) i.next();
			
			
				String yhm = foo.elementText("yhm");
		//		Map<String,String> numList = new HashMap<String, String>();
				numList = new HashMap<String, String>();
				
				Element foo1;
				Element foo2;
				
				for (Iterator k = foo.elementIterator("xtusers"); k.hasNext();) 
				{					
					foo1 = (Element) k.next();
					for (Iterator j = foo1.elementIterator("xtuser"); j.hasNext();) 
					{
						foo2 = (Element) j.next();
						numList.put(foo2.attributeValue("xtmd"), foo2.getText());
					}
				}
			}
		     return numList;
		
		}
}
