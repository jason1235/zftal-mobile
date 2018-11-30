package com.zfsoft.mobile.peEvaluation.dao;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;

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

/**
 * 体育测评dao
 * @author liucb
 */
public interface IPEEvaluationDao {

	/**
	 * 班级列表分页
	 * @return
	 */
	List<ClassInfoEntity> getClassInfoList(ClassInfoQuery query);
	
	/**
	 * 班级总数
	 * @return
	 */
	int getClassInfoListCount(ClassInfoQuery query);
	
	/**
	 * 体育班级体侧分数列表
	 */
	List<PEDataEntity> getClassScoreList(ClassScoreQuery query);
	
	/**
	 * 体育班级体侧分数列表总数
	 */
	int getClassScoreListCount(ClassScoreQuery query);
	
	/**
	 * 学生基本信息
	 */
	StudentInfoEntity getStudentInfo(StudentInfoQuery query);
	
	/**
	 * 根据班级查询学生信息列表
	 */
	List<StudentInfoEntity> getStudentInfoListByClass(StudentInfoQuery query);
	
	/**
	 * 查询是否存在学生体测记录
	 */
	int checkHaveData(PEDataQuery query);
	
	/**
	 * 插入体测记录
	 */
	int insertData(PEDataQuery query);
	
	/**
	 * 更新体测记录
	 */
	int updateData(PEDataQuery query);
	
	/**
	 * 学生查询体测成绩
	 * @return
	 */
	PEDataEntity queryStudentScore(PEDataQuery query);
	
	/**
	 * 学生成绩班级占比
	 * @param query
	 */
	double countScoreClassPercent(ClassScoreQuery query);
	
	/**
	 * 学生成绩学院占比
	 * @param query
	 */
	double countScoreInstitutePercent(ClassScoreQuery query);

	/**
	 * 后台学生信息列表
	 * @param query
	 */
	List<StudentInfoEntity> selectHtStudentList(StudentInfoQuery query);

	//查询后台学生列表数量
	int countHtStudent();

	//根据学籍号删除学生信息
	void delStuBySchoolNumber(@Param("schoolNumber")String schoolNumber);
	
	//根据学籍号更新学生信息
	int updateStuByPrimaryKey(StudentInfoQuery studentInfoEntity);

	//后台记录总数
	int countHtPeData(PEDataQuery query);

	//后台分数记录
	List<PEDataEntity> selectHtPeData(PEDataQuery query);

	//查询学院列表
	List<InstituteInfoEntity> selectInstituteInfoEntities();

	//根据学院id 查询班级列表
	List<ClassInfoEntity> selectClassInfoByInsId(@Param("instituteId")String instituteId);
	
	//体育测评数据分析
	List<DataAnalEntity> selectDataAnal(@Param("dataType")String dataType);
	
	//导出
	List<ExportEntity> selectExportList(@Param("dataType")String dataType);
}
