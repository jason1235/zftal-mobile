package com.zfsoft.hrm.baseinfo.search.dao.daointerface;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zfsoft.dao.query.BaseQuery;
import com.zfsoft.hrm.baseinfo.search.entities.CommonSearch;
import com.zfsoft.hrm.baseinfo.search.entities.CommonSearchRelation;
import com.zfsoft.hrm.common.IBaseDao;

/**
 * {@link CommonSearch }数据操作接口
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-15
 * @version V1.0.0
 */
public interface ICommonSearchDao extends IBaseDao<CommonSearch> {

	public void insertRelation(CommonSearchRelation relation);
	
	public void updateRelation(CommonSearchRelation relation);
	
	public void deleteRelation(CommonSearchRelation relation);
	
	public void deleteRelations(String id);
	/**
	 * 查询记录数
	 * @param query 查询条件
	 * @return 符合条件的记录数量
	 * @throws DataAccessException 如果操作出现异常
	 */
	public int findPagingCount(BaseQuery query) throws DataAccessException;
	/**
	 * 分页查询列表 
	 * @param query 查询条件
	 * @return
	 * @throws DataAccessException 如果操作出现异常
	 */
	public List<CommonSearch>findPagingInfoList(BaseQuery query)throws DataAccessException;
}
