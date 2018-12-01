package com.zfsoft.mobile.services.service;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.services.dao.query.BusinessQuery;
import com.zfsoft.mobile.services.dao.query.ServiceManagerQuery;
import com.zfsoft.mobile.services.entity.AppServiceEntity;
import com.zfsoft.mobile.services.entity.FwdyjsModel;
import com.zfsoft.mobile.services.entity.ServiceManager;
import com.zfsoft.mobile.services.entity.VisitsServiceEntity;
import com.zfsoft.mobile.services.entity.fwbmVisitEntity;

public interface IServiceManagerService {

	PageList<ServiceManager> getList(ServiceManagerQuery serviceQuery);

	void remove(Map<String, Object> param);

	void fabu(Map<String, Object> param);

	void quxiao(Map<String, Object> param);

	void insert(ServiceManager model);

	void update(ServiceManager model);


	void up(ServiceManager model);

	void down(ServiceManager model);

	void changeShow(ServiceManager model);
	
	List<ServiceManager> getKyInfoClassService();

	void updateIndex(Map<String, String> map);

	int getMaxWebFwbm();

	List<fwbmVisitEntity> getVisitsByFwbm(fwbmVisitEntity entity);
	
	public String check(ServiceManager model);

	List<AppServiceEntity> getFwdyxt();

	List<AppServiceEntity> getAllFwbm();

	PageList<VisitsServiceEntity> getAllVSStatis(VisitsServiceEntity visitsQuery);

	/** 
	* @author: zhangxu
	* @Title: recommendControl 
	* @Description: 服务是否推荐
	* @param @param param    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void updateRecommend(Map<String, Object> param);

}
