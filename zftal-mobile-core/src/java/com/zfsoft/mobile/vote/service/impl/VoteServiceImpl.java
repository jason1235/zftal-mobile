package com.zfsoft.mobile.vote.service.impl;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.mobile.services.entity.TwoExamPaperEntity;
import com.zfsoft.mobile.vote.dao.IVoteDao;
import com.zfsoft.mobile.vote.entity.QzEntity;
import com.zfsoft.mobile.vote.entity.VoteCountEntity;
import com.zfsoft.mobile.vote.entity.VoteGroupEntity;
import com.zfsoft.mobile.vote.entity.VoteMainEntity;
import com.zfsoft.mobile.vote.entity.VoteOptionEntity;
import com.zfsoft.mobile.vote.entity.VotePartInPersonEntity;
import com.zfsoft.mobile.vote.entity.VoteResultDetailEntity;
import com.zfsoft.mobile.vote.entity.VoteResultEntity;
import com.zfsoft.mobile.vote.query.VoteMainQuery;
import com.zfsoft.mobile.vote.service.IVoteService;


public class VoteServiceImpl implements IVoteService{
	private IVoteDao voteDao;
	
	/**
	 * 获取投票列表
	 * @param query
	 * @return
	 */
	 @Override
	public PageList<VoteMainEntity> getList(VoteMainQuery query){
		 PageList<VoteMainEntity> pageList = new PageList<VoteMainEntity>();
			Paginator paginator = new Paginator();
			if(query!=null){
				paginator.setItemsPerPage(query.getPerPageSize());
				paginator.setPage((Integer)query.getToPage());
				paginator.setItems(voteDao.getListCount(query));
				pageList.setPaginator(paginator);
				if((Integer)query.getToPage() > paginator.getPages()){
					return pageList;
				}
				if(paginator.getBeginIndex() <= paginator.getItems()){
					query.setStartRow(paginator.getBeginIndex());
					query.setEndRow(paginator.getEndIndex());
					List<VoteMainEntity> list = voteDao.getList(query);
					pageList.addAll(list);
				}
			}
			return pageList;
	 }
	
	/**
	 * 获取投票数量
	 * @param query
	 * @return
	 */
	@Override
	public int getListCount(VoteMainQuery query){
		return voteDao.getListCount(query);
	}

	
	/**
	 * 插入投票
	 * @param query
	 * @return
	 */
	@Override
	public int insertVoteMain(VoteMainEntity voteMainEntity){
		return voteDao.insertVoteMain(voteMainEntity);
	}
	
	/**
	 * 检查某个用户是否参与过某项投票
	 * @param params
	 * @return
	 */
	public int checkHaveOrNotVote(Map<String,Object> params){
		return voteDao.checkHaveOrNotVote(params);
	}
	
	/**
	 * 插入选项
	 * @param voteOptionEntity
	 * @return
	 */
	@Override
	public int insertVoteOption(VoteOptionEntity voteOptionEntity){
		return voteDao.insertVoteOption(voteOptionEntity);
	}
	
	/**
	 * 插入投票群组信息
	 * @param params
	 * @return
	 */
	public int insertVoteGroup(Map<String,Object> params){
		return voteDao.insertVoteGroup(params);
	}
	
	/**
	 * 修改投票主体
	 * @param voteMainEntity
	 * @return
	 */
	public int updateVoteMain(VoteMainEntity voteMainEntity){
		return voteDao.updateVoteMain(voteMainEntity);
	}
	
	/**
	 * 获取某项投票的所有选项
	 * @param params
	 * @return
	 */
	public List<VoteOptionEntity>  getOptionsByVoteId(Map<String,Object> params){
		return voteDao.getOptionsByVoteId(params);
	}
	
	/**
	 * 插入投票结果
	 * @param voteResultEntity
	 * @return
	 */
	public int insertVoteResult(VoteResultEntity voteResultEntity){
		return voteDao.insertVoteResult(voteResultEntity);
	}
	
	/**
	 * 获取投票详情
	 * @param params
	 */
	public VoteMainEntity getVoteById(Map<String,Object> params){
		return voteDao.getVoteById(params);
	}
	
	/**
	 * 统计某项投票结果
	 * @param params
	 * @return
	 */
	public List<VoteCountEntity> getVoteResultCount(Map<String,Object> params){
		return voteDao.getVoteResultCount(params);
	}
	
	/**
	 * 获取某项投票总计投票次数
	 * @param params
	 * @return
	 */
	public long getVoteResultTotalTimes(Map<String,Object> params){
		return voteDao.getVoteResultTotalTimes(params);
	}
	
	
	/**
	 * 获取某项投票参与人数
	 * @param params
	 * @return
	 */
	public long getVoteResultTotalPartInPersons(Map<String,Object> params){
		return voteDao.getVoteResultTotalPartInPersons(params);
	}
	
	/**
	 * 某项投票结果详情
	 * @param params
	 * @return
	*/
	public List<VoteResultDetailEntity> getVoteResultDetail(Map<String,Object> params){
		return voteDao.getVoteResultDetail(params);
	}
	
	
	/**
	 * 投票群组内参与人员
	 * @param params
	 * @return
	 */
	public List<VotePartInPersonEntity> getVoteHavePartInPersons(Map<String,Object> params){
		return voteDao.getVoteHavePartInPersons(params);
	}
	
	/**
	 * 投票群组内未参与人员
	 * @param params
	 * @return
	 */
	public List<VotePartInPersonEntity> getVoteHaveNotPartInPersons(Map<String,Object> params){
		return voteDao.getVoteHaveNotPartInPersons(params);
	}
	
	/**
	 * 我参与的投票
	 * @param params
	 * @return
	 */
	 public List<VoteMainEntity> getMyPartInList(VoteMainQuery query){
		 PageList<VoteMainEntity> pageList = new PageList<VoteMainEntity>();
			Paginator paginator = new Paginator();
			if(query!=null){
				paginator.setItemsPerPage(query.getPerPageSize());
				paginator.setPage((Integer)query.getToPage());
				paginator.setItems(voteDao.getMyPartInListCount(query));
				pageList.setPaginator(paginator);
				if((Integer)query.getToPage() > paginator.getPages()){
					return pageList;
				}
				if(paginator.getBeginIndex() <= paginator.getItems()){
					query.setStartRow(paginator.getBeginIndex());
					query.setEndRow(paginator.getEndIndex());
					List<VoteMainEntity> list = voteDao.getMyPartInList(query);
					pageList.addAll(list);
				}
			}
			return pageList;
	 }
	 
	 /**
	  * 我参与的投票数量
	  * @param query
	  * @return
	  */
	 public int getMyPartInListCount(VoteMainQuery query){
		 return voteDao.getMyPartInListCount(query);
	 }
	 
	 /**
	  * 群组列表
	  * @return
	  */
	 public List<QzEntity> getQzList(){
		 return voteDao.getQzList();
	 }
	 
	/**
	  * 以上都是给移动端的接口，以下都是移动后台的接口
	  */
	 
	/**
	  * 移动后台投票列表
	  */
	public PageList<VoteMainEntity> getYdhtList(VoteMainQuery query){
	    PageList<VoteMainEntity> pageList = new PageList<VoteMainEntity>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(voteDao.getYdhtListCount(query));
			pageList.setPaginator(paginator);
			if((Integer)query.getToPage() > paginator.getPages()){
				return pageList;
			}
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<VoteMainEntity> list = voteDao.getYdhtList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}
	
	 /**
	  * 删除投票
	  * @param map
	  * @return
	  */
	 public int deleteVoteByVoteId(Map<String,Object> map){
		 //删除投票、删除相关选项、删除相关分组、删除相关投票结果
		 int rows = voteDao.deleteVoteByVoteId(map);
		 
		 if(rows>0){
			 voteDao.deleteVoteOptionsByVoteId(map);
			 voteDao.deleteVoteGroupByVoteId(map);
			 voteDao.deleteVoteResultsByVoteId(map);			 
		 }
		 return rows;
	 }
	 
	 /**
	  * 删除某项投票相关分组
	  * @param map
	  * @return
	  */
	 public int deleteVoteGroupByVoteId(Map<String,Object> map){
		 return voteDao.deleteVoteGroupByVoteId(map);
	 }
	 
	 /**
	  * 删除某项投票相关选项
	  * @param map
	  * @return
	  */
	 public int deleteVoteOptionsByVoteId(Map<String,Object> map){
		 return voteDao.deleteVoteOptionsByVoteId(map);
	 }
	 
	 /**
	  * 删除某项投票相关结果
	  * @param map
	  * @return
	  */
	 public int deleteVoteResultsByVoteId(Map<String,Object> map){
		 return voteDao.deleteVoteResultsByVoteId(map);
	 }
	 
	 /**
	  * 获取投票相关的分组
	  * @param map
	  * @return
	  */
	 public VoteGroupEntity getVoteGroupById(Map<String,Object> map){
		 return voteDao.getVoteGroupById(map);
	 }
	 
	 /**
	  * 获取某个用户投过的某个选项
	  * @param map
	  * @return
	  */
	 public VoteResultEntity getCheckedOptionByUserId(Map<String,Object> map){
		 return voteDao.getCheckedOptionByUserId(map);
	 }
	
	public IVoteDao getVoteDao() {
		return voteDao;
	}

	public void setVoteDao(IVoteDao voteDao) {
		this.voteDao = voteDao;
	}
}
