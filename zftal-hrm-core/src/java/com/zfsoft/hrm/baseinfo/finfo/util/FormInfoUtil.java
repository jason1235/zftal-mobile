package com.zfsoft.hrm.baseinfo.finfo.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.finfo.entities.FormInfo;
import com.zfsoft.hrm.baseinfo.finfo.entities.FormInfoMember;
import com.zfsoft.hrm.baseinfo.finfo.entities.FormInfoMemberProperty;
import com.zfsoft.hrm.baseinfo.finfo.service.svcinterface.IFormInfoMemberPropertyService;
import com.zfsoft.hrm.baseinfo.finfo.service.svcinterface.IFormInfoMemberService;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.config.FormInfoFactory;
import com.zfsoft.orcus.lang.ArrayUtil;
import com.zfsoft.util.base.StringUtil;

/**
 * 信息维护成员工具类
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-22
 * @version V1.0.0
 */
public class FormInfoUtil {
	
	/**
	 * 过滤无效的成员信息（不改变原始成员）
	 * @param members 原始的成员信息
	 * @return
	 * @throws IllegalArgumentException if members is null
	 */
	public static FormInfoMember[] filtrate( FormInfoMember[] members ) {
		Assert.notNull( members );
		
		FormInfoMember[] result = new FormInfoMember[0];
		
		for ( FormInfoMember member : members ) {
			if( InfoClassCache.contains( member.getClassId() ) ) {
				result = (FormInfoMember[]) ArrayUtil.addElement( result, member, FormInfoMember.class );
			}
		}
		
		return result;
	}
	
	/**
	 * 过滤无效的成员属性信息（不改变原始成员属性）
	 * @param properties 原始成员属性
	 * @return
	 * @throws IllegalArgumentException if properties is null
	 */
	public static FormInfoMemberProperty[] filtrate( FormInfoMemberProperty[] properties ) {
		Assert.notNull( properties );
		
		FormInfoMemberProperty[] result = new FormInfoMemberProperty[0];
		
		for( FormInfoMemberProperty property : properties ) {
			FormInfoMember member = property.getMember();
			if( InfoClassCache.getInfoClass( member.getClassId() ).getPropertyByName(member.getName() ) == null ) {
				result = (FormInfoMemberProperty[]) ArrayUtil.addElement( result, property, FormInfoMemberProperty.class );
			}
		}
		
		return result;
	}
	
	/**
	 * 对指定的成员组进行成员的填充
	 * <p>
	 * 根据成员组的类型获取该类型下所有的信息类列表（不含Overall），
	 * 如果信息类列表中的信息类未被任何成员组所使用，
	 * 实例一个成员组使用该信息类
	 * </p>
	 * @param name 成员组名称
	 * @param members 现有组成成员集合
	 * @return
	 * @throws IllegalArgumentException if name is null or members is null
	 */
	public static FormInfoMember[] fill( String name, FormInfoMember[] members ) {
		Assert.hasText( name );
		Assert.notNull( members );
		
		FormInfoMember[] result = getOmit( name, members );
		
		result = (FormInfoMember[]) ArrayUtil.addElements( members, result, FormInfoMember.class );
		
		return result;
	}
	
	/**
	 * 获取组成成员中无效的组成成员在组成成员集合中的索引
	 * <p>
	 * 无效的组成成员：使用已经不存在的信息类的组成成员
	 * </p>
	 * @param members 原始组成成员集合
	 * @return
	 */
	public static Integer[] getSpilthIndex( FormInfoMember[] members ) {
		Assert.notNull( members );
		
		Integer[] result = new Integer[0];
		
		for (int i = 0; i < members.length; i++) {
			if( InfoClassCache.contains( members[i].getClassId() ) ) {
				continue;
			}
			
			result = (Integer[]) ArrayUtil.addElement( result, i, Integer.class );
		}
		
		return result;
	}
	
	/**
	 * 获取指定组成成员下的成员数据集合中无效的成员属性集合索引
	 * @param member 组成成员
	 * @param properties 原始成员属性集合
	 * @return 无效的成员属性集合索引
	 */
	public static Integer[] getSpilthIndex( FormInfoMember member, FormInfoMemberProperty[] properties ) {
		Assert.notNull( member);
		Assert.hasText( member.getClassId() );
		Assert.notNull( properties );
		
		Integer[] result = new Integer[0];
		
		for ( int i = 0; i < properties.length; i++ ) {
			InfoClass clazz = InfoClassCache.getInfoClass( member.getClassId() );
			
			String pName = properties[i].getpName();

			if( clazz != null && clazz.getPropertyByName( pName ) != null ) {
				continue;
			}

			result = (Integer[]) ArrayUtil.addElement( result, i, Integer.class );
		}
		
		return result;
	}
	
	/**
	 * 获取成员组中被遗漏的组成成员集合
	 * <p>
	 * 遗漏的组成成员：未被组成成员使用的信息类转换成的组成成员
	 * </p>
	 * @param name 成员组名称
	 * @param members 现有组成成员集合
	 * @return 不带显示序号的成员集合
	 */
	public static FormInfoMember[] getOmit( String name, FormInfoMember[] members ) {
		Assert.hasText( name );
		Assert.notNull( members );
		
		FormInfo form = FormInfoFactory.getInfos( name );
		form = FormInfoFactory.getRoleInfos(name);
		List<InfoClass> classes = InfoClassCache.getInfoClasses( form.getType() );
		
		FormInfoMember[] result = new FormInfoMember[0];

		for ( InfoClass clazz : classes ) {
			if( exist( members, clazz.getGuid() ) ) {
				continue;
			}
			
			FormInfoMember member = new FormInfoMember( clazz.getGuid(), clazz.getName() );
			member.setName( name );
			
			result = (FormInfoMember[]) ArrayUtil.addElement( result, member, FormInfoMember.class );
		}
		
		return result;
	}
	
	/**
	 * 获取成员中被遗漏的成员属性集合（never null）
	 * <p>
	 * 遗漏的成员属性：未被成员属性所使用的信息类属性转成的成员属性
	 * </p>
	 * @param member 组成成员
	 * @param properties 现有成员属性集合
	 * @return 不带显示序号的被遗漏的成员属性集合
	 */
	public static FormInfoMemberProperty[] getOmit( FormInfoMember member, FormInfoMemberProperty[] properties ) {
		Assert.notNull( member);
		Assert.hasText( member.getClassId() );
		Assert.notNull( properties );
		
		FormInfoMemberProperty[] result = new FormInfoMemberProperty[0];
		InfoClass clazz = InfoClassCache.getInfoClass( member.getClassId() );
		
		for ( InfoProperty p : clazz.getProperties() ) {
			if( exist( properties, p.getFieldName() ) ) {
				continue;
			}
			
			FormInfoMemberProperty property = createInfoMemberProperty( p );
			property.setMember( member );

			result = (FormInfoMemberProperty[]) ArrayUtil.addElement( result, property, FormInfoMemberProperty.class );
		}
		
		return result;
	}
	
	/**
	 * 判断特定的成员组中是有成员使用指定的信息类
	 * @param members 成员组列表
	 * @param classId 信息类ID
	 * @return
	 * @throws IllegalArgumentException if members is null or classId is null
	 */
	public static boolean exist( FormInfoMember[] members, String classId ) {
		Assert.notNull( members );
		Assert.hasText( classId );
		
		boolean result = false;
		
		for ( FormInfoMember member : members ) {
			if( classId.equals( member.getClassId() ) ) {
				result = true;
				break;
			}
		}
		
		return result;
	}
	
	/**
	 * 判断特定的成员属性集合中是否存在指定名称的成员属性
	 * @param properties 成员属性集合
	 * @param pName 成员属性名称
	 * @return
	 * @throws IllegalArgumentException if properties is null or pName is null(or empty, blank)
	 */
	public static boolean exist( FormInfoMemberProperty[] properties, String pName ) {
		Assert.notNull( properties );
		Assert.hasText( pName );
		
		boolean result = false;
		
		for ( FormInfoMemberProperty property : properties ) {
			if( pName.equals( property.getpName() ) ) {
				result = true;
				break;
			}
		}
		
		return result;
	}

	/**
	 * 创建组成成员
	 * <p>
	 * 如果 name==null 或 name == '' 表示为指定所属成员组名称;
	 * </p>
	 * @param name 成员组组名
	 * @param classId 组成成员所使用信息类ID
	 * @return 如果指定信息类ID的信息类不存在则返回null
	 * @throws IllegalArgumentException if classId is null(or empty, blank)
	 */
	public static FormInfoMember createMember( String name, String classId ) {
		Assert.hasText( classId );
		
		InfoClass clazz = InfoClassCache.getInfoClass( classId );
		
		if( clazz == null ) {
			return null;
		}
		
		FormInfoMember member = new FormInfoMember( classId, clazz.getName() );
		member.setName( name );

		return member;
	}
	
	/**
	 * 创建组成成员属性
	 * @param name 成员组组名
	 * @param classId 组成成员所使用信息类ID
	 * @return 如果指定信息类ID的信息类不存在则返回null
	 */
	public static FormInfoMemberProperty[] createMemberProperties( String name, String classId ) {
		Assert.hasText( classId );
		
		FormInfoMember member = createMember( name, classId );
		
		//member不得为null
		if( member == null ) {
			return null;
		}
		
		FormInfoMemberProperty[] result = new FormInfoMemberProperty[0];
		InfoClass clazz = InfoClassCache.getInfoClass( classId );
		int index = 0;
		
		for ( InfoProperty property : clazz.getProperties() ) {
			FormInfoMemberProperty memberProperty = createInfoMemberProperty( property );
			memberProperty.setIndex( index++ );
			memberProperty.setMember( member );
			
			result = ( FormInfoMemberProperty[] ) ArrayUtil.addElement( result, memberProperty, FormInfoMemberProperty.class );
		}
		
		return result;
	}
	
	/**
	 * 创建组成成员属性
	 * @param member 组成成员信息
	 * @param pName 属性名称
	 * @return
	 */
	public static FormInfoMemberProperty createMemberProperty( FormInfoMember member, String pName ) {
		Assert.notNull( member );
		Assert.hasText( member.getClassId() );
		Assert.hasText( member.getName() );
		Assert.hasText( pName );
		
		InfoClass clazz = InfoClassCache.getInfoClass( member.getClassId() );
		InfoProperty property = clazz.getPropertyByName( pName );
		
		FormInfoMemberProperty memberProperty = createInfoMemberProperty( property );
		memberProperty.setMember( member );
		
		return memberProperty;
	}
	
	/**
	 * 创建组成成员属性
	 * @param property 信息类实体属性
	 * @return
	 */
	private static FormInfoMemberProperty createInfoMemberProperty( InfoProperty property ) {
		Assert.notNull( property );
		
		FormInfoMemberProperty result = new FormInfoMemberProperty( property.getFieldName(), property.getViewable() );
		result.setEditable( property.getEditable() );
		result.setNeed( property.getNeed() );
		result.setDefaultValue( property.getDefaultValue() );
		
		return result;
	}
	
	/**
	 * 创建信息类
	 * @param member
	 * @param properties
	 * @return
	 */
	public static DynaBean createBean( FormInfoMember member, FormInfoMemberProperty[] properties ) {
		Assert.notNull( member );
		Assert.hasText( member.getClassId() );
		Assert.notNull( properties );
		
		InfoClass clazz = InfoClassCache.getInfoClass( member.getClassId() );
		
		List<InfoProperty> ps = new ArrayList<InfoProperty>();
		
		for ( FormInfoMemberProperty property : properties) {
			InfoProperty p = clazz.getPropertyByName( property.getpName() );
			p.setViewable( property.isViewable() );
			p.setEditable( property.isEditable() );
			p.setDefaultValue( property.getDefaultValue() );
			p.setNeed( property.isNeed() );
			
			ps.add( p );
		}
		
		clazz.setProperties( ps );
		clazz.setName( member.getText() );
		
		return new DynaBean( clazz );
	}
	
	
	public static InfoClass reFillPropertyByRole(List<String> roles,String classId){
		InfoClass clazz = InfoClassCache.getInfoClass(classId).clone();
		if(roles == null || roles.size()!=1){
			return clazz;
		}
		String roleId = roles.get(0);
		IFormInfoMemberService formInfoMemberService = SpringHolder.getBean("baseFormInfoMemberService",IFormInfoMemberService.class);
		IFormInfoMemberPropertyService formInfoMemberPropertyService = SpringHolder.getBean("baseFormInfoMemberPropertyService",IFormInfoMemberPropertyService.class);
		
		FormInfoMember member = formInfoMemberService.getMember(roleId, classId, false);
		FormInfoMemberProperty[] properties = formInfoMemberPropertyService.getMemberProperties(member);
		
		List<InfoProperty> pList = new ArrayList<InfoProperty>();
		for (FormInfoMemberProperty p:properties) {
			InfoProperty property = clazz.getPropertyByName(p.getpName());
			if(property == null||!p.isCreated())continue;
			
			property = property.clone();
			if(!StringUtil.isEmpty(p.getDefaultValue())){
				property.setDefaultValue(p.getDefaultValue());
			}
			if(property.getEditable()){
				property.setEditable(p.isEditable());
			}
			if(property.getViewable()){
				property.setViewable(p.isViewable());
			}
			if(!property.getNeed()){
				property.setNeed(p.isNeed());
			}
			pList.add(property);
		}
		if(!pList.isEmpty())
			clazz.setProperties(pList);
		return clazz;
	
	}
	
}
