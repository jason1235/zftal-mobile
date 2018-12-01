package com.zfsoft.dao.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.common.query.ModelBase;
import com.zfsoft.util.base.StringUtil;
 

/**
 * 
 * 类名称：YhsjfwModel 
 * 类描述：用户数据范围
 * 创建人：caozf
 * 创建时间：2012-7-10
 */
public class YhjsfwModel extends ModelBase
{
	private static final long serialVersionUID = 8330994734408809557L;
	private String yhsjfwb_id;    //用户数据范围ID 
	private String yh_id;         //用户ID   
	private String js_id;         //角色ID  
	private String sjfwz_id;      //数据范围组ID
	private String sjfwzmc;       //数据范围组名称
	private String sjfwztj;        //数据范围条件
	
	private List<String> lists;    //数据范围条件集合，mybatis遍历集合用
	
	private String bmdm_ids; 	 //部门、学院代码
	private String bmdm_njdm_ids;//部门年级代码	
	private String zydm_ids;     //专业代码
	private String zydm_njdm_ids;//专业年级代码
	private String bjdm_ids;	 //班级代码
	private String xxdm_ids;     //学校代码
	
	private String bmdm_ids_not; //未选中学院代码
	private String zydm_ids_not; //未选中专业年级代码
	private String bjdm_ids_not; //未选中班级代码
	private String bmdm_njdm_ids_not;//未选中学院年级代码
	private String zydm_njdm_ids_not;//未选中专业年级代码
	
	public String getYhsjfwb_id() {
		return yhsjfwb_id;
	}

	public void setYhsjfwb_id(String yhsjfwbId) {
		yhsjfwb_id = yhsjfwbId;
	}

	public String getYh_id() {
		return yh_id;
	}

	public void setYh_id(String yhId) {
		yh_id = yhId;
	}

	public String getJs_id() {
		return js_id;
	}

	public void setJs_id(String jsId) {
		js_id = jsId;
	}

	public String getSjfwz_id() {
		return sjfwz_id;
	}

	public void setSjfwz_id(String sjfwzId) {
		sjfwz_id = sjfwzId;
	}

	public String getBmdm_ids() {
		return bmdm_ids;
	}

	public void setBmdm_ids(String bmdmIds) {
		bmdm_ids = StringUtil.removeLast(bmdmIds);
	}

	public String getBmdm_njdm_ids() {
		return bmdm_njdm_ids;
	}

	public void setBmdm_njdm_ids(String bmdmNjdmIds) {
		bmdm_njdm_ids = StringUtil.removeLast(bmdmNjdmIds);
	}

	public String getZydm_ids() {
		return zydm_ids;
	}

	public void setZydm_ids(String zydmIds) {
		zydm_ids = StringUtil.removeLast(zydmIds);
	}

	public String getZydm_njdm_ids() {
		return zydm_njdm_ids;
	}

	public void setZydm_njdm_ids(String zydmNjdmIds) {
		zydm_njdm_ids = StringUtil.removeLast(zydmNjdmIds);
	}

	public String getBjdm_ids() {
		return bjdm_ids;
	}

	public void setBjdm_ids(String bjdmIds) {
		bjdm_ids = StringUtil.removeLast(bjdmIds);
	}

	public String getSjfwzmc() {
		return sjfwzmc;
	}

	public void setSjfwzmc(String sjfwzmc) {
		this.sjfwzmc = sjfwzmc;
	}
	public String getSjfwztj() {
		return sjfwztj;
	}

	public void setSjfwztj(String sjfwztj) {
		this.sjfwztj = sjfwztj;
	}

	public String getBmdm_ids_not() {
		return bmdm_ids_not;
	}

	public void setBmdm_ids_not(String bmdmIdsNot) {
		bmdm_ids_not = bmdmIdsNot;
	}

	public String getZydm_ids_not() {
		return zydm_ids_not;
	}

	public void setZydm_ids_not(String zydmIdsNot) {
		zydm_ids_not = zydmIdsNot;
	}

	public String getBjdm_ids_not() {
		return bjdm_ids_not;
	}

	public void setBjdm_ids_not(String bjdmIdsNot) {
		bjdm_ids_not = bjdmIdsNot;
	}

	public String getBmdm_njdm_ids_not() {
		return bmdm_njdm_ids_not;
	}

	public void setBmdm_njdm_ids_not(String bmdmNjdmIdsNot) {
		bmdm_njdm_ids_not = bmdmNjdmIdsNot;
	}

	public String getZydm_njdm_ids_not() {
		return zydm_njdm_ids_not;
	}

	public void setZydm_njdm_ids_not(String zydmNjdmIdsNot) {
		zydm_njdm_ids_not = zydmNjdmIdsNot;
	}
	public List<String> getLists() {
		return lists;
	}

	public void setLists(List<String> lists) {
		this.lists = lists;
	}

	public String getXxdm_ids() {
		return xxdm_ids;
	}

	public void setXxdm_ids(String xxdmIds) {
		xxdm_ids = xxdmIds;
	}
 
	
}
