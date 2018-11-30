package com.zfsoft.hrm.baseinfo.dyna.business.bizinterface;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.dyna.exception.DynaBeanException;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.exception.InfoClassException;

/** 
 * 动态类Business操作接口
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-10
 * @version V1.0.0
 */
public interface IDynaBeanBusiness {
	
	/**
	 * 根据动态bean查询条件获取符合条件的动态bean列表
	 * @param query 动态bean查询条件
	 * @return
	 * @throws InfoClassException 如果操作出现异常
	 */
	public List<DynaBean> queryBeans( DynaBeanQuery query ) throws InfoClassException;
	/**
	 * 查询符合条件的动态数据列表
	 * @param query 动态数据查询实体
	 * @return
	 * @throws DynaBeanException 如果操作出现异常
	 */
	public List<DynaBean> findList( DynaBeanQuery query ) throws DynaBeanException;
	/**
	 * 查询符合条件的动态数据列表
	 * @param paraMap
	 * @return
	 * @throws DynaBeanException
	 */
	public List<Map<String, Object>> findListNoUniqable(Map<String,Object> paraMap) throws DynaBeanException;
	/**
	 * 查询符合条件的动态数据列表--酬金管理
	 * @param paraMap
	 * @return
	 * @throws DynaBeanException
	 */
	public List<Map<String, Object>> findListNoUniqableForCJ(Map<String,Object> paraMap) throws DynaBeanException;
	/**
	 * 删除记录,该方法属性业务方法
	 * @param bean 将被删除的记录
	 * @return 若主库记录被修改则返回true，否则返回false.
	 * @throws DynaBeanException 如果操作出现异常
	 */
	public boolean removeRecord( DynaBean bean ) throws DynaBeanException;
	
	/**
	 * 删除记录,该方法属性业务方法,工号可以为空
	 * @param bean 将被删除的记录
	 * @return 若主库记录被修改则返回true，否则返回false.
	 * @throws DynaBeanException 如果操作出现异常
	 */
	public boolean removeRecordNoCheckGh( DynaBean bean ) throws DynaBeanException;
	
	/**
	 * 查询某字段paramName值为value的数据
	 * @methodName findUniqueByParam 
	 * @param paramName
	 * @param value
	 * @param clazz
	 * @return
	 * @throws InfoClassException DynaBean
	 */
	public DynaBean findUniqueByParam(String paramName, Object value,InfoClass clazz) throws InfoClassException;
	/**
	 * 查询某字段paramName值为value的数据
	 * <p>该方法只查询默认类型的综合信息</p>
	 * @param paramName 字段名称
	 * @param value 值
	 * @return
	 * @throws DynaBeanException如果操作出现异常
	 */
	public DynaBean findUniqueByParam(String paramName,Object value) throws InfoClassException;
	
	/**
	 * 根据主键（人员主键，如：工号）值获取人员综合信息
	 * <p>该方法可视为<b> 人员信息查询方法</b></p>
	 * @return 人员默认类型的人员综合信息，如果不存在 则返回null
	 * @throws InfoClassException 如果操作出现异常
	 */
	public DynaBean queryBeanByPK( String pkValue ) throws InfoClassException;

	/**
	 * 根据工号(gh)获取动态bean数据
	 * @param bean
	 * @return
	 */
	public DynaBean findById(DynaBean bean);
	
	/**
	 * 新增动态bean数据
	 * @param bean
	 */
	public void addBean(DynaBean bean);
	
	/**
	 * 更新动态bean数据
	 * @param bean
	 * @return
	 */
	public boolean modifyRecord(DynaBean bean);
	
	/**
	 * 更新动态bean数据,工号可以为空
	 * @param bean
	 * @return
	 */
	public boolean modifyRecordNoCheckGh(DynaBean bean);
	
	/**
	 * 更新动态类数据（只更新指定部分数据,亦只对更新数据进行非空校验）
	 * @param bean 要更新的动态信息类（里面必须要有的属性 globalid）
	 * @param values 所更新的信息
	 * @param compel 是否强制更新（无视其【是否可编辑】属性）
	 * @return
	 */
	public boolean modifyRecord(DynaBean bean, Map<String, Object> values, boolean compel);
	
	/**
	 * 更新动态类数据（只更新指定部分数据,亦只对更新数据进行非空校验）,工号可以为空
	 * @param bean 要更新的动态信息类（里面必须要有的属性 globalid）
	 * @param values 所更新的信息
	 * @param compel 是否强制更新（无视其【是否可编辑】属性）
	 * @return
	 */
	public boolean modifyRecordNoCheckGh(DynaBean bean, Map<String, Object> values, boolean compel);
	
	/**
	 * 更新动态实体数据
	 * <p>
	 * 该方法不做任何业务处理，仅仅是将Bean中的数据保存到数据库中
	 * </p>
	 * @param bean 动态Bean对象，null则方法不做任何事情
	 * @throws DynaBeanException  如果操作出现异常
	 */
	public void modifyBean( DynaBean bean ) throws DynaBeanException;
	
	/**
	 * 增加记录,该方法属性业务方法
	 * @param bean 将被新增的记录
	 * @return 若主库记录被修改则返回true，否则返回false.
	 */
	public boolean addRecord( DynaBean bean );
	
	/**
	 * 增加记录,该方法属性业务方法,工号可以为空
	 * @param bean 将被新增的记录
	 * @return 若主库记录被修改则返回true，否则返回false.
	 */
	public boolean addRecordNoCheckGh( DynaBean bean );
	
	/**
	 * 删除动态bean信息,(不含业务判断)
	 * @param bean
	 */
	public void removeBean(DynaBean bean);
	
	/**
	 * 常用查询分页
	 * @param query
	 * @return
	 */
	public PageList<DynaBean> findPagingInfoList(DynaBeanQuery query);
	/**
	 * 查询分页(主体表别名t 关联overall表 表别名o)
	 * overall_xm 姓名
	 * overall_dwm 部门码
	 * @param query
	 * @return
	 */
	public PageList<DynaBean> findPagingInfoLeftJoinOverall(DynaBeanQuery query);
	
	/**
	 * 常用查询计数
	 * @param query
	 * @return
	 */
	public int findCount(DynaBeanQuery query);
	
	public int findCountNoUniqable(Map<String,Object> paraMap);
	
	public int findCountNoUniqableForCJ(Map<String,Object> paraMap);
	
	
	
	/**
	 * 新增一个人员
	 * @param staffid 职工号
	 */
	public void addPerson( String staffid );
	
	/**
	 * 删除一个人员
	 * @param staffid 职工号
	 */
	public void deletePerson( String staffid );
	
	/**
	 * 判断overall表中,人员是否存在
	 * @param staffid 工号
	 * @return
	 */
	public boolean existPerson(String staffid);
	
	/**
	 * 记录生效后的操作
	 * @throws DynaBeanException 如果操作出现异常
	 */
	public void doPass( DynaBean bean );
	
	/**
	 * 删除符合条件的数据 - 酬金管理
	 * @param paraMap
	 * @throws DataAccessException  如果操作出现异常
	 */
	public void deleteForCJ( Map<String, Object> paraMap );
	
	/**
	 * 存储数据（单条时若已经存在信息则修改，其他情况统一为添加）
	 * @param beans
	 * @param gh
	 */
	public void doImportData(List<DynaBean> beans, String gh, boolean compel);
}
