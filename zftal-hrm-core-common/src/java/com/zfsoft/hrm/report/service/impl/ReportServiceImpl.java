package com.zfsoft.hrm.report.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ibm.icu.text.SimpleDateFormat;
import com.zfsoft.dao.entities.IndexModel;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.config.IConstants;
import com.zfsoft.hrm.menu.business.IMenuBusiness;
import com.zfsoft.hrm.report.business.IReportXmlFileBusiness;
import com.zfsoft.hrm.report.entity.ReportQuery;
import com.zfsoft.hrm.report.entity.ReportView;
import com.zfsoft.hrm.report.entity.ReportXmlFile;
import com.zfsoft.hrm.report.file.XmlFileUtil;
import com.zfsoft.hrm.report.service.IReportService;
import com.zfsoft.hrm.report.xentity.Item;
import com.zfsoft.hrm.report.xentity.ReportContent;
import com.zfsoft.util.base.StringUtil;
/**
 * 报表服务实现
 * @author 沈鲁威 Patrick Shen
 * @since 2012-9-13
 * @version V1.0.0
 */
public class ReportServiceImpl implements IReportService {
	
	private IReportXmlFileBusiness reportXmlFileBusiness;
	private IMenuBusiness menuBusiness;

	public void setMenuBusiness(IMenuBusiness menuBusiness) {
		this.menuBusiness = menuBusiness;
	}

	public void setReportXmlFileBusiness(
			IReportXmlFileBusiness reportXmlFileBusiness) {
		this.reportXmlFileBusiness = reportXmlFileBusiness;
	}

	@Override
	public ReportView getViewById(String id) {
		return reportXmlFileBusiness.getView(id,null);
	}
	
	@Override
	public ReportView getViewById(String id,String snapTime) {
		return reportXmlFileBusiness.getView(id,snapTime);
	}

	@Override
	public ReportView getNullViewById(String id) {
		return reportXmlFileBusiness.getNullView(id);
	}
	@Override
	public int getValue(ReportQuery query){
		return reportXmlFileBusiness.getValue(query);
	}
	@Override
	public List<ReportXmlFile> getReportAllList() {
		return reportXmlFileBusiness.getReportXmlFileList(new ReportXmlFile());
	}
	@Override
	public List<String> getSnapTimeList(String id) {
		
		return reportXmlFileBusiness.getSnapTimeList(reportXmlFileBusiness.getById(id));
	}
	
	@Override
	public void add(ReportXmlFile reportXmlFile) {
		menuBusiness.addReportMenu(reportXmlFile);
		reportXmlFileBusiness.add(reportXmlFile);
		IndexModel model=menuBusiness.getById(reportXmlFile.getMenuId());
		model.setDyym("/summary/report_list.html?id="+reportXmlFile.getReportId());
		menuBusiness.modify(model);
		addMenu_qsfx(model, reportXmlFile.getReportId());
	}

	/**
	 * 添加趋势分析菜单
	 */
	private void addMenu_qsfx(IndexModel reportMenu,String reportId){
		IndexModel qsfx = new IndexModel();
		qsfx.setGnmkdm(reportMenu.getGnmkdm().replace(IConstants.REPORT_ROOT_MENU, IConstants.REPORT_QSFX_MENU));
		qsfx.setGnmkmc(reportMenu.getGnmkmc());
		qsfx.setXssx(reportMenu.getXssx());
		qsfx.setFjgndm(IConstants.REPORT_QSFX_MENU);
		qsfx.setDyym("/summary/reportview_list.html?id="+reportId);
		menuBusiness.insertMenu(qsfx);
	}
	
	@Override
	public void modify(ReportXmlFile entity) {
		ReportContent rcontent=XmlFileUtil.getXmlTitleSet(getReportById(entity.getReportId()).getContentStream());
		rcontent.setTitle(entity.getReportName());
		rcontent.setSql(entity.getSql());
		rcontent.setHistorySql(entity.getHistorySql());
		rcontent.setSftjqxfw(entity.getSftjqxfw());
		entity.setContent(XmlFileUtil.getXmlTitleSetByte(rcontent));
		reportXmlFileBusiness.modify(entity);
		IndexModel model=menuBusiness.getById(entity.getMenuId());
		model.setGnmkmc(entity.getReportName());
		menuBusiness.modify(model);
		IndexModel qsfx=menuBusiness.getById(entity.getMenuId().replace(IConstants.REPORT_ROOT_MENU, IConstants.REPORT_QSFX_MENU));
		if (qsfx == null) {
			addMenu_qsfx(model, entity.getReportId());
		} else {
			qsfx.setGnmkmc(entity.getReportName());
			menuBusiness.modify(qsfx);
		}
		
	}

	@Override
	public void remove(String id) {
		ReportXmlFile reportXmlFile=reportXmlFileBusiness.getById(id);
		reportXmlFileBusiness.remove(id);
		menuBusiness.remove(reportXmlFile.getMenuId());
		menuBusiness.remove(reportXmlFile.getMenuId().replace(IConstants.REPORT_ROOT_MENU, IConstants.REPORT_QSFX_MENU));
	}

	@Override
	public ReportXmlFile getReportById(String id) {
		return reportXmlFileBusiness.getById(id);
	}

	@Override
	public void removeColumn(ReportXmlFile reportXmlFile,String fieldName) {
		ReportContent rcontent=XmlFileUtil.getXmlTitleSet(reportXmlFile.getContentStream());
		for (Item item: rcontent.getColumns()) {
			if(fieldName.equals(item.getFieldName())){
				rcontent.getColumns().remove(item);
				break;
			}
		}
		reportXmlFile.setContent(XmlFileUtil.getXmlTitleSetByte(rcontent));
		reportXmlFileBusiness.modify(reportXmlFile);
	}

	@Override
	public void addColumn(ReportXmlFile reportXmlFile, Item item,String index) {
		ReportContent rcontent=XmlFileUtil.getXmlTitleSet(reportXmlFile.getContentStream());
		
		item.setFieldName("c"+new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()));
		
		if(!StringUtil.isEmpty(index)){
			rcontent.getColumns().add(Integer.parseInt(index),item);
		}else{
			rcontent.getColumns().add(item);
		}
		reportXmlFile.setContent(XmlFileUtil.getXmlTitleSetByte(rcontent));
		reportXmlFileBusiness.modify(reportXmlFile);
	}

	@Override
	public void modifyColumn(ReportXmlFile reportXmlFile, Item item) {
		ReportContent rcontent=XmlFileUtil.getXmlTitleSet(reportXmlFile.getContentStream());
		Item mitem=rcontent.getColumn(item.getFieldName());
		mitem.setCondition(item.getCondition());
		mitem.setName(item.getName());
		reportXmlFile.setContent(XmlFileUtil.getXmlTitleSetByte(rcontent));
		reportXmlFileBusiness.modify(reportXmlFile);
	}

	@Override
	public void removeRow(ReportXmlFile reportXmlFile,String fieldName) {
		ReportContent rcontent=XmlFileUtil.getXmlTitleSet(reportXmlFile.getContentStream());
		for (Item item: rcontent.getRows()) {
			if(fieldName.equals(item.getFieldName())){
				rcontent.getRows().remove(item);
				break;
			}
		}
		reportXmlFile.setContent(XmlFileUtil.getXmlTitleSetByte(rcontent));
		reportXmlFileBusiness.modify(reportXmlFile);
	}

	@Override
	public void addRow(ReportXmlFile reportXmlFile, Item item,String index) {
		ReportContent rcontent=XmlFileUtil.getXmlTitleSet(reportXmlFile.getContentStream());
		item.setFieldName("r"+new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()));
		if(!StringUtil.isEmpty(index)){
			rcontent.getRows().add(Integer.parseInt(index),item);
		}else{
			rcontent.getRows().add(item);
		}
		reportXmlFile.setContent(XmlFileUtil.getXmlTitleSetByte(rcontent));
		reportXmlFileBusiness.modify(reportXmlFile);
	}

	@Override
	public void modifyRow(ReportXmlFile reportXmlFile, Item item) {
		ReportContent rcontent=XmlFileUtil.getXmlTitleSet(reportXmlFile.getContentStream());
		Item mitem=rcontent.getRow(item.getFieldName());
		mitem.setCondition(item.getCondition());
		mitem.setName(item.getName());
		reportXmlFile.setContent(XmlFileUtil.getXmlTitleSetByte(rcontent));
		reportXmlFileBusiness.modify(reportXmlFile);
	}

	@Override
	public void columnLeft(String id, Item item) {
		ReportXmlFile reportXmlFile=reportXmlFileBusiness.getById(id);
		ReportContent rcontent=XmlFileUtil.getXmlTitleSet(reportXmlFile.getContentStream());
		rcontent.columnLeft(item.getFieldName());
		reportXmlFile.setContent(XmlFileUtil.getXmlTitleSetByte(rcontent));
		reportXmlFileBusiness.modify(reportXmlFile);		
	}
	
	@Override
	public void columnRight(String id, Item item) {
		ReportXmlFile reportXmlFile=reportXmlFileBusiness.getById(id);
		ReportContent rcontent=XmlFileUtil.getXmlTitleSet(reportXmlFile.getContentStream());
		rcontent.columnRight(item.getFieldName());
		reportXmlFile.setContent(XmlFileUtil.getXmlTitleSetByte(rcontent));
		reportXmlFileBusiness.modify(reportXmlFile);		
	}
	@Override
	public void rowUp(String id, Item item) {
		ReportXmlFile reportXmlFile=reportXmlFileBusiness.getById(id);
		ReportContent rcontent=XmlFileUtil.getXmlTitleSet(reportXmlFile.getContentStream());
		rcontent.rowUp(item.getFieldName());
		reportXmlFile.setContent(XmlFileUtil.getXmlTitleSetByte(rcontent));
		reportXmlFileBusiness.modify(reportXmlFile);		
	}
	@Override
	public void rowDown(String id, Item item) {
		ReportXmlFile reportXmlFile=reportXmlFileBusiness.getById(id);
		ReportContent rcontent=XmlFileUtil.getXmlTitleSet(reportXmlFile.getContentStream());
		rcontent.rowDown(item.getFieldName());
		reportXmlFile.setContent(XmlFileUtil.getXmlTitleSetByte(rcontent));
		reportXmlFileBusiness.modify(reportXmlFile);		
	}

	@Override
	public PageList<Map<String, Object>> findDetailPageList(ReportQuery query) {
		
		return reportXmlFileBusiness.findDetailPageList(query);
	}
}
