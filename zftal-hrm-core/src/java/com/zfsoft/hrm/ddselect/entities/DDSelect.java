package com.zfsoft.hrm.ddselect.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.zfsoft.common.query.QueryModel;

/**
 * 
 * @author yxlong
 * 2013-8-2
 */
public class DDSelect extends QueryModel implements Serializable {

	private static final long serialVersionUID = 7866503534990130994L;
	
	private String name;
	
	private String chineseName;
	
	private String queryName;

	private String exportIdStr; // 导出id

	private List<Fields> fields = new ArrayList<Fields>();
	
	private List<Keys> keys = new ArrayList<Keys>();
	
	private List<Indexs> indexs = new ArrayList<Indexs>();
	
	private List<PFkeyRelation> pfkeyRelations = new ArrayList<PFkeyRelation>();
	
	public DDSelect(){
		super.setShowCount(16);
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the chineseName
	 */
	public String getChineseName() {
		return chineseName;
	}

	/**
	 * @param chineseName the chineseName to set
	 */
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	/**
	 * @return the queryName
	 */
	public String getQueryName() {
		return queryName;
	}

	/**
	 * @param queryName the queryName to set
	 */
	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}
	/**
	 * @return the exportIdStr
	 */
	public String getExportIdStr() {
		return exportIdStr;
	}
	/**
	 * @param exportIdStr the exportIdStr to set
	 */
	public void setExportIdStr(String exportIdStr) {
		this.exportIdStr = exportIdStr;
	}
	/**
	 * @return the fields
	 */
	public List<Fields> getFields() {
		return fields;
	}
	/**
	 * @param fields the fields to set
	 */
	public void setFields(List<Fields> fields) {
		this.fields = fields;
	}
	/**
	 * @return keys : return the property keys.
	 */
	
	public List<Keys> getKeys() {
		return keys;
	}
	/**
	 * @param keys : set the property keys.
	 */
	
	public void setKeys(List<Keys> keys) {
		this.keys = keys;
	}
	/**
	 * @return indexs : return the property indexs.
	 */
	
	public List<Indexs> getIndexs() {
		return indexs;
	}
	/**
	 * @param indexs : set the property indexs.
	 */
	
	public void setIndexs(List<Indexs> indexs) {
		this.indexs = indexs;
	}
	/**
	 * @return pfkeyRelations : return the property pfkeyRelations.
	 */
	
	public List<PFkeyRelation> getPfkeyRelations() {
		return pfkeyRelations;
	}
	/**
	 * @param pfkeyRelations : set the property pfkeyRelations.
	 */
	
	public void setPfkeyRelations(List<PFkeyRelation> pfkeyRelations) {
		this.pfkeyRelations = pfkeyRelations;
	}
	
}
