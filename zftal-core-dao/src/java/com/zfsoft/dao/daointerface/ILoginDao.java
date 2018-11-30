package com.zfsoft.dao.daointerface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.common.dao.BaseDao;
import com.zfsoft.dao.entities.LoginModel;
import com.zfsoft.dao.entities.LoginRecordModel;
import com.zfsoft.dao.entities.YhglModel;
import com.zfsoft.dao.query.LoginRecordModelQuery;

/**
 * 
* 
* 类名称：LoginDao 
* 类描述： 登录dao
* 创建人：hhy 
* 创建时间：2011-12-20 上午10:50:04 
* 修改人：hhy 
* 修改时间：2011-12-20 上午10:50:04 
* 修改备注： 
* @version 
*
 */
public interface ILoginDao extends BaseDao<LoginModel>{

	/**
	 * 
	* 方法名: getTeaxx
	* 方法描述: 获取教职工信息
	* 修改时间：2011-12-20 上午10:50:19 
	* 参数 @param model
	* 参数 @return 
	* 返回类型 HashMap<String,String> 
	* @throws
	 */
	public HashMap<String, String> getTeaxx(LoginModel model);
	
	/**
	 * 
	* 方法名: getStuxx
	* 方法描述: 获取学生信息
	* 修改时间：2011-12-20 上午10:50:33 
	* 参数 @param model
	* 参数 @return 
	* 返回类型 HashMap<String,String> 
	* @throws
	 */
	public HashMap<String, String> getStuxx(LoginModel model);
	
	/**
	 * 
	* 方法名: getList
	* 方法描述: 查询用户结果集 
	* 修改时间：2011-12-20 上午11:43:31 
	* 参数 @param model 
	* 返回类型 List<HashMap<String, String>> 
	* @throws
	 */
	public List<HashMap<String, String>> getList(LoginModel model);
	
	/**
	 * 
	* 方法名: insertYhxx
	* 方法描述: 增加用户信息
	* 修改时间：2011-12-20 上午11:44:30 
	* 参数 @param model 
	* 返回类型 void 
	* @throws
	 */
	//public void insertYhxx(LoginModel model);
	
	/**
	 * 
	* 方法名: viewYhxx
	* 方法描述: 查看用户信息 
	* 修改时间：2011-12-20 下午12:03:26 
	* 参数 @param model
	* 参数 @return 
	* 返回类型 LoginModel 
	* @throws
	 */
//	public LoginModel viewYhxx(LoginModel model);
	
	/**
	 * 
	* 方法名: updateYhxx
	* 方法描述: 修改用户信息
	* 修改时间：2011-12-20 上午11:44:37 
	* 参数 @param model 
	* 返回类型 void 
	* @throws
	 */
	//public void updateYhxx(LoginModel model);
	
	/**
	 * 
	* 方法名: deleteYhxx
	* 方法描述: 删除用户信息
	* 修改时间：2011-12-20 上午11:44:50 
	* 参数 @param arrayParam 
	* 返回类型 void 
	* @throws
	 */
//	public void deleteYhxx(String[] arrayParam);
	
	/**
	 * 
	* 方法描述: 查询教师功能模块代码信息
	* 参数 @param model
	* 参数 @return 参数说明
	* 返回类型 List<HashMap<String,String>> 返回类型
	* @throws
	 */
	public List<HashMap<String, String>> queryTeaGnmkdm(LoginModel model);
	
	/**
	 * 
	* 方法描述: 查询学生功能模块代码
	* 参数 @param model
	* 参数 @return 学号要放在yhm中
	* 返回类型 List<HashMap<String,String>> 返回类型
	* @throws
	 */
	public List<HashMap<String, String>> queryStuGnmkdm(LoginModel model);
	
	public List<HashMap<String, String>> queryGnmkdmList(LoginModel model);
	
	public List<HashMap<String, String>> querySjGnmkdmList(LoginModel model);
	
	public int addLoginRecord(Map<String,Object> params);
	
	public int updateLoginRecord(Map<String,Object> params); 
	
	public int selectLoginRecordByUsername(Map<String,Object> params);
	
	public List<LoginRecordModel> getLoginRecordList(LoginRecordModelQuery loginRecordModel);
	
	public int getLoginRecordListCount(LoginRecordModelQuery loginRecordModel);
	
	public int deleteLoginRecord(LoginRecordModelQuery loginRecordModel);

	public YhglModel getUserByDeviceid(String deviceId);
}
