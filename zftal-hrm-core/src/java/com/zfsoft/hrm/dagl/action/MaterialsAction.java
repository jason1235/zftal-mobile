package com.zfsoft.hrm.dagl.action;

import java.util.Date;
import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.dagl.entity.ArchiveItem;
import com.zfsoft.hrm.dagl.entity.Archives;
import com.zfsoft.hrm.dagl.query.MaterialsAddItem;
import com.zfsoft.hrm.dagl.service.IMaterialsService;
import com.zfsoft.util.base.StringUtil;

/**
 * 
 * @author ZhangXu
 * @date 2015-06-09
 * @version V1.0.0
 */
public class MaterialsAction extends HrmAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -164266706477977692L;
	
	private IMaterialsService materialsService;
	
	private MaterialsAddItem query = new MaterialsAddItem();
	
	private MaterialsAddItem materials;
	
	private String op;
	
	private String asc;
	
	private String sortFieldName;
	
	private String userInput;
	
	public String getAsc() {
		return asc;
	}

	public void setAsc(String asc) {
		this.asc = asc;
	}

	public String getSortFieldName() {
		return sortFieldName;
	}

	public void setSortFieldName(String sortFieldName) {
		this.sortFieldName = sortFieldName;
	}

	public String page(){
		if(StringUtil.isNotEmpty(sortFieldName)){
			if(asc.equals("up")){
				query.setOrderStr( sortFieldName );
			}else{
				query.setOrderStr( sortFieldName +" desc");
			}
		}
		PageList<MaterialsAddItem> list = materialsService.getList(query);
		getValueStack().set("list", list);
		return "page";
	}
	
	public String removeData(){
		materialsService.removeData(materials.getClassId());
		setSuccessMessage("删除数据成功");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String toAdd(){
		PageList<ArchiveItem> archiveItemList = materialsService.getArchiveItemList(query);
		getValueStack().set("archiveItemList", archiveItemList);
		getValueStack().set("nowTime", new Date());
		op = "add";
		return "toAdd";
	}
	
	public String toModify(){
		query = new MaterialsAddItem();
		query.setClassId(materials.getClassId());
		List<MaterialsAddItem> materialsTemp = materialsService.getList(query);
		materials = materialsTemp.get(0);
		query = new MaterialsAddItem();
		PageList<ArchiveItem> archiveItemList = materialsService.getArchiveItemList(query);
		getValueStack().set("archiveItemList", archiveItemList);
		getValueStack().set("nowTime", new Date());
		op = "modify";
		return "toModify";
	}
	
	public String modifyData(){
		materialsService.update(materials);
		setSuccessMessage("更新数据成功");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String saveMaterials(){
		if(materials.getClassClid() == null){
			this.setErrorMessage("没有选择材料！");
	    	getValueStack().set(DATA, getMessage());
			return DATA;
		}
		materialsService.insert(materials);
		setSuccessMessage("添加数据成功");
		getValueStack().set(DATA, getMessage());
		return DATA;
			
	}

	public void setMaterialsService(IMaterialsService materialsService) {
		this.materialsService = materialsService;
	}

	public IMaterialsService getMaterialsService() {
		return materialsService;
	}

	public void setQuery(MaterialsAddItem query) {
		this.query = query;
	}

	public MaterialsAddItem getQuery() {
		return query;
	}

	public void setMaterials(MaterialsAddItem materials) {
		this.materials = materials;
	}

	public MaterialsAddItem getMaterials() {
		return materials;
	}

	public void setUserInput(String userInput) {
		this.userInput = userInput;
	}

	public String getUserInput() {
		return userInput;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String getOp() {
		return op;
	}

}
