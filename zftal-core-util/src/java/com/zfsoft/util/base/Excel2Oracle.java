package com.zfsoft.util.base;

//读取Excel的类

import java.io.OutputStream;

import java.util.List;

import jxl.Workbook;

import jxl.write.Label;

import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;


public class Excel2Oracle {

	public static void exportData(List<String[]> vec,String[] ColumnName,String[] ColumnNameCN, OutputStream os)
	throws Exception {
		WritableWorkbook wwb = Workbook.createWorkbook(os);
		WritableSheet ws = wwb.createSheet("数据导出", 0);
		try {
			for (int m = 0; m < ColumnNameCN.length; m++) {
				ws.addCell(new Label(m, 0, ColumnNameCN[m]));
			}
			int k = ColumnName.length;
			for (int i = 0; i < vec.size(); i++) {
				String[] tmp = (String[]) vec.toArray()[i];
				for (int j = 0; j < k; j++) {
					ws.addCell(new Label(j, i + 1, tmp[j]));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("数据导出失败!");
		} finally {
			wwb.write();
			wwb.close();
			os.flush();
			os.close();
		}
	}
	
}
