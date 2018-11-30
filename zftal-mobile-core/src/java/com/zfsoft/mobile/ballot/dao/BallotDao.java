package com.zfsoft.mobile.ballot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zfsoft.mobile.ballot.entity.Ballot;
import com.zfsoft.mobile.ballot.query.BallotQuery;

public interface BallotDao {

	int countBallot();

	List<Ballot> selectBallotList(BallotQuery query);

	Ballot selectBallotById(@Param(value = "id") String id);
	
	Ballot findBallotById(@Param(value = "id") String id);

	void insert(Ballot ballot);
	
	int update(Ballot ballot);
	
	void delete(@Param("id")String id);
	
}
