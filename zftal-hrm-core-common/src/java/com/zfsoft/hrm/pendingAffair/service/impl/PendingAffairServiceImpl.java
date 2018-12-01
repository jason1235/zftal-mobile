package com.zfsoft.hrm.pendingAffair.service.impl;

import java.util.List;
import java.util.Map;

import com.zfsoft.common.log.User;
import com.zfsoft.dataprivilege.util.DeptFilterUtil;
import com.zfsoft.hrm.pendingAffair.dao.IPendingAffairInfoDao;
import com.zfsoft.hrm.pendingAffair.entities.PendingAffairInfo;
import com.zfsoft.hrm.pendingAffair.enums.PendingAffairBusinessTypeEnum;
import com.zfsoft.hrm.pendingAffair.service.svcinterface.IPendingAffairService;
import com.zfsoft.util.base.StringUtil;

public class PendingAffairServiceImpl implements IPendingAffairService {
	private IPendingAffairInfoDao pendingAffairInfoDao;

	public void setPendingAffairInfoDao(IPendingAffairInfoDao pendingAffairInfoDao) {
		this.pendingAffairInfoDao = pendingAffairInfoDao;
	}

	public List<PendingAffairInfo> getListByUser(User user) {
		PendingAffairInfo query = new PendingAffairInfo();
		query.setUserId(user.getYhm());
		return this.getNewList(pendingAffairInfoDao.findByUserId(query));
	}

	@Override
	public List<PendingAffairInfo> getListByRoles(User user) {
		PendingAffairInfo query = new PendingAffairInfo();
		query.setQueryRoleIds(user.getJsdms());
		String expression = DeptFilterUtil.getCondition("t", "bm_id");
		if(expression!=null&&!expression.trim().isEmpty()){
			query.setExpression(expression);
		}
		return this.getNewList(pendingAffairInfoDao.findByRoleIds(query));
	}

	/**
	 * 
	 * 方法描述：重新组装LIST
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * @since: 2013-5-13 上午11:10:14
	 */
	private List<PendingAffairInfo> getNewList(List<PendingAffairInfo> list) {
		Map<String, String> typeMap = PendingAffairBusinessTypeEnum.getMap();
		if (list != null && list.size() > 0) {
			for (PendingAffairInfo pa : list) {
				if (pa != null && StringUtil.isNotEmpty(pa.getAffairType())
						&& StringUtil.isNotEmpty(typeMap.get(pa.getAffairType()))) {
					pa.setAffairName(typeMap.get(pa.getAffairType()).replace("@@", String.valueOf(pa.getSumNumber())));
				}
			}
		}
		return list;
	}

	@Override
	public List<PendingAffairInfo> getListByQuery(PendingAffairInfo query) {
		return pendingAffairInfoDao.findList(query);
	}

	@Override
	public PendingAffairInfo getById(String id) {
		PendingAffairInfo query = new PendingAffairInfo();
		query.setId(id);
		return pendingAffairInfoDao.findById(query);
	}

	@Override
	public void addPendingAffairInfo(PendingAffairInfo entity) {
		pendingAffairInfoDao.insert(entity);
	}

	@Override
	public void modifyPendingAffairInfo(PendingAffairInfo pendingAffairInfo) {
		pendingAffairInfoDao.update(pendingAffairInfo);
	}

	@Override
	public void modifyByYwId(PendingAffairInfo pendingAffairInfo) {
		pendingAffairInfoDao.modifyByYwId(pendingAffairInfo);
	}

	@Override
	public void removePendingAffairInfo(String id) {
		PendingAffairInfo query = getById(id);
		pendingAffairInfoDao.delete(query);
	}

	@Override
	public void removePendingAffairInfoByBid(String bid) {
		PendingAffairInfo query = new PendingAffairInfo();
		query.setBusinessId(bid);
		pendingAffairInfoDao.delete(query);
	}
}
