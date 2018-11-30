package com.zfsoft.hrm.baseinfo.infoclass.business.impl;

import org.springframework.util.Assert;

import com.zfsoft.hrm.baseinfo.infoclass.business.bizinterface.ICatalogBusiness;
import com.zfsoft.hrm.baseinfo.infoclass.dao.daointerface.ICatalogDao;
import com.zfsoft.hrm.baseinfo.infoclass.entities.Catalog;
import com.zfsoft.hrm.baseinfo.infoclass.exception.InfoClassException;
import com.zfsoft.hrm.config.type.InfoCatalogType;

/**
 * {@link ICatalogBusiness}的缺省实现
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-6
 * @version V1.0.0
 */
public class CatalogBusinessImpl implements ICatalogBusiness {
	
	private ICatalogDao dao;

	public void setDao(ICatalogDao dao) {
		this.dao = dao;
	}
	
	@Override
	public Catalog getCatalogById(String guid) throws InfoClassException {
		Assert.notNull( guid, "parameter [guid] is required; it cannot be null." );
		
		return dao.findById( guid );
	}

	@Override
	public InfoCatalogType getCatalogTypeInfo(String guid) throws InfoClassException {
		Assert.notNull( guid, "parameter [guid] is required; it cannot be null." );
		
		Catalog catalog = getCatalogById( guid );
		
		if( catalog == null ) {
			return null;
		}
		
		return catalog.getTypeInfo();
	}

}
