package com.zfsoft.mobile.xfyj.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.zfsoft.mobile.ballot.entity.Ballot;
import com.zfsoft.mobile.servlet.entity.ListEntity;
import com.zfsoft.mobile.servlet.entity.ResultEntity;
import com.zfsoft.mobile.xfyj.entity.XfyjEntity;
import com.zfsoft.mobile.xfyj.entity.XfyjxqEntity;
import com.zfsoft.mobile.xfyj.entity.XfyjxqQueryEntity;
import com.zfsoft.mobile.xfyj.service.XfyjService;
import com.zfsoft.untils.CodeUtil;

/*
 * 学情预警
 */
public class XfyjAction {
	private static Logger logger = Logger.getLogger(XfyjAction.class);

	private XfyjService xfyjService;

	public void getList() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		Gson gson = new Gson();
		String apptoken = "";
		try {
			PrintWriter out = response.getWriter();
			//apptoken = new String(request.getParameter("apptoken").getBytes("ISO8859-1"), "UTF-8");

			List<XfyjEntity> list = xfyjService.getList();
			ResultEntity<List<XfyjEntity>> result = new ResultEntity<List<XfyjEntity>>(
					1, "", list);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getDetail() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		Gson gson = new Gson();
		String apptoken = "";
		String id = "";
		String zgh = "";
		String start = null;
		String size = null;
		try {
			PrintWriter out = response.getWriter();
			apptoken = new String(request.getParameter("apptoken").getBytes("ISO8859-1"), "UTF-8");
			id = new String(request.getParameter("id").getBytes("ISO8859-1"), "UTF-8");
			start = new String(request.getParameter("start").getBytes("ISO8859-1"), "UTF-8");
			size = new String(request.getParameter("size").getBytes("ISO8859-1"), "UTF-8");
			zgh = new String(request.getParameter("userName").getBytes("ISO8859-1"), "UTF-8");

			try {
				id = CodeUtil.decode(id, apptoken);
				start  				= CodeUtil.decode(start, apptoken);
				size  				= CodeUtil.decode(size, apptoken);
				zgh					= CodeUtil.decode(zgh, apptoken);
				System.err.println("start = " + start + "and size = "+ size);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			//List<XfyjxqEntity> list = xfyjService.getDetails(id,zgh);
			//ResultEntity<List<XfyjxqEntity>> result = new ResultEntity<List<XfyjxqEntity>>(1, "", list);
			//out.write(gson.toJson(result));
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		XfyjAction.logger = logger;
	}

	public XfyjService getXfyjService() {
		return xfyjService;
	}

	public void setXfyjService(XfyjService xfyjService) {
		this.xfyjService = xfyjService;
	}
}
