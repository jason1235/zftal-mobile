package com.zfsoft.mobile.ballot.service;

import java.util.List;

import com.zfsoft.mobile.ballot.entity.Ballot;
import com.zfsoft.mobile.ballot.query.BallotQuery;

public interface BallotService {

	public int countBallot();

	public Ballot selectBallotById(String id);
	
	public Ballot findBallotById(String id);

	public List<Ballot> selectBallotList(BallotQuery query);
	
	int updateBallot(Ballot ballot);
	
	void createBallot(Ballot ballot);
	
	void deleteBallot(String id);
	
}
