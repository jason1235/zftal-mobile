package com.zfsoft.mobile.pushmsg.service.impl;

import java.util.List;

import com.zfsoft.mobile.pushmsg.dao.IPushRegistrationDao;
import com.zfsoft.mobile.pushmsg.entity.PushRegistration;
import com.zfsoft.mobile.pushmsg.entity.PushRegistrationQuery;
import com.zfsoft.mobile.pushmsg.service.IPushRegistrationService;
import com.zfsoft.util.base.StringUtil;

/**
 * 
 * @author ChenMinming
 * @date 2015-4-22
 * @version V1.0.0
 */
public class PushRegistrationServiceImpl implements IPushRegistrationService {
	
	private IPushRegistrationDao pushRegistrationDao;

	@Override
	public List<PushRegistration> getPushRegistrationList(
			PushRegistrationQuery query) {
		return pushRegistrationDao.getPushRegistrationList(query);
	}

	@Override
	public void save(PushRegistration pushRegistration) {
		PushRegistrationQuery query = new PushRegistrationQuery();
		//query.setRegistrationId(pushRegistration.getRegistrationId());
		query.setAppType(pushRegistration.getAppType());
		//query.setSbType(pushRegistration.getSbType());
		query.setUserIds(new String[]{pushRegistration.getUserId()});
		if(StringUtil.isEmpty(pushRegistration.getRegistrationId()))return;
		List<PushRegistration> list = pushRegistrationDao.getPushRegistrationList(query);
		if(list.size()>0){
			for (PushRegistration p : list) {
				pushRegistrationDao.delete(p);
			}
		}
		pushRegistrationDao.insert(pushRegistration);
	}

	/**
	 * 设置
	 * @param pushRegistrationDao 
	 */
	public void setPushRegistrationDao(IPushRegistrationDao pushRegistrationDao) {
		this.pushRegistrationDao = pushRegistrationDao;
	}

}
