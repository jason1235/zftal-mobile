package com.zfsoft.hrm.bnsinfochange.dao;

import com.zfsoft.dao.annotation.BaseAnDao;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.bnsinfochange.entity.BusinessInfoChange;
import com.zfsoft.hrm.bnsinfochange.query.BusinessInfoQuery;

/**
 * 
 * @author ChenMinming
 * @date 2014-6-17
 * @version V1.0.0
 */
public interface IBusinessInfoChangeDao extends BaseAnDao<BusinessInfoChange>{

	PageList<BusinessInfoChange> getPagedList(BusinessInfoQuery cloneQuery);
}