package com.zfsoft.mobile.market.service;

import java.util.List;
import java.util.Map;

import com.zfsoft.mobile.market.entity.Market;
import com.zfsoft.mobile.market.entity.MarketCampus;
import com.zfsoft.mobile.market.entity.MarketColl;
import com.zfsoft.mobile.market.entity.MarketComment;
import com.zfsoft.mobile.market.entity.MarketCommentQuery;
import com.zfsoft.mobile.market.entity.MarketQuery;
import com.zfsoft.mobile.market.entity.MarketType;
import com.zfsoft.mobile.market.entity.MarketTypeQuery;


public interface MarketService {

	List<MarketType> selectMarketType();

	List<MarketCampus> selectMarketCampus();

	void addMarket(Market market);

	List<Market> selectMarketList(MarketQuery marketQuery);

	Market getMarketById(String id);

	Integer getIsCollByUserId(String username,String marketId);

	void addMarketColl(MarketColl coll);

	void delMarketColl(MarketColl coll);

	void updateMarket(Market market);

	void delMarket(String id);

	void addMarketComment(MarketComment comment);

	List<MarketComment> getCommentList(String marketId, String commentId);

	String getUserNameById(String createUserId);

	List<Market> selectHtMatketList(MarketQuery query);

	List<MarketComment> gethtCommentList(MarketCommentQuery commentQuery);

	void delMarketComment(String id);

	List<MarketTypeQuery> queryMarketType(MarketTypeQuery marketTypeQuery);

	MarketType getMarketTypeById(String id);

	Map<String, Object> saveMarketType(MarketType marketType);

	Map<String, Object> htDelType(String id);

	List<MarketCampus> queryMarketCampus(MarketCampus marketCampus);

	MarketCampus getMarketCampusById(String id);

	Map<String, Object> saveMarketCampus(MarketCampus marketCampus);

	Map<String, Object> htDelCampus(String id);

}
