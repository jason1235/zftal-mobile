/**
 * 
 */
package com.zfsoft.mobile.servlet.appCenterHttp.dao.daointerface;

import java.util.List;
import java.util.Map;

import com.zfsoft.hrm.file.entity.ImageDB;
import com.zfsoft.mobile.services.entity.Business;
import com.zfsoft.mobile.services.entity.ServiceManager;
import com.zfsoft.mobile.webservices.entity.MemoCatalog;
import com.zfsoft.mobile.webservices.entity.MemoDB;
import com.zfsoft.mobile.webservices.entity.MemoPictureEntity;

/**
 * @author zhangxu
 * @description 
 * @date 2017-5-8 下午04:37:15
 */
public interface IAppCenterHttpDao {
	
	/**
     * 获取图片
     * @param imageId
     * @return
     */
	public ImageDB findImageById(String imageId);

	/**
     * 取得用户的所有业务系统
     * @param userId
     * @return
     */
    public List<Business> getAllXtYwByUser(String userId);
    /**
     * 根据用户取得该用户的所有服务数量
     * @param param
     * @return
     */
    public int getAllServiceByUserCnt(Map<String, Object> param);
    
    /**
     * 根据用户取得该用户的所有服务
     * @param param
     * @return
     */
    public List<ServiceManager> getAllServiceByUser(Map<String, Object> param);
    
    
    /**
     * 根据用户、服务名称搜索该用户的服务
     * @param userId
     * @param fwmc
     * @return
     */
    public List<ServiceManager> getSearchedServiceList(Map<String, Object> param);

	/** 
	* @author: zhangxu
	* @Title: insertMemo 
	* @Description: 
	* @param @param memoEntity    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void insertMemo(MemoDB memoEntity);

	/** 
	* @author: zhangxu
	* @Title: updateMemo 
	* @Description: 
	* @param @param memoEntity    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void updateMemo(MemoDB memoEntity);

	/** 
	* @author: zhangxu
	* @Title: getMyMemoListCount 
	* @Description: 
	* @param @param query
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws 
	*/
	public int getMyMemoListCount(MemoDB query);

	/** 
	* @author: zhangxu
	* @Title: getMyMemoList 
	* @Description: 
	* @param @param query
	* @param @return    设定文件 
	* @return List<MemoDB>    返回类型 
	* @throws 
	*/
	public List<MemoDB> getMyMemoList(MemoDB query);

	/** 
	* @author: zhangxu
	* @Title: getMemoPicture 
	* @Description: 
	* @param @param pictureEnity
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws 
	*/
	public int getMemoPicture(MemoPictureEntity pictureEnity);

	/** 
	* @author: zhangxu
	* @Title: insertMemoPicture 
	* @Description: 
	* @param @param pictureEnity    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void insertMemoPicture(MemoPictureEntity pictureEnity);

	/** 
	* @author: zhangxu
	* @Title: deleteMyMemo 
	* @Description: 
	* @param @param memoQuery    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void deleteMyMemo(MemoDB memoQuery);

	/** 
	* @author: zhangxu
	* @Title: getMemoPictureList 
	* @Description: 
	* @param @param memoFileName
	* @param @return    设定文件 
	* @return List<MemoPictureEntity>    返回类型 
	* @throws 
	*/
	public List<MemoPictureEntity> getMemoPictureList(String memoFileName);

	/** 
	* @author: zhangxu
	* @Title: deleteMyMemoPicture 
	* @Description: 
	* @param @param memoFileName    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void deleteMyMemoPicture(String memoFileName);

	/** 
	* @author: zhangxu
	* @Title: insertmemoCatalogByUser 
	* @Description: 
	* @param @param memoCatalog    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void insertmemoCatalogByUser(MemoCatalog memoCatalog);

	/** 
	* @author: zhangxu
	* @Title: getMemoCatalogList 
	* @Description: 
	* @param @param userId
	* @param @return    设定文件 
	* @return List<MemoCatalog>    返回类型 
	* @throws 
	*/
	public List<MemoCatalog> getMemoCatalogList(String userId);

	/** 
	* @author: zhangxu
	* @Title: deleteAllmemoCatalogByUser 
	* @Description: 
	* @param @param userId    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void deleteAllmemoCatalogByUser(String userId);
}
