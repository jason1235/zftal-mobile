package com.newBackMh.parameters.test;

import java.util.Arrays;
import java.util.List;

import com.common.JavaXML;
import com.common.backMhConfig;

public class BackMhparameters {
	public static void main(String[] args) {

	
		
		    String TabName = backMhConfig.getString("backMhTabName");
		    String TabType = backMhConfig.getString("backMhTabType");
		    TabName="校园资讯;首页";
		    TabType="0;1";
			String[] tabmane = TabName.split(";");
			String[] tabtype = TabType.split(";");
			String tabNames=null;
			if(TabName.equals("") && TabType.equals("")){
				
				StringBuffer msg=new StringBuffer();
				msg.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				msg.append("<zfsoft>");
				msg.append("</zfsoft>");
				tabNames=msg.toString();
				System.out.println(tabNames);
			}
			else{
				List tabmanelist = Arrays.asList(tabmane);
				List tabtypelist = Arrays.asList(tabtype);
//				List list = XMLParser.ReadXMl();
				tabNames=JavaXML.BuildXMLDocBack(tabmanelist,tabtypelist);
				System.out.println(tabNames);
			}
		
	
	}
}
