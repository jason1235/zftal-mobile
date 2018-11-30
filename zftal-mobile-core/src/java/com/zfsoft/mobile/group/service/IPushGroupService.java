package com.zfsoft.mobile.group.service;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.entities.JsglModel;
import com.zfsoft.dao.entities.YhglModel;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.group.entity.PushGroup;
import com.zfsoft.mobile.group.entity.PushGroupMember;
import com.zfsoft.mobile.group.query.PushGroupQuery;

/**
 * 
 * @author ChenMinming
 * @date 2015-4-21
 * @version V1.0.0
 */
public interface IPushGroupService {

	PageList<PushGroup> getPageList(PushGroupQuery query);
	
	void save(PushGroup pushGroup);
	
	void delete(PushGroup pushGroup);
	
	PushGroup findById(String id);
	
    public List<PushGroupMember> getPagedListWfpYh(PushGroupMember member);

    public List<PushGroupMember> getPagedListYfpYh(PushGroupMember member);
    
    public PushGroupMember insertMember(PushGroupMember member);

	public PushGroupMember deleteMember(PushGroupMember member);

	void insertAllMember(PushGroupMember member);

	PageList<PushGroupMember> getPagedListReWfpYh(PushGroupMember query);

	void insertUser(Map<String, Object> param);

	void deleteUser(Map<String, Object> param);
}
