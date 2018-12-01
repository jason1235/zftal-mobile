package com.zfsoft.hrm.baseinfo.search.entities;


/**
 * 常用查询条件关系映射
 * @ClassName: CommonSearchRelation 
 * @author jinjj
 * @date 2012-6-15 下午03:58:48 
 *
 */
public class CommonSearchRelation {
	
	private String guid;					//全局ID			
	
	private String conditionId;					//条件ID

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getConditionId() {
		return conditionId;
	}

	public void setConditionId(String conditionId) {
		this.conditionId = conditionId;
	}
	
}
