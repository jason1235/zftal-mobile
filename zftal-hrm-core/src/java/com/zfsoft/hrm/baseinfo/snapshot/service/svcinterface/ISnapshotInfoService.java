package com.zfsoft.hrm.baseinfo.snapshot.service.svcinterface;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.snapshot.entities.SnapshotInfo;
import com.zfsoft.hrm.baseinfo.snapshot.query.SnapshotInfoQuery;

/**
 * 导出信息类表单service
 * @author Administrator
 *
 */
public interface ISnapshotInfoService {

    public PageList<SnapshotInfo> getPagingList(SnapshotInfoQuery snapshotInfoQuery);
}
