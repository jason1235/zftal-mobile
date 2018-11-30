package com.zfsoft.hrm.manoeuvre.manoeuvreInfo.business.businessinterface;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.manoeuvre.manoeuvreInfo.entities.ManoeuvreInfo;
import com.zfsoft.hrm.manoeuvre.manoeuvreInfo.query.ManoeuvreQuery;

public interface IManoeuvreBusiness {

	/**
	 * 新增人员调配信息
	 * @param info
	 */
	public boolean add(ManoeuvreInfo info);
	
	/**
	 * 修改人员调配信息
	 * @param info
	 */
	public boolean modify(ManoeuvreInfo info);

	/**
	 * 修改信息当前环节
	 * @param info
	 */
	public boolean modifyCurrentTask(ManoeuvreInfo info) ;

	/**
	 * 删除人员调配信息
	 * @param id
	 */
	public void remove(String id);

	/**
	 * 获取人员调配信息列表
	 * @param query
	 * @return
	 */
	public List<ManoeuvreInfo> getList(ManoeuvreQuery query);

	/**
	 * 根据信息编号查询人员调配信息列表
	 * @param id
	 * @return
	 */
	public ManoeuvreInfo getById(String id);

	/**
	 * 分页查询人员调配信息列表
	 * @param query
	 * @return
	 */
	public PageList<ManoeuvreInfo> getPagingList(ManoeuvreQuery query);
	
	public boolean existByNode(String nid);
	/**
	 * 保存人员调配信息（保存不执行）
	 * @param info
	 */
	public boolean save(ManoeuvreInfo info);

}
