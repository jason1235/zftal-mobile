package com.zfsoft.mobile.index.service.impl;

import java.util.List;
import java.util.Map;

import com.zfsoft.mobile.index.dao.IndexInfoDao;
import com.zfsoft.mobile.index.entity.TsgJyxx;
import com.zfsoft.mobile.index.entity.YktJbxx;
import com.zfsoft.mobile.index.service.IndexInfoService;


public class IndexInfoServiceImpl implements IndexInfoService{

	private IndexInfoDao indexInfoDao;

	public IndexInfoDao getIndexInfoDao() {
		return indexInfoDao;
	}

	public void setIndexInfoDao(IndexInfoDao indexInfoDao) {
		this.indexInfoDao = indexInfoDao;
	}

	@Override
	public YktJbxx getYktInfo(String ryh) {
		return indexInfoDao.getYktInfo(ryh);
	}

	@Override
	public Map<String, Object> getTeaSh(String kh) {
		return indexInfoDao.getTeaSh(kh);
	}

	@Override
	public Map<String, Object> getStuSh(String kh) {
		return indexInfoDao.getStuSh(kh);
	}

	@Override
	public List<TsgJyxx> selectLibInfo(String ryh) {
		return indexInfoDao.selectLibInfo(ryh);
	}
	
	
	
	
}
