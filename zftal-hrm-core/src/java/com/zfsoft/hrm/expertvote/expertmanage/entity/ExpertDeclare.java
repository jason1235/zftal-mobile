package com.zfsoft.hrm.expertvote.expertmanage.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.common.query.QueryModel;
import com.zfsoft.workflow.enumobject.WorkNodeStatusEnum;

/**
 * 专家推荐上报
 * @author: xiaoyongjun
 * @since: 2014-3-11 上午09:47:32
 */
public class ExpertDeclare extends QueryModel implements Serializable {
	
	/* serialVersionUID: serialVersionUID */
	
	private static final long serialVersionUID = 6960358518416875479L;
	private String id;  
	private String tjrgh;  //推荐人工号
	private String type;   //专家类型
	private String sbz;    //上报者
	private Date dedate; //上报时间
	private String config_id;  //动态表单配置id
	private String instance_id;  //动态表单实例id
	private String status;    //审核状态
	private Date adudate;   //审核时间
	
	private String tjrxm;   //推荐人姓名
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTjrgh() {
		return tjrgh;
	}
	public void setTjrgh(String tjrgh) {
		this.tjrgh = tjrgh;
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
	public Date getDedate() {
		return dedate;
	}
	public void setDedate(Date dedate) {
		this.dedate = dedate;
	}
	public Date getAdudate() {
		return adudate;
	}
	public void setAdudate(Date adudate) {
		this.adudate = adudate;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusText() {
		if (StringUtils.isEmpty(status)) {
			return "未上报";
		}
		try {
			WorkNodeStatusEnum e = Enum.valueOf(WorkNodeStatusEnum.class,
					status);
			if (e == null) {
				return null;
			} else {
				return e.getText();
			}
		} catch (Exception e) {
			return "无法解析的状态:" + status;
		}
	}
	public String getTjrxm() {
		return tjrxm;
	}
	public void setTjrxm(String tjrxm) {
		this.tjrxm = tjrxm;
	}
}
