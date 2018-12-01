package com.zfsoft.hrm.summary.roster.business.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.baseinfo.dyna.html.ViewParse;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.summary.roster.business.IRosterDataBusiness;
import com.zfsoft.hrm.summary.roster.dao.daointerface.IRosterDataDao;
import com.zfsoft.hrm.summary.roster.query.RosterDataQuery;

/** 
 * @author jinjj
 * @date 2012-9-7 上午10:54:48 
 *  
 */
public class RosterDataBusinessImpl implements IRosterDataBusiness {

	private IRosterDataDao rosterDataDao;
	
	@Override
	public List<Map<String, String>> getList(RosterDataQuery query) {
		List<Map<String,String>> data = parseData(query.getPropList(),rosterDataDao.getList(query));
		return data;
	}

	@Override
	public PageList<Map<String, String>> getPage(RosterDataQuery query) {
		PageList<Map<String, String>> pageList = new PageList<Map<String, String>>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			
			paginator.setItems(rosterDataDao.getPagingCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<Map<String,String>> data = parseData(query.getPropList(),rosterDataDao.getPagingList(query));
				pageList.addAll(data);
			}
		}
		return pageList;
	}
	
	private List<Map<String,String>> parseData(List<InfoProperty> props,List<Map<String,Object>> dataMap){
		List<Map<String,String>> data = new ArrayList<Map<String,String>>();
		for(Map<String,Object> d: dataMap){
			Map<String,String> parsed = new LinkedHashMap<String,String>();
			parsed.put("RN", d.get("RN").toString());
			parsed.put("GH", d.get("GH").toString());
			parsed.put("XM", d.get("XM")==null?"":d.get("XM").toString());
			for(InfoProperty prop : props){
				String name = StringUtils.upperCase(prop.getFieldName());
				String value = ViewParse.parse(prop, d.get(name));
				parsed.put(name, value);
			}
			data.add(parsed);
		}
		return data;
	}

	public void setRosterDataDao(IRosterDataDao rosterDataDao) {
		this.rosterDataDao = rosterDataDao;
	}

}
