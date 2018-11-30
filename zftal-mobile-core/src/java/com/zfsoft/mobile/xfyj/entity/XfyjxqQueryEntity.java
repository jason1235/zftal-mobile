package com.zfsoft.mobile.xfyj.entity;

import com.zfsoft.dao.query.BaseQuery;


public class XfyjxqQueryEntity extends BaseQuery{
    private String id;         //id
    private String xfyjId;         //所属模块id
    private String zgh;         //职工号（用户id）
    private String dataName;         //展示数据名称
    private String dataCon;         //展示数据内容
    
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getXfyjId() {
		return xfyjId;
	}
	public void setXfyjId(String xfyjId) {
		this.xfyjId = xfyjId;
	}
	public String getZgh() {
		return zgh;
	}
	public void setZgh(String zgh) {
		this.zgh = zgh;
	}
	public String getDataName() {
		return dataName;
	}
	public void setDataName(String dataName) {
		this.dataName = dataName;
	}
	public String getDataCon() {
		return dataCon;
	}
	public void setDataCon(String dataCon) {
		this.dataCon = dataCon;
	}
    
    
	
	
}
