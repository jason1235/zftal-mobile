package com.zfsoft.fifa.decorator;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zfsoft.orcus.lang.reflect.SingletonClassLoader;

/**
 * Decorator工具类
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-19
 * @version V1.0.0
 */
public class DecoratorUtil implements Serializable {

	private static final long serialVersionUID = -865316928449657070L;

	private static Map<String, Object> _map = new HashMap<String, Object>();
	
	private static Log LOG = LogFactory.getLog( DecoratorUtil.class.getName() );
	
	@SuppressWarnings("unchecked")
	public static Decorator lookup( String className ) {
		Decorator decorator = null;
		
		decorator = (Decorator) _map.get( className );
		
		if( decorator == null ) {
			try {
				Class clazz = SingletonClassLoader.getInstance().loadClass( className );
				Object obj = clazz.newInstance();
				
				if( obj instanceof Decorator ) {
					_map.put( className, obj );
					return (Decorator) obj;
				}
				
				//类型不正确
				throw new DecoratorNotFoundException( "Decorator定义有误:" + className );
			} catch (Exception e) {
				LOG.error(e);
				throw new DecoratorNotFoundException( "无法加载Decorator:" + className, e );
			}
		}
		
		return decorator;
	}
}
