package com.zfsoft.hrm.staffturn.leaveschool.service.impl;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.dyna.business.bizinterface.IDynaBeanBusiness;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.pendingAffair.entities.PendingAffairInfo;
import com.zfsoft.hrm.pendingAffair.service.svcinterface.IPendingAffairService;
import com.zfsoft.hrm.staffturn.leaveschool.business.ILeaveProcessBusiness;
import com.zfsoft.hrm.staffturn.leaveschool.entities.LeaveProcess;
import com.zfsoft.hrm.staffturn.leaveschool.query.LeaveProcessQuery;
import com.zfsoft.hrm.staffturn.leaveschool.service.svcinterface.ILeaveProcessService;

/** 
 * 离校处理service实现
 * @author jinjj
 * @date 2012-8-3 上午02:37:19 
 *  
 */
public class LeaveProcessServiceImpl implements ILeaveProcessService {

	private ILeaveProcessBusiness leaveProcessBusiness;
	private IDynaBeanBusiness dynaBeanBusiness;
	private IPendingAffairService pendingAffairService;
	
	@Override
	public void updateStatus(LeaveProcess process) {
		leaveProcessBusiness.updateStatus(process);		
		//更改当前代办事宜状态
		updatePendingAffair(process);
	}
	
	private void updatePendingAffair(LeaveProcess process){
		PendingAffairInfo query = new PendingAffairInfo();
		LeaveProcess p = leaveProcessBusiness.getLeaveProcess(process.getGuid());
		String businessId = p.getAccountId()+"-"+p.getDeptId();
		query.setBusinessId(businessId);
		query.setStatus(1);
		pendingAffairService.modifyByYwId(query);	
	}

	@Override
	public List<LeaveProcess> getList(LeaveProcessQuery query) {
		List<LeaveProcess> list = leaveProcessBusiness.getList(query);
		fetchOverallInfo(list);
		return list;
	}
	
	@Override
	public PageList<LeaveProcess> getPagingList(LeaveProcessQuery query) {
		PageList<LeaveProcess> list = leaveProcessBusiness.getPagingList(query);
		fetchOverallInfo(list);
		return list;
	}

	/** 
	 * 获取OVERALL人员信息
	 * @param list
	 */
	private void fetchOverallInfo(List<LeaveProcess> list) {
		for(int i=0;i<list.size();i++){
			LeaveProcess process = list.get(i);
			DynaBeanQuery dyQuery=new DynaBeanQuery(InfoClassCache.getOverallInfoClass() );
			dyQuery.setExpress( "gh = #{params.gh}" );
			dyQuery.setParam( "gh", process.getAccountId() );
			List<DynaBean> dyBeans=dynaBeanBusiness.queryBeans( dyQuery );
			if(dyBeans.size()>0){
				process.setDynaBean(dyBeans.get(0));
			}else{
				process.setDynaBean(new DynaBean(InfoClassCache.getOverallInfoClass()));
			}
			list.set(i, process);
		}
	}

	public void setLeaveProcessBusiness(ILeaveProcessBusiness leaveProcessBusiness) {
		this.leaveProcessBusiness = leaveProcessBusiness;
	}

	public void setDynaBeanBusiness(IDynaBeanBusiness dynaBeanBusiness) {
		this.dynaBeanBusiness = dynaBeanBusiness;
	}

	public void setPendingAffairService(IPendingAffairService pendingAffairService) {
		this.pendingAffairService = pendingAffairService;
	}

}
