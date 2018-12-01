package com.zfsoft.hrm.staffturn.retire.action;

import java.io.File;
import java.io.IOException;
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
import org.quartz.JobDataMap;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.config.ICodeConstants;
import com.zfsoft.hrm.core.util.DownloadFilenameUtil;
import com.zfsoft.hrm.print.entity.PrintTemplateEntity;
import com.zfsoft.hrm.print.service.IPrintTemplateService;
import com.zfsoft.hrm.schedule.ScheduleControlService;
import com.zfsoft.hrm.staffturn.config.IStatusUpdateConfig;
import com.zfsoft.hrm.staffturn.retire.entities.RetireInfo;
import com.zfsoft.hrm.staffturn.retire.query.RetireInfoQuery;
import com.zfsoft.hrm.staffturn.retire.service.svcinterface.IRetireService;
import com.zfsoft.hrm.util.ExcelExportUtil;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.base.Struts2Utils;

/**
 * 退休
 * @author   沈鲁威 Patrick Shen
 * @since   2012-7-30
 * @version   V1.0.0
 */
public class RetireAction extends HrmAction{

	private static final long serialVersionUID = -4074331425973887767L;
	
	private IRetireService retireService;

	private RetireInfo model=new RetireInfo();
	
	private RetireInfoQuery query=new RetireInfoQuery();
	
	private final static String RETIRE_SCAN = "RETIRE_SCAN";
	private final static String RECEIVER = "RECEIVER";
	private String sortFieldName=null;
	private String asc="up";
	private ScheduleControlService scheduleControlService;
	private IPrintTemplateService printTemplateService;
	
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
		map.put("deptCode", ICodeConstants.DM_DEF_ORG);
		map.put("sexCode", ICodeConstants.SEX);
		map.put("dutyCode", ICodeConstants.ADMIN_DUTY_LEVEL);
		map.put("retireTypeCode", ICodeConstants.RETIRE_TYPE);
		return map;
	}
	/**
	 * 查询，展示退休查询界面
	 * @return
	 */
	public String list() {
		initList();
		if(!StringUtil.isEmpty(sortFieldName)){
			if(asc.equals("up")){
				query.setOrderStr( sortFieldName );
			}else{
				query.setOrderStr( sortFieldName +" desc");
			}
		}else{
			query.setOrderStr( "gh" );
		}
		this.getValueStack().set("retireInfoList",retireService.getRetireInfoList(query));
		return LIST_PAGE;
	}

	/**
	 * 增加动作，展现工号输入界面，展现退休信息修改界面
	 * @return
	 */
	public String add() {
		// 20140428 add start
		model.setReceiver(retireService.getReceiver());
		// 20140428 add end
		//展现输入
		return "add";
	}

	/**
	 * 修改动作，展现退休信息修改界面
	 * @return
	 */
	public String modify() {
		initList();
		model=retireService.getRetireInfoByUserId(query.getUserId());
		query.setOpType("modify");
		return EDIT_PAGE;
	}
	
	/**
	 * 删除操作
	 * @return
	 */
	public String delete() {
		retireService.removeRetire(model);
		this.setSuccessMessage("操作成功");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	/**
	 * 修改动作，展现退休信息修改界面
	 * @return
	 */
	public String certPrint() {
		model=retireService.getRetireInfoByUserId(query.getUserId());
		PrintTemplateEntity e = new PrintTemplateEntity();
		e.setMbbzm("HRM_TXZS");
		List<PrintTemplateEntity> mbs = printTemplateService.findByParam(e);
//		return "certPrint";
		Map<String, Object> data = new HashMap<String, Object>();
//		data.put("info", model);
		data.put("mbs", mbs);
		data.put("xm", model.getOverall().getViewHtml().get("xm"));
		data.put("xb", model.getOverall().getViewHtml().get("xbm"));
		data.put("jg", model.getOverall().getViewHtml().get("jg"));
		data.put("mz", model.getOverall().getViewHtml().get("mzm"));
		data.put("zc", model.getOverall().getViewHtml().get("zc"));
		String gwm = model.getOverall().getValues().get("gwlb").toString();
		String gw = "";
		if ("01".equals(gwm)) {
			gw = "管理人员";
		} else if ("02".equals(gwm)) {
			gw = "专业技术人员";
		} else if ("03".equals(gwm)) {
			gw = "工勤技能人员";
		}
		data.put("gw", gw);
		data.put("csrq", model.getOverall().getViewHtml().get("csrq"));
		data.put("txsj", model.getRetireTimeString());
		data.put("rzsj", model.getOverall().getViewHtml().get("rzrq"));
		data.put("sfzh", model.getOverall().getViewHtml().get("sfzh"));
		
		data.put("success", true);
		getValueStack().set(DATA, data);
		return DATA;
	}
	/**
	 * 保存动作，返回保存结果
	 * @return
	 * @throws SchedulerException 
	 */
	public String save() throws SchedulerException {
		model.setState(1);
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
		
		retireService.saveRetire(model,query.getOpType());
		// 20140425 add start
		Trigger trigger = scheduleControlService.getTriggerByTriggerName(RETIRE_SCAN);
		String receiver = "";
		if(trigger != null){
			JobDataMap map = trigger.getJobDataMap();
			receiver = map.getString(RECEIVER);
		}
		
		if ("add".equals(query.getOpType())) {
			String mReceiver = model.getReceiver();
			if (StringUtils.isNotEmpty(receiver)) {
				String[] rs = (receiver + mReceiver).split(";");
				String retrs = "";
				for (String r : rs) {
					if (!retrs.contains(r.trim())) {
						retrs += r.trim() + ";";
					}
				}
				
				model.setReceiver(retrs);
			}
			retireService.saveMessage(model);
			
			model.setReceiver(mReceiver);
			int count = retireService.getReceiverCount();
			
			if (count > 0) {
				if (!StringUtil.isEmpty(model.getReceiver()) && !(retireService.getReceiver()).equals(model.getReceiver())) {
					retireService.updateReceiver(model);
				}
			} else {
				retireService.insertReceiver(model);
			}
		}
		// 20140425 add end
		this.setSuccessMessage("操作成功");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	/**
	 * 导出
	 * @param: 
	 * @return:
	 * @throws Exception 
	 * @throws IOException 
	 */
	public String export() throws IOException, Exception{
		query.setPerPageSize(Integer.MAX_VALUE);
		List<RetireInfo> list=retireService.getRetireInfoList(query);
//		int i=1;
//		DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
//		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
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
//			String num = retireInfo.getNum();
//			
//			String xm=null;
//			String xbm=null;
//			String dwm=null;
//			if(overall!=null){
//				//姓名
//				xm=overall.getViewHtml().get("xm");
//				//性别码
//				xbm=overall.getViewHtml().get("xbm");
//				//部门名称
//				dwm=overall.getViewHtml().get("dwm");
//			}
//				
//			Map<String,String> map=new HashMap<String, String>();
//			map.put("index", ""+i++);
//			map.put("userId", retireInfo.getUserId());
//			map.put("xm", xm);
//			map.put("xbm", xbm);
//			map.put("csrq", csrq!=null ? df.format((Date)csrq):"");
//			map.put("txsj", retireDate!=null?df.format(retireDate):"");
//			if(retireInfo.getOverall()!=null){
//				map.put("txgl", retireInfo.getOverall().getViewHtml().get("gl"));
//			}else{
//				map.put("txgl", "");
//			}
//			map.put("identifier", retireInfo.getIdentifier());
//			map.put("num", num);
//			
//			if(IStatusUpdateConfig.RETIRE_REENGAGE.equals(retireInfo.getDoResult())){
//				map.put("reEngage", "是");
//			}else if(StringUtil.isEmpty(retireInfo.getDoResult())){
//				map.put("reEngage", "");
//			}else{
//				map.put("reEngage", "否");
//			}
//		
//			map.put("dwm", dwm);
//			map.put("rzrq", rzrq!=null ? df.format((Date)rzrq):"");
//
//			data.add(map);
//		}
//		
		getResponse().reset();
		getResponse().setCharacterEncoding("utf-8");
		getResponse().setContentType("application/vnd.ms-excel");
		String useragent = getRequest().getHeader("user-agent");
		String disposition = DownloadFilenameUtil.fileDisposition(useragent,"退休人员信息.xls");
//		getResponse().setHeader("Content-Disposition", disposition);
////		String path = this.getClass().getResource("/").getPath()+ "exportModel" + File.separator + "retire";
//		String path = "WEB-INF" + File.separator + "classes" + File.separator
//			+ "exportModel" + File.separator + "retire";
//		path = Struts2Utils.getSession().getServletContext().getRealPath(path);
//		ExcelExportUtil.export(new File(path + File.separator + "retire.xls"), getResponse().getOutputStream(), data);
		getResponse().setHeader("Content-Disposition", disposition);
		WritableWorkbook wwb = Workbook.createWorkbook( getResponse().getOutputStream() );
		
		List<String> fieldList = new ArrayList<String>();
		WritableSheet sheet1 = wwb.createSheet("退休人员信息", 0);
		//头三行固定为 序号 工号 姓名
		int i=0;
		int j=0;
		//序号批注设置为信息类guid 后续导入时使用该批注确定修改的信息类类型
		sheet1.addCell(new Label(i++,j,"序号"));
		sheet1.addCell(new Label(i++,j,"职工号"));
		sheet1.addCell(new Label(i++,j,"姓名"));
		sheet1.addCell(new Label(i++,j,"性别"));
		sheet1.addCell(new Label(i++,j,"出生年月"));
		sheet1.addCell(new Label(i++,j,"职称"));
		sheet1.addCell(new Label(i++,j,"行政职务级别"));
		sheet1.addCell(new Label(i++,j,"专技职务级别"));
		sheet1.addCell(new Label(i++,j,"工人等级"));
		sheet1.addCell(new Label(i++,j,"现任职务"));
		sheet1.addCell(new Label(i++,j,"退休时间"));
		sheet1.addCell(new Label(i++,j,"退休工龄"));
		sheet1.addCell(new Label(i++,j,"退休证编号"));
		sheet1.addCell(new Label(i++,j,"退休文号"));
		sheet1.addCell(new Label(i++,j,"是否返聘"));
		sheet1.addCell(new Label(i++,j,"部门"));
		sheet1.addCell(new Label(i++,j,"参加工作时间"));
		sheet1.addCell(new Label(i++,j,"离退后享受级别"));
		sheet1.addCell(new Label(i++,j,"离退休费支付单位"));
		sheet1.addCell(new Label(i++,j,"离退后管理单位"));
		sheet1.addCell(new Label(i++,j,"异地安置地点"));
		j++;
		for (RetireInfo retireInfo : list) {
			i=0;
			sheet1.addCell(new Label(i++,j,j+""));
			sheet1.addCell(new Label(i++,j,retireInfo.getUserId()));
			sheet1.addCell(new Label(i++,j,retireInfo.getOverall().getViewHtml().get("xm")));
			sheet1.addCell(new Label(i++,j,retireInfo.getOverall().getViewHtml().get("xbm")));
			sheet1.addCell(new Label(i++,j,retireInfo.getOverall().getViewHtml().get("csrq")));
			sheet1.addCell(new Label(i++,j,retireInfo.getOverall().getViewHtml().get("zc")));
			sheet1.addCell(new Label(i++,j,retireInfo.getOverall().getViewHtml().get("zwjbdm")));
			sheet1.addCell(new Label(i++,j,retireInfo.getOverall().getViewHtml().get("zyjszj")));
			sheet1.addCell(new Label(i++,j,retireInfo.getOverall().getViewHtml().get("grdj")));
			sheet1.addCell(new Label(i++,j,retireInfo.getOverall().getViewHtml().get("xrzw")));
			sheet1.addCell(new Label(i++,j,retireInfo.getRetireTimeString()));
			sheet1.addCell(new Label(i++,j,StringUtil.isEmpty(retireInfo.getRetireTimeString())?"":retireInfo.getOverall().getViewHtml().get("gl")));
			sheet1.addCell(new Label(i++,j,retireInfo.getIdentifier()));
			sheet1.addCell(new Label(i++,j,retireInfo.getNum()));
			sheet1.addCell(new Label(i++,j,"04".equals(retireInfo.getDoResult())?"是":"否"));
			sheet1.addCell(new Label(i++,j,retireInfo.getOverall().getViewHtml().get("dwm")));
			sheet1.addCell(new Label(i++,j,retireInfo.getOverall().getViewHtml().get("rzrq")));
			sheet1.addCell(new Label(i++,j,CodeUtil.getItemValue("DM_GB_A_LTXGBXXSDYLBDM", retireInfo.getLthxsjbm())));
			sheet1.addCell(new Label(i++,j,retireInfo.getLtxfzfdw()));
			sheet1.addCell(new Label(i++,j,retireInfo.getLthgldw()));
			sheet1.addCell(new Label(i++,j,retireInfo.getYdazdd()));
			j++;
		}
		
		wwb.write();
		wwb.close();
		return null;
	}
	
	public RetireInfo getModel() {
		return model;
	}
	
	public void setModel(RetireInfo model) {
		this.model = model;
	}

	public RetireInfoQuery getQuery() {
		return query;
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

	public static String getRetireScan() {
		return RETIRE_SCAN;
	}

	public void setScheduleControlService(
			ScheduleControlService scheduleControlService) {
		this.scheduleControlService = scheduleControlService;
	}

	/**
	 * @param printTemplateService the printTemplateService to set
	 */
	public void setPrintTemplateService(IPrintTemplateService printTemplateService) {
		this.printTemplateService = printTemplateService;
	}
	
	
}
