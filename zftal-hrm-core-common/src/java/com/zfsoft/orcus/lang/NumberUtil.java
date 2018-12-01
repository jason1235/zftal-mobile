package com.zfsoft.orcus.lang;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * 数字格式化的工具类
 * <p>
 * 包括设置小数的小数点位数、不同进制整数转化和数字的格式化表示
 * </p>
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-26
 * @version V1.0.0
 */
public class NumberUtil {
	
	/**
	 * 以指定模式设置小数位数
	 * @param value 输入值
	 * @param scale 小数位数
	 * @param mode 模式，取值为BigDecimal定义的模式常量，如：BigDecimal.ROUND_UP
	 */
	public final static float round( float value, int scale, int mode ) {
		BigDecimal bigDecimal = new BigDecimal( value );
		
		bigDecimal = bigDecimal.setScale( scale, mode );
		
		return bigDecimal.floatValue();
	}
	
	/**
	 * 以指定模式设置小数位数
	 * @param value 输入值
	 * @param scale 小数位数
	 * @param mode 模式，取值为BigDecimal定义的模式常量，如：BigDecimal.ROUND_UP
	 */
	public final static double round( double value, int scale, int mode ) {
		BigDecimal bigDecimal = new BigDecimal( value );
		
		bigDecimal = bigDecimal.setScale( scale, mode );
		
		return bigDecimal.doubleValue();
	}
	
	/**
	 * 设置小数位数
	 * <p>逢非零值进一，输出值总是大于或等于输入值</p>
	 * @param value 输入值
	 * @param scale 小数位数
	 */
	public final static float roundUp( float value, int scale ) {
		
		return round( value, scale, BigDecimal.ROUND_UP );
	}

	/**
	 * 设置小数位数
	 * <p>逢非零值进一，输出值总是大于或等于输入值</p>
	 * @param value 输入值
	 * @param scale 小数位数
	 */
	public final static double roundUp( double value, int scale ) {
		
		return round( value, scale, BigDecimal.ROUND_UP );
	}
	
	/**
	 * 设置小数位数
	 * <p>逢五进一</p>
	 * @param value 输入值
	 * @param scale 输出值
	 */
	public final static float roundHalfUp( float value, int scale ) {
		
		return  round( value, scale, BigDecimal.ROUND_HALF_UP );
	}
	
	/**
	 * 设置小数位数
	 * <p>逢五进一</p>
	 * @param value 输入值
	 * @param scale 输出值
	 */
	public final static double roundHalfUp( double value, int scale ) {
		
		return round( value, scale, BigDecimal.ROUND_HALF_UP );
	}
	
	/**
	 * 设置小数位数
	 * <p>
	 * 如输入值小数大于要设置的小数位数，则直接废弃多余的位数；<br>
	 * 如输入值小数小于等于要设置的小数位数，则输出值等于输入值或是一个在设置位数下最接近输入值且比输入值小的数；<br>
	 * 输出值总是小于输入值
	 * </p>
	 * @param value 输入值
	 * @param scale 输出值
	 */
	public final static float roundDown( float value, int scale ) {
		
		return round( value, scale, BigDecimal.ROUND_DOWN );
	}
	
	/**
	 * 设置小数位数
	 * <p>
	 * 如输入值小数大于要设置的小数位数，则直接废弃多余的位数；<br>
	 * 如输入值小数小于等于要设置的小数位数，则输出值等于输入值或是一个在设置位数下最接近输入值且比输入值小的数；<br>
	 * 输出值总是小于输入值
	 * </p>
	 * @param value 输入值
	 * @param scale 输出值
	 */
	public final static double roundDown( double value, int scale ) {
		
		return round( value, scale, BigDecimal.ROUND_DOWN );
	}
	
	/**
	 * 设置小数位数
	 * <p>
	 * 逢六进一
	 * </p>
	 * @param value 输入值
	 * @param scale 小数位数
	 */
	public final static float roundHalfDown( float value, int scale ) {
		
		return round( value, scale, BigDecimal.ROUND_HALF_DOWN );
	}
	
	/**
	 * 设置小数位数
	 * <p>
	 * 逢六进一
	 * </p>
	 * @param value 输入值
	 * @param scale 小数位数
	 */
	public final static double roundHalfDown( double value, int scale ) {
		
		return round( value, scale, BigDecimal.ROUND_HALF_DOWN );
	}
	
	/**
	 * 将数值一指定的进制数转化为字符串
	 * @param value 输入值
	 * @param radix 进制，如：2、4、8、16
	 */
	public final static String byteToString( byte value, int radix ) {
		BigInteger bigInteger = new BigInteger( "" + value );
		
		return bigInteger.toString( radix );
	}
	
	/**
	 * 将数值一指定的进制数转化为字符串
	 * @param value 输入值
	 * @param radix 进制，如：2、4、8、16
	 */
	public final static String shortToString( short value, int radix ) {
		
		BigInteger bigInteger = new BigInteger( "" + value );
		
		return bigInteger.toString( radix );
	}
	
	/**
	 * 将数值一指定的进制数转化为字符串
	 * @param value 输入值
	 * @param radix 进制，如：2、4、8、16
	 */
	public final static String intToString( int value, int radix ) {
		BigInteger bigInteger = new BigInteger( "" + value );
		
		return bigInteger.toString( radix );
	}
	
	/**
	 * 将数值一指定的进制数转化为字符串
	 * @param value 输入值
	 * @param radix 进制，如：2、4、8、16
	 */
	public final static String longToString( long value, int radix ) {
		BigInteger bigInteger = new BigInteger( "" + value );
		
		return bigInteger.toString( radix );
	}
	
	/**
	 * 将不同进制的字符串转换为数值
	 * @param value 字符串
	 * @param radix 进制，如：2、4、8、16
	 */
	public final static byte stringToByte( String value, int radix ) {
		BigInteger bigInteger = new BigInteger( value, radix );
		
		return bigInteger.byteValue();
	}
	
	/**
	 * 将不同进制的字符串转换为数值
	 * @param value 字符串
	 * @param radix 进制，如：2、4、8、16
	 */
	public final static short stringToShort( String value, int radix ) {
		BigInteger bigInteger = new BigInteger( value, radix );
		
		return bigInteger.shortValue();
	}
	
	/**
	 * 将不同进制的字符串转换为数值
	 * @param value 字符串
	 * @param radix 进制，如：2、4、8、16
	 */
	public final static int stringToInteger( String value, int radix ) {
		BigInteger bigInteger = new BigInteger( value, radix );

		return bigInteger.intValue();
	}
	
	/**
	 * 将不同进制的字符串转换为数值
	 * @param value 字符串
	 * @param radix 进制，如：2、4、8、16
	 */
	public final static long stringToLong( String value, int radix ) {
		BigInteger bigInteger = new BigInteger( value, radix );

		return bigInteger.longValue();
	}
	
	/**
	 * 将数值转换为不同地区的货币表示
	 * @param value 输入值
	 * @param locale 地区
	 */
	public final static String currency( short value, Locale locale ) {
		
		return NumberFormat.getCurrencyInstance( locale ).format( value );
	}
	
	/**
	 * 将数值转换为不同地区的货币表示
	 * @param value 输入值
	 * @param locale 地区
	 */
	public final static String currency( int value, Locale locale ) {
		
		return NumberFormat.getCurrencyInstance( locale ).format( value );
	}
	
	/**
	 * 将数值转换为不同地区的货币表示
	 * @param value 输入值
	 * @param locale 地区
	 */
	public final static String currency( long value, Locale locale ) {
		
		return NumberFormat.getCurrencyInstance( locale ).format( value );
	}
	
	/**
	 * 将数值转换为不同地区的货币表示
	 * @param value 输入值
	 * @param locale 地区
	 */
	public final static String currency( float value, Locale locale ) {
		
		return NumberFormat.getCurrencyInstance( locale ).format( value );
	}
	
	/**
	 * 将数值转换为不同地区的货币表示
	 * @param value 输入值
	 * @param locale 地区
	 */
	public final static String currency( double value, Locale locale ) {
		
		return NumberFormat.getCurrencyInstance( locale ).format( value );
	}
	
	/**
	 * 将数值转换为中国大陆地区的货币表示
	 * @param value 输入值
	 */
	public final static String currencyOfChina( short value ) {
		
		return currency( value, Locale.SIMPLIFIED_CHINESE );
	}
	
	/**
	 * 将数值转换为中国大陆地区的货币表示
	 * @param value 输入值
	 */
	public final static String currencyOfChina( int value ) {
		
		return currency( value, Locale.SIMPLIFIED_CHINESE );
	}
	
	/**
	 * 将数值转换为中国大陆地区的货币表示
	 * @param value 输入值
	 */
	public final static String currencyOfChina( long value ) {
		
		return currency( value, Locale.SIMPLIFIED_CHINESE );
	}
	
	/**
	 * 将数值转换为中国大陆地区的货币表示
	 * @param value 输入值
	 */
	public final static String currencyOfChina( float value ) {
		
		return currency( value, Locale.SIMPLIFIED_CHINESE );
	}
	
	/**
	 * 将数值转换为中国大陆地区的货币表示
	 * @param value 输入值
	 */
	public final static String currencyOfChina( double value ) {
		
		return currency( value, Locale.SIMPLIFIED_CHINESE );
	}
	
	/**
	 * 将数值格式化为逗号分隔的格式
	 * @param value 输入值
	 */
	public final static String commaFormat( short value ) {
		DecimalFormat formatter = new DecimalFormat("###,###,###,###,###,###,###");
		
		return formatter.format( value );
	}
	
	/**
	 * 将数值格式化为逗号分隔的格式
	 * @param value 输入值
	 */
	public final static String commaFormat( int value ) {
		DecimalFormat formatter = new DecimalFormat("###,###,###,###,###,###,###");
		
		return formatter.format( value );
	}
	
	/**
	 * 将数值格式化为逗号分隔的格式
	 * @param value 输入值
	 */
	public final static String commaFormat( long value ) {
		DecimalFormat formatter = new DecimalFormat("###,###,###,###,###,###,###");
		
		return formatter.format( value );
	}
	
	/**
	 * 将数值格式化为逗号分隔的格式
	 * @param value 输入值
	 */
	public final static String commaFormat( float value ) {
		DecimalFormat formatter = new DecimalFormat("###,###,###,###,###,###,###.##########");
		
		return formatter.format( value );
	}
	
	/**
	 * 将数值格式化为逗号分隔的格式
	 * @param value 输入值
	 */
	public final static String commaFormat( double value ) {
		DecimalFormat formatter = new DecimalFormat("###,###,###,###,###,###,###.##########");
		
		return formatter.format( value );
	}
	
	/**
	 * 使用定制格式格式一个数
	 * @param value 输入值
	 * @param model 定制格式
	 */
	public final static String format( short value, String model ) {
		DecimalFormat formatter = new DecimalFormat( model );
		
		return formatter.format( value );
	}
	
	/**
	 * 使用定制格式格式一个数
	 * @param value 输入值
	 * @param model 定制格式
	 */
	public final static String format( int value, String model ) {
		DecimalFormat formatter = new DecimalFormat( model );
		
		return formatter.format( value );
	}
	
	/**
	 * 使用定制格式格式一个数
	 * @param value 输入值
	 * @param model 定制格式
	 */
	public final static String format( long value, String model ) {
		DecimalFormat formatter = new DecimalFormat( model );
		
		return formatter.format( value );
	}
	
	/**
	 * 使用定制格式格式一个数
	 * @param value 输入值
	 * @param model 定制格式
	 */
	public final static String format( float value, String model ) {
		DecimalFormat formatter = new DecimalFormat( model );
		
		return formatter.format( value );
	}
	
	/**
	 * 使用定制格式格式一个数
	 * @param value 输入值
	 * @param model 定制格式
	 */
	public final static String format( double value, String model ) {
		DecimalFormat formatter = new DecimalFormat( model );
		
		return formatter.format( value );
	}
	
}
