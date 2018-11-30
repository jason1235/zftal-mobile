package com.zfsoft.workflow.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zfsoft.workflow.exception.WorkFlowException;
import com.zfsoft.workflow.model.SpBusiness;
import com.zfsoft.workflow.model.query.SpBusinessQuery;

/**
 * 业务管理DAO接口类
 * @author yingjie.fan
 * @version 1.0
 */
public interface ISpBusinessDao {
	/* @interface model: 添加一条SpBusiness记录 */
	void insert(SpBusiness spBusiness) throws DataAccessException;

	/* @interface model: 更新一条SpBusiness记录 */
	void update(SpBusiness spBusiness) throws DataAccessException;

	/* @interface model: 删除一条SpBusiness记录 */
	void delete(String bId) throws DataAccessException;
	
	/* @interface model: 删除一条SpBusiness记录 */
	void deleteByRelDetail(String relDetail) throws DataAccessException;
	
	/* @interface model: 根据条件查询是否有SpBusiness记录 */
	int getCountByNameAndType(SpBusiness spBusiness) throws DataAccessException;
	
	/* @interface model: 查询是否有和流程关联记录 */
	int getCountOfRelevance(String bId) throws DataAccessException;

	/* @interface model: 查询SpBusiness结果集,返回SpBusiness对象的集合 */
	List<SpBusiness> findSpBusiness(SpBusiness spBusiness) throws DataAccessException;	
	
	/* @interface model: 查询SpBusiness结果集,返回SpBusiness对象的集合 */
	List<SpBusiness> findSpWorkBusiness(SpBusiness spBusiness) throws WorkFlowException;
	
	List<SpBusiness> findSpBusinessById(String bid);
	/*
	 * @interface model: 根据关联详情查询对应的SpBusiness结果集,返回相应的业务编码
	 */
	String findSpBusinessCodeByRelDetail(String Detail) throws DataAccessException;
	
	List<SpBusiness> findSpBusinessByRelDetail(String relDetail);
	
	int getPagingCount(SpBusinessQuery query);
	
	/* @interface model: 分页查询SpBusiness结果集,返回SpBusiness对象的集合 */
	List<SpBusiness> getPagingList(SpBusinessQuery query) throws DataAccessException;
}
