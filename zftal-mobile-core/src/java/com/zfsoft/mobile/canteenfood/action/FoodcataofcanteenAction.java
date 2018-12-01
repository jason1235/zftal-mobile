package com.zfsoft.mobile.canteenfood.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.mobile.canteenfood.entity.Canteen;
import com.zfsoft.mobile.canteenfood.entity.Foodcataofcanteen;
import com.zfsoft.mobile.canteenfood.entity.Foodofcanteen;
import com.zfsoft.mobile.canteenfood.service.ICanteenService;
import com.zfsoft.mobile.canteenfood.service.IFoodcataofcanteenService;
import com.zfsoft.mobile.canteenfood.service.IFoodofcanteenService;

/**
 *食品类别action类
 *@author yangbilin
 *@createtime 2017-07-20 15:30  
 */
public class FoodcataofcanteenAction extends HrmAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String op;
	private Foodcataofcanteen query=new Foodcataofcanteen();
	private Foodcataofcanteen model = new Foodcataofcanteen();
	private PageList<Foodcataofcanteen> pageList;
	private IFoodcataofcanteenService foodcataService;
	private ICanteenService canteenService;
	private IFoodofcanteenService foodService;
	
	public String list(){
		pageList = foodcataService.getPageList(query);
		List<Canteen> canteenList=canteenService.getCanteennameList(new HashMap<String, Object>());
		this.getValueStack().set("canteenList", canteenList);
		return "list";
	}
	
	public String toAdd(){
		op = "add";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("isactive", "1");
		List<Canteen> canteenList= canteenService.getCanteennameList(params);
		this.getValueStack().set("canteenList", canteenList);
		return "edit";
	}
	
	public String toModify(){
		List<Foodcataofcanteen> list= foodcataService.getPageList(query);
		if(list!=null&&list.size()>0){
			op="modify";
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("isactive", "1");
			List<Canteen> canteenList= canteenService.getCanteennameList(params);
			this.getValueStack().set("canteenList", canteenList);
			model = list.get(0);
		}
		return "edit";
	}
	
	public String saveOrUpdate(){
		if(StringUtils.isNotBlank(query.getFoodcataId())){
			this.setSuccessMessage("成功修改数据！");
			foodcataService.updateFoodcata(query);
		}else{
			this.setSuccessMessage("成功保存数据！");
			foodcataService.insertFoodcata(query);
		}
		this.getValueStack().set("data", getMessage());
		return "data";
	}
	
	public String remove(){
		if(StringUtils.isNotBlank(query.getFoodcataId())){
			model=foodcataService.findById(query.getFoodcataId());
			if(model!=null){
				if("1".equals(model.getIsactive())){
					this.setErrorMessage("当前状态为出售，不可删除");
					this.getValueStack().set("data", this.getMessage());
					return "data";
				}
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("foodcataId", query.getFoodcataId());
				List<Foodofcanteen> foods=foodService.getFoodsList(map);
				if(foods!=null&&foods.size()>0){
					this.setErrorMessage("当前食品类别下存在食品，不可删除");
					this.getValueStack().set("data", this.getMessage());
					return "data";
				}
			}
			int flag=foodcataService.deleteFoodcata(query);
			if(flag>0){
				this.setSuccessMessage("删除成功！");
			}else{
				this.setErrorMessage("删除失败！");
			}
		}else{
			this.setErrorMessage("获取删除数据失败！");
		}
		this.getValueStack().set("data", this.getMessage());
		return "data";
	}
	
	public String control(){
		if(StringUtils.isNotBlank(query.getFoodcataId())){
			if("1".equals(query.getIsactive())){
				Canteen ct = canteenService.findById(query.getCanteenId());
				if(ct!=null){
					if("0".equals(ct.getIsactive())){
						this.setErrorMessage("当前类别不可启用，其所属商家处于停用状态，请先启用其所属商家");
						this.getValueStack().set("data", this.getMessage());
						return "data";
					}
				}
			}else if("0".equals(query.getIsactive())){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("foodcataId", query.getFoodcataId());
				map.put("isactive", "1");
				List<Foodofcanteen> foods=foodService.getFoodsList(map);
				if(foods!=null&&foods.size()>0){
					this.setErrorMessage("当前类别下存在启用食品，请先停用其食品状态");
					this.getValueStack().set("data", this.getMessage());
					return "data";
				}
			}
			int res=foodcataService.control(query);
			if(res>0){
				this.setSuccessMessage("操作成功");
			}else{
				this.setErrorMessage("操作失败");
			}
		}
		this.getValueStack().set("data", getMessage());
		return "data";
	}
	
	
	
	public ICanteenService getCanteenService() {
		return canteenService;
	}

	public void setCanteenService(ICanteenService canteenService) {
		this.canteenService = canteenService;
	}

	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	public Foodcataofcanteen getQuery() {
		return query;
	}
	public void setQuery(Foodcataofcanteen query) {
		this.query = query;
	}
	public Foodcataofcanteen getModel() {
		return model;
	}
	public void setModel(Foodcataofcanteen model) {
		this.model = model;
	}
	public PageList<Foodcataofcanteen> getPageList() {
		return pageList;
	}
	public void setPageList(PageList<Foodcataofcanteen> pageList) {
		this.pageList = pageList;
	}
	public IFoodcataofcanteenService getFoodcataService() {
		return foodcataService;
	}
	public void setFoodcataService(IFoodcataofcanteenService foodcataService) {
		this.foodcataService = foodcataService;
	}
	public IFoodofcanteenService getFoodService() {
		return foodService;
	}
	public void setFoodService(IFoodofcanteenService foodService) {
		this.foodService = foodService;
	}
}
