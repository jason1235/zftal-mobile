package com.zfsoft.mobile.peEvaluation.service.impl;

import java.text.DecimalFormat;
import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.mobile.peEvaluation.dao.IPEEvaluationDao;
import com.zfsoft.mobile.peEvaluation.entity.ClassInfoEntity;
import com.zfsoft.mobile.peEvaluation.entity.DataAnalEntity;
import com.zfsoft.mobile.peEvaluation.entity.ExportEntity;
import com.zfsoft.mobile.peEvaluation.entity.InstituteInfoEntity;
import com.zfsoft.mobile.peEvaluation.entity.PEDataEntity;
import com.zfsoft.mobile.peEvaluation.entity.StudentInfoEntity;
import com.zfsoft.mobile.peEvaluation.query.ClassInfoQuery;
import com.zfsoft.mobile.peEvaluation.query.ClassScoreQuery;
import com.zfsoft.mobile.peEvaluation.query.PEDataQuery;
import com.zfsoft.mobile.peEvaluation.query.StudentInfoQuery;
import com.zfsoft.mobile.peEvaluation.service.IPEEvaluationService;

public class PEEvaluationServiceImpl implements IPEEvaluationService{
	private IPEEvaluationDao peEvaluationDao;
	
	/**
	 * 班级列表分页
	 * @return
	 */
	public PageList<ClassInfoEntity> getClassInfoList(ClassInfoQuery query){
		PageList<ClassInfoEntity> pageList = new PageList<ClassInfoEntity>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(peEvaluationDao.getClassInfoListCount(query));
			pageList.setPaginator(paginator);
			if((Integer)query.getToPage() > paginator.getPages()){
				return pageList;
			}
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<ClassInfoEntity> list = peEvaluationDao.getClassInfoList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}
	
	/**
	 * 班级总数
	 * @return
	 */
	public int getClassInfoListCount(ClassInfoQuery query){
		return peEvaluationDao.getClassInfoListCount(query);
	}
	
	/**
	 * 体育班级体侧分数列表
	 */
	public PageList<PEDataEntity> getClassScoreList(ClassScoreQuery query){
		PageList<PEDataEntity> pageList = new PageList<PEDataEntity>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(peEvaluationDao.getClassScoreListCount(query));
			pageList.setPaginator(paginator);
			if((Integer)query.getToPage() > paginator.getPages()){
				return pageList;
			}
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<PEDataEntity> list = peEvaluationDao.getClassScoreList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}
	
	/**
	 * 体育班级体侧分数列表总数
	 */
	public int getClassScoreListCount(ClassScoreQuery query){
		return peEvaluationDao.getClassScoreListCount(query);
	}
	
	/**
	 * 学生基本信息
	 */
	public StudentInfoEntity getStudentInfo(StudentInfoQuery query){
		return peEvaluationDao.getStudentInfo(query);
	}

	/**
	 * 根据班级查询学生信息列表
	 */
	public List<StudentInfoEntity> getStudentInfoListByClass(StudentInfoQuery query){
		return peEvaluationDao.getStudentInfoListByClass(query);
	}
	
	/**
	 * 查询是否存在学生体测记录
	 */
	public int checkHaveData(PEDataQuery query){
		return peEvaluationDao.checkHaveData(query);
	}
	
	/**
	 * 插入体测记录
	 */
	public int insertData(PEDataQuery query){
		return peEvaluationDao.insertData(query);
	}
	
	/**
	 * 更新体测记录
	 */
	public int updateData(PEDataQuery query){
		return peEvaluationDao.updateData(query);
	}
	
	/**
	 * 学生查询体测成绩
	 * @return
	 */
	public PEDataEntity queryStudentScore(PEDataQuery query){
		//暂时不计算分数占比
		return peEvaluationDao.queryStudentScore(query);
	}
	
	/**
	 * 学生成绩班级占比
	 * @param query
	 */
	public double countScoreClassPercent(ClassScoreQuery query){
		return peEvaluationDao.countScoreClassPercent(query);
	}
	
	/**
	 * 学生成绩学院占比
	 * @param query
	 */
	public double countScoreInstitutePercent(ClassScoreQuery query){
		return peEvaluationDao.countScoreInstitutePercent(query);
	}
	
	public IPEEvaluationDao getPeEvaluationDao() {
		return peEvaluationDao;
	}

	public void setPeEvaluationDao(IPEEvaluationDao peEvaluationDao) {
		this.peEvaluationDao = peEvaluationDao;
	}

	
	@Override
	public PageList<StudentInfoEntity> selectHtStudentList(StudentInfoQuery query) {
		PageList<StudentInfoEntity> list = new PageList<StudentInfoEntity>();
		Paginator paginator = new Paginator();
		if (query != null) {
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage(query.getToPage());
			paginator.setItems(peEvaluationDao.countHtStudent());
			list.setPaginator(paginator);
			
			if (paginator.getBeginIndex() <= paginator.getItems()) {
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				list.addAll(peEvaluationDao.selectHtStudentList(query));
			}
		}
		return list;
	}

	@Override
	public void delStuBySchoolNumber(String schoolNumber) {
		peEvaluationDao.delStuBySchoolNumber(schoolNumber);
	}

	@Override
	public int updateStuByPrimaryKey(StudentInfoQuery studentInfoEntity) {
		return peEvaluationDao.updateStuByPrimaryKey(studentInfoEntity);
	}

	@Override
	public PageList<PEDataEntity> selectHtPeDataEntities(PEDataQuery query) {
		PageList<PEDataEntity> list = new PageList<PEDataEntity>();
		Paginator paginator = new Paginator();
		if (query != null) {
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage(query.getToPage());
			paginator.setItems(peEvaluationDao.countHtPeData(query));
			list.setPaginator(paginator);
			
			if (paginator.getBeginIndex() <= paginator.getItems()) {
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				list.addAll(peEvaluationDao.selectHtPeData(query));
			}
		}
		return list;
	}

	@Override
	public List<InstituteInfoEntity> selectInstituteInfoEntities() {
		return peEvaluationDao.selectInstituteInfoEntities();
	}

	@Override
	public List<ClassInfoEntity> selectClassInfoByInsId(String instituteId) {
		return peEvaluationDao.selectClassInfoByInsId(instituteId);
	}

	@Override
	public List<DataAnalEntity> selectDataAnal(String dataType) {
		List<DataAnalEntity> list = peEvaluationDao.selectDataAnal(dataType);
		
		DecimalFormat df = new DecimalFormat("0.00");
		double a = 0; //学院人数
		double b = 0; //合格人数
		double c = 0; //优良人数
		double d = 0; //合格率
		double e = 0; //优良率
		for (DataAnalEntity dataAnal : list) {
			a = Double.parseDouble(dataAnal.getXyrs());
			b = Double.parseDouble(dataAnal.getXyhgrs());
			c = Double.parseDouble(dataAnal.getXyylrs());
			//学院人数不为0计算合格率和优良率
			if (a != 0) {
				d = b / a * 100;
				e = c / a * 100;
			}
			dataAnal.setQualified(df.format(d));
			dataAnal.setExcellent(df.format(e));
		}
		
		return list;
	}

	@Override
	public List<ExportEntity> selectExportList(String dataType) {
		return peEvaluationDao.selectExportList(dataType);
	}
}
