package com.oa.service.imp;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import javax.activation.DataHandler;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.common.Config;
import com.common.WebServiceConf;
import com.newmobile.login.CXFService.IWSSerServicePortType;
import com.newmobile.login.service.IWSSerService;
import com.oa.service.DownLoadBean;
import com.oa.service.FileModel;
import com.oa.service.IEmailInformationXMLService;
import com.oa.service.IOaMobileService;
import com.util.SelectItems;
import com.util.WebServiceUtil;
import com.util.encode.XmltoString;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.CodeUtil;
import com.zfsoft.util.base.StringUtil;

/**
 * <p>
 * Description: OA邮件接口
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * <p>
 * Company: zfsoft.com
 * </p>
 *
 * @since Oct 22, 2012 1:50:55 PM
 * @author huangzhaoxia
 * @version 1.0
 */
@Service
@Component(value = "emailInformationXMLService")
public class EmailInformationXMLService implements IEmailInformationXMLService {
	private static Logger logger = Logger.getLogger(EmailInformationXMLService.class);
	private final String infromation=Config.getString("mobile.infromation");


	/**
	 * 根据【是否阅办】获取待办列表
	 * @param yhm 用户名
	 * @param sfyb 是否阅办 (0 否 1 是)
	 * @param start
	 * @param size
	 * @param sign
	 * @return
	 */
	@Override
	public String getTodoTaskListBySfyb(String yhm, String sfyb, String start,
			String size, String sign,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			sfyb  		= CodeUtil.decode(sfyb, apptoken);
			start  		= CodeUtil.decode(start, apptoken);
			size  		= CodeUtil.decode(size, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);

		} catch (Exception e) {
			e.printStackTrace();
		}
		String str=null;
		str=WebServiceUtil.createServiceOa().getTodoTaskListBySfyb(yhm,sfyb,
				Integer.valueOf(start), Integer.valueOf(size), sign);
		if(infromation.equals("0")){
			logger.error("调用getTodoTaskListBySfyb根据【是否阅办】获取待办列表："+"yhm="+yhm+"sfyb="+sfyb+"start="+start+"size="+size+"sign="+sign);
			logger.error("调用getTodoTaskListBySfyb根据【是否阅办】获取待办列表返回为："+str);
		}
		return str;
	}


	/**
	 * 邮件收件箱列表
	 */
	@Override
	public String getMailListByType(String yhm, String type, String start,
			String size, String sign,String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			type  		= CodeUtil.decode(type, apptoken);
			start  		= CodeUtil.decode(start, apptoken);
			size  		= CodeUtil.decode(size, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		String str=null;
		str=WebServiceUtil.createServiceOa().getMailListByType(yhm, type,
				Integer.valueOf(start), Integer.valueOf(size), sign);
		if(infromation.equals("0")){
			logger.error("调用getMailListByType邮件收件箱列表："+"yhm="+yhm+"type="+type+"start="+start+"size="+size+"sign="+sign);
			logger.error("调用getMailListByType邮件收件箱列表返回为："+str);
		}
		return str;
	}

	/**
	 * 邮件收件箱列表
	 */
	@Override
	public String getMailListByTypeAndCond(String yhm, String type, String start,
			String size, String sign,String cond, String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			type  		= CodeUtil.decode(type, apptoken);
			start  		= CodeUtil.decode(start, apptoken);
			size  		= CodeUtil.decode(size, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);
			cond  		= CodeUtil.decode(cond, apptoken);

		} catch (Exception e) {
			e.printStackTrace();
		}


		String str=null;
		str=WebServiceUtil.createServiceOa().getMailListByTypeAndCond(yhm, type,
				Integer.valueOf(start), Integer.valueOf(size),sign, cond);
		if(infromation.equals("0")){
			logger.error("调用getMailListByType邮件收件箱列表筛选条件："+"yhm="+yhm+"type="+type+"start="+start+"size="+size+"sign="+sign);
			logger.error("调用getMailListByType邮件收件箱列表筛选条件返回为："+str);
		}
		return str;
	}



	/**
	 * 获取邮件详情
	 */
	@Override
	public String getMailInfoById(String yhm, String eid, String sign,String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			eid  		= CodeUtil.decode(eid, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		String str=null;
		str=WebServiceUtil.createServiceOa().getMailInfoById(yhm, eid, sign);
		if(infromation.equals("0")){
			logger.error("调用getMailInfoById获取邮件详情："+"yhm="+yhm+"eid="+eid+"sign="+sign);
			logger.error("调用getMailInfoById获取邮件详情返回为："+str);
		}
		return str;
	}
	@Override
	public String checkPassword(String yhm,String flowPassword,String sign,String apptoken){
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  			= CodeUtil.decode(yhm, apptoken);
			flowPassword  	= CodeUtil.decode(flowPassword, apptoken);
			sign  			= CodeUtil.decode(sign, apptoken);

		} catch (Exception e) {
			e.printStackTrace();
		}


		String str=null;
		str=WebServiceUtil.createServiceOa().checkPassword(yhm, flowPassword, sign);
		if(infromation.equals("0")){
			logger.error("判断用户签名口令方法参数："+"yhm="+yhm+"flowPassword="+flowPassword+"sign="+sign);
			logger.error("判断用户签名口令方法返回为："+str);
		}
		return str;
	}

	/**
	 * 流程任务执行
	 */
	@Override
	public String excuTaskForFlow(String yhm, String id,String taskId, String sign,String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			id  		= CodeUtil.decode(id, apptoken);
			taskId  	= CodeUtil.decode(taskId, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);

		} catch (Exception e) {
			e.printStackTrace();
		}


		String str=null;
		str=WebServiceUtil.createServiceOa().excuTaskForFlow(yhm, id, taskId, sign);
		if(infromation.equals("0")){
			logger.error("oa流程任务执行参数："+"yhm="+yhm+"id="+id+"taskId="+taskId+"sign="+sign);
			logger.error("oa流程任务执行返回为："+str);
		}
		return str;
	}

	/**
	 * 删除邮件
	 */
	@Override
	public String deleteMailByID(String yhm, String eid, String type,
			String sign,String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			eid  		= CodeUtil.decode(eid, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);
			type  		= CodeUtil.decode(type, apptoken);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		String str=null;
		str=WebServiceUtil.createServiceOa().deleteMailByID(yhm, eid, type,
				sign);
		if(infromation.equals("0")){
			logger.error("调用deleteMailByID删除邮件："+"yhm="+yhm+"eid="+eid+"typ="+type);
			logger.error("调用deleteMailByID删除邮件返回为："+ str);
		}
		return str;
	}

	/**
	 * 标记已读
	 */
	@Override
	public String updateMailByID(String yhm, String eid, String sign,String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			eid  		= CodeUtil.decode(eid, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		String str=null;
		str=WebServiceUtil.createServiceOa().updateMailByID(yhm, eid, sign);
		if(infromation.equals("0")){
			logger.error("调用updateMailByID标记已读："+"yhm="+yhm+"eid="+eid+"sign="+sign);
			logger.error("调用updateMailByID标记已读返回为："+str);
		}
		return str;
	}

	/**
	 * 对oa过来的信息进行xml解析判断
	 * @throws DocumentException
	 */
	public boolean containErrorCode(String xml) throws DocumentException{
		Document document = DocumentHelper.parseText(xml);
        Element elementTemplate = document.getRootElement();
        Element ResultInfo = (Element)elementTemplate.selectSingleNode("//ResultInfo");
        Element code = (Element)elementTemplate.selectSingleNode("//code");

		if(ResultInfo != null && code != null){
			String errorCode = elementTemplate.elementText("code");
			if(errorCode != null && errorCode.equals("201"))
				return true;
		}

		return false;
	}

	/**
	 * 写信、回复、全部回复、转发
	 */
	@Override
	public String sendMail(String yjid, String yhm, String fsrxm, String sjrxm,
			String sjrdm, String csrxm, String csrdm, String title,
			String content, String type, String sign,String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken)) {
			return "app_token error";
		}
		try {
			yjid  		= CodeUtil.decode(yjid, apptoken);
			yhm  		= CodeUtil.decode(yhm, apptoken);
			fsrxm  		= CodeUtil.decode(fsrxm, apptoken);
			sjrxm  		= CodeUtil.decode(sjrxm, apptoken);
			sjrdm  		= CodeUtil.decode(sjrdm, apptoken);
			csrxm  		= CodeUtil.decode(csrxm, apptoken);
			csrdm  		= CodeUtil.decode(csrdm, apptoken);
			title  		= CodeUtil.decode(title, apptoken);
			content  	= CodeUtil.decode(content, apptoken);
			type  		= CodeUtil.decode(type, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		String str=null;
		str=WebServiceUtil.createServiceOa().sendMail(yjid, yhm, fsrxm,
				sjrxm, sjrdm, csrxm, csrdm, title, content, type, sign);
		if(infromation.equals("0")){
			logger.error("调用sendMail写信、回复、全部回复、转发："+"yjid="+yjid+"yhm="+yhm+"fsrxm="+fsrxm+"sjrxm="+sjrxm+"sjrdm="+sjrdm+"csrxm="+csrxm
					+"csrdm="+csrdm+"title="+title+"content="+content+"type="+type+"sign="+sign);
			logger.error("调用sendMail写信、回复、全部回复、转发返回为："+str);
		}

		/*try {
			if(containErrorCode(str)){
				WSPushMsg msg =new WSPushMsg();
				msg.setTimeToLive("86400");
				String[]  sjrArr = sjrdm.split(",");
				for (int i = 0; i < sjrArr.length; i++) {
					sjrArr[i] = sjrArr[i].substring(sjrArr[i].indexOf("-")+1, sjrArr[i].length());
					logger.error("send mails for users,users are ："+sjrArr[i]);
				}
				msg.setTsdx(sjrArr);
				//msg.setTsdx(new String[]{"1053"});
				msg.setTsdxlx("ALIAS");
				msg.setTsnr(title);
				msg.setTsfs("1");
				msg.setTspt("ALL");
				msg.setTsry("system");
			    Map<String, String> map = new HashMap<String, String>();
			    map.put("func_type", "302");
			    msg.setExtras(map);

				String appType = SubSystemHolder.getPropertiesValue("appType");
				appType = StringUtil.isEmpty(appType) ? "YDXY" : appType;
				msg.setAppType(appType);
				logger.error("sendMail Iwspushservice,infomationis , appType :" + msg.getAppType()+",content:"+ msg.getTsnr());
				//msg.setExtras(new HashMap<String, String>());

				JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
				factory.setServiceClass(IWSPushServicePortType.class);
				factory.setAddress(WebServiceConf.SERVICE_PUSHMOBILESERVICE);//接口地址
				factory.getInInterceptors().add(new LoggingInInterceptor());
				factory.getOutInterceptors().add(new LoggingOutInterceptor());

				IWSPushServicePortType service = (IWSPushServicePortType) factory.create();

				String str2 =service.push(JSONObject.fromObject(msg).toString(), Encrypt.encrypt(JSONObject.fromObject(msg)+"zfsoft_ydxy"));


			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}*/



		return str;
	}

	/**
	 * 获取收件人、抄送人根结构
	 */
	@Override
	public String getFirstDepInfo(String yhm, String sign,String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		String str=null;
		str=WebServiceUtil.createServiceOa().getFirstDepInfo(yhm, sign);
		if(infromation.equals("0")){
			logger.error("调用getFirstDepInfo获取收件人、抄送人根结构："+"yhm="+yhm+"sign="+sign);
			logger.error("调用getFirstDepInfo获取收件人、抄送人根结构返回为："+str);
		}
		return str;
	}

	/**
	 * 获取子级收件人、抄送人根结构
	 */
	@Override
	public String getDepAndUserByDepNum(String yhm, String depnum,
			String depname, String sum, String sign,String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			depnum  	= CodeUtil.decode(depnum, apptoken);
			depname  	= CodeUtil.decode(depname, apptoken);
			sum  		= CodeUtil.decode(sum, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		String str=null;
		str=WebServiceUtil.createServiceOa().getDepAndUserByDepNum(yhm,
				depnum, depname, sum, sign);
		if(infromation.equals("0")){
			logger.error("调用getDepAndUserByDepNum获取子级收件人、抄送人根结构："+"yhm="+yhm+"depnum="+depnum+"depname="+depname+"sum="+sum+"sign="+sign);
			logger.error("调用getDepAndUserByDepNum获取子级收件人、抄送人根结构返回为："+str);
		}
		return str;
	}

	/**
	 * 搜索联系人
	 */
	@Override
	public String getDepAndUserInfoForSearch(String yhm, String name,
			String sign,String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			name  		= CodeUtil.decode(name, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String str=null;
		str=WebServiceUtil.createServiceOa().getDepAndUserInfoForSearch(yhm,
				name, sign);
		if(infromation.equals("0")){
			logger.error("调用getDepAndUserInfoForSearch搜索联系人："+"yhm="+yhm+"name="+name+"sign="+sign);
			logger.error("调用getDepAndUserInfoForSearch搜索联系人返回为："+str);
		}
		return str;
	}

	/**
	 * 获取新邮件消息
	 */
	@Override
	public String getNewMailCount(String yhm, String sign,String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		String str=null;
		str =WebServiceUtil.createServiceOa().getNewMailCount(yhm, sign);
		if(infromation.equals("0")){
			logger.error("调用getNewMailCount获取新邮件消息"+"yhm="+yhm+"sign="+sign);
			logger.error("调用getNewMailCount获取新邮件消息返回为："+str);
		}
		return str;
	}

	/**
	 * 保存到草稿箱
	 */
	@Override
	public String saveToDraft(String yjid, String yhm, String fsrxm,
			String sjrxm, String sjrdm, String csrxm, String csrdm,
			String title, String content, String sign,String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yjid  		= CodeUtil.decode(yjid, apptoken);
			yhm  		= CodeUtil.decode(yhm, apptoken);
			fsrxm  		= CodeUtil.decode(fsrxm, apptoken);
			sjrxm  		= CodeUtil.decode(sjrxm, apptoken);
			sjrdm  		= CodeUtil.decode(sjrdm, apptoken);
			csrxm  		= CodeUtil.decode(csrxm, apptoken);
			csrdm  		= CodeUtil.decode(csrdm, apptoken);
			title  		= CodeUtil.decode(title, apptoken);
			content  	= CodeUtil.decode(content, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		String str=null;
		str=WebServiceUtil.createServiceOa().saveToDraft(yjid, yhm, fsrxm,
				sjrxm, sjrdm, csrxm, csrdm, title, content, sign);
		if(infromation.equals("0")){
			logger.error("调用saveToDraft保存到草稿箱："+"yjid="+yjid+"yhm="+yhm+"fsrxm="+fsrxm+"sjrxm="+sjrxm+"sjrdm="+sjrdm+"csrxm="+csrxm
					+"csrdm="+csrdm+"title="+title+"content="+content+"sign="+sign);
			logger.error("调用saveToDraft保存到草稿箱返回为："+str);
		}
		return str;
	}

	/**
	 * 获取草稿邮件
	 */
	@Override
	public String getDraftInfoById(String yhm, String yjid, String sign,String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			yjid  		= CodeUtil.decode(yjid, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		String str=null;
		str=WebServiceUtil.createServiceOa().getDraftInfoById(yhm, yjid,
				sign);
		if(infromation.equals("0")){
			logger.error("调用getDraftInfoById获取草稿邮件："+"yhm="+yhm+"yjid="+yjid+"sign="+sign);
			logger.error("调用getDraftInfoById获取草稿邮件返回为："+str);
		}
		return str;
	}

	/**
	 * 获取通讯录详情
	 */
	@Override
	public String getAddressInfo(String yhm, String yhid, String sign,String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			yhid  		= CodeUtil.decode(yhid, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		String str=null;
		str=WebServiceUtil.createServiceOa().getAddressInfo(yhm, yhid, sign);
		if(infromation.equals("0")){
			logger.error("调用getAddressInfo获取通讯录详情："+"yhm="+yhm+"yjid="+yhid+"sign="+sign);
			logger.error("调用getAddressInfo获取通讯录详情返回为："+str);
		}
		return str;
	}

	/**
	 * 待办事宜列表
	 */
	@Override
	public String getTodoTaskList(String yhm, String start, String size,
			String sign,String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			start  		= CodeUtil.decode(start, apptoken);
			size  		= CodeUtil.decode(size, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		String str=null;
		str=WebServiceUtil.createServiceOa().getTodoTaskList(yhm,
				Integer.valueOf(start), Integer.valueOf(size), sign);
		if(infromation.equals("0")){
			logger.error("调用getTodoTaskList待办事宜列表："+"yhm="+yhm+"start="+start+"size="+size+"sign="+sign);
			logger.error("调用getTodoTaskList待办事宜列表返回为："+str);
		}
		return str;
	}

	/**
	 * 添加办公日程
	 */
	@Override
	public String addSchedule(String yhm, String zt, String date,
			String starttime, String endtime, String content, String sign,String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			zt  		= CodeUtil.decode(zt, apptoken);
			date  		= CodeUtil.decode(date, apptoken);
			starttime  	= CodeUtil.decode(starttime, apptoken);
			endtime  	= CodeUtil.decode(endtime, apptoken);
			content  	= CodeUtil.decode(content, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		String str=null;
		str=WebServiceUtil.createServiceOa().addSchedule(yhm, zt, date,
				starttime, endtime, content, sign);
		if(infromation.equals("0")){
			logger.error("调用getTodoTaskList添加办公日程："+"yhm="+yhm+"zt="+zt+"date="+date+"starttime="+starttime+"endtime="+endtime
					+"content="+content+"sign="+sign);
			logger.error("调用getTodoTaskList添加办公日程返回为："+str);
		}
		return str;
	}

	/**
	 * 办公日程共享人列表详情
	 */
	@Override
	public String getSharePerson(String yhm, String sign,String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		String str=null;
		str=WebServiceUtil.createServiceOa().getSharePerson(yhm, sign);
		if(infromation.equals("0")){
			logger.error("调用getSharePerson办公日程共享人列表详情："+"yhm="+yhm+"sign="+sign);
			logger.error("调用getSharePerson办公日程共享人列表详情返回为："+str);
		}
		return str;
	}

	/**
	 * 通知公告列表获取
	 */
	@Override
	public String getNoticeList(String yhm, String type,String start, String size,
			String sign,String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			type  		= CodeUtil.decode(type, apptoken);
			start  		= CodeUtil.decode(start, apptoken);
			size  		= CodeUtil.decode(size, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		if(start.equals("0")){
			start="1";
		}
		String str=null;
		String isXiBei = Config.getString("isxibei");
		if(!StringUtil.isEmpty(isXiBei) && isXiBei.equals("yes")){
			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
			factory.setServiceClass(IWSSerServicePortType.class);
			factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
			factory.getInInterceptors().add(new LoggingInInterceptor());
			factory.getOutInterceptors().add(new LoggingOutInterceptor());

			IWSSerServicePortType service = (IWSSerServicePortType) factory.create();

			str =service.getNoticeList(yhm, type, start, size);
		}else{
			str=WebServiceUtil.createServiceOa().getNoticeList(yhm,type,
					Integer.valueOf(start), Integer.valueOf(size), sign);
		}

		if(infromation.equals("0")){
			logger.error("调用getNoticeList通知公告列表获取："+"yhm="+yhm+"type="+type+"start="+start+"size="+size+"sign="+sign);
			logger.error("调用getNoticeList通知公告列表获取返回为："+str);
		}
		return str;
	}
	/**
	 * 获取通讯录列表
	 */
	@Override
	public String getAddressList(String yhm, String updatetime, String sign,String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			updatetime  = CodeUtil.decode(updatetime, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		String str=null;
		str=WebServiceUtil.createServiceOa().getAddressList(yhm, updatetime,
				sign);
		if(infromation.equals("0")){
			logger.error("调用getAddressList获取通讯录列表："+"yhm="+yhm+"updatetime="+updatetime+"sign="+sign);
			logger.error("调用getAddressList获取通讯录列表返回为："+str);
		}
		return str;
	}

	/**
	 * 删除办公日程
	 */
	@Override
	public String deleteSchedule(String yhm, String id, String sign,String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			id  		= CodeUtil.decode(id, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		String str=null;
		str=WebServiceUtil.createServiceOa().deleteSchedule(yhm, id, sign);
		if(infromation.equals("0")){
			logger.error("调用deleteSchedule删除办公日程："+"yhm="+yhm+"id="+id+"sign="+sign);
			logger.error("调用deleteSchedule删除办公日程返回为："+str);
		}
		return str;
	}

	/**
	 * 办公日程列表详情
	 */
	@Override
	public String getScheduleList(String yhm, String yhid, String start,
			String size, String sign,String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			yhid  		= CodeUtil.decode(yhid, apptoken);
			start  		= CodeUtil.decode(start, apptoken);
			size  		= CodeUtil.decode(size, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		String str=null;
		str=WebServiceUtil.createServiceOa().getScheduleList(yhm, yhid,
				Integer.valueOf(start), Integer.valueOf(size), sign);
		if(infromation.equals("0")){
			logger.error("调用getScheduleList办公日程列表详情："+"yhm="+yhm+"yhid="+yhid+"start="+start+"size="+size+"sign="+sign);
			logger.error("调用getScheduleList办公日程列表详情返回为："+str);
		}
		return str;
	}
	/**
	 * 办公日程详情
	 */
	@Override
	public String getScheduleInfo(String yhm, String id, String sign,String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			id  		= CodeUtil.decode(id, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		String str=null;
		str=WebServiceUtil.createServiceOa().getScheduleInfo(yhm, id, sign);
		if(infromation.equals("0")){
			logger.error("调用getScheduleInfo办公日程详情："+"yhm="+yhm+"id="+id+"sign="+sign);
			logger.error("调用getScheduleInfo办公日程详情返回为："+str);
		}
		return str;
	}

	/**
	 * 我的会议、全体会议列表获取
	 */
	@Override
	public String getNewConferenceList(String yhm, String type, String start,
			String size, String sign,String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			type  		= CodeUtil.decode(type, apptoken);
			start  		= CodeUtil.decode(start, apptoken);
			size  		= CodeUtil.decode(size, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		String str=null;
		str =WebServiceUtil.createServiceOa().getNewConferenceList(yhm, type,
				Integer.valueOf(start), Integer.valueOf(size), sign);
		if(infromation.equals("0")){
			logger.error("调用getNewConferenceList我的会议、全体会议列表获取："+"yhm="+yhm+"type="+type+"start="+start+"size="+size+"sign="+sign);
			logger.error("调用getNewConferenceList我的会议、全体会议列表获取返回为："+str);
		}
		return str;
	}
	/**
	 * 获取我的会议、全体会议详情
	 */
	@Override
	public String getConferenceInfo(String yhm, String id, String sign,String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			id  		= CodeUtil.decode(id, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		String str=null;
		str=WebServiceUtil.createServiceOa()
		.getConferenceInfo(yhm, id, sign);
		if(infromation.equals("0")){
			logger.error("调用getConferenceInfo获取我的会议、全体会议详情"+"yhm="+yhm+"id="+id+"sign="+sign);
			logger.error("调用getConferenceInfo获取我的会议、全体会议详情返回为："+str);
		}
		return str;
	}

	/**
	 * 通知公告详情
	 */
	@Override
	public String getNoticeInfo(String yhm, String id, String sign,String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			id  		= CodeUtil.decode(id, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		String str=null;
		String isXiBei = Config.getString("isxibei");
		if(!StringUtil.isEmpty(isXiBei) && isXiBei.equals("yes")){
			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
			factory.setServiceClass(IWSSerServicePortType.class);
			factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
			factory.getInInterceptors().add(new LoggingInInterceptor());
			factory.getOutInterceptors().add(new LoggingOutInterceptor());

			IWSSerServicePortType service = (IWSSerServicePortType) factory.create();

			str =service.getNoticeInfo(id);
		}else{
			str=WebServiceUtil.createServiceOa().getNoticeInfo(yhm, id, sign);

		}

		if(infromation.equals("0")){
			logger.error("调用getConferenceInfo通知公告详情："+"yhm="+yhm+"id="+id+"sign="+sign);
			logger.error("调用getConferenceInfo通知公告详情返回为："+str);
		}
		return str;
	}
	@Override
	public String getSignKey(String key,String apptoken) {
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			key  		= CodeUtil.decode(key, apptoken);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		String str=null;
		str =WebServiceUtil.createServiceOa().getSignKey(key);
		if(infromation.equals("0")){
			logger.error("调用getSignKey："+"key="+key);
			logger.error("调用getSignKey返回为："+str);
		}
		return str;
	}

	/**
	 * OA登入
	 */
	@Override
	public String checkLogin(String yhm, String sign,String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		String str=null;
		str=WebServiceUtil.createServiceOa().checkLogin(yhm, sign);
		if(infromation.equals("0")){
			logger.error("调用checkLoginOA登入："+"yhm="+yhm+"sign="+sign);
			logger.error("调用checkLoginOA登入返回为："+str);
		}
		return str;
	}

	/**
	 * OA附件下载
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getFileModel(String id,String apptoken) {
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			id  		= CodeUtil.decode(id, apptoken);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		String zfxml = "";
		File f = new File(this.getClass().getResource("/").getPath());
		String url = f.getPath().split("WEB-INF")[0] + "upload//";
		DownLoadBean bean = new DownLoadBean();
		try {
			// 调用WebService
			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
			factory.setServiceClass(IOaMobileService.class);
			factory.setAddress(WebServiceConf.SERVICE_OASERVICE);// 接口地址

			IOaMobileService service = (IOaMobileService) factory.create();
			FileModel file = service.getFileModel(id);
			if (file != null) {
				DataHandler dataHandler = file.getDataHandler();
				InputStream is = dataHandler.getInputStream();
				FileOutputStream fos = new FileOutputStream(url + id);// 附件下载存放地址
				// 存放流
				byte[] bytes = new byte[2048];
				int len = 0;
				while ((len = is.read(bytes)) != -1) {
					fos.write(bytes, 0, len);
				}
				fos.flush();
				fos.close();
				is.close();
				// 将信息存入BEAN 组合成XML
				bean.setAdjunctId(id);
				//bean.setDownLoadURL(WebServiceConf.DOWNLOADURL + id);
				bean.setDownLoadURL(Config.getString("suploadPath") + "upload/" + id);
				bean.setFileName(file.getName());

				String[] output = new String[] { "fileName", "adjunctId",
						"downLoadURL" };// 需要输出的字段
				@SuppressWarnings("rawtypes")
				List indexlist = new ArrayList();
				indexlist.add(bean);
				zfxml = XmltoString.xmlToStringNew(output, SelectItems
						.getReflectObjPropertyValue(indexlist,
								DownLoadBean.class, output), "DOWNLOAD");
				file = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return zfxml;
	}

	/**
	 * 待办事宜提交
	 */
	@Override
	public String doSubmitFlow(String yhm, String id, String nextid,
			String nextuser, String comment, String sign,String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			id  		= CodeUtil.decode(id, apptoken);
			nextid  	= CodeUtil.decode(nextid, apptoken);
			nextuser  	= CodeUtil.decode(nextuser, apptoken);
			comment  	= CodeUtil.decode(comment, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		String str=null;
		str=WebServiceUtil.createServiceOa().doSubmitFlow(yhm, id, nextid,
				nextuser, comment, sign);
		if(infromation.equals("0")){
			logger.error("调用doSubmitFlow待办事宜提交："+"yhm="+yhm+"id="+id+"nextid="+nextid+"nextuser="+nextuser+"comment="+comment+"sign="+sign);
			logger.error("调用doSubmitFlow待办事宜提交返回为："+str);
		}
		return str;
	}

	/**
	 * 待办事宜详情
	 */
	@Override
	public String getTableInfo(String yhm, String id, String tablename,
			String sign,String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			id  		= CodeUtil.decode(id, apptoken);
			tablename  	= CodeUtil.decode(tablename, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		String str=null;
		str =WebServiceUtil.createServiceOa().getTableInfo(yhm, id,
				tablename, sign);
		if(infromation.equals("0")){
			logger.error("调用getTableInfo待办事宜详情："+"yhm="+yhm+"id="+id+"tablename="+tablename+"sign="+sign);
			logger.error("调用getTableInfo待办事宜详情返回为："+str);
		}
		return str;
	}

	/**
	 * 待办事宜提交认证
	 */
	@Override
	public String doSubmitBefore(String yhm, String id, String sign,String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			id  		= CodeUtil.decode(id, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		String str=null;
		str=WebServiceUtil.createServiceOa().doSubmitBefore(yhm, id, sign);
		if(infromation.equals("0")){
			logger.error("调用doSubmitBefore待办事宜提交认证："+"yhm="+yhm+"id="+id+"sign="+sign);
			logger.error("调用doSubmitBefore待办事宜提交认证返回为："+str);
		}
		return str;
	}

	/**
	 * 待办事宜提交新方法
	 */
	public String doSubmitFlowNew(String yhm, String id, String nextid,
			String nextuser, String comment, String tablename, String ftzd,
			String ftfs, String zid, String sign,String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			id  		= CodeUtil.decode(id, apptoken);
			nextid  	= CodeUtil.decode(nextid, apptoken);
			nextuser  	= CodeUtil.decode(nextuser, apptoken);
			comment  	= CodeUtil.decode(comment, apptoken);
			tablename  	= CodeUtil.decode(tablename, apptoken);
			ftzd  		= CodeUtil.decode(ftzd, apptoken);
			ftfs  		= CodeUtil.decode(ftfs, apptoken);
			zid  		= CodeUtil.decode(zid, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		String str=null;
		str=WebServiceUtil.createServiceOa().doSubmitFlowone(yhm, id,
				nextid, nextuser, comment, tablename, ftzd, ftfs, zid, sign);
		if(infromation.equals("0")){
			logger.error("调用doSubmitFlowNew待办事宜提交新方法："+"yhm="+yhm+"id="+id+"nextid="+nextid+"nextuser="+nextuser+"comment="+comment
					+"tablename="+tablename+"ftzd="+ftzd+"ftfs="+ftfs+"zid="+zid+"sign="+sign);
			logger.error("调用doSubmitFlowNew待办事宜提交新方法返回为："+str);
		}

		/*try {
			if(containErrorCode(str) && !StringUtil.isEmpty(nextuser)){
				WSPushMsg msg =new WSPushMsg();
				msg.setTimeToLive("86400");
				String[]  users = nextuser.split(",");
				msg.setTsdx(users);
				//msg.setTsdx(new String[]{"1053"});
				msg.setTsdxlx("ALIAS");
				msg.setTsnr("您有的新的待办，请及时处理");
				msg.setTsfs("1");
				msg.setTspt("ALL");
				msg.setTsry("system");
				String appType = SubSystemHolder.getPropertiesValue("appType");
				appType = StringUtil.isEmpty(appType) ? "YDXY" : appType;
				msg.setAppType(appType);
				logger.error("sendMail Iwspushservice,infomationis , appType :" + msg.getAppType()+",content:"+ msg.getTsnr());
				//msg.setExtras(new HashMap<String, String>());
				Map<String, String> map = new HashMap<String, String>();
			    map.put("func_type", "306");
			    msg.setExtras(map);

				JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
				factory.setServiceClass(IWSPushServicePortType.class);
				factory.setAddress(WebServiceConf.SERVICE_PUSHMOBILESERVICE);//接口地址
				factory.getInInterceptors().add(new LoggingInInterceptor());
				factory.getOutInterceptors().add(new LoggingOutInterceptor());

				IWSPushServicePortType service = (IWSPushServicePortType) factory.create();

				String str2 =service.push(JSONObject.fromObject(msg).toString(), Encrypt.encrypt(JSONObject.fromObject(msg)+"zfsoft_ydxy"));


			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}*/

		return str;
	}

	/**
	 * 获取待办事宜详情
	 * @param yhm
	 * @param id
	 * @param sign
	 * @return
	 */
	public String getFlowInfo(String yhm, String id, String sign,String apptoken){
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			id  		= CodeUtil.decode(id, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		String str=null;
		str=WebServiceUtil.createServiceOa().getFlowInfo(yhm, id, sign);

		if(infromation.equals("0")){
			logger.error("调用getFlowInfo获取待办事宜详情："+"yhm="+yhm+"id="+id+"sign="+sign);
			logger.error("调用getFlowInfo获取待办事宜详情返回为："+str);
		}
		return str;
	}

	/**
	 * <p>Description: 获取用户权限</p>
	 * @param yhm  用户名
	 * @param sign 密钥
	 * @return
	 *
	 * @since 2014-8-21 上午09:36:42
	 * @author yangz
	 */
	@Override
	public String getSendInfo(String yhm, String sign,String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		// TODO 获取用户权限
		String str=null;
		str=WebServiceUtil.createServiceOa().getSendInfo(yhm, sign);
		if(infromation.equals("0")){
			logger.error("调用doSubmitBefore获取待办事宜详情："+"yhm="+yhm+"sign="+sign);
			logger.error("调用doSubmitBefore获取待办事宜详情返回为："+str);
		}
		return str;
	}

	/**
	 * <p>Description: 公告类别方法</p>
	 * @param yhm  用户名
	 * @param sign 密钥
	 * @return
	 *
	 * @since 2014-8-21 上午11:39:39
	 * @author yangz
	 */
	@Override
	public String getNoticType(String yhm, String sign,String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		// TODO 公告类别
		String str=null;
		str=WebServiceUtil.createServiceOa().getNoticType(yhm, sign);
		if(infromation.equals("0")){
			logger.error("调用doSubmitBefore获取待办事宜详情："+"yhm="+yhm+"sign="+sign);
			logger.error("调用doSubmitBefore获取待办事宜详情返回为："+str);
		}
		return str;
	}

	/**
	 * <p>Description: 检测待办事宜是否可以退回方法</p>
	 * @param yhm      用户名
	 * @param id       id(和流程提交方法中的ID是同一个)
	 * @param sign     秘钥
	 * @return
	 *
	 * @since 2014-12-16 下午02:27:37
	 * @author yangz
	 */
	@Override
	public String getDoBackBefore(String yhm, String id, String sign,String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			id  		= CodeUtil.decode(id, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		// TODO 检测待办事宜是否可以退回
		String str=null;
		str=WebServiceUtil.createServiceOa().doBackBefore(yhm, id, sign);
		if(infromation.equals("0")){
			logger.error("调用getDoBackBefore检测代办事宜是否可以退回方法："+"yhm="+yhm+"sign="+sign);
			logger.error("调用getDoBackBefore检测代办事宜是否可以退回方法为："+str);
		}
		return str;
	}

	/**
	 * <p>Description: 待办事宜回退</p>
	 * @param yhm      用户名
	 * @param id       id
	 * @param comment  意见
	 * @param sign     秘钥
	 * @return
	 *
	 * @since 2014-12-16 下午02:35:29
	 * @author yangz
	 */
	@Override
	public String getdoBackFlow(String yhm, String id, String comment,
			String sign,String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			id  		= CodeUtil.decode(id, apptoken);
			comment  	= CodeUtil.decode(comment, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO 待办事宜回退
		String str=null;
		str=WebServiceUtil.createServiceOa().doBackFlow(yhm, id, comment, sign);
		if(infromation.equals("0")){
			logger.error("调用getdoBackFlow待办事宜回退方法："+"yhm="+yhm+"sign="+sign);
			logger.error("调用getdoBackFlow待办事宜回退为："+str);
		}
		return str;
	}

	/**
	 * <p>Description: 会议通知列表</p>
	 * @param yhm
	 * @param start
	 * @param size
	 * @param sign
	 * @return
	 *
	 * @since 2015-2-2 下午04:39:57
	 * @author yangz
	 */
	@Override
	public String getMeetNoticeList(String yhm, String start, String size,
			String sign,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			start  		= CodeUtil.decode(start, apptoken);
			size  	= CodeUtil.decode(size, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		String str=null;
		int start1=Integer.valueOf(start);
		int size1=Integer.valueOf(size);
		str=WebServiceUtil.createServiceOa().getMeetNoticeList(yhm, start1, size1, sign);
		if(infromation.equals("0")){
			logger.error("调用getMeetNoticeList会议通知列表方法："+"yhm="+yhm+"sign="+sign);
			logger.error("调用getMeetNoticeList会议通知列表返回为："+str);
		}
		return str;
	}

	/**
	 * <p>Description: 会议通知详情</p>
	 * @param yhm
	 * @param id
	 * @param sign
	 * @return
	 *
	 * @since 2015-2-2 下午04:46:55
	 * @author yangz
	 */
	@Override
	public String getMeetNoticeInfo(String yhm, String id, String sign,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			id  		= CodeUtil.decode(id, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		String str=null;
		str=WebServiceUtil.createServiceOa().getMeetNoticeInfo(yhm, id, sign);
		if(infromation.equals("0")){
			logger.error("调用getMeetNoticeInfo会议通知详情："+"yhm="+yhm+"sign="+sign);
			logger.error("调用getMeetNoticeInfo会议通知详情为："+str);
		}
		return str;
	}

	/**
	 * <p>Description: 获取领导常用审批意见</p>
	 * @param yhm
	 * @param sign
	 * @return
	 *
	 * @since 2015-6-16 上午10:41:53
	 * @author yangz
	 */
	@Override
	public String getLdcyyjbList(String yhm, String sign,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		String str=null;
		str=WebServiceUtil.createServiceOa().getLdcyyjbList(yhm,sign);
		if(infromation.equals("0")){
			logger.error("调用getLdcyyjbList获取领导常用审批意见："+"yhm="+yhm+"sign="+sign);
			logger.error("调用getLdcyyjbList获取领导常用审批意见为："+str);
		}
		return str;
	}

	/**
	 * <p>Description: 获取已办事宜列表</p>
	 * @param yhm
	 * @param start
	 * @param size
	 * @param sign
	 * @return
	 *
	 * @since 2015-8-25 上午10:52:53
	 * @author yangz
	 */
	@Override
	public String getDoneTaskList(String yhm, String start, String size,
			String sign,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			start  		= CodeUtil.decode(start, apptoken);
			size  		= CodeUtil.decode(size, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		String str=null;
		str=WebServiceUtil.createServiceOa().getDoneTaskList(yhm,
				Integer.valueOf(start), Integer.valueOf(size), sign);
		if(infromation.equals("0")){
			logger.error("调用getDoneTaskList获取已办事宜列表："+"yhm="+yhm+"start="+start+"size="+size+"sign="+sign);
			logger.error("调用getDoneTaskList获取已办事宜列表返回为："+str);
		}
		return str;
	}

	/**
	 * <p>Description: 获取办结事宜列表</p>
	 * @param yhm
	 * @param start
	 * @param size
	 * @param sign
	 * @return
	 */
	@Override
	public String getSoluteTaskList(String yhm, String start, String size,
			String sign,String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			start  		= CodeUtil.decode(start, apptoken);
			size  		= CodeUtil.decode(size, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		String str=null;
		str=WebServiceUtil.createServiceOa().getSoluteTaskList(yhm,
				Integer.valueOf(start), Integer.valueOf(size), sign);
		if(infromation.equals("0")){
			logger.error("调用getSoluteTaskList获取办结事宜列表："+"yhm="+yhm+"start="+start+"size="+size+"sign="+sign);
			logger.error("调用getSoluteTaskList获取办结事宜列表返回为："+str);
		}
		return str;
	}


	@Override
	public String updateMailXbByID(String yhm, String id, String type,
			String sign, String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			id  		= CodeUtil.decode(id, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);
			type  		= CodeUtil.decode(type, apptoken);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		String str=null;
		str=WebServiceUtil.createServiceOa().updateMailXbByID(yhm, id, type,
				sign);
		if(infromation.equals("0")){
			logger.error("updateMailXbByID更新邮件星标状态："+"yhm="+yhm+"eid="+id+"typ="+type);
			logger.error("updateMailXbByID更新邮件星标状态："+ str);
		}
		return str;
	}


	@Override
	public String getLclxList(String yhm, String sign, String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);

		} catch (Exception e) {
			e.printStackTrace();
		}
		String str=null;
		str=WebServiceUtil.createServiceOa().getLclxList(yhm,  sign);
		if(infromation.equals("0")){
			logger.error("获取流程类型列表 :"+"yhm="+yhm+"sign="+sign);
			logger.error("获取流程类型列表返回为："+str);
		}
		return str;
	}


	@Override
	public String getSoluteTaskListByConditionAndLx(String yhm,
			String condition, String lxid, String start, String size,
			String sign, String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			condition  	= CodeUtil.decode(condition, apptoken);
			lxid  		= CodeUtil.decode(lxid, apptoken);
			start  		= CodeUtil.decode(start, apptoken);
			size  		= CodeUtil.decode(size, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);

		} catch (Exception e) {
			e.printStackTrace();
		}
		String str=null;
		str=WebServiceUtil.createServiceOa().getSoluteTaskListByConditionAndLx(
				yhm, condition, lxid, Integer.valueOf(start), Integer.valueOf(size), sign
				);
		if(infromation.equals("0")){
			logger.error("根据【待办事宜名和流程类型ID】获取办结事宜列表："+"yhm="+yhm+"condition="+condition
					+"lxid="+lxid+"start="+start+"size="+size+"sign="+sign);
			logger.error("根据【待办事宜名和流程类型ID】获取办结事宜列表返回为："+str);
		}
		return str;
	}


	@Override
	public String getDoneTaskListByConditionAndLx(String yhm, String condition,
			String lxid, String start, String size, String sign, String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			condition  	= CodeUtil.decode(condition, apptoken);
			lxid  		= CodeUtil.decode(lxid, apptoken);
			start  		= CodeUtil.decode(start, apptoken);
			size  		= CodeUtil.decode(size, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);

		} catch (Exception e) {
			e.printStackTrace();
		}
		String str=null;
		str=WebServiceUtil.createServiceOa().getDoneTaskListByConditionAndLx(
				yhm, condition, lxid, Integer.valueOf(start), Integer.valueOf(size), sign
				);
		if(infromation.equals("0")){
			logger.error("根据【待办事宜名和流程类型ID】获取已办事宜列表："+"yhm="+yhm+"condition="+condition
					+"lxid="+lxid+"start="+start+"size="+size+"sign="+sign);
			logger.error("根据【待办事宜名和流程类型ID】获取已办事宜列表返回为："+str);
		}
		return str;
	}


	@Override
	public String getTodoTaskListByConditionAndLx(String yhm, String condition,
			String lxid, String start, String size, String sign, String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			condition  	= CodeUtil.decode(condition, apptoken);
			lxid  		= CodeUtil.decode(lxid, apptoken);
			start  		= CodeUtil.decode(start, apptoken);
			size  		= CodeUtil.decode(size, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);

		} catch (Exception e) {
			e.printStackTrace();
		}
		String str=null;
		str=WebServiceUtil.createServiceOa().getTodoTaskListByConditionAndLx(
				yhm, condition, lxid, Integer.valueOf(start), Integer.valueOf(size), sign
				);
		if(infromation.equals("0")){
			logger.error("根据【待办事宜名和流程类型ID】获取待办列表："+"yhm="+yhm+"condition="+condition
					+"lxid="+lxid+"start="+start+"size="+size+"sign="+sign);
			logger.error("根据【待办事宜名和流程类型ID】获取待办列表返回为："+str);
		}
		return str;
	}


	/* (non-Javadoc)
	 * @see com.oa.service.IEmailInformationXMLService#saveGzrzb(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String saveGzrzb(String yhm, String rznr, String rq, String sign, String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			rznr  		= CodeUtil.decode(rznr, apptoken);
			rq  		= CodeUtil.decode(rq, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);

		} catch (Exception e) {
			e.printStackTrace();
		}
		if(infromation.equals("0")){
			logger.error("移动端新增工作日志："+"yhm="+yhm+",rznr="+rznr+",rq="+rq+",sign="+sign);
		}
		String result = "";
		// 调用WebService

		result=WebServiceUtil.createServiceOa().saveGzrzb(yhm, rznr, rq, sign);
		if(infromation.equals("0")){
			logger.error("移动端新增工作日志返回为："+result);
		}
		return result;
	}


	/* (non-Javadoc)
	 * @see com.oa.service.IEmailInformationXMLService#getGzrzList(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String getGzrzList(String yhm, String start, String size, String sign, String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			start  		= CodeUtil.decode(start, apptoken);
			size  		= CodeUtil.decode(size, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);

		} catch (Exception e) {
			e.printStackTrace();
		}
		if(infromation.equals("0")){
			logger.error("移动端获取工作日志列表："+"yhm="+yhm+",start="+start+",size="+size+",sign="+sign);
		}
		String result = "";
		// 调用WebService
		result=WebServiceUtil.createServiceOa().getGzrzList(yhm, Integer.valueOf(start), Integer.valueOf(size), sign);
		if(infromation.equals("0")){
			logger.error("移动端获取工作日志列表返回为："+result);
		}
		return result;
	}


	public String getMessageText(String xml){
		Document document = null;
		try {
			document = DocumentHelper.parseText(xml);
		} catch (DocumentException e) {
			logger.error("移动端ldap认证及ＯＡ认证： err:");
			logger.error(e, e.fillInStackTrace());
		}
        Element elementTemplate = document.getRootElement();
        Element ResultInfo = (Element)elementTemplate.selectSingleNode("//ResultInfo");
        Element code = (Element)elementTemplate.selectSingleNode("//code");
        Element message = (Element)elementTemplate.selectSingleNode("//message");

		if(ResultInfo != null && code != null){
			String errorCode = elementTemplate.elementText("code");
			if(errorCode != null && !errorCode.equals("") && errorCode.equals("201")){
				return elementTemplate.elementText("message");
			}else
				return null;
		}
		return null;

	}


	/* (non-Javadoc)
	 * @see com.oa.service.IEmailInformationXMLService#getLdapGh(java.lang.String, java.lang.String)
	 */
	@Override
	public String getLdapGh(String email, String pw) {
		if(infromation.equals("0")){
			logger.error("移动端ldap认证及ＯＡ认证："+"email="+email+",pw="+pw);
		}
		String result = "";
		// 调用WebService
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IOaMobileService.class);
		factory.setAddress(WebServiceConf.SERVICE_OASERVICE);// 接口地址

		IOaMobileService service = (IOaMobileService) factory.create();
		result = service.getLdapGh(email, pw);
		if(infromation.equals("0")){
			logger.error("移动端ldap认证及ＯＡ认证返回为："+result);
		}

		String username = getMessageText(result);

		if(!StringUtil.isEmpty(username)){
			if(infromation.equals("0")){
				logger.error("获取登陆接口：");
			}


			JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();
	        bean.setServiceClass(IWSSerService.class);
	        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);
	        IWSSerService helloWorldService = (IWSSerService)bean.create();
	        result = helloWorldService.login(username, pw, "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS",null);

			if(infromation.equals("0")){
			logger.error("登陆接口返回为："+result);
			}

//			JaxWsProxyFactoryBean factory1 = new JaxWsProxyFactoryBean();
//			factory1.setServiceClass(IWSSerServicePortType.class);
//			factory1.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//			factory1.getInInterceptors().add(new LoggingInInterceptor());
//			factory1.getOutInterceptors().add(new LoggingOutInterceptor());
//
//			IWSSerServicePortType service1 = (IWSSerServicePortType) factory1.create();
//
//			result =service1.login(username, pw, "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS");
//
//			if(infromation.equals("0")){
//				logger.error("登陆接口返回为："+result);
//			}

		}
		return result;
	}


	/* (non-Javadoc)
	 * @see com.oa.service.IEmailInformationXMLService#getLdhyhdInfo(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String getLdhyhdInfo(String yhm, String id,String sign, String apptoken) {

		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			id  		= CodeUtil.decode(id, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);

		} catch (Exception e) {
			e.printStackTrace();
		}
		String str=null;
		str=WebServiceUtil.createServiceOa().getLdhyhdInfo(yhm,id,sign);
		if(infromation.equals("0")){
			logger.error("调用getLdhyhdList获取领导会议安排列表："+"yhm="+yhm+"id="+id);
			logger.error("调用getLdhyhdList获取领导会议安排列表："+str);
		}
		return str;
	}


	/* (non-Javadoc)
	 * @see com.oa.service.IEmailInformationXMLService#getLdhyhdList(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String getLdhyhdList(String yhm, String kssj, String jssj,
			String sign, String apptoken) {

		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			kssj  		= CodeUtil.decode(kssj, apptoken);
			jssj  		= CodeUtil.decode(jssj, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);

		} catch (Exception e) {
			e.printStackTrace();
		}
		String str=null;
		str=WebServiceUtil.createServiceOa().getLdhyhdList(yhm,
				kssj, jssj, sign);
		if(infromation.equals("0")){
			logger.error("调用getLdhyhdList获取领导会议安排列表："+"yhm="+yhm+"kssj="+kssj+"jssj"+jssj+"sign="+sign);
			logger.error("调用getLdhyhdList获取领导会议安排列表："+str);
		}
		return str;

	}

	@Override
	public String confirmWjcy(String yhm,String id,String nr,String sign,String apptoken){
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm = CodeUtil.decode(yhm,apptoken);
			id = CodeUtil.decode(id,apptoken);
			if(nr==null||"".equals(nr)){
				nr = "内容为空！";
			}else{
				nr = CodeUtil.decode(nr,apptoken);
			}
			sign = CodeUtil.decode(sign,apptoken);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*OaMobileServiceImplService mxs = new OaMobileServiceImplService();
		IOaMobileService ims =  mxs.getOaMobileServiceImplPort();
		String result = ims.confirmWjcy(yhm, id, nr, sign);
		return result;*/

		String str=null;
		str=WebServiceUtil.createServiceOa().confirmWjcy(yhm, id, nr, sign);
		if(infromation.equals("0")){
			logger.error("文件传阅确认："+"yhm="+yhm+"id="+id+"sign="+sign);
			logger.error("文件传阅确认："+str);
		}
		return str;
	}


	/* (non-Javadoc)
	 * @see com.oa.service.IEmailInformationXMLService#getWjcySjxSlById(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String getWjcySjxSlById(String yhm, String sign, String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);

		} catch (Exception e) {
			e.printStackTrace();
		}
		String str=null;
		str=WebServiceUtil.createServiceOa().getWjcySjxSlById(yhm,sign);
		if(infromation.equals("0")){
			logger.error("1.3.11.7	获取文件传阅收件箱数量："+"yhm="+yhm+"sign="+sign);
			logger.error("1.3.11.7	获取文件传阅收件箱数量："+str);
		}
		return str;
	}


	/* (non-Javadoc)
	 * @see com.oa.service.IEmailInformationXMLService#getWjcySjxList(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String getWjcySjxList(String yhm, String start, String size,
			String sign, String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			start  		= CodeUtil.decode(start, apptoken);
			size  		= CodeUtil.decode(size, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);

		} catch (Exception e) {
			e.printStackTrace();
		}
		String str=null;
		str=WebServiceUtil.createServiceOa().getWjcySjxList(yhm, Integer.valueOf(start), Integer.valueOf(size),sign);
		if(infromation.equals("0")){
			logger.error("1.3.11.1	获取文件传阅收件箱列表："+"yhm="+yhm+"sign="+sign);
			logger.error("1.3.11.1	获取文件传阅收件箱列表:"+str);
		}
		return str;
	}


	/* (non-Javadoc)
	 * @see com.oa.service.IEmailInformationXMLService#getWjcySjxInfoById(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String getWjcySjxInfoById(String yhm, String eid, String sign,
			String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			eid  		= CodeUtil.decode(eid, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);

		} catch (Exception e) {
			e.printStackTrace();
		}
		String str=null;
		str=WebServiceUtil.createServiceOa().getWjcySjxInfoById(yhm, eid, sign);
		if(infromation.equals("0")){
			logger.error("1.3.11.4	根据文件id获取文件传阅收件箱详情："+"yhm="+yhm+"eid="+eid+"sign="+sign);
			logger.error("1.3.11.4	根据文件id获取文件传阅收件箱详情:"+str);
		}
		return str;
	}


	/* (non-Javadoc)
	 * @see com.oa.service.IEmailInformationXMLService#getWjcyFjxList(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String getWjcyFjxList(String yhm, String start, String size,
			String sign, String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			start  		= CodeUtil.decode(start, apptoken);
			size  		= CodeUtil.decode(size, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);

		} catch (Exception e) {
			e.printStackTrace();
		}
		String str=null;
		str=WebServiceUtil.createServiceOa().getWjcyFjxList(yhm, Integer.valueOf(start), Integer.valueOf(size), sign);
		if(infromation.equals("0")){
			logger.error("1.3.11.2	获取文件传阅发件箱列表："+"yhm="+yhm+"start="+start+"size="+size+"sign="+sign);
			logger.error("1.3.11.2	获取文件传阅发件箱列表:"+str);
		}
		return str;
	}


	/* (non-Javadoc)
	 * @see com.oa.service.IEmailInformationXMLService#getWjcyFjxInfoById(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String getWjcyFjxInfoById(String yhm, String eid, String sign,
			String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			eid  		= CodeUtil.decode(eid, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);

		} catch (Exception e) {
			e.printStackTrace();
		}
		String str=null;
		str=WebServiceUtil.createServiceOa().getWjcyFjxInfoById(yhm, eid, sign);
		if(infromation.equals("0")){
			logger.error("1.3.11.5	根据文件id获取文件传阅发件箱详情："+"yhm="+yhm+"eid="+eid+"sign="+sign);
			logger.error("1.3.11.5	根据文件id获取文件传阅发件箱详情:"+str);
		}
		return str;
	}


	/* (non-Javadoc)
	 * @see com.oa.service.IEmailInformationXMLService#updateWjcyZt(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String updateWjcyZt(String yhm, String id, String sign,
			String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			id  		= CodeUtil.decode(id, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);

		} catch (Exception e) {
			e.printStackTrace();
		}
		String str=null;
		str=WebServiceUtil.createServiceOa().updateWjcyZt(yhm, id, sign);
		if(infromation.equals("0")){
			logger.error("1.3.11.8	修改文件传阅为已读updateWjcyZt："+"yhm="+yhm+"id="+id+"sign="+sign);
			logger.error("1.3.11.8	修改文件传阅为已读updateWjcyZt:"+str);
		}
		return str;
	}

	public static void main(String[] args) {
		String result = "";
		// 调用WebService
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IOaMobileService.class);
		factory.setAddress(WebServiceConf.SERVICE_OASERVICE);// 接口地址

		IOaMobileService service = (IOaMobileService) factory.create();
		result = service.getLdapGh("8448", "0");
		System.out.println(result);
	}


	@Override
	public String getZfNewFj(String yhm, String id, String sign, String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			id  		= CodeUtil.decode(id, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);

		} catch (Exception e) {
			e.printStackTrace();
		}
		String str=null;
		str=WebServiceUtil.createServiceOa().getZfNewFj(yhm, id, sign);
		if(infromation.equals("0")){
			logger.error("1.3.11.10	根据收件id获取原发件并封装新的发件getZfNewFj："+"yhm="+yhm+"id="+id+"sign="+sign);
			logger.error("1.3.11.10	根据收件id获取原发件并封装新的发件getZfNewFj:"+str);
		}
		return str;
	}


	@Override
	public String saveWjcyZf(String yhm, String id, String zfyj, String zfdx,
			String yxqx, String sign, String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			id  		= CodeUtil.decode(id, apptoken);
			zfyj  		= CodeUtil.decode(zfyj, apptoken);
			zfdx  		= CodeUtil.decode(zfdx, apptoken);
			yxqx  		= CodeUtil.decode(yxqx, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);

		} catch (Exception e) {
			e.printStackTrace();
		}
		String str=null;
		str=WebServiceUtil.createServiceOa().saveWjcyZf(yhm, id,zfyj,zfdx,yxqx,sign);
		if(infromation.equals("0")){
			logger.error("1.3.11.11	确认转发文件传阅saveWjcyZf："+"yhm="+yhm+"id="+id+"sign="+sign);
			logger.error("1.3.11.11	确认转发文件传阅saveWjcyZf:"+str);
		}
		return str;
	}


	/**
	 * <p>Description:确认会议是否出席</p>
	 * @param yhm 当前登录的用户名
	 * @param id 此getConferenceNoticeList接口的id
	 * @param conferenceid 此getConferenceNoticeList的conferenceid
	 * @param reason 原因（不出席时，原因必填）
	 * @param ispresent 只能填y或n（y表示出席；n表示不出席）
	 * @param time 当前时间（格式2018-04-10 10:18）
	 * @param sign 公钥
	 * @param apptoken
	 * @return
	 */
	@Override
	public String saveConferenceInformfeedback(String yhm,String id,String conferenceid,String reason,String ispresent,
			String time,String sign,String apptoken){
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			id  		= CodeUtil.decode(id, apptoken);
			conferenceid= CodeUtil.decode(conferenceid, apptoken);
			reason  	= CodeUtil.decode(reason, apptoken);
			ispresent  	= CodeUtil.decode(ispresent, apptoken);
			time  	    = CodeUtil.decode(time, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String str=null;
		str=WebServiceUtil.createServiceOa().saveConferenceInformfeedback(id, yhm, conferenceid, reason, ispresent, time, sign);
		if(infromation.equals("0")){
			logger.error("1.3.5.4	确认会议是否出席saveConferenceInformfeedback："+"yhm="+yhm+"id="+id+"sign="+sign);
			logger.error("1.3.5.4	确认会议是否出席saveConferenceInformfeedback:"+str);
		}
		return str;
	}

	/**
	 * <p>Description:获取会议通知列表</p>
	 * @param yhm 当前登录的用户名
	 * @param type 0：未确认；1已回复
	 * @param start 页码
	 * @param size 每页数量
	 * @param sign 公钥
	 * @param apptoken
	 * @return
	 */
	@Override
	public String getConferenceNoticeList(String yhm,String type,String start,
			String size,String sign,String apptoken){
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			type  		= CodeUtil.decode(type, apptoken);
			start  		= CodeUtil.decode(start, apptoken);
			size  		= CodeUtil.decode(size, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String str=null;
		str=WebServiceUtil.createServiceOa().getConferenceNoticeList(yhm, type, Integer.parseInt(start), Integer.parseInt(size), sign);
		if(infromation.equals("0")){
			logger.error("1.3.5.3	获取会议通知列表getConferenceNoticeList："+"yhm="+yhm+"sign="+sign);
			logger.error("1.3.5.3	获取会议通知列表getConferenceNoticeList:"+str);
		}
		return str;
	}

	/**
	 * <p>Description:附件上传</p>
	 * @param yhm 当前登录的用户名(加密)
	 * @param FileModel 文件域  (不加密)
	 * @param sign 公钥 (加密)
	 * @param apptoken
	 * @return
	 */
	@Override
	public String fjUploadByType(String yhm,FileModel file,String sign,String apptoken){
		/*if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		/*try {
			InputStream fis = file.getDataHandler().getInputStream();
			FileOutputStream fos = new FileOutputStream("D:/3333.docx");
			byte[] buf = new byte[1024];
			int len = 0;
			while((len=fis.read(buf) )>=0){
			  fos.write(buf, 0, len);
			}
			fis.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}*/


		String str=null;
		str = WebServiceUtil.createServiceOa().fjUploadByType(yhm, "", file.getName(), "mail", file, sign);
		if(infromation.equals("0")){
			logger.error("1.1.1.1	附件上传fjUploadByType："+"yhm="+yhm+"sign="+sign);
			logger.error("1.1.1.1	附件上传fjUploadByType:"+str);
		}
		return str;
	}


	@Override
	public String getNoReadMail(String yhm, String start,
			String size, String sign, String cond, String apptoken) {

		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			start  		= CodeUtil.decode(start, apptoken);
			size  		= CodeUtil.decode(size, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);
			cond  		= CodeUtil.decode(cond, apptoken);

		} catch (Exception e) {
			e.printStackTrace();
		}


		String str=null;
		str=WebServiceUtil.createServiceOa().getMailListByTypeAndCond(yhm, "4",
				Integer.valueOf(start), Integer.valueOf(size),sign, cond);
		if(infromation.equals("0")){
			logger.error("调用getMailListByType邮件收件箱列表筛选条件："+"yhm="+yhm+"type=4"+"start="+start+"size="+size+"sign="+sign);
			logger.error("调用getMailListByType邮件收件箱列表筛选条件返回为："+str);
		}
		return str;
	}


	@Override
	public String doBackBeforeByReturnNode(String yhm, String id, String sign, String apptoken) {

		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			id  		= CodeUtil.decode(id, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);
		} catch (Exception e) {
			e.printStackTrace();
		}


		String str=null;
		str=WebServiceUtil.createServiceOa().doBackBeforeByReturnNode(yhm, id, sign);
		if(infromation.equals("0")){
			logger.error("调用doBackBeforeByReturnNode流程自由退回操作前判断："+"yhm="+yhm+"id="+id);
			logger.error("调用doBackBeforeByReturnNode流程自由退回操作前判断返回为："+str);
		}
		return str;
	}


	@Override
	public String doBackFlowByReturnNode(String yhm, String id,
			String returnNode, String returnSfsfh, String comment, String sign, String apptoken) {

		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			id  		= CodeUtil.decode(id, apptoken);
			returnNode  = CodeUtil.decode(returnNode, apptoken);
			returnSfsfh = CodeUtil.decode(returnSfsfh, apptoken);
			comment  	= CodeUtil.decode(comment, apptoken);
			sign  	    = CodeUtil.decode(sign, apptoken);
		} catch (Exception e) {
			e.printStackTrace();
		}


		String str=null;
		str=WebServiceUtil.createServiceOa().doBackFlowByReturnNode(yhm, id, returnNode, returnSfsfh, comment, sign);
		if(infromation.equals("0")){
			logger.error("调用doBackFlowByReturnNode流程自由退回操作："+"yhm="+yhm+"id="+id+"returnNode="+returnNode+"returnSfsfh="+returnSfsfh+"comment="+comment+"sign="+sign);
			logger.error("调用doBackFlowByReturnNode流程自由退回操作返回为："+str);
		}
		return str;
	}


	@Override
	public String getCurrentSectionFiled(String yhm, String id,
			String tableName, String zid, String sign, String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			id  		= CodeUtil.decode(id, apptoken);
			tableName  = CodeUtil.decode(tableName, apptoken);
			zid = CodeUtil.decode(zid, apptoken);
			sign  	    = CodeUtil.decode(sign, apptoken);
		} catch (Exception e) {
			e.printStackTrace();
		}


		String str=null;
		str=WebServiceUtil.createServiceOa().getCurrentSectionFiled(yhm, id, tableName, zid, sign);
		if(infromation.equals("0")){
			logger.error("调用getCurrentSectionFiled获取当前环节可编辑的字段列表及对应值操作："+"yhm="+yhm+"id="+id+"tableName="+tableName+"zid="+zid+"sign="+sign);
			logger.error("调用getCurrentSectionFiled获取当前环节可编辑的字段列表及对应值操作返回为："+str);
		}
		return str;
	}


	@Override
	public String updateCurrentSectionFiled(String yhm, String id,
			String tableName, String paramJson, String sign, String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";

		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			id  		= CodeUtil.decode(id, apptoken);
			tableName  = CodeUtil.decode(tableName, apptoken);
			paramJson = CodeUtil.decode(paramJson, apptoken);
			sign  	    = CodeUtil.decode(sign, apptoken);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String str=null;
		str=WebServiceUtil.createServiceOa().updateCurrentSectionFiled(yhm, id, tableName, paramJson, sign);
		if(infromation.equals("0")){
			logger.error("调用updateCurrentSectionFiled更新业务表数据操作："+"yhm="+yhm+"id="+id+"tableName="+tableName+"paramJson="+paramJson+"sign="+sign);
			logger.error("调用updateCurrentSectionFiled更新业务表数据操作返回为："+str);
		}
		return str;
	}

	/*
	@Override
	public String doSubmitFlowoneNew(String yhm, String id, String nextid,
			String nextuser, String comment, String tablename, String ftzd,
			String ftfs, String zid, String sign, String sffsdx,String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";

		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			id  		= CodeUtil.decode(id, apptoken);
			nextid  = CodeUtil.decode(nextid, apptoken);
			nextuser = CodeUtil.decode(nextuser, apptoken);
			comment  	    = CodeUtil.decode(comment, apptoken);
			tablename  	    = CodeUtil.decode(tablename, apptoken);
			ftzd  	    = CodeUtil.decode(ftzd, apptoken);
			ftfs  	    = CodeUtil.decode(ftfs, apptoken);
			zid  	    = CodeUtil.decode(zid, apptoken);
			sign  	    = CodeUtil.decode(sign, apptoken);
			sffsdx  	    = CodeUtil.decode(sffsdx, apptoken);

		} catch (Exception e) {
			e.printStackTrace();
		}

		String str=null;
		str=WebServiceUtil.createServiceOa().doSubmitFlowoneNew(yhm, id, nextid, nextuser, comment, tablename, ftzd, ftfs, zid, sign, sffsdx);
		if(infromation.equals("0")){
			logger.error("调用doSubmitFlowoneNew更新业务表数据操作返回为："+str);
		}
		return str;
	}

*/
	@Override
	public String getConferenceNoticeListAndCond(String yhm, String type,
			String check, String start, String size, String sign,
			String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";

		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			type  		= CodeUtil.decode(type, apptoken);
			check  = CodeUtil.decode(check, apptoken);
			start = CodeUtil.decode(start, apptoken);
			size  	    = CodeUtil.decode(size, apptoken);
			sign  	    = CodeUtil.decode(sign, apptoken);

		} catch (Exception e) {
			e.printStackTrace();
		}

		String str=null;
		str=WebServiceUtil.createServiceOa().getConferenceNoticeListAndCond(yhm, type, check, Integer.valueOf(start), Integer.valueOf(size), sign);
		if(infromation.equals("0")){
			logger.error("调用getConferenceNoticeListAndCond更新业务表数据操作返回为："+str);
		}
		return str;
	}


	@Override
	public String getNoticeListAndCond(String yhm, String type, String check,
			String start, String size, String sign, String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";

		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			type  		= CodeUtil.decode(type, apptoken);
			check  = CodeUtil.decode(check, apptoken);
			start = CodeUtil.decode(start, apptoken);
			size  	    = CodeUtil.decode(size, apptoken);
			sign  	    = CodeUtil.decode(sign, apptoken);

		} catch (Exception e) {
			e.printStackTrace();
		}

		String str=null;
		str=WebServiceUtil.createServiceOa().getNoticeListAndCond(yhm, type, check, Integer.valueOf(start), Integer.valueOf(size), sign);
		if(infromation.equals("0")){
			logger.error("调用getNoticeListAndCond更新业务表数据操作返回为："+str);
		}
		return str;
	}


	@Override
	public String getFfwjList(String yhm, String type, String start,
			String size, String sign, String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";

		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			type  		= CodeUtil.decode(type, apptoken);
			start = CodeUtil.decode(start, apptoken);
			size  	    = CodeUtil.decode(size, apptoken);
			sign  	    = CodeUtil.decode(sign, apptoken);

		} catch (Exception e) {
			e.printStackTrace();
		}

		String str=null;
		str=WebServiceUtil.createServiceOa().getFfwjList(yhm, type, Integer.valueOf(start), Integer.valueOf(size), sign);
		if(infromation.equals("0")){
			logger.error("调用getFfwjList获取收发文分发文件列表操作返回为："+str);
		}
		return str;
	}


	@Override
	public String getZxgwInfoById(String yhm, String id, String type, String sign,
			String apptoken) {
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";

		try {
			yhm  		= CodeUtil.decode(yhm, apptoken);
			id  		= CodeUtil.decode(id, apptoken);
			type  		= CodeUtil.decode(type, apptoken);
			sign  	    = CodeUtil.decode(sign, apptoken);

		} catch (Exception e) {
			e.printStackTrace();
		}

		String str=null;
		str=WebServiceUtil.createServiceOa().getZxgwInfoById(yhm, id, type, sign);
		if(infromation.equals("0")){
			logger.error("调用getZxgwInfoById根据公文id取得用户通最新公文操作返回为："+str);
		}
		return str;
	}
}
