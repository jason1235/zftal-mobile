package com.zfsoft.hrm.contract.service;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.contract.entity.CategoryConfig;
/**
 * 合同种类管理
 * @author: xiaoyongjun
 * @since: 2014-2-26 下午06:15:24
 */
public interface ICategoryConfigService {
	public PageList<CategoryConfig> getPagedList(CategoryConfig categoryConfig) throws RuntimeException;
	
	public void delete(String id) throws RuntimeException;
	
	public void insert(CategoryConfig categoryConfig) throws RuntimeException;
	
	public void update(CategoryConfig categoryConfig) throws RuntimeException;
	
	public CategoryConfig getById(String id) throws RuntimeException;
	
	public int checkDm(String id) throws RuntimeException;
	
	public String getXm(String gh) throws RuntimeException;
	/**
	 * 扫描达需要合同到期提醒的人员，并对合同种类管理人员发出提醒消息，每天扫描一次
	 * @param receiver  需要通知的人
	 */
	public void doScanByDate();
}
