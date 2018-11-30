package com.zfsoft.hrm.authpost.post.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.authpost.post.dao.daointerface.IDeptPostDao;
import com.zfsoft.hrm.authpost.post.entities.DeptPost;
import com.zfsoft.hrm.authpost.post.entities.PostInfo;
import com.zfsoft.hrm.authpost.post.query.DeptPostQuery;
import com.zfsoft.hrm.authpost.post.service.svcinterface.IDeptPostService;
import com.zfsoft.hrm.authpost.post.service.svcinterface.IPostInfoService;
import com.zfsoft.hrm.baseinfo.code.entities.Item;
import com.zfsoft.hrm.baseinfo.code.service.svcinterface.IItemDecorator;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.baseinfo.org.entities.OrgInfo;
import com.zfsoft.hrm.baseinfo.org.query.OrgQuery;
import com.zfsoft.hrm.baseinfo.org.service.svcinterface.IOrgService;
import com.zfsoft.hrm.config.ICodeConstants;
import com.zfsoft.hrm.core.exception.RuleException;

/** 
 * 部门岗位管理service
 * @author jinjj
 * @date 2012-7-24 下午02:12:11 
 *  
 */
public class DeptPostServiceImpl implements IDeptPostService ,IItemDecorator {

	private IDeptPostDao deptPostDao;
	private IPostInfoService postInfoService;
	private IOrgService orgService;
	
	@Override
	public DeptPost getById(String guid) {
		DeptPost entity = deptPostDao.getById(guid);
		return entity;
	}

	@Override
	public List<DeptPost> getList(DeptPostQuery query) {
		List<DeptPost> list = deptPostDao.getList(query);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageList getPageList(DeptPostQuery query) {
		PageList pageList = new PageList();
		Paginator paginator = new Paginator();
		paginator.setItemsPerPage(query.getPerPageSize());
		paginator.setPage((Integer)query.getToPage());
		
		paginator.setItems(deptPostDao.getPagingCount(query));
		pageList.setPaginator(paginator);
		
		if(paginator.getBeginIndex() <= paginator.getItems()){
			query.setStartRow(paginator.getBeginIndex());
			query.setEndRow(paginator.getEndIndex());
			pageList.addAll(deptPostDao.getPagingList(query));
		}
		return pageList;
	}

	@Override
	public void save(DeptPost post) {
		//post.setGuid(post.getDeptId()+post.getPostId());
		DeptPost entity = deptPostDao.getById(post.getGuid());
		if(entity!=null){
			throw new RuleException("该部门岗位已存在");
		}
		deptPostDao.insert(post);
		CodeUtil.insertItem(convertPostToItem(post));
	}

	@Override
	public void update(DeptPost post) {
		deptPostDao.update(post);
		CodeUtil.updateItem(convertPostToItem(post));
	}
	
	@Override
	public void remove(String guid) {
		//TODO 需要对编制进行判断，对已使用的编制或数据级联处理，或不允许删除
		DeptPostQuery query = new DeptPostQuery();
		query.setSuperiorId(guid);
		List<DeptPost> list = deptPostDao.getList(query);
		if(list.size()>0){
			throw new RuleException("该岗位含下级岗位，无法删除");
		}
		DeptPost post = new DeptPost();
		post = deptPostDao.getById(guid);
		deptPostDao.remove(guid);
		//post.setGuid(guid);
		CodeUtil.delItem(convertPostToItem(post));
	}
	
	@Override
	public List<Item> getItemList() {
		List<Item> list = new ArrayList<Item>();
		list.addAll(getDeptPostItemList());
		return list;
	}
	
	private List<Item> getDeptPostItemList(){
		List<DeptPost> list = deptPostDao.getList(new DeptPostQuery());
		List<Item> itemList = new ArrayList<Item>();
		for(DeptPost post : list){
			Item item = convertPostToItem(post);
			itemList.add(item);
		}
		return itemList;
	}
	
	private Item convertPostToItem(DeptPost post){
		Item item = new Item();
		item.setHasParentNodeInfo(0);
		item.setChecked(1);
		item.setDumped(0);
		item.setVisible(1);
		
		item.setCatalogId(ICodeConstants.DM_DEF_DEPT_POST);
		item.setGuid(post.getGuid());
		//TODO 需代码库重构，加载机制冲突
		item.setDescription(getDeptName(post.getDeptId())+getPostInfo(post.getPostId()));
		return item;
	}
	
	private String getDeptName(String deptId){
		//OrganizeModel org = organizeService.getTheOrg(deptId);
		OrgInfo org = orgService.getById(deptId);
		if(org!=null){
			//return org.getOrgName();
			return org.getName();
		}else{
			return deptId;
		}
	}
	
	private String getPostInfo(String postId){
		PostInfo post = postInfoService.getById(postId);
		if(post!=null){
			return post.getName();
		}else{
			return postId;
		}
	}

	public void setDeptPostDao(IDeptPostDao deptPostDao) {
		this.deptPostDao = deptPostDao;
	}

	public void setPostInfoService(IPostInfoService postInfoService) {
		this.postInfoService = postInfoService;
	}

	/*public void setOrganizeService(IOrganizeService organizeService) {
		this.organizeService = organizeService;
	}*/

	public void setOrgService(IOrgService orgService) {
		this.orgService = orgService;
	}

	@Override
	public List<OrgInfo> getDisusedList(OrgQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

}
