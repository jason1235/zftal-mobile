package com.zfsoft.hrm.pendingAffair.entities;

import java.util.List;

import com.zfsoft.dao.annotation.MyBatisBean;
import com.zfsoft.dao.annotation.SQLField;
import com.zfsoft.dao.annotation.Table;

@Table("hrm_xtgl_dbsy")
public class PendingAffairInfo extends MyBatisBean {
	@SQLField(key=true)
	private String id;			//编号
	@SQLField("dbsym")
	private String affairName;	//待办事宜名称
	@SQLField("ywid")
	private String businessId;	//业务ID
	@SQLField("cdlj")
	private String menu;		//链接菜单
	@SQLField("dblx")
	private String affairType;		//类型
	@SQLField("dbzt")
	private int status;			//1已处理，0未处理
	@SQLField("glr")
	private String userId;		//关联人
	@SQLField("jsdm")
	private String roleId;		//角色编码
	@SQLField("bm_id")
	private String deptId;		//部门ID
	
	private String expression; //附加sql查询条件（数据范围）
	
	private List<String> queryRoleIds;
	private int sumNumber;            //代办事宜汇总数量
	/**
	 * @return businessId : return the property businessId.
	 */
	
	public String getBusinessId() {
		return businessId;
	}

	/**
	 * @param businessId : set the property businessId.
	 */
	
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	public String getAffairName() {
		return affairName;
	}

	public void setAffairName(String affairName) {
		this.affairName = affairName;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getMenu() {
		return menu;
	}

	/**
	 * 设置
	 * @param menu 
	 */
	public void setMenu(String menu) {
		this.menu = menu;
	}

	/**
	 * @return affairType : return the property affairType.
	 */
	
	public String getAffairType() {
		return affairType;
	}

	/**
	 * @param affairType : set the property affairType.
	 */
	
	public void setAffairType(String affairType) {
		this.affairType = affairType;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the roleId
	 */
	public String getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the queryRoleIds
	 */
	public List<String> getQueryRoleIds() {
		return queryRoleIds;
	}

	/**
	 * @param queryRoleIds the queryRoleIds to set
	 */
	public void setQueryRoleIds(List<String> queryRoleIds) {
		this.queryRoleIds = queryRoleIds;
	}

	/**
	 * @return sumNumber : return the property sumNumber.
	 */
	
	public int getSumNumber() {
		return sumNumber;
	}

	/**
	 * @param sumNumber : set the property sumNumber.
	 */
	
	public void setSumNumber(int sumNumber) {
		this.sumNumber = sumNumber;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	

}
