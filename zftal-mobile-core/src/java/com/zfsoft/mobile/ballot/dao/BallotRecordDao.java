package com.zfsoft.mobile.ballot.dao;

import org.apache.ibatis.annotations.Param;

import com.zfsoft.mobile.ballot.entity.BallotRecord;

public interface BallotRecordDao {

	void insert(BallotRecord ballotRecord);
	
	int update(BallotRecord ballotRecord);
	
	void delete(@Param("userId") String userId,@Param("ballotDetailId") String ballotDetailId);

	int countBallotNum(@Param("userId") String userId, @Param("ballotDetailId") String ballotDetailId, @Param("ballotTime") String ballotTime);

	int countBallotNumByBallotId(@Param("userId") String userId,  @Param("ballotId") String ballotId,
			 @Param("ballotTime") String ballotTime);
	
}
