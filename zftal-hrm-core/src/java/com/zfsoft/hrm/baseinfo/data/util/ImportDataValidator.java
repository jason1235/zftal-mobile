package com.zfsoft.hrm.baseinfo.data.util;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.hrm.baseinfo.code.entities.Item;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.baseinfo.data.entity.InfoType;
import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.orcus.lang.TimeUtil;

/** 
 * 数据导入验证器
 * @author jinjj
 * @date 2012-10-22 下午04:46:35 
 *  
 */
public class ImportDataValidator {

	private Sheet sheet ;
	private Map<Integer,InfoProperty> map = new HashMap<Integer,InfoProperty>();
	private int maxValidateError = 200;
	private Map<String,HashMap<String,String>> dataMap = new LinkedHashMap<String,HashMap<String,String>>();
	private String classId;
	/**
	 * 检查内容
	 * @throws Exception
	 */
	public void checkContent(File file,String classId) throws Exception{
		try{
			Workbook wb = Workbook.getWorkbook(file);
			sheet = wb.getSheet(0);
		}catch (Exception e) {
			throw new RuleException("加载数据文件出错",e);
		}
		checkHead(classId);
		validateData();
	}
	
	/**
	 * 检查内容
	 * @throws Exception
	 */
	public void checkContentNoCheckGh(File file) throws Exception{
		try{
			Workbook wb = Workbook.getWorkbook(file);
			sheet = wb.getSheet(0);
		}catch (Exception e) {
			throw new RuleException("加载数据文件出错",e);
		}
		checkHeadNoCheckGh();
		validateDataNoCheckGh();
	}
	
	protected void checkHead(String valiClassId) throws Exception{
		DataProcessInfoUtil.setInfo("数据头校验开始...", null);
		int columns = sheet.getColumns();
		Cell c = sheet.getCell(0, 0);
		
		classId = getComment(c);
		if(StringUtils.isEmpty(classId)){
			DataProcessInfoUtil.setInfo("数据导入模版校验失败，请检查模版是否正确！", null);
			throw new RuleException("信息类编号缺失，请校验数据表头");
		}
		InfoClass clazz = InfoClassCache.getInfoClass(classId);
		if(clazz == null){
			throw new RuleException("数据导入模版校验失败，请检查模版是否正确！");
		}
		DataProcessInfoUtil.setInfo("数据格式解析，检定为["+clazz.getName()+"]", null);
		
		if(valiClassId != null&&!classId.equals(valiClassId.trim())){
			DataProcessInfoUtil.setInfo("数据导入模版校验失败，请检查模版是否正确！", null);
			throw new RuleException("信息类编号与要导入的信息类不一致！");
		}
		if(clazz.getEditables().size()!=(columns-2)){
			throw new RuleException("信息类数据项个数不匹配，请比对模板");
		}
		Map<String,InfoProperty> pMap = new HashMap<String,InfoProperty>();
		pMap.put("gh", clazz.getPropertyByName("gh"));
		for(InfoProperty p : clazz.getEditables()){
			pMap.put(p.getFieldName(), p);
		}
		for(int i = 1;i<columns;i++){
			Cell cell =sheet.getCell(i, 0);
			String column = getComment(cell);
			if(StringUtils.isEmpty(column)){
				throw new RuleException(cell.getContents()+"标注信息缺失");
			}
			InfoProperty p = pMap.get(column);
			if(p==null){
				DataProcessInfoUtil.setInfo("数据导入模版校验失败，请检查模版是否正确！", null);
				throw new RuleException(cell.getContents()+"标注信息错误，无法匹配，请比对信息类配置");
			}
			map.put(i,p);
		}
		DataProcessInfoUtil.setInfo("数据头校验结束", null);
	}
	
	protected void checkHeadNoCheckGh() throws Exception{
		DataProcessInfoUtil.setInfo("数据头校验开始...", null);
		int columns = sheet.getColumns();
		Cell c = sheet.getCell(0, 0);
		
		classId = getComment(c);
		if(StringUtils.isEmpty(classId)){
			DataProcessInfoUtil.setInfo("数据导入模版校验失败，请检查模版是否正确！", null);
			throw new RuleException("信息类编号缺失，请校验数据表头");
		}
		InfoClass clazz = InfoClassCache.getInfoClass(classId);
		if(clazz == null){
			throw new RuleException("数据导入模版校验失败，请检查模版是否正确！");
		}
		DataProcessInfoUtil.setInfo("数据格式解析，检定为["+clazz.getName()+"]", null);
		if(clazz.getEditables().size()!=(columns-1)){
			throw new RuleException("信息类数据项个数不匹配，请比对模板");
		}
		Map<String,InfoProperty> pMap = new HashMap<String,InfoProperty>();
		for(InfoProperty p : clazz.getEditables()){
			pMap.put(p.getFieldName(), p);
		}
		for(int i = 1;i<columns;i++){
			Cell cell =sheet.getCell(i, 0);
			String column = getComment(cell);
			if(StringUtils.isEmpty(column)){
				throw new RuleException(cell.getContents()+"标注信息缺失");
			}
			InfoProperty p = pMap.get(column);
			if(p==null){
				DataProcessInfoUtil.setInfo("数据导入模版校验失败，请检查模版是否正确！", null);
				throw new RuleException(cell.getContents()+"标注信息错误，无法匹配，请比对信息类配置");
			}
			map.put(i,p);
		}
		DataProcessInfoUtil.setInfo("数据头校验结束", null);
	}
	
	protected String getComment(Cell c){
		if(c.getCellFeatures()!=null){
			String comment=c.getCellFeatures().getComment();
			if(StringUtils.isEmpty(comment)){
				return null;
			}
			int commaIndex=comment.indexOf(",");
			if(commaIndex==-1){
				return comment;
			}
			return comment.substring(0, commaIndex);
		}else{
			return null;
		}
	}
	
	private void validateData() throws Exception{
		DataProcessInfoUtil.setInfo("数据内容校验开始...", null);
		int rows = sheet.getRows();
		int columns = sheet.getColumns();
		int cnt = 0;
		if(rows==1){
			DataProcessInfoUtil.setInfo("数据内容行数为0行", InfoType.WARN);
		}
		int total = (rows-1)*(columns-1);
		int step = 0;
		HashMap<String,String> uniqueMap = new HashMap<String,String>();
		for(int m=1;m<rows;m++){
			HashMap<String,String> data = new HashMap<String,String>();
			for(int n=1;n<columns;n++){
				Cell c = sheet.getCell(n,m);
				InfoProperty p = map.get(n);
				try{
					DataProcessInfoUtil.setStep("数据校验:", ++step, total);
					String v = c.getContents();
					if(c.getType().equals(CellType.DATE)){
						Date d = ((DateCell)c).getDate();
						v = TimeUtil.format(d, TimeUtil.yyyy_MM_dd);
					}
					if(v == null){
						v = "";
					}
					String value = validateCell(v,p);
					data.put(p.getFieldName(), value);
					InfoClass clazz = InfoClassCache.getInfoClass(classId);
					if(!clazz.isMoreThanOne()){
						if(p.getFieldName().equals("gh")){
							if(uniqueMap.get(value)!=null){
								throw new RuleException("职工号["+c.getContents()+"]数据文档中已重复存在");
							}
						}
					}
				}catch (Exception e) {
					if(e instanceof RuleException){
						DataProcessInfoUtil.setInfo("序号:"+m+" 列名:["+p.getName()+
								"("+p.getTypeInfo().getText()+")] 校验失败,原因:"+e.getMessage(), InfoType.ERROR);
						cnt++;
						if(cnt>=maxValidateError){
							throw new RuleException("校验失败次数达到上限"+maxValidateError+"次");	
						}
					}else{
						throw new RuleException("校验数据发生未知异常",e);
					}
				}
			}
			dataMap.put(m+"", data);
			uniqueMap.put(data.get("gh"), data.get("gh"));
			//TODO 测试延迟
			Thread.sleep(10);
		}
		if(cnt>0){
			throw new RuleException("校验含错误信息,未通过");	
		}
		DataProcessInfoUtil.setInfo("数据内容校验结束", null);
	}
	
	private void validateDataNoCheckGh() throws Exception{
		DataProcessInfoUtil.setInfo("数据内容校验开始...", null);
		int rows = sheet.getRows();
		int columns = sheet.getColumns();
		int cnt = 0;
		if(rows==1){
			DataProcessInfoUtil.setInfo("数据内容行数为0行", InfoType.WARN);
		}
		int total = (rows-1)*(columns-1);
		int step = 0;
		HashMap<String,String> uniqueMap = new HashMap<String,String>();
		for(int m=1;m<rows;m++){
			HashMap<String,String> data = new HashMap<String,String>();
			for(int n=1;n<columns;n++){
				Cell c = sheet.getCell(n,m);
				InfoProperty p = map.get(n);
				try{
					DataProcessInfoUtil.setStep("数据校验:", ++step, total);
					String v = c.getContents();
					if(c.getType().equals(CellType.DATE)){
						Date d = ((DateCell)c).getDate();
						v = TimeUtil.format(d, TimeUtil.yyyy_MM_dd);
					}
					if(v == null){
						v = "";
					}
					String value = validateCell(v,p);
					data.put(p.getFieldName(), value);
					InfoClass clazz = InfoClassCache.getInfoClass(classId);
					
					if(p.getFieldName().equals("gh")&&StringUtils.isNotEmpty(value)){
						if(!clazz.isMoreThanOne()){
							if(uniqueMap.get(value)!=null){
								throw new RuleException("工号["+c.getContents()+"]数据文档中已重复存在");
							}
							uniqueMap.put(value,value);
						}
					}
				}catch (Exception e) {
					if(e instanceof RuleException){
						DataProcessInfoUtil.setInfo("序号:"+m+" 列名:["+p.getName()+
								"("+p.getTypeInfo().getText()+")] 校验失败,原因:"+e.getMessage(), InfoType.ERROR);
						cnt++;
						if(cnt>=maxValidateError){
							throw new RuleException("校验失败次数达到上限"+maxValidateError+"次");	
						}
					}else{
						throw new RuleException("校验数据发生未知异常",e);
					}
				}
			}
			dataMap.put(m+"", data);
		}
		if(cnt>0){
			throw new RuleException("校验含错误信息,未通过");	
		}
		DataProcessInfoUtil.setInfo("数据内容校验结束", null);
	}
	
	public static String validateCell(String value,InfoProperty p) throws Exception{
		String type = p.getTypeInfo().getName();
		if(Type.CODE.equals(type)){
			return codeProcess(value,p);
		}else if(Type.DATE.equals(type)){
			return dateProcess(value,p);
		}else if(Type.MONTH.equals(type)){
			return dateProcess(value,p);
		}else if(Type.YEAR.equals(type)){
			return dateProcess(value,p);
		}else if(Type.SIGLE_SEL.equals(type)){
			return singleProcess(value,p);
		}
		return commonProcess(value,p);
	}
	
	private static String codeProcess(String value,InfoProperty p)throws Exception{
		if(p.getNeed()&&StringUtils.isEmpty(value)){
			throw new RuleException("内容缺失");
		}
		if(StringUtils.isEmpty(value)){//代码库字段数据，不必填为空时直接返回
			return value;
		}
		List<Item> list = CodeUtil.getAvailableItems(p.getCodeId());
		String result = null;
		for(Item i:list){
			if(i.getDescription().equals(value)){
				result = i.getGuid();
				break;
			}
		}
		if(StringUtils.isEmpty(result)){
			throw new RuleException("代码库转换失败["+value+"]");
		}
		return result;
	}
	
	private static String commonProcess(String value,InfoProperty p)throws Exception{
		if(p.getNeed() && StringUtils.isEmpty(value)){
			throw new RuleException("内容缺失");
		}
		return value;
	}
	
	private static String dateProcess(String value,InfoProperty p) throws Exception{
		Date result = null;
		if(p.getNeed() && StringUtils.isEmpty(value)){
			throw new RuleException("内容缺失");
		}
		if(!StringUtils.isEmpty(value)){
			try{
				result = TimeUtil.toDate(value);
				if(result == null){
					throw new RuleException("转换日期失败["+value+"]"); 
				}
			}catch (Exception e) {
				throw new RuleException(e.getMessage(),e);
			}
		}
		return value;
	}
	
	private static String singleProcess(String value,InfoProperty p) throws Exception{
		if(p.getNeed()&&StringUtils.isEmpty(value)){
			throw new RuleException("内容缺失");
		}
		if(StringUtils.isEmpty(value)){//代码库字段数据，不必填为空时直接返回
			return value;
		}
		String result=null;
		if(value.equals("是")){
			result = "1";
		}
		if(value.equals("否")){
			result ="0";
		}
		if(StringUtils.isEmpty(result)){
			throw new RuleException("单选属性字段转换失败["+value+"]");
		}
		return result;
	}
	
	public Map<String,HashMap<String,String>> getDataMap() {
		return dataMap;
	}

	public String getClassId() {
		return classId;
	}
	
}
