package com.zfsoft.mobile.canteenfood.action;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.mobile.canteenfood.entity.Foodofcanteen;
import com.zfsoft.mobile.canteenfood.entity.Orderofcanteen;
import com.zfsoft.mobile.canteenfood.service.IFoodofcanteenService;
import com.zfsoft.mobile.canteenfood.service.IOrderofcanteenService;

/**
 *订单action类
 *@author yangbilin
 *@createtime 2017-07-20 15:30  
 */
public class OrderofcanteenAction extends HrmAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PageList<Orderofcanteen> pageList = new PageList<Orderofcanteen>();
	private Orderofcanteen query=new Orderofcanteen();
	private IOrderofcanteenService orderService;
	private IFoodofcanteenService foodService;
	
	public String list(){
		SimpleDateFormat sfEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//pageList=orderService.getPageList(query);
		PageList<Orderofcanteen> pagelist1=orderService.getPageList(query);
		if(pagelist1!=null&&pagelist1.size()>0){
			for (Orderofcanteen orderofcanteen : pagelist1) {
				Map<String,Object> params = new HashMap<String, Object>();
				params.put("orderId", orderofcanteen.getOrderId());
				List<Foodofcanteen> foodslist = foodService.getFoodsListFororder(params);
				if(foodslist!=null&&foodslist.size()>0){
					orderofcanteen.setFoodlist(foodslist);
				}
				orderofcanteen.setCreatetimeStr(sfEnd.format(orderofcanteen.getCreatetime()));
				pageList.add(orderofcanteen);
			}
		}
		pageList.setPaginator(pagelist1.getPaginator());
		return "list";
	}
	
	public String control(){
		if(StringUtils.isNotBlank(query.getOrderId())){
			int flag=orderService.control(query);
			if(flag>0){
				this.setSuccessMessage("操作成功");
			}else{
				this.setErrorMessage("操作失败");
			}	
		}
		getValueStack().set("id",query.getOrderId());
		this.getValueStack().set("data", getMessage());
		return "data";
	}

	public PageList<Orderofcanteen> getPageList() {
		return pageList;
	}

	public void setPageList(PageList<Orderofcanteen> pageList) {
		this.pageList = pageList;
	}

	public Orderofcanteen getQuery() {
		return query;
	}

	public void setQuery(Orderofcanteen query) {
		this.query = query;
	}

	public IOrderofcanteenService getOrderService() {
		return orderService;
	}

	public void setOrderService(IOrderofcanteenService orderService) {
		this.orderService = orderService;
	}

	public IFoodofcanteenService getFoodService() {
		return foodService;
	}

	public void setFoodService(IFoodofcanteenService foodService) {
		this.foodService = foodService;
	}

}
