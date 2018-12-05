package com.zfsoft.globalweb.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;
import com.zfsoft.common.action.BaseAction;
import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.common.log.Role;
import com.zfsoft.common.log.Subsystem;
import com.zfsoft.common.log.User;
import com.zfsoft.common.system.SubSystemHolder;
import com.zfsoft.dao.entities.IndexModel;
import com.zfsoft.dao.entities.JsglModel;
import com.zfsoft.dao.entities.UserSubsystemLastRole;
import com.zfsoft.dao.entities.WdyyModel;
import com.zfsoft.dao.entities.YhglModel;
import com.zfsoft.service.SubsystemUtil;
import com.zfsoft.service.svcinterface.IIndexService;
import com.zfsoft.service.svcinterface.IWdyyService;
import com.zfsoft.service.svcinterface.IYhglService;
import com.zfsoft.util.base.StringUtil;

public class IndexAction extends BaseAction implements ModelDriven<IndexModel>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1701172969179269969L;
	private IIndexService service;
	private IWdyyService wdyyService;
    private IYhglService yhglService;
	private IndexModel model = new IndexModel();
	private String url;
	private String quickId;
	
	public String constructing(){
		return null;
	}
	
	/**
	 * 
	* 方法描述: 首页的加载(教师，学生)
	* 参数 @return 参数说明
	* 返回类型 String 返回类型
	* @throws
	 */
	public String initMenu() {
		//如果系统部署方式为平台部署
		if("platform".equals(SubSystemHolder.getPropertiesValue("system_deploy"))){
			//如果session中标识为平台登陆，则跳转到平台首页
			if(getSession().getAttribute("platform_login")!=null){
				getSession().removeAttribute("platform_login");
				return "to_pindex";
			}
    	}
		ValueStack vs = getValueStack();
		User user=SessionFactory.getUser();
		
		List<String> jsxx=user.getJsdms();
		List<HashMap<String, String>> list=new ArrayList<HashMap<String,String>>();
		try {
			list = service.cxDbCd(jsxx);
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		vs.set("topMenuList", list);
		vs.set("gnmkdm", model.getGnmkdm());
		
		if (StringUtil.isEmpty(model.getGnmkdm())){
			vs.set("userId", user.getYhm());
			if("student".equals(user.getYhlx())){
				return "teapage"; 
			}else{
				return "teapage";
			}
			
		} 
		
		return "index";
	}
	/**
	 * 平台首页
	 * @return
	 */
	public String pindex() {
		if(!"platform".equals(SubSystemHolder.getPropertiesValue("system_deploy"))){
			throw new RuntimeException("非法访问平台首页！");
		}
		getValueStack().set("subsystems", SubsystemUtil.getAllSubsystems());
		return "platform_index";
	}
	
	
	public String content(){
		WdyyModel model=new WdyyModel();
		User user=getUser();
		model.setYhdm(user.getYhm());
		//模型驱动获取不到 url后面传的值
		model.setFjgndm(getRequest().getParameter("fjgndm"));
		try {
			String dyym = "";List<WdyyModel> wdyyList=null;
			if("teacher".equals(user.getYhlx())){
				wdyyList= wdyyService.cxLsWdyy(model);//查询老师
/*				if(wdyyList!=null && wdyyList.size()>0){
					dyym = wdyyList.get(0).getDyym();
					dyym = dyym.substring(dyym.lastIndexOf("/")+1,dyym.length());
					setUrl(dyym);
				}*//*else{
					Map<String,Object> param = new HashMap<String,Object>();
					param.put("fjgndm", getRequest().getParameter("fjgndm"));
					List<String> jsxx=(List<String>)SessionFactory.getSession().getAttribute(user.getYhm());
					param.put("list", jsxx);
					List<HashMap<String,String>> lists=service.cxLsmrdyym(param);
					if(lists!= null && lists.size()>0){
						dyym = lists.get(0).get("DYYM");
						dyym = dyym.substring(dyym.lastIndexOf("/")+1,dyym.length());
						setUrl(dyym);
					}
				}*/				
			}else if("student".equals(user.getYhlx())){
				wdyyList=wdyyService.cxXsWdyy(model);//查询学生
				//TODO 学生未设置我的应用则先不考虑，后续人完善补充，该 功能。
			}
 
			if(StringUtil.isEmpty(dyym)){
				setUrl("index_center.html");
			}
			
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return "contentPage";
 
	}
	
	public String center(){
		return "center";
	}
	
	public String sessionOut(){
	    return Action.LOGIN;
	}
	
	/**
	 * 
	* 方法描述: 左边菜单加载
	* 参数 @return 参数说明
	* 返回类型 String 返回类型
	* 修改人：yjd 
	* 修改时间：2012-04-25 上午10:00:06 
	* 修改备注： 
	* @throws
	 */
	public String leftMenu() {
		List<HashMap<String, Object>> menuList = new ArrayList<HashMap<String,Object>>();
		User user=SessionFactory.getUser();
		List<String> jsxx=user.getJsdms();
		try {
			menuList = service.cxZbCd(jsxx,model);
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		getValueStack().set("menuList", menuList);
		return "leftMenu";
	}
	
	/**
	 * 切换登陆角色
	 * @return
	 */
    public String switchRole(){
    	User user=SessionFactory.getUser();
    	String newRole=getRequest().getParameter("newRole");

    	if(StringUtils.isEmpty(newRole)){
    		return "switch";
    	}
    	//用户拥有的角色
    	List<Role> allRoles=user.getAllRoles();
    	//用户拥有的角色为空，则session放置空角色列表，默认不切换
    	if(allRoles==null||allRoles.size()==0){
    		List<String> emptyList=Collections.emptyList();
    		user.setJsdms(emptyList);
    		getRequest().getSession().setAttribute(user.getYhm(), user.getJsdms());
    		return "switch";
    	}
    	
    	boolean haveRole=false;
    	//检查是否拥有这个角色，如果为否，默认不切换
    	for (Role role : allRoles) {
			if(newRole.equals(role.getJsdm())){
				haveRole=true;
				break;
			}
		}
    	if(!haveRole){
    		return "switch";
    	}
		//这个角色和上次登陆角色相同，默认不切换
		if(newRole.equals(user.getScdljsdm())){
			return "switch";
		}else{
			//保存本次登陆的角色
			if(getSession().getAttribute("platform_deploy")==null){
				YhglModel yhglModel=new YhglModel();
				yhglModel.setZgh(user.getYhm());
				yhglModel.setScdljsdm(newRole);
				yhglService.update(yhglModel);
			}else{
				this.saveUserSubsystemRole(user.getYhm(), ((Subsystem)getSession().getAttribute("subsystem")).getSysCode(), newRole);
			}
			
			user.setScdljsdm(newRole);
			List<String> jsdms=new ArrayList<String>(1);
			jsdms.add(newRole);
			user.setJsdms(jsdms);
			getRequest().getSession().setAttribute(user.getYhm(), user.getJsdms());
			
			return "switch";
		}
    	
    }
    
    /**
     * 子系统入口
     * @return
     */
    public String enterSub(){
    	//如果系统部署方式不是平台部署
    	if(!"platform".equals(SubSystemHolder.getPropertiesValue("system_deploy"))){
    		setErrMsg("系统部署方式为非平台部署，访问非法！");
    		return DATA;
    	}
    	String sysCode=getRequest().getParameter("sysCode");
    	if(StringUtils.isEmpty(sysCode)){
    		setErrMsg("子系统为空！");
    		return DATA;
    	}
    	List<Subsystem> subsystems=SubsystemUtil.getAllSubsystems();
    	if(subsystems==null||subsystems.size()==0){
    		setErrMsg("平台没有配置子系统！");
    		return DATA;
    	}
    	boolean isExists=false;
    	for (Subsystem subsystem : subsystems) {
			if(sysCode.equals(subsystem.getSysCode())){
				isExists=true;
			}
		}
    	if(!isExists){
    		setErrMsg("子系统不存在！");
    		return DATA;
    	}
    	//根据职工号和sysCode查询角色表,校验用户是否有权限
    	JsglModel model=new JsglModel();
    	User user=getUser();
    	model.setZgh(user.getYhm());
    	model.setSysCode(sysCode);
		List<Role> allRoles = new ArrayList<Role>();
		List<JsglModel> jsglModels = yhglService.cxJsdm(model);
		if(jsglModels==null||jsglModels.size()==0){
			setErrMsg("您还没有权限操作该子系统！");
			return DATA;
		}
		for(JsglModel m:jsglModels){
			Role r=new Role();
			r.setJsdm(m.getJsdm());
			r.setJsmc(m.getJsmc());
			allRoles.add(r);
		}
		user.setAllRoles(allRoles);
		
		//上次登陆子系统的角色
		List<UserSubsystemLastRole> list=yhglService.queryUserSubsystemRole(user.getYhm(), sysCode);
		if(list!=null&&list.size()>0){
			user.setScdljsdm(list.get(0).getRoleId());
		}
		
		this.initUserSubsystemRoles(user,sysCode);
		Map<String,Boolean> map=new HashMap<String, Boolean>();
		map.put("success",true);
		ActionContext.getContext().getValueStack().set("data", map);
		return DATA;
    }
    
    private void setErrMsg(String msg){
    	ValueStack vs= ActionContext.getContext().getValueStack();
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("success", false);
		map.put("text", msg);
		vs.set("data", map);
    }
    
    /**
	 * 初始化角色信息
	 * @param user
	 */
    private void initUserSubsystemRoles(User user,String sysCode){
    	getSession().setAttribute("subsystem", SubsystemUtil.getSubsystem(sysCode));
    	String login_type=SubSystemHolder.getPropertiesValue("login_type");
    	//用户拥有的角色
    	List<Role> allRoles=user.getAllRoles();
    	//1、单角色登陆
    	if("sole_role".equals(login_type)){
    		List<String> jsdms=new ArrayList<String>(1);
    		if(allRoles==null||allRoles.size()==0){
    			user.setJsdms(jsdms);
    			getRequest().getSession().setAttribute(user.getYhm(), null);
    			return;
    		}else if(StringUtils.isEmpty(user.getScdljsdm())){
    			String currentJsdm=allRoles.get(0).getJsdm();
    			user.setScdljsdm(currentJsdm);
    			jsdms.add(currentJsdm);
    			//保存本次登陆的角色
    			this.saveUserSubsystemRole(user.getYhm(), sysCode, currentJsdm);
    		}else{
    			//检测上次登陆的角色，该用户是否还拥有，如果不再拥有，则取第一个拥有的角色，并写入db
    			for (Role role : allRoles) {
					if(user.getScdljsdm().equals(role.getJsdm())){
						jsdms.add(user.getScdljsdm());
						break;
					}
				}
    			if(jsdms.size()==0){
    				String currentJsdm=allRoles.get(0).getJsdm();
    				user.setScdljsdm(currentJsdm);
    				jsdms.add(currentJsdm);
    				//保存本次登陆的角色
    				this.saveUserSubsystemRole(user.getYhm(), sysCode, currentJsdm);
    			}
    		}
    		user.setJsdms(jsdms);
    	}else{
    		//2、普通登陆
    		if(allRoles==null||allRoles.size()==0){
    			List<String> emptyList=Collections.emptyList();
    			user.setJsdms(emptyList);
    			getRequest().getSession().setAttribute(user.getYhm(), null);
    			return;
    		}else{
    			List<String> jsdms=new ArrayList<String>();
    			for(Role role:user.getAllRoles()){
    				jsdms.add(role.getJsdm());
    			}
    			user.setJsdms(jsdms);
    		}
    	}
    	getSession().setAttribute(user.getYhm(), user.getJsdms());
    }
    
    /**
     * 保存访问子系统的角色
     * @param userId
     * @param sysCode
     * @param roleId
     */
    private void saveUserSubsystemRole(String userId,String sysCode,String roleId){
    	UserSubsystemLastRole model=new UserSubsystemLastRole();
    	model.setUserId(userId);
    	model.setSysCode(sysCode);
    	model.setRoleId(roleId);
    	List<UserSubsystemLastRole> list=yhglService.queryUserSubsystemRole(userId, sysCode);
    	if(list==null||list.size()==0){
    		yhglService.addUserSubsystemRole(model);
    	}else{
    		yhglService.modifyRoleByUserAndSubsystem(model);
    	}
    }
	public IndexModel getModel() {
		return model;
	}
	public void setModel(IndexModel model) {
		this.model = model;
	}


	public IIndexService getService() {
		return service;
	}


	public void setService(IIndexService service) {
		this.service = service;
	}

	public IYhglService getYhglService() {
		return yhglService;
	}

	public void setYhglService(IYhglService yhglService) {
		this.yhglService = yhglService;
	}

	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public IWdyyService getWdyyService() {
		return wdyyService;
	}


	public void setWdyyService(IWdyyService wdyyService) {
		this.wdyyService = wdyyService;
	}


	public String getQuickId() {
		return quickId;
	}


	public void setQuickId(String quickId) {
		this.quickId = quickId;
	}

}
