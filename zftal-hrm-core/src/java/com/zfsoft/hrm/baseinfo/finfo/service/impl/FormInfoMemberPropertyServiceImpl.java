package com.zfsoft.hrm.baseinfo.finfo.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.zfsoft.hrm.baseinfo.finfo.business.bizinterface.IFormInfoMemberPropertyBusiness;
import com.zfsoft.hrm.baseinfo.finfo.entities.FormInfoMember;
import com.zfsoft.hrm.baseinfo.finfo.entities.FormInfoMemberProperty;
import com.zfsoft.hrm.baseinfo.finfo.exception.FormInfoException;
import com.zfsoft.hrm.baseinfo.finfo.service.svcinterface.IFormInfoMemberPropertyService;
import com.zfsoft.hrm.baseinfo.finfo.util.FormInfoUtil;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.orcus.lang.ArrayUtil;

/**
 * {@link IFormInfoMemberPropertyService}的实现
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-27
 * @version V1.0.0
 */
public class FormInfoMemberPropertyServiceImpl implements IFormInfoMemberPropertyService {
	
	private IFormInfoMemberPropertyBusiness formInfoMemberPropertyBusiness;

	public void setFormInfoMemberPropertyBusiness(
			IFormInfoMemberPropertyBusiness formInfoMemberPropertyBusiness) {
		this.formInfoMemberPropertyBusiness = formInfoMemberPropertyBusiness;
	}

	@Override
	public FormInfoMemberProperty[] getMemberProperties(FormInfoMember member)
			throws FormInfoException {
		if( member == null ) {
			throw new RuleException("成员属性所属的成员为null");
		}
		
		FormInfoMemberProperty[] properties = formInfoMemberPropertyBusiness.getMemberProperties( member ); 
		
		return valid( member, properties );
	}
	
	@Override
	public FormInfoMemberProperty[] getEditMemberProperties(
			FormInfoMember member) throws FormInfoException {
		FormInfoMemberProperty[] result = new FormInfoMemberProperty[0];
		
		//此处不直接调用getViewMemberProperties( FormInfoMember member )可降低循环次数
		for ( FormInfoMemberProperty property : getMemberProperties(member) ) {
			if( property.isViewable() && property.isEditable() ) {
				result = (FormInfoMemberProperty[]) ArrayUtil.addElement( result, property, FormInfoMemberProperty.class );
			}
		}
		
		return result;
	}

	@Override
	public FormInfoMemberProperty[] getViewMemberProperties(
			FormInfoMember member) throws FormInfoException {
		FormInfoMemberProperty[] result = new FormInfoMemberProperty[0];
		
		for ( FormInfoMemberProperty property : getMemberProperties(member) ) {
			if( property.isViewable() ) {
				result = (FormInfoMemberProperty[]) ArrayUtil.addElement( result, property, FormInfoMemberProperty.class );
			}
		}
		
		return result;
	}

	@Override
	public FormInfoMemberProperty getMemberProperty(FormInfoMemberProperty property )
			throws FormInfoException {
		if( property == null ) {
			throw new RuleException("成员属性为null");
		}
		
		return formInfoMemberPropertyBusiness.getMemberProperty( property );
	}

	@Override
	public void save(FormInfoMemberProperty property) throws FormInfoException {
		if( property == null ) {
			throw new RuleException("保存的成员属性为null");
		}
		
		if( formInfoMemberPropertyBusiness.exist( property ) ) {
			modify( property );
		} else {
			add( property );
		}
	}
	
	@Override
	public void saveProList(FormInfoMember member, String[] pNames)
			throws FormInfoException {
		if( pNames == null||pNames.length==0 ) {
			formInfoMemberPropertyBusiness.removeProperty(member);
			return;
		}
		FormInfoMemberProperty[] properties = formInfoMemberPropertyBusiness.getMemberProperties( member );
		int num = pNames.length;
		for (FormInfoMemberProperty property : properties) {
			property.setCreated(false);
			if(num>0){
				for (int i=0;i<pNames.length;i++) {
					if(pNames[i]!=null&&property.getpName().equals(pNames[i])){
						property.setCreated(true);
						pNames[i]=null;
						property.setIndex(i);
						formInfoMemberPropertyBusiness.modifyIndex( property );
						num--;
						break;
					}
				}
			}
			if(!property.isCreated()){
				formInfoMemberPropertyBusiness.removeProperty(property);
			}
		}
		for (int i=0;i<pNames.length;i++) {
			String pName = pNames[i];
			if(pName!=null){
				FormInfoMemberProperty property = FormInfoUtil.createMemberProperty(member, pName );
				property.setIndex(i);
				add(property );
			}
		}
		
		
	}
	@Override
	public void modifyViewable(FormInfoMemberProperty property)
			throws FormInfoException {
		if( property == null ) {
			throw new RuleException("修改的成员属性为null");
		}
		
		if( !formInfoMemberPropertyBusiness.exist( property ) ) {
			add( FormInfoUtil.createMemberProperty( property.getMember(), property.getpName() ) );
		}
		
		formInfoMemberPropertyBusiness.modifyViewable( property );
	}

	@Override
	public void modifyIndex(FormInfoMember member, String[] pNames)
			throws FormInfoException {
		FormInfoMemberProperty property = new FormInfoMemberProperty();
		property.setMember( member );
		
		property.setpName( pNames[0] );
		FormInfoMemberProperty property1 = getMemberProperty( property );
		property.setpName( pNames[1] );
		FormInfoMemberProperty property2 = getMemberProperty( property );
		
		int index1 = property1.getIndex();
		int index2 = property2.getIndex();
		
		property1.setIndex( index2 );
		property2.setIndex( index1 );
		
		formInfoMemberPropertyBusiness.modifyIndex( property1 );
		formInfoMemberPropertyBusiness.modifyIndex( property2 );
	}
	
	/**
	 * 增加组成成员属性
	 * @param property 增加的组成成员属性
	 * @throws FormInfoException 如果操作出现异常
	 */
	private void add( FormInfoMemberProperty property ) throws FormInfoException {
		if( property == null ) {
			throw new RuleException("增加的成员属性为null");
		}
		
		formInfoMemberPropertyBusiness.addProperty( property );
	}
	
	/**
	 * 修改组成成员属性
	 * @param property 修改的组成成员属性
	 * @throws FormInfoException 如果操作出现异常
	 */
	private void modify( FormInfoMemberProperty property ) throws FormInfoException {
		if( property == null ) {
			throw new RuleException("修改的成员属性为null");
		}
		
		formInfoMemberPropertyBusiness.modifyProperty( property );
	}
	
	private void remove( FormInfoMemberProperty property ) throws FormInfoException {
		if( property == null ) {
			throw new RuleException("删除的成员属性为null");
		}
		
		formInfoMemberPropertyBusiness.removeProperty( property );
	}
	
	/**
	 * 对成员属性的有效性进行验证
	 * <p>
	 * 对失效的成员属性进行删除，对遗漏的成员属性进行增加
	 * </p>
	 * @param result 原始的成员属性集合
	 * @throws FormInfoException 如果操作出现异常
	 */
	private FormInfoMemberProperty[] valid( FormInfoMember member,
			FormInfoMemberProperty[] result ) throws FormInfoException {
		if( member == null || member.getName() == null || member.getClassId() == null ) {
			throw new RuleException("成员属性所属成员信息不完整");
		}
		
		//对无效的成员属性从数组中删除
		Integer[] indexes = FormInfoUtil.getSpilthIndex( member, result );
		int i = 0;
		
		for (Integer index : indexes) {
			//此处对成员属性的显示索引的重置是使其的显示索引与数据库中保持一致，为后续的成员属性增加做基础准备
			FormInfoMemberProperty property = result[index];
			result[index].setIndex( property.getIndex() - i++ );
			
			remove( result[index] );
		}
		
		result = (FormInfoMemberProperty[]) ArrayUtil.removeElement( result, indexes, FormInfoMemberProperty.class );
		
		//对遗漏的组成成员加入到数组中
		int index = result.length == 0 ? 0 : result[result.length-1].getIndex() + 1;	//获取原始有效数组中最大的序列索引，并对其+1
		FormInfoMemberProperty[] properties = FormInfoUtil.getOmit( member, result );
		i = 0;
		
		for (FormInfoMemberProperty property : properties) {
			property.setCreated(false);
			property.setIndex(index + i++ );
//			add( property );
		}
		
		result = (FormInfoMemberProperty[]) ArrayUtil.addElements( result, properties, FormInfoMemberProperty.class );
		
		return result;
	}

}
