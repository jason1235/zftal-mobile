package com.zfsoft.hrm.normal.info.query;

import java.util.Map;

import com.zfsoft.dao.query.BaseQuery;

/**
 * 个人综合信息查询条件实体
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-15
 * @version V1.0.0
 */
public class OverallInfoQuery extends BaseQuery {
	
	private static final long serialVersionUID = -1634778856596674209L;

	private String condition;		//条件表达式
	
	private Map<String,Object> props;		//条件参数

	/**
	 * 返回条件表达式
	 */
	public String getCondition() {
		return condition;
	}

	/**
	 * 设置条件表达式
	 * @param condition 条件表达式
	 */
	public void setCondition(String condition) {
		this.condition = condition;
	}

	/**
	 * 获取参数集合
	 * @return
	 */
	public Map<String, Object> getProps() {
		return props;
	}

	/**
	 * 设置参数集合
	 * @param props
	 */
	public void setProps(Map<String, Object> props) {
		this.props = props;
	}
}
