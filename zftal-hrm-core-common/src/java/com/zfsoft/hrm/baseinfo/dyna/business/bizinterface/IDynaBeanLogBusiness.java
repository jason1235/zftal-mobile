package com.zfsoft.hrm.baseinfo.dyna.business.bizinterface;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;

/**
 * 动态类审核business
 * @author jinjj
 * @date 2012-10-8 下午06:43:14 
 *
 */
public interface IDynaBeanLogBusiness {

	/**
	 * 根据logid 获取动态bean LOG数据
	 * @param bean
	 * @return
	 */
	public DynaBean findById(DynaBean bean);
	
	/**
	 * 更新动态bean数据
	 * @param bean
	 * @return
	 */
	public void submitRecord(DynaBean bean);
	
	/**
	 * 增加记录,该方法属性业务方法
	 * @param bean 将被新增的记录
	 * @param operate TODO
	 * @return 若主库记录被修改则返回true，否则返回false.
	 */
	public void doLog( DynaBean bean, String operate );
	
	/**
	 * 日志分页
	 * @param query
	 * @return
	 */
	public PageList<DynaBean> getPagingList(DynaBeanQuery query);
	
	/**
	 * 移除日志
	 * @param logBean
	 */
	public void removeById(DynaBean logBean);
}
