package com.zfsoft.globalweb.common.daointerface;

import java.util.List;

import com.zfsoft.globalweb.common.entities.Common;

/**
 * 
 * @author yxlong
 * 2013-8-2
 */
public interface ICommonDao {
	public List<Common> getCommon(Common common);
	public Integer getMaxSort();
	public void insert(Common common);
	public void delete(Common common);
}
