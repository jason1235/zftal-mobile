package com.zfsoft.service.base;

import java.util.List;


public interface BaseAnService<T> {
	public void add(T entity);
	public void removeById(T entity);
	public void modify(T entity);
	public <Q> T getById(Q query);
	public <Q> List<T> getList(Q query);
}
