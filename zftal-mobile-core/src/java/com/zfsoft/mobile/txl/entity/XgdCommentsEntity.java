package com.zfsoft.mobile.txl.entity;

public class XgdCommentsEntity {

	private String theKey;
	private String theVal;
	public XgdCommentsEntity(){
		
	}
	
	public XgdCommentsEntity(String theKey,String theVal){
		this.theKey = theKey;
		this.theVal = theVal;
	}
	
	public String getTheKey() {
		return theKey;
	}
	public void setTheKey(String theKey) {
		this.theKey = theKey;
	}
	public String getTheVal() {
		return theVal;
	}
	public void setTheVal(String theVal) {
		this.theVal = theVal;
	}
	
}
