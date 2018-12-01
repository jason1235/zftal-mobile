package com.zfsoft.hrm.notice.entity;

import java.util.List;

import com.zfsoft.hrm.core.file.FileRequest;
import com.zfsoft.hrm.file.entity.Attachement;

public class NoticeAttachement extends FileRequest {

	private static final long serialVersionUID = -7573241599855516027L;

	private List<Attachement> attachements;//附件列表
	
	private List<String> removeAttachementIds;//删除附件id集合

	/**
	 * 返回
	 * @return 
	 */
	public List<Attachement> getAttachements() {
		return attachements;
	}

	/**
	 * 设置
	 * @param attachements 
	 */
	public void setAttachements(List<Attachement> attachements) {
		this.attachements = attachements;
	}

	/**
	 * 返回
	 * @return 
	 */
	public List<String> getRemoveAttachementIds() {
		return removeAttachementIds;
	}

	/**
	 * 设置
	 * @param removeAttachementIds 
	 */
	public void setRemoveAttachementIds(List<String> removeAttachementIds) {
		this.removeAttachementIds = removeAttachementIds;
	}
	
	public String getGuid() {
		return null;
	}
	
	
}
