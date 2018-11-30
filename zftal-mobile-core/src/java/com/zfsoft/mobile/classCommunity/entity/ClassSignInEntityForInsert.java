package com.zfsoft.mobile.classCommunity.entity;

/**
 * 签到课程信息实体
 * @author H110MF
 *
 */
public class ClassSignInEntityForInsert {
	private String id;         //唯一主键
	private String classSignInId; //签到课程id
    private String className;  //课程名称
    private String startTime;  //开始时间
    private String endTime;    //结束时间
    private String teacherId;  //教师id
    private String teacherName;//教师名称
    private String loglat;     //课程位置经纬度
    private String address;    //地址
    private String bak;       //备注
    private String qrCodeFileName;    //二维码文件名
    private String qrCodeFilePath;    //二维码文件路径
    private byte[] qrCodeFileContent; //二维码文件
    
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getLoglat() {
		return loglat;
	}
	public void setLoglat(String loglat) {
		this.loglat = loglat;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBak() {
		return bak;
	}
	public void setBak(String bak) {
		this.bak = bak;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getQrCodeFileName() {
		return qrCodeFileName;
	}
	public void setQrCodeFileName(String qrCodeFileName) {
		this.qrCodeFileName = qrCodeFileName;
	}
	public String getQrCodeFilePath() {
		return qrCodeFilePath;
	}
	public void setQrCodeFilePath(String qrCodeFilePath) {
		this.qrCodeFilePath = qrCodeFilePath;
	}
	public byte[] getQrCodeFileContent() {
		return qrCodeFileContent;
	}
	public void setQrCodeFileContent(byte[] qrCodeFileContent) {
		this.qrCodeFileContent = qrCodeFileContent;
	}
	public String getClassSignInId() {
		return classSignInId;
	}
	public void setClassSignInId(String classSignInId) {
		this.classSignInId = classSignInId;
	}
}
