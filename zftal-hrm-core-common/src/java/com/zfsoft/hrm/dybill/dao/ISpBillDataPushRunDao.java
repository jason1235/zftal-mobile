package com.zfsoft.hrm.dybill.dao;

import com.zfsoft.hrm.dybill.dto.DataPushBean;
/**
 * @className: ISpBillDataPushRunDao 
 * @author Patrick Shen shenluwei@126.com
 * @date 2013-8-14 下午02:11:02
 */
public interface ISpBillDataPushRunDao {
	/**
	 * @methodName insert 
	 * @param bean void
	 */
	public void insert(DataPushBean bean);
	/**
	 * @methodName update 
	 * @param bean void
	 */
	public void update(DataPushBean bean);
}
