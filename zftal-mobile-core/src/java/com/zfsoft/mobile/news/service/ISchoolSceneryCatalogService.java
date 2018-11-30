/**
 * 
 */
package com.zfsoft.mobile.news.service;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.news.entity.SchoolSceneryCatalog;

/**
 * @author zhangxu
 * @description 
 * @date 2017-5-10 上午10:01:55
 */
public interface ISchoolSceneryCatalogService {
	
	/**
	 * 
	* @author: zhangxu
	* @Title: getAllList 
	* @Description: 移动端获取所有校园风景类别
	* @param @return    设定文件 
	* @return List<SchoolSceneryCatalog>    返回类型 
	* @throws
	 */
	List<SchoolSceneryCatalog> getAllList();

	/** 
	* @author: zhangxu
	* @Title: getList 
	* @Description: 获取所有列表数据
	* @param @param query
	* @param @return    设定文件 
	* @return PageList<SchoolSceneryCatalog>    返回类型 
	* @throws 
	*/
	PageList<SchoolSceneryCatalog> getList(SchoolSceneryCatalog query);

	/** 
	* @author: zhangxu
	* @Title: insert 
	* @Description: TODO
	* @param @param query    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void insert(SchoolSceneryCatalog query);

	/** 
	* @author: zhangxu
	* @Title: update 
	* @Description: TODO
	* @param @param query    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void update(SchoolSceneryCatalog query);

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
	* @Title: delete 
	* @Description: TODO
	* @param @param query    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void delete(SchoolSceneryCatalog query);

}
