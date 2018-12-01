package com.zfsoft.hrm.baseinfo.finfo.service.impl;

import com.zfsoft.hrm.baseinfo.finfo.business.bizinterface.IFormInfoMemberBusiness;
import com.zfsoft.hrm.baseinfo.finfo.business.bizinterface.IFormInfoMemberPropertyBusiness;
import com.zfsoft.hrm.baseinfo.finfo.entities.FormInfoMember;
import com.zfsoft.hrm.baseinfo.finfo.exception.FormInfoException;
import com.zfsoft.hrm.baseinfo.finfo.service.svcinterface.IFormInfoMemberService;
import com.zfsoft.hrm.baseinfo.finfo.util.FormInfoUtil;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.orcus.lang.ArrayUtil;

/**
 * {@link IFormInfoMemberService}的实现
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-22
 * @version V1.0.0
 */
public class FormInfoMemberServiceImpl implements IFormInfoMemberService {

	private IFormInfoMemberBusiness formInfoMemberBusiness;
	
	private IFormInfoMemberPropertyBusiness formInfoMemberPropertyBusiness;
	
	public void setFormInfoMemberBusiness(
			IFormInfoMemberBusiness formInfoMemberBusiness ) {
		this.formInfoMemberBusiness = formInfoMemberBusiness;
	}

	public void setFormInfoMemberPropertyBusiness(
			IFormInfoMemberPropertyBusiness formInfoMemberPropertyBusiness) {
		this.formInfoMemberPropertyBusiness = formInfoMemberPropertyBusiness;
	}
	
	@Override
	public FormInfoMember[] getMembers(String name,Boolean batch) throws FormInfoException {
		if( name == null || "".equals( name ) ) {
			throw new RuleException( "成员组组名为空" );
		}
		
		FormInfoMember[] members = formInfoMemberBusiness.getMembers( name ,batch);
		
		return valid( name, members);
	}

	@Override
	public FormInfoMember getMember(String name, String classId,Boolean batch)
			throws FormInfoException {
		if( name == null || "".equals( name ) ) {
			throw new RuleException( "成员组组名为空" );
		}
		
		if( classId == null || "".equals( classId ) ) {
			throw new RuleException( "组成成员信息类ID为空" );
		}
		
		FormInfoMember member = formInfoMemberBusiness.getMember( name, classId ,batch);
		
		if( member == null ) {
			member = FormInfoUtil.createMember( name, classId );
			
			//saveMember( member );
		}
		
		return member;
	}
	
	@Override
	public void saveMember(FormInfoMember member) throws FormInfoException {
		if( member == null ) {
			throw new RuleException("保存的组成成员为空");
		}
		
		if( formInfoMemberBusiness.exist( member ) ) {
			modifyMember( member );
		} else {
			addMember( member );
		}
		
	}
	
	@Override
	public void modifyMemberOpen(FormInfoMember member)
			throws FormInfoException {
		if( member == null ) {
			throw new RuleException("修改的组成成员为空");
		}
		
		if( !formInfoMemberBusiness.exist( member ) ) {
			addMember( FormInfoUtil.createMember( member.getName(), member.getClassId() ) );
		}

		formInfoMemberBusiness.openMember( member );
	}
	
	@Override
	public void modifySwapMemberIndex(String name, String[] classIds,Boolean batch)
			throws FormInfoException {
		FormInfoMember member1 = getMember( name, classIds[0] ,batch);
		FormInfoMember member2 = getMember( name, classIds[1] ,batch);
		
		int index1 = member1.getIndex();
		int index2 = member2.getIndex();
		
		member1.setIndex( index2 );
		member2.setIndex( index1 );
		
		formInfoMemberBusiness.modifyMemberIndex( member1 );
		formInfoMemberBusiness.modifyMemberIndex( member2 );
	}
	
	/**
	 * 对组成成员的有效性进行验证
	 * <p>
	 * 对无效的组成成员进行删除，对遗漏的组成成员进行添加
	 * </p>
	 * @param name 组成成员所属的组名
	 * @param result 原始的组成成员集合
	 * @return
	 * @throws FormInfoException 如果操作出现异常
	 */
	private FormInfoMember[] valid( String name, FormInfoMember[] result ) throws FormInfoException {
		if( name == null || "".equals( name ) ) {
			throw new RuleException( "组成成员组组名为null" );
		}
		
		if( result == null ) {
			result = new FormInfoMember[0];
		}
		
		//对无效的组成成员从数组中删除
		Integer[] indexes = FormInfoUtil.getSpilthIndex(result);
		int i = 0;
		
		for (Integer index : indexes) {
			//此处对组成成员的显示索引的重置是使其的显示索引与数据库中保持一致，为后续的组成成员增加做基础准备
			FormInfoMember member = result[index];
			result[index].setIndex( member.getIndex() - i++ );
			
			removeMember( member );
		}
		
		result = (FormInfoMember[]) ArrayUtil.removeElement( result, indexes, FormInfoMember.class );
		
		//对遗漏的组成成员加入到数组中
		int length = result.length;
		int index = length == 0 ? 0 : result[length-1].getIndex() + 1;	//获取原始有效数组中最大的序列索引，并对其+1
		                       
		FormInfoMember[] members = FormInfoUtil.getOmit( name, result );
		i = 0;

		for ( FormInfoMember member : members) {
			//所有的遗漏组成成员添加到数组的最后一位
			member.setIndex( index + i++ );
			addMember( member );
		}
		
		result = (FormInfoMember[]) ArrayUtil.addElements( result, members, FormInfoMember.class );
		
		return result;
	}
	
	/**
	 * 新增组成成员描述信息 
	 * @param member 新增的组成成员描述信息 
	 * @throws FormInfoException 如果操作出现异常
	 */
	private void addMember( FormInfoMember member ) throws FormInfoException {
		if( member == null ) {
			throw new RuleException("新增的组成成员为空");
		}
		
		//新增组成成员
		formInfoMemberBusiness.addMember( member );
		
		//增加组成成员属性
		//formInfoMemberPropertyBusiness.resetPeoperties( member );
		
	}
	
	/**
	 * 修改组成成员描述信息
	 * @param member 修改的组成成员描述信息
	 * @throws FormInfoException 如果操作出现异常
	 */
	private void modifyMember( FormInfoMember member ) throws FormInfoException {
		if( member == null ) {
			throw new RuleException("修改的组成成员为空");
		}
		
		formInfoMemberBusiness.modifyMember( member );
	}
	
	/**
	 * 删除组成成员信息
	 * @param member 删除的组成成员
	 * @throws FormInfoException 如果操作出现异常
	 */
	private void removeMember( FormInfoMember member ) throws FormInfoException {
		if( member == null ) {
			throw new RuleException("删除的组成成员为空");
		}
		
		//删除成员属性
		formInfoMemberPropertyBusiness.removeProperty( member );
		
		//删除成员
		formInfoMemberBusiness.removeMember( member );
	}

}
