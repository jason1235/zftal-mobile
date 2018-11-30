package com.zfsoft.hrm.config.type;

import com.zfsoft.hrm.config.GeneralType;

/**
 * 信息类属性类型描述信息
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-7-30
 * @version V1.0.0
 */
public class InfoPropertyType extends GeneralType {

	private static final long serialVersionUID = -1917476194450126008L;

	private static final String DATA_TYPE = "dataType";
	
	private static final String FORMAT = "format";
	
	private static final String DEFAULT_DATA_TYPE = "VARCHAR2";
	
	private static final String[] HAS_LENGTH_TYPES = new String[]{"VARCHAR2"};
	
	/**
	 * 返回数据库字段类型
	 * @return 如：VARCHAR2, NUMBE
	 */
	public String getDataType() {

		return appendix.getProperty( DATA_TYPE, DEFAULT_DATA_TYPE );
	}
	
	/**
	 * 返回数据显示格式
	 * <p>
	 * 一般用于日期类型解析显示格式，如：yyyy-MM-dd
	 * </p>
	 */
	public String getFormat() {
		
		return appendix.getProperty( FORMAT );
	}
	
	/**
	 * 是否有最大长度限制
	 * @return true 有； false 没有
	 */
	public boolean hasMaxLen() {
		boolean result = false;
		
		for ( String str : HAS_LENGTH_TYPES ) {
			if( str.equals( getDataType() ) ) {
				result = true;
				break;
			}
		}
		
		return result;
	}

}
