package com.zfsoft.hrm.expertvote.expertmanage.service;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.expertvote.expertmanage.entity.ExpertDeclare;
import com.zfsoft.hrm.expertvote.expertmanage.query.ExpertAuditQuery;

public interface IExpertDeclareService {
	/**
	 * 推荐人员列表
	 * @param: 
	 * @return:
	 */
	public PageList<ExpertDeclare> getPagedList(ExpertDeclare zjkDeclare);
	/**
	 * 添加推荐人员信息
	 * @param: 
	 * @return:
	 */
	public void insert(ExpertDeclare zjkDeclare);
	/**
	 * 删除
	 */
	public void delete(String id) throws RuntimeException;
	/**
	 * 根据id获取推荐人信息
	 * @param: 
	 * @return:
	 */
	public ExpertDeclare getById(String id);
	
	public void doInput(ExpertDeclare zjkDeclare, String workNum);
	
	public String doAdd(ExpertDeclare zjkDeclare, String workNum) throws RuntimeException;
	/**
	 * 申报信息
	 * @param zjkDeclare   增加推荐人申报信息
	 * @throws RuntimeException 
	 */
	public void doPush(ExpertDeclare zjkDeclare, String workNum) throws RuntimeException;
	/**
	 * 修改推荐人申报信息
	 * @param sectionGrading 修改推荐人申报信息
	 * @param isNeed 		
	 * @throws RuntimeException 
	 */
	public void update(ExpertDeclare zjkDeclare,boolean isNeed) throws RuntimeException;
	/**
	 * 专家推荐审核列表
	 * @param: 
	 * @return:
	 */
	public PageList<ExpertDeclare> getPageList(ExpertAuditQuery query) throws RuntimeException;
	/**
	 * 申报取消
	 */
	public void doCancel(String id) throws RuntimeException;
}
