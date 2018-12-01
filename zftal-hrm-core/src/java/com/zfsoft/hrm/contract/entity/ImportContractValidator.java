package com.zfsoft.hrm.contract.entity;

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

import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.hrm.baseinfo.data.entity.InfoType;
import com.zfsoft.hrm.baseinfo.data.util.DataProcessInfoUtil;
import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.contract.service.IContractService;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.orcus.lang.TimeUtil;

/** 
 * 合同导入数据验证器
 * @date 2014-3-3 下午04:46:35 
 *  
 */
public class ImportContractValidator {

	private Sheet sheet ;
	private Map<Integer,Fields> map = new HashMap<Integer,Fields>();
	private int maxValidateError = 200;
	private Map<String,HashMap<String,String>> dataMap = new LinkedHashMap<String,HashMap<String,String>>();
	
	private IContractService contractService;
	/**
	 * 检查内容
	 * @throws Exception
	 */
	public void checkContent(File file) throws Exception{
		try{
			Workbook wb = Workbook.getWorkbook(file);
			sheet = wb.getSheet(0);
		}catch (Exception e) {
			throw new RuleException("加载数据文件出错",e);
		}
		checkHead();
		validateData();
	}
	
	public Sheet getSheet() {
		return sheet;
	}

	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}

	public Map<Integer, Fields> getMap() {
		return map;
	}

	public void setMap(Map<Integer, Fields> map) {
		this.map = map;
	}

	public int getMaxValidateError() {
		return maxValidateError;
	}

	public void setMaxValidateError(int maxValidateError) {
		this.maxValidateError = maxValidateError;
	}

	public IContractService getContractService() {
		return contractService;
	}

	public void setContractService(IContractService contractService) {
		this.contractService = contractService;
	}

	public void setDataMap(Map<String, HashMap<String, String>> dataMap) {
		this.dataMap = dataMap;
	}

	protected void checkHead() throws Exception{
		DataProcessInfoUtil.setInfo("数据头校验开始...", null);
		int columns = sheet.getColumns();             //模版项数
		contractService = (IContractService)SpringHolder.getBean("contractNewService");
		List<Fields> contractProperty = contractService.getColumns();	
		if(contractProperty.size()!=columns){
			throw new RuleException("模版数据项个数不匹配，请比对模板");
		}

		Map<String,Fields> pMap = new HashMap<String,Fields>();
		for(Fields f : contractProperty){
			pMap.put(f.getColumn_name(), f);
		}
		for(int i = 1;i<columns;i++){
			Cell cell =sheet.getCell(i, 0);
			String column = getComment(cell);
			if(StringUtils.isEmpty(column)){
				throw new RuleException(cell.getContents()+"标注信息缺失");
			}
			Fields f = pMap.get(column);
			if(f==null){
				throw new RuleException(cell.getContents()+"标注信息错误，无法匹配，请比对导入模版");
			}
			map.put(i,f);
		}
		DataProcessInfoUtil.setInfo("数据头校验结束", null);
	}
	
	protected String getComment(Cell c){
		if(c.getCellFeatures()!=null){
			return c.getCellFeatures().getComment();
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
				
				Fields f = map.get(n);
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
					String value = validateCell(v,f);
					data.put(f.getColumn_name(), value);
					
					if(f.getColumn_name().equals("GH")){
						if(uniqueMap.get(value)!=null){
							throw new RuleException("职工号["+c.getContents()+"]数据文档中已重复存在");
						}
					}
					
				}catch (Exception e) {
					if(e instanceof RuleException){
						DataProcessInfoUtil.setInfo("序号:"+m+" 列名:["+f.getComments()+
								"("+f.getColumn_name()+")] 校验失败,原因:"+e.getMessage(), InfoType.ERROR);
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
	
	public static String validateCell(String value,Fields f) throws Exception{
		String type = f.getData_type();//.getTypeInfo().getName();
		if(Type.MONTH.equals(type)){
			return dateProcess(value,f);
		}else if(Type.DATE.equals(type)){
			return dateProcess(value,f);
		}else if(Type.YEAR.equals(type)){
			return dateProcess(value,f);
		}else if(Type.SIGLE_SEL.equals(type)){
			return singleProcess(value,f);
		}
		return value;
	}	
	
	private static String dateProcess(String value,Fields f) throws Exception{
		Date result = null;
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
	
	private static String singleProcess(String value,Fields f) throws Exception{
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
}
