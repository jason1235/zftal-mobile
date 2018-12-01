package com.zfsoft.orcus.lang;

/**
 * 原始类型工具类
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-8
 * @version V1.0.0
 */
public class Primitives {

	/** boolean的全限定名 */
    public final static String QN_BOOLEAN = boolean.class.getName();
    
    /** byte的全限定名 */
    public final static String QN_BYTE = byte.class.getName();
    
    /** char的全限定名 */
    public final static String QN_CHAR = char.class.getName();
    
    /** short的全限定名 */
    public final static String QN_SHORT = short.class.getName();
    
    /** int的全限定名 */
    public final static String QN_INT = int.class.getName();
    
    /** long的全限定名 */
    public final static String QN_LONG = long.class.getName();
    
    /** float的全限定名 */
    public final static String QN_FLOAT = float.class.getName();
    
    /** double的全限定名 */
    public final static String QN_DOUBLE = double.class.getName();
    
    /**
     * 返回指定类型的缺省值
     * @param qulifiedName 类型的全限定名，例 int, int[], java.lang.Object, java.lang.Object[]
     * @return 如果类型为原始类型则返回缺省值，否则返回null。
     */
    public static Object getDefaultValue(String qulifiedName) {
		if( QN_BOOLEAN.equals( qulifiedName ) ) {
			return Boolean.FALSE;
			
		} else if( QN_BYTE.equals( qulifiedName ) ) {
			return new Byte( (byte) 0 );
			
		} else if( QN_CHAR.equals( qulifiedName ) ) {
			return new Character( (char) 0 );
			
		} else if( QN_DOUBLE.equals( qulifiedName ) ) {
			return new Double( (double) 0.0 );
			
		} else if( QN_FLOAT.equals( qulifiedName ) ) {
			return new Float( (float) 0.0 );
			
		} else if( QN_INT.equals( qulifiedName ) ) {
			return new Integer( (int) 0 );
			
		} else if( QN_LONG.equals( qulifiedName ) ) {
			return new Long( (int) 0 );
			
		} else if( QN_SHORT.equals( qulifiedName ) ) {
			return new Short( (short) 0 );
			
		} else {
			return null;
		}
    }
    
	/**
	 * 返回原始类型的包覆类
	 * 
	 * @param qulifiedName 原始类型的全限定名，例 int, boolean等等
	 * @return 原始类型的包覆类
	 * @throws NonprimitiveException (runtime)如果qulifiedName不是原始类型的全限定名
	 */
    @SuppressWarnings("unchecked")
	public static Class getWrapperClass( String qulifiedName ) {
		if( QN_BOOLEAN.equals( qulifiedName ) ) {
			return Boolean.class;
			
		} else if( QN_BYTE.equals( qulifiedName ) ) {
			return Byte.class;
			
		} else if( QN_CHAR.equals( qulifiedName ) ) {
			return Character.class;
			
		} else if( QN_DOUBLE.equals( qulifiedName ) ) {
			return Double.class;
			
		} else if( QN_FLOAT.equals( qulifiedName ) ) {
			return Float.class;
			
		} else if( QN_INT.equals( qulifiedName ) ) {
			return Integer.class;
			
		} else if( QN_LONG.equals( qulifiedName ) ) {
			return Long.class;
			
		} else if( QN_SHORT.equals( qulifiedName ) ) {
			return Short.class;
			
		} else {
			throw new NonprimitiveException( qulifiedName );
		}
	}
    
    /**
     * 返回包覆类的原始类型
     * @param qulifiedName 包覆类型的全限定名，例 java.lang.Integer等等
     * @return 原始类型的全限定名
     * @throws NonwrapperException (runtime)如果qulifiedName不是包覆类的全限定名
     */
    public static String getPrimitiveClass( String qulifiedName ) {
		if( Boolean.class.getName().equals( qulifiedName ) ) {
			return QN_BOOLEAN;
			
		} else if( Byte.class.getName().equals( qulifiedName ) ) {
			return QN_BYTE;
			
		} else if( Character.class.getName().equals( qulifiedName ) ) {
			return QN_CHAR;
			
		} else if( Double.class.getName().equals( qulifiedName ) ) {
			return QN_DOUBLE;
			
		} else if( Float.class.getName().equals( qulifiedName ) ) {
			return QN_FLOAT;
			
		} else if( Integer.class.getName().equals( qulifiedName ) ) {
			return QN_INT;
			
		} else if( Long.class.getName().equals( qulifiedName ) ) {
			return QN_LONG;
			
		} else if( Short.class.getName().equals( qulifiedName ) ) {
			return QN_SHORT;
			
		} else {
			throw new NonwrapperException( qulifiedName );
		}
    }
    
    /**
     * 修正作为数字输入的字符串
     * @param input 输入字符串
     * @return 修正后的字符串
     */
    public static String reviseNumber(String input) {
    	if( input == null || input.length() == 0 ) {
    		return "";
    	}
    	
    	input = input.trim();
    	input = StringUtil.replace(input, " ", "");
    	input = StringUtil.replace(input, ",", "");
    	input = StringUtil.replace(input, ";", "");
    	input = StringUtil.replace(input, "，", "");
    	input = StringUtil.replace(input, "；", "");
    	input = StringUtil.replace(input, "。", ".");
    	input = StringUtil.replace(input, "０", "0");
    	input = StringUtil.replace(input, "１", "1");
    	input = StringUtil.replace(input, "２", "2");
    	input = StringUtil.replace(input, "３", "3");
    	input = StringUtil.replace(input, "４", "4");
    	input = StringUtil.replace(input, "５", "5");
    	input = StringUtil.replace(input, "６", "6");
    	input = StringUtil.replace(input, "７", "7");
    	input = StringUtil.replace(input, "８", "8");
    	input = StringUtil.replace(input, "９", "9");
    	
    	StringBuffer out = new StringBuffer("");
    	
    	for ( char c : input.toCharArray() ) {
			if( ( c >= '0' && c <= '9' ) || c == '.' || c == '+' || c == '-' ) {
				out.append(c);
			}
		}
    	
    	return out.toString();
    }
	
}
