package com.zfsoft.hrm.expertvote.expertmanage.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.expertvote.expertmanage.entity.ExpertInfo;

public interface IExpertInfoDao {
	/**
	 * 新增
	 * @param: 
	 * @return:
	 */
	public void insert(ExpertInfo expertInfo) throws DataAccessException;
	/**
	 * 列表
	 */
	public PageList<ExpertInfo> getPagedExpert(ExpertInfo expertInfo) throws DataAccessException;
	/**
	 * 删除
	 */
	public void delete(String id) throws DataAccessException;
	/**
	 * 获取所有专家的ID：随机抽取专家（范围：全部）
	 */
	public List<String> getListIdByAll() throws DataAccessException;
	/**
	 * 随机抽取专家（范围：本专业领域）
	 */
	public List<String> getListIdBySingle(String zydm) throws DataAccessException;
}
