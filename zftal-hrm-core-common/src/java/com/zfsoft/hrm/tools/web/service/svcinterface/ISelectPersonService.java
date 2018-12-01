package com.zfsoft.hrm.tools.web.service.svcinterface;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.tools.web.exception.SelectPersonException;
import com.zfsoft.hrm.tools.web.query.SelectPersonQuery;

/**
 * 人员选择业务处理类接口
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-10
 * @version V1.0.0
 */
public interface ISelectPersonService {
	
	/**
	 * 获取人员名单
	 * @param bean 人员选择实体
	 * @return
	 * @throws RuntimeException 如果操作出现异常
	 */
	public PageList<DynaBean> getPerson( SelectPersonQuery bean ) throws SelectPersonException;
}
