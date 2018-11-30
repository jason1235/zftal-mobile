package com.zfsoft.hrm.baseinfo.code.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zfsoft.hrm.baseinfo.code.entities.Catalog;
import com.zfsoft.hrm.baseinfo.code.entities.Item;
import com.zfsoft.hrm.config.IConstants;
import com.zfsoft.util.base.StringUtil;


/** 
 * @ClassName: CodeUtil 
 * @Description: 代码库工具
 * @author jinjj
 * @date 2012-5-23 上午08:31:17 
 *  
 */
public class CodeUtil {

	private static Logger log = LoggerFactory.getLogger(CodeUtil.class);
	
	private static CodeLoader loader;
	
	/**
	 * 
	 * @Title: initialize 
	 * @Description:代码库初始化，加载所有编目和条目至内存，
	 * 				仅当代码库为空时执行，INFO级日志，显示加载耗时
	 */
	public static synchronized void initialize(){
		if(loader == null){
			long time = System.currentTimeMillis();
			loader = new CodeLoader();
			log.info("initialize cost time:"+(System.currentTimeMillis()-time)+"ms");
		}
	}
	
	public static synchronized void append(Catalog catalog,List<Item> itemList){
		loader.append(catalog,itemList);
	}
	
	/**
	 * 
	 * @Title: reload 
	 * @Description:代码库重载，INFO级日志，显示加载耗时
	 */
	public static synchronized void reload(){
		long time = System.currentTimeMillis();
		loader.reload();
		log.info("reload cost time:"+(System.currentTimeMillis()-time)+"ms");
	}
	
	/**
	 * 
	 * @Title: getItemValue 
	 * @Description: 获取代码名称
	 * @param catalogId 编目编号
	 * @param itemId 条目编号
	 * @return 当不存在或者未找到时返回原值
	 */
	public static String getItemValue2(String catalogId,String itemId){
		String s =getItemValue(catalogId,itemId);
		if(StringUtil.isEmpty(s))
			return itemId;
		return s;
	}
	
	/**
	 * 
	 * @Title: getItemValue 
	 * @Description: 获取代码名称
	 * @param catalogId 编目编号
	 * @param itemId 条目编号
	 * @return 当不存在或者未找到时返回null
	 */
	public static String getItemValue(String catalogId,String itemId){
		Item item = loader.getItem(catalogId, itemId);
		if(item != null){
			Catalog catalog = loader.getCatalog(catalogId);
			if(catalog.getIncludeParentNode()!=null && catalog.getIncludeParentNode()==1){
				return fullItemDescription(catalog,item);
			}else{
				return item.getDescription();
			}
		}
		return null;
	}
	
	private static String fullItemDescription(Catalog catalog,Item item){
		StringBuffer sb = new StringBuffer(item.getDescription());
		if(item.getHasParentNodeInfo()==0){//页节点不属性不包含父节点，则不读取父节点
			return sb.toString();
		}
		Item parent = loader.getItem(catalog.getGuid(), item.getParentId());
		if(parent != null){
				if(!StringUtils.isEmpty(catalog.getDelimiter()))
					sb.insert(0, catalog.getDelimiter());
				sb.insert(0, fullItemDescription(catalog,parent));
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * @Title: getItem 
	 * @Description: 获取条目对象
	 * @param catalogId
	 * @param itemId
	 * @return {@link Item}
	 */
	public static Item getItem(String catalogId,String itemId) {
		return loader.getItem(catalogId, itemId);
	}
	
	public static void delItem(Item entity) {
		loader.delItem(entity);
	}
	
	public static void updateItem(Item item){
		loader.updateItem(item);
	}
	public static void updateRoot(String rootName,LinkedHashMap<String,Item> root){
		loader.updateRoot(rootName,root);
	}
	public static void insertItem(Item item){
		loader.insertItem(item);
	}
	
	/**
	 * 
	 * @Title: insertCatalog 
	 * @Description: 新增编目时，同步缓存
	 * @param catalog
	 */
	public static void insertCatalog(Catalog catalog){
		loader.insertCatalog(catalog);
	}
	
	public static void updateCatalog(Catalog catalog){
		loader.updateCatalog(catalog);
	}
	
	public static void delCatalog(Catalog catalog){
		loader.delCatalog(catalog);
	}
	
	/**
	 * 获取编目对象
	 * @param catalogId
	 * @return
	 */
	public static Catalog getCatalog(String catalogId){
		return loader.getCatalog(catalogId);
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * 获取所有编目合集，LinkedHashMap
	 */
	public static Map<String,Catalog> getCatalogMap(){
		return (Map<String,Catalog>)loader.getCatalogMap().clone();
	}
	
	/**
	 * 
	 * @Title: reloadDataByCatalogId 
	 * @Description: 重新载入某个编目下的所有数据
	 * @param catalogId
	 */
	public static void reloadDataByCatalogId(String catalogId){
		loader.reloadDataByCatalogId(catalogId);
	}
	
	/**
	 * 获取子节点集合
	 * @param catalogId 编目ID
	 * @param parentId 条目父节点ID
	 * @return
	 */
	public static List<Item> getChildren(String catalogId,String parentId){
		List<Item> children = new ArrayList<Item>();
		List<Item> list = new ArrayList<Item>();
		Item cal=null;
		if(StringUtils.isEmpty(parentId)){
			cal = loader.getItem(catalogId, IConstants.ROOT);
		}else{
			cal = loader.getItem(catalogId, parentId);
		}
		if(cal==null){
			log.error("请确认输入编目是否正确："+catalogId+"  所属："+parentId);
			throw new RuntimeException("请确认输入编目是否正确");
		}else{
			list=cal.getChildren();
		}
		for(Item item:list){
			Item child = loader.getItem(catalogId, item.getGuid());
			children.add(child);
		}
		return children;
	}
	/**
	 * 获取可显示的子节点集合
	 * @param catalogId 编目ID
	 * @param parentId 条目父节点ID
	 * @return
	 */
	public static List<Item> getChildrenForVisable(String catalogId,String parentId){
		List<Item> children = new ArrayList<Item>();
		List<Item> list = new ArrayList<Item>();
		Item cal=null;
		if(StringUtils.isEmpty(parentId)){
			cal = loader.getItem(catalogId, IConstants.ROOT);
		}else{
			cal = loader.getItem(catalogId, parentId);
		}
		if(cal==null){
			log.error("请确认输入编目是否正确："+catalogId+"  所属："+parentId);
			throw new RuntimeException("请确认输入编目是否正确");
		}else{
			list=cal.getChildren();
		}
		for(Item item:list){
			Item child = loader.getItem(catalogId, item.getGuid());
			if(child.getVisible()==1){
				children.add(child);
			}
		}
		return children;
	}
	
	/**
	 * 
	 * @Title: getMapData 
	 * @Description: 获得目标编目的条目MAP集合
	 * @param catalog
	 * @return
	 */
	public static LinkedHashMap<String,Item> getMapData(Catalog catalog,List<Item> itemList){
		LinkedHashMap<String,Item> itemMap = new LinkedHashMap<String,Item>(itemList.size()>12?itemList.size():16);
		List<Item> root = new ArrayList<Item>();//存储顶层条目信息
		for (Item item : itemList) {
			item = generateSubItems(item,itemList);
			if(StringUtils.isEmpty(item.getParentId())){
				root.add(item);
			}
			itemMap.put(item.getGuid(), item);
		}
		Item rItem = new Item();
		rItem.setCatalogId(catalog.getGuid());
		rItem.setGuid(IConstants.ROOT);
		rItem.setChildren(root);
		itemMap.put(rItem.getGuid(), rItem);
		return itemMap;
	}
	
	/**
	 * 
	 * @Title: generateSubItems 
	 * @Description: 迭代数据，标明子节点集合
	 * @param item
	 * @param list
	 * @return
	 */
	public static Item generateSubItems(Item item,List<Item> list){
		List<Item> subs = new ArrayList<Item>();
		for(Item obj:list){
			if(item.getGuid().equals(IConstants.ROOT)){//判断是否为条目根节点
				if(StringUtils.isEmpty(obj.getParentId())){
					subs.add(obj);
				}
			}else{
				if(item.getGuid().equals(obj.getParentId())){
					subs.add(obj);
				}
			}
		}
		item.setChildren(subs);
		return item;
	}
	/**
	 * 获取编目所有叶子节点
	 * @param catalogId
	 */
	public static List<Item> getItemLeff(String catalogId) {
		List<Item> results=new ArrayList<Item>();
		for(Item item : loader.getMap().get(catalogId).values()){
			if(!item.getHasChild()){
				results.add(item);
			}
		}
		return results;
	}
	
	/**
	 * 获取编目下所有有效节点
	 * @param catalogId
	 * @return
	 */
	public static List<Item> getAvailableItems(String catalogId){
		List<Item> results=new ArrayList<Item>();
		Collection<Item> data = loader.getMap().get(catalogId).values();
		for(Item item : data){
			if(!item.getGuid().equals(IConstants.ROOT) && item.getChecked()==1){//可选中并排除根节点虚拟条目
				results.add(item);
			}
		}
		return results;
	}
	/**
	 * 根据子节点的编目ID反向获取整条编目List
	 * @param catalogId
	 * @param childId
	 * @return 
	 */
	public static List<Item> getReverseItemList(String catalogId,String childId)
	{
		List<Item> ids=new ArrayList<Item>();
		getReverseItemList(catalogId,childId,ids);
		return ids;
	}
	
	private static void getReverseItemList(String catalogId,String id,List<Item> list)
	{
		Item item=getItem(catalogId, id);
		if(item == null){
			return;
		}
		if(item.getParentId() != null&& !item.getParentId().equals(id))
		{
			getReverseItemList(catalogId,item.getParentId(),list);
		}
		list.add(item);
	}
}
