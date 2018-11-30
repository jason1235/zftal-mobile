package com.zfsoft.hrm.authpost.auth.action;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.authpost.auth.query.AuthDetailQuery;
import com.zfsoft.hrm.authpost.auth.service.svcinterface.IAuthDetailService;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.util.base.StringUtil;

/** 
 * 编制详细
 * @author jinjj
 * @date 2012-7-27 上午11:53:20 
 *  
 */
public class AuthDetailAction extends HrmAction {

	private static final long serialVersionUID = -2219952889135200967L;

	private IAuthDetailService authDetailService;
	private AuthDetailQuery query = new AuthDetailQuery();
	private PageList pageList;
	private String sortFieldName = null;
	private String asc = "up";
	/**
	 * 分页
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception{
		if(!StringUtil.isEmpty(sortFieldName)){
			if(asc.equals("up")){
				query.setOrderStr( sortFieldName );
			}else{
				query.setOrderStr( sortFieldName +" desc");
			}
		}else{
			query.setOrderStr( " zgh" );
		}
		pageList = authDetailService.getPage(query);
		this.setInActionContext("paginator", pageList.getPaginator());
		return "list";
	}
	
	public AuthDetailQuery getQuery() {
		return query;
	}
	
	public void setQuery(AuthDetailQuery query) {
		this.query = query;
	}
	
	public PageList getPageList() {
		return pageList;
	}
	
	public void setAuthDetailService(IAuthDetailService authDetailService) {
		this.authDetailService = authDetailService;
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
