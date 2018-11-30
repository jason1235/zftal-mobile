package com.zfsoft.mobile.sourceexchange.dao;

import java.util.List;

import com.zfsoft.mobile.sourceexchange.entity.Sourceconsumerhis;

/**
 *用户积分兑换历史记录dao接口
 *@author yangbilin
 *@createtime 2017-07-08 16:52
 */
public interface ISourceconsumerhisDao {
	
	/**
	 *积分购买--flag==0
	 *@return 订单编号 
	 */
	public void purchaseGoods(Sourceconsumerhis consuming);
	
	/**
	 *积分兑换
	 *@return 订单编号 
	 */
	public int exchangeGoods(Sourceconsumerhis consuming);
	
	/**
	 *积分兑换列表 --分页
	 */
	public int getPageCount(Sourceconsumerhis consuming);
	public List<Sourceconsumerhis> getPageList(Sourceconsumerhis consuming);
	/**
	 *积分兑换列表 --不分页flag==1
	 */
	public List<Sourceconsumerhis> getAllsourceconsumeList(String userName);
	
	/**
	 *积分兑换详情 
	 */
	public Sourceconsumerhis sourceconsumerhisDetail(String id);
	

}
