package com.zfsoft.hrm.tools.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.util.DynaBeanUtil;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.config.SearchCondition;
import com.zfsoft.hrm.config.SelectPersonConfig;
import com.zfsoft.hrm.config.SelectPersonConfigFactory;
import com.zfsoft.hrm.config.SelectPersonUtil;
import com.zfsoft.hrm.tools.web.query.SelectPersonQuery;
import com.zfsoft.hrm.tools.web.service.svcinterface.ISelectPersonService;
import com.zfsoft.util.base.StringUtil;

/**
 * 人员选择Action
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-10
 * @version V1.0.0
 */
public class SelectPersonAction extends HrmAction {
	
	private static final long serialVersionUID = -4269673189827967791L;

	private static final String SINGLE = "single";
	
	private static final String MULTIPLE = "multiple";
	
	private ISelectPersonService service;
	
	private Map<String, String> values = new HashMap<String, String>();
	
	private String type;
	
	private SelectPersonQuery query = new SelectPersonQuery();
	
	private String[] ghList;
	private Map<String, String> titleMap = new HashMap<String, String>();
	
	/**
	 * 单选
	 */
	public String single() {
		
		SelectPersonConfig config = SelectPersonConfigFactory.getSelectSinglePerson( type );
		
		doQuery( config );
		
		return SINGLE;
	}

	/**
	 * 多选
	 */
	public String multiple() {
		String defaultValue = getRequest().getParameter("defaultValue");
		if(ghList==null&&!StringUtil.isEmpty(defaultValue)){
			ghList = defaultValue.split(";");
		}
		if(ghList!=null){
			for (int i=0;i<ghList.length;i++) {
				titleMap.put(ghList[i],DynaBeanUtil.getPersonName(ghList[i] == null ? "" : ghList[i]));
			}
		}
		SelectPersonConfig config = SelectPersonConfigFactory.getSelectMultiplePerson( type );
		
		doQuery( config );
		
		return MULTIPLE;
	}
	
	/**
	 * 执行人员查询操作
	 * @param config
	 */
	private void doQuery( SelectPersonConfig config ) {
		Map<String, Object> values = new HashMap<String, Object>();
		
		for ( SearchCondition condition : config.getConditions() ) {
			String key = condition.getName();
			Object value = condition.getDefaultValue();
			
			if( value != null && !"".equals(value) ) {
				System.out.println( "key:" + key );
				value = SelectPersonUtil.getConditionValue( type, key, value);
				values.put( key, value );
			}
		}
		
		for ( String key : this.values.keySet() ) {
			Object value = this.values.get( key );
			value = SelectPersonUtil.getConditionValue( type, key, value);
			values.put( key, value );
			
		}
		
		
		query.setConfig( config );
		
		query.setValues( values );
		
		PageList<DynaBean> beans = service.getPerson( query );
		this.setInActionContext("paginator", beans.getPaginator());
		getValueStack().set( "query", query );
		getValueStack().set( "beans", beans );
	}
	
	public void setService(ISelectPersonService service) {
		this.service = service;
	}
	
	public Map<String, String> getValues() {
		return values;
	}

	public void setValues(Map<String, String> values) {
		this.values = values;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public SelectPersonQuery getQuery() {
		return query;
	}

	public void setQuery(SelectPersonQuery query) {
		this.query = query;
	}

	/**
	 * 返回
	 */
	public String[] getGhList() {
		return ghList;
	}

	/**
	 * 设置
	 * @param gh 
	 */
	public void setGhList(String[] ghList) {
		this.ghList = ghList;
	}

	/**
	 * 返回
	 */
	public Map<String, String> getTitleMap() {
		return titleMap;
	}

	/**
	 * 设置
	 * @param titelMap 
	 */
	public void setTitelMap(Map<String, String> titleMap) {
		this.titleMap = titleMap;
	}
	
	
}
