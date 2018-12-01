package com.zfsoft.hrm.util;

import java.io.File;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.util.base.StringUtil;

import jxl.Cell;
import jxl.Range;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * Excel导出工具类
 * @author ChenMinming
 * @date 2013-8-8
 * @version V1.0.0
 */
public class ExcelExportUtil {
	
	/**
	 * DEMO中内容填充起始标志
	 * 会以这个字段所在的单元格为起始端点进行内容填充
	 * 以此字段所在行的上一行为配置端读取key（批注设置key）
	 */
	public final static String START_POINT = "$CONTENTS";
	
	
	/**
	 * 以指定模板将内容填充导出excel到指定输出流中
	 * excel为2003版本
	 * 
	 * @param temp 指定模板
	 * @param out 导出流
	 * @param list 导出数据
	 * @throws Exception
	 */
	public static void export(File temp,OutputStream out,List<Map<String, String>> list) throws Exception{
		Workbook wbk = null;
		WritableWorkbook wwb = null;
		if(temp==null||!temp.exists()){
			throw new RuleException("请指定导出模板！");
		}
		try{
			WorkbookSettings setting = new WorkbookSettings();
			wbk = Workbook.getWorkbook(temp,setting);
			wwb = Workbook.createWorkbook(out,wbk);
			/**
			 * 需要重新设置一个较短的作者名，
			 * 不设置时可能会在调用write方法时抛出ArrayOut异常
			 * （jar包中将转换的byte数组的大小设置为固定的112）
			 */
			setting.setWriteAccess("zfsoft");
			WritableSheet[] sheets = wwb.getSheets();
			for (WritableSheet sheet : sheets) {
				Cell c = sheet.findCell(START_POINT);
				if(c == null ){
					continue;
				}
				int column = c.getColumn();
				int row = c.getRow();
				if(row == 0) return;
				Cell[] cells = sheet.getRow(row-1);
				
//				String[] key = new String[cells.length-column];
				Map<Integer, String> keyMap = new HashMap<Integer, String>();
				
//				for (int i = column; i < cells.length; i++) {
//					if(cells[i]==null||cells[i].getCellFeatures()==null)
//						continue;
//					key[i-column]=cells[i].getCellFeatures().getComment();
//					
//				}
				for (Cell cell : cells) {
					if(cell==null||cell.getColumn()<column||cell.getCellFeatures()==null)
						continue;
					keyMap.put(cell.getColumn(), cell.getCellFeatures().getComment());
				}
				for (Range r : sheet.getMergedCells()) {
					if(r.getBottomRight().getRow()==row-1){
						Cell cell = r.getTopLeft();
						if(cell==null||cell.getColumn()<column||cell.getCellFeatures()==null)
							continue;
						keyMap.put(cell.getColumn(), cell.getCellFeatures().getComment());
					}
				}
//				for (Map<String, String> map : list) {
//					for (int i = 0; i < key.length; i++) {
//						if(StringUtil.isEmpty(key[i])) continue;
//						 sheet.addCell(generateValueLabel(i+column,row,map.get(key[i])));
//					}
//					row++;
//				}
				for (Map<String, String> map : list) {
					for (Integer i : keyMap.keySet()) {
						if(StringUtil.isEmpty(keyMap.get(i))) continue;
						 sheet.addCell(generateValueLabel(i,row,map.get(keyMap.get(i))));
					}
					row++;
				}
				wwb.write();
			}
		}finally{
			if(wwb != null)
				wwb.close();
			if(wbk != null)
				wbk.close();
		}
	}
	
	public static void main(String[] args) throws Exception {
//		OutputStream out = new FileOutputStream(new File("d:\\test\\test1.xls"));
//		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("id1", "工号1");
//		map.put("id2", "工1");
//		map.put("id3", "号1");
//		list.add(map);
//		export(out, list);
//		PostInfo p = new PostInfo();
//		p.setId("415646545");
//		p.setName("45846");
//		p.setSort(1);
		
	}
	
	/**
	 * 将对象转换为导出excel时需要使用的map对象
	 * 以 无参的get方法的返回值为导出数据
	 * 
	 * 如getIdString()方法
	 * key： objectName.idstring
	 * value：getIdString()方法返回值
	 * @param objectName
	 * @param obj
	 * @return
	 */
	public static Map<String, String> object2Map(String objectName,Object obj){
		
		Method[]  methods = obj.getClass().getMethods();
		Map<String, String> map = new HashMap<String, String>();
		
		for (Method method : methods) {
			if(!method.getName().startsWith("get")) continue;
			if(method.getParameterTypes().length>0) continue;
			Object o = null;
			try{
				o = method.invoke(obj);
			}catch(Exception e){
				continue;
			}
			if (o!=null) {
				map.put(objectName+"."+method.getName().substring(3).toLowerCase(), o.toString());
			}
		}
		return map;
	}
	
	private static CellFormat getValueCellFormat() throws WriteException {
		WritableFont wf=new WritableFont(WritableFont.TAHOMA,11,WritableFont.NO_BOLD,false);
		WritableCellFormat wcf=new WritableCellFormat(wf);
		wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
		return wcf;
	}
	/**
	 * 组装内容
	 * @param column
	 * @param row
	 * @param cellContent
	 * @return
	 */
	private static Label generateValueLabel(int column,int row, Object cellContent) throws WriteException{
		WritableCellFeatures f = new WritableCellFeatures();
		String content;
		if(cellContent==null){
			content = "";
		}else{
			content = cellContent.toString();
		}
		Label label = new Label(column,row,content);
		label.setCellFeatures(f);
		label.setCellFormat(getValueCellFormat());
		return label;
	}
	
	private static CellFormat getTitleCellFormat() throws WriteException {
		WritableFont wf=new WritableFont(WritableFont.TAHOMA,11,WritableFont.BOLD,false);
		WritableCellFormat wcf=new WritableCellFormat(wf);
		wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
		wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
		wcf.setAlignment(Alignment.CENTRE);
		return wcf;
	}
	private static CellFormat getContentCellFormat() throws WriteException {
		WritableFont wf=new WritableFont(WritableFont.TAHOMA,11,WritableFont.NO_BOLD,false);
		WritableCellFormat wcf=new WritableCellFormat(wf);
		wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
		wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
		return wcf;
	}
	public static Label generateLabel(int column,int row, Object cellContent,boolean isTitel) throws WriteException{
		WritableCellFeatures f = new WritableCellFeatures();
		String content;
		if(cellContent==null){
			content = "";
		}else{
			content = cellContent.toString();
		}
		Label label = new Label(column,row,content);
		label.setCellFeatures(f);
		if(isTitel){
			label.setCellFormat(getTitleCellFormat());
		}else{
			label.setCellFormat(getContentCellFormat());
		}
		return label;
	}
	
}
