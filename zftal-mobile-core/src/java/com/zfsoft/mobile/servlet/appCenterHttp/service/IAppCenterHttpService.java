/**
 * 
 */
package com.zfsoft.mobile.servlet.appCenterHttp.service;

import java.io.IOException;
import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.services.entity.Business;
import com.zfsoft.mobile.services.entity.ServiceManager;
import com.zfsoft.mobile.webservices.entity.MemoCatalog;
import com.zfsoft.mobile.webservices.entity.MemoDB;
import com.zfsoft.mobile.webservices.entity.MemoPictureEntity;

/**
 * 
 * @author zhangxu
 * @description 
 * @date 2017-5-8 下午04:30:30
 */
public interface IAppCenterHttpService {
	/**
	 * 
	* @author: zhangxu
	* @Title: checkMemoPictureEntity 
	* @Description: 检查备忘录图片是否在项目下存在，不存在则根据数据库中存储的生成
	* @param @param memoFileName    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void checkMemoPictureEntity(String memoFileName);
	/**
	 * 
	* @author: zhangxu
	* @Title: checkMemo 
	* @Description: 检查备忘录是否在项目下存在，不存在则根据数据库中存储的生成
	* @param @param memoDB
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws
	 */
	public boolean checkMemo(MemoDB memoDB);
	/**
	 * 
	* @author: zhangxu
	* @Title: insertmemoCatalogByUser 
	* @Description: 插入个人备忘录类别
	* @param @param memoCatalog    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void insertmemoCatalogByUser(MemoCatalog memoCatalog);
	/**
	 * 
	* @author: zhangxu
	* @Title: getMemoCatalogList 
	* @Description: 获取个人备忘录类别
	* @param @param userId
	* @param @return    设定文件 
	* @return List<MemoCatalog>    返回类型 
	* @throws
	 */
	public List<MemoCatalog> getMemoCatalogList(String userId);
	/**
	 * 
	* @author: zhangxu
	* @Title: deleteAllmemoCatalogByUser 
	* @Description:删除个人所有备忘录类别 
	* @param @param userId    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void deleteAllmemoCatalogByUser(String userId);
	/**
	 * 
	* @author: zhangxu
	* @Title: deleteMyMemo 
	* @escription: 删除我的备忘录 
	* @param @param memoQuery    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void deleteMyMemo(MemoDB memoQuery);
	/**
	 * 
	* @author: zhangxu
	* @Title: insertMemo 
	* @Description: 插入备忘录
	* @param @param memoEntity    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void insertMemo(MemoDB memoEntity);
	/**
	 * 
	* @author: zhangxu
	* @Title: updateMemo 
	* @Description: 更新备忘录
	* @param @param memoEntity    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void updateMemo(MemoDB memoEntity);
	/**
	 * 
	* @author: zhangxu
	* @Title: getMyMemoList 
	* @Description:获取备忘录列表 
	* @param @param memoDB
	* @param @return    设定文件 
	* @return PageList<MemoDB>    返回类型 
	* @throws
	 */
	public PageList<MemoDB> getMyMemoList(MemoDB memoDB);
	/**
	 * 
	* @author: zhangxu
	* @Title: getMemoPicture 
	* @Description: 获取我的备忘录图片数量
	* @param @param pictureEnity
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	public int getMemoPicture(MemoPictureEntity pictureEnity);
	/**
	 * 
	* @author: zhangxu
	* @Title: insertMemoPicture 
	* @Description: 插入备忘录图片
	* @param @param pictureEnity    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void insertMemoPicture(MemoPictureEntity pictureEnity);
	
	/**
     * 根据imageId在项目中生成图片
     */
	public boolean doImage(String url, String imageId) throws IOException;
	/**
	 * 判断图片地址是否存在,true存在，false不存在
	 * @param url
	 * @param imageId
	 * @throws IOException 
	 */
	public boolean checkImage(String url, String imageId) throws IOException;
	
	/**
     * 服务地址模板替换
     * @param lst
     * @param userId
     * @return
     */
    public List<ServiceManager> getOpenUrl(List<ServiceManager> lst, String userId);

	/**
     * 取得用户的所有业务系统
     * @param userId
     * @return
     */
    public List<Business> getAllXtYwByUser(String userId);
    
    /**
     * 根据用户、业务ID取得该用户的所有服务
     * @param userId
     * @param ywId
     * @return
     */
    public List<ServiceManager> getAllServiceByUser(String userId, String ywId);
    
    /**
     * 根据用户、服务名称搜索该用户的服务
     * @param userId
     * @param fwmc
     * @return
     */
    public List<ServiceManager> getSearchedServiceList(String userId, String fwmc);
	
}
