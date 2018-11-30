package com.zfsoft.hrm.staffturn.retire.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.util.DynaBeanUtil;
import com.zfsoft.hrm.message.business.IMessageBusiness;
import com.zfsoft.hrm.message.entity.Message;
import com.zfsoft.hrm.staffturn.retire.dao.daointerface.IRetireScanDao;
import com.zfsoft.hrm.staffturn.retire.entities.RetireAlgorithm;
import com.zfsoft.hrm.staffturn.retire.entities.RetireInfo;
import com.zfsoft.hrm.staffturn.retire.entities.RetireRule;
import com.zfsoft.hrm.staffturn.retire.entities.RetireScheduleTypeEnum;
import com.zfsoft.hrm.staffturn.retire.service.svcinterface.IRetireScanService;
import com.zfsoft.hrm.staffturn.retire.service.svcinterface.IRetireService;
import com.zfsoft.hrm.staffturn.retire.service.svcinterface.RetireRuleService;

/** 
 * 退休检测service实现
 * @author jinjj
 * @date 2012-7-31 上午03:26:31 
 *  
 */
public class RetireScanServiceImpl implements IRetireScanService {

	private static Log log = LogFactory.getLog(RetireScanServiceImpl.class);
	private IRetireScanDao retireScanDao;
	private IMessageBusiness messageBusiness;
	private IRetireService retireService;
	
	private RetireRuleService retireRuleService;
	
	@Override
	public void doScan(String receiver,int notifySelf,RetireScheduleTypeEnum period) {
		//读出规则list
		RetireRule query=new RetireRule();
		List<RetireRule> retireRules= retireRuleService.getList(query);
		// 20140423 add start
		String receiverTemp = "";
		// 20140423 add end
		//遍历规则list，处理每个规则产生的预退休人员
		for (RetireRule retireRule : retireRules) {
			try{
				// 20140423 upd start
				receiverTemp = retireRule.getReceiver();
				if (receiverTemp == null || "".equals(receiverTemp.trim())) {
					receiverTemp = receiver;
				}
				this.exeRule(retireRule, receiverTemp, notifySelf, period);
				// this.exeRule(retireRule, receiver, notifySelf,period);
				// 20140423 upd end
			}catch(Exception e){
				log.error("", e);
			}
		}
	}
	
	@Override
	public void doScanByDate(String receiver, int notifySelf,
			RetireScheduleTypeEnum period, String date) {
		//读出规则list
		RetireRule query=new RetireRule();
		List<RetireRule> retireRules= retireRuleService.getList(query);
		// 20140423 add start
		String receiverTemp = "";
		// 20140423 add end
		//遍历规则list，处理每个规则产生的预退休人员
		for (RetireRule retireRule : retireRules) {
			try{
				// 20140423 upd start
				receiverTemp = retireRule.getReceiver();
				if (receiverTemp == null || "".equals(receiverTemp.trim())) {
					receiverTemp = receiver;
				}
				this.exeRuleByDate(retireRule, receiverTemp, notifySelf, period, date);
				// this.exeRuleByDate(retireRule, receiver, notifySelf,period,date);
				// 20140423 upd end
			}catch(Exception e){
				log.error("", e);
			}
		}
		
	}
	
	/**
	 * 根据规则处理预退休人员
	 * @param retireRule
	 * @param receiver
	 * @param notifySelf
	 */
	private void exeRule(RetireRule retireRule,String receiver,int notifySelf,RetireScheduleTypeEnum period){
			//查询优先级比本规则高的的规则
			List<RetireRule> retireRules= retireRuleService.findHigherRules(retireRule.getSeq());
			
			RetireAlgorithm algorithm=new RetireAlgorithm();
			algorithm.setAge(String.valueOf(retireRule.getRetire_age()));
			algorithm.setCond_col(retireRule.getCond_col());
			algorithm.setCond_val(retireRule.getCond_val());
			algorithm.setSex(retireRule.getSex());
			algorithm.setRetireScheduleTypeEnum(period);
			// 20140423 add start
			algorithm.setIdentity(retireRule.getIdentity());
			algorithm.setDuty_level(retireRule.getDuty_level());
			// 20140423 add end
			
			//根据规则查询即将退休的人员，如果退休人员在退休表中已经存在或者属于更高优先级规则，则略过，否则插入
			Map<String,Object> paraMap=new HashMap<String, Object>();
			paraMap.put("algorithm", algorithm);
			paraMap.put("retireRules", retireRules);
			List<RetireInfo> retires = retireScanDao.getRetireList(paraMap);
			
			for(RetireInfo retire : retires){
				int cnt=retireService.getCountByUserId(retire.getUserId());
				if(cnt>0){
					continue;
				}
				DynaBean selfInfo = DynaBeanUtil.getPerson(retire.getUserId());
				if(selfInfo==null){
					continue;
				}
				Date borth=(Date) selfInfo.getValue("csrq");
				Date retireDate=this.computeRetireAge(borth, retireRule.getRetire_age());
				retire.setRetireTime(retireDate);
				
				Message message = buildMessage(retire);
				message.setReceiver(receiver);
				messageBusiness.save(message);
				if(notifySelf==1){
					Message notifySelfMessage = buildNotifySelf(retire);
					notifySelfMessage.setReceiver(retire.getUserId());
					messageBusiness.save(notifySelfMessage);
				}
				retireService.saveRetire(retire, "add");
			}
	}
	
	/**
	 * 根据规则处理预退休人员
	 * @param retireRule
	 * @param receiver
	 * @param notifySelf
	 * @param date
	 */
	private void exeRuleByDate(RetireRule retireRule,String receiver,int notifySelf,RetireScheduleTypeEnum period,String date){
			//查询优先级比本规则高的的规则
			List<RetireRule> retireRules= retireRuleService.findHigherRules(retireRule.getSeq());
			
			RetireAlgorithm algorithm=new RetireAlgorithm();
			algorithm.setAge(String.valueOf(retireRule.getRetire_age()));
			algorithm.setCond_col(retireRule.getCond_col());
			algorithm.setCond_val(retireRule.getCond_val());
			algorithm.setSex(retireRule.getSex());
			algorithm.setRetireScheduleTypeEnum(period);
			// 20140423 add start
			algorithm.setIdentity(retireRule.getIdentity());
			algorithm.setDuty_level(retireRule.getDuty_level());
			// 20140423 add end
			
			algorithm.setManual_time(date);
			
			//根据规则查询即将退休的人员，如果退休人员在退休表中已经存在或者属于更高优先级规则，则略过，否则插入
			Map<String,Object> paraMap=new HashMap<String, Object>();
			paraMap.put("algorithm", algorithm);
			paraMap.put("retireRules", retireRules);
			List<RetireInfo> retires = retireScanDao.getRetireList(paraMap);
			
			for(RetireInfo retire : retires){
				int cnt=retireService.getCountByUserId(retire.getUserId());
				if(cnt>0){
					continue;
				}
				DynaBean selfInfo = DynaBeanUtil.getPerson(retire.getUserId());
				if(selfInfo==null){
					continue;
				}
				Date borth=(Date) selfInfo.getValue("csrq");
				Date retireDate=this.computeRetireAge(borth, retireRule.getRetire_age());
				retire.setRetireTime(retireDate);
				
				Message message = buildMessage(retire);
				message.setReceiver(receiver);
				messageBusiness.save(message);
				if(notifySelf==1){
					Message notifySelfMessage = buildNotifySelf(retire);
					notifySelfMessage.setReceiver(retire.getUserId());
					messageBusiness.save(notifySelfMessage);
				}
				retireService.saveRetire(retire, "add");
			}
	}
	
	
	/**
	 * 根据出生日期和退休年龄，计算退休日期
	 * @param borth
	 * @param retireAge
	 * @return
	 */
	private Date computeRetireAge(Date borth,int retireAge){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(borth);
		calendar.add(Calendar.YEAR, retireAge);
		return calendar.getTime();
	}
	
	private Message buildMessage(RetireInfo retire){
		Message message = new Message();
		message.setSender("system");
		message.setTitle("人员退休提醒");
		message.setContent("员工:[code]"+retire.getUserId()+"[:code] 将达到退休时间，请注意处理");
		return message;
	}
	
	private Message buildNotifySelf(RetireInfo retire){
		Message message = new Message();
		message.setSender("system");
		message.setTitle("退休提醒");
		message.setContent("您将达到退休时间，请注意处理");
		return message;
	}

	public void setRetireScanDao(IRetireScanDao retireScanDao) {
		this.retireScanDao = retireScanDao;
	}

	public void setMessageBusiness(IMessageBusiness messageBusiness) {
		this.messageBusiness = messageBusiness;
	}

	public void setRetireService(IRetireService retireService) {
		this.retireService = retireService;
	}

	public RetireRuleService getRetireRuleService() {
		return retireRuleService;
	}

	public void setRetireRuleService(RetireRuleService retireRuleService) {
		this.retireRuleService = retireRuleService;
	}

	
	
}
