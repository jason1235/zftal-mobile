package com.zfsoft.dataprivilege.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.springframework.util.Assert;

import com.zfsoft.dataprivilege.dto.DeptFilter;
import com.zfsoft.dataprivilege.filter.impl.DealDeptFilter;
import com.zfsoft.dataprivilege.service.IDataPrivilegeService;
import com.zfsoft.dataprivilege.util.DeptFilterUtil;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.util.DynaBeanUtil;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.config.ICodeConstants;
import com.zfsoft.util.base.StringUtil;

public class DeptFilterAction extends HrmAction {

	private static final long serialVersionUID = 2561693364736209075L;
	
	private String[] selectId;
	
	private DeptFilter deptFilter = new DeptFilter();
	
	private String viewType;
	
	private IDataPrivilegeService dataPrivilegeService;
	
	
	public String save(){
		try{
			if(selectId != null && selectId.length > 0){
				Assert.isTrue(!StringUtil.isEmpty(deptFilter.getUserId()), "用户编号不可为空");
				Assert.isTrue(!StringUtil.isEmpty(deptFilter.getRoleId()), "用户角色不可为空");
				Assert.isTrue(!StringUtil.isEmpty(deptFilter.getDataType()), "数据范围类型不可为空");
				Assert.isTrue(DealDeptFilter.TYPE_SELF.equals(deptFilter.getDataType()) || DealDeptFilter.TYPE_IN.equals(deptFilter.getDataType()) || DealDeptFilter.TYPE_ALL.equals(deptFilter.getDataType()), "数据范围类型不正确");
				List<String> orgList = new ArrayList<String>();
				for (String str : selectId) {
					orgList.add(str);
				}
				deptFilter.setOrgList(orgList);
			}
			else{
				deptFilter.setDataType(DealDeptFilter.TYPE_IN);
				deptFilter.setOrgList(null);
			}
			dataPrivilegeService.saveValue(deptFilter);
			setSuccessMessage("保存成功");
		}catch (Exception e) {
			setErrorMessage("保存失败:" + e.getMessage());
		}
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String remove(){
		try{
			Assert.isTrue(!StringUtil.isEmpty(deptFilter.getUserId()), "用户编号不可为空");
			Assert.isTrue(!StringUtil.isEmpty(deptFilter.getRoleId()), "用户角色不可为空");
			dataPrivilegeService.deleteDataPrivilege(deptFilter);
			setSuccessMessage("删除成功");
		}catch (Exception e) {
			setErrorMessage("删除失败:" + e.getMessage());
		}
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String load(){
		String dwm="";
		DynaBean dynaBean=DynaBeanUtil.getPerson(deptFilter.getUserId());
		if(dynaBean==null||dynaBean.getValue("dwm")==null){
			dwm = "";
		}else{
			dwm = (String)dynaBean.getValue("dwm");
		}
		
		getFilter();
		HashSet<String> orgSet = new HashSet<String>();
		for (String str : deptFilter.getOrgList()) {
			orgSet.add(str);
		}
//		String html = DeptFilterUtil.getOrgRootAndSelfTreeHtml(deptFilter.getDataType(),orgSet);
		String html = DeptFilterUtil.getOrgRootAndSelfTreeHtml(deptFilter.getDataType(),orgSet,CodeUtil.getItemValue(ICodeConstants.DM_DEF_ORG, dwm));
		getValueStack().set("orghtml", html);
		getValueStack().set("orgUrl", "/dataprivilege/deptFilter_save.html?deptFilter.userId="+deptFilter.getUserId()+"&deptFilter.roleId="+deptFilter.getRoleId());
		return LIST_PAGE;
	}
	
	public String getOrgsText(){
		getFilter();
		HashMap<String, String> datatips = new HashMap<String, String>();
		datatips.put("simple", deptFilter.getViewOrgs(9));
		datatips.put("whole", deptFilter.getWholeOrgsText());
		//getValueStack().set(DATA,deptFilter.getOrgsText());
		getValueStack().set(DATA,datatips);
		return DATA;
	}
	
	public String getOrgsTextForRow(){
		getFilter();
		HashMap<String, String> datatips = new HashMap<String, String>();
		datatips.put("simple", deptFilter.getViewOrgs(35));
		datatips.put("whole", deptFilter.getWholeOrgsText());
		//getValueStack().set(DATA,deptFilter.getOrgsTextForRow());
		getValueStack().set(DATA,datatips);
		return DATA;
	}
	
	private void getFilter(){
		Assert.isTrue(!StringUtil.isEmpty(deptFilter.getUserId()), "用户编号不可为空");
		Assert.isTrue(!StringUtil.isEmpty(deptFilter.getRoleId()), "用户角色不可为空");
		deptFilter = (DeptFilter) dataPrivilegeService.getValueObject(deptFilter);
	}

	/**
	 * 返回
	 * @return 
	 */
	public String[] getSelectId() {
		return selectId;
	}

	/**
	 * 设置
	 * @param selectId 
	 */
	public void setSelectId(String[] selectId) {
		this.selectId = selectId;
	}

	/**
	 * 返回
	 * @return 
	 */
	public DeptFilter getDeptFilter() {
		return deptFilter;
	}

	/**
	 * 设置
	 * @param deptFilter 
	 */
	public void setDeptFilter(DeptFilter deptFilter) {
		this.deptFilter = deptFilter;
	}

	/**
	 * 返回
	 * @return 
	 */
	public IDataPrivilegeService getDataPrivilegeService() {
		return dataPrivilegeService;
	}

	/**
	 * 设置
	 * @param dataPrivilegeService 
	 */
	public void setDataPrivilegeService(IDataPrivilegeService dataPrivilegeService) {
		this.dataPrivilegeService = dataPrivilegeService;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getViewType() {
		return viewType;
	}

	/**
	 * 设置
	 * @param viewType 
	 */
	public void setViewType(String viewType) {
		this.viewType = viewType;
	}
	
	

}
