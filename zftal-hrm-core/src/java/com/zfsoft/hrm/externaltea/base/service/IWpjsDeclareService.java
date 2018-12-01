package com.zfsoft.hrm.externaltea.base.service;




import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.externaltea.base.entity.WpjsDeclare;
import com.zfsoft.hrm.externaltea.base.query.WpjsDeclareQuery;
/** 
 * @author xyj
 * @date 2013-5-16 下午03:47:30 
 *  
 */
public interface IWpjsDeclareService {

	public void save(WpjsDeclare declare);

	public void update(WpjsDeclare declare);

	public void delete(String id);
	
	public WpjsDeclare getById(String id);
	
	public PageList<WpjsDeclare> findWpjsDeclare(WpjsDeclareQuery query);
	
	public PageList<WpjsDeclare> findWpjsDeclareByTime(WpjsDeclareQuery query);
	
	public PageList<WpjsDeclare> getPageList(WpjsDeclareQuery query);
	
}