package com.zfsoft.hrm.manoeuvre.configInfo.query;

import com.zfsoft.dao.query.BaseQuery;
import com.zfsoft.hrm.config.IConstants;

/**
 * 审核环节节点信息查询类
 * @author yongjun.fang
 *
 */
public class TaskNodeQuery extends BaseQuery {

	private static final long serialVersionUID = 480980474532705334L;

	private String nid;//环节节点编号
	
	private String nodeName;//环节节点名称
	
	private String order;//顺序码
	
	private String remark;//备注
	
	private String sortCol;//排序条件
	
	public TaskNodeQuery() {
		setPerPageSize(IConstants.COMMON_PAGE_SIZE);
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getNid() {
		return nid;
	}

	/**
	 * 设置
	 * @param nid 
	 */
	public void setNid(String nid) {
		this.nid = nid;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getNodeName() {
		return nodeName;
	}

	/**
	 * 设置
	 * @param nodeName 
	 */
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置
	 * @param remark 
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getSortCol() {
		return sortCol;
	}

	/**
	 * 设置
	 * @param sortCol 
	 */
	public void setSortCol(String sortCol) {
		this.sortCol = sortCol;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * 设置
	 * @param order 
	 */
	public void setOrder(String order) {
		this.order = order;
	}


}
