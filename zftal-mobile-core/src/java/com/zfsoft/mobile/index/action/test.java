package com.zfsoft.mobile.index.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.cxf.wsdl.http.UrlEncoded;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.junit.Test;

import com.anysoft.webservicetest.encoder.DBEncrypt;
import com.common.WebServiceConf;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jw.httpUtils.HttpRequest;
import com.zfsoft.common.system.BaseHolder;
import com.zfsoft.mobile.classCommunity.entity.FirstAnswerItemEntity;
import com.zfsoft.mobile.common.utils.HttpClientUtil;
import com.zfsoft.mobile.peEvaluation.query.PEDataQuery;
import com.zfsoft.mobile.peEvaluation.until.GeneralUtil;
import com.zfsoft.untils.AESEncrypt;
import com.zfsoft.util.encrypt.Encrypt;

public class test {

	@Test
	public void test() throws ParseException, IOException {
		
	}

	@Test
	public void getgeneral() {
		String a = Encrypt.encrypt("123123.");
		System.out.println(a);
	}

	@Test
	public void testImport() {

		File file = new File(
				"C:/Users/Public/Pictures/Sample Pictures/test.xls");
		System.out.println(file.getName());
		Workbook workbook = getWorkBook(file, file.getName());
		if (workbook != null) {
			Sheet sheet = workbook.getSheetAt(0);

			for (Row r : sheet) {
				if (r.getRowNum() < 1) {
					continue;
				}
				for (int a = 0; a < 12; a++) {
					System.out.println(a + "=" + getCellValue(r.getCell(a)));
				}
			
			}
		}

	}

	/** 
	 * 对Excel的各个单元格的格式进行判断并转换 
	 */  
	private String getCellValue(Cell cell) {   
	        String cellValue = "";   
	        DecimalFormat df = new DecimalFormat("#");   
	        if(cell==null||cell.equals("")||cell.getCellType() ==HSSFCell.CELL_TYPE_BLANK){
	        	return cellValue;
	        }
	        switch (cell.getCellType()) {   
	        case HSSFCell.CELL_TYPE_STRING:   
	            cellValue =cell.getRichStringCellValue().getString().trim();   
	            break;   
	        case HSSFCell.CELL_TYPE_NUMERIC:   
	            cellValue =df.format(cell.getNumericCellValue()).toString();   
	            break;   
	        case HSSFCell.CELL_TYPE_BOOLEAN:   
	            cellValue =String.valueOf(cell.getBooleanCellValue()).trim();   
	            break;   
	        default:   
	            cellValue = "";   
	        }   
	        return cellValue;   
	    }   
	
	private final static String xls = "xls";
	private final static String xlsx = "xlsx";

	public static Workbook getWorkBook(File file, String filename) {
		try {
			// String fileName = file.getName();
			InputStream input = new FileInputStream(file); // 建立输入流
			boolean isE2007 = false; // 判断是否是excel2007格式
			if (filename.endsWith("xlsx"))
				isE2007 = true;
			Workbook workbook = null;
			// 根据文件格式(2003或者2007)来初始化
			if (isE2007)
				workbook = new XSSFWorkbook(input);
			else
				workbook = new HSSFWorkbook(input);

			return workbook;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
