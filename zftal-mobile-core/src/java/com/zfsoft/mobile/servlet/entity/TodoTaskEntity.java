package com.zfsoft.mobile.servlet.entity;

import java.util.List;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class TodoTaskEntity {

	private List<TodoTaskItemEntity> todoTaskList;
	
	private int sum;//总数
	
	

	public List<TodoTaskItemEntity> getTodoTaskList() {
		return todoTaskList;
	}



	public void setTodoTaskList(List<TodoTaskItemEntity> todoTaskList) {
		this.todoTaskList = todoTaskList;
	}



	public int getSum() {
		return sum;
	}



	public void setSum(int sum) {
		this.sum = sum;
	}



	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
	
	
}
