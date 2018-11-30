package com.zfsoft.hrm.authpost.auth.service.svcinterface;

import java.util.List;

import com.zfsoft.hrm.authpost.auth.entities.AuthStatistics;
import com.zfsoft.hrm.authpost.post.entities.DeptPost;
import com.zfsoft.hrm.authpost.post.entities.PersonPostInfo;

/**
 * 
 * @author 沈鲁威 Patrick Shen
 * @since 2012-7-24
 * @version V1.0.0
 */
public interface IAuthStatisticsService {
	/**
	 * 获取二层树形展示的编制统计列表
	 * @param selectedPostType
	 * @param deptPost
	 * @return
	 */
	List<AuthStatistics> getTreeList(DeptPost deptPost);
	/**
	 * 获取现编人员岗位类别
	 * @param deptId
	 * @param postType
	 * @return
	 */
	public List<PersonPostInfo> getPersonPostInfoByTypeCode(String deptId,String postType);
	/**
	 * 获取现编人员具体岗位
	 * @param deptId
	 * @param postId
	 * @return
	 */
	List<PersonPostInfo> getPersonPostInfoByLevel(String deptId,String postType,String level);
	/**
	 * 获取缺编情况通过岗位类别和部门
	 * @param deptId
	 * @param postType
	 * @return
	 */
	List<AuthStatistics>  getDeptPostShortByTypeCode(String deptId, String postType);
	/**
	 * 获取缺编情况通过部门和岗位等级
	 * @param deptId
	 * @param level
	 * @param string 
	 * @return
	 */
	List<AuthStatistics> getDeptPostShortByLevel(String deptId, String postType, String level);
}
