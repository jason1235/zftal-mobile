package com.zfsoft.hrm.baseinfo.search.service.svcinterface;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.search.entities.CommonSearch;
import com.zfsoft.hrm.baseinfo.search.entities.CommonSearchRelation;
import com.zfsoft.hrm.baseinfo.search.entities.Condition;
import com.zfsoft.hrm.baseinfo.search.exception.SearchException;
import com.zfsoft.hrm.baseinfo.search.query.CommonSearchQuery;
import com.zfsoft.hrm.config.Direction;

/**
 * 普通查询定义业务操作接口
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-15
 * @version V1.0.0
 */
public interface ICommonSearchService {
	
	/**
	 * 获取普通查询实体列表
	 * @param query 查询条件
	 * @return 符合查询条件的普通查询列表
	 * @throws SearchException (runtime)如果操作出现异常 
	 */
	public List<CommonSearch> getSearchs(CommonSearchQuery query) throws SearchException;
	
	/**
	 * 获取指定的普通查询实体
	 * @param guid 普通查询的全局ID
	 * @return
	 * @throws SearchException (runtime)如果操作出现异常 
	 */
	public CommonSearch getSearch(String guid) throws SearchException;
	
	/**
	 * 增加普通查询
	 * @param bean 普通查询实体
	 * @throws SearchException (runtime)如果操作出现异常 
	 */
	public void addSearch(CommonSearch bean) throws SearchException;
	
	/**
	 * 修改普通查询实体
	 * @param bean 普通查询实体
	 * @throws SearchException (runtime)如果操作出现异常
	 */
	public void modifySearch(CommonSearch bean) throws SearchException;
	
	/**
	 * 删除普通查询实体
	 * @param guid 普通查询实体全局ID
	 * @throws SearchException (runtime)如果操作出现异常
	 */
	public void removeSearch(String guid) throws SearchException;
	
	/**
	 * 新增普通查询与条件的映射
	 * @param relation 映射关系实体
	 * @throws SearchException
	 */
	public void insertRelation(CommonSearchRelation relation)throws SearchException;
	
	/**
	 * 删除普通查询与条件的映射
	 * @param relation 映射关系实体
	 * @throws SearchException
	 */
	public void deleteRelation(CommonSearchRelation relation)throws SearchException;
	
	/**
	 * 获取该普通查询的的统计数据
	 * <p>
	 * 获取指定普通查询条件类型下各个条件系列（及条件）统计情况
	 * </p>
	 * <pre>
	 * 如：
	 * 行政单位100
	 * 校长办公室5	党政办公室6	人事处20		组织处16
	 * 
	 * 教学单位500
	 * 理学院60		计算机学院89	商学院81		医学院24
	 * </pre>
	 * @param guid 普通查询实体的全局ID
	 * @return
	 * @throws SearchException (runtime)如果操作出现异常
	 */
	public CommonSearch getCountData(String guid) throws SearchException;
	
	/**
	 * 获取普通查询实体列表
	 * @param query 查询条件
	 * @return 符合查询条件的普通查询列表
	 * @throws SearchException (runtime)如果操作出现异常 
	 */
	public PageList  getSearchsPagingInfo(CommonSearchQuery query) throws SearchException;
	
	/**
	 * 获取查询子项的数据(可用于三级或四级的信息展现)
	 * @param parentGuid 父条件ID
	 * @return
	 */
	public List<Condition> getSubs(String parentGuid);
	
	/**
	 * 菜单移动
	 * @param menuId
	 * @param dirc 方向
	 */
	public void doMenuMove(String menuId,Direction dirc);
}
