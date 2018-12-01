package com.zfsoft.mobile.ballot.service.impl;

import java.util.List;

import com.zfsoft.mobile.ballot.dao.BallotDao;
import com.zfsoft.mobile.ballot.dao.BallotRecordDao;
import com.zfsoft.mobile.ballot.entity.Ballot;
import com.zfsoft.mobile.ballot.entity.BallotRecord;
import com.zfsoft.mobile.ballot.query.BallotQuery;
import com.zfsoft.mobile.ballot.service.BallotRecordService;
import com.zfsoft.mobile.ballot.service.BallotService;

public class BallotRecordServiceImpl implements BallotRecordService{

	private BallotRecordDao ballotRecordDao;

	public BallotRecordDao getBallotRecordDao() {
		return ballotRecordDao;
	}

	public void setBallotRecordDao(BallotRecordDao ballotRecordDao) {
		this.ballotRecordDao = ballotRecordDao;
	}

	@Override
	public int countBallotNum(String userId, String ballotDetailId,
			String ballotTime) {
		return ballotRecordDao.countBallotNum(userId,ballotDetailId,ballotTime);
	}

	@Override
	public void createBallotRecord(BallotRecord ballotRecord) {
		ballotRecordDao.insert(ballotRecord);		
	}

	@Override
	public void deleteBallotRecord(String userId, String ballotDetailId) {
		ballotRecordDao.delete(userId, ballotDetailId);
	}

	@Override
	public int countBallotNumByBallotId(String userId, String ballotId,
			String ballotTime) {
		return ballotRecordDao.countBallotNumByBallotId(userId,ballotId,ballotTime);
	}
	
	
	
}
