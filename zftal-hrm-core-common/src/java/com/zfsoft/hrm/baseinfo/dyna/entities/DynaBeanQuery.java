package com.zfsoft.hrm.baseinfo.dyna.entities;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.zfsoft.dao.query.BaseQuery;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.core.util.SQLExplainUtil;

/** 
 * 动态数据查询实体
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-20
 * @version V1.0.0
 */
public class DynaBeanQuery extends BaseQuery {
	
	private static final long serialVersionUID = 2910409006945550692L;
	
	private InfoClass clazz;			//实体类描述

	private String express;				//简单查询条件表达式，如：gh ## #{gh}
	
	private Map<String,Object> params = new HashMap<String,Object>();	//条件参数，如：{gh, '%01%'}
	
	private String fuzzyValue;			//模糊查询的值
	
	private boolean history;			//是否包含历史记录
	
	private Boolean deleted = false;	//是否已删除，默认为false
	
	private Date snapTime;				//
	private Date snapTimeStart;				//
	private Date snapTimeEnd;				//
	
	/**
	 * 构造函数,分页数为2,用于常用查询
	 * @param clazz 实体类描述
	 */
	public DynaBeanQuery( InfoClass clazz ) {
		setPerPageSize(2);
		this.clazz = clazz;
	}
	public DynaBeanQuery() {
		this.setPerPageSize(15);
	}


	/**
	 * 返回是否包含历史记录
	 */
	public boolean isHistory() {
		return history;
	}

	/**
	 * 设置是否包含历史记录
	 * @param history 是否包含历史记录
	 */
	public void setHistory(boolean history) {
		this.history = history;
	}

	/**
	 * 返回实体类描述
	 * @return
	 */
	public InfoClass getClazz() {
		return clazz;
	}

	public void setClazz(InfoClass clazz) {
		this.clazz = clazz;
	}

	/**
	 * 返回模糊搜索参数
	 * @return
	 */
	public String getFuzzyValue() {
		return fuzzyValue;
	}

	/**
	 * 设置迷糊搜索参数
	 * @param fuzzyValue 模糊搜索参数
	 */
	public void setFuzzyValue(String fuzzyValue) {
		this.fuzzyValue = fuzzyValue;
	}

	/**
	 * 返回条件表达式(已解析)
	 */
	public String getExpress() {
		return SQLExplainUtil.parseExpress(express);
	}

	/**
	 * 设置条件表达式
	 * @param express 条件表达式
	 */
	public void setExpress(String express) {
		this.express = express;
	}

	/**
	 * 返回条件表达式参数
	 */
	public Map<String, Object> getParams() {
		return params;
	}

	/**
	 * 设置条件表达式的参数
	 * @param params 条件表达式的参数
	 */
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	
	/**
	 * 设置条件表达式的参数
	 * @param param 参数索引
	 * @param value 参数值
	 */
	public void setParam( String param, String value ) {
		this.params.put( param, value );
	}

	/**
	 * 返回是否已删除，默认为false
	 */
	public Boolean isDeleted() {
		return deleted;
	}

	public Boolean getDeleted() {
		return deleted;
	}
	/**
	 * 设置是否已删除，默认为false
	 * @param deleted 是否已删除
	 */
	public void setDeleted( Boolean deleted ) {
		this.deleted = deleted;
	}
	/**
	 * @return the snapTime
	 */
	public Date getSnapTime() {
		return snapTime;
	}
	/**
	 * @param snapTime the snapTime to set
	 */
	public void setSnapTime(Date snapTime) {
		this.snapTime = snapTime;
	}
	public Date getSnapTimeStart() {
		return snapTimeStart;
	}
	public void setSnapTimeStart(Date snapTimeStart) {
		this.snapTimeStart = snapTimeStart;
	}
	public Date getSnapTimeEnd() {
		return snapTimeEnd;
	}
	public void setSnapTimeEnd(Date snapTimeEnd) {
		this.snapTimeEnd = snapTimeEnd;
	}
	
}
