package com.zfsoft.service.impl;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.common.service.BaseServiceImpl;
import com.zfsoft.dao.daointerface.IJsglDao;
import com.zfsoft.dao.daointerface.IYhglDao;
import com.zfsoft.dao.daointerface.IYhjsfwDao;
import com.zfsoft.dao.entities.JsglModel;
import com.zfsoft.dao.entities.NewJsglModel;
import com.zfsoft.dao.entities.YhglModel;
import com.zfsoft.dao.entities.YhjsfwModel;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.service.svcinterface.IJsglService;
import com.zfsoft.util.base.MessageUtil;
import com.zfsoft.util.base.StringUtil;



/**
 * 
 * 类名称：JsglServiceImpl 
 * 类描述：角色管理业务实现类 创建人：Administrator 
 * 创建时间：2012-4-1 下午03:52:52
 * 修改人：Administrator 
 * 修改时间：2012-4-1 下午03:52:52 
 * 修改备注：
 * 
 * @version
 * 
 */
public class JsglServiceImpl extends BaseServiceImpl<JsglModel, IJsglDao>
		implements IJsglService {
	private IYhjsfwDao sjfwDao;
	private IYhglDao yhglDao;

	// 查询岗位代码信息
	public List<JsglModel> cxGwjbList()  {
		return dao.cxGwjbList();
	}

	// 删除角色信息
	public String scJsdmxx(JsglModel model)  {
		String mess="";
		int successRow=0;
		int errorRow=0;
		String pks = model.getPkValue();
		if (StringUtil.isBlank(pks)) {
			return "没有要删除的数据";
		}
			String pkValues[] = pks.split(",");
			List<JsglModel> list = new ArrayList<JsglModel>();
			for (int i = 0; i < pkValues.length; i++) {
				model.setJsdm(pkValues[i]);
				JsglModel jsglmodel = dao.getModel(model);
				String sfksc        = jsglmodel.getSfksc();
				String num          = jsglmodel.getYhnum();
				if((sfksc==null||!sfksc.equals("1"))&&num.equals("0")){
					successRow++;
					//shenqi 20120911将删除对象从model修改为jsglmodel
					jsglmodel.setPkValue(pkValues[i]);
					list.add(jsglmodel);
				}
			}

			Map<String, Object> param = new HashMap<String, Object>();
			param.put("list", list);
			if(list.size()>0){
				// 删除角色信息
				 successRow = dao.batchDelete(param);
				
				// 删除角色权限信息,由于是关系删除，可能存在删除0条数据，不作判断
				 dao.scJsgnmkdmxx(param);
			}
			errorRow = pkValues.length-successRow; 
			if(errorRow == 0){
				mess=MessageUtil.getText("I99005");
			}else{
				mess=MessageUtil.getText("I99009");
			}
		
		return mess;
	}

	/**
	 * 查询功能模块列表信息根据用户所属角色
	 */
	public List<JsglModel> cxGnmkdmList(JsglModel model)  {
		return dao.cxGnmkdmList(model);
	}
	
	/**
	 * 查询所有功能模块列表信息
	 * @param model
	 * @return
	 */
	public List<JsglModel> cxAllGnmkdmList(JsglModel model)  {
		return dao.cxAllGnmkdmList(model);
	}

	// 查询第一级功能模块信息
//	public List<JsglModel> cxDyjGnmkdm(JsglModel model)  {
//		if (model == null) {
//			model = new JsglModel();
//		}
//		
//		return cxGnmkdmList(model);
//	}

	/**
	 * 查询功能模块操作权限列表
	 */
	public List<JsglModel> cxGnmkczList(JsglModel model)  {
		return dao.cxGnmkczList(model);
	}
	
	/**
	 * 查询功能模块操作权限列表根据用户所属角色
	 * @param model
	 * @return
	 */
	public List<JsglModel> cxAllGnmkczList(JsglModel model)  {
		return dao.cxAllGnmkczList(model);
	}

	// 查询功能模块操作权限字符串
	public String getGnmkczStr(JsglModel model)  {
		List<JsglModel> rs = cxGnmkczList(model);
		// TODO 字符串操作建议使用StringBuilder
		String str = "";
		//StringBuilder str = new StringBuilder();
		for (JsglModel object : rs) {
			str+= object.getGnmkdm()+ ":" ;
			str+=  object.getCzdm() + ":";
			str+=  object.getCzmc() + "-";
		}
		return str != "" ? str.substring(0, str.length() - 1) : str;
	}

	// 查询功能模块操作权限字符串
//	public List<JsglModel> getGnmkczList(JsglModel model)  {
//		List<JsglModel> rs = cxGnmkczList(model);
//		return rs;
//	}

	/**
	 * 
	 * 方法描述: 保存角色功能模块权限 参数 @return 参数说明 返回类型
	 */
	public boolean zjJsGnmkqx(JsglModel model)  {
		int result = dao.zjJsgnmkdmxx(model);
		return result > 0 ? true : false;
	}

	/**
	 * 
	 * 方法描述: 删除角色功能模块权限 参数 @return 参数说明 返回类型
	 */
	public boolean scJsgnqx(JsglModel model)  {
		List<JsglModel> list = new ArrayList<JsglModel>();
		list.add(model);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("list", list);
		
			 dao.scJsgnmkdmxx(param);
		
		return true;

	}

	/**
	 * 
	 * 方法描述: 批量保存角色功能模块权限 参数 @return 参数说明 返回类型
	 */
	public boolean zjJsGnmkqxBatch(JsglModel model)  {
		boolean result = false;
		model.setJsdm(model.getPkValue());
		// 先删除角色对应的功能权限
		result = scJsgnqx(model);

		// 再进行批量插入
		if (result) {
			String[] gnczcbv = model.getBtns();
			// 先保存三级功能模块操作按钮信息
			if (gnczcbv != null && gnczcbv.length > 0) {
				for (String str : gnczcbv) {
					model
							.setGnmkdm(str != null && str != "" ? str
									.split("_")[0] : "");
					model.setCzdm(str != null && str != "" ? str.split("_")[1]
							: "");
					zjJsGnmkqx(model);
				}
				result = true;
			}

			result = zjJssjgnmkqx(model);
		}
		return result;
	}

	/**
	 * 插入三级功能模块代码信息（用于未选择任何操作按钮时） 无操作按钮时，则插入#代替操作代码
	 * 
	 * @param model
	 * @return
	 * @
	 */
	public boolean zjJssjgnmkqx(JsglModel model)  {

		String[] gnczcbv = model.getBtns();
		String[] gnmkdmcbv = model.getSjgndmcbv();
		List<String> array = new ArrayList<String>();
		if (gnczcbv != null && gnczcbv.length > 0) {
		for (String gnmkdm : gnmkdmcbv) {
			for (String czdm : gnczcbv) {
				if (gnmkdm.equalsIgnoreCase(czdm.split("_")[0])) {
					array.add(gnmkdm);
				}
			}
		}
		}
		String[] targetArray = array != null ? array.toArray(new String[0])
				: new String[] {};
		List<String> rs = new ArrayList<String>();
		if (gnmkdmcbv != null && gnmkdmcbv.length > 0) {
		for (String gnmkdm : gnmkdmcbv) {
			if (!search(targetArray, gnmkdm)) {
				rs.add(gnmkdm);
			}
		}
		}
		if (rs != null) {
			for (String s : rs) {
				model.setGnmkdm(s);
				model.setCzdm("#");
				try {
					zjJsGnmkqx(model);
				} catch (Exception e) {
					return false;
				}

			}
		}
		return true;
	}

	/**
	 * 查询一个字符串在一个数组中是否存在
	 * 
	 * @param arr
	 * @param b
	 * @return
	 */
	public boolean search(String[] arr, String b) {
		int j = -1;
		for (int i = 0; i < arr.length; i++) {
			j = (arr[i] != null && arr[i].equalsIgnoreCase(b) == true) ? i : -1;
			if (j >= 0)
				return true;
		}
		return false;
	}

	// 查询单个角色代码功能权限
	public String getJsgnqxStr(JsglModel model)  {
		String result = "";
		//TODO StringBuilder result = new StringBuilder();
		List<JsglModel> rs = cxJsgnqxList(model);
		if (rs != null && rs.size() > 0) {
			for (JsglModel object : rs) {
				result+=object.getGnmkdm();
				result+="_";
				result+= object.getCzdm();
				result+=",";
			}
		}
		return result != "" ? result.substring(0, result.length() - 1) : result;
	}

	/**
	 * 
	 * 方法描述: 查询单个角色功能权限代码 参数 @return 参数说明 返回类型
	 */
	public List<JsglModel> cxJsgnqxList(JsglModel model)  {
		return dao.cxJsGnqxList(model);
	}

	/**
	 * 
	 * 方法描述: 删除单个角色对应用户 参数 @return 参数说明 返回类型
	 */
	public boolean scJsyhfpxx(JsglModel model)  {
			
			dao.scJsfpyhxx(model);
			return true;
	}

	/**
	 * 
	 * 方法描述: 插入单个角色单个用户 参数 @return 参数说明 返回类型
	 */
	public boolean zjJsyhfpxx(JsglModel model)  {
		int result = dao.zjJsfpyhxx(model);
		return result > 0 ? true : false;
	}

	// 保存角色对应用户信息
	public boolean saveJsyhfpxx(JsglModel model)  {
		boolean result = false;
		result = scJsyhfpxx(model);
		if (result) {
			YhjsfwModel sjfw = new YhjsfwModel();
			sjfw.setJs_id(model.getJsdm());
			//根据用户角色,删除数据范围组记录
			sjfwDao.scSjfwzYhJs(sjfw);
			//根据用户角色,删除数据范围记录
			sjfwDao.scSjfwYhJs(sjfw);
			Map<String,String> map = null;
			if (model.getYhCbv() != null && model.getYhCbv().length > 0) {
				for (String s : model.getYhCbv()) {
					JsglModel object = new JsglModel();
					object.setZgh(s);
					object.setJsdm(model.getJsdm());
					result = zjJsyhfpxx(object);
					//保存用户、角色数据范围
					//YhjsfwModel sjfwModel = new YhjsfwModel();
					YhglModel yhgl = new YhglModel();
					yhgl.setZgh(s);
					yhgl = yhglDao.getModel(yhgl);
					String jgdm = yhgl.getJgdm();
					//如果机构代码为-1则为超级管理员
					//如果机构类别为1则为教学院系，其它为全校。
					map = new HashMap<String,String>();
					if(!"-1".equals(jgdm)){
						if(!"1".equals(yhgl.getCydm_id_bmlb())){
							map.put("sjfwztj", "bmdm_id='-3'");//代码全学院数据权限
						}else{
							map.put("sjfwztj", "bmdm_id='"+yhgl.getJgdm()+"'");
						}
					}else{
						map.put("sjfwztj", "bmdm_id='-1'");
					}
					//保存用户,角色数据范围
					map.put("sjfwzmc", yhgl.getJgmc());
					map.put("yh_id", yhgl.getZgh());
					map.put("js_id", model.getJsdm());
					sjfwDao.zjYhjsfw(map);
				}
			}
		}
		return result;
	}

	// 查询角色用户列表
	public String getJsyhxxList(List<YhglModel> list)  {
		
		String rs = "";
		//StringBuilder rs = new StringBuilder();
		if (list != null) {
			for (YhglModel model : list) {
				rs+= model.getZgh();
				rs+= "!!";
			}
		}
		return rs != "" ? rs.substring(0, rs.length() - 2) : rs;
	}

	/**
	 * 设置子节点
	 * 
	 * @param model
	 * @param list
	 * @return
	 */
	private List<JsglModel> setChildList(JsglModel model, List<JsglModel> list) {
		List<JsglModel> childList = new ArrayList<JsglModel>();

		// 获取该gnmk下的子功能模块
		for (int i = 0; i < list.size(); i++) {
			JsglModel jsglmodel = list.get(i);
			String dm = jsglmodel.getGnmkdm();
			if (dm.substring(0, dm.length() - 2).equalsIgnoreCase(
					model.getGnmkdm())) {
				JsglModel gnmkModel = new JsglModel();

				gnmkModel.setGnmkdm(dm);
				gnmkModel.setGnmkmc(jsglmodel.getGnmkmc());
				childList.add(gnmkModel);
			}
		}
		list.removeAll(childList);
		model.setChildSize(String.valueOf(childList.size()>1?childList.size():1));
		model.setChildList(childList);
		return childList;
	}

	/**
	 * 查询功能模块:系统管理员(admin)获得所有功能模块;
	 *             其他用户根据所属角色(可能存在多个角色)获取角色下的功能模块;
	 * @param model
	 */
	public List<JsglModel> getAllGnmkList(JsglModel model)  {

		List<JsglModel> list = new ArrayList<JsglModel>();
		List<JsglModel> gnmkListOne = null;
		List<JsglModel> gnmkListTwo = null;
		List<JsglModel> gnmkListThree = null;
		List<JsglModel> gnmkListFour = null;
		
		//判断用户是否为管理员，如果用户的角色代码中含有admin字眼，则为管理员
		List<String> jsdms= SessionFactory.getUser().getJsdms();
		boolean isAdmin=false;
		for (String jsdm : jsdms) {
			if(jsdm.indexOf("admin")>-1){
				isAdmin=true;
				break;
			}
		}
		
		if(isAdmin){
			// 一级菜单
			model.setLen("3");
			gnmkListOne = cxAllGnmkdmList(model);
			// 二级菜单
			model.setLen("5");
			gnmkListTwo = cxAllGnmkdmList(model);
			// 三级菜单
			model.setLen("7");
			gnmkListThree = cxAllGnmkdmList(model);
			// 四级按钮
			gnmkListFour = cxAllGnmkczList(model);
		}else{
			// 一级菜单
			model.setLen("3");
			gnmkListOne = cxGnmkdmList(model);
			// 二级菜单
			model.setLen("5");
			gnmkListTwo = cxGnmkdmList(model);
			// 三级菜单
			model.setLen("7");
			gnmkListThree = cxGnmkdmList(model);
			// 四级按钮
			gnmkListFour = cxGnmkczList(model);
		}
	 
		for (int i = 0; i < gnmkListOne.size(); i++) {
			JsglModel jsglmodelOne = new JsglModel();
			JsglModel jsglOne = gnmkListOne.get(i);
			jsglmodelOne.setGnmkdm(jsglOne.getGnmkdm());
			jsglmodelOne.setGnmkmc(jsglOne.getGnmkmc());

			List<JsglModel> childListOne = setChildList(jsglmodelOne,
					gnmkListTwo);

			for (JsglModel gnmkModelTwo : childListOne) {
				// 三级菜单
				List<JsglModel> childListTwo = setChildList(gnmkModelTwo,
						gnmkListThree);

				for (JsglModel gnmkModelThr : childListTwo) {
					String dmThr = gnmkModelThr.getGnmkdm();
					List<JsglModel> btnList = new ArrayList<JsglModel>();

					for (JsglModel btn : gnmkListFour) {
						if (btn.getGnmkdm().equalsIgnoreCase(dmThr)) {
							btnList.add(btn);
						}
					}
					gnmkModelThr.setBtnList(btnList);
					// 删除已知按钮，减小循环次数
					gnmkListFour.removeAll(btnList);
				}

			}
			list.add(jsglmodelOne);
		}
		return list;
	}
	
	//根据职工号查询角色信息
	public List<JsglModel> cxJsxxZgh(Map<String,String> map)  {
		return dao.cxJsxxZgh(map);
	}	

	//查询学院组织机构列表
	public List<HashMap<String, String>> queryXyZzjg(Map<String,String> map)  {
 
		return dao.queryXyZzjg(map);
	}

	//查询专业组织机构列表
	public List<HashMap<String, String>> queryZyZzjg(Map<String, String> map)
			 {
		return dao.queryZyZzjg(map);
	}

	//查询年级组织机构列表
	public List<HashMap<String, String>> queryNjZzjg(Map<String, String> map)
			 {
		return dao.queryNjZzjg(map);
	}

	//查询班级组织机构列表
	public List<HashMap<String, String>> queryBjZzjg(Map<String, String> map)
			 {
		return dao.queryBjZzjg(map);
	}

    @Override
    public List<YhglModel> getPagedListWfpYh(JsglModel model)  {

        return dao.getPagedListWfpYh(model);
    }

    @Override
    public List<YhglModel> getPagedListYfpYh(JsglModel model)  {

        return dao.getPagedListYfpYh(model);
    }
    
	//查询所有一级功能模块列表
	public List<JsglModel> getGnmkYj(JsglModel model) {
		return dao.getGnmkYj(model);
	}

	//查询该用户是否具有二级授权功能
	public boolean getYhEjsq(JsglModel jsglModel) {
		List<JsglModel> lists = dao.getYhEjsq(jsglModel);
		if (lists != null && lists.size() > 0) {
			return true;
		} 
		return false;
	}
	
    
	// 保存角色对应用户信息
	public boolean saveFp(JsglModel model)  {
		boolean result = false;
		if (model.getYhCbv() != null && model.getYhCbv().length > 0) {
			for (String s : model.getYhCbv()) {
				JsglModel object = new JsglModel();
				object.setZgh(s);
				object.setJsdm(model.getJsdm());
				result = zjJsyhfpxx(object);
			}
		}
		return result;
	}
	
	/**
	 * 方法描述: 删除用户角色信息
	 */
	public boolean removeFp(JsglModel model)  {
		dao.scJsfpyhxxSingle(model);
		return true;
	}

	public IYhjsfwDao getSjfwDao() {
		return sjfwDao;
	}

	public void setSjfwDao(IYhjsfwDao sjfwDao) {
		this.sjfwDao = sjfwDao;
	}

	public IYhglDao getYhglDao() {
		return yhglDao;
	}

	public void setYhglDao(IYhglDao yhglDao) {
		this.yhglDao = yhglDao;
	}

	@Override
	public PageList<NewJsglModel> getJsglModelList(NewJsglModel model) {
		PageList<NewJsglModel> pageList = new PageList<NewJsglModel>();
		Paginator paginator = new Paginator();
		if(model!=null){
			paginator.setItemsPerPage(model.getPerPageSize());
			paginator.setPage((Integer)model.getToPage());
			paginator.setItems(dao.getJsglModeCount(model));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				model.setStartRow(paginator.getBeginIndex());
				model.setEndRow(paginator.getEndIndex());
				List<NewJsglModel> list = dao.getJsglModeList(model);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	@Override
	public void remove(String jsdm) {
		JsglModel model = new JsglModel();
		model.setJsdm(jsdm);
		dao.scJsfpyhxx(model);
		dao.batchNewDelete(jsdm);
	}

	@Override
	public void insert(NewJsglModel newJsglQuery) {
		JsglModel model = new JsglModel();
		model.setJsdm(newJsglQuery.getJsdm());
		model.setJsmc(newJsglQuery.getJsmc());
		model.setSfejsq("1");
		model.setJscjr("hrmadmin");
		dao.insertNew(model);
	}

	@Override
	public void update(NewJsglModel newJsglQuery) {
		JsglModel model = new JsglModel();
		model.setJsdm(newJsglQuery.getJsdm());
		model.setJsmc(newJsglQuery.getJsmc());
		dao.updateNew(model);
	}

	/**
	 * 
	 * 方法描述: 查询单个角色功能权限代码 参数 @return 参数说明 返回类型6.0.1重构版本
	 */
	public List<JsglModel> cxJsgnqxListNew(JsglModel model)  {
		return dao.cxJsgnqxListNew(model);
	}

	/**
	 * 为当前角色增加
	 */
	@Override
	public void insertMenuByJsdm(Map<String, Object> param) {
		dao.insertMenuByJsdm(param);
	}

	/**
	 * 为当前角色删除菜单
	 */
	@Override
	public void deleteMenuByJsdm(Map<String, Object> param) {
		dao.deleteMenuByJsdm(param);
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.service.svcinterface.IJsglService#getAllJsglist(com.zfsoft.dao.entities.NewJsglModel)
	 */
	@Override
	public List<NewJsglModel> getAllJsglist(NewJsglModel model) {
		return dao.getAllJsglist(model);
	}

}
