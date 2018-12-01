package com.zfsoft.util.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;





public class BaseDataReader {
	
	private BaseDataReader(){
		
	}

    private static Logger log = LoggerFactory.getLogger(BaseDataReader.class.getName());
	
 
	public static Document getDoc(){
		try {
			 
			  SAXReader saxReader = new SAXReader();       
	            Document document = saxReader.read(BaseDataReader.class.getResource("")+"baseData.xml");
			 return document;
		}catch(Exception e){
			e.printStackTrace();
		}  
		 
		return null;
	}
	
	
	/**
	 * xml中读取下拉列表选项
	 * @param id baseData.xml中data接点的id
	 * @return
	 */
	public static List<HashMap<String,String>> getListOptions(final String id){
	
		Element root = BaseDataReader.getDoc().getRootElement();
		List<Element> dataList = root.elements("data");
		List<HashMap<String,String>> optionList = new ArrayList<HashMap<String,String>>();
		for (int i = 0 ; i < dataList.size() ; i++) {
			if (id.equalsIgnoreCase(dataList.get(i).attributeValue("id"))) {
				List<Element> options = dataList.get(i).elements("option");
				
				for (int j = 0 ; j < options.size() ; j++) {
					
					HashMap<String,String> option = new HashMap<String,String>();
					
					option.put("key", options.get(j).attributeValue("key"));
					option.put("value", options.get(j).getText());
					optionList.add(option);
				}
				break;
			}
		}
		return optionList;
	}
	
	
	public static void main(String[] args) {
		
		getListOptions("isNot");
		 
	}
}
