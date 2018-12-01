package com.zfsoft.hrm.baseinfo.infoclass.dao.daointerface;

import java.util.List;

import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoPropertyView;
import com.zfsoft.hrm.baseinfo.infoclass.query.InfoPropertyViewQuery;

/** 
 * 信息属性展现
 * @author jinjj
 * @date 2012-11-13 上午09:28:45 
 *  
 */
public interface IInfoPropertyViewDao {

	/**
	 * 插入
	 * @param view
	 */
	public void insert(InfoPropertyView view);
	
	/**
	 * 删除
	 * @param query
	 */
	public void deleteBatch(InfoPropertyViewQuery query);
	
	/**
	 * 获取列表
	 * @param query
	 * @return
	 */
	public List<InfoPropertyView> getList(InfoPropertyViewQuery query);
}
