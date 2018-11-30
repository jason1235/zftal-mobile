package com.zfsoft.hrm.authpost.post.query;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.dao.query.BaseQuery;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.config.ICodeConstants;

/** 
 * 历史岗位查询实体
 * @author jinjj
 * @date 2012-7-25 下午06:35:37 
 *  
 */
public class PostHistoryQuery extends BaseQuery {

	private static final long serialVersionUID = 3620873627942331827L;
	
	private Date snapTime;
	
	private String deptId;

	/**
	 * 快照时间
	 * @return
	 */
	public Date getSnapTime() {
		return snapTime;
	}

	/**
	 * 快照时间
	 * @param snapTime
	 */
	public void setSnapTime(Date snapTime) {
		this.snapTime = snapTime;
	}

	/**
	 * 所属部门
	 * @return
	 */
	public String getDeptId() {
		return deptId;
	}

	/**
	 * 所属部门
	 * @param deptId
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
	public String getDeptValue(){
		String str = CodeUtil.getItemValue(ICodeConstants.DM_DEF_ORG, getDeptId());
		if(StringUtils.isEmpty(str)){
			return getDeptId();
		}
		return str;
	}
}
