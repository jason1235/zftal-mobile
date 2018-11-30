package com.zfsoft.hrm.contract.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.baseinfo.data.entity.InfoType;
import com.zfsoft.hrm.baseinfo.data.util.DataProcessInfoUtil;
import com.zfsoft.hrm.contract.dao.IContractDao;
import com.zfsoft.hrm.contract.entity.Contract;
import com.zfsoft.hrm.contract.entity.Fields;
import com.zfsoft.hrm.contract.entity.ImportContractValidator;
import com.zfsoft.hrm.contract.entity.StatusEnum;
import com.zfsoft.hrm.contract.service.IContractService;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.orcus.lang.TimeUtil;

/**
 * 
 * @author
 * 2014-2-28
 */
public class ContractServiceImpl implements IContractService {

	private IContractDao contractDao;
	
	@Override
	public PageList<Contract> getStaffList(Contract contract) {
		PageList<Contract> pageList = new PageList<Contract>();
		pageList.addAll(contractDao.getPagedStaffList(contract));
		Paginator paginator = new Paginator();
		paginator.setItemsPerPage(contract.getShowCount());
		paginator.setPage(contract.getCurrentPage());
		paginator.setItems(contract.getTotalResult());
		pageList.setPaginator(paginator);
		return pageList;
	}

	/**
	 * @return the contractDao
	 */
	public IContractDao getContractDao() {
		return contractDao;
	}

	/**
	 * @param contractDao the contractDao to set
	 */
	public void setContractDao(IContractDao contractDao) {
		this.contractDao = contractDao;
	}

	@Override
	public PageList<Contract> getList(Contract contract) {
		PageList<Contract> pageList = new PageList<Contract>();
		pageList.addAll(contractDao.getPagedList(contract));
		Paginator paginator = new Paginator();
		paginator.setItemsPerPage(contract.getShowCount());
		paginator.setPage(contract.getCurrentPage());
		paginator.setItems(contract.getTotalResult());
		pageList.setPaginator(paginator);
		return pageList;
	}

	@Override
	public void doSign(Contract contract) {
		contractDao.insert(contract);
	}

	@Override
	public Contract getStaff(Contract contract) {
		return contractDao.getStaff(contract);
	}

	@Override
	public void doModify(Contract contract) {
		contractDao.update(contract);
	}

	@Override
	public void doOperate(Contract contract, List<String> ids) {
		for(String id : ids){
			contract.setWorkNum(id);
			contractDao.operate(contract);
		}
	}

	@Override
	public void doRegular(List<String> ids) {
			for(String id : ids){
				Contract contract = new Contract();
				contract.setWorkNum(id);
				contract = getStaff(contract);
				if(contract.getActualProbationDate() != null){
					throw new RuntimeException("不能重复转正！");
				}
			}
	}

	@Override
	public void doSequel(Contract contract) {
		contract.setStatus(StatusEnum.TERMINATE.getKey());
		contract.setOverResult("合同续聘");
		contract.setActualEndDate(new Date());
		contractDao.operate(contract);
		contract.setStatus(StatusEnum.SIGNATURE.getKey());
		contractDao.insert(contract);
	}
	public String findMaxBh(String regex){
		return contractDao.findMaxBh(regex);
	}
	@Override
	public List<Fields> getColumns() {
		return contractDao.getColumns();
	}
	@Override
	public void doContractImport(ImportContractValidator icv) throws Exception {
		int maxError = 20;
		Map<String,HashMap<String,String>> dataMap = icv.getDataMap();
		int cnt = 0;
		DataProcessInfoUtil.setInfo("数据导入开始", null);
		int step=0;
		for(Entry entry : dataMap.entrySet()){
			HashMap<String,String> data = (HashMap<String,String>)entry.getValue();
			Contract contract = new Contract();
			contract.setWorkNum(data.get("GH"));
			contract.setNumber(data.get("HTBH"));
			contract.setType(data.get("HTZLBM"));
			contract.setTerm(Integer.parseInt(data.get("HTQX")));
			contract.setStartDate(TimeUtil.toDate(data.get("HTQSRQ")));
			contract.setProbationDate(TimeUtil.toDate(data.get("SYQJZRQ")));
			contract.setActualProbationDate(TimeUtil.toDate(data.get("SYQSJJSRQ")));
			contract.setEndDate(TimeUtil.toDate(data.get("HTZZRQ")));
			contract.setDeferDate(TimeUtil.toDate(data.get("YQJSSJ")));			
			contract.setActualEndDate(TimeUtil.toDate(data.get("SJJSSJ")));			
			contract.setStatus(data.get("ZT"));
			contract.setDisuse(Boolean.parseBoolean(data.get("SFZF")));		
			contract.setRemark(data.get("BZ"));
			contract.setCreator(data.get("CJR"));
			contract.setCreateDate(TimeUtil.toDate(data.get("CJSJ")));
			contract.setMender(data.get("XGR"));
			contract.setUpdateDate(TimeUtil.toDate(data.get("XGSJ")));			
			contract.setOverResult(data.get("ZZYY"));
			contract.setRegularResult(data.get("SYQZZSM"));
			contract.setDeferResult(data.get("YQYY"));
			contract.setRavelDate(TimeUtil.toDate(data.get("JCRQ")));
			contract.setRavelResult(data.get("JCYY"));

			try{
				DataProcessInfoUtil.setStep("数据入库:", ++step, dataMap.size());
				contractDao.importinsert(contract);
			}catch (RuleException e) {
				cnt++;
				DataProcessInfoUtil.setInfo("第"+step+"行，数据规则异常，"+e.getMessage(),null);
				if(cnt>=maxError){
					throw new RuleException("数据入库检测规则异常达到"+maxError+"次，入库操作终止");
				}
			}catch (Exception e) {
				throw e;
			}
		}
		DataProcessInfoUtil.setInfo("数据导入完成", null);
		DataProcessInfoUtil.setInfo("数据导入:共"+dataMap.size()+"条",InfoType.INFO);
	}
}
