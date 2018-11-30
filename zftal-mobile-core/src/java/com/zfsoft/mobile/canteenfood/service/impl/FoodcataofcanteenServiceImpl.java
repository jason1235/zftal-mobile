package com.zfsoft.mobile.canteenfood.service.impl;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.mobile.canteenfood.dao.IFoodcataofcanteenDao;
import com.zfsoft.mobile.canteenfood.entity.Foodcataofcanteen;
import com.zfsoft.mobile.canteenfood.service.IFoodcataofcanteenService;

public class FoodcataofcanteenServiceImpl implements IFoodcataofcanteenService{

	private IFoodcataofcanteenDao foodcataDao;

	/**
	 *根据食品类别id获取该类别的信息
	 * @param foodcataId
	 * @return Foodcataofcanteen
	 */
	@Override
	public Foodcataofcanteen findById(String foodcataId) {
		// TODO Auto-generated method stub
		return foodcataDao.findById(foodcataId);
	}

	/**
	 *获取该商家下所有的食品类别数量
	 * @param map
	 * @return int
	 */
	@Override
	public List<Foodcataofcanteen>  getFoodcataList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return foodcataDao.getFoodcataList(map);
	}

	/**
	 *商家食品种类列表--分页
	 * @param query
	 *@return PageList<Foodcataofcanteen>
	 */
	@Override
	public PageList<Foodcataofcanteen> getPageList(Foodcataofcanteen query) {
		Paginator paginator = new Paginator();
		PageList<Foodcataofcanteen> pageList = new PageList<Foodcataofcanteen>();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage(query.getToPage());
			paginator.setItems(foodcataDao.getPageCount(query));
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
				List<Foodcataofcanteen> list = foodcataDao.getPageList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	/**
	 * 更新状态
	 * @param query
	 * @return int
	 */
	@Override
	public int control(Foodcataofcanteen query) {
		// TODO Auto-generated method stub
		return foodcataDao.control(query);
	}

	/**
	 *删除商家食品种类
	 *@param query
	 *@return int 
	 */
	@Override
	public int deleteFoodcata(Foodcataofcanteen query) {
		// TODO Auto-generated method stub
		return foodcataDao.deleteFoodcata(query);
	}

	/**
	 *增加商家食品种类信息
	 *@param query
	 *@return int 
	 */
	@Override
	public int insertFoodcata(Foodcataofcanteen query) {
		// TODO Auto-generated method stub
		return foodcataDao.insertFoodcata(query);
	}

	/**
	 *更新商家食品种类
	 *@param query
	 *@return int 
	 */
	@Override
	public int updateFoodcata(Foodcataofcanteen query) {
		// TODO Auto-generated method stub
		return foodcataDao.updateFoodcata(query);
	}
	

	public IFoodcataofcanteenDao getFoodcataDao() {
		return foodcataDao;
	}

	public void setFoodcataDao(IFoodcataofcanteenDao foodcataDao) {
		this.foodcataDao = foodcataDao;
	}

	@Override
	public List<String> getFoodcataIdList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return foodcataDao.getFoodcataIdList(map);
	}
	
}
