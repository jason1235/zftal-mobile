package com.zfsoft.mobile.sourceexchange.dao;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.sourceexchange.entity.Sourcesigninhis;

/**
 *用户签到dao
 * @author yangbilin
 * @createtime 2017-07-08 16:30
 */
public interface ISourcesigninDao {
	
	//签到列表
	int getPageCount(Sourcesigninhis source);
	PageList<Sourcesigninhis> getPageList(Sourcesigninhis source);
	
	//签到
	public int signIn(Sourcesigninhis source);
	
	//是否已签到
	public int isSignedToday(Sourcesigninhis signed);

}
