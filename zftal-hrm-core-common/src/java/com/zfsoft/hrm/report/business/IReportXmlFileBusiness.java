package com.zfsoft.hrm.report.business;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.report.entity.ReportQuery;
import com.zfsoft.hrm.report.entity.ReportView;
import com.zfsoft.hrm.report.entity.ReportXmlFile;
/**
 * 报表业务接口 
 * @author 沈鲁威 Patrick Shen
 * @since 2012-9-13
 * @version V1.0.0
 */
public interface IReportXmlFileBusiness {
	/**
	 * 对应的infoClass增加一个表头配置文件
	 * @param infoClass
	 */
	public void add(ReportXmlFile entity);
	/**
	 * 移除对应的表头配置文件
	 * @param classId
	 */
	public void remove(String classId);
	/**
	 *修改对应的表头配置文件
	 * @param entity
	 */
	public void modify(ReportXmlFile entity);
	/**
	 * 获取
	 * @param id
	 * @return
	 */
	public ReportXmlFile getById(String id);
	/**
	 * 获取
	 * @param id
	 * @return
	 */
	public List<ReportXmlFile> getReportXmlFileList(ReportXmlFile query);
	/**
	 * 获取报表
	 * @param id
	 * @return
	 */
	public ReportView getView(String id, String snapTime);
	/**
	 * 获取空报表
	 * @param id
	 * @return
	 */
	public ReportView getNullView(String id);
	/**
	 * 获取快照时间列表
	 * @param query
	 * @return
	 */
	public List<String> getSnapTimeList(ReportXmlFile query);
	/**
	 * 获取某项数据
	 * @param query
	 * @return
	 */
	public int getValue(ReportQuery query);
	
	/**
	 * 获取某项数据详情
	 * @param query
	 * @return
	 */
	public PageList<Map<String, Object>> findDetailPageList(ReportQuery query);
}
