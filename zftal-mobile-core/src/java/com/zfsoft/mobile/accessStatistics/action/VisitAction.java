/**
 * 
 */
package com.zfsoft.mobile.accessStatistics.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.zfsoft.globalweb.utils.JSONUtils;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.mobile.accessStatistics.entity.VisitForDay;
import com.zfsoft.mobile.accessStatistics.entity.VisitForHour;
import com.zfsoft.mobile.accessStatistics.service.IVisitService;
import com.zfsoft.util.base.StringUtil;

/**
 * @author zhangxu
 * @description 
 * @date 2017-6-14 下午03:13:53
 */
public class VisitAction extends HrmAction{
	
	private IVisitService visitService;
	private String chooesDate;
	private String chooesHourDate;
	
	public String visitStatistic(){
		HttpServletRequest request = ServletActionContext.getRequest();
		int type= Integer.valueOf(request.getParameter("type"));
		Map<String,Object> map = new HashMap<String,Object>();
		switch (type) {
		case 1:
			List<VisitForDay> topVisitForDayList = visitService.getTopTodayVisitByMouth();
			List<String> topUserVisitCountList = new ArrayList<String>();
			List<String> topVisitTimeList = new ArrayList<String>();
			if(topVisitForDayList.size() > 0){
				for (int i = topVisitForDayList.size() -1; i >= 0; i--) {
					topUserVisitCountList.add(String.valueOf(topVisitForDayList.get(i).getUserVisitCount()));
					topVisitTimeList.add(topVisitForDayList.get(i).getVisitTime());
				}
			}
			map.put("success", true);
			map.put("topUserVisitCountList", topUserVisitCountList);
			map.put("topVisitTimeList", topVisitTimeList);
			getValueStack().set(DATA, map);
			break;
		case 2:
			Date date = new Date();
	    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
	    	chooesDate = request.getParameter("chooesDate");
	    	chooesDate = StringUtil.isEmpty(chooesDate) ? 
	    					formatter.format(date) : chooesDate;
			List<VisitForDay> visitForDayList = visitService.getTodayVisitByMouth(chooesDate+"-01");
			List<String> userVisitCountList = new ArrayList<String>();
			List<String> visitTimeList = new ArrayList<String>();
			String visitTime = null;
			for(VisitForDay entity : visitForDayList){
				userVisitCountList.add(String.valueOf(entity.getUserVisitCount()));
				visitTime = entity.getVisitTime();
				visitTimeList.add(visitTime.substring(visitTime.lastIndexOf("-")+1, visitTime.length())+"号");
			}
			map.put("success", true);
			map.put("userVisitCountList", userVisitCountList);
			map.put("visitTimeList", visitTimeList);
			map.put("chooesDate", chooesDate);
			getValueStack().set(DATA, map);
			break;
		case 3:
			List<VisitForHour> topVisitForHourList = visitService.getTopHourVisitByDay();
			List<String> topUserVisitHourCountList = new ArrayList<String>();
			List<String> topVisitTimeHourList = new ArrayList<String>();
			if(topVisitForHourList.size() > 0){
				for (int i = topVisitForHourList.size() -1; i >= 0; i--) {
					topUserVisitHourCountList.add(String.valueOf(topVisitForHourList.get(i).getUserVisitCount()));
					topVisitTimeHourList.add(topVisitForHourList.get(i).getVisitTime());
				}
			}
			map.put("success", true);
			map.put("topUserVisitHourCountList", topUserVisitHourCountList);
			map.put("topVisitTimeHourList", topVisitTimeHourList);
			getValueStack().set(DATA, map);
			break;
		case 4:
			Date dateHour = new Date();
	    	SimpleDateFormat formatterHour = new SimpleDateFormat("yyyy-MM-dd");
	    	chooesHourDate = request.getParameter("chooesHourDate");
	    	chooesHourDate = StringUtil.isEmpty(chooesHourDate) ? 
	    				formatterHour.format(dateHour) : chooesHourDate;
			List<VisitForHour> visitForHourList = visitService.getHourVisitByDay(chooesHourDate);
			List<String> userVisitHourCountList = new ArrayList<String>();
			List<String> visitTimeHourList = new ArrayList<String>();
			String visitHourTime = null;
			for(VisitForHour entity : visitForHourList){
				userVisitHourCountList.add(String.valueOf(entity.getUserVisitCount()));
				visitHourTime = entity.getVisitTime();
				visitTimeHourList.add(visitHourTime.substring(visitHourTime.lastIndexOf(" ")+1, visitHourTime.length()));
			}
			map.put("success", true);
			map.put("userVisitHourCountList", userVisitHourCountList);
			map.put("visitTimeHourList", visitTimeHourList);
			map.put("chooesHourDate", chooesHourDate);
			getValueStack().set(DATA, map);
			break;

		default:
			break;
		}
		
		return DATA;
	}
	

	public String visitForDayStatisticTop(){
		
		List<VisitForDay> topVisitForDayList = visitService.getTopTodayVisitByMouth();
		List<String> topUserVisitCountList = new ArrayList<String>();
		List<String> topVisitTimeList = new ArrayList<String>();
		if(topVisitForDayList.size() > 0){
			for (int i = topVisitForDayList.size() -1; i >= 0; i--) {
				topUserVisitCountList.add(String.valueOf(topVisitForDayList.get(i).getUserVisitCount()));
				topVisitTimeList.add(topVisitForDayList.get(i).getVisitTime());
			}
		}
		getValueStack().set("topUserVisitCountList", JSONUtils.obj2json(topUserVisitCountList));
		getValueStack().set("topVisitTimeList", JSONUtils.obj2json(topVisitTimeList));
		
		return "visitForDayStatistic";
	}


	public String getChooesDate() {
		return chooesDate;
	}


	public void setChooesDate(String chooesDate) {
		this.chooesDate = chooesDate;
	}


	public void setVisitService(IVisitService visitService) {
		this.visitService = visitService;
	}


	public IVisitService getVisitService() {
		return visitService;
	}


	public String getChooesHourDate() {
		return chooesHourDate;
	}


	public void setChooesHourDate(String chooesHourDate) {
		this.chooesHourDate = chooesHourDate;
	}
	
}
