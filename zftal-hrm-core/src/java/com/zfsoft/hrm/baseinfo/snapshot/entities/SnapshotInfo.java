package com.zfsoft.hrm.baseinfo.snapshot.entities;

import java.io.Serializable;

import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;

/**
 * 实体
 * @author Administrator
 *
 */
public class SnapshotInfo extends DynaBean implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -9087311783832058935L;

    /**
     * 构造函数
     * @param clazz
     */
    public SnapshotInfo(InfoClass clazz) {
        super(clazz);
    }

}
