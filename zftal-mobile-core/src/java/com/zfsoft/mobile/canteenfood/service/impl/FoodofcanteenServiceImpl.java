package com.zfsoft.mobile.canteenfood.service.impl;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.mobile.canteenfood.dao.IFoodofcanteenDao;
import com.zfsoft.mobile.canteenfood.entity.Foodofcanteen;
import com.zfsoft.mobile.canteenfood.service.IFoodofcanteenService;

public class FoodofcanteenServiceImpl implements IFoodofcanteenService{

	private IFoodofcanteenDao foodDao;
	
	/**
	 * 根据食品类别foodcataid获取在售的食品
	 * @param params
	 * @return List<Foodofcanteen>
	 * @author yangbilin
	 */
	@Override
	public List<Foodofcanteen> getFoodsList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return foodDao.getFoodsList(params);
	}

	/**
	 * 根据订单orderid获得购买的食品列表信息
	 * @param params
	 * @return List<Foodofcanteen>
	 * @author yangbilin
	 */
	@Override
	public List<Foodofcanteen> getFoodsListFororder(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return foodDao.getFoodsListFororder(params);
	}

	/**
	 * 更新食品库存
	 * @param query
	 * @return int
	 * @author yangbilin
	 */
	@Override
	public int updateStorage(Foodofcanteen query) {
		// TODO Auto-generated method stub
		return foodDao.updateStorage(query);
	}

	/**
	 * 根据食品编号foodid食品信息
	 * @param foodId
	 * @return Foodofcanteen
	 * @author yangbilin
	 */
	@Override
	public Foodofcanteen findById(String foodId) {
		// TODO Auto-generated method stub
		return foodDao.findById(foodId);
	}

	/**
	 *根据食品类别遍历获取食品启用数量
	 *@param params
	 *@return  List<Foodofcanteen>
	 *@author yangbilin
	 */
	@Override
	public int getFoodsListCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return foodDao.getFoodsListCount(params);
	}

	/**
	 *商家食品列表--分页
	 * @param query
	 *@return PageList<Foodofcanteen>
	 */
	@Override
	public PageList<Foodofcanteen> getPageList(Foodofcanteen query) {
		// TODO Auto-generated method stub
		Paginator paginator = new Paginator();
		PageList<Foodofcanteen> pageList = new PageList<Foodofcanteen>();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage(query.getToPage());
			paginator.setItems(foodDao.getPageCount(query));
			pageList.setPaginator(paginator);
			//当前页码大于总页数，返回列表
			if((Integer)query.getToPage() > paginator.getPages()){
				return pageList;
			}
			/**
			 * 当前页的起始项序号小于总项数量
			 * 设置当前页的起始和结束序号并返回页面
			 */
			if (paginator.getBeginIndex() <= paginator.getItems()) {
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<Foodofcanteen> list = foodDao.getPageList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	/**
	 *增加商家食品信息
	 *@param query
	 *@return int 
	 */
	@Override
	public int insertFood(Foodofcanteen query) {
		// TODO Auto-generated method stub
		return foodDao.insertFood(query);
	}

	/**
	 *更新商家食品
	 *@param query
	 *@return int 
	 */
	@Override
	public int updateFood(Foodofcanteen query) {
		// TODO Auto-generated method stub
		return foodDao.updateFood(query);
	}

	/**
	 *删除商家食品
	 *@param query
	 *@return int 
	 */
	@Override
	public int deleteFood(Foodofcanteen query) {
		// TODO Auto-generated method stub
		return foodDao.deleteFood(query);
	}

	/**
	 *删除商家食品
	 *@param foodId
	 *@return int 
	 */
	@Override
	public int deleteFoodById(String foodId) {
		// TODO Auto-generated method stub
		return foodDao.deleteFoodById(foodId);
	}

	/**
	 * 更新状态
	 * @param query
	 * @return int
	 */
	@Override
	public int control(Foodofcanteen query) {
		// TODO Auto-generated method stub
		return foodDao.control(query);
	}
	
	public IFoodofcanteenDao getFoodDao() {
		return foodDao;
	}

	public void setFoodDao(IFoodofcanteenDao foodDao) {
		this.foodDao = foodDao;
	}

}
