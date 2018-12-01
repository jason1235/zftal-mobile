package com.zfsoft.mobile.services.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zfsoft.common.service.BaseService;
import com.zfsoft.dao.entities.JsglModel;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.services.dao.query.FwdyjsQuery;
import com.zfsoft.mobile.services.entity.FwdyjsModel;

public interface IFwdyjsService extends BaseService<FwdyjsModel>{

	List<FwdyjsModel> getPagedList(FwdyjsModel fwdyjsquery);

	List<FwdyjsModel> getPagedListWeifp(FwdyjsModel fwdyjsquery);

	//void insert(FwdyjsQuery fwdyjsquery);

	//void delete(FwdyjsQuery fwdyjsquery);

	ArrayList<FwdyjsModel> getWeiModel(FwdyjsModel query);

	PageList<FwdyjsModel> getPagedListWeifpNew(FwdyjsModel query);

	void insert(Map<String, Object> param);

	void delete(Map<String, Object> param);


}
