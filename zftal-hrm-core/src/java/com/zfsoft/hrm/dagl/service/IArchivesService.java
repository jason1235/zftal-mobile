package com.zfsoft.hrm.dagl.service;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.dagl.entity.ArchiveItem;
import com.zfsoft.hrm.dagl.entity.Archives;
import com.zfsoft.hrm.dagl.entity.ArchivesLog;
import com.zfsoft.hrm.dagl.query.ArchivesQuery;

/**
 * 
 * @author ChenMinming
 * @date 2014-10-21
 * @version V1.0.0
 */
public interface IArchivesService {
	/**
	 * 档案增加
	 * @param data
	 */
	public void save(Archives archives,List<ArchiveItem> items,String message);
	/**
	 * 分页查询
	 * @return
	 */
	public PageList<Archives> getPageList(ArchivesQuery query);
	/**
	 * 档案修改（只能修改状态）
	 * @param data
	 */
	public void update(Archives archives,String message);
	/**
	 * 删除。。。
	 */
	public void remove(String id);
	/**
	 * 根据id获取对象
	 */
	public Archives findById(String id);
	/**
	 * 根据档案编号获取对象
	 */
	public Archives findByBh(String bh);
	/**
	 * 获取日志对象
	 */
	public List<ArchivesLog> getLogList(ArchivesLog archivesLog);
	/**
	 * 获取日志对象
	 */
	public void addLogList(ArchivesLog archivesLog);
}
