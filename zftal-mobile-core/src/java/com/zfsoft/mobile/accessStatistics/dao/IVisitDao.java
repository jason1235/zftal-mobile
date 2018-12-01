/**
 * 
 */
package com.zfsoft.mobile.accessStatistics.dao;

import java.util.List;

import com.zfsoft.mobile.accessStatistics.entity.VisitForDay;
import com.zfsoft.mobile.accessStatistics.entity.VisitForHour;

/**
 * @author zhangxu
 * @description 
 * @date 2017-6-14 上午09:32:55
 */
public interface IVisitDao {

	/** 
	* @author: zhangxu
	* @Title: getTodayVisit 
	* @Description: 
	* @param @return    设定文件 
	* @return VisitForDay    返回类型 
	* @throws 
	*/
	VisitForDay getTodayVisit();

	/** 
	* @author: zhangxu
	* @Title: insertTodayVisit 
	* @Description: 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void insertTodayVisit();

	/**
	 * @param userVisitCount  
	* @author: zhangxu
	* @Title: updateTodayVisit 
	* @Description: 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void updateTodayVisit(int userVisitCount);

	/** 
	* @author: zhangxu
	* @Title: deleteHourVisit 
	* @Description: 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void deleteHourVisit();

	/** 
	* @author: zhangxu
	* @Title: getTodayHourVisit 
	* @Description: 
	* @param @return    设定文件 
	* @return VisitForDay    返回类型 
	* @throws 
	*/
	VisitForHour getTodayHourVisit();

	/** 
	* @author: zhangxu
	* @Title: insertTodayHourVisit 
	* @Description: 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void insertTodayHourVisit();

	/**
	 * @param i  
	* @author: zhangxu
	* @Title: updateTodayHourVisit 
	* @Description: 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void updateTodayHourVisit(int i);

	/** 
	* @author: zhangxu
	* @Title: getTodayVisitByMouth 
	* @Description: 
	* @param @param dateStr
	* @param @return    设定文件 
	* @return List<VisitForDay>    返回类型 
	* @throws 
	*/
	List<VisitForDay> getTodayVisitByMouth(String dateStr);

	/** 
	* @author: zhangxu
	* @Title: getTopTodayVisitByMouth 
	* @Description: 
	* @param @return    设定文件 
	* @return List<VisitForDay>    返回类型 
	* @throws 
	*/
	List<VisitForDay> getTopTodayVisitByMouth();

	/** 
	* @author: zhangxu
	* @Title: getTopHourVisitByDay 
	* @Description: 
	* @param @return    设定文件 
	* @return List<VisitForHour>    返回类型 
	* @throws 
	*/
	List<VisitForHour> getTopHourVisitByDay();

	/** 
	* @author: zhangxu
	* @Title: getHourVisitByDay 
	* @Description: 
	* @param @param chooesHourDate
	* @param @return    设定文件 
	* @return List<VisitForHour>    返回类型 
	* @throws 
	*/
	List<VisitForHour> getHourVisitByDay(String chooesHourDate);

}
