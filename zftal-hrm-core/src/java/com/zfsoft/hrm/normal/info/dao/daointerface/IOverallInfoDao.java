package com.zfsoft.hrm.normal.info.dao.daointerface;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zfsoft.dao.query.BaseQuery;
import com.zfsoft.hrm.normal.info.entity.OverallInfo;

/**
 * 个人综合信息数据库操作接口
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-15
 * @version V1.0.0
 */
public interface IOverallInfoDao {

	/**
	 * 统计符合条件的记录数
	 * @param query 查询条件
	 * @return
	 * @throws DataAccessException 如果操作出现异常
	 */
	public int count(BaseQuery query) throws DataAccessException;
	/**
	 * @param searchStr
	 * @return
	 * @throws DataAccessException
	 */
	public List<OverallInfo> userListThink(OverallInfo query) throws DataAccessException;
}
