package com.zfsoft.hrm.seniorreport.service;

import java.util.List;

import com.zfsoft.hrm.report.entity.ReportQuery;
import com.zfsoft.hrm.seniorreport.entity.SeniorReportConfig;
import com.zfsoft.hrm.seniorreport.entity.SeniorReportConfigData;
import com.zfsoft.hrm.seniorreport.entity.SeniorReportHistroy;
import com.zfsoft.hrm.seniorreport.entity.SeniorReportView;
import com.zfsoft.hrm.seniorreport.entity.ViewItem;
import com.zfsoft.hrm.seniorreport.enums.SeniorReportType;

/**
 * 
 * @author ChenMinming
 * @date 2013-12-24
 * @version V1.0.0
 */
public interface ISeniorReportService {
	/**
	 * 获取报表数据
	 * @param id
	 * @return
	 */
	public SeniorReportView getViewById(String id);
	/**
	 * 获取历史报表数据
	 * @param id
	 * @return
	 */
	public SeniorReportView getViewById(String id, String snapTime);
	/**
	 * 获取空报表
	 * @param id
	 * @return
	 */
	public SeniorReportView getNullViewById(String id);
	/**
	 * 获取报表列表
	 * @return
	 */
	public List<SeniorReportConfigData> getReportList(SeniorReportConfigData query);
	/**
	 * 向数据库增加报表
	 * @param SeniorReportConfigData
	 */
	public void add(SeniorReportConfigData SeniorReportConfigData);

	public void add(SeniorReportConfig seniorReportConfig,SeniorReportType type);

	/**
	 * 修改过数据库报表
	 * @param reportXmlFile
	 */
	public void modify(SeniorReportConfigData SeniorReportConfigData);
	/**
	 * 删除数据库报表
	 * @param id
	 */
	public void remove(String id);
	/**
	 * 获取数据库报表
	 * @param id
	 * @return
	 */
	public SeniorReportConfigData getReportById(String id);
	/**
	 * 删除列表头
	 * @param fieldName
	 */
	public void removeColumn(SeniorReportConfigData data,String fieldName);
	/**
	 * 增加列表头
	 * @param index 
	 * @param fieldName
	 */
	public void addColumn(SeniorReportConfigData data, ViewItem viewItem, String pId);
	/**
	 * 修改列表头
	 * @param fieldName
	 */
	public void modifyColumn(SeniorReportConfigData data, ViewItem viewItem);
	/**
	 * 删除行表头
	 * @param reportXmlFile 
	 * @param fieldName
	 */
	public void removeRow(SeniorReportConfigData data, String fieldName);
	/**
	 * 增加行表头
	 * @param index 
	 * @param fieldName
	 */
	public void addRow(SeniorReportConfigData data, ViewItem viewItem, String pId);
	/**
	 * 修改行表头
	 * @param fieldName
	 */
	public void modifyRow(SeniorReportConfigData data, ViewItem viewItem);
	/**
	 * 左移
	 * @param id
	 * @param item
	 */
	public void columnLeft(String id, ViewItem viewItem);
	/**
	 * 右移
	 * @param id
	 * @param item
	 */
	public void columnRight(String id, ViewItem viewItem);
	/**
	 * 上移
	 * @param id
	 * @param item
	 */
	public void rowUp(String id, ViewItem viewItem);
	/**
	 * 下移
	 * @param id
	 * @param item
	 */
	public void rowDown(String id, ViewItem viewItem);
	/**
	 * 获取存档的报表列表
	 * @param id
	 * @return
	 */
	public List<SeniorReportHistroy> getSnapTimeList(SeniorReportHistroy query);
	/**
	 * 报表存档
	 * @param reportXmlFile
	 */
	public void saveHistroy(SeniorReportHistroy histroy);
	/**
	 * 删除存档
	 * @param reportXmlFile
	 */
	public void removeHistroy(String id);
	
	/**
	 * 尝试执行sql
	 * tryToExecSqL
	 * @param reportXmlFile
	 */
	public String tryToExecSqL(SeniorReportConfigData config, ViewItem viewItem);
}
