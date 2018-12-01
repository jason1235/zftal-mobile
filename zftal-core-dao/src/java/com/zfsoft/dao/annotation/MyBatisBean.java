package com.zfsoft.dao.annotation;

import java.util.List;
import java.util.Map;
/**
 * 为了便于mybatis配置文件编写而构建的类，提供获取所有sql字段的方法等
 * @author 沈鲁威 Patrick Shen
 * @since 2012-8-8
 * @version V1.0.0
 */
public class MyBatisBean{
	public MyBatisBean(){
		//注册到MybatisBeanContext
		MybatisBeanContext.getClassContextMap(this.getClass());
	}
	
	/**
	 * 返回所有的数据库名称序列
	 * 插入和更新时候使用
	 * @return
	 */
	public List<String> getSqlNamesAll() {
		return MybatisBeanContext.sqlNames(true,this.getClass());
	}
	/**
	 * 返回无主键值可为空的数据库名称序列
	 * 插入和更新时候使用
	 * @return
	 */
	public List<String> getSqlNamesNoKeyAndIsNull() {
		return MybatisBeanContext.sqlNames(false,this.getClass());
	}
	/**
	 * 返回有主键值不为空的数据库名称序列
	 * 插入和更新时候使用
	 * @return
	 */
	public List<String> getSqlNamesHasKeyAndNotNull() {
		return MybatisBeanContext.sqlNames(true,false,this);
	}
	/**
	 * 返回无主键值不为空的数据库名称序列
	 * 插入和更新时候使用
	 * @return
	 */
	public List<String> getSqlNamesNoKeyAndNotNull() {
		return MybatisBeanContext.sqlNames(false,false,this);
	}
	
	/**
	 * sql字段 和本地字段的映射
	 * 一般在更新时候使用
	 * @return
	 */
	public Map<String, String> getSqlLocalNameMap() {
		return MybatisBeanContext.getClassContextMap(this.getClass()).getSqlLocalNameMap();
	}
	/**
	 * 获取表名
	 * @return
	 */
	public String getTableName(){
		return MybatisBeanContext.getClassContextMap(this.getClass()).getTableName();
	}
	/**
	 * 获取主键
	 * @return
	 */
	public String getKeyOf(){
		return MybatisBeanContext.getClassContextMap(this.getClass()).getKeyOf();
	}
	/**
	 * 获取主键本地名称
	 * @return
	 */
	public String getKeyOfLocal(){
		return getSqlLocalNameMap().get(getKeyOf());
	}
	
}
