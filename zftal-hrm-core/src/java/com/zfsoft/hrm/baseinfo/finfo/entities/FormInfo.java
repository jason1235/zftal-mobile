package com.zfsoft.hrm.baseinfo.finfo.entities;

import java.io.Serializable;

/**
 * 信息维护描述信息
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-22
 * @version V1.0.0
 */
public class FormInfo implements Serializable {
	
	private static final long serialVersionUID = 4216377652449822402L;

	private String type;			//信息维护类型
	private String name;			//描写信息维护的名字(即：成员组组名)
	private String text;			//描述信息维护的文本信息
	private String help;			//填写说明
	private String comment;			//注释
	
//	private FInfoMember[] members;	//组成信息维护的成员

	/**
	 * 返回信息维护类型
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置信息维护类型
	 * @param type 信息维护类型
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * 返回描写信息维护的名字(即：成员组组名)
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置描写信息维护的名字(即：成员组组名)
	 * @param name 描写信息维护的名字(即：成员组组名)
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 返回描述信息维护的文本信息
	 */
	public String getText() {
		return text;
	}

	/**
	 * 设置描述信息维护的文本信息
	 * @param text 描述信息维护的文本信息
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * 返回填写说明
	 */
	public String getHelp() {
		return help;
	}

	/**
	 * 设置填写说明
	 * @param help 填写说明
	 */
	public void setHelp(String help) {
		this.help = help;
	}

	/**
	 * 返回注释
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * 设置注释
	 * @param comment 注释
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

//	/**
//	 * 返回组成信息维护的成员
//	 */
//	public FInfoMember[] getMembers() {
//		return members;
//	}
//
//	/**
//	 * 设置组成信息维护的成员
//	 * @param members 组成信息维护的成员
//	 */
//	public void setMembers(FInfoMember[] members) {
//		this.members = members;
//	}

}
