package com.zfsoft.dataprivilege.dao;

import java.util.List;

import com.zfsoft.dataprivilege.entity.DataPrivilege;

public interface IDataPrivilegeDao {
	public List<DataPrivilege> findByQuery(DataPrivilege query);

	public void insert(DataPrivilege dataPrivilege);

	public void update(DataPrivilege dataPrivilege);

	public void delete(DataPrivilege dataPrivilege);
}
