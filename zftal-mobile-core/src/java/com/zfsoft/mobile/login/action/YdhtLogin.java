package com.zfsoft.mobile.login.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.common.Config;
//import com.ibm.icu.text.SimpleDateFormat;
import com.zfsoft.common.action.BaseAction;
import com.zfsoft.common.log.Role;
import com.zfsoft.common.log.User;
import com.zfsoft.common.system.SubSystemHolder;
import com.zfsoft.dao.entities.LoginModel;
import com.zfsoft.dao.entities.YhglModel;
import com.zfsoft.mobile.MD5Util.MD5Util;
import com.zfsoft.mobile.common.utils.MobileSystemHolder;
import com.zfsoft.service.svcinterface.ILoginService;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.encrypt.DBEncrypt;

public class YdhtLogin extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 200319411841306471L;
	
	private ILoginService loginService;

	public ILoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(ILoginService loginService) {
		this.loginService = loginService;
	}

	public void getInfoClass() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String choice  = request.getParameter("choice");
		String userId  = request.getParameter("uid");
		String type    = request.getParameter("type");
		User user = new User();
		HttpSession session = getSession();
		/*if(StringUtil.isEmpty(Config.getString("caurl")) || 
				(!StringUtil.isEmpty(Config.getString("caurl")) && !Config.getString("caurl").equals("yes"))){*/
			String procode = request.getParameter("procode");
			String key     = request.getParameter("key");
			String pk = MobileSystemHolder.getPropertiesValue("ydmh" + "_private_key");
			String privatekey;
			DBEncrypt tool = new DBEncrypt();
			
			if (StringUtils.isEmpty(pk)) {
	    		privatekey = "";
	    	} else {
	    		privatekey = tool.dCode(pk.getBytes());
	    	}
			
			String publickey = null;
			publickey = MD5Util.md5Encode(procode + choice + userId + privatekey);
			System.out.println(publickey);
			
			if(!StringUtil.isEmpty(userId) && !StringUtil.isEmpty(key) && !StringUtil.isEmpty(publickey) && publickey.equals(key)){
				LoginModel model = new LoginModel();
				model.setYhm(userId);
				user = loginService.cxYhxx(model);
				session.setAttribute(USER_INFO_KEY, user);
				this.initRoles(user);
				
			}
		/*}else{
			if(!StringUtil.isEmpty(userId)){
				LoginModel model = new LoginModel();
				model.setYhm(userId);
				user = loginService.cxYhxx(model);
				session.setAttribute(USER_INFO_KEY, user);
				this.initRoles(user);
			}
		}*/
		
		String address =  request.getScheme() + "://"+ request.getServerName() //获取访问移动后台的部署ip及端口
		+ ":" + request.getServerPort() + request.getContextPath();

		address += choice.equals("booklist")  ? "/portal/portal_booklist.html" : 
				   choice.equals("paymyself") ? "/portal/portal_paymyself.html":
				   choice.equals("cardlist")  ? "/portal/portal_cardlist.html" : 
				   choice.equals("srlist")  ? "/portal/portal_srlist.html"+ userId: 
				   choice.equals("examlist")  ? "/exampaper/exampaper_list.html" : 
				   choice.equals("twoExamlist")  ? "/twoExampaper/twoExampaper_list.html" : 
				   choice.equals("assetlist")  ? "/asset/asset_list.html" : 
					   "/basedata/basedata_clientBaseData.html";
			      
		
        //String address =  request.getScheme() + "://"+ request.getServerName()
        //         + ":" + request.getServerPort() + request.getContextPath() + "/basedata/basedata_clientBaseData.html";
		//System.out.println(address);
		response.sendRedirect(address + "?classId=" + choice + "&type=" + type);
	}
	
	/**
	 * 初始化角色信息
	 * @param user
	 */
    private void initRoles(User user){
    	//如果系统部署方式为平台部署,session中标识为平台登陆
    	if("platform".equals(SubSystemHolder.getPropertiesValue("system_deploy"))){
    		getSession().setAttribute("platform_deploy", "1");
    		getSession().setAttribute("platform_login", "1");
    		return;
    	}
    	String login_type=SubSystemHolder.getPropertiesValue("login_type");
    	//用户拥有的角色
    	List<Role> allRoles=user.getAllRoles();
    	//1、单角色登陆
    	if("sole_role".equals(login_type)){
    		List<String> jsdms=new ArrayList<String>(1);
    		if(allRoles==null||allRoles.size()==0){
    			user.setJsdms(jsdms);
    			getRequest().getSession().setAttribute(user.getYhm(), null);
    			return;
    		}else if(StringUtils.isEmpty(user.getScdljsdm())){
    			String currentJsdm=allRoles.get(0).getJsdm();
    			user.setScdljsdm(currentJsdm);
    			jsdms.add(currentJsdm);
    			//保存本次登陆的角色
    		}else{
    			//检测上次登陆的角色，该用户是否还拥有，如果不再拥有，则取第一个拥有的角色，并写入db
    			for (Role role : allRoles) {
					if(user.getScdljsdm().equals(role.getJsdm())){
						jsdms.add(user.getScdljsdm());
						break;
					}
				}
    			if(jsdms.size()==0){
    				String currentJsdm=allRoles.get(0).getJsdm();
    				user.setScdljsdm(currentJsdm);
    				jsdms.add(currentJsdm);
    				//保存本次登陆的角色
        			YhglModel yhglModel=new YhglModel();
        			yhglModel.setZgh(user.getYhm());
        			yhglModel.setScdljsdm(currentJsdm);
    			}
    		}
    		user.setJsdms(jsdms);
    	}else{
    		//2、普通登陆
    		if(allRoles==null||allRoles.size()==0){
    			List<String> emptyList=Collections.emptyList();
    			user.setJsdms(emptyList);
    			getRequest().getSession().setAttribute(user.getYhm(), null);
    			return;
    		}else{
    			List<String> jsdms=new ArrayList<String>();
    			for(Role role:user.getAllRoles()){
    				jsdms.add(role.getJsdm());
    			}
    			user.setJsdms(jsdms);
    		}
    	}
    	getRequest().getSession().setAttribute(user.getYhm(), user.getJsdms());
    }
    
    public static void main(String[] args) throws Exception {
    	String pk = "jLAwzgEPwViRGcbYbrb1tRuCZrcNLmmS";
		String privatekey;
		DBEncrypt tool = new DBEncrypt();
		if (StringUtils.isEmpty(pk)) {
    		privatekey = "";
    	} else {
    		privatekey = tool.dCode(pk.getBytes());
    	}
    	String publickey = null;
		publickey = MD5Util.md5Encode("003" + "4" + "20140001" + privatekey);
		System.out.println(publickey);
		System.out.println(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	}
	
}
