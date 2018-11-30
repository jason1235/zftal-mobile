package com.zfsoft.mobile.reportFix.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zfsoft.common.log.User;
import com.zfsoft.mobile.reportFix.entity.FixType;
import com.zfsoft.mobile.reportFix.entity.FixTypeQuery;
import com.zfsoft.mobile.reportFix.entity.ReportFixCountEntity;
import com.zfsoft.mobile.reportFix.entity.ReportFixEntity;
import com.zfsoft.mobile.reportFix.entity.ReportFixPicsEntity;
import com.zfsoft.mobile.reportFix.entity.ReportFixQuery;

public interface IReportFixDao {
	/**
	 * 报修列表
	 * @param reportFixQuery
	 * @return
	 */
	public List<ReportFixEntity> getList(ReportFixQuery reportFixQuery);
	
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
	public List<ReportFixEntity> getYdhtList(ReportFixQuery reportFixQuery);
	
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

	public void serRepair(@Param("fixId")String fixId,@Param("repairId") String repairId);

	public User getUser(@Param("username")String username);

	public List<FixType> getFixType();

	public String selectTypeName(@Param("type")String type);

	public int countFixType();

	public List<FixType> selectHtFixTypeList(FixTypeQuery query);

	public void deleteFixType(@Param("id")String id);

	public FixType getFixTypeById(@Param("id")String id);

	public void updateFixTypeById(FixTypeQuery fixTypeQuery);

	public void insertReportFixType(FixTypeQuery fixTypeQuery);

	public List<ReportFixEntity> selectExpReportFixList();
}
