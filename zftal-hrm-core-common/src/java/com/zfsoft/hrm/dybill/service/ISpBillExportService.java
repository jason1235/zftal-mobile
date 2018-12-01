package com.zfsoft.hrm.dybill.service;

import java.util.List;
import java.util.Map;

import com.zfsoft.hrm.dybill.entity.SpBillConfig;
import com.zfsoft.hrm.dybill.entity.SpBillInstance;

/**
 * 实例检出
 * @author Patrick Shen
 */
public interface ISpBillExportService {
	/**
	 * 实例检出（单个）默认解析代码值,KEY值为小写
	 * @param entity
	 */
	public Map<String,String> getValueMap(SpBillConfig spBillConfig,SpBillInstance spBillInstance);
	
	/**
	 * 实例检出,KEY值为小写
	 * @param spBillConfig
	 * @param spBillInstance
	 * @param explain 是否解析代码值
	 * @return
	 */
	public Map<String, String> getValueMap(SpBillConfig spBillConfig,
			SpBillInstance spBillInstance,boolean explain);
	/**
	 * 实例检出
	 * @param entity
	 */
	public Map<String,Map<String,String>> getValueMap(SpBillConfig spBillConfig,List<SpBillInstance> spBillInstanceList);
	
	public Map<String,String> getValueMapForExport(SpBillConfig spBillConfig,
			SpBillInstance spBillInstance);
	
	/**
	 * 检出选择实例map
	 * map key如下
	 * 	data_key 主键名
	 * 	data_id 主键值
	 * 	data_source  来源表
	 * 	
	 * @return
	 */
	public List<Map<String, String>> getCatchMap(SpBillConfig spBillConfig,
			SpBillInstance spBillInstance);
}
