package com.zfsoft.mobile.skin.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.mobile.servlet.entity.ListEntity;
import com.zfsoft.mobile.servlet.entity.ResultEntity;
import com.zfsoft.mobile.skin.entity.SkinEntity;
import com.zfsoft.mobile.skin.service.ISkinService;
import com.zfsoft.mobile.vote.entity.VoteMainEntity;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.CodeUtil;
import com.zfsoft.util.base.StringUtil;

/**
 * 手机端皮肤action
 * @author liucb
 *
 */
public class SkinAction extends HrmAction{
    private static Logger logger = Logger.getLogger(SkinAction.class);
	private ISkinService skinService;
	
	/**
	 * 获取皮肤列表
	 */
	public void list(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String username=null;
		String apptoken=null;
		
		Gson gson = new Gson();
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try{
			username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
		 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			
			if(!ApptokenUtils.compare(apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			try {
				username     = CodeUtil.decode(username, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			List<SkinEntity> skinList = skinService.getSkinList();
		
	        ResultEntity<List<SkinEntity>> result = new ResultEntity<List<SkinEntity>>(1, "成功", skinList);
			
	        out.write(gson.toJson(result));
	        out.flush();
	        out.close();
		}catch(Exception ex){
			ResultEntity<String> result = new ResultEntity<String>(0, "失败", "后台异常，获取数据失败");
			out.write(gson.toJson(result));
			out.flush();
			out.close();
			ex.printStackTrace();
		}
	}
	
	/**
	 * 下载皮肤apk文件
	 */
	public void downloadSkinApkFile(){
		
	}
	
	
	
	public ISkinService getSkinService() {
		return skinService;
	}
	
	public void setSkinService(ISkinService skinService) {
		this.skinService = skinService;
	}
}
