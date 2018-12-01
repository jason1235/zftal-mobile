package com.zfsoft.hrm.baseinfo.finfo.business.impl;

import org.springframework.util.Assert;

import com.zfsoft.hrm.baseinfo.finfo.business.bizinterface.IFormInfoMemberBusiness;
import com.zfsoft.hrm.baseinfo.finfo.dao.daointerface.IFormInfoMemberDao;
import com.zfsoft.hrm.baseinfo.finfo.entities.FormInfoMember;
import com.zfsoft.hrm.baseinfo.finfo.exception.FormInfoException;
import com.zfsoft.hrm.baseinfo.finfo.query.FormInfoMemberQuery;

/**
 * {@link IFormInfoMemberBusiness}的实现
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-23
 * @version V1.0.0
 */
public class FormInfoMemberBusinessImpl implements IFormInfoMemberBusiness {

	private IFormInfoMemberDao formInfoMemberDao;
	
	public void setFormInfoMemberDao(IFormInfoMemberDao formInfoMemberDao) {
		this.formInfoMemberDao = formInfoMemberDao;
	}
	
	@Override
	public FormInfoMember[] getMembers(String name,Boolean batch) throws FormInfoException {
		Assert.hasText( name );
		
		FormInfoMemberQuery query = new FormInfoMemberQuery();
		query.setName( name );
		query.setBatch(batch);
		return formInfoMemberDao.findList( query );
	}
	
	@Override
	public FormInfoMember getMember(String name, String classId,Boolean batch) throws FormInfoException {
		Assert.hasText( name );
		Assert.hasText( classId );
		
		FormInfoMember result = null;
		
		FormInfoMemberQuery query = new FormInfoMemberQuery();
		query.setName( name );
		query.setClassId( classId );
		query.setBatch(batch);
		FormInfoMember[] members = formInfoMemberDao.findList( query );
		
		if( members != null && members.length > 0 ) {
			result = members[0];
		}
		
		return result;
	}
	
	@Override
	public boolean exist(FormInfoMember member) throws FormInfoException {
		Assert.notNull( member );
		Assert.hasText( member.getName() );
		Assert.hasText( member.getClassId() );
		
		FormInfoMemberQuery query = new FormInfoMemberQuery();
		
		query.setName( member.getName() );
		query.setClassId( member.getClassId() );
		query.setBatch(member.getBatch());
		return formInfoMemberDao.findCount(query) > 0 ? true : false;
	}

	@Override
	public void addMember(FormInfoMember member) throws FormInfoException {
		Assert.notNull( member );
		Assert.hasText( member.getName() );
		Assert.hasText( member.getClassId() );
		
		formInfoMemberDao.insert( member );
	}

	@Override
	public void modifyMember(FormInfoMember member) throws FormInfoException {
		Assert.notNull( member );
		Assert.hasText( member.getName() );
		Assert.hasText( member.getClassId() );
		
		formInfoMemberDao.update( member );
	}

	@Override
	public void openMember(FormInfoMember member) throws FormInfoException {
		Assert.notNull( member );
		Assert.hasText( member.getName() );
		Assert.hasText( member.getClassId() );
		
		formInfoMemberDao.updateOpen( member );
	}
	
	@Override
	public void modifyMemberIndex(FormInfoMember member)
			throws FormInfoException {
		Assert.notNull( member );
		Assert.hasText( member.getName() );
		Assert.hasText( member.getClassId() );
		
		formInfoMemberDao.updateIndex( member );
	}

	@Override
	public void removeMember(FormInfoMember member) throws FormInfoException {
		Assert.notNull( member );
		Assert.hasText( member.getName() );
		Assert.hasText( member.getClassId() );
		
		formInfoMemberDao.delete(member);
		upIndex( member );
	}
	
	/**
	 * 对指定组成成员后的所有同组组成成员显示索引向前移动一位
	 * @param member 组成成员信息
	 * @throws FormInfoException 如果操作出现异常
	 */
	private void upIndex( FormInfoMember member ) throws FormInfoException {
		Assert.notNull( member );
		
		formInfoMemberDao.upIndex( member );
	}

}
