package com.zfsoft.hrm.dagl.dao;

import java.util.List;

import com.zfsoft.hrm.dagl.entity.ArchiveItem;

/**
 * 
 * @author ChenMinming
 * @date 2014-10-21
 * @version V1.0.0
 */
public interface IArchiveItemDao {
	public void insert(ArchiveItem archiveItem);
	public void updata(ArchiveItem archiveItem);
	public void remove(ArchiveItem archiveItem);
	public ArchiveItem findById(String id);
	public List<ArchiveItem> getList(ArchiveItem archiveItem);
	public int getArchiveItemCount(ArchiveItem itenQuery);
}
