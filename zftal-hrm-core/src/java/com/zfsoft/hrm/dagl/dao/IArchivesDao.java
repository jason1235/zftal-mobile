package com.zfsoft.hrm.dagl.dao;

import java.util.List;
import com.zfsoft.hrm.dagl.entity.Archives;
import com.zfsoft.hrm.dagl.query.ArchivesQuery;

/**
 * 
 * @author ChenMinming
 * @date 2014-10-21
 * @version V1.0.0
 */
public interface IArchivesDao {
	public void insert(Archives archives);
	public void update(Archives archives);
	public void remove(String id);
	public Archives findById(String id);
	public Archives findByBh(String bh);
	public List<Archives> getList(Archives archives);
	public List<Archives> getPagingList(ArchivesQuery query);
	public int getPagingCount(ArchivesQuery query);
}
