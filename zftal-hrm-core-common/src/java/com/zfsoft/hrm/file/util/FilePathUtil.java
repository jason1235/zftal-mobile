package com.zfsoft.hrm.file.util;

import java.util.Properties;

public class FilePathUtil {
	
	public static String resetPath(String path) {
		Properties prop = System.getProperties();
//		if(("Windows Vista").equals(prop.getProperty("os.name"))
//				||("Windows Server 2008").equals(prop.getProperty("os.name"))
//				||("Windows 7").equals(prop.getProperty("os.name"))){
//			path=path.replace("%20", " ");
//		}else{
//			path=path.replace(" ", "%20");
//		}
		System.out.println(prop.getProperty("os.name"));
		return path;
	}
	
	public static String resetPathToNotNull(String path) {
		path=path.replace(" ", "%20");
		return path;
	}
}
