package com.zfsoft.service.svcinterface;

import java.util.List;

import com.zfsoft.common.log.Subsystem;
/**
 * 子系统操作service
 * 
 * @author gonghui
 * 2014-5-15
 */
public interface SubsystemService {

	public List<Subsystem> queryAllEnabledAndDefaultOrder();
}
