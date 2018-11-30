package com.zfsoft.mobile.servlet.myPortalHttp.dao.daointerface;

import java.util.List;
import java.util.Map;

import com.zfsoft.hrm.file.entity.ImageDB;
import com.zfsoft.mobile.webservices.entity.CardBusinessEntity;
import com.zfsoft.mobile.webservices.query.CardBusinessQuery;

/**
 * @author zhangxu
 * @description 
 * @date 2017-5-8 下午04:37:15
 */
public interface IMyPortalHttpDao {

	/** 
	* @author: zhangxu
	* @Title: getMyPicture 
	* @Description: TODO
	* @param @param userId
	* @param @return    设定文件 
	* @return List<ImageDB>    返回类型 
	* @throws 
	*/
	List<ImageDB> getMyPicture(String userId);

	/** 
	* @author: zhangxu
	* @Title: getCardKH 
	* @Description: 
	* @param @param username
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	String getCardKH(String username);

	/** 
	* @author: zhangxu
	* @Title: getCardNumber 
	* @Description: 
	* @param @param username
	* @param @return    设定文件 
	* @return float    返回类型 
	* @throws 
	*/
	float getCardNumber(String username);

	/** 
	* @author: zhangxu
	* @Title: getodetailCount 
	* @Description: 
	* @param @param businessQuery
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws 
	*/
	int getodetailCount(CardBusinessQuery businessQuery);

	/** 
	* @author: zhangxu
	* @Title: getodetailList 
	* @Description: 
	* @param @param businessQuery
	* @param @return    设定文件 
	* @return List<CardBusinessEntity>    返回类型 
	* @throws 
	*/
	List<CardBusinessEntity> getodetailList(CardBusinessQuery businessQuery);

	/** 
	* @author: zhangxu
	* @Title: getcdetailCount 
	* @Description: 
	* @param @param businessQuery
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws 
	*/
	int getcdetailCount(CardBusinessQuery businessQuery);

	/** 
	* @author: zhangxu
	* @Title: getcdetailList 
	* @Description: 
	* @param @param businessQuery
	* @param @return    设定文件 
	* @return List<CardBusinessEntity>    返回类型 
	* @throws 
	*/
	List<CardBusinessEntity> getcdetailList(CardBusinessQuery businessQuery);

	/** 
	* @author: zhangxu
	* @Title: updateMyPicture 
	* @Description: 
	* @param @param imageDB    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void updateMyPicture(ImageDB imageDB);

	/** 
	* @author: zhangxu
	* @Title: insertMyPicture 
	* @Description: 
	* @param @param imageDB    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void insertMyPicture(ImageDB imageDB);
	
	 Map<String,Object> getPortalSource(String userId);

}
