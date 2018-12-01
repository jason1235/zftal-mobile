package com.zfsoft.hrm.seniorreport.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


/**
 * 
 * @author ChenMinming
 * @date 2013-12-23
 * @version V1.0.0
 */
@XmlRootElement(name="reportInstance")
public class SeniorReportView {
	private String startTitle;
	private String reportTitle;
	private LinkedHashMap<ViewItem,List<Col>> itemValueMaps=new LinkedHashMap<ViewItem,List<Col>>();
	private List<BodyItem> bodyList;
	private List<ViewItem> titles=new ArrayList<ViewItem>();
	private List<ViewItem> left=new ArrayList<ViewItem>();
	
	@XmlElement(name = "reportTitle")
	public String getReportTitle() {
		return reportTitle;
	}
	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}
	
	@XmlElement(name = "startTitle")
	public String getStartTitle() {
		return startTitle;
	}
	public void setStartTitle(String startTitle) {
		this.startTitle = startTitle;
	}
	
	@XmlElement(name = "viewItem")
	@XmlElementWrapper(name = "titles")
	public List<ViewItem> getTitles() {
		return titles;
	}
	public void setTitles(List<ViewItem> titles) {
		this.titles = titles;
	}

	@XmlElement(name = "viewItem")
	@XmlElementWrapper(name = "left")
	public List<ViewItem> getLeft() {
		return left;
	}

	public void setLeft(List<ViewItem> left) {
		this.left = left;
	}
	
	@XmlElement(name = "bodyItme")
	@XmlElementWrapper(name = "bodyList")
	public List<BodyItem> getBodyList(){
		bodyList = new ArrayList<BodyItem>();
		if(itemValueMaps==null){
			return bodyList;
		}
		for(ViewItem item:itemValueMaps.keySet()){
			bodyList.add(new BodyItem(item.getItemId(), itemValueMaps.get(item)));
		}
		return bodyList;
	}
	
	public void fillMapfromBodyList(){
		List<ViewItem> list = findFloorList(left);
		Map<String, ViewItem> itemMap = new HashMap<String, ViewItem>();
		for (ViewItem viewItem : list) {
			itemMap.put(viewItem.getItemId(), viewItem);
		}
		for(BodyItem bodyItem:bodyList){
			bodyItem.fillColListByValueList();
			itemValueMaps.put(itemMap.get(bodyItem.getItemId()), bodyItem.getColList());
		}
	}
	/**
	 * 返回
	 */
	@XmlTransient
	public LinkedHashMap<ViewItem, List<Col>> getItemValueMaps() {
		return itemValueMaps;
	}

	public void setItemValueMaps(LinkedHashMap<ViewItem, List<Col>> itemValueMaps) {
		this.itemValueMaps = itemValueMaps;
	}
	

	private List<ViewItem> findFloorList(List<ViewItem> vwItemList){
		List<ViewItem>  list= new ArrayList<ViewItem>();
		for (ViewItem viewItem : vwItemList) {
			if(viewItem.getSubItem()==null||viewItem.getSubItem().isEmpty()){
				list.add(viewItem);
			}else{
				list.addAll(findFloorList(viewItem.getSubItem()));
			}
		}
		return list;
	}
	
	public void setBodyList(List<BodyItem> bodyList) {
		this.bodyList = bodyList;
	}

	
}
