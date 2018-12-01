package com.zfsoft.mobile.interest.dao;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zfsoft.mobile.interest.entity.InterestComment;
import com.zfsoft.mobile.interest.entity.InterestCommentQuery;
import com.zfsoft.mobile.interest.entity.InterestEntity;
import com.zfsoft.mobile.interest.entity.InterestPost;
import com.zfsoft.mobile.interest.entity.InterestPostQuery;
import com.zfsoft.mobile.interest.entity.InterestQuery;
import com.zfsoft.mobile.interest.entity.InterestType;
import com.zfsoft.mobile.interest.entity.PersonInfo;
import com.zfsoft.mobile.interest.entity.PersonInfoQuery;

public interface InterestDao {

	List<InterestType> selectTypeList();

	List<InterestEntity> selectInterestListByType(InterestQuery interestQuery);

	void insertPersonInfo(PersonInfo personInfo);

	void insertInterest(InterestEntity interestEntity);

	int selectInterestListCount(InterestQuery query);

	void insertPost(InterestPost interestPost);

	int getInterestPostCount(InterestPostQuery query);

	List<InterestPost> getInterestPostList(InterestPostQuery query);

	InterestEntity getInterestById(@Param("interestId")String interestId);

	void updatePersoninfo(PersonInfo personInfo);

	InterestPost getInterestPostById(@Param("interestId")String interestId);

	void updateInterestPost(InterestPost interestPost);

	void setPostNotTop(@Param("interestId")String interestId);

	void setPostTop(@Param("postId")String postId);

	void delPost(@Param("postId")String postId, @Param("userId")String userId);

	void updateInterestById(InterestEntity interestEntity);

	int selectpersonListCount(PersonInfoQuery query);

	List<PersonInfo> getPersonInfoList(PersonInfoQuery query);

	void addComment(InterestComment interestComment);

	List<InterestComment> getCommentList(@Param("postId")String postId, @Param("commentId")String commentId);

	int selectInterestCommentCount(InterestCommentQuery query);

	List<InterestComment> selectInterestComment(InterestCommentQuery query);

	void delInterestById(@Param("interestId")String interestId);

	void delInterestPostById(@Param("interestId")String interestId);

	void delInterestCommentById(@Param("interestId")String interestId);

	List<InterestEntity> selectHtInterestList(InterestQuery query);

	void exitInterest(PersonInfo personInfo);

	InterestEntity selectInterestInfo(InterestQuery interestQuery);

}
