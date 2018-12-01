package com.zfsoft.mobile.classCommunity.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.mobile.classCommunity.dao.IDynamicDao;
import com.zfsoft.mobile.classCommunity.entity.ClassEntity;
import com.zfsoft.mobile.classCommunity.entity.DynamicCommentEntity;
import com.zfsoft.mobile.classCommunity.entity.DynamicCommentReplyEntity;
import com.zfsoft.mobile.classCommunity.entity.DynamicEntity;
import com.zfsoft.mobile.classCommunity.entity.DynamicFileEntity;
import com.zfsoft.mobile.classCommunity.entity.DynamicFileEntityForInsert;
import com.zfsoft.mobile.classCommunity.query.ClassEntityQuery;
import com.zfsoft.mobile.classCommunity.query.DynamicEntityQuery;
import com.zfsoft.mobile.classCommunity.service.IDynamicService;

/**
 * 动态service实现
 * @author H110MF
 *
 */
public class DynamicServiceImpl implements IDynamicService{
	private IDynamicDao dynamicDao;

	/**
	 * 获取班级分页列表
	 * @param query
	 * @return
	 */
	public List<ClassEntity> getClassList(ClassEntityQuery query){
		/*PageList<ClassEntity> pageList = new PageList<ClassEntity>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(dynamicDao.getClassListCount(query));
			pageList.setPaginator(paginator);
			if((Integer)query.getToPage() > paginator.getPages()){
				return pageList;
			}
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<ClassEntity> list = dynamicDao.getClassList(query);
				pageList.addAll(list);
			} 
		}
		return pageList;*/
		return dynamicDao.getClassList(query);
	}
	
	/**
     * 我可加入的班级圈子列表
     * @param query
     * @return
     */
	public List<ClassEntity> getMyWantClassList(ClassEntityQuery query){
		return dynamicDao.getMyWantClassList(query);
	}
	
	/**
	 * 班级总数
	 * @param query
	 * @return
	 */
	public int getClassListCount(ClassEntityQuery query){
		return dynamicDao.getClassListCount(query);
	}
	
	/**
	 * 班级圈子帖子总数
	 * @param classId
	 * @return
	 */
	public int getClassDynamicCount(String classId){
		return dynamicDao.getClassDynamicCount(classId);
	}
	
	/**
	 * 班级圈子成员总数
	 * @param classId
	 * @return
	 */
	public int getClassMemberCount(String classId){
		return dynamicDao.getClassMemberCount(classId);
	}

	/**
	 * 帖子分页列表
	 * @param dynamicEntityQuery
	 * @return
	 */
	public PageList<DynamicEntity> getDynamicPageList(DynamicEntityQuery query){
		PageList<DynamicEntity> pageList = new PageList<DynamicEntity>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(dynamicDao.getDynamicListCount(query));
			pageList.setPaginator(paginator);
			if((Integer)query.getToPage() > paginator.getPages()){
				return pageList;
			}
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<DynamicEntity> list = dynamicDao.getDynamicPageList(query);
				
				//1遍历查询出每一个帖子的评论列表
				//2遍历查询出每一个评论的回复列表
				for (int i = 0; i < list.size(); i++) {
					if(list.get(i) != null){
						String dynamicId = list.get(i).getId();
						List<DynamicFileEntity> pics = dynamicDao.getPictureList(dynamicId);
						if(pics == null){
							pics = new ArrayList<DynamicFileEntity>();
						}
						list.get(i).setPictureList(pics);
						
						List<DynamicCommentEntity> comments = dynamicDao.getCommentList(dynamicId);
						if(comments != null){
							for (int j = 0; j < comments.size(); j++) {
								if(comments.get(j)!=null){
									String commentId = comments.get(j).getId();
									List<DynamicCommentReplyEntity> replys = dynamicDao.getReplyList(commentId);
									if(replys == null){
										replys = new ArrayList<DynamicCommentReplyEntity>();
									}
									comments.get(j).setReplyList(replys);
								}
							}							
						}else{
							comments = new ArrayList<DynamicCommentEntity>();
						}
						list.get(i).setCommentList(comments);
					}
				}
				pageList.addAll(list);
			} 
		}
		return pageList;
	}
	
	/**
	 * 班级圈子帖子总数
	 */
	@Override
	public int getDynamicListCount(DynamicEntityQuery dynamicEntityQuery) {
		return dynamicDao.getDynamicListCount(dynamicEntityQuery);
	}
	
	/**
	 * 图片列表
	 * @param dynamicId
	 * @return
	 */
	public List<DynamicFileEntity> getPictureList(@Param("dynamicId") String dynamicId){
		return dynamicDao.getPictureList(dynamicId);
	}
	
	/**
	 * 评论列表
	 * @param dynamicId
	 * @return
	 */
	public List<DynamicCommentEntity> getCommentList(@Param("dynamicId") String dynamicId){
		return dynamicDao.getCommentList(dynamicId);
	}
	
	/**
	 * 回复列表
	 * @param commentId
	 * @return
	 */
	public List<DynamicCommentReplyEntity> getReplyList(@Param("commentId") String commentId){
		return dynamicDao.getReplyList(commentId);
	}
	
	/**
	 * 新增帖子
	 * @param dynamicEntity
	 * @return
	 */
	public int insertDynamic(DynamicEntity dynamicEntity){
		return dynamicDao.insertDynamic(dynamicEntity);
	}
	
	/**
	 * 新增帖子图片
	 * @param dynamicFileEntity
	 * @return
	 */
	public int insertDynamicPicture(DynamicFileEntityForInsert dynamicFileEntity){
		return dynamicDao.insertDynamicPicture(dynamicFileEntity);
	}
	
	/**
	 * 新增评论
	 * @param dynamicCommentEntity
	 * @return
	 */
	public int insertDynamicComment(DynamicCommentEntity dynamicCommentEntity){
		return dynamicDao.insertDynamicComment(dynamicCommentEntity);
	}
	
	/**
	 * 新增回复
	 * @param dynamicCommentReplyEntity
	 * @return
	 */
	public int insertDynamicCommentReply(DynamicCommentReplyEntity dynamicCommentReplyEntity){
		return dynamicDao.insertDynamicCommentReply(dynamicCommentReplyEntity);
	}
	
	/**
	 * 点赞
	 * @param dynamicId
	 * @return
	 */
	public int updatePraiseCount(@Param("dynamicId")String dynamicId){
		return dynamicDao.updatePraiseCount(dynamicId);
	}
	
	/**
	 * 查询某个账号是否点赞过某个帖子
	 * @param params
	 * @return
	 */
	public int getUserPraiseRecordCount(Map<String,String> params){
		return dynamicDao.getUserPraiseRecordCount(params);
	}
	
	/**
	 * 新增帖子点赞记录
	 * @param params
	 * @return
	 */
	public int insertUserPraiseRecord(Map<String,String> params){
		return dynamicDao.insertUserPraiseRecord(params);
	}
	
	public IDynamicDao getDynamicDao() {
		return dynamicDao;
	}

	public void setDynamicDao(IDynamicDao dynamicDao) {
		this.dynamicDao = dynamicDao;
	}

}
