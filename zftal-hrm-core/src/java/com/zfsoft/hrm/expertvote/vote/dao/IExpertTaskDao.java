package com.zfsoft.hrm.expertvote.vote.dao;

import java.util.List;
import java.util.Map;

import com.zfsoft.hrm.expertvote.vote.entity.ExpertTask;
import com.zfsoft.hrm.expertvote.vote.entity.ExpertTaskDetail;
import com.zfsoft.hrm.expertvote.vote.query.ExpertTaskQuery;

/**
 * 
 * @author ChenMinming
 * @date 2014-3-14
 * @version V1.0.0
 */
public interface IExpertTaskDao {

	/**
	 * 查询
	 * @param id
	 * @return
	 */
	ExpertTask getById(String id);
	/**
	 * 查询方法
	 * @param query
	 * @return
	 */
	List<ExpertTask> getPagingList(ExpertTaskQuery query);
	
	int getPagingCount(ExpertTaskQuery query);

	List<ExpertTask> getList(ExpertTaskQuery query);
	/**
	 * 返回该任务目前被使用情况（检查spworkTask中关于该任务的数目）
	 * @param id
	 * @return
	 */
	int getUseCount(String id);
	/**
	 * 增加专家审核任务
	 * @param task
	 */
	void insert(ExpertTask task);
	/**
	 * 修改专家审核任务
	 * @param task
	 */
	void update(ExpertTask task);
	
	/**
	 * 删除专家审核任务
	 * @param task
	 */
	void delete(String id);
	/**
	 * 统计任务各状态的数量
	 * @param taskDetail
	 * @return
	 * 		key: E_STATUS  任务执行状态
	 * 			 STATUS    节点执行状态
	 *           NUM       数量
	 * 
	 */
	List<Map<String, Object>> getTaskCount(ExpertTaskDetail taskDetail);
	/**
	 * 当前任务节点所存在的专业列表
	 * @param expertTaskDetail
	 * @return
	 */
	List<String> getBsnsClassIdOfTask(ExpertTaskDetail expertTaskDetail);
	/**
	 * 根据节点获取对应的专家组
	 * @param expertTaskDetail
	 * @return
	 */
	List<String> findGroupByBsnsClassId(ExpertTaskDetail expertTaskDetail);
	
}
