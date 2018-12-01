package com.zfsoft.hrm.authpost.auth.dao.daointerface;

import java.util.List;

import com.zfsoft.hrm.authpost.post.entities.DeptPost;
import com.zfsoft.hrm.authpost.post.entities.PersonPostInfo;

/**
 * 
 * @author 沈鲁威 Patrick Shen
 * @since 2012-7-24
 * @version V1.0.0
 */
public interface IAuthStatisticsDao {
	/**
	 * 通过部门编码、岗位类别    获取部门岗位编制计划信息
	 * @param likeId
	 * @return
	 */
	public int getDeptPostByLikeId(DeptPost query);
	/**
	 * 通过部门编码、岗位类别    获取部门岗位等级编制实际数量
	 * @param likeId
	 * @param type
	 * @param level
	 * @return
	 */
	public int getDeptTypeLevelCurrentNum(DeptPost query);
	/**
	 * 通过部门编码、岗位类别    获取部门岗位等级编制实际数量
	 * @param likeId
	 * @param type
	 * @return
	 */
	public int getDeptTypeCurrentNum(DeptPost query);
	/**
	 * 
	 * @param string
	 * @param level
	 * @return
	 */
	public List<DeptPost> findDeptPost(DeptPost deptPost);
	/**
	 * 获取人员岗位关联
	 * @param query
	 * @return
	 */
	public List<PersonPostInfo> findPersonPostInfo(DeptPost query);
	/**
	 * 通过部门编码、岗位等级    获取部门岗位编制计划信息
	 * @param query
	 * @return
	 */
	public int getDeptPostByLikeLevel(DeptPost query);
	/**
	 * 通过部门编码、岗位等级    获取部门岗位等级编制实际数量
	 * @param query
	 * @return
	 */
	public int getDeptPostLevelCurrentNum(DeptPost query);
}
