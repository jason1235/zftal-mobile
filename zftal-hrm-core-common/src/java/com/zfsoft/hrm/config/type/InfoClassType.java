package com.zfsoft.hrm.config.type;

import com.zfsoft.hrm.config.GeneralType;

/**
 * 信息类类型描述信息
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-7-28
 * @version V1.0.0
 */
public class InfoClassType extends GeneralType {

	private static final long serialVersionUID = -1414004929928492300L;
	
	private static final String EDIT = "editable";
	
	private static final String MORE = "moreThanOne";
	
	/**
	 * 是否可编辑
	 * @return true 可编辑；false 不可编辑
	 */
	public boolean isEditable() {
		
		return new Boolean( appendix.getProperty( EDIT ) );
	}
	
	/**
	 * 是否多于一条记录
	 * @return true 多与一条（多条）；false 一条（单条）
	 */
	public boolean isMoreThanOne() {
		
		return new Boolean( appendix.getProperty( MORE ) );
	}

}
