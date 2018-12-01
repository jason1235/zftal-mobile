package com.zfsoft.hrm.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.config.type.InfoCatalogType;

/**
 * 选择人员查询条件及查询列表显示字段描述信息
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-8
 * @version V1.0.0
 */
public class SelectPersonConfig implements Serializable {
	
	private static final long serialVersionUID = -8178116447858845516L;
	
	private InfoCatalogType type;
	
	private String[] properties;
	
	private SearchCondition[] conditions;
	
	////////////////////////////////////////////
	////////////////////////////////////////////
	
	public List<InfoProperty> getPropertyInfos() {
		List<InfoProperty> list = new ArrayList<InfoProperty>();
		
		InfoClass clazz = InfoClassCache.getOverallInfoClass( type.getName() );
		
		for ( String pName : properties ) {
			InfoProperty prop = clazz.getPropertyByName( pName );
			list.add( prop );
		}
		
		return list;
	}
	
	/**
	 * 返回查询人员的类型
	 */
	public InfoCatalogType getType() {
		return type;
	}

	/**
	 * 设置查询人员的类型
	 * @param type 查询人员的类型
	 */
	public void setType(InfoCatalogType type) {
		this.type = type;
	}
	
	/**
	 * 返回查询列表显示字段集合
	 */
	public String[] getProperties() {
		return properties;
	}

	/**
	 * 设置查询列表显示字段集合
	 * @param properties 查询列表显示字段集合
	 */
	public void setProperties(String[] properties) {
		this.properties = properties;
	}

	/**
	 * 返回查询条件集合
	 */
	public SearchCondition[] getConditions() {
		return conditions;
	}

	/**
	 * 设置查询条件集合
	 * @param conditions 查询条件集合
	 */
	public void setConditions(SearchCondition[] conditions) {
		this.conditions = conditions;
	}
}
