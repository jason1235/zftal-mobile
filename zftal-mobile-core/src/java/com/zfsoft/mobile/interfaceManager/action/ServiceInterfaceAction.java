package com.zfsoft.mobile.interfaceManager.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

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
import com.zfsoft.mobile.services.service.IServiceManagerService;
import com.zfsoft.mobile.wsdl.Parameters;
import com.zfsoft.mobile.wsdl.WSDLParser;

public class ServiceInterfaceAction extends HrmAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6781680501670318933L;

	private IInterfaceManagerService interfaceManagerService;
	
	private IServiceInterfaceService serviceInterfaceService;
	
	private IServiceManagerService serviceManagerService;
	
	private ServiceInterface model = new ServiceInterface();
	
	private ServiceInterfaceQuery query = new ServiceInterfaceQuery();
	
	String op;

	public String list() {
		PageList<InterfaceManager> interfacemanagerList = new PageList<InterfaceManager>();
		InterfaceManagerQuery  interfacemanagerquery = new InterfaceManagerQuery();
		interfacemanagerList = interfaceManagerService.getList(interfacemanagerquery);
		this.getValueStack().set("interfacemanagerList", interfacemanagerList);
		
		PageList<ServiceManager> ServiceManagerList = new PageList<ServiceManager>();
		ServiceManagerQuery serviceQuery = new ServiceManagerQuery();
		serviceQuery.setClassDeleted("0");
		ServiceManagerList = serviceManagerService.getList(serviceQuery);
		this.getValueStack().set("ServiceManagerList", ServiceManagerList);
		
		
		
		
		PageList<ServiceInterface> list = serviceInterfaceService.getList(query);
		this.getValueStack().set("list", list);
		return "list";
	}
	
	

	public String toAdd() throws Exception{
		PageList<InterfaceManager> interfacemanagerList = new PageList<InterfaceManager>();
		InterfaceManagerQuery  interfacemanagerquery = new InterfaceManagerQuery();
		interfacemanagerList = interfaceManagerService.getList(interfacemanagerquery);
		this.getValueStack().set("interfacemanagerList", interfacemanagerList);
		
		PageList<ServiceManager> ServiceManagerList = new PageList<ServiceManager>();
		ServiceManagerQuery serviceQuery = new ServiceManagerQuery();
		serviceQuery.setClassDeleted("0");
		ServiceManagerList = serviceManagerService.getList(serviceQuery);
		this.getValueStack().set("ServiceManagerList", ServiceManagerList);
		
		InterfaceManager jiekou = interfacemanagerList.get(0);
		WSDLParser paser = new WSDLParser();
		List<String> methodList = paser.getAllMethodByServiceUrl(jiekou.getClassJkdz());
		this.getValueStack().set("methodList", methodList);
		
		op = "add";
		return "edit";
	}
	
	public String toModify() throws Exception{
		PageList<InterfaceManager> interfacemanagerList = new PageList<InterfaceManager>();
		InterfaceManagerQuery  interfacemanagerquery = new InterfaceManagerQuery();
		interfacemanagerList = interfaceManagerService.getList(interfacemanagerquery);
		this.getValueStack().set("interfacemanagerList", interfacemanagerList);
		
		PageList<ServiceManager> ServiceManagerList = new PageList<ServiceManager>();
		ServiceManagerQuery serviceQuery = new ServiceManagerQuery();
		serviceQuery.setClassDeleted("0");
		ServiceManagerList = serviceManagerService.getList(serviceQuery);
		this.getValueStack().set("ServiceManagerList", ServiceManagerList);
		
		/*InterfaceManager jiekou = interfacemanagerList.get(0);
		WSDLParser paser = new WSDLParser();
		List<String> methodList = paser.getAllMethodByServiceUrl(jiekou.getClassJkdz());
		this.getValueStack().set("methodList", methodList);*/
		
		PageList<ServiceInterface> list = serviceInterfaceService.getList(query);
		model = list.get(0);
		WSDLParser paser = new WSDLParser();
		List<String> methodList = paser.getAllMethodByServiceUrl(model.getClassJkdz());
		this.getValueStack().set("methodList", methodList);
		op = "modify";
		return "edit";
	}
	
	public ServiceInterface getModel() {
		return model;
	}



	public void setModel(ServiceInterface model) {
		this.model = model;
	}



	public ServiceInterfaceQuery getQuery() {
		return query;
	}



	public void setQuery(ServiceInterfaceQuery query) {
		this.query = query;
	}



	public String getOp() {
		return op;
	}



	public void setOp(String op) {
		this.op = op;
	}



	public String save() throws Exception{
		if(model.getClassFwjkid() == null)
			serviceInterfaceService.insert(model);
		else
			serviceInterfaceService.update(model);
	    
	    this.setSuccessMessage("成功插入数据！");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String test() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		String arrayObj = request.getParameter("arrayStr").trim();
		String[] arrayObjList = arrayObj.split(",");
		String ssjk = request.getParameter("ssjk").trim();
		String jkffm = request.getParameter("jkffm").trim();
		//Object[] arrayObj = request.getParameterMap()("arrayObj");
		InterfaceManagerQuery interfaceQuery = new InterfaceManagerQuery();
		interfaceQuery.setClassJkid(ssjk);
		InterfaceManager interfacemanager = interfaceManagerService.getList(interfaceQuery).get(0);
		WSDLParser paser = new WSDLParser();
		
		try {
			List<Parameters> parametersList = paser.getParamByMethodNameAndWsUrl(jkffm, interfacemanager.getClassJkdz());
			if(arrayObjList != null && arrayObjList.length > 0){
				String result = paser.getExecutionResult(interfacemanager.getClassJkdz(), jkffm, parametersList, arrayObjList);
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("success", true);
				map.put("result", result);
				getValueStack().set(DATA, map);
				return DATA;
			}else{
				boolean isCanExecuted = paser.executionMethod(interfacemanager.getClassJkdz(), jkffm, parametersList);
				if(isCanExecuted){
					this.setSuccessMessage("接口方法测试成功！");
					getValueStack().set(DATA, getMessage());
					return DATA;
				}else{
					this.setErrorMessage("接口方法测试不通过!！");
					getValueStack().set(DATA, getMessage());
					return DATA;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			//throw e;
			this.setErrorMessage(e.getMessage());
			getValueStack().set(DATA, getMessage());
			return DATA;
		}
		
		
		
	}
	
	public String getMethodList() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		String ssjk = request.getParameter("ssjk").trim();
		InterfaceManagerQuery interfaceManagerQuery = new InterfaceManagerQuery();
		interfaceManagerQuery.setClassJkid(ssjk);
		InterfaceManager jiekou = interfaceManagerService.getList(interfaceManagerQuery).get(0);
		WSDLParser paser = new WSDLParser();
		List<String> methodList = paser.getAllMethodByServiceUrl(jiekou.getClassJkdz());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		map.put("methodList", methodList);
		getValueStack().set(DATA, map);
		return DATA;
	}
	
	public String getParamList() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		String qjssjk = request.getParameter("qjssjk").trim();
		String qjjkffm = request.getParameter("qjjkffm").trim();
		InterfaceManagerQuery interfaceManagerQuery = new InterfaceManagerQuery();
		interfaceManagerQuery.setClassJkid(qjssjk);
		InterfaceManager jiekou = interfaceManagerService.getList(interfaceManagerQuery).get(0);
		WSDLParser paser = new WSDLParser();
		List<Parameters> parametersList = paser.getParamByMethodNameAndWsUrl(qjjkffm, jiekou.getClassJkdz());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		map.put("parametersList", parametersList);
		getValueStack().set(DATA, map);
		return DATA;
	}
	
	public String remove(){
		/*ServiceInterfaceQuery serviceinterfacequery = new ServiceInterfaceQuery();
		if(query != null && query.getClassFwjkid() != null)
			serviceinterfacequery.setClassSsjk(query.getClassFwjkid());
		serviceinterfacequery.setClassDeleted("0");
		List<ServiceManager> count = serviceInterfaceService.getList(serviceinterfacequery);
		if(count.size() > 0){
			this.setErrorMessage("存在相关的服务接口,不能删除!");
			getValueStack().set(DATA, getMessage());
			return DATA;
		}else{*/
			serviceInterfaceService.remove(query);
			this.setSuccessMessage("删除成功！");
			getValueStack().set(DATA, getMessage());
			return DATA;
		//}
	}
	
	public IInterfaceManagerService getInterfaceManagerService() {
		return interfaceManagerService;
	}

	public void setInterfaceManagerService(
			IInterfaceManagerService interfaceManagerService) {
		this.interfaceManagerService = interfaceManagerService;
	}

	public IServiceInterfaceService getServiceInterfaceService() {
		return serviceInterfaceService;
	}

	public void setServiceInterfaceService(
			IServiceInterfaceService serviceInterfaceService) {
		this.serviceInterfaceService = serviceInterfaceService;
	}



	public void setServiceManagerService(IServiceManagerService serviceManagerService) {
		this.serviceManagerService = serviceManagerService;
	}



	public IServiceManagerService getServiceManagerService() {
		return serviceManagerService;
	}
	
}
