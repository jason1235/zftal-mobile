package com.zfsoft.workflow.service;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.workflow.exception.WorkFlowException;
import com.zfsoft.workflow.model.SpBusiness;
import com.zfsoft.workflow.model.query.SpBusinessQuery;

/**
 * 类说明：业务管理SERVICE接口类
 * @author yingjie.fan
 * @version 1.0
 */
public interface ISpBusinessService extends BaseInterface {
	/* @interface model: 添加一条SpBusiness记录 */
	void insert(SpBusiness spBusiness, String[] calssIds, String[] calssPrivilege) throws WorkFlowException;
	
	/* @interface model: 添加一条SpBusiness记录 */
	void insert(SpBusiness spBusiness) throws WorkFlowException;

	/* @interface model: 更新一条SpBusiness记录 */
	void update(SpBusiness spBusiness, String[] calssIds, String[] calssPrivilege) throws WorkFlowException;

	/* @interface model: 删除一条SpBusiness记录 */
	void delete(String bId) throws WorkFlowException;
	
	/* @interface model: 删除一条SpBusiness记录 */
	void deleteByRelDetail(String relDetail) throws WorkFlowException;

	/* @interface model: 查询SpBusiness结果集,返回SpBusiness对象的集合 */
	List<SpBusiness> findSpBusiness(SpBusiness spBusiness) throws WorkFlowException;
	
	SpBusiness findSpBusinessById(String bid);
	
	SpBusiness findSpBusinessByIdAndBType(String bid, String bType);
	
	SpBusiness findSpBusinessByBcode(String bcode, String workId);
	
	SpBusiness findSpBusinessByRelDetail(String relDetail, String workId);
	
	/* @interface model: 查询SpBusiness结果集，有分页,返回SpBusiness对象的集合 */
	PageList<SpBusiness> getPagingBusiness(SpBusinessQuery query) throws WorkFlowException;
}
