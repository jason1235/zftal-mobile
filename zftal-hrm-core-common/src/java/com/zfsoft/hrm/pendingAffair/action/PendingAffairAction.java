package com.zfsoft.hrm.pendingAffair.action;

import java.util.HashMap;
import java.util.List;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.common.log.User;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.pendingAffair.entities.PendingAffairInfo;
import com.zfsoft.hrm.pendingAffair.service.svcinterface.IPendingAffairService;

public class PendingAffairAction extends HrmAction{

	private static final long serialVersionUID = 972636126126265438L;
	
	private List<PendingAffairInfo> list;
	
	private IPendingAffairService pendingAffairService;
	

	public String list(){
		User user = SessionFactory.getUser();
		list = pendingAffairService.getListByUser(user);
		list.addAll(pendingAffairService.getListByRoles(user));
		return LIST_PAGE;
	}
	
	public String indexList(){
		int maxIndex = 7;
		User user = SessionFactory.getUser();
		list = pendingAffairService.getListByUser(user);
		if(list.size()<maxIndex){
			List<PendingAffairInfo> rList = pendingAffairService.getListByRoles(user);
			for (PendingAffairInfo info :rList) {
				list.add(info);
				if(list.size() >= maxIndex){
					break;
				}
			}
//			list.addAll(pendingAffairService.getListByRoles(user));
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("result", list);
		map.put("success", true);
		getValueStack().set(DATA, map);
		return DATA;
	}

	public List<PendingAffairInfo> getList() {
		return list;
	}

	public void setPendingAffairService(IPendingAffairService pendingAffairService) {
		this.pendingAffairService = pendingAffairService;
	}
	
}
