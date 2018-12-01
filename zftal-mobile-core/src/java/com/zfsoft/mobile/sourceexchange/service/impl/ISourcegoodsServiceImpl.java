package com.zfsoft.mobile.sourceexchange.service.impl;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.mobile.sourceexchange.dao.ISourcegoodsDao;
import com.zfsoft.mobile.sourceexchange.entity.Sourcegoods;
import com.zfsoft.mobile.sourceexchange.query.SourcegoodsQuery;
import com.zfsoft.mobile.sourceexchange.service.ISourcegoodsService;

public class ISourcegoodsServiceImpl implements ISourcegoodsService{
	
	private ISourcegoodsDao sourceGoodsDao;

	@Override
	public PageList<Sourcegoods> getPageList(SourcegoodsQuery query) {
		Paginator paginator = new Paginator();
		PageList<Sourcegoods> pagelist = new PageList<Sourcegoods>();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			//当前页数
			paginator.setPage(query.getToPage());
			paginator.setItems(sourceGoodsDao.getPageCount(query));
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
				List<Sourcegoods> list = sourceGoodsDao.getPageList(query);
				pagelist.addAll(list);
			}
		}
		return pagelist;
	}

	@Override
	public List<Sourcegoods> getAllgoodsList(SourcegoodsQuery query) {
		// TODO Auto-generated method stub
		return sourceGoodsDao.getAllgoodsList(query);
	}

	@Override
	public void insert(SourcegoodsQuery query) {
		// TODO Auto-generated method stub
		sourceGoodsDao.insert(query);
	}

	@Override
	public void update(SourcegoodsQuery query) {
		// TODO Auto-generated method stub
		sourceGoodsDao.update(query);
	}

	@Override
	public void deleteGoods(String goodsid) {
		// TODO Auto-generated method stub
		sourceGoodsDao.deleteGoods(goodsid);
	}

	@Override
	public Sourcegoods findById(SourcegoodsQuery query) {
		// TODO Auto-generated method stub
		return sourceGoodsDao.findById(query);
	}

	@Override
	public int goodsControl(SourcegoodsQuery query) {
		// TODO Auto-generated method stub
		return sourceGoodsDao.goodsControl(query);
	}

	@Override
	public void updateStorage(SourcegoodsQuery query) {
		sourceGoodsDao.updateStorage(query);
	}

	public ISourcegoodsDao getSourceGoodsDao() {
		return sourceGoodsDao;
	}

	public void setSourceGoodsDao(ISourcegoodsDao sourceGoodsDao) {
		this.sourceGoodsDao = sourceGoodsDao;
	}

}
