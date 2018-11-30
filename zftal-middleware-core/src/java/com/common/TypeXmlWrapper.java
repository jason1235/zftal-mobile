package com.common;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;


///

public class TypeXmlWrapper {
	
	private Document document = null;
	private Element root = null;
	
	///
	
	public TypeXmlWrapper()
	{
		document = DocumentHelper.createDocument();
		root = document.addElement("zfsoft");
	}

	public String getTypeXml() throws IOException  
	{
		StringWriter stringWriter = new StringWriter();  
        OutputFormat xmlFormat = new OutputFormat();  
        xmlFormat.setEncoding("UTF-8");
        xmlFormat.setNewlines(true);
        xmlFormat.setIndent(true); 
        xmlFormat.setIndent("    ");
        XMLWriter xmlWriter = new XMLWriter(stringWriter, xmlFormat);
        xmlWriter.write(document);
        xmlWriter.close(); 
		return stringWriter.toString();
	}
	
	public Element addType(String name, String id, List<entry> children)
	{
		Element node = root.addElement("ttype");
		node.addAttribute("name", name);
		node.addAttribute("id", id);
		
		for (int i = 0; children != null && i < children.size(); i++)
		{
			entry pair = children.get(i);
			String cname = pair.name;
			String cid = pair.id;
			
			// skip invalid child node.
			if (cid == null || "".equals(cid)) continue;
			
			if (cname == null) cname = "";
			
			Element child = node.addElement("type");
			Element child1 = child.addElement("name");
			child1.addText(cname);
			Element child2 = child.addElement("id");
			child2.addText(cid);
		}
		
		return node;
	}
	
	public class entry
	{
		public String name = "";
		public String id = "";
	}

}
