package com.zfsoft.hrm.authpost.auth.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.hrm.authpost.auth.entities.AuthStatistics;
import com.zfsoft.hrm.authpost.auth.service.svcinterface.IAuthStatisticsService;
import com.zfsoft.hrm.authpost.post.entities.DeptPost;
import com.zfsoft.hrm.baseinfo.code.entities.Item;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.config.ICodeConstants;
/**
 * 编制统计
 * @author 沈鲁威 Patrick Shen
 * @since 2012-7-24
 * @version V1.0.0
 */
public class AuthStatisticsAction extends HrmAction {

	private static final long serialVersionUID = -6290791405619509382L;
	
	public static final String CURRENT_USER_PAGE="current_user_page";
	
	public static final String SHORT_POST_PAGE="short_post_page";

	private IAuthStatisticsService authStatisticsService;

	private AuthStatistics model=new AuthStatistics();
	
	private DeptPost deptPost=new DeptPost();
	
	/**
	 * 初始化选中框的数据源
	 */
	private void initList(){
		List<Item> typeList=CodeUtil.getChildren(ICodeConstants.DM_DEF_WORKPOST,null);
		Item item=new Item();
		item.setGuid("all");
		item.setDescription("全部");
		typeList.add(0, item);
		this.getValueStack().set("typeList", typeList);
		getValueStack().set("code", loadCodeInPage());		
	}
	/**
	 * 加载页面中使用的代码编号
	 * @return
	 */
	private Map<String,String> loadCodeInPage(){
		Map<String,String> map = new HashMap<String,String>();
		map.put("deptId", ICodeConstants.DM_DEF_ORG);
		map.put("postType", ICodeConstants.DM_DEF_WORKPOST);
		return map;
	}
	
	public String list() {
		initList();
		this.getValueStack().set("list",authStatisticsService.getTreeList(deptPost));
		return LIST_PAGE;
	}

	public String currentlist_type(){
		this.getValueStack().set("personPostInfoList",
				authStatisticsService.getPersonPostInfoByTypeCode(deptPost.getDeptId(),deptPost.getPostType()));
		return CURRENT_USER_PAGE;
	}
	public String currentlist_level(){
		this.getValueStack().set("personPostInfoList",
				authStatisticsService.getPersonPostInfoByLevel(deptPost.getDeptId(),deptPost.getPostType(),deptPost.getLevel()));
		return CURRENT_USER_PAGE;
	}
	public String currentlist_type_short(){
		this.getValueStack().set("deptPostList",
				authStatisticsService.getDeptPostShortByTypeCode(deptPost.getDeptId(),deptPost.getPostType()));
		return SHORT_POST_PAGE;
	}
	public String currentlist_level_short(){
		this.getValueStack().set("deptPostList",
				authStatisticsService.getDeptPostShortByLevel(deptPost.getDeptId(),deptPost.getPostType(),deptPost.getLevel()));
		return SHORT_POST_PAGE;
	}
	public void setAuthStatisticsService(
			IAuthStatisticsService authStatisticsService) {
		this.authStatisticsService = authStatisticsService;
	}
	
	public AuthStatistics getModel(){
		return model;
	}
	
	public void setModel(AuthStatistics model) {
		this.model = model;
	}
	public DeptPost getDeptPost() {
		return deptPost;
	}
	public void setDeptPost(DeptPost deptPost) {
		this.deptPost = deptPost;
	}
}
