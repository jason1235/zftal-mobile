package com.zfsoft.mobile.reportFix.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zfsoft.common.log.User;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.mobile.ballot.entity.Ballot;
import com.zfsoft.mobile.reportFix.dao.IReportFixDao;
import com.zfsoft.mobile.reportFix.entity.FixType;
import com.zfsoft.mobile.reportFix.entity.FixTypeQuery;
import com.zfsoft.mobile.reportFix.entity.ReportFixCountEntity;
import com.zfsoft.mobile.reportFix.entity.ReportFixEntity;
import com.zfsoft.mobile.reportFix.entity.ReportFixPicsEntity;
import com.zfsoft.mobile.reportFix.entity.ReportFixQuery;
import com.zfsoft.mobile.reportFix.service.IReportFixService;
import com.zfsoft.mobile.vote.entity.VoteMainEntity;

/**
 * 报修serviceimpl
 * @author liucb
 *
 */
public class ReportFixServicelmpl implements IReportFixService{
    private IReportFixDao reportFixDao;
 
   
    /**
	 * 报修列表
	 * @param reportFixQuery
	 * @return
	 */
	public PageList<ReportFixEntity> getList(ReportFixQuery reportFixQuery){
		PageList<ReportFixEntity> pageList = new PageList<ReportFixEntity>();
		Paginator paginator = new Paginator();
		if(reportFixQuery!=null){
			paginator.setItemsPerPage(reportFixQuery.getPerPageSize());
			paginator.setPage((Integer)reportFixQuery.getToPage());
			paginator.setItems(reportFixDao.getListCount(reportFixQuery));
			pageList.setPaginator(paginator);
			if((Integer)reportFixQuery.getToPage() > paginator.getPages()){
				return pageList;
			}
			if(paginator.getBeginIndex() <= paginator.getItems()){
				reportFixQuery.setStartRow(paginator.getBeginIndex());
				reportFixQuery.setEndRow(paginator.getEndIndex());
				List<ReportFixEntity> list = reportFixDao.getList(reportFixQuery);
				
				//循环遍历报修信息,获取相应的图片信息
				/*for (int i = 0; i < list.size(); i++) {
					ReportFixEntity reportFixEntity = list.get(i);
					if(reportFixEntity!=null){
						String fixObjId = reportFixEntity.getId();
						reportFixQuery.setId(fixObjId);
						List<ReportFixPicsEntity> fixObjectPictureEntities = reportFixDao.getFixPictures(reportFixQuery);
						if(fixObjectPictureEntities==null){
							fixObjectPictureEntities = new ArrayList<ReportFixPicsEntity>();
						}
						reportFixEntity.setPictures(fixObjectPictureEntities);
					}
				}*/
				pageList.addAll(list);
			}
		}
		return pageList;
	}
	
	/**
	 * 报修列表count
	 * @param reportFixQuery
	 * @return
	 */
	public int getListCount(ReportFixQuery reportFixQuery){
		return reportFixDao.getListCount(reportFixQuery);
	}
	
    /**
     * 报修相关图片	
     * @param reportFixQuery
     * @return
     */
	public List<ReportFixPicsEntity> getFixPictures(ReportFixQuery reportFixQuery){
		return reportFixDao.getFixPictures(reportFixQuery);
	}
	
	/**
	 * 报修统计
	 * @return
	 */
	public List<ReportFixCountEntity> getCountAmount(){
		return reportFixDao.getCountAmount();
	}
	
	/**
	 * 插入报修
	 * @param reportFixEntity
	 */
	public void insertReportFix(ReportFixEntity reportFixEntity){
		reportFixDao.insertReportFix(reportFixEntity);
	}
	
	/**
	 * 插入图片
	 * @param reportFixPicsEntity
	 */
	public void insertReportFixPicture(ReportFixPicsEntity reportFixPicsEntity){
		reportFixDao.insertReportFixPicture(reportFixPicsEntity);
	}
	
	/**
	 * 提交评价
	 * @param reportFixEntity
	 */
	public void updateEvaluateById(ReportFixEntity reportFixEntity){
		reportFixDao.updateEvaluateById(reportFixEntity);
	}
	
	/**
	 * 更新状态
	 * @param reportFixEntity
	 */
	public void updateStatusById(ReportFixEntity reportFixEntity){
		reportFixDao.updateStatusById(reportFixEntity);
	}
	
	/**
	 * 后台列表
	 * @param reportFixQuery
	 * @return
	 */
	public PageList<ReportFixEntity> getYdhtList(ReportFixQuery query){
		 PageList<ReportFixEntity> pageList = new PageList<ReportFixEntity>();
			Paginator paginator = new Paginator();
			if(query!=null){
				paginator.setItemsPerPage(query.getPerPageSize());
				paginator.setPage((Integer)query.getToPage());
				paginator.setItems(reportFixDao.getYdhtListCount(query));
				pageList.setPaginator(paginator);
				if((Integer)query.getToPage() > paginator.getPages()){
					return pageList;
				}
				if(paginator.getBeginIndex() <= paginator.getItems()){
					query.setStartRow(paginator.getBeginIndex());
					query.setEndRow(paginator.getEndIndex());
					List<ReportFixEntity> list = reportFixDao.getYdhtList(query);
					pageList.addAll(list);
				}
			}
			return pageList;
	}
	
	/**
	 * 后台列表count
	 * @param reportFixQuery
	 * @return
	 */
	public int getYdhtListCount(ReportFixQuery reportFixQuery){
		return reportFixDao.getYdhtListCount(reportFixQuery);
	}
	
	/**
	 * 后台删除
	 * @param params
	 * @return
	 */
	public int deleteReportFixByReportFixId(Map<String,Object> params){
		return reportFixDao.deleteReportFixByReportFixId(params);
	}
	
	/**
	 * 获取报修详情
	 * @return
	 */
	public ReportFixEntity getReportFixById(Map<String,Object> params){
		return reportFixDao.getReportFixById(params);
	}
	
	public IReportFixDao getReportFixDao() {
		return reportFixDao;
	}
	
	public void setReportFixDao(IReportFixDao reportFixDao) {
		this.reportFixDao = reportFixDao;
	}

	@Override
	public void serRepair(String fixId, String repairId) {
		reportFixDao.serRepair(fixId,repairId);
	}

	@Override
	public User getUser(String username) {
		return reportFixDao.getUser(username);
	}

	@Override
	public List<FixType> getFixType() {
		return reportFixDao.getFixType();
	}

	@Override
	public String selectTypeName(String type) {
		return reportFixDao.selectTypeName(type);
	}

	@Override
	public List<FixType> selectHtFixTypeList(FixTypeQuery query) {
		PageList<FixType> list = new PageList<FixType>();
		Paginator paginator = new Paginator();
		if (query != null) {
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage(query.getToPage());
			paginator.setItems(reportFixDao.countFixType());
			list.setPaginator(paginator);
			
			if (paginator.getBeginIndex() <= paginator.getItems()) {
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				list.addAll(reportFixDao.selectHtFixTypeList(query));
			}
		}
		return list;
	}

	@Override
	public void deleteFixType(String id) {
		reportFixDao.deleteFixType(id);
	}

	@Override
	public FixType getFixTypeById(String id) {
		return reportFixDao.getFixTypeById(id);
	}

	@Override
	public void updateFixTypeById(FixTypeQuery fixTypeQuery) {
		reportFixDao.updateFixTypeById(fixTypeQuery);
	}

	@Override
	public void insertReportFixType(FixTypeQuery fixTypeQuery) {
		reportFixDao.insertReportFixType(fixTypeQuery);
	}

	@Override
	public List<ReportFixEntity> selectExpReportFixList() {
		return reportFixDao.selectExpReportFixList();
	}
}
