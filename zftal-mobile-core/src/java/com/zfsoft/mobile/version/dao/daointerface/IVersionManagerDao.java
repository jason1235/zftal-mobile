package com.zfsoft.mobile.version.dao.daointerface;

import java.util.List;

import com.zfsoft.mobile.version.entity.VersionManager;

public interface IVersionManagerDao {

	/**
	 * <p>Description: 获取最新版本信息</p>
	 * @param schoolcode 项目编码
	 * @param typecode  产品类型
	 * @param terrace  平台
	 * @param versionnum 版本号
	 * @return
	 *
	 * @since Jan 16, 2013 9:28:00 AM
	 * @author huangzhaoxia
	 */
	public List<VersionManager> getList(VersionManager versionManager);
	
	
	public int getListCount(VersionManager versionManager);


	public void insert(VersionManager query);


	public void update(VersionManager query);


	public void updateStatusById(VersionManager query);


	public void delete(VersionManager query);
	
}
