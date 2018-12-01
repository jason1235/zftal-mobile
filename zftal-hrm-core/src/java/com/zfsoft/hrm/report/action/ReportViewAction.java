package com.zfsoft.hrm.report.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.report.dto.Col;
import com.zfsoft.hrm.report.entity.ReportQuery;
import com.zfsoft.hrm.report.entity.ReportXmlFile;
import com.zfsoft.hrm.report.file.XmlFileUtil;
import com.zfsoft.hrm.report.service.IReportService;
import com.zfsoft.hrm.report.xentity.Item;
import com.zfsoft.hrm.report.xentity.ReportContent;
import com.zfsoft.util.date.TimeUtil;

/**
 * 报表分析展示
 */
public class ReportViewAction extends HrmAction {

	private static final long serialVersionUID = 7215513140769016822L;

	private IReportService reportService;
	private String id;
	private ReportQuery rquery = new ReportQuery();
	private Item row;
	private Item col;
	private int num=5;


	
	public String list(){
		List<String> times = reportService.getSnapTimeList(id);
		ReportXmlFile reportXml = reportService.getReportById(id);
		ReportContent reportContent=XmlFileUtil.getXmlTitleSet(reportXml.getContentStream());
		if(rquery==null)rquery = new ReportQuery();
		rquery.setSql(reportContent.getSql());
		if (col != null ) {
			col=reportContent.getColumn(col.getFieldName());
		}
		if (col == null ) {
			col = reportContent.getColumns().get(0);
		}
		if (row != null ) {
			row=reportContent.getRow(row.getFieldName());
		}
		if (row == null ) {
			row = reportContent.getRows().get(0);
		}
		rquery.setColumnCondition(col.getCondition());
		rquery.setRowCondition(row.getCondition());
		String year = TimeUtil.getYear();
		Map<String, Integer> data = new HashMap<String, Integer>();
		data.put(year, reportService.getValue(rquery));
		
		rquery.setSql(reportContent.getHistorySql());
		for (int i = 1; i < num; i++) {
			String oldYear = String.valueOf(Integer.valueOf(year)-i);
			int value = 0;
			for (String time : times) {
				if (time.startsWith(oldYear.toString())) {
					rquery.setSnapTime(time);
					value = reportService.getValue(rquery);
					break;
				}
			}
			data.put(oldYear, value);
		}
		fullDataXML(reportContent, data);
		this.getValueStack().set("reportContent", reportContent);
		return "list";
	}

	private void fullDataXML(ReportContent reportContent,Map data) {
//		String[] color = new String[] { "9D080D", "AFD8F8", "F6BD0F", "8BBA00", "FF8E46", "FF8E46", "008E8E", "D64646", "8E468E",
//				"588526", "B3AA00", "008ED6", "A186BE" };
//		StringBuffer xmlData = new StringBuffer();
//		xmlData.append("<?xml version='1.0' encoding='GBK'?>");
//		xmlData.append("<graph caption='" + StringToXML(reportContent.getTitle()+"("+row.getName()+"_"+col.getName()+")")
//				+ "' xAxisName='' yAxisName='' decimalPrecision='0' formatNumberScale='0'>");
//		String year = TimeUtil.getYear();
//		for (int i=num-1;i>=0;i--) {
//				String oldYear = String.valueOf(Integer.valueOf(year)-i);
//				xmlData.append("<category name='" + StringToXML(oldYear+"年") + "' />");
//				String usecolor = color[i % color.length];
//				xmlData.append("<set name='" + StringToXML(oldYear+"年") + "' value='"+data.get(oldYear)+"' color='"+usecolor+"' showValues='1'/>");
//		}
//		xmlData.append("</graph>");
//		this.getValueStack().set("xmlData", xmlData);
		
		
		StringBuilder echarts_obj = new StringBuilder();
		echarts_obj.append("{title : {text: '"+reportContent.getTitle()+"("+row.getName()+"_"+col.getName()+")"+"'},");
		echarts_obj.append("tooltip : {trigger: 'axis'},");
		echarts_obj.append("toolbox: {show : true,feature : {mark : {show: true},dataView : {show: true, readOnly: true},magicType : {show: true, type: ['line', 'bar']},restore : {show: true},saveAsImage : {show: true}}},");
		echarts_obj.append("calculable : false,");
		echarts_obj.append("xAxis : [{type : 'category',data : [");
		String year = TimeUtil.getYear();
		for (int i=num-1;i>=0;i--) {
			String oldYear = String.valueOf(Integer.valueOf(year)-i);
				echarts_obj.append("'" + oldYear + "',");
		}
		echarts_obj=echarts_obj.deleteCharAt(echarts_obj.length()-1);
		echarts_obj.append("]}],");
		echarts_obj.append("yAxis:[{type : 'value'}],");
		echarts_obj.append("series : [");
		echarts_obj.append("{ name:'',type:'bar',data:[");
		year = TimeUtil.getYear();
		for (int i=num-1;i>=0;i--) {
				String oldYear = String.valueOf(Integer.valueOf(year)-i);
					echarts_obj.append(data.get(oldYear) + ",");
				}
		echarts_obj=echarts_obj.deleteCharAt(echarts_obj.length()-1);
		echarts_obj.append("],markPoint : {data : [{type : 'max', name: '最大值'},{type : 'min', name: '最小值'}]}}");
		echarts_obj.append("]}");
		this.getValueStack().set("echarts_obj", echarts_obj);
	}

	private String StringToXML(String value) {
		value = value.toString().replaceAll("&", "&amp;");
		value = value.toString().replaceAll("<", "&lt;");
		value = value.toString().replaceAll(">", "&gt;");
		return value;
	}

	/**
	 * 返回
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置
	 * @param id 
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 返回
	 */
	public Item getRow() {
		return row;
	}

	/**
	 * 设置
	 * @param row 
	 */
	public void setRow(Item row) {
		this.row = row;
	}

	/**
	 * 返回
	 */
	public Item getCol() {
		return col;
	}

	/**
	 * 设置
	 * @param col 
	 */
	public void setCol(Item col) {
		this.col = col;
	}

	/**
	 * 返回
	 */
	public int getNum() {
		return num;
	}

	/**
	 * 设置
	 * @param num 
	 */
	public void setNum(int num) {
		this.num = num;
	}

	/**
	 * 设置
	 * @param reportService 
	 */
	public void setReportService(IReportService reportService) {
		this.reportService = reportService;
	}


}
