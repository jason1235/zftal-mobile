package com.zfsoft.hrm.expertvote.vote.service;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.expertvote.vote.entity.DeclareInstance;
import com.zfsoft.hrm.expertvote.vote.query.DeclareInstanceQuery;

/**
 * 
 * @author ChenMinming
 * @date 2014-3-27
 * @version V1.0.0
 */
public interface IDeclareInstanceService {
	/**
	 * 获取详情
	 * @param id
	 * @return
	 */
	DeclareInstance getById(String id);
	/**
	 * 分页查询
	 * @param query
	 * @return
	 */
	PageList<DeclareInstance> getPageList(DeclareInstanceQuery query);
}