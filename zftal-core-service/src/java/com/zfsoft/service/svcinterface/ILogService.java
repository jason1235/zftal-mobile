package com.zfsoft.service.svcinterface;

import com.zfsoft.dao.entities.OperateLogModel;

public interface ILogService {

	/**
	 * 往数据库插入操作日志
	 * 
	 * @param bean
	 * @
	 */
	public void insert(OperateLogModel model) ;
}
