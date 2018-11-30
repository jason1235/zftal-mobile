package com.zfsoft.hrm.baseinfo.snapshot.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.baseinfo.snapshot.dao.daointerface.ISnapshotInfoDao;
import com.zfsoft.hrm.baseinfo.snapshot.entities.SnapshotInfo;
import com.zfsoft.hrm.baseinfo.snapshot.query.SnapshotInfoQuery;
import com.zfsoft.hrm.baseinfo.snapshot.service.svcinterface.ISnapshotInfoService;

/**
 * SnapshotInfoServiceImpl
 * @author Administrator
 *
 */
public class SnapshotInfoServiceImpl implements ISnapshotInfoService {
    private ISnapshotInfoDao snapshotInfoDao;

    /**
     * 查询
     */
    public PageList<SnapshotInfo> getPagingList(SnapshotInfoQuery query) {
        PageList<SnapshotInfo> pageList = new PageList<SnapshotInfo>();
        List<SnapshotInfo> snapshotInfos = new ArrayList<SnapshotInfo>();
        Paginator paginator = new Paginator();
        
        if (query != null) {
            // 设置
            paginator.setItemsPerPage(query.getPerPageSize());
            paginator.setPage((Integer)query.getToPage());
            paginator.setItems(snapshotInfoDao.getPagingCount(query));
            pageList.setPaginator(paginator);
            
            if (paginator.getBeginIndex() <= paginator.getItems()) {
            	query.setStartRow(paginator.getBeginIndex());
                query.setEndRow(paginator.getEndIndex());
                // 查询信息
                List<Map<String, Object>> list = snapshotInfoDao.getPagingList(query);
                
                for (Map<String, Object> map : list) {
                    SnapshotInfo snapshotInfo = new SnapshotInfo(query.getClazz());
                    snapshotInfo.setValues(map);
                    snapshotInfos.add(snapshotInfo);
                }
                
                pageList.addAll(snapshotInfos);
            }

        }
        return pageList;
    }
    /**
     * @param snapshotInfoDao the snapshotInfoDao to set
     */
    public void setSnapshotInfoDao(ISnapshotInfoDao snapshotInfoDao) {
        this.snapshotInfoDao = snapshotInfoDao;
    }
    
}
