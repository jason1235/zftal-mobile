package com.zfsoft.mobile.interest.service;

import java.util.List;

import com.zfsoft.mobile.interest.entity.InterestComment;
import com.zfsoft.mobile.interest.entity.InterestCommentQuery;
import com.zfsoft.mobile.interest.entity.InterestEntity;
import com.zfsoft.mobile.interest.entity.InterestPost;
import com.zfsoft.mobile.interest.entity.InterestPostQuery;
import com.zfsoft.mobile.interest.entity.InterestQuery;
import com.zfsoft.mobile.interest.entity.InterestType;
import com.zfsoft.mobile.interest.entity.PersonInfo;
import com.zfsoft.mobile.interest.entity.PersonInfoQuery;

public interface InterestService {

	List<InterestType> selectTypeList();

	List<InterestEntity> selectInterestListByType(InterestQuery interestQuery);

	void insertPersonInfo(PersonInfo personInfo);

	void insertInterest(InterestEntity interestEntity);

	void insertPost(InterestPost interestPost);

	List<InterestPost> getInterestPostList(InterestPostQuery interestPostQuery);

	InterestEntity getInterestById(String interestId);

	void updatePersoninfo(PersonInfo personInfo);

	InterestPost getInterestPostById(String interestId);

	void updateInterestPost(InterestPost interestPost);

	void setPostNotTop(String interestId);

	void setPostTop(String postId);

	void delPost(String postId, String userId);

	void updateInterestById(InterestEntity interestEntity);

	List<PersonInfo> getPersonInfoList(PersonInfoQuery query);

	void addComment(InterestComment interestComment);

	List<InterestComment> getCommentList(String postId, String commentId);

	List<InterestEntity> selectInterestList(InterestQuery interestQuery);

	List<InterestComment> gethtCommentList(
			InterestCommentQuery interestCommentQuery);

	void delInterestById(String interestId);

	void delInterestPostById(String interestId);

	void delInterestCommentById(String interestId);

	void exitInterest(PersonInfo personInfo);

	InterestEntity selectInterestInfo(InterestQuery interestQuery);




}
