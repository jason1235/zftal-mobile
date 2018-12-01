package com.zfsoft.dao.daointerface;

import java.util.List;

import com.zfsoft.common.log.Subsystem;
/**
 * 子系统操作dao
 * 
 * @author gonghui
 * 2014-5-15
 */
public interface SubsystemDao{
	public List<Subsystem> findList(Subsystem query);
}
