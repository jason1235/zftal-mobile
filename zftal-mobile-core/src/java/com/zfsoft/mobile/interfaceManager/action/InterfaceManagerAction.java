package com.zfsoft.mobile.interfaceManager.action;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

//import org.apache.bcel.generic.NEW;
import org.apache.struts2.ServletActionContext;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.mobile.interfaceManager.dao.query.InterfaceManagerQuery;
import com.zfsoft.mobile.interfaceManager.dao.query.ServiceInterfaceQuery;
import com.zfsoft.mobile.interfaceManager.entity.InterfaceManager;
import com.zfsoft.mobile.interfaceManager.entity.ServiceInterface;
import com.zfsoft.mobile.interfaceManager.service.IInterfaceManagerService;
import com.zfsoft.mobile.interfaceManager.service.IServiceInterfaceService;
import com.zfsoft.mobile.services.dao.query.BusinessQuery;
import com.zfsoft.mobile.services.dao.query.ServiceManagerQuery;
import com.zfsoft.mobile.services.entity.Business;
import com.zfsoft.mobile.services.entity.ServiceManager;
import com.zfsoft.mobile.services.service.IBusinessService;
import com.zfsoft.mobile.wsdl.WSDLParser;

public class InterfaceManagerAction extends HrmAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5493098761095821686L;

	private IInterfaceManagerService interfaceManagerService;
	
	private IBusinessService businessService;
	
	String op;
	
	private InterfaceManager model = new InterfaceManager();
	
	private InterfaceManagerQuery query = new InterfaceManagerQuery();
	
	private IServiceInterfaceService serviceInterfaceService ;
	
	public InterfaceManager getModel() {
		return model;
	}



	public void setModel(InterfaceManager model) {
		this.model = model;
	}



	public InterfaceManagerQuery getQuery() {
		return query;
	}



	public void setQuery(InterfaceManagerQuery query) {
		this.query = query;
	}



	public IServiceInterfaceService getServiceInterfaceService() {
		return serviceInterfaceService;
	}



	public void setServiceInterfaceService(
			IServiceInterfaceService serviceInterfaceService) {
		this.serviceInterfaceService = serviceInterfaceService;
	}



	public String list(){
		/*PageList<Business> businessList = new PageList<Business>();
		BusinessQuery businessQuery = new BusinessQuery();
		businessList = businessService.getList(businessQuery);
		this.getValueStack().set("businessList", businessList);*/
		PageList<InterfaceManager> list = interfaceManagerService.getList(query);
		this.getValueStack().set("list", list);
		return "list";
	}
	
	

	public String toAdd(){
		/*PageList<Business> businessList = new PageList<Business>();
		BusinessQuery businessQuery = new BusinessQuery();
		businessList = businessService.getList(businessQuery);
		this.getValueStack().set("businessList", businessList);*/
		op = "add";
		return "edit";
	}
	
	public String toModify(){
		/*PageList<Business> businessList = new PageList<Business>();
		BusinessQuery businessQuery = new BusinessQuery();
		businessList = businessService.getList(businessQuery);
		this.getValueStack().set("businessList", businessList);*/
		PageList<InterfaceManager> list = interfaceManagerService.getList(query);
		model = list.get(0);
		op = "modify";
		return "edit";
	}
	
	public String save() throws Exception{
		if(model.getClassJkid() == null)
			interfaceManagerService.insert(model);
		else
			interfaceManagerService.update(model);
	    
	    this.setSuccessMessage("成功插入数据！");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String test() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String qbjkdz = request.getParameter("qbjkdz");
		WSDLParser paser = new WSDLParser();
		try {
			boolean result = paser.isCanPass(qbjkdz);
			if(result){
				this.setSuccessMessage("接口测试成功！");
				getValueStack().set(DATA, getMessage());
				return DATA;
			}else{
				this.setErrorMessage("接口测试不通过!！");
				getValueStack().set(DATA, getMessage());
				return DATA;
			}
		} catch (Exception e) {
			// TODO: handle exception
			//throw e;
			this.setErrorMessage(e.getMessage());
			getValueStack().set(DATA, getMessage());
			return DATA;
		}
		
	    
	    
	}
	
	public String remove(){
		/*ServiceInterfaceQuery serviceinterfacequery = new ServiceInterfaceQuery();
		if(query != null && query.getClassJkid() != null)
			serviceinterfacequery.setClassSsjk(query.getClassJkid());
		serviceinterfacequery.setClassDeleted("0");
		PageList<ServiceInterface> count = serviceInterfaceService.getList(serviceinterfacequery);
		if(count.size() > 0){
			this.setErrorMessage("存在相关的服务接口,不能删除!");
			getValueStack().set(DATA, getMessage());
			return DATA;
		}else{*/
			interfaceManagerService.remove(query);
			this.setSuccessMessage("删除成功！");
			getValueStack().set(DATA, getMessage());
			return DATA;
		//}
	}
	
	
	public String qiyong(){
		interfaceManagerService.updateQiYong(query);
		this.setSuccessMessage("成功启用");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String tingyong(){
		/*ServiceInterfaceQuery serviceinterfacequery = new ServiceInterfaceQuery();
		if(query != null && query.getClassJkid() != null)
			serviceinterfacequery.setClassSsjk(query.getClassJkid());
		serviceinterfacequery.setClassDeleted("0");
		List<ServiceManager> count = serviceInterfaceService.getList(serviceinterfacequery);
		if(count.size() > 0){
			this.setErrorMessage("存在相关的服务接口已经发布,不能停用!");
			getValueStack().set(DATA, getMessage());
			return DATA;
		}else{*/
			interfaceManagerService.updateTingYong(query);
			this.setSuccessMessage("成功停用！");
			getValueStack().set(DATA, getMessage());
			return DATA;
		//}
	}

	public void setInterfaceManagerService(IInterfaceManagerService interfaceManagerService) {
		this.interfaceManagerService = interfaceManagerService;
	}

	public IInterfaceManagerService getInterfaceManagerService() {
		return interfaceManagerService;
	}

	public void setBusinessService(IBusinessService businessService) {
		this.businessService = businessService;
	}

	public IBusinessService getBusinessService() {
		return businessService;
	}
	
	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}
	
}
