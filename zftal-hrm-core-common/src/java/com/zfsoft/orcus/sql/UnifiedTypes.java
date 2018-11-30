package com.zfsoft.orcus.sql;

/**
 * 统一数据库字段类型定义
 * 
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-27
 * @version V1.0.0
 */
public class UnifiedTypes {
	
	public static final String BOOLEAN = "BOOLEAN";

	public static final String BYTE = "BYTE";

	public static final String FIXED_BYTES = "FIXED_BYTES";

	public static final String VAR_BYTES = "VAR_BYTES";

	public static final String LONG_BYTES = "LONG_BYTES";

	public static final String SHORT = "SHORT";

	public static final String INT = "INT";

	public static final String LONG = "LONG";

	public static final String FLOAT = "FLOAT";

	public static final String DOUBLE = "DOUBLE";

	public static final String BIGDECIMAL = "BIGDECIMAL";

	public static final String FIXED_CHARS = "FIXED_CHARS";

	public static final String VAR_CHARS = "VAR_CHARS";

	public static final String LONG_CHARS = "LONG_CHARS";

	public static final String DATE = "DATE";

	public static final String TIME = "TIME";

	public static final String TIMESTAMP = "TIMESTAMP";

	public static final String BLOB = "BLOB";

	public static final String CLOB = "CLOB";

	/**
	 * 返回指定类型是否可指定其显示长度
	 * 
	 * @param type 类型
	 * @return true表示可以，false表示不可以
	 */
	public static boolean sizeEditable(String type) {
		
		if (type == null) {
			return true;
		}

		if ( type.equalsIgnoreCase( BOOLEAN ) ) 		return false;
		
		if ( type.equalsIgnoreCase( BYTE ) ) 			return false;
		
		if ( type.equalsIgnoreCase( FIXED_BYTES  ) ) 	return true;
		
		if ( type.equalsIgnoreCase( VAR_BYTES ) ) 		return true;
		
		if ( type.equalsIgnoreCase( LONG_BYTES ) ) 		return false;
		
		if ( type.equalsIgnoreCase( SHORT ) ) 			return false;
		
		if ( type.equalsIgnoreCase( INT ) ) 			return false;
		
		if ( type.equalsIgnoreCase( LONG ) ) 			return false;
		
		if ( type.equalsIgnoreCase( FLOAT ) ) 			return false;
		
		if ( type.equalsIgnoreCase( DOUBLE ) ) 			return false;
		
		if ( type.equalsIgnoreCase( BIGDECIMAL ) ) 		return true;
		
		if ( type.equalsIgnoreCase( FIXED_CHARS ) ) 	return true;
		
		if ( type.equalsIgnoreCase( VAR_CHARS ) ) 		return true;
		
		if ( type.equalsIgnoreCase( LONG_CHARS ) ) 		return false;
		
		if ( type.equalsIgnoreCase( DATE ) ) 			return false;
		
		if ( type.equalsIgnoreCase( TIME ) ) 			return false;
		
		if ( type.equalsIgnoreCase( TIMESTAMP ) ) 		return false;
		
		if ( type.equalsIgnoreCase( BLOB ) ) 			return true;
		
		if ( type.equalsIgnoreCase( CLOB ) ) 			return true;

		return true;
	}

	/**
	 * 返回指定类型的缺省显示长度
	 * 
	 * @param type
	 *            类型
	 * @return 显示长度
	 */
	public static int defaultSize(String type) {
		
		if ( type == null ) {
			return 0;
		}

		if ( type.equalsIgnoreCase( BOOLEAN ) ) 	return 1;
		
		if ( type.equalsIgnoreCase( BYTE ) ) 		return 3;
		
		if ( type.equalsIgnoreCase( FIXED_BYTES ) ) return 64;
		
		if ( type.equalsIgnoreCase( VAR_BYTES ) ) 	return 64;
		
		if ( type.equalsIgnoreCase( LONG_BYTES ) ) 	return 2147483647;
		
		if ( type.equalsIgnoreCase( SHORT ) ) 		return 5;
		
		if ( type.equalsIgnoreCase( INT ) ) 		return 10;
		
		if ( type.equalsIgnoreCase( LONG ) ) 		return 20;
		
		if ( type.equalsIgnoreCase( FLOAT ) ) 		return 10;
		
		if ( type.equalsIgnoreCase( DOUBLE ) ) 		return 17;
		
		if ( type.equalsIgnoreCase( BIGDECIMAL ) ) 	return 17;
		
		if ( type.equalsIgnoreCase( FIXED_CHARS ) ) return 64;
		
		if ( type.equalsIgnoreCase( VAR_CHARS ) ) 	return 64;
		
		if ( type.equalsIgnoreCase( LONG_CHARS ) ) 	return 2147483647;
		
		if ( type.equalsIgnoreCase( DATE ) ) 		return 20;
		
		if ( type.equalsIgnoreCase( TIME ) ) 		return 20;
		
		if ( type.equalsIgnoreCase( TIMESTAMP ) ) 	return 20;
		
		if ( type.equalsIgnoreCase( BLOB ) ) 		return 2147483647;
		
		if ( type.equalsIgnoreCase( CLOB ) ) 		return 2147483647;

		return 0;
	}

	/**
	 * 返回指定类型是否可指定其精度及小数位数
	 * 
	 * @param type
	 *            类型
	 * @return true表示可以，false表示不可以
	 */
	public static boolean precisionEditable(String type) {
		
		if (type == null) {
			return true;
		}

		if ( type.equalsIgnoreCase( BOOLEAN ) ) 	return false;
		
		if ( type.equalsIgnoreCase( BYTE ) ) 		return false;
		
		if ( type.equalsIgnoreCase( FIXED_BYTES ) ) return false;
		
		if ( type.equalsIgnoreCase( VAR_BYTES ) ) 	return false;
		
		if ( type.equalsIgnoreCase( LONG_BYTES ) ) 	return false;
		
		if ( type.equalsIgnoreCase( SHORT ) ) 		return false;
		
		if ( type.equalsIgnoreCase( INT ) ) 		return false;
		
		if ( type.equalsIgnoreCase( LONG ) ) 		return false;
		
		if ( type.equalsIgnoreCase( FLOAT ) ) 		return false;
		
		if ( type.equalsIgnoreCase( DOUBLE ) ) 		return false;
		
		if ( type.equalsIgnoreCase( BIGDECIMAL ) ) 	return true;
		
		if ( type.equalsIgnoreCase( FIXED_CHARS ) ) return false;
		
		if ( type.equalsIgnoreCase( VAR_CHARS ) ) 	return false;
		
		if ( type.equalsIgnoreCase( LONG_CHARS ) ) 	return false;
		
		if ( type.equalsIgnoreCase( DATE ) ) 		return false;
		
		if ( type.equalsIgnoreCase( TIME ) ) 		return false;
		
		if ( type.equalsIgnoreCase( TIMESTAMP ) ) 	return false;
		
		if ( type.equalsIgnoreCase( BLOB ) ) 		return false;
		
		if ( type.equalsIgnoreCase( CLOB ) ) 		return false;

		return true;
	}
}
