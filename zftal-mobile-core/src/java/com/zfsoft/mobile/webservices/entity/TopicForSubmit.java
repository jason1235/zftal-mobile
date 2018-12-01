package com.zfsoft.mobile.webservices.entity;


public class TopicForSubmit {
	private String title;
	private int type;
	private String selection;
	
	private int maxSel;
	
	public TopicForSubmit(String title, int type, String selection,int maxSel) {
		super();
		this.title = title;
		this.type = type;
		this.selection = selection;
		this.maxSel = maxSel;
	}

	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getSelection() {
		return selection;
	}

	public void setSelection(String selection) {
		this.selection = selection;
	}



	public void setMaxSel(int maxSel) {
		this.maxSel = maxSel;
	}



	public int getMaxSel() {
		return maxSel;
	}
	
	
	
}
