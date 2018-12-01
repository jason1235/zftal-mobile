package com.zfsoft.hrm.baseinfo.org.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.dyna.service.svcinterface.IDynaBeanService;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.org.entities.OrgPeople;
import com.zfsoft.hrm.baseinfo.org.service.svcinterface.IOrgPeopleService;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.util.base.StringUtil;

public class OrgPeopleAction extends HrmAction {

	private static final long serialVersionUID = 1082945186883643998L;
	
	private List<DynaBean> peopleList = new ArrayList<DynaBean>();
	
	private DynaBeanQuery query = new DynaBeanQuery( null );
	
	private String oid;
	
	private OrgPeople orgPeople = new OrgPeople();
	
	private String[] staffIds;
	
	private IOrgPeopleService orgPeopleService;
	
	private IDynaBeanService dynaBeanService;
	
	public String peopleList(){
		Assert.isTrue(!StringUtil.isEmpty(oid), "未选择任何部门");
		query.setClazz( InfoClassCache.getOverallInfoClass() );
		query.setOrderStr( "gh" );
		String exp = "exists (select 1 from HRM_BZGL_ZZJGB z where z.bmdm=dwm and z.bmdm = '" + oid + "')";
		query.setExpress(exp + " and DQZTM like '1%'");
		peopleList = dynaBeanService.findList(query);
		return LIST_PAGE;
	}
	
	public String peopleMove(){
		Assert.isTrue(staffIds != null && staffIds.length > 0, "未选择人员");
		Assert.isTrue(!StringUtil.isEmpty(orgPeople.getPlanOrg()), "未选择变更目标部门");
		for (String staffid : staffIds) {
			orgPeople.getStaffidList().add(staffid);
		}
		orgPeopleService.peopleMove(orgPeople);
		setSuccessMessage("部门变动成功");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	/**
	 * 返回
	 * @return 
	 */
	public List<DynaBean> getPeopleList() {
		return peopleList;
	}

	/**
	 * 设置
	 * @param peopleList 
	 */
	public void setPeopleList(List<DynaBean> peopleList) {
		this.peopleList = peopleList;
	}

	/**
	 * 返回
	 * @return 
	 */
	public DynaBeanQuery getQuery() {
		return query;
	}

	/**
	 * 设置
	 * @param query 
	 */
	public void setQuery(DynaBeanQuery query) {
		this.query = query;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getOid() {
		return oid;
	}

	/**
	 * 设置
	 * @param oid 
	 */
	public void setOid(String oid) {
		this.oid = oid;
	}

	/**
	 * 返回
	 * @return 
	 */
	public IDynaBeanService getDynaBeanService() {
		return dynaBeanService;
	}

	/**
	 * 设置
	 * @param dynaBeanService 
	 */
	public void setDynaBeanService(IDynaBeanService dynaBeanService) {
		this.dynaBeanService = dynaBeanService;
	}

	/**
	 * 返回
	 * @return 
	 */
	public OrgPeople getOrgPeople() {
		return orgPeople;
	}

	/**
	 * 设置
	 * @param orgPeople 
	 */
	public void setOrgPeople(OrgPeople orgPeople) {
		this.orgPeople = orgPeople;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String[] getStaffIds() {
		return staffIds;
	}

	/**
	 * 设置
	 * @param staffIds 
	 */
	public void setStaffIds(String[] staffIds) {
		this.staffIds = staffIds;
	}

	/**
	 * 返回
	 * @return 
	 */
	public IOrgPeopleService getOrgPeopleService() {
		return orgPeopleService;
	}

	/**
	 * 设置
	 * @param orgPeopleService 
	 */
	public void setOrgPeopleService(IOrgPeopleService orgPeopleService) {
		this.orgPeopleService = orgPeopleService;
	}


}
