package com.zfsoft.mobile.services.dao.daointerface;

import java.util.List;

import com.zfsoft.mobile.services.entity.LossObjectEntity;
import com.zfsoft.mobile.services.entity.LossObjectPictureEntity;

public interface ILossObjectDao {

	int getListCount(LossObjectEntity query);

	List<LossObjectEntity> getList(LossObjectEntity query);
	
	LossObjectEntity getOne(LossObjectEntity query);

	void insert(LossObjectEntity entity);
	
	void newInsert(LossObjectEntity entity);

	void update(LossObjectEntity query);

	void delete(LossObjectEntity query);

	/** 
	* @author: zhangxu
	* @Title: insertLossObjectPicture 
	* @Description: 
	* @param @param pictureEntity    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void insertLossObjectPicture(LossObjectPictureEntity pictureEntity);

	/** 
	* @author: zhangxu
	* @Title: getPictureList 
	* @Description: 
	* @param @param lossObjectId
	* @param @return    设定文件 
	* @return List<LossObjectPictureEntity>    返回类型 
	* @throws 
	*/
	List<LossObjectPictureEntity> getPictureList(String lossObjectId);

	/** 
	* @author: zhangxu
	* @Title: updateLosser 
	* @Description: 
	* @param @param query    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void updateLosser(LossObjectEntity query);

}
