package com.zfsoft.hrm.file.biz.impl;

import java.util.Date;
import java.util.List;

import org.springframework.util.Assert;

import com.zfsoft.hrm.file.biz.bizinterface.IAttachementBusiness;
import com.zfsoft.hrm.file.dao.daointerface.IAttachementDao;
import com.zfsoft.hrm.file.entity.Attachement;
import com.zfsoft.hrm.file.exception.AttachementException;
import com.zfsoft.hrm.file.query.AttachementQuery;

/**
 * {@link IAttachementBusiness}的实现
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-10-19
 * @version V1.0.0
 */
public class AttachementBusinessImpl implements IAttachementBusiness {
	
	private IAttachementDao attachementDao;

	@Override
	public List<Attachement> getAttachements(AttachementQuery query)
			throws AttachementException {
		Assert.notNull( query );
		
		return attachementDao.findList( query );
	}

	@Override
	public List<Attachement> getFromAttachements(String formId)
			throws AttachementException {
		Assert.hasText( formId );
		
		AttachementQuery query = new AttachementQuery();
		
		query.setFormId( formId );
		
		return getAttachements( query );
	}

	@Override
	public void removeByFormId(String formId) throws AttachementException {
		Assert.hasText( formId );
		
		attachementDao.deleteByFormId( formId );
	}

	@Override
	public void removeById(String guid) throws AttachementException {
		Assert.hasText( guid );
		
		attachementDao.deleteById( guid );
	}

	@Override
	public void save(Attachement bean) throws AttachementException {
		Assert.notNull( bean );
//		Assert.hasText( bean.getFormId() );
		
		bean.setUploadTime( new Date() );
		
		attachementDao.insert( bean );
	}

	@Override
	public Attachement getAttachement(String guid) throws AttachementException {
		Assert.hasText( guid );
		
		return attachementDao.findById( guid );
	}
	
	public void setAttachementDao(IAttachementDao attachementDao) {
		this.attachementDao = attachementDao;
	}

}
