package com.zfsoft.mobile.skin.service.impl;

import java.util.List;

import com.zfsoft.mobile.skin.dao.ISkinDao;
import com.zfsoft.mobile.skin.entity.SkinEntity;
import com.zfsoft.mobile.skin.entity.SkinPreviewPicsEntity;
import com.zfsoft.mobile.skin.service.ISkinService;

/**
 * 手机端皮肤serviceImpl
 * @author liucb
 *
 */
public class SkinServiceImpl implements ISkinService{
	private ISkinDao skinDao;

    public List<SkinEntity> getSkinList(){
    	return skinDao.getSkinList();
    }
	
	public List<SkinPreviewPicsEntity> getPreviewPicList(){
		return skinDao.getPreviewPicList();
	}

	
	
	public ISkinDao getSkinDao() {
		return skinDao;
	}

	public void setSkinDao(ISkinDao skinDao) {
		this.skinDao = skinDao;
	}
}
