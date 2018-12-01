package com.zfsoft.hrm.baseinfo.finfo.query;

import java.io.Serializable;

import com.zfsoft.hrm.baseinfo.finfo.entities.FormInfoMember;
import com.zfsoft.hrm.baseinfo.finfo.entities.FormInfoMemberProperty;

/**
 * {@link FormInfoMemberPropertyQuery}查询条件实体
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-24
 * @version V1.0.0
 */
public class FormInfoMemberPropertyQuery implements Serializable {
	
	private static final long serialVersionUID = 8517324819271093776L;

	private String classId;		//成员属性所属成员使用的信息类ID
	
	private String name;		//成员属性所属成员所属的成员组名称
	
	private String pName;		//成员属性名称
	
//	/**
//	 * （空）构造函数
//	 */
//	public FormInfoMemberPropertyQuery() {
//		//do nothing;
//	}
//	
//	/**
//	 * 构造函数
//	 * @param member 属性所属组成成员
//	 */
//	public FormInfoMemberPropertyQuery( FormInfoMember member ) {
//		if( member != null ) {
//			name = member.getName();
//			classId = member.getClassId();
//		}
//	}
//	
//	/**
//	 * 构造函数
//	 * @param property 组成成员属性
//	 */
//	public FormInfoMemberPropertyQuery( FormInfoMemberProperty property ) {
//		if( property != null ) {
//			pName = property.getpName();
//
//			if( property.getMember() != null ) {
//				name = property.getMember().getName();
//				classId = property.getMember().getClassId();
//			}
//		}
//		
//	}

	/**
	 * 返回成员属性所属成员使用的信息类ID
	 */
	public String getClassId() {
		return classId;
	}

	/**
	 * 设置成员属性所属成员使用的信息类ID
	 * @param classId 成员属性所属成员使用的信息类ID
	 */
	public void setClassId(String classId) {
		this.classId = classId;
	}

	/**
	 * 返回成员属性所属成员所属的成员组名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置成员属性所属成员所属的成员组名称
	 * @param name 成员属性所属成员所属的成员组名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 返回成员属性名称
	 */
	public String getpName() {
		return pName;
	}

	/**
	 * 设置成员属性名称
	 * @param pName 成员属性名称
	 */
	public void setpName(String pName) {
		this.pName = pName;
	}
	
}
