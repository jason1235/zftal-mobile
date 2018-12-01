package com.zfsoft.mobile.services.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.mobile.services.dao.daointerface.ILossObjectDao;
import com.zfsoft.mobile.services.entity.Business;
import com.zfsoft.mobile.services.entity.LossObjectEntity;
import com.zfsoft.mobile.services.entity.LossObjectPictureEntity;
import com.zfsoft.mobile.services.service.ILossObjectService;
import com.zfsoft.util.base.StringUtil;

public class LossObjectServiceImpl implements ILossObjectService {

	private ILossObjectDao lossObjectDao;

	public void setLossObjectDao(ILossObjectDao lossObjectDao) {
		this.lossObjectDao = lossObjectDao;
	}

	public ILossObjectDao getLossObjectDao() {
		return lossObjectDao;
	}

	@Override
	public PageList<LossObjectEntity> getList(LossObjectEntity query) {
		PageList<LossObjectEntity> pageList = new PageList<LossObjectEntity>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(lossObjectDao.getListCount(query));
			pageList.setPaginator(paginator);
			if((Integer)query.getToPage() > paginator.getPages()){
				return pageList;
			}
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<LossObjectEntity> list = lossObjectDao.getList(query);
				//循环遍历失物招领信息,获取相应的图片信息
				for (int i = 0; i < list.size(); i++) {
					LossObjectEntity lossObjectEntity = list.get(i);
					if(lossObjectEntity!=null){
						String lossObjId = lossObjectEntity.getId();
						List<LossObjectPictureEntity> lossObjectPictureEntities = lossObjectDao.getPictureList(lossObjId);
						if(lossObjectPictureEntities==null){
							lossObjectPictureEntities = new ArrayList<LossObjectPictureEntity>();
						}
						lossObjectEntity.setPictures(lossObjectPictureEntities);
					}
					/*lossObjectEntity.setLossuser(lossObjectEntity.getLossuser()==null? "":lossObjectEntity.getLossuser());
					lossObjectEntity.setPlace(lossObjectEntity.getPlace()==null? "":lossObjectEntity.getLossuser());*/
				}

				pageList.addAll(list);
			}
		}
		return pageList;
	}

	public LossObjectEntity getOne(LossObjectEntity query){
		return lossObjectDao.getOne(query);
	}
	
	@Override
	public void insert(LossObjectEntity entity) {
		entity.setIsover("0");
		if(StringUtil.isEmpty(entity.getIspass())){
			entity.setIspass("0");
		}
		//entity.setTimecreate(new Date());
		lossObjectDao.insert(entity);
	}
	
	public void newInsert(LossObjectEntity entity){
		entity.setIsover("0");
		if(StringUtil.isEmpty(entity.getIspass())){
			entity.setIspass("0");
		}
		//entity.setTimecreate(new Date());
		lossObjectDao.newInsert(entity);
	}

	@Override
	public void update(LossObjectEntity query) {
		lossObjectDao.update(query);
	}

	@Override
	public void delete(LossObjectEntity query) {
		lossObjectDao.delete(query);
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.services.service.ILossObjectService#insertLossObjectPicture(com.zfsoft.mobile.services.entity.LossObjectPictureEntity)
	 */
	@Override
	public void insertLossObjectPicture(LossObjectPictureEntity pictureEntity) {
		lossObjectDao.insertLossObjectPicture(pictureEntity);
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.services.service.ILossObjectService#getPictureList(java.lang.String)
	 */
	@Override
	public List<LossObjectPictureEntity> getPictureList(String lossObjectId) {
		return lossObjectDao.getPictureList(lossObjectId);
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.services.service.ILossObjectService#updateLosser(com.zfsoft.mobile.services.entity.LossObjectEntity)
	 */
	@Override
	public void updateLosser(LossObjectEntity query) {
		lossObjectDao.updateLosser(query);
	}
}
