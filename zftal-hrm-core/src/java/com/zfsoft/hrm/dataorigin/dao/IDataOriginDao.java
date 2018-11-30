package com.zfsoft.hrm.dataorigin.dao;

import java.util.List;
import java.util.Map;

import com.zfsoft.hrm.dataorigin.entity.DataOriginConfig;
import com.zfsoft.hrm.dybill.entity.SpBillConfig;

/**
 * 
 * @author Administrator
 *
 */
public interface IDataOriginDao {

    /**
     * 取得表单
     * @return
     */
    public List<SpBillConfig> getBillConfigs();

    /**
     * 取得业务xml
     * @param ywId
     * @return
     */
    public DataOriginConfig getConfigsByParam(Map<String, String> param);

    /**
     * 插入新的数据源
     * @param dataOriginConfig
     */
    public void insertConfig(DataOriginConfig dataOriginConfig);

    /**
     * 更新数据源
     * @param dataOriginConfig
     */
    public void updateConfig(DataOriginConfig dataOriginConfig);

    /**
     * 取得数据
     * @param param
     * @return
     */
    public List<Map<String, Object>> getOutPutData(Map<String, Object> param);
    
    /**
     * 取得表单
     * @param billId
     * @return
     */
    public SpBillConfig getBillConfigById(String billId);

    /**
     * 取得版本
     * @param id
     * @return
     */
    public List<String> getBillConfigsVersion(String id);

}
