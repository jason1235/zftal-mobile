package com.zfsoft.hrm.baseinfo.code.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.hrm.baseinfo.code.entities.Catalog;
import com.zfsoft.hrm.baseinfo.code.entities.Item;
import com.zfsoft.hrm.baseinfo.code.service.svcinterface.IItemService;
import com.zfsoft.hrm.config.IConstants;

/**
 * 代码库加载类 
 * @author jinjj
 * @date 2012-5-23 上午10:36:52 
 *  
 */
public class CodeLoader {

	private IItemService itemService;
	private Map<String,LinkedHashMap<String,Item>> map;
	private Map<String,Catalog> cMap;
	Log log = LogFactory.getLog(CodeLoader.class);

	public CodeLoader(){
		itemService = (IItemService)SpringHolder.getBean("baseCodeItemService");
		load();
	}
	
	/**
	 * 
	 * @Title: load 
	 * @Description: 加载代码库信息
	 */
	@SuppressWarnings("unchecked")
	public void load(){
		List<Object> list = itemService.loadCode();
		if(list.size()<2){
			throw new RuntimeException("加载代码库出错");
		}
		if(map==null){
			map = (Map<String,LinkedHashMap<String,Item>>)list.get(0);
			if(map.size()==0){
				log.warn("代码库条目加载完毕,但数据量为0");
			}
		}
		if(cMap==null){
			cMap = (LinkedHashMap<String,Catalog>)list.get(1);
			if(map.size()==0){
				log.warn("代码库编目加载完毕,但数据量为0");
			}
		}
	}
	/**
	 * 追加代码
	 * @param items
	 */
	public void append(Catalog catalog,List<Item> itemList) {
		LinkedHashMap<String,Item> itemOrgMap = CodeUtil.getMapData(catalog, itemList);;
		map.put(catalog.getGuid(), itemOrgMap);
		cMap.put(catalog.getGuid(), catalog);
		;
	}
	
	/**
	 * 
	 * @Title: reload 
	 * @Description:重新加载代码库信息
	 */
	@SuppressWarnings("unchecked")
	public void reload(){
		List<Object> list = itemService.loadCode();
		map = (Map<String,LinkedHashMap<String,Item>>)list.get(0);
		cMap = (Map<String,Catalog>)list.get(1);
		if(map.size()==0){
			log.warn("代码库加载完毕,但数据量为0");
		}
	}
	
	public Item getItem(String catalogId, String itemId){
		//参数不全，返回null
		if(StringUtils.isEmpty(catalogId)||StringUtils.isEmpty(itemId)){
			return null;
		}
		Catalog catalog = cMap.get(catalogId);
		if(catalog == null){
			return null;
		}
		Item item = map.get(catalogId).get(itemId);
		return item;
	}
	
	public void updateItem(Item entity){
		map.get(entity.getCatalogId()).put(entity.getGuid(), entity);
	}
	
	public void updateRoot(String rootName,LinkedHashMap<String,Item> root){
		map.put(rootName, root);
	}
	
	public void insertItem(Item entity){
		List<Item> initChildren = new ArrayList<Item>();
		entity.setChildren(initChildren);
		Item pItem;
		if(StringUtils.isEmpty(entity.getParentId())){//获取父节点
			pItem = map.get(entity.getCatalogId()).get(IConstants.ROOT);
		}else{
			pItem = map.get(entity.getCatalogId()).get(entity.getParentId());
		}
		pItem.getChildren().add(entity);//更新父节点所属子节点集合
		if(StringUtils.isEmpty(entity.getParentId())){
			map.get(entity.getCatalogId()).put(IConstants.ROOT,pItem);
		}else{
			map.get(entity.getCatalogId()).put(entity.getParentId(),pItem);
		}
		map.get(entity.getCatalogId()).put(entity.getGuid(),entity);
	}
	
	public void delItem(Item entity){//实体仅包含GUID及编目ID
		Item item = map.get(entity.getCatalogId()).get(entity.getGuid());
		Item pItem;
		if(StringUtils.isEmpty(item.getParentId())){//获取父节点
			pItem = map.get(entity.getCatalogId()).get(IConstants.ROOT);
		}else{
			pItem = map.get(entity.getCatalogId()).get(item.getParentId());
		}
		removeSub(pItem.getChildren(),entity.getGuid());//修改父节点所属子节点集合
		if(StringUtils.isEmpty(item.getParentId())){
			map.get(entity.getCatalogId()).put(IConstants.ROOT,pItem);
		}else{
			map.get(entity.getCatalogId()).put(item.getParentId(),pItem);
		}
		map.get(entity.getCatalogId()).remove(entity.getGuid());
	}
	
	private void removeSub(List<Item> list,String itemId){
		for(int i=0;i<list.size();i++){
			Item item = list.get(i);
			if(item.getGuid().equals(itemId)){
				list.remove(i);
				break;
			}
		}
	}
	
	public void insertCatalog(Catalog entity){
		LinkedHashMap<String,Item> store = new LinkedHashMap<String,Item>();
		Item root = new Item();
		root.setChildren(new ArrayList<Item>());
		root.setCatalogId(entity.getGuid());
		root.setGuid(IConstants.ROOT);
		store.put(root.getGuid(), root);
		map.put(entity.getGuid(), store);
		cMap.put(entity.getGuid(), entity);
	}
	
	/**
	 * 
	 * @Title: updateCatalog 
	 * @Description:更新编目 
	 * @param entity
	 */
	public void updateCatalog(Catalog entity){
		cMap.put(entity.getGuid(),entity);
	}
	
	/**
	 * 
	 * @Title: delCatalog 
	 * @Description: 删除编目及其条目缓存
	 * @param entity
	 */
	public void delCatalog(Catalog entity){
		cMap.remove(entity.getGuid());
		map.remove(entity.getGuid());
	}
	
	public Catalog getCatalog(String catalogId){
		return cMap.get(catalogId);
	}
	
	public LinkedHashMap<String,Catalog> getCatalogMap(){
		return (LinkedHashMap<String,Catalog>)cMap;
	}
	
	public void reloadDataByCatalogId(String catalogId){
		LinkedHashMap<String,Item> fleshData = itemService.loadCodeByCatalogId(catalogId);
		map.put(catalogId, fleshData);
	}

	public Map<String, LinkedHashMap<String, Item>> getMap() {
		return map;
	}
	
}
