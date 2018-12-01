package com.zfsoft.hrm.infochange.dao;

import com.zfsoft.dao.annotation.BaseAnDao;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.infochange.entity.InfoChange;
import com.zfsoft.hrm.infochange.query.InfoChangeQuery;

/**
 * 
 * @author ChenMinming
 * @date 2013-8-26
 * @version V1.0.0
 */
public interface IInfoInputDao extends BaseAnDao<InfoChange>{

	PageList<InfoChange> getPagedList(InfoChangeQuery cloneQuery);
}
