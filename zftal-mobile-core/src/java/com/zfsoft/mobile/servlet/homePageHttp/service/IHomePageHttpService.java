/**
 * 
 */
package com.zfsoft.mobile.servlet.homePageHttp.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.zfsoft.mobile.services.entity.ServiceManager;

/**
 * 
 * @author zhangxu
 * @description 
 * @date 2017-5-8 下午04:30:30
 */
public interface IHomePageHttpService {
	/**
     * 登录的服务
     * @param userId
     * @return
     */
    public List<ServiceManager> getLoginFw(Map<String, Object> param);
	
	/**
     * 删除用户所有常用服务
     * @param userId
     */
    public void deleteAllFrequentlyService(String userId);

	/** 
	* @author: zhangxu
	* @Title: checkImage 
	* @Description: 
	* @param @param string
	* @param @param string2
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws 
	*/
	boolean checkImage(String string, String string2);
	/**
     * 根据imageId在项目中生成图片
     */
	public boolean doImage(String url, String imageId) throws IOException;
	/**
	 * 
	* @author: zhangxu
	* @Title: getCommonService 
	* @Description: 获取首页不登录时通用服务
	* @param @return    设定文件 
	* @return List<ServiceManager>    返回类型 
	* @throws
	 */
	public List<ServiceManager> getCommonService();
	
	/**
     * 通用服务服务地址模板替换
     * @param lst
     * @return
     */
    public List<ServiceManager> getOpenUrl(List<ServiceManager> lst);
    /**
     * 根据用户取得该用户的常用服务
     * @param userId
     * @return
     */
    public List<ServiceManager> getFrequentlyServiceByUser(String userId);
	/** 
	* @author: zhangxu
	* @Title: getOpenUrl 
	* @Description: 
	* @param @param lst
	* @param @param userId
	* @param @return    设定文件 
	* @return List<ServiceManager>    返回类型 
	* @throws 
	*/
	List<ServiceManager> getOpenUrl(List<ServiceManager> lst, String userId);
	
	/**
     * 添加常用服务
     * @param userId
     * @param fwBm
     * @param isadd
     */
    public void insertFrequentlyService(String userId, String fwBm, boolean isadd);
}
