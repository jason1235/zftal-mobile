package com.zfsoft.mobile.interest.service.impl;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.mobile.interest.dao.InterestDao;
import com.zfsoft.mobile.interest.entity.InterestComment;
import com.zfsoft.mobile.interest.entity.InterestCommentQuery;
import com.zfsoft.mobile.interest.entity.InterestEntity;
import com.zfsoft.mobile.interest.entity.InterestPost;
import com.zfsoft.mobile.interest.entity.InterestPostQuery;
import com.zfsoft.mobile.interest.entity.InterestQuery;
import com.zfsoft.mobile.interest.entity.InterestType;
import com.zfsoft.mobile.interest.entity.PersonInfo;
import com.zfsoft.mobile.interest.entity.PersonInfoQuery;
import com.zfsoft.mobile.interest.service.InterestService;
import com.zfsoft.mobile.market.entity.Market;

public class InterestServiceImpl implements InterestService{

	private InterestDao interestDao;
	
	@Override
	public List<InterestType> selectTypeList() {
		return interestDao.selectTypeList();
	}

	/*============================================================*/
	
	public InterestDao getInterestDao() {
		return interestDao;
	}

	public void setInterestDao(InterestDao interestDao) {
		this.interestDao = interestDao;
	}

	@Override
	public List<InterestEntity> selectInterestListByType(
			InterestQuery query) {
		PageList<InterestEntity> pageList = new PageList<InterestEntity>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(interestDao.selectInterestListCount(query));
			pageList.setPaginator(paginator);
			if((Integer)query.getToPage() > paginator.getPages()){
				return pageList;
			}
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<InterestEntity> list = interestDao.selectInterestListByType(query);
				pageList.addAll(list);
			} 
		}
		return pageList;
	}

	@Override
	public void insertPersonInfo(PersonInfo personInfo) {
		interestDao.insertPersonInfo(personInfo);
	}

	@Override
	public void insertInterest(InterestEntity interestEntity) {
		interestDao.insertInterest(interestEntity);
	}

	@Override
	public void insertPost(InterestPost interestPost) {
		interestDao.insertPost(interestPost);
	}

	@Override
	public List<InterestPost> getInterestPostList(InterestPostQuery query) {
		PageList<InterestPost> pageList = new PageList<InterestPost>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(interestDao.getInterestPostCount(query));
			pageList.setPaginator(paginator);
			if((Integer)query.getToPage() > paginator.getPages()){
				return pageList;
			}
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<InterestPost> list = interestDao.getInterestPostList(query);
				pageList.addAll(list);
			} 
		}
		return pageList;
	}

	@Override
	public InterestEntity getInterestById(String interestId) {
		return interestDao.getInterestById(interestId);
	}

	@Override
	public void updatePersoninfo(PersonInfo personInfo) {
		interestDao.updatePersoninfo(personInfo);
	}

	@Override
	public InterestPost getInterestPostById(String interestId) {
		return interestDao.getInterestPostById(interestId);
	}

	@Override
	public void updateInterestPost(InterestPost interestPost) {
		interestDao.updateInterestPost(interestPost);
	}

	@Override
	public void setPostNotTop(String interestId) {
		interestDao.setPostNotTop(interestId);
	}

	@Override
	public void setPostTop(String postId) {
		interestDao.setPostTop(postId);
	}

	@Override
	public void delPost(String postId, String userId) {
		interestDao.delPost(postId,userId);
	}

	@Override
	public void updateInterestById(InterestEntity interestEntity) {
		interestDao.updateInterestById(interestEntity);
	}

	@Override
	public List<PersonInfo> getPersonInfoList(PersonInfoQuery query) {
		PageList<PersonInfo> pageList = new PageList<PersonInfo>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(interestDao.selectpersonListCount(query));
			pageList.setPaginator(paginator);
			if((Integer)query.getToPage() > paginator.getPages()){
				return pageList;
			}
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<PersonInfo> list = interestDao.getPersonInfoList(query);
				pageList.addAll(list);
			} 
		}
		return pageList;
	}

	@Override
	public void addComment(InterestComment interestComment) {
		interestDao.addComment(interestComment);
	}

	@Override
	public List<InterestComment> getCommentList(String postId, String commentId) {
		return interestDao.getCommentList(postId,commentId);
	}

	@Override
	public List<InterestEntity> selectInterestList(InterestQuery query) {
		PageList<InterestEntity> list = new PageList<InterestEntity>();
		Paginator paginator = new Paginator();
		if (query != null) {
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage(query.getToPage());
			paginator.setItems(interestDao.selectInterestListCount(query));
			list.setPaginator(paginator);
			
			if (paginator.getBeginIndex() <= paginator.getItems()) {
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				list.addAll(interestDao.selectHtInterestList(query));
			}
		}
		return list;
	}

	@Override
	public List<InterestComment> gethtCommentList(
			InterestCommentQuery query) {
		PageList<InterestComment> list = new PageList<InterestComment>();
		Paginator paginator = new Paginator();
		if (query != null) {
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage(query.getToPage());
			paginator.setItems(interestDao.selectInterestCommentCount(query));
			list.setPaginator(paginator);
			
			if (paginator.getBeginIndex() <= paginator.getItems()) {
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				list.addAll(interestDao.selectInterestComment(query));
			}
		}
		return list;
	}

	@Override
	public void delInterestById(String interestId) {
		interestDao.delInterestById(interestId);
	}

	@Override
	public void delInterestPostById(String interestId) {
		interestDao.delInterestPostById(interestId);
	}

	@Override
	public void delInterestCommentById(String interestId) {
		interestDao.delInterestCommentById(interestId);
	}

	@Override
	public void exitInterest(PersonInfo personInfo) {
		interestDao.exitInterest(personInfo);
	}

	@Override
	public InterestEntity selectInterestInfo(InterestQuery interestQuery) {
		return interestDao.selectInterestInfo(interestQuery);
	}
}
