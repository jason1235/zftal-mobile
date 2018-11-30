package com.zfsoft.hrm.dybillgrade.service;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.dybillgrade.entity.SpBillGradeCondition;
import com.zfsoft.hrm.dybillgrade.entity.SpBillGradeConditionQuery;
import com.zfsoft.hrm.dybillgrade.entity.SpBillGradeConfig;
import com.zfsoft.hrm.dybillgrade.entity.SpBillGradeConfigQuery;

/**
 * 
 * @author ChenMinming
 * @date 2014-6-6
 * @version V1.0.0
 */
public interface ISpBillGradeConfigService {
	
	public void save(SpBillGradeConfig spBillGradeConfig);
	
	public void update(SpBillGradeConfig spBillGradeConfig);

	public void delete(String id);
	
	public SpBillGradeConfig getById(String id);
	
	public List<SpBillGradeConfig> findList(SpBillGradeConfig spBillGradeConfig);
	
	public PageList<SpBillGradeConfig> findSpBillGradeConfigPageList(SpBillGradeConfigQuery query);
	
	public SpBillGradeConfig findFullSpBillGradeConfig(String id);
	
	public void saveCondition(SpBillGradeCondition spBillGradeCondition);
	
	public void updateCondition(SpBillGradeCondition spBillGradeCondition);

	public void deleteCondition(String id);
	
	public SpBillGradeCondition getConditionById(String id);
	
	public PageList<SpBillGradeCondition> findSpBillGradeConditionPageList(SpBillGradeConditionQuery query);
	
}
