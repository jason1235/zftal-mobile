package com.zfsoft.mobile.group.dao;

import java.util.List;
import java.util.Map;

import com.zfsoft.mobile.group.entity.PushGroup;
import com.zfsoft.mobile.group.entity.PushGroupMember;
import com.zfsoft.mobile.group.query.PushGroupQuery;

/**
 * 
 * @author ChenMinming
 * @date 2015-4-21
 * @version V1.0.0
 */
public interface IPushGroupDao {
	public List<PushGroup> getPagingList(PushGroupQuery pushGroupQuery);
	public int getPagingCount(PushGroupQuery pushGroupQuery);
	public PushGroup findById(String id);
	public void update(PushGroup pushGroup);
	public void insert(PushGroup pushGroup);
	public void delete(PushGroup pushGroup);
	
	public List<PushGroupMember> getPagedListWfpYh(PushGroupMember member);
	public List<PushGroupMember> getPagedListYfpYh(PushGroupMember member);
	public List<PushGroupMember> getPagedListByYhid(PushGroupMember member);
	
	public void insertMember(PushGroupMember member);
	public void insertAllMember(PushGroupMember member);
	public void deleteMember(PushGroupMember member);
	public int getPagingCountReWfpYh(PushGroupMember query);
	public List<PushGroupMember> getPagingListReWfpYh(PushGroupMember query);
	public void insertUser(Map<String, Object> param);
	public void deleteUser(Map<String, Object> param);
	public int getPageCountReYfpYh(PushGroupMember query);
	public List<PushGroupMember> getPagedListReYfpYh(PushGroupMember query);
}