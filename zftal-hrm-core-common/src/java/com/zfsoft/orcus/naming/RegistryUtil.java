package com.zfsoft.orcus.naming;

import com.zfsoft.orcus.lang.LocalReference;

/**
 * 注册表工具类，提供设置和返回系统注册表的功能
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-18
 * @version V1.0.0
 */
public class RegistryUtil {

	private static final LocalReference REF = new LocalReference() {

		@Override
		protected Object createInstance() {
			return new RegistryUtil();
		}
		
	};
	
	private Registry _registry;
	
	/**
	 * （私有）构造函数
	 */
	private RegistryUtil() {
		
		_registry = new SimpleRegistry();
	}
	
	private static RegistryUtil getInstance() {
		return (RegistryUtil) REF.get();
	}
	
	/**
	 * 返回系统注册表；缺省系统注册表为{@link SimpleRegistry}
	 * @return 注册表
	 */
	private Registry getDefault() {
		
		return _registry;
	}
	
	/**
	 * 设置系统注册表
	 * @param registry 注册表
	 * @throws RuntimeException 如果registry==null
	 */
	private void setDefault(Registry registry) {
		if( registry == null ) {
			throw new RuntimeException("parameter [registry] is required; it cannot be null.");
		}
		
		_registry = registry;
	}
	
	/**
	 * 返回系统注册表；缺省系统注册表为{@link SimpleRegistry}
	 * @return 注册表
	 */
	public static Registry getRegistry() {
		
		return getInstance().getDefault();
	}
	
	/**
	 * 设置系统注册表
	 * @param registry 注册表
	 * @throws RuntimeException 如果registry==null
	 */
	public static void setRegistry(Registry registry) {
		getInstance().setDefault(registry);
	}
}
