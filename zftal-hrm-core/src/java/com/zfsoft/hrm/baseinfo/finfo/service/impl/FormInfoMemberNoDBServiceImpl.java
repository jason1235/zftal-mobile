package com.zfsoft.hrm.baseinfo.finfo.service.impl;

import java.util.List;

import com.zfsoft.hrm.baseinfo.finfo.entities.FormInfoMember;
import com.zfsoft.hrm.baseinfo.finfo.exception.FormInfoException;
import com.zfsoft.hrm.baseinfo.finfo.service.svcinterface.IFormInfoMemberService;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;

/**
 * 
 * @author ChenMinming
 * @date 2015-6-1
 * @version V1.0.0
 */
public class FormInfoMemberNoDBServiceImpl implements IFormInfoMemberService{

	@Override
	public FormInfoMember getMember(String name, String classId, Boolean batch)
			throws FormInfoException {
		InfoClass clazz = InfoClassCache.getInfoClass(classId);
		if(clazz==null) return null;
		FormInfoMember member = new FormInfoMember( clazz.getGuid(), clazz.getName() );
		member.setName( name );
		return member;
	}

	@Override
	public FormInfoMember[] getMembers(String name, Boolean batch)
			throws FormInfoException {
		List<InfoClass>  classList = InfoClassCache.getInfoClasses();
		FormInfoMember[] r = new FormInfoMember[classList.size()];
		int i=0;
		for (InfoClass c : classList) {
			r[i++]=new FormInfoMember( c.getGuid(), c.getName() );
		}
		return r;
	}

	@Override
	public void modifyMemberOpen(FormInfoMember member)
			throws FormInfoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modifySwapMemberIndex(String name, String[] classIds,
			Boolean batch) throws FormInfoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveMember(FormInfoMember member) throws FormInfoException {
		// TODO Auto-generated method stub
		
	}

}
