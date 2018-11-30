package com.zfsoft.hrm.baseinfo.infoclass.service.svcinterface;

import java.util.List;
import java.util.Map;

import com.zfsoft.hrm.baseinfo.infoclass.exception.InfoClassException;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.query.InfoClassQuery;

/**
 * 信息类业务操作接口
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @since 2012-5-21
 * @version V1.0.0
 */
public interface IInfoClassService {
	/**
	 * 获取信息类是否支持模糊查询
	 * @param xxlid
	 */
	public String getProceduresMhcx(String xxlid);
	/**
	 * 获取信息类是否支持查看全部记录
	 * @param xxlid
	 */
	public String getProceduresXxlall(String xxlid);
	/**
	 * 修改存储过程
	 */
	public void modifyProcedure(Map<String, String> map);

	/**
	 * 返回符合条件的信息类列表
	 * <p>
	 * 此列表中的信息类不包含信息类属性列表，
	 * 如需包含信息类属性列表请使用 {@link #getFullList(InfoClassQuery)}
	 * </p>
	 * @param query 查询条件
	 * @return
	 * @throws RuntimeException 如果操作出现异常
	 */
	public List<InfoClass> getList(InfoClassQuery query) throws InfoClassException;
	
	/**
	 * 获取符合条件的信息类列表
	 * <p>
	 * 此列表中的信息类包含信息类属性列表,
	 * 如无需包含信息类属性列表请使用 {@link #getList(InfoClassQuery)} 
	 * </p>
	 * @param query 查询条件
	 * @return
	 * @throws RuntimeException 如果操作出现异常
	 */
	public List<InfoClass> getFullList(InfoClassQuery query) throws InfoClassException;
	
	/**
	 * 获取信息类信息
	 * @param classId 信息ID
	 * @return
	 * @throws RuntimeException 如果操作出现异常
	 */
	public InfoClass getInfoClass(String classId) throws InfoClassException;
	
	/**
	 * 获取完整的信息类信息
	 * <p>
	 * 返回的信息类包含该信息类的信息类属性列表
	 * </p>
	 * @param classId 信息类ID
	 * @return 包含信息类属性列表的信息类信息
	 * @throws InfoClassException 如果操作出现异常
	 */
	public InfoClass getFullInfoClass( String classId ) throws InfoClassException;
	
	/**
	 * 返回指定目录下的所有信息类信息
	 * @param catalogId 信息类目录
	 * @return
	 * @throws RuntimeException 如果操作出现异常
	 */
	public List<InfoClass> getCatalogInfoClasses(String catalogId) throws InfoClassException;

	/**
	 * 统计符合条件的记录数
	 * @param query 统计条件
	 * @return
	 * @throws RuntimeException 如果操作出现异常
	 */
	public int count(InfoClassQuery query) throws InfoClassException;
	
	/**
	 * 增加信息类
	 * @param entity 增加的信息类
	 * @throws RuntimeException 如果操作出现异常
	 */
	public void add(InfoClass entity) throws RuntimeException;
	
	/**
	 * 修改信息类
	 * @param entity 修改的信息类
	 * @throws RuntimeException 如果操作出现异常
	 */
	public void modify(InfoClass entity) throws InfoClassException;
	
	/**
	 * 删除信息类
	 * @param guid 信息类ID
	 * @throws RuntimeException 如果操作出现异常
	 */
	public void remove(String guid) throws InfoClassException;
	/**
	 * 获取完整的信息类信息
	 * <p>
	 * 返回的信息类包含该信息类的信息类属性列表
	 * </p>
	 * @param classId 信息类ID  express  显示、编辑、同步、虚拟的查询条件
	 * @return 包含信息类属性列表的信息类信息
	 * @throws InfoClassException 如果操作出现异常
	 */
	public InfoClass getFullInfoClass(String _s_id, String express);
}
