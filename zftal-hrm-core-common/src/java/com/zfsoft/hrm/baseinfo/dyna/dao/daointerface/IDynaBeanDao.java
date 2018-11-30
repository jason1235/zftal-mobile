package com.zfsoft.hrm.baseinfo.dyna.dao.daointerface;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.zfsoft.dao.query.BaseQuery;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.SyncBean;
import com.zfsoft.hrm.baseinfo.dyna.exception.DynaBeanException;

/** 
 * 动态Bean数据操作接口
 * @ClassName: IDynaBeanDao 
 * @author jinjj
 * @date 2012-6-20 上午10:42:53 
 *  
 */
public interface IDynaBeanDao {
	
	/**
	 * 查询所有符合条件的动态Bean列表
	 * @param query 查询条件实体
	 * @return
	 * @throws DataAccessException 如果操作出现异常
	 */
	public List<Map<String, Object>> findList( BaseQuery query ) throws DataAccessException;
	public List<Map<String, Object>> findListNoUniqable(Map<String,Object> paraMap) throws DataAccessException;
	/**
	 * 查询符合条件的动态数据列表--酬金管理
	 * @param paraMap
	 * @return
	 * @throws DynaBeanException
	 */
	public List<Map<String, Object>> findListNoUniqableForCJ(Map<String,Object> paraMap) throws DataAccessException;
	
	/**
	 * 查询统计符合条件的记录数
	 * @param query 查询条件实体
	 * @return
	 * @throws DataAccessException 如果操作出现异常
	 */
	public int findCount( BaseQuery query ) throws DataAccessException;
	public int findCountNoUniqable(Map<String,Object> paraMap) throws DataAccessException;
	public int findCountNoUniqableForCJ(Map<String,Object> paraMap) throws DataAccessException;
	
	/**
	 * 查询单个动态Bean实体
	 * @param bean 动态Bean对象，其中globalid必须存在
	 * @return
	 * @throws DataAccessException 如果操作出现异常
	 */
	public Map<String, Object> findById( DynaBean bean ) throws DataAccessException;
	
	/**
	 * 将动态Bean插入到数据库中去
	 * @param bean 将被插入的动态Bean
	 * @throws DataAccessException 如果操作出现异常
	 */
	public void insert( DynaBean bean ) throws DataAccessException;
	
	/**
	 * 将动态Bean更新到数据库中去
	 * @param bean 将被更新的动态Bean
	 * @throws DataAccessException 如果操作出现异常
	 */
	public void update( DynaBean bean ) throws DataAccessException;
	
	/**
	 * 将动态Bean从数据库中删除
	 * @param bean 将被删除的动态Bean
	 * @throws DataAccessException  如果操作出现异常
	 */
	public void delete( DynaBean bean ) throws DataAccessException;
	
	/**
	 * 根据职工号删除指定动态Bean对应数据表中的数据信息
	 * @param bean 动态Bean对象
	 * @throws DataAccessException 如果操作出现异常
	 */
	public void deleteByStaffid( DynaBean bean ) throws DataAccessException;

	/**
	 * 增加日志信息
	 * @param logBean 日志实体
	 * @throws DataAccessException 如果操作出现异常
	 */
	public void insertLog( DynaBean logBean ) throws DataAccessException;
	
	/**
	 * 更新
	 * @param params keys = {table, column, staffid}
	 * @throws DataAccessException 如果操作出现异常
	 */
	public void updateUniquely( Map<String, String> params ) throws DataAccessException;
	
	/**
	 * 同步更新
	 * @param sync 同步的实体
	 */
	public void updateSync( SyncBean sync) throws DataAccessException;
	
	/**
	 * 分页查询列表 
	 * @param query 查询条件
	 * @return
	 * @throws DataAccessException 如果操作出现异常
	 */
	public List<Map<String, Object>>findPagingInfoList(BaseQuery query)throws DataAccessException;
	/**
	 * 分页查询符合条件的动态Bean列表（表别名 t） 并关联上overall表（表别名 o）
	 * overall_xm 姓名
	 * overall_dwm 部门码
	 * @param query 查询条件实体
	 * @return
	 * @throws DataAccessException 如果操作出现异常
	 */
	public List<Map<String, Object>> findPagingInfoListLeftJoinOverAll( BaseQuery query ) throws DataAccessException;
	
	/**
	 * 删除符合条件的数据 - 酬金管理
	 * @param paraMap
	 * @throws DataAccessException  如果操作出现异常
	 */
	public void deleteForCJ( Map<String, Object> paraMap ) throws DataAccessException;

}
