package com.firstLink.dao;

import java.util.List;

import com.zfsoft.dao.entities.User;


public interface IUserDao {

	public int getListCount(User query);
	
	public List<User> getList(User query);

	public void save(User query);

	public void delete(User query);

	public void update(User query);
	
	public void updateBySfqy(User query);
	
	public void updatePassword(User query);
}
