package com.zfsoft.hrm.baseinfo.org.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.zfsoft.util.base.StringUtil;

public class OrgPeople implements Serializable {

	private static final long serialVersionUID = -1886968728342438228L;
	
	private String staffids;//待移动人员编号字符串
	
	private String planOrg;//目标部门
	
	private List<String> staffidList = new ArrayList<String>();//待移动人员编号列表
	
	public void initStaffidList(){
		if(StringUtil.isEmpty(staffids) || ";".equals(staffids)){
			return;
		}
		if(staffidList.size() > 0){
			staffidList = new ArrayList<String>();
		}
		String[] staffArr = staffids.split(";");
		for (String sid : staffArr) {
			staffidList.add(sid);
		}
	}
	
	/**
	 * 返回
	 * @return 
	 */
	public String getStaffids() {
		return staffids;
	}

	/**
	 * 设置
	 * @param staffids 
	 */
	public void setStaffids(String staffids) {
		this.staffids = staffids;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getPlanOrg() {
		return planOrg;
	}

	/**
	 * 设置
	 * @param planOrg 
	 */
	public void setPlanOrg(String planOrg) {
		this.planOrg = planOrg;
	}

	/**
	 * 返回
	 * @return 
	 */
	public List<String> getStaffidList() {
		return staffidList;
	}

	/**
	 * 设置
	 * @param staffidList 
	 */
	public void setStaffidList(List<String> staffidList) {
		this.staffidList = staffidList;
	}

}
