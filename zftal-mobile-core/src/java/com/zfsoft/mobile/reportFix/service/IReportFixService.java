package com.zfsoft.mobile.reportFix.service;

import java.util.List;
import java.util.Map;

import com.zfsoft.common.log.User;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.ballot.entity.Ballot;
import com.zfsoft.mobile.reportFix.entity.FixType;
import com.zfsoft.mobile.reportFix.entity.FixTypeQuery;
import com.zfsoft.mobile.reportFix.entity.ReportFixCountEntity;
import com.zfsoft.mobile.reportFix.entity.ReportFixEntity;
import com.zfsoft.mobile.reportFix.entity.ReportFixPicsEntity;
import com.zfsoft.mobile.reportFix.entity.ReportFixQuery;

/**
 * 报修service接口
 * @author liucb
 *
 */
public interface IReportFixService {
	/**
	 * 报修列表
	 * @param reportFixQuery
	 * @return
	 */
	public PageList<ReportFixEntity> getList(ReportFixQuery reportFixQuery);
	
	/**
	 * 报修列表count
	 * @param reportFixQuery
	 * @return
	 */
	public int getListCount(ReportFixQuery reportFixQuery);
	
    /**
     * 报修相关图片	
     * @param reportFixQuery
     * @return
     */
	public List<ReportFixPicsEntity> getFixPictures(ReportFixQuery reportFixQuery);
	
	/**
	 * 报修统计
	 * @return
	 */
	public List<ReportFixCountEntity> getCountAmount();
	
	/**
	 * 插入报修
	 * @param reportFixEntity
	 */
	public void insertReportFix(ReportFixEntity reportFixEntity);
	
	/**
	 * 插入图片
	 * @param reportFixPicsEntity
	 */
	public void insertReportFixPicture(ReportFixPicsEntity reportFixPicsEntity);
	
	/**
	 * 提交评价
	 * @param reportFixEntity
	 */
	public void updateEvaluateById(ReportFixEntity reportFixEntity);
	
	/**
	 * 更新状态
	 * @param reportFixEntity
	 */
	public void updateStatusById(ReportFixEntity reportFixEntity);
	
	/**
	 * 后台列表
	 * @param reportFixQuery
	 * @return
	 */
	public PageList<ReportFixEntity> getYdhtList(ReportFixQuery reportFixQuery);
	
	/**
	 * 后台列表count
	 * @param reportFixQuery
	 * @return
	 */
	public int getYdhtListCount(ReportFixQuery reportFixQuery);
	
	/**
	 * 后台删除
	 * @param params
	 * @return
	 */
	public int deleteReportFixByReportFixId(Map<String,Object> params);
	
	/**
	 * 获取报修详情
	 * @return
	 */
	public ReportFixEntity getReportFixById(Map<String,Object> params);

	public void serRepair(String fixId, String repairId);

	public User getUser(String username);

	public List<FixType> getFixType();

	public String selectTypeName(String type);

	public List<FixType> selectHtFixTypeList(FixTypeQuery fixTypeQuery);

	public void deleteFixType(String id);

	public FixType getFixTypeById(String id);

	public void updateFixTypeById(FixTypeQuery fixTypeQuery);

	public void insertReportFixType(FixTypeQuery fixTypeQuery);

	public List<ReportFixEntity> selectExpReportFixList();
}
