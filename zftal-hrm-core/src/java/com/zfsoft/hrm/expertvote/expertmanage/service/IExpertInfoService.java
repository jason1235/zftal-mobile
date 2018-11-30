package com.zfsoft.hrm.expertvote.expertmanage.service;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.expertvote.expertmanage.entity.ExpertInfo;

public interface IExpertInfoService {
	public void insert(ExpertInfo expertInfo) throws RuntimeException;
	public PageList<ExpertInfo> getPagedExpert(ExpertInfo expertInfo) throws RuntimeException;
	public void delete(String id) throws RuntimeException;
	/**
	 * 随机抽取专家（范围全部）
	 * @param: 
	 * @return:
	 */
	public List<String> getListIdByAll() throws RuntimeException;
	/**
	 * 随机抽取专家（范围本专业领域）
	 */
	public List<String> getListIdBySingle(String zydm) throws RuntimeException;
}
