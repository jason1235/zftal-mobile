/**
 * 
 */
package com.zfsoft.mobile.news.action;

import java.util.HashMap;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.mobile.news.entity.SchoolScenery;
import com.zfsoft.mobile.news.entity.SchoolSceneryCatalog;
import com.zfsoft.mobile.news.service.ISchoolSceneryCatalogService;
import com.zfsoft.mobile.news.service.ISchoolSceneryService;
import com.zfsoft.mobile.services.dao.query.BusinessQuery;
import com.zfsoft.util.base.StringUtil;

/**
 * @author zhangxu
 * @description 学校风景类别控制器
 * @date 2017-5-10 上午09:59:27
 */
public class SchoolSceneryCatalogAction  extends HrmAction {

	private ISchoolSceneryCatalogService schoolSceneryCatalogService;
	private ISchoolSceneryService schoolSceneryService;
	private SchoolSceneryCatalog  query = new SchoolSceneryCatalog();
	private String op;
	
	
	public String list(){
		PageList<SchoolSceneryCatalog> list = new PageList<SchoolSceneryCatalog>();
		list = schoolSceneryCatalogService.getList(query);
		this.getValueStack().set("list", list);
		return "list";
	}
	
	public String edit(){
		if(!StringUtil.isEmpty(query.getSceneryCatalogId())){
			query = schoolSceneryCatalogService.getList(query).get(0);
			op = "modify";
		}else{
			op = "add";
		}
		return "edit";
	}
	
	public String save(){
		SchoolSceneryCatalog sceneryCatalogQuery = new SchoolSceneryCatalog();
		sceneryCatalogQuery.setSceneryCatalogName(query.getSceneryCatalogName());
		PageList<SchoolSceneryCatalog> list = schoolSceneryCatalogService.getList(sceneryCatalogQuery);
		if(list != null && list.size() > 0){
			this.setErrorMessage("存在类别名称相同的记录，请修改!");
			getValueStack().set(DATA, getMessage());
			return DATA;
		}
		if(StringUtil.isEmpty(query.getSceneryCatalogId())){
			schoolSceneryCatalogService.insert(query);
		}
		else
			schoolSceneryCatalogService.update(query);
	    
	    this.setSuccessMessage("操作成功！");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String updateActive(){
		SchoolScenery schoolScenery = new SchoolScenery();
		schoolScenery.setSceneryCatalogId(query.getSceneryCatalogId());
		schoolScenery.setIsActive("1");
		PageList<SchoolScenery> list = schoolSceneryService.getList(schoolScenery);
		if(list != null && list.size() > 0){
			this.setErrorMessage("不能停用，该类别下还有启用的校园风景记录!");
			getValueStack().set(DATA, getMessage());
			return DATA;
		}
		schoolSceneryCatalogService.update(query);
		this.setSuccessMessage("操作成功！");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String delete(){
		SchoolScenery schoolScenery = new SchoolScenery();
		schoolScenery.setSceneryCatalogId(query.getSceneryCatalogId());
		PageList<SchoolScenery> list = schoolSceneryService.getList(schoolScenery);
		if(list != null && list.size() > 0){
			this.setErrorMessage("不能停用，该类别下还有校园风景记录!");
			getValueStack().set(DATA, getMessage());
			return DATA;
		}
		schoolSceneryCatalogService.delete(query);
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
					map.put("sceneryCatalogId", ids[i]);
					schoolSceneryCatalogService.updateIndex(map);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return list();
	}
	
	
	public ISchoolSceneryCatalogService getSchoolSceneryCatalogService() {
		return schoolSceneryCatalogService;
	}
	public void setSchoolSceneryCatalogService(
			ISchoolSceneryCatalogService schoolSceneryCatalogService) {
		this.schoolSceneryCatalogService = schoolSceneryCatalogService;
	}
	public ISchoolSceneryService getSchoolSceneryService() {
		return schoolSceneryService;
	}
	public void setSchoolSceneryService(ISchoolSceneryService schoolSceneryService) {
		this.schoolSceneryService = schoolSceneryService;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String getOp() {
		return op;
	}

	public SchoolSceneryCatalog getQuery() {
		return query;
	}

	public void setQuery(SchoolSceneryCatalog query) {
		this.query = query;
	}
	
	
}
