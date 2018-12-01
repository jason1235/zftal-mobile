package com.zfsoft.hrm.contract.dao;

import java.util.List;

import com.zfsoft.hrm.contract.entity.Contract;
import com.zfsoft.hrm.contract.entity.Fields;
import com.zfsoft.hrm.contract.entity.RemindContract;

/**
 * 
 * @author
 * 2014-2-28
 */
public interface IContractDao {
	public List<Contract> getPagedStaffList(Contract contract);
	public List<Contract> getPagedList(Contract contract);
	public void insert(Contract contract);
	public void update(Contract contract);
	public void operate(Contract contract);
	public Contract getStaff(Contract contract);
	
	public List<RemindContract> getContractList();
	public int getContract(String s);
	public List<Fields> getColumns();
	public void importinsert(Contract contract);
	public String findMaxBh(String regex);
}
