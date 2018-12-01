package com.zfsoft.hrm.baseinfo.data.entity;

import java.util.Date;

import com.zfsoft.orcus.lang.TimeUtil;

/** 
 * 处理信息对象
 * @author jinjj
 * @date 2012-10-25 上午09:21:55 
 *  
 */
public class InfoEntity {

	private Date createTime;
	
	private String info;
	
	private InfoType infoType;

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public InfoType getInfoType() {
		return infoType;
	}

	public void setInfoType(InfoType infoType) {
		this.infoType = infoType;
	}

	public String getFomatInfo(){
		if(info.equals("-1")){
			return info;
		}else{
			StringBuilder sb = new StringBuilder();
			sb.append("<li>");
			sb.append("<font color='gray'>[");
			sb.append(TimeUtil.format(createTime, TimeUtil.HH_mm_ss_SSS));
			sb.append("]</font> ");
			sb.append(getTypeInfo(infoType));
			sb.append(" ").append(info);
			sb.append("</li>");
			return sb.toString();
		}
	}
	
	private String getTypeInfo(InfoType type){
		switch(type){
			case ERROR:
				return "<font color='tomato'>[错误]</font>";
			case WARN:
				return "<font color='khaki'>[警告]</font>";
			case INFO:
				return "<font color='lightskyblue'>[信息]</font>";
			default:
				return "<font color='silver'>[未知]</font>";
		}
	}
}
