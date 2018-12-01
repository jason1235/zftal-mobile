package com.zfsoft.mobile.servlet.entity;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.zfsoft.util.base.StringUtil;

public class TodoTaskItemEntity {

	private String id;//待办事宜id
	private String title;//标题
	private String time;//日期
	private String createUser;//起草人
	private String type;//类型
	private String tablename;//学校名称
	
	public TodoTaskItemEntity(){
		
	}
	
	public TodoTaskItemEntity(String id,String title,String time,String createUser,String type,String tablename){
		this.id = StringUtil.isEmpty(id) ? "" : id;
		this.title = StringUtil.isEmpty(title) ? "" : title;
		this.time = StringUtil.isEmpty(time) ? "" : time;
		this.createUser = StringUtil.isEmpty(createUser) ? "" : createUser;
		this.type = StringUtil.isEmpty(type) ? "" : type;
		this.tablename = StringUtil.isEmpty(tablename) ? "" : tablename;
	}
	
	public String getId() {
		return id;
	}




	public void setId(String id) {
		this.id = id;
	}




	public String getTitle() {
		return title;
	}




	public void setTitle(String title) {
		this.title = title;
	}




	public String getTime() {
		return time;
	}




	public void setTime(String time) {
		this.time = time;
	}




	public String getCreateUser() {
		return createUser;
	}




	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}




	public String getType() {
		return type;
	}




	public void setType(String type) {
		this.type = type;
	}




	public String getTablename() {
		return tablename;
	}




	public void setTablename(String tablename) {
		this.tablename = tablename;
	}




	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
