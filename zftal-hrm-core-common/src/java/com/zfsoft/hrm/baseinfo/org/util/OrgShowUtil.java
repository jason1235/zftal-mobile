package com.zfsoft.hrm.baseinfo.org.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.zfsoft.hrm.baseinfo.code.entities.Item;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.util.base.StringUtil;

public class OrgShowUtil {
	
	public static String getOrgTreeHtml(HashSet<String> orgSet){
		return getOrgTreeHtml(null,1,new ArrayList<String>(),orgSet);
	}
	
	public static String getOrgTreeHtml(String parentId, int deep, List<String> codeIdList, HashSet<String> orgSet){
		List<Item> list = CodeUtil.getChildren("DM_DEF_ORG", parentId);
		StringBuilder sb = new StringBuilder();
		for(Item item : list){
			codeIdList.add(item.getGuid());
			sb.append("<tr id='tr_"+createCodeId(codeIdList)+"' pid='"+createCodeParentId(codeIdList)+"'");
			if(!StringUtil.isEmpty(parentId)){
				sb.append(" style='display:none' ");
			}
			sb.append(" >");
			sb.append("<td><input type='checkbox' name='selectId' id='"+createCodeId(codeIdList)+"' "+checkVisible(item)+" value='"+item.getGuid()+"' ");
			if(orgSet.contains(item.getGuid())){
				sb.append("checked='true' ");
			}
			sb.append("/>  ");
			sb.append("<span name='list_ico' class='list_ico"+deep+"' opened='false'>  ");
			sb.append("<font>");
			sb.append(item.getDescription());
			sb.append("</font>");
			sb.append("</span></td>");
			sb.append("<td>");
			sb.append(item.getGuid());
			sb.append("</td>");
			sb.append("</tr>");
			if(item.getHasChild()){
				sb.append(getOrgTreeHtml(item.getGuid(), deep+1, codeIdList, orgSet));
			}
			codeIdList.remove(deep-1);
		}
		return sb.toString();
	}
	
	private static String createCodeId(List<String> codeIdList){
		StringBuilder sb = new StringBuilder();
		for(String str : codeIdList){
			if(sb.length()>0){
				sb.append("_");
			}
			sb.append(str);
		}
		return sb.toString();
	}
	
	private static String createCodeParentId(List<String> codeIdList){
		if(codeIdList.size() <= 1){
			return "";
		}
		List<String> list = new ArrayList<String>();
		list.addAll(codeIdList);
		list.remove(list.size() - 1);
		StringBuilder sb = new StringBuilder();
		for(String str : list){
			if(sb.length()>0){
				sb.append("_");
			}
			sb.append(str);
		}
		return sb.toString();
	}
	
	private static String checkVisible(Item item){
		if(item.getChecked()==1){
			return "";
		}else{
			return "disabled='disabled' ";
		}
	}
	
}
