package com.zfsoft.hrm.baseinfo.audit.business.impl;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.dataprivilege.util.DeptFilterUtil;
import com.zfsoft.hrm.baseinfo.audit.business.IAuditProcessBusiness;
import com.zfsoft.hrm.baseinfo.audit.dao.IAuditProcessDao;
import com.zfsoft.hrm.baseinfo.audit.entity.AuditProcess;
import com.zfsoft.hrm.baseinfo.audit.query.AuditProcessQuery;
import com.zfsoft.hrm.config.IConstants;
import com.zfsoft.hrm.pendingAffair.entities.PendingAffairInfo;
import com.zfsoft.hrm.pendingAffair.enums.PendingAffairBusinessTypeEnum;
import com.zfsoft.hrm.pendingAffair.service.svcinterface.IPendingAffairService;

/** 
 * @author jinjj
 * @date 2012-10-9 下午03:54:16 
 *  
 */
public class AuditProcessBusinessImpl implements IAuditProcessBusiness {

	private IAuditProcessDao processDao;
	private IPendingAffairService pendingAffairService;
	
	public void setPendingAffairService(IPendingAffairService pendingAffairService) {
		this.pendingAffairService = pendingAffairService;
	}
	@Override
	public void save(AuditProcess process) {
		//TODO 流程检查
		processDao.insert(process);
		PendingAffairInfo pendingAffairInfo=new PendingAffairInfo();
		pendingAffairInfo.setAffairName("工号"+process.getGh()+"用户请求信息类审核，请处理");
		pendingAffairInfo.setMenu(IConstants.DBSY_AUDIT_PROCESS_MENU);
		pendingAffairInfo.setStatus(0);
		pendingAffairInfo.setAffairType(PendingAffairBusinessTypeEnum.SH_GRXX.getKey());
		pendingAffairInfo.setRoleId(process.getRoleId());
		pendingAffairInfo.setBusinessId("xxlsh-"+process.getGlobalId());
		pendingAffairService.addPendingAffairInfo(pendingAffairInfo);
	}

	@Override
	public void update(AuditProcess process) {
		PendingAffairInfo query=new PendingAffairInfo();
		query.setBusinessId("xxlsh-"+process.getGlobalId());
		List<PendingAffairInfo> PendingAffairInfos=
			pendingAffairService.getListByQuery(query);
		for (PendingAffairInfo pendingAffairInfo : PendingAffairInfos) {
			pendingAffairInfo.setStatus(1);
			pendingAffairService.modifyPendingAffairInfo(pendingAffairInfo);
		}
		processDao.update(process);
	}

	@Override
	public void delete(String guid) {
		processDao.delete(guid);
	}

	@Override
	public AuditProcess getById(String guid) {
		return processDao.getById(guid);
	}

	@Override
	public List<AuditProcess> getList(AuditProcessQuery query) {
		return processDao.getList(query);
	}

	@Override
	public PageList<AuditProcess> getPagingList(AuditProcessQuery query) {
		PageList<AuditProcess> pageList = new PageList<AuditProcess>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			
			paginator.setItems(processDao.getPagingCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				pageList.addAll(processDao.getPagingList(query));
			}
		}
		return pageList;
	}
	
	@Override
	public PageList<AuditProcess> getAuditPagingList(AuditProcessQuery query) {
		PageList<AuditProcess> pageList = new PageList<AuditProcess>();
		//query.setExpress(DeptFilterUtil.getCondition("o","dwm"));
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			DeptFilterUtil.getCondition("","");
			paginator.setItems(processDao.getAuditPagingCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				pageList.addAll(processDao.getAuditPagingList(query));
			}
		}
		return pageList;
	}

	public void setProcessDao(IAuditProcessDao processDao) {
		this.processDao = processDao;
	}
	
}
