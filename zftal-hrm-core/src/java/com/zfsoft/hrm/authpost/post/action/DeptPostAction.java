package com.zfsoft.hrm.authpost.post.action;

import java.util.HashMap;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.authpost.post.entities.DeptPost;
import com.zfsoft.hrm.authpost.post.query.DeptPostQuery;
import com.zfsoft.hrm.authpost.post.service.svcinterface.IDeptPostService;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.config.ICodeConstants;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.util.base.StringUtil;

/** 
 * 部门岗位action
 * @author jinjj
 * @date 2012-7-24 下午04:50:53 
 *  
 */
public class DeptPostAction extends HrmAction {

	private static final long serialVersionUID = -6046748901837595502L;

	private IDeptPostService deptPostService;
	
	private DeptPost post;
	
	private PageList pageList;
	
	private DeptPostQuery query = new DeptPostQuery();
	
	private String sortFieldName = null;
	
	private String asc = "up";
	
	/**
	 * 保存
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception{
		validation();
		deptPostService.save(post);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	/**
	 * 修改
	 * @return
	 * @throws Exception
	 */
	public String update()throws Exception{
		validation();
		deptPostService.update(post);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	public String remove()throws Exception{
		deptPostService.remove(post.getGuid());
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	/**
	 * 录入
	 */
	public String input()throws Exception{
		getValueStack().set("code", loadCodeInPage());
		return "edit";
	}
	
	/**
	 * 编辑
	 * @return
	 * @throws Exception
	 */
	public String edit()throws Exception{
		post = deptPostService.getById(post.getGuid());
		getValueStack().set("code", loadCodeInPage());
		return "edit";
	}
	
	/**
	 * 加载页面中使用的代码编号
	 * @return
	 */
	private Map<String,String> loadCodeInPage(){
		Map<String,String> map = new HashMap<String,String>();
		map.put("deptId", ICodeConstants.DM_DEF_ORG);
		map.put("level",ICodeConstants.DM_DEF_POST_LEVEL);
		map.put("superiorId",ICodeConstants.DM_DEF_DEPT_POST);
		map.put("postId",ICodeConstants.DM_POSTINFO);
		return map;
	}
	
	/**
	 * 分页
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception{
		if(!StringUtil.isEmpty(sortFieldName)){
			if(asc.equals("up")){
				query.setOrderStr( sortFieldName );
			}else{
				query.setOrderStr( sortFieldName +" desc");
			}
		}else{
			query.setOrderStr( " guid" );
		}
		pageList = deptPostService.getPageList(query);
		this.setInActionContext("paginator", pageList.getPaginator());
		getValueStack().set("code", loadCodeInPage());
		return "list";
	}
	
	private void validation() {
		if(post.getDuty().length()>64){
			throw new RuleException("岗位职责描述请不要超过64个字");
		}
	}

	public DeptPost getPost() {
		return post;
	}

	public void setPost(DeptPost post) {
		this.post = post;
	}

	public PageList getPageList() {
		return pageList;
	}

	public void setDeptPostService(IDeptPostService deptPostService) {
		this.deptPostService = deptPostService;
	}

	public DeptPostQuery getQuery() {
		return query;
	}

	public void setQuery(DeptPostQuery query) {
		this.query = query;
	}

	public String getSortFieldName() {
		return sortFieldName;
	}

	public void setSortFieldName(String sortFieldName) {
		this.sortFieldName = sortFieldName;
	}

	public String getAsc() {
		return asc;
	}

	public void setAsc(String asc) {
		this.asc = asc;
	}
	
}
