package com.zfsoft.mobile.version.action;

import java.util.Date;

import com.zfsoft.common.system.BaseHolder;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.mobile.common.utils.ImageTagHtml;
import com.zfsoft.mobile.version.entity.VersionManager;
import com.zfsoft.mobile.version.service.IVersionManagerService;
import com.zfsoft.util.base.StringUtil;

public class VersionManagerAction extends HrmAction{

	private IVersionManagerService versionManagerService;
	private PageList<VersionManager> list = new PageList<VersionManager>();
	private VersionManager query = new VersionManager();
	private String op;
	
	public String list(){
		list = versionManagerService.getList(query);
		return LIST_PAGE;
	}
	
	public String modify(){
		op = "modify";
		query = versionManagerService.getList(query).get(0);
		String fileHtml = ImageTagHtml.getFileHtml("fileid", Type.FILE,"apk", 200, 180, 150,query.getFileid());
        getValueStack().set("fileHtml", fileHtml);
		return EDIT_PAGE;
	}
	
	public String add(){
		op = "add";
		String fileHtml = ImageTagHtml.getFileHtml("fileid", Type.FILE,"apk", 200, 180, 150,query.getFileid());
        getValueStack().set("fileHtml", fileHtml);
		return EDIT_PAGE;
	}
	
	public String save(){
		String fileid = getRequest().getParameter("fileid");
		query.setFileid(fileid);
		String fileURL = !StringUtil.isEmpty(fileid) ? 
				getImageHost()+"/file/attachement_download.html?model.guId="+fileid : "";
		query.setFileaddress(fileURL);
		if(StringUtil.isEmpty(query.getId())){
			versionManagerService.insert(query);
		}else{
			versionManagerService.update(query);
		}
		this.setSuccessMessage("操作成功！");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String control(){
		versionManagerService.updateStatusById(query);
		this.setSuccessMessage("操作成功！");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String remove(){
		versionManagerService.delete(query);
		this.setSuccessMessage("删除成功！");
		getValueStack().set(DATA, getMessage());
		return DATA;
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
	public void setVersionManagerService(IVersionManagerService versionManagerService) {
		this.versionManagerService = versionManagerService;
	}

	public IVersionManagerService getVersionManagerService() {
		return versionManagerService;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String getOp() {
		return op;
	}

	public void setList(PageList<VersionManager> list) {
		this.list = list;
	}

	public PageList<VersionManager> getList() {
		return list;
	}

	public VersionManager getQuery() {
		return query;
	}

	public void setQuery(VersionManager query) {
		this.query = query;
	}
	
}
