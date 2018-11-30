package com.zfsoft.hrm.seniorreport.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.dyna.service.svcinterface.IDynaBeanService;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.config.IConstants;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.core.util.DownloadFilenameUtil;
import com.zfsoft.hrm.report.entity.ReportQuery;
import com.zfsoft.hrm.report.ofcentity.Element;
import com.zfsoft.hrm.report.ofcentity.OpenFlashChart;
import com.zfsoft.hrm.seniorreport.entity.Col;
import com.zfsoft.hrm.seniorreport.entity.SeniorReportConfig;
import com.zfsoft.hrm.seniorreport.entity.SeniorReportConfigData;
import com.zfsoft.hrm.seniorreport.entity.SeniorReportHistroy;
import com.zfsoft.hrm.seniorreport.entity.SeniorReportView;
import com.zfsoft.hrm.seniorreport.entity.ViewItem;
import com.zfsoft.hrm.seniorreport.enums.SeniorReportType;
import com.zfsoft.hrm.seniorreport.service.ISeniorReportService;
import com.zfsoft.hrm.util.ExcelExportUtil;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.base.Struts2Utils;

/**
 * 复合报表展示
 * @author ChenMinming
 * @date 2013-12-23
 * @version V1.0.0
 */
public class SeniorReportAction extends HrmAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2154874534837L;
	private ISeniorReportService seniorReportService;
	private String id;
	private SeniorReportType type;
	private String snapTime;

	private IDynaBeanService dynaBeanService;
	private ReportQuery rquery = new ReportQuery();
	private DynaBeanQuery query = getDynaQuery();
	
	private DynaBeanQuery getDynaQuery() {
		DynaBeanQuery dynaBeanQuery = new DynaBeanQuery(
				InfoClassCache.getOverallInfoClass(IConstants.INFO_CATALOG_TYPE_DEFAULT));
		dynaBeanQuery.setPerPageSize(20);
		return dynaBeanQuery;
	} 
	/**
	 * 列表页面
	 * @return
	 */
	public String list() {
		//获取表单导出模板 不存在模板的情况下则界面不提供导出按钮
		String path = "WEB-INF" + File.separator + "classes" + File.separator
		+ "exportModel" + File.separator + "seniorreport";
		path+=File.separator+id+".xls";
		path = Struts2Utils.getSession().getServletContext().getRealPath(path);
		
		File temp = new File(path);
		this.getValueStack().set("hasModel", temp.exists());
		SeniorReportHistroy histroy = new SeniorReportHistroy();
		histroy.setReportId(id);
		List<SeniorReportHistroy> snapList = seniorReportService.getSnapTimeList(histroy);
		//快照时间列表
		this.getValueStack().set("snapList",snapList);
		return LIST_PAGE;
	}
	
	public String page(){
		this.getValueStack().set("types", SeniorReportType.values());
		return "page";
	}

	public String getList(){
		if(type == null){
			type = SeniorReportType.values()[0];
		}
		SeniorReportConfigData data = new SeniorReportConfigData();
		data.setMenuId(type.getKey());
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("reportList", seniorReportService.getReportList(data));
		this.getValueStack().set(DATA, dataMap);
		return DATA;
	}

	public String queryReport(){
		SeniorReportHistroy histroy = new SeniorReportHistroy();
		histroy.setReportId(id);
		List<SeniorReportHistroy> histroyList = seniorReportService.getSnapTimeList(histroy);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("list", histroyList);
		this.getValueStack().set(DATA, dataMap);
		return DATA;
	}
	
	public String saveHistroy(){
		SeniorReportHistroy histroy = new SeniorReportHistroy();
		histroy.setGh(getUser().getYhm());
		histroy.setView(seniorReportService.getViewById(id));
		histroy.setReportId(id);
		histroy.setHistroyTime(new Date());
		seniorReportService.saveHistroy(histroy);
		getMessage().setText("操作成功!");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String reportDetail(){
		if(StringUtil.isEmpty(id)){
			throw new RuleException("请选择需要查询的报表！");
		}
		Map<String, Object> data = new HashMap<String, Object>();
		//获取表单导出模板 不存在模板的情况下则界面不提供导出按钮
		String path = "WEB-INF" + File.separator + "classes" + File.separator
		+ "exportModel" + File.separator + "seniorreport";
		path+=File.separator+id+".xls";
		path = Struts2Utils.getSession().getServletContext().getRealPath(path);
		
		File temp = new File(path);
		data.put("hasModel", temp.exists());
		//表单展现对象
		SeniorReportView view = null;
		if(StringUtil.isEmpty(snapTime)){
			view = seniorReportService.getViewById(id);
		}else{
			SeniorReportHistroy histroy = new SeniorReportHistroy();
			histroy.setId(snapTime);
			List<SeniorReportHistroy> list = seniorReportService.getSnapTimeList(histroy);
			if(!list.isEmpty()){
				view=list.get(0).getView();
				view.fillMapfromBodyList();
			}
		}
		if (view==null) {
			view=new SeniorReportView();
		}
		StringBuffer buffer = new StringBuffer();
		int x = fillTitels(view.getTitles(),buffer)+1;
		data.put("reportView", view);
		
		StringBuffer bodybuffer = new StringBuffer("<tr>");
		int y = fillBody(view.getLeft(),bodybuffer,view.getItemValueMaps(),1);
		String body = bodybuffer.toString();
		for (int i = y; i > 0; i--) {
			body = body.replaceAll("TEMP_"+i+"_"+bodybuffer.hashCode(), (y-i+1)+"");
		}
		data.put("Body", body);
		data.put("Title", "<tr><td rowspan ="+x+" colspan = "+y+" >"+view.getStartTitle()+"</td></tr>"+buffer);
		this.getValueStack().set(DATA, data);
		return DATA;
	}
	/**
	 * 复合报表表单内容展示方法【递归方法】
	 * (与SeniorReportConfigAction中同名方法逻辑相同)
	 * @param left 复合报表的行标题
	 * @param buffer 用于填充表单内容的对象
	 * @param itemValueMaps 报表的内容
	 * @param loop 当前所在的子集层数（计算占用列时使用）     外部调用统一传1
	 * @return 最大子集层数
	 */
	private int fillBody(List<ViewItem> left,StringBuffer buffer,LinkedHashMap<ViewItem,List<Col>> itemValueMaps,int loop){
		int size = 0;
		boolean flag=false;
		String key = "TEMP_"+loop+"_"+buffer.hashCode();
		for (ViewItem viewItem : left) {
			String temp=" ";
			if(flag){
				buffer.append("<tr>");
			}else{
				flag=true;
			}
			if(viewItem.getSubItem()==null||viewItem.getSubItem().isEmpty()){
				temp = " colspan = "+key;
			}
			buffer.append("<td rowspan="+viewItem.getNum()+temp+" >");
			buffer.append("<div name='row' class='tab_szcd' style='position: relative;float:none;display:block;' >");
			buffer.append("<input type='hidden' name='fieldName' value='"+viewItem.getItemId()+"' />");
			buffer.append(viewItem.getName());
			buffer.append("</div>");
			buffer.append("</td>");
			if(viewItem.getSubItem()!=null&&!viewItem.getSubItem().isEmpty()){
				int a = fillBody(viewItem.getSubItem(),buffer,itemValueMaps,loop+1);
				if(a >= size)
					size = a;
			}else{
				List<Col> cols = itemValueMaps.get(viewItem);
				for (Col c:cols) {
					buffer.append("<td>");
					if(!"0".equals(c.getValue())){
						buffer.append("<span>"+c.getValue()+"</span>");
						buffer.append("<input id='condition1' type='hidden' value=\""+viewItem.getSql()+"\"/>");
						buffer.append("<input id='condition2' type='hidden' value=\""+c.getCondition()+"\"/>");
					}else{
						buffer.append("<span style='color: #CCCCCC;'>"+c.getValue()+"</span>");
					}
					buffer.append("</td>");
				}
			}
			buffer.append("</tr>");
		}
		size++;
		return size;
	}
	/**
	 * 复合表单表头展示方法【递归方法】
	 * (与SeniorReportConfigAction中同名方法逻辑相同)
	 * @param titles 表头（列标题）对象
	 * @param buffer 用于填充表单内容的对象
	 * @return 最大子集层数
	 */
	private int fillTitels(List<ViewItem> titles,StringBuffer buffer){
		int size = 1;
		List<ViewItem> subTitels = new ArrayList<ViewItem>();
		StringBuffer str=new StringBuffer();
		for (ViewItem viewItem : titles) {
			if(viewItem.getSubItem()!=null&&!viewItem.getSubItem().isEmpty()){
				subTitels.addAll(viewItem.getSubItem());
			}
		}
		if(!subTitels.isEmpty()){
			size+=fillTitels(subTitels,str);
		}
		buffer.append("<tr>");
		for (ViewItem viewItem : titles) {
			int rows=1;
			if(viewItem.getSubItem()==null||viewItem.getSubItem().isEmpty()){
				rows = size;
			}
			buffer.append("<td style='word-break:break-all;' colspan="+viewItem.getNum()+" rowspan = "+rows+">");
			buffer.append(viewItem.getName());
			buffer.append("</div>");
			buffer.append("</td>");
		}
		buffer.append("</tr>");
		buffer.append(str);
		return size;
	}

	/**
	 * 点击数字时展现相应的人员列表
	 * @return
	 */
	public String list_rev() {
		SeniorReportConfigData data = seniorReportService.getReportById(id);
		SeniorReportConfig  config = data.getSeniorReportConfig();
		StringBuffer sql = new StringBuffer(" exists (");
		sql.append("select 1 from (");
		sql.append(config.getSql());
		sql.append(") report where report.gh=t.gh and");
		sql.append(" ("+rquery.getColumnCondition() + ") and ("
				+ rquery.getRowCondition());
		sql.append(") )");
		query.setExpress(sql.toString());
		Paginator paginator = new Paginator();
		if (query != null) {
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer) query.getToPage());

			if (paginator.getBeginIndex() <= paginator.getItems()) {
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
			}
		}

		this.getValueStack().set("pageList", dynaBeanService.findPagingInfoList(query));

		return "list_rev"; 
	}

	/**
	 * 导出数据
	 * @return
	 * @throws Exception
	 */
	public String exportData() throws Exception {
		SeniorReportView view = null;
		if(StringUtil.isEmpty(snapTime)){
			view = seniorReportService.getViewById(id);
		}else{
			SeniorReportHistroy histroy = new SeniorReportHistroy();
			histroy.setId(snapTime);
			List<SeniorReportHistroy> list = seniorReportService.getSnapTimeList(histroy);
			if(!list.isEmpty()){
				view=list.get(0).getView();
				view.fillMapfromBodyList();
			}
		}
		if (view==null) {
			view=new SeniorReportView();
		}
		getResponse().reset();
		getResponse().setCharacterEncoding("utf-8");
		getResponse().setContentType("application/vnd.ms-excel");

		String useragent = getRequest().getHeader("user-agent"); 
		String disposition = DownloadFilenameUtil.fileDisposition(useragent,
				view.getReportTitle() + ".xls");
		getResponse().setHeader("Content-Disposition", disposition);

		String path = "WEB-INF" + File.separator + "classes" + File.separator
		+ "exportModel" + File.separator + "seniorreport";
		path+=File.separator+id+".xls";
		path = Struts2Utils.getSession().getServletContext().getRealPath(path);
		
		File temp = new File(path);
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		for (ViewItem viewItem :view.getItemValueMaps().keySet()) {
			List<Col> cols = view.getItemValueMaps().get(viewItem);
			Map<String, String> map = new HashMap<String, String>();
			int index = 1;
			for (Col col : cols) {
				map.put(index+"", col.getValue());
				index++;
			}
			list.add(map);
		}
		
		ExcelExportUtil.export(temp, getResponse().getOutputStream(), list);
		return null;
	}


	public String chart() {
		this.setSuccessMessage("");
		OpenFlashChart openFlashChart = new OpenFlashChart();
		List<Element> elements = new ArrayList<Element>();
		openFlashChart.setElements(elements);
		elements.add(new Element());
		this.getValueStack().set("data", openFlashChart);
		return DATA;
	}
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSnapTime() {
		return snapTime;
	}

	public void setSnapTime(String snapTime) {
		this.snapTime = snapTime;
	}

	public ReportQuery getRquery() {
		return rquery;
	}

	public void setRquery(ReportQuery rquery) {
		this.rquery = rquery;
	}

	public DynaBeanQuery getQuery() {
		return query;
	}

	public void setQuery(DynaBeanQuery query) {
		this.query = query;
	}

	public void setSeniorReportService(ISeniorReportService seniorReportService) {
		this.seniorReportService = seniorReportService;
	}

	public void setDynaBeanService(IDynaBeanService dynaBeanService) {
		this.dynaBeanService = dynaBeanService;
	}
	public SeniorReportType getType() {
		return type;
	}
	public void setType(String type) {
		try{
		this.type = SeniorReportType.valueOf(type);
		}catch (Exception e) {
			this.type=null;
		}
	}
}
