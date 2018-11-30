package com.zfsoft.service.svcinterface;

import java.util.List;

import com.zfsoft.common.log.User;
import com.zfsoft.dao.entities.AncdModel;

public interface IAncdService {
	
	public List<AncdModel> cxAncd(AncdModel ancdModel, User user);

	/**
	 * 
	* 方法描述: 查询按钮菜单  拦截调用
	* 参数 @param ancdMode 菜单模型
	* 参数 @param user 当前用户信息
	* 返回类型 void 返回类型
	* @throws
	 */
	public List<AncdModel> cxAncd(User user, String path) ;
}
