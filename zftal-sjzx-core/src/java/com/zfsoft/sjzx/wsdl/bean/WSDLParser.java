package com.zfsoft.sjzx.wsdl.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.zfsoft.util.base.StringUtil;




public class WSDLParser extends DefaultHandler {
	public   List<String> imports = new ArrayList<String>();
	public   List<String> operationNames = new ArrayList<String>();
	public   List<Parameters> parameters = new ArrayList<Parameters>();
	public   List<Operations> operations = new ArrayList<Operations>();
	public 	 String namespace = null;
	Operations operation = null;
	  int index = 0;
	
	boolean opFlag = false;
	boolean typeFlag = false,schemaFlag = false,paramFlag = false,elementStart = false;
	String formDefault = "qualified";

	//开始解析文档
	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		
	}

	//开始解析元素
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		
		
		//import
		if (qName.endsWith(":import")) {
			if (attributes != null && attributes.getLength() > 0) {
				String location = StringUtil.isEmpty(attributes.getValue("location")) ? StringUtil.isEmpty("schemaLocation") 
						  ? null : attributes.getValue("schemaLocation")
							  	 : attributes.getValue("location");
				imports.add(location);
				namespace = attributes.getValue("namespace");
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
			typeFlag = true;
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
		} else if (typeFlag&&schemaFlag&&(StringUtil.isEmpty(formDefault) || (
										  !StringUtil.isEmpty(formDefault) && 
										  "unqualified".equalsIgnoreCase(formDefault)))
				  ) {
			//cxf格式
			if (qName.endsWith(":complexType")&& attributes != null && attributes.getLength()>0) {
				System.out.println("in new Operations()-------");
				operation = new Operations();
				operation.setName(attributes.getValue("name"));
				System.out.println("operation:"+operation.getName());
				paramFlag = true;
			}
			if (paramFlag && qName.endsWith(":element")) {
				System.out.println("in parameters.add(param)-------");
				Parameters param = new Parameters();
				param.setIndex(index++);
				String type = attributes.getValue("type");
				if (type.split(":").length > 1) {
					param.setType(type.split(":")[1]);
				} else {
					param.setType(attributes.getValue("type"));
				}
				param.setName(attributes.getValue("name"));
				System.out.println("param:"+param.getName());
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
			typeFlag = false;
		}
		if (qName.endsWith(":complexType")&& (StringUtil.isEmpty(formDefault) || (
											  !StringUtil.isEmpty(formDefault) && 
											  "unqualified".equalsIgnoreCase(formDefault)))
			) {
			paramFlag = false;
			index = 0;
			List<Parameters> paras = new ArrayList<Parameters>();
			paras.addAll(parameters);
			operation.setParams(paras);
			parameters.clear();
			operations.add(operation);
			System.out.println("out new Operations()-------");
		} else if (qName.endsWith(":complexType") && "qualified".equalsIgnoreCase(formDefault)) {
			paramFlag = false;
		}
		if (elementStart && !paramFlag && qName.endsWith(":element")) {
			System.out.println("out elementStart = false-------");
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
			//if (operationNames.size() > 0){
				//for (int i = 0; i < operationNames.size(); i++) {
					for (int j = 0; j < operations.size(); j++) {
						if(operations.get(j).getName().endsWith("Response")) continue;
						Operations op = operations.get(j);
						//if (operationNames.get(i).equalsIgnoreCase(op.getName())) {
							ops.add(op);
						//}
					}
				//}
			//}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*WSDLParser.imports.clear();
		WSDLParser.operationNames.clear();
		WSDLParser.operations.clear();
		WSDLParser.parameters.clear();*/
		return ops;
	}
	
	public static void main(String[] args) {
		String url = "D:/LibServicePort.xml";
		System.out.println(url);
		
		List<Operations> ops =new WSDLParser().parse(url);
		System.out.println(ops);
	}
	
}
