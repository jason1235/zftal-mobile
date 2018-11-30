package com.zfsoft.util.base;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;


public class ResponseUtils {
	
	public static void setHtmlText(String text, HttpServletResponse response) throws IOException {
		response = response == null ? ServletActionContext.getResponse()
				: response;
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(text);
	}
	
	public static void setHtmlText(String text) throws IOException {
		setHtmlText(text, null);
	}
	
	
	public static void setStringText(String text, HttpServletResponse response) throws IOException {
		response = response == null ? ServletActionContext.getResponse()
				: response;
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(text);
	}
	
	public static void setStringText(String text) throws IOException {
		ResponseUtils.setStringText(text, null);
	}
	
	public static void setStringText(int text) throws IOException {
		ResponseUtils.setStringText(String.valueOf(text), null);
	}
	
	public static void setXmlText(String text, HttpServletResponse response) throws IOException {
		response = response == null ? ServletActionContext.getResponse()
				: response;
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(text);
	}
	
	public static void setXmlText(String text) throws IOException {
		ResponseUtils.setXmlText(text, null);
	}
	
	public static void setJsonText(String text, HttpServletResponse response) throws IOException {
		response = response == null ? ServletActionContext.getResponse()
				: response;
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(text);
	}
	
	public static void setJsonText(String text) throws IOException {
		ResponseUtils.setJsonText(text, null);
	}
}
