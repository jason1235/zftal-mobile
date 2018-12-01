package com.zfsoft.hrm.authpost.post.action;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.authpost.post.query.PostHistoryLogQuery;
import com.zfsoft.hrm.authpost.post.service.svcinterface.IPostHistoryLogService;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.util.base.StringUtil;

/** 
 * 快照日志
 * @ClassName: SnapshotLogAction 
 * @author jinjj
 * @date 2012-7-18 下午03:38:33 
 *  
 */
public class PostHistoryLogAction extends HrmAction {

	private static final long serialVersionUID = 1041719749429669950L;
	private IPostHistoryLogService postHistoryLogService;
	private PageList pageList;
	private PostHistoryLogQuery query = new PostHistoryLogQuery();;
	private String snapTime;
	private String sortFieldName = null;
	private String asc = "up";
	
	/**
	 * 日志分页snap_time
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception{
		if(!StringUtil.isEmpty(sortFieldName)){
			if(asc.equals("up")){
				query.setOrderStr( sortFieldName );
			}else{
				query.setOrderStr( sortFieldName +" desc");
			}
		}else{
			query.setOrderStr( " guid" );
		}
		pageList = postHistoryLogService.getPage(query);
		this.setInActionContext("paginator", pageList.getPaginator());
		return "list";
	}
	
	public PostHistoryLogQuery getQuery() {
		return query;
	}

	public void setQuery(PostHistoryLogQuery query) {
		this.query = query;
	}


	public PageList getPageList() {
		return pageList;
	}

	public void setPostHistoryLogService(
			IPostHistoryLogService postHistoryLogService) {
		this.postHistoryLogService = postHistoryLogService;
	}

	public String getSnapTime() {
		return snapTime;
	}

	public void setSnapTime(String snapTime) {
		this.snapTime = snapTime;
	}

	public String getSortFieldName() {
		return sortFieldName;
	}

	public void setSortFieldName(String sortFieldName) {
		this.sortFieldName = sortFieldName;
	}

	public String getAsc() {
		return asc;
	}

	public void setAsc(String asc) {
		this.asc = asc;
	}
	
}
