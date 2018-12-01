/**
 * 
 */
package com.zfsoft.mobile.servlet.homePageHttp.dao.daointerface;

import java.util.List;
import java.util.Map;

import com.zfsoft.hrm.file.entity.ImageDB;
import com.zfsoft.mobile.services.entity.ServiceManager;

/**
 * @author zhangxu
 * @description 
 * @date 2017-5-8 下午04:37:15
 */
public interface IHomePageHttpDao {
	/**
	 * 登录的服务
	 * @param param
	 * @return
	 */
	public List<ServiceManager> getLoginFw(Map<String, Object> param);
	/**
     * 删除用户的所有常用服务
     * @param param
     */
    public void deleteAllFrequentlyService(Map<String, Object> param);
	/**
     * 添加常用服务
     * @param param
     */
    public void insertFrequentlyService(Map<String, Object> param);
	/**
     * 更新常用服务--上移
     * @param param
     */
    public void upFrequentlyService(Map<String, Object> param);
    /**
     * 更新常用服务--下移
     * @param param
     */
    public void downFrequentlyService(Map<String, Object> param);
	/**
     * 根据用户取得该用户的所有服务
     * @param param
     * @return
     */
    public List<ServiceManager> getAllServiceByUser(Map<String, Object> param);
	/**
     * 根据用户取得该用户的常用服务
     * @param userId
     * @return
     */
    public List<ServiceManager> getFrequentlyServiceByUser(Map<String, Object> params);
	public void deleteFrequentlyServiceNotInYhqx(Map<String, Object> params);
	
	
	
	
	

	/** 
	* @author: zhangxu
	* @Title: findImageById 
	* @Description: 
	* @param @param imageId
	* @param @return    设定文件 
	* @return ImageDB    返回类型 
	* @throws 
	*/
	ImageDB findImageById(String imageId);
	/**
	 * 
	* @author: zhangxu
	* @Title: getCommonService 
	* @Description:获取首页不登录时通用服务 
	* @param @return    设定文件 
	* @return List<ServiceManager>    返回类型 
	* @throws
	 */
	public List<ServiceManager> getCommonService();

}
