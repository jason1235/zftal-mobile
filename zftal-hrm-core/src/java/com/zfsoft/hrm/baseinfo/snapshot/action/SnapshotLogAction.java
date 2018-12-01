package com.zfsoft.hrm.baseinfo.snapshot.action;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.snapshot.query.SnapshotLogQuery;
import com.zfsoft.hrm.baseinfo.snapshot.service.svcinterface.ISnapshotLogService;
import com.zfsoft.hrm.common.HrmAction;

/** 
 * 快照日志
 * @ClassName: SnapshotLogAction 
 * @author jinjj
 * @date 2012-7-18 下午03:38:33 
 *  
 */
public class SnapshotLogAction extends HrmAction {

	private static final long serialVersionUID = 1041719749429669950L;
	private ISnapshotLogService snapshotLogService;
	private PageList pageList;
	private SnapshotLogQuery query = new SnapshotLogQuery();;
	private String snapTime;
	
	/**
	 * 日志分页
	 * @return
	 * @throws Exception
	 */
	public String page()throws Exception{
		pageList = snapshotLogService.getPage(query);
		return "logPage";
	}
	
	public SnapshotLogQuery getQuery() {
		return query;
	}

	public void setQuery(SnapshotLogQuery query) {
		this.query = query;
	}

	public PageList getPageList() {
		return pageList;
	}

	public void setSnapshotLogService(ISnapshotLogService snapshotLogService) {
		this.snapshotLogService = snapshotLogService;
	}

	public String getSnapTime() {
		return snapTime;
	}

	public void setSnapTime(String snapTime) {
		this.snapTime = snapTime;
	}
	
}
