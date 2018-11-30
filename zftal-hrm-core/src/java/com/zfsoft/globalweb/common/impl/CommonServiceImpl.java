package com.zfsoft.globalweb.common.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.globalweb.common.daointerface.ICommonDao;
import com.zfsoft.globalweb.common.entities.Common;
import com.zfsoft.globalweb.common.svcinterface.ICommonService;

/**
 * 
 * @author
 * 2014-2-20
 */
public class CommonServiceImpl implements ICommonService {
	private ICommonDao commonDao;

	@Override
	public List<Common> getCommon(Common common) {
		return commonDao.getCommon(common);
	}

	/**
	 * @return the commonDao
	 */
	public ICommonDao getCommonDao() {
		return commonDao;
	}

	/**
	 * @param commonDao the commonDao to set
	 */
	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}

	@Override
	public void save(String resourceIds, String userId, String roseId) {
		if(StringUtils.isEmpty(resourceIds)){
			return ;
		}
		Common common = new Common();
		common.setUserId(userId);
		common.setRoseId(roseId);
		remove(common);
		String[] resources = resourceIds.split(",");
		for(int i=0; i < resources.length; i++){
			common.setResourceId(resources[i]);
			Integer max = commonDao.getMaxSort();
			common.setSort(max == null ? 1 : max.intValue() + 1);
			commonDao.insert(common);
		}
	}

	@Override
	public void remove(Common common) {
		commonDao.delete(common);
	}

	@Override
	public void add(Common common) {
		Integer max = commonDao.getMaxSort();
		common.setSort(max == null ? 1 : max.intValue() + 1);
		commonDao.insert(common);
	}

}
