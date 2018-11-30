package com.zfsoft.hrm.staffturn.retire.action;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.hrm.baseinfo.dyna.business.bizinterface.IDynaBeanBusiness;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.config.ICodeConstants;
import com.zfsoft.hrm.core.util.DownloadFilenameUtil;
import com.zfsoft.hrm.staffturn.config.IStatusUpdateConfig;
import com.zfsoft.hrm.staffturn.retire.entities.RetireInfo;
import com.zfsoft.hrm.staffturn.retire.query.RetireInfoQuery;
import com.zfsoft.hrm.staffturn.retire.service.svcinterface.IRetireService;
import com.zfsoft.hrm.util.WordExportUtil;
import com.zfsoft.util.base.StringUtil;

/**
 * 预退休
 * @author   沈鲁威 Patrick Shen
 * @since   2012-7-30
 * @version   V1.0.0
 */
public class PreRetireAction extends HrmAction implements ModelDriven<RetireInfo>{

	private static final long serialVersionUID = -4074331425973887767L;
	
	private IRetireService retireService;
	
	private RetireInfo model=new RetireInfo();
	
	private RetireInfoQuery query=new RetireInfoQuery();
	
	private String sortFieldName=null;
	private String asc="up";
	private String op;
	
	public void setRetireService(IRetireService retireService) {
		this.retireService = retireService;
	}
	/**
	 * 初始化选中框的数据源
	 */
	private void initList() {
		getValueStack().set("code", loadCodeInPage());		
	}
	/**
	 * 加载页面中使用的代码编号
	 * @return
	 */
	private Map<String,String> loadCodeInPage(){
		Map<String,String> map = new HashMap<String,String>();
		map.put("deptId", ICodeConstants.DM_DEF_ORG);
		return map;
	}
	/**
	 * 查询，展示退休查询界面
	 * @return
	 */
	public String list() {
		initList();
		if(query.getDeptCode()==null||query.getDeptCode().equals("")){
			query.setDeptCode(null);
		}
		if(!StringUtil.isEmpty(sortFieldName)){
			if(asc.equals("up")){
				query.setOrderStr( sortFieldName );
			}else{
				query.setOrderStr( sortFieldName +" desc");
			}
		}else{
			query.setOrderStr( "gh" );
		}
		this.getValueStack().set("retireInfoList",retireService.getPreRetireInfoList(query));
		return LIST_PAGE;
	}
	/**
	 * 导出退休审批表
	 * @return
	 */
	public String exportInfo() throws Exception{
		if(query.getDeptCode()==null||query.getDeptCode().equals("")){
			query.setDeptCode(null);
		}
		
		Map<String, Object> data = new HashMap<String, Object>();
//		RetireInfo info=retireService.getPreRetireByUserId(model.getUserId());
		RetireInfo info=retireService.getRetireInfoByUserId(model.getUserId());
		DateFormat df=new SimpleDateFormat("yyyy年MM月dd日");
//		DynaBean overall= info.getOverall();
			//任职日期
//		Date rzrq= (Date)overall.getValues().get("rzrq");
			//退休日期
		Date retireDate=info.getRetireTime();
			//计算工作年限
//		int workAge=0;
//		if(rzrq!=null && retireDate!=null){
//			Calendar beginWorkDay=Calendar.getInstance();
//			beginWorkDay.setTime(rzrq);
//			
//			Calendar retireDay=Calendar.getInstance();
//			retireDay.setTime(retireDate);
//			
//			workAge=retireDay.get(Calendar.YEAR)-beginWorkDay.get(Calendar.YEAR);
//			if(retireDay.get(Calendar.MONTH)<beginWorkDay.get(Calendar.MONTH)){
//				workAge--;
//			}
//		}
		IDynaBeanBusiness beanBusiness = (IDynaBeanBusiness)SpringHolder.getBean("baseDynaBeanBusiness");
		DynaBeanQuery query = new DynaBeanQuery(InfoClassCache.getInfoClassByName("txfgl"));
		DynaBean jbxx= beanBusiness.findUniqueByParam("gh", model.getUserId(), 
				InfoClassCache.getInfoClass("C393FE11C4DC8E46E040007F01003F39"));
		query.setExpress(" gh = #{params.gh}");
		query.setParam("gh", model.getUserId());
		List<DynaBean> list = beanBusiness.queryBeans(query);
		DynaBean bean = null;
		if (list==null||list.isEmpty()) {
			bean = new DynaBean(InfoClassCache.getInfoClassByName("txfgl"));
		} else {
			bean = list.get(0);
		}
		data.put("retire", bean);
//		data.put("gznx", (rzrq!=null&&retireDate!=null) ? String.valueOf(workAge):"");
		data.put("txsj", retireDate!=null?df.format(retireDate):"");
		data.put("info", info);
		data.put("jbxx", jbxx);
		getResponse().reset();
				
		getResponse().setCharacterEncoding("utf-8");
		getResponse().setContentType("application/msword");
		String useragent = getRequest().getHeader("user-agent");
		String disposition = DownloadFilenameUtil.fileDisposition(useragent,"预退休人员信息.doc");
		getResponse().setHeader("Content-Disposition", disposition);
		WordExportUtil.getInstance().exportTable(getResponse().getOutputStream(), "retire"+File.separator+"pre_retire_info.xml", data);
		return null;
	}
	/**
	 * 导出延长退休审批表
	 * @param: 
	 * @return:
	 * @throws Exception 
	 */
	public String exportEmpInfo() throws Exception{
		if(query.getDeptCode()==null||query.getDeptCode().equals("")){
			query.setDeptCode(null);
		}
		
		Map<String, Object> data = new HashMap<String, Object>();
		RetireInfo info=retireService.getPreRetireByUserId(model.getUserId());
		
		data.put("info", info);
		
		getResponse().reset();
		getResponse().setCharacterEncoding("utf-8");
		getResponse().setContentType("application/msword");
		String useragent = getRequest().getHeader("user-agent");
		String disposition = DownloadFilenameUtil.fileDisposition(useragent,"高级专家延长退（离）休年龄审批表.doc");
		getResponse().setHeader("Content-Disposition", disposition);
		WordExportUtil.getInstance().exportTable(getResponse().getOutputStream(), "retire"+File.separator+"pre_yc_retire.xml", data);
		return null;
	}
	
	/**
	 * 导出
	 * @return
	 */
	public String export() throws Exception{
		if(query.getDeptCode()==null||query.getDeptCode().equals("")){
			query.setDeptCode(null);
		}
		
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		List<RetireInfo> list=retireService.getPreList(query);
		
		
//		int i=1;
//		DateFormat df=new SimpleDateFormat("yyyy.MM");
//		for (RetireInfo retireInfo : list) {
//			DynaBean overall= retireInfo.getOverall();
//			//出生日期
//			Date csrq=null;
//			//任职日期
//			Date rzrq = null;
//			if(overall!=null){
//				if(overall.getValues().get("csrq")!=null){
//					csrq=(Date)overall.getValues().get("csrq");
//				}
//				if(overall.getValues().get("rzrq")!=null){
//					rzrq= (Date)overall.getValues().get("rzrq");
//				}
//			}
//
//			//退休日期
//			Date retireDate=retireInfo.getRetireTime();
//			
//			//计算工作年限
//			int workAge=0;
//			if(rzrq!=null && retireDate!=null){
//				Calendar beginWorkDay=Calendar.getInstance();
//				beginWorkDay.setTime(rzrq);
//				
//				Calendar retireDay=Calendar.getInstance();
//				retireDay.setTime(retireDate);
//				
//				workAge=retireDay.get(Calendar.YEAR)-beginWorkDay.get(Calendar.YEAR);
//				if(retireDay.get(Calendar.MONTH)<beginWorkDay.get(Calendar.MONTH)){
//					workAge--;
//				}
//			}
//			
//			String xrzw=null;
//			String zzyjszwzg=null;
//			String zw=null;
//			String xm=null;
//			String xbm=null;
//			if(overall!=null){
//				//现任行政职务
//				xrzw= overall.getViewHtml().get("xrzw");
//				//现专业技术职务资格
//				zzyjszwzg= overall.getViewHtml().get("zzyjszwzg");
//				//职务
//				zw=xrzw;
//				//姓名
//				xm=overall.getViewHtml().get("xm");
//				//性别码
//				xbm=overall.getViewHtml().get("xbm");
//			}
//				
//			if(StringUtils.isNotEmpty(zzyjszwzg)){
//				zw+="/"+zzyjszwzg;
//			}
//			Map<String,String> map=new HashMap<String, String>();
//			map.put("index", ""+i++);
//			map.put("userId", retireInfo.getUserId());
//			map.put("xm", xm);
//			map.put("xbm", xbm);
//			map.put("gznx", (rzrq!=null&&retireDate!=null) ? String.valueOf(workAge):"");
//			map.put("txsj", retireDate!=null?df.format(retireDate):"");
//			map.put("csrq", csrq!=null ? df.format((Date)csrq):"");
//			map.put("rzrq", rzrq!=null ? df.format((Date)rzrq):"");
//			map.put("zw", zw);
//			data.add(map);
//		}
//		
		getResponse().reset();
		getResponse().setCharacterEncoding("utf-8");
		getResponse().setContentType("application/vnd.ms-excel");
		String useragent = getRequest().getHeader("user-agent");
		String disposition = DownloadFilenameUtil.fileDisposition(useragent,"预退休人员信息.xls");
		getResponse().setHeader("Content-Disposition", disposition);
////		String path = this.getClass().getResource("/").getPath()+ "exportModel" + File.separator + "retire";
//		String path = "WEB-INF" + File.separator + "classes" + File.separator
//			+ "exportModel" + File.separator + "retire";
//		path = Struts2Utils.getSession().getServletContext().getRealPath(path);
//		ExcelExportUtil.export(new File(path + File.separator + "pre_retire.xls"), getResponse().getOutputStream(), data);
//		
		WritableWorkbook wwb = Workbook.createWorkbook( getResponse().getOutputStream() );
		
		List<String> fieldList = new ArrayList<String>();
		WritableSheet sheet1 = wwb.createSheet("预退休人员信息", 0);
		//头三行固定为 序号 工号 姓名
		int i=0;
		int j=0;
		//序号批注设置为信息类guid 后续导入时使用该批注确定修改的信息类类型

		sheet1.addCell(new Label(i++,j,"序号"));
		sheet1.addCell(new Label(i++,j,"职工号"));
		sheet1.addCell(new Label(i++,j,"姓名"));
		sheet1.addCell(new Label(i++,j,"性别"));
		sheet1.addCell(new Label(i++,j,"出生年月"));
		sheet1.addCell(new Label(i++,j,"预退休时间"));
		sheet1.addCell(new Label(i++,j,"退休文号"));
		sheet1.addCell(new Label(i++,j,"部门"));
		sheet1.addCell(new Label(i++,j,"任职岗位 "));
		sheet1.addCell(new Label(i++,j,"职称"));
		sheet1.addCell(new Label(i++,j,"行政职务级别"));
		sheet1.addCell(new Label(i++,j,"专技职务级别"));
		sheet1.addCell(new Label(i++,j,"工人等级 "));
		sheet1.addCell(new Label(i++,j,"现任职务"));
		sheet1.addCell(new Label(i++,j,"是否博士生导师"));
		sheet1.addCell(new Label(i++,j,"现任职务"));
		sheet1.addCell(new Label(i++,j,"现任职务"));
		sheet1.addCell(new Label(i++,j,"现任职务"));
		j++;
		for (RetireInfo retireInfo : list) {
			i=0;
			sheet1.addCell(new Label(i++,j,j+""));
			sheet1.addCell(new Label(i++,j,retireInfo.getUserId()));
			sheet1.addCell(new Label(i++,j,retireInfo.getOverall().getViewHtml().get("xm")));
			sheet1.addCell(new Label(i++,j,retireInfo.getOverall().getViewHtml().get("xbm")));
			sheet1.addCell(new Label(i++,j,retireInfo.getOverall().getViewHtml().get("csrq")));
			sheet1.addCell(new Label(i++,j,retireInfo.getRetireTime()==null ? retireInfo.getOldRetireTimeString() : retireInfo.getRetireTimeString()));
			sheet1.addCell(new Label(i++,j,retireInfo.getNum()));
			sheet1.addCell(new Label(i++,j,retireInfo.getOverall().getViewHtml().get("dwm")));
			sheet1.addCell(new Label(i++,j,retireInfo.getOverall().getViewHtml().get("rzgwm")));
			sheet1.addCell(new Label(i++,j,retireInfo.getOverall().getViewHtml().get("zc")));
			sheet1.addCell(new Label(i++,j,retireInfo.getOverall().getViewHtml().get("zwjbdm")));
			sheet1.addCell(new Label(i++,j,retireInfo.getOverall().getViewHtml().get("zj")));
			sheet1.addCell(new Label(i++,j,retireInfo.getOverall().getViewHtml().get("grdj")));
			sheet1.addCell(new Label(i++,j,retireInfo.getOverall().getViewHtml().get("xrzw")));
			sheet1.addCell(new Label(i++,j,retireInfo.getOverall().getViewHtml().get("sfbssds")));
			j++;
		}
		
		wwb.write();
		wwb.close();
		return null;
	}
	

	/**
	 * 增加动作，展现工号输入界面，展现退休信息修改界面
	 * @return
	 */
	public String add() {
		return EDIT_PAGE;
	}
	/**
	 * 修改动作，展现退休信息修改界面
	 * @return
	 */
	public String modifyInit() {
		model=retireService.getPreRetireByUserId(model.getUserId());
		/*
		IDynaBeanBusiness iDynaBeanBusiness=(IDynaBeanBusiness) SpringHolder.getBean("baseDynaBeanBusiness");
		个人概况
		DynaBean dynaBean= iDynaBeanBusiness.findUniqueByParam("gh", model.getUserId(), 
				InfoClassCache.getInfoClass("C393FE11C4DC8E46E040007F01003F39"));
		*/
		DynaBean dynaBean=model.getOverall();
		if(dynaBean!=null){
			if(StringUtils.isEmpty(model.getRetirePost())){
				model.setRetirePost(dynaBean.getValueString("zwjbdm"));
			}
			if(StringUtils.isEmpty(model.getRetirePostLevel())){
				model.setRetirePostLevel(dynaBean.getValueString("zj"));
			}
			if(StringUtils.isEmpty(model.getGrdj())){
				model.setGrdj(dynaBean.getValueString("grdj"));
			}
		}
		
		return "modify";
	}
	/**
	 * 修改预退休
	 * @return
	 */
	public String modify() {
		// 20140424 add start
		// 延聘
/*		if (IConstants.MARK_YES.equals(model.getIsEmploy())) {
			if (model.getRetireTime() != null && model.getOldRetireTime() != null 
					&& model.getRetireTime().compareTo(model.getOldRetireTime()) < 0) {
				this.setErrorMessage("延聘选择是时，退休日期不能小于原退休日期");
				getValueStack().set(DATA, getMessage());
				return DATA;
			}
		}*/
		// 20140424 add end
		if(!StringUtil.isEmpty(model.getIdentifier())){
			query.setIdentifier(model.getIdentifier());
			List<RetireInfo> list = retireService.getRetireInfoList(query);
			for (RetireInfo retireInfo : list) {
				if(model.getIdentifier().equals(retireInfo.getIdentifier())
						&&!model.getUserId().equals(retireInfo.getUserId())){
					this.setErrorMessage("退休证编号不能重复");
					getValueStack().set(DATA, getMessage());
					return DATA;
				}
			}
		}
		// 20140424 add start
		//model.setOldRetireTime(model.getRetireTime());
		// 20140424 add end
		retireService.modify(model);
		this.setSuccessMessage("操作成功");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	/**
	 * 保存动作，返回保存结果
	 * @return
	 */
	public String save() {
		RetireInfo retireInfo=retireService.getPreRetireByUserId(model.getUserId());
		if(retireInfo!=null){
			retireService.modifyPreRetireToRetire(retireInfo);
		}else{
			retireService.saveRetire(model,"add");
		}
		this.setSuccessMessage("操作成功");
		
		this.getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	/**
	 * 删除操作
	 * @return
	 */
	public String delete() {
		retireService.removeRetire(model);
		this.setSuccessMessage("操作成功");
		this.getValueStack().set(DATA, getMessage());
		return DATA;
	}

	/**
	 * 退休处理，展现退休信息修改界面
	 * @return
	 */
	public String retireDeal() {
		model=retireService.getPreRetireByUserId(model.getUserId());
		if(StringUtils.isEmpty(model.getRetireType())){
			model.setRetireType("2");
		}
		
		/*
		IDynaBeanBusiness iDynaBeanBusiness=(IDynaBeanBusiness) SpringHolder.getBean("baseDynaBeanBusiness");
		个人概况
		DynaBean dynaBean= iDynaBeanBusiness.findUniqueByParam("gh", model.getUserId(), 
				InfoClassCache.getInfoClass("C393FE11C4DC8E46E040007F01003F39"));
		*/
		DynaBean dynaBean=model.getOverall();
		if(dynaBean!=null){
			if(StringUtils.isEmpty(model.getRetirePost())){
				model.setRetirePost(dynaBean.getValueString("zwjbdm"));
			}
			if(StringUtils.isEmpty(model.getRetirePostLevel())){
				model.setRetirePostLevel(dynaBean.getValueString("zj"));
			}
			if(StringUtils.isEmpty(model.getGrdj())){
				model.setGrdj(dynaBean.getValueString("grdj"));
			}
		}
		return "deal_edit";
	}
	
	/** 
	 * 预退休取消/撤销处理  ---add by heyc on 20130806
	 * @return  String  
	 */
	public String retireCancel() {	
		RetireInfo reInfo=retireService.getPreRetireByUserId(model.getUserId());
		if(reInfo!=null){
			retireService.cancelPreRetire(reInfo);
		}
		initList();
		if(query.getDeptCode()==null||query.getDeptCode().equals("")){
			query.setDeptCode(null);
		}
		this.getValueStack().set("retireInfoList",retireService.getPreRetireInfoList(query));
		this.getValueStack().setValue("currentMenu", "教职工管理 > 退休管理 > 预退休人员管理");
		return LIST_PAGE ;
	}
	
	public String retireDealSave() {
		model.setDoResult(op);               //设置处理结果
		// 20140424 add start
		// 延聘
		if (IStatusUpdateConfig.RETIRE_ISEMPLOY.equals(model.getDoResult())) {
			if (model.getRetireTime() != null && model.getOldRetireTime() != null 
					&& model.getRetireTime().compareTo(model.getOldRetireTime()) < 0) {
				this.setErrorMessage("延聘选择是时，退休日期不能小于原退休日期");
				getValueStack().set(DATA, getMessage());
				return DATA;
			}
		}
		// 20140424 add end
		if(!StringUtil.isEmpty(model.getIdentifier())){
			query.setIdentifier(model.getIdentifier());
			List<RetireInfo> list = retireService.getRetireInfoList(query);
			for (RetireInfo retireInfo : list) {
				if(model.getIdentifier().equals(retireInfo.getIdentifier())
						&&!model.getUserId().equals(retireInfo.getUserId())){
					this.setErrorMessage("退休证编号不能重复");
					getValueStack().set(DATA, getMessage());
					return DATA;
				}
			}
		}
		retireService.modifyPreRetireToRetire(model);
		this.setSuccessMessage("操作成功");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	/**
	 * 批量修改退休文号init
	 * @return
	 */
	public String plxgtxwhInit(){
		String op = getRequest().getParameter("op");   
		getValueStack().set("ops", op);
		return "plxgtxwh";
	}
	/**
	 * 批量修改退休文号
	 * @return
	 */
	public String plxgtxwh(){
		List<RetireInfo> list=new ArrayList<RetireInfo>();
		String[] ids=getRequest().getParameterValues("ids");
		
		for (String id : ids) {
			RetireInfo retireInfo=new RetireInfo();
			retireInfo.setUserId(id);
			retireInfo.setNum(model.getNum());
			list.add(retireInfo);
		}
		retireService.doPlxgtxwh(list);
		
		this.setSuccessMessage("操作成功");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	/**
	 * 批量修改退休文号(退休人员管理中的修改文号，只修改文号，不做其他操作)
	 * @return
	 */
	public String plxgtxwh2(){
		String[] ids=getRequest().getParameterValues("ids");
		
		RetireInfo retireInfo=new RetireInfo();
		for (String id : ids) {
			retireInfo.setUserId(id);
			retireInfo.setNum(model.getNum());
			retireService.doPlxgtxwh2(retireInfo);
		}
		
		this.setSuccessMessage("操作成功");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	@Override
	public RetireInfo getModel() {
		return model;
	}
	
	public RetireInfoQuery getQuery() {
		return query;
	}
	public void setModel(RetireInfo model) {
		this.model = model;
	}
	public void setQuery(RetireInfoQuery query) {
		this.query = query;
	}
	public String getSortFieldName() {
		return sortFieldName;
	}
	public void setSortFieldName(String sortFieldName) {
		this.sortFieldName = sortFieldName;
	}
	public String getAsc() {
		return asc;
	}
	public void setAsc(String asc) {
		this.asc = asc;
	}
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	
}
