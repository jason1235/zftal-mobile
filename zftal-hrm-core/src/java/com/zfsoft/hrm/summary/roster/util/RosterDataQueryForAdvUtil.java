package com.zfsoft.hrm.summary.roster.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.summary.roster.entity.RosterColumn;

/**
 * 花名册数据高级查询，sql预处理工具
 * 
 * @author gonghui
 * 2014-1-10
 */
public class RosterDataQueryForAdvUtil extends AbstractRosterDataQuery{
	private List<RosterQueryCondForAdv> rosterQueryCondForAdvs;
	private List<RosterColumn> columnList;
	
	Map<String,String> tableMap = new LinkedHashMap<String,String>();
	Map<String,String> columnMap = new LinkedHashMap<String,String>();
	List<InfoProperty> propList = new ArrayList<InfoProperty>();
	private Map<String,RosterColumn> columnInfoMap=new LinkedHashMap<String, RosterColumn>();
	
	private Map<String,Object> valuesMap=new HashMap<String, Object>();
	private String conditioinStr;
	private String tableStr;
	private String columnStr;
	private String orderStr;
	
	public RosterDataQueryForAdvUtil(List<RosterQueryCondForAdv> rosterQueryCondForAdvs,
			List<RosterColumn> columnList){
		this.rosterQueryCondForAdvs=rosterQueryCondForAdvs;
		this.columnList=columnList;
	}
	
	public static RosterDataQueryForAdvUtil newInstance(List<RosterQueryCondForAdv> rosterQueryCondForAdvs,
			List<RosterColumn> columnList){
		return new RosterDataQueryForAdvUtil(rosterQueryCondForAdvs, columnList);
	}
	
	public void process(){
		defaultInit();
		fillColumnData();
		this.conditioinStr = createConditionStr();
		this.tableStr = createTableStr();
		this.columnStr = createColumnStr();
		//先按部门排序再按人员排序
		this.orderStr = " order by HRM_BZGL_ZZJGB.pxm,overall.pxm";
	}
	
	private void defaultInit(){
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
	
	public String createConditionStr(){
		if(rosterQueryCondForAdvs==null||rosterQueryCondForAdvs.size()==0){
			return "";
		}
		StringBuilder conditionSb=new StringBuilder();
		int i=1;
		for (RosterQueryCondForAdv queryCondForAdv : rosterQueryCondForAdvs) {
			InfoClass clazz = InfoClassCache.getInfoClass(queryCondForAdv.getClassId());
			InfoProperty property = clazz.getPropertyById(queryCondForAdv.getConfigId());
			
			tableMap.put(clazz.getGuid(), clazz.getIdentityName());
			
			queryCondForAdv.setInfoProperty(property);
			queryCondForAdv.setClazz(clazz);
			
			conditionSb.append(queryCondForAdv.getParenthesisBefore());
			conditionSb.append(this.getClassFieldName(queryCondForAdv));
			conditionSb.append(" ");
			if("like".equals(queryCondForAdv.getOperator())||"not like".equals(queryCondForAdv.getOperator())){
				conditionSb.append(queryCondForAdv.getOperator());
				conditionSb.append(" '%'||");
				conditionSb.append(this.parseCondValueStr(queryCondForAdv,i));
				conditionSb.append("||'%'");
			}else{
				conditionSb.append(queryCondForAdv.getOperator());
				conditionSb.append(" ");
				conditionSb.append(this.parseCondValueStr(queryCondForAdv,i));
			}
			
			conditionSb.append(queryCondForAdv.getParenthesisAfter());
			if(i<rosterQueryCondForAdvs.size()){
				conditionSb.append(" ");
				conditionSb.append(queryCondForAdv.getLogicalRel());
				conditionSb.append(" ");
			}
			i++;
		}
		return conditionSb.toString();
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
	
	private String parseCondValueStr(RosterQueryCondForAdv queryCondForAdv,int i){
		StringBuilder sb=new StringBuilder();
		String type=queryCondForAdv.getInfoProperty().getFieldType();
		if (Type.COMMON.equals(type)) {
			valuesMap.put("v"+i, queryCondForAdv.getFieldValue());
			return sb.append(this.parseVal(i)).toString();
		} else if (Type.LONG_STR.equals(type)) {
			valuesMap.put("v"+i, queryCondForAdv.getFieldValue());
			return sb.append(this.parseVal(i)).toString();
		} else if (Type.CODE.equals(type)) {
			valuesMap.put("v"+i, queryCondForAdv.getFieldValue());
			return sb.append(this.parseVal(i)).toString();
		} else if (Type.DATE.equals(type)) {
			valuesMap.put("v"+i, queryCondForAdv.getFieldValue());
			return this.parseDateTypeValue(queryCondForAdv,i);
		} else if (Type.MONTH.equals(type)) {
			valuesMap.put("v"+i, queryCondForAdv.getFieldValue());
			return this.parseDateTypeValue(queryCondForAdv,i);
		} else if (Type.YEAR.equals(type)) {
			valuesMap.put("v"+i, queryCondForAdv.getFieldValue());
			return this.parseDateTypeValue(queryCondForAdv,i);
		} else if (Type.IMAGE.equals(type)) {
			//result = imageParser(property);
			throw new RuleException("无法支持图片类型字段的查询");
		}  else if (Type.PHOTO.equals(type)) {
			//result = imageParser(property);
			throw new RuleException("无法支持照片类型字段的查询");
		} else if (Type.FILE.equals(type)) {
			//result = imageParser(property);
			throw new RuleException("无法支持文件类型字段的查询");
		} else if (Type.SIGLE_SEL.equals(type)) {
			valuesMap.put("v"+i, queryCondForAdv.getFieldValue());
			return sb.append(this.parseVal(i)).toString();
		} else if (Type.NUMBER.equals(type)){
			try {
				int d=Integer.parseInt(queryCondForAdv.getFieldValue());
				valuesMap.put("v"+i,d);
				return sb.append(this.parseVal(i)).toString();
			} catch (NumberFormatException e) {
				try {
					double d=Double.parseDouble(queryCondForAdv.getFieldValue());
					valuesMap.put("v"+i,d);
					return sb.append(this.parseVal(i)).toString();
				} catch (NumberFormatException e1) {
					throw new RuleException(queryCondForAdv.getInfoProperty().getName()+"，请输入有效整数或小数！");
				}
			}
		} else {
			valuesMap.put("v"+i, queryCondForAdv.getFieldValue());
			return sb.append(this.parseVal(i)).toString();
		}

	}
	
	private String parseVal(int i){
		StringBuilder sb=new StringBuilder();
		sb.append("#{valuesMap.v").append(i).append("}");
		return sb.toString();
	}
	private String parseDateTypeValue(RosterQueryCondForAdv queryCondForAdv,int i){
		StringBuilder sb=new StringBuilder();
		sb.append("to_date(");
		sb.append(this.parseVal(i));
		sb.append(",'");
		sb.append(queryCondForAdv.getInfoProperty().getTypeInfo().getFormat());
		sb.append("')");
		return sb.toString();
	}
	private String getClassFieldName(RosterQueryCondForAdv queryCondForAdv){
		StringBuilder sb=new StringBuilder();
		if(queryCondForAdv.getInfoProperty().getVirtual()){
			sb.append(queryCondForAdv.getInfoProperty()
					.getDisplayFormula(queryCondForAdv.getClazz().getIdentityName()));
		}else{
			sb.append(queryCondForAdv.getClazz().getIdentityName()+".");
			sb.append(queryCondForAdv.getInfoProperty().getFieldName());
		}
		return sb.toString();
	}

	public List<RosterQueryCondForAdv> getRosterQueryCondForAdvs() {
		return rosterQueryCondForAdvs;
	}

	public void setRosterQueryCondForAdvs(
			List<RosterQueryCondForAdv> rosterQueryCondForAdvs) {
		this.rosterQueryCondForAdvs = rosterQueryCondForAdvs;
	}

	public List<RosterColumn> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<RosterColumn> columnList) {
		this.columnList = columnList;
	}

	public Map<String, String> getTableMap() {
		return tableMap;
	}

	public void setTableMap(Map<String, String> tableMap) {
		this.tableMap = tableMap;
	}

	public Map<String, String> getColumnMap() {
		return columnMap;
	}

	public void setColumnMap(Map<String, String> columnMap) {
		this.columnMap = columnMap;
	}

	public Map<String, RosterColumn> getColumnInfoMap() {
		return columnInfoMap;
	}

	public void setColumnInfoMap(Map<String, RosterColumn> columnInfoMap) {
		this.columnInfoMap = columnInfoMap;
	}

	public List<InfoProperty> getPropList() {
		return propList;
	}

	public void setPropList(List<InfoProperty> propList) {
		this.propList = propList;
	}

	public Map<String, Object> getValuesMap() {
		return valuesMap;
	}

	public void setValuesMap(Map<String, Object> valuesMap) {
		this.valuesMap = valuesMap;
	}

	public String getConditioinStr() {
		return conditioinStr;
	}

	public void setConditioinStr(String conditioinStr) {
		this.conditioinStr = conditioinStr;
	}

	public String getTableStr() {
		return tableStr;
	}

	public void setTableStr(String tableStr) {
		this.tableStr = tableStr;
	}

	public String getColumnStr() {
		return columnStr;
	}

	public void setColumnStr(String columnStr) {
		this.columnStr = columnStr;
	}

	public String getOrderStr() {
		return orderStr;
	}

	public void setOrderStr(String orderStr) {
		this.orderStr = orderStr;
	}

	
}
