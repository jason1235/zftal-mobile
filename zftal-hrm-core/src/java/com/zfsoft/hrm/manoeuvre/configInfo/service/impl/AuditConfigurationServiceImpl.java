package com.zfsoft.hrm.manoeuvre.configInfo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dataprivilege.dao.IDataPrivilegeDao;
import com.zfsoft.dataprivilege.dto.DeptFilter;
import com.zfsoft.dataprivilege.entity.DataPrivilege;
import com.zfsoft.dataprivilege.filter.impl.DealDeptFilter;
import com.zfsoft.hrm.baseinfo.code.entities.Item;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.util.DynaBeanUtil;
import com.zfsoft.hrm.manoeuvre.configInfo.business.businessinterface.IAuditConfigOrgBusiness;
import com.zfsoft.hrm.manoeuvre.configInfo.business.businessinterface.IAuditConfigurationBusiness;
import com.zfsoft.hrm.manoeuvre.configInfo.entities.AuditConfigOrgInfo;
import com.zfsoft.hrm.manoeuvre.configInfo.entities.AuditConfiguration;
import com.zfsoft.hrm.manoeuvre.configInfo.query.AuditConfigurationQuery;
import com.zfsoft.hrm.manoeuvre.configInfo.service.svcinterface.IAuditConfigurationService;
import com.zfsoft.hrm.manoeuvre.exception.ManoeuvreException;
import com.zfsoft.util.jaxb.JaxbUtil;

public class AuditConfigurationServiceImpl implements IAuditConfigurationService {

	private IAuditConfigurationBusiness auditConfigurationBusiness;
	
	private IAuditConfigOrgBusiness auditConfigOrgBusiness;
	
	/**
	 * 设置business
	 * @param auditConfigurationBusiness
	 */
	public void setAuditConfigurationBusiness(
			IAuditConfigurationBusiness auditConfigurationBusiness) {
		this.auditConfigurationBusiness = auditConfigurationBusiness;
	}
	
	public void setAuditConfigOrgBusiness(
			IAuditConfigOrgBusiness auditConfigOrgBusiness) {
		this.auditConfigOrgBusiness = auditConfigOrgBusiness;
	}
	
	//----------------------------基础方法------------------------------------------
	
	@Override
	public boolean add(AuditConfiguration info) throws ManoeuvreException {
		try{
			boolean flg = auditConfigurationBusiness.add(info);
			
			List<String> dwms = getSjfw(info);
			
			if (dwms != null) {
				AuditConfigOrgInfo orgInfo;
				for (String dwm : dwms) {
					orgInfo = new AuditConfigOrgInfo();
					orgInfo.setAid(info.getAid());
					orgInfo.setOid(dwm);
					auditConfigOrgBusiness.add(orgInfo);
				}
			}
			
			return flg;
		}catch(IllegalArgumentException e){
			throw new ManoeuvreException("参数异常",e);
		}catch(RuntimeException e){
			throw new ManoeuvreException("新增审核设置信息失败",e);
		}
	}

	@Override
	public AuditConfiguration getInfoById(String aid)
			throws ManoeuvreException {
		try{
			return auditConfigurationBusiness.getInfoById(aid);
		}catch(IllegalArgumentException e){
			throw new ManoeuvreException("参数异常",e);
		}catch(RuntimeException e){
			throw new ManoeuvreException("查找审核设置信息失败",e);
		}
	}

	@Override
	public List<AuditConfiguration> getList(AuditConfigurationQuery query)
			throws ManoeuvreException {
		try{
			return auditConfigurationBusiness.getList(query);
		}catch(IllegalArgumentException e){
			throw new ManoeuvreException("参数异常",e);
		}catch(RuntimeException e){
			throw new ManoeuvreException("查询审核设置信息失败",e);
		}
	}

	@Override
	public PageList<AuditConfiguration> getPage(AuditConfigurationQuery query)
			throws ManoeuvreException {
		try{
			return auditConfigurationBusiness.getPage(query);
		}catch(IllegalArgumentException e){
			throw new ManoeuvreException("参数异常",e);
		}catch(RuntimeException e){
			throw new ManoeuvreException("查询审核设置信息失败",e);
		}
	}

	@Override
	public boolean modify(AuditConfiguration info) throws ManoeuvreException {
		try{
			return auditConfigurationBusiness.modify(info);
		}catch(IllegalArgumentException e){
			throw new ManoeuvreException("参数异常",e);
		}catch(RuntimeException e){
			throw new ManoeuvreException("修改审核设置信息失败",e);
		}
	}

	@Override
	public void remove(String aid) throws ManoeuvreException {
		try{
			auditConfigOrgBusiness.removeByAid(aid);
			auditConfigurationBusiness.remove(aid);
		}catch(IllegalArgumentException e){
			throw new ManoeuvreException("参数异常",e);
		}catch(RuntimeException e){
			throw new ManoeuvreException("删除审核设置信息失败",e);
		}
	}

	/**
	 * 取得角色包含的人员
	 */
	@Override
	public List<String> findPersonByRole(AuditConfiguration info) {
		return auditConfigurationBusiness.findPersonByRole(info);
	}

	/**
	 * 取得角色
	 */
	@Override
	public List<Map<String, String>> getRoles() {
		return auditConfigurationBusiness.getRoles();
	}

	//----------------------------------业务扩充------------------------------------
	@SuppressWarnings("unchecked")
	private List<String> getSjfw(AuditConfiguration info) {
		
		DynaBean dynaBean = DynaBeanUtil.getPerson(info.getAssessor());
		Object dwm = null;
		List<String> dws = new ArrayList<String>();
		if (dynaBean == null || dynaBean.getValue("dwm") == null) {
			dwm = null;
		} else {
			dwm = dynaBean.getValue("dwm");
		}
		
		if ("2".equals(info.getAudittype())) {
			if(dwm == null) {
				return null;
			} else {
				dws.add(dwm.toString());
			}
			return dws;
		}
		
		IDataPrivilegeDao dataPrivilegeDao = (IDataPrivilegeDao)SpringHolder.getBean("dataPrivilegeDao");
		DataPrivilege query = new DataPrivilege();
		query.setUserId(info.getAssessor());
		query.setRoleId(info.getRole());
		query.setFilterId("bmgl");
		List<DataPrivilege> dataPrivileges = dataPrivilegeDao.findByQuery(query);
		
		// 默认查询的是本部门
		if(dataPrivileges.size() <= 0) {
			// 无
			if(dwm == null) {
				return null;
			} else {
				dws.add(dwm.toString());
			}
			return dws;
		}

		//开始查看权限，并组装数据权限
		for (DataPrivilege dataPrivilege : dataPrivileges) {
			// 将xml解析成对象
			DeptFilter deptFilter = (DeptFilter)JaxbUtil.getObjectFromXml(dataPrivilege.getXmlValue(), DeptFilter.class);
			
			String typeObj = deptFilter.getDataType();//取得部门过滤类型
			Object objlist = deptFilter.getOrgList();//取得组织机构代码列表
			
			if (typeObj != null) {
				if (DealDeptFilter.TYPE_SELF.equalsIgnoreCase(typeObj.toString())) {//本部门
					if (dwm == null && dataPrivileges.size() <= 1) {
						return null;
					}
					dws.add(dwm.toString());
				} else if (DealDeptFilter.TYPE_IN.equalsIgnoreCase(typeObj.toString())) {//多个部门
					if (objlist != null) {
						dws.addAll((List<String>)objlist);
					} else {
						return null;
					}
				} else {//全部
					List<Item> list = CodeUtil.getChildren("DM_DEF_ORG", null);
					dws.addAll(getDws(list));
				}
			}
		}
		
		return dws;
	}
	
	private List<String> getDws(List<Item> list) {
		List<String> dws = new ArrayList<String>();
		
		for (Item i : list) {
			if (i.getChildren() != null && i.getChildren().size() > 0) {
				dws.addAll(getDws(i.getChildren()));
			} else {
				dws.add(i.getGuid());
			}
		}

		return dws;
	}
	
}
