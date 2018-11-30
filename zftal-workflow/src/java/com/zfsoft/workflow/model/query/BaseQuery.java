package com.zfsoft.workflow.model.query;

import java.io.Serializable;

/**
 * 
 * 类描述：查询基础类对象
 *
 * @version: 1.0
 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 * @since: 2013-3-16 下午03:01:28
 */
public class BaseQuery implements Serializable{
	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = -9139939983936811032L;
	
	private String workId;//工作ID

	public void setWorkId(String workId) {
		this.workId = workId;
	}

	public String getWorkId() {
		return workId;
	}
}
