package com.zfsoft.orcus.lang;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * 本地引用
 * <p>
 * 该类的一个实例代表指定类型的一个实例，即指定类型实例的引用，
 * 而被引用的指定类型实例可以是本地实例或全局实例。
 * </p>
 * <p>本地实例：与线程上下文类加载器相关联的对象实例。</p>
 * <p>全局实例：不与线程上下文类加载器相关联的对象实例。</p>
 * 
 * <pre>
 * 使用示例：
 * public class MyClass
 * {
 *      private static final LocalReference REF = 
 *         new LocalReference() {
 *            protected Object createInstance()
 *             {
 *                 return new MyClass();
 *             }
 *         };
 *      
 *      //阻止该类被用户实例化  
 *      private MyClass(){}
 *      
 *      //返回该类的本地实例
 *      public static MyClass getInstance()
 *      {
 *          return (MyClass)REF.get();
 *      }
 * }
 * </pre>
 * 
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-11
 * @version V1.0.0
 */
public abstract class LocalReference {
	
	private Map<ClassLoader, Object> _values = new WeakHashMap<ClassLoader, Object>();
	
	private boolean _globalInitialized = false;
	
	private Object _global;
	
	private boolean _localMode = true;

	/**
	 * 生成对象实例
	 * 
	 * <p>
	 * 该方法是用于新建指定类型实例的工厂方法，由该类的子类提供实现
	 * </p>
	 * @return 对象实例
	 */
	abstract protected Object createInstance();
	
	/**
	 * 返回是否是本地模式（缺省为true）
	 * 
	 * <p>如为本地模式，则方法#get()试图返回本地实例；</p>
	 * <p>如为全局模式，则方法#get()返回全局实例；</p>
	 * @return
	 */
	public boolean isLocalMode(){
		return _localMode;
	}
	
	/**
	 * 设置本地模式
	 * 
	 * <p>如为本地模式，则方法#get()试图返回本地实例；</p>
	 * <p>如为全局模式，则方法#get()返回全局实例；</p>
	 * @param localMode true-本地模式；false-全局模式
	 */
	public void setLocalMode( boolean localMode ) {
		_localMode = localMode;
	}
	
	/**
	 * 获取一个对象实例
	 * <p>如当前为本地模式，则方法<b>试图</b>返回本地实例(如果获取线程上下文类加载器失败则返回全局实例)；</p>
	 * <p>如当前为全局模式，则方法返回全局实例；</p>
	 * @return 实例对象
	 */
	public synchronized Object get() {
		_values.isEmpty();
		
		if( _localMode ) {
			try {
				ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
				
				if( contextClassLoader != null ) {
					Object value = _values.get(contextClassLoader);
					
					if(value == null && !_values.containsKey(contextClassLoader)) {
						value = createInstance();
						_values.put(contextClassLoader, value);
					}
					
					return value;
				}
			} catch (SecurityException e) {
				//忽略
			}
		}
		
		if( !_globalInitialized ) {
			_global = createInstance();
			_globalInitialized = true;
		}
		
		return _global;
	}
	
	/**
	 * 设置与当前线程上下文类加载器相关的本地实例，
	 * 如果获取线程上下文类加载器失败则方法不做任何事情。
	 * @param value 与当前线程上下文类加载器相关的本地实例
	 */
	public synchronized void set(Object value) {
		_values.isEmpty();
		
		try {
			ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
			
			if ( contextClassLoader != null ) {
				_values.put(contextClassLoader, value);
				return;
			}
		} catch (SecurityException e) {
			// 忽略
		}
	}
	
	/**
	 * 移除与当前线程上下文类加载器相联系的本地实例，
	 * 如果获取线程上下文类加载器失败则方法不做任何事情。
	 */
	public synchronized void unset() {
		try {
			ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
			
			unset(contextClassLoader);
		} catch (SecurityException e) {
			// 忽略
		}
	}
	
	/**
	 * 移除与指定类加载器相联系的对象实例
	 * @param classLoader 
	 */
	public synchronized void unset(ClassLoader classLoader) {
		_values.remove(classLoader);
	}
}
