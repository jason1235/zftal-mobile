package com.zfsoft.workflow.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.zfsoft.workflow.exception.WorkFlowException;
import com.zfsoft.workflow.model.SpAuditingLog;
import com.zfsoft.workflow.model.SpWorkNode;
import com.zfsoft.workflow.model.SpWorkProcedure;
import com.zfsoft.workflow.model.SpWorkTask;
import com.zfsoft.workflow.model.query.WorkAuditingQuery;
import com.zfsoft.workflow.model.query.WorkTaskQuery;
import com.zfsoft.workflow.model.result.BaseResult;
import com.zfsoft.workflow.model.result.NodeListSqlResult;
import com.zfsoft.workflow.model.result.TaskListSqlResult;

/**
 * 
 * 接口类描述：审批工作流对外接口
 * 
 * 【接口方法命名规则说明：】
 * 		1、新增：方法名以"add"开头
 * 		2、修改：方法名以"update"开头
 *      3、查询：方法名以"query"开头
 * 		4、提交（执行）：方法名以"do"开头
 *
 * @version: 1.0
 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 * @since: 2013-3-17 上午09:54:18
 */
public interface ISpWorkFlowService extends BaseInterface {

	/**
	 * 
	 * 接口方法描述：新增一个工作审批任务（申报或上报工作进入审批流程时调用此接口方法）
	 * 
	 * @param hashMap
	 * 				KEY值元素说明
	 *            		businessCode   业务编码（工作属于那种业务）（必填） 
	 *            		workId         要审核的工作ID（必填）
	 *            		userId         上报人用户ID
	 *            		departmentId   部门ID
	 *            
	 * @return AddRsultObject 
	 * 				执行返回结果对象
	 * @throws WorkFlowException 
	 * 				抛出异常类
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * @version: 2013-3-12 上午11:05:45
	 */
	public BaseResult addSpWorkFlow(HashMap<String, String> map) throws WorkFlowException;
	
	/**
	 * 
	 * 接口方法描述：根据工作ID取消申报方法
	 *
	 * @param: workId 
	 * 				工作ID
	 * @return: boolean 
	 * 				成功（true）、失败（false）
	 * @version: 1.0
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * @since: 2013-3-18 下午05:05:47
	 */
	public boolean doCancelDeclare(String workId) throws WorkFlowException; 
	
	/**
	 * 
	 * 接口方法描述：通过工作审核查询对象查询结果集合
	 * 
	 * @param: query 
	 * 				查询对象
	 *     				对象属性说明：
     *        				businessCode   业务编码（如果是招聘人员审核则传入的是编织类别代码）（选填）
	 *		  				businessType   业务类型（例如：招聘人员审核、编辑计划审核等）（必填）
	 *		  				status         状态（待审核；通过；不通过）（必填）
	 *		  				roleIdArray    角色ID数组（必填）
	 *
	 * @return: Map 
	 * 				返回对象集合（填充：List(key值：“list”)、String[](组装好的结果集中的workId元素数组。key值：workIdArray)）
	 * @version: 1.0
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * @since: 2013-3-19 上午10:58:55
	 */
	public Map<String, Object> queryWorkFlowList(WorkAuditingQuery query) throws WorkFlowException;
	
	/**
	 * 
	 * 接口方法描述：通过工作审核查询对象查询结果集合
	 * 
	 * @param: query 
	 * 				查询对象
	 *     				对象属性说明：
     *        				businessCode   业务编码（如果是招聘人员审核则传入的是编织类别代码）
	 *		  				businessType   业务类型（例如：招聘人员审核、编辑计划审核等）（业务编码和业务类型必须填充一个）
	 *		  				status         状态（待审核；通过；不通过）（必填）
	 *		  				roleIdArray    角色ID数组（必填）
	 *
	 * @return: ListSqlResult 
	 * 				返回SQL对象
	 * @version: 1.0
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * @since: 2013-3-19 上午10:58:55
	 */
	public NodeListSqlResult queryWorkFlowListSql(WorkAuditingQuery query) throws WorkFlowException;

	/**
	 * 
	 * 接口方法描述：根据工作ID获取工作审批流程的全部信息（用于查看页面使用）
	 * 
	 * @param: workId 
	 * 				工作ID
	 * @return: SpWorkProcedure 
	 * 				工作审批信息对象（包含节点集合（节点中又包含任务集合）、连线集合、审批日志集合）
	 * @throws: WorkFlowException 
	 * 				抛出异常类
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * @version: 2013-3-12 上午11:09:40
	 */
	public SpWorkProcedure queryWorkFlowByWorkId(String workId) throws WorkFlowException;
	
	/**
	 * 
	 * 接口方法描述：根据工作ID和角色ID获取工作审批流程的全部信息（用于审核页面使用）
	 * 
	 * @param: workId 工作ID
	 * 		   roleIdArray 角色ID数组（如果为空，则不按角色设置节点是否编辑状态）
	 * @return: SpWorkProcedure 
	 * 				工作审批信息对象（包含节点集合（节点中又包含任务集合）、连线集合、审批日志集合）
	 * @throws: WorkFlowException 
	 * 				抛出异常类
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * @version: 2013-3-12 上午11:09:40
	 */
	public SpWorkProcedure queryWorkFlowByWorkIdAndRoleId(String workId, String[] roleIdArray) throws WorkFlowException;

	/**
	 * 
	 * 接口方法描述：执行某个环节审核操作
	 * 
	 * @param: spWorkNode 
	 *              	节点对象
	 *                   	对象中属性：
	 *                   		wid          节点ID（必填）			
	 *                   		nodeId       工作ID（必填）
	 *                   		status       审核状态（ 保存；通过；不通过；退回（详见WorkNodeStatusEnum枚举类））（必填）
	 *                   		auditorId    审核人（必填）
	 *                   		auditResult  审核结果
	 *                   		suggestion   审核意见（必填）
	 *                          spWorkTaskList   任务集合对象
	 *                   	                      任务对象中参数：
	 *                   		        taskId     任务ID（必填）
	 *                   		        wid        工作ID（必填）
	 *                   		        operator   操作人（必填）
	 *                   		        result     操作结果
	 *         roleIdArray
	 *                   角色ID数组（校验是否有角色权限操作节点）
	 *         returnNodeId
	 *                   退回到的节点ID（如果操作类型为退回，则此字段必须填写）
	 *                   
	 * @return: BaseRsult 
	 * 				执行结果对象
	 * @version: 1.0
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * @since: 2013-3-16 下午04:01:52
	 */
	public BaseResult doAuditingRsult(SpWorkNode spWorkNode, String[] roleIdArray, String returnNodeId) throws WorkFlowException;
	
	/**
	 * 
	 * 接口方法描述：审核环节撤销审核操作
	 *
	 * @param: spWorkNode
	 * 					 节点对象
	 * 		   roleIdArray
	 *                   角色ID数组（校验是否有角色权限操作节点）
	 * @return: 
	 * @version: 1.0
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * @since: 2013-7-16 上午11:52:40
	 */
	public BaseResult doCancelAuditingRsult(SpWorkNode spWorkNode, String[] roleIdArray) throws WorkFlowException;

	/**
	 * 
	 * 接口方法描述：判断关联的任务执行状态
	 *
	 * @param: spWorkTaskList   要执行的任务集合
	 * 		   taskType         任务类型
	 * @return: List       返回工作ID结果
	 * @version: 1.0
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * @since: 2013-3-28 下午05:13:25
	 */
	public List<SpWorkTask> judgeTaskEstatus(String taskType, List<SpWorkTask> spWorkTaskList) throws WorkFlowException;
	
	/**
	 * 
	 * 接口方法描述：手动（批量）执行任务操作
	 * 
	 * @param: spWorkTaskList 
	 * 				工作审核任务对象集合
	 * 					集合中对象中必填参数：
	 * 						taskId  任务ID（必填）
	 * 						wid     工作ID（必填）
	 * 
	 * @return: List 
	 * 				执行成功的结果集合
	 * @version: 1.0
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * @since: 2013-3-17 上午10:56:16
	 */
	public List<SpWorkTask> doTaskRsult(List<SpWorkTask> spWorkTaskList) throws WorkFlowException;
	
	/**
	 * 
	 * 接口方法描述：通过任务查询对象查询任务SQL
	 * 
	 * @param: query 
	 * 				查询任务对象
			    		对象中的属性：
							taskType  任务类型（必填）
                			status    执行状态（待执行；已执行）（必填）
	            
	 * @return: TaskListSqlResult 
	 * 				返回任务查询SQL对象
	 * @version: 1.0
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * @since: 2013-3-19 下午03:12:36
	 */
	public TaskListSqlResult queryWorkTaskListSql(WorkTaskQuery query) throws WorkFlowException;
	
	/**
	 * 
	 * 接口方法描述：查询某业务历史操作日志
	 * 
	 * @param wid 业务编号，必须的
	 * @param roleId 角色
	 * @return
	 * @throws WorkFlowException
	 */
	public List<SpAuditingLog> querySpAuditingLog(String wid, String roleId) throws WorkFlowException;
	
	/**
	 * 
	 * 接口方法描述：新增代办事宜对象
	 *
	 * @param: 
	 * @return: 
	 * @version: 1.0
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * @since: 2013-5-7 上午11:42:47
	 */
	public boolean addPendingAffairInfo(SpWorkNode spWorkNode) throws WorkFlowException;
	
	/**
	 * 
	 * 接口方法描述：修改代办事宜对象
	 *
	 * @param: 
	 * @return: 
	 * @version: 1.0
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * @since: 2013-5-7 上午11:43:38
	 */
	public boolean updatePendingAffairInfo(SpWorkNode spWorkNode) throws WorkFlowException;
	
	/**
	 * 
	 * 接口方法描述：通过工作ID和节点ID获取工作审核节点对象
	 *
	 * @param: 
	 * @return: 
	 * @version: 1.0
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * @since: 2013-5-8 下午04:53:55
	 */
	public SpWorkNode queryWorkNodeByWidAndNodeId(String workId, String nodeId) throws DataAccessException;
	
}
