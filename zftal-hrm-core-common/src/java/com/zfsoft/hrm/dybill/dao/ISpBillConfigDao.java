package com.zfsoft.hrm.dybill.dao;

import java.util.List;

import com.zfsoft.dao.annotation.BaseAnDao;
import com.zfsoft.hrm.dybill.entity.SpBillConfig;
import com.zfsoft.hrm.dybill.entity.SpBillQuery;

public interface ISpBillConfigDao extends BaseAnDao<SpBillConfig>{
	//分页查询表单配置列表显示
	List<SpBillQuery> getPagingList(SpBillQuery query);
	int getPagingCount(SpBillQuery query);
}
