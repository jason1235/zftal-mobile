package com.zfsoft.hrm.summary.roster.query;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.query.BaseQuery;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.summary.roster.util.AbstractRosterDataQuery;

/** 
 * 花名册数据查询实体
 * @author jinjj
 * @date 2012-9-7 上午10:19:22 
 *  
 */
public class RosterDataQuery extends BaseQuery {

	private static final long serialVersionUID = -1751412746155952942L;
	
	private AbstractRosterDataQuery util;
	
	public RosterDataQuery(){
		setPerPageSize(10);
	}
	
	/**
	 * 条件拼装字符串
	 * @return
	 */
	public String getConditionStr(){
		return util.getConditioinStr();
	}
	
	public String getTableStr(){
		return util.getTableStr();
	}
	
	public String getColumnStr(){
		return util.getColumnStr();
	}
	
	public String getOrderStr(){
		return util.getOrderStr();
	}

	public List<InfoProperty> getPropList() {
		return util.getPropList();
	}

	public void setUtil(AbstractRosterDataQuery util) {
		this.util = util;
	}

	public AbstractRosterDataQuery getUtil() {
		return util;
	}
	
	public Map<String, Object> getValuesMap() {
		return util.getValuesMap();
	}
	
}
