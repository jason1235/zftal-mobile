package com.zfsoft.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.common.log.User;
import com.zfsoft.common.service.BaseServiceImpl;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.collection.CollectionUtil;
import com.zfsoft.dao.daointerface.IIndexDao;
import com.zfsoft.dao.daointerface.IYhglDao;
import com.zfsoft.dao.entities.IndexModel;
import com.zfsoft.dao.entities.JsglModel;
import com.zfsoft.service.svcinterface.IIndexService;


/**
 * 
* 
* 类名称：IndexServiceImpl 
* 类描述：首页业务层接口实现类
* 创建人：yjd 
* 创建时间：2012-05-09 上午10:53:39 
* 修改备注： 
* @version 
*
 */
public class IndexServiceImpl extends BaseServiceImpl<IndexModel, IIndexDao> implements IIndexService {
	
	private IYhglDao yhglDao;
	
	/**
	 * 查询顶部 一级菜单  top
	 */
	public List<HashMap<String, String>> cxDbCd(List<String> jsxx)  {
		User user=SessionFactory.getUser();
		if("student".equals(user.getYhlx())){
			IndexModel model=new IndexModel();
			model.setFjgndm("N");
			return dao.cxXsGmdm(model);
		}else{
			if(!CollectionUtil.isEmpty(jsxx)){
			    List<HashMap<String, String>> list1= dao.cxLsYjGmdmGjJsWsj(jsxx);
			    List<HashMap<String, String>> list2= dao.cxLsYjGmdmGjJs(jsxx);
			    List<HashMap<String, String>> list3= new ArrayList<HashMap<String,String>>();
			    for(int i=0;i<list1.size();i++){
			        HashMap<String, String> map1=  list1.get(i);
			        int j=0;
			        for(j=0;j<list2.size();j++){
			            HashMap<String, String> map2=  list2.get(j);
			            if(map1.get("GNMKDM").equals(map2.get("GNMKDM"))){
			                break;
			            }
			        }
			        if(j==list2.size()){
			            list3.add(map1);
			        }
			    }
			    list3.addAll(list2);
			    Collections.sort(list3, new Comparator<HashMap<String,String>>(){
                    @Override
                    public int compare(HashMap<String, String> o1, HashMap<String, String> o2) {
                        if(Integer.parseInt(o1.get("XSSX"))>Integer.parseInt(o2.get("XSSX"))){
                            return 1;
                        }else{
                            return -1;
                        }
                    }});

				return list3;
			}else{
				return new ArrayList<HashMap<String,String>>();
			}
				
		}
	}
	
	/**
	 * 查询左部  二、三级 菜单  left
	 */
	public List<HashMap<String, Object>> cxZbCd(List<String> jsxx,IndexModel model)  {
		List<HashMap<String, Object>> rs = new ArrayList<HashMap<String,Object>>();
		List<HashMap<String, String>> ejList=null;
		List<HashMap<String, String>> sjList=null;
		User user=SessionFactory.getUser();
		if("student".equals(user.getYhlx())){
			IndexModel m=new IndexModel();
			m.setFjgndm(model.getGnmkdm());//一级菜单传过来的  一级菜单功能模块代码
			ejList = dao.cxXsGmdm(m);
			sjList = dao.cxXsGmdmSj(m);
			
		}else{
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("list", jsxx);
			map.put("model", model);
			if(!CollectionUtil.isEmpty(jsxx)){
				//如果是admin角色则开放所有功能模块
				ejList = dao.cxLsEJGmdmGjJs(map);//二级
				sjList = dao.cxLsSjGmdmGjJs(map);//三级
			}else{
				ejList = new ArrayList<HashMap<String,String>>();//二级
				sjList = new ArrayList<HashMap<String,String>>();//三级
			}
		}

		for (HashMap<String, String> map : ejList) {
			HashMap<String, Object> objMap = new HashMap<String, Object>();
			objMap.put("GNMKDM", map.get("GNMKDM"));
			objMap.put("GNMKMC", map.get("GNMKMC"));
			objMap.put("FJGNDM", map.get("FJGNDM"));
			objMap.put("DYYM", map.get("DYYM"));
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
	 * 
	 * 方法描述: 登录成功获取当前用户的对应角色  即老师
	 * 参数 @param model 登录类
	 * @throws
	 */
	public List<String> cxJsxxLb(User user)  {
		List<String> list=null;
		if("student".equals(user.getYhlx())){
			//学生是没有角色的
			return null;
		}else{
			List<HashMap<String, String>> jsxxs=dao.cxJsxxLb(user);
			if(jsxxs.size() > 0){
				list=new ArrayList<String>();
				for (int i = 0; i < jsxxs.size(); i++) {
					list.add(jsxxs.get(i).get("JSDM"));
				}
			}
		}
		return list;
	}
	
	/**
	 * 三级菜单权限
	 */
	public boolean yzQx(String path) {
		User user=SessionFactory.getUser();
		IndexModel model=new IndexModel();
		Map<String, Object> map=new HashMap<String, Object>();
		List<HashMap<String, String>> list=null;
		model.setDyym(path);
		map.put("model", model);
		if("student".equals(user.getYhlx())){
			return true;//学生没有操作权限控制
		}else{
			List<String> jsxx=(List<String>)SessionFactory.getSession().getAttribute(user.getYhm());
			map.put("list", jsxx);
			list=dao.cxLsSjGmdm(map);
		}
		if(list!=null && list.size()>0){
			return true;
		}
		return false;
	}
	
	/**
	 * 切换用户
	 */
	public User qhYh(IndexModel indexModel) {
		Map<String, String> map = dao.getTeaxx(indexModel);
		User qhUser=new User();
		if (map != null && !map.isEmpty()){
			qhUser.setYhm(map.get("ZGH"));
			qhUser.setXm(map.get("XM"));
			qhUser.setYhlx("teacher");
			qhUser.setBmmc(map.get("BMMC"));
			qhUser.setBmdm_id(map.get("BMDM_ID"));
			qhUser.setGwjbdm(map.get("GWJBDM"));
			qhUser.setGwjbmc(map.get("GWJBMC"));
			qhUser.setFsYhm(map.get("FSZGH"));
			JsglModel jsglModel  = new JsglModel();
			jsglModel.setZgh(map.get("ZGH"));
			List<String> jsdms = new ArrayList<String>();
			List<JsglModel> jsglModels = yhglDao.cxJsdm(jsglModel);
			for(JsglModel m:jsglModels){
				jsdms.add(m.getJsdm());
			}
			qhUser.setJsdms(jsdms);			
		}
		return qhUser;
	}

	/**
	 * 附属用户信息   user当前用户user
	 */
	public User fsYh(User user) {
		IndexModel model=new IndexModel();
		User u=new User();
		if(user != null){
			if(StringUtil.isEmpty(user.getFsYhm())){
				model.setFsZgh(user.getYhm());
			}else{
				model.setYhm(user.getFsYhm());
			}
			Map<String, String> map = dao.getTeaxx(model);
			if (map != null && !map.isEmpty()){
				u.setYhm(map.get("ZGH"));
				u.setXm(map.get("XM"));
				u.setYhlx("teacher");
				u.setBmmc(map.get("BMMC"));
				u.setBmdm_id(map.get("BMDM_ID"));
				u.setGwjbdm(map.get("GWJBDM"));
				u.setGwjbmc(map.get("GWJBMC"));
				u.setFsYhm(map.get("FSZGH"));
				JsglModel jsglModel  = new JsglModel();
				jsglModel.setZgh(map.get("ZGH"));
				List<String> jsdms = new ArrayList<String>();
				List<JsglModel> jsglModels = yhglDao.cxJsdm(jsglModel);
				for(JsglModel m:jsglModels){
					jsdms.add(m.getJsdm());
				}
				user.setJsdms(jsdms);				
			}
		}
		return u;
	}

	/**
	 * 方法名: cxLsmrdyym
	 * 方法描述: 查询老师一级模块对应页面 （当前用户）
	 */
	public List<HashMap<String, String>> cxLsmrdyym(Map<String, Object> param) {
	 
		return dao.cxLsmrdyym(param);
	}
	
	public IYhglDao getYhglDao() {
		return yhglDao;
	}

	public void setYhglDao(IYhglDao yhglDao) {
		this.yhglDao = yhglDao;
	}
}
