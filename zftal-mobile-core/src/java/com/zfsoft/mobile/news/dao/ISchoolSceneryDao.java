/**
 * 
 */
package com.zfsoft.mobile.news.dao;

import java.util.List;
import java.util.Map;

import com.zfsoft.mobile.news.entity.SchoolScenery;

/**
 * @author zhangxu
 * @description 
 * @date 2017-5-10 上午10:10:03
 */
public interface ISchoolSceneryDao {
	
	/**
	 * 
	* @author: zhangxu
	* @Title: getMaxSortNumber 
	* @Description: 获取最大排序码
	* @param @param query
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	int getMaxSortNumber(SchoolScenery query);

	/** 
	* @author: zhangxu
	* @Title: getListCount 
	* @Description: TODO
	* @param @param query
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws 
	*/
	int getListCount(SchoolScenery query);

	/** 
	* @author: zhangxu
	* @Title: getList 
	* @Description: TODO
	* @param @param query
	* @param @return    设定文件 
	* @return List<SchoolScenery>    返回类型 
	* @throws 
	*/
	List<SchoolScenery> getList(SchoolScenery query);

	/** 
	* @author: zhangxu
	* @Title: insert 
	* @Description: TODO
	* @param @param query    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void insert(SchoolScenery query);

	/** 
	* @author: zhangxu
	* @Title: updateIndex 
	* @Description: 排序
	* @param @param map    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void updateIndex(Map<String, String> map);

	/** 
	* @author: zhangxu
	* @Title: update 
	* @Description: TODO
	* @param @param query    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void update(SchoolScenery query);

	/** 
	* @author: zhangxu
	* @Title: delete 
	* @Description: TODO
	* @param @param query    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void delete(SchoolScenery query);

}
