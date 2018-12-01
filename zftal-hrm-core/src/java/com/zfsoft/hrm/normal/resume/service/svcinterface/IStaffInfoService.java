package com.zfsoft.hrm.normal.resume.service.svcinterface;

import java.util.List;

import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.normal.resume.dto.StaffInfoDto;

/** 
 * 教职工信息
 * @author jinjj
 * @date 2012-8-20 上午08:53:29 
 *  
 */
public interface IStaffInfoService {

	/**
	 * 查询
	 * @param bean
	 * @return
	 */
	public DynaBean getById(DynaBean bean);
	
	/**
	 * 新增
	 * @param bean
	 */
	public void save(DynaBean bean);
	
	/**
	 * 更新
	 * @param bean
	 */
	public void update(DynaBean bean);
	
	/**
	 * 删除
	 * @param bean
	 */
	public void remove(DynaBean bean);
	
	/**
	 * 获得员工综合信息
	 * @param gh
	 * @return
	 */
	public DynaBean getStaffOverAllInfo(String gh);
	/**
	 * 获得员工综合信息
	 * @param modeType 
	 * @param gh
	 * @return
	 */
	public List<StaffInfoDto> getStaffOverAllInfoPageList(String classId,DynaBeanQuery query, String modeType);
	
	/**
	 * 获得员工综合信息,工号可以为空
	 * @param modeType 
	 * @param gh
	 * @return
	 */
	public List<StaffInfoDto> getStaffOverAllInfoPageListNoCheckGh(String classId,DynaBeanQuery query, String modeType);
	
	/**
	 * 查询集合
	 * @param query
	 * @return
	 */
	public List<DynaBean> queryBeans(DynaBeanQuery query);

}
