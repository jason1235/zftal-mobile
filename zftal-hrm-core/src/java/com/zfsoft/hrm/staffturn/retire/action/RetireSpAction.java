package com.zfsoft.hrm.staffturn.retire.action;

import java.util.HashMap;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.config.SearchCondition;
import com.zfsoft.hrm.config.SelectPersonConfig;
import com.zfsoft.hrm.config.SelectPersonConfigFactory;
import com.zfsoft.hrm.config.SelectPersonUtil;
import com.zfsoft.hrm.staffturn.retire.entities.RetireRule;
import com.zfsoft.hrm.tools.web.query.SelectPersonQuery;
import com.zfsoft.hrm.tools.web.service.svcinterface.ISelectPersonService;

public class RetireSpAction extends HrmAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4008580066773279125L;

	private ISelectPersonService service;
	
	private SelectPersonQuery query = new SelectPersonQuery();
	
	private String type;
	
	private String dept;
	
	private RetireRule model=new RetireRule();
	
	/**
	 * 多选
	 */
	public String multiple() {
		
		SelectPersonConfig config = SelectPersonConfigFactory.getSelectMultiplePerson( type );
		
		doQuery( config );
		
		return "multiple";
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
			
			if ("org".equals(key)) {
				values.put( key, dept );
			}
			
			if( value != null && !"".equals(value) ) {
				System.out.println( "key:" + key );
				value = SelectPersonUtil.getConditionValue( type, key, value);
				values.put( key, value );
			}
		}
		query.setConfig( config );
		query.setValues( values );
		
		PageList<DynaBean> beans = service.getPerson( query );
		this.setInActionContext("paginator", beans.getPaginator());
		getValueStack().set( "query", query );
		getValueStack().set( "beans", beans );
	}

	/**
	 * @return the service
	 */
	public ISelectPersonService getService() {
		return service;
	}

	/**
	 * @param service the service to set
	 */
	public void setService(ISelectPersonService service) {
		this.service = service;
	}

	/**
	 * @return the query
	 */
	public SelectPersonQuery getQuery() {
		return query;
	}

	/**
	 * @param query the query to set
	 */
	public void setQuery(SelectPersonQuery query) {
		this.query = query;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the dept
	 */
	public String getDept() {
		return dept;
	}

	/**
	 * @param dept the dept to set
	 */
	public void setDept(String dept) {
		this.dept = dept;
	}

	/**
	 * @return the model
	 */
	public RetireRule getModel() {
		return model;
	}


}
