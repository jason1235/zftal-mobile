package com.zfsoft.hrm.staffturn.dead.service.impl;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.baseinfo.dyna.business.bizinterface.IDynaBeanBusiness;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.staffturn.config.IStatusUpdateConfig;
import com.zfsoft.hrm.staffturn.dead.dao.daointerface.IDeadDao;
import com.zfsoft.hrm.staffturn.dead.entities.DeadInfo;
import com.zfsoft.hrm.staffturn.dead.query.DeadInfoQuery;
import com.zfsoft.hrm.staffturn.dead.service.svcinterface.IDeadService;
import com.zfsoft.hrm.staffturn.retire.business.IRetireBusiness;

/**
 * 离世
 * @author  沈鲁威 Patrick Shen
 * @since  2012-7-25
 * @version  V1.0.0
 */
public class DeadServiceImpl implements IDeadService {
	
	private IDeadDao deadDao;
	private IRetireBusiness retireBusiness;
	private IDynaBeanBusiness iDynaBeanBusiness;
	
	public void setiDynaBeanBusiness(IDynaBeanBusiness iDynaBeanBusiness) {
		this.iDynaBeanBusiness = iDynaBeanBusiness;
	}

	public void setDeadDao(IDeadDao deadDao) {
		this.deadDao = deadDao;
	}

	public void setRetireBusiness(IRetireBusiness retireBusiness) {
		this.retireBusiness = retireBusiness;
	}

	@Override
	public PageList<DeadInfo> getDeadInfoList(DeadInfoQuery query) {
		query.setQueryClass(DeadInfo.class);
		//查询结果集
		PageList<DeadInfo> deadInfolist=deadDao.getPagedQuery(query);
		for(DeadInfo deadInfo : deadInfolist){
				deadInfo.setOverall(
						iDynaBeanBusiness.findUniqueByParam("gh", deadInfo.getUserId()));
				deadInfo.setRetireInfo(
						retireBusiness.getRetireInfoByUserIdAndState(deadInfo.getUserId(),"2"));
				setDeadAge(deadInfo);     //计算离世年龄
		}
		
		Paginator paginator = new Paginator();
		paginator.setItemsPerPage(query.getShowCount());
		paginator.setPage(query.getCurrentPage());
		paginator.setItems(query.getTotalResult());
		deadInfolist.setPaginator(paginator);
		return deadInfolist;
	}

	private void setDeadAge(DeadInfo deadInfo){
		Date lsrq = deadInfo.getDeadTime();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date csrq = null;
		try {
			if(deadInfo.getOverall() != null 
					&& deadInfo.getOverall().getValue("csrq") != null){
				csrq = df.parse(deadInfo.getOverall().getValue("csrq").toString());
			}			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String deadAge;
		if(lsrq ==null || csrq ==null){
			deadAge = "";
		}else{
			long day=(lsrq.getTime()-csrq.getTime())/(24*60*60*1000)+1;
			String year=new DecimalFormat("#.00").format(day/365f);
			String age[]=year.split("\\.");
			deadAge = age[0].replace(" ", "").length()<1?"0":age[0];
		}
		deadInfo.setDeadAge(deadAge);
	}
	
	private void setPersonState(DeadInfo deadInfo,String state) {
		InfoClass clazz = InfoClassCache.getInfoClass(IStatusUpdateConfig.BASEINFO_CLASS_ID);
		DynaBean dyBean = new DynaBean(clazz);
		DynaBeanQuery dyQuery=new DynaBeanQuery( clazz );
		dyQuery.setExpress( "gh = #{params.gh}" );
		dyQuery.setParam( "gh", deadInfo.getUserId());
		List<DynaBean> dyBeans=iDynaBeanBusiness.queryBeans( dyQuery );
		if(dyBeans.size()>0){
			dyBean = dyBeans.get(0);
		}else{
			throw new RuleException("未找到该员工基本数据");
		}
		Map<String, Object> values = new HashMap<String, Object>();
		values.put(IStatusUpdateConfig.STATUS_COLUMN_NAME, state);
		retireBusiness.updateStateToDead(deadInfo.getUserId());
		iDynaBeanBusiness.modifyRecord(dyBean, values, false);
	}
	@Override
	public void saveDeadInfo(DeadInfo deadInfo, String type) {
		if(type.equals("add")){
			deadDao.insert(deadInfo);
			setPersonState(deadInfo,IStatusUpdateConfig.DEAD_STATUS);
		}
		else
			deadDao.update(deadInfo);
	}

	@Override
	public void removeDeadInfo(DeadInfoQuery query) {
		deadDao.delete(query);
	}

	@Override
	public DeadInfo getDeadInfoByUserId(String userId) {
		DeadInfoQuery query=new DeadInfoQuery();
		query.setQueryClass(DeadInfo.class);
		query.setUserId(userId);
		DeadInfo deadInfo=deadDao.findById(query);
		deadInfo.setRetireInfo(
				retireBusiness.getRetireInfoByUserId(deadInfo.getUserId()));
		deadInfo.setOverall(
				iDynaBeanBusiness.findUniqueByParam("gh", deadInfo.getUserId()));
		return deadInfo;
	}

}
