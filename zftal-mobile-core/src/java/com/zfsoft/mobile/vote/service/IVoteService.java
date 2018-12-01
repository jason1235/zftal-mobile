package com.zfsoft.mobile.vote.service;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.vote.entity.QzEntity;
import com.zfsoft.mobile.vote.entity.VoteCountEntity;
import com.zfsoft.mobile.vote.entity.VoteGroupEntity;
import com.zfsoft.mobile.vote.entity.VoteMainEntity;
import com.zfsoft.mobile.vote.entity.VoteOptionEntity;
import com.zfsoft.mobile.vote.entity.VotePartInPersonEntity;
import com.zfsoft.mobile.vote.entity.VoteResultDetailEntity;
import com.zfsoft.mobile.vote.entity.VoteResultEntity;
import com.zfsoft.mobile.vote.query.VoteMainQuery;

public interface IVoteService {
	
	/**
	 * 获取投票列表
	 * @param query
	 * @return
	 */
	PageList<VoteMainEntity> getList(VoteMainQuery query);
	
	/**
	 * 获取投票数量
	 * @param query
	 * @return
	 */
	int getListCount(VoteMainQuery query);
	
	/**
	 * 插入投票
	 * @param query
	 * @return
	 */
	int insertVoteMain(VoteMainEntity voteMainEntity);
	
	/**
	 * 检查某个用户是否参与过某项投票
	 * @param params
	 * @return
	 */
	int checkHaveOrNotVote(Map<String,Object> params);
	
	/**
	 * 插入选项
	 * @param voteOptionEntity
	 * @return
	 */
	int insertVoteOption(VoteOptionEntity voteOptionEntity);
	
	/**
	 * 插入投票群组信息
	 * @param params
	 * @return
	 */
	int insertVoteGroup(Map<String,Object> params);
	
	/**
	 * 修改投票主体
	 * @param voteMainEntity
	 * @return
	 */
	int updateVoteMain(VoteMainEntity voteMainEntity);
	
	/**
	 * 获取某项投票的所有选项
	 * @param params
	 * @return
	 */
	List<VoteOptionEntity>  getOptionsByVoteId(Map<String,Object> params);
	
	/**
	 * 插入投票结果
	 * @param voteResultEntity
	 * @return
	 */
	int insertVoteResult(VoteResultEntity voteResultEntity);
	
	/**
	 * 获取投票详情
	 * @param params
	 */
	VoteMainEntity getVoteById(Map<String,Object> params);
	
	/**
	 * 统计某项投票结果
	 * @param params
	 * @return
	 */
	List<VoteCountEntity> getVoteResultCount(Map<String,Object> params);
	
	/**
	 * 获取某项投票总计投票次数
	 * @param params
	 * @return
	 */
	long getVoteResultTotalTimes(Map<String,Object> params);
	
	/**
	 * 获取某项投票参与人数
	 * @param params
	 * @return
	 */
	long getVoteResultTotalPartInPersons(Map<String,Object> params);
	
	/**
	 * 某项投票结果详情
	 * @param params
	 * @return
	 */
	List<VoteResultDetailEntity> getVoteResultDetail(Map<String,Object> params);
	
	/**
	 * 投票群组内参与人员
	 * @param params
	 * @return
	 */
	List<VotePartInPersonEntity> getVoteHavePartInPersons(Map<String,Object> params);
	
	/**
	 * 投票群组内未参与人员
	 * @param params
	 * @return
	 */
	List<VotePartInPersonEntity> getVoteHaveNotPartInPersons(Map<String,Object> params);
	
	/**
	 * 我参与的投票
	 * @param params
	 * @return
	 */
	 List<VoteMainEntity> getMyPartInList(VoteMainQuery query);
	 
	 /**
	  * 我参与的投票数量
	  * @param query
	  * @return
	  */
	 int getMyPartInListCount(VoteMainQuery query);
	 
	 /**
	  * 群组列表
	  * @return
	  */
	 List<QzEntity> getQzList();
	 
	 /**
	  * 以上都是给移动端的接口，以下都是移动后台的接口
	  */
	 
	 /**
	  * 移动后台投票列表
	  */
	 PageList<VoteMainEntity> getYdhtList(VoteMainQuery query);

	 /**
	  * 删除投票
	  * @param map
	  * @return
	  */
	 int deleteVoteByVoteId(Map<String,Object> map);
	 
	 /**
	  * 删除某项投票相关分组
	  * @param map
	  * @return
	  */
	 int deleteVoteGroupByVoteId(Map<String,Object> map);
	 
	 /**
	  * 删除某项投票相关选项
	  * @param map
	  * @return
	  */
	 int deleteVoteOptionsByVoteId(Map<String,Object> map);
	 
	 /**
	  * 删除某项投票相关结果
	  * @param map
	  * @return
	  */
	 int deleteVoteResultsByVoteId(Map<String,Object> map);
	 
	 /**
	  * 获取投票相关的分组
	  * @param map
	  * @return
	  */
	 VoteGroupEntity getVoteGroupById(Map<String,Object> map);
	 
	 /**
	  * 获取某个用户投过的某个选项
	  * @param map
	  * @return
	  */
	 VoteResultEntity getCheckedOptionByUserId(Map<String,Object> map);
}
