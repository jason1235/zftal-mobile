package com.zfsoft.hrm.summary.roster.business.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.summary.roster.business.IRosterConfigBusiness;
import com.zfsoft.hrm.summary.roster.dao.daointerface.IRosterConfigDao;
import com.zfsoft.hrm.summary.roster.entity.RosterConfig;
import com.zfsoft.hrm.summary.roster.query.RosterConfigQuery;

/** 
 * 花名册配置
 * @author jinjj
 * @date 2012-8-31 上午09:18:08 
 *  
 */
public class RosterConfigBusinessImpl implements IRosterConfigBusiness {

	private IRosterConfigDao rosterConfigDao;
	
	@Override
	public void save(RosterConfig config) {
		RosterConfig old = rosterConfigDao.getById(config.getGuid());
		if(old != null){
			throw new RuleException("该字段配置已存在");
		}
		rosterConfigDao.insert(config);
	}

	@Override
	public void update(RosterConfig config) {
		rosterConfigDao.update(config);
	}

	@Override
	public RosterConfig getById(String guid) {
		RosterConfig config = rosterConfigDao.getById(guid);
		InfoClass clazz = InfoClassCache.getInfoClass(config.getClassid());
		InfoProperty property = clazz.getPropertyById(config.getGuid());
		config.setInfoProperty(property);
		return config;
	}

	@Override
	public void delete(String guid) {
		rosterConfigDao.delete(guid);
	}

	@Override
	public PageList<RosterConfig> getPagingList(RosterConfigQuery query) {
		PageList<RosterConfig> pageList = new PageList<RosterConfig>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			
			paginator.setItems(rosterConfigDao.getPagingCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				pageList.addAll(rosterConfigDao.getPagingList(query));
			}
		}
		return pageList;
	}
	
	@Override
	public List<RosterConfig> getList(RosterConfigQuery query) {
		List<RosterConfig> list = rosterConfigDao.getList(query);
		List<RosterConfig> list2 = new ArrayList<RosterConfig>();
		for(int i=0;i<list.size();i++){
			RosterConfig config = list.get(i);
			InfoClass clazz = InfoClassCache.getInfoClass(config.getClassid());
			if(clazz==null){
				continue;
			}
			InfoProperty property = clazz.getPropertyById(config.getGuid());
			config.setInfoProperty(property);
			if(property== null){//迭代中无法找到对应的infoproperty
//				throw new RuleException("该信息类查询字段的信息已过期，请对比信息类数据");
				continue;
			}
			//list.set(i, config);
			list2.add(config);
		}
		return list2;
	}
	
	@Override
	public Map<String, Object> getAllCounts() {
		List<Map<String,Object>> list = rosterConfigDao.getCountByClassid();
		Map<String,Object> map = new HashMap<String,Object>();
		for(Map<String,Object> m :list){
			map.put((String)m.get("CLASSID"), m.get("CNT"));
		}
		return map;
	}

	public void setRosterConfigDao(IRosterConfigDao rosterConfigDao) {
		this.rosterConfigDao = rosterConfigDao;
	}

}
