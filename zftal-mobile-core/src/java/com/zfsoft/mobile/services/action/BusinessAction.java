package com.zfsoft.mobile.services.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.mobile.services.dao.daointerface.IBusinessDao;
import com.zfsoft.mobile.services.dao.query.BusinessQuery;
import com.zfsoft.mobile.services.dao.query.ServiceManagerQuery;
import com.zfsoft.mobile.services.entity.Business;
import com.zfsoft.mobile.services.entity.ServiceManager;
import com.zfsoft.mobile.services.service.IBusinessService;
import com.zfsoft.mobile.services.service.IServiceManagerService;
import com.zfsoft.util.base.StringUtil;

public class BusinessAction extends HrmAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1940084126866206721L;
	
	private IBusinessService businessService;
	
	private IServiceManagerService  serviceManagerService;
	
	private BusinessQuery query = new BusinessQuery();
	
	private Business model = new Business();
	
	private String op;
	
	

	public String list(){
		PageList<Business> list = new PageList<Business>();
		
		//判断是否存在移动后台的服务,即procode编码为999的业务系统
		BusinessQuery ydhtQuery = new BusinessQuery();
		ydhtQuery.setProcode("999");
		businessService.getYdht(ydhtQuery);
		
		
		list = businessService.getList(query);
		this.getValueStack().set("list", list);
		return "list";
	}
	
	public String toAdd(){
		op = "add";
		return "edit";
	}
	
	public String toModify(){
		PageList<Business> list = businessService.getList(query);
		model = list.get(0);
		op = "modify";
		return "edit";
	}
	
	/**
	 * 保存修改后的索引顺序Action
	 * @return
	 */
	public String updateIndex() {
		String[] ids = getRequest().getParameterValues("ids");
		String minStr = getRequest().getParameter("minIndex");
		int min =0;
		if (minStr != null) {
			min = Integer.parseInt(minStr);
		}
		Map<String, String> map = null;
		if (ids != null) {
			try {
				for (int i =0; i <ids.length; i++) {
					map = new HashMap<String, String>();
					map.put("index", (i+min)+"");
					map.put("id", ids[i]);
					businessService.updateIndex(map);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return list();
	}
	
	public String save() throws Exception{
		if(model.getClassId() == null){
			String checkResult = businessService.check(model);
			if(!StringUtil.isEmpty(checkResult)){
				this.setErrorMessage(checkResult);
				getValueStack().set(DATA, getMessage());
				return DATA;
			}
			businessService.insert(model);
		}
		else
			businessService.update(model);
	    
	    this.setSuccessMessage("成功插入数据！");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String remove(){
		ServiceManagerQuery serviceQuery = new ServiceManagerQuery();
		if(query != null && query.getClassId() != null)
			serviceQuery.setClassSsywxt(query.getClassId());
		serviceQuery.setClassDeleted("0");
		List<ServiceManager> count = serviceManagerService.getList(serviceQuery);
		if(count.size() > 0){
			this.setErrorMessage("存在相关的服务,不能删除!");
			getValueStack().set(DATA, getMessage());
			return DATA;
		}else{
			businessService.remove(query);
			this.setSuccessMessage("删除成功！");
			getValueStack().set(DATA, getMessage());
			return DATA;
		}
	}
	
	public String getYwxtdz(){
		PageList<Business> list = new PageList<Business>();
		list = businessService.getList(query);
		System.out.println(list.get(0).getClassXtdz());

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		map.put("xtdz", list.get(0).getClassXtdz());
		getValueStack().set(DATA, map);
		return DATA;
	}
	
	public String qiyong(){
		businessService.updateQiYong(query);
		this.setSuccessMessage("成功启用");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String tingyong(){
		ServiceManagerQuery serviceQuery = new ServiceManagerQuery();
		if(query != null && query.getClassId() != null)
			serviceQuery.setClassSsywxt(query.getClassId());
		serviceQuery.setClassFbzt("1");
		serviceQuery.setClassDeleted("0");
		List<ServiceManager> count = serviceManagerService.getList(serviceQuery);
		if(count.size() > 0){
			this.setErrorMessage("存在相关的服务已经发布,不能停用!");
			getValueStack().set(DATA, getMessage());
			return DATA;
		}else{
			businessService.updateTingYong(query);
			this.setSuccessMessage("成功停用！");
			getValueStack().set(DATA, getMessage());
			return DATA;
		}
	}
	
	public BusinessQuery getQuery() {
		return query;
	}

	public void setQuery(BusinessQuery query) {
		this.query = query;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String edit(){
		return "edit";
	}

	public void setBusinessService(IBusinessService businessService) {
		this.businessService = businessService;
	}

	public IBusinessService getBusinessService() {
		return businessService;
	}

	public void setServiceManagerService(IServiceManagerService serviceManagerService) {
		this.serviceManagerService = serviceManagerService;
	}

	public IServiceManagerService getServiceManagerService() {
		return serviceManagerService;
	}

	public void setModel(Business model) {
		this.model = model;
	}

	public Business getModel() {
		return model;
	}
}
