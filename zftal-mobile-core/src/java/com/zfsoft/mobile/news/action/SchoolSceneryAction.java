/**
 * 
 */
package com.zfsoft.mobile.news.action;

import java.util.HashMap;
import java.util.Map;

import com.common.Config;
import com.zfsoft.common.system.BaseHolder;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.mobile.common.service.IMobileCommonService;
import com.zfsoft.mobile.common.utils.ImageTagHtml;
import com.zfsoft.mobile.news.entity.SchoolScenery;
import com.zfsoft.mobile.news.entity.SchoolSceneryCatalog;
import com.zfsoft.mobile.news.service.ISchoolSceneryCatalogService;
import com.zfsoft.mobile.news.service.ISchoolSceneryService;
import com.zfsoft.util.base.StringUtil;

/**
 * @author zhangxu
 * @description 
 * @date 2017-5-10 上午10:09:40
 */
public class SchoolSceneryAction extends HrmAction  {

	private ISchoolSceneryService schoolSceneryService;
	private ISchoolSceneryCatalogService schoolSceneryCatalogService;
	private IMobileCommonService mobileCommonService;
	private SchoolScenery query = new SchoolScenery();
	private String op;
	private String sceneryLogo;
	
	public String list(){
		PageList<SchoolScenery> list = new PageList<SchoolScenery>();
		list = schoolSceneryService.getList(query);
		this.getValueStack().set("list", list);
		PageList<SchoolSceneryCatalog> sceneryCatalogList = schoolSceneryCatalogService.getList(new SchoolSceneryCatalog());
		this.getValueStack().set("sceneryCatalogList", sceneryCatalogList);
		return "list";
	}
	
	public String edit(){
		if(!StringUtil.isEmpty(query.getSceneryId())){
			query = schoolSceneryService.getList(query).get(0);
			getValueStack().set("imageHtml", ImageTagHtml.getImageHtml("sceneryLogo", Type.IMAGE, 1024, 90, 90, query.getSceneryPicId(), true));
			op = "modify";
		}else{
			getValueStack().set("imageHtml", ImageTagHtml.getImageHtml("sceneryLogo", Type.IMAGE, 1024, 90, 90, null, true));
			op = "add";
		}
		PageList<SchoolSceneryCatalog> sceneryCatalogList = schoolSceneryCatalogService.getList(new SchoolSceneryCatalog());
		this.getValueStack().set("sceneryCatalogList", sceneryCatalogList);
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
	
	public String save(){
		if (!StringUtil.isEmpty(sceneryLogo)) {
			query.setSceneryPicId(sceneryLogo);
			query.setSceneryPicUrl(getImageHost()+mobileCommonService.getUploadImagePath(sceneryLogo));
		}else{
			this.setErrorMessage("请上传图片!");
			getValueStack().set(DATA, getMessage());
			return DATA;
		}
		if(op.equals("add")){
			schoolSceneryService.insert(query);
		}
		else
			schoolSceneryService.update(query);
	    
	    this.setSuccessMessage("操作成功！");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String delete(){
		schoolSceneryService.delete(query);
		this.setSuccessMessage("操作成功！");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String updateActive(){
		
		schoolSceneryService.update(query);
		this.setSuccessMessage("操作成功！");
		getValueStack().set(DATA, getMessage());
		return DATA;
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
					map.put("sortNumber", (i+min)+"");
					map.put("sceneryId", ids[i]);
					schoolSceneryService.updateIndex(map);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return list();
	}
	
	
	
	

	public IMobileCommonService getMobileCommonService() {
		return mobileCommonService;
	}

	public void setMobileCommonService(IMobileCommonService mobileCommonService) {
		this.mobileCommonService = mobileCommonService;
	}

	public String getSceneryLogo() {
		return sceneryLogo;
	}

	public void setSceneryLogo(String sceneryLogo) {
		this.sceneryLogo = sceneryLogo;
	}

	public SchoolScenery getQuery() {
		return query;
	}

	public void setQuery(SchoolScenery query) {
		this.query = query;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public ISchoolSceneryService getSchoolSceneryService() {
		return schoolSceneryService;
	}

	public void setSchoolSceneryService(ISchoolSceneryService schoolSceneryService) {
		this.schoolSceneryService = schoolSceneryService;
	}

	public void setSchoolSceneryCatalogService(
			ISchoolSceneryCatalogService schoolSceneryCatalogService) {
		this.schoolSceneryCatalogService = schoolSceneryCatalogService;
	}

	public ISchoolSceneryCatalogService getSchoolSceneryCatalogService() {
		return schoolSceneryCatalogService;
	}
	
	
}
