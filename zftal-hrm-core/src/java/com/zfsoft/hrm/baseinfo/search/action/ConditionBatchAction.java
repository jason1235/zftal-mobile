package com.zfsoft.hrm.baseinfo.search.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.zfsoft.hrm.baseinfo.code.entities.Item;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.baseinfo.search.entities.Condition;
import com.zfsoft.hrm.baseinfo.search.service.svcinterface.IConditionService;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.util.base.StringUtil;

/** 
 * 条件批量添加
 * @author jinjj
 * @date 2012-11-23 上午11:35:39 
 *  
 */
public class ConditionBatchAction extends HrmAction {

	private String propertyName;
	private String rootId;
	private String selectId;
	private int deep = 0;
	private List<String> codeIdList = new ArrayList<String>();
	private IConditionService conditionService;
	
	public String setup() throws Exception{
		InfoClass clazz = InfoClassCache.getOverallInfoClass();
		List<InfoProperty> list = clazz.getViewables();
		List<InfoProperty> codeList = new ArrayList<InfoProperty>();
		for(InfoProperty p : list){
			if(p.getTypeInfo().getName().equals(Type.CODE)){
				codeList.add(p);
			}
		}
		getValueStack().set("codeList", codeList);
		return "setup";
	}
	
	public String load() throws Exception{
		InfoClass clazz = InfoClassCache.getOverallInfoClass();
		InfoProperty p = clazz.getPropertyByName(propertyName);
		String codeId = p.getCodeId();
		String html = getConfigListHtml(codeId,null);
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		map.put("result", html);
		getValueStack().set(DATA, map);
		return DATA;
	}
	
	private String getConfigListHtml(String codeId,String parentId){
		deep++;
		List<Item> list = CodeUtil.getChildren(codeId, parentId);
		StringBuilder sb = new StringBuilder();
		for(Item item : list){
			codeIdList.add(item.getGuid());
			sb.append("<tr>");
			sb.append("<td><span class='list_ico"+deep+"'><input type='checkbox' name='selectId' id='"+createCodeId()+"' "+checkVisible(item)+" value='"+item.getGuid()+"'/>  ");
			sb.append(item.getDescription());
			sb.append("</span></td>");
			sb.append("<td>");
			sb.append(item.getGuid());
			sb.append("</td>");
			sb.append("</tr>");
			if(item.getHasChild()){
				sb.append(getConfigListHtml(codeId, item.getGuid()));
			}
			codeIdList.remove(deep-1);
		}
		deep--;
		return sb.toString();
	}
	
	private String createCodeId(){
		StringBuilder sb = new StringBuilder();
		for(String str : codeIdList){
			if(sb.length()>0){
				sb.append("_");
			}
			sb.append(str);
		}
		return sb.toString();
	}
	
	private String checkVisible(Item item){
		if(item.getChecked()==1){
			return "";
		}else{
			return "disabled='disabled' ";
		}
	}
	
	public String save() throws Exception{
		if(StringUtil.isEmpty(selectId)){
			throw new RuleException("请选择条件项");
		}
		HashMap<String,String> map = new HashMap<String,String>();
		String[] strs = selectId.split(",");
		for(String s : strs){
			map.put(s.trim(),s.trim());
		}
		Condition con = conditionService.getById(rootId);
		conditionService.saveBatch(propertyName, map, con);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String delete() throws Exception{
		Condition con = conditionService.getById(rootId);
		conditionService.deleteBatch(con);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getRootId() {
		return rootId;
	}

	public void setRootId(String rootId) {
		this.rootId = rootId;
	}

	public void setConditionService(IConditionService conditionService) {
		this.conditionService = conditionService;
	}

	public String getSelectId() {
		return selectId;
	}

	public void setSelectId(String selectId) {
		this.selectId = selectId;
	}
	
}
