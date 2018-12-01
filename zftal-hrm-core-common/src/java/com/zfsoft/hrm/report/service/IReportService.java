package com.zfsoft.hrm.report.service;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.report.entity.ReportQuery;
import com.zfsoft.hrm.report.entity.ReportView;
import com.zfsoft.hrm.report.entity.ReportXmlFile;
import com.zfsoft.hrm.report.xentity.Item;
/**
 * 报表服务接口 
 * @author 沈鲁威 Patrick Shen
 * @since 2012-9-13
 * @version V1.0.0
 */
public interface IReportService {
	/**
	 * 获取报表数据
	 * @param id
	 * @return
	 */
	public ReportView getViewById(String id);
	/**
	 * 获取历史报表数据
	 * @param id
	 * @return
	 */
	public ReportView getViewById(String id, String snapTime);
	/**
	 * 获取空报表
	 * @param id
	 * @return
	 */
	public ReportView getNullViewById(String id);
	/**
	 * 获取报表列表
	 * @return
	 */
	public List<ReportXmlFile> getReportAllList();
	/**
	 * 向数据库增加报表
	 * @param reportXmlFile
	 */
	public void add(ReportXmlFile reportXmlFile);
	/**
	 * 修改过数据库报表
	 * @param reportXmlFile
	 */
	public void modify(ReportXmlFile reportXmlFile);
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
	public ReportXmlFile getReportById(String id);
	/**
	 * 删除列表头
	 * @param fieldName
	 */
	public void removeColumn(ReportXmlFile reportXmlFile,String fieldName);
	/**
	 * 增加列表头
	 * @param index 
	 * @param fieldName
	 */
	public void addColumn(ReportXmlFile reportXmlFile, Item item, String index);
	/**
	 * 修改列表头
	 * @param fieldName
	 */
	public void modifyColumn(ReportXmlFile reportXmlFile, Item item);
	/**
	 * 删除行表头
	 * @param reportXmlFile 
	 * @param fieldName
	 */
	public void removeRow(ReportXmlFile reportXmlFile, String fieldName);
	/**
	 * 增加行表头
	 * @param index 
	 * @param fieldName
	 */
	public void addRow(ReportXmlFile reportXmlFile, Item item, String index);
	/**
	 * 修改行表头
	 * @param fieldName
	 */
	public void modifyRow(ReportXmlFile reportXmlFile, Item item);
	/**
	 * 左移
	 * @param id
	 * @param item
	 */
	public void columnLeft(String id, Item item);
	/**
	 * 右移
	 * @param id
	 * @param item
	 */
	public void columnRight(String id, Item item);
	/**
	 * 上移
	 * @param id
	 * @param item
	 */
	public void rowUp(String id, Item item);
	/**
	 * 下移
	 * @param id
	 * @param item
	 */
	public void rowDown(String id, Item item);
	/**
	 * 获取快照年份列表
	 * @param id
	 * @return
	 */
	public List<String> getSnapTimeList(String id);
	/**
	 * 获取某项数据
	 * @param query
	 * @return
	 */
	public int getValue(ReportQuery query);
	
	public PageList<Map<String, Object>> findDetailPageList(ReportQuery query);
}
