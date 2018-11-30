package com.zfsoft.hrm.baseinfo.dyna.service.svcinterface;

import java.util.List;

import javax.mail.search.SearchException;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.dyna.exception.DynaBeanException;

/** 
 * 动态Bean业务操作接口
 * @author jinjj
 * @date 2012-6-20 上午10:42:53 
 */
public interface IDynaBeanService {
	
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
	 * 增加动态实体数据
	 * <p>
	 * 该方法不做任何业务处理，仅仅是将Bean中的数据保存到数据库中
	 * </p>
	 * @param bean 动态Bean对象，null则方法不做任何事情
	 * @throws DynaBeanException  如果操作出现异常
	 */
	public void addBean( DynaBean bean ) throws DynaBeanException;
	
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
	 * 删除动态实体数据
	 * <p>
	 * 该方法不做任何业务处理，仅仅是将Bean中的数据从数据库中删除
	 * </p>
	 * @param bean 动态Bean对象，null则方法不做任何事情
	 * @throws DynaBeanException  如果操作出现异常
	 */
	public void removeBean( DynaBean bean ) throws DynaBeanException;
	
	/**
	 * 查询符合条件的动态数据列表
	 * @param query 动态数据查询实体
	 * @return
	 * @throws DynaBeanException 如果操作出现异常
	 */
	public List<DynaBean> findList( DynaBeanQuery query ) throws DynaBeanException;
	/**
	 * 查询某字段paramName值为value的数据
	 * @param paramName
	 * @param value
	 * @return
	 * @throws DynaBeanException如果操作出现异常
	 */
	public DynaBean findUniqueByParam(String paramName,Object value) throws DynaBeanException; 
	
	/**
	 * 查询单个动态数据实体
	 * @param bean 动态数据实体
	 * @return
	 * @throws DynaBeanException 如果操作出现异常
	 */
	public DynaBean findById( DynaBean bean ) throws DynaBeanException;
	
	/**
	 * 查询统计符合条件的动态数据记录数
	 * @param query 动态数据查询实体
	 * @return
	 * @throws DynaBeanException 如果操作出现异常
	 */
	public int findCount( DynaBeanQuery query ) throws DynaBeanException;
	
	/**
	 * 增加记录,该方法属性业务方法
	 * @param bean 将被新增的记录
	 * @return 若主库记录被修改则返回true，否则返回false.
	 * @throws DynaBeanException 如果操作出现异常
	 */
	public boolean addRecord( DynaBean bean ) throws DynaBeanException;
	
	/**
	 * 增加记录,该方法属性业务方法,工号可以为空
	 * @param bean 将被新增的记录
	 * @return 若主库记录被修改则返回true，否则返回false.
	 * @throws DynaBeanException 如果操作出现异常
	 */
	public boolean addRecordNoCheckGh( DynaBean bean ) throws DynaBeanException;
	
	/**
	 * 修改记录,该方法属性业务方法
	 * @param bean 将被修改的记录
	 * @return 若主库记录被修改则返回true，否则返回false.
	 * @throws DynaBeanException 如果操作出现异常
	 */
	public boolean modifyRecord( DynaBean bean ) throws DynaBeanException;
	
	/**
	 * 修改记录,该方法属性业务方法,工号可以为空
	 * @param bean 将被修改的记录
	 * @return 若主库记录被修改则返回true，否则返回false.
	 * @throws DynaBeanException 如果操作出现异常
	 */
	public boolean modifyRecordNoChecked( DynaBean bean ) throws DynaBeanException;
	
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
	 * 获取普通查询实体列表(含分页查询)
	 * @param query 查询条件
	 * @return 符合查询条件的普通查询列表
	 * @throws SearchException (runtime)如果操作出现异常 
	 */
	public PageList<DynaBean>  findPagingInfoList(DynaBeanQuery query) throws DynaBeanException;
	/**
	 * 分配工号
	 * @param classId
	 * @param globalid
	 * @param gh
	 * @throws DynaBeanException
	 */
	public void doFpgh(String classId,String globalid,String gh ) throws DynaBeanException;
}
