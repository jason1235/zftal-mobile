package com.zfsoft.hrm.staffturn.retire.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DuplicateKeyException;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.baseinfo.dyna.business.bizinterface.IDynaBeanBusiness;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.message.business.IMessageBusiness;
import com.zfsoft.hrm.message.entity.Message;
import com.zfsoft.hrm.staffturn.config.IStatusUpdateConfig;
import com.zfsoft.hrm.staffturn.retire.business.IRetireBusiness;
import com.zfsoft.hrm.staffturn.retire.dao.daointerface.IRetireDao;
import com.zfsoft.hrm.staffturn.retire.entities.RetireInfo;
import com.zfsoft.hrm.staffturn.retire.query.RetireInfoQuery;
import com.zfsoft.hrm.staffturn.retire.service.svcinterface.IRetireService;
import com.zfsoft.util.base.StringUtil;

/**
 * @author  沈鲁威 Patrick Shen
 * @since  2012-7-25
 * @version  V1.0.0
 */
public class RetireServiceImpl implements IRetireService {
	
	private IRetireDao dao;
	private IRetireBusiness retireBusiness;
	private IDynaBeanBusiness iDynaBeanBusiness;
	// 20140425 add start
	private IMessageBusiness messageBusiness;

	private final static String NOTIFY = "notify";
	private final static String SELF = "self";
	// 20140425 add end
	/**返聘人员信息类标识*/
	public static final String FPRYB="hrm_fpryb";
	
	public void setiDynaBeanBusiness(IDynaBeanBusiness iDynaBeanBusiness) {
		this.iDynaBeanBusiness = iDynaBeanBusiness;
	}

	public void setRetireBusiness(IRetireBusiness retireBusiness) {
		this.retireBusiness = retireBusiness;
	}

	public void setDao(IRetireDao dao) {
		this.dao = dao;
	}

	@Override
	public void saveRetire(RetireInfo retireInfo,String type) {

		if(StringUtil.isEmpty(retireInfo.getUserId())){
			throw new RuntimeException("工号不能为空");
		}
		
		if(type.equals("add")){
			addPersonToPreRetire(retireInfo);
		}else{
			boolean exist =iDynaBeanBusiness.existPerson(retireInfo.getUserId());
			if(!exist){
				throw new RuntimeException("不存在该员工，请确认工号输入是否准确!");
			}
			dao.update(retireInfo);
		}
		if(new Integer(1).equals(retireInfo.getState())){
			setPersonState(retireInfo,IStatusUpdateConfig.RETIRE_STATUS);
		}
	}
	// 20140425 add start
	/**
	 * 
	 */
	@Override
	public void saveMessage(RetireInfo retireInfo) {
		// 通知
		Message message = buildMessage(retireInfo.getUserId(), NOTIFY);
		message.setReceiver(retireInfo.getReceiver());
		messageBusiness.save(message);

		Message notifySelfMessage = buildMessage(retireInfo.getUserId(), SELF);
		notifySelfMessage.setReceiver(retireInfo.getUserId());
		messageBusiness.save(notifySelfMessage);
	}
	// 20140425 add end

	private void addPersonToPreRetire(RetireInfo retireInfo) {
		String[] userIds= retireInfo.getUserId().split(";");
		for (String userId : userIds) {
			boolean exist =iDynaBeanBusiness.existPerson(userId);
			if(!exist){
				throw new RuntimeException("不存在该员工，请确认工号输入是否准确!");
			}
			try{
				retireInfo.setUserId(userId);
				dao.insert(retireInfo);
			}catch(DuplicateKeyException e){
				throw new RuntimeException("预退休人员已存在!");
			}
		}
	}

	@Override
	public void removeRetire(RetireInfo retireInfo) {
		dao.delete(retireInfo);
	}

	@Override
	public void modifyPreRetireToRetire(RetireInfo retireInfo) {
		retireInfo.setState(1);
		dao.update(retireInfo);
		
		setPersonState(retireInfo,retireInfo.getDoResult());
		//gh,xm,dwm同步到fpryb信息类中
		if(retireInfo.getDoResult().equals(IStatusUpdateConfig.RETIRE_REENGAGE)){
			InfoClass clazz = InfoClassCache.getOverallInfoClass();
			DynaBean dyBean = new DynaBean(clazz);
			DynaBeanQuery dyQuery=new DynaBeanQuery( clazz );
			dyQuery.setExpress( "gh = #{params.gh}" );
			dyQuery.setParam( "gh", retireInfo.getUserId());
			List<DynaBean> dyBeans=iDynaBeanBusiness.queryBeans( dyQuery );
			if(dyBeans.size()>0){
				dyBean = dyBeans.get(0);
			}else{
				throw new RuleException("未找到该员工基本数据");
			}
			InfoClass clazz_fpry = InfoClassCache.getInfoClassByName(FPRYB);
			DynaBean dyBean_fpry = new DynaBean(clazz_fpry);
			dyBean_fpry.setValue("gh", dyBean.getValue("gh"));
			dyBean_fpry.setValue("xm", dyBean.getValue("xm"));
			dyBean_fpry.setValue("dwm", dyBean.getValue("dwm"));
			List<InfoProperty> pros=new ArrayList<InfoProperty>();
			if(clazz_fpry.getPropertyByName("gh")!=null){
				pros.add(clazz_fpry.getPropertyByName("gh"));
			}
			if(clazz_fpry.getPropertyByName("xm")!=null){
				pros.add(clazz_fpry.getPropertyByName("xm"));
			}
			if(clazz_fpry.getPropertyByName("dwm")!=null){
				pros.add(clazz_fpry.getPropertyByName("dwm"));
			}
			for (InfoProperty infoProperty : pros) {
				infoProperty.setEditable(true);
			}
			dyBean_fpry.setEditables(pros);
			iDynaBeanBusiness.addRecordNoCheckGh(dyBean_fpry);
		}
	}

	private void setPersonState(RetireInfo retireInfo,String state) {
		InfoClass clazz = InfoClassCache.getInfoClass(IStatusUpdateConfig.BASEINFO_CLASS_ID);
		DynaBean dyBean = new DynaBean(clazz);
		DynaBeanQuery dyQuery=new DynaBeanQuery( clazz );
		dyQuery.setExpress( "gh = #{params.gh}" );
		dyQuery.setParam( "gh", retireInfo.getUserId());
		List<DynaBean> dyBeans=iDynaBeanBusiness.queryBeans( dyQuery );
		if(dyBeans.size()>0){
			dyBean = dyBeans.get(0);
		}else{
			throw new RuleException("未找到该员工基本数据");
		}
		Map<String, Object> values = new HashMap<String, Object>();
		values.put(IStatusUpdateConfig.STATUS_COLUMN_NAME, state);
		iDynaBeanBusiness.modifyRecord(dyBean, values, false);
	}

	@Override
	public PageList<RetireInfo> getPreRetireInfoList(RetireInfoQuery query) {
		query.setState(0);
		query.setQueryClass(RetireInfo.class);
		PageList<RetireInfo> pageList = new PageList<RetireInfo>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			
			paginator.setItems(dao.getCountQuery(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
			}
			List<RetireInfo> list=setOverall(dao.findByQuery(query));
			pageList.addAll(list);
		}
		return pageList;
		
	}
	@Override
	public PageList<RetireInfo> getRetireInfoList(RetireInfoQuery query) {
		query.setState(1);
		query.setQueryClass(RetireInfo.class);
		PageList<RetireInfo> pageList = new PageList<RetireInfo>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			
			paginator.setItems(dao.getCountQuery(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
			}
			List<RetireInfo> list=setOverall(dao.findByQuery(query));
			pageList.addAll(list);
		}
		return pageList;
	}

	private List<RetireInfo> setOverall(List<RetireInfo> retireInfos){
		for(RetireInfo retireInfo : retireInfos){
			retireInfo.setOverall(iDynaBeanBusiness.findUniqueByParam("gh", retireInfo.getUserId()));
		}
		return retireInfos;
	}
	@Override
	public RetireInfo getPreRetireByUserId(String userId) {
		RetireInfo retireInfo=retireBusiness.getPreRetireByUserId(userId);
		if(retireInfo==null){
			return null;
		}
		retireInfo.setOverall(iDynaBeanBusiness.findUniqueByParam("gh", retireInfo.getUserId()));
		return retireInfo;
	}
	
	@Override
	public RetireInfo getRetireInfoByUserId(String userId) {
		RetireInfo retireInfo=retireBusiness.getRetireInfoByUserId(userId);
		if(retireInfo==null){
			return null;
		}
		retireInfo.setOverall(iDynaBeanBusiness.findUniqueByParam("gh", retireInfo.getUserId()));
		return retireInfo;
	}
    /**
     * 撤销预退休人员
     * add by heyc on 20130806
     */
	@Override
	public void cancelPreRetire(RetireInfo retireInfo) {
		int  i=dao.delete(retireInfo);
	/*	if(i==0){
			System.out.println(retireInfo.getTableName()+" table is not have id :" +retireInfo.getUserId()+" dataInfo");
		}*/
	}
	
	@Override
	public int getCountByUserId(String userId){
		RetireInfoQuery query=new RetireInfoQuery();
		query.setQueryClass(RetireInfo.class);
		query.setUserId(userId);
		return dao.getCountQuery(query);
	}

	@Override
	public List<RetireInfo> getPreList(RetireInfoQuery query) {
		query.setState(0);
		query.setQueryClass(RetireInfo.class);
		List<RetireInfo> list=setOverall(dao.findByQuery(query));
		
		return list;
	}

	@Override
	public void doPlxgtxwh(List<RetireInfo> list) {
		if(list==null||list.size()==0){
			return;
		}
		for (RetireInfo retireInfo : list) {
			dao.update(retireInfo);
		}
	}
	
	@Override
	public void doPlxgtxwh2(RetireInfo retireInfo) {
		dao.updateXgwh(retireInfo);
	}

	@Override
	public void modify(RetireInfo retireInfo) {
		dao.update(retireInfo);
	}
	
	// 20140425 add start
	public void setMessageBusiness(IMessageBusiness messageBusiness) {
		this.messageBusiness = messageBusiness;
	}
	
	/**
	 * 添加信息
	 */
	private Message buildMessage(String userId, String type){
		Message message = new Message();
		// 标题
		String strTitle = "";
		// 内容
		String strContent = "";
		
		// 发送给通知人员
		if (NOTIFY.equals(type)) {
			strTitle = "人员退休提醒";
			strContent = "员工:[code]" + userId + "[:code] 已经退休，请注意处理";
		} else {
			strTitle = "退休提醒";
			strContent = "您已经退休，请注意处理";
		}
		message.setSender("system");
		message.setTitle(strTitle);
		message.setContent(strContent);
		return message;
	}
	
	@Override
	public String getReceiver() {
		return dao.getReceiver();
	}

	@Override
	public int getReceiverCount() {
		return dao.getReceiverCount();
	}

	@Override
	public void insertReceiver(RetireInfo retireInfo) {
		dao.insertReceiver(retireInfo);
	}

	@Override
	public void updateReceiver(RetireInfo retireInfo) {
		dao.updateReceiver(retireInfo);
	}
	// 20140425 add end
}
