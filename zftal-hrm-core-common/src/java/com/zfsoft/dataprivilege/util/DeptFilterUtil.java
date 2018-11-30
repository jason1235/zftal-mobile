package com.zfsoft.dataprivilege.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.zfsoft.dataprivilege.filter.impl.DealDeptFilter;
import com.zfsoft.hrm.baseinfo.code.entities.Item;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.util.base.StringUtil;
/**
 * 部门过滤工具
 * @author fyj
 */
public class DeptFilterUtil {
	
	/**
	 * 部门过滤条件程序接口
	 * @param aslisName 表别名
	 * @param fieldName 字段名
	 * @return
	 */
	public static String getCondition(String aslisName,String fieldName){
		Map<String,Object> paramMap=new HashMap<String, Object>();
		paramMap.put(DealDeptFilter.OTHERNAME, aslisName);
		paramMap.put(DealDeptFilter.DEPTFIELD, fieldName);
		return DataFilterUtil.getCondition(paramMap,"bmgl");
	}
	
	/**
	 * 生成返回组织机构节点树
	 * @param typeName 数据范围类型
	 * @param orgSet 已选部门代码集合
	 * @return
	 */
	public static String getOrgRootAndSelfTreeHtml(String typeName, HashSet<String> orgSet){
		StringBuilder sb = new StringBuilder();
		sb.append(initRootAndSelfTree(typeName));
		sb.append(getOrgRootAndSelfTreeHtml(null,1,new ArrayList<String>(),typeName,orgSet));
		return sb.toString();
	}
	
	/**
	 * 生成返回组织机构节点树-带本部门名称
	 * @param typeName 数据范围类型
	 * @param orgSet 已选部门代码集合
	 * @return
	 */
	public static String getOrgRootAndSelfTreeHtml(String typeName, HashSet<String> orgSet, String ownerOrg){
		StringBuilder sb = new StringBuilder();
		sb.append(initRootAndSelfTree(typeName,ownerOrg));
		sb.append(getOrgRootAndSelfTreeHtml(null,1,new ArrayList<String>(),typeName,orgSet));
		return sb.toString();
	}
	
	
	/**
	 * 生成返回组织机构节点树
	 * @param parentId 上级部门节点代码
	 * @param deep 深度
	 * @param codeIdList 用于生成代码辅助作用的list
	 * @param typeName 数据范围类型
	 * @param orgSet 已选部门代码集合
	 * @return
	 */
	public static String getOrgRootAndSelfTreeHtml(String parentId, int deep, List<String> codeIdList, String typeName, HashSet<String> orgSet){
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
			if(DealDeptFilter.TYPE_IN.equals(typeName) && orgSet.contains(item.getGuid())){
				sb.append("checked='true' ");
			}
			sb.append("/>  ");
			sb.append("<span name='list_ico' class='list_ico"+(deep+1)+"' opened='false'>  ");
			sb.append("<font>");
			sb.append(item.getDescription());
			sb.append("</font>");
			sb.append("</span></td>");
			sb.append("</tr>");
			if(item.getHasChild()){
				sb.append(getOrgRootAndSelfTreeHtml(item.getGuid(), deep+1, codeIdList, typeName, orgSet));
			}
			codeIdList.remove(deep-1);
		}
		return sb.toString();
	}
	
	/**
	 * 生成一个节点编号
	 * @param codeIdList
	 * @return
	 */
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
	
	/**
	 * 返回上级节点编号
	 * @param codeIdList
	 * @return
	 */
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
	
	/**
	 * 设定是否可显示
	 * @param item
	 * @return
	 */
	private static String checkVisible(Item item){
		if(item.getChecked()==1){
			return "";
		}else{
			return "disabled='disabled' ";
		}
	}
	
	/**
	 * 初始化节点树
	 * @param typeName
	 * @return
	 */
	private static String initRootAndSelfTree(String typeName){
		StringBuilder sb = new StringBuilder();
		sb.append("<tr>                                                        		");
		sb.append("	<td>                                                       		");
		sb.append("		<input type='checkbox' name='selectId' id='all' value='all' ");
		if(DealDeptFilter.TYPE_ALL.equals(typeName)){
			sb.append("	checked='true' 												");
		}
		sb.append(" />     															");
		sb.append("		<span name='list_ico' class='list_ico1' opened='false'>  	");
		sb.append("			<font name='all'                         				");
		if(DealDeptFilter.TYPE_ALL.equals(typeName)){
			sb.append("			color='#0457A7'                       				");
		}
		sb.append("			 >全部</font>                        					");
		sb.append("		</span>                                                  	");
		sb.append("	</td>                                                      		");
		sb.append("</tr>                                                       		");
		sb.append("<tr>                                                        		");
		sb.append("	<td>                                                       		");
		sb.append("	  <input type='checkbox' name='selectId' id='self' value='self' ");
		if(DealDeptFilter.TYPE_SELF.equals(typeName)){
			sb.append("	checked='true' 												");
		}
		sb.append(" />     															");
		sb.append("		<span name='list_ico' class='list_ico1' opened='false'>  	");
		sb.append("			<font name='self'                        				");
		if(DealDeptFilter.TYPE_SELF.equals(typeName)){
			sb.append("			color='#0457A7'                       				");
		}
		sb.append("			 >本部门</font>                        					");
		sb.append("		</span>                                                  	");
		sb.append("	</td>                                                      		");
		sb.append("</tr>                                                       		");
		sb.append("<tr id='choose'>                                            		");
		sb.append("	<td>                                                       		");
		sb.append("		<input type='checkbox' id='checkAll' disabled='disabled'/>                   	");
		sb.append("		<span name='choose_ico' class='list_ico1' opened='false'>	");
		sb.append("			<font name='choose'>选择部门</font>                    	");
		sb.append("		</span>                                                  	");
		sb.append("	</td>                                                     	 	");
		sb.append("</tr>                                                       		");
		return sb.toString();
	}
	
	/**
	 * 初始化节点树-带本部门名称
	 * @param typeName
	 * @return
	 */
	private static String initRootAndSelfTree(String typeName, String ownerOrg){
		StringBuilder sb = new StringBuilder();
		sb.append("<tr>                                                        		");
		sb.append("	<td>                                                       		");
		sb.append("		<input type='checkbox' name='selectId' id='all' value='all' ");
		if(DealDeptFilter.TYPE_ALL.equals(typeName)){
			sb.append("	checked='true' 												");
		}
		sb.append(" />     															");
		sb.append("		<span name='list_ico' class='list_ico1' opened='false'>  	");
		sb.append("			<font name='all'                         				");
		if(DealDeptFilter.TYPE_ALL.equals(typeName)){
			sb.append("			color='#0457A7'                       				");
		}
		sb.append("			 >全部</font>                        					");
		sb.append("		</span>                                                  	");
		sb.append("	</td>                                                      		");
		sb.append("</tr>                                                       		");
		sb.append("<tr>                                                        		");
		sb.append("	<td>                                                       		");
		sb.append("	  <input type='checkbox' name='selectId' id='self' value='self' ");
		if(DealDeptFilter.TYPE_SELF.equals(typeName)){
			sb.append("	checked='true' 												");
		}
		sb.append(" />     															");
		sb.append("		<span name='list_ico' class='list_ico1' opened='false'>  	");
		sb.append("			<font name='self'                        				");
		if(DealDeptFilter.TYPE_SELF.equals(typeName)){
			sb.append("			color='#0457A7'                       				");
		}
		sb.append("			 >"+ownerOrg+"（本部门）</font>                        			");
		sb.append("		</span>                                                  	");
		sb.append("	</td>                                                      		");
		sb.append("</tr>                                                       		");
		sb.append("<tr id='choose'>                                            		");
		sb.append("	<td>                                                       		");
		sb.append("		<input type='checkbox' id='checkAll' disabled='disabled'/>  ");
		sb.append("		<span name='choose_ico' class='list_ico1' opened='false'>	");
		sb.append("			<font name='choose'>选择部门</font>                    	");
		sb.append("		</span>                                                  	");
		sb.append("	</td>                                                     	 	");
		sb.append("</tr>                                                       		");
		return sb.toString();
	}
	
}
