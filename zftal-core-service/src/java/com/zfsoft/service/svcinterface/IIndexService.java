package com.zfsoft.service.svcinterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.common.log.User;
import com.zfsoft.common.service.BaseService;
import com.zfsoft.dao.entities.IndexModel;

public interface IIndexService extends BaseService<IndexModel> {
	
	/**
	 * 查询顶部(一级)  根据角色列表
	 * @param jsxx 角色列表
	 * @return
	 */
	public List<HashMap<String, String>> cxDbCd(List<String> jsxx);
	

	/**
	 * 查询左部(二、三级)  根据角色列表
	 * @param jsxx 角色列表
	 * @return
	 */
	public List<HashMap<String, Object>> cxZbCd(List<String> jsxx,IndexModel model) ;
	
	/**
	 * 查询角色信息列表
	 * @param model
	 * @return
	 * @
	 */
	public List<String> cxJsxxLb(User user) ;
	
	/**
	 * 验证上级菜单权限
	 * @param path
	 * @return
	 */
	public boolean yzQx(String path) ;
	
	/**
	 * 切换用户
	 * @param user 需要切换的用户信息
	 * @return
	 */
	public User qhYh(IndexModel indexModel) ;
	
	 /** 用户附属
	 * @param user
	 * @return
	 */
	public User fsYh(User user) ;
	
	/**
	 * 
	* 方法名: cxLsmrdyym
	* 方法描述: 查询老师一级模块对应页面 （当前用户）
	* 参数 @param param
	* 参数 @return 
	* 返回类型 List<HashMap<String,String>> 
	* @throws
	 */
	public List<HashMap<String,String>> cxLsmrdyym(Map<String,Object> param) ;
}
