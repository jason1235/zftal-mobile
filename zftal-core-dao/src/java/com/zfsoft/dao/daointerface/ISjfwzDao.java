package com.zfsoft.dao.daointerface;

import java.util.List;
import java.util.Map;

import com.zfsoft.common.dao.BaseDao;
import com.zfsoft.dao.entities.SjfwzModel;

/**
 * 
 * 类名称：IYhsjfwDao 
 * 类描述：用户数据范围
 * 创建人：caozf
 * 创建时间：2012-7-10
 */
public interface ISjfwzDao extends BaseDao<SjfwzModel>{

	/**
	 * 根据用户角色查询数据范围组
	 * @param t
	 * @return
	 * @throws Exception
	 */
	public List<SjfwzModel> cxSjfwzYhjs(Map<String,Object> maps);
	
}
