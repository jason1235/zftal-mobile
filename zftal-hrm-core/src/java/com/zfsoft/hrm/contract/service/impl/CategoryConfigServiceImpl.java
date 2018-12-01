package com.zfsoft.hrm.contract.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;

import com.zfsoft.common.exception.ServiceException;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.contract.dao.ICategoryConfigDao;
import com.zfsoft.hrm.contract.dao.IContractDao;
import com.zfsoft.hrm.contract.entity.CategoryConfig;
import com.zfsoft.hrm.contract.entity.RemindContract;
import com.zfsoft.hrm.contract.service.ICategoryConfigService;
import com.zfsoft.hrm.message.business.IMessageBusiness;
import com.zfsoft.hrm.message.entity.Message;
import com.zfsoft.orcus.lang.TimeUtil;

public class CategoryConfigServiceImpl implements ICategoryConfigService {
	private static Log log = LogFactory.getLog(CategoryConfigServiceImpl.class);
	private ICategoryConfigDao categoryConfigDao;
	private IContractDao contractDao;
	private IMessageBusiness messageBusiness;

	@Override
	public int checkDm(String id) throws RuntimeException {
		return categoryConfigDao.checkDm(id);
	}

	@Override
	public void delete(String id) throws DataAccessException {
		int count = contractDao.getContract(id);
		if (count > 0) {
			throw new ServiceException("该合同种类代码有用户已经使用，不能删除！");
		}
		categoryConfigDao.delete(id);
	}
	
	@Override
	public CategoryConfig getById(String id) throws RuntimeException {
		return categoryConfigDao.getById(id);
	}
	
	@Override
	public PageList<CategoryConfig> getPagedList(CategoryConfig categoryConfig)
			throws RuntimeException {
		PageList<CategoryConfig> pageList = new PageList<CategoryConfig>();
		pageList.addAll(categoryConfigDao.getPagedList(categoryConfig));
		Paginator paginator = new Paginator();
		paginator.setItemsPerPage(categoryConfig.getShowCount());
		paginator.setPage(categoryConfig.getCurrentPage());
		paginator.setItems(categoryConfig.getTotalResult());
		pageList.setPaginator(paginator);
		return pageList;
	}

	@Override
	public String getXm(String gh) throws RuntimeException {
		return categoryConfigDao.getXm(gh);
	}

	@Override
	public void insert(CategoryConfig categoryConfig) throws RuntimeException {
		categoryConfigDao.insert(categoryConfig);
	}

	@Override
	public void doScanByDate() {
		List<RemindContract> contracts = contractDao.getContractList();
		// 遍历list,获取各合同种类类型对应的用户
		for (RemindContract rContract : contracts) {
			try {
				this.sendMessage(rContract);
			} catch (Exception e) {
				log.error("", e);
			}
		}
	}
	/**
	 * 根据到期日期和提醒天数，计算提醒日期
	 * @return
	 * @param zzrq终止日期，t提醒天数
	 */
	private Date computeRemindDate(Date zzrq,int t){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(zzrq);
		calendar.add(Calendar.DATE, 0-t);
		return calendar.getTime();
	}
	/**
	 * 提醒消息处理
	 * 
	 * @param retireRule
	 * @param receiver
	 * @param notifySelf
	 */
	private void sendMessage(RemindContract rContract) {
		String receiver = rContract.getGlry();
		int maturityDay = rContract.getDqtxts();
		int extendDay = rContract.getXqtxts();
		int tryoutDay = rContract.getSyqtxts();
		
		if(rContract.getHtzzrq()!=null && !"".equals(rContract.getHtzzrq())){
			Date mDate = this.computeRemindDate(rContract.getHtzzrq(), maturityDay);     //合同到期提醒日期
			//到期提醒
			if(TimeUtil.format(mDate, "yyyyMMdd").equals(TimeUtil.date())){
				Message message = maturityMessage(rContract);
				message.setReceiver(receiver);
				messageBusiness.save(message);
			}
		}
		
		if(rContract.getHtzzrq() != null && !"".equals(rContract.getHtzzrq())){
			Date eDate = this.computeRemindDate(rContract.getHtzzrq(), extendDay);       //合同续签提醒日期
			// 续签提醒
			if (TimeUtil.format(eDate, "yyyyMMdd").equals(TimeUtil.date())) {
				Message message = extendMessage(rContract);
				message.setReceiver(receiver);
				messageBusiness.save(message);
			}
		}
		
		if(rContract.getSyqjzrq() !=null && !"".equals(rContract.getSyqjzrq())){
			Date tDate = this.computeRemindDate(rContract.getSyqjzrq(), tryoutDay);      //合同试用期提醒日期	
			// 试用期提醒
			if (TimeUtil.format(tDate, "yyyyMMdd").equals(TimeUtil.date())) {
				Message message = tryoutMessage(rContract);
				message.setReceiver(receiver);
				messageBusiness.save(message);
			}
		}
	}
	/**
	 * 到期提醒
	 * 
	 * @return:
	 * @since: 2014-3-4 上午09:55:57
	 */
	private Message maturityMessage(RemindContract rContract) {
		Message message = new Message();
		message.setSender("system");
		message.setTitle("到期合同提醒");
		message.setContent("员工:[code]" + rContract.getGh()
				+ "[:code] 合同即将到期，请注意处理！");
		return message;
	}
	/**
	 * 续签提醒
	 * 
	 * @param:
	 * @since: 2014-3-4 上午09:56:24
	 */
	private Message extendMessage(RemindContract rContract) {
		Message message = new Message();
		message.setSender("system");
		message.setTitle("续签合同提醒");
		message.setContent("员工:[code]" + rContract.getGh()
				+ "[:code] 有续签合同需要续签，请注意处理！");
		return message;
	}
	/**
	 * 试用期提醒
	 * 
	 * @return:
	 * @since: 2014-3-4 上午09:56:41
	 */
	private Message tryoutMessage(RemindContract rContract) {
		Message message = new Message();
		message.setSender("system");
		message.setTitle("试用期合同提醒");
		message.setContent("员工:[code]" + rContract.getGh()
				+ "[:code] 试用期合同即将到期，请注意处理！");
		return message;
	}
	@Override
	public void update(CategoryConfig categoryConfig) throws RuntimeException {
		categoryConfigDao.update(categoryConfig);
	}
	public void setCategoryConfigDao(ICategoryConfigDao categoryConfigDao) {
		this.categoryConfigDao = categoryConfigDao;
	}

	public void setContractDao(IContractDao contractDao) {
		this.contractDao = contractDao;
	}

	public void setMessageBusiness(IMessageBusiness messageBusiness) {
		this.messageBusiness = messageBusiness;
	}
	public ICategoryConfigDao getCategoryConfigDao() {
		return categoryConfigDao;
	}

	public IContractDao getContractDao() {
		return contractDao;
	}

	public IMessageBusiness getMessageBusiness() {
		return messageBusiness;
	}
}
