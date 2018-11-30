package com.zfsoft.mobile.canteenfood.service;

import java.util.List;

import com.zfsoft.mobile.canteenfood.entity.Foodorder;

public interface IFoodorderService {

	
	public List<String> getFoodIds(Foodorder query);
	public void insertOrders(Foodorder query);
}
