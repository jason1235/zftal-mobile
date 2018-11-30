package com.zfsoft.hrm.report.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.common.system.SubSystemHolder;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.dataprivilege.util.DeptFilterUtil;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.dyna.service.svcinterface.IDynaBeanService;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.search.constants.SearchConstants;
import com.zfsoft.hrm.baseinfo.snapshot.query.SnapshotInfoQuery;
import com.zfsoft.hrm.baseinfo.snapshot.service.svcinterface.ISnapshotInfoService;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.config.ICodeConstants;
import com.zfsoft.hrm.config.IConstants;
import com.zfsoft.hrm.core.util.DownloadFilenameUtil;
import com.zfsoft.hrm.report.dto.Col;
import com.zfsoft.hrm.report.entity.ReportQuery;
import com.zfsoft.hrm.report.entity.ReportView;
import com.zfsoft.hrm.report.entity.ReportXmlFile;
import com.zfsoft.hrm.report.ofcentity.Element;
import com.zfsoft.hrm.report.ofcentity.OpenFlashChart;
import com.zfsoft.hrm.report.service.IReportService;
import com.zfsoft.hrm.report.xentity.Item;
import com.zfsoft.hrm.report.xentity.ReportFieldConfig;
import com.zfsoft.hrm.report.xentity.ReportViewField;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.chart.ChartUtils;
import com.zfsoft.util.date.DateTimeUtil;
import com.zfsoft.util.jaxb.JaxbUtil;

/**
 * 报表展示
 * 
 * @author 沈鲁威 Patrick Shen
 * @since 2012-9-13
 * @version V1.0.0
 */
public class ReportAction extends HrmAction {

	private static final long serialVersionUID = 7215513140769016822L;

	private IReportService reportService;
	private String id;
	private String snapTime;

	private IDynaBeanService dynaBeanService;
	private ReportQuery rquery = new ReportQuery();
	private SnapshotInfoQuery query = getDynaQuery();

	private SnapshotInfoQuery getDynaQuery() {
		SnapshotInfoQuery dynaBeanQuery = new SnapshotInfoQuery(
				InfoClassCache.getOverallInfoClass(IConstants.INFO_CATALOG_TYPE_DEFAULT));
		dynaBeanQuery.setPerPageSize(20);
		return dynaBeanQuery;
	}

	public String list() {
		this.getValueStack().set("snapTimeList", reportService.getSnapTimeList(id));
		ReportView view;
		if (StringUtil.isEmpty(snapTime)) {
			view = reportService.getViewById(id);
		} else {
			view = reportService.getViewById(id, snapTime);
		}
		fullDataXML(view);
		this.getValueStack().set("reportView", view);
		return LIST_PAGE;
	}

	private void fullDataXML(ReportView view) {  
//		String[] color = new String[] { "9D080D", "AFD8F8", "F6BD0F", "8BBA00", "FF8E46", "FF8E46", "008E8E", "D64646", "8E468E",
//				"588526", "B3AA00", "008ED6", "A186BE" };
//		StringBuilder xmlData = new StringBuilder();
//		xmlData.append("<?xml version='1.0' encoding='GBK'?>");
//		xmlData.append("<graph caption='" + StringToXML(view.getReportTitle())
//				+ "' xAxisName='' yAxisName='' decimalPrecision='0' formatNumberScale='0'>");
//		xmlData.append("<categories>");
//
//		for (Item title : view.getKeySet()) {
//			if (!title.getName().equals("合计") && !title.getName().equals("总计") && !title.getName().equals("汇总")
//					&& !title.getName().equals("总数")) {
//				xmlData.append("<category name='" + StringToXML(title.getName()) + "' />");
//			}
//		}
//		xmlData.append("</categories>");
//		int i = 0;
//		boolean first = true;
//		for (Item title : view.getTitles()) {
//			if (!title.getName().equals("合计") && !title.getName().equals("总计") && !title.getName().equals("汇总")
//					&& !title.getName().equals("总数")) {
//				if (first) {
//					first = false;
//					continue;
//				}
//				xmlData.append("<category name='" + StringToXML(title.getName()) + "' />");
//				String usecolor = color[i % color.length];
//				xmlData.append("<dataset seriesName='" + StringToXML(title.getName()) + "' color='" + usecolor
//						+ "' showValues='1'>");
//				for (Item row : view.getKeySet()) {
//					if (!row.getName().equals("合计") && !row.getName().equals("总计") && !row.getName().equals("汇总")
//							&& !row.getName().equals("总数")) {
//						Col col = view.getItemValueMaps().get(row).get(i);
//						xmlData.append("<set value='" + col.getValue() + "' />");
//					}					
//				}
//				xmlData.append("</dataset>");
//			}
//			i++;
//		}
//		xmlData.append("</graph>");
//		this.getValueStack().set("xmlData", xmlData);
		
		StringBuilder echarts_obj = new StringBuilder();
		echarts_obj.append("{title : {text: '"+view.getReportTitle()+"'},");
		echarts_obj.append("tooltip : {trigger: 'axis'},legend: {data:[");
		for (Item row : view.getKeySet()) {
			if (!row.getName().equals("合计") && !row.getName().equals("总计") && !row.getName().equals("汇总")
					&& !row.getName().equals("总数")) {
				echarts_obj.append("'" + row.getName() + "',");
			}
		}
		echarts_obj=echarts_obj.deleteCharAt(echarts_obj.length()-1);
		echarts_obj.append("]},");
		echarts_obj.append("toolbox: {show : true,feature : {mark : {show: true},dataView : {show: true, readOnly: true},magicType : {show: true, type: ['line', 'bar']},restore : {show: true},saveAsImage : {show: true}}},");
		echarts_obj.append("calculable : false,");
		echarts_obj.append("xAxis : [{type : 'category',data : [");
		boolean first=true;
		for (Item title : view.getTitles()) {
			if (first) {
				first = false;
				continue;
			}
			if (!title.getName().equals("合计") && !title.getName().equals("总计") && !title.getName().equals("汇总")
					&& !title.getName().equals("总数")) {
				echarts_obj.append("'" + title.getName() + "',");
			}
		}
		echarts_obj=echarts_obj.deleteCharAt(echarts_obj.length()-1);
		echarts_obj.append("]}],");
		echarts_obj.append("yAxis:[{type : 'value'}],");
		echarts_obj.append("series : [");
		for (Item row : view.getKeySet()) {
			if (!row.getName().equals("合计") && !row.getName().equals("总计") && !row.getName().equals("汇总")
					&& !row.getName().equals("总数")) {
				echarts_obj.append("{ name:'"+row.getName()+"',type:'bar',data:[");
				List<Col> l= view.getItemValueMaps().get(row);
				for (int j = 1; j < l.size(); j++) {
					echarts_obj.append(l.get(j).getValue() + ",");
				}
				echarts_obj=echarts_obj.deleteCharAt(echarts_obj.length()-1);
				echarts_obj.append("],markPoint : {data : [{type : 'max', name: '最大值'},{type : 'min', name: '最小值'}]}},");
			}
		}
		echarts_obj=echarts_obj.deleteCharAt(echarts_obj.length()-1);
		echarts_obj.append("]");
		if(view.getTitles().size()*view.getKeySet().size()>30){
			int length = 4*150/(view.getTitles().size()-1);
		echarts_obj.append(",dataZoom : {show : true,realtime : true,y: 36,height: 20,start : 0,end : "+length+"}");
		}
		echarts_obj.append("}");
		this.getValueStack().set("echarts_obj", echarts_obj);
	}

	private String StringToXML(String value) {
		value = value.toString().replaceAll("&", "&amp;");
		value = value.toString().replaceAll("<", "&lt;");
		value = value.toString().replaceAll(">", "&gt;");
		return value;
	}

	public String list_rev() {
		//添加数据范围过滤
		ReportXmlFile config = reportService.getReportById(id);
		
		if (StringUtil.isEmpty(snapTime)) {
			rquery.setSql(config.getSql());
		}else{
			rquery.setSql(config.getHistorySql());
		}
		if("1".equals(config.getSftjqxfw())){
			String deptFilter=DeptFilterUtil.getCondition("", "dwm");
			if(StringUtil.isEmpty(deptFilter)){
				deptFilter="1!=1";
			}
			String express = "";
			
			if (!StringUtil.isEmpty(deptFilter)) {
				express += "(" + deptFilter + ")";
				if(deptFilter.indexOf("where")!=-1){				
					express = rquery.getSql()+" where " + express;
				}else{
					express = rquery.getSql()+" and " + express;
				}
				rquery.setSql(express);
			}
		}
		this.getValueStack().set("pageList", reportService.findDetailPageList(rquery));
		ReportFieldConfig c = JaxbUtil.getObjectFromXml(config.getFieldConfig(), ReportFieldConfig.class);
		if (c!=null){
			this.getValueStack().set("fieldConfig", c.getFields());
		}else{
			List<ReportViewField> fieldConfig = new ArrayList<ReportViewField>();
			ReportViewField f1 = new ReportViewField();
			f1.setFieldDesc("职工号");
			f1.setFieldType("TEXT");
			f1.setFieldName("GH");
			fieldConfig.add(f1);
			ReportViewField f2 = new ReportViewField();
			f2.setFieldDesc("姓名");
			f2.setFieldType("TEXT");
			f2.setFieldName("XM");
			fieldConfig.add(f2);
			ReportViewField f3 = new ReportViewField();
			f3.setFieldDesc("性别");
			f3.setFieldType("CODE");
			f3.setFieldName("XBM");
			f3.setFieldFormat(ICodeConstants.SEX);
			fieldConfig.add(f3);
			ReportViewField f4 = new ReportViewField();
			f4.setFieldDesc("出生年月");
			f4.setFieldType("TIME");
			f4.setFieldName("CSRQ");
			f4.setFieldFormat("yyyy-MM");
			fieldConfig.add(f4);
			ReportViewField f5 = new ReportViewField();
			f5.setFieldDesc("部门");
			f5.setFieldType("CODE");
			f5.setFieldName("DWM");
			f5.setFieldFormat(ICodeConstants.DM_DEF_ORG);
			fieldConfig.add(f5);
			ReportViewField f6 = new ReportViewField();
			f6.setFieldDesc("参加工作时间");
			f6.setFieldType("TIME");
			f6.setFieldName("RZRQ");
			f6.setFieldFormat("yyyy-MM-dd");
			fieldConfig.add(f6);
			this.getValueStack().set("fieldConfig", fieldConfig);
		}
		return "list_rev";
	}

	public String exportData() throws Exception {
		ReportView reportView;
		if (StringUtil.isEmpty(snapTime)) {
			reportView = reportService.getViewById(id);
		} else {
			reportView = reportService.getViewById(id, snapTime);
		}

		getResponse().reset();
		getResponse().setCharacterEncoding("utf-8");
		getResponse().setContentType("application/vnd.ms-excel");

		String useragent = getRequest().getHeader("user-agent");
		String filename = DateTimeUtil.getFormatDate(new Date(), "yyyy-MM-dd_HHmmss");
		String disposition = DownloadFilenameUtil.fileDisposition(useragent, filename + ".xls");
		getResponse().setHeader("Content-Disposition", disposition);

		WritableWorkbook wwb = Workbook.createWorkbook(getResponse().getOutputStream());
		WritableSheet sheet1 = wwb.createSheet(reportView.getReportTitle(), 0);
		Label label;
		int i = 0;
		
		int ic = 0;
		String[] color = new String[] { "9D080D", "AFD8F8", "F6BD0F", "8BBA00", "FF8E46", "FF8E46", "008E8E", "D64646", "8E468E",
				"588526", "B3AA00", "008ED6", "A186BE" };
		
		List<String> columns = new ArrayList<String>();
		List<String> rows = new ArrayList<String>();
		List<String> colors = new ArrayList<String>();
		List<Double> rowdata;
		ArrayList<List<Double>> allData = new ArrayList<List<Double>>();
		boolean first = true;
		for (Item item : reportView.getTitles()) {
			label = generateTheadLabel(i, 0, item.getName(), item.getCondition());
			sheet1.addCell(label);
			if (!item.getName().equals("合计") && !item.getName().equals("总计") && !item.getName().equals("汇总")
					&& !item.getName().equals("总数")) {
				if (first) {
					first = false;
					i++;
					continue;
				}
				rows.add(item.getName());
				rowdata = new ArrayList<Double>();
				colors.add(color[ic % color.length]);
				for (Item row : reportView.getKeySet()) {
					if (!row.getName().equals("合计") && !row.getName().equals("总计") && !row.getName().equals("汇总")
							&& !row.getName().equals("总数")) {
						Col col = reportView.getItemValueMaps().get(row).get(ic);
						rowdata.add(Double.valueOf(col.getValue()));
					}					
				}
				allData.add(rowdata);
			}
			ic++;
			i++;
		}
		i = 1;
		int j = 1;
		for (Item item : reportView.getItemValueMaps().keySet()) {
			label = generateTheadLabel(0, i, item.getName(), item.getCondition());
			sheet1.addCell(label);
			j = 1;
			for (Col col : reportView.getItemValueMaps().get(item)) {
				label = new Label(j, i, col.getValue(), getValueCellFormat());
				sheet1.addCell(label);
				j++;
			}
			i++;
			
			if (!item.getName().equals("合计") && !item.getName().equals("总计") && !item.getName().equals("汇总")
					&& !item.getName().equals("总数")) {
				columns.add(item.getName());
			}
		}
		
		Map<String, String[]> rck = new HashMap<String, String[]>();
		double[][] data = new double[allData.size()][];
		double[] rdata;
		int rdsC = 0;
		for (List<Double> rds : allData) {
			rdata = new double[rds.size()];
			int rdc = 0;
			for (Double rd : rds) {
				rdata[rdc] = rd.doubleValue();
				rdc++;
			}
			data[rdsC] = rdata;
			rdsC++;
		}
		String[] rk = new String[rows.size()];
		for (int rkc = 0; rkc < rows.size(); rkc++) {
			rk[rkc] = rows.get(rkc);
		}
		
		String[] ck = new String[columns.size()];
		for (int ckc = 0; ckc < columns.size(); ckc++) {
			ck[ckc] = columns.get(ckc);
		}

		rck.put(ChartUtils.ROW_KEY, rk);
		rck.put(ChartUtils.COLUMN_KEY, ck);
		String imagePath = "";
//		try {
//			imagePath = ChartUtils.getBarChart3DImage(null, rck, data, SubSystemHolder.getPropertiesValue("chart_path"), filename, ChartUtils.IMAGE_TYPE_PNG, colors);
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		File imageFile = new File(imagePath);
		WritableImage image = new WritableImage(0, i + 2, data[0].length * 2, data[0].length * 4, imageFile);
		sheet1.addImage(image);

		wwb.write();
		wwb.close();
		return null;
	}

	/**
	 * 表头样式
	 * 
	 * @return
	 * @throws WriteException
	 */
	private CellFormat getTitleCellFormat() throws WriteException {
		WritableFont wf = new WritableFont(WritableFont.TAHOMA, 11, WritableFont.BOLD, false);
		WritableCellFormat wcf = new WritableCellFormat(wf);
		wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
		wcf.setBackground(Colour.GRAY_25);
		return wcf;
	}

	/**
	 * 组装表头，标注
	 * 
	 * @param column
	 * @param row
	 * @param cellContext
	 * @param cellComment
	 * @return
	 * @throws WriteException
	 */
	private Label generateTheadLabel(int column, int row, String cellContext, String cellComment) throws WriteException {
		WritableCellFeatures f = new WritableCellFeatures();
		f.setComment(cellComment);
		Label label = new Label(column, row, cellContext);
		label.setCellFormat(getTitleCellFormat());
		label.setCellFeatures(f);
		return label;
	}

	private CellFormat getValueCellFormat() throws WriteException {
		WritableFont wf = new WritableFont(WritableFont.TAHOMA, 11, WritableFont.NO_BOLD, false);
		WritableCellFormat wcf = new WritableCellFormat(wf);
		wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
		return wcf;
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

	public void setReportService(IReportService reportService) {
		this.reportService = reportService;
	}

	public void setDynaBeanService(IDynaBeanService dynaBeanService) {
		this.dynaBeanService = dynaBeanService;
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

	public SnapshotInfoQuery getQuery() {
		return query;
	}

	public void setQuery(SnapshotInfoQuery query) {
		this.query = query;
	}

}
