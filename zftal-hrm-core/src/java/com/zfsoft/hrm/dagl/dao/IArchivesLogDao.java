package com.zfsoft.hrm.dagl.dao;

import java.util.List;

import com.zfsoft.hrm.dagl.entity.ArchivesLog;

/**
 * 
 * @author ChenMinming
 * @date 2014-10-21
 * @version V1.0.0
 */
public interface IArchivesLogDao {
	void insert(ArchivesLog log);
	List<ArchivesLog> getList(ArchivesLog log);
}
