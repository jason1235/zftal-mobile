package com.zfsoft.hrm.expertvote.vote.entity;

import com.zfsoft.common.query.QueryModel;
/**
 * 
 * @author: xiaoyongjun
 * @since: 2014-4-21 上午10:56:03
 */
public class GroupMember extends QueryModel {
	private String id;
	private String zjz_id; //专家组ID
	private String zj_id;  //专家ID

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getZjz_id() {
		return zjz_id;
	}
	public void setZjz_id(String zjz_id) {
		this.zjz_id = zjz_id;
	}
	public String getZj_id() {
		return zj_id;
	}
	public void setZj_id(String zj_id) {
		this.zj_id = zj_id;
	}
}
