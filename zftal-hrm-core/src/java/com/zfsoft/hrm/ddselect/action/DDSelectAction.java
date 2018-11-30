package com.zfsoft.hrm.ddselect.action;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ModelDriven;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.ddselect.entities.DDSelect;
import com.zfsoft.hrm.ddselect.entities.Fields;
import com.zfsoft.hrm.ddselect.entities.Indexs;
import com.zfsoft.hrm.ddselect.entities.Keys;
import com.zfsoft.hrm.ddselect.entities.PFkeyRelation;
import com.zfsoft.hrm.ddselect.service.svcinterface.IDDSelectService;
import com.zfsoft.util.base.StringUtil;

/**
 * 
 * @author yxlong
 * 2013-8-2
 */
public class DDSelectAction extends HrmAction implements ModelDriven<DDSelect> {

	private static final long serialVersionUID = -7252179719785397079L;

	private DDSelect model = new DDSelect();
	
	private PageList<DDSelect> pageList;
	private List<Fields> list = new ArrayList<Fields>();
	private List<Keys> keyList = new ArrayList<Keys>();
	private List<Indexs> indexList = new ArrayList<Indexs>();
	private List<PFkeyRelation> relationList = new ArrayList<PFkeyRelation>();
	private List<String> ids = new ArrayList<String>();
	private IDDSelectService ddSelectService;
	
	public String list(){
		model.setQueryName(StringUtil.upperCase(model.getQueryName()));
		pageList = ddSelectService.getPagedTable(model);
		return "list";
	}
	
	public String detail(){
		list = ddSelectService.getField(model.getName());
		keyList = ddSelectService.getKey(model.getName());
		indexList = ddSelectService.getIndex(model.getName());
		relationList = ddSelectService.getPFkeyRelation(model.getName());
		return "detail";
	}
	
	public String export(){
		model.setShowCount(Integer.MAX_VALUE);
		StringBuffer str = new StringBuffer();
		for(String id : ids){
			str.append("'");
			str.append(id);
			str.append("',");
		}
		String exportStrId = str.toString();
		model.setExportIdStr(StringUtil.isEmpty(exportStrId) ? null : exportStrId.substring(0,exportStrId.length() - 1));
		list();
		return "export";
	}
	
	@Override
	public DDSelect getModel() {
		return model;
	}
	
	/**
	 * @return the pageList
	 */
	public PageList<DDSelect> getPageList() {
		return pageList;
	}
	/**
	 * @param pageList the pageList to set
	 */
	public void setPageList(PageList<DDSelect> pageList) {
		this.pageList = pageList;
	}
	/**
	 * @return the ddSelectService
	 */
	public IDDSelectService getDdSelectService() {
		return ddSelectService;
	}
	/**
	 * @param ddSelectService the ddSelectService to set
	 */
	public void setDdSelectService(IDDSelectService ddSelectService) {
		this.ddSelectService = ddSelectService;
	}
	/**
	 * @param model the model to set
	 */
	public void setModel(DDSelect model) {
		this.model = model;
	}

	/**
	 * @return the list
	 */
	public List<Fields> getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(List<Fields> list) {
		this.list = list;
	}

	/**
	 * @return the ids
	 */
	public List<String> getIds() {
		return ids;
	}

	/**
	 * @param ids the ids to set
	 */
	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	/**
	 * @return keyList : return the property keyList.
	 */
	
	public List<Keys> getKeyList() {
		return keyList;
	}

	/**
	 * @param keyList : set the property keyList.
	 */
	
	public void setKeyList(List<Keys> keyList) {
		this.keyList = keyList;
	}

	/**
	 * @return indexList : return the property indexList.
	 */
	
	public List<Indexs> getIndexList() {
		return indexList;
	}

	/**
	 * @param indexList : set the property indexList.
	 */
	
	public void setIndexList(List<Indexs> indexList) {
		this.indexList = indexList;
	}

	/**
	 * @return relationList : return the property relationList.
	 */
	
	public List<PFkeyRelation> getRelationList() {
		return relationList;
	}

	/**
	 * @param relationList : set the property relationList.
	 */
	
	public void setRelationList(List<PFkeyRelation> relationList) {
		this.relationList = relationList;
	}

}
