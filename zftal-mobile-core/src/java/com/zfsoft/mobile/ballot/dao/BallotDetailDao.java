package com.zfsoft.mobile.ballot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zfsoft.common.log.User;
import com.zfsoft.mobile.ballot.entity.Ballot;
import com.zfsoft.mobile.ballot.entity.BallotDetail;
import com.zfsoft.mobile.ballot.query.BallotDetailQuery;

public interface BallotDetailDao {

	List<BallotDetail> selectByBallotId(BallotDetailQuery query);

	void insert(BallotDetail ballotDetail);
	
	int update(BallotDetail ballotDetail);
	
	void delete(@Param("id")String id);

	BallotDetail findById(@Param("id")String id);

	int count();

	List<BallotDetail> selectRanking(BallotDetailQuery query);

	List<User> selectUsers();

	int selectUserJs(@Param("yhm") String yhm);

	void setUserJx(@Param("yhm") String yhm,@Param("yhlx")  String yhlx);
	
}
