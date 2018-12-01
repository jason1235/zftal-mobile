package com.zfsoft.hrm.baseinfo.snapshot.entities;

import java.io.Serializable;
import java.text.DecimalFormat;

/** 
 * 进度信息
 * @ClassName: ProgressInfo 
 * @author jinjj
 * @date 2012-7-20 下午02:15:39 
 *  
 */
public class ProgressInfo implements Serializable{

	private static final long serialVersionUID = 5293021724856529591L;

	private String description;
	
	private int totalStep=1;
	
	private int stepIndex;
	
	private DecimalFormat format =  new DecimalFormat(DEFAULT_FORMAT);
	
	private final static String  DEFAULT_FORMAT = "##.#%";

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getTotalStep() {
		return totalStep;
	}

	public void setTotalStep(int totalStep) {
		this.totalStep = totalStep;
	}

	public int getStepIndex() {
		return stepIndex;
	}

	public void setStepIndex(int stepIndex) {
		this.stepIndex = stepIndex;
	}

	public String getProgress() {
		float value = (float)stepIndex/totalStep;
		return format.format(value);
	}

	public void setFormat(DecimalFormat format) {
		this.format = format;
	}

	public boolean isFinish(){
		if(stepIndex<totalStep){
			return false;
		}else{
			return true;
		}
	}
}
