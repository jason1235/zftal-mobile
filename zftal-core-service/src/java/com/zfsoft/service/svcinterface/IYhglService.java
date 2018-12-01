package com.zfsoft.service.svcinterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.common.service.BaseService;
import com.zfsoft.dao.entities.JsglModel;
import com.zfsoft.dao.entities.UserSubsystemLastRole;
import com.zfsoft.dao.entities.YhglModel;
import com.zfsoft.dao.entities.YhglModelNew;
import com.zfsoft.dao.entities.YhxxbModel;
import com.zfsoft.dao.page.PageList;

/**
 * 
 * 
 * 类名称：YhglService 类描述： 用户信息业务处理接口 创建人：Administrator 创建时间：2012-4-10 下午06:44:18
 * 修改人：Administrator 修改时间：2012-4-10 下午06:44:18 修改备注：
 * 
 * @version
 * 
 */
public interface IYhglService extends BaseService<YhglModel> {

	/**
	 * 
	 * 方法描述: 保存用户信息 参数 @return 参数说明 返回类型 boolean 返回类型
	 */
	public boolean zjYhxx(YhglModel model) ;

	/**
	 * 
	 * 方法描述: 修改用户信息 参数 @return 参数说明 返回类型 boolean 返回类型
	 */
	public boolean xgYhxx(YhglModel model) ;

	/**
	 * 
	 * 方法描述: 查询用户角色列表 参数 @return 参数说明 返回类型 List<JsglModel> 返回类型
	 */
	public List<JsglModel> cxJsdmList() ;
	
	/**
	 * 
	 * 方法描述: 查询用户角色列表 参数 @return 参数说明 返回类型 List<JsglModel> 返回类型
	 */
	public List<JsglModel> cxJsdmList(JsglModel jsglModel) ;

	/**
	 * 
	 * 方法描述: 删除用户信息 参数 @return 参数说明 返回类型 boolean 返回类型
	 */
	public boolean scYhxx(YhglModel model) ;

	/**
	 * 
	 * 方法描述:设置所属角色 参数 @return 参数说明 返回类型 boolean 返回类型
	 */
	public boolean szSsjs(YhglModel model) ;

	/**
	 * 
	 * 方法描述: 密码初始化 参数 @return 参数说明 返回类型 boolean 返回类型
	 */
	public boolean mmCsh(YhglModel model) ;

	/**
	 * 
	 * 方法描述: 修改密码 参数 @return 参数说明 返回类型 boolean 返回类型
	 */
	public boolean xgMm(YhglModel model) ;

	/**
	 * 
	 * 方法描述: 根据角色代码查询所属用户 参数 @return 参数说明 返回类型 List<YhglModel> 返回类型
	 */
	public List<YhglModel> cxYhByJsdm(YhglModel model) ;

	/**
	 * 
	 * 方法描述: 根据角色代码查询角色名称 参数 @return 参数说明 返回类型 boolean 返回类型
	 */
	public JsglModel cxJsmcByJsdm(JsglModel model) ;

	/**
	 * 
	 * 方法描述: 格式化用户信息输出列表，角色分配用户时使用 参数 @return 参数说明 返回类型 List<String[]> 返回类型
	 */
	public List<HashMap<String, Object>> formatYhxxList(YhglModel model)
			;
	/**
	 * 方法描述: 根据用户代码查询角色信息
	 * @param model
	 * @return
	 * @
	 */
	public List<JsglModel> cxJsdm(JsglModel model) ;
	
	/**
	 * 方法描述：根据职工号获取其证件号码
	 * @param: 
	 * @return: 
	 */
	public String zjhmByZgh(String zgh);
	
	/**
	 * 方法描述：查找所有的职工号，用于批量密码初始化
	 */
	public List<String> zghList();
	
	/**
	 * 新增用户_子系统_上次登录角色 
	 * @param model
	 * @return
	 */
	public int addUserSubsystemRole(UserSubsystemLastRole model);
	/**
	 * 更新用户上次访问子系统的角色
	 * @param model
	 * @return
	 */
	public int modifyRoleByUserAndSubsystem(UserSubsystemLastRole model);
	/**
	 * 查询用户上次访问子系统的录角色
	 * @param model
	 * @return
	 */
	public List<UserSubsystemLastRole> queryUserSubsystemRole(String userId,String sysCode);

	public PageList<YhglModelNew> getPagedListNew(YhglModelNew model);
	
	/**
	 * 查询用户前10条的积分和头像
	 * @param username
	 * @return List<YhglModel>
	 * @author yangbilin
	 */
	public List<YhglModel> getRankinglist(Map<String, Object> params);
	/**
	 * 更新用户积分
	 * @param params
	 * @author yangbilin
	 */
	void updateYhbSource(Map<String, Object> params);
	
	/**
	 * 通过姓名更新用户积分
	 * @param params
	 * @author yangbilin
	 */
	void updateYhbSourceWithXm(Map<String, Object> params);
	
	/**
	 * 根据userid获取用户积分
	 * @param userId
	 * @author yangbilin
	 */
	public Integer sourceByZgh(String userId);
	/**
	 * 根据userid获取用户姓名
	 * @param userId
	 * @author yangbilin
	 */
	public String xmByZgh(String userId);

	/** 
	* @author: zhangxu
	* @Title: updateJs 
	* @Description: 
	* @param @param yhglModelQuery    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void updateJs(YhglModelNew yhglModelQuery);
	
	//后台解绑账号设备号
	public void unbindDeviceId(String zgh);

	public boolean jbsbh(YhglModel model);

	public void bcsbh(YhglModel model);

	public YhglModel getYhglModelByZgh(String zgh);

	public List<YhglModel> getUserByDeviceidAndRylx(String deviceid, String rylx);

	public List<YhglModel> getGradeRankinglist(HashMap<String, Object> param);

	public List<YhxxbModel> getYhxxbPagedList(YhxxbModel yhQuery);

	public YhxxbModel getYhxxModel(YhxxbModel yhModel);

	//public List<YhxxbModel> getYhxxbPagedListByMap(Map<String, Object> params);
}
