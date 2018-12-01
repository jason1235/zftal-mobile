package com.zfsoft.dao.daointerface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zfsoft.common.dao.BaseDao;
import com.zfsoft.common.log.User;
import com.zfsoft.dao.entities.JsglModel;
import com.zfsoft.dao.entities.LoginModel;
import com.zfsoft.dao.entities.UserSubsystemLastRole;
import com.zfsoft.dao.entities.YhglModel;
import com.zfsoft.dao.entities.YhglModelNew;
import com.zfsoft.dao.entities.YhxxbModel;
import com.zfsoft.dao.entities.userStatictisEntity;

/**
 * 
* 
* 类名称：YhglDao 
* 类描述： 用户管理DAO
* 创建人：Administrator 
* 创建时间：2012-4-10 下午06:45:13 
* 修改人：Administrator 
* 修改时间：2012-4-10 下午06:45:13 
* 修改备注： 
* @version 
*
 */
public interface IYhglDao extends BaseDao<YhglModel>{
	
	/**
	 * 
	* 方法描述: 查询启用的用户信息列表
	* 参数 @return
	* 参数 @ 参数说明
	* 返回类型 List<YhglModel> 返回类型
	* @throws
	 */
	public List<YhglModel> cxQyyhxxList(YhglModel model);
	

	/**
	 * 
	* 方法描述: 查询用户角色列表
	* 参数 @param model
	* 参数 @return
	* 参数 @ 参数说明
	* 返回类型 List<YhglModel> 返回类型
	* @throws
	 */
	public List<JsglModel> cxJsdmList(JsglModel jsglModel);

	
	/**
	 * 
	* 方法描述: 删除用户角色信息
	* 参数 @param array 参数说明
	* 返回类型 void 返回类型
	* @throws
	 */
	public int scYhjsxx(Map<String, Object> param);
	
	/**
	 * 
	* 方法描述: 保存用户角色表
	* 参数 @param model 参数说明
	* 返回类型 void 返回类型
	* @throws
	 */
	public int zjYhjsxx(YhglModel model);
	
	/**
	 * 
	* 方法描述: 密码初始化
	* 参数 @param array 参数说明
	* 返回类型 void 返回类型
	* @throws
	 */
	public int mmCsh(YhglModel model);
	
	/**
	 * 
	* 方法描述: 修改密码
	* 参数 @param array 参数说明
	* 返回类型 void 返回类型
	* @throws
	 */
	public int xgMm(YhglModel model);
	
	/**
	 * 
	* 方法描述: 根据角色代码查询用户列表
	* 参数 @param model
	* 参数 @return 参数说明
	* 返回类型 JsglModel 返回类型
	* @throws
	 */
	public List<YhglModel> cxYhByJsdm(YhglModel model);
	
	/**
	 * 
	* 方法描述: 根据角色代码查询角色名称
	* 参数 @param model
	* 参数 @return 参数说明
	* 返回类型 JsglModel 返回类型
	* @throws
	 */
	public  JsglModel cxJsmcByJsdm(JsglModel model);
	
	/**
	 * 方法描述: 根据用户代码查询角色信息
	 * @param model
	 * @return
	 * @
	 */
	public List<JsglModel> cxJsdm(JsglModel model);
	
	/**
	 * 方法描述：根据职工号获取其证件号码
	 * @param: 
	 * @return: 
	 */
	public String zjhmByZgh(String zgh);
	
	/**
	 * 方法描述：查找所有职工号，用于批量初始化密码
	 * @param: 
	 * @return: 
	 */
	public List<String> zghList();
	/**
	 * 保存用户_子系统_上次登录角色 
	 * @param model
	 * @return
	 */
	public int insertUserSubsystemRole(UserSubsystemLastRole model);
	/**
	 * 更新用户上次访问子系统的角色
	 * @param model
	 * @return
	 */
	public int updateRoleByUserAndSubsystem(UserSubsystemLastRole model);
	/**
	 * 查询用户_子系统_上次登录角色表
	 * @param model
	 * @return
	 */
	public List<UserSubsystemLastRole> findUserSubsystemRoles(UserSubsystemLastRole model);


	/**
	 * 用户秘钥更新
	 * @param yhglModel
	 */
	public void updateStrKey(YhglModel yhglModel);

	//获取用户秘钥
	public String getStrKey(String apptoken);

	//根据用户名获取秘钥
	public String getStrKeyByYhm(String zgh);
	
	//获取当前日期按用户安装量是否在数据库中存在
	public int getInstallsCount(); 
	
	//更新当前日期按用户安装量
	public void updateInstallsCount();

	//增加实时日期统计量 
	public void insertInstallsCount();
	
	//根据时间查询用户安装量
	public List<userStatictisEntity> getInstallsStatistic(userStatictisEntity entity);


	public int getListCountNew(YhglModelNew model);


	public List<YhglModelNew> getListNew(YhglModelNew model);

	public List<YhglModel> getRankinglist(Map<String, Object> params);
	void updateYhbSource(Map<String, Object> params);
	void updateYhbSourceWithXm(Map<String, Object> params);
	
	public int sourceByZgh(String userId);
	public String xmByZgh(String userId);
	public Map<String,String> getInfoByZgh(LoginModel model);

	/** 
	* @author: zhangxu
	* @Title: updateJs 
	* @Description: 
	* @param @param yhglModelQuery    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void updateJs(YhglModelNew yhglModelQuery);
	
	public int bindDevice(User user);


	public int countUserByDeviceid(@Param("yhm")String yhm,@Param("deviceId")String deviceId);


	public void unbindDeviceId(@Param("zgh") String zgh);


	public List<YhglModel> getUserByDeviceidAndRylx(@Param("deviceid")String deviceid, @Param("rylx")String rylx);


	public void jbsbh(String zgh);


	public void bcsbh(YhglModel model);


	public YhglModel getYhglModelByZgh(String zgh);


	public List<YhglModel> getGradeRankinglist(HashMap<String, Object> params);


	public int getYhxxListCount(YhxxbModel model);


	public List<YhxxbModel> getYhxxList(YhxxbModel model);


	public YhxxbModel getYhxxModel(YhxxbModel model);
	
}
