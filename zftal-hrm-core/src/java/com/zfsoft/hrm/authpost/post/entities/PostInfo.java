package com.zfsoft.hrm.authpost.post.entities;

import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.config.ICodeConstants;

/**
 * 岗位信息表
 * @author 沈鲁威 Patrick Shen
 * @since 2012-7-24
 * @version V1.0.0
 */
public class PostInfo {
	
	private String id;//岗位编码
	private String typeCode;//岗位类别
	private String name;//岗位名称
	private int sort;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTypeName(){
		if(typeCode!=null&&!typeCode.trim().equals(""))
			return CodeUtil.getItem(ICodeConstants.DM_DEF_WORKPOST, typeCode).getDescription();
		else
			return "";
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	
}
