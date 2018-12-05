package com.zfsoft.globalweb.action;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;
import com.zfsoft.common.action.BaseAction;
import com.zfsoft.common.log.User;
import com.zfsoft.common.query.QueryModel;
import com.zfsoft.common.service.BaseLog;
import com.zfsoft.dao.entities.BmdmModel;
import com.zfsoft.dao.entities.DczdpzModel;
import com.zfsoft.dao.entities.JsglModel;
import com.zfsoft.dao.entities.NewJsglModel;
import com.zfsoft.dao.entities.YhglModel;
import com.zfsoft.dao.entities.YhglModelNew;
import com.zfsoft.dao.entities.YhjsfwModel;
import com.zfsoft.dao.entities.YhxxbModel;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.interest.entity.InterestEntity;
import com.zfsoft.mobile.servlet.entity.ListEntity;
import com.zfsoft.mobile.servlet.entity.ResultEntity;
import com.zfsoft.service.impl.LogEngineImpl;
import com.zfsoft.service.svcinterface.IBmdmService;
import com.zfsoft.service.svcinterface.IJsglService;
import com.zfsoft.service.svcinterface.ILoginService;
import com.zfsoft.service.svcinterface.IYhglService;
import com.zfsoft.service.svcinterface.IYhjsfwService;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.CodeUtil;
import com.zfsoft.util.base.MessageUtil;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.encrypt.Encrypt;

/**
 * 
 * 
 * 类名称：YhglAction 类描述： 用户管理控制 创建人：Administrator 创建时间：2012-4-10 下午06:41:27
 * 修改人：Administrator 修改时间：2012-4-10 下午06:41:27 修改备注：
 * 
 * @version
 * 
 */
public class YhglAction extends BaseAction implements ModelDriven<YhglModel> {
	
	private static Logger logger = Logger.getLogger(YhglAction.class);

	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = 1L;

	private YhglModel model = new YhglModel();
	private YhglModelNew yhglModelQuery = new YhglModelNew();

	private DczdpzModel dcmodel = new DczdpzModel();

	private JsglModel jsglmodel = new JsglModel();

	private IYhglService yhglService;

	private IJsglService jsglService;
	
	private NewJsglModel NewJsglQuery = new NewJsglModel();
	
	public NewJsglModel getNewJsglQuery() {
		return NewJsglQuery;
	}

	public void setNewJsglQuery(NewJsglModel newJsglQuery) {
		NewJsglQuery = newJsglQuery;
	}

	private IYhjsfwService yhjsfwService;
	
	private IBmdmService bmdmService;
	
	private PageList<YhglModelNew> yhglModelList = new PageList<YhglModelNew>();
	
	private BaseLog baseLog = LogEngineImpl.getInstance();
	
	//后台解绑账户设备
	private String zgh = "";
	
	private ILoginService loginService;
	

	/**
	 *查询类型表
	 * 
	 * @throws Exception
	 */
	public void setValueStack() throws Exception {
		ValueStack vs = getValueStack();

		// 查询部门列表
		//List<YhglModel> bmdmList = yhglService.getModelList(model);
		//vs.set("bmdmList", bmdmList);

		User user=getUser();
		JsglModel jsglModel=new JsglModel();
		jsglModel.setIsAdmin(user.isAdmin());
		jsglModel.setJscjr(user.getYhm());
		
		// 查询角色列表
		List<JsglModel> jsxxList = yhglService.cxJsdmList(jsglModel);
		vs.set("jsxxList", jsxxList);

		int size = jsxxList.size();
		vs.set("col", size > 4 ? (size % 4 == 0 ? size / 4 : (size / 4 + 1)): 1);

		// 岗位级别列表
		//vs.set("gwjbList", jsglService.cxGwjbList());
	}
	
	/**
	 * 查询机构代码类型库
	 */
	public void setJgdmStack() throws Exception{
		ValueStack vs = getValueStack();
		//查询部门代码列表
		List<BmdmModel> jgdmsList = bmdmService.getModelList(new BmdmModel());
		vs.set("jgdmsList", jgdmsList);
	}

	/**
	 * 
	 * 方法描述: 用户信息查询 参数 @return 参数说明 返回类型 String 返回类型
	 * 
	 * @throws
	 *//*
	public String cxYhxx1() {
		try {
			if (QUERY.equals(model.getDoType())) {
				QueryModel queryModel = model.getQueryModel();
				queryModel.setItems(yhglService.getPagedList(model));
				getValueStack().set(DATA, queryModel);
				return DATA;
			}
			setJgdmStack();
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return "cxYhxx";
	}*/
	
	
	
	
	public void yhxxList() throws Exception{
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		Gson gson = new Gson();
		
		try {
			PrintWriter out = response.getWriter();
			
			String apptoken = null; 
			String start = null;
			String size = null;
			String js = null;
			String xm = null;//搜索条件
			String zgh = null;//搜索条件
			
			apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			start = StringUtil.isEmpty(request.getParameter("start")) ? "" : request.getParameter("start");
			size = StringUtil.isEmpty(request.getParameter("size")) ? "" : request.getParameter("size");
			js = StringUtil.isEmpty(request.getParameter("js")) ? "" : request.getParameter("js");
			xm = StringUtil.isEmpty(request.getParameter("xm")) ? "" : request.getParameter("xm");
			zgh = StringUtil.isEmpty(request.getParameter("zgh")) ? "" : request.getParameter("zgh");
			
			if(!ApptokenUtils.compare(apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			try {
				xm     = CodeUtil.decode(xm, apptoken);
				zgh     = CodeUtil.decode(zgh, apptoken);
				js     = CodeUtil.decode(js, apptoken);
				start        = CodeUtil.decode(start, apptoken);
				size         = CodeUtil.decode(size, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			YhxxbModel yhQuery = new YhxxbModel();
			yhQuery.setToPage(Integer.valueOf(start));
			yhQuery.setPerPageSize(Integer.valueOf(size));
			yhQuery.setJs(js);
			yhQuery.setXm(xm);
			yhQuery.setZgh(zgh);
			
			List<YhxxbModel> list = yhglService.getYhxxbPagedList(yhQuery);
			
			ListEntity<YhxxbModel> resultList = new ListEntity<YhxxbModel>();
			resultList.setItemList(list);
			if(list == null || list.size() < Integer.valueOf(size))	{
				resultList.setOvered(true);
			}else{
				resultList.setOvered(false);
			}
			ResultEntity<ListEntity<YhxxbModel>> result = new ResultEntity<ListEntity<YhxxbModel>>(1, "成功", resultList);
			System.out.println(gson.toJson(result));
			out.write(gson.toJson(result));
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.error("yhxxList......error");
			e.printStackTrace();
		}
		
	}
	
	
	
	public void yhxx() throws Exception{
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		Gson gson = new Gson();
		
		try {
			PrintWriter out = response.getWriter();
			
			String apptoken = null; 
			String zgh = null;//搜索条件
			
			apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			zgh = StringUtil.isEmpty(request.getParameter("zgh")) ? "" : request.getParameter("zgh");
			
			if(!ApptokenUtils.compare(apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			try {
				zgh     = CodeUtil.decode(zgh, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			
			YhxxbModel yhModel = new YhxxbModel();
			yhModel.setZgh(zgh);

			// 查询单个用户信息
			YhxxbModel model = yhglService.getYhxxModel(yhModel);
			
			ResultEntity<YhxxbModel> result = new ResultEntity<YhxxbModel>(1, "成功", model);
			System.out.println(gson.toJson(result));
			out.write(gson.toJson(result));
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.error("yhxx......error");
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	public String cxYhxx() {
		yhglModelList = yhglService.getPagedListNew(yhglModelQuery);
//		NewJsglModel model = new NewJsglModel();
		//model.setJsdm(yhglModelQuery.getJsdm());
		List<NewJsglModel> jsList = jsglService.getAllJsglist(new NewJsglModel());
		getValueStack().set("jsList", jsList);
		return "cxYhxx";
	}
	
	
	public void jbsbh(){
		Map<String,Object> result = new HashMap<String,Object>();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			boolean flag = yhglService.jbsbh(model);
			if(flag){
				result.put("status","success");
				result.put("msg","解绑设备号成功");
			}else{
				result.put("status","fail");
				result.put("msg","解绑设备号失败");
			}
		}catch(Exception e){
			e.printStackTrace();
		} 
		out.write(JSONObject.fromObject(result).toString());
		
	}
	
	public void jbyh(){
		Map<String,Object> result = new HashMap<String,Object>();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			boolean flag = yhglService.jbyh(model);
			if(flag){
				result.put("status","success");
				result.put("msg","解绑用户成功");
			}else{
				result.put("status","fail");
				result.put("msg","解绑用户失败");
			}
		}catch(Exception e){
			e.printStackTrace();
		} 
		out.write(JSONObject.fromObject(result).toString());
	}
	
	
	public String bdsbh(){
		
		return "bdsbh";
	}
	
	
	public void bcsbh(){
		
		Map<String,Object> result = new HashMap<String,Object>();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		
		try {
			out = response.getWriter();
			
			if( !StringUtils.isEmpty(model.getZgh()) && !StringUtils.isEmpty(model.getDeviceid()) ){
				
				//查询用户信息，判断设备号是否已绑定
				YhglModel yh =  yhglService.getYhglModelByZgh(model.getZgh());
				
				if( yh!=null && !StringUtils.isEmpty(yh.getRylx()) ){
					
					if( !StringUtils.isEmpty(yh.getDeviceid()) ){
						//
						result.put("status","fail");
						result.put("msg","该账号已有绑定设备号，绑定失败");
					}else{
						List<YhglModel> yhModelList = yhglService.getUserByDeviceidAndRylx(model.getDeviceid(),yh.getRylx());
						
						if( yhModelList!=null && yhModelList.size()>0 ){
							result.put("status","fail");
							result.put("msg","该设备已绑定过账号，绑定设备号失败");
						}else{
							yhglService.bcsbh(model);
							result.put("status","success");
							result.put("msg","绑定设备号成功");
						}
					}
					
				}else{
					result.put("status","fail");
					result.put("msg","该用户的类型为空，绑定设备号失败");
				}
				
				
				
			}else{
				result.put("status","fail");
				result.put("msg","设备号或职工号不能为空，绑定设备号失败");
			}
		}catch(Exception e){
			e.printStackTrace();
		} 
		out.write(JSONObject.fromObject(result).toString());
	}
	
	
	
	/**
	 * 
	* @author: zhangxu
	* @Title: fpjs 
	* @Description: 为用户分配角色 
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public String fpjs(){
		//yhglModelQuery = yhglService.getPagedListNew(yhglModelQuery).get(0);
		PageList<NewJsglModel> JsglModelList = jsglService.getJsglModelList(NewJsglQuery);
		getValueStack().set("JsglModelList", JsglModelList);
		return "fpjs";
	}
	
	public String updateJs(){
		yhglService.updateJs(yhglModelQuery);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		map.put("text", "操作成功");
		getValueStack().set(DATA, map);
		return DATA;
	}

	/**
	 * 
	 * 方法描述: 增加用户信息 参数 @return 参数说明 返回类型 String 返回类型
	 * 
	 * @throws
	 */
	public String zjYhxx() {
		try {
			model.setSfqy("1");

			setValueStack();

		} catch (Exception e) {
			logException(e);
			return ERROR;
		}

		return "zjYhxx";
	}

	/**
	 * 
	 * 方法描述: 保存增加用户信息 参数 @return 参数说明 返回类型 String 返回类型
	 * 
	 * @throws
	 */
	public String zjBcYhxx() {
		try {
			User user = getUser();
			// 密码加密
			model.setMm(Encrypt.encrypt(model.getMm()));
			boolean result = yhglService.zjYhxx(model);
			String key = result ? "I99001" : "I99002";
			getValueStack().set("result", getText(key));
			setValueStack();
			if (result) {

				// 记操作日志
				
				baseLog.insert(user, getText("log.message.ywmc",
				 new String[] { "用户管理", "XG_XTGL_YHB" }),
				"系统管理", getText("log.message.czms", new
				String[] { "新增用户", "职工号", model.getZgh() }));
				

			}
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}

		return "zjYhxx";
	}

	/**
	 * 
	 * 方法描述: 修改用户信息 参数 @return 参数说明 返回类型 String 返回类型
	 * 
	 * @throws
	 */
	public String xgYhxx() {
		try {
			ValueStack vs = getValueStack();
			YhglModel yhglModel = new YhglModel();

			// 查询单个用户信息
			yhglModel = yhglService.getModel(model);

			try {
				BeanUtils.copyProperties(model, yhglModel);
			} catch (Exception e) {
				e.printStackTrace();
			}
			setValueStack();
			setJgdmStack();
			vs.set("model", model);

		} catch (Exception e) {
			logException(e);
			return ERROR;
		}

		return "xgYhxx";
	}

	/**
	 * 
	 * 方法描述: 保存修改用户信息 参数 @return 参数说明 返回类型 String 返回类型
	 * 
	 * @throws
	 */
	public String xgBcYhxx() {
		try {

			User user = this.getUser();
			ValueStack vs = getValueStack();

			// 修改用户信息
			boolean result = yhglService.xgYhxx(model);
			String key = result ? "I99001" : "I99002";
			getValueStack().set("result", getText(key));

			setValueStack();
			vs.set("model", model);
			if (result) {
				// 记操作日志
				baseLog.update(user, getText("log.message.ywmc",
				 new String[] { "用户管理", "XG_XTGL_YHB" }),
				 "系统管理", getText("log.message.czms", new
				 String[] { "修改用户", "职工号", model.getZgh() }));
			}
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}

		return "xgYhxx";
	}

	/**
	 * 
	 * 方法描述: 删除用户信息 参数 @return 参数说明 返回类型 String 返回类型
	 * 
	 * @throws
	 */
	public String scYhxx() throws Exception {
		User user = getUser();
		String pks = getRequest().getParameter("ids");
		model.setPkValue(pks);
		boolean result = yhglService.scYhxx(model);
		String key = result ? "I99005" : "I99006";
		getValueStack().set(DATA, getText(key));
		if (result) {
			// 记操作日志
			String opDesc = getText("log.message.czms", new String[] {
					"批量删除用户", "职工号", pks });

			
			baseLog.delete(user, getText("log.message.ywmc", new
			  String[] { "用户管理", "XG_XTGL_YHB" }), "系统管理",
			  opDesc);
			 
		}
		return DATA;
	}

	/**
	 * 
	 * 方法描述: 查看用户信息 参数 @return 参数说明 返回类型 String 返回类型
	 * 
	 * @throws
	 */
	public String ckYhxx() {
		try {
			ValueStack vs = getValueStack();
			YhglModel yhglModel = new YhglModel();
			// 查询单个用户信息
			yhglModel = yhglService.getModel(model);

			try {
				BeanUtils.copyProperties(model, yhglModel);
			} catch (Exception e) {
				e.printStackTrace();
			}
			vs.set("model", model);

		} catch (Exception e) {
			logException(e);
			return ERROR;
		}

		return SUCCESS;
	}

	/**
	 * 
	 * 方法描述: 设置所属角色 参数 @return 参数说明 返回类型 String 返回类型
	 * 
	 * @throws
	 */
	public String szssjsYh() throws Exception {
		ValueStack vs = getValueStack();
		try {
			// 查询单个用户信息
			YhglModel yhglModel = new YhglModel();
			yhglModel = yhglService.getModel(model);
			// 查询角色对应数据范围
			Map<String,String> map = new HashMap<String,String>();
			map.put("yh_id", model.getZgh());
			List<YhjsfwModel> yhjsModels = yhjsfwService.cxSjfwYh(map);

			try {
				BeanUtils.copyProperties(model, yhglModel);
			} catch (Exception e) {
				logException(e);
			}
			
			User user=getUser();
			JsglModel jsglModel=new JsglModel();
			jsglModel.setIsAdmin(user.isAdmin());
			jsglModel.setJscjr(user.getYhm());
			
			List<JsglModel> jsxxList = yhglService.cxJsdmList(jsglModel);
			int size = jsxxList.size();
			vs.set("col", size > 4 ? (size % 4 == 0 ? size / 4 : (size / 4 + 1)): 1);
			//循环取出角色数据范围,设置到数据范围字段中,供页面使用
			StringBuilder builder = null;
			for (JsglModel jsgl : jsxxList) {
				builder = new StringBuilder();
				for (YhjsfwModel model : yhjsModels) {
					if (model.getJs_id().equals(jsgl.getJsdm())) {
						builder.append(model.getSjfwzmc()).append(";");
					}
				}
				jsgl.setSjfwzmc(builder.toString());
			}
			vs.set("jsxxList", jsxxList);
			vs.set("yhjsModels", yhjsModels);

		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return "szSsjs";
	}

	/**
	 * 
	 * 方法描述: 保存设置所属角色 参数 @return 参数说明 返回类型 String 返回类型
	 * 
	 * @throws
	 */
	public String szssjsSaveYh() throws Exception {
		try {
			User user = getUser();

			// 修改用户所属角色
			boolean result = yhglService.szSsjs(model);			
			String key = result ? "I99001" : "I99002";
			getValueStack().set("result", getText(key));
			// 查询单个用户信息
			YhglModel yhglModel = new YhglModel();
			yhglModel = yhglService.getModel(model);			
			BeanUtils.copyProperties(model, yhglModel);
			// 查询角色对应数据范围
			Map<String,String> map = new HashMap<String,String>();
			map.put("yh_id", model.getZgh());
			List<YhjsfwModel> yhjsModels = yhjsfwService.cxSjfwYh(map);
			getValueStack().set("yhjsModels", yhjsModels);
			
			setValueStack();

			if (result) {
				// 记操作日志
				String opDesc = getText("log.message.czms", new String[] {
						"设置角色", "职工号", getRequest().getParameter("zgh") });

				
				baseLog.update(user, getText("log.message.ywmc", new
				  String[] { "用户管理", "XG_XTGL_YHB" }),
				  "系统管理", opDesc);
				 

			}
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}

		return "szSsjs";
	}

	/**
	 * 方法描述：选择密码初始化规则
	 */
	public String mmcshgz() throws Exception {
		String pkValue = this.getRequest().getParameter("pkValue");
		this.getValueStack().set("pkValue", pkValue);
		return "mmCshgz";
	}
	/**
	 * 
	 * 方法描述: 密码初始化 参数 @return 参数说明 返回类型 String 返回类型
	 * 
	 * @throws
	 */
	public String mmcsh() throws Exception {
		String cshgz = this.getRequest().getParameter("cshgz");    //初始化规则
		String zgh = this.getRequest().getParameter("pkValue");    //初始化的教职工号 

		String msg="";
		if(cshgz.equals("zjhm")){                                  //按身份证号后6位初始化
			String zjhm=yhglService.zjhmByZgh(zgh);
			if(zjhm==null||zjhm.length()<6){
				msg=MessageUtil.getText("I99013");
				getValueStack().set(DATA, getText(msg));
				return DATA;
			}else{
				String subZjhm = zjhm.substring(zjhm.length()-6);     //取证件号码后6位
				model.setMm(Encrypt.encrypt(subZjhm));				
			}
		}
		else{                                                     //按指定密码初始化
			String zdmm = this.getRequest().getParameter("zdmm");
			model.setMm(Encrypt.encrypt(zdmm));
		}
		// 对初始化的密码加密
		User user = getUser();
		boolean result = yhglService.mmCsh(model);
		if(result){
			msg=MessageUtil.getText("I99010");
		}else{
			msg=MessageUtil.getText("I99011");
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("success", true);
		data.put("html", getText(msg));
		getValueStack().set(DATA, data);
		if (result) {
			// 记操作日志			
			baseLog.update(user, getText("log.message.ywmc", new
			  String[] { "用户管理", "XG_XTGL_YHB" }), "系统管理",
			  getText("log.message.czms", new String[] { "密码初始化", "职工号",
			  model.getPkValue() }));
		}
		return DATA;
	}
	
	/**
	 * 方法描述：选择批量密码初始化规则
	 */
	public String plmmcshgz() throws Exception {
		return "plmmCshgz";
	}
	
	/**
	 * 
	 * 方法描述: 批量密码初始化 参数 @return 参数说明 返回类型 String 返回类型
	 * 
	 * @throws
	 */
	public String plmmcsh() throws Exception {
		String cshgz = this.getRequest().getParameter("cshgz");    //初始化规则
		List<String> zghList=null;
		zghList = yhglService.zghList();		
		String msg="";
		int i;													   //计算批量初始化条数
		
		if(cshgz.equals("zjhm")){                                  //按身份证号后6位初始化
			i=0;
			for (String zgh:zghList){
				if(zgh.equals(YhglModel.INNER_USER_ADMIN)){
					continue;
				}else{
					String zjhm=yhglService.zjhmByZgh(zgh);
					if(zjhm==null||zjhm.length()<6){
						continue;
					}else{
						String subZjhm = zjhm.substring(zjhm.length()-6);     //取证件号码后6位
						model.setPkValue(zgh);
						model.setMm(Encrypt.encrypt(subZjhm));	  //加密字符串
						yhglService.mmCsh(model);   			  //修改密码加密串
						i++;
					}
				}
				msg=getText("log.message.czms",new String[]{"初始化成功","共",i+""+"条"});
				getValueStack().set(DATA, getText(msg));
			}
		}
		else{                                                     //按指定密码初始化
			i=0;
			String zdmm = this.getRequest().getParameter("zdmm");
			for (String zgh:zghList){
				if(zgh.equals(YhglModel.INNER_USER_ADMIN)){
					continue;
				}else{
					model.setPkValue(zgh);
					model.setMm(Encrypt.encrypt(zdmm));       //加密字符串
					yhglService.mmCsh(model);                 //修改密码加密串
					i++;
				}
			}
			msg=getText("log.message.czms",new String[]{"初始化成功","共",i+""+"条"});
			getValueStack().set(DATA, getText(msg));
		}		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("success", true);
		data.put("html", getText(msg));
		getValueStack().set(DATA, data);
		// 对初始化的密码记录日志
		User user = getUser();					
		baseLog.update(user, getText("log.message.ywmc", new
		  String[] { "用户管理", "XG_XTGL_YHB" }), "系统管理",
		  getText("log.message.czms", new String[] { "密码批量初始化", "共",i+""+"条"}));
		return DATA;
	}

	/**
	 * 密码修改
	 * 
	 * @return
	 * @throws Exception
	 */
	public String xgMm() throws Exception {
		if (OPER_SAVE.equalsIgnoreCase(getRequest().getParameter("doType"))) {
			User user = getUser();
			model.setZgh(user.getYhm());
			Map<String, Object> data = new HashMap<String, Object>();
			// 查询单个用户信息
			YhglModel yhglModel = yhglService.getModel(model);

			ValueStack vs = getValueStack();
			if (!Encrypt.encrypt(model.getYmm()).equals(yhglModel.getMm())) {
				data.put("success", false);
				data.put("html", "原密码错误，请重新输入！");
				vs.set(DATA, data);
				return DATA;
			}
			boolean boo = yhglService.xgMm(model);
			if (boo) {
				data.put("success", true);
				data.put("html", getText("修改成功，请重新登录！"));
				vs.set(DATA, data);

				// 记操作日志
				
				baseLog.update(user, getText("log.message.ywmc", new
				  String[] { "用户管理", "XG_XTGL_YHB" }),
				  "系统管理", getText("log.message.czms", new
				  String[] { "密码修改", "职工号", user.getYhm() }));
				return DATA;
				 
			} else {
				data.put("success", false);
				data.put("html", "修改失败！");
				vs.set(DATA, data);
			}
		}
		return SUCCESS;
	}

	/**
	 * 
	 * 方法描述: 根据角色查看所分配的用户 参数 @return 参数说明 返回类型 String 返回类型
	 * 
	 * @throws
	 */
	public String ckJsyh() {
		try {
			ValueStack vs = getValueStack();
			JsglModel jsglModel = new JsglModel();

			jsglModel.setJsdm(getRequest().getParameter("jsdm"));

			// 根据角色代码得到角色名称
			jsglModel = yhglService.cxJsmcByJsdm(jsglModel);

			// 根据角色代码查询所属用户
			List<YhglModel> yhList = yhglService.cxYhByJsdm(model);
			vs.set("yhList", yhList);

			int size = yhList.size();
			vs.set("col",
					size > 4 ? (size % 4 == 0 ? size / 4 : (size / 4 + 1)) : 1);

			BeanUtils.copyProperties(model, jsglModel);
			vs.set("model", model);

		} catch (Exception e) {
			logException(e);
			return ERROR;
		}

		return SUCCESS;
	}
	
	/**
	 * 方法描述: 批量数据授权 
	 * 参数 @return 参数说明 
	 * 返回类型 String 返回类型
	 * @throws
	 */
	public String cxPlsjsq(){
		try {
			if (QUERY.equals(model.getDoType())) {
				QueryModel queryModel = model.getQueryModel();
				queryModel.setItems(yhglService.getPagedList(model));
				getValueStack().set(DATA, queryModel);
				return DATA;
			}
			//查询所有角色代码
			setValueStack();
			//查询所有机构
			setJgdmStack();
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return "cxPlsjsq";
	}
	
	/**
	 * 
	 * 方法描述: 验证职工号是否已经存在 参数 @return 参数说明 返回类型 String 返回类型
	 * 
	 * @throws
	 */
	public String valideZgh() throws Exception {
		YhglModel yhglModel = new YhglModel();
		yhglModel.setZgh(getRequest().getParameter("pkValue"));
		// 查询单个用户信息
		yhglModel = yhglService.getModel(yhglModel);

		if (null != yhglModel) {
			getValueStack().set("data", "该职工号已经存在!");
		}
		return "data";
	}
	
	/**
	 * 
	 * 方法描述: 查询所有机构代码
	 * @throws
	 */
	public String cxJgdms(){
		try {
			if (QUERY.equals(model.getDoType())) {
				QueryModel queryModel = model.getQueryModel();
				BmdmModel bmdmModel = new BmdmModel();
				bmdmModel.setQueryModel(queryModel);
				String bmmc = super.getRequest().getParameter("bmmc");
				bmdmModel.setBmmc(bmmc);
				queryModel.setItems(bmdmService.getPagedList(bmdmModel));
				getValueStack().set(DATA, queryModel);
				return DATA;
			}
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}		
		
		return "cxJgdms";
	}
	

	
	/**
	 * 
	 * 方法描述: 用户账号设备绑定信息列表
	 * @throws
	 */
	public String bingUserList() {
		User user = getUser();
		if (user.getGrade() == null) {
			this.getValueStack().set("msg", "请先设置登录账号所属的学院");
			return "bingUserList";
		}else{
			yhglModelQuery.setGrade(user.getGrade());
			yhglModelList = yhglService.getPagedListNew(yhglModelQuery);
			this.getValueStack().set("msg", "1");
			return "bingUserList";
		}
	}
	
	
	/**
	 * 
	 * 方法描述: 对该用户设备解绑
	 * @throws
	 */
	public String unBingUser() {
		yhglService.unbindDeviceId(yhglModelQuery.getZgh());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		map.put("text", "操作成功");
		getValueStack().set(DATA, map);
		return DATA;
	}
	
	
	

	
	/**
	 * 切换用户启用状态
	 */
	public void switchYhQy(){
		Map<String,Object> result = new HashMap<String,Object>();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			boolean flag = yhglService.update(model);
			if(flag){
				result.put("status","success");
				result.put("msg","切换状态成功");
			}else{
				result.put("status","fail");
				result.put("msg","切换状态失败");
			}
		}catch(Exception e){
			e.printStackTrace();
		} 
		out.write(JSONObject.fromObject(result).toString());
	}
	

	public YhglModel getModel() {
		return model;
	}

	public IYhglService getYhglService() {
		return yhglService;
	}

	public void setYhglService(IYhglService yhglService) {
		this.yhglService = yhglService;
	}

	public IJsglService getJsglService() {
		return jsglService;
	}

	public void setJsglService(IJsglService jsglService) {
		this.jsglService = jsglService;
	}

	public JsglModel getJsglmodel() {
		return jsglmodel;
	}

	public DczdpzModel getDcmodel() {
		return dcmodel;
	}

	public void setDcmodel(DczdpzModel dcmodel) {
		this.dcmodel = dcmodel;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public IYhjsfwService getYhjsfwService() {
		return yhjsfwService;
	}

	public void setYhjsfwService(IYhjsfwService yhjsfwService) {
		this.yhjsfwService = yhjsfwService;
	}

	public IBmdmService getBmdmService() {
		return bmdmService;
	}

	public void setBmdmService(IBmdmService bmdmService) {
		this.bmdmService = bmdmService;
	}

	public void setYhglModelList(PageList<YhglModelNew> yhglModelList) {
		this.yhglModelList = yhglModelList;
	}

	public PageList<YhglModelNew> getYhglModelList() {
		return yhglModelList;
	}

	public void setYhglModelQuery(YhglModelNew yhglModelQuery) {
		this.yhglModelQuery = yhglModelQuery;
	}

	public YhglModelNew getYhglModelQuery() {
		return yhglModelQuery;
	}

	public String getZgh() {
		return zgh;
	}
	public void setZgh(String zgh) {
		this.zgh = zgh;
	}

	public ILoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(ILoginService loginService) {
		this.loginService = loginService;
	}
	
	
}
