package com.zfsoft.mobile.pushmsg.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.zfsoft.common.system.SubSystemHolder;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.mobile.pushmsg.dao.IPushMsgDao;
import com.zfsoft.mobile.pushmsg.entity.PushMsg;
import com.zfsoft.mobile.pushmsg.entity.PushRegistration;
import com.zfsoft.mobile.pushmsg.entity.PushRegistrationQuery;
import com.zfsoft.mobile.pushmsg.enums.PushObjectTypeEnum;
import com.zfsoft.mobile.pushmsg.query.PushMsgQuery;
import com.zfsoft.mobile.pushmsg.service.IPushMsgService;
import com.zfsoft.mobile.pushmsg.service.IPushRegistrationService;
import com.zfsoft.mobile.support.HPushMsgImpl;
import com.zfsoft.mobile.support.MPushMsgImpl;
import com.zfsoft.mobile.support.PushMsgResult;
import com.zfsoft.mobile.support.PushMsgSendInterface;

public class PushMsgServiceImpl implements IPushMsgService {
	Logger logger = Logger.getLogger(PushMsgServiceImpl.class);

	private IPushMsgDao pushMsgDao;
	private PushMsgSendInterface pushMsgSend;
	private MPushMsgImpl mPushSend = new MPushMsgImpl();
	private HPushMsgImpl hPushSend = new HPushMsgImpl();
	private IPushRegistrationService pushRegistrationService;
	private final static int MAX_SEND_LENGTH = findMaxSendLength();

	private static int findMaxSendLength(){
		String max = SubSystemHolder.getPropertiesValue("max_send_length");
		try {
			return Integer.valueOf(max);
		} catch (Exception e) {
			return 200;
		}
	} 
	@Override
	public void save(PushMsg pushMsg) {
			//发送之前进行处理 如果为群组类型则需要将关系细化到个人
		PushRegistrationQuery query = new PushRegistrationQuery();
		query.setAppType(pushMsg.getAppType());
		List<PushRegistration> registration = new ArrayList<PushRegistration>();
		List<PushRegistration> jgRegistration = new ArrayList<PushRegistration>();//极光
		List<PushRegistration> xmRegistration = new ArrayList<PushRegistration>();//小米
		List<PushRegistration> hwRegistration = new ArrayList<PushRegistration>();//华为
		
		String tsdx="";
		String tsjg="";
		String tsdxlx = pushMsg.getTsdxlx();
		//分组
		if(PushObjectTypeEnum.TAG.getKey().equals(pushMsg.getTsdxlx().toUpperCase())){
			String[] tagArray=pushMsg.getTsdxArray();
			query.setGroupIds(tagArray);
			registration = pushRegistrationService.getPushRegistrationList(query);
			pushMsg.setTsdxlx(PushObjectTypeEnum.REGISTRATION_ID.getKey());
		}
		//特定人
		else if(PushObjectTypeEnum.ALIAS.getKey().equals(pushMsg.getTsdxlx().toUpperCase())){
			String[] userIds=pushMsg.getTsdxArray();
			query.setUserIds(userIds);
			registration = pushRegistrationService.getPushRegistrationList(query);
			pushMsg.setTsdxlx(PushObjectTypeEnum.REGISTRATION_ID.getKey());
		}
		//系统中所有用户
		else if(PushObjectTypeEnum.ALL_USER.getKey().equals(pushMsg.getTsdxlx().toUpperCase())){
			query.setExists(true);
			registration = pushRegistrationService.getPushRegistrationList(query);
			pushMsg.setTsdxlx(PushObjectTypeEnum.REGISTRATION_ID.getKey());
		}
		//所有不在系统中的用户
		else if(PushObjectTypeEnum.ALL_NOT_USER.getKey().equals(pushMsg.getTsdxlx().toUpperCase())){
			query.setExists(false);
			registration = pushRegistrationService.getPushRegistrationList(query);
			pushMsg.setTsdxlx(PushObjectTypeEnum.REGISTRATION_ID.getKey());
		}
		
		//整理推送对象
		Map<String, String> map = new HashMap<String, String>();
		for (PushRegistration pushRegistration : registration) {
			if(map.get(pushRegistration.getUserId())==null){
				tsdx+=pushRegistration.getUserId()+",";
				map.put(pushRegistration.getUserId(), pushRegistration.getUserId());
			}
		}
		
		//对推送用户系统分类，按不同方式推送
		for (int i = 0; i < registration.size(); i++) {
			if("EMUI".equals(registration.get(i).getSbType())){
				hwRegistration.add(registration.get(i));
			}else if("MIUI".equals(registration.get(i).getSbType())){
				xmRegistration.add(registration.get(i));
			}else{
				jgRegistration.add(registration.get(i));
			}
		}
		
		//控制单次发送最大数 超过则进行多次发送
		//极光
		if(registration.size()>0){
			do {
				int length = MAX_SEND_LENGTH>registration.size()?registration.size():MAX_SEND_LENGTH;
				String[] tsdxArray = new String[length];
				for (int i = 0; i < tsdxArray.length; i++) {
					tsdxArray[i]=registration.get(i).getRegistrationId();
				}
				pushMsg.setTsdxArray(tsdxArray);
				PushMsgResult pmr = pushMsgSend.send(pushMsg);//极光推送
				tsjg+=pmr.getResult();
				registration=registration.subList(length, registration.size());
			} while (registration.size()>0);
		}
		
		//小米
		if(xmRegistration.size()>0){
			do {
				int length = MAX_SEND_LENGTH>xmRegistration.size()?xmRegistration.size():MAX_SEND_LENGTH;
				String[] tsdxArray = new String[length];
				for (int i = 0; i < tsdxArray.length; i++) {
					tsdxArray[i]=xmRegistration.get(i).getRegistrationId();
				}
				pushMsg.setTsdxArray(tsdxArray);
				PushMsgResult pmr = mPushSend.send(pushMsg);//小米推送
				tsjg+=pmr.getResult();
				xmRegistration=xmRegistration.subList(length, xmRegistration.size());
			} while (xmRegistration.size()>0);
		}
		
		//华为
		if(hwRegistration.size()>0){
			do {
				int length = MAX_SEND_LENGTH>hwRegistration.size()?hwRegistration.size():MAX_SEND_LENGTH;
				String[] tsdxArray = new String[length];
				for (int i = 0; i < tsdxArray.length; i++) {
					tsdxArray[i]=hwRegistration.get(i).getRegistrationId();
				}
				pushMsg.setTsdxArray(tsdxArray);
				PushMsgResult pmr = hPushSend.sendPushMessage(pushMsg);
				tsjg+=pmr.getResult();
				hwRegistration=hwRegistration.subList(length, hwRegistration.size());
			} while (hwRegistration.size()>0);
		}
		
		//保存至数据库
		pushMsg.setTsdxlx(tsdxlx);
		//pushMsg.setTsdx(tsdx);
		pushMsg.setTsjg(tsjg);
		pushMsgDao.insert(pushMsg);
	}

	public PageList<PushMsg> getPageList(PushMsgQuery query) {
		PageList<PushMsg> pageList = new PageList<PushMsg>();
		Paginator paginator = new Paginator();
		try {
			if (query != null) {
				paginator.setItemsPerPage(query.getPerPageSize());
				paginator.setPage((Integer) query.getToPage());

				paginator.setItems(pushMsgDao.getPagingCount(query));
				pageList.setPaginator(paginator);
				if((Integer)query.getToPage() > paginator.getPages()){
					return pageList;
				}
				if (paginator.getBeginIndex() <= paginator.getItems()) {
					query.setStartRow(paginator.getBeginIndex());
					query.setEndRow(paginator.getEndIndex());
					pageList.addAll(pushMsgDao.getPagingList(query));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageList;
	}

	public PushMsg getEntity(PushMsg entity) throws RuntimeException {
		// TODO Auto-generated method stub
		return pushMsgDao.getEntity(entity);
	}
	
	public IPushMsgDao getPushMsgDao() {
		return pushMsgDao;
	}

	public void setPushMsgDao(IPushMsgDao pushMsgDao) {
		this.pushMsgDao = pushMsgDao;
	}

	public PushMsgSendInterface getPushMsgSend() {
		return pushMsgSend;
	}

	public void setPushMsgSend(PushMsgSendInterface pushMsgSend) {
		this.pushMsgSend = pushMsgSend;
	}
	/**
	 * 设置
	 * @param pushRegistrationService 
	 */
	public void setPushRegistrationService(
			IPushRegistrationService pushRegistrationService) {
		this.pushRegistrationService = pushRegistrationService;
	}
	
}
