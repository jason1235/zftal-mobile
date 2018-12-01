package com.zfsoft.mobile.services.dao.daointerface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.services.dao.query.ServiceManagerQuery;
import com.zfsoft.mobile.services.entity.AppServiceEntity;
import com.zfsoft.mobile.services.entity.ServiceManager;
import com.zfsoft.mobile.services.entity.VisitsServiceEntity;
import com.zfsoft.mobile.services.entity.fwbmVisitEntity;

public interface IServiceManagerDao {

	PageList<ServiceManager> getList(ServiceManagerQuery serviceQuery);

	int getListCount(ServiceManagerQuery serviceQuery);

	void delete(Map<String, Object> param);

	void updateById(Map<String, Object> param);

	void insert(ServiceManager model);

	int getMaxPxm();

	void update(ServiceManager model);

	void upOrDown(ServiceManager model);

	int getListCountByWfp(ServiceManagerQuery serviceQuery);

	ArrayList<ServiceManager> getUpList(ServiceManagerQuery serviceQuery);

	ArrayList<ServiceManager> getDownList(ServiceManagerQuery serviceQuery);

	void changeShow(ServiceManager model);
	
	/**
	 * 获取科研信息类发布的服务
	 * @return
	 */
	ArrayList<ServiceManager> getKyInfoClassService();

	/**
	 * 排序函数
	 * @param map
	 */
	void updateIndex(Map<String, String> map);

	int getMaxWebFwbm();

	List<fwbmVisitEntity> getVisitsByFwbm(fwbmVisitEntity entity);

	List<AppServiceEntity> getFwdyxt();

	List<AppServiceEntity> getAllFwbm();

	PageList<VisitsServiceEntity> getAllVSStatis(VisitsServiceEntity visitsQuery);

	int getAllVSStatisCount(VisitsServiceEntity visitsQuery);

	/** 
	* @author: zhangxu
	* @Title: recommendControl 
	* @Description: 
	* @param @param param    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void updateRecommend(Map<String, Object> param);



}
