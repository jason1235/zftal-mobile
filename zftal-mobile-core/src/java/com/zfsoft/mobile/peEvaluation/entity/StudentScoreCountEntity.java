package com.zfsoft.mobile.peEvaluation.entity;

/**
 * 学生成绩统计实体
 * @author liucb
 */
public class StudentScoreCountEntity {
	private String item;                //体测项
	private String value;               //体测值
	private String classPercent;        //超过班级占比
	private String institutePercent;    //超过学院占比
	
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getClassPercent() {
		return classPercent;
	}
	public void setClassPercent(String classPercent) {
		this.classPercent = classPercent;
	}
	public String getInstitutePercent() {
		return institutePercent;
	}
	public void setInstitutePercent(String institutePercent) {
		this.institutePercent = institutePercent;
	}
}
