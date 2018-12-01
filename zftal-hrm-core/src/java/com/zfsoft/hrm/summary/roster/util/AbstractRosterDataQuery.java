package com.zfsoft.hrm.summary.roster.util;

import java.util.List;
import java.util.Map;

import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;

public abstract class AbstractRosterDataQuery {
	
	public Map<String, Object> getValuesMap() {
		return null;
	}
	public abstract List<InfoProperty> getPropList();

	public abstract String getConditioinStr();

	public abstract String getTableStr();

	public abstract String getColumnStr();

	public abstract String getOrderStr();

}
