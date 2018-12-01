package com.zfsoft.mobile.classCommunity.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zfsoft.mobile.classCommunity.entity.ClassEntity;
import com.zfsoft.mobile.classCommunity.entity.DynamicCommentEntity;
import com.zfsoft.mobile.classCommunity.entity.DynamicCommentReplyEntity;
import com.zfsoft.mobile.classCommunity.entity.DynamicEntity;
import com.zfsoft.mobile.classCommunity.entity.DynamicFileEntity;
import com.zfsoft.mobile.classCommunity.entity.DynamicFileEntityForInsert;
import com.zfsoft.mobile.classCommunity.query.ClassEntityQuery;
import com.zfsoft.mobile.classCommunity.query.DynamicEntityQuery;

/**
 * 动态dao
 * @author H110MF
 *
 */
public interface IDynamicDao {

	/**
	 * 获取班级分页列表
	 * @param query
	 * @return
	 */
	List<ClassEntity> getClassList(ClassEntityQuery query);
	
	/**
	 * 班级总数
	 * @param query
	 * @return
	 */
	int getClassListCount(ClassEntityQuery query);
	
    /**
     * 我可加入的班级圈子列表
     * @param query
     * @return
     */
	List<ClassEntity> getMyWantClassList(ClassEntityQuery query);
	
	
	/**
	 * 班级圈子帖子总数
	 * @param classId
	 * @return
	 */
	int getClassDynamicCount(@Param("classId") String classId);
	
	/**
	 * 班级圈子成员总数
	 * @param classId
	 * @return
	 */
	int getClassMemberCount(@Param("classId") String classId);
	
	/**
	 * 帖子分页列表
	 * @param dynamicEntityQuery
	 * @return
	 */
	List<DynamicEntity> getDynamicPageList(DynamicEntityQuery dynamicEntityQuery);
	
	/**
	 * 班级圈子帖子总数
	 * @param dynamicEntityQuery
	 * @return
	 */
	int getDynamicListCount(DynamicEntityQuery dynamicEntityQuery);
	
	/**
	 * 图片列表
	 * @param dynamicId
	 * @return
	 */
	List<DynamicFileEntity> getPictureList(@Param("dynamicId") String dynamicId);
	
	/**
	 * 评论列表
	 * @param dynamicId
	 * @return
	 */
	List<DynamicCommentEntity> getCommentList(@Param("dynamicId") String dynamicId);
	
	/**
	 * 回复列表
	 * @param commentId
	 * @return
	 */
	List<DynamicCommentReplyEntity> getReplyList(@Param("commentId") String commentId);
	
	/**
	 * 新增帖子
	 * @param dynamicEntity
	 * @return
	 */
	int insertDynamic(DynamicEntity dynamicEntity);
	
	/**
	 * 新增帖子图片
	 * @param dynamicFileEntity
	 * @return
	 */
	int insertDynamicPicture(DynamicFileEntityForInsert dynamicFileEntity);
	
	/**
	 * 新增评论
	 * @param dynamicCommentEntity
	 * @return
	 */
	int insertDynamicComment(DynamicCommentEntity dynamicCommentEntity);
	
	/**
	 * 新增回复
	 * @param dynamicCommentReplyEntity
	 * @return
	 */
	int insertDynamicCommentReply(DynamicCommentReplyEntity dynamicCommentReplyEntity);
	
	/**
	 * 点赞
	 * @param dynamicId
	 * @return
	 */
	int updatePraiseCount(@Param("dynamicId")String dynamicId);
	
	/**
	 * 查询某个账号是否点赞过某个帖子
	 * @param params
	 * @return
	 */
	int getUserPraiseRecordCount(Map<String,String> params);
	
	/**
	 * 新增帖子点赞记录
	 * @param params
	 * @return
	 */
	int insertUserPraiseRecord(Map<String,String> params);
}
