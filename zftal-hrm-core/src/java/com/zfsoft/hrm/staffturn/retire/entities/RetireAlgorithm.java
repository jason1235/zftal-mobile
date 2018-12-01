package com.zfsoft.hrm.staffturn.retire.entities;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.orcus.lang.TimeUtil;

/** 
 * @author jinjj
 * @date 2012-7-31 上午05:58:05 
 *  
 */
public class RetireAlgorithm {

	private String sex;
	
	private String age;
	
	private int before = 1;//默认提前1个月
	
	private String level;
	
	private String post;
	
	private String cond_col;//条件字段
	
	private String cond_val;//条件值
	
	private String manual_time;//手动执行时间  
	
	private RetireScheduleTypeEnum retireScheduleTypeEnum;
    
	// 20140423 add start
	private String identity; // 人员身份
	
	private String duty_level; // 行政职务级别
	// 20140423 add end
	
	public String getManual_time() {
		return manual_time;
	}

	public void setManual_time(String manual_time) {
		this.manual_time = manual_time;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public int getBefore() {
		return before;
	}

	public void setBefore(int before) {
		this.before = before;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}
	
	public String getCond_col() {
		return cond_col;
	}

	public void setCond_col(String cond_col) {
		this.cond_col = cond_col;
	}

	public String getCond_val() {
		return cond_val;
	}

	public void setCond_val(String cond_val) {
		this.cond_val = cond_val;
	}
	
	public RetireScheduleTypeEnum getRetireScheduleTypeEnum() {
		return retireScheduleTypeEnum;
	}

	public void setRetireScheduleTypeEnum(
			RetireScheduleTypeEnum retireScheduleTypeEnum) {
		this.retireScheduleTypeEnum = retireScheduleTypeEnum;
	}

	// 20140423 add start
	/**
	 * @return the identity
	 */
	public String getIdentity() {
		return identity;
	}

	/**
	 * @param identity the identity to set
	 */
	public void setIdentity(String identity) {
		this.identity = identity;
	}

	/**
	 * @return the duty_level
	 */
	public String getDuty_level() {
		return duty_level;
	}

	/**
	 * @param duty_level the duty_level to set
	 */
	public void setDuty_level(String duty_level) {
		this.duty_level = duty_level;
	}
	// 20140423 add end

	public String getCondition(){
		StringBuilder sb = new StringBuilder();
		if(!StringUtils.isEmpty(this.sex)){
			append(sb,"over.xbm",this.sex);
		}
		//如果手动执行的时间参数manual_time非空   by陈成豪
		if(StringUtils.isNotEmpty(this.age)){
			if(RetireScheduleTypeEnum.MONTH==retireScheduleTypeEnum){
				appendRetireTimeByMonth(sb,"over.csrq");
			}else{
				appendRetireTimeByYear(sb,"over.csrq");
			}
		}
		if(StringUtils.isNotEmpty(cond_col)&&StringUtils.isNotEmpty(cond_val)){
			appendConditionValue(sb);
		}
		
		// 20140423 add start
		if (StringUtils.isNotEmpty(this.identity)) {
			append(sb,"over.rysf",this.identity);
		}
		if (StringUtils.isNotEmpty(this.duty_level)) {
			append(sb,"over.zwjbdm",this.duty_level);
		}
		// 20140423 add end
		sb.append(" and over.dqztm like '1%' ");
		return sb.toString();
	}
	
	private void append(StringBuilder sb,String column,String columnValue){
		if(sb.toString().length()>0){
			sb.append(" and ");
		}
		sb.append(" ").append(column).append(" = '").append(columnValue).append("'");
	}
	
	private void appendRetireTimeByMonth(StringBuilder sb,String column){
		if(sb.toString().length()>0){
			sb.append(" and ");
		}
		String today =null;
		if(StringUtils.isNotEmpty(this.manual_time)){
			today = this.manual_time +"-01 00:00:00";
		}else{
			today=TimeUtil.beginTimeOfMonth();
		}
		int year = -Integer.valueOf(this.age).intValue();
		Date bornMonth = TimeUtil.addYear(today, year);
		Date startDate = TimeUtil.addMonth(bornMonth, before);
		//String start = TimeUtil.format(startDate, "yyyy-MM-dd");
		String end = TimeUtil.endTimeOfMonth(TimeUtil.format(startDate, "yyyy-MM-dd"));
		end = TimeUtil.format(end,"yyyy-MM-dd");
		//sb.append(" "+column+" >= to_date('").append(start).append("','yyyy-MM-dd') and "); 
		sb.append(" "+column+" <= to_date('").append(end).append("','yyyy-MM-dd')"); 
	}
	
	private void appendRetireTimeByYear(StringBuilder sb,String column){
		if(sb.toString().length()>0){
			sb.append(" and ");
		}
		String yearEndDay=null;
		if(StringUtils.isNotEmpty(this.manual_time)){
			yearEndDay = this.manual_time+"-12-31";
		}else{
			yearEndDay=TimeUtil.current("yyyy-12-31");
		}
		int year = -Integer.valueOf(this.age).intValue();
		Date bornDay = TimeUtil.addYear(yearEndDay, year);
		String end = TimeUtil.format(bornDay,"yyyy-MM-dd");
		sb.append(" "+column+" <= to_date('").append(end).append("','yyyy-MM-dd')"); 
	}
	
	
	
	
	private void appendConditionValue(StringBuilder sb){
		if(sb.toString().length()>0){
			sb.append(" and ");
		}
		sb.append(" over.");
		sb.append(this.cond_col);
		sb.append("='");
		sb.append(this.cond_val);
		sb.append("'");
	}
	
	public static void main(String[] args) throws Exception{
		RetireAlgorithm alg = new RetireAlgorithm();
		alg.setAge("60");
		alg.setSex("1");
		System.out.println(alg.getCondition());
	}
}
