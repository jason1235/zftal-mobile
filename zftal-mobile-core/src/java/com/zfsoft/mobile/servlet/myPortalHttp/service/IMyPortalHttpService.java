/**
 * 
 */
package com.zfsoft.mobile.servlet.myPortalHttp.service;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.file.entity.ImageDB;
import com.zfsoft.mobile.webservices.entity.CardBusinessEntity;
import com.zfsoft.mobile.webservices.query.CardBusinessQuery;

/**
 * 
 * @author zhangxu
 * @description 
 * @date 2017-5-8 下午04:30:30
 */
public interface IMyPortalHttpService {
	/**
	 * 
	* @author: zhangxu
	* @Title: updateMyPicture 
	* @Description:更新个人头像 
	* @param @param imageDB    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void updateMyPicture(ImageDB imageDB);
	/**
	 * 
	* @author: zhangxu
	* @Title: insertMyPicture 
	* @Description:插入个人头像 
	* @param @param imageDB    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void insertMyPicture(ImageDB imageDB);
	
	/**
	 * 
	* @author: zhangxu
	* @Title: getodetailCount 
	* @Description: 获取一卡通消费分页
	* @param @param businessQuery
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	public int getodetailCount(CardBusinessQuery businessQuery);

	/**
	 * 
	* @author: zhangxu
	* @Title: getodetailList 
	* @Description: 
	* @param @param businessQuery
	* @param @return    设定文件 
	* @return PageList<CardBusinessEntity>    返回类型 
	* @throws
	 */
	public PageList<CardBusinessEntity> getodetailList(
			CardBusinessQuery businessQuery);


	public int getcdetailCount(CardBusinessQuery businessQuery);


	public PageList<CardBusinessEntity> getcdetailList(
			CardBusinessQuery businessQuery);

	/**
	 * 获取我的头像
	* @author: zhangxu
	* @Title: getMyPicture 
	* @Description: TODO
	* @param @param userId：用户id
	* @param @return    设定文件 
	* @return List<ImageDB>    返回类型 
	* @throws
	 */
	public List<ImageDB> getMyPicture(String userId);

	/** 
	* @author: zhangxu
	* @Title: getCardKH 
	* @Description: 获取一卡通卡号
	* @param @param username
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	public String getCardKH(String username);

	/** 
	* @author: zhangxu
	* @Title: getCardNumber 
	* @Description: 获取一卡通余额
	* @param @param username
	* @param @return    设定文件 
	* @return char[]    返回类型 
	* @throws 
	*/
	public float getCardNumber(String username);
	public Map<String, Object> getPortalSource(String userName);
}
