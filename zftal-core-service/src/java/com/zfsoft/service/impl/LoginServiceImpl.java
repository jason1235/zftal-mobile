package com.zfsoft.service.impl;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.zfsoft.common.log.Role;
import com.zfsoft.common.log.User;
import com.zfsoft.common.service.BaseServiceImpl;
import com.zfsoft.common.system.SubSystemHolder;
import com.zfsoft.dao.daointerface.IJsglDao;
import com.zfsoft.dao.daointerface.ILoginDao;
import com.zfsoft.dao.daointerface.IYhglDao;
import com.zfsoft.dao.entities.JsglModel;
import com.zfsoft.dao.entities.LoginModel;
import com.zfsoft.dao.entities.LoginRecordModel;
import com.zfsoft.dao.entities.YhglModel;
import com.zfsoft.dao.entities.userStatictisEntity;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.dao.query.LoginRecordModelQuery;
import com.zfsoft.service.svcinterface.ILoginService;
import com.zfsoft.util.encrypt.Encrypt;

/**
 * 
* 
* 类名称：LoginServiceImpl 
* 类描述： 登录业务层接口实现类
* 创建人：hhy 
* 创建时间：2011-12-20 上午10:53:39 
* 修改人：hhy 
* 修改时间：2011-12-20 上午10:53:39 
* 修改备注： 
* @version 
*
 */
public class LoginServiceImpl extends BaseServiceImpl<LoginModel, ILoginDao> implements ILoginService {
	private ILoginDao loginDao;
	private IJsglDao jsglDao;
	private IYhglDao yhglDao;
	
	private static Logger logger = Logger.getLogger(LoginServiceImpl.class);
	
	/**
	 * 
	* 方法名: checkLogin
	* 方法描述: 验证用户登录返回提示信息 
	* 修改时间：2011-12-20 上午10:52:41 
	* 参数 @param model
	* 参数 @param session
	* 参数 @return 
	* 返回类型 String 
	* @throws
	 */
	@SuppressWarnings("static-access")
	public User cxYhxx(LoginModel model)
			 {
		Encrypt encrypt = new Encrypt();
		
		//OaEncrypt encrypt = new OaEncrypt();
		if (!StringUtils.isEmpty(model.getMm())) {
			model.setMm(encrypt.encrypt(model.getMm()));
		}
		
		User user = teaLogin( model );
		
//		if( user == null ) {
//			user = stuLogin( model );
//		}
		
		return user;
	}

	
	@Override
	public User cxYhxxSso(LoginModel model,HttpSession session) {
		model.setMm( null );
		
		//单点登录只有教师登录，不存在学生登录
		User user= teaLogin( model );
		
		session.setAttribute("user", user);
		
        //初始化角色信息
        this.initRoles(user,session);
		
		return user;
	}
	
	/**
	 * 初始化角色信息
	 * @param user
	 */
    private void initRoles(User user,HttpSession session){
    	String login_type=SubSystemHolder.getPropertiesValue("login_type");
    	//用户拥有的角色
    	List<Role> allRoles=user.getAllRoles();
    	//1、单角色登陆
    	if("sole_role".equals(login_type)){
    		List<String> jsdms=new ArrayList<String>(1);
    		if(allRoles==null||allRoles.size()==0){
    			user.setJsdms(jsdms);
    			session.setAttribute(user.getYhm(), null);
    			return;
    		}else if(StringUtils.isEmpty(user.getScdljsdm())){
    			String currentJsdm=allRoles.get(0).getJsdm();
    			user.setScdljsdm(currentJsdm);
    			jsdms.add(currentJsdm);
    			//保存本次登陆的角色
    			YhglModel yhglModel=new YhglModel();
    			yhglModel.setZgh(user.getYhm());
    			yhglModel.setScdljsdm(currentJsdm);
    			yhglDao.update(yhglModel);
    		}else{
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
        			YhglModel yhglModel=new YhglModel();
        			yhglModel.setZgh(user.getYhm());
        			yhglModel.setScdljsdm(currentJsdm);
        			yhglDao.update(yhglModel);
    			}
    		}
    		user.setJsdms(jsdms);
    	}else{
    		//2、普通登陆
    		if(allRoles==null||allRoles.size()==0){
    			List<String> emptyList=Collections.emptyList();
    			user.setJsdms(emptyList);
    			session.setAttribute(user.getYhm(), null);
    			return;
    		}else{
    			List<String> jsdms=new ArrayList<String>();
    			for(Role role:user.getAllRoles()){
    				jsdms.add(role.getJsdm());
    			}
    			user.setJsdms(jsdms);
    		}
    	}
    	session.setAttribute(user.getYhm(), user.getJsdms());
    }
	
	/**
	 * 教师登录
	 * @param model
	 * @return
	 */
	private User teaLogin( LoginModel model ) {
		User user = null;
		Map<String, String> map = loginDao.getTeaxx(model);
		YhglModel ym = null;
		if (map != null && !map.isEmpty()){
			logger.error("map不为空");
			user = new User();
			if (!"1".equals((String)map.get("SFQY"))) {
				user.setSfqy("0");
				return user;
			} 
			user.setYhm(map.get("ZGH"));
			user.setXm(map.get("XM"));
			// 评教系统用 start
			/*if (map.containsKey("RYLX")) {
				if (StringUtils.isEmpty(map.get("RYLX"))) {
					user.setYhlx("teacher");
				} else {
					user.setYhlx(map.get("RYLX"));
				}
			} else {
				user.setYhlx("teacher");
			}*/
			String yhlx = !StringUtils.isEmpty(map.get("RYLX")) ? map.get("RYLX") : "teacher";
			user.setYhlx(yhlx);
			// 评教系统用 end
			user.setBmmc(map.get("BMMC"));
			user.setBmdm_id(map.get("BMDM_ID"));
			user.setGwjbdm(map.get("GWJBDM"));
			user.setGwjbmc(map.get("GWJBMC"));
			user.setScdljsdm(map.get("SCDLJSDM"));
			user.setDeviceId(map.get("DEVICEID"));
			user.setGrade(map.get("GRADE"));
			JsglModel jsglModel  = new JsglModel();
			jsglModel.setZgh(map.get("ZGH"));
			List<Role> allRoles = new ArrayList<Role>();
			List<JsglModel> jsglModels = yhglDao.cxJsdm(jsglModel);
			for(JsglModel m:jsglModels){
				Role r=new Role();
				r.setJsdm(m.getJsdm());
				r.setJsmc(m.getJsmc());
				r.setSfejsq(m.getSfejsq());
				allRoles.add(r);
			}
			user.setAllRoles(allRoles);
			ym = new YhglModel();
			ym.setZgh(model.getYhm());
			ym.setErrNum("0");
			yhglDao.update(ym);
		}else{
			logger.error("map为空");
			YhglModel yhglModel=new YhglModel();
			yhglModel.setZgh(model.getYhm());
			ym = yhglDao.getModel(yhglModel);
			if (ym != null ) {
				int errNum;
				int num;
				try{
					num = Integer.valueOf(SubSystemHolder.getPropertiesValue("error_login_num"));
				}catch (Exception e) {
					num = 5;
				}
				try {
					errNum = Integer.valueOf(ym.getErrNum());
				} catch (Exception e) {
					errNum = 0;
				}
				if ("1".equals(ym.getSfqy())) {
					errNum++;
					if (errNum >= num) {
						ym.setSfqy("0");
					}
					YhglModel modify = new YhglModel();
					modify.setZgh(ym.getZgh());
					modify.setSfqy(ym.getSfqy());
					modify.setErrNum(errNum+"");
					yhglDao.update(modify);
				}
			}
		}
		return user;
	}
	
	/**
	 * 学生登录
	 * @param model
	 * @return
	 */
	private User stuLogin( LoginModel model ) {
		User user = null;
		
		Map<String, String> map = loginDao.getStuxx(model);
		
		if (map != null && !map.isEmpty()){
			user = new User();
			user.setYhm(map.get("XH"));
			user.setXm(map.get("XM"));
			user.setYhlx("student");
			user.setBmdm_id(map.get("GWJBDM"));
			user.setBmmc(map.get("GWJBMC"));
		}
		
		return user;
	}
	

	/**
	 * 
	* 方法描述: 查询用户功能模块代码
	* 参数 @param model
	* 参数 @return 参数说明
	* 返回类型 List<HashMap<String,String>> 返回类型
	* @throws
	 */
	public List<HashMap<String, String>> queryYjGnmkdm(LoginModel model) {
		model.setFjgndm("N");
		return loginDao.queryTeaGnmkdm(model);
	}

	/**
	 * 查询二级菜单
	 */
	public List<HashMap<String, String>> queryEjMenu(LoginModel model) {
		return loginDao.queryGnmkdmList(model);
	}

	/**
	 * 查询三级菜单
	 */
	public List<HashMap<String, String>> querySjMenu(LoginModel model) {
		return loginDao.querySjGnmkdmList(model);
	}

	/**
	 * 
	* 方法描述: 查询用户二级，三级功能菜单列表
	* 参数 @param model
	* 参数 @return 参数说明
	* 返回类型 List<HashMap<String,Object>> 返回类型
	* @throws
	 */
	public List<HashMap<String, Object>> queryLeftMenu(LoginModel model) {
		List<HashMap<String, String>> ejList = queryEjMenu(model);//二级
		List<HashMap<String, String>> sjList = querySjMenu(model);//三级
		List<HashMap<String, Object>> rs = new ArrayList<HashMap<String,Object>>();
		
		for (HashMap<String, String> map : ejList) {
			HashMap<String, Object> objMap = new HashMap<String, Object>();
			objMap.put("GNMKDM", map.get("GNMKDM"));
			objMap.put("GNMKMC", map.get("GNMKMC"));
			objMap.put("FJGNDM", map.get("FJGNDM"));
			List<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
			for (HashMap<String, String> innermap : sjList) {
				if (map != null
						&& (map.get("GNMKDM") != null)
						&& innermap != null
						&& map.get("GNMKDM").equalsIgnoreCase(
								innermap.get("FJGNDM"))) {
					list.add(innermap);
				}
			}
			objMap.put("sjMenu", list);
			
			rs.add(objMap);
		}
		return rs;
	}
	
	
	/**
	 * 权限方式二 ，  展示保留这种方式
	 * 方法描述: 将用户信息相关经过处理再塞入session
	 * 参数 @param model 登录类
	 * 参数 @param session HttpSession
	 * 参数 @param user 用户信息类
	 * @throws
	 */
	public void setYhqxToSession(LoginModel model, HttpSession session)
			 {
		List<JsglModel> sjJsglList = null;// 三级
		List<JsglModel> ejJsglList = null;// 二级
		List<JsglModel> yjJsglList = null;// 一级
		if ("teacher".equals(model.getYhlx())) {
			// 获取当前用户三级菜单功能块s
			sjJsglList = jsglDao.cxSjGndm(model.getYhm());
			for (JsglModel jsglModel : sjJsglList) {
				// 将三级功能模块载入session
				session.setAttribute(jsglModel.getDyym(), jsglModel);
			}
			// 获取当前用户二级菜单功能块
			ejJsglList = jsglDao.cxErGndm(model.getYhm());
			for (JsglModel jsglModel : ejJsglList) {
				// 将二级功能模块载入session
				session.setAttribute(jsglModel.getGnmkdm(), jsglModel);
			}
			// 获取当前用户一级菜单功能块
			yjJsglList = jsglDao.cxYiGndm(model.getYhm());
			for (JsglModel jsglModel : yjJsglList) {
				// 将一级功能模块载入session
				session.setAttribute(jsglModel.getGnmkdm(), jsglModel);
			}

		} else {
			// 获取当前用户三级菜单功能块s
			sjJsglList = jsglDao.cxSjGndmXs();
			for (JsglModel jsglModel : sjJsglList) {
				// 将三级功能模块载入session
				// session.setAttribute(jsglModel.getGnmkdm(), jsglModel);
				session.setAttribute(jsglModel.getDyym(), jsglModel);
			}
			// 获取当前用户二级菜单功能块
			ejJsglList = jsglDao.cxErGndmXs();
			for (JsglModel jsglModel : ejJsglList) {
				// 将二级功能模块载入session
				session.setAttribute(jsglModel.getGnmkdm(), jsglModel);
			}
			// 获取当前用户一级菜单功能块
			yjJsglList = jsglDao.cxYiGndmXs();
			for (JsglModel jsglModel : yjJsglList) {
				// 将一级功能模块载入session
				session.setAttribute(jsglModel.getGnmkdm(), jsglModel);
			}
		}
		// 设置权限list
		session.setAttribute("sjJsglList", sjJsglList);
		session.setAttribute("ejJsglList", ejJsglList);
		session.setAttribute("yjJsglList", yjJsglList);
	}
	
	public ILoginDao getLoginDao() {
		return loginDao;
	}


	public void setLoginDao(ILoginDao loginDao) {
		this.loginDao = loginDao;
	}


	public IJsglDao getJsglDao() {
		return jsglDao;
	}


	public void setJsglDao(IJsglDao jsglDao) {
		this.jsglDao = jsglDao;
	}


	public IYhglDao getYhglDao() {
		return yhglDao;
	}


	public void setYhglDao(IYhglDao yhglDao) {
		this.yhglDao = yhglDao;
	}




	@Override
	public void updateStrKey(YhglModel yhglModel) {
		// TODO Auto-generated method stub
		yhglDao.updateStrKey(yhglModel);
	}


	@Override
	public String getStrKey(String apptoken) {
		return yhglDao.getStrKey(apptoken);
	}


	@Override
	public String getStrKeyByYhm(String zgh) {
		// TODO Auto-generated method stub
		return yhglDao.getStrKeyByYhm(zgh);
	}

	/**
	 * app端进入app引导页调用此接口做安装统计
	 */
	@Override
	public void installsCount() {
		// TODO Auto-generated method stub
		int count = yhglDao.getInstallsCount();
		if(count == 1){
			yhglDao.updateInstallsCount();
		}
		if(count == 0){
			yhglDao.insertInstallsCount(); 
		}
	}
	//根据时间查询用户安装量
	@Override
	public List<userStatictisEntity> getInstallsStatistic(userStatictisEntity entity){
		return yhglDao.getInstallsStatistic(entity);
	}
	
	/**
	 * 根据用户名查询用户信息
	 * @param userId
	 * @return
	 */
	public User getInfoByZgh(LoginModel model){
		Map<String,String> map = yhglDao.getInfoByZgh(model);
		User user = null;
		YhglModel ym = null;
		if (map != null && !map.isEmpty()){
			user = new User();
			if (!"1".equals((String)map.get("SFQY"))) {
				user.setSfqy("0");
				return user;
			} 
			user.setYhm(map.get("ZGH"));
			user.setXm(map.get("XM"));
			// 评教系统用 start
			/*if (map.containsKey("RYLX")) {
				if (StringUtils.isEmpty(map.get("RYLX"))) {
					user.setYhlx("teacher");
				} else {
					user.setYhlx(map.get("RYLX"));
				}
			} else {
				user.setYhlx("teacher");
			}*/
			String yhlx = !StringUtils.isEmpty(map.get("RYLX")) ? map.get("RYLX") : "teacher";
			user.setYhlx(yhlx);
			// 评教系统用 end
			user.setBmmc(map.get("BMMC"));
			user.setBmdm_id(map.get("BMDM_ID"));
			user.setGwjbdm(map.get("GWJBDM"));
			user.setGwjbmc(map.get("GWJBMC"));
			user.setScdljsdm(map.get("SCDLJSDM"));
			JsglModel jsglModel  = new JsglModel();
			jsglModel.setZgh(map.get("ZGH"));
			List<Role> allRoles = new ArrayList<Role>();
			List<JsglModel> jsglModels = yhglDao.cxJsdm(jsglModel);
			for(JsglModel m:jsglModels){
				Role r=new Role();
				r.setJsdm(m.getJsdm());
				r.setJsmc(m.getJsmc());
				r.setSfejsq(m.getSfejsq());
				allRoles.add(r);
			}
			user.setAllRoles(allRoles);
			ym = new YhglModel();
			ym.setZgh(model.getYhm());
			ym.setErrNum("0");
			yhglDao.update(ym);
		}	
		return user;
	}
	
    public int addLoginRecord(Map<String,Object> params){
    	return loginDao.addLoginRecord(params);
    }
	
	public int updateLoginRecord(Map<String,Object> params){
		return loginDao.updateLoginRecord(params);
	}
	
	public int selectLoginRecordByUsername(Map<String,Object> params){
		return loginDao.selectLoginRecordByUsername(params);
	}
	
    public PageList<LoginRecordModel> getLoginRecordList(LoginRecordModelQuery query){
    	PageList<LoginRecordModel> pageList = new PageList<LoginRecordModel>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(loginDao.getLoginRecordListCount(query));
			pageList.setPaginator(paginator);
			if((Integer)query.getToPage() > paginator.getPages()){
				return pageList;
			}
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<LoginRecordModel> list = loginDao.getLoginRecordList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
    }
	
	public int getLoginRecordListCount(LoginRecordModelQuery loginRecordModel){
		return loginDao.getLoginRecordListCount(loginRecordModel);
	}
	
	public int deleteLoginRecord(LoginRecordModelQuery loginRecordModel){
		return loginDao.deleteLoginRecord(loginRecordModel);
	}


	@Override
	public int bindDevice(User user) {
		return yhglDao.bindDevice(user);
	}


	@Override
	public int countUserByDeviceid(String yhm,String deviceId) {
		return yhglDao.countUserByDeviceid(yhm,deviceId);
	}


	@Override
	public YhglModel getUserByDeviceid(String deviceId) {
		return loginDao.getUserByDeviceid(deviceId);
	}


	@Override
	public List<YhglModel> getUserByDeviceidAndRylx(String deviceId, String lx) {
		return yhglDao.getUserByDeviceidAndRylx(deviceId,lx);
	}


}
