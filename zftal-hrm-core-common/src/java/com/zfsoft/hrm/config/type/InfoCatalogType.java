package com.zfsoft.hrm.config.type;

import java.util.Properties;

import com.zfsoft.hrm.config.GeneralType;
import com.zfsoft.util.base.StringUtil;

/**
 * 信息类目录类型描述信息
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-3
 * @version V1.0.0
 */
public class InfoCatalogType extends GeneralType {

	private static final long serialVersionUID = 1260320563481633235L;
	
	private static final String PRIMARY_NAME = "primaryName";
	
	private static final String PRIMARY_FIELD_NAME = "primaryFieldName";
	
	private static final String CREATE_LOG = "log";
	
	private static final String CREATE_SNAP = "snap";
	
	protected Properties menus = new Properties();	//附加属性
	
	/**
	 * 返回主键名字
	 */
	public String getPrimaryName() {

		return appendix.getProperty( PRIMARY_NAME );
	}
	
	/**
	 * 返回主键字段名字
	 */
	public String getPrimaryFileName() {
		
		return appendix.getProperty( PRIMARY_FIELD_NAME );
	}
	
	/**
	 * 是否创建日志库
	 */
	public boolean isCreateLog() {
		return new Boolean( appendix.getProperty( CREATE_LOG ) );
	}
	
	/**
	 * 是否创建快照
	 */
	public boolean isCreateSnap() {
		return new Boolean( appendix.getProperty( CREATE_SNAP ) );
	}

	/**
	 * 返回
	 */
	public Properties getMenus() {
		return menus;
	}

	/**
	 * 设置
	 * @param menus 
	 */
	public void setMenus(Properties menus) {
		this.menus = menus;
	}

}
