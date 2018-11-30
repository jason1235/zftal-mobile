package com.zfsoft.hrm.download.service;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.download.entity.FileDown;
import com.zfsoft.hrm.download.query.FileDownQuery;

/** 
 * @author jinjj
 * @date 2013-5-21 上午11:17:19 
 *  
 */
public interface IFileDownService {

	public FileDown getById(String id);

	public void save(FileDown file);

	public void update(FileDown file);
	
	public void updateStatus(FileDown file);

	public void delete(String id);

	public PageList<FileDown> getPagingList(FileDownQuery query);

}