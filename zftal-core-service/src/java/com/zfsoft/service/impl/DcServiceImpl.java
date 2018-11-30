package com.zfsoft.service.impl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.zfsoft.common.exception.ServiceException;
import com.zfsoft.common.log.User;
import com.zfsoft.common.service.BaseServiceImpl;
import com.zfsoft.dao.daointerface.IDcDao;
import com.zfsoft.dao.entities.DczdpzModel;
import com.zfsoft.service.svcinterface.IDcService;
import com.zfsoft.util.base.Excel2Oracle;

/**
 * 
 * 
 * 类名称：DcServiceImpl 类描述： 导出Service 创建人：xucy 创建时间：2012-4-24 上午08:41:27 修改人：xucy
 * 修改时间：2012-4-24 上午08:41:27 修改备注：
 * 
 * @version
 * 
 */
public class DcServiceImpl extends BaseServiceImpl<DczdpzModel, IDcDao>
		implements IDcService {

	// 查询所有导出字段
	public List<DczdpzModel> cxDczdList(DczdpzModel model, User user)
			 {
		model.setZgh(user.getYhm());
		List<DczdpzModel> listByZgh = dao.cxDczdListByZgh(model);//职工号下选中数据
		List<DczdpzModel> lists =new ArrayList<DczdpzModel>();
		if (listByZgh != null && listByZgh.size() > 0) {
			for(int i =0;i<listByZgh.size();i++){
				DczdpzModel dcmodel = listByZgh.get(i);
				lists.add(dcmodel);
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("dcclbh", model.getDcclbh());
			param.put("list", lists);
			
			List<DczdpzModel> listByPublic  = dao.cxDczdListByPublic(param);//非选中数据
			
			listByZgh.addAll(listByPublic);
			return listByZgh;
		} else {
			List<DczdpzModel> list = dao.cxDczdList(model);
			return list;
		}
	}

	// 查询需要导出字段
	public List<DczdpzModel> cxZdList(DczdpzModel model, User user)
			 {
		model.setZgh(user.getYhm());
		List<DczdpzModel> list = dao.cxZdListByZgh(model);
		if (list == null || list.size() == 0) {
			list = dao.cxZdList(model);
		}
		return list;
	}

	// 查询默认字段
	public DczdpzModel cxMrzd(DczdpzModel model, User user)  {
		model.setZgh(user.getYhm());
		DczdpzModel mrzmodel = dao.cxMrzdByZgh(model);
		if (mrzmodel == null) {
			mrzmodel = dao.cxMrzd(model);
		}
		return mrzmodel;
	}

	// 查询选择字段
	public DczdpzModel cxXzzd(DczdpzModel model, User user)  {
		model.setZgh(user.getYhm());
		DczdpzModel mrzmodel = dao.cxXzzdByZgh(model);
		if (mrzmodel == null) {
			mrzmodel = dao.cxXzzd(model);
		}
		return mrzmodel;
	}

	// 保存字段选择
	public boolean bcZdsz(DczdpzModel model, User user)  {
		boolean flag = true;
		String dczds[] = model.getDczdcbv();
		model.setZgh(user.getYhm());
		dao.scDczd(model);
		// 保存选中字段
		if (null != dczds && dczds.length > 0) {
			for (int i = 0; i < dczds.length; i++) {
				String zd = dczds[i];
				model.setZd(zd);
				model.setZgh(user.getYhm());
				dao.insert(model);
			}
		}
		return flag;
	}

	/**
	 * 
	 * 方法描述: 导出数据 参数 @return 参数说明 返回类型 String 返回类型
	 * 
	 * @throws
	 */
	// TODO 不建议在service层使用HTTP....，request,response,session 不要作为参数传到service
	// TODO 这里仅需要response.getOutputStream()，其它不与http相关的放在Action层
	public String dcSj(DczdpzModel model, List list, User user,
			HttpServletResponse response)  {

		List<DczdpzModel> dcmodellist = cxZdList(model, user);// 需要导出字段集合

		if (null != dcmodellist && dcmodellist.size() > 0) {

			String zds[] = new String[dcmodellist.size()];
			String zdmcs[] = new String[dcmodellist.size()];

			for (int m = 0; m < dcmodellist.size(); m++) {
				DczdpzModel dcmodel = dcmodellist.get(m);
				String zd = dcmodel.getZd();
				String zdmc = dcmodel.getZdmc();
				zds[m] = zd;
				zdmcs[m] = zdmc;
			}

			String[] tmp = new String[zds.length];
			List<String[]> modellist = new ArrayList<String[]>();

			for (int i = 0; i < list.size(); i++) {
				for (int j = 0; j < zds.length; j++) {
					Object object = list.get(i);
					String zdm = zds[j];
					String get = "get";
					String first = zdm.substring(0, 1);
					String last = zdm.substring(1, zdm.length());
					String firstToup = first.toUpperCase();
					String parm = get + firstToup + last;
					String parm1 = get + zdm;
					Class myClass = object.getClass();
					String obj = null;
					try {
						obj = (String) myClass.getMethod(parm1).invoke(object);
					} catch (IllegalArgumentException e) {
						throw new ServiceException(e.getMessage());
					} catch (SecurityException e) {
						throw new ServiceException(e.getMessage());
					} catch (IllegalAccessException e) {
						throw new ServiceException(e.getMessage());
					} catch (InvocationTargetException e) {
						throw new ServiceException(e.getMessage());
					} catch (NoSuchMethodException e) {
						throw new ServiceException(e.getMessage());
					}
					tmp[j] = obj;
				}
				modellist.add(tmp);
				tmp = new String[zds.length];
			}

			response.reset();

			response.setHeader("Content-Disposition",
					"attachment; filename=exportData.xls");
			response.setContentType("application/vnd.ms-excel");
			try {
				Excel2Oracle.exportData(modellist, zds, zdmcs, response.getOutputStream());
			} catch (IOException e) {
				throw new ServiceException(e.getMessage());
			} catch (Exception e) {
				throw new ServiceException(e.getMessage());
			}
		}
		return null;
	}

}
