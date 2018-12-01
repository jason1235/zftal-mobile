package com.zfsoft.hrm.baseinfo.search.service.svcinterface;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.search.entities.Condition;
import com.zfsoft.hrm.baseinfo.search.exception.SearchException;
import com.zfsoft.hrm.baseinfo.search.query.ConditionQuery;
import com.zfsoft.hrm.core.exception.RuleException;

/**
 * 条件定义业务操作
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-13
 * @version V1.0.0
 */
public interface IConditionService {
	
	/**
	 * 获取符合条件的条件列表集合
	 * <p>
	 * 该方法会对查询到的数据列表进行数据填充
	 * </p>
	 * @param query 查询条件
	 * @return
	 * @throws SearchException 如果操作出现异常
	 */
	public List<Condition> getFullList(ConditionQuery query) throws SearchException;
	
	/**
	 * 获取符合条件的条件系列列表
	 * @param query 查询条件
	 * @return 符合条件的条件系列列表
	 * @throws SearchException 如果操作出现异常
	 */
	public List<Condition> getCatalogList(ConditionQuery query) throws SearchException;
	
	/**
	 * 增加条件系列
	 * @param entity 条件系列信息
	 * @throws RuleException (runtime) 如果entity==null
	 * @throws SearchException 如果操作出现异常
	 */
	public void addCatalog(Condition entity) throws SearchException;
	
	/**
	 * 修改条件系列
	 * @param entity 条件系列信息
	 * @throws RuleException (runtime) 如果entity==null
	 * @throws SearchException 如果操作出现异常
	 */
	public void modifyCatalog(Condition entity) throws SearchException;
	
	/**
	 * 获取符合条件的条件列表
	 * @param query 查询条件
	 * @return 符合条件的条件列表
	 * @throws SearchException 如果操作出现异常
	 */
	public List<Condition> getItemList(ConditionQuery query) throws SearchException;
	
	/**
	 * 获取指定的条件（条件系列）信息
	 * @param guid 全局ID
	 * @return
	 * @throws SearchException 如果操作出现异常
	 */
	public Condition getById(String guid) throws SearchException;
	
	/**
	 * 增加条件
	 * @param entityItem 条件（条件系列）信息
	 * @throws RuleException (runtime) 如果entity==null
	 * @throws SearchException 如果操作出现异常
	 */
	public void addItem(Condition entity) throws SearchException;
	
	/**
	 * 修改条件
	 * @param entity 条件（条件系列）信息
	 * @throws RuleException (runtime) 如果entity==null
	 * @throws SearchException 如果操作出现异常
	 */
	public void modifyItem(Condition entity) throws SearchException;
	
	/**
	 * 删除条件
	 * @param guid 全局ID，不得为null
	 * @throws RuleException (runtime)包含子节点，无法删除
	 * @throws SearchException 如果操作出现异常
	 */
	public void removeItem(String guid) throws SearchException;
	
	/**
	 * 更新条件序列
	 * @param list
	 * @throws SearchException
	 */
	public void updateOrder(List<Condition> list) throws SearchException;
	/**
	 * 分页查询
	 * @param list
	 * @throws SearchException
	 */
	public PageList getPagingInfo(ConditionQuery query);
	
	/**
	 * 批量保存
	 * @param propertyName
	 * @param keyMap
	 * @param con
	 */
	public void saveBatch(String propertyName,Map<String,String> keyMap,Condition con);
	
	/**
	 * 批量删除
	 * @param root
	 */
	public void deleteBatch(Condition root);
}
