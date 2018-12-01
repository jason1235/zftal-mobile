package com.zfsoft.hrm.baseinfo.org.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.zfsoft.hrm.baseinfo.org.entities.OrgInfo;
import com.zfsoft.hrm.baseinfo.org.entities.OrgSearch;
import com.zfsoft.util.base.StringUtil;

public class OrgSearchUtil {

	/**
	 * 生成组织机构查询结果的方法，其原理可参照OrgUtil的supplyChildren方法
	 * @param list  全部组织机构节点列表
	 * @param datalist  按条件查询所得结果列表
	 * @param countMapList  列表每一项为Map<部门编号,部门人数>
	 * @return
	 */
	public static List<OrgSearch> supplySearchResult(List<OrgInfo> list, List<OrgInfo> datalist, List<HashMap<String, String>> countMapList){
		List<OrgInfo> list_head = new ArrayList<OrgInfo>();
		List<OrgInfo> list_not_null = new ArrayList<OrgInfo>();
		HashMap<String, OrgSearch> orignMap = new HashMap<String, OrgSearch>();
		HashMap<String, Integer> countmap = orgCountMap(countMapList);
		OrgUtil.operateTwoLists(list, list_head, list_not_null);
		List<OrgSearch> resultList = resultList(list_head, list_not_null, orignMap, countmap);
		viewAndClickAble(datalist, orignMap);
		return resultList;
	}
	
	/**
	 * 生成结果list，list中每一项为一棵节点树
	 * @param list_head  全部一级部门节点
	 * @param list_not_null  全部非一级部门节点
	 * @param orignMap  节点记录map，全部节点都要记录进该map中
	 * @param countmap	部门-人数统计map
	 * @return
	 */
	private static List<OrgSearch> resultList(List<OrgInfo> list_head,List<OrgInfo> list_not_null,HashMap<String, OrgSearch> orignMap, HashMap<String, Integer> countmap){
		List<OrgSearch> searchList = new ArrayList<OrgSearch>();
		for (OrgInfo orgInfo : list_head) {
			OrgSearch orgSearch = new OrgSearch();
			orgSearch.setOrgInfo(orgInfo);
			orgSearch.getResult().setProperty("exactCount", String.valueOf(countmap.get(orgInfo.getOid()) != null ? countmap.get(orgInfo.getOid()) : 0 ));
			fill(orgSearch, list_not_null, orignMap, countmap);
			searchList.add(orgSearch);
		}
		return searchList;
	}
	
	/**
	 * 填充生成节点树
	 * @param orgSearch  组织机构查询节点
	 * @param list  全部的非一级部门节点
	 * @param orignMap  节点记录map，全部节点都要记录进该map中
	 * @param countmap	部门-人数统计map
	 */
	private static void fill(OrgSearch orgSearch, List<OrgInfo> list, HashMap<String, OrgSearch> orignMap, HashMap<String, Integer> countmap){
		List<OrgSearch> separateList = new ArrayList<OrgSearch>();
		long totalCount = Long.valueOf((String)orgSearch.getResult().get("exactCount"));
		for (OrgInfo org : list) {
			if(org.getParent().getOid().equals(orgSearch.getOrgInfo().getOid())){
				OrgSearch search = new OrgSearch();
				search.setOrgInfo(org);
				search.getResult().setProperty("exactCount", String.valueOf(countmap.get(org.getOid()) != null ? countmap.get(org.getOid()) : 0 ));
				fill(search, list, orignMap, countmap);
				totalCount += search.getTotalCount();
				separateList.add(search);
			}
		}
		sortSearch(separateList);
		orgSearch.setChildrenSearch(separateList);
		orgSearch.setTotalCount(totalCount);
		orignMap.put(orgSearch.getOrgInfo().getOid(), orgSearch);
	}
	
	/**
	 * 确定查询出的节点哪些可显示、可点击的方法
	 * @param list 条件查询出的列表
	 * @param map 全部节点的map
	 * <p>该方法将全部节点的map和已查询出的结果进行比较，若map中的某一节点在查询结果中，</p>
	 * <p>则将该节点、该节点所有父级节点、该节点全部子节点设为可显示，该节点及其全部下级子节点设为可点击</p>
	 */
	private static void viewAndClickAble(List<OrgInfo> list, HashMap<String, OrgSearch> map){
		if(list == null || list.size() == 0){
			return;
		}
		for (OrgInfo orgInfo : list) {
			if(map.get(orgInfo.getOid()) != null){
				OrgSearch search = map.get(orgInfo.getOid());
				viewable(search,map);
				clickable(search);
			}
		}
	}
	
	/**
	 * 将指定节点以及其所有父级节点、全部子节点设为可显示
	 * @param search 指定节点
	 * @param map 全部节点map
	 */
	private static void viewable(OrgSearch search, HashMap<String, OrgSearch> map){
		search.setViewable(true);
		parentViewable(search, map);
		childrenViewable(search);
	}
	
	/**
	 * 将指定节点所有父级节点设为可显示
	 * @param search 指定节点
	 * @param map 全部节点map
	 */
	private static void parentViewable(OrgSearch search, HashMap<String, OrgSearch> map){
		if(search.getOrgInfo().getParent() == null || StringUtil.isEmpty(search.getOrgInfo().getParent().getOid())){
			return;
		}
		OrgSearch parentSearch = map.get(search.getOrgInfo().getParent().getOid());
		if(parentSearch != null){
			parentSearch.setViewable(true);
			parentViewable(parentSearch, map);
		}
	}
	
	/**
	 * 将指定节点全部子节点设为可显示
	 * @param search 指定节点
	 */
	private static void childrenViewable(OrgSearch search){
		if(search.getChildrenSearch() == null || search.getChildrenSearch().size() == 0){
			return;
		}
		for (OrgSearch child : search.getChildrenSearch()) {
			if(!child.isViewable()){
				child.setViewable(true);
				childrenViewable(child);
			}
		}
	}
	
	/**
	 * 将指定节点以及其全部子节点设为可点击
	 * @param search 指定节点
	 */
	private static void clickable(OrgSearch search){
		search.setClickable(true);
		if(search.getChildrenSearch() == null || search.getChildrenSearch().size() == 0){
			return;
		}
		for (OrgSearch child : search.getChildrenSearch()) {
			if(!child.isClickable()){
				clickable(child);
			}
		}
	}
	
	private static HashMap<String, Integer> orgCountMap(List<HashMap<String, String>> countMapList){
		HashMap<String, Integer> countmap = new HashMap<String, Integer>();
		if(countMapList != null && countMapList.size() > 0){
			for (HashMap<String, String> hashMap : countMapList) {
				countmap.put((String)hashMap.get("OID"), Integer.valueOf(String.valueOf(hashMap.get("CT"))));
			}
		}
		return countmap;
	}
	
	private static void sortSearch(List<OrgSearch> list){
		if(list != null && list.size() > 0){
			Collections.sort(list, new OrgSearchUtil().new CompOrgSearch());
		}
	}
	
	/**
	 * 组织机构查询比较器
	 *
	 */
	private class CompOrgSearch implements Comparator<OrgSearch>{

		@Override
		public int compare(OrgSearch o1, OrgSearch o2) {
			String reg = "\\d{1,}";
			if(o1.getOrgInfo().getOrderCode() == null ||o1.getOrgInfo().getOrderCode().length()>20|| !o1.getOrgInfo().getOrderCode().matches(reg))
				return 1;
			if(o2.getOrgInfo().getOrderCode() == null ||o2.getOrgInfo().getOrderCode().length()>20|| !o2.getOrgInfo().getOrderCode().matches(reg))
				return -1;
			String a=o1.getOrgInfo().getOrderCode();
			String b=o2.getOrgInfo().getOrderCode();
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
	
}
