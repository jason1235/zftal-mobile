package com.zfsoft.mobile.sourceexchange.action;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.mobile.sourceexchange.entity.Sourceconsumerhis;
import com.zfsoft.mobile.sourceexchange.service.ISourceconsumerhisService;

public class SourceconsumerhisAction extends HrmAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ISourceconsumerhisService consumingHisService;
	private Sourceconsumerhis query=new Sourceconsumerhis();
	private PageList<Sourceconsumerhis> pageList;;

	public String list(){
		pageList=consumingHisService.getPageList(query);
		return "list";
	}
	
	public String control(){
		if(StringUtils.isNotBlank(query.getId())){
			int flag=consumingHisService.exchangeGoods(query);
			if(flag>0){
				this.setSuccessMessage("操作成功");
			}else{
				this.setErrorMessage("操作失败");
			}	
		}
		getValueStack().set("id",query.getId());
		this.getValueStack().set("data", getMessage());
		return "data";
	}
	
	
	public ISourceconsumerhisService getConsumingHisService() {
		return consumingHisService;
	}

	public void setConsumingHisService(ISourceconsumerhisService consumingHisService) {
		this.consumingHisService = consumingHisService;
	}

	public Sourceconsumerhis getQuery() {
		return query;
	}

	public void setQuery(Sourceconsumerhis query) {
		this.query = query;
	}

	public PageList<Sourceconsumerhis> getPageList() {
		return pageList;
	}

	public void setPageList(PageList<Sourceconsumerhis> pageList) {
		this.pageList = pageList;
	}

}
