package com.zfsoft.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import com.zfsoft.common.service.BaseServiceImpl;
import com.zfsoft.dao.daointerface.IKlwhDao;
import com.zfsoft.dao.entities.XsmmModel;
import com.zfsoft.service.svcinterface.KlwhService;
import com.zfsoft.util.base.Excel2Oracle;
import com.zfsoft.util.encrypt.Encrypt;

/**
 * 类名称：KlwhServiceImpl 
 * 类描述： 基础数据业务处理实现类 创建人：xucy 
 * 创建时间：2012-4-13 下午01:44:39
 * @version
 * 
 */
public class KlwhServiceImpl extends BaseServiceImpl<XsmmModel, IKlwhDao>
		implements KlwhService {
	//单个密码初始化
	public boolean plCsh(XsmmModel model) throws Exception {
		boolean flag = false;
		model.setJmmm(Encrypt.encrypt(model.getMm()));// 加密
		int result = dao.update(model);
		flag = result > 0 ? true : false;
		return flag;
	}

	// 全部密码初始化
	public boolean qbCsh(XsmmModel model,HttpServletResponse response) throws Exception {
		boolean flag = false;
		List<XsmmModel> xslist = getXsxxList(model);
		List<XsmmModel> xsmmlist = new ArrayList<XsmmModel>();
		if (null != xslist && xslist.size() > 0) {
			for (int i = 0; i < xslist.size(); i++) {
				String pwd = getRandomNum(6);//随即密码
				XsmmModel xsmodel = xslist.get(i);
				xsmodel.setMm(pwd);
				model.setJmmm(Encrypt.encrypt(pwd));// 加密
				model.setPkValue(xsmodel.getXh());
				int result = dao.update(model);
				flag = result > 0 ? true : false;
				xsmmlist.add(xsmodel);
			}
			
			//导出到excel
			String zds[] = {"xh","mm"};
			String zdmcs[] = {"学号","密码"};
			String[] tmp = new String[zds.length];
			List<String[]> modellist = new ArrayList<String[]>();

			for (int i = 0; i < xsmmlist.size(); i++) {
				for (int j = 0; j < zds.length; j++) {
					Object object = xsmmlist.get(i);
					String zdm = zds[j];
					String get = "get";
					String first = zdm.substring(0, 1);
					String last = zdm.substring(1, zdm.length());
					String firstToup = first.toUpperCase();
					String parm = get + firstToup + last;
					Class myClass = object.getClass();
					String obj = (String) myClass.getMethod(parm).invoke(
							object);
					tmp[j] = obj;
				}
				modellist.add(tmp);
				tmp = new String[zds.length];
			}

			response.reset();

			response.setHeader("Content-Disposition",
					"attachment; filename=exportData.xls");
			response.setContentType("application/vnd.ms-excel");
			Excel2Oracle.exportData(modellist, zds, zdmcs, response
					.getOutputStream());
			
		}
		return flag;
	}

	public List<XsmmModel> getXsxxList(XsmmModel model) throws Exception {
		return dao.getXsxxList(model);
	}
	
	//产生随机数
	public static String getRandomNum(int len){
		int i;
		int count = 0;
		char[] str = {'a','b','c','d','e','f','g','h','i','j','k','l','m',
				'n','o','p','q','r','s','t','u','v','w','x','y','z','0','1'
				,'2','3','4','5','6','7','8','9'};
		StringBuffer pwd = new StringBuffer();
		Random r = new Random();
		while(count<len){
			i=Math.abs(r.nextInt(36));
			if(i>=0&&i<str.length){
				pwd.append(str[i]);
				count++;
			}
		}
		return pwd.toString();
	}
}
