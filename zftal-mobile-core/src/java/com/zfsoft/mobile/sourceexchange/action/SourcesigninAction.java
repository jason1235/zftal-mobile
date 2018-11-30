package com.zfsoft.mobile.sourceexchange.action;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.mobile.sourceexchange.entity.Sourcesigninhis;
import com.zfsoft.mobile.sourceexchange.service.ISourcesigninService;

public class SourcesigninAction extends HrmAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ISourcesigninService sourceSignService;
	private Sourcesigninhis query=new Sourcesigninhis();
	private PageList<Sourcesigninhis> pageList;
	
	public String list(){
		pageList=sourceSignService.getPageList(query);
		return "list";
	}
	
	public Sourcesigninhis getQuery() {
		return query;
	}

	public void setQuery(Sourcesigninhis query) {
		this.query = query;
	}

	public PageList<Sourcesigninhis> getPageList() {
		return pageList;
	}

	public void setPageList(PageList<Sourcesigninhis> pageList) {
		this.pageList = pageList;
	}

	public ISourcesigninService getSourceSignService() {
		return sourceSignService;
	}

	public void setSourceSignService(ISourcesigninService sourceSignService) {
		this.sourceSignService = sourceSignService;
	}

}
