package com.zfsoft.hrm.usermanage.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.usermanage.dao.IBaseDao;
import com.zfsoft.hrm.usermanage.entity.DeleteItem;
import com.zfsoft.hrm.usermanage.service.IUserManageService;
import com.zfsoft.util.base.StringUtil;

/**
 * 
 * @author ChenMinming
 * @date 2014-1-13
 * @version V1.0.0
 */
public class UserManageServiceImpl implements IUserManageService {
	
	private IBaseDao baseDao;
	@Override
	public List<String> doReallyDelete(String gh) {
		if(StringUtil.isEmpty(gh)){
			return null;
		}
		List<String> list = new ArrayList<String>();
		//删除信息类中的数据(数据表、日志表、快照表)
		DeleteItem deleteTable;
		for(InfoClass clazz :InfoClassCache.getInfoClasses()){
			try{
			deleteTable = new DeleteItem(clazz.getIdentityName(), "gh", gh);
			baseDao.deleteByGh(deleteTable);
			}catch (Exception e) {
				list.add(clazz.getName());
				continue;
			}
			deleteTable = new DeleteItem(clazz.getIdentityName()+"_log", "gh", gh);
			baseDao.deleteByGh(deleteTable);
			deleteTable = new DeleteItem(clazz.getIdentityName()+"_snap", "gh", gh);
			baseDao.deleteByGh(deleteTable);
			
		}
		//删除overall表（数据表、快照表）
		deleteTable = new DeleteItem(InfoClassCache.getOverallInfoClass().getIdentityName()+"_snap", "gh", gh);
		baseDao.deleteByGh(deleteTable);
		deleteTable = new DeleteItem(InfoClassCache.getOverallInfoClass().getIdentityName(), "gh", gh);
		baseDao.deleteByGh(deleteTable);
		//删除用户表、角色表
		deleteTable = new DeleteItem("zftal_xtgl_yhjsb", "zgh", gh);
		baseDao.deleteByGh(deleteTable);
		deleteTable = new DeleteItem("zftal_xtgl_yhb", "zgh", gh);
		baseDao.deleteByGh(deleteTable);
		return list;
	}
	
	public void setBaseDao(IBaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	

}
