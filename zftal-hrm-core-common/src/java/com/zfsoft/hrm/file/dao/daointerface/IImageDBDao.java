package com.zfsoft.hrm.file.dao.daointerface;

import com.zfsoft.hrm.file.entity.ImageDB;

public interface IImageDBDao {
	
	public void insert(ImageDB fileDB);
	public void delete(ImageDB fileDB);
	public void update(ImageDB fileDB);
	
	public ImageDB findById(String fileDBId);
}
