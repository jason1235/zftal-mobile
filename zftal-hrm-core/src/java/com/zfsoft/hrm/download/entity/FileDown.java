package com.zfsoft.hrm.download.entity;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.config.ICodeConstants;
import com.zfsoft.hrm.core.file.FileRequest;

/** 
 * @author jinjj
 * @date 2013-5-21 上午10:36:15 
 *  文件下载
 */
public class FileDown extends FileRequest{

	private static final long serialVersionUID = -7483032607223236577L;

	private String guid;
	
	private String name;
	
	private String fileId;
	
	private String creator;
	
	private String type;
	
	private Date createTime;
	
	private int status;
	
	private int top;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getFileType() {
		if (StringUtils.isEmpty(type)) {
			return "";
		} else {
			return CodeUtil.getItem(ICodeConstants.DM_FILE_TYPE, type).getDescription();
		}
	}

}
