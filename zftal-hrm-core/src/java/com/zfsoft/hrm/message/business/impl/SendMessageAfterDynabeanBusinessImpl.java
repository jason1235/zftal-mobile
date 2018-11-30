package com.zfsoft.hrm.message.business.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.Query;

import com.zfsoft.hrm.baseinfo.dyna.business.bizinterface.IDynaBeanModifyAfterBusiness;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.util.DynaBeanUtil;
import com.zfsoft.hrm.message.business.IMessageBusiness;
import com.zfsoft.hrm.message.config.MessageTimerSendConfig;
import com.zfsoft.hrm.message.dao.daointerface.IMessageDao;
import com.zfsoft.hrm.message.dao.daointerface.IMessageStatusDao;
import com.zfsoft.hrm.message.dao.daointerface.MessageTimerSendConfigDao;
import com.zfsoft.hrm.message.service.svcinterface.MessageTimerSendConfigService;

/**
 * 
 * @author ChenMinming
 * @date 2015-6-23
 * @version V1.0.0
 */
public class SendMessageAfterDynabeanBusinessImpl 
	implements IDynaBeanModifyAfterBusiness{
	
	private MessageTimerSendConfigService messageTimerSendConfigService;
	

	@Override
	public void doAfterModify(DynaBean dynaBean) {
		MessageTimerSendConfig config = new MessageTimerSendConfig();
		config.setTablename(dynaBean.getClazz().getIdentityName()+"_log");
		config.setSfjsts(1);
		config.setZt("1");
		List<MessageTimerSendConfig> configList = messageTimerSendConfigService.getList(config);
		if(configList==null||configList.isEmpty()) return;
		DynaBean message = DynaBeanUtil.getPerson(dynaBean.getValueString("gh"));
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = new HashMap<String, Object>();
		for (String k: message.getViewHtml().keySet()) {
			map.put(k.toUpperCase(), dynaBean.getValue(k));
		}
		list.add(map);
		for (MessageTimerSendConfig messageTimerSendConfig : configList) {
			messageTimerSendConfigService.sendMessage(messageTimerSendConfig, list);
		}
		
	}


	/**
	 * 设置
	 * @param messageTimerSendConfigService 
	 */
	public void setMessageTimerSendConfigService(
			MessageTimerSendConfigService messageTimerSendConfigService) {
		this.messageTimerSendConfigService = messageTimerSendConfigService;
	}

}
