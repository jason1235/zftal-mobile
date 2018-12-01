package com.zfsoft.mobile.services.service;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.services.dao.query.BusinessQuery;
import com.zfsoft.mobile.services.entity.Business;
import com.zfsoft.mobile.services.entity.OauthYhDyXt;

public interface IBusinessService {

	public PageList<Business> getList(BusinessQuery query);

	public void updateQiYong(BusinessQuery query);

	public void remove(BusinessQuery query);

	public void updateTingYong(BusinessQuery query);

	public void insert(Business model) throws Exception;

	public void update(Business model) throws Exception;

	public String getProcodeBm(String classSsywxt);

	public void getYdht(BusinessQuery ydhtQuery);
	
	public List<OauthYhDyXt> getOauthYhDyXtList(OauthYhDyXt model);

	public void insertOauthYhDyXt(OauthYhDyXt model);

	public void updateOauthYhDyXt(OauthYhDyXt model);

	public int getOauthYhDyXtCount(OauthYhDyXt model);

	public void updateIndex(Map<String, String> map);
	
	public String check(Business model);

	public List<Business> getBusinessList(BusinessQuery businessQuery);
}
