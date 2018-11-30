package com.zfsoft.hrm.contract.service;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.contract.entity.Contract;
import com.zfsoft.hrm.contract.entity.Fields;
import com.zfsoft.hrm.contract.entity.ImportContractValidator;

/**
 * 
 * @author
 * 2014-2-28
 */
public interface IContractService {
	public PageList<Contract> getStaffList(Contract contract);
	public PageList<Contract> getList(Contract contract);
	public void doSign(Contract contract);
	public void doModify(Contract contract);
	public void doSequel(Contract contract);
	public void doOperate(Contract contract, List<String> ids);
	public void doRegular(List<String> ids);
	public Contract getStaff(Contract contract);
	public List<Fields> getColumns();
	public void doContractImport(ImportContractValidator icv) throws Exception;
	public String findMaxBh(String regex);
}
