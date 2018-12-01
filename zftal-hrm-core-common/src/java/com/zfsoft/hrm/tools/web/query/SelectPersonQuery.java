package com.zfsoft.hrm.tools.web.query;

import java.util.HashMap;
import java.util.Map;

import com.zfsoft.dao.query.BaseQuery;
import com.zfsoft.dataprivilege.util.DeptFilterUtil;
import com.zfsoft.hrm.config.SearchCondition;
import com.zfsoft.hrm.config.SelectPersonConfig;

/**
 * 人员选择实体
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-10
 * @version V1.0.0
 */
public class SelectPersonQuery extends BaseQuery {

	private static final long serialVersionUID = -7890956343274731722L;
	
	private SelectPersonConfig config;		//人员选择配置信息
	
	private Map<String, Object> values;		//查询条件
	
	/**
	 * 构造函数
	 */
	public SelectPersonQuery() {
		config = new SelectPersonConfig();
		values = new HashMap<String, Object>();
		setPerPageSize(11);
	}
	
	/**
	 * 返回条件表达式
	 * @return
	 */
	public String getCondition() {
		String out = "1 == 1";
		
		for ( SearchCondition condition : config.getConditions() ) {
			Object value = values.get( condition.getName() );
			
			if( value == null || "".equals(value) ) {
				continue;
			}
			
			out += " and " + condition.getCondition();
		}
		
		out += " and " + DeptFilterUtil.getCondition("", "t.dwm");
		
		return out;
	}
	
	/**
	 * 返回人员选择配置信息
	 */
	public SelectPersonConfig getConfig() {
		return config;
	}

	/**
	 * 设置人员选择配置信息
	 * @param config 人员选择配置信息
	 */
	public void setConfig(SelectPersonConfig config) {
		this.config = config;
	}

	/**
	 * 返回查询条件
	 */
	public Map<String, Object> getValues() {
		return values;
	}

	/**
	 * 设置查询条件
	 * @param values 查询条件
	 */
	public void setValues(Map<String, Object> values) {
		this.values = values;
	}
	
}
