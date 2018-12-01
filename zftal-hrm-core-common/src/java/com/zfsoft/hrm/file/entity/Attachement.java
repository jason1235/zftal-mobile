package com.zfsoft.hrm.file.entity;

import java.util.Date;

/**
 * 附件内容实体
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-10-18
 * @version V1.0.0
 */
public class Attachement {
	
	private String 	guId;			// 主键ID
	private String 	formId="";			// 表单ID
	private String 	type="";			// 附件类型  
	private String 	name;			// 文件名称
	private Long 	size;			// 附件大小
	private byte[] 	content;		// 附件内容
	private Date 	uploadTime;		// 上传时间 格式：yyyy-MM-dd HH:mm:ss
	private String 	uploadMan="";		// 上传人
	private String 	uploadManName="";	// 上传人姓名
	private String 	remark="";			// 备注

	/**
	 * 返回主键ID
	 */
	public String getGuId() {
		return guId;
	}

	/**
	 * 设置主键ID
	 * @param guId 主键ID
	 */
	public void setGuId(String guId) {
		this.guId = guId;
	}

	/**
	 * 返回表单ID
	 */
	public String getFormId() {
		return formId;
	}

	/**
	 * 设置表单ID
	 * @param formId 表单ID
	 */
	public void setFormId(String formId) {
		this.formId = formId;
	}

	/**
	 * 返回附件类型  
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置附件类型  
	 * @param type 附件类型  
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 返回文件名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置文件名称
	 * @param name 文件名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 返回附件大小
	 */
	public Long getSize() {
		return size;
	}

	/**
	 * 设置附件大小
	 * @param size 附件大小
	 */
	public void setSize(Long size) {
		this.size = size;
	}

	/**
	 * 返回附件内容
	 */
	public byte[] getContent() {
		return content;
	}

	/**
	 * 设置附件内容
	 * @param content 附件内容
	 */
	public void setContent(byte[] content) {
		this.content = content;
	}

	/**
	 * 返回上传时间 格式：yyyy-MM-dd HH:mm:ss
	 */
	public Date getUploadTime() {
		
		return uploadTime;
	}

	/**
	 * 设置上传时间 格式：yyyy-MM-dd HH:mm:ss
	 * @param uploadTime 上传时间 格式：yyyy-MM-dd HH:mm:ss
	 */
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	/**
	 * 返回上传人
	 */
	public String getUploadMan() {
		return uploadMan;
	}

	/**
	 * 设置上传人
	 * @param uploadMan 上传人
	 */
	public void setUploadMan(String uploadMan) {
		this.uploadMan = uploadMan;
	}

	/**
	 * 返回上传人姓名
	 */
	public String getUploadManName() {
		return uploadManName;
	}

	/**
	 * 设置上传人姓名
	 * @param uploadManName 上传人姓名
	 */
	public void setUploadManName(String uploadManName) {
		this.uploadManName = uploadManName;
	}
	
	/**
	 * 返回备注
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置备注
	 * @param remark 备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
