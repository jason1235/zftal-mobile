package com.zfsoft.mobile.myportal.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionContext;
import com.zfsoft.common.system.BaseHolder;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.mobile.common.enums.FwbmEnum;
import com.zfsoft.mobile.common.service.IMobileCommonService;
import com.zfsoft.mobile.common.utils.FileUntils;
import com.zfsoft.mobile.common.utils.ImageTagHtml;
import com.zfsoft.mobile.myportal.entity.MhTslbEntity;
import com.zfsoft.mobile.myportal.entity.MyPortal;
import com.zfsoft.mobile.myportal.query.MyPortalQuery;
import com.zfsoft.mobile.myportal.service.IMyPortalService;
import com.zfsoft.mobile.services.entity.AppServiceEntity;
import com.zfsoft.mobile.services.service.IServiceManagerService;
import com.zfsoft.mobile.servlet.entity.ResultEntity;
import com.zfsoft.mobile.vote.entity.VoteMainEntity;
import com.zfsoft.mobile.vote.entity.VoteOptionEntity;
import com.zfsoft.util.base.StringUtil;

public class MyPortalAction extends HrmAction {
	private IMyPortalService myPortalService;
	private IMobileCommonService mobileCommonService;
	private MyPortalQuery query = new MyPortalQuery();
	private PageList<MyPortal> pageList;
	private MyPortal model;
	private String op = "add";
	private String portalLogo;
	private IServiceManagerService serviceManagerService;
	
	public IServiceManagerService getServiceManagerService() {
		return serviceManagerService;
	}

	public void setServiceManagerService(
			IServiceManagerService serviceManagerService) {
		this.serviceManagerService = serviceManagerService;
	}

	public String list() {
		pageList = myPortalService.getPageList(query);
		return "list";
	}
	
	public String add() {
		getValueStack().set("fwbmList", FwbmEnum.values());
		getValueStack().set("imageHtml", ImageTagHtml.getImageHtml("portalLogo", Type.IMAGE, 1024, 90, 90, null, true));
		
		op = "add";
		
		ArrayList<String> iconList = FileUntils.getImagesPathList(null,null);
		getValueStack().set("iconList", iconList);
		ArrayList<String> fileNameList = FileUntils.getImagesNameList(null,null);
		getValueStack().set("fileNameList", fileNameList);
		
		List<AppServiceEntity> AppFwList = serviceManagerService.getFwdyxt();
		getValueStack().set("AppFwList", JSONArray.fromObject(AppFwList));
		return "edit";
	}
	
	public String modify() {
		getValueStack().set("fwbmList", FwbmEnum.values());
		op = "modify";
		model = myPortalService.findById(query);
		getValueStack().set("imageHtml", ImageTagHtml.getImageHtml("portalLogo", Type.IMAGE, 1024, 90, 90, model.getTbid(), true));
		
		String imageName = model.getTburl();
		if(!StringUtil.isEmpty(imageName)){
			imageName = imageName.substring(imageName.lastIndexOf("/")+1, imageName.length());
		}
		getValueStack().set("imageName", imageName);
		ArrayList<String> iconList = FileUntils.getImagesPathList(imageName,null);
		getValueStack().set("iconList", iconList);
		ArrayList<String> fileNameList = FileUntils.getImagesNameList(imageName,null);
		getValueStack().set("fileNameList", fileNameList);
		List<AppServiceEntity> AppFwList = serviceManagerService.getFwdyxt();
		getValueStack().set("AppFwList", JSONArray.fromObject(AppFwList));
		return "edit";
	}
	
	private String getImageHost() {
		String url = BaseHolder.getPropertiesValue("suploadPath");
		if (url == null) {
			return "/";
		}
        url = url.replace("\\", "/");
        if (!url.endsWith("/")) {
        	url += "/";
        }
		return url;
	}
	
	public String getImages(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String keyWord = request.getParameter("keyWord");
		String imageName = request.getParameter("imageName");
		ArrayList<String> iconList = FileUntils.getImagesPathList(null,keyWord);
		//getValueStack().set("iconList", JSONArray.fromObject(iconList));
		ArrayList<String> fileNameList = FileUntils.getImagesNameList(null,keyWord);
		//getValueStack().set("fileNameList", JSONArray.fromObject(fileNameList));
		
		StringBuilder imageHtml = new StringBuilder();
		if(iconList != null && iconList.size() > 0){
			for (int i = 0; i < iconList.size(); i++) {
				imageHtml.append("<li class='col-md-3 col-sm-3 col-xs-3 iconclass' style='white-space:nowrap;text-overflow:ellipsis;overflow:hidden;'>");
				imageHtml.append("<img style='height: 50px;width: 50px;' src='"+getImageHost()+iconList.get(i)+"'><h5>");
				imageHtml.append("<input type='radio' value='"+iconList.get(i)+"' name='icoPath'");
				if(!StringUtil.isEmpty(imageName) && imageName.equals(fileNameList.get(i))){
					imageHtml.append("checked='checked'");
				}
				imageHtml.append("/>"+fileNameList.get(i)+"</h5></li>");
			}
			
		}
		
		Map<String, Object> data = new HashMap<String, Object>();
        data.put("success", true);
        data.put("imageHtml", imageHtml.toString());
        getValueStack().set(DATA, data);
		return DATA;
	}
	
	/**
	 * 保存修改后的索引顺序Action
	 * @return
	 */
	public String updateIndex() {
		String[] ids = getRequest().getParameterValues("ids");
		String minStr = getRequest().getParameter("minIndex");
		//String maxStr = getRequest().getParameter("maxIndex");
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
					myPortalService.updateIndex(map);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return list();
	}
	
	public String save() {
		String code = getRequest().getParameter("code");
		if(!StringUtils.isEmpty(code)){
			if(code.equals("0")){
				int maxWebFwbm = myPortalService.getMaxWebFwbm();
				query.setCode(String.valueOf(maxWebFwbm));
			}else{
				query.setCode(code);
			}
		}
		
		
		if ("add".equals(op)) {
			query.setCreator(getUser().getYhm());
		} else {
			query.setUpdater(getUser().getYhm());
		}
		
		String iconmethod = getRequest().getParameter("uploadMethod");
		String fwtbdz;
		if(iconmethod.equals("fromsomewhere")){//系统中选择的时候
			fwtbdz = getRequest().getParameter("icoPath");
			query.setTbid(null);
			query.setTburl(fwtbdz);
		}else{//自已上传的时候
			if (!StringUtil.isEmpty(portalLogo)) {
				query.setTbid(portalLogo);
				//query.setTburl(mobileCommonService.getUploadImagePath(portalLogo));
				query.setTburl(mobileCommonService.getMinUploadImagePath(portalLogo));
			}
			
		}
		
		
		try {
			myPortalService.doSave(query);
			this.setSuccessMessage("操作成功");
		} catch (Exception e) {
			this.setErrorMessage(e.getMessage());
		}
		this.getValueStack().set("data", this.getMessage());
		return "data";
	}
	
	public String control() {
		try {
			myPortalService.doControl(query);
			this.setSuccessMessage("操作成功");
		} catch (Exception e) {
			this.setErrorMessage(e.getMessage());
		}
		this.getValueStack().set("data", this.getMessage());
		return "data";
	}
	
	//跳转到新增特色类别界面
	public String toTslb(){
		return "tslb";
	}
	
	//特色类别增加
	public void saveTslb(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		
		Gson gson = new Gson();
		PrintWriter out = null;
		Map<String,Object> tslbEntity = new HashMap<String, Object>();
   	 	try {
   	 	    out = response.getWriter();
   	 	    
   	 	    String lbmc = request.getParameter("lbmc");
   	 	    String lbms = request.getParameter("lbms");
   	 	    if(lbmc!=null){
   	 	        tslbEntity.put("lbmc", lbmc);
   	 	        tslbEntity.put("lbms", lbms);
   	 	        if(myPortalService.getHaveTslbByLbmc(tslbEntity)==0){
   	 	        	myPortalService.insertTslb(tslbEntity);
   	 	        	ResultEntity<String> result = new ResultEntity<String>(1, "操作成功","success");
   	 	        	out.write(gson.toJson(result));
   	 	        	out.flush();
   	 	        	out.close();
   	 	        }else{
	 	        	ResultEntity<String> result = new ResultEntity<String>(2, "该类别名称已存在，请重新定义","success");
	 	        	out.write(gson.toJson(result));
	 	        	out.flush();
	 	        	out.close();
   	 	        }
   	 	    }
   	 	} catch (Exception e) {
   	 		e.printStackTrace(); 
            ResultEntity<String> result = new ResultEntity<String>(0, "操作失败", "error");
            out.write(gson.toJson(result));
            out.flush();
            out.close();
	 	}
	}
	
	//特色类别列表，供页面选择框使用
	public void tslbList(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		
		Gson gson = new Gson();
		PrintWriter out = null;
   	 	try {
   	 	    out = response.getWriter();
   	 	    
   	 	    List<MhTslbEntity> list = myPortalService.getMhlbList();
   	 	    if(list==null){
   	 	        list = new ArrayList<MhTslbEntity>();
   	 	    }
   	    	ResultEntity<List<MhTslbEntity>> result = new ResultEntity<List<MhTslbEntity>>(1, "success",list);
        	out.write(gson.toJson(result));
        	out.flush();
        	out.close();
   	 	      
   	 	} catch (Exception e) {
   	 		e.printStackTrace(); 
            ResultEntity<String> result = new ResultEntity<String>(0, "操作失败", "error");
            out.write(gson.toJson(result));
            out.flush();
            out.close();
	 	}
	}
	
	public IMyPortalService getMyPortalService() {
		return myPortalService;
	}
	public void setMyPortalService(IMyPortalService myPortalService) {
		this.myPortalService = myPortalService;
	}
	public IMobileCommonService getMobileCommonService() {
		return mobileCommonService;
	}

	public void setMobileCommonService(IMobileCommonService mobileCommonService) {
		this.mobileCommonService = mobileCommonService;
	}

	public MyPortalQuery getQuery() {
		return query;
	}
	public void setQuery(MyPortalQuery query) {
		this.query = query;
	}
	public PageList<MyPortal> getPageList() {
		return pageList;
	}
	public void setPageList(PageList<MyPortal> pageList) {
		this.pageList = pageList;
	}
	public MyPortal getModel() {
		return model;
	}
	public void setModel(MyPortal model) {
		this.model = model;
	}
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	public String getPortalLogo() {
		return portalLogo;
	}
	public void setPortalLogo(String portalLogo) {
		this.portalLogo = portalLogo;
	}
	
	
}
