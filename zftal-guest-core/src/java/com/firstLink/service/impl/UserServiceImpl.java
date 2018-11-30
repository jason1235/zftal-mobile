package com.firstLink.service.impl;

import java.util.List;


import com.firstLink.dao.IUserDao;
import com.firstLink.service.IUserService;
import com.zfsoft.dao.entities.User;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.util.encrypt.Encrypt;

public class UserServiceImpl implements IUserService {
	IUserDao userDao;
	public PageList<User> getList(User query) {
		PageList<User> pageList = new PageList<User>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(userDao.getListCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<User> list = userDao.getList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}
	public IUserDao getuserDao() {
		return userDao;
	}
	public void setuserDao(IUserDao userDao) {
		this.userDao = userDao;
	}
	public void save(User query) {
		query.setPassword(Encrypt.encrypt(query.getPassword()));
		query.setSfqy("1");
		userDao.save(query);
	}
	public void delete(User query) {
		userDao.delete(query);
	}
	public void update(User query) {
		userDao.update(query);
	}
	@Override
	public void updateBySfqy(User query) {
		userDao.updateBySfqy(query);
	}
	@Override
	public void updatePassword(User query) {
		query.setPassword(Encrypt.encrypt("888888"));
		userDao.updatePassword(query);
	}
	 
    
}
