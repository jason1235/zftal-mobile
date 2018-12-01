package com.zfsoft.hrm.expertvote.vote.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.expertvote.vote.dao.IGroupMemberDao;
import com.zfsoft.hrm.expertvote.vote.entity.GroupMember;
import com.zfsoft.hrm.expertvote.vote.query.GroupMemberQuery;
import com.zfsoft.hrm.expertvote.vote.service.IGroupMemberService;

public class GroupMemberServiceImpl implements IGroupMemberService {
	private IGroupMemberDao groupMemberDao;

	@Override
	public void insert(GroupMember groupMember) {
		groupMemberDao.insert(groupMember);
	}

	public IGroupMemberDao getGroupMemberDao() {
		return groupMemberDao;
	}

	public void setGroupMemberDao(IGroupMemberDao groupMemberDao) {
		this.groupMemberDao = groupMemberDao;
	}

	@Override
	public PageList<GroupMemberQuery> getPagedList(
			GroupMemberQuery groupMemberQuery) {
		PageList<GroupMemberQuery> pageList = new PageList<GroupMemberQuery>();
		pageList.addAll(groupMemberDao.getPagedList(groupMemberQuery));
		Paginator paginator = new Paginator();
		paginator.setItemsPerPage(groupMemberQuery.getShowCount());
		paginator.setPage(groupMemberQuery.getCurrentPage());
		paginator.setItems(groupMemberQuery.getTotalResult());
		pageList.setPaginator(paginator);
		return pageList;
	}

	@Override
	public List<String> getRandomExpert(List<String> sList, int count) {
		// 创建一个长度为count(count<=list)的数组,用于存随机数
		int[] a = new int[count];
		// 利于此数组产生随机数
		int[] b = new int[sList.size()];
		int size = sList.size();

		// 取样填充至数组a满
		for (int i = 0; i < count; i++) {
			int num = (int) (Math.random() * size); // [0,size)
			int where = -1;
			for (int j = 0; j < b.length; j++) {
				if (b[j] != -1) {
					where++;
					if (where == num) {
						b[j] = -1;
						a[i] = j;
					}
				}
			}
			size = size - 1;
		}
		// a填满后 将数据加载到rslist
		List<String> rslist = new ArrayList<String>();
		for (int i = 0; i < count; i++) {
			String sf = (String) sList.get(a[i]);
			rslist.add(sf);
		}
		return rslist;
	}

	@Override
	public void remove(String id) {
		groupMemberDao.remove(id);
	}

	@Override
	public int getZj_id(String id,String zjz_id) {
		return groupMemberDao.getZj_id(id,zjz_id);
	}

	@Override
	public int getMemberCount(String id) {
		return groupMemberDao.getMemberCount(id);
	}
}
