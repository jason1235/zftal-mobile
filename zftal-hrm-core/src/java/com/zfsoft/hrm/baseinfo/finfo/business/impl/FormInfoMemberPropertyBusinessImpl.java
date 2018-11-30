package com.zfsoft.hrm.baseinfo.finfo.business.impl;

import org.springframework.util.Assert;

import com.zfsoft.hrm.baseinfo.finfo.business.bizinterface.IFormInfoMemberPropertyBusiness;
import com.zfsoft.hrm.baseinfo.finfo.dao.daointerface.IFormInfoMemberPropertyDao;
import com.zfsoft.hrm.baseinfo.finfo.entities.FormInfoMember;
import com.zfsoft.hrm.baseinfo.finfo.entities.FormInfoMemberProperty;
import com.zfsoft.hrm.baseinfo.finfo.exception.FormInfoException;
import com.zfsoft.hrm.baseinfo.finfo.query.FormInfoMemberPropertyQuery;
import com.zfsoft.hrm.baseinfo.finfo.util.FormInfoUtil;

/**
 * {@link IFormInfoMemberPropertyBusiness}的实现
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-24
 * @version V1.0.0
 */
public class FormInfoMemberPropertyBusinessImpl implements IFormInfoMemberPropertyBusiness {

	private IFormInfoMemberPropertyDao formInfoMemberPropertyDao;
	
	public void setFormInfoMemberPropertyDao(
			IFormInfoMemberPropertyDao formInfoMemberPropertyDao) {
		this.formInfoMemberPropertyDao = formInfoMemberPropertyDao;
	}
	
	@Override
	public FormInfoMemberProperty[] getMemberProperties(
			FormInfoMemberPropertyQuery query) throws FormInfoException {
		Assert.notNull( query );
		
		return formInfoMemberPropertyDao.find( query );
	}
	
	@Override
	public FormInfoMemberProperty[] getMemberProperties(FormInfoMember member)
			throws FormInfoException {
		Assert.notNull( member );
		Assert.hasText( member.getName() );
		Assert.hasText( member.getClassId() );
		
		FormInfoMemberPropertyQuery query = new FormInfoMemberPropertyQuery();
		
		query.setName( member.getName() );
		query.setClassId( member.getClassId() );
		
		return getMemberProperties( query );
	}
	
	@Override
	public FormInfoMemberProperty getMemberProperty(
			FormInfoMemberProperty property) throws FormInfoException {
		Assert.notNull( property );
		Assert.notNull( property.getMember() );
		Assert.hasText( property.getMember().getName() );
		Assert.hasText( property.getMember().getClassId() );
		Assert.hasText( property.getpName() );
		
		FormInfoMemberPropertyQuery query = new FormInfoMemberPropertyQuery();
		
		query.setName( property.getMember().getName() );
		query.setClassId( property.getMember().getClassId() );
		query.setpName( property.getpName() );
		
		FormInfoMemberProperty[] properties = getMemberProperties( query );
		
		if( properties == null || properties.length == 0 ) {
			return null;
		}
		
		return properties[0];
	}
	
	@Override
	public boolean exist(FormInfoMemberProperty property)
			throws FormInfoException {
		Assert.notNull( property );
		
		FormInfoMemberPropertyQuery query = new FormInfoMemberPropertyQuery();
		
		query.setpName( property.getpName() );
		if( property.getMember() != null ) {
			query.setName( property.getMember().getName() );
			query.setClassId( property.getMember().getClassId() );
		}
		
		return formInfoMemberPropertyDao.count( query ) > 0 ? true : false;
	}
	
	@Override
	public void addProperty(FormInfoMemberProperty property)
			throws FormInfoException {
		Assert.notNull( property );
		Assert.notNull( property.getMember() );
		
		formInfoMemberPropertyDao.insert( property );
	}
	
	@Override
	public void batchAddProperties(FormInfoMemberProperty[] properties)
			throws FormInfoException {
		Assert.notNull( properties );
		
		for ( FormInfoMemberProperty property : properties) {
			addProperty( property );
		}
	}

	@Override
	public void resetPeoperties(FormInfoMember member) throws FormInfoException {
		Assert.notNull( member );
		Assert.hasText( member.getName() );
		Assert.hasText( member.getClassId() );
		
		removeProperty( member );
		
		batchAddProperties (FormInfoUtil.createMemberProperties( member.getName(), member.getClassId() ) );
	}
	
	@Override
	public void modifyProperty(FormInfoMemberProperty property)
			throws FormInfoException {
		Assert.notNull( property );
		Assert.hasText( property.getpName() );
		Assert.notNull( property.getMember() );
		Assert.hasText( property.getMember().getName() );
		Assert.hasText( property.getMember().getClassId() );
		
		formInfoMemberPropertyDao.update( property );
	}

	@Override
	public void removeProperty(FormInfoMember member) throws FormInfoException {
		Assert.notNull( member );
		Assert.hasText( member.getName() );
		Assert.hasText( member.getClassId() );
		
		removeProperty( member, null );
	}
	
	@Override
	public void removeProperty(FormInfoMemberProperty property) throws FormInfoException {
		Assert.notNull( property );
		Assert.hasText( property.getpName() );
		Assert.notNull( property.getMember() );
		//Assert.hasText( property.getMember().getName() );
		Assert.hasText( property.getMember().getClassId() );
		
		upIndex( property );
		
		removeProperty( property.getMember(), property.getpName() );
	}

	@Override
	public void modifyViewable(FormInfoMemberProperty property)
			throws FormInfoException {
		Assert.notNull( property );
		Assert.hasText( property.getpName() );
		Assert.notNull( property.getMember() );
		Assert.hasText( property.getMember().getName() );
		Assert.hasText( property.getMember().getClassId() );
		
		formInfoMemberPropertyDao.updateViewable( property );
	}
	
	@Override
	public void modifyIndex(FormInfoMemberProperty property)
			throws FormInfoException {
		Assert.notNull( property );
		Assert.hasText( property.getpName() );
		Assert.notNull( property.getMember() );
		Assert.hasText( property.getMember().getName() );
		Assert.hasText( property.getMember().getClassId() );
		
		formInfoMemberPropertyDao.updateIndex( property );
	}
	
	/**
	 * 删除成员属性
	 * @param member 成员属性所属组成成员
	 * @param pName 成员属性名称
	 */
	private void removeProperty( FormInfoMember member, String pName ) {
		Assert.notNull( member );
		Assert.hasText( member.getName() );
		Assert.hasText( member.getClassId() );
		
		FormInfoMemberPropertyQuery query = new FormInfoMemberPropertyQuery();
		
		query.setName( member.getName() );
		query.setClassId( member.getClassId() );
		query.setpName( pName );
		
		formInfoMemberPropertyDao.delete( query );
	}
	
	/**
	 * 指定的成员属性后的所有成员属性向前移动一位（显示序号）
	 * @throws FormInfoException
	 */
	private void upIndex( FormInfoMemberProperty property ) throws FormInfoException {
		Assert.notNull( property );
		Assert.hasText( property.getpName() );
		Assert.notNull( property.getMember() );
		Assert.hasText( property.getMember().getName() );
		Assert.hasText( property.getMember().getClassId() );
		
		property = getMemberProperty( property );
		
		formInfoMemberPropertyDao.upIndex( property );
	}

}
