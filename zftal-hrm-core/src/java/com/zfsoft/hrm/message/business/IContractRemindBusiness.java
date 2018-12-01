package com.zfsoft.hrm.message.business;

import java.util.Date;
import java.util.List;
import java.util.Map;

/** 
 * @author jinjj
 * @date 2012-9-25 下午04:18:37 
 *  
 */
public interface IContractRemindBusiness {

	/**
	 * 以合同结束日期查询人员集合
	 * @param endDate
	 * @return
	 */
	public List<Map<String,String>> getList(Date endDate);
}
