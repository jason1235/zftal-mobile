/**
 * 
 */
package com.zfsoft.mobile.services.service;

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
 * @date 2017-12-21 下午02:07:55
 */
public interface IExpressService {
	
	/** 
	* @author: zhangxu
	* @Title: getList 
	* @Description: 
	* @param @param query
	* @param @return    设定文件 
	* @return List<ExpressEntity>    返回类型 
	* @throws 
	*/
	PageList<ExpressEntity> getPageList(ExpressEntity query);

	/** 
	* @author: zhangxu
	* @Title: getList 
	* @Description: 
	* @param @param query
	* @param @return    设定文件 
	* @return List<ExpressEntity>    返回类型 
	* @throws 
	*/
	List<ExpressEntityForApp> getList(ExpressEntity query);

	/** 
	* @author: zhangxu
	* @Title: getExpressPicById 
	* @Description: 
	* @param @param expressId
	* @param @return    设定文件 
	* @return List<ExpressPicEntity>    返回类型 
	* @throws 
	*/
	List<ExpressPicEntity> getExpressPicById(String expressId);

	/** 
	* @author: zhangxu
	* @Title: getCommentList 
	* @Description: 
	* @param @param query
	* @param @return    设定文件 
	* @return List<ExpressCommentEntity>    返回类型 
	* @throws 
	*/
	List<ExpressCommentEntityForApp> getCommentList(ExpressCommentEntity query);

	/** 
	* @author: zhangxu
	* @Title: insertExpress 
	* @Description: 
	* @param @param expressEntity    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void insertExpress(ExpressEntity expressEntity);

	/** 
	* @author: zhangxu
	* @Title: insertExpressPic 
	* @Description: 
	* @param @param picEntity    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void insertExpressPic(ExpressPicEntity picEntity);

	/** 
	* @author: zhangxu
	* @Title: updateGoodFlag 
	* @Description: 
	* @param @param query    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void updateGoodFlag(ExpressCommentEntity query);

	/** 
	* @author: zhangxu
	* @Title: insertComment 
	* @Description: 
	* @param @param query    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void insertComment(ExpressCommentEntity query);

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
	* @Title: delete 
	* @Description: 
	* @param @param param    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void delete(Map<String, Object> param);

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
	* @Description: 添加某个用户是否针对某项评论点赞
	* @param @param params    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void insertExpressGoodFlag(Map<String,Object> params);
}
