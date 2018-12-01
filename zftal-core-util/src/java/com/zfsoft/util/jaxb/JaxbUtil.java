package com.zfsoft.util.jaxb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.zfsoft.util.base.StringUtil;
/**
 * xml和对象转化工具
 * @author Patrick Shen
 */
public class JaxbUtil {

	/**
	 * xml转对象
	 * @param obj
	 * @return
	 */
	public static String getXmlFromObject(Object obj){
		return getXmlFromObjectSchema(obj,true);
	}
	
	/**
	 * xml转对象
	 * @param obj
	 * @return
	 */
	public static String getXmlFromObjectSchema(Object obj,Boolean hasSchema){
		String xml = "";
		if(obj==null){
			return xml;
		}
		try {
			JAXBContext context = JAXBContext.newInstance(obj.getClass());
			Marshaller m = context.createMarshaller();
			String encoding = "GBK";
			m.setProperty(Marshaller.JAXB_ENCODING, encoding);
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.setProperty(Marshaller.JAXB_FRAGMENT, false);
			if(!hasSchema){
				m.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, false);
				m.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, false);
			}
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			m.marshal(obj, outputStream);
			xml = outputStream.toString();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return xml;
	}
	/**
	 * 对象转xml
	 * @param value
	 * @param cl
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getObjectFromXml(String value,Class<T> cl){
		value=value.replaceAll("[\\x00-\\x08\\x0b-\\x0c\\x0e-\\x1f]", "");
		if(StringUtil.isEmpty(value)){
			return null;
		}
		try {
			value = value.replaceAll("\n", "&lt;br/&gt;");
			value = value.replaceAll(">&lt;br/&gt;", ">");
			JAXBContext context = JAXBContext.newInstance(cl);
			
			ByteArrayInputStream fr = new ByteArrayInputStream(value.getBytes());
	
			InputStreamReader inputStreamReader = new InputStreamReader(fr,"GBK");
	
			Unmarshaller um = context.createUnmarshaller();
	
			return (T)um.unmarshal(inputStreamReader);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}
}
