package com.firstLink.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.base.xtgl.entity.IndexModel;
import com.base.xtgl.entity.MenuPermissionEntity;
import com.base.xtgl.service.IMenuBusiness;
import com.firstLink.service.IUserService;
import com.zfsoft.dao.entities.User;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.encrypt.Encrypt;


public class UserAction extends HrmAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7223984943988512662L;

	private IUserService userService ;
	
	private IMenuBusiness menubusiness;
	
	private User query = new User();
	
	private MenuPermissionEntity menuQuery = new MenuPermissionEntity();
	
	private String op = "add";
	
	private String[] id;
	
	private String[] yiid;
	
	private String userId;
	
	public String index(){
		HttpSession session = getSession();
		User reuser = (User)session.getAttribute(GUESTUSER_INFO_KEY);
		String name = (String)session.getAttribute("name");
		if(reuser == null || StringUtil.isEmpty(name)){
			return "login";
		}
		User user = (User)getSession().getAttribute(GUESTUSER_INFO_KEY);
		List<IndexModel> menuList = menubusiness.getAllListByUser();
		getValueStack().set("menuList", menuList);
		List<IndexModel> reMenuList = menubusiness.getAllListByUser();
		getValueStack().set("reMenuList", reMenuList);
		return "index";
	}
	
	public String deleteSession(){
		HttpSession session = getSession();
		session.removeAttribute(GUESTUSER_INFO_KEY);
		setSuccessMessage("登出成功!");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String login(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if(StringUtil.isEmpty(username)){
			HashMap map=new HashMap();
         	map.put("success", false);
         	map.put("message", "用户名不能为空");
			this.getValueStack().set(DATA, map);
			return DATA;
		}
		if(StringUtil.isEmpty(password)){
			HashMap map=new HashMap();
         	map.put("success", false);
         	map.put("message", "密码不能为空");
         	this.getValueStack().set(DATA, map);
			return DATA;
		}
		User userQuery = new User();
		userQuery.setUserId(username);
		userQuery.setPassword(Encrypt.encrypt(password));
		PageList<User> userList = new PageList<User>();
		userList = userService.getList(userQuery);
		if(userList.size() == 0){
			HashMap map=new HashMap();
         	map.put("success", false);
         	map.put("message", "用户名或密码不正确！");
         	this.getValueStack().set(DATA, map);
			return DATA;
		}
		if(userList.size() > 1){
			HashMap map=new HashMap();
         	map.put("success", false);
         	map.put("message", "用户名有多个，请联系管理人员改正！");
         	this.getValueStack().set(DATA, map);
			return DATA;
		}
		User user = new User();
		user.setUserId(username);
		user.setName(userList.get(0).getName());
		HttpSession session = getSession();
		session.setAttribute(GUESTUSER_INFO_KEY, user);
		session.setAttribute("name", userList.get(0).getName());
		
		setSuccessMessage("登录");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	
	public String updatePassword(){
		userService.updatePassword(query);
		setSuccessMessage("密码初试化成功!");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

    
    public String selectAll(){
    	
    	PageList<User> userList=userService.getList(query);
        getValueStack().set("userList", userList);
        return "success";
    }
    
    public String zhuce(){
    	HttpServletRequest request = ServletActionContext.getRequest();
    	User entity = new User();
    	String username = request.getParameter("username");
    	entity.setUserId(username);
    	int count = userService.getList(entity).size();
    	if(count > 0){
    		setErrorMessage("已存在此用户名,请重新输入!");
    		getValueStack().set(DATA, getMessage());
         	return DATA;
    	}
    	String password = request.getParameter("password");
    	String name 	= request.getParameter("name");
    	entity.setPassword(password);
    	entity.setName(name);
    	userService.save(entity);
    	setSuccessMessage("保存成功！");
    	getValueStack().set(DATA, getMessage());
     	return DATA;
    }
    
    public String save(){
    	if(op.equals("add")){
    		userService.save(query);
    		/*Map<String, Object> param = new HashMap<String, Object>();
    		List<String> tids = new ArrayList<String>();
    		tids.add("N01");
    		param.put("tids", tids);
            param.put("userId", query.getUserId());
    		menuBusiness.insertYhMenu(param);*/
    	}else{
    		userService.update(query);
    	}
    	setSuccessMessage("保存成功！");
    	getValueStack().set(DATA, getMessage());
     	return DATA;
    }
    
    public String control(){
    	userService.updateBySfqy(query);
    	setSuccessMessage("操作成功！");
    	getValueStack().set(DATA, getMessage());
		return DATA;
    }

    public String tomodeify(){
    	query = userService.getList(query).get(0);
    	op = "tomodeify";
    	return "add";
    }
    
    public String toadd(){
    	op = "add";
    	return "add";
    }
    
    public String delete(){
    	userService.delete(query);
    	/*Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", query.getUserId());
    	menuBusiness.deleteYhMenu(param);*/
    	HashMap map=new HashMap();
     	map.put("success", true);
     	this.getValueStack().set(DATA, map);
     	return DATA;
    }




	public MenuPermissionEntity getMenuQuery() {
		return menuQuery;
	}

	public void setMenuQuery(MenuPermissionEntity menuQuery) {
		this.menuQuery = menuQuery;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public void setQuery(User query) {
		this.query = query;
	}

	public User getQuery() {
		return query;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String getOp() {
		return op;
	}


	public void setId(String[] id) {
		this.id = id;
	}

	public String[] getId() {
		return id;
	}

	public void setYiid(String[] yiid) {
		this.yiid = yiid;
	}

	public String[] getYiid() {
		return yiid;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public void setMenubusiness(IMenuBusiness menubusiness) {
		this.menubusiness = menubusiness;
	}

	public IMenuBusiness getMenubusiness() {
		return menubusiness;
	}





}
