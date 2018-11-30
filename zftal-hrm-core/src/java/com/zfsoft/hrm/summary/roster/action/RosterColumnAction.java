package com.zfsoft.hrm.summary.roster.action;

import java.util.List;

import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.summary.roster.entity.RosterColumn;
import com.zfsoft.hrm.summary.roster.html.RosterColumnHtmlUtil;
import com.zfsoft.hrm.summary.roster.service.svcinterface.IRosterColumnService;

/** 
 * 花名册字段
 * @author jinjj
 * @date 2012-9-10 下午06:38:18 
 *  
 */
public class RosterColumnAction extends HrmAction {

	private static final long serialVersionUID = 5475363097970556011L;

	private IRosterColumnService rosterColumnService;
	
	private RosterColumn column;
	private String rosterId;
	private List<RosterColumn> list;
	private String type;
	
	public String save() throws Exception{
		rosterColumnService.save(column);
		//System.out.println("class:"+column.getClassId()+" roster:"+column.getRosterId()+" column:"+column.getColumnId());
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String delete() throws Exception{
		//System.out.println("class:"+column.getClassId()+" roster:"+column.getRosterId()+" column:"+column.getColumnId());
		rosterColumnService.delete(column);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String updateOrder() throws Exception{
		rosterColumnService.updateOrder(column, type);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String updateSort() throws Exception{
		rosterColumnService.updateSort(column);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String list() throws Exception{
		list = rosterColumnService.getList(rosterId);
		String html = RosterColumnHtmlUtil.parse(list);
		getValueStack().set("html", html);
		return "list";
	}

	public RosterColumn getColumn() {
		return column;
	}

	public void setColumn(RosterColumn column) {
		this.column = column;
	}

	public String getRosterId() {
		return rosterId;
	}

	public void setRosterId(String rosterId) {
		this.rosterId = rosterId;
	}

	public List<RosterColumn> getList() {
		return list;
	}

	public void setRosterColumnService(IRosterColumnService rosterColumnService) {
		this.rosterColumnService = rosterColumnService;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
