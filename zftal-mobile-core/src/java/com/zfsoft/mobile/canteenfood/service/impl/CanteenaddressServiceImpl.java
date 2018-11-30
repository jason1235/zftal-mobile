package com.zfsoft.mobile.canteenfood.service.impl;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.mobile.canteenfood.dao.ICanteenaddressDao;
import com.zfsoft.mobile.canteenfood.entity.Canteenaddress;
import com.zfsoft.mobile.canteenfood.service.ICanteenaddressService;

public class CanteenaddressServiceImpl implements ICanteenaddressService{

	
	private ICanteenaddressDao addressDao;
	
	@Override
	public PageList<Canteenaddress> getPageList(Canteenaddress query) {
		Paginator paginator = new Paginator();
		PageList<Canteenaddress> pagelist = new PageList<Canteenaddress>();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage(query.getToPage());
			paginator.setItems(addressDao.getPageCount(query));
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
				List<Canteenaddress> list = addressDao.getPageList(query);
				pagelist.addAll(list);
			}
		}
		return pagelist;
	}

	/**
	 *根据id获取地址具体信息
	 *@param addressId
	 *@return Canteenaddress
	 */
	@Override
	public Canteenaddress findById(String addressId) {
		// TODO Auto-generated method stub
		return addressDao.findById(addressId);
	}

	@Override
	public int insertAddress(Canteenaddress query) {
		// TODO Auto-generated method stub
		return addressDao.insertAddress(query);
	}

	@Override
	public int updateAddress(Canteenaddress query) {
		// TODO Auto-generated method stub
		return addressDao.updateAddress(query);
	}

	@Override
	public int deleteAddress(Canteenaddress query) {
		// TODO Auto-generated method stub
		return addressDao.deleteAddress(query);
	}

	public ICanteenaddressDao getAddressDao() {
		return addressDao;
	}

	public void setAddressDao(ICanteenaddressDao addressDao) {
		this.addressDao = addressDao;
	}
	
	

}
