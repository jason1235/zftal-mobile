package com.zfsoft.globalweb.action;



import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;
import com.zfsoft.common.action.BaseAction;
import com.zfsoft.common.log.User;
import com.zfsoft.common.query.QueryModel;
import com.zfsoft.common.service.BaseLog;
import com.zfsoft.dao.entities.JsglModel;
import com.zfsoft.dao.entities.NewJsglModel;
import com.zfsoft.dao.entities.YhglModel;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.service.impl.LogEngineImpl;
import com.zfsoft.service.svcinterface.IJsglService;
import com.zfsoft.service.svcinterface.IXsglfwService;
import com.zfsoft.service.svcinterface.IYhglService;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.collection.CollectionUtil;
import com.zfsoft.util.xml.BaseDataReader;



/**
 * 
 * 
 * 类名称：JsglAction 
 * 类描述： 角色管理控制 创建人：Administrator 
 * 创建时间：2012-4-1 下午03:46:07
 * 修改人：Administrator 
 * 修改时间：2012-4-1 下午03:46:07 
 * 修改备注：
 * 
 * @version
 * 
 */
public class JsglAction extends BaseAction implements ModelDriven<JsglModel> {

	/**
	 * @Fields serialVersionUID :
	 */

	private static final long serialVersionUID = 1L;
	private JsglModel model = new JsglModel();// 全局MODEL
	private NewJsglModel NewJsglQuery = new NewJsglModel();
	private IJsglService jsglService;//角色管理SERVICE
	private IYhglService yhglService;//用户管理SERVICE
	private IXsglfwService xsglfwService;//学生管理范围SERVICE
	private String op;
	
	private String sffpyh_qry;
	private String jsmc_qry;
	private String gnmkdm_qry;

	private BaseLog baseLog = LogEngineImpl.getInstance();
	
	

	public NewJsglModel getNewJsglQuery() {
		return NewJsglQuery;
	}

	public void setNewJsglQuery(NewJsglModel newJsglQuery) {
		NewJsglQuery = newJsglQuery;
	}

	public void setValueStack() throws Exception{
		ValueStack vs = getValueStack();
		List<HashMap<String, String>> sfList = BaseDataReader.getListOptions("isNot");
		vs.set("sfList", sfList);
	}
	
	/**
	 * 
	 * 方法描述: 角色查询 
	 * 参数 @return 
	 * 参数说明 
	 * 返回类型 String 返回类型
	 * 
	 * @throws
	 */
	public String cxJsxx1() {
		try {
			//TODO 建议每个Action动作写一个Action方法
			if (OPER_SAVE.equalsIgnoreCase(getRequest().getParameter("doType"))) {
				boolean result = jsglService.zjJsGnmkqxBatch(model);
				String key = result ? "I99001" : "I99002";
				getValueStack().set("result", getText(key));
			}

			if (QUERY.equals(model.getDoType())) {
				QueryModel queryModel = model.getQueryModel();
				User user=getUser();
				model.setIsAdmin(user.isAdmin());
				model.setJscjr(user.getYhm());
				queryModel.setItems(jsglService.getPagedList(model));
				getValueStack().set(DATA, queryModel);
				return DATA;
			}
			setValueStack();
			String key = getRequest().getParameter("message");
			getValueStack().set("message", getText(key));
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String cxJsxx(){
		PageList<NewJsglModel> JsglModelList = jsglService.getJsglModelList(NewJsglQuery);
		getValueStack().set("JsglModelList", JsglModelList);
		return "cxJsxx";
	}
	
	public String edit(){
		if(!StringUtil.isEmpty(NewJsglQuery.getJsdm())){
			op = "modify";
			NewJsglQuery = jsglService.getJsglModelList(NewJsglQuery).get(0);
			getValueStack().set("NewJsglQuery", NewJsglQuery);
		}else{
			op = "add";
		}
		return "edit";
	}
	
	public String insert(){
		jsglService.insert(NewJsglQuery);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		map.put("text", "操作成功");
		getValueStack().set(DATA, map);
		return DATA;
	}
	
	public String save(){
		if(op.equals("modify")){
			jsglService.update(NewJsglQuery);
		}else{
			JsglModel model = jsglService.getModel(NewJsglQuery.getJsdm());
			if(model != null){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("success", false);
				map.put("text", "已存在此角色代码");
				getValueStack().set(DATA, map);
				return DATA;
			}
			jsglService.insert(NewJsglQuery);
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		map.put("text", "操作成功");
		getValueStack().set(DATA, map);
		return DATA;
	}
	
	public String remove(){
		jsglService.remove(NewJsglQuery.getJsdm());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		map.put("text", "删除成功");
		getValueStack().set(DATA, map);
		return DATA;
	}

	/**
	 *  
	 * 方法描述: 增加角色 
	 * 参数 @return 
	 * 参数说明 
	 * 返回类型 String 返回类型
	 * 
	 * @throws
	 */
	public String zjJsxx() {

		try {
			setValueStack();
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 * 方法描述: 保存增加角色 
	 * 参数 @return 
	 * 参数说明 
	 * 返回类型 String 返回类型
	 * 
	 * @throws
	 */
	public String zjBcJsxx() { 

		try {
			User user = getUser();
			// 保存数据操作
			model.setJscjr(user.getYhm());
			boolean result = jsglService.insert(model);
			String key = result ? "I99001" : "I99002";
			getValueStack().set("result", getText(key));
			setValueStack();
			if (result) {

				// 记操作日志
				baseLog.insert(user, getText("log.message.ywmc",
						new String[] { "角色管理", "XG_XTGL_JSXXB" }),
						"系统管理",
						getText("log.message.czms", new String[] { "新增角色",
								"角色名称", model.getJsmc() }));

			}
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}

		return "zjJsxx";
	}

	/**
	 * 
	 * 方法描述: 修改角色 
	 * 参数 @return 
	 * 参数说明 
	 * 返回类型 String 返回类型
	 * 
	 * @throws
	 */
	public String xgJsxx() {
		try {
			//TODO 以下三行代码 JsglModel jsglModel = jsglService.getModel(model);
			JsglModel jsglModel = new JsglModel();
			jsglModel.setJsdm(getRequest().getParameter("jsdm"));

			// 查询单个角色信息
			jsglModel = jsglService.getModel(jsglModel);

			try {
				BeanUtils.copyProperties(model, jsglModel);
			} catch (Exception e) {
				e.printStackTrace();
			}

			setValueStack();
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 * 方法描述: 保存修改角色
	 * 参数 @return 
	 * 参数说明 
	 * 返回类型 String 返回类型
	 * 
	 * @throws
	 */
	public String xgBcJsxx() {
		try {
			User user = getUser();
			ValueStack vs = getValueStack();
			//TODO 以下三行代码 JsglModel jsglModel = jsglService.getModel(model);
			JsglModel jsglModel = new JsglModel();
			jsglModel.setJsdm(getRequest().getParameter("jsdm"));

			// 查询单个角色信息
			jsglModel = jsglService.getModel(jsglModel);

			// 保存角色操作信息
			// 默认角色的岗位是不能进行修改的
			if ("1".equalsIgnoreCase(model.getSfksc())) {
				model.setGwjbdm(getRequest().getParameter("bkscgwjbdm"));
			}
			boolean result = jsglService.update(model);
			String key = result ? "I99001" : "I99002";
			getValueStack().set("result", getText(key));

			setValueStack();
			vs.set("model", model);

			if (result) {

				// 记操作日志
				baseLog.update(user, getText("log.message.ywmc",
						new String[] { "角色管理", "XG_XTGL_JSXXB" }),
						"系统管理",
						getText("log.message.czms", new String[] { "修改角色",
								"角色名称", model.getJsmc() }));
			}
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}

		return "xgJsxx";
	}

	/**
	 * 
	 * 方法描述: 删除角色信息 
	 * 参数 @return 
	 * 参数说明 
	 * 返回类型 String 返回类型
	 * 
	 * @throws
	 */
	public String scJsxx() throws Exception {
		User user = getUser();
		// 删除角色信息
		String pks    = getRequest().getParameter("ids");
		model.setPkValue(pks);
		String result = jsglService.scJsdmxx(model);
		
		getValueStack().set(DATA, result);
			// 记操作日志
			String opDesc = getText("log.message.czms", new String[] {
					"批量删除角色", "角色代码", model.getPkValue() });

			baseLog.delete(user, getText("log.message.ywmc",
					new String[] { "角色管理", "XG_XTGL_JSXXB" }),
					"系统管理", opDesc);
		
		return DATA;
	}

	

	/**
	 * 
	 * 方法描述: 查询角色代码功能权限
	 * 参数 @return
	 * 参数说明
	 * 返回类型 String 返回类型
	 * 
	 * @throws
	 */
	public String cxJsdmGnqx() {
		try {
			getValueStack().set("data", jsglService.getJsgnqxStr(model));
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return "data";
	}

	/**
	 * @throws UnsupportedEncodingException 
	 * 
	 * 方法描述: 角色分配用户
	 * 管理 参数 @return 
	 * 参数说明 
	 * 返回类型 String 返回类型
	 * 
	 * @throws
	 */
	public String fpyhJs() throws UnsupportedEncodingException {
		String temp=getRequest().getParameter("jsmc_qry");
		if(StringUtils.isNotEmpty(temp)){
			temp=URLDecoder.decode(temp, "utf-8");
			this.jsmc_qry=temp;
		}
		ValueStack vs = getValueStack();
		try {
			// 查询单个角色信息
			JsglModel jsglModel = jsglService.getModel(model);
			YhglModel yhglModel = new YhglModel();
			BeanUtils.copyProperties(yhglModel, model);
			vs.set("model", jsglModel);// 角色信息
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}

		return SUCCESS;
	}

	/**
	 * 
	 * 方法描述: 保存角色分配用户管理 
	 * 参数 @return 参数说明 
	 * 返回类型 String 
	 * 返回类型
	 * 
	 * @throws
	 */
	public String fpyhSaveJs() {
		try {
			String param = super.getRequest().getParameter("param");
			if(StringUtil.isNotBlank(param)){
				String[] ids = param.split("&");
				model.setYhCbv(ids);
			}else{
				getValueStack().set(DATA, getText("I99001"));
				return DATA;
			}
			
			// 保存角色分配用户信息
			boolean result = jsglService.saveJsyhfpxx(model);
			String key = result ? "I99001" : "I99002";
			getValueStack().set(DATA, getText(key));
			
			if (result) {
				// 记操作日志
				baseLog.update(getUser(), getText("log.message.ywmc",
						new String[] { "角色管理", "XG_XTGL_JSXXB" }),
						"系统管理", getText("log.message.czms",
								new String[] { "角色分配用户", "角色代码",
										model.getJsdm() }));
			}
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return DATA;
	}

	/**
     * 
     * 方法描述: 角色分配用户，未分配用户列表
     * 
     * @throws
     */
	public String fpyhWfpYhxx() throws Exception {
		QueryModel queryModel = model.getQueryModel();
		String param = super.getRequest().getParameter("param");
		if (!StringUtil.isEmpty(param)) {
			StringBuffer buffer = null;
			String str = null;
			String[] ids = StringUtil.split(param, ",");
			if (ids != null && ids.length > 0) {
				buffer = new StringBuffer();
				for (String id : ids) {
					buffer.append("'").append(id).append("'").append(",");
				}
				str = buffer.toString();
				str = str.substring(0, str.length() - 1);
			}
			model.setYhids(str);
		}
		try {
			queryModel.setItems(jsglService.getPagedListWfpYh(model));
		} catch (Exception e) {
			logException(e);
			e.printStackTrace();
		}
		getValueStack().set(DATA, queryModel);
		return DATA;
	}
        
	/**
      * 
      * 方法描述: 角色分配用户，已分配用户列表
      * 
      * @throws
    */
    public String fpyhYfpYhxx() throws Exception{
        QueryModel queryModel = model.getQueryModel();
        queryModel.setItems(jsglService.getPagedListYfpYh(model));
        getValueStack().set(DATA, queryModel);
        return DATA;
    }
            
	/**
	 * 
	 * 方法描述: 功能授权
	 *  参数 @return 
	 *  参数说明 
	 *  返回类型 String 返回类型
	 * @throws UnsupportedEncodingException 
	 * 
	 */
	public String gnsqJs() throws UnsupportedEncodingException {
		String temp=getRequest().getParameter("jsmc_qry");
		if(StringUtils.isNotEmpty(temp)){
			temp=URLDecoder.decode(temp, "utf-8");
			this.jsmc_qry=temp;
		}
		ValueStack vs = getValueStack();

		try {
			model.setZgh(getUser().getYhm());
			// 查询单个角色信息
			JsglModel jsglModel = jsglService.getModel(model);
			String btns = jsglService.getJsgnqxStr(model);
			List<JsglModel> list = jsglService.getAllGnmkList(model);
			
			User user=getUser();
			if(user.isAdmin()){
				getValueStack().set("sfejsq",true);
			}else{
				JsglModel jsglQuery=new JsglModel();
				jsglQuery.setZgh(user.getYhm());
				jsglQuery.setList(user.getJsdms());
				boolean sfejsq =jsglService.getYhEjsq(jsglQuery);
				getValueStack().set("sfejsq",sfejsq);
			}
			
			vs.set("allGnmkList", list);// 所有功能模块
			// 界面高度
			int height = (list.size() * 29 + 36) < 800 ? 800
					: (list.size() * 29 + 36);
			vs.set("height", height);
			vs.set("czans", btns);

			vs.set("model", jsglModel);// 角色信息
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}
		
	/**
	 * 
	 * 根据开课学院，查询组织机构树
	 *  参数 @return 
	 *  参数说明 
	 *  返回类型 String 返回类型
	 * 
	 */
	public String getZzjg() {
		Map<String, String> map = new HashMap<String, String>();
		String kkxy = getRequest().getParameter("kkxy");
		String id = getRequest().getParameter("id");
		try {
			if (StringUtil.isEmpty(id)) {
				map.put("kkxy", kkxy);
				List<HashMap<String, String>>  lists= jsglService.queryXyZzjg(map);
				List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
				for(HashMap<String, String> m:lists){
					Map<String, Object> item = new HashMap<String, Object>();
					item.put("id", m.get("BMDM_ID") + "&" + m.get("ZJTYPE"));
					item.put("text", m.get("BMMC"));
					Object obj =  m.get("TOTAL");
					Double d = new BigDecimal(obj.toString()).doubleValue();
					//如果记录数大于1,说明该机构下存在隶属学院或机构
					if(d>1){
						item.put("state", "closed");
					}					
					items.add(item);
				}
				getValueStack().set(DATA, items);
			}else{
				String[] ids = StringUtil.split(id, '&');
				List<Map<String,Object>> items = this.getChildren(ids[0], ids[1]);
				getValueStack().set(DATA, items);
			}
		} catch (Exception e) {
			logException(e);
		}

		return DATA;
	}
	
	/**
	 * 分配用户查询用户信息
	 * @return
	 * @author HuangXiaoping created on 2012-7-18 
	 * @since ZFTAL 1.0
	 */
	public String fpyhCxYhxx()throws Exception{
		YhglModel yhglModel=new YhglModel();
		yhglModel.setZgh(model.getZgh());
	    yhglModel=yhglService.getModel(yhglModel);
	    getValueStack().set(DATA,yhglModel);
	    return DATA;
	}
	
	/**
	 * 获取子节点数据内容
	 * @param id 节点ID
	 * @param parm 节点类型(学院、专业、年级、班级)
	 * @return
	 */
	private List<Map<String,Object>> getChildren(String id,String parm){
		Map<String, String> map = null;List<Map<String, Object>> items = null;
		try {
			if(!StringUtil.isEmpty(parm)){
				if (parm.equals("XY")) {
					map = new HashMap<String, String>();
					map.put("bmdm_id", id);
					List<HashMap<String, String>>  lists = jsglService.queryZyZzjg(map);
					items = new ArrayList<Map<String, Object>>();
					for(HashMap<String, String> m:lists){
						Map<String, Object> item = new HashMap<String, Object>();
						item.put("id", m.get("ZYDM_ID") + "&" + m.get("ZJTYPE"));
						item.put("text", m.get("ZYMC"));
						Object obj =  m.get("TOTAL");
						Double d = new BigDecimal(obj.toString()).doubleValue();
						//如果记录数大于1,说明该机构下存在隶属学院或机构
						if(d>1){
							item.put("state", "closed");
						}
						items.add(item);
					}
					return items;
				} else if(parm.equals("ZY")){
					map = new HashMap<String, String>();
					map.put("zydm_id", id);
					List<HashMap<String, String>>  lists = jsglService.queryNjZzjg(map);
					items = new ArrayList<Map<String, Object>>();
					for(HashMap<String, String> m:lists){
						Map<String, Object> item = new HashMap<String, Object>();
						item.put("id", m.get("NJDM_ID") + "&" + m.get("ZJTYPE")+ "|" + m.get("ZYDM_ID"));
						item.put("text", m.get("NJMC"));
						//默认展示
						item.put("state", "closed");
						items.add(item);
					}
					return items;
				}else if(parm.indexOf("NJ")!=-1){
					map = new HashMap<String, String>();
					String[] parms = StringUtil.split(parm, '|');
					map.put("njdm_id", id);
					map.put("zydm_id", parms[1]);
					List<HashMap<String, String>>  lists = jsglService.queryBjZzjg(map);
					items = new ArrayList<Map<String, Object>>();
					for(HashMap<String, String> m:lists){
						Map<String, Object> item = new HashMap<String, Object>();
						item.put("id", m.get("BJDM_ID") + "&" + m.get("ZJTYPE"));
						item.put("text", m.get("BJMC"));
						items.add(item);
					}
					return items;
				}
		 }
		} catch (Exception e) {
			e.printStackTrace();
		}

		return items;
	}
	
	
	/**
	 * 
	 * 方法描述: 保存角色分配用户管理,逐条保存 
	 * 参数 @return 参数说明 
	 * 返回类型 String 
	 * 返回类型
	 * 
	 * @throws
	 */
	public String saveFp() {
		ValueStack vs = getValueStack();
		try {
			if(jsglService.saveFp(model)){
				vs.set(DATA, "保存成功");
			}
			else{
				vs.set(DATA, "保存失败");
			}
			
		} catch (Exception e) {
			vs.set(DATA, "保存失败 : " + e.getMessage());
		}
		return DATA;
	}
	
	/**
	 * 
	 * 方法描述: 删除角色分配用户管理,逐条删除
	 * 参数 @return 参数说明 
	 * 返回类型 String 
	 * 返回类型
	 * 
	 * @throws
	 */
	public String removeFp() {
		ValueStack vs = getValueStack();
		try {
			if(jsglService.removeFp(model)){
				vs.set(DATA, "删除成功");
			}
			else{
				vs.set(DATA, "删除失败");
			}
			
		} catch (Exception e) {
			vs.set(DATA, "删除失败 : " + e.getMessage());
		}
		return DATA;
	}
	
	public IJsglService getJsglService() {
		return jsglService;
	}

	public void setJsglService(IJsglService jsglService) {
		this.jsglService = jsglService;
	}

	public IYhglService getYhglService() {
		return yhglService;
	}

	public void setYhglService(IYhglService yhglService) {
		this.yhglService = yhglService;
	}

	public JsglModel getModel() {
		model.setModelBase(super.getUser());
		return model;
	}
	public IXsglfwService getXsglfwService() {
		return xsglfwService;
	}

	public void setXsglfwService(IXsglfwService xsglfwService) {
		this.xsglfwService = xsglfwService;
	}

	public String getSffpyh_qry() {
		return sffpyh_qry;
	}

	public void setSffpyh_qry(String sffpyh_qry) {
		this.sffpyh_qry = sffpyh_qry;
	}

	public String getJsmc_qry() {
		return jsmc_qry;
	}

	public void setJsmc_qry(String jsmc_qry) {
		this.jsmc_qry = jsmc_qry;
	}

	public String getGnmkdm_qry() {
		return gnmkdm_qry;
	}

	public void setGnmkdm_qry(String gnmkdm_qry) {
		this.gnmkdm_qry = gnmkdm_qry;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String getOp() {
		return op;
	}
	
}
