package com.zfsoft.hrm.baseinfo.code;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.zfsoft.hrm.baseinfo.code.entities.Catalog;
import com.zfsoft.hrm.baseinfo.code.service.svcinterface.IItemDecorator;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.config.ICodeConstants;


public class OrgInitializor implements InitializingBean{
	
	private static Logger log = LoggerFactory.getLogger(OrgInitializor.class);
	
	private IItemDecorator orgService;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Catalog orgCatalog = new Catalog();
		orgCatalog.setGuid(ICodeConstants.DM_DEF_ORG);
		orgCatalog.setName("组织机构代码表");
		orgCatalog.setIncludeParentNode(1);
		orgCatalog.setDelimiter("-");
		CodeUtil.append(orgCatalog, orgService.getItemList());
		log.debug("组织机构代码表初始化完成");
	}

	public void setOrgService(IItemDecorator orgService) {
		this.orgService = orgService;
	}
	
}
