package com.zfsoft.workflow.model;

import java.io.Serializable;

/**
 * 工作审核节点连线表
 * 
 * @version 3.2.0
 */
public class SpWorkLine extends SpLine implements Serializable {

	/* serialVersionUID: serialVersionUID */

	private static final long serialVersionUID = 1L;
	/* @property:ID */
	private String id;
	/* @property:工作ID */
	private String wid;

	/* Default constructor - creates a new instance with no values set. */
	public SpWorkLine() {
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

	/* {@inheritDoc} */
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof SpWorkLine)) {
			return false;
		}
		final SpWorkLine spworkline = (SpWorkLine) o;
		return this.hashCode() == spworkline.hashCode();
	}

	/* {@inheritDoc} */
	public int hashCode() {
		return this.hashCode();
	}

	/**
	 * 将连线对象填充到工作审核连线对象中
	 * 
	 * @param spLine
	 * @return
	 */
	public static SpWorkLine putLineObject(SpLine spLine) {
		SpWorkLine spWorkLine = new SpWorkLine();
		if (spLine != null) {
			spWorkLine.setLineId(spLine.getLineId());
			spWorkLine.setLineDesc(spLine.getLineDesc());
			spWorkLine.setPid(spLine.getPid());
			spWorkLine.setUnodeId(spLine.getUnodeId());
			spWorkLine.setDnodeId(spLine.getDnodeId());
			spWorkLine.setExpression(spLine.getExpression());
		}
		return spWorkLine;
	}
}
