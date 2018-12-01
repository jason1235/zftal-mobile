/**
 * 
 */
package com.zfsoft.mobile.accessStatistics.service;

import java.util.List;

import com.zfsoft.mobile.accessStatistics.entity.VisitForDay;
import com.zfsoft.mobile.accessStatistics.entity.VisitForHour;

/**
 * @author zhangxu
 * @description 
 * @date 2017-6-13 下午03:57:47
 */
public interface IVisitService {
	public void visitByUserId(String userId);
	public void testCache();
	/** 
	* @author: zhangxu
	* @Title: getTodayVisitByMouth 
	* @Description: 获取某月每天累计访问量
	* @param @param dateStr 某月
	* @param @return    设定文件 
	* @return List<VisitForDay>    返回类型 
	* @throws 
	*/
	public List<VisitForDay> getTodayVisitByMouth(String dateStr);
	/** 
	* @author: zhangxu
	* @Title: getTopTodayVisitByMouth 
	* @Description: 获取一年内历史访问累积量最高的10天排序
	* @param @param dateStr 
	* @param @return    设定文件 
	* @return List<VisitForDay>    返回类型 
	* @throws 
	*/
	public List<VisitForDay> getTopTodayVisitByMouth();
	/** 
	* @author: zhangxu
	* @Title: getTopHourVisitByDay 
	* @Description: 
	* @param @return    设定文件 
	* @return List<VisitForHour>    返回类型 
	* @throws 
	*/
	public List<VisitForHour> getTopHourVisitByDay();
	/** 
	* @author: zhangxu
	* @Title: getHourVisitByDay 
	* @Description: 
	* @param @param chooesHourDate
	* @param @return    设定文件 
	* @return List<VisitForHour>    返回类型 
	* @throws 
	*/
	public List<VisitForHour> getHourVisitByDay(String chooesHourDate);
}
