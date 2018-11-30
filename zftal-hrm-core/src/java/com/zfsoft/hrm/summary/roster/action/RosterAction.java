package com.zfsoft.hrm.summary.roster.action;

import java.util.Date;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.summary.roster.entity.Roster;
import com.zfsoft.hrm.summary.roster.query.RosterQuery;
import com.zfsoft.hrm.summary.roster.service.svcinterface.IRosterService;
import com.zfsoft.util.base.StringUtil;

/** 
 * 花名册action
 * @author jinjj
 * @date 2012-8-30 下午02:25:45 
 *  
 */
public class RosterAction extends HrmAction {

	private static final long serialVersionUID = -2805129537037675777L;
	private IRosterService rosterService;
	
	private Roster roster;
	private RosterQuery query = new RosterQuery();
	private PageList<Roster> pageList;
	private String sortFieldName = null;
	private String asc = "up";
	
	
	public String page() throws Exception{
	    
	    /* 花名册增加类型  2013-9-5 added by 1021 */
	    query.setCreator(SessionFactory.getUser().getYhm());
	    if(!StringUtil.isEmpty(sortFieldName)){
			if(asc.equals("up")){
				query.setOrderStr( sortFieldName );
			}else{
				query.setOrderStr( sortFieldName +" desc");
			}
		}else{
			query.setOrderStr( " lx, createtime desc" );
		}
		pageList = rosterService.getPagingList(query);
		getValueStack().set("currentUserYhm", SessionFactory.getUser().getYhm());
		return "page";
	}
	
	public String save() throws Exception{
		roster.setCreatetime(new Date());
		roster.setCreator(SessionFactory.getUser().getYhm());
		rosterService.save(roster);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String input() throws Exception{
		
		return "input";
	}
	
	public String query() throws Exception{
		roster = rosterService.getById(roster.getGuid());
		return "input";
	}
	
	public String update() throws Exception{
		rosterService.update(roster);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String delete() throws Exception{
		rosterService.delete(roster.getGuid());
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String saveOther() throws Exception{
		
		return "other";
	}

	public Roster getRoster() {
		return roster;
	}

	public void setRoster(Roster roster) {
		this.roster = roster;
	}

	public RosterQuery getQuery() {
		return query;
	}

	public void setQuery(RosterQuery query) {
		this.query = query;
	}

	public void setRosterService(IRosterService rosterService) {
		this.rosterService = rosterService;
	}

	public PageList<Roster> getPageList() {
		return pageList;
	}

	public String getSortFieldName() {
		return sortFieldName;
	}

	public void setSortFieldName(String sortFieldName) {
		this.sortFieldName = sortFieldName;
	}

	public String getAsc() {
		return asc;
	}

	public void setAsc(String asc) {
		this.asc = asc;
	}
	
}
