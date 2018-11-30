package com.zfsoft.hrm.baseinfo.struct.service.svcinterface;

import com.zfsoft.hrm.baseinfo.struct.exception.StructException;

/**
 * 信息类元数据业务处理接口
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-7-2
 * @version V1.0.0
 */
public interface IBeanMetadataService {
	
	/**
	 * 增加信息类属性
	 * @throws StructException
	 */
	public void add() throws StructException;
	
}
