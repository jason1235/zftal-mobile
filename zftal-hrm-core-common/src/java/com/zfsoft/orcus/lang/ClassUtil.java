package com.zfsoft.orcus.lang;

/**
 * 类型工具类
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-8
 * @version V1.0.0
 */
@SuppressWarnings("unchecked")
public class ClassUtil {
	
	/**
	 * <p>包名分隔字符: <code>&#x2e;</code></p>
	 */
	public static final char PACKAGE_SEPARATOR_CHAR = '.';
	
	/**
	 * <p>包名分隔字符串: <code>&#x2e;</code></p>
	 */
	public static final String PACKAGE_SEPARATOR = String.valueOf(PACKAGE_SEPARATOR_CHAR);
	
	/**
	 * <p>内隐类类名分隔字符 <code>$</code></p>
	 */
	public static final char INNER_CLASS_SEPARATOR_CHAR = '$';
	
	/**
	 * <p>内隐类类名分隔字符串: <code>$</code></p>
	 */
	public static final String INNER_CLASS_SEPARATOR = String.valueOf(INNER_CLASS_SEPARATOR_CHAR);

	/**
	 * 获取基本类型的包覆类
	 * @param primitiveType 类型
	 * @return primitiveType对应的包覆类
	 * @throws NonwrapperException (runtime)如果qulifiedName不是包覆类的全限定名
	 */
	public static Class getWrapperClass( Class primitiveType ) {
		if( boolean.class.equals( primitiveType ) ) {
			return Boolean.class;
			
		} else if( char.class.equals( primitiveType ) ) {
			return Character.class;
			
		} else if( byte.class.equals( primitiveType ) ) {
			return Byte.class;
			
		} else if( short.class.equals( primitiveType ) ) {
			return Short.class;
			
		} else if( int.class.equals( primitiveType ) ) {
			return Integer.class;
			
		} else if( long.class.equals( primitiveType ) ) {
			return Long.class;
			
		} else if( float.class.equals( primitiveType ) ) {
			return Float.class;
			
		} else if( double.class.equals( primitiveType ) ) {
			return Double.class;
		} else {
			throw new NonwrapperException();
		}
		
	}
	
	/**
	 * 返回类型的显示名( 如int，java.lang.Object，int[][]，java.lang.Object[][])
	 * @param clazz 类型 (never null)
	 * @return 类型的显示名
	 */
	public static String getDisplayName(Class clazz) {
		if( clazz.isArray() == false ) {
			return clazz.getName();
		}
		
		return ArrayUtil.getDefineName(clazz);
	}
	
	/**
	 * 返回短类名（不带包名的类名）
	 * @param clazz 类型，不能为null
	 * @return 不带包名的类名，如String，ArrayList
	 */
	public static String getShortClassName(Class clazz) {
		if( clazz == null ) {
			throw new RuntimeException("parameter [className] is required; it cannot be null.");
		}
		
		return getShortClassName( clazz.getName() );
	}
	
	/**
	 * 返回短类名（不带包名的类名）
	 * @param className 类名，不能为null
	 * @return 不带包名的类名，如String，ArrayList
	 */
	public static String getShortClassName(String className) {
		if( className == null || "".equals(className) ) {
			throw new RuntimeException("parameter [className] is required; it cannot be null.");
		}
		
		char[] chars = className.toCharArray();
		
		int lastDot = 0;
		
		for (int i = 0; i < chars.length; i++) {
			if( chars[i] == PACKAGE_SEPARATOR_CHAR ) {
				lastDot = i + 1;
			} else if( chars[i] == INNER_CLASS_SEPARATOR_CHAR ) {
				chars[i] = PACKAGE_SEPARATOR_CHAR;
			}
		}
		
		return new String(chars, lastDot, chars.length - lastDot);
	}
	
	/**
	 * 返回包名
	 * @param clazz 类型 ，不得为null
	 * @return 包名，如果clazz指定的类为基础类型或在缺省包中则返回""
	 */
	public static String getPackageName(Class clazz) {
		if( clazz == null ) {
			throw new RuntimeException("parameter [className] is required; it cannot be null.");
		}
		
		return getPackageName( clazz.getName() );
	}
	
	/**
	 * 返回包名
	 * @param className 类名， 不得为null
	 * @return 包名，如果clazz指定的类为基础类型或在缺省包中则返回""
	 */
	public static String getPackageName(String className) {
		if( className == null || "".equals(className) ) {
			throw new RuntimeException("parameter [className] is required; it cannot be null.");
		}
		
		int i = className.lastIndexOf( PACKAGE_SEPARATOR_CHAR );
		
		if( i == -1 ) {
			return "";
		}
		
		return className.substring(0, i);
	}
}
