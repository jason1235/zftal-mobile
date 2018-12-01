package com.zfsoft.hrm.authpost.post.service.svcinterface;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.authpost.post.entities.DeptPost;
import com.zfsoft.hrm.authpost.post.query.DeptPostQuery;

/** 
 * 部门岗位管理service
 * @author jinjj
 * @date 2012-7-24 下午01:36:03 
 *  
 */
public interface IDeptPostService {

	/**
	 * 保存部门岗位
	 * @param post
	 */
	public void save(DeptPost post);
	
	/**
	 * 更新部门岗位
	 * @param post
	 */
	public void update(DeptPost post);
	
	/**
	 * 获取部门岗位
	 * @param guid
	 * @return
	 */
	public DeptPost getById(String guid);
	
	/**
	 * 部门岗位分页
	 * @param query
	 * @return
	 */
	public PageList getPageList(DeptPostQuery query);
	
	/**
	 * 部门岗位集合
	 * @param query
	 * @return
	 */
	public List<DeptPost> getList(DeptPostQuery query);
	
	/**
	 * 删除部门岗位
	 * @param guid
	 */
	public void remove(String guid);
}
