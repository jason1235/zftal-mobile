package com.zfsoft.mobile.services.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;
import com.zfsoft.common.query.QueryModel;
import com.zfsoft.dao.entities.JsglModel;
import com.zfsoft.dao.entities.NewJsglModel;
import com.zfsoft.dao.entities.YhglModel;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.mobile.services.dao.query.FwdyjsQuery;
import com.zfsoft.mobile.services.dao.query.ServiceManagerQuery;
import com.zfsoft.mobile.services.entity.FwdyjsModel;
import com.zfsoft.mobile.services.service.IFwdyjsService;
import com.zfsoft.mobile.services.service.IServiceManagerService;
import com.zfsoft.service.svcinterface.IJsglService;
import com.zfsoft.util.base.StringUtil;

public class FwdyjsAction extends HrmAction implements ModelDriven<FwdyjsModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2439615399514398857L;
	
	private IFwdyjsService fwdyjsService;
	
	private IServiceManagerService serviceManagerService;
	
	private FwdyjsModel query = new FwdyjsModel();
	
	private FwdyjsModel model;
	
	private PageList<FwdyjsModel> pageList;
	
	private IJsglService jsglService;//角色管理SERVICE
	
	String jsid;
	
	String jsmcNew;
	
	String jsmc_qry;
	
	String[] fwbm;
	
	String[] yifwbm;

	
	public String getJsmcNew() {
		return jsmcNew;
	}

	public IJsglService getJsglService() {
		return jsglService;
	}

	public void setJsglService(IJsglService jsglService) {
		this.jsglService = jsglService;
	}

	public void setJsmcNew(String jsmcNew) {
		this.jsmcNew = jsmcNew;
	}

	public String getJsmc_qry() {
		return jsmc_qry;
	}
	
	/*public FwdyjsModel getModel() {
		model.setModelBase(super.getUser());
		return model;
	}*/

	public void setJsmc_qry(String jsmc_qry) {
		this.jsmc_qry = jsmc_qry;
	}

	public FwdyjsModel getModel() {
		return model;
	}

	public void setModel(FwdyjsModel model) {
		this.model = model;
	}

	public String[] getFwbm() {
		return fwbm;
	}

	public void setFwbm(String[] fwbm) {
		this.fwbm = fwbm;
	}

	public String[] getYifwbm() {
		return yifwbm;
	}

	public void setYifwbm(String[] yifwbm) {
		this.yifwbm = yifwbm;
	}

	public String getJsid() {
		return jsid;
	}

	public void setJsid(String jsid) {
		this.jsid = jsid;
	}

	public FwdyjsModel getQuery() {
		return query;
	}

	public void setQuery(FwdyjsModel query) {
		this.query = query;
	}

	/*public void setModel(FwdyjsModel model) {
		this.model = model;
	}*/

	/**
     * 方法描述: 分配服务
	 *  参数 @return 
	 *  参数说明 
	 *  返回类型 String 返回类型
	 * @throws UnsupportedEncodingException  
    *//*
    public String fpfu(){
    	
    	String jsmc;
    	String temp = null;
		try {
			temp = new String(getRequest().getParameter("jsmc").getBytes("iso-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	//String temp=getRequest().getParameter("jsmc");
		if(StringUtils.isNotEmpty(temp)){
			try {
				temp=URLDecoder.decode(temp, "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jsmc=temp;
			this.getValueStack().set("jsmc", jsmc);
		}
		
		
    	List<FwdyjsModel> yifpList = new PageList<FwdyjsModel>();
    	jsid = getRequest().getParameter("jsdm");
    	query.setClassJsid(jsid);
    	yifpList = fwdyjsService.getPagedList(query);
    	List<FwdyjsModel> weifplist = new ArrayList<FwdyjsModel>();
    	weifplist = fwdyjsService.getPagedListWeifp(query);
    	this.getValueStack().set("yifpList", yifpList);
    	this.getValueStack().set("weifplist", weifplist);
		
		
   	    return "fwdyjslist";
    }*/
    
    /**
     * 方法描述: 分配服务
	 *  参数 @return 
	 *  参数说明 
	 *  返回类型 String 返回类型
	 * @throws UnsupportedEncodingException  
    */
    public String fpfu(){
    	
    	String jsmc;
    	String temp = null;
		try {
			temp = getRequest().getParameter("jsmc");
			if(StringUtils.isNotEmpty(temp)){
				temp = new String(temp.getBytes("iso-8859-1"),"utf-8");
			}
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	//String temp=getRequest().getParameter("jsmc");
		if(StringUtils.isNotEmpty(temp)){
			try {
				temp=URLDecoder.decode(temp, "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jsmcNew=temp;
			this.getValueStack().set("jsmcNew", jsmcNew);
		}
		
		NewJsglModel newJsglQuery = new NewJsglModel();
		jsid = getRequest().getParameter("jsid");
		newJsglQuery.setJsdm(jsid);
		PageList<NewJsglModel> JsglModelList = jsglService.getJsglModelList(newJsglQuery);
		getValueStack().set("jsglModel", JsglModelList.get(0));
		
    	List<FwdyjsModel> yifpList = new PageList<FwdyjsModel>();
    	
    	query.setClassJsid(jsid);
    	yifpList = fwdyjsService.getPagedList(query);
    	query.setPerPageSize(10);
    	pageList = fwdyjsService.getPagedListWeifpNew(query);
    	this.getValueStack().set("yifpList", yifpList);
    	this.getValueStack().set("pageList", pageList);
		
		
   	    return "fwdyjslist";
    }
    
    
    public String fenpei(){
    	String fwbm = getRequest().getParameter("fwbm");
    	query.setClassFwbm(fwbm);
    	String isOrNot = getRequest().getParameter("isOrNot");
    	if(isOrNot.equals("no"))
    		query.setClassJsid(jsid);
    	FwdyjsModel fwdyjsmodel = new FwdyjsModel();
    	fwdyjsmodel = isOrNot == "yes" ? fwdyjsService.getPagedList(query).get(0) : fwdyjsService.getWeiModel(query).get(0);
	    getValueStack().set(DATA,fwdyjsmodel);
	    return DATA;
	}
    
    
    /*public String getWfp(){
    	QueryModel queryModel = model.getQueryModel();
    	model.setClassJsid(jsid);
    	//query.setPerPageSize(queryModel.getShowCount());
    	//query.setToPage(queryModel.getCurrentPage());
    	//query.setClassJsid(jsid);
    	if(model != null && model.getClassFwbm() != null)
    		query.setClassFwbm(model.getClassFwbm());
    	if(model != null && model.getClassFwmc() != null)
    		query.setClassFwmc(model.getClassFwmc());
		try {
			List<FwdyjsModel> list = fwdyjsService.getPagedListWeifp(model);
			if(list != null && list.size() != 0){
				queryModel.setItems(list);
				//queryModel.setTotalResult(list.size());
				//queryModel.setTotalPage(queryModel.getTotalResult()/queryModel.getShowCount());
			}
		} catch (Exception e) {
			logException(e);
			e.printStackTrace();
		}
		getValueStack().set(DATA, queryModel);
		return DATA;
	}*/
    
	/*public String getYfp(){
    	QueryModel queryModel = model.getQueryModel();
    	//query.setClassJsid(jsid);
    	model.setClassJsid(jsid);
		
		try {
			queryModel.setItems(fwdyjsService.getPagedList(model));
		} catch (Exception e) {
			logException(e);
			e.printStackTrace();
		}
		getValueStack().set(DATA, queryModel);
		return DATA;
	}*/
	
   /* public String add(){
    	String fwbm=getRequest().getParameter("fwbm");
    	String jsid=getRequest().getParameter("jsid");
    	FwdyjsQuery fwdyjsquery = new FwdyjsQuery();
    	fwdyjsquery.setClassJsid(jsid);
    	fwdyjsquery.setClassFwbm(fwbm);
    	fwdyjsService.insert(fwdyjsquery);
    	this.setMessage(true, "成功为当前角色分配服务");
    	getValueStack().set(DATA, getMessage());
		return DATA;
    }*/
    
    /**
	 * 为当前角色增加门户服务选项
	 * @return
	 */
	public String add(){
		Map<String, Object> param = new HashMap<String, Object>();
		List<String> tids = new ArrayList<String>();
        for (String idCheck : fwbm) {
            tids.add(idCheck.trim());
            
        }
        param.put("tids", tids);
        param.put("jsid", jsid);
        fwdyjsService.insert(param);
    	this.setMessage(true, "成功为当前角色分配服务");
    	getValueStack().set(DATA, getMessage());
		return DATA;
    }
    
    /*public String remove(){
    	String fwbm=getRequest().getParameter("fwbm");
    	FwdyjsQuery fwdyjsquery = new FwdyjsQuery();
    	fwdyjsquery.setClassJsid(jsid);
    	fwdyjsquery.setClassFwbm(fwbm);
    	fwdyjsService.delete(fwdyjsquery);
    	this.setMessage(true, "成功为当前角色删除服务");
    	getValueStack().set(DATA, getMessage());
		return DATA;
    }*/
    
    /**
	 * 删除当前角色某些服务
	 * @return
	 */
    public String remove(){
    	Map<String, Object> param = new HashMap<String, Object>();
		List<String> tids = new ArrayList<String>();
        for (String idCheck : yifwbm) {
            tids.add(idCheck.trim());
            
        }
        param.put("tids", tids);
        param.put("jsid", jsid);
        fwdyjsService.delete(param);
    	this.setMessage(true, "成功为当前角色删除服务");
    	getValueStack().set(DATA, getMessage());
		return DATA;
    }

    
    /*public String reduce(){
    	String fwbm=getRequest().getParameter("fwbm");
    	FwdyjsQuery fwdyjsquery = new FwdyjsQuery();
    	fwdyjsquery.setClassJsid(jsid);
    	fwdyjsquery.setClassFwbm(fwbm);
    	fwdyjsService.delete(fwdyjsquery);
    	this.setMessage(true, "成功为当前角色解绑服务");
    	getValueStack().set(DATA, getMessage());
		return DATA;
    }*/
	
	public void setFwdyjsService(IFwdyjsService fwdyjsService) {
		this.fwdyjsService = fwdyjsService;
	}

	public IFwdyjsService getFwdyjsService() {
		return fwdyjsService;
	}

	public void setServiceManagerService(IServiceManagerService serviceManagerService) {
		this.serviceManagerService = serviceManagerService;
	}

	public IServiceManagerService getServiceManagerService() {
		return serviceManagerService;
	}

	public void setPageList(PageList<FwdyjsModel> pageList) {
		this.pageList = pageList;
	}

	public PageList<FwdyjsModel> getPageList() {
		return pageList;
	}
	
}
