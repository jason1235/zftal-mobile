package com.zfsoft.hrm.report.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.dataprivilege.util.DeptFilterUtil;
import com.zfsoft.hrm.report.business.IReportXmlFileBusiness;
import com.zfsoft.hrm.report.dao.IReportXmlFileDao;
import com.zfsoft.hrm.report.dto.Col;
import com.zfsoft.hrm.report.entity.ReportQuery;
import com.zfsoft.hrm.report.entity.ReportView;
import com.zfsoft.hrm.report.entity.ReportXmlFile;
import com.zfsoft.hrm.report.file.XmlFileUtil;
import com.zfsoft.hrm.report.xentity.Item;
import com.zfsoft.hrm.report.xentity.ReportContent;
import com.zfsoft.util.base.StringUtil;
/**
 * 报表业务实现 
 * @author 沈鲁威 Patrick Shen
 * @since 2012-9-13
 * @version V1.0.0
 */
public class ReportXmlFileBusinessImpl implements IReportXmlFileBusiness{
	
	private IReportXmlFileDao reportXmlFileDao;

	public void setReportXmlFileDao(IReportXmlFileDao reportXmlFileDao) {
		this.reportXmlFileDao = reportXmlFileDao;
	}

	@Override
	public void add(ReportXmlFile entity) {
		ReportContent reportContent=new ReportContent();
		reportContent.setTitle(entity.getReportName());
		reportContent.setSql(entity.getSql());
		reportContent.setHistorySql(entity.getHistorySql());
		reportContent.setSftjqxfw(entity.getSftjqxfw());
		entity.setContent(XmlFileUtil.getXmlTitleSetByte(reportContent));
		reportXmlFileDao.insert(entity);
	}

	@Override
	public void remove(String id) {
		ReportXmlFile model=getById(id);
		if(model!=null){
			reportXmlFileDao.delete(model);
		}
	}

	@Override
	public void modify(ReportXmlFile entity) {
		reportXmlFileDao.update(entity);
	}

	@Override
	public ReportXmlFile getById(String id) {
		if(StringUtils.isEmpty(id)){
			return null;
		}
		ReportXmlFile entity=new ReportXmlFile();
		entity.setReportId(id);
		List<ReportXmlFile> list=reportXmlFileDao.findByQuery(entity);
		if(list!=null&&list.size()>0){
			entity=list.get(0);
		}
		if(entity!=null){
			ReportContent reportContent=XmlFileUtil.getXmlTitleSet(entity.getContentStream());
			entity.setSql(reportContent.getSql());
			entity.setHistorySql(reportContent.getHistorySql());
			entity.setSftjqxfw(reportContent.getSftjqxfw());
		}
		return entity;
	}
	
	private String addDeptFilter(String sql){
		String express = "";
		String deptFilter=DeptFilterUtil.getCondition("", "dwm");
		if (!StringUtil.isEmpty(deptFilter)) {
			express += "(" + deptFilter + ")";
			if(deptFilter.indexOf("where")!=-1){				
				express = sql+" where " + express;
			}else{
				express = sql+" and " + express;
			}
		}
		return express;
	}

	@Override
	public ReportView getView(String id,String snapTime) {
		if(id==null)return null;
		ReportXmlFile query=new ReportXmlFile();
		query.setReportId(id);
		List<ReportXmlFile> list=reportXmlFileDao.findByQuery(query);
		if(list!=null&&list.size()>0){
			query=list.get(0);
		}
		
		ReportView reportView=new ReportView();
		ReportContent reportContent=XmlFileUtil.getXmlTitleSet(query.getContentStream());
		
		System.out.println(XmlFileUtil.getXmlTitleSetString(reportContent));
		reportView.setReportTitle(reportContent.getTitle());
		Item item=new Item();
		item.setName(reportContent.getStartTitle());
		reportView.getTitles().add(item);
		reportView.putTitle(reportContent.getColumns());
		ReportQuery queryV=new ReportQuery();
		
		if(StringUtil.isEmpty(snapTime)){
			if("1".equals(reportContent.getSftjqxfw())){   //是否添加权限范围
				String filterSql = addDeptFilter(reportContent.getSql());
				queryV.setSql(filterSql);
			}else{
				queryV.setSql(reportContent.getSql());
			}
			
		}else{
			if("1".equals(reportContent.getSftjqxfw())){   //是否添加权限范围
				queryV.setSnapTime(snapTime);
				String filterSql = addDeptFilter(reportContent.getHistorySql());
				queryV.setSql(filterSql);
			}
			else{
				queryV.setSnapTime(snapTime);
				queryV.setSql(reportContent.getHistorySql());
			}
		}
		
		List<Col> values;
		Col valueCol=new Col();
		for(Item row:reportContent.getRows()){
			values=new ArrayList<Col>();
			queryV.setRowCondition(row.getCondition());
			for(Item column:reportContent.getColumns()){
				queryV.setColumnCondition(column.getCondition());
				valueCol=new Col();
				valueCol.setCondition(column.getCondition());
				valueCol.setValue(""+reportXmlFileDao.count(queryV));
				values.add(valueCol);
			}
			reportView.putValues(row, values);
		}
		
		return reportView;
	}
	@Override
	public int getValue(ReportQuery query){
		return reportXmlFileDao.count(query);
	}
	
	@Override
	public PageList<Map<String, Object>> findDetailPageList(ReportQuery query) {
		PageList<Map<String, Object>> pageList = new PageList<Map<String, Object>>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			
			paginator.setItems(reportXmlFileDao.count(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<Map<String, Object>> list = reportXmlFileDao.getPagingList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}
	
	@Override
	public ReportView getNullView(String id) {
		ReportXmlFile query=new ReportXmlFile();
		query.setReportId(id);
		List<ReportXmlFile> list=reportXmlFileDao.findByQuery(query);
		if(list!=null&&list.size()>0){
			query=list.get(0);
		}
		
		ReportView reportView=new ReportView();
		ReportContent reportContent=XmlFileUtil.getXmlTitleSet(query.getContentStream());
		if(reportContent==null){
			return null;
		}
		System.out.println(XmlFileUtil.getXmlTitleSetString(reportContent));
		reportView.setReportTitle(reportContent.getTitle());
		Item item=new Item();
		item.setName(reportContent.getStartTitle());
		reportView.getTitles().add(item);
		reportView.putTitle(reportContent.getColumns());
		ReportQuery queryV=new ReportQuery();
		queryV.setSql(reportContent.getSql());
		List<Col> values;
		if(reportContent.getRows()==null){
			return reportView;
		}
		Col valueCol=new Col();
		for(Item row:reportContent.getRows()){
			values=new ArrayList<Col>();
			queryV.setRowCondition(row.getCondition());
			for(Item column:reportContent.getColumns()){
				queryV.setColumnCondition(column.getCondition());
				valueCol=new Col();
				valueCol.setCondition(column.getCondition());
				valueCol.setValue("0");
				values.add(valueCol);
			}
			reportView.putValues(row, values);
		}
		return reportView;
	}

	@Override
	public List<ReportXmlFile> getReportXmlFileList(ReportXmlFile query) {
		return reportXmlFileDao.findByQuery(query);
	}

	@Override
	public List<String> getSnapTimeList(ReportXmlFile query) {
		ReportContent reportContent=XmlFileUtil.getXmlTitleSet(query.getContentStream());
		ReportQuery queryV=new ReportQuery();
		queryV.setSql(reportContent.getHistorySql());
		return reportXmlFileDao.snapTimeList(queryV);
	}


	
}
