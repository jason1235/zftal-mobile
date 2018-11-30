package com.zfsoft.hrm.authpost.post.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import com.zfsoft.hrm.authpost.post.dao.daointerface.IPostInfoDao;
import com.zfsoft.hrm.authpost.post.entities.PostInfo;
import com.zfsoft.hrm.authpost.post.service.svcinterface.IPostInfoService;
import com.zfsoft.hrm.baseinfo.code.entities.Item;
import com.zfsoft.hrm.baseinfo.code.service.svcinterface.IItemDecorator;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.baseinfo.org.entities.OrgInfo;
import com.zfsoft.hrm.baseinfo.org.query.OrgQuery;
import com.zfsoft.hrm.config.ICodeConstants;
/**
 * 
 * @author 沈鲁威 Patrick Shen
 * @since 2012-7-25
 * @version V1.0.0
 */
public class PostInfoServiceImpl implements IPostInfoService,IItemDecorator {
	private IPostInfoDao dao;

	public void setDao(IPostInfoDao dao) {
		this.dao = dao;
	}

	@Override
	public List<PostInfo> getListByType(String typeCode) {
		return dao.findListByType(typeCode);
	}

	@Override
	public PostInfo getById(String guid) {
		return dao.findById(guid);
	}

	@Override
	public void add(PostInfo entity) {
		List<PostInfo> postInfos=dao.findListByType(entity.getTypeCode());
		int i=0;
		for(PostInfo info : postInfos){
			info.setSort(i);
			dao.update(info);
			i++;
		}
		entity.setSort(postInfos.size());
		try {
			dao.insert(entity);
		} catch (Exception e) {
			throw new RuntimeException("岗位编码重复");
		}
		
		reload();
	}

	@Override
	public void modify(PostInfo entity) {
		dao.update(entity);
		reload();
	}
	@Override
	public void modify(List<PostInfo> entitys) {
		for(PostInfo entity : entitys){
			dao.update(entity);
		}
		reload();
	}

	@Override
	public void remove(String guid) {
		try {
			dao.delete(guid);
		} catch (Exception e) {
			throw new RuntimeException("岗位可能被引用，请确认");
		}
		reload();
	}
	
	@Override
	public void sort(String type, String id) {
		PostInfo postInfo=dao.findById(id);
		List<PostInfo> postInfos=dao.findListByType(postInfo.getTypeCode());
		for(int i=0;i < postInfos.size(); i++){
			PostInfo info=postInfos.get(i);
			info.setSort(i);
			if(postInfo.getId().equals(info.getId())){
				if(type.equals("up")){
					if(i<=0)return;
					else{
						postInfos.get(i-1).setSort(i+1);
						dao.update(postInfos.get(i-1));
						info.setSort(i-1);
					}
				}else{
					if(i>=postInfos.size()-1)return;
					else{
						postInfos.get(i+1).setSort(i);
						dao.update(postInfos.get(i+1));
						info.setSort(i+1);
						dao.update(info);
						i++;i++;
						continue;
					}
				}
			}
			dao.update(info);
		}
		reload();
	}
	
	/**
	 * The method of changing OrganizationType type to Item type
	 * @author yongjun.fang
	 * @param org OrganizeType type
	 * @param catalogId
	 * @return the Item belongs to the organizationType
	 */
	public Item changeItemTypeToItem(Item type){
		if(type == null){
			return null;
		}
		String catalogId = ICodeConstants.DM_POSTINFO;
		Item item = new Item();
		item.setCatalogId(catalogId);
		item.setGuid(ICodeConstants.POSTINFO_PREFIX+type.getGuid());
		item.setDescription(type.getDescription());
		item.setHasParentNodeInfo(0);
		item.setChecked(1);
		item.setDumped(0);
		item.setVisible(1);
		
		return item;
	}
	public Item changePostToItem(PostInfo postInfo){
		if(postInfo == null){
			return null;
		}
		String catalogId = ICodeConstants.DM_POSTINFO;
		Item item = new Item();
		item.setGuid(postInfo.getId());
		item.setDescription(postInfo.getName());
		item.setCatalogId(catalogId);
		item.setParentId(ICodeConstants.POSTINFO_PREFIX+postInfo.getTypeCode());
		item.setHasParentNodeInfo(1);
		item.setChecked(1);
		item.setDumped(0);
		item.setVisible(1);
		
		return item;
	}
	
	
	@Override
	public List<Item> getItemList() {
		//List<Item> typeItems=CodeUtil.getChildren(ICodeConstants.DM_DEF_WORKPOST, null);
		List<Item> itemList = new ArrayList<Item>();
		//itemList.addAll(getPostTypeItemList(typeItems));
		//itemList.addAll(getPostItemList(typeItems));
		return itemList;
	}

	/**
	 * Get the item list that changed by OrganizationType
	 * @param typeItems 
	 * @param catalogId
	 * @return
	 */
	public List<Item> getPostTypeItemList(List<Item> typeItems){
		if(typeItems == null || typeItems.size() == 0){
			return null;
		}
		List<Item> itemList = new ArrayList<Item>();
		Iterator<Item> it = typeItems.iterator();
		while(it.hasNext()){
			Item type = it.next();
			Item item = changeItemTypeToItem(type);
			itemList.add(item);
		}
		return itemList;
	}
	
	/**
	 * Get the item list that changed by OrganizeModel
	 * @param catalogId
	 * @return
	 */
	public List<Item> getPostItemList(List<Item> typeItems){
		if(typeItems == null || typeItems.size() == 0){
			return null;
		}
		List<Item> itemList = new ArrayList<Item>();
		for(Item item:typeItems){
			for(PostInfo postInfo : dao.findListByType(item.getGuid())){
				itemList.add(changePostToItem(postInfo));
			}
		}
		return itemList;
	}
	/**
	 * 
	 */
	private void reload(){
		LinkedHashMap<String,Item> root=
			CodeUtil.getMapData(CodeUtil.getCatalog(ICodeConstants.DM_POSTINFO), getItemList());
		CodeUtil.updateRoot(ICodeConstants.DM_POSTINFO,root);
	}

	@Override
	public List<OrgInfo> getDisusedList(OrgQuery query) {
		// TODO Auto-generated method stub
		return null;
	}
}
