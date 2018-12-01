package com.zfsoft.mobile.canteenfood.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.file.util.ImageDBUtil;
import com.zfsoft.mobile.canteenfood.entity.Canteen;
import com.zfsoft.mobile.canteenfood.entity.Foodcataofcanteen;
import com.zfsoft.mobile.canteenfood.entity.Foodofcanteen;
import com.zfsoft.mobile.canteenfood.service.ICanteenService;
import com.zfsoft.mobile.canteenfood.service.IFoodcataofcanteenService;
import com.zfsoft.mobile.canteenfood.service.IFoodofcanteenService;
import com.zfsoft.mobile.common.service.IMobileCommonService;
import com.zfsoft.mobile.common.utils.ImageTagHtml;
import com.zfsoft.util.base.StringUtil;

/**
 *食品action类
 *@author yangbilin
 *@createtime 2017-07-20 15:30  
 */
public class FoodofcanteenAction extends HrmAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String op;
	private Foodofcanteen query = new Foodofcanteen();
	private Foodofcanteen model=  new Foodofcanteen();
	private PageList<Foodofcanteen> pageList;
	private IFoodofcanteenService foodService;
	private IMobileCommonService mobileCommonService;
	private IFoodcataofcanteenService foodcataService;
	private ICanteenService canteenService;
	
	private String food;
	
	public String list(){
		pageList = foodService.getPageList(query);
		return "list";
	}
	
	public String toAdd(){
		this.getValueStack().set("imageHtml", ImageTagHtml.getImageHtml("food", Type.IMAGE, 1024, 2, 1, null, true));
		op = "add";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("isactive", "1");
		List<Foodcataofcanteen> foodcataList = foodcataService.getFoodcataList(params);
		for(int i=0;i<foodcataList.size();i++){
			Foodcataofcanteen foc = foodcataList.get(i);
			if(foc!=null){
				String cId = foc.getCanteenId();
				Canteen ct = canteenService.findById(cId);
				if(ct!=null){
					foodcataList.get(i).setFoodcataName(foodcataList.get(i).getFoodcataName()+"-"+(ct.getCanteenName()==null? "":ct.getCanteenName()));
				}
			}
		}
		this.getValueStack().set("foodcataList", foodcataList);
		return "edit";
	}
	public String image() {
		this.getValueStack().set("data", ImageTagHtml.getImageHtml("food", Type.IMAGE, 1024, 2, 1, null, true));
		return "data";
	}
	
	public String toModify(){
		List<Foodofcanteen> list = foodService.getPageList(query);
		if(list!=null&&list.size()>0){
			op="modify";
			model = list.get(0);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("isactive", "1");
			List<Foodcataofcanteen> foodcataList = foodcataService.getFoodcataList(params);
			this.getValueStack().set("foodcataList", foodcataList);
			if(StringUtil.isEmpty(model.getPicId())){
				getValueStack().set("imageHtmlArr", "");
			}else{
				String imageHtmlArr=ImageTagHtml.getImageHtml("food", Type.IMAGE, 1024, 2, 1, model.getPicId(), true);
				getValueStack().set("imageHtmlArr", imageHtmlArr);
			}
		}
		return "edit";
	}
	
	public String saveOrUpdate(){
		if(food!=null){
			query.setPicId(food);
			query.setPicPath(mobileCommonService.getGoodsImagePath(food,"canteenfood"));
		}
		if(StringUtils.isNotBlank(query.getFoodId())){
			this.setSuccessMessage("成功修改数据！");
			foodService.updateFood(query);
		}else{
			this.setSuccessMessage("成功保存数据！");
			foodService.insertFood(query);
		}
		this.getValueStack().set("data", getMessage());
		return "data";
	}
	
	public String remove(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/");
		if(StringUtils.isNotBlank(query.getFoodId())){
			model = foodService.findById(query.getFoodId());
			if(model!=null){
				if("1".equals(model.getIsactive())){
					this.setErrorMessage("当前状态为启用状态，不可删除");
					this.getValueStack().set("data", this.getMessage());
					return "data";
				}
				if(StringUtils.isNotBlank(model.getPicId())){
					String picpath=model.getPicPath();
					ImageDBUtil.deleteFileToDB(model.getPicId(),realPath+picpath);
				}
			}
			int flag = foodService.deleteFood(query);
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
		if(StringUtils.isNotBlank(query.getFoodId())){
			if("1".equals(query.getIsactive())){
				Foodcataofcanteen foodcata = foodcataService.findById(query.getFoodCataid());
				if(foodcata!=null){
					if("0".equals(foodcata.getIsactive())){
						this.setErrorMessage("当前食品不可启用，其所属食品类别处于停用状态");
						this.getValueStack().set("data", this.getMessage());
						return "data";
					}
					Canteen cn = canteenService.findById(foodcata.getCanteenId());
					if(cn!=null&&"0".equals(cn.getIsactive())){
						this.setErrorMessage("当前食品不可启用，其所属商家处于停用状态");
						this.getValueStack().set("data", this.getMessage());
						return "data";
					}
				}
			}
			int res = foodService.control(query);
			if(res>0){
				this.setSuccessMessage("操作成功");
			}else{
				this.setErrorMessage("操作失败");
			}
		}
		this.getValueStack().set("data", getMessage());
		return "data";
	}
	
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	public Foodofcanteen getQuery() {
		return query;
	}
	public void setQuery(Foodofcanteen query) {
		this.query = query;
	}
	public Foodofcanteen getModel() {
		return model;
	}
	public void setModel(Foodofcanteen model) {
		this.model = model;
	}
	public PageList<Foodofcanteen> getPageList() {
		return pageList;
	}
	public void setPageList(PageList<Foodofcanteen> pageList) {
		this.pageList = pageList;
	}
	public IFoodofcanteenService getFoodService() {
		return foodService;
	}
	public void setFoodService(IFoodofcanteenService foodService) {
		this.foodService = foodService;
	}
	public IMobileCommonService getMobileCommonService() {
		return mobileCommonService;
	}
	public void setMobileCommonService(IMobileCommonService mobileCommonService) {
		this.mobileCommonService = mobileCommonService;
	}
	public IFoodcataofcanteenService getFoodcataService() {
		return foodcataService;
	}
	public void setFoodcataService(IFoodcataofcanteenService foodcataService) {
		this.foodcataService = foodcataService;
	}
	public ICanteenService getCanteenService() {
		return canteenService;
	}
	public void setCanteenService(ICanteenService canteenService) {
		this.canteenService = canteenService;
	}
	public String getFood() {
		return food;
	}
	public void setFood(String food) {
		this.food = food;
	}
	
}
