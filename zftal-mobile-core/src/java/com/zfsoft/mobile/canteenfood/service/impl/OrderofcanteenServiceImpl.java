package com.zfsoft.mobile.canteenfood.service.impl;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.mobile.canteenfood.dao.IOrderofcanteenDao;
import com.zfsoft.mobile.canteenfood.entity.Orderofcanteen;
import com.zfsoft.mobile.canteenfood.service.IOrderofcanteenService;

public class OrderofcanteenServiceImpl implements IOrderofcanteenService{

	private IOrderofcanteenDao orderDao;
	
	@Override
	public int placeOrder(Orderofcanteen query) {
		// TODO Auto-generated method stub
		return orderDao.placeOrder(query);
	}

	public IOrderofcanteenDao getOrderDao() {
		return orderDao;
	}

	public void setOrderDao(IOrderofcanteenDao orderDao) {
		this.orderDao = orderDao;
	}

	/**
	 *根据用户userid获取该用户的所有订单列表--分页
	 *@param query
	 *@return PageList<Orderofcanteen>
	 *@author yangbilin 
	 */
	@Override
	public PageList<Orderofcanteen> getPageList(Orderofcanteen query) {
		// TODO Auto-generated method stub
		Paginator paginator = new Paginator();
		PageList<Orderofcanteen> pagelist = new PageList<Orderofcanteen>();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage(query.getToPage());
			paginator.setItems(orderDao.getPageCount(query));
			pagelist.setPaginator(paginator);
			//当前页码大于总页数，返回列表
			if((Integer)query.getToPage() > paginator.getPages()){
				return pagelist;
			}
			/**
			 * 当前页的起始项序号小于总项数量
			 * 设置当前页的起始和结束序号并返回页面
			 */
			if (paginator.getBeginIndex() <= paginator.getItems()) {
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<Orderofcanteen> list=orderDao.getPageList(query);
				pagelist.addAll(list);
			}
		}
		return pagelist;
	}

	/**
	 *根据订单编号orderid获取订单信息
	 *@param orderId
	 *@return Orderofcanteen
	 *@author yangbilin 
	 */
	@Override
	public Orderofcanteen findById(String orderId) {
		// TODO Auto-generated method stub
		return orderDao.findById(orderId);
	}

	@Override
	public int control(Orderofcanteen query) {
		// TODO Auto-generated method stub
		return orderDao.control(query);
	}
	
	

}
