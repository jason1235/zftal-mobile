package com.zfsoft.mobile.canteenfood.service.impl;

import java.util.List;

import com.zfsoft.mobile.canteenfood.dao.IFoodorderDao;
import com.zfsoft.mobile.canteenfood.entity.Foodorder;
import com.zfsoft.mobile.canteenfood.service.IFoodorderService;

public class FoodorderServiceImpl implements IFoodorderService{

	private IFoodorderDao foodorderDao;
	
	public void insertOrders(Foodorder query){
		foodorderDao.insertOrders(query);
	}

	public IFoodorderDao getFoodorderDao() {
		return foodorderDao;
	}

	public void setFoodorderDao(IFoodorderDao foodorderDao) {
		this.foodorderDao = foodorderDao;
	}

	@Override
	public List<String> getFoodIds(Foodorder query) {
		// TODO Auto-generated method stub
		return foodorderDao.getFoodIds(query);
	}
	
	
}
