package com.zfsoft.hrm.manoeuvre.manoeuvreInfo.business.impl;

import java.util.List;

import org.springframework.util.Assert;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.manoeuvre.manoeuvreInfo.business.businessinterface.IManoeuvreBusiness;
import com.zfsoft.hrm.manoeuvre.manoeuvreInfo.dao.daointerface.IManoeuvreDao;
import com.zfsoft.hrm.manoeuvre.manoeuvreInfo.entities.ManoeuvreInfo;
import com.zfsoft.hrm.manoeuvre.manoeuvreInfo.query.ManoeuvreQuery;
import com.zfsoft.util.base.StringUtil;

public class ManoeuvreBusinessImpl implements IManoeuvreBusiness {
	
	private IManoeuvreDao manoeuvreDao;
	
	public void setManoeuvreDao(IManoeuvreDao manoeuvreDao) {
		this.manoeuvreDao = manoeuvreDao;
	}
	
	//------------------------------------------------------------------------

	@Override
	public boolean add(ManoeuvreInfo info) {
		Assert.notNull(info, "新增人员调配信息不可为空");
		return manoeuvreDao.insert(info) > 0;
	}
	
	@Override
	public boolean modify(ManoeuvreInfo info) {
		Assert.notNull(info, "修改人员调配信息不可为空");
		return manoeuvreDao.update(info) > 0;
	}

	@Override
	public boolean modifyCurrentTask(ManoeuvreInfo info) {
		Assert.notNull(info, "人员调配信息不可为空");
		return manoeuvreDao.updateTask(info) > 0;
	}

	@Override
	public void remove(String id) {
		Assert.isTrue(!StringUtil.isEmpty(id), "未选定任何记录");
		manoeuvreDao.delete(id);
	}

	@Override
	public List<ManoeuvreInfo> getList(ManoeuvreQuery query) {
		Assert.notNull(query, "人员调配查询信息不可为空");
		return manoeuvreDao.findList(query);
	}

	@Override
	public ManoeuvreInfo getById(String id) {
		Assert.isTrue(!StringUtil.isEmpty(id), "未选定任何记录");
		return manoeuvreDao.findById(id);
	}

	@Override
	public PageList<ManoeuvreInfo> getPagingList(ManoeuvreQuery query) {
		PageList<ManoeuvreInfo> pageList = new PageList<ManoeuvreInfo>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			
			paginator.setItems(manoeuvreDao.findPagingCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				pageList.addAll(manoeuvreDao.findPagingList(query));
			}
		}	
		
		return pageList;
	}
	
	@Override
	public boolean existByNode(String nid) {
		return manoeuvreDao.existByNode(nid) > 0 ? true : false;
	}

	@Override
	public boolean save(ManoeuvreInfo info) {
		Assert.notNull(info, "新增人员调配信息不可为空");
		return manoeuvreDao.save(info) > 0;
	}
}
