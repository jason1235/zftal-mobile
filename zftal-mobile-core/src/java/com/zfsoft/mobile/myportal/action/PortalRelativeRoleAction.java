package com.zfsoft.mobile.myportal.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.dao.entities.NewJsglModel;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.mobile.myportal.entity.MyPortal;
import com.zfsoft.mobile.myportal.entity.PortalRelativeRole;
import com.zfsoft.mobile.myportal.service.IPortalRelativeRoleService;
import com.zfsoft.mobile.services.dao.query.FwdyjsQuery;
import com.zfsoft.mobile.services.entity.FwdyjsModel;
import com.zfsoft.service.svcinterface.IJsglService;

public class PortalRelativeRoleAction extends HrmAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -829800267872894809L;
	
	private IPortalRelativeRoleService portalRelativeRoleService;
	
	private PortalRelativeRole query = new PortalRelativeRole();
	
	private PortalRelativeRole model;
	
	private PageList<PortalRelativeRole> pageList;
	
	private IJsglService jsglService;//角色管理SERVICE
	
	
	
	public IJsglService getJsglService() {
		return jsglService;
	}

	public void setJsglService(IJsglService jsglService) {
		this.jsglService = jsglService;
	}

	String jsid;
	
	String jsmcNew;
	
	String jsmc_qry;
	
	String[] fwbm;
	
	String[] yifwbm;
	
	/**
	 *  方法描述: 分配服务
	 *  参数 @return 
	 *  参数说明 
	 *  返回类型 String 返回类型
	 * @throws UnsupportedEncodingException  
	 * @return
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
		
		pageList = new PageList<PortalRelativeRole>();
    	List<PortalRelativeRole> yifpList = new PageList<PortalRelativeRole>();
    	jsid = getRequest().getParameter("jsid");
    	query.setClassJsid(jsid);
    	yifpList = portalRelativeRoleService.getPagedList(query);
    	pageList = portalRelativeRoleService.getPagedListWeifp(query);
    	this.getValueStack().set("yifpList", yifpList);
    	this.getValueStack().set("pageList", pageList);
		
		
   	    return "list";
	}
	
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
    	portalRelativeRoleService.insert(param);
    	this.setMessage(true, "成功为当前角色分配服务");
    	getValueStack().set(DATA, getMessage());
		return DATA;
    }
    
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
    	portalRelativeRoleService.delete(param);
    	this.setMessage(true, "成功为当前角色删除服务");
    	getValueStack().set(DATA, getMessage());
		return DATA;
    }

	public void setPortalRelativeRoleService(IPortalRelativeRoleService portalRelativeRoleService) {
		this.portalRelativeRoleService = portalRelativeRoleService;
	}

	public IPortalRelativeRoleService getPortalRelativeRoleService() {
		return portalRelativeRoleService;
	}

	public PortalRelativeRole getQuery() {
		return query;
	}

	public void setQuery(PortalRelativeRole query) {
		this.query = query;
	}

	public PortalRelativeRole getModel() {
		return model;
	}

	public void setModel(PortalRelativeRole model) {
		this.model = model;
	}

	public String getJsid() {
		return jsid;
	}

	public void setJsid(String jsid) {
		this.jsid = jsid;
	}

	public String getJsmcNew() {
		return jsmcNew;
	}

	public void setJsmcNew(String jsmcNew) {
		this.jsmcNew = jsmcNew;
	}

	public String getJsmc_qry() {
		return jsmc_qry;
	}

	public void setJsmc_qry(String jsmc_qry) {
		this.jsmc_qry = jsmc_qry;
	}

	public PageList<PortalRelativeRole> getPageList() {
		return pageList;
	}

	public void setPageList(PageList<PortalRelativeRole> pageList) {
		this.pageList = pageList;
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

	
	
	
}
