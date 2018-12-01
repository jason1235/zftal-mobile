package com.zfsoft.mobile.portal.entity;

public class bookDetail {
	//读者证号
	private String classDzzh;
	//条形码
	private String classTxm;
	//正题名
	private String classZtm;
	//借出时间
	private String classJcsj;
	//限还时间
	private String classXhsj;
	//续借次数
	private String classXjcs;
	//流通类型
	private String classLtlx;
	//文献类型
	private String classWxlx;
	//书期号
	private String classSqh;
	//出版社
	private String classCbs;
	//出版日期
	private String classCbrq;
	//已借数
	private String classYjs;
	//是否归还(0未归还，1归还)
	private String classSfgh;
	//归还时间
	private String classGhsj;
	//作者
	private String classZz;
	//管藏地
	private String classGcd;
	//人缘好
	private String classYrh;
	//时间比较
	private int classIsLate;
	//警示天数
	private int warnDays;
	public int getWarnDays() {
		return warnDays;
	}

	public void setWarnDays(int warnDays) {
		this.warnDays = warnDays;
	}

	public String getClassZtm() {
		return classZtm;
	}

	public void setClassZtm(String classZtm) {
		this.classZtm = classZtm;
	}

	public String getClassDzzh() {
		return classDzzh;
	}

	public void setClassDzzh(String classDzzh) {
		this.classDzzh = classDzzh;
	}

	public String getClassTxm() {
		return classTxm;
	}

	public void setClassTxm(String classTxm) {
		this.classTxm = classTxm;
	}

	public String getClassJcsj() {
		return classJcsj;
	}

	public void setClassJcsj(String classJcsj) {
		this.classJcsj = classJcsj;
	}

	public String getClassXhsj() {
		return classXhsj;
	}

	public void setClassXhsj(String classXhsj) {
		this.classXhsj = classXhsj;
	}

	public String getClassXjcs() {
		return classXjcs;
	}

	public void setClassXjcs(String classXjcs) {
		this.classXjcs = classXjcs;
	}

	public String getClassLtlx() {
		return classLtlx;
	}

	public void setClassLtlx(String classLtlx) {
		this.classLtlx = classLtlx;
	}

	public String getClassWxlx() {
		return classWxlx;
	}

	public void setClassWxlx(String classWxlx) {
		this.classWxlx = classWxlx;
	}

	public String getClassSqh() {
		return classSqh;
	}

	public void setClassSqh(String classSqh) {
		this.classSqh = classSqh;
	}

	public String getClassCbs() {
		return classCbs;
	}

	public void setClassCbs(String classCbs) {
		this.classCbs = classCbs;
	}

	public String getClassCbrq() {
		return classCbrq;
	}

	public void setClassCbrq(String classCbrq) {
		this.classCbrq = classCbrq;
	}

	public String getClassYjs() {
		return classYjs;
	}

	public void setClassYjs(String classYjs) {
		this.classYjs = classYjs;
	}

	public String getClassSfgh() {
		return classSfgh;
	}

	public void setClassSfgh(String classSfgh) {
		this.classSfgh = classSfgh;
	}

	public String getClassGhsj() {
		return classGhsj;
	}

	public void setClassGhsj(String classGhsj) {
		this.classGhsj = classGhsj;
	}

	public String getClassZz() {
		return classZz;
	}

	public void setClassZz(String classZz) {
		this.classZz = classZz;
	}

	public String getClassGcd() {
		return classGcd;
	}

	public void setClassGcd(String classGcd) {
		this.classGcd = classGcd;
	}

	public void setClassYrh(String classYrh) {
		this.classYrh = classYrh;
	}

	public String getClassYrh() {
		return classYrh;
	}

	public void setClassIsLate(int classIsLate) {
		this.classIsLate = classIsLate;
	}

	public int getClassIsLate() {
		return classIsLate;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj == this) {
            return true;
        }
        if (!(obj instanceof bookDetail)) {
            return false;
        }
		
		bookDetail book = (bookDetail) obj;
		
        return (book.classDzzh.equals(this.classDzzh) && book.classTxm.equals(this.classTxm) && book.classJcsj.equals(this.classJcsj)) ? true : false;
	}

	
	
}
