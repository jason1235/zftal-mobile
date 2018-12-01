package com.zfsoft.mobile.version.entity;

import java.util.Date;

import com.zfsoft.dao.query.BaseQuery;



/**
 * 参数设置
 */
public class VersionManager extends BaseQuery{

	private String id;
	/**
	 * 学校编号 db_column: SCHOOLCODE
	 */
	private String schoolcode;

	/**
	 * 类型编号 db_column: TYPECODE
	 */
	private String typecode;
	/**
	 * 学校名称 db_column: SCHOOLNAME
	 */
	//private String schoolname;

	/**
	 * 类型名称 db_column: TYPENAME
	 */
	private String typename;
	/**
	 * 平台 db_column: TERRACE
	 */
	private String terrace;
	
	/**
	 * 服务器地址 db_column: SERVERADDRESS
	 */
	private String serveraddress;
	
	/**
	 * 版本号 db_column: VERSIONNUM
	 */
	private String versionnum;

	/**
	 * 状态（0——停用；1——启用） db_column: CHECKSTATUS
	 */
	private String checkStatus;
	
	/**
	 * 文件名 db_column: FILENAME
	 */
	private String fileid;
	
	/**
	 * 文件下载路径 db_column: FILEADDRESS
	 */
	private String fileaddress;
	
	/**
	 * 升级提示 db_column: UPGRADEPROMPT
	 */
	private String upgradeprompt;
	/**
	 * 备注 db_column: REMARK
	 */
	private String remark;
	/**
	 * 编辑用户 db_column: EDIT_MANAGER
	 */
	private String editManager;
	
	protected Date timeModify;
	
	private Date timecreate;


	


	public String getFileid() {
		return fileid;
	}




	public void setFileid(String fileid) {
		this.fileid = fileid;
	}




	public String getFileaddress() {
		return fileaddress;
	}




	public void setFileaddress(String fileaddress) {
		this.fileaddress = fileaddress;
	}



	public String getTerrace() {
		return terrace;
	}




	public void setTerrace(String terrace) {
		this.terrace = terrace;
	}



	public String getServeraddress() {
		return serveraddress;
	}




	public void setServeraddress(String serveraddress) {
		this.serveraddress = serveraddress;
	}



	public String getVersionnum() {
		return versionnum;
	}




	public void setVersionnum(String versionnum) {
		this.versionnum = versionnum;
	}


	public String getSchoolcode() {
		return schoolcode;
	}




	public void setSchoolcode(String schoolcode) {
		this.schoolcode = schoolcode;
	}



	public String getTypecode() {
		return typecode;
	}




	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}



	/*public String getSchoolname() {
		return schoolname;
	}




	public void setSchoolname(String schoolname) {
		this.schoolname = schoolname;
	}*/



	public String getTypename() {
		return typename;
	}




	public void setTypename(String typename) {
		this.typename = typename;
	}





	public String getCheckStatus() {
		return checkStatus;
	}




	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}



	public String getUpgradeprompt() {
		return upgradeprompt;
	}




	public void setUpgradeprompt(String upgradeprompt) {
		this.upgradeprompt = upgradeprompt;
	}



	public String getRemark() {
		return remark;
	}




	public void setRemark(String remark) {
		this.remark = remark;
	}



	public String getEditManager() {
		return editManager;
	}




	public void setEditManager(String editManager) {
		this.editManager = editManager;
	}

	public Date getTimeModify() {
		return timeModify;
	}


	public void setTimeModify(Date timeModify) {
		this.timeModify = timeModify;
	}




	public void setTimecreate(Date timecreate) {
		this.timecreate = timecreate;
	}




	public Date getTimecreate() {
		return timecreate;
	}




	public void setId(String id) {
		this.id = id;
	}




	public String getId() {
		return id;
	}

}

