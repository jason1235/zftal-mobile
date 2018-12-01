package com.zfsoft.mobile.pushmsg.dao;

import java.util.List;

import com.zfsoft.mobile.pushmsg.entity.PushRegistration;
import com.zfsoft.mobile.pushmsg.entity.PushRegistrationQuery;

/**
 * 
 * @author ChenMinming
 * @date 2015-4-22
 * @version V1.0.0
 */
public interface IPushRegistrationDao {
	public void insert(PushRegistration pushRegistration);
	public void delete(PushRegistration pushRegistration);
	public List<PushRegistration> getPushRegistrationList(PushRegistrationQuery query);
}
