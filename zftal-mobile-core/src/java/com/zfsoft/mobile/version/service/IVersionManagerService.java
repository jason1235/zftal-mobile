package com.zfsoft.mobile.version.service;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.version.entity.VersionManager;

public interface IVersionManagerService {

	/**
	 * <p>Description: 获取最新版本信息</p>
	 * @param schoolcode 项目编码
	 * @param typecode  产品类型
	 * @param terrace  平台
	 * @param versionnum 版本号
	 * @return
	 *
	 * @author zhangxu
	 */
	public PageList<VersionManager> getList(VersionManager versionManager);

	public void insert(VersionManager query);

	public void update(VersionManager query);

	public void updateStatusById(VersionManager query);

	public void delete(VersionManager query);
}
