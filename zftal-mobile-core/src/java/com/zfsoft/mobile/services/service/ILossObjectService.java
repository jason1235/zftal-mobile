package com.zfsoft.mobile.services.service;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.services.entity.LossObjectEntity;
import com.zfsoft.mobile.services.entity.LossObjectPictureEntity;

public interface ILossObjectService {
	
	public List<LossObjectPictureEntity> getPictureList(String lossObjectId);
	
	public PageList<LossObjectEntity> getList(LossObjectEntity query);
	
	public LossObjectEntity getOne(LossObjectEntity query);
	
	public void insert(LossObjectEntity entity);
	
	public void newInsert(LossObjectEntity entity);

	public void update(LossObjectEntity query);

	public void delete(LossObjectEntity query);

	/** 
	* @author: zhangxu
	* @Title: insertLossObjectPicture 
	* @Description: 
	* @param @param pictureEntity    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void insertLossObjectPicture(LossObjectPictureEntity pictureEntity);

	/** 
	* @author: zhangxu
	* @Title: updateLosser 
	* @Description: 
	* @param @param query    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void updateLosser(LossObjectEntity query);

}
