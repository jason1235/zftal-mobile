package com.zfsoft.hrm.expertvote.expertmanage.dao;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.expertvote.expertmanage.entity.ExpertDeclare;
import com.zfsoft.hrm.expertvote.expertmanage.query.ExpertAuditQuery;

public interface IExpertDeclareDao {
	/**
	 * 申报信息列表
	 * @param: 
	 * @return:
	 */
	public PageList<ExpertDeclare> getPagedList(ExpertDeclare zjkDeclare);
	/**
	 * 添加申报信息
	 * @param: 
	 * @return:
	 */
	public void insert(ExpertDeclare zjkDeclare);
	/**
	 * 根据id获取申报信息
	 * @param: 
	 * @return:
	 */
	public ExpertDeclare getById(String id);
	/**
	 * 取消
	 * @param: 
	 * @return:
	 */
	public void updateDeclare(ExpertDeclare zjkDeclare);
	/**
	 * 删除申报信息
	 * @param: 
	 * @return:
	 */
	public void delete(String id);
	/**
	 * 专家审核列表查询
	 * @param: 
	 * @return:
	 */
	public List<ExpertDeclare> getPagingList(ExpertAuditQuery query); 
	/**
	 * 专家审核列表查询
	 * @param: 
	 * @return:
	 */
	public int getPagingCount(ExpertAuditQuery query);
}
