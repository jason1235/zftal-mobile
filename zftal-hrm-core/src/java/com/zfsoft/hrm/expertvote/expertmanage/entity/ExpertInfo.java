package com.zfsoft.hrm.expertvote.expertmanage.entity;

import java.io.Serializable;
import java.util.Date;

import com.zfsoft.common.query.QueryModel;
/**
 * 专家库信息
 * @author: xiaoyongjun
 * @since: 2014-3-18 上午10:36:26
 */
public class ExpertInfo extends QueryModel implements Serializable {

	
	/* serialVersionUID: serialVersionUID */
	
	private static final long serialVersionUID = -754788331652195325L;
	
	private String id;
	
	private String gh;			 //专家工号
		
	private String type;		 //专业代码
	
	private String sbz;			 //上报者
	
	private Date dedate;		 //入库时间
	
	private String config_id;    //专家推荐表ID
	
	private String instance_id;  //专家推荐配置ID
	
	private String xm;  //姓名

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGh() {
		return gh;
	}

	public void setGh(String gh) {
		this.gh = gh;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSbz() {
		return sbz;
	}

	public void setSbz(String sbz) {
		this.sbz = sbz;
	}

	public String getConfig_id() {
		return config_id;
	}

	public void setConfig_id(String config_id) {
		this.config_id = config_id;
	}

	public String getInstance_id() {
		return instance_id;
	}

	public void setInstance_id(String instance_id) {
		this.instance_id = instance_id;
	}

	public Date getDedate() {
		return dedate;
	}

	public void setDedate(Date dedate) {
		this.dedate = dedate;
	}

	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

}
