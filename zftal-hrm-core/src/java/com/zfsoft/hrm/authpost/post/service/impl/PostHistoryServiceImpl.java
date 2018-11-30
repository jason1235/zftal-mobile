package com.zfsoft.hrm.authpost.post.service.impl;

import java.util.Date;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.authpost.post.business.IPostHistoryBusiness;
import com.zfsoft.hrm.authpost.post.business.IPostHistoryLogBusiness;
import com.zfsoft.hrm.authpost.post.query.PostHistoryQuery;
import com.zfsoft.hrm.authpost.post.service.svcinterface.IPostHistoryService;

/** 
 * 历史岗位service
 * @author jinjj
 * @date 2012-7-26 下午01:38:27 
 *  
 */
public class PostHistoryServiceImpl implements IPostHistoryService {

	private IPostHistoryBusiness postHistoryBusiness;
	private IPostHistoryLogBusiness postHistoryLogBusiness;
	
	@Override
	public void doAuto() {
		postHistoryBusiness.doAuto();
		postHistoryLogBusiness.saveAutoLog();

	}

	@Override
	public void doManual(Date snapTime) {
		postHistoryBusiness.doManual(snapTime);
		postHistoryLogBusiness.saveManualLog(snapTime);
	}
	
	@Override
	public PageList getPage(PostHistoryQuery query) {
		return postHistoryBusiness.getPage(query);
	}

	@Override
	public void remove(Date snapTime) {
		postHistoryBusiness.remove(snapTime);
		postHistoryLogBusiness.remove(snapTime);
	}

	public void setPostHistoryBusiness(IPostHistoryBusiness postHistoryBusiness) {
		this.postHistoryBusiness = postHistoryBusiness;
	}

	public void setPostHistoryLogBusiness(
			IPostHistoryLogBusiness postHistoryLogBusiness) {
		this.postHistoryLogBusiness = postHistoryLogBusiness;
	}

}
