package com.zfsoft.mobile.module.action;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.mobile.common.service.IMobileCommonService;
import com.zfsoft.mobile.common.utils.ImageTagHtml;
import com.zfsoft.mobile.module.entity.Module;
import com.zfsoft.mobile.module.query.ModuleQuery;
import com.zfsoft.mobile.module.service.IModuleService;
import com.zfsoft.util.base.StringUtil;

public class ModuleAction extends HrmAction {
	
	private IModuleService moduleService;
	
	private IMobileCommonService mobileCommonService;
	
	private PageList<Module> pageList;
	
	private ModuleQuery query = new ModuleQuery();
	
	private Module model;
	
	private String op = "add";
	
	private String moduleLogo;
	
	////////////////////////////////////////
	
	public String list() {
		pageList = moduleService.getPageList(query);
		return "list";
	}
	
	public String add() {
		this.getValueStack().set("imageHtml", ImageTagHtml.getImageHtml("moduleLogo", Type.IMAGE, 1024, 150, 150, null, true));
		return "edit";
	}
	
	public String modify() {
		op = "modify";
		model = moduleService.findById(query);
		this.getValueStack().set("imageHtml", ImageTagHtml.getImageHtml("moduleLogo", Type.IMAGE, 1024, 150, 150, model.getIcon(), true));
		return "edit";
	}
	
	public String save() {
		if ("add".equals(op)) {
			query.setCreator(getUser().getYhm());
		} else {
			query.setUpdater(getUser().getYhm());
		}
		if (!StringUtil.isEmpty(moduleLogo)) {
			query.setIcon(moduleLogo);
			query.setIconUrl(mobileCommonService.getUploadImagePath(moduleLogo));
		}
		try {
			moduleService.doSave(query);
			this.setSuccessMessage("操作成功");
		} catch (Exception e) {
			this.setErrorMessage(e.getMessage());
		}
		this.getValueStack().set("data", this.getMessage());
		return "data";
	}
	
	public String control() {
		try {
			moduleService.doControl(query);
			this.setSuccessMessage("操作成功");
		} catch (Exception e) {
			this.setErrorMessage(e.getMessage());
		}
		this.getValueStack().set("data", this.getMessage());
		return "data";
	}

	////////////////////////////////////////////
	public IModuleService getModuleService() {
		return moduleService;
	}

	public void setModuleService(IModuleService moduleService) {
		this.moduleService = moduleService;
	}

	
	public IMobileCommonService getMobileCommonService() {
		return mobileCommonService;
	}

	public void setMobileCommonService(IMobileCommonService mobileCommonService) {
		this.mobileCommonService = mobileCommonService;
	}

	public PageList<Module> getPageList() {
		return pageList;
	}

	public void setPageList(PageList<Module> pageList) {
		this.pageList = pageList;
	}

	public ModuleQuery getQuery() {
		return query;
	}

	public void setQuery(ModuleQuery query) {
		this.query = query;
	}

	public Module getModel() {
		return model;
	}

	public void setModel(Module model) {
		this.model = model;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String getModuleLogo() {
		return moduleLogo;
	}

	public void setModuleLogo(String moduleLogo) {
		this.moduleLogo = moduleLogo;
	}
	
}
