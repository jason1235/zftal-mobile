package com.zfsoft.hrm.summary.roster.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.core.util.DownloadFilenameUtil;
import com.zfsoft.hrm.summary.roster.entity.Roster;
import com.zfsoft.hrm.summary.roster.entity.RosterColumn;
import com.zfsoft.hrm.summary.roster.entity.RosterConfig;
import com.zfsoft.hrm.summary.roster.entity.RosterParam;
import com.zfsoft.hrm.summary.roster.html.RosterConfigHtmlUtil;
import com.zfsoft.hrm.summary.roster.query.RosterConfigQuery;
import com.zfsoft.hrm.summary.roster.query.RosterConfigRelationQuery;
import com.zfsoft.hrm.summary.roster.query.RosterDataQuery;
import com.zfsoft.hrm.summary.roster.service.svcinterface.IRosterColumnService;
import com.zfsoft.hrm.summary.roster.service.svcinterface.IRosterConfigRelationService;
import com.zfsoft.hrm.summary.roster.service.svcinterface.IRosterConfigService;
import com.zfsoft.hrm.summary.roster.service.svcinterface.IRosterDataService;
import com.zfsoft.hrm.summary.roster.service.svcinterface.IRosterParamService;
import com.zfsoft.hrm.summary.roster.service.svcinterface.IRosterService;
import com.zfsoft.hrm.summary.roster.util.RosterDataQueryForAdvUtil;
import com.zfsoft.hrm.summary.roster.util.RosterDataQueryUtil;
import com.zfsoft.hrm.summary.roster.util.RosterQueryCondForAdv;
import com.zfsoft.hrm.summary.roster.util.RosterValidUtil;
import com.zfsoft.util.date.TimeUtil;

/** 
 * 花名册查询ACTION
 * @author jinjj
 * @date 2012-8-30 下午04:45:05 
 *  
 */
public class RosterDataAction extends HrmAction {

	private static final long serialVersionUID = 2111604574128450213L;

	Logger log = LoggerFactory.getLogger(RosterDataAction.class);
	
	private IRosterConfigService rosterConfigService;
	private List<RosterConfig> configList;
	private IRosterConfigRelationService rosterConfigRelationService;
	private IRosterColumnService rosterColumnService;
	private List<RosterConfig> relatedRosterList;
	private String guid;
	private RosterDataQuery query = new RosterDataQuery();
	private IRosterDataService rosterDataService;
	private IRosterParamService rosterParamService;
	private PageList<Map<String,String>> pageList;
	private IRosterService rosterService;
	
	private Set<String> keySet;
	private Collection<RosterColumn> columnCollection;
	private String name;
	
	/* 花名册增加类型  2013-9-5 added by 1021 */
	private String rosterType;
	private String description;
	
	public String page()throws Exception{
		RosterConfigQuery configQuery = new RosterConfigQuery();
		configList = rosterConfigService.getList(configQuery);
		RosterConfigRelationQuery relationQuery = new RosterConfigRelationQuery();
		relationQuery.setRosterId(guid);
		relatedRosterList = rosterConfigRelationService.getList(relationQuery);
		initSelected(configList,relatedRosterList);
		RosterParam p = new RosterParam();
		p.setGuid(guid);
		p = rosterParamService.getById(p);
		HashMap<String,String[]> paramMap = new HashMap<String,String[]>();
		if(p != null){
			paramMap = (HashMap<String, String[]>) SerializationUtils.deserialize(p.getData());
		}
		String initConfigHtml = createInitConfigHtml(relatedRosterList,paramMap);//初始化条件页面
//		String initParamHtml = createInitParamHtml(relatedRosterList,paramMap);
		getValueStack().set("initConfigHtml", initConfigHtml);
//		getValueStack().set("initParamHtml", initParamHtml);
		return "page";
	}
	
	private String createInitConfigHtml(List<RosterConfig> list,HashMap<String,String[]> paramMap){
		StringBuilder sb = new StringBuilder();
		for(RosterConfig relation : list){
			InfoClass clazz = InfoClassCache.getInfoClass(relation.getClassid());
			InfoProperty property = clazz.getPropertyById(relation.getGuid());
			relation.setInfoProperty(property);
			sb.append(RosterConfigHtmlUtil.parse(relation,paramMap.get(relation.getGuid())));
		}
		return sb.toString();
	}
	
	private void initSelected(List<RosterConfig> list1,List<RosterConfig> list2){
		for(RosterConfig relation:list2){
			for(int i=0;i<list1.size();i++){
				RosterConfig config = list1.get(i);
				if(relation.getGuid().equals(config.getGuid())){
					config.setSelected(true);
					list1.set(i, config);
					break;
				}
			}
		}
	}
	
	public String query() throws Exception{
		validateParam();
		List<RosterColumn> columnList = rosterColumnService.getList(guid);
		
		RosterDataQueryUtil util = new RosterDataQueryUtil();
		util.setConfigList(relatedRosterList);
		util.setColumnList(columnList);
		util.setParamMap(getRequest().getParameterMap());
		util.process();
		
		Map<String,RosterColumn> columnNameMap = util.getColumnNameMap();
		keySet = columnNameMap.keySet();
		columnCollection = columnNameMap.values();
		
		query.setUtil(util);
		pageList = rosterDataService.getPageList(query);
		
		//查询结束后，保存参数
		//saveParams();不再被动保存，改为用户主动保存参数
		
		return "query";
	}
	/**
	 * 高级查询
	 * @return
	 * @throws Exception
	 */
	public String queryForAdv() throws Exception{
		
		String[] classIds= getRequest().getParameterValues("classId");
		String[] configIds= getRequest().getParameterValues("configId");
		String[] fieldValues= getRequest().getParameterValues("fieldValue");
		String[] operators= getRequest().getParameterValues("operator");
		String[] parenthesisBefores= getRequest().getParameterValues("parenthesisBefore");
		String[] parenthesisAfters= getRequest().getParameterValues("parenthesisAfter");
		String[] logicalRels= getRequest().getParameterValues("logicalRel");
		
		List<RosterQueryCondForAdv> rosterQueryCondForAdvs=new ArrayList<RosterQueryCondForAdv>();
		if(configIds!=null&&classIds.length>0){
			for (int i=0;i<configIds.length;i++) {
				RosterQueryCondForAdv queryCondForAdv=new RosterQueryCondForAdv();
				queryCondForAdv.setClassId(classIds[i]);
				queryCondForAdv.setConfigId(configIds[i]);
				queryCondForAdv.setFieldValue(fieldValues[i]);
				queryCondForAdv.setLogicalRel(logicalRels[i]);
				queryCondForAdv.setOperator(operators[i]);
				queryCondForAdv.setParenthesisAfter(parenthesisAfters[i]);
				queryCondForAdv.setParenthesisBefore(parenthesisBefores[i]);
				
				rosterQueryCondForAdvs.add(queryCondForAdv);
			}
		}
		List<RosterColumn> columnList = rosterColumnService.getList(guid);
		RosterDataQueryForAdvUtil processor=RosterDataQueryForAdvUtil.newInstance(rosterQueryCondForAdvs, columnList);
		//高级查询预处理
		processor.process();
		
		Map<String,RosterColumn> columnNameMap = processor.getColumnInfoMap();
		keySet = columnNameMap.keySet();
		columnCollection = columnNameMap.values();
		
		query.setUtil(processor);
		pageList = rosterDataService.getPageList(query);
		
		return "query";
	}
	
	/**
	 * 主动保存查询参数
	 * @return
	 * @throws Exception
	 */
	public String saveQuery()throws Exception{
		validateParam();
		
		saveParams();
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	private RosterParam createParam(){
		HashMap<String,String[]> params = (HashMap<String, String[]>) getRequest().getParameterMap();
		byte[] data = SerializationUtils.serialize(params);
		RosterParam p = new RosterParam();
		p.setGuid(guid);
		p.setData(data);
		return p;
	}
	
	private void saveParams(){
		try{
			RosterParam p = createParam();
			rosterParamService.save(p);
		}catch (Exception e) {
			log.error("花名册参数保存出错:"+e.getMessage());
		}
	}
	
	public String export() throws Exception{
		validateParam();
		List<RosterColumn> columnList = rosterColumnService.getList(guid);
		
		RosterDataQueryUtil util = new RosterDataQueryUtil();
		util.setConfigList(relatedRosterList);
		util.setColumnList(columnList);
		util.setParamMap(getRequest().getParameterMap());
		util.process();
		
		Map<String,RosterColumn> columnNameMap = util.getColumnNameMap();
		keySet = columnNameMap.keySet();
		columnCollection = columnNameMap.values();
		
		query.setUtil(util);
		List<Map<String,String>> dataList = rosterDataService.getList(query);
		
		exportProcess(dataList);
		return null;
	}
	/**
	 * 高级查询导出
	 * @return
	 * @throws Exception
	 */
	public String exportForAdv() throws Exception{
		String[] classIds= getRequest().getParameterValues("classId");
		String[] configIds= getRequest().getParameterValues("configId");
		String[] fieldValues= getRequest().getParameterValues("fieldValue");
		String[] operators= getRequest().getParameterValues("operator");
		String[] parenthesisBefores= getRequest().getParameterValues("parenthesisBefore");
		String[] parenthesisAfters= getRequest().getParameterValues("parenthesisAfter");
		String[] logicalRels= getRequest().getParameterValues("logicalRel");
		
		List<RosterQueryCondForAdv> rosterQueryCondForAdvs=new ArrayList<RosterQueryCondForAdv>();
		if(configIds!=null&&classIds.length>0){
			for (int i=0;i<configIds.length;i++) {
				RosterQueryCondForAdv queryCondForAdv=new RosterQueryCondForAdv();
				queryCondForAdv.setClassId(classIds[i]);
				queryCondForAdv.setConfigId(configIds[i]);
				queryCondForAdv.setFieldValue(fieldValues[i]);
				queryCondForAdv.setLogicalRel(logicalRels[i]);
				queryCondForAdv.setOperator(operators[i]);
				queryCondForAdv.setParenthesisAfter(parenthesisAfters[i]);
				queryCondForAdv.setParenthesisBefore(parenthesisBefores[i]);
				
				rosterQueryCondForAdvs.add(queryCondForAdv);
			}
		}
		List<RosterColumn> columnList = rosterColumnService.getList(guid);
		RosterDataQueryForAdvUtil processor=RosterDataQueryForAdvUtil.newInstance(rosterQueryCondForAdvs, columnList);
		//高级查询预处理
		processor.process();
		
		Map<String,RosterColumn> columnNameMap = processor.getColumnInfoMap();
		keySet = columnNameMap.keySet();
		columnCollection = columnNameMap.values();
		
		query.setUtil(processor);
		List<Map<String,String>> dataList = rosterDataService.getList(query);
		
		exportProcess(dataList);
		return null;
	}
	
	private void exportProcess(List<Map<String,String>> dataList) throws Exception{
		getResponse().reset();
		getResponse().setCharacterEncoding("utf-8");
		getResponse().setContentType("application/vnd.ms-excel");
		String useragent = getRequest().getHeader("user-agent");
		String disposition = DownloadFilenameUtil.fileDisposition(useragent, "花名册.xls");
		getResponse().setHeader("Content-Disposition", disposition);
		WritableWorkbook wwb = Workbook.createWorkbook( getResponse().getOutputStream() );
		WritableSheet sheet1 = wwb.createSheet(TimeUtil.getDay(), 0);
		
		sheet1.addCell(new Label( 0, 0, "序号" ));
		int i=0;
		for(RosterColumn column:columnCollection){
			i++;
			sheet1.addCell(new Label( i, 0, column.getName() ));
		}
		
		for(int m=0;m<dataList.size();m++){
			Map<String,String> data = dataList.get(m);
			int n=0;
			sheet1.addCell(new Label( 0, m+1, data.get("RN") ));
			for(String key:keySet){
				sheet1.addCell(new Label( n+1, m+1, data.get(key) ));
				n++;
			}
		}
		wwb.write();
		wwb.close();
	}
	
	/**
	 * 另存为
	 * @return
	 * @throws Exception
	 */
	public String saveOther()throws Exception{
		validateParam();
		
		Roster roster = new Roster();
		roster.setGuid(guid);
		roster.setName(name);
		roster.setRosterType(rosterType);
		roster.setDescription(description);
		roster.setCreatetime(new Date());
		roster.setCreator(SessionFactory.getUser().getYhm());
		guid = roster.getGuid();//替换为复制后的花名册
		RosterParam p = createParam();
		rosterService.saveOther(roster,p);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	private void validateParam(){
		RosterConfigRelationQuery relationQuery = new RosterConfigRelationQuery();
		relationQuery.setRosterId(guid);
		relatedRosterList = rosterConfigRelationService.getList(relationQuery);
		RosterValidUtil valid = new RosterValidUtil();
		valid.validate(relatedRosterList, getRequest().getParameterMap());
	}
	
	public void setRosterConfigService(IRosterConfigService rosterConfigService) {
		this.rosterConfigService = rosterConfigService;
	}

	public List<RosterConfig> getConfigList() {
		return configList;
	}

	public void setRosterConfigRelationService(
			IRosterConfigRelationService rosterConfigRelationService) {
		this.rosterConfigRelationService = rosterConfigRelationService;
	}

	public void setRosterDataService(IRosterDataService rosterDataService) {
		this.rosterDataService = rosterDataService;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public PageList<Map<String, String>> getPageList() {
		return pageList;
	}

	public RosterDataQuery getQuery() {
		return query;
	}

	public void setQuery(RosterDataQuery query) {
		this.query = query;
	}

	public void setRosterColumnService(IRosterColumnService rosterColumnService) {
		this.rosterColumnService = rosterColumnService;
	}

	public Set<String> getKeySet() {
		return keySet;
	}

	public Collection<RosterColumn> getColumnCollection() {
		return columnCollection;
	}

	public void setRosterParamService(IRosterParamService rosterParamService) {
		this.rosterParamService = rosterParamService;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRosterType() {
        return rosterType;
    }

    public void setRosterType(String rosterType) {
        this.rosterType = rosterType;
    }

    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setRosterService(IRosterService rosterService) {
		this.rosterService = rosterService;
	}
	
}
