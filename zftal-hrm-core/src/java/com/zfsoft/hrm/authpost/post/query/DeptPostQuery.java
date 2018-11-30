package com.zfsoft.hrm.authpost.post.query;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.dao.query.BaseQuery;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.config.ICodeConstants;

/** 
 * 部门岗位查询实体
 * @author jinjj
 * @date 2012-7-24 下午01:29:21 
 *  
 */
public class DeptPostQuery extends BaseQuery {

	private static final long serialVersionUID = -6859474367531480853L;

	private String type;
	
	private String name;
	
	private String deptId;
	
	private String level;
	
	private String superiorId;

	/**
	 * 岗位类别
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * 岗位类别
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 岗位名称
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 岗位名称
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 部门编号
	 * @return
	 */
	public String getDeptId() {
		return deptId;
	}

	/**
	 * 部门编号
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
	
	/**
	 * 岗位等级
	 * @return
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * 岗位等级
	 * @param level
	 */
	public void setLevel(String level) {
		this.level = level;
	}

	/**
	 * 上级岗位
	 * @return
	 */
	public String getSuperiorId() {
		return superiorId;
	}

	/**
	 * 上级岗位
	 * @param superiorId
	 */
	public void setSuperiorId(String superiorId) {
		this.superiorId = superiorId;
	}
	
}
