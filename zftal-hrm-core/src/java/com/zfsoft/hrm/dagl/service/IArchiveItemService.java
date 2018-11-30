package com.zfsoft.hrm.dagl.service;

import java.util.List;

import com.zfsoft.hrm.dagl.entity.ArchiveItem;

/**
 * 
 * @author ChenMinming
 * @date 2014-10-23
 * @version V1.0.0
 */
public interface IArchiveItemService {
	public List<ArchiveItem> getList(ArchiveItem archiveItem);
	public void insert(ArchiveItem archiveItem,String dataMessage);
	public void update(ArchiveItem archiveItem,String dataMessage);
	public void remove(ArchiveItem archiveItem,String dataMessage);
	public ArchiveItem findById(String id);
}
