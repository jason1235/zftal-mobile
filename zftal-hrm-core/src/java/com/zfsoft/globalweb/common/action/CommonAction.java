package com.zfsoft.globalweb.common.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.common.log.User;
import com.zfsoft.dao.entities.IndexModel;
import com.zfsoft.globalweb.common.entities.Common;
import com.zfsoft.globalweb.common.svcinterface.ICommonService;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.service.svcinterface.IIndexService;

/**
 * 
 * @author
 * 2014-2-21
 */
public class CommonAction extends HrmAction implements ModelDriven<Common> {

	private static final long serialVersionUID = 1L;

	private List<Common> list = new ArrayList<Common>();
	private Common model = new Common();
	private IndexModel indexModel = new IndexModel();
	private String resourceIds;
	
	private ICommonService commonService;
	private IIndexService service;
	
	public String list(){
		User user=SessionFactory.getUser();
		List<String> jsxx=(List<String>)getRequest().getSession().getAttribute(user.getYhm());
		model.setRoseId(jsxx.get(0));
		model.setUserId(getUser().getYhm());
		list = commonService.getCommon(model);
		this.getValueStack().set("userCommons", list);
		return "list";
	}
	
	public String getOneList(){
		User user=SessionFactory.getUser();
		List<String> jsxx=(List<String>)getRequest().getSession().getAttribute(user.getYhm());
		model.setRoseId(jsxx.get(0));
		model.setUserId(getUser().getYhm());
		list = commonService.getCommon(model);
		this.getValueStack().set(DATA, list);
		return DATA;
	}
	
	public String toListAll(){
//		ValueStack vs = getValueStack();
//		User user=SessionFactory.getUser();
//		
//		List<String> jsxx=(List<String>)getRequest().getSession().getAttribute(user.getYhm());
//		List<HashMap<String, String>> list=new ArrayList<HashMap<String,String>>();
//		try {
//			list = service.cxDbCd(jsxx);
//		} catch (Exception e) {
//			logException(e);
//			return ERROR;
//		}
		getValueStack().set("forward", getMessage());
		return "forward";
	}
	
	public String listAll(){
		List<HashMap<String, Object>> menuList = new ArrayList<HashMap<String,Object>>();
		User user=SessionFactory.getUser();
		List<String> jsxx=(List<String>)getRequest().getSession().getAttribute(user.getYhm());
		List<HashMap<String, String>> maps = service.cxDbCd(jsxx);
		if(StringUtils.isEmpty(indexModel.getGnmkdm()) && maps.size() > 0){
			indexModel.setGnmkdm(maps.get(0).get("GNMKDM"));
		}
		try {
			menuList = service.cxZbCd(jsxx,indexModel);
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		List<IndexModel> lstResource = new ArrayList<IndexModel>();
		for(int i=0 ; i < menuList.size(); i++){
			Map<String, Object> menu = menuList.get(i);
			List<HashMap<String, String>> menus=  (List<HashMap<String, String>>) menu.get("sjMenu");
			for(int j=0; j < menus.size(); j++){
				Map<String, String> map = menus.get(j);
				IndexModel indexmodel = new IndexModel();
				indexmodel.setGnmkdm((String) map.get("GNMKDM"));
				indexmodel.setGnmkmc((String) map.get("GNMKMC"));
				lstResource.add(indexmodel);
			}
		}
		
		getValueStack().set("lstResource", lstResource);
		List<IndexModel> topTypeResource = new ArrayList<IndexModel>();
		for(int i=0 ; i < maps.size(); i++){
			Map<String, String> map = maps.get(i);
			IndexModel indexmodel = new IndexModel();
			indexmodel.setGnmkdm((String) map.get("GNMKDM"));
			indexmodel.setGnmkmc((String) map.get("GNMKMC"));
			topTypeResource.add(indexmodel);
		}
		getValueStack().set("topTypeResource", topTypeResource);
		model.setRoseId(jsxx.get(0));
		model.setUserId(getUser().getYhm());
		list = commonService.getCommon(model);
		this.getValueStack().set("userCommons", list);
		
		return "listAll";
	}
	
	public String save(){
		User user=SessionFactory.getUser();
		List<String> jsxx=(List<String>)getRequest().getSession().getAttribute(user.getYhm());
		try {
			commonService.save(resourceIds, user.getYhm(),jsxx.get(0));
		} catch (Exception e) {
			setErrorMessage(e.getMessage());
		}
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String apply(){
		User user=SessionFactory.getUser();
		List<String> jsxx=(List<String>)getRequest().getSession().getAttribute(user.getYhm());
		model.setUserId(user.getYhm());
		model.setRoseId(jsxx.get(0));
		commonService.add(model);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String cancel(){
		User user=SessionFactory.getUser();
		List<String> jsxx=(List<String>)getRequest().getSession().getAttribute(user.getYhm());
		model.setUserId(user.getYhm());
		model.setRoseId(jsxx.get(0));
		commonService.remove(model);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	@Override
	public Common getModel() {
		return model;
	}

	/**
	 * @return the list
	 */
	public List<Common> getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(List<Common> list) {
		this.list = list;
	}

	/**
	 * @return the commonService
	 */
	public ICommonService getCommonService() {
		return commonService;
	}

	/**
	 * @param commonService the commonService to set
	 */
	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(Common model) {
		this.model = model;
	}

	/**
	 * @return the service
	 */
	public IIndexService getService() {
		return service;
	}

	/**
	 * @param service the service to set
	 */
	public void setService(IIndexService service) {
		this.service = service;
	}

	/**
	 * @return the indexModel
	 */
	public IndexModel getIndexModel() {
		return indexModel;
	}

	/**
	 * @param indexModel the indexModel to set
	 */
	public void setIndexModel(IndexModel indexModel) {
		this.indexModel = indexModel;
	}

	/**
	 * @return the resourceIds
	 */
	public String getResourceIds() {
		return resourceIds;
	}

	/**
	 * @param resourceIds the resourceIds to set
	 */
	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}

}
