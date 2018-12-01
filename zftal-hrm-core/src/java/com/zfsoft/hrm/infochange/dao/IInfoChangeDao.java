package com.zfsoft.hrm.infochange.dao;

import com.zfsoft.dao.annotation.BaseAnDao;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.infochange.entity.InfoChange;
import com.zfsoft.hrm.infochange.query.InfoChangeQuery;
/** 
 * @author Patrick Shen
 * @date 2013-6-9 下午02:22:08 
 */
public interface IInfoChangeDao extends BaseAnDao<InfoChange>{

	PageList<InfoChange> getPagedList(InfoChangeQuery cloneQuery);
}
