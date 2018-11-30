package com.zfsoft.dao.query;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zfsoft.common.query.QueryModel;
import com.zfsoft.dao.annotation.MybatisBeanContext;

public class BeanQueryMap<K, V> extends QueryModel implements Map<K, V>{
	
	private static final long serialVersionUID = 1L;
	
	private Map<K, V> map=new HashMap<K, V>();
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
	
	/**
	 * 获取主键本地名称
	 * @return
	 */
	public String getKeyOfLocal(){
		return getSqlLocalNameMap().get(getKeyOf());
	}
	@Override
	public int size() {
		return map.size();
	}
	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}
	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}
	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}
	@Override
	public V get(Object key) {
		return map.get(key);
	}
	@Override
	public V put(K key, V value) {
		return map.put(key, value);
	}
	@Override
	public V remove(Object key) {
		return map.remove(key);
	}
	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		map.putAll(m);
	}
	@Override
	public void clear() {
		map.clear();
	}
	@Override
	public Set<K> keySet() {
		return map.keySet();
	}
	@Override
	public Collection<V> values() {
		return map.values();
	}
	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return map.entrySet();
	}
}
