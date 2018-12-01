package com.zfsoft.mobile.services.dao.daointerface;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.services.dao.query.BusinessQuery;
import com.zfsoft.mobile.services.entity.Business;
import com.zfsoft.mobile.services.entity.OauthYhDyXt;

public interface IBusinessDao {

	PageList<Business> getList(BusinessQuery query);

	int getListCount(BusinessQuery query);

	void update(Business model);

	void delete(BusinessQuery query);

	void updateById(BusinessQuery query);

	void insert(Business model);

	String getProcodeBm(String classSsywxt);

	List<OauthYhDyXt> getOauthYhDyXtList(OauthYhDyXt model);

	void insertOauthYhDyXt(OauthYhDyXt model);

	void updateOauthYhDyXt(OauthYhDyXt model);

	int getOauthYhDyXtCount(OauthYhDyXt model);

	int getMaxPxm();

	void updateIndex(Map<String, String> map);

	List<Business> getBusinessList(BusinessQuery businessQuery);

}
