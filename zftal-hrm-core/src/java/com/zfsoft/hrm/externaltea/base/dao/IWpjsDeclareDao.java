package com.zfsoft.hrm.externaltea.base.dao;

import java.util.List;



import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.externaltea.base.entity.WpjsDeclare;
import com.zfsoft.hrm.externaltea.base.query.WpjsDeclareQuery;



/** 
 * @author xyj
 * @date 2013-5-16 下午01:58:43 
 *  
 */
public interface IWpjsDeclareDao {

	public void insert(WpjsDeclare wpjs);
	
	public void update(WpjsDeclare wpjs);
	
	public void delete(String id);
	
	public WpjsDeclare getById(String id);
	
	public PageList<WpjsDeclare> findWpjsDeclare(WpjsDeclareQuery query);
	
	public PageList<WpjsDeclare> findWpjsDeclareByTime(WpjsDeclareQuery query);
	
	public List<WpjsDeclare> getPageList(WpjsDeclareQuery query); 
	
	public List<WpjsDeclare> getList(WpjsDeclareQuery query);
	
	public List<WpjsDeclare> getPagingList(WpjsDeclareQuery query);
	
	public int getPagingCount(WpjsDeclareQuery query);
}
