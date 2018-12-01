package com.zfsoft.hrm.pendingAffair.service.svcinterface;

import java.util.List;

import com.zfsoft.common.log.User;
import com.zfsoft.hrm.pendingAffair.entities.PendingAffairInfo;

/**
 * 
 * @author Administrator
 *
 */
public interface IPendingAffairNoSessionService {

    /**
     * 通过角色获取
     * @param roleIds
     * @return
     */
    public List<PendingAffairInfo> getListByRoles(User user);
}
