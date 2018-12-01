package com.zfsoft.hrm.expertvote.vote.service;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.expertvote.vote.entity.GroupMember;
import com.zfsoft.hrm.expertvote.vote.query.GroupMemberQuery;

public interface IGroupMemberService {
	/**
	 * 专家组添加成员
	 * @param: 
	 * @return:
	 */
	public void insert(GroupMember groupMember);
	/**
	 * 专家组内专家成员列表
	 * @param: 
	 * @return:
	 */
	public PageList<GroupMemberQuery> getPagedList(GroupMemberQuery groupMemberQuery);
	/**
	 * 随机抽取专家添加到组
	 */
	public List<String> getRandomExpert(List<String> expertList, int count);
	/**
	 * 移除专家组内专家成员
	 */
	public void remove(String id);
	/**
	 * 用于添加成员判断重复性
	 */
	public int getZj_id(String id,String zjz_id);
	/**
	 * 删除专家信息判断是否被专家组使用
	 * @param: 
	 * @return:
	 */
	public int getMemberCount(String id);
}
