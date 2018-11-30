package com.zfsoft.mobile.services.dao.daointerface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zfsoft.common.dao.BaseDao;
import com.zfsoft.dao.entities.JsglModel;
import com.zfsoft.mobile.services.dao.query.FwdyjsQuery;
import com.zfsoft.mobile.services.entity.FwdyjsModel;



public interface IFwdyjsDao extends BaseDao<FwdyjsModel>{

	int getCount(FwdyjsQuery fwdyjsquery);

	List<FwdyjsModel> getPagedList(FwdyjsModel fwdyjsquery);

	int getWfpCount(FwdyjsQuery fwdyjsquery);

	List<FwdyjsModel> getPagedListWfp(FwdyjsModel fwdyjsquery);

	//void insert(FwdyjsQuery fwdyjsquery);
	
	//void insertYhqx(FwdyjsQuery fwdyjsquery);

	//void delete(FwdyjsQuery fwdyjsquery);
	
	//void deleteYhqx(FwdyjsQuery fwdyjsquery);

	ArrayList<FwdyjsModel> getWeiModel(FwdyjsModel query);

	int getPagedListWeiCountNew(FwdyjsModel query);

	List<FwdyjsModel> getPagedListWeifpNew(FwdyjsModel query);

	void insert(Map<String, Object> param);

	void insertYhqx(Map<String, Object> param);

	void delete(Map<String, Object> param);

	void deleteYhqx(Map<String, Object> param);


}
