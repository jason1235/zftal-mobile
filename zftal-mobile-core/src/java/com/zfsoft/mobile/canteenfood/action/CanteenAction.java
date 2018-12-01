package com.zfsoft.mobile.canteenfood.action;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;


import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.file.util.ImageDBUtil;
import com.zfsoft.mobile.canteenfood.entity.Canteen;
import com.zfsoft.mobile.canteenfood.entity.Foodcataofcanteen;
import com.zfsoft.mobile.canteenfood.service.ICanteenService;
import com.zfsoft.mobile.canteenfood.service.IFoodcataofcanteenService;
import com.zfsoft.mobile.canteenfood.service.IFoodofcanteenService;
import com.zfsoft.mobile.common.service.IMobileCommonService;
import com.zfsoft.mobile.common.utils.ImageTagHtml;
import com.zfsoft.util.base.StringUtil;

/**
 *移动商家action类
 *@author yangbilin
 *@createtime 2017-07-20 15:30  
 */
public class CanteenAction extends HrmAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String op;
	private Canteen query = new Canteen();
	private Canteen model = new Canteen();
	private PageList<Canteen> pageList;
	private ICanteenService canteenService;
	private IMobileCommonService mobileCommonService;
	private IFoodcataofcanteenService foodcataService;
	private IFoodofcanteenService foodService;
	
	private String canteen;
	
	public String list(){
		pageList = canteenService.getPageList2(query);
		return "list";
	}
	
	public String toAdd(){
		this.getValueStack().set("imageHtml", ImageTagHtml.getImageHtml("canteen", Type.IMAGE, 1024, 2, 1, null, true));
		op = "add";
		return "edit";
	}
	public String image() {
		this.getValueStack().set("data", ImageTagHtml.getImageHtml("canteen", Type.IMAGE, 1024, 2, 1, null, true));
		return "data";
	}
	
	public String toModify(){
		List<Canteen> list= canteenService.getPageList2(query);
		if(list!=null&&list.size()>0){
			op="modify";
			model = list.get(0);
			if(StringUtil.isEmpty(model.getPicId())){
				getValueStack().set("imageHtmlArr", "");
			}else{
				String imageHtmlArr=ImageTagHtml.getImageHtml("canteen", Type.IMAGE, 1024, 2, 1, model.getPicId(), true);
				getValueStack().set("imageHtmlArr", imageHtmlArr);
			}
		}
		return "edit";
	}
	
	public String saveOrUpdate(){
		if(canteen!=null){
			query.setPicId(canteen);
			query.setPicPath(mobileCommonService.getGoodsImagePath(canteen,"canteenfood"));
		}
		if(StringUtils.isNotBlank(query.getCanteenId())){
			this.setSuccessMessage("成功修改数据！");
			canteenService.updateCanteen(query);
		}else{
			this.setSuccessMessage("成功保存数据！");
			canteenService.insertCanteen(query);
		}
		this.getValueStack().set("data", getMessage());
		return "data";
	}
	
	public String remove(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/");
		if(StringUtils.isNotBlank(query.getCanteenId())){
			model=canteenService.findById(query.getCanteenId());
			if(model!=null){
				if("1".equals(model.getIsactive())){
					this.setErrorMessage("当前状态为营业状态，不可删除");
					this.getValueStack().set("data", this.getMessage());
					return "data";
				}
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("canteenId", query.getCanteenId());
				List<Foodcataofcanteen> fcs=foodcataService.getFoodcataList(map);
				if(fcs!=null&&fcs.size()>0){
					this.setErrorMessage("当前商家下存在食品类别，不可删除");
					this.getValueStack().set("data", this.getMessage());
					return "data";
				}
				if(StringUtils.isNotBlank(model.getPicId())){
					String picpath=model.getPicPath();
					ImageDBUtil.deleteFileToDB(model.getPicId(),realPath+picpath);
				}
			}
			int flag=canteenService.deleteCanteen(query);
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
		if(StringUtils.isNotBlank(query.getCanteenId())){
			if("0".equals(query.getIsactive())){
				model=canteenService.findById(query.getCanteenId());
				if(model!=null){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("canteenId", query.getCanteenId());
					map.put("isactive", "1");
					List<String> fcs=foodcataService.getFoodcataIdList(map);
					if(fcs!=null&&fcs.size()>0){
						map.remove("canteenId");
						map.put("fcs",fcs);
						int count=foodService.getFoodsListCount(map);
						if(count>0){
							this.setErrorMessage("当前商家的食品类别下存在启用食品，请先停用其食品状态");
							this.getValueStack().set("data", this.getMessage());
							return "data";
						}
						this.setErrorMessage("当前商家存在启用的食品类别，请先停用其食品状态");
						this.getValueStack().set("data", this.getMessage());
						return "data";
					}
				}
			}
			int res=canteenService.control(query);
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
	public Canteen getQuery() {
		return query;
	}
	public void setQuery(Canteen query) {
		this.query = query;
	}
	public PageList<Canteen> getPageList() {
		return pageList;
	}
	public void setPageList(PageList<Canteen> pageList) {
		this.pageList = pageList;
	}
	public ICanteenService getCanteenService() {
		return canteenService;
	}
	public void setCanteenService(ICanteenService canteenService) {
		this.canteenService = canteenService;
	}
	public String getCanteen() {
		return canteen;
	}
	public void setCanteen(String canteen) {
		this.canteen = canteen;
	}

	public Canteen getModel() {
		return model;
	}

	public void setModel(Canteen model) {
		this.model = model;
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

	public IFoodofcanteenService getFoodService() {
		return foodService;
	}

	public void setFoodService(IFoodofcanteenService foodService) {
		this.foodService = foodService;
	}

	public static void main(String[] args) {
		String orderid = UUID.randomUUID().toString();
		System.out.println(orderid);
		System.out.println(orderid.replace("-", ""));
	}

	public void test(){
		  Connection con = null;
		  Statement st = null;
		  ResultSet rs = null;
		  CallableStatement cst = null;
		  try{
		   Class.forName("oracle.jdbc.driver.OracleDriver");
		   con = DriverManager.getConnection
		("jdbc:oracle:thin:@10.71.19.133:1521:ORCL","zftal_mobile","zftal_mobile");
		   String sql =  "{? = call getdistance(?,?,?,?)}";
		   cst = con.prepareCall(sql);
		   cst.registerOutParameter(1, Types.DOUBLE);
		   cst.setDouble(2, 121.506656);
		   cst.setDouble(3, 31.245087);
		   cst.setDouble(4, 121.508883);
		   cst.setDouble(5, 31.243481);
		   cst.execute();
		   double result = cst.getDouble(1);
		   System.out.println(result);
		  }catch(Exception e){
		   e.printStackTrace();
		  }finally{
		   try{
		    if(rs !=null){
		     rs.close();
		    }
		    if(st!=null){
		     st.close();
		    }
		    if(cst != null){
		     cst.close();
		    }
		    if(con != null){
		     con.close();
		    }
		   }catch(Exception e){
		    e.printStackTrace();
		   }
		  }
		 }

}
