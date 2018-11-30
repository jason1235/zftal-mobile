package com.zfsoft.mobile.txl.service;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.txl.entity.XGDTxl;
import com.zfsoft.mobile.txl.entity.XgdCommentsEntity;
import com.zfsoft.mobile.txl.query.XGDTxlQuery;

public interface IXGDTxlService {
	
	/**
	 * 根据ID查询通讯录记录
	 * @param query
	 * @return
	 */
	public XGDTxl findById(XGDTxlQuery query);
	
	/**
	 * 获取分页列表
	 * @param query
	 * @return
	 */
	public PageList<XGDTxl> getPageList(XGDTxlQuery query);
	
	
	/**
	 * 获取分页列表
	 * @param query
	 * @return
	 */
	public PageList<XGDTxl> getPageListRe(XGDTxlQuery query);
	
	/**
	 * 获取通讯录记录数
	 * @param query
	 * @return
	 */
	public int getPageCount(XGDTxlQuery query);
	
	/**
	 * 插入记录
	 * @param query
	 * @return
	 */
	public int insert(XGDTxlQuery query);
	
	/**
	 * 更新记录
	 * @param query
	 * @return
	 */
	public int update(XGDTxlQuery query);
	
	/**
	 * 清空所有的数据
	 * @return
	 */
	public int deleteAll();

	public void insertComment(XgdCommentsEntity entity);
	
	public List<XgdCommentsEntity> getCommentList();

	public void deleteAllComment();
}
