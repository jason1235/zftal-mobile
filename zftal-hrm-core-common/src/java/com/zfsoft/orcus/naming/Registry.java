package com.zfsoft.orcus.naming;

import java.util.List;

/**
 * 对象注册表
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-18
 * @version V1.0.0
 */
public interface Registry {

	/**
	 * 在注册表中注册(绑定)一个对象
	 * @param name 绑定对象的名字
	 * @param object 绑定对象
	 * @throws RunTimeExcepiton 如果name == null 或 object== null
	 * @throws BindingAlreadExistsException 如果同名绑定对象已存在
	 * @throws BindingFailedException 注册处理失败
	 */
	public void bind(String name, Object object) throws BindingAlreadExistsException, BindingFailedException;
	
	/**
	 * 在注册表中重新注册(绑定)一个对象
	 * <p>
	 * 如果绑定已存在则替换原来的绑定，如果不存在则新增一个绑定
	 * </p>
	 * @param name 绑定对象的名字
	 * @param object 绑定对象
	 * @throws RunTimeExcepiton 如果name == null 或 object== null
	 * @throws BindingFailedException 注册处理失败
	 */
	public void rebind(String name, Object object) throws BindingFailedException;
	
	/**
	 * 在注册表中注销一个对象
	 * @param name 绑定对象的名字
	 * @return 返回被注销的绑定对象，如果绑定不存在，则返回null
	 * @throws RunTimeExcepiton 如果name == null
	 * @throws UnbindingFailedException 注销处理失败
	 */
	public Object unbind( String name ) throws UnbindingFailedException;
	
	/**
	 * 在注册表中查询一个对象
	 * @param name 绑定对象的名字
	 * @return 返回被查询的对象，如果被查询的对象不存在，则返回null
	 * @throws RunTimeExcepiton 如果name == null
	 * @throws RegistryException 如果查询失败
	 */
	public Object lookup( String name ) throws RegistryException;
	
	/**
	 * 获取注册表中指定目录下所有绑定对象的名字
	 * @param dir 目录名
	 * @return 返回指定目录下所有绑定对象的名字，如果指定目录下没有绑定对象，则返回new ArrayList(0)
	 * @throws RunTimeExcepiton 如果dir == null
	 * @throws RegistryException 如果处理失败
	 */
	public List<String> list( String dir ) throws RegistryException;
}
