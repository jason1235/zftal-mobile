package com.zfsoft.hrm.dybillgrade.dao;

import com.zfsoft.dao.annotation.BaseAnDao;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.dybillgrade.entity.SpBillGradeCondition;
import com.zfsoft.hrm.dybillgrade.entity.SpBillGradeConditionQuery;

/**
 * 
 * @author ChenMinming
 * @date 2014-6-6
 * @version V1.0.0
 */
public interface ISpBillGradeConditionDao extends BaseAnDao<SpBillGradeCondition>{
	//分页查询表单配置列表显示
	PageList<SpBillGradeCondition> getPagingList(SpBillGradeConditionQuery query);
	int getPagingCount(SpBillGradeConditionQuery query);
}
