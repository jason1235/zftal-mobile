package com.zfsoft.mobile.ballot.service.impl;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.mobile.ballot.dao.BallotDao;
import com.zfsoft.mobile.ballot.entity.Ballot;
import com.zfsoft.mobile.ballot.query.BallotQuery;
import com.zfsoft.mobile.ballot.service.BallotService;

public class BallotServiceImpl implements BallotService{

	private BallotDao ballotDao;
	

	public BallotDao getBallotDao() {
		return ballotDao;
	}

	public void setBallotDao(BallotDao ballotDao) {
		this.ballotDao = ballotDao;
	}

	@Override
	public int countBallot() {
		return ballotDao.countBallot();
	}

	@Override
	public PageList<Ballot> selectBallotList(BallotQuery query) {
		PageList<Ballot> list = new PageList<Ballot>();
		Paginator paginator = new Paginator();
		if (query != null) {
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage(query.getToPage());
			paginator.setItems(ballotDao.countBallot());
			list.setPaginator(paginator);
			
			if (paginator.getBeginIndex() <= paginator.getItems()) {
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				list.addAll(ballotDao.selectBallotList(query));
			}
		}
		return list;
	}

	@Override
	public Ballot selectBallotById(String id) {
		return ballotDao.selectBallotById(id);
	}

	@Override
	public Ballot findBallotById(String id) {
		return ballotDao.findBallotById(id);
	}

	@Override
	public int updateBallot(Ballot ballot) {
		return ballotDao.update(ballot);
	}

	@Override
	public void createBallot(Ballot ballot) {
		ballotDao.insert(ballot);
	}

	@Override
	public void deleteBallot(String id) {
		ballotDao.delete(id);
	}
}
