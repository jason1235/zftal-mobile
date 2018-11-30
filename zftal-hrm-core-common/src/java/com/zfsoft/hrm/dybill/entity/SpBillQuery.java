package com.zfsoft.hrm.dybill.entity;

import java.util.List;

import com.zfsoft.dao.query.BaseQuery;
import com.zfsoft.hrm.dybill.enums.BillConfigStatus;
import com.zfsoft.hrm.dybill.enums.BillType;

public class SpBillQuery extends BaseQuery {
	
	/* serialVersionUID: serialVersionUID */
	
	private static final long serialVersionUID = -8357427308354227132L;
	private String id;
	private String name;
	private BillConfigStatus status;
	private BillType bill_type;
	private String id_name;
	private List<Integer> versions;
	
	public List<Integer> getVersions() {
		return versions;
	}
	public void setVersions(List<Integer> versions) {
		this.versions = versions;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public BillConfigStatus getStatus() {
		return status;
	}
	public void setStatus(BillConfigStatus status) {
		this.status = status;
	}
	public BillType getBill_type() {
		return bill_type;
	}
	public void setBill_type(BillType bill_type) {
		this.bill_type = bill_type;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getId_name() {
		return id_name;
	}
	public void setId_name(String id_name) {
		this.id_name = id_name;
	}

}
