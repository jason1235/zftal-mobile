package com.zfsoft.hrm.summary.roster.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.summary.roster.entity.RosterColumn;
import com.zfsoft.hrm.summary.roster.entity.RosterConfig;

/** 
 * 花名册数据查询，SQL预处理工具
 * @author jinjj
 * @date 2012-9-12 上午09:57:31 
 *  
 */
public class RosterDataQueryUtil extends AbstractRosterDataQuery{

	private List<RosterColumn> columnList;
	private List<RosterConfig> configList;
	private Map<String,String[]> paramMap;
	
	Map<String,String> conditionMap = new LinkedHashMap<String,String>();
	Map<String,String> tableMap = new LinkedHashMap<String,String>(); 
	Map<String,String> orderMap = new LinkedHashMap<String,String>();
	Map<String,String> columnMap = new LinkedHashMap<String,String>();
	Map<String,RosterColumn> columnInfoMap = new LinkedHashMap<String,RosterColumn>();
	List<InfoProperty> propList = new ArrayList<InfoProperty>();
	
	private String conditioinStr;
	private String tableStr;
	private String columnStr;
	private String orderStr;
	
	public void process(){
		defaultColumnInit();
		fillColumnData();
		fillConditionData();
		conditioinStr = createConditionStr();
		tableStr = createTableStr();
		columnStr = createColumnStr();
		//先按部门排序再按人员排序
		orderStr = " order by HRM_BZGL_ZZJGB.pxm,overall.pxm";
	}
	
	private void defaultColumnInit(){
		InfoClass overall = InfoClassCache.getOverallInfoClass();
		tableMap.put(overall.getGuid(), overall.getIdentityName());//初始化默认表
		columnMap.put("GH", "overall.gh");
		columnMap.put("XM", "overall.xm");
		RosterColumn c = new RosterColumn();
		c.setName("工号");
		columnInfoMap.put("GH",c);
		c = new RosterColumn();
		c.setName("姓名");
		columnInfoMap.put("XM",c);
	}
	
	private void fillColumnData(){
		for(RosterColumn column : columnList){
			InfoClass clazz = InfoClassCache.getInfoClass(column.getClassId());
			InfoProperty p = clazz.getPropertyById(column.getColumnId());
			String columnExpr = null;
			if(p.getVirtual()){
				columnExpr = p.getDisplayFormula(clazz.getIdentityName())+p.getFieldName();
			}else{
				columnExpr = clazz.getIdentityName()+"."+p.getFieldName();
			}
			columnMap.put(StringUtils.upperCase(p.getFieldName()), columnExpr);
			propList.add(p);
			column.setName(p.getName());
			columnInfoMap.put(StringUtils.upperCase(p.getFieldName()), column);
			tableMap.put(clazz.getGuid(), clazz.getIdentityName());
		}
	}
	
	private void fillConditionData(){
		for(RosterConfig config : configList){
			String[] s = paramMap.get(config.getGuid());
			String condition = ConditionParseUtil.parse(config, s);
			conditionMap.put(config.getGuid(), condition);
			
			InfoClass clazz = InfoClassCache.getInfoClass(config.getClassid());
			tableMap.put(clazz.getGuid(), clazz.getIdentityName());
		}
	}
	
	/**
	 * 条件拼装字符串
	 * @return
	 */
	private String createConditionStr(){
		StringBuilder sb = new StringBuilder();
		for(String value:conditionMap.values()){
			if(!StringUtils.isEmpty(value)){
				if(sb.length()>0){
					sb.append(" and ");
				}
				sb.append(value);
				sb.append("\n");
			}
		}
		return sb.toString();
	}
	
	private String createTableStr(){
		StringBuilder sb = new StringBuilder();
		for(String value:tableMap.values()){
			if(!StringUtils.isEmpty(value)){
				if(sb.length()>0){// left join tableA on overall.gh = tableA.gh
					sb.append(" left join ");
					sb.append(value);
					sb.append(" on overall.gh = "+value+".gh");
					sb.append("\n");
				}else{
					sb.append(value);
					sb.append("\n");
				}
			}
		}
		//关联部门表
		sb.append("left join HRM_BZGL_ZZJGB on overall.dwm=HRM_BZGL_ZZJGB.bmdm \n");
		return sb.toString();
	}
	
	private String createColumnStr(){
		StringBuilder sb = new StringBuilder();
		for(String value:columnMap.values()){
			if(!StringUtils.isEmpty(value)){
				if(sb.length()>0){// left join tableA on overall.gh = tableA.gh
					sb.append(", ");
					sb.append(value);
					sb.append("\n");
				}else{
					sb.append(value);
					sb.append("\n");
				}
			}
		}
		return sb.toString();
	}

	public void setColumnList(List<RosterColumn> columnList) {
		this.columnList = columnList;
	}

	public void setConfigList(List<RosterConfig> configList) {
		this.configList = configList;
	}

	public void setParamMap(Map<String, String[]> paramMap) {
		this.paramMap = paramMap;
	}

	public String getConditioinStr() {
		return conditioinStr;
	}

	public String getTableStr() {
		return tableStr;
	}

	public String getColumnStr() {
		return columnStr;
	}

	public String getOrderStr() {
		return orderStr;
	}

	public List<InfoProperty> getPropList() {
		return propList;
	}

	public void setPropList(List<InfoProperty> propList) {
		this.propList = propList;
	}

	public Map<String, RosterColumn> getColumnNameMap() {
		return columnInfoMap;
	}

	public Map<String, String[]> getParamMap() {
		return paramMap;
	}
	
}
