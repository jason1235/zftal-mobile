package com.firstLink.service;



import com.zfsoft.dao.entities.User;
import com.zfsoft.dao.page.PageList;


public interface IUserService {

	public PageList<User> getList(User query);

	public void save(User query);

	public void delete(User query);

	public void update(User query);
	
	public void updateBySfqy(User query);
	
	public void updatePassword(User query);
}
