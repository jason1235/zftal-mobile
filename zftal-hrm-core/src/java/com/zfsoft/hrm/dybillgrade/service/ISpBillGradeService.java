package com.zfsoft.hrm.dybillgrade.service;

import com.zfsoft.hrm.dybillgrade.entity.SpBillGradeConfig;
import com.zfsoft.hrm.dybillgrade.entity.SpBillGradeResult;
import com.zfsoft.hrm.dybillgrade.enums.GradeBusinessEnums;

/**
 * 
 * @author ChenMinming
 * @date 2014-6-12
 * @version V1.0.0
 */
public interface ISpBillGradeService {
	/**
	 * 根据表单的id以及业务编号获取表单评分配置
	 * 无则返回null
	 * @param billConfigId
	 * @param gradeBusinessEnums
	 * @return
	 */
	public SpBillGradeConfig getGradeConfig(String billConfigId,GradeBusinessEnums gradeBusinessEnums);
	/**
	 * 进行评分计算
	 * @param configId 评分配置id
	 * @param billInstanceId 表单实例id
	 * @param again 重复评分时允许覆盖
	 * @return 评分结果
	 */
	public SpBillGradeResult doGrade(String configId,String billInstanceId,boolean again);
	/**
	 * 查询根据指定id查询评分结果
	 * @param id
	 * @return
	 */
	public SpBillGradeResult getGradeResult(String id);
	/**
	 * 对指定评分结果进行重新评分
	 * @param id
	 * @return
	 */
	public SpBillGradeResult doGradeAgain(String id);
	/**
	 * 清理指定评分结果
	 * @param id
	 * @return
	 */
	public void delete(String id);

}
