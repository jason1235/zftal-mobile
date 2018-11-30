package com.zfsoft.hrm.dybillgrade.dao;
import com.zfsoft.dao.annotation.BaseAnDao;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.dybillgrade.entity.SpBillGradeConfig;
import com.zfsoft.hrm.dybillgrade.entity.SpBillGradeConfigQuery;

/**
 * 
 * @author ChenMinming
 * @date 2014-6-6
 * @version V1.0.0
 */
public interface ISpBillGradeConfigDao extends BaseAnDao<SpBillGradeConfig>{
	//分页查询表单配置列表显示
	PageList<SpBillGradeConfig> getPagingList(SpBillGradeConfigQuery query);
	int getPagingCount(SpBillGradeConfigQuery query);
}
