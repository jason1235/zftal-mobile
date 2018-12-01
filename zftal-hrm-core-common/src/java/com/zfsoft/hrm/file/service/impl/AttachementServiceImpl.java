package com.zfsoft.hrm.file.service.impl;

import java.util.List;

import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.file.biz.bizinterface.IAttachementBusiness;
import com.zfsoft.hrm.file.entity.Attachement;
import com.zfsoft.hrm.file.query.AttachementQuery;
import com.zfsoft.hrm.file.service.svcinterface.IAttachementService;

/**
 * {@link IAttachementService}的（缺省）实现
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-10-19
 * @version V1.0.0
 */
public class AttachementServiceImpl implements IAttachementService {
	
	private IAttachementBusiness attachementBusiness;

	@Override
	public Attachement getById( String guid ) throws RuntimeException {
		if( guid == null || guid.trim().length() == 0 ) {
			throw new RuleException( "指定的附件全局ID为空" );
		}
		
		return attachementBusiness.getAttachement( guid );
	}

	@Override
	public List<Attachement> getList( AttachementQuery query ) throws RuntimeException {
		if( query == null ) {
			throw new RuleException( "查询条件实体为null" );
		}
		
		return attachementBusiness.getAttachements( query );
	}

	@Override
	public void removeById( String guid ) throws RuntimeException {
		if( guid == null || guid.trim().length() == 0 ) {
			throw new RuleException( "指定的附件全局ID为空" );
		}
		
		attachementBusiness.removeById( guid );
	}

	public void setAttachementBusiness(IAttachementBusiness attachementBusiness) {
		this.attachementBusiness = attachementBusiness;
	}

}
