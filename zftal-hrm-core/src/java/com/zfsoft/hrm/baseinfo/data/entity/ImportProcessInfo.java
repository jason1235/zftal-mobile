package com.zfsoft.hrm.baseinfo.data.entity;

/** 
 * @author jinjj
 * @date 2012-10-24 下午01:32:08 
 *  
 */
public class ImportProcessInfo {

	private boolean finish = false;
	
	private String description;

	public boolean isFinish() {
		return finish;
	}

	public void setFinish(boolean finish) {
		this.finish = finish;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
