package com.zfsoft.fifa.statement;

import java.io.Serializable;

/**
 * StatementElement的类型
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-20
 * @version V1.0.0
 */
public interface ElementType extends Serializable {
	
	/**
	 * 普通字符串类型
	 */
	public static final String COMMON 		= "COMMON";
	
	public static final String LONG_STR		= "LONG_STR";
	
	public static final String CODE 		= "CODE";
	
	public static final String DATE_Y 		= "DATE_Y";
	
	public static final String DATE_YM 		= "DATE_YM";
	
	public static final String DATE_YMD 	= "DATE_YMD";
	
	public static final String IMAGE 		= "IMAGE";
	
	public static final String DOUBLE 		= "DOUBLE";
	
	public static final String SIGLE_SEL 	= "SIGLE_SEL";
	
	public static final String FILE 		= "FILE";

}
