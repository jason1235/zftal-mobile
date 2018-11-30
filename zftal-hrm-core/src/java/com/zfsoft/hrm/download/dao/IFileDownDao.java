package com.zfsoft.hrm.download.dao;

import java.util.List;

import com.zfsoft.hrm.download.entity.FileDown;
import com.zfsoft.hrm.download.query.FileDownQuery;

/** 
 * @author jinjj
 * @date 2013-5-21 上午10:40:46 
 *  
 */
public interface IFileDownDao {

	public FileDown getById(String id);
	
	public void insert(FileDown file);
	
	public void update(FileDown file);
	
	public void delete(String id);
	
	public List<FileDown> getPagingList(FileDownQuery query);
	
	public int getPagingCount(FileDownQuery query);
	
}
