/**
 * 
 */
package com.zfsoft.mobile.accessStatistics.entity;

import java.util.Date;

/**
 * @author zhangxu
 * @description 用户每天每小时访问app的统计，此entity用于缓存中
 * @date 2017-6-13 下午04:01:35
 */
public class VisitForHour {
	private String userId;//用户id
	private String visitTime;//访问时间
	private int userVisitCount;//每天累计访问次数
	
	public VisitForHour(){
		
	}
	
	public VisitForHour(String userId,String visitTime){
		this.userId = userId;
		this.setVisitTime(visitTime);
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}
	public String getVisitTime() {
		return visitTime;
	}
	public void setUserVisitCount(int userVisitCount) {
		this.userVisitCount = userVisitCount;
	}
	public int getUserVisitCount() {
		return userVisitCount;
	}
	
	
}
