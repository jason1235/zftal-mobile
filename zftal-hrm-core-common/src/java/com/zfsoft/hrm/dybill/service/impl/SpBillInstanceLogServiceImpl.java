package com.zfsoft.hrm.dybill.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.zfsoft.hrm.dybill.dao.ISpBillInstanceLogDao;
import com.zfsoft.hrm.dybill.entity.SpBillInstance;
import com.zfsoft.hrm.dybill.entity.SpBillInstanceLog;
import com.zfsoft.hrm.dybill.service.ISpBillInstanceLogService;
import com.zfsoft.util.date.TimeUtil;

/**
 * 
 * @author ChenMinming
 * @date 2013-8-27
 * @version V1.0.0
 */
public class SpBillInstanceLogServiceImpl implements ISpBillInstanceLogService {
	
	private ISpBillInstanceLogDao spBillInstanceLogDao;
	@Override
	public List<SpBillInstanceLog> getLogList(SpBillInstanceLog spBillInstanceLog) {
		List<SpBillInstanceLog> list = null;
		try{
			list = spBillInstanceLogDao.findListByInstanceId(spBillInstanceLog);
		}catch(Exception e){
			e.printStackTrace();
		}
		if(list!=null){
			return list;
		}
		return new ArrayList<SpBillInstanceLog>();
	}

	@Override
	public void insertInstanceLog(SpBillInstance spBillInstance,String staffId){
		try{
			SpBillInstanceLog spBillInstanceLog = new SpBillInstanceLog();
			spBillInstanceLog.setContent(spBillInstance.getContent());
			spBillInstanceLog.setOperator(staffId);
			spBillInstanceLog.setBillInstanceId(spBillInstance.getId());
			spBillInstanceLog.setModifyDate(TimeUtil.getNowTimestamp());
			spBillInstanceLogDao.insert(spBillInstanceLog);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public SpBillInstanceLog findById(String id) {
		try{
			SpBillInstanceLog spBillInstanceLog = new SpBillInstanceLog();
			spBillInstanceLog.setId(id);
			return spBillInstanceLogDao.findById(spBillInstanceLog);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public void deleteByInstanceId(String instanceId) {
		try{
			SpBillInstanceLog spBillInstanceLog = new SpBillInstanceLog();
			spBillInstanceLog.setBillInstanceId(instanceId);
			spBillInstanceLogDao.deleteByInstanceId(spBillInstanceLog);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 设置
	 * @param spBillInstanceLogDao 
	 */
	public void setSpBillInstanceLogDao(ISpBillInstanceLogDao spBillInstanceLogDao) {
		this.spBillInstanceLogDao = spBillInstanceLogDao;
	}


	
}
