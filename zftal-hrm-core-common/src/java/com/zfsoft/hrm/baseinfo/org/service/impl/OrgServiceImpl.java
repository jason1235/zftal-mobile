package com.zfsoft.hrm.baseinfo.org.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import com.zfsoft.common.system.SubSystemHolder;
import com.zfsoft.hrm.baseinfo.code.entities.Item;
import com.zfsoft.hrm.baseinfo.code.service.svcinterface.IItemDecorator;
import com.zfsoft.hrm.baseinfo.org.business.businessinterfaces.IOrgBusiness;
import com.zfsoft.hrm.baseinfo.org.entities.OrgInfo;
import com.zfsoft.hrm.baseinfo.org.query.OrgQuery;
import com.zfsoft.hrm.baseinfo.org.service.svcinterface.IOrgService;
import com.zfsoft.hrm.baseinfo.org.util.OrgUtil;
import com.zfsoft.hrm.config.Type;
import com.zfsoft.hrm.config.TypeFactory;
import com.zfsoft.hrm.config.type.OrgType;
import com.zfsoft.util.base.StringUtil;

public class OrgServiceImpl implements IOrgService,IItemDecorator {

	private IOrgBusiness orgBusiness;
	
	public void setOrgBusiness(IOrgBusiness orgBusiness) {
		this.orgBusiness = orgBusiness;
	}
	
	@Override
	public boolean add(OrgInfo info) {
		Assert.notNull(info, "新增信息不可为空");
		if(StringUtil.isEmpty(info.getOrderCode())){
			info.setOrderCode(info.getOid());
		}
		boolean res = orgBusiness.add(info);
		OrgUtil.changeItemsWhenAdd(info);
		return res;
	}

	@Override
	public boolean modify(OrgInfo info) {
		Assert.notNull(info, "修改信息不可为空");
		Assert.isTrue(!StringUtil.isEmpty(info.getOid()), "未指定任何信息");
		if(StringUtil.isEmpty(info.getOrderCode())){
			info.setOrderCode(info.getOid());
		}
		OrgInfo oldInfo = orgBusiness.getById(info.getOid());
		Assert.notNull(oldInfo, "未找到指定信息");
		boolean res = orgBusiness.modify(info);
		if(!StringUtil.isEmpty(oldInfo.getType()) && !oldInfo.getType().equals(info.getType())){
			res = res && updateType(info);
		}
		OrgUtil.changeItemsWhenModify(info);
		return res;
	}
	
	@Override
	public void remove(String oid) {
		Assert.isTrue(!StringUtil.isEmpty(oid), "未指定任何信息");
		Assert.isTrue(!orgBusiness.deleteJudge(oid), "该部门存在下级部门或者已被系统重要信息引用，不可删除");
		orgBusiness.remove(oid);
	}

	@Override
	public boolean disuse(String oid) {
		Assert.isTrue(!StringUtil.isEmpty(oid), "未指定任何信息");
		Assert.isTrue(!orgBusiness.disusedJudge(oid), "该部门存在下级部门，不可废除");
		Assert.isTrue(!orgBusiness.deleteBefore(oid), "该部门存在“在职”人员，不可废除");
		OrgInfo info = orgBusiness.getById(oid);
		Assert.notNull(info, "未找到指定信息");
		boolean res = orgBusiness.disuse(childrenList(info));
		OrgUtil.changeItemsWhenRemove(fillInfo(info));
		return res;
	}
	
	@Override
	public boolean use(String oid) {
		Assert.isTrue(!StringUtil.isEmpty(oid), "未指定任何信息");
		OrgInfo info = orgBusiness.getById(oid);
		Assert.notNull(info, "未找到指定信息");
		boolean res = orgBusiness.use(OrgUtil.listWithParent(info));
		OrgUtil.changeItemsWhenUse(info);
		return res; 
	}
	
	@Override
	public OrgInfo getById(String oid) {
		Assert.isTrue(!StringUtil.isEmpty(oid), "未指定任何信息");
		return orgBusiness.getById(oid);
	}

	@Override
	public List<OrgInfo> getList(OrgQuery query) {
		Assert.notNull(query, "查询信息不可为空");
		return OrgUtil.supplyChildren(orgBusiness.getList(query));
	}
	
	@Override
	public List<OrgInfo> getDisusedList(OrgQuery query) {
		Assert.notNull(query, "查询信息不可为空");
		return OrgUtil.supplyChildren(orgBusiness.getDisusedList(query));
	}

	@Override
	public List<OrgInfo> getPage(OrgQuery query) {
		return orgBusiness.getPage(query);
	}
	
	@Override
	public List<Item> getItemList() {
		List<Item> itemList = new ArrayList<Item>();
		itemList.addAll(getTypeItemList());
		itemList.addAll(getOrgItemList());
		return itemList;
	}
	
	//----------------------------------  工具方法    ----------------------------------------------------
	
	/**
	 * 修改制定部门及其子部门的部门类型
	 */
	private boolean updateType(OrgInfo info){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("type", info.getType());
		map.put("orgList", childrenList(info));
		return orgBusiness.updateType(map);
	}
	
	/**
	 * 获得指定部门及其以下子部门编号列表
	 * @param oid
	 * @return 
	 */
	private List<OrgInfo> childrenList(OrgInfo info){
		List<OrgInfo> keyList = new ArrayList<OrgInfo>();
		if(info == null || StringUtil.isEmpty(info.getOid())){
			return keyList;
		}
		keyList.add(info);
		OrgQuery query = new OrgQuery();
		query.setParent(info);
		query.setUseDisused(false);
		List<OrgInfo> sonlist = orgBusiness.getList(query);
		if(sonlist != null && sonlist.size() > 0){
			for (OrgInfo orgInfo : sonlist) {
				keyList.addAll(childrenList(orgInfo));
			}
		}
		
		return keyList;
	}
	
	private OrgInfo fillInfo(OrgInfo info){
		if(info == null || StringUtil.isEmpty(info.getOid())){
			return info;
		}
		OrgQuery query = new OrgQuery();
		query.setParent(info);
		query.setUseDisused(false);
		List<OrgInfo> sonlist = orgBusiness.getList(query);
		if(sonlist != null && sonlist.size() > 0){
			for (OrgInfo orgInfo : sonlist) {
				fillInfo(orgInfo);
			}
		}
		info.setChildren(sonlist);
		return info;
	}
	
	/**
	 * 获取部门类型Item包装类别表
	 * @return
	 */
	private List<Item> getTypeItemList(){
		List<Item> itemList = new ArrayList<Item>();
		Type[] types = TypeFactory.getTypes( OrgType.class );
		if( types == null || types.length == 0){
			return itemList;
		}
		for ( Type type : types ) {
			if(type != null){
				itemList.add(OrgUtil.changeOrgTypeToItem((OrgType) type));
			}
		}
		return itemList;
	}
	
	/**
	 * 获取部门Item包装类别表
	 * @return
	 */
	private List<Item> getOrgItemList(){
		List<Item> itemList = new ArrayList<Item>();
		List<OrgInfo> orgList = orgBusiness.getList(new OrgQuery());
		if(orgList == null || orgList.size() == 0){
			return itemList;
		}
		for (OrgInfo orgInfo : orgList) {
			if(orgInfo != null){
				itemList.add(OrgUtil.changeOrgToItem(orgInfo));
			}
		}
		return itemList;
	}

	@Override
	public OrgInfo getOrgTree(OrgQuery query) {
		List<OrgInfo> orgs=new ArrayList<OrgInfo>();
		List<OrgInfo> orgInfos= orgBusiness.findOrgList(query);
		List<Type> types= OrgUtil.getTypeList();
		//如果机构类型不为空，则只查找该机构类型下的机构
		if(StringUtils.isNotEmpty(query.getType())){
			boolean matched=false;
			for (Type type : types) {
				if(type.getName().equals(query.getType())){
					types=new ArrayList<Type>();
					types.add(type);
					matched=true;
					break;
				}
			}
			if(!matched){
				types=Collections.emptyList();
			}
		}
		//把机构类型转化为机构节点
		for (Type type : types) {
			OrgInfo orgInfo=new OrgInfo();
			orgInfo.setName(type.getText());
			orgInfo.setOid(type.getName());
			orgInfo.setChildren(new ArrayList<OrgInfo>());
			orgs.add(orgInfo);
		}
		//组织每个机构类型的机构树
		for (OrgInfo orgUpper : orgs) {
			for (OrgInfo orgInfo : orgInfos) {
				if(orgInfo.getParent()==null&&orgUpper.getOid().equals(orgInfo.getType())){
					orgUpper.getChildren().add(orgInfo);
					getChilds(orgInfos, orgInfo);
				}
			}			
		}
		//创建顶层节点
		OrgInfo root=new OrgInfo();
		String rootName=SubSystemHolder.getPropertiesValue("org_root");
		root.setName(rootName==null?"顶层机构":rootName);
		root.setOid("root");
		root.setChildren(orgs);
		return root;
	}
	
	private void getChilds(List<OrgInfo> orgInfos,OrgInfo parent){

		if(parent.getChildren()==null){
			parent.setChildren(new ArrayList<OrgInfo>());
		}
		for(OrgInfo orgInfo:orgInfos){
			if(orgInfo.getParent()!=null&&parent.getOid().equals(orgInfo.getParent().getOid())){
				parent.getChildren().add(orgInfo);
				getChilds(orgInfos,orgInfo);
			}
		}
	}
}
