package com.zfsoft.weibo.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONArray;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.weibo.action.entity.WeiBoEntity;
import com.zfsoft.weibo.service.IWeiboService;
import com.zfsoft.weibo.weibo4j.Oauth;
import com.zfsoft.weibo.weibo4j.examples.oauth2.Log;
import com.zfsoft.weibo.weibo4j.http.AccessToken;
import com.zfsoft.weibo.weibo4j.http.HttpClient;
import com.zfsoft.weibo.weibo4j.http.HttpRequest;
import com.zfsoft.weibo.weibo4j.model.PostParameter;
import com.zfsoft.weibo.weibo4j.model.WeiboException;
import com.zfsoft.weibo.weibo4j.org.json.JSONObject;
import com.zfsoft.weibo.weibo4j.util.BareBonesBrowserLaunch;
import com.zfsoft.weibo.weibo4j.util.WeiboConfig;

public class WeiboAction  extends HrmAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -800545839058129490L;
	public static WeiBoEntity entity = new WeiBoEntity();
	private IWeiboService weiboService;
	private WeiBoEntity query = new WeiBoEntity();
	private PageList<WeiBoEntity> list;
	private String op = "add";
	private WeiBoEntity model = new WeiBoEntity();
	
	/**
	 * 返回微博列表
	 * @return
	 */
	public String list(){
		list = weiboService.getPageList(query);
		for(int i = 0; i < list.size(); i++){
			WeiBoEntity entity = list.get(i);
			if(StringUtil.isEmpty(entity.getAccesstoken())){
				list.get(i).setIstimeout("2");//如果没有accesstoken说明此微博还没有获取过accesstoken
			}else{
				if(list.get(i).getIstimeout().equals("0")){
					String sr = null;
					try {
						sr=HttpRequest.sendPost(
													"https://api.weibo.com/oauth2/get_token_info", 
													"access_token=" + list.get(i).getAccesstoken()
												);//获取此微博accesstoken信息，验证是否失效
						if(StringUtil.isEmpty(sr)){
							list.get(i).setIstimeout("1");//如果返回的是空则说明此微博accesstoken已失效
						}else{
							JSONObject jsonObject = new JSONObject(sr);
							list.get(i).setRemaintime(jsonObject.getString("expire_in"));//不为空获取剩余有效时间，时间为秒
						}
					} catch (Exception e) {
						// TODO: handle exception
						System.out.println(e.toString());
					}
				}else{
					list.get(i).setIstimeout("1");
				}
			}
		}
		return "list";
	}
	
	public String toAdd(){
		op = "add";
		return "edit";
	}
	
	public String toModify(){
		PageList<WeiBoEntity> list = weiboService.getPageList(query);
		model = list.get(0);
		op = "modify";
		return "edit";
	}
	
	public String tingyong(){
		weiboService.tingyong(model);
		this.setSuccessMessage("成功停用！");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String qiyong(){
		PageList<WeiBoEntity> list = weiboService.getPageList(model);
		model = list.get(0);
		if(StringUtil.isEmpty(model.getAccesstoken())){
			this.setErrorMessage("accesst为空，请先授权验证再启动");
			getValueStack().set(DATA, getMessage());
			return DATA;
		}else{
			String sr=HttpRequest.sendPost(
					"https://api.weibo.com/oauth2/get_token_info", 
					"access_token=" + model.getAccesstoken()
				);//获取此微博accesstoken信息，验证是否失效
			if(StringUtil.isEmpty(sr)){
				this.setErrorMessage("accesst已失效，请重新授权验证再启动");
				getValueStack().set(DATA, getMessage());
				return DATA;
			}
		}
		weiboService.qiyong(model);
		this.setSuccessMessage("成功停用！");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	
	
	public String access(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String code = request.getParameter("code");
		if(StringUtil.isEmpty(code)){
			System.out.println("---code  null----start");
			String redirect_URI = request.getRequestURL().toString();
			System.out.println("-------redirect_URI---------" + redirect_URI);
			PageList<WeiBoEntity> list = weiboService.getPageList(model);
			model = list.get(0);
			String url = WeiboConfig.getValue("authorizeURL").trim() + "?client_id="
			+ model.getClient_id() + "&redirect_uri="
			+ redirect_URI.trim()
			+ "&response_type=code" 
			+ "&state="
			+ "&scope=";
			getValueStack().set("client_id", model.getClient_id());
			System.out.println("---client_id----" + model.getClient_id());
			getValueStack().set("url", url);
			System.out.println("---url----" + url);
			entity.setClient_id(model.getClient_id());
			entity.setClient_sercret(model.getClient_sercret());
			System.out.println("---code  null----end");
			return "access";
			//Oauth oauth = new Oauth();
			/*System.out.println("---url---"+url);
			BareBonesBrowserLaunch.openURL(url);*/
		}else{
			System.out.println("---code not null----start");
			try{
				HttpClient client = new HttpClient();
				System.out.println("---entity.getClient_id()----" + entity.getClient_id());
				System.out.println("---entity.getClient_sercret()----" + entity.getClient_sercret());
				String redirect_URI = request.getRequestURL().toString();
				AccessToken token = new AccessToken(client.post(
						WeiboConfig.getValue("accessTokenURL"),
						new PostParameter[] {
								new PostParameter("client_id", entity.getClient_id()),
								new PostParameter("client_secret", entity.getClient_sercret()),
								new PostParameter("grant_type", "authorization_code"),
								new PostParameter("code", code),
								new PostParameter("redirect_uri", redirect_URI) }, false));
				System.out.println("---token----"+token);
				entity.setAccesstoken(token.getAccessToken());
				entity.setIstimeout("0");
				weiboService.updateAccessById(entity);
			} catch (WeiboException e) {
				if(401 == e.getStatusCode()){
					Log.logInfo("Unable to get the access token.");
				}else{
					e.printStackTrace();
				}
			}
			getValueStack().set("redirect_isNot", "1");
			System.out.println("---code not null----end");
			return "list";
		}
	}
	
	public String remove(){
		weiboService.delete(model);
		this.setSuccessMessage("成功删除数据！");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String save() throws Exception{
		if(StringUtil.isEmpty(model.getWbbh()))
			weiboService.insert(model);
		else
			weiboService.update(model);
	    
	    this.setSuccessMessage("成功插入数据！");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public PageList<WeiBoEntity> getList() {
		return list;
	}

	public void setList(PageList<WeiBoEntity> list) {
		this.list = list;
	}

	public WeiBoEntity getModel() {
		return model;
	}

	public void setModel(WeiBoEntity model) {
		this.model = model;
	}

	public IWeiboService getWeiboService() {
		return weiboService;
	}
	public void setWeiboService(IWeiboService weiboService) {
		this.weiboService = weiboService;
	}
	public WeiBoEntity getQuery() {
		return query;
	}
	public void setQuery(WeiBoEntity query) {
		this.query = query;
	}
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}

	
}
