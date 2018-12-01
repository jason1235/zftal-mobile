package com.zfsoft.hrm.report.ofcentity;

import java.util.ArrayList;
import java.util.List;

public class XLabels {
	
	private List<String> labels=new ArrayList<String>();

	public List<String> getLabels() {
		labels=new ArrayList<String>();
		labels.add("教师");
		labels.add("科研");
		labels.add("行政");
		labels.add("教辅");
		labels.add("工人");
		labels.add("校产");
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}
}
