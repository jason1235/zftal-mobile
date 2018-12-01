package com.zfsoft.dao.query;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.annotation.MybatisBeanContext;

public class BeanQueryV2 extends BaseQuery{
	
	private static final long serialVersionUID = 1L;
	/**
	 * 查询的对象类型
	 */
	private Class<?> clasz;
	
	public void setQueryClass(Class<?> clasz){
		this.clasz= clasz;
	}
	/**
	 * sql字段 和本地字段的映射
	 * 查询时候使用
	 * @return
	 */
	public Map<String, String> getSqlLocalNameMap() {
		if(clasz==null){
			throw new RuntimeException("query对象未配置查询返回类型，示例query.setQueryClass(Bean.class)");
		}
		return MybatisBeanContext.getClassContextMap(clasz).getSqlLocalNameMap();
	}
	
	/**
	 * 返回所有的数据库名称序列
	 * 查询时使用
	 * @return
	 */
	public List<String> getSqlNamesAll() {
		if(clasz==null){
			throw new RuntimeException("query对象未配置查询返回类型，示例query.setQueryClass(Bean.class)");
		}
		return MybatisBeanContext.sqlNames(true, clasz);
	}
	/**
	 * 返回无主键值可为空的数据库名称序列
	 * 查询时使用
	 * @return
	 */
	public List<String> getSqlNamesNoKeyAndIsNull() {
		if(clasz==null){
			throw new RuntimeException("query对象未配置查询返回类型，示例query.setQueryClass(Bean.class)");
		}
		return MybatisBeanContext.sqlNames(false, clasz);
	}
	/**
	 * 通过Table注解获取表名
	 * @return
	 */
	public String getTableName(){
		return MybatisBeanContext.getClassContextMap(clasz).getTableName();
	}
	
	/**
	 * 通过Table注解获取主键
	 * @return
	 */
	public String getKeyOf(){
		return MybatisBeanContext.getClassContextMap(clasz).getKeyOf();
	}
}
