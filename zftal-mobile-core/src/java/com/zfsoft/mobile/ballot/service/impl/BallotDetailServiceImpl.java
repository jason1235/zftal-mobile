package com.zfsoft.mobile.ballot.service.impl;

import java.util.List;

import com.zfsoft.common.log.User;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.mobile.ballot.dao.BallotDetailDao;
import com.zfsoft.mobile.ballot.entity.Ballot;
import com.zfsoft.mobile.ballot.entity.BallotDetail;
import com.zfsoft.mobile.ballot.query.BallotDetailQuery;
import com.zfsoft.mobile.ballot.service.BallotDetailService;

public class BallotDetailServiceImpl implements BallotDetailService{
	
	private BallotDetailDao ballotDetailDao;

	public BallotDetailDao getBallotDetailDao() {
		return ballotDetailDao;
	}

	public void setBallotDetailDao(BallotDetailDao ballotDetailDao) {
		this.ballotDetailDao = ballotDetailDao;
	}

	@Override
	public List<BallotDetail> selectByBallotId(BallotDetailQuery query) {
		PageList<BallotDetail> list = new PageList<BallotDetail>();
		Paginator paginator = new Paginator();
		if (query != null) {
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage(query.getToPage());
			paginator.setItems(ballotDetailDao.count());
			list.setPaginator(paginator);
			
			if (paginator.getBeginIndex() <= paginator.getItems()) {
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				list.addAll(ballotDetailDao.selectByBallotId(query));
			}
		}
		return list;
	}

	@Override
	public BallotDetail findById(String id) {
		return ballotDetailDao.findById(id);
	}

	@Override
	public int updateBallotDetail(BallotDetail ballotDetail) {
		return ballotDetailDao.update(ballotDetail);
	}

	@Override
	public void deleteBallotDetail(String id) {
		ballotDetailDao.delete(id);
	}

	@Override
	public List<BallotDetail> selectRanking(BallotDetailQuery query) {
		
		PageList<BallotDetail> list = new PageList<BallotDetail>();
		Paginator paginator = new Paginator();
		if (query != null) {
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage(query.getToPage());
			paginator.setItems(ballotDetailDao.count());
			list.setPaginator(paginator);
			
			if (paginator.getBeginIndex() <= paginator.getItems()) {
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				list.addAll(ballotDetailDao.selectRanking(query));
			}
		}
		return list;
	}

	@Override
	public void createBallolDetail(BallotDetail ballotDetail) {
		ballotDetailDao.insert(ballotDetail);
	}

	@Override
	public void setjsxx() {
		List<User> users = ballotDetailDao.selectUsers();
		for (User user : users) {
			int a = ballotDetailDao.selectUserJs(user.getYhm());
			if(a == 0){
				ballotDetailDao.setUserJx(user.getYhm(),user.getYhlx());
			}
		}
	}

}
