package com.zfsoft.hrm.summary.roster.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.summary.roster.entity.RosterConfig;
import com.zfsoft.hrm.summary.roster.entity.RosterConfigRelation;
import com.zfsoft.hrm.summary.roster.html.RosterConfigHtmlUtil;
import com.zfsoft.hrm.summary.roster.query.RosterConfigQuery;
import com.zfsoft.hrm.summary.roster.service.svcinterface.IRosterConfigRelationService;
import com.zfsoft.hrm.summary.roster.service.svcinterface.IRosterConfigService;

/** 
 * 花名册配置action
 * @author jinjj
 * @date 2012-8-30 下午02:25:45 
 *  
 */
public class RosterConfigAction extends HrmAction {

	private static final long serialVersionUID = -2805129537037675777L;
	private IRosterConfigService rosterConfigService;
	private IRosterConfigRelationService rosterConfigRelationService;
	
	private RosterConfig config;
	private RosterConfigQuery query = new RosterConfigQuery();
	private List<RosterConfig> configList;
	private List<InfoClass> classList = new ArrayList<InfoClass>();
	private Map<String,Object> cntMap;
	private String rosterId;
	
	public String page() throws Exception{
		classList.add(InfoClassCache.getOverallInfoClass());
		List<InfoClass> allList = InfoClassCache.getInfoClasses();
		for(InfoClass c:allList){
			if(!c.isMoreThanOne()){
				classList.add(c);
			}
		}
		cntMap = rosterConfigService.getAllCounts();
		for(InfoClass c:classList){//整理，空数据占位
			if(cntMap.get(c.getGuid())==null){
				cntMap.put(c.getGuid(), "0");
			}
		}
		return "page";
	}
	
	public String children() throws Exception{
		configList = rosterConfigService.getList(query);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		map.put("result", configList);
		getValueStack().set(DATA, map);
		return DATA;
	}
	
	public String save() throws Exception{
		//TODO 需要判断同名的字段
		config.setCreatetime(new Date());
		rosterConfigService.save(config);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String input() throws Exception{
		
		return "input";
	}
	
	public String query() throws Exception{
		config = rosterConfigService.getById(config.getGuid());
		return "input";
	}
	
	public String update() throws Exception{
		rosterConfigService.update(config);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String delete() throws Exception{
		rosterConfigService.delete(config.getGuid());
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	/**
	 * 异步加载查询配置详细，用于花名册数据中，条件加载
	 * @return
	 * @throws Exception
	 */
	public String load() throws Exception{
		config = rosterConfigService.getById(config.getGuid());
		String html = RosterConfigHtmlUtil.parse(config);
		InfoProperty prop = config.getInfoProperty();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		map.put("html", html);
		if(Type.CODE.equals(prop.getTypeInfo().getName())){
			map.put("type", Type.CODE);
		}
		//记录花名册使用条件
		RosterConfigRelation relation = new RosterConfigRelation();
		relation.setRosterId(rosterId);
		relation.setConfigId(config.getGuid());
		relation.setCreatetime(new Date());
		rosterConfigRelationService.save(relation);
		getValueStack().set(DATA, map);
		return DATA;
	}
	
	/**
	 * 移除花名册使用条件
	 * @return
	 * @throws Exception
	 */
	public String cancel()throws Exception{
		RosterConfigRelation relation = new RosterConfigRelation();
		relation.setRosterId(rosterId);
		relation.setConfigId(config.getGuid());
		rosterConfigRelationService.delete(relation);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		getValueStack().set(DATA, map);
		return DATA;
	}
	
	public RosterConfig getConfig() {
		return config;
	}

	public void setConfig(RosterConfig config) {
		this.config = config;
	}

	public RosterConfigQuery getQuery() {
		return query;
	}

	public void setQuery(RosterConfigQuery query) {
		this.query = query;
	}

	public List<RosterConfig> getConfigList() {
		return configList;
	}

	public List<InfoClass> getClassList() {
		return classList;
	}

	public Map<String, Object> getCntMap() {
		return cntMap;
	}

	public void setRosterConfigService(IRosterConfigService rosterConfigService) {
		this.rosterConfigService = rosterConfigService;
	}

	public void setRosterConfigRelationService(
			IRosterConfigRelationService rosterConfigRelationService) {
		this.rosterConfigRelationService = rosterConfigRelationService;
	}

	public String getRosterId() {
		return rosterId;
	}

	public void setRosterId(String rosterId) {
		this.rosterId = rosterId;
	}

}
