package com.zfsoft.hrm.baseinfo.infoclass.dao.daointerface;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.baseinfo.infoclass.query.InfoPropertyQuery;

/**
 * 信息类属性数据操作接口
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @since 2012-5-21
 * @version V1.0.0
 */
public interface IInfoPropertyDao {

	/**
	 * 根据条件查询信息类属性列表
	 * @param query 查询条件
	 * @return
	 * @throws DataAccessException 如果操作出现异常
	 */
	public List<InfoProperty> findList(InfoPropertyQuery query) throws DataAccessException;

	/**
	 * 统计信息类属性记录数
	 * @param query 统计条件
	 * @return
	 * @throws DataAccessException 如果操作出现异常
	 */
	public int findCount(InfoPropertyQuery query) throws DataAccessException;

	/**
	 * 查询信息类属性
	 * @param guid 信息类属性ID
	 * @return
	 * @throws DataAccessException 如果操作出现异常
	 */
	public InfoProperty findById(String guid) throws DataAccessException;

	/**
	 * 增加信息类属性
	 * @param entity 信息类属性
	 * @throws DataAccessException 如果操作出现异常
	 */
	public void insert(InfoProperty entity) throws DataAccessException;

	/**
	 * 修改信息类属性
	 * @param entity 信息类属性
	 * @throws DataAccessException 如果操作出现异常
	 */
	public void update(InfoProperty entity) throws DataAccessException;
	
	/**
	 * 修改信息类同步属性
	 * @param entity 信息类属性
	 */
	public void updateSync(InfoProperty entity);

	/**
	 * 删除信息类属性
	 * @param guid 信息类属性ID
	 * @throws DataAccessException 如果操作出现异常
	 */
	public void delete(String guid) throws DataAccessException;
	
	/**
	 * 根据信息类ID删除信息类属性
	 * @param classId 信息类ID
	 * @throws DataAccessException 如果操作出现异常
	 */
	public void deleteByClassId(String classId) throws DataAccessException;
	
	/**
	 * 更新信息类属性索引
	 * <p>
	 * 将指定信息类属性之后的索引信息类属性索引向上移一位，在信息类属性删除时使用
	 * </p>
	 * @param entity 信息类属性
	 * @throws DataAccessException 如果操作出现异常
	 */
	public void updateAllIndex(InfoProperty entity) throws DataAccessException;
	
	/**
	 * 更新索引字段
	 * @param entity 信息类属性
	 * @throws DataAccessException 如果操作出现异常
	 */
	public void updateIndex(InfoProperty entity) throws DataAccessException;
	
	/**
	 * 同步修改字段
	 * @param map
	 * 			key: targetTable 同步目标表
	 * 			     targetField 同步目标对象
	 * 				 infoField   数据源字段
	 * 				 infoTable	   数据源表
	 * 				 condition   同步条件
	 * @return
	 * @throws DataAccessException
	 */
	public int syncProperty(Map<String, String> map) throws DataAccessException;

	public void deleteMenuProperty(String guid);
}
