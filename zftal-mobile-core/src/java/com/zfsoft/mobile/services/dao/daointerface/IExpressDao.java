/**
 * 
 */
package com.zfsoft.mobile.services.dao.daointerface;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.services.entity.ExpressCommentEntity;
import com.zfsoft.mobile.services.entity.ExpressCommentEntityForApp;
import com.zfsoft.mobile.services.entity.ExpressEntity;
import com.zfsoft.mobile.services.entity.ExpressEntityForApp;
import com.zfsoft.mobile.services.entity.ExpressPicEntity;

/**
 * @author zhangxu
 * @description 
 * @date 2017-12-21 下午02:08:55
 */
public interface IExpressDao {

	List<ExpressEntityForApp> getList(ExpressEntity query);

	int getListCount(ExpressEntity query);
	
	List<ExpressPicEntity> getExpressPicById(String expressId);
	
	List<ExpressCommentEntityForApp> getCommentList(ExpressCommentEntity query);
	
	int getCommentListCount(ExpressCommentEntity query);
	
	void insertComment(ExpressCommentEntity query);
	
	void updateGoodFlag(ExpressCommentEntity query);
	
	void insertExpress(ExpressEntity query);
	
	void insertExpressPic(ExpressPicEntity query);

	/**
	 * @param expressId  
	* @author: zhangxu
	* @Title: getMaxOrderNumber 
	* @Description: 
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws 
	*/
	int getMaxOrderNumber(String expressId);

	/** 
	* @author: zhangxu
	* @Title: getPageListCount 
	* @Description: 
	* @param @param query
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws 
	*/
	int getPageListCount(ExpressEntity query);

	/** 
	* @author: zhangxu
	* @Title: getPageList 
	* @Description: 
	* @param @param query
	* @param @return    设定文件 
	* @return List<ExpressEntity>    返回类型 
	* @throws 
	*/
	List<ExpressEntity> getPageList(ExpressEntity query);

	/** 
	* @author: zhangxu
	* @Title: delete 
	* @Description: 
	* @param @param param    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void delete(Map<String, Object> param);

	/** 
	* @author: zhangxu
	* @Title: deleteExpressComment 
	* @Description: 
	* @param @param param    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void deleteExpressComment(Map<String, Object> param);

	/** 
	* @author: zhangxu
	* @Title: deleteExpressPic 
	* @Description: 
	* @param @param param    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void deleteExpressPic(Map<String, Object> param);
	
	/**
	 * 
	* @author: liucb
	* @Title: updateGoodCount 
	* @Description: 点赞次数加一
	* @param @param params    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void updateGoodCount(Map<String,Object> params);
	
	/**
	 * 
	* @author: liucb
	* @Title: checkUserGoodFlag 
	* @Description: 判断某个用户是否针对某项评论点赞
	* @param @param params
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	int checkUserGoodFlag(Map<String,Object> params);
	
	
	/**
	 * 
	* @author: liucb
	* @Title: insertExpressGoodFlag 
	* @Description: 点赞
	* @param @param params    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void insertExpressGoodFlag(Map<String,Object> params);
}
