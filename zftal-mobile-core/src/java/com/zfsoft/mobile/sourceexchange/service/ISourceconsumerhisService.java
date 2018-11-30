package com.zfsoft.mobile.sourceexchange.service;


import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.sourceexchange.entity.Sourceconsumerhis;

public interface ISourceconsumerhisService {
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
	public PageList<Sourceconsumerhis> getPageList(Sourceconsumerhis consuming);

}
