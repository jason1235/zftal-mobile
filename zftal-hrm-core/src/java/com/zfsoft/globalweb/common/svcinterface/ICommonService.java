package com.zfsoft.globalweb.common.svcinterface;

import java.util.List;

import com.zfsoft.globalweb.common.entities.Common;

/**
 * 
 * @author
 * 2014-2-20
 */
public interface ICommonService {
	public List<Common> getCommon(Common common);
	public void add(Common common);
	public void save(String resourceIds, String userId, String roseId);
	public void remove(Common common);
}
