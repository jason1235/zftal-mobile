/**
 * 
 */
package com.zfsoft.mobile.news.service;

import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.news.entity.SchoolScenery;

/**
 * @author zhangxu
 * @description 
 * @date 2017-5-10 上午10:10:23
 */
public interface ISchoolSceneryService {

	/** 
	* @author: zhangxu
	* @Title: getList 
	* @Description: TODO
	* @param @param query
	* @param @return    设定文件 
	* @return PageList<SchoolScenery>    返回类型 
	* @throws 
	*/
	PageList<SchoolScenery> getList(SchoolScenery query);

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
	* @Description: TODO
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
