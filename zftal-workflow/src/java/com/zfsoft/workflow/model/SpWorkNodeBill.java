package com.zfsoft.workflow.model;

import java.io.Serializable;

/**
 * 
 * 类描述：工作审核节点动态表单关联表
 *
 * @version: 1.0
 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 * @since: 2013-4-19 下午02:31:30
 */
public class SpWorkNodeBill extends SpNodeBill implements Serializable {

	/* serialVersionUID: serialVersionUID */

	private static final long serialVersionUID = 1L;
	/* @property:ID */
	private String id;
	/* @property:工作ID */
	private String wid;

	/* Default constructor - creates a new instance with no values set. */
	public SpWorkNodeBill() {
	}

	/* @model:����ID */
	public void setId(String obj) {
		this.id = obj;
	}

	/* @model:��ȡID */
	public String getId() {
		return this.id;
	}

	/**
	 * @return wId : return the property wId.
	 */

	public String getWid() {
		return wid;
	}

	/**
	 * @param wId
	 *            : set the property wId.
	 */

	public void setWid(String wid) {
		this.wid = wid;
	}
	
	/**
	 * 
	 * 方法描述：将动态表单对象填充到工作审核动态表单对象中
	 *
	 * @param: 
	 * @return: 
	 * @version: 1.0
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * @since: 2013-4-22 下午02:14:22
	 */
	public static SpWorkNodeBill putWorkNodeBillObject(SpNodeBill spNodeBill) {
		SpWorkNodeBill wnb = new SpWorkNodeBill();
		if(spNodeBill != null){
			wnb.setBillId(spNodeBill.getBillId());
			wnb.setBillType(spNodeBill.getBillType());
			wnb.setClassId(spNodeBill.getClassId());
			wnb.setClassesPrivilege(spNodeBill.getClassesPrivilege());
			wnb.setNodeId(spNodeBill.getNodeId());
		}
		return wnb;
	}
}
