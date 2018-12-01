package com.zfsoft.mobile.canteenfood.service.impl;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.mobile.canteenfood.dao.ICanteenDao;
import com.zfsoft.mobile.canteenfood.entity.Canteen;
import com.zfsoft.mobile.canteenfood.service.ICanteenService;

public class CanteenServiceImpl implements ICanteenService{
	
	private ICanteenDao canteenDao;

	/**
	 * 获取所有商家类别--分页
	 * @param query
	 * @return PageList<Canteen>
	 * @author yangbilin
	 */
	@Override
	public PageList<Canteen> getPageList(Canteen query) {
		// TODO Auto-generated method stub
		Paginator paginator = new Paginator();
		PageList<Canteen> pageList = new PageList<Canteen>();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage(query.getToPage());
			paginator.setItems(canteenDao.getPageCount(query));
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
				List<Canteen> list = canteenDao.getPageList(query);
				
				pageList.addAll(list);
			}
		}
		return pageList;
	}
	
	public PageList<Canteen> getPageList2(Canteen query) {
		// TODO Auto-generated method stub
		Paginator paginator = new Paginator();
		PageList<Canteen> pageList = new PageList<Canteen>();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage(query.getToPage());
			paginator.setItems(canteenDao.getPageCount(query));
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
				List<Canteen> list = canteenDao.getPageList2(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	/**
	 *根据id获取商家信息
	 *@param canteeId
	 *@return Canteen
	 */
	@Override
	public Canteen findById(String canteenId) {
		Canteen canteen = new Canteen();
		System.out.println("impl1="+canteen.getPicPath());
		canteen = canteenDao.findById(canteenId);
		System.out.println("impl2="+canteen.getPicPath());
		return canteen;
	}

	/**
	 *增加商家信息
	 *@param query
	 *@return int 
	 */
	@Override
	public int insertCanteen(Canteen query) {
		// TODO Auto-generated method stub
		return canteenDao.insertCanteen(query);
	}

	/**
	 *更新商家
	 *@param query
	 *@return int 
	 */
	@Override
	public int updateCanteen(Canteen query) {
		// TODO Auto-generated method stub
		return canteenDao.updateCanteen(query);
	}

	/**
	 *删除商家信息
	 *@param query
	 *@return int 
	 */
	@Override
	public int deleteCanteen(Canteen query) {
		// TODO Auto-generated method stub
		return canteenDao.deleteCanteen(query);
	}

	/**
	 *删除商家信息
	 *@param canteeId
	 *@return int 
	 */
	@Override
	public int deleteCanteenById(String canteeId) {
		// TODO Auto-generated method stub
		return canteenDao.deleteCanteenById(canteeId);
	}

	/**
	 * 更新状态
	 * @param query
	 * @return int
	 */
	@Override
	public int control(Canteen query) {
		// TODO Auto-generated method stub
		return canteenDao.control(query);
	}

	/**
	 * 商家dao
	 * @return ICanteenDao
	 */
	public ICanteenDao getCanteenDao() {
		return canteenDao;
	}

	public void setCanteenDao(ICanteenDao canteenDao) {
		this.canteenDao = canteenDao;
	}

	/**
	 *商家名称列表--不分页
	 * @param query
	 *@return List<Canteen>
	 */
	@Override
	public List<Canteen> getCanteennameList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return canteenDao.getCanteennameList(params);
	}

	@Override
	public double getDistance(Map<String, Double> map) {
		// TODO Auto-generated method stub
		//return canteenDao.getDistance(map);
		return Double.parseDouble("");
	}
	
	

}
