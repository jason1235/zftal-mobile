package com.zfsoft.util.base;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <p>
 * Title:对配置文件进行分析
 * </p>
 * <p>
 * Description: 通过对配置文件进行分解后，返回配置文件的属性
 * </p>
 * 
 * @author zhangqy
 * @version 1.0
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ConfigurationResourceUtil {

	private static Map map = new HashMap();
	/**
	 * 静态初始化
	 */
	static {
		try {
			initial();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始话内容 Map
	 * 
	 */
	private static void initial() throws Exception {
		Document document = null;
		InputStream input = null;
		SAXReader saxReader = new SAXReader();

		// 得到配置文件流
		input = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("ConfigurationResource.xml");
		document = saxReader.read(input);
		// 得到description-content下的desc元素下所有的元素
		List list = document.selectNodes("//configurations/configuration");
		Iterator it = list.iterator();
		while (it.hasNext()) {
			String key = "";
			String value = "";
			Element element = (Element) it.next();
			// 得到ID值
			key = (element.attribute("id").getValue());
			// 得到对因的id的值
			Iterator iterator = element.elementIterator("value");
			Element attributeElement = (Element) iterator.next();
			value = attributeElement.getText();
			map.put(key, value);
		}
	}

	/**
	 * 通过配置文件中的ID值得到相对应的内容
	 * 
	 * @param key
	 *            String 配置文件对应的id
	 * @return String
	 */
	public static String getValue(String idKey) {
		String value = "";
		try {
			if (map != null && idKey != null) {
				value = (String) map.get(idKey);
				// 如果第一次取值为空的话,重新解析一下配置文件
				if (value == null || value.equals("")) {
					initial();
					value = (String) map.get(idKey);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

}
