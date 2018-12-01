package com.zfsoft.hrm.authpost.post.service.impl;

import java.util.Date;
import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.authpost.post.business.IPostHistoryLogBusiness;
import com.zfsoft.hrm.authpost.post.entities.PostHistoryLog;
import com.zfsoft.hrm.authpost.post.query.PostHistoryLogQuery;
import com.zfsoft.hrm.authpost.post.service.svcinterface.IPostHistoryLogService;

/** 
 * 历史岗位日志service
 * @author jinjj
 * @date 2012-7-26 下午01:24:44 
 *  
 */
public class PostHistoryLogServiceImpl implements IPostHistoryLogService {

	private IPostHistoryLogBusiness postHistoryLogBusiness;
	
	@Override
	public PageList getPage(PostHistoryLogQuery query) {
		return postHistoryLogBusiness.getPage(query);
	}

	@Override
	public void remove(Date snapTime) {
		postHistoryLogBusiness.remove(snapTime);
	}

	@Override
	public void saveAutoLog() {
		postHistoryLogBusiness.saveAutoLog();
	}

	@Override
	public void saveManualLog(Date snapTime) {
		postHistoryLogBusiness.saveManualLog(snapTime);
	}
	
	@Override
	public List<PostHistoryLog> getList(PostHistoryLogQuery query) {
		return postHistoryLogBusiness.getList(query);
	}

	public void setPostHistoryLogBusiness(
			IPostHistoryLogBusiness postHistoryLogBusiness) {
		this.postHistoryLogBusiness = postHistoryLogBusiness;
	}

}
