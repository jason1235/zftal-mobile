package com.zfsoft.hrm.baseinfo.data.entity;

import java.text.DecimalFormat;

/** 
 * 导入进度信息
 * @author jinjj
 * @date 2012-10-26 上午10:08:28 
 *  
 */
public class ImportProcessStep {

	private String msg;
	
	private int step;
	
	private int total = Integer.MAX_VALUE;
	
	private final static String  DEFAULT_FORMAT = "##.#%";
	
	private DecimalFormat format =  new DecimalFormat(DEFAULT_FORMAT);

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
	public String getPercent(){
		float value = (float)step/total;
		return format.format(value);
	}
}
