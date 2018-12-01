package com.zfsoft.hrm.baseinfo.audit.service;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.audit.entity.AuditInfo;
import com.zfsoft.hrm.baseinfo.audit.entity.AuditProcess;
import com.zfsoft.hrm.baseinfo.audit.query.AuditProcessQuery;

/** 
 * @author jinjj
 * @date 2012-10-10 上午09:21:07 
 *  
 */
public interface IAuditProcessService {

	/**
	 * 审核流程分页
	 * @param query
	 * @return
	 */
	public PageList<AuditProcess> getPagingList(AuditProcessQuery query);
	
	/**
	 * 审核流程分页-审核人
	 * @param query
	 * @return
	 */
	public PageList<AuditProcess> getAuditPagingList(AuditProcessQuery query);
	
	/**
	 * 查询
	 * @param guid
	 * @return
	 */
	public AuditProcess getById(String guid);
	
	/**
	 * 审核通过
	 * @param info
	 */
	public void doPass(AuditInfo info);
	
	/**
	 * 审核拒绝
	 * @param info
	 */
	public void doReject(AuditInfo info);
	
	/**
	 * 审核意见列表
	 * @param guid
	 * @return
	 */
	public List<AuditInfo> getInfoList(String guid);
	
	/**
	 * 获得变动记录详细html信息
	 * @param processId
	 * @return
	 */
	public String getRecordDetailHtml(String processId);
}
