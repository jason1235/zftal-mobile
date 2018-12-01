package com.zfsoft.hrm.dagl.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.dagl.entity.ArchiveItem;
import com.zfsoft.hrm.dagl.entity.Archives;
import com.zfsoft.hrm.dagl.entity.ArchivesLog;
import com.zfsoft.hrm.dagl.enumobject.ArchiveStatus;
import com.zfsoft.hrm.dagl.query.ArchivesQuery;
import com.zfsoft.hrm.dagl.service.IArchiveItemService;
import com.zfsoft.hrm.dagl.service.IArchivesService;
import com.zfsoft.util.base.StringUtil;

/**
 * 
 * @author ChenMinming
 * @date 2014-10-21
 * @version V1.0.0
 */
public class ArchivesAction extends HrmAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4943696340286662387L;
	private IArchivesService archivesService;
	private IArchiveItemService archiveItemService;
	private ArchivesQuery query = new ArchivesQuery();
	private PageList<Archives> pageList = new PageList<Archives>();
	private List<ArchiveItem> dataList = new ArrayList<ArchiveItem>();
	private String dataMessage;
	private Archives archives;
	private String asc;
	private String sortFieldName;
	private ArchiveItem archiveItem;
	
	public String page(){
		if(StringUtil.isNotEmpty(sortFieldName)){
			if(asc.equals("up")){
				query.setOrderStr( sortFieldName );
			}else{
				query.setOrderStr( sortFieldName +" desc");
			}
		}
		if(StringUtil.isEmpty(query.getStatus())){
			query.setStatus(ArchiveStatus.SAVE.getKey());
		}
		pageList = archivesService.getPageList(query);
		getValueStack().set("statusList", ArchiveStatus.values());
		return "page";
	}
	
	public String saveArchives(){
		Archives bean = archivesService.findByBh(archives.getBh());
		if(bean!=null){
			setErrorMessage("该档案编号已经存在！");
			getValueStack().set(DATA, getMessage());
			return DATA;
		}
		archivesService.save(archives,dataList,dataMessage);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String toAdd(){
		getValueStack().set("nowTime", new Date());
		return "toAdd";
	}
	public String updateArchives(){
		Archives bean = archivesService.findByBh(archives.getBh());
		if(bean!=null&&!bean.getId().equals(archives.getId())){
			setErrorMessage("该档案编号已经存在！");
			getValueStack().set(DATA, getMessage());
			return DATA;
		}
		archives.setStatus(null);
		archivesService.update(archives, dataMessage);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String toModify(){
		archives = archivesService.findById(archives.getId());
		getValueStack().set("nowTime", new Date());
		return "toUpdate";
	}
	
	public String detail(){
		archives = archivesService.findById(archives.getId());
		ArchivesLog archivesLog = new ArchivesLog();
		archivesLog.setArchivesId(archives.getId());
		getValueStack().set("logList", archivesService.getLogList(archivesLog)); 
		ArchiveItem archiveItem = new ArchiveItem();
		archiveItem.setArchiveId(archives.getId());
		dataList = archiveItemService.getList(archiveItem);
		return "detail";
	}
	
	public String addLog(){
		ArchiveStatus status = archives.getStatus();
		archives = archivesService.findById(archives.getId());
		getValueStack().set("nowTime", new Date());
		getValueStack().set("status", status);
		return "addLog";
	}
	
	public String change(){
		if(ArchiveStatus.SAVE.equals(archives.getStatus())){
			ArchivesLog archivesLog = new ArchivesLog();
			archivesLog.setArchivesId(archives.getId());
			archivesLog.setLogMessage("档案借阅："+dataMessage);
			archivesLog.setLogOperator(getUser().getYhm());
			archivesLog.setOperatorTime(archives.getChangeStatusTime());
			archivesService.addLogList(archivesLog);
		}else{
			archivesService.update(archives, dataMessage);
		}
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String toAddItem(){
		archives = archivesService.findById(archives.getId());
		getValueStack().set("nowTime", new Date());
		return "addItem";
	}
	
	public String addArchiveItem(){
		archiveItemService.insert(archiveItem,dataMessage);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String removeArchiveItem(){
		archiveItemService.remove(archiveItem,dataMessage);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	/**
	 * 返回
	 */
	public ArchivesQuery getQuery() {
		return query;
	}

	/**
	 * 设置
	 * @param query 
	 */
	public void setQuery(ArchivesQuery query) {
		this.query = query;
	}

	/**
	 * 返回
	 */
	public Archives getArchives() {
		return archives;
	}

	/**
	 * 设置
	 * @param archives 
	 */
	public void setArchives(Archives archives) {
		this.archives = archives;
	}
	/**
	 * 设置
	 * @param archivesService 
	 */
	public void setArchivesService(IArchivesService archivesService) {
		this.archivesService = archivesService;
	}

	/**
	 * 返回
	 */
	public PageList<Archives> getPageList() {
		return pageList;
	}

	/**
	 * 设置
	 * @param pageList 
	 */
	public void setPageList(PageList<Archives> pageList) {
		this.pageList = pageList;
	}

	/**
	 * 返回
	 */
	public String getAsc() {
		return asc;
	}

	/**
	 * 设置
	 * @param asc 
	 */
	public void setAsc(String asc) {
		this.asc = asc;
	}

	/**
	 * 返回
	 */
	public String getSortFieldName() {
		return sortFieldName;
	}

	/**
	 * 设置
	 * @param sortFieldName 
	 */
	public void setSortFieldName(String sortFieldName) {
		this.sortFieldName = sortFieldName;
	}

	/**
	 * 返回
	 */
	public List<ArchiveItem> getDataList() {
		return dataList;
	}

	/**
	 * 设置
	 * @param dataList 
	 */
	public void setDataList(List<ArchiveItem> dataList) {
		this.dataList = dataList;
	}

	/**
	 * 返回
	 */
	public String getDataMessage() {
		return dataMessage;
	}

	/**
	 * 设置
	 * @param dataMessage 
	 */
	public void setDataMessage(String dataMessage) {
		this.dataMessage = dataMessage;
	}

	/**
	 * 设置
	 * @param archiveItemService 
	 */
	public void setArchiveItemService(IArchiveItemService archiveItemService) {
		this.archiveItemService = archiveItemService;
	}

	/**
	 * 返回
	 */
	public ArchiveItem getArchiveItem() {
		return archiveItem;
	}

	/**
	 * 设置
	 * @param archiveItem 
	 */
	public void setArchiveItem(ArchiveItem archiveItem) {
		this.archiveItem = archiveItem;
	}

}
