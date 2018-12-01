package com.zfsoft.hrm.expertvote.vote.entity;

import com.zfsoft.util.base.StringUtil;

/**
 * 
 * @author ChenMinming
 * @date 2014-3-14
 * @version V1.0.0
 */
public class ExpertTask {
	//任务ID （与工作流任务id保持统一）
	private String id;
	//任务名称
	private String name;
	//审核级别（与专家组审核级别对应）
	private String level;
	//通过比率控制
	private String passPoint;
	//比率计算方式（暂时只有  0:进一）
	private String pointType="0";
	//所属模块
	private String belongToSys;
	//所属模块名称
	private String belongToSysName;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLevel() {
		return level;
	}
	
	public void setLevel(String level) {
		this.level = level;
	}
	
	public String getPassPoint() {
		return passPoint;
	}
	
	public void setPassPoint(String passPoint) {
		this.passPoint = passPoint;
	}
	
	public String getPointType() {
		return pointType;
	}
	
	public void setPointType(String pointType) {
		this.pointType = pointType;
	}
	
	public int computeAllowNum(int sum) {
		int point = 0;
		try{
			if(StringUtil.isEmpty(passPoint)){
				return 0;
			}
			point = Integer.valueOf(passPoint.trim());
		}catch(NumberFormatException e){
			return 0;
		}
		//进一
		if("0".equals(pointType)){
			int a = sum*point%100==0?0:1;
			return sum*point/100+a;
		}
		//四舍五入
		else if("1".equals(pointType)){
			int a = sum*point%100-50<0?0:1;
			return sum*point/100+a;
		}
		//去尾
		else{
			return sum*point/100;
		}
	}

	/**
	 * 返回
	 */
	public String getBelongToSys() {
		return belongToSys;
	}

	/**
	 * 设置
	 * @param belongToSys 
	 */
	public void setBelongToSys(String belongToSys) {
		this.belongToSys = belongToSys;
	}

	/**
	 * 返回
	 */
	public String getBelongToSysName() {
		return belongToSysName;
	}

	/**
	 * 设置
	 * @param belongToSysName 
	 */
	public void setBelongToSysName(String belongToSysName) {
		this.belongToSysName = belongToSysName;
	}
}
