package com.zfsoft.dao.query;

import com.zfsoft.dao.page.PageResult;

/**
 * 基础查询实体(含分页请求)
 * <p>
 * 该类只定义了默认的分页大小，其他查询条件需要自行定义，默认大小为20
 * </p>
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-5
 * @version V1.0.0
 */
public class BaseQuery extends PageResult {

	private static final long serialVersionUID = -6889907462745399730L;
	
	
	/**
	 * （空）构造函数
	 */
	public BaseQuery() {
		setPerPageSize(20);
	}
}
