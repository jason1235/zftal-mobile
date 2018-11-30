package com.zfsoft.mobile.wsdl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


import org.apache.commons.lang.StringUtils;
import org.codehaus.xfire.client.Client;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class WSDLParser extends DefaultHandler {
	public  List<String> imports = new ArrayList<String>();
	public  List<String> operationNames = new ArrayList<String>();
	public  List<Parameters> parameters = new ArrayList<Parameters>();
	public  List<Operations> operations = new ArrayList<Operations>();
	
	Operations operation = null;
	static int index = 0;
	
	boolean opFlag = false;
	boolean typeFlag = false,schemaFlag = false,paramFlag = false,elementStart = false;
	String formDefault = "qualified";

	public WSDLParser(){
		reset();
	}
	
	//开始解析文档
	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 将webserviceUrl路径转为合法的路径
	 * 
	 * @param webserviceUrl
	 * @return
	 * @throws Exception
	 */
	public String getWebserviceUrl(String webserviceUrl){
		if (StringUtils.isNotEmpty(webserviceUrl)) {
			// 判断url里面是否存在wsdl后缀
			if (webserviceUrl.indexOf("?") >= 0) {
				if (!webserviceUrl.endsWith("wsdl")) {
					webserviceUrl = new StringBuilder(webserviceUrl).append("wsdl").toString();
				}
			} else {
				webserviceUrl = new StringBuilder(webserviceUrl).append("?wsdl").toString();
			}
			return webserviceUrl;
		} else {
			return "";
		}
	}
	
	/**
	 * 测试webserviceice是否可通
	 * @throws Exception 
	 * @throws Exception 
	 */
	public boolean isCanPass(String webserviceUrl) throws Exception {
		
		webserviceUrl  = this.getWebserviceUrl(webserviceUrl);
		URL _url = null;
		_url = new URL(webserviceUrl);
	    HttpURLConnection httpConnection = null;
		httpConnection = (HttpURLConnection)_url.openConnection();
	    httpConnection.setReadTimeout(20000);//设置http连接的读超时,单位是毫秒
	    try {
	    	httpConnection.connect();
		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
			System.out.println("连接url出错！！！");
			return false;
		}
		String contentType = httpConnection.getContentType();
		//System.out.println(contentType.equals("text/xml"));
		return contentType.equals("text/xml") ? true : false;
		
	}
	
	/**
	 * 获取所有方法
	 * @throws Exception 
	 */
	public List<String> getAllMethodByServiceUrl(String webserviceUrl) throws Exception{
		//System.out.println(isCanPass(webserviceUrl));
		if(!isCanPass(webserviceUrl)) return null;
		webserviceUrl  = this.getWebserviceUrl(webserviceUrl);
		
		List<String> methodList = new ArrayList<String>();
		List<Operations> ops = new ArrayList<Operations>();
		ops =this.parse(webserviceUrl);
		for(Operations operation : ops){
			methodList.add(operation.getName());
		}
		return methodList;
	}
	
	/**
	 * 获取方法的所有参数
	 * @throws Exception 
	 */
	public List<Parameters> getParamByMethodNameAndWsUrl(String methodName, String webserviceUrl) throws Exception{
		if(!isCanPass(webserviceUrl) || webserviceUrl == "" || methodName == null || methodName == "") return null;
		
		webserviceUrl  = this.getWebserviceUrl(webserviceUrl);
		
		List<Operations> ops =new WSDLParser().parse(webserviceUrl);
		for(Operations operation : ops){
			if(operation.getName().trim().equals(methodName)){
				return  operation.getParams();
			}
		}
		return null;
	}
	
	/**
	 * 测试webservice接口方法是否可行
	 */
	public boolean executionMethod(String webserviceUrl, String methodName, List<Parameters> parametersList) throws Exception {
		if(!isCanPass(webserviceUrl) || webserviceUrl == "" || methodName == null || methodName == "")
			return false;
		
		webserviceUrl  = this.getWebserviceUrl(webserviceUrl);
		
		Object parameter[] = new Object[parametersList.size()];
		for(int i = 0; i<parametersList.size(); i++){
			if(parametersList.get(i).getType().equals("int"))
				parameter[i] = 0;
			if(parametersList.get(i).getType().equals("string"))
				parameter[i] = "";
			if(parametersList.get(i).getType().equals("boolean"))
				parameter[i] = false;
		}
		Client client = new Client(new URL(webserviceUrl));
		try {
			Object[] results = client.invoke(methodName, parameter);
			StringBuilder sb = new StringBuilder();
			for(Object result : results){
				sb.append((String)result);
			}
			System.out.println(sb);
		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
			return false;
		}
       return true;
	}
	
	/**
	 * 测试webservice接口方法是否可行
	 */
	public String getExecutionResult(String webserviceUrl, String methodName, List<Parameters> parametersList , String[] paramList) throws Exception {
		if(!isCanPass(webserviceUrl) || webserviceUrl == "" || methodName == null || methodName == "")
			throw new Exception("webserviceUrl不能通过或地址、方法、参数为空！");
		
		webserviceUrl  = this.getWebserviceUrl(webserviceUrl);
		
		Object parameter[] = new Object[parametersList.size()];
		for(int i = 0; i<parametersList.size(); i++){
			if(parametersList.get(i).getType().equals("int"))
				parameter[i] = Integer.parseInt(paramList[i]);
			if(parametersList.get(i).getType().equals("string"))
				parameter[i] = paramList[i];
			if(parametersList.get(i).getType().equals("boolean"))
				parameter[i] = paramList[i] == "1" ? true : false;
		}
		Client client = new Client(new URL(webserviceUrl));
		try {
			Object[] results = client.invoke(methodName, parameter);
			StringBuilder sb = new StringBuilder();
			for(Object result : results){
				sb.append(result.toString());
			}
			return sb.toString();
		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
			throw e;
		}
	}

	//开始解析元素
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		
		
		//import
		if (qName.endsWith(":import")) {
			if (attributes != null && attributes.getLength() > 0) {
				imports.add(attributes.getValue("location"));
			}
		}
		//portType
		if (qName.endsWith(":portType")) {
			opFlag = true;
		}
		if (opFlag && qName.endsWith(":operation")) {
			if (attributes != null && attributes.getLength() > 0) {
				operationNames.add(attributes.getValue("name"));
			}
		}
		//type
		if (qName.endsWith(":types")) {
			typeFlag = true;
		}
		if (qName.endsWith(":schema")) {
			schemaFlag = true;
			if (attributes != null && attributes.getLength() > 0) {
				formDefault = attributes.getValue("elementFormDefault");
			}
		}
		if (typeFlag&&schemaFlag&&"qualified".equalsIgnoreCase(formDefault)) {
			//xfire格式
			if (qName.endsWith(":element") && attributes != null && attributes.getLength()==1) {
				elementStart = true;
				operation = new Operations();
				operation.setName(attributes.getValue("name"));
			}
			if (qName.endsWith(":complexType")) {
				paramFlag = true;
			}
			if (qName.endsWith(":element") && attributes != null && attributes.getLength()>1) {
				Parameters param = new Parameters();
				param.setIndex(index++);
				String type = attributes.getValue("type");
				if (type.split(":").length > 1) {
					param.setType(type.split(":")[1]);
				} else {
					param.setType(attributes.getValue("type"));
				}
				param.setName(attributes.getValue("name"));
				parameters.add(param);
			}
		} else if (typeFlag&&schemaFlag&&"unqualified".equalsIgnoreCase(formDefault)) {
			//cxf格式
			if (qName.endsWith(":complexType")&& attributes != null && attributes.getLength()>0) {
				operation = new Operations();
				operation.setName(attributes.getValue("name"));
				paramFlag = true;
			}
			if (paramFlag && qName.endsWith(":element")) {
				Parameters param = new Parameters();
				param.setIndex(index++);
				String type = attributes.getValue("type");
				if (type.split(":").length > 1) {
					param.setType(type.split(":")[1]);
				} else {
					param.setType(attributes.getValue("type"));
				}
				param.setName(attributes.getValue("name"));
				parameters.add(param);
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		if (qName.endsWith(":portType")) {
			opFlag = false;
		}
		if (qName.endsWith(":types")) {
			typeFlag = false;
		}
		if (qName.endsWith(":schema")) {
			schemaFlag = false;
		}
		if (qName.endsWith(":complexType")&& "unqualified".equalsIgnoreCase(formDefault)) {
			paramFlag = false;
			index = 0;
			List<Parameters> paras = new ArrayList<Parameters>();
			paras.addAll(parameters);
			operation.setParams(paras);
			parameters.clear();
			operations.add(operation);
		} else if (qName.endsWith(":complexType") && "qualified".equalsIgnoreCase(formDefault)) {
			paramFlag = false;
		}
		if (elementStart && !paramFlag && qName.endsWith(":element")) {
			elementStart = false;
			//paramFlag = false;
			index = 0;
			List<Parameters> paras = new ArrayList<Parameters>();
			paras.addAll(parameters);
			operation.setParams(paras);
			parameters.clear();
			operations.add(operation);
		}
		
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}
	
	public List<Operations> parse(String path) {
		List<Operations> ops = new ArrayList<Operations>();
		SAXParserFactory parserFactory = SAXParserFactory.newInstance();
		try {
			SAXParser parser = parserFactory.newSAXParser();
			parser.parse(path, this);
			if (imports.size() > 0) {
				for (int i = 0; i < imports.size(); i++) {
					parser.parse(imports.get(i), this);
				}
			}
			if (operationNames.size() > 0){
				for (int i = 0; i < operationNames.size(); i++) {
					for (int j = 0; j < operations.size(); j++) {
						Operations op = operations.get(j);
						if (operationNames.get(i).equalsIgnoreCase(op.getName())) {
							ops.add(op);
						}
					}
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ops;
	}
	
	public void reset(){
		imports = new ArrayList<String>();
		operationNames = new ArrayList<String>();
		List<Parameters> parameters = new ArrayList<Parameters>();
		List<Operations> operations = new ArrayList<Operations>();
	}
	
}
