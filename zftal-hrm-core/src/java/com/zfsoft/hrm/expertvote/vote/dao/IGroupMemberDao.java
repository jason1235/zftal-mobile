package com.zfsoft.hrm.expertvote.vote.dao;

import org.springframework.dao.DataAccessException;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.expertvote.vote.entity.GroupMember;
import com.zfsoft.hrm.expertvote.vote.query.GroupMemberQuery;

public interface IGroupMemberDao {
	/**
	 * 专家组添加成员
	 * @param: 
	 * @return:
	 */
	public void insert(GroupMember groupMember) throws DataAccessException;
	/**
	 * 专家组内专家成员列表
	 * @param: 
	 * @return:
	 */
	public PageList<GroupMemberQuery> getPagedList(GroupMemberQuery groupMemberQuery) throws DataAccessException;
	/**
	 * 移除专家组内专家成员
	 */
	public void remove(String id) throws DataAccessException;
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
