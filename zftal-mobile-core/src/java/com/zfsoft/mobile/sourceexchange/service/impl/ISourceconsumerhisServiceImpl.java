package com.zfsoft.mobile.sourceexchange.service.impl;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.mobile.sourceexchange.dao.ISourceconsumerhisDao;
import com.zfsoft.mobile.sourceexchange.entity.Sourceconsumerhis;
import com.zfsoft.mobile.sourceexchange.service.ISourceconsumerhisService;

public class ISourceconsumerhisServiceImpl implements ISourceconsumerhisService{

	private ISourceconsumerhisDao consumingHisDao;
	
	@Override
	public void purchaseGoods(Sourceconsumerhis consuming) {
		// TODO Auto-generated method stub
		consumingHisDao.purchaseGoods(consuming);
	}

	@Override
	public int exchangeGoods(Sourceconsumerhis consuming) {
		// TODO Auto-generated method stub
		return consumingHisDao.exchangeGoods(consuming);
	}

	@Override
	public PageList<Sourceconsumerhis> getPageList(Sourceconsumerhis query) {
		Paginator paginator = new Paginator();
		PageList<Sourceconsumerhis> pageList = new PageList<Sourceconsumerhis>();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage(query.getToPage());
			paginator.setItems(consumingHisDao.getPageCount(query));
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
				List<Sourceconsumerhis> list = consumingHisDao.getPageList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	public ISourceconsumerhisDao getConsumingHisDao() {
		return consumingHisDao;
	}

	public void setConsumingHisDao(ISourceconsumerhisDao consumingHisDao) {
		this.consumingHisDao = consumingHisDao;
	}

}
