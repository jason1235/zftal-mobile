package com.zfsoft.mobile.sourceexchange.service;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.sourceexchange.entity.Sourcesigninhis;

public interface ISourcesigninService {
	//签到列表
	PageList<Sourcesigninhis> getPageList(Sourcesigninhis query);
	
	//签到
	public int signIn(Sourcesigninhis source);
	
	//是否已签到
	public int isSignedToday(Sourcesigninhis signed);
}
