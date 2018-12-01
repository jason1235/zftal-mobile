package com.zfsoft.hrm.dagl.service.impl;

import java.util.List;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.dagl.dao.IArchiveItemDao;
import com.zfsoft.hrm.dagl.dao.IArchivesDao;
import com.zfsoft.hrm.dagl.dao.IArchivesLogDao;
import com.zfsoft.hrm.dagl.entity.ArchiveItem;
import com.zfsoft.hrm.dagl.entity.Archives;
import com.zfsoft.hrm.dagl.entity.ArchivesLog;
import com.zfsoft.hrm.dagl.query.ArchivesQuery;
import com.zfsoft.hrm.dagl.service.IArchivesService;

/**
 * 
 * @author ChenMinming
 * @date 2014-10-21
 * @version V1.0.0
 */
public class ArchivesServiceImpl implements IArchivesService {
	private IArchivesDao archivesDao;
	private IArchivesLogDao archivesLogDao;
	private IArchiveItemDao archiveItemDao;

	@Override
	public PageList<Archives> getPageList(ArchivesQuery query) {
		PageList<Archives> pageList = new PageList<Archives>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			
			paginator.setItems(archivesDao.getPagingCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<Archives> list = archivesDao.getPagingList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	@Override
	public void remove(String id) {
		archivesDao.remove(id);
	}

	@Override
	public void update(Archives archives,String message) {
		ArchivesLog archivesLog = new ArchivesLog();
		archivesLog.setArchivesId(archives.getId());
		archivesLog.setLogOperator(SessionFactory.getUser().getYhm());
		archivesLog.setOperatorTime(archives.getChangeStatusTime());
		if(archives.getStatus()!=null){
			archivesLog.setLogMessage("档案"+archives.getStatusText()+":"+message);
		}else{
			archives.setChangeStatusTime(null);
			archivesLog.setLogMessage("更新档案信息:"+message);
		}
		archivesDao.update(archives);
		archivesLogDao.insert(archivesLog);
	}

	/**
	 * 设置
	 * @param archivesDao 
	 */
	public void setArchivesDao(IArchivesDao archivesDao) {
		this.archivesDao = archivesDao;
	}

	/**
	 * 设置
	 * @param archivesLogDao 
	 */
	public void setArchivesLogDao(IArchivesLogDao archivesLogDao) {
		this.archivesLogDao = archivesLogDao;
	}

	@Override
	public void save(Archives archives, List<ArchiveItem> items, String message) {
		archivesDao.insert(archives);
		if(items!=null){
			for (ArchiveItem archiveItem : items) {
				archiveItem.setArchiveId(archives.getId());
				archiveItemDao.insert(archiveItem);
				ArchivesLog archivesItemLog = new ArchivesLog();
				archivesItemLog.setArchivesId(archiveItem.getArchiveId());
				archivesItemLog.setArchiveItem(archiveItem.getItemId());
				archivesItemLog.setLogOperator(SessionFactory.getUser().getYhm());
				archivesItemLog.setOperatorTime(archiveItem.getCreateTime());
				archivesItemLog.setLogMessage("材料添加("+archiveItem.getName()+"):"+message);
				archivesLogDao.insert(archivesItemLog);
			}
		}
		ArchivesLog archivesLog = new ArchivesLog();
		archivesLog.setArchivesId(archives.getId());
		archivesLog.setLogOperator(SessionFactory.getUser().getYhm());
		archivesLog.setOperatorTime(archives.getChangeStatusTime());
		archivesLog.setLogMessage("档案调入:"+message);
		archivesLogDao.insert(archivesLog);
	}

	/**
	 * 设置
	 * @param archiveItemDao 
	 */
	public void setArchiveItemDao(IArchiveItemDao archiveItemDao) {
		this.archiveItemDao = archiveItemDao;
	}

	@Override
	public Archives findById(String id) {
		return archivesDao.findById(id);
	}

	@Override
	public List<ArchivesLog> getLogList(ArchivesLog archivesLog) {
		return archivesLogDao.getList(archivesLog);
	}

	@Override
	public void addLogList(ArchivesLog archivesLog) {
		archivesLogDao.insert(archivesLog);
	}

	@Override
	public Archives findByBh(String bh) {
		return archivesDao.findByBh(bh);
	}

}
