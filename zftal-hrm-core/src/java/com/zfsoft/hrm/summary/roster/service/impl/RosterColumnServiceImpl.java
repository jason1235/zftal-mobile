package com.zfsoft.hrm.summary.roster.service.impl;

import java.util.List;

import com.zfsoft.hrm.summary.roster.business.IRosterColumnBusiness;
import com.zfsoft.hrm.summary.roster.entity.RosterColumn;
import com.zfsoft.hrm.summary.roster.service.svcinterface.IRosterColumnService;

/** 
 * 花名册字段service实现
 * @author jinjj
 * @date 2012-9-10 下午06:18:47 
 *  
 */
public class RosterColumnServiceImpl implements IRosterColumnService {

	private IRosterColumnBusiness rosterColumnBusiness;
	
	@Override
	public void save(RosterColumn column) {
		rosterColumnBusiness.save(column);
	}

	@Override
	public void delete(RosterColumn column) {
		rosterColumnBusiness.delete(column);
	}

	@Override
	public void updateOrder(RosterColumn column, String type) {
		List<RosterColumn> list = rosterColumnBusiness.getList(column.getRosterId());
		int current=-1;
		for(int i=0;i<list.size();i++){
			RosterColumn c = list.get(i);
			c.setOrder(String.valueOf(i));
			if(c.getColumnId().equals(column.getColumnId())){
				current = i;
			}
			list.set(i, c);
		}
		
		if("up".equals(type)){
			RosterColumn t = list.get(current-1);
			RosterColumn c = list.get(current);
			t.setOrder(current+"");
			c.setOrder(current-1+"");
			list.set(current, t);
			list.set(current-1, c);
		}
		if("down".equals(type)){
			RosterColumn t = list.get(current+1);
			RosterColumn c = list.get(current);
			t.setOrder(current+"");
			c.setOrder(current+1+"");
			list.set(current, t);
			list.set(current+1, c);
		}
		for(RosterColumn c : list){
			rosterColumnBusiness.updateOrder(c);
		}
	}

	@Override
	public void updateSort(RosterColumn column) {
		rosterColumnBusiness.updateSort(column);
	}

	@Override
	public List<RosterColumn> getList(String rosterId) {
		return rosterColumnBusiness.getList(rosterId);
	}

	public void setRosterColumnBusiness(IRosterColumnBusiness rosterColumnBusiness) {
		this.rosterColumnBusiness = rosterColumnBusiness;
	}

}
