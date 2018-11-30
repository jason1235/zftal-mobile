package com.zfsoft.hrm.baseinfo.infoclass.service.impl;

import java.util.List;

import com.zfsoft.hrm.baseinfo.infoclass.business.bizinterface.IInfoPropertyViewBusiness;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoPropertyView;
import com.zfsoft.hrm.baseinfo.infoclass.query.InfoPropertyViewQuery;
import com.zfsoft.hrm.baseinfo.infoclass.service.svcinterface.IInfoPropertyViewService;
import com.zfsoft.hrm.baseinfo.infoclass.util.InfoPropertyViewCacheUtil;

/** 
 * @author jinjj
 * @date 2012-11-13 上午11:06:38 
 *  
 */
public class InfoPropertyViewServiceImpl implements IInfoPropertyViewService {

	private IInfoPropertyViewBusiness viewBusiness;
	
	@Override
	public void save(List<InfoPropertyView> list) {
		if(list.size()>0){
			InfoPropertyViewQuery query = new InfoPropertyViewQuery();
			InfoPropertyView obj = list.get(0);
			query.setClassId(obj.getClassId());
			query.setUsername(obj.getUsername());
			viewBusiness.deleteBatch(query);
			
			for(InfoPropertyView view : list){
				viewBusiness.save(view);
			}
			InfoPropertyViewCacheUtil.clear();
		}	
	}
	
	public void deleteBatch(InfoPropertyViewQuery query){
		viewBusiness.deleteBatch(query);
	}

	@Override
	public List<InfoPropertyView> getList(InfoPropertyViewQuery query) {
		return viewBusiness.getList(query);
	}

	public void setViewBusiness(IInfoPropertyViewBusiness viewBusiness) {
		this.viewBusiness = viewBusiness;
	}

}
