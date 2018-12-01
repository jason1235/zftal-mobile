package com.zfsoft.orcus.lang.converters;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import com.zfsoft.orcus.lang.ArrayUtil;
import com.zfsoft.orcus.lang.Cleaner;
import com.zfsoft.orcus.lang.LocalReference;
import com.zfsoft.orcus.lang.reflect.SingletonClassLoader;

/**
 * 数据转换工具实现类，提供转换器注册表及字符串和其它类型数据相互转换的功能。
 * 该类在缺省情况下自动注册如下类型（包括以这些类型为元素类型的一维和多维数组）的转换器:
 * <ul>
 * <li>boolean and java.lang.Boolean</li>
 * <li>byte and java.lang.Byte</li>
 * <li>char and java.lang.Character</li>
 * <li>int and java.lang.Integer</li>
 * <li>long and java.lang.Long</li>
 * <li>short and java.lang.Short</li>
 * <li>double and java.lang.Double</li>
 * <li>float and java.lang.Float</li>
 * <li>java.lang.BigDecimal</li>
 * <li>java.lang.BigInteger</li>
 * <li>java.lang.Class</li>
 * <li>java.util.Date</li>
 * <li>java.util.GregorianCalendar</li>
 * <li>java.lang.String</li>
 * <li>java.lang.StringBuffer</li>
 * <li>java.io.File</li>
 * <li>java.net.URL</li>
 * <li>java.sql.Date</li>
 * <li>java.sql.Time</li>
 * <li>java.sql.Timestamp</li>
 * </ul>
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-19
 * @version V1.0.0
 */
@SuppressWarnings("unchecked")
public class ConvertUtil {
	
	/**
	 * 查找指定类型的数据转换器
	 * <p>
	 * 如果指定类型是数组则不同维数的数组将联系同一个数据转换器<br>
	 * 如： String[] 和 String[][]对应的数据转换器是一样的
	 * </p>
	 * @param clazz 数据类型，不能为null
	 * @return 数据转换器，如果没有数据转换器与指定类型相联系则返回null
	 */
	public static Converter lookup( Class clazz ) {
		return getActor().lookup(clazz);
	}
	
	/**
	 * 查找指定类型的数据转换器
	 * <p>
	 * 如果指定类型是数组则不同维数的数组将联系同一个数据转换器<br>
	 * 如： String[] 和 String[][]对应的数据转换器是一样的
	 * </p>
	 * @param className 数据类型的类名，不能为null
	 * @return 数据转换器，如果没有数据转换器与指定类型相联系则返回null
	 */
	public static Converter lookup(String className) {
		try {
			return getActor().lookup( SingletonClassLoader.getInstance().loadClass(className) );
		} catch ( ClassNotFoundException e) {
			return null;
		}
	}
	
	/**
	 * 注册指定类型的数据转换器
	 * <p>
	 * 如指定类型的数据转换器已存在，则替换原有的数据转换器<br>
	 * 如果指定类型是数组则不同维数的数组将被视为同一类型，<br>
	 * register( converter, String[].class ) 和
	 * register( converter,String[][].class )的效果是一样的
	 * </p>
	 * @param clazz 数据类型，不能为null
	 * @param converter 数据转换器，不能为null
	 */
	public static void register(Class clazz, Converter converter) {
		getActor().register(clazz, converter);
	}
	
	/**
	 * 注销指定类型的数据转换器
	 * <p>
	 * 如果指定类型是数组则不同维数的数组将被视为同一类型<br>
	 * 如：deregister( String[].class ) 和 deregister( String[][].class )的效果是一样的
	 * </p>
	 * @param clazz 数据类型，不能为null
	 */
	public static void deregister( Class clazz ) {
		getActor().deregister( clazz );
	}
	
	/**
	 * 注销所有已注册的数据转换器并重新注册所有的标准数据转换器
	 */
	public void deregister() {
		getActor().deregister();
	}
	
	/**
	 * 将输入数据转换为字符串
	 * @param value 输入数据
	 * @return null if value is null, value.toString() if value is array, a
     *         string coverted by converter registered for String.class
	 */
	public static String convert(Object value) {
		return getActor().convert(value);
	}
	
	/**
	 * 将输入数据转换为字符串数组
	 * @param values 输入数据
	 * @return null if value is null, value.toString() if value is array, a
     *         string coverted by converter registered for String.class
	 */
	public static String[] convert( Object[] values ) {
		return getActor().convert( values );
	}
	
	/**
	 * 将字符串转换为指定的类型
	 * @param value 字符串
	 * @param clazz 转换的目的类型
	 * @return 转换后的数据(类型则clazz指定)
	 * @throws NullInputException (runtime)当 value==null时<strong>可能</strong>抛出该异常
	 * @throws ConversionException (runtime)当转换出错时<strong>可能</strong>抛出该异常
	 */
	public static Object convert( String value, Class clazz ) {
		return getActor().convert( value, clazz );
	}
	
	/**
	 * 将字符串数组转换为指定的类型的数组
	 * @param values 字符串数组
	 * @param clazz 转换的目的类型
	 * @return 转换后的数据(类型则clazz指定)
	 * @throws NullInputException (runtime)当 values==null时<strong>可能</strong>抛出该异常
	 * @throws ConversionException (runtime)当转换出错时<strong>可能</strong>抛出该异常
	 */
	public static Object convert( String[] values, Class clazz ) {
		return getActor().convert( values, clazz );
	}
	
	private static Actor getActor() {
		return (Actor) REF.get();
	}
	
	private static final LocalReference REF = new LocalReference() {
		
		@Override
		protected Object createInstance() {
			return new ConvertUtil().new Actor();
		}
	};
	
	private class Actor {
		
		
		private Map<Class, Converter> _nonarrayConverters = new HashMap<Class, Converter>();
		
		private Map<Class, Converter> _arrayConverters = new HashMap<Class, Converter>();
		
		private Actor() {
			deregister();
		}
		
		private Converter lookup( Class clazz ) {
			if( clazz.isArray() ) {
				return (Converter) _arrayConverters.get( ArrayUtil.getBaseComponentType(clazz) );
			} else {
				return (Converter) _nonarrayConverters.get( clazz );
			}
		}
		
		private void register( Class clazz, Converter converter ) {
			if( clazz.isArray() ) {
				_arrayConverters.put( ArrayUtil.getBaseComponentType(clazz), converter );
			} else {
				_nonarrayConverters.put(clazz, converter);
			}
		}
		
		private void deregister(Class clazz) {
			if( clazz.isArray() ) {
				_arrayConverters.remove( ArrayUtil.getBaseComponentType(clazz) );
			} else {
				_nonarrayConverters.remove( clazz );
			}
		}
		
		private void deregister() {
			_arrayConverters.clear();
			_nonarrayConverters.clear();
			
			register( BigDecimal.class, new BigDecimalConverter( null ) );
			register( BigDecimal[].class, new ArrayConverter( null ) );
			
			register( BigInteger.class, new BigIntegerConverter( null ) );
			register( BigInteger[].class, new ArrayConverter( null ) );
			
			register( boolean.class, new BooleanConverter( Boolean.FALSE ) );
			register( boolean[].class, new ArrayConverter( null ) );
			register( Boolean.class, new BooleanConverter() );
			register( Boolean[].class, new ArrayConverter( null ) );
			
			register( byte.class, new ByteConverter( new Byte( (byte) 0 ) ) );
			register( byte[].class, new ArrayConverter( null ) );
			register( Byte.class, new ByteConverter( null ) );
			register( Byte[].class, new ArrayConverter( null ) );
			
			register( char.class, new CharacterConverter( new Character( (char)0x00 ) ) );
			register( char[].class, new ArrayConverter( null ) );
			register( Character.class, new CharacterConverter( null ) );
			register( Character[].class, new ArrayConverter( null ) );
			
			register( Class.class, new ClassConverter( null ) );
			register( Class[].class, new ArrayConverter( null ) );
			
			register( Date.class, new DateConverter( null ) );
			register( Date[].class, new ArrayConverter( null ) );
			
			register( double.class, new DoubleConverter( new Double((double) 0.0 ) ) );
			register( double[].class, new ArrayConverter( null ) );
			register( Double.class, new DoubleConverter( null ) );
			register( Double[].class, new ArrayConverter( null ) );
			
			register( File.class, new FileConverter( null ) );
			register( File[].class, new ArrayConverter( null ) );
			
			register( GregorianCalendar.class, new GregorianCalendarConverter( null ) );
			register( GregorianCalendar[].class, new ArrayConverter( null ) );
			
			register( float.class, new FloatConverter( new Float( (float) 0.0 ) ) );
			register( float[].class, new ArrayConverter( null ) );
			register( Float.class, new FloatConverter( null ) );
			register( Float[].class, new ArrayConverter( null ) );
			
			register( int.class, new IntegerConverter( new Integer( 0 ) ) );
			register( int[].class, new ArrayConverter( null ) );
			register( Integer.class, new IntegerConverter( null ) );
			register( Integer[].class, new ArrayConverter( null ) );
			
			register( int.class, new IntegerConverter( new Integer( 0 ) ) );
			register( int[].class, new ArrayConverter( null ) );
			register( Integer.class, new IntegerConverter( null ) );
			register( Integer[].class, new ArrayConverter( null ) );
			
			register( long.class, new LongConverter( new Long( 0L ) ) );
			register( long[].class, new ArrayConverter( null ) );
			register( Long.class, new LongConverter( null ) );
			register( Long[].class, new ArrayConverter( null ) );
			
			register( short.class, new ShortConverter( new Short( (short) 0 ) ) );
			register( short[].class, new ArrayConverter( null ) );
			register( Short.class, new ShortConverter( null ) );
			register( Short[].class, new ArrayConverter( null ) );
			
			register( java.sql.Date.class, new SqlDateConverter( null ) );
			register( java.sql.Date[].class, new ArrayConverter( null ) );
			
			register( java.sql.Time.class, new SqlTimeConverter( null ) );
			register( java.sql.Time[].class, new ArrayConverter( null ) );
			
			register( java.sql.Timestamp.class, new SqlTimestampConverter( null ) );
			register( java.sql.Timestamp[].class, new ArrayConverter( null ) );
			
			register( String.class, new SqlTimeConverter( null ) );
			register( String[].class, new ArrayConverter( null ) );
			
			register( StringBuffer.class, new StringBufferConverter( null ) );
			register( StringBuffer[].class, new ArrayConverter( null ) );
		}
		
		private String convert(Object value) {
			if( value == null ) {
				return null;
			} else if( value.getClass().isArray() ) {
				return value.toString();
			} else {
				Converter converter = lookup( String.class );
				return (String) converter.convert(String.class, value);
			}
		}
		
		private String[] convert(Object[] values) {
			if( values == null ) {
				return null;
			} else {
				Converter converter = lookup( String[].class );
				return (String[]) converter.convert(String[].class, values);
			}
		}
		
		private Object convert( String value, Class clazz ) {
			Converter converter = lookup( clazz );
			
			if( converter == null ) {
				throw new ConversionException("No Converter specified for type:" + clazz);
			}
			
			return converter.convert(clazz, value);
		}
		
		private Object convert( String[] values, Class clazz ){
			if( clazz == null || !clazz.isArray() ) {
				throw new ConversionException("Invalid type for array conversion:" + clazz );
			}
			
			Converter converter = lookup( clazz );
			
			if( converter == null ) {
				throw new ConversionException("No Converter specified for type:" + clazz);
			}
			
			return converter.convert(clazz, values);
		}
		
		@Override
		public void finalize() throws Throwable {
			Cleaner.clean( _arrayConverters );
			Cleaner.clean( _nonarrayConverters );
			
			super.finalize();
		}
	}
	
}
