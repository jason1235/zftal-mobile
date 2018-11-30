package com.zfsoft.mobile.ballot.service;

import java.util.List;

import com.zfsoft.mobile.ballot.entity.Ballot;
import com.zfsoft.mobile.ballot.entity.BallotRecord;
import com.zfsoft.mobile.ballot.query.BallotQuery;

public interface BallotRecordService {

	int countBallotNum(String userId,String ballotDetailId,String ballotTime);
	
	int countBallotNumByBallotId(String userId,String ballotId,String ballotTime);
	
	void createBallotRecord(BallotRecord ballotRecord);
	
	void deleteBallotRecord(String userId,String ballotDetailId);
}
