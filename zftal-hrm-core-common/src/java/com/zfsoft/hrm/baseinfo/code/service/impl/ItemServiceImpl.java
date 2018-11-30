package com.zfsoft.hrm.baseinfo.code.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.hrm.baseinfo.code.dao.daointerface.ICatalogDao;
import com.zfsoft.hrm.baseinfo.code.dao.daointerface.IItemDao;
import com.zfsoft.hrm.baseinfo.code.entities.Catalog;
import com.zfsoft.hrm.baseinfo.code.entities.Item;
import com.zfsoft.hrm.baseinfo.code.query.CatalogQuery;
import com.zfsoft.hrm.baseinfo.code.query.ItemQuery;
import com.zfsoft.hrm.baseinfo.code.service.svcinterface.IItemService;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.config.IConstants;
import com.zfsoft.hrm.core.exception.RuleException;

/** 
 * @ClassName: ItemServiceImpl 
 * @Description: 代码条目服务类
 * @author jinjj
 * @date 2012-5-18 上午11:54:15 
 *  
 */
public class ItemServiceImpl implements IItemService {

	private IItemDao itemDao;
	private ICatalogDao catalogDao;
	
	@Override
	public int delete(List<Item> list) throws RuntimeException {
		int count=0;
		for(Item item:list){
			Item cache = CodeUtil.getItem(item.getCatalogId(), item.getGuid());
			if(cache.getChildren().size()>0){
				throw new RuleException("条目[编号:"+cache.getGuid()+" 名称:"+cache.getDescription()+"]包含子项，无法删除");
			}
			count += itemDao.delete(item);
		}
		for(Item item:list){
			CodeUtil.delItem(item);
		}
		return count;
		
	}
	
	@Override
	public List<Object> loadCode() throws RuntimeException {
		Map<String, LinkedHashMap<String, Item>> map = new LinkedHashMap<String,LinkedHashMap<String,Item>>();
		Map<String, Catalog> cMap = new LinkedHashMap<String,Catalog>();
		//迭代编目数据
		//TODO could use multi-threads
		List<Catalog> list = catalogDao.getList(new CatalogQuery());
		for (Catalog catalog : list) {
			LinkedHashMap<String,Item> itemMap = loadCodeByCatalogId(catalog.getGuid());
			map.put(catalog.getGuid(), itemMap);
			cMap.put(catalog.getGuid(), catalog);
		}
		
		List<Object> totalList = new ArrayList<Object>();
		totalList.add(map);
		totalList.add(cMap);
		return totalList;
	}
	
	public LinkedHashMap<String, Item> loadCodeByCatalogId(String catalogId){
		Catalog ca = new Catalog();
		ca.setGuid(catalogId);
		ItemQuery query = new ItemQuery();
		query.setCatalogId(ca.getGuid());
		List<Item> itemList = itemDao.getList(query);
		return CodeUtil.getMapData(ca,itemList);
	}
	
	
	@Override
	public Item getEntity(Item entity) throws RuntimeException {
		return itemDao.getEntity(entity);
	}

	@Override
	public List<Item> getList(ItemQuery query) throws RuntimeException {
		return itemDao.getList(query);
	}
	
	public List<Item> getTreeList(ItemQuery query)throws RuntimeException{
		List<Item> list = CodeUtil.getItem(query.getCatalogId(), IConstants.ROOT).getChildren();
		return getChildren(list);
	}
	
	private List<Item> getChildren(List<Item> list){//递归拼装子节点集合
		for(int i=0;i<list.size();i++){
			Item obj = list.get(i);
			Item cache = CodeUtil.getItem(obj.getCatalogId(), obj.getGuid());
			List<Item> children = cache.getChildren();
			children = getChildren(children);
			cache.setChildren(children);
			list.set(i, cache);
		}
		return list;
	}
	
	@Override
	public int updateOrder(List<Item> list) throws RuntimeException {
		int res = 0;
		for(Item item:list){
			res += itemDao.update(item);
		}
		Item obj = list.get(0);
		Item cache = CodeUtil.getItem(obj.getCatalogId(), obj.getGuid());//根据OBJ去内存获取完整信息
		ItemQuery query = new ItemQuery();
		query.setCatalogId(cache.getCatalogId());
		query.setParentId(cache.getParentId());
		List<Item> nodes = itemDao.getTopNodeList(query);
		for(Item node:nodes){
			Item temp = CodeUtil.getItem(node.getCatalogId(), node.getGuid());
			node.setChildren(temp.getChildren());
			CodeUtil.updateItem(node);
		}
		Item pCache;
		if(!StringUtils.isEmpty(cache.getParentId())){//判断条目是否为根节点
			pCache = CodeUtil.getItem(cache.getCatalogId(), cache.getParentId());
		}else{
			pCache = CodeUtil.getItem(cache.getCatalogId(), IConstants.ROOT);
		}
		CodeUtil.generateSubItems(pCache, nodes);
		CodeUtil.updateItem(pCache);
		return res;
	}

	@Override
	public int insert(Item entity) throws RuntimeException {
		int count = itemDao.insert(entity);
		CodeUtil.insertItem(entity);
		return count;
	}

	@Override
	public int update(Item entity) throws RuntimeException {
		int count = itemDao.update(entity);
		Item old = CodeUtil.getItem(entity.getCatalogId(), entity.getGuid());
		entity.setChildren(old.getChildren());
		CodeUtil.updateItem(entity);
		return count;
	}
	
	@Override
	public int insertList(List<Item> list) throws RuntimeException {
		int cnt=0;
		for(Item item:list){
			cnt += itemDao.insert(item);
		}
		if(cnt>0){
			CodeUtil.reloadDataByCatalogId(list.get(0).getCatalogId());
		}
		return cnt;
	}

	public void setItemDao(IItemDao itemDao) {
		this.itemDao = itemDao;
	}

	public void setCatalogDao(ICatalogDao catalogDao) {
		this.catalogDao = catalogDao;
	}
}
