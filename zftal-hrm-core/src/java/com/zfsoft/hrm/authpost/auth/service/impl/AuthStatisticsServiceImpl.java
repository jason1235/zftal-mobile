package com.zfsoft.hrm.authpost.auth.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.hrm.authpost.auth.dao.daointerface.IAuthStatisticsDao;
import com.zfsoft.hrm.authpost.auth.entities.AuthStatistics;
import com.zfsoft.hrm.authpost.auth.service.svcinterface.IAuthStatisticsService;
import com.zfsoft.hrm.authpost.post.dao.daointerface.IPostInfoDao;
import com.zfsoft.hrm.authpost.post.entities.DeptPost;
import com.zfsoft.hrm.authpost.post.entities.PersonPostInfo;
import com.zfsoft.hrm.baseinfo.code.entities.Item;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.baseinfo.dyna.business.bizinterface.IDynaBeanBusiness;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.org.dao.daointerface.IOrgDao;
//import com.zfsoft.hrm.baseinfo.org.dao.daointerface.IOrganizeDao;
import com.zfsoft.hrm.baseinfo.org.entities.OrgInfo;
//import com.zfsoft.hrm.baseinfo.org.entities.OrganizeModel;
import com.zfsoft.hrm.baseinfo.org.query.OrgQuery;
import com.zfsoft.hrm.config.ICodeConstants;
import com.zfsoft.util.base.StringUtil;
/**
 * 
 * @author 沈鲁威 Patrick Shen
 * @since 2012-7-25
 * @version V1.0.0
 */
public class AuthStatisticsServiceImpl implements IAuthStatisticsService {
	
	private IAuthStatisticsDao authDao;
	//private IOrganizeDao orgDao;
	private IOrgDao orgDao;
	private IPostInfoDao postInfoDao;
	private IDynaBeanBusiness dynaBeanBusiness;
	
	@Override
	public List<AuthStatistics> getTreeList(DeptPost deptPost) {
		String selectedPostType=deptPost.getPostType();
		AuthStatistics authStatistics;
		List<AuthStatistics> list=new ArrayList<AuthStatistics>();
		List<Item> items=CodeUtil.getItemLeff(ICodeConstants.DM_DEF_WORKPOST);
		if(selectedPostType==null||selectedPostType.equals("")){//所属类别为空说明要获取所有类别
			if(deptPost.getDeptId()==null||deptPost.getDeptId().equals("")){//所属部门为空说明要获取所有部门
				//取得所有类别所有部门
				/*for(OrganizeModel org : orgDao.findOrgByLevel(1)){
					for(Item ditem : items){
						authStatistics=getDeptTree(ditem.getGuid(),org.getOrgCode(),items);
						if(authStatistics!=null){
							list.add(authStatistics);
						}
					}
				}*/
				//-------------组织机构查询方法改变而做的修改，2012.12.13 15:00  start ---------------
				OrgQuery orgQuery = new OrgQuery();
				orgQuery.setLevel("1");
				for(OrgInfo org : orgDao.findList(orgQuery)){
					for(Item ditem : items){
						authStatistics=getDeptTree(ditem.getGuid(),org.getOid(),items);
						if(authStatistics!=null){
							list.add(authStatistics);
						}
					}
				}
				//-------------  end ---------------
			}else{
				//取得所有类别某个部门
				for(Item ditem : items){
					authStatistics=getDeptTree(ditem.getGuid(),deptPost.getDeptId(),items);
					if(authStatistics!=null){
						list.add(authStatistics);
					}
				}
			}
		}else{//取得某类别
			if(deptPost.getDeptId()==null||deptPost.getDeptId().equals("")){//所属部门为空说明要获取所有部门
				//取得某个类别所有部门
				/*for(OrganizeModel org : orgDao.findOrgByLevel(1)){
					authStatistics=getDeptTree(selectedPostType,org.getOrgCode(),items);
					if(authStatistics!=null){
						list.add(authStatistics);
					}
				}*/
				//-------------组织机构查询方法改变而做的修改，2012.12.13 15:00  start ---------------
				OrgQuery orgQuery = new OrgQuery();
				orgQuery.setLevel("1");
				for(OrgInfo org : orgDao.findList(orgQuery)){
					authStatistics=getDeptTree(selectedPostType,org.getOid(),items);
					if(authStatistics!=null){
						list.add(authStatistics);
					}
				}
				//------------- end ---------------
			}else{
				//取得某个类别某个部门
				authStatistics=getDeptTree(selectedPostType,deptPost.getDeptId(),items);
				if(authStatistics!=null){
					list.add(authStatistics);
				}
			}
		}
		return list;
	}

	private AuthStatistics getDeptTree(String postType,String deptId, List<Item> typeItems) {
		DeptPost query=new DeptPost();
		query.setDeptId(deptId);
		query.setPostType(postType);
		
		AuthStatistics deptStatistics=new AuthStatistics();
		deptStatistics.setName(CodeUtil.getItemValue(ICodeConstants.DM_DEF_WORKPOST, postType));
		deptStatistics.setDeptId(deptId);
		deptStatistics.setDeptName(getDeptValue(deptId));
		deptStatistics.setTypeCode(postType);
		deptStatistics.setPlanAuthNum(
				authDao.getDeptPostByLikeId(query));
		deptStatistics.setCurrentAuthNum(
				authDao.getDeptTypeCurrentNum(query));
		if(deptStatistics.getPlanAuthNum()==0&&deptStatistics.getCurrentAuthNum()==0)return null;
		
		List<DeptPost> deptPosts=authDao.findDeptPost(query);
		
		List<PersonPostInfo> personPostInfos=authDao.findPersonPostInfo(query);
		
		deptStatistics.getChildren().addAll(creater(postType, deptId, deptPosts, personPostInfos,"type"));
		
		return deptStatistics;
	}

	@Override
	public List<PersonPostInfo> getPersonPostInfoByTypeCode(String deptId,
			String postType) {
		DeptPost query=new DeptPost();
		query.setDeptId(deptId);
		query.setPostType(postType);
		List<PersonPostInfo> result=authDao.findPersonPostInfo(query);
		DynaBean dyBean;
		for (PersonPostInfo personPostInfo : result) {
			 dyBean=dynaBeanBusiness.findUniqueByParam("gh", personPostInfo.getUserId());
			 personPostInfo.setOverall(dyBean);
		}
		return result;
	}

	@Override
	public List<PersonPostInfo> getPersonPostInfoByLevel(String deptId,String postType,
			String level) {
		DeptPost query=new DeptPost();
		query.setDeptId(deptId);
		query.setPostType(postType);
		query.setLevel(level);
		List<PersonPostInfo> result=authDao.findPersonPostInfo(query);
		DynaBean dyBean;
		for (PersonPostInfo personPostInfo : result) {
			 dyBean=dynaBeanBusiness.findUniqueByParam("gh", personPostInfo.getUserId());
			 personPostInfo.setOverall(dyBean);
		}
		return result;
	}

	@Override
	public List<AuthStatistics> getDeptPostShortByTypeCode(String deptId,
			String postType) {
		DeptPost query=new DeptPost();
		query.setDeptId(deptId);
		query.setPostType(postType);
		List<DeptPost> deptPosts=authDao.findDeptPost(query);
		
		List<PersonPostInfo> personPostInfos=authDao.findPersonPostInfo(query);
		return creater(postType, deptId, deptPosts, personPostInfos,"");
	}

	@Override
	public List<AuthStatistics> getDeptPostShortByLevel(String deptId,String postType,
			String level) {
		DeptPost query=new DeptPost();
		query.setDeptId(deptId);
		query.setPostType(postType);
		query.setLevel(level);
		List<DeptPost> deptPosts=authDao.findDeptPost(query);
		List<PersonPostInfo> personPostInfos=authDao.findPersonPostInfo(query);
		List<PersonPostInfo> npersonPostInfos=new ArrayList<PersonPostInfo>();
		if(null==level){
			level="";
		}
		for (PersonPostInfo personPostInfo : personPostInfos) {
			if(level.equals(personPostInfo.getPostLevel())){
				npersonPostInfos.add(personPostInfo);
			}
		}
		return creater(postType, deptId, deptPosts, personPostInfos,"");
	}
	
	private List<AuthStatistics> creater(String postType, String deptId,
			List<DeptPost> deptPosts, List<PersonPostInfo> personPostInfos,String keyType) {
		List<AuthStatistics> list=new ArrayList<AuthStatistics>();
		Map<String, AuthStatistics> lmap=new LinkedHashMap<String,AuthStatistics>();
		AuthStatistics statistic;
		String key = null;
		for(DeptPost deptPost : deptPosts){
			key=deptPost.getLevel();
			if(StringUtil.isEmpty(key)){
				key="";
			}
			if(!"type".equals(keyType)){
				key=deptPost.getDeptId()+":"+deptPost.getPostId();
			}
			if(lmap.get(key)==null){
				statistic=new AuthStatistics();
				statistic.setLevel(deptPost.getLevel());
				if(StringUtils.isEmpty(deptPost.getLevel())){
					statistic.setName("(无)");
					statistic.setLevelName("(无)");
				}else{
					statistic.setLevelName(deptPost.getLevelValue());
					statistic.setName(deptPost.getLevelValue());
				}
				statistic.setDeptPostName(deptPost.getPostValue());
				deptPost.setPostType(postType);
				statistic.setPostTypeName(deptPost.getPostTypeValue());
				statistic.setDeptId(deptId);
				statistic.setTypeCode(postType);
				statistic.setPlanAuthNum(deptPost.getPlanNumber());
				statistic.setPostId(deptPost.getGuid());
				deptPost.setPostType(postType);
				lmap.put(key, statistic);
			}else{
				statistic=lmap.get(key);
				deptPost.setPostType(postType);
				statistic.setPlanAuthNum(deptPost.getPlanNumber()+statistic.getPlanAuthNum());
			}
		}
		for (PersonPostInfo personPostInfo : personPostInfos) {
			key=personPostInfo.getPostLevel();
			if(StringUtil.isEmpty(key)){
				key="";
			}
			if(!"type".equals(keyType)){
				key=personPostInfo.getDeptId()+":"+personPostInfo.getPostInfo().getId();
			}
			if(lmap.get(key)==null){
				statistic=new AuthStatistics();
				
				DeptPost query=new DeptPost();
				query.setDeptId(deptId);
				query.setPostType(postType);
				query.setLevel(personPostInfo.getPostLevel());
				
				statistic.setLevel(personPostInfo.getPostLevel());
				statistic.setLevelName(personPostInfo.getLevelValue());
				statistic.setDeptPostName(personPostInfo.getDeptValue()+personPostInfo.getPostInfo().getName());
				statistic.setPostTypeName(personPostInfo.getPostInfo().getTypeName());
				statistic.setDeptId(deptId);
				statistic.setTypeCode(postType);
				statistic.setName(personPostInfo.getLevelValue());
				statistic.setPlanAuthNum(0);
//				statistic.setPlanAuthNum(authDao.getDeptPostByLikeLevel(query));
//				statistic.setCurrentAuthNum(authDao.getDeptPostLevelCurrentNum(query));
				statistic.setPostId(personPostInfo.getPostInfo().getId());
				statistic.setCurrentAuthNum(1);
				lmap.put(key, statistic);
			}else{
				statistic=lmap.get(key);
				statistic.setCurrentAuthNum(statistic.getCurrentAuthNum()+1);
			}
		}
		if(!"type".equals(keyType)){
			for (AuthStatistics authStatistics : lmap.values()) {
				if(authStatistics.getCurrentAuthNum()==authStatistics.getPlanAuthNum())continue;
				list.add(authStatistics);
			}
		}else{
			list.addAll(lmap.values());
		}
		
		
		
		return list;
	}

	public String getDeptValue(String deptId){
		String str = CodeUtil.getItemValue(ICodeConstants.DM_DEF_ORG, deptId);
		if(StringUtils.isEmpty(str)){
			return deptId;
		}
		return str;
	}

	public void setAuthDao(IAuthStatisticsDao authDao) {
		this.authDao = authDao;
	}

	/*public void setOrgDao(IOrganizeDao orgDao) {
		this.orgDao = orgDao;
	}*/
	
	public void setOrgDao(IOrgDao orgDao) {
		this.orgDao = orgDao;
	}

	public void setPostInfoDao(IPostInfoDao postInfoDao) {
		this.postInfoDao = postInfoDao;
	}

	public void setDynaBeanBusiness(IDynaBeanBusiness dynaBeanBusiness) {
		this.dynaBeanBusiness = dynaBeanBusiness;
	}

}
