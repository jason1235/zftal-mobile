package com.zfsoft.sjzx.jhpz.wspz.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.zfsoft.sjzx.jhpz.wspz.model.WsjhParamsConfig;
import com.zfsoft.sjzx.jhpz.wspz.model.WsjhResult;
import com.zfsoft.sjzx.jhpz.wspz.model.WsjhdzConfig;
import com.zfsoft.zfca.tp.cas.util.Tool;


/**
 * <p>
 * xml帮助类
 * </p>
 * 
 * @author wangjian
 * 
 */
public class XmlHelper {

	/**
	 * <p>
	 * 根据LIST结果集获取对应的XML内容
	 * </p>
	 * 
	 * @param list
	 * @param root
	 * @param info
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getXml(List<Map<String,String>> list, String root, String info) {
		Document document = DocumentHelper.createDocument();
		Element rootElement = document.addElement(root);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map map = (Map) list.get(i);
				Element infoElement = rootElement.addElement(info);
				if (map != null && map.size() > 0) {
					for (@SuppressWarnings("unchecked")
					Iterator<Entry<String, String>> it = map.entrySet()
							.iterator(); it.hasNext();) {
						Entry<String, String> entry = it.next();
						String key = entry.getKey();
						String value = String.valueOf(entry.getValue());
						if (!Tool.isNull(key)) {
							Element keyElement = infoElement.addElement(key);
							value=value==null?"":value;
							keyElement.setText(value.trim());
						}
					}
				}
			}
		}
		return rootElement.asXML();
	}


	/**
	 * <p>
	 * 根据Map结果集获取对应的XML内容
	 * </p>
	 * 
	 * @param map
	 * @return
	 */
	public static String getXml(Map<String,String> map) {
		Document document = DocumentHelper.createDocument();
		Element rootElement = document.addElement("errors");
		if (map != null && map.size() > 0) {
					for (Iterator<Entry<String, String>> it = map.entrySet()
							.iterator(); it.hasNext();) {
						Entry<String, String> entry = it.next();
						String key = entry.getKey();
						String value = entry.getValue();
						if (!Tool.isNull(key) && !Tool.isNull(value)) {
							Element keyElement = rootElement.addElement(key);
							keyElement.setText(value);
						}
					}
		}
			
		return rootElement.asXML();
	}

	
	
	/**
	 * <p>根据文件路径获取对应的XML Document</p>
	 * @param file
	 * @return
	 * @throws Exception
	 */
	private static Document getDocument(String file) throws Exception{
		SAXReader saxReader = new SAXReader();
		Document doc = saxReader.read(new FileInputStream(file));
		return doc;
	}

	/**
	 * <p>删除对应的服务名</p>
	 * @param doc
	 * @param serviceName
	 * @return
	 */
	private static Document deleteElement(Document doc,String serviceName){
		Element rootElement = doc.getRootElement();
		Iterator it = rootElement.elementIterator("service");//获取子节点元素 ebank
		while(it.hasNext()){
			 Element element = (Element)it.next();
			 if(element!=null){
				String name = element.attributeValue("name");
				if(!Tool.isNull(name)&&name.equals(serviceName)){
					 rootElement.remove(element);
				}				
			 }
		}
		return doc;
	}
	
	
	/**
	 * <p>
	 * 添加服务名称元素
	 * </p>
	 * 
	 * @param doc
	 * @param serviceName
	 * @param serviceClassName
	 * @return
	 */
	private  static Document addElement(Document doc, String serviceName,
			String serviceClassName) {
		Element rootElement = doc.getRootElement();
		Element serviceElement = rootElement.addElement("service");
		serviceElement.addAttribute("name", serviceName);
		Element descElement = serviceElement.addElement("description");
		descElement.setText(serviceName);
		Element paramElement = serviceElement.addElement("parameter");
		paramElement.addAttribute("name", "ServiceClass");
		paramElement.setText(serviceClassName);
		Element mrsElement = serviceElement.addElement("messageReceivers");
		Element mrElement = mrsElement.addElement("messageReceiver");
		mrElement.addAttribute("mep", "http://www.w3.org/2004/08/wsdl/in-only");
		mrElement.addAttribute("class",
				"org.apache.axis2.rpc.receivers.RPCInOnlyMessageReceiver");
		Element mr2Element = mrsElement.addElement("messageReceiver");
		mr2Element.addAttribute("mep", "http://www.w3.org/2004/08/wsdl/in-out");
		mr2Element.addAttribute("class",
				"org.apache.axis2.rpc.receivers.RPCMessageReceiver");
		return doc;
	}

	/**
	 * <p>
	 * 把XML内容重新写入
	 * </p>
	 * 
	 * @param file
	 * @param doc
	 * @throws
	 */
	private  static void writeXml(String file, Document doc) throws Exception {
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		XMLWriter writer = new XMLWriter(new FileOutputStream(new File(file)),
				format);
		writer.write(doc);
		writer.close();
	}
	/**
	 * <p>发布生成XML</p>
	 * @param file
	 * @param serviceName
	 * @param className
	 * @throws Exception
	 */
	public static final void generatorXmlByDeploy(String file,String serviceName,String className) throws Exception{
		Document doc = getDocument(file);
		deleteElement(doc,serviceName);		
		writeXml(file,doc);
		
		Document doc2 = getDocument(file);
		addElement(doc2,serviceName,className);
		writeXml(file,doc2);
	}
	/**
	 * <p>删除并生成XML</p>
	 * @param file
	 * @param serviceName
	 * @throws Exception
	 */
	public static final void generatorXmlByDelete(String file,String serviceName) throws Exception{
		Document doc = getDocument(file);
		deleteElement(doc,serviceName);
		writeXml(file,doc);
	}

	public static void main(String[] args) throws Exception {
		Map<String,String> map = new HashMap<String,String>();
		map.put("msg","error");
		System.out.println(getXml(map));
	}

    /**
     * <p>获取第一条返回数据内容</p>
     * @param objs
     * @param index
     * @return
     * @throws DocumentException
     */
	public static List<String> getList(String objs,String type, int index) throws DocumentException {
		objs=replaceFf(objs);
		Document doc = DocumentHelper.parseText(objs);
			Element rootElement = doc.getRootElement();
			List<String> list = new ArrayList<String>();
			Iterator tableElement = rootElement.elementIterator(type);	
			 int i= 0;
			while(tableElement.hasNext()){
				 Element tElement = (Element)tableElement.next();				
				 if(tElement!=null&&i==index){
					 Iterator it= tElement.elementIterator();
					 while(it.hasNext()){
						 Element tsElement = (Element)it.next();
						 list.add(tsElement.getName());
					 }
				 }
				 i++;
			}
		
		return list;
	}
	/**
     * <p>返回WebService交换结果集</p>
     * @param objs
     * @param index
     * @return
     * @throws DocumentException
     */
	public static List<List<WsjhResult>> getList(String objs,String type,List<WsjhdzConfig> wcList,Map<String,String> paramsMap,List<WsjhParamsConfig> wpcList) throws DocumentException {		
		objs=replaceFf(objs);
		Document doc = DocumentHelper.parseText(objs);
			Element rootElement = doc.getRootElement();
			List<List<WsjhResult>> wrsList = new ArrayList<List<WsjhResult>>();
			Iterator tableElement = rootElement.elementIterator(type);				
			while(tableElement.hasNext()){
				 Element tElement = (Element)tableElement.next();	
				 List<WsjhResult> wrList = new ArrayList<WsjhResult>();
				 if(tElement!=null){
					 Iterator it= tElement.elementIterator();
					 while(it.hasNext()){
						 Element tsElement = (Element)it.next();
						 if(tsElement!=null&&wcList!=null&&wcList.size()>0){
							 WsjhdzConfig wc = getWsjhdzConfig(wcList,tsElement.getName());
							 if(wc!=null){
								 WsjhResult wr  = new WsjhResult();
								 wr.setMbzd(wc.getMbzd());
								 wr.setMbzdsjlx(wc.getMbzdsjlx());
								 wr.setSfzj(Integer.parseInt(wc.getSfzj()==null?"0":wc.getSfzj()));
								 wr.setValue(tsElement.getText());
								 wrList.add(wr);
							 }
						 }
					 }
				 }
				 wrsList.add(wrList);
			}
		 if(wrsList!=null&&wrsList.size()>0&&paramsMap!=null&&paramsMap.size()>0){
			 List<WsjhResult> wrList = wrsList.get(0);//取出默认第一条数据
			 if(wrList!=null&&wrList.size()>0&&wrList.size()==wcList.size()){
				 return wrsList;
			 }else{//返回结果数据与对照数据不相等。则进行在次赋值
				 wrsList = getList(wrsList,paramsMap,wcList,wpcList);//从方法参数中获取			 
			 }
		 }
		
		return wrsList;
	}

    private static List<List<WsjhResult>> getList(List<List<WsjhResult>> wrsList, Map<String, String> paramsMap,
			List<WsjhdzConfig> wcList,List<WsjhParamsConfig> wpcList) {
    	List<WsjhResult> wrList = wrsList.get(0);//默认取出第一条数据
    	List<WsjhdzConfig> wcsList = getNotContainsWsjhResultList(wrList,wcList,paramsMap);
    	List<WsjhResult> notContainsWrList = getNotContainsWsjhResultList(paramsMap, wcsList,wpcList);
    	for(List<WsjhResult> wrs:wrsList){
    		if(wrs!=null)wrs.addAll(notContainsWrList);
    	}    	
		return wrsList;
	}


	private static List<WsjhResult> getNotContainsWsjhResultList(Map<String, String> paramsMap, List<WsjhdzConfig> wcsList,List<WsjhParamsConfig> wpcList) {
		List<WsjhResult> wrList = new ArrayList<WsjhResult>();
		for(WsjhdzConfig wc:wcsList){    		 
    		 if(wc!=null){
    			 WsjhResult wr  = new WsjhResult();
    			 wr.setMbzd(wc.getMbzd());
    			 wr.setMbzdsjlx(wc.getMbzdsjlx());
    			 wr.setSfzj(Integer.parseInt(wc.getSfzj()==null?"0":wc.getSfzj()));
    			 wr.setValue(paramsMap.get(getMbzdByParams(wc.getFhbl(),wpcList)));
    			 wrList.add(wr);
    		 }			 
    	}
		return wrList;
	}

    private static String getMbzdByParams(String fhbl,List<WsjhParamsConfig> wpcList) {
    	if(wpcList!=null){
			for(WsjhParamsConfig wpc:wpcList){
				if(wpc!=null&&wpc.getCszd()!=null){
					if(wpc.getCszd().equals(fhbl))return wpc.getCsz();
				}
			}
    	}
		return fhbl;
	}


	/**
     * <p>获取没有包含在交换结果中的字段对照</p>
     * @param wrList
     * @param wcList
     * @return
     */
	private static  List<WsjhdzConfig> getNotContainsWsjhResultList(	List<WsjhResult> wrList, List<WsjhdzConfig> wcList,Map<String,String> paramsMap) {
		List<WsjhdzConfig> notContainsWcList = new ArrayList<WsjhdzConfig>();
		for(WsjhdzConfig wc:wcList){
			if(wc!=null&&wc.getMbzd()!=null){
				if(!isNotContainsMbzd(wc.getMbzd(),wrList)){
					notContainsWcList.add(wc);
				}
			}
		}
		return notContainsWcList;
	}


	private static boolean isNotContainsMbzd(String mbzd,List<WsjhResult> wrList) {
		for(WsjhResult wr:wrList){
			if(wr!=null&&wr.getMbzd().equals(mbzd))return true;
		}
		return false;
	}


	/**
     * <p>根据名称获取对应的返回字段对照配置内容</p>
     * @param wcList
     * @param name
     * @return
     */
	private static WsjhdzConfig getWsjhdzConfig(List<WsjhdzConfig> wcList,	String name) {
		for(WsjhdzConfig wc:wcList){
			if(wc!=null){
				if(wc.getFhbl()!=null&&wc.getFhbl().equals(name))return wc;
			}
		}
		return null;
	}
    
	public static String replaceFf(String objs){
		if(objs!=null&&!"".equals(objs)){
			objs.replaceAll("\000", "").replaceAll("\0x0", "");	
		}
		return objs;
	}



}
