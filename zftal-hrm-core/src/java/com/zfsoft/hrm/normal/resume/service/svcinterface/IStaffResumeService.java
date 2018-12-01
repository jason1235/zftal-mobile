package com.zfsoft.hrm.normal.resume.service.svcinterface;

import java.util.List;

import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;

/** 
 * 员工详细信息(简历)service
 * @ClassName: IStaffResumeService 
 * @author jinjj
 * @date 2012-6-21 上午09:35:38 
 *  
 */
public interface IStaffResumeService {

	/**
	 * 以信息类别获取信息类数据
	 * @param query
	 * @return
	 */
	public List<List<DynaBean>> getInfoByCatalog(String catalogId,DynaBeanQuery query);
	/**
	 * 获取信息类数据
	 * @param query
	 * @return
	 */
	public List<DynaBean> getInfoByClass(DynaBeanQuery query);
	
	/**
	 * 获取信息类数据
	 * @param query
	 * @return
	 */
	public DynaBean getById(DynaBean bean);
	
	/**
	 * 更新信息类数据
	 * @param bean
	 */
	public void update(DynaBean bean);
	
	/**
	 * 删除数据
	 * @param bean
	 */
	public void delete(DynaBean bean);
	
	/**
	 * 新增数据
	 * @param bean
	 */
	public void insert(DynaBean bean);
}
