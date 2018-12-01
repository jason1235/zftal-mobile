package com.zfsoft.mobile.webservices.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.struts2.json.JSONUtil;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONBuilder;

import com.common.Config;
import com.common.WebServiceConf;
import com.google.gson.Gson;
import com.mh.CXFServe.service.ICaService;
import com.zfsoft.common.system.SubSystemHolder;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.mobile.group.service.IPushGroupService;
import com.zfsoft.mobile.pushmsg.entity.PushMsg;
import com.zfsoft.mobile.pushmsg.entity.PushRegistration;
import com.zfsoft.mobile.pushmsg.query.PushMsgQuery;
import com.zfsoft.mobile.pushmsg.service.IPushMsgService;
import com.zfsoft.mobile.pushmsg.service.IPushRegistrationService;
import com.zfsoft.mobile.webservices.entity.PushListPage;
import com.zfsoft.mobile.webservices.entity.WSPushMsg;
import com.zfsoft.mobile.webservices.service.IWSPushService;
import com.zfsoft.orcus.lang.TimeUtil;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.encrypt.DBEncrypt;
import com.zfsoft.util.encrypt.Encrypt;

/**
 * 
 * @author ChenMinming
 * @date 2015-4-23
 * @version V1.0.0
 */
public class WSPushServiceImpl implements IWSPushService {

	private static String PushEncryptWord = findPushEncryptWord();
	private IPushGroupService pushGroupService;
	private IPushMsgService pushMsgService;
	private IPushRegistrationService pushRegistrationService;
	
	

	@Override
	public String GetPushMsg(String parameterList, int page,
			int pageSize , String sign) {
		System.out.println(Encrypt.encrypt(parameterList+PushEncryptWord));
		if(StringUtil.isEmpty(parameterList)||parameterList.split("&").length!=3){
			return errorInfo("002", "参数错误");
		}
		if(!sign.equals(Encrypt.encrypt(parameterList+PushEncryptWord))){
			return errorInfo("001", "未授权访问");
		};
		
		String[] param = parameterList.split("&");
		PushMsgQuery query = new PushMsgQuery();
		query.setPerPageSize(pageSize);
		query.setToPage(page);
		query.setOrderStr(" tssj desc");
		query.setTsdx(param[0]);
		query.setTsfs(param[1]);
		query.setAppType(param[2]);
		PageList<PushMsg> pageList;
		try{
			pageList = pushMsgService.getPageList(query);
		}catch(Exception e){
			e.printStackTrace();
			return errorInfo("101", "数据查询错误");
			
		}
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><table>");
		for (PushMsg pushMsg : pageList) {
			sb.append("<msg>");
			sb.append("<data><name>推送ID</name><value>"+pushMsg.getTsid()+"</value></data>");
			sb.append("<data><name>推送时间</name><value>"+TimeUtil.format(pushMsg.getTssj(), TimeUtil.yyyy_MM_dd_HH_mm_ss) +"</value></data>");
			sb.append("<data><name>推送内容</name><value>"+pushMsg.getTsnr()+"</value></data>");
			sb.append("<data><name>推送方式</name><value>"+pushMsg.getTsfs()+"</value></data>");
			sb.append("</msg>");
		}
		sb.append("<page>");
		sb.append("<total>"+pageList.getPaginator().getItems()+"</total>");
		sb.append("<totalpage>"+pageList.getPaginator().getPages()+"</totalpage>");
		sb.append("<curentpage>"+pageList.getPaginator().getPage()+"</curentpage>");
		sb.append("</page>");
		sb.append("</table>");
		return sb.toString();
	}

	@Override
	public String GetSendMsg(String parameterList, int page,
			int pageSize , String sign) {
		
		if(StringUtil.isEmpty(parameterList)||parameterList.split("&").length!=3){
			return errorInfo("002", "参数错误");
		}
		if(!sign.equals(Encrypt.encrypt(parameterList+PushEncryptWord))){
			return errorInfo("001", "未授权访问");
		};
		
		String[] param = parameterList.split("&");
		PushMsgQuery query = new PushMsgQuery();
		query.setPerPageSize(pageSize);
		query.setToPage(page);
		query.setOrderStr(" tssj desc");
		query.setTsry(param[0]);
		query.setTsfs(param[1]);
		query.setAppType(param[2]);
		PageList<PushMsg> pageList;
		try{
			pageList = pushMsgService.getPageList(query);
		}catch(Exception e){
			e.printStackTrace();
			return errorInfo("101", "数据查询错误");
			
		}
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><table>");
		for (PushMsg pushMsg : pageList) {
			sb.append("<msg>");
			sb.append("<data><name>推送ID</name><value>"+pushMsg.getTsid()+"</value></data>");
			sb.append("<data><name>推送时间</name><value>"+TimeUtil.format(pushMsg.getTssj(), TimeUtil.yyyy_MM_dd_HH_mm_ss) +"</value></data>");
			sb.append("<data><name>推送内容</name><value>"+pushMsg.getTsnr()+"</value></data>");
			sb.append("<data><name>推送方式</name><value>"+pushMsg.getTsfs()+"</value></data>");
			sb.append("</msg>");
		}
		sb.append("<page>");
		sb.append("<total>"+pageList.getPaginator().getItems()+"</total>");
		sb.append("<totalpage>"+pageList.getPaginator().getPages()+"</totalpage>");
		sb.append("<curentpage>"+pageList.getPaginator().getPage()+"</curentpage>");
		sb.append("</page>");
		sb.append("</table>");
		return sb.toString();
	}

	@Override
	public String SetUserIdAndRegistrationId(String parameterList, String sign) {
		System.out.println(Encrypt.encrypt(parameterList+PushEncryptWord));
		if(StringUtil.isEmpty(parameterList)||parameterList.split("&").length!=3){
			return errorInfo("002", "参数错误");
		}
		if(!sign.equals(Config.getString("strKey", "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS"))){
			return errorInfo("001", "未授权访问");
		};
		String[] param = parameterList.split("&");
		PushRegistration pushRegistration = new PushRegistration();
		pushRegistration.setUserId(param[0]);
		pushRegistration.setRegistrationId(param[1]);
		pushRegistration.setAppType(param[2]);
		try{
			pushRegistrationService.save(pushRegistration);
		}catch(Exception e){
			e.printStackTrace();
			return errorInfo("101", "数据查询错误");
		}
		return errorInfo("000", "正常");
	}

	@Override
	public String Push(String parameterList,String sign){
//		System.out.println(Encrypt.encrypt(parameterList+PushEncryptWord));
		if(!sign.equals(Encrypt.encrypt(parameterList+PushEncryptWord))){
			return errorInfo("001", "未授权访问");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
		};
		WSPushMsg msg =null;
		try{
			JSONObject json = JSONObject.fromObject(parameterList);
			msg = (WSPushMsg)JSONObject.toBean(json, WSPushMsg.class);
		}catch (Exception e) {
			e.printStackTrace();
			return errorInfo("002", "参数错误");
		}
		
		PushMsg pushMsg = new PushMsg();
		pushMsg.setTsbt(StringUtil.isEmpty(msg.getTsbt())?"":msg.getTsbt());
		pushMsg.setTsdxlx(StringUtil.isEmpty(msg.getTsdxlx())?"ALL":msg.getTsdxlx());
		pushMsg.setTsnr(msg.getTsnr());
		pushMsg.setTspt(StringUtil.isEmpty(msg.getTspt())?"ALL":msg.getTspt());
		pushMsg.setTsry(msg.getTsry());
		pushMsg.setTsfs(msg.getTsfs());
		pushMsg.setTsdxArray(msg.getTsdx());
		pushMsg.setExtras(msg.getExtras());
		pushMsg.setAppType(msg.getAppType());
		pushMsg.setTswds(msg.getTswds());
		Gson gson = new Gson();
		//Map<String,String> map = new HashMap<String,String>();
		//map.put("func_type", msg.getExtras().get("func_type"));
		//pushMsg.setExtrasStr(gson.toJson(map));
		pushMsg.setExtrasStr(gson.toJson(msg.getExtras()));
		try{
			pushMsg.setTimeToLive(Long.valueOf(msg.getTimeToLive()));
		}catch (Exception e) {
		}
		try{
			pushMsgService.save(pushMsg);
		}catch(Exception e){
			e.printStackTrace();
			return errorInfo("101", "数据查询错误");
		}
		return errorInfo("000", "正常");
	}
	
	public static void main(String[] args) {
		DBEncrypt tool = new DBEncrypt();
		System.out.println(tool.dCode("jLAwzgEPwVhnneX6mCYkhQ==".getBytes()));
		
		WSPushMsg msg =new WSPushMsg();
		msg.setTimeToLive("86400");
		msg.setTsdx(new String[]{"10024"});
		msg.setTsdxlx("ALIAS");
		msg.setTsnr("教务推送测试");
		msg.setTsfs("1");
		msg.setTspt("ALL");
		msg.setTsry("system");
		msg.setAppType("YDXY");
		msg.setTsbt("hello");
		//Map<String, String> map = new HashMap<String, String>();
		//map.put("xtbm", "jw");
		//map.put("func_type", "306");
		/*try {
			map.put("webURL", java.net.URLEncoder.encode("http://portal.zfsoft.com:8089/login_sso.aspx?procode=002&type=2&choice=JS0101&role=JS", "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		map.put("webNAME", "教务推送测试");
		msg.setExtras(map);*/
		System.out.println(JSONObject.fromObject(msg));
		
		System.out.println(Encrypt.encrypt(JSONObject.fromObject(msg)+"zfsoft_ydxy"));
		
		
		/*JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IWSPushService.class);
		factory.setAddress("http://10.71.33.72:8088/zftal-mobile/service/IWSPushService");//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		
		IWSPushService service = (IWSPushService) factory.create();*/
		
		
		
		//service.Push(JSONObject.fromObject(msg).toString(), Encrypt.encrypt(JSONObject.fromObject(msg)+"zfsoft_ydxy"));
	}

	private static String findPushEncryptWord(){
		try{
		String word = SubSystemHolder.getPropertiesValue("push_encrypt_word");
		DBEncrypt tool = new DBEncrypt();
		return tool.dCode(word.getBytes());
		}catch(Exception e){
			return "";
		}
	} 
	
	/**
	 * 设置
	 * @param pushGroupService 
	 */
	public void setPushGroupService(IPushGroupService pushGroupService) {
		this.pushGroupService = pushGroupService;
	}

	/**
	 * 设置
	 * @param pushMsgService 
	 */
	public void setPushMsgService(IPushMsgService pushMsgService) {
		this.pushMsgService = pushMsgService;
	}

	/**
	 * 设置
	 * @param iPushRegistrationService 
	 */
	public void setPushRegistrationService(
			IPushRegistrationService pushRegistrationService) {
		this.pushRegistrationService = pushRegistrationService;
	}
	
	private String errorInfo(String code,String info){
		return "<?xml version=\"1.0\" encoding=\"utf-8\" ?><ResultInfo><code>"+
		code+"</code><message>"+
		info+"</message></ResultInfo>";
		
	}

	@Override
	public String PushMsgList(String userId, int start, int size) {
		PushMsgQuery query = new PushMsgQuery();
		query.setTsdx(userId);
		query.setPerPageSize(size);
		query.setToPage(start);
		query.setOrderStr(" tssj desc ");
		PageList<PushMsg> pushMsgList = pushMsgService.getPageList(query);
		Gson gson = new Gson();
		return gson.toJson(pushMsgList);
	}

}
