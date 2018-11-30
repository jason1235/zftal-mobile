package com.zfsoft.mobile.webcasts.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.mobile.common.utils.FileUntils;
import com.zfsoft.mobile.webcasts.entity.WebcastsEntity;
import com.zfsoft.mobile.webcasts.service.IWebcastsService;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.CodeUtil;
import com.zfsoft.util.base.StringUtil;

public class WebcastsAction extends HrmAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String op;
	private WebcastsEntity model = new WebcastsEntity();
	private WebcastsEntity query = new WebcastsEntity();
	private PageList<WebcastsEntity> pageList;
	private IWebcastsService webcastService;
	
	
	public String list(){
		pageList=webcastService.getPageList(query);
		return "list";
	}
	
	public String remove(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/");
		if(StringUtil.isNotBlank(query.getWebcastId())){
			model = webcastService.findById(query.getWebcastId());
			if(model != null){
				String picpath = model.getPicPath();
				FileUntils fileutils = new FileUntils();
				fileutils.deletePic(realPath, picpath);
			}
			webcastService.deleteWebcasts(query.getWebcastId());
			this.setSuccessMessage("成功删除数据！");
		}else{
			this.setSuccessMessage("获取删除数据失败！");
		}
		this.getValueStack().set("data", getMessage());
		return "data";
	}
	
	public String control(){
		int res=webcastService.webcastsControl(query);
		if(res>0){
			this.setSuccessMessage("操作成功");
		}else{
			this.setErrorMessage("操作失败");
		}
		this.getValueStack().set("data", getMessage());
		return "data";
	}
	
	public IWebcastsService getWebcastService() {
		return webcastService;
	}
	public void setWebcastService(IWebcastsService webcastService) {
		this.webcastService = webcastService;
	}
	
	
	public static void main(String[] args) {
		String username;
		try {
			System.out.println(ApptokenUtils.compare("kOvyqHdVOWk=", "ce3e40ade764cb760b237f104ed11f44"));
			
			username = CodeUtil.decode("kOvyqHdVOWk=", "ce3e40ade764cb760b237f104ed11f44");
			System.out.println(username);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	public WebcastsEntity getModel() {
		return model;
	}
	public void setModel(WebcastsEntity model) {
		this.model = model;
	}
	public WebcastsEntity getQuery() {
		return query;
	}
	public void setQuery(WebcastsEntity query) {
		this.query = query;
	}
	public PageList<WebcastsEntity> getPageList() {
		return pageList;
	}
	public void setPageList(PageList<WebcastsEntity> pageList) {
		this.pageList = pageList;
	}
	

}
