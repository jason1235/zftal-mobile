package com.zfsoft.workflow.model.result;

import java.io.Serializable;

import com.zfsoft.workflow.enumobject.WorkNodeStatusEnum;
import com.zfsoft.workflow.model.BaseObject;

/**
 * 
 * 类描述：查询符合条件的工作审核任务集合接口返回SQL对象
 * 
 * @version: 1.0
 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 * @since: 2013-3-19 下午12:29:15
 */
public class TaskListSqlResult extends BaseObject implements Serializable {

	/* serialVersionUID: serialVersionUID */

	private static final long serialVersionUID = 7269664315842844205L;

	private static final String sql_start_part = " SELECT wt.TASK_ID,wt.W_ID,wt.NODE_ID,wt.TASK_CODE,wt.TASK_NAME,wt.TASK_TYPE,wt.TASK_DESC,wt.E_STATUS,wt.OPERATOR,wt.OPREATE_TIME,wt.RESULT "
			+ "  FROM SP_WORK_TASK wt, SP_WORK_NODE wn "
			+ " WHERE wt.NODE_ID = wn.NODE_ID AND wt.W_ID = wn.W_ID AND wn.status = '"
			+ WorkNodeStatusEnum.PASS_AUDITING.getKey() + "' ";

	private static final String sql_end_part = " ";

	/* @property:SQL内容 */
	private String sqlContent;
	/* @property:表名称 */
	private String tableName = "workflow";

	/* Default constructor - creates a new instance with no values set. */
	public TaskListSqlResult() {
	}

	/**
	 * @param sqlContent
	 *            : set the property sqlContent.
	 */

	public void setSqlContent(String sqlContent) {
		this.sqlContent = sqlContent;
	}

	/**
	 * @return sqlContent : return the property sqlContent.
	 */

	public String getSqlContent() {
		return sqlContent;
	}

	/**
	 * @param tableName
	 *            : set the property tableName.
	 */

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @return tableName : return the property tableName.
	 */

	public String getTableName() {
		return tableName;
	}

	/**
	 * @return sqlEndPart : return the property sqlEndPart.
	 */

	public static String getSqlEndPart() {
		return sql_end_part;
	}

	/**
	 * @return sqlStartPart : return the property sqlStartPart.
	 */

	public static String getSqlStartPart() {
		return sql_start_part;
	}

}
