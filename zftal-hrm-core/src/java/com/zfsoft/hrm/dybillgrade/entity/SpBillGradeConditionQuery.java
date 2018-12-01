package com.zfsoft.hrm.dybillgrade.entity;

import com.zfsoft.dao.query.BaseQuery;

/**
 * 
 * @author ChenMinming
 * @date 2014-6-6
 * @version V1.0.0
 */
public class SpBillGradeConditionQuery extends BaseQuery{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9008189325761143524L;
	private String configId;
	private String billClassId;
	private int num=1;
	private String score;

	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getBillClassId() {
		return billClassId;
	}
	public void setBillClassId(String billClassId) {
		this.billClassId = billClassId;
	}
	/**
	 * 返回
	 */
	public String getConfigId() {
		return configId;
	}
	/**
	 * 设置
	 * @param configId 
	 */
	public void setConfigId(String configId) {
		this.configId = configId;
	}
}
