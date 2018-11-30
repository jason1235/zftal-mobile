package com.zfsoft.hrm.baseinfo.snapshot.query;

import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;

public class SnapshotInfoQuery extends DynaBeanQuery {

    /**
     * 
     */
    private static final long serialVersionUID = -1943203041801920987L;

    /**
     * 构造函数
     * @param infoClass
     */
    public SnapshotInfoQuery(InfoClass infoClass) {
        super(infoClass);
        setPerPageSize(10);
    }
}
