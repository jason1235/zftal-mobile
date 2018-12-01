package com.zfsoft.mobile.market.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.mobile.market.dao.MarketDao;
import com.zfsoft.mobile.market.entity.Market;
import com.zfsoft.mobile.market.entity.MarketCampus;
import com.zfsoft.mobile.market.entity.MarketColl;
import com.zfsoft.mobile.market.entity.MarketComment;
import com.zfsoft.mobile.market.entity.MarketCommentQuery;
import com.zfsoft.mobile.market.entity.MarketQuery;
import com.zfsoft.mobile.market.entity.MarketType;
import com.zfsoft.mobile.market.entity.MarketTypeQuery;
import com.zfsoft.mobile.market.service.MarketService;
import com.zfsoft.mobile.reportFix.entity.FixType;

public class MarketServiceImpl implements MarketService{

	private MarketDao marketDao;
	
	public MarketDao getMarketDao() {
		return marketDao;
	}

	public void setMarketDao(MarketDao marketDao) {
		this.marketDao = marketDao;
	}
	
	
	@Override
	public List<MarketType> selectMarketType() {
		return marketDao.selectMarketType();
	}

	
	@Override
	public List<MarketCampus> selectMarketCampus() {
		return marketDao.selectMarketCampus();
	}

	@Override
	public void addMarket(Market market) {
		marketDao.addMarket(market);
	}

	@Override
	public List<Market> selectMarketList(MarketQuery query) {
		PageList<Market> pageList = new PageList<Market>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(marketDao.getClassListCount(query));
			pageList.setPaginator(paginator);
			if((Integer)query.getToPage() > paginator.getPages()){
				return pageList;
			}
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<Market> list = marketDao.selectMarketList(query);
				pageList.addAll(list);
			} 
		}
		return pageList;
	}

	@Override
	public Market getMarketById(String id) {
		return marketDao.getMarketById(id);
	}

	@Override
	public Integer getIsCollByUserId(String username,String marketId) {
		return marketDao.getIsCollByUserId(username,marketId);
	}

	@Override
	public void addMarketColl(MarketColl coll) {
		marketDao.addMarketColl(coll);
	}

	@Override
	public void delMarketColl(MarketColl coll) {
		marketDao.delMarketColl(coll);
	}

	@Override
	public void updateMarket(Market market) {
		marketDao.updateMarket(market);
	}

	@Override
	public void delMarket(String id) {
		marketDao.delMarket(id);
	}

	@Override
	public void addMarketComment(MarketComment comment) {
		marketDao.addMarketComment(comment);
	}

	@Override
	public List<MarketComment> getCommentList(String marketId, String commentId) {
		return marketDao.getCommentList(marketId,commentId);
	}

	@Override
	public String getUserNameById(String createUserId) {
		return marketDao.getUserNameById(createUserId);
	}

	@Override
	public List<Market> selectHtMatketList(MarketQuery query) {
		PageList<Market> list = new PageList<Market>();
		Paginator paginator = new Paginator();
		if (query != null) {
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage(query.getToPage());
			paginator.setItems(marketDao.countHtMatketList(query));
			list.setPaginator(paginator);
			
			if (paginator.getBeginIndex() <= paginator.getItems()) {
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				list.addAll(marketDao.selectHtMatketList(query));
			}
		}
		return list;
	}

	@Override
	public List<MarketComment> gethtCommentList(MarketCommentQuery query) {
		PageList<MarketComment> list = new PageList<MarketComment>();
		Paginator paginator = new Paginator();
		if (query != null) {
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage(query.getToPage());
			paginator.setItems(marketDao.countHtCommentList(query));
			list.setPaginator(paginator);
			
			if (paginator.getBeginIndex() <= paginator.getItems()) {
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				list.addAll(marketDao.selectHtCommentList(query));
			}
		}
		return list;
	}

	@Override
	public void delMarketComment(String id) {
		marketDao.delMarketComment(id);
	}

	@Override
	public List<MarketTypeQuery> queryMarketType(MarketTypeQuery marketTypeQuery) {
		PageList<MarketTypeQuery> list = new PageList<MarketTypeQuery>();
		Paginator paginator = new Paginator();
		
		if (marketTypeQuery != null) {
			
			paginator.setItemsPerPage(marketTypeQuery.getPerPageSize());
			paginator.setPage(marketTypeQuery.getToPage());
			
			paginator.setItems(marketDao.countHtMatketTypeList(marketTypeQuery));
			
			list.setPaginator(paginator);
			
			if (paginator.getBeginIndex() <= paginator.getItems()) {
				
				marketTypeQuery.setStartRow(paginator.getBeginIndex());
				marketTypeQuery.setEndRow(paginator.getEndIndex());
				list.addAll(marketDao.selectHtMatketTypeList(marketTypeQuery));
			}
		}
		
		return list;
	}

	@Override
	public MarketType getMarketTypeById(String id) {
		return marketDao.getMarketTypeById(id);
	}

	@Override
	public Map<String, Object> saveMarketType(MarketType marketType) {
		Map<String, Object> map = new HashMap<String, Object>(); 
		boolean flag = false;
		String msg = "";
		
		if( marketType!=null ){
			
			if( !StringUtils.isEmpty(marketType.getName()) ){
				
				try {
					if( !StringUtils.isEmpty(marketType.getId()) ){
						//modify
						marketDao.updateMarketType(marketType);
					}else{
						//add
						marketDao.saveMarketType(marketType);
					}
					flag = true;
					msg = "保存成功!";
				} catch (Exception e) {
					e.printStackTrace();
					msg = "服务器错误!";
				}
				
			}else{
				msg = "类型名称不能为空!";
			}
			
		}else{
			msg = "参数错误!";
		}
		
		map.put("flag", flag);
		map.put("msg", msg);
		return map;
	}

	@Override
	public Map<String, Object> htDelType(String id) {
		Map<String, Object> map = new HashMap<String, Object>(); 
		boolean flag = false;
		String msg = "";
		
		try {
			if( !StringUtils.isEmpty(id) ){
				marketDao.htDelType(id);
				flag = true;
				msg = "删除成功!";
			}else{
				msg = "请选择一条记录!";
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = "服务器错误!";
		}
		
		map.put("flag", flag);
		map.put("msg", msg);
		return map;
	}

	@Override
	public List<MarketCampus> queryMarketCampus(MarketCampus marketCampus) {
		PageList<MarketCampus> list = new PageList<MarketCampus>();
		Paginator paginator = new Paginator();
		
		if (marketCampus != null) {
			
			paginator.setItemsPerPage(marketCampus.getPerPageSize());
			paginator.setPage(marketCampus.getToPage());
			
			paginator.setItems(marketDao.countHtMatketCampusList(marketCampus));
			
			list.setPaginator(paginator);
			
			if (paginator.getBeginIndex() <= paginator.getItems()) {
				
				marketCampus.setStartRow(paginator.getBeginIndex());
				marketCampus.setEndRow(paginator.getEndIndex());
				list.addAll(marketDao.selectHtMatketCampusList(marketCampus));
			}
		}
		return list;
	}

	@Override
	public MarketCampus getMarketCampusById(String id) {
		return  marketDao.getMarketCampusById(id);
	}

	@Override
	public Map<String, Object> saveMarketCampus(MarketCampus marketCampus) {
		Map<String, Object> map = new HashMap<String, Object>(); 
		boolean flag = false;
		String msg = "";
		
		if( marketCampus!=null ){
			
			if( !StringUtils.isEmpty(marketCampus.getName()) ){
				
				try {
					if( !StringUtils.isEmpty(marketCampus.getId()) ){
						//modify
						marketDao.updateMarketCampus(marketCampus);
					}else{
						//add
						marketDao.saveMarketCampus(marketCampus);
					}
					flag = true;
					msg = "保存成功!";
				} catch (Exception e) {
					e.printStackTrace();
					msg = "服务器错误!";
				}
				
			}else{
				msg = "校区名称不能为空!";
			}
			
		}else{
			msg = "参数错误!";
		}
		
		map.put("flag", flag);
		map.put("msg", msg);
		return map;
	}

	@Override
	public Map<String, Object> htDelCampus(String id) {
		Map<String, Object> map = new HashMap<String, Object>(); 
		boolean flag = false;
		String msg = "";
		
		try {
			if( !StringUtils.isEmpty(id) ){
				marketDao.htDelCampus(id);
				flag = true;
				msg = "删除成功!";
			}else{
				msg = "请选择一条记录!";
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = "服务器错误!";
		}
		
		map.put("flag", flag);
		map.put("msg", msg);
		return map;
	}
	
}
