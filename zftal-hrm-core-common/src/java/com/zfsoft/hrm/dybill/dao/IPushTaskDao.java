package com.zfsoft.hrm.dybill.dao;

import com.zfsoft.hrm.dybill.entity.SpBillDataPushEventConfig;

/**
 * 
 * @author ChenMinming
 * @date 2013-9-10
 * @version V1.0.0
 */
public interface IPushTaskDao {
	public void insert(SpBillDataPushEventConfig config);
	public void update(SpBillDataPushEventConfig config);
	public void delete(SpBillDataPushEventConfig config);
}
