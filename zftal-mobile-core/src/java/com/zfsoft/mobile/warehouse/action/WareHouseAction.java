package com.zfsoft.mobile.warehouse.action;

import java.util.List;

import org.apache.log4j.Logger;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.mobile.servlet.appCenterHttp.action.AppCenterHttpAction;
import com.zfsoft.mobile.warehouse.entity.WareHouseEntity;
import com.zfsoft.mobile.warehouse.service.WareHouseService;

public class WareHouseAction extends HrmAction{
	
	private static Logger logger = Logger.getLogger(AppCenterHttpAction.class);

	private WareHouseService wareHouseService;
	
	private WareHouseEntity entity;
	private String op;
	
	
	//投票活动列表
	public String htList(){
		PageList<WareHouseEntity> list = wareHouseService.getList(entity);
		
		this.getValueStack().set("list", list);
			
		return "list";
	}
	
	public String toAdd(){
		op = "add";
		return "edit";
	}
	
	public String toUpdate(){
		op="modify";
		entity = wareHouseService.findById(entity);
		this.getValueStack().set("ware", entity);
		return "edit";
	}
	
	
	public String saveOrUpdate(){
		if (entity.getId() == null) {
			wareHouseService.insertWareHouse(entity);
			this.setSuccessMessage("成功增加数据！");
		}else {
			wareHouseService.updateWareHouseyId(entity);
			this.setSuccessMessage("成功修改数据！");
		}
		this.getValueStack().set("data", getMessage());
		return "data";
	}
	
	public static Logger getLogger() {
		return logger;
	}
	public static void setLogger(Logger logger) {
		WareHouseAction.logger = logger;
	}
	public WareHouseService getWareHouseService() {
		return wareHouseService;
	}
	public void setWareHouseService(WareHouseService wareHouseService) {
		this.wareHouseService = wareHouseService;
	}
	public WareHouseEntity getEntity() {
		return entity;
	}
	public void setEntity(WareHouseEntity entity) {
		this.entity = entity;
	}
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	
}
