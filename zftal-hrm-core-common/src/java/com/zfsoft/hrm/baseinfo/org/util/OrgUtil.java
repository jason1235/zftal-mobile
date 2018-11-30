package com.zfsoft.hrm.baseinfo.org.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import org.springframework.util.Assert;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.common.system.SubSystemHolder;
import com.zfsoft.dao.entities.YhglModel;
import com.zfsoft.dataprivilege.dto.DeptFilter;
import com.zfsoft.dataprivilege.filter.impl.DealDeptFilter;
import com.zfsoft.dataprivilege.util.DataFilterUtil;
import com.zfsoft.hrm.baseinfo.code.entities.Item;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.util.DynaBeanUtil;
import com.zfsoft.hrm.baseinfo.org.entities.OrgInfo;
import com.zfsoft.hrm.config.ICodeConstants;
import com.zfsoft.hrm.config.Type;
import com.zfsoft.hrm.config.TypeFactory;
import com.zfsoft.hrm.config.type.OrgType;
import com.zfsoft.util.base.StringUtil;

public class OrgUtil {
	
	/**
	 * 获取全部部门类型的有序序列
	 * @return
	 */
	public static List<Type> getTypeList(){
		Type[] types = TypeFactory.getTypes( OrgType.class );
		List<Type> typeList = new ArrayList<Type>();
		if(types.length > 0){
			for (Type type : types) {
				typeList.add(type);
			}
		}
		Collections.sort(typeList, new OrgUtil().new CompType());
		return typeList;
	}
	
	/**
	 * 获得指定部门及其所有上级部门编号列表
	 * @param oid
	 * @return
	 */
	public static List<OrgInfo> listWithParent(OrgInfo info){
		List<OrgInfo> keyList = new ArrayList<OrgInfo>();
		if(info == null || StringUtil.isEmpty(info.getOid())){
			return keyList;
		}
		keyList.add(info);
		if(info.getParent() != null && !StringUtil.isEmpty(info.getParent().getOid())){
			keyList.addAll(listWithParent(info.getParent()));
		}
		return keyList;
	}
	
	/**
	 * 返回查询结果中的一级部门列表，每个一级部门中包含其所辖二级部门子序列
	 * 这个方法的大意是，先将参数列表中的一级节点和非一级节点分别放在一级list、set
	 * 和非一级list、set中；然后将非一级list中的每个节点的父节点逐层拆出来，将拆到最后的
	 * 一级节点放到一级list和set中（如已包含在一级set中则不放），其他的非一级节点放到
	 * 非一级list和set中（如已包含在非一级set中则不放）；最后遍历一级节点列表，并在非一级
	 * 列表中查找每个一级节点的子部门，排序后放入其子序列（sonList属性）中，从而得到深度
	 * 为两层的组织机构节点树，以便于页面展示。
	 * @param list 待处理list
	 * @return
	 */
	public static List<OrgInfo> supplyChildren(List<OrgInfo> list){
		List<OrgInfo> list_head = new ArrayList<OrgInfo>();//用于存储一级部门节点
		List<OrgInfo> list_not_null = new ArrayList<OrgInfo>();//用于存储二级及以后节点
		//将参数列表中的节点分到两个list中
		operateTwoLists(list, list_head, list_not_null);
		//将二级列表中的节点依照父节点放入一级列表节点的子序列中并排序
		for (OrgInfo orgInfo : list_head) {
			List<OrgInfo> separateList = new ArrayList<OrgInfo>();
			for (OrgInfo org : list_not_null) {
				if(org.getParent().getOid().equals(orgInfo.getOid())){
					separateList.add(org);
				}
			}
			sort(separateList);
			orgInfo.setChildren(separateList);
		}
		return getOneSelfDept(list_head);
	}
	
	private static List<OrgInfo> getOneSelfDept(List<OrgInfo> list){
		String userid = SessionFactory.getUser().getYhm();
		if(userid == null || "".equals(userid)){
			Assert.isTrue(false, "当前您还未登陆");
		}
		String on_off =SubSystemHolder.getPropertiesValue("org_scope_on_off");
		if((!((StringUtil.isNotEmpty(on_off) && "on".equalsIgnoreCase(on_off))))||YhglModel.INNER_USER_ADMIN.equals(userid)){
			return list;
		}
		List<DeptFilter> deptFilters = (List<DeptFilter>) DataFilterUtil.getCondition("bmgl");
		List<String> orgList = null;
		for(DeptFilter deptFilter : deptFilters){
			if(SessionFactory.getUser().getJsdms().indexOf(deptFilter.getRoleId()) != -1){
				if(DealDeptFilter.TYPE_SELF.equals(deptFilter.getDataType())){
//					orgList.add(getOwnerOrg(getUser().getYhm()));
					break;
				}
				if(DealDeptFilter.TYPE_ALL.equals(deptFilter.getDataType())){
					return list;
				}
				orgList = deptFilter.getOrgList();
				break;
			}
		}

		List<OrgInfo> listTemp = new ArrayList<OrgInfo>();
		if(orgList == null||orgList.isEmpty()){
			DynaBean selfInfo = DynaBeanUtil.getPerson(userid);
			if( selfInfo == null ) {
				Assert.isTrue(false, "系统没有您的信息");
			}
			
			String selfOrg = (String)selfInfo.getValue("dwm");
			if(selfOrg == null || "".equals(selfOrg)){
				Assert.isTrue(false, "您没有部门信息");
			}
			orgList = new ArrayList<String>();
			orgList.add(selfOrg);
		}
		for (String org : orgList) {			
			for(OrgInfo orgInfo : list){
				if(orgInfo.getOid().equals(org)){
					listTemp.add(orgInfo);
					break;
				}else{
					if(check(orgInfo.getChildren(),org)){
						listTemp.add(orgInfo);
						break;
					}
				}
			}
		}
		
		return listTemp;
	}
	
	private static boolean check(List<OrgInfo> list, String deptId){
		boolean bol = false;
		
		if(list == null){
			return bol;
		}
		for(OrgInfo orgInfo : list){
			if(orgInfo.getOid().equals(deptId)){
				return true;
			}else{
				bol = check(orgInfo.getChildren(),deptId);
			}
		}
		
		return bol;
	}
	
	/**
	 * 将组织机构类型对象转换为Item对象
	 * @param type
	 * @return
	 */
	public static Item changeOrgTypeToItem(OrgType type){
		Item item = new Item();
		item.setCatalogId(ICodeConstants.DM_DEF_ORG);
		item.setGuid(type.getName());
		item.setDescription(type.getText());
		item.setHasParentNodeInfo(0);
		item.setChecked(0);
		item.setDumped(0);
		item.setVisible(1);
		return item;
	}
	
	/**
	 * 将组织机构对象转换为Item对象
	 * @param org
	 * @return
	 */
	public static Item changeOrgToItem(OrgInfo org){
		Item item = new Item();
		item.setGuid(org.getOid());
		item.setDescription(org.getName());
		item.setCatalogId(ICodeConstants.DM_DEF_ORG);
		item.setChecked(1);
		item.setDumped(0);
		item.setVisible(1);
		if(org.getParent() == null || StringUtil.isEmpty(org.getParent().getOid())){
			item.setParentId(org.getType());
			item.setHasParentNodeInfo(0);//置为0，避免出现部门类别
		}
		else{
			item.setParentId(org.getParent().getOid());
			item.setHasParentNodeInfo(1);
		}
		return item;
	}
	
	/**
	 * 组织机构增加时对内存中组织机构伪代码库进行添加
	 * @param info
	 */
	public static void changeItemsWhenAdd(OrgInfo info){
		CodeUtil.insertItem(changeOrgToItem(info));
	}
	
	/**
	 * 组织机构启用时对内存中组织机构伪代码库进行添加
	 * @param info
	 */	
	public static void changeItemsWhenUse(OrgInfo info){
		if(info == null || StringUtil.isEmpty(info.getOid())){
			return;
		}
		if(info.getParent() != null && !StringUtil.isEmpty(info.getParent().getOid())){
			changeItemsWhenUse(info.getParent());
		}
		if(CodeUtil.getItem(ICodeConstants.DM_DEF_ORG, info.getOid()) == null){
			CodeUtil.insertItem(changeOrgToItem(info));
		}
	}
	
	/**
	 * 组织机构修改时对内存中组织机构伪代码库进行修改
	 * @param info
	 */
	public static void changeItemsWhenModify(OrgInfo info){
		Item item_new = changeOrgToItem(info);
		Item item_old = CodeUtil.getItem( ICodeConstants.DM_DEF_ORG, info.getOid());
		List<Item> childrenList = item_old.getChildren();
		CodeUtil.delItem(item_old);
		CodeUtil.insertItem(item_new);
		item_new.setChildren(childrenList);
		CodeUtil.updateItem(item_new);
	}
	
	/**
	 * 组织机构废弃时对内存中组织机构伪代码库进行删除
	 * @param info
	 */
	/*public static void changeItemsWhenRemove(OrgInfo info){
		Item item = changeOrgToItem(info);
		CodeUtil.delItem(item);
	}*/
	
	/**
	 * 组织机构废弃时对内存中组织机构伪代码库进行删除
	 * @param info
	 */
	/*public static void changeItemsWhenRemove(OrgInfo info, List<OrgInfo> list){
		if(list == null || list.size() == 0){
			return;
		}
		for (OrgInfo orgInfo : list) {
			if(orgInfo.getOid().equals(info.getOid())){
				continue;
			}
			CodeUtil.delItem(changeOrgToItem(orgInfo));
		}
		CodeUtil.delItem(changeOrgToItem(info));
	}*/
	
	/**
	 * 组织机构废弃时对内存中组织机构伪代码库进行删除
	 * @param info
	 */
	public static void changeItemsWhenRemove(OrgInfo info){
		if(info == null || StringUtil.isEmpty(info.getOid())){
			return;
		}
		if(info.getChildren() != null && info.getChildren().size() > 0){
			for (OrgInfo orgInfo : info.getChildren()) {
				changeItemsWhenRemove(orgInfo);
			}
		}
		if(CodeUtil.getItem(ICodeConstants.DM_DEF_ORG, info.getOid()) != null){
			CodeUtil.delItem(changeOrgToItem(info));
		}
		
	}
	
	/**
	 * 将待处理list转化成一级部门列表list_head和二级部门列表list_not_null
	 * @param list
	 * @param list_head
	 * @param list_not_null
	 */
	public static void operateTwoLists(List<OrgInfo> list, List<OrgInfo> list_head, List<OrgInfo> list_not_null){
		List<OrgInfo> list_more = new ArrayList<OrgInfo>();//存储所有非一级节点
		HashSet<String> keySet = new HashSet<String>();//存储list中的部门编号，避免重复
		HashSet<String> headSet = new HashSet<String>();//存储一级节点编号
		HashSet<String> notNullSet = new HashSet<String>();//存储非一级节点编号
		for (OrgInfo orgInfo : list) {
			keySet.add(orgInfo.getOid());
		}
		for (OrgInfo orgInfo : list) {
			if(orgInfo.getParent() == null || orgInfo.getParent().getOid() == null || "".equals(orgInfo.getParent().getOid())){
				if(!headSet.contains(orgInfo.getOid())){
					list_head.add(orgInfo);
					headSet.add(orgInfo.getOid());
				}
			}
			else{
				if(!notNullSet.contains(orgInfo.getOid())){
					list_not_null.add(orgInfo);
					notNullSet.add(orgInfo.getOid());
					if(!headSet.contains(orgInfo.getParent().getOid())){
						list_head.add(orgInfo.getParent());
						headSet.add(orgInfo.getParent().getOid());
					}
				}
//				supply(orgInfo, list_head, list_more, keySet, headSet, notNullSet);
			}
		}
		list_not_null.addAll(list_more);
		sort(list_head);
	}
	
	/**
	 * 将父子关系树所有节点补全
	 * @param orgInfo
	 * @param list
	 * @param keySet
	 */
	private static void supply(OrgInfo orgInfo, List<OrgInfo> list_head, List<OrgInfo> list_more, HashSet<String> keySet, HashSet<String> headSet, HashSet<String> notNullSet){
		if(orgInfo.getParent() == null || orgInfo.getParent().getOid() == null || "".equals(orgInfo.getParent().getOid())){
			if(!keySet.contains(orgInfo.getOid()) && !headSet.contains(orgInfo.getOid())){
				list_head.add(orgInfo);
				headSet.add(orgInfo.getOid());
			}
			return;
		}
		supply(orgInfo.getParent(),list_head,list_more,keySet,headSet,notNullSet);
		if(!keySet.contains(orgInfo.getOid()) && !notNullSet.contains(orgInfo.getOid())){
			list_more.add(orgInfo);
			notNullSet.add(orgInfo.getOid());
		}
	}
	
	/**
	 * 依照排序码排序list
	 * @param list
	 * @return
	 */
	private static void sort(List<OrgInfo> list){
		if(list != null && list.size() > 0){
			Collections.sort(list, new OrgUtil().new CompOrg());
		}
	}
	
	/**
	 * 组织机构比较器
	 *
	 */
	private class CompOrg implements Comparator<OrgInfo>{

		@Override
		public int compare(OrgInfo o1, OrgInfo o2) {
			String reg = "\\d{1,}";
			if(o1.getOrderCode() == null ||o1.getOrderCode().length()>20|| !o1.getOrderCode().matches(reg))
				return 1;
			if(o2.getOrderCode() == null ||o2.getOrderCode().length()>20|| !o2.getOrderCode().matches(reg))
				return -1;
			String a=o1.getOrderCode();
			String b=o2.getOrderCode();
			int length=20-a.length();
			for (int i = length; i > 0; i--) {
				a="0"+a;
			}
			length=20-b.length();
			for (int i = length; i > 0; i--) {
				b="0"+b;
			}
			
			
			return a.compareTo(b);
		}
		
	}
	/**
	 * 类型比较器
	 *
	 */
	private class CompType implements Comparator<Type>{

		@Override
		public int compare(Type t1, Type t2) {
			String order1 = t1.getName() != null ? t1.getName() : "";
			String order2 = t2.getName() != null ? t2.getName() : "";
			return order1.compareTo(order2);
		}
		
	}
	
}
