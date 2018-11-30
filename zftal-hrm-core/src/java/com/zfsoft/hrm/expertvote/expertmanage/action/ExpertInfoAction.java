package com.zfsoft.hrm.expertvote.expertmanage.action;

import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.expertvote.expertmanage.entity.ExpertInfo;
import com.zfsoft.hrm.expertvote.expertmanage.service.IExpertInfoService;
import com.zfsoft.hrm.expertvote.vote.service.IGroupMemberService;
import com.zfsoft.util.base.StringUtil;

public class ExpertInfoAction extends HrmAction {
	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 7526713460925763453L;
	private IExpertInfoService expertInfoService;
	private PageList<ExpertInfo> pageList;
	private ExpertInfo expertInfo = new ExpertInfo();
	
	private String ids[];
	private String asc;
	private String sortFieldName;
	
	public String page(){
		String px = "";
		if (!StringUtil.isEmpty(sortFieldName)) {
			if("up".equals(asc)){
				px = sortFieldName;
			}else{
				px = sortFieldName+" desc";
			}
		}
		expertInfo.setSortOrder(px);
		if(expertInfo != null){
			pageList=expertInfoService.getPagedExpert(expertInfo);
		}
		getValueStack().set("beginIndex", expertInfo.getCurrentResult()+1);
		return LIST_PAGE;
	}
	
	public String delete(){
		int arrayLength = ids.length;
		for (int i = 0; i < arrayLength; i++) {
			expertInfo.setId(ids[i]);
			if(expertInfo.getId()!=null){
				IGroupMemberService groupMemberService = (IGroupMemberService)SpringHolder.getBean("groupMemberService");
				int count = groupMemberService.getMemberCount(expertInfo.getId());
				if(count>0){
					this.setErrorMessage("该用户在专家组中存在，不能删除！");
				
				}else{
					expertInfoService.delete(expertInfo.getId());
					this.setSuccessMessage("删除成功！");
				}
			}	
		}
		this.getValueStack().set(DATA, getMessage());
		return DATA;
	}

	public ExpertInfo getExpertInfo() {
		return expertInfo;
	}

	public void setExpertInfo(ExpertInfo expertInfo) {
		this.expertInfo = expertInfo;
	}

	public void setExpertInfoService(IExpertInfoService expertInfoService) {
		this.expertInfoService = expertInfoService;
	}

	public void setPageList(PageList<ExpertInfo> pageList) {
		this.pageList = pageList;
	}

	public PageList<ExpertInfo> getPageList() {
		return pageList;
	}

	public String getAsc() {
		return asc;
	}

	public void setAsc(String asc) {
		this.asc = asc;
	}

	public String getSortFieldName() {
		return sortFieldName;
	}

	public void setSortFieldName(String sortFieldName) {
		this.sortFieldName = sortFieldName;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}
}
