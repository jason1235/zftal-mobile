package com.zfsoft.mobile.group.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.SessionHolder;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.mobile.group.dao.IPushGroupDao;
import com.zfsoft.mobile.group.entity.PushGroup;
import com.zfsoft.mobile.group.entity.PushGroupMember;
import com.zfsoft.mobile.group.query.PushGroupQuery;
import com.zfsoft.mobile.group.service.IPushGroupService;
import com.zfsoft.util.base.StringUtil;

/**
 * 
 * @author ChenMinming
 * @date 2015-4-21
 * @version V1.0.0
 */
public class PushGroupServiceImpl implements IPushGroupService {
	private IPushGroupDao pushGroupDao;

	/**
	 * 设置
	 * @param pushGroupDao 
	 */
	public void setPushGroupDao(IPushGroupDao pushGroupDao) {
		this.pushGroupDao = pushGroupDao;
	}

	@Override
	public PageList<PushGroup> getPageList(PushGroupQuery query) {
		PageList<PushGroup> pageList = new PageList<PushGroup>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(pushGroupDao.getPagingCount(query));
			pageList.setPaginator(paginator); 
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<PushGroup> list = pushGroupDao.getPagingList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	@Override
	public PushGroup findById(String id) {
		return pushGroupDao.findById(id);
	}

	@Override
	public List<PushGroupMember> getPagedListWfpYh(PushGroupMember member) {
		return pushGroupDao.getPagedListWfpYh(member);
	}

	@Override
	public List<PushGroupMember> getPagedListYfpYh(PushGroupMember query) {
		/*PageList<PushGroupMember> pageList = new PageList<PushGroupMember>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(pushGroupDao.getPageCountReYfpYh(query));
			pageList.setPaginator(paginator); 
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<PushGroupMember> list = pushGroupDao.getPagedListReYfpYh(query);
				pageList.addAll(list);
			}
		}
		return pageList;*/
		
		return pushGroupDao.getPagedListYfpYh(query);
	}

	@Override
	public void save(PushGroup model) {
		if(StringUtil.isEmpty(model.getId())){
			model.setCreator(SessionFactory.getUser().getYhm());
			pushGroupDao.insert(model);
		}else{
			pushGroupDao.update(model);
		}
	}

	@Override
	public PushGroupMember deleteMember(PushGroupMember member) {
		List<PushGroupMember> memberList = pushGroupDao.getPagedListYfpYh(member);
		if(memberList==null||memberList.size()<1)return null;
		pushGroupDao.deleteMember(member);
		return memberList.get(0);
	}

	@Override
	public PushGroupMember insertMember(PushGroupMember member) {
		List<PushGroupMember> memberList = pushGroupDao.getPagedListByYhid(member);
		if(memberList==null||memberList.size()<1)return null;
		pushGroupDao.insertMember(member);
		return memberList.get(0);
	}

	@Override
	public void delete(PushGroup pushGroup) {
		PushGroupMember member = new PushGroupMember();
		member.setGroupId(pushGroup.getId());
		pushGroupDao.deleteMember(member);
		pushGroupDao.delete(pushGroup);
		
	}

	@Override
	public void insertAllMember(PushGroupMember member) {
		pushGroupDao.insertAllMember(member);
	}

	@Override
	public PageList<PushGroupMember> getPagedListReWfpYh(PushGroupMember query) {
		PageList<PushGroupMember> pageList = new PageList<PushGroupMember>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(pushGroupDao.getPagingCountReWfpYh(query));
			pageList.setPaginator(paginator); 
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<PushGroupMember> list = pushGroupDao.getPagingListReWfpYh(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	@Override
	public void insertUser(Map<String, Object> param) {
		pushGroupDao.insertUser(param);
	}

	@Override
	public void deleteUser(Map<String, Object> param) {
		pushGroupDao.deleteUser(param);
	}
}
