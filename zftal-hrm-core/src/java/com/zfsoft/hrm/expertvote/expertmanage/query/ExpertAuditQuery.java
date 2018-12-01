package com.zfsoft.hrm.expertvote.expertmanage.query;

import com.zfsoft.dao.query.BaseQuery;

/**
 * 专家推荐审核查询
 * @author: xiaoyongjun
 * @since: 2014-3-14 下午12:37:49
 */
public class ExpertAuditQuery extends BaseQuery {

	private static final long serialVersionUID = -450747548035665970L;
	private String gh;       //工号
	private String name;	//姓名
	private String status;	//状态
	private String express;//流程表达式
	private String express2;//数据授权

	public String getGh() {
		return gh;
	}

	public void setGh(String gh) {
		this.gh = gh;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getExpress() {
		return express;
	}

	public void setExpress(String express) {
		this.express = express;
	}

	public String getExpress2() {
		return express2;
	}

	public void setExpress2(String express2) {
		this.express2 = express2;
	}

	/**
	 * 返回
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置
	 * @param name 
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}
