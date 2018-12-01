package com.base.xtgl.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.base.xtgl.dao.IMenuDao;
import com.base.xtgl.entity.IndexModel;
import com.base.xtgl.entity.MenuPermissionEntity;
import com.base.xtgl.service.IMenuBusiness;
import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.dao.entities.User;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;

public class MenuBusinessImpl implements IMenuBusiness{

	private IMenuDao menudao;




	
	

	@Override
	public PageList<IndexModel> getMenu(IndexModel query) {
		PageList<IndexModel> pageList = new PageList<IndexModel>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(menudao.getListCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<IndexModel> list = menudao.getList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	@Override
	public void remove(String gnmkdm) {
		menudao.delete(gnmkdm);
	}

	@Override
	public void insertMenu(IndexModel model) {
		menudao.insertMenu(model);
	}

	@Override
	public void modify(IndexModel model) {
		menudao.update(model);
	}

	@Override
	public void updateSfqy(IndexModel model) {
		menudao.updateSfqy(model);
	}

	@Override
	public List<IndexModel> getAllList() {
		return menudao.getAllList();
	}

	@Override
	public List<MenuPermissionEntity> getPermissionList(
			MenuPermissionEntity menuQuery) {
		return menudao.getPermissionList(menuQuery);
	}

	@Override
	public List<MenuPermissionEntity> getNotPermissionList(
			MenuPermissionEntity menuQuery) {
		return menudao.getNotPermissionList(menuQuery);
	}

	@Override
	public void insertYhMenu(Map<String, Object> param) {
		menudao.insertYhMenu(param);
	}

	@Override
	public void deleteYhMenu(Map<String, Object> param) {
		menudao.deleteYhMenu(param);
	}
	@Override
	public boolean yanzheng(String path){
		HttpSession session = ServletActionContext.getRequest().getSession();
		User user = (User)session.getAttribute("guestUser");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("path", path);
        param.put("userId", user.getUserId());
		int count = menudao.yanzheng(param);
		if(count == 0){
			return false;
		}else{
			return true;
		}
	}

	@Override
	public List<IndexModel> getAllListByUser() {
		return menudao.getAllListByUser();
	}

	public void setMenudao(IMenuDao menudao) {
		this.menudao = menudao;
	}

	public IMenuDao getMenudao() {
		return menudao;
	}
	

}
