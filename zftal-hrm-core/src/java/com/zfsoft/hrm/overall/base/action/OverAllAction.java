package com.zfsoft.hrm.overall.base.action;

import com.zfsoft.dao.entities.YhglModel;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.overall.base.entity.OverAll;
import com.zfsoft.hrm.overall.base.query.OverAllQuery;
import com.zfsoft.hrm.overall.base.service.IOverAllService;

public class OverAllAction  extends HrmAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3316082244468579656L;
	private IOverAllService overAllService;
	private OverAllQuery overAllQuery;
	private OverAll overAll;
	
	public IOverAllService getOverAllService() {
		return overAllService;
	}

	public void setOverAllService(IOverAllService overAllService) {
		this.overAllService = overAllService;
	}

	public OverAllQuery getOverAllQuery() {
		return overAllQuery;
	}

	public void setOverAllQuery(OverAllQuery overAllQuery) {
		this.overAllQuery = overAllQuery;
	}

	public OverAll getOverAll() {
		return overAll;
	}

	public void setOverAll(OverAll overAll) {
		this.overAll = overAll;
	}

	public String view() {
		String s = YhglModel.INNER_USER_ADMIN;
		overAll = overAllService.getByGh(s);
//		overAll = overAllService.getByGh(overAll.getGh());
		return "view";
	}
}
