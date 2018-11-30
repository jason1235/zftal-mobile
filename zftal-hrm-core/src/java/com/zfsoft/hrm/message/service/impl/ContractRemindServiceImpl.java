package com.zfsoft.hrm.message.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.message.business.IContractRemindBusiness;
import com.zfsoft.hrm.message.business.IMessageBusiness;
import com.zfsoft.hrm.message.config.ContractRemind;
import com.zfsoft.hrm.message.config.IMessageJobDefine;
import com.zfsoft.hrm.message.entity.Message;
import com.zfsoft.hrm.message.service.svcinterface.IContractRemindService;
import com.zfsoft.hrm.message.service.svcinterface.IMessageJobService;
import com.zfsoft.orcus.lang.TimeUtil;

/** 
 * @author jinjj
 * @date 2012-9-25 下午04:22:57 
 *  
 */
public class ContractRemindServiceImpl implements IContractRemindService,IMessageJobService {

	private IContractRemindBusiness remindBusiness;
	private IMessageBusiness messageBusiness;
	
	@Override
	public void doJob(IMessageJobDefine job) {
		if(!(job instanceof ContractRemind)){
			throw new RuleException("任务信息不匹配");
		}
		ContractRemind remind = (ContractRemind)job;
		int days = remind.getAheadDay();
		String now = TimeUtil.dateX();
		Date endDate = TimeUtil.addDay(now, days);
		List<Map<String,String>> list = remindBusiness.getList(endDate);
		
		processMessage(list,remind,endDate);
	}
	
	private void processMessage(List<Map<String,String>> list,ContractRemind remind, Date endDate){
		if(list.size()==0){
			return;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(TimeUtil.format(endDate, TimeUtil.yyyy_MM_dd));
		sb.append("有"+list.size()+"名员工合同到期<br/>");
		sb.append("人员列表如下:");
		sb.append("[code]");
		for(Map<String,String> map:list){
			sb.append(map.get("GH"));
			sb.append(";");
		}
		sb.append("[:code]");
		Message message = new Message();
		message.setSender(remind.getSender());
		message.setReceiver(remind.getReceiver());
		message.setContent(sb.toString());
		message.setTitle(remind.getJobDescription());
		message.setSendTime(new Date());
		messageBusiness.save(message);
	}

	public void setRemindBusiness(IContractRemindBusiness remindBusiness) {
		this.remindBusiness = remindBusiness;
	}

	public void setMessageBusiness(IMessageBusiness messageBusiness) {
		this.messageBusiness = messageBusiness;
	}

}
