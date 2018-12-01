package com.zfsoft.mobile.market.dao;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zfsoft.mobile.market.entity.Market;
import com.zfsoft.mobile.market.entity.MarketCampus;
import com.zfsoft.mobile.market.entity.MarketColl;
import com.zfsoft.mobile.market.entity.MarketComment;
import com.zfsoft.mobile.market.entity.MarketCommentQuery;
import com.zfsoft.mobile.market.entity.MarketQuery;
import com.zfsoft.mobile.market.entity.MarketType;
import com.zfsoft.mobile.market.entity.MarketTypeQuery;

public interface MarketDao {

	public List<MarketType> selectMarketType();

	public List<MarketCampus> selectMarketCampus();

	public void addMarket(Market market);

	public int getClassListCount(MarketQuery query);

	public List<Market> selectMarketList(MarketQuery query);

	public Market getMarketById(@Param(value = "id")String id);

	public Integer getIsCollByUserId(@Param("username")String username,@Param("marketId")String marketId);

	public void addMarketColl(MarketColl coll);

	public void delMarketColl(MarketColl coll);

	public void updateMarket(Market market);

	public void delMarket(@Param("id")String id);

	public void addMarketComment(MarketComment comment);

	public List<MarketComment> getCommentList(@Param("marketId")String marketId, @Param("commentId")String commentId);

	public String getUserNameById(@Param("userId")String userId);

	public int countHtMatketList(MarketQuery query);

	public List<Market> selectHtMatketList(MarketQuery query);

	public int countHtCommentList(MarketCommentQuery query);

	public List< MarketComment> selectHtCommentList(MarketCommentQuery query);

	public void delMarketComment(@Param("id")String id);

	public int countHtMatketTypeList(MarketTypeQuery marketTypeQuery);

	public List<MarketTypeQuery> selectHtMatketTypeList(MarketTypeQuery marketTypeQuery);

	public MarketType getMarketTypeById(String id);

	public void updateMarketType(MarketType marketType);

	public void saveMarketType(MarketType marketType);

	public void htDelType(String id);

	public int countHtMatketCampusList(MarketCampus marketCampus);

	public  List<MarketCampus> selectHtMatketCampusList(MarketCampus marketCampus);

	public MarketCampus getMarketCampusById(String id);

	public void updateMarketCampus(MarketCampus marketCampus);

	public void saveMarketCampus(MarketCampus marketCampus);

	public void htDelCampus(String id);

}
