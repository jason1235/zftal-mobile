package com.zfsoft.hrm.pendingAffair.dao;

import com.zfsoft.dao.annotation.BaseAnDao;
import com.zfsoft.hrm.pendingAffair.entities.PendingAffairInfo;

/**
 * 
 * @author Administrator
 *
 */
public interface IPendingAffairNoSessionDao extends BaseAnDao<PendingAffairInfo>{
    
    /**
     * 方法描述：取得部门名
     * @param pendingAffairInfo
     */
    public String getDwmByUserId(String yhm);
    
}
