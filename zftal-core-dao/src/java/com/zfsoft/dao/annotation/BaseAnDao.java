package com.zfsoft.dao.annotation;

import java.util.List;

public interface BaseAnDao <T>{
	public void insert(T entity);
	public void update(T entity);
	public void delete(T entity);
	public <Q> List<T> findList(Q query);
	public <Q> T findById(Q query);
}
