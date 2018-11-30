package com.zfsoft.mobile.ballot.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zfsoft.mobile.ballot.entity.BallotDetail;
import com.zfsoft.mobile.ballot.query.BallotDetailQuery;

public interface BallotDetailService {

	List<BallotDetail> selectByBallotId(BallotDetailQuery query);

	BallotDetail findById(String id);

	int updateBallotDetail(BallotDetail ballotDetail); 
	
	void deleteBallotDetail(String id);

	List<BallotDetail> selectRanking(BallotDetailQuery query);
	
	void createBallolDetail(BallotDetail ballotDetail);

	void setjsxx();
}
