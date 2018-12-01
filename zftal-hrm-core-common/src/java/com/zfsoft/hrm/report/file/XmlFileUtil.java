package com.zfsoft.hrm.report.file;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.zfsoft.hrm.report.xentity.ReportContent;
/**
 * 报表文件转换对象
 * @author 沈鲁威 Patrick Shen
 * @since 2012-9-13
 * @version V1.0.0
 */
public class XmlFileUtil {

	/**
	 * 从数据库文件中解析表头对象
	 * 
	 * @param fileName
	 * @return
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static ReportContent getXmlTitleSet(InputStream inputStream) {
		if (inputStream == null) {
			return null;
		}
		try {

			JAXBContext context;

			context = JAXBContext.newInstance(ReportContent.class);
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream);

			Unmarshaller um = context.createUnmarshaller();

			ReportContent xmlTitleSet = (ReportContent) um
					.unmarshal(inputStreamReader);

			return xmlTitleSet;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getXmlTitleSetString(Object obj) {
		String xml = "";
		if(obj==null){
			return xml;
		}
		try {
			JAXBContext context = JAXBContext.newInstance(obj.getClass());
			Marshaller m = context.createMarshaller();
			String encoding = System.getProperty("file.encoding");
			m.setProperty(Marshaller.JAXB_ENCODING, encoding);
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.setProperty(Marshaller.JAXB_FRAGMENT, false);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			m.marshal(obj, outputStream);
			xml = outputStream.toString();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return xml;

	}
	
	public static byte[] getXmlTitleSetByte(Object obj) {
		ByteArrayOutputStream outputStream = null;
		try {
			JAXBContext context = JAXBContext.newInstance(obj.getClass());
			Marshaller m = context.createMarshaller();
			String encoding = System.getProperty("file.encoding");
			m.setProperty(Marshaller.JAXB_ENCODING, encoding);
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.setProperty(Marshaller.JAXB_FRAGMENT, false);
			outputStream = new ByteArrayOutputStream();
			m.marshal(obj, outputStream);
			return outputStream.toByteArray();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}
}
