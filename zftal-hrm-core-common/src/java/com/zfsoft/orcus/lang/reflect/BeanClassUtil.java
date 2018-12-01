package com.zfsoft.orcus.lang.reflect;

import java.util.HashMap;
import java.util.Map;

import com.zfsoft.orcus.lang.LocalReference;

/**
 * JavaBean描述工具类，提供获取、解析、注册、注销JavaBean描述的功能。
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-8
 * @version V1.0.0
 */
public class BeanClassUtil {
	
	/**
	 * 获取JavaBean描述
	 * 
	 * <p>
	 * 如果JavaBean描述已注册，则返回注册的JavaBean描述；<br>
	 * 如果JavaBean描述未注册且类型为非动态JavaBean，则解析及自动注册JavaBean描述，并返回该JavaBean描述；<br>
	 * 如果JavaBean描述未注册且类型为动态JavaBean，则抛出{@link java.lang.ClassNotFoundException}
	 * </p>
	 * @param className 类名，不得为null
	 * @return JavaBean描述
	 * @throws ClassNotFoundException 如果className指定的类不存在
	 * @throws BeanIntrospectionException 如果解析失败
	 */
	public static BeanClass getBeanClass(String className) throws ClassNotFoundException, BeanIntrospectionException {
		return getActor().getBeanClass(className);
	}
	
	/**
	 * 注册JavaBean描述
	 * @param beanClass JavaBean描述
	 * @throws RuntimeException 如果JavaBean==null
	 * @throws BeanClassAlreadyExistsException (runtime)如果beanClass已存在
	 */
	public static void register(BeanClass beanClass) {
		getActor().register(beanClass);
	}
	
	/**
	 * 注销JavaBean描述
	 * @param className 类名
	 * @return 被注销的JavaBean描述，null表示指定的JavaBean描述不存在
	 * @throws RuntimeException 如果className==null
	 */
	public static BeanClass deregister( String className ) {
		return getActor().deregister(className); 
	}
	 
	/**
	 * 判断指定的JavaBean描述是否存在
	 * @param className 类名， 不得为空
	 * @return true表示指定的JavaBean描述存在，false表示指定的JavaBean描述不存在。
	 * @throws RuntimeException 如果class==null
	 */
	public static boolean contains(String className) {
		return getActor().contains(className);
	}
	
	private static final LocalReference REF = new LocalReference() {
		
		@Override
		protected Object createInstance() {
			return new BeanClassUtil().new Actor();
		}
	};
	
	private static Actor getActor() {
		return (Actor)REF.get();
	}
	
	private class Actor {
		
		private Map<String, BeanClass> _cache = new HashMap<String, BeanClass>();
		
		private BeanClass getBeanClass(String className) throws BeanIntrospectionException, ClassNotFoundException {
			if( className == null || "".equals(className) ) {
				throw new RuntimeException("parameter [className] is required; it cannot be null.");
			}
			
			BeanClass clazz = (BeanClass) _cache.get(className);
			
			if( clazz == null ) {
				clazz = new GenericBeanClass(SingletonClassLoader.getInstance().loadClass(className));
				_cache.put(className, clazz);
			}
			
			return clazz;
		}
		
		private void register( BeanClass beanClass ) {
			if( beanClass == null ) {
				throw new RuntimeException("parameter [beanClass] is required; it cannot be null.");
			}
			
			if( _cache.containsKey( beanClass.type().getQulifiedName() ) ) {
				throw new TypeAlreardyExistsException();
			}
			
			_cache.put(beanClass.type().getQulifiedName(), beanClass);
			
			if( !TypeUtil.contains( beanClass.type().getQulifiedName() ) ) {
				TypeUtil.register(beanClass.type());
			}
		}
		
		private BeanClass deregister(String className) {
			if( className == null || "".equals(className) ) {
				throw new RuntimeException("parameter [className] is required; it cannot be null.");
			}
			
			return _cache.remove(className);
		}
		
		private boolean contains(String className) {
			if( className == null || "".equals(className) ) {
				throw new RuntimeException("parameter [className] is required; it cannot be null.");
			}
			
			return _cache.containsKey(className);
		}
		
		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#finalize()
		 */
		@Override
		public void finalize() throws Throwable {
			_cache.clear();
			
			super.finalize();
		}
	}

}
