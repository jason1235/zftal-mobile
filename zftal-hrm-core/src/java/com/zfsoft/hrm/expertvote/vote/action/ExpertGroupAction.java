package com.zfsoft.hrm.expertvote.vote.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.util.Assert;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.expertvote.expertmanage.entity.ExpertInfo;
import com.zfsoft.hrm.expertvote.expertmanage.service.IExpertInfoService;
import com.zfsoft.hrm.expertvote.vote.entity.ExpertGroup;
import com.zfsoft.hrm.expertvote.vote.entity.GroupMember;
import com.zfsoft.hrm.expertvote.vote.query.ExpertGroupQuery;
import com.zfsoft.hrm.expertvote.vote.query.GroupMemberQuery;
import com.zfsoft.hrm.expertvote.vote.service.IExpertGroupService;
import com.zfsoft.hrm.expertvote.vote.service.IGroupMemberService;
import com.zfsoft.util.base.StringUtil;

/**
 * 专家组管理
 * 
 * @author ChenMinming
 * @date 2014-3-11
 * @version V1.0.0
 */
public class ExpertGroupAction extends HrmAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2013031111339946L;
	private IExpertGroupService expertGroupService;
	private ExpertGroup model = new ExpertGroup();
	private PageList<ExpertGroup> pageList = new PageList<ExpertGroup>();
	private ExpertGroupQuery query = new ExpertGroupQuery();
	private String asc;
	private String sortFieldName;

	private IExpertInfoService expertInfoService;
	private PageList<ExpertInfo> expertList = new PageList<ExpertInfo>();
	private ExpertInfo expertInfo = new ExpertInfo();
	private GroupMember groupMember = new GroupMember();
	private String[] ids;
	private IGroupMemberService groupMemberService;
	private PageList<GroupMemberQuery> memberList = new PageList<GroupMemberQuery>();
	private GroupMemberQuery groupMemberQuery = new GroupMemberQuery();
	private List<String> cy_ids = new ArrayList<String>();

	public String page() {
		String px = "";
		if (!StringUtil.isEmpty(sortFieldName)) {
			if ("up".equals(asc)) {
				px = sortFieldName;
			} else {
				px = sortFieldName + " desc";
			}
		}
		query.setOrderStr(px);
		pageList = expertGroupService.getPageList(query);
		int beginIndex = pageList.getPaginator().getBeginIndex();
		getValueStack().set("beginIndex", beginIndex);
		return "page";
	}

	public String detail() {
		if (model != null && !StringUtil.isEmpty(model.getId())) {
			model = expertGroupService.getById(model.getId());
		}
		if (model == null) {
			model = new ExpertGroup();
		}
		groupMemberQuery.setZjz_id(model.getId());
		memberList = groupMemberService.getPagedList(groupMemberQuery);
		return "detail";
	}

	public String add() {
		return "add";
	}

	public String delete() {
		if (model.getId() != null) {
			groupMemberQuery.setZjz_id(model.getId());
			memberList = groupMemberService.getPagedList(groupMemberQuery);
			if (memberList.size() > 0) {
				this.setErrorMessage("该专家组有专家成员，不能删除");
			} else {
				expertGroupService.delete(model.getId());
				this.setSuccessMessage("删除成功");
			}
		}
		this.getValueStack().set(DATA, getMessage());
		return DATA;
	}

	public String save() {
		if (model == null) {
			setErrorMessage("参数为空");
			getValueStack().set(DATA, getMessage());
		} else {
			if (StringUtil.isEmpty(model.getId())) {
				expertGroupService.saveExpertGroup(model);
			} else {
				expertGroupService.modifyExpertGroup(model);
			}
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("id", model.getId());
			data.put("success", true);
			getValueStack().set(DATA, data);
		}
		return DATA;
	}

	public String select() {
		expertList = expertInfoService.getPagedExpert(expertInfo);
		this.getValueStack().set("zjz_id", groupMember.getZjz_id());
		return "select";
	}

	/**
	 * 手动添加专家
	 * 
	 * @param:
	 * @return:
	 */
	public String addExpert() {
		for (int i = 0; i < ids.length; i++) {
			int n = groupMemberService.getZj_id(ids[i],groupMember.getZjz_id());     //根据选择的专家id和该专家组的zid，判断是否该成员已经添加到该组
			if (n > 0) {
				continue;
			}
			groupMember.setZj_id(ids[i]);
			groupMemberService.insert(groupMember);
		}
		this.setSuccessMessage("添加成功！");
		this.getValueStack().set(DATA, getMessage());
		return DATA;
	}

	public String rule() {
		this.getValueStack().set("zid", groupMember.getZjz_id());
		return "rule";
	}

	/**
	 * 随机抽取专家
	 * 
	 * @param PageList
	 *            <ExpertInfo> pageList2
	 * @param:
	 * @return:
	 */
	public String random() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String rule = request.getParameter("zy");
		List<String> sList = null;
		if (rule.equals("single")) {
			String zydm = request.getParameter("zydm");
			sList = expertInfoService.getListIdBySingle(zydm);
		} else {
			sList = expertInfoService.getListIdByAll();
		}
		Assert.notEmpty(sList, "没有可选专家抽取");
		int count = Integer.parseInt(request.getParameter("count"));
		if (sList.size() > count) {
			List<String> ranList = groupMemberService.getRandomExpert(sList,
					count);
			for (String s : ranList) {
				groupMember.setZj_id(s);
				groupMemberService.insert(groupMember);
			}
		} else {
			for (String s : sList) {
				groupMember.setZj_id(s);
				groupMemberService.insert(groupMember);
			}
		}
		this.setSuccessMessage("抽取完成！");
		this.getValueStack().set(DATA, getMessage());
		return DATA;
	}

	public String remove() {
		if (cy_ids.size() > 0) {
			for (String s : cy_ids) {
				groupMemberService.remove(s);
			}
		}
		this.setSuccessMessage("移除成功！");
		this.getValueStack().set(DATA, getMessage());
		return DATA;
	}

	public String modify() {
		return DATA;
	}

	public ExpertGroup getModel() {
		return model;
	}

	public void setModel(ExpertGroup model) {
		this.model = model;
	}

	public PageList<ExpertGroup> getPageList() {
		return pageList;
	}

	public void setPageList(PageList<ExpertGroup> pageList) {
		this.pageList = pageList;
	}

	public ExpertGroupQuery getQuery() {
		return query;
	}

	public void setQuery(ExpertGroupQuery query) {
		this.query = query;
	}

	public void setExpertGroupService(IExpertGroupService expertGroupService) {
		this.expertGroupService = expertGroupService;
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

	public PageList<ExpertInfo> getExpertList() {
		return expertList;
	}

	public void setExpertList(PageList<ExpertInfo> expertList) {
		this.expertList = expertList;
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

	public GroupMember getGroupMember() {
		return groupMember;
	}

	public void setGroupMember(GroupMember groupMember) {
		this.groupMember = groupMember;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public void setGroupMemberService(IGroupMemberService groupMemberService) {
		this.groupMemberService = groupMemberService;
	}

	public GroupMemberQuery getGroupMemberQuery() {
		return groupMemberQuery;
	}

	public void setGroupMemberQuery(GroupMemberQuery groupMemberQuery) {
		this.groupMemberQuery = groupMemberQuery;
	}

	public PageList<GroupMemberQuery> getMemberList() {
		return memberList;
	}

	public void setMemberList(PageList<GroupMemberQuery> memberList) {
		this.memberList = memberList;
	}

	public List<String> getCy_ids() {
		return cy_ids;
	}

	public void setCy_ids(List<String> cy_ids) {
		this.cy_ids = cy_ids;
	}
}