package com.zfsoft.mobile.webservices.entity;

import java.util.ArrayList;

public class Questionnaire {
	
	private String qn_name;
	private String qn_intro;
	private String qn_marking;
	private String qn_owner;
	private ArrayList<TopicForSubmit> topics;
	
	public Questionnaire(String qn_name, String qn_intro, String qn_marking,String qn_owner,
			ArrayList<TopicForSubmit> topics) {
		super();
		this.qn_name = qn_name;
		this.qn_intro = qn_intro;
		this.qn_marking = qn_marking;
		this.qn_owner = qn_owner;
		this.topics = topics;
	}

	public String getQn_name() {
		return qn_name;
	}

	public void setQn_name(String qn_name) {
		this.qn_name = qn_name;
	}

	public String getQn_intro() {
		return qn_intro;
	}

	public void setQn_intro(String qn_intro) {
		this.qn_intro = qn_intro;
	}

	public String getQn_marking() {
		return qn_marking;
	}

	public void setQn_marking(String qn_marking) {
		this.qn_marking = qn_marking;
	}

	public ArrayList<TopicForSubmit> getTopics() {
		return topics;
	}

	public void setTopics(ArrayList<TopicForSubmit> topics) {
		this.topics = topics;
	}

	public void setQn_owner(String qn_owner) {
		this.qn_owner = qn_owner;
	}

	public String getQn_owner() {
		return qn_owner;
	}
	
	
	
	
	
}
