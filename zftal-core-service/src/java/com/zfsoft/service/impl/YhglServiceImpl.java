package com.zfsoft.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.common.log.User;
import com.zfsoft.common.service.BaseServiceImpl;
import com.zfsoft.dao.daointerface.IYhglDao;
import com.zfsoft.dao.daointerface.IYhjsfwDao;
import com.zfsoft.dao.entities.JsglModel;
import com.zfsoft.dao.entities.UserSubsystemLastRole;
import com.zfsoft.dao.entities.YhglModel;
import com.zfsoft.dao.entities.YhglModelNew;
import com.zfsoft.dao.entities.YhjsfwModel;
import com.zfsoft.dao.entities.YhxxbModel;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.service.svcinterface.IYhglService;
import com.zfsoft.util.encrypt.Encrypt;

/**
 * 
 * 
 * 类名称：YhglServiceImpl 类描述： 用户管理业务处理实现类 创建人：Administrator 创建时间：2012-4-10
 * 下午06:44:39 修改人：Administrator 修改时间：2012-4-10 下午06:44:39 修改备注：
 * 
 * @version
 * 
 */
public class YhglServiceImpl extends BaseServiceImpl<YhglModel, IYhglDao>
		implements IYhglService {
	
	private IYhjsfwDao sjfwDao;

	// 查询启用的用户信息列表
	public List<YhglModel> cxQyyhxxList(YhglModel model)  {
		return dao.cxQyyhxxList(model);
	}

	// 查询角色列表
	public List<JsglModel> cxJsdmList() {
		return dao.cxJsdmList(new JsglModel());
	}
	
	// 查询角色列表
	public List<JsglModel> cxJsdmList(JsglModel jsglModel) {
		return dao.cxJsdmList(jsglModel);
	}

	// 保存用户信息
	public boolean zjYhxx(YhglModel model)  {
		boolean flag = false;
		// 保存用户表
		int result = dao.insert(model);
		flag = result > 0 ? true : false;
		// 保存用户角色表
		String jsdms[] = model.getCbvjsxx();
		Map<String,String> map = null;
		if (null != jsdms && jsdms.length > 0) {
			for (int i = 0; i < jsdms.length; i++) {
				String jsdm = jsdms[i];
				model.setJsdm(jsdm);
				result = dao.zjYhjsxx(model);
				//保存用户,角色数据范围
				//如果机构代码为-1则为超级管理员
				//如果机构类别为1则为教学院系，其它为全校。
				map = new HashMap<String,String>();
				if(!"-1".equals(model.getJgdm())){
					if(!"1".equals(model.getCydm_id_bmlb())){
						map.put("sjfwztj", "bmdm_id=-3");//代码所有学院数据权限
					}else{
						map.put("sjfwztj", "bmdm_id="+model.getJgdm());
					}
				}else{
					map.put("sjfwztj", "bmdm_id=-1");
				}
				map.put("sjfwzmc", model.getJgmc());
				map.put("yh_id", model.getZgh());
				map.put("js_id", jsdm);
				sjfwDao.zjYhjsfw(map);
			}
		}
		return flag;
	}

	// 修改用户信息
	public boolean xgYhxx(YhglModel model)  {
		boolean flag = false;
		// 修改用户信息
		model.setJsdm(model.getCbvjsxx()[0]);
		int result = dao.update(model);
		flag = result > 0 ? true : false;
		// 先删除用户角色表信息，然后增加用户角色信息
		model.setPkValue(model.getZgh());

		List<YhglModel> list = new ArrayList<YhglModel>();
		
		User user=SessionFactory.getUser();
		JsglModel jsglModel=new JsglModel();
		jsglModel.setIsAdmin(user.isAdmin());
		jsglModel.setJscjr(user.getYhm());
		List<JsglModel> jsList = cxJsdmList(jsglModel);
		for (JsglModel js : jsList) {
			YhglModel m = new YhglModel();
			m.setZgh(model.getZgh());
			m.setJsdm(js.getJsdm());
			list.add(m);
		}
		if(jsList.size()>0){
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("list", list);
			result = dao.scYhjsxx(param);
		}
		// 删除用户数据范围组
		YhjsfwModel sjfw = new YhjsfwModel();
		sjfw.setYh_id(model.getZgh());
		sjfwDao.scSjfwzYhJs(sjfw);		
		// 删除用户数据范围
		sjfwDao.scSjfwYhJs(sjfw);
		
		String jsdms[] = model.getCbvjsxx();
		Map<String,String> map = null;
		if (null != jsdms && jsdms.length > 0) {
			for (int i = 0; i < jsdms.length; i++) {
				String jsdm = jsdms[i];
				model.setJsdm(jsdm);
				result = dao.zjYhjsxx(model);
				//保存用户,角色数据范围
				//如果机构代码为-1则为超级管理员
				//如果机构类别为1则为教学院系，其它为全校。
				map = new HashMap<String,String>();
				if(!"-1".equals(model.getJgdm())){
					if(!"1".equals(model.getCydm_id_bmlb())){
						map.put("sjfwztj", "bmdm_id=-3");//代码所有学院数据权限
					}else{
						map.put("sjfwztj", "bmdm_id="+model.getJgdm());
					}
				}else{
					map.put("sjfwztj", "bmdm_id=-1");
				}
				map.put("sjfwzmc", model.getJgmc());
				map.put("yh_id", model.getZgh());
				map.put("js_id", jsdm);
				sjfwDao.zjYhjsfw(map);
			}
			
		}
		return flag;
	}

	// 删除用户信息
	public boolean scYhxx(YhglModel model)  {
		boolean flag = false;
		String pkValue = model.getPkValue();

		if (null != pkValue && !pkValue.equals("")) {
			String[] pks = pkValue.split(",");

			List<YhglModel> list = new ArrayList<YhglModel>();
			for (int i = 0; i < pks.length; i++) {

				YhglModel yhmodel = new YhglModel();
				yhmodel.setZgh(pks[i]);
				list.add(yhmodel);

			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("list", list);
			// 删除用户信息
			int result = dao.batchDelete(param);
			flag = result > 0 ? true : false;
			// 删除用户角色信息
			result = dao.scYhjsxx(param);
		}
		return flag;
	}

	// 密码初始化
	public boolean mmCsh(YhglModel model)  {
		boolean flag = false;
		String pkValue = model.getPkValue();
		
		if (null != pkValue && !pkValue.equals("")) {
			String[] pks = pkValue.split(",");
			for (int i = 0; i < pks.length; i++) {
				model.setPkValue(pks[i]);
				int result = dao.mmCsh(model);
				flag = result > 0 ? true : false;
			}
		}
		return flag;
	}

	// 设置所属角色
	public boolean szSsjs(YhglModel model)  {
		boolean flag = false;
		String jsdms[] = model.getCbvjsxx();
		// 先删除职工号下的用户角色信息
		model.setPkValue(model.getZgh());

		List<YhglModel> list = new ArrayList<YhglModel>();
		list.add(model);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("list", list);
		int result = dao.scYhjsxx(param);
		flag = result > 0 ? true : false;

		// 保存用户角色信息
		if (null != jsdms && jsdms.length > 0) {
			for (int j = 0; j < jsdms.length; j++) {
				String jsdm = jsdms[j];
				model.setJsdm(jsdm);
				result = dao.zjYhjsxx(model);
				flag = result > 0 ? true : false;
			}
		}
		return flag;
	}

	// 修改密码
	 public boolean xgMm(YhglModel model)  {
		model.setMm(Encrypt.encrypt(model.getMm()));// 加密
		int result = dao.xgMm(model);
		return result > 0 ? true : false;
//		 return false;
	 }

	// 根据角色代码查询用户
	public List<YhglModel> cxYhByJsdm(YhglModel model)  {
		return dao.cxYhByJsdm(model);
	}

	// 根据角色代码查询角色名称
	public JsglModel cxJsmcByJsdm(JsglModel model)  {
		return dao.cxJsmcByJsdm(model);
	}

	// 将用户信息转为横向输出 角色分配用户时使用
	public List<HashMap<String, Object>> formatYhxxList(YhglModel model)
			 {

		List<HashMap<String, Object>> rs = new ArrayList<HashMap<String, Object>>();

		List<YhglModel> yhxxList = cxQyyhxxList(model);
		if (yhxxList != null && yhxxList.size() > 0) {
			// 用户部门列表
			List<HashMap<String, String>> bmdmList = formatBmdmList(yhxxList);

			// 先循环部门
			for (HashMap<String, String> map : bmdmList) {

				HashMap<String, Object> object = new HashMap<String, Object>();
				List<Object> arrayList = new ArrayList<Object>();

				// 再根据部门查询用户信息
				for (YhglModel yhglModel : yhxxList) {
					if (map.get("bmdm_id").equalsIgnoreCase(yhglModel.getBmdm_id())) {
						arrayList.add(yhglModel);
					}
				}

				int size = arrayList.size();

				if (arrayList.size() < 4) {
					for (int i = 4; i > size; i--) {
						YhglModel nullModel = new YhglModel();
						arrayList.add(nullModel);
					}
				}

				object.put("bmdm_id", map.get("bmdm_id"));// 部门名称
				object.put("bmmc", map.get("bmmc"));// 部门名称
				object.put("col", size > 4 ? (size % 4 == 0 ? size / 4
						: (size / 4 + 1)) : 1);// 行合并数
				object.put("subList", arrayList);// 用户列表
				rs.add(object);
			}
		}
		return rs;
	}

	/**
	 * 
	 * 方法描述: 获取单个用户部门信息 参数 @return 参数说明 返回类型 List<YhglModel> 返回类型
	 */
	public List<HashMap<String, String>> formatBmdmList(List<YhglModel> yhxxList) {
		List<HashMap<String, String>> rs = new ArrayList<HashMap<String, String>>();
		Set<HashMap<String, String>> set = new HashSet<HashMap<String, String>>();
		for (YhglModel model : yhxxList) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("bmdm_id", model.getBmdm_id());
			map.put("bmmc", model.getBmmc());
			set.add(map);
		}
		for (HashMap<String, String> map : set) {
			rs.add(map);
		}
		return rs;
	}

	/**
	 * 
	 * 方法描述: 根据用户代码查询角色信息
	 *  @return 参数说明 
	 *  返回类型 List<YhglModel> 返回类型
	 */
	public List<JsglModel> cxJsdm(JsglModel model)  {
		 
		return dao.cxJsdm(model);
	}

	public IYhjsfwDao getSjfwDao() {
		return sjfwDao;
	}

	public void setSjfwDao(IYhjsfwDao sjfwDao) {
		this.sjfwDao = sjfwDao;
	}

	/**
	 * 方法描述：根据职工号获取证件号码
	 */
	@Override
	public String zjhmByZgh(String zgh) {
		return dao.zjhmByZgh(zgh);
	}

	/**
	 * 方法描述：获取所有的教职工号，用于批量初始化密码
	 */
	@Override
	public List<String> zghList() {
		return dao.zghList();
	}

	@Override
	public int addUserSubsystemRole(UserSubsystemLastRole model) {
		return dao.insertUserSubsystemRole(model);
	}

	@Override
	public int modifyRoleByUserAndSubsystem(UserSubsystemLastRole model) {
		return dao.updateRoleByUserAndSubsystem(model);
	}

	@Override
	public List<UserSubsystemLastRole> queryUserSubsystemRole(String userId,
			String sysCode) {
		Assert.hasText(userId, "userId 不能为空");
		Assert.hasText(sysCode, "sysCode 不能为空");
		UserSubsystemLastRole query=new UserSubsystemLastRole();
		query.setUserId(userId);
		query.setSysCode(sysCode);
		return dao.findUserSubsystemRoles(query);
	}

	@Override
	public PageList<YhglModelNew> getPagedListNew(YhglModelNew model) {
		PageList<YhglModelNew> pageList = new PageList<YhglModelNew>();
		Paginator paginator = new Paginator();
		if(model!=null){
			paginator.setItemsPerPage(model.getPerPageSize());
			paginator.setPage((Integer)model.getToPage());
			paginator.setItems(dao.getListCountNew(model));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				model.setStartRow(paginator.getBeginIndex());
				model.setEndRow(paginator.getEndIndex());
				List<YhglModelNew> list = dao.getListNew(model);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	
	@Override
	public void updateYhbSource(Map<String, Object> params) {
		dao.updateYhbSource(params);
	}

	/**
	 * 通过姓名更新用户积分
	 * @param params
	 * @author yangbilin
	 */
	@Override
	public void updateYhbSourceWithXm(Map<String, Object> params){
		dao.updateYhbSourceWithXm(params);
	}
	
	@Override
	public Integer sourceByZgh(String userId) {
		return dao.sourceByZgh(userId);
	}

	@Override
	public String xmByZgh(String userId) {
		return dao.xmByZgh(userId);
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.service.svcinterface.IYhglService#updateJs(com.zfsoft.dao.entities.YhglModelNew)
	 */
	@Override
	public void updateJs(YhglModelNew yhglModelQuery) {
		dao.updateJs(yhglModelQuery);
	}

	@Override
	public List<YhglModel> getRankinglist(Map<String, Object> params) {
		return dao.getRankinglist(params);
	}
	
	@Override
	public List<YhglModel> getGradeRankinglist(HashMap<String, Object> params) {
		return dao.getGradeRankinglist(params);
	}
	
	@Override
	public void unbindDeviceId(String zgh) {
		dao.unbindDeviceId(zgh);
	}

	@Override
	public boolean jbsbh(YhglModel model) {
		String zgh = model.getZgh();
		
		if( !StringUtils.isEmpty(zgh) ){
			dao.jbsbh(zgh);
			return true;
		}
		return false;
	}

	@Override
	public void bcsbh(YhglModel model) {
		dao.bcsbh(model);
	}

	@Override
	public YhglModel getYhglModelByZgh(String zgh) {
		return dao.getYhglModelByZgh(zgh);
	}

	@Override
	public List<YhglModel> getUserByDeviceidAndRylx(String deviceid, String lx) {
		return dao.getUserByDeviceidAndRylx(deviceid,lx);
	}

	@Override
	public List<YhxxbModel> getYhxxbPagedList(YhxxbModel model) {
		PageList<YhxxbModel> pageList = new PageList<YhxxbModel>();
		Paginator paginator = new Paginator();
		if(model!=null){
			paginator.setItemsPerPage(model.getPerPageSize());
			paginator.setPage((Integer)model.getToPage());
			paginator.setItems(dao.getYhxxListCount(model));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				model.setStartRow(paginator.getBeginIndex());
				model.setEndRow(paginator.getEndIndex());
				List<YhxxbModel> list = dao.getYhxxList(model);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	@Override
	public YhxxbModel getYhxxModel(YhxxbModel model) {
		return dao.getYhxxModel(model);
	}

	
}
