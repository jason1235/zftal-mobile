package com.zfsoft.mobile.peEvaluation.entity;

/**
 * 学生信息实体
 * @author liucb
 */
public class StudentInfoEntity {
    private String schoolNumber; //学籍号
    private String name;         //姓名
    private String gender;       //性别 1男2女
    private String birthday;     //出生日期
    private String idNumber;     //身份证号码
    private String nation;       //民族
    private String homeAddr;     //家庭住址 
    private String highSchoolName; //学校名称
    private String className;    //班级名称
    private String instituteName;//学院名称
    private String classId;      //班级id
    private String instituteId;  //学院id
    private String bak;          //备注
    private String height;       //身高
    private String weight;       //体重
    
    private String sclassname;//班级名称
    private String sschoolname;//学院名称
    
	public String getSchoolNumber() {
		return schoolNumber;
	}
	public void setSchoolNumber(String schoolNumber) {
		this.schoolNumber = schoolNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getHomeAddr() {
		return homeAddr;
	}
	public void setHomeAddr(String homeAddr) {
		this.homeAddr = homeAddr;
	}
	public String getHighSchoolName() {
		return highSchoolName;
	}
	public void setHighSchoolName(String highSchoolName) {
		this.highSchoolName = highSchoolName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getInstituteName() {
		return instituteName;
	}
	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	public String getInstituteId() {
		return instituteId;
	}
	public void setInstituteId(String instituteId) {
		this.instituteId = instituteId;
	}
	public String getBak() {
		return bak;
	}
	public void setBak(String bak) {
		this.bak = bak;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getSclassname() {
		return sclassname;
	}
	public void setSclassname(String sclassname) {
		this.sclassname = sclassname;
	}
	public String getSschoolname() {
		return sschoolname;
	}
	public void setSschoolname(String sschoolname) {
		this.sschoolname = sschoolname;
	}
	
	
}
