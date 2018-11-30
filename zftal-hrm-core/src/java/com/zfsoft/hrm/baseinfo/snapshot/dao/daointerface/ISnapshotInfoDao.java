package com.zfsoft.hrm.baseinfo.snapshot.dao.daointerface;

import java.util.List;
import java.util.Map;

import com.zfsoft.hrm.baseinfo.snapshot.query.SnapshotInfoQuery;

/**
 * 导出信息类表单DAO
 * @author Administrator
 *
 */
public interface ISnapshotInfoDao {
    /**
     * 查询数据件数
     * @param snapshotInfoQuery
     * @return
     */
    public int getPagingCount (SnapshotInfoQuery snapshotInfoQuery);
    
    /**
     * 查询数据
     * @param snapshotInfoQuery
     * @return
     */
    public List<Map<String,Object>> getPagingList(SnapshotInfoQuery snapshotInfoQuery);
}
