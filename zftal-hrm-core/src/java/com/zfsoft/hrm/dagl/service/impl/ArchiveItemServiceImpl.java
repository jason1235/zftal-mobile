package com.zfsoft.hrm.dagl.service.impl;

import java.util.Date;
import java.util.List;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.dagl.dao.IArchiveItemDao;
import com.zfsoft.hrm.dagl.dao.IArchivesLogDao;
import com.zfsoft.hrm.dagl.entity.ArchiveItem;
import com.zfsoft.hrm.dagl.entity.ArchivesLog;
import com.zfsoft.hrm.dagl.query.MaterialsAddItem;
import com.zfsoft.hrm.dagl.service.IArchiveItemService;
import com.zfsoft.orcus.lang.TimeUtil;

/**
 * 
 * @author ChenMinming
 * @date 2014-10-23
 * @version V1.0.0
 */
public class ArchiveItemServiceImpl implements IArchiveItemService {

	private IArchiveItemDao archiveItemDao;
	private IArchivesLogDao archivesLogDao;
	@Override
	public ArchiveItem findById(String id) {
		return archiveItemDao.findById(id);
	}

	@Override
	public List<ArchiveItem> getList(ArchiveItem archiveItem) {
		
		
		PageList<ArchiveItem> pageList = new PageList<ArchiveItem>();
		Paginator paginator = new Paginator();
		if(archiveItem!=null){
			paginator.setItemsPerPage(archiveItem.getPerPageSize());
			paginator.setPage((Integer)archiveItem.getToPage());
			paginator.setItems(archiveItemDao.getArchiveItemCount(archiveItem));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				archiveItem.setStartRow(paginator.getBeginIndex());
				archiveItem.setEndRow(paginator.getEndIndex());
				List<ArchiveItem> list = archiveItemDao.getList(archiveItem);
				pageList.addAll(list);
			}
		}
		
		
		return archiveItemDao.getList(archiveItem);
	}

	@Override
	public void insert(ArchiveItem archiveItem,String dataMessage) {
		archiveItemDao.insert(archiveItem);
		ArchivesLog archivesLog = new ArchivesLog();
		archivesLog.setArchivesId(archiveItem.getArchiveId());
		archivesLog.setArchiveItem(archiveItem.getItemId());
		archivesLog.setLogOperator(SessionFactory.getUser().getYhm());
		archivesLog.setOperatorTime(archiveItem.getCreateTime());
		archivesLog.setLogMessage("材料添加("+archiveItem.getName()+"):"+dataMessage);
		archivesLogDao.insert(archivesLog);
		
	}

	@Override
	public void remove(ArchiveItem archiveItem,String dataMessage) {
		archiveItem = archiveItemDao.findById(archiveItem.getItemId());
		ArchivesLog archivesLog = new ArchivesLog();
		archivesLog.setArchivesId(archiveItem.getArchiveId());
		archivesLog.setArchiveItem(archiveItem.getItemId());
		archivesLog.setLogOperator(SessionFactory.getUser().getYhm());
		archivesLog.setOperatorTime(TimeUtil.toDate(TimeUtil.format(new Date(), TimeUtil.yyyy_MM_dd)));
		archivesLog.setLogMessage("材料删除:档案材料 "+archiveItem.getName()
				+"("+TimeUtil.format(archiveItem.getCreateTime(),TimeUtil.yyyy_MM_dd)+") 被删除");
		archivesLogDao.insert(archivesLog);
		archiveItemDao.remove(archiveItem);
	}

	@Override
	public void update(ArchiveItem archiveItem,String dataMessage) {
		archiveItemDao.updata(archiveItem);
	}

	/**
	 * 设置
	 * @param archiveItemDao 
	 */
	public void setArchiveItemDao(IArchiveItemDao archiveItemDao) {
		this.archiveItemDao = archiveItemDao;
	}

	/**
	 * 设置
	 * @param archivesLogDao 
	 */
	public void setArchivesLogDao(IArchivesLogDao archivesLogDao) {
		this.archivesLogDao = archivesLogDao;
	}

}
