package com.zfsoft.hrm.authpost.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.zfsoft.hrm.baseinfo.code.entities.Catalog;
import com.zfsoft.hrm.baseinfo.code.service.svcinterface.IItemDecorator;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.config.ICodeConstants;


public class PostInitializor implements InitializingBean{
	
	private static Logger log = LoggerFactory.getLogger(PostInitializor.class);
	
	private IItemDecorator deptPostService;
	private IItemDecorator postInfoService;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Catalog deptCatalog = new Catalog();
		deptCatalog.setGuid(ICodeConstants.DM_DEF_DEPT_POST);
		deptCatalog.setName("部门岗位代码表");
		CodeUtil.append(deptCatalog, deptPostService.getItemList());
		
		Catalog postCatalog = new Catalog();
		postCatalog.setGuid(ICodeConstants.DM_POSTINFO);
		postCatalog.setName("岗位信息代码表");
		CodeUtil.append(postCatalog, postInfoService.getItemList());
		log.debug("部门岗位及岗位信息初始化完成");
	}

	public void setDeptPostService(IItemDecorator deptPostService) {
		this.deptPostService = deptPostService;
	}

	public void setPostInfoService(IItemDecorator postInfoService) {
		this.postInfoService = postInfoService;
	}

}
