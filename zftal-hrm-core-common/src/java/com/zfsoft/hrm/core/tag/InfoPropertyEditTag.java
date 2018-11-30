package com.zfsoft.hrm.core.tag;

import javax.servlet.jsp.tagext.TagSupport;

import com.zfsoft.hrm.baseinfo.dyna.html.Type;

/**
 * 信息类属性标签
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-14
 * @version V1.0.0
 */
public class InfoPropertyEditTag extends TagSupport {
	
	private static final long serialVersionUID = 929748856060547123L;
	
	private String name;		//名字
	private String type;		//字段类型
	private String codeTable;	//代码表，用于代码选择类型
	private String defValue;	//默认值
	private String format;		//格式，用于日期类型
	private boolean hidden;		//是否隐藏
	
	/**
	 * 设置名字
	 * @param name 名字
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 设置字段类型
	 * 详见{@link Type}
	 * @param type 字段类型
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * 设置格式，用于日期类型
	 * @param format 格式，用于日期类型
	 */
	public void setFormat(String format) {
		this.format = format;
	}
	
	/**
	 * 设置代码表，用于代码选择类型
	 * @param codeTable 代码表，用于代码选择类型
	 */
	public void setCodeTable(String codeTable) {
		this.codeTable = codeTable;
	}

	/**
	 * 设置默认值
	 * @param defValue 默认值
	 */
	public void setDefValue(String defValue) {
		this.defValue = defValue;
	}

	/**
	 * 设置是否隐藏
	 * @param hidden 是否隐藏 
	 */
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

}
