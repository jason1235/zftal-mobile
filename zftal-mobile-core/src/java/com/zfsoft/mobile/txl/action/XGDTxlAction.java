package com.zfsoft.mobile.txl.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.springframework.web.multipart.MultipartFile;



import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.mobile.txl.entity.XGDTxl;
import com.zfsoft.mobile.txl.entity.XgdCommentsEntity;
import com.zfsoft.mobile.txl.query.XGDTxlQuery;
import com.zfsoft.mobile.txl.service.IXGDTxlService;
import com.zfsoft.util.base.StringUtil;

public class XGDTxlAction extends HrmAction {
	private static Logger logger  = Logger.getLogger(XGDTxlAction.class); 
	
	private File file;
	//private MultipartFile file;
	
	private String filename;
	
	
	private PageList<XGDTxl> pageList;
	
	private List<Map<String, List<XGDTxl>>> list;
	
	private XGDTxlQuery query = new XGDTxlQuery();
	
	private XGDTxl model = new XGDTxl();
	
	private IXGDTxlService xgdTxlService;
	
	private List<XgdCommentsEntity> commentsList;
	
	
	
	
	/*public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}*/

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String excelDownload(){
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("application/vnd.ms-excel;");
			response.setHeader("Content-Disposition", new String("attachment;filename=contacts.xls"));
			HttpServletRequest request = ServletActionContext.getRequest();
			String path = request.getSession().getServletContext().getRealPath("/")+"upload/"+"contacts.xls";
			File f = new File(path);
			FileInputStream in = new FileInputStream(f);
			byte b[] = new byte[1024];
			int i = 0;
			ServletOutputStream out = response.getOutputStream();
			while((i=in.read(b))!=-1){
				out.write(b, 0, i);
			}
			out.flush();
			out.close();
			in.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 通讯录列表显示
	 * @return
	 */
	public String index() {
		if(StringUtil.isNotEmpty(query.getKs())){
			query.setBm(query.getKs());
		}
		pageList = xgdTxlService.getPageList(query);
		list = new ArrayList<Map<String,List<XGDTxl>>>();
		Map<String, List<XGDTxl>> map = new HashMap<String, List<XGDTxl>>();
		List<XGDTxl> txlList = new ArrayList<XGDTxl>();
		String bm = "";
		int index = 0;
		for (XGDTxl txl : pageList) {
			index++;
			String txlBm = txl.getBm();
			if (!bm.equals(txlBm) && index!=1) {
				bm = txlBm;
				if (!"".equals(bm)) {
					map.put(txlBm, txlList);
					list.add(map);
					map = new HashMap<String, List<XGDTxl>>();
					txlList = new ArrayList<XGDTxl>();
				}
			}
			txlList.add(txl);
			
		}
		map.put(bm, txlList);
		list.add(map);
		getValueStack().set("listSize", pageList.size());
		
		commentsList = xgdTxlService.getCommentList();
		if(commentsList == null || commentsList.size() == 0){
			commentsList = new ArrayList<XgdCommentsEntity>();
			commentsList.add(new XgdCommentsEntity("0", "电话"));
			commentsList.add(new XgdCommentsEntity("1", "部门"));
			commentsList.add(new XgdCommentsEntity("2", "科室"));
			commentsList.add(new XgdCommentsEntity("3", "主要业务"));
			commentsList.add(new XgdCommentsEntity("4", "办公地点1"));
			commentsList.add(new XgdCommentsEntity("5", "办公地点2"));
			commentsList.add(new XgdCommentsEntity("6", "部门别称"));
			commentsList.add(new XgdCommentsEntity("7", "传真"));
		}
		
		return "index";
	}
	
	public String detail(){
		model = xgdTxlService.getPageList(query).get(0);
		commentsList = xgdTxlService.getCommentList();
		if(commentsList == null || commentsList.size() == 0){
			commentsList = new ArrayList<XgdCommentsEntity>();
			commentsList.add(new XgdCommentsEntity("0", "电话"));
			commentsList.add(new XgdCommentsEntity("1", "部门"));
			commentsList.add(new XgdCommentsEntity("2", "科室"));
			commentsList.add(new XgdCommentsEntity("3", "主要业务"));
			commentsList.add(new XgdCommentsEntity("4", "办公地点1"));
			commentsList.add(new XgdCommentsEntity("5", "办公地点2"));
			commentsList.add(new XgdCommentsEntity("6", "部门别称"));
			commentsList.add(new XgdCommentsEntity("7", "传真"));
		}
		return "detail";
	}
	
	/**
	 * 上传通讯录Excel
	 * @return
	 */
	public String upload() {
		pageList = xgdTxlService.getPageListRe(query);
		commentsList = xgdTxlService.getCommentList();
		if(commentsList == null || commentsList.size() == 0){
			commentsList = new ArrayList<XgdCommentsEntity>();
			commentsList.add(new XgdCommentsEntity("0", "电话"));
			commentsList.add(new XgdCommentsEntity("1", "部门"));
			commentsList.add(new XgdCommentsEntity("2", "科室"));
			commentsList.add(new XgdCommentsEntity("3", "主要业务"));
			commentsList.add(new XgdCommentsEntity("4", "办公地点1"));
			commentsList.add(new XgdCommentsEntity("5", "办公地点2"));
			commentsList.add(new XgdCommentsEntity("6", "部门别称"));
			commentsList.add(new XgdCommentsEntity("7", "传真"));
		}
		return "upload";
	}
	
	private final static String xls = "xls";  
    private final static String xlsx = "xlsx";  
    
	public static Workbook getWorkBook(File file,String filename) {  
		try {
		//String fileName = file.getName();
		InputStream input = new FileInputStream(file);  //建立输入流  
		boolean isE2007 = false;    //判断是否是excel2007格式  
        if(filename.endsWith("xlsx"))  
            isE2007 = true;  
		Workbook workbook  = null;  
        //根据文件格式(2003或者2007)来初始化  
        if(isE2007)
        	workbook = new XSSFWorkbook(input);
		else  
			workbook = new HSSFWorkbook(input);  
        
        return workbook;  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        /*//获得文件名  
        String fileName = file.getOriginalFilename();  
        //创建Workbook工作薄对象，表示整个excel  
        Workbook workbook = null;  
        try {  
            //获取excel文件的io流  
            InputStream is = file.getInputStream();  
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象  
            if(fileName.endsWith(xls)){  
                //2003  
                workbook = new HSSFWorkbook(is);  
            }else if(fileName.endsWith(xlsx)){  
                //2007  
                workbook = new XSSFWorkbook(is);  
            }  
        } catch (IOException e) {  
            logger.info(e.getMessage());  
        }  */
        return null;  
    }  
	
	
	/**
	 * 处理Excel上传
	 * @return
	 */
	/*public String imp() {
		//System.out.println(file.getName());
		List<XGDTxlQuery> queryList = new ArrayList<XGDTxlQuery>();
		try {
			xgdTxlService.deleteAll();
			Workbook workbook = Workbook.getWorkbook(file);
			Sheet sheet = workbook.getSheet(0);
			int cols = sheet.getColumns();
			int rows = sheet.getRows();
			HttpServletRequest request = ServletActionContext.getRequest();
			String path = request.getSession().getServletContext().getRealPath("/")+"upload/"+"contacts.xls";
			WritableWorkbook writebook = Workbook.createWorkbook(new File(path));  
			//生成名为“第一页”的工作表，参数0表示这是第一页   
			WritableSheet writesheet = writebook.createSheet("第一页", 0);  
			Label label = null;
			label = new Label(0,0,"电话");  
			writesheet.addCell(label);
			label = new Label(1,0,"部门");  
			writesheet.addCell(label);
			label = new Label(2,0,"科室");  
			writesheet.addCell(label);
			label = new Label(3,0,"主要业务");  
			writesheet.addCell(label);
			label = new Label(4,0,"办公地点1");  
			writesheet.addCell(label);
			label = new Label(5,0,"办公地点2");  
			writesheet.addCell(label);
			label = new Label(6,0,"部门别称");  
			writesheet.addCell(label);
			label = new Label(7,0,"传真");  
			writesheet.addCell(label);
			int temp = 0;
			for (int i = 1; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					XGDTxlQuery txlQuery = new XGDTxlQuery();
					txlQuery.setDh(sheet.getCell(j++, i).getContents());
					txlQuery.setBm(sheet.getCell(j++, i).getContents());
					txlQuery.setKs(sheet.getCell(j++, i).getContents());
					txlQuery.setZyyw(sheet.getCell(j++, i).getContents());
					txlQuery.setBgdd1(sheet.getCell(j++, i).getContents());
					txlQuery.setBgdd2(sheet.getCell(j++, i).getContents());
					txlQuery.setBmbc(sheet.getCell(j++, i).getContents());
					txlQuery.setChuanzhen(sheet.getCell(j++, i).getContents());
					queryList.add(txlQuery);
					xgdTxlService.insert(txlQuery);
					temp =  j - 8;
					label = new Label(temp++,i,txlQuery.getDh());  
					writesheet.addCell(label);
					label = new Label(temp++,i,txlQuery.getBm());  
					writesheet.addCell(label);
					label = new Label(temp++,i,txlQuery.getKs());  
					writesheet.addCell(label);
					label = new Label(temp++,i,txlQuery.getZyyw());  
					writesheet.addCell(label);
					label = new Label(temp++,i,txlQuery.getBgdd1());  
					writesheet.addCell(label);
					label = new Label(temp++,i,txlQuery.getBgdd2());  
					writesheet.addCell(label);
					label = new Label(temp++,i,txlQuery.getBmbc());  
					writesheet.addCell(label);
					label = new Label(temp++,i,txlQuery.getBmbc());  
					writesheet.addCell(label);
				}
			}
			writebook.write();  
			writebook.close(); 
		} catch (Exception e) {
			e.printStackTrace();
			try {
				this.getResponse().getWriter().print("<script>alert('导入失败！')</script>");
				return null;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} 
		try {
			this.getResponse().getWriter().print("<script>alert('导入成功！')</script>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}*/
	
	
	public boolean isNumeric(String str){ 
		   Pattern pattern = Pattern.compile("[0-9]*"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
		       return false; 
		   } 
		   return true; 
		}
	
	/**
	 * 处理Excel上传
	 * @return
	 */
	public String imp() {
		if(StringUtil.isEmpty(filename)){
			this.setErrorMessage("请先导入文件！");
			getValueStack().set(DATA, getMessage());
			return DATA;
//				this.getResponse().getWriter().print("<script>window.parent.openerror('请先导入文件！');</script>");
				//this.getResponse().getWriter().print("<script>alert('请先导入文件！')</script>");
		}
		if(!filename.endsWith("xlsx") && !filename.endsWith("xls")){
			this.setErrorMessage("您导入的不是excel文件，请导入excel文件！");
			getValueStack().set(DATA, getMessage());
			return DATA;
			/*try {
				this.getResponse().getWriter().print("<script>alert('您导入的不是excel文件，请导入excel文件！')</script>");
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;*/
		}
		
		
		//System.out.println(file.getName());
		List<XGDTxlQuery> queryList = new ArrayList<XGDTxlQuery>();
		try {
//			xgdTxlService.deleteAll();
			Workbook workbook = getWorkBook(file,filename);
			if(workbook != null){  
				for(int sheetNum = 0;sheetNum < workbook.getNumberOfSheets();sheetNum++){  
					//获得当前sheet工作表  
	                Sheet sheet = workbook.getSheetAt(sheetNum);  
	                if(sheet == null){  
	                    continue;  
	                } 
	              //获得当前sheet的开始行  
	                int firstRowNum;
	                firstRowNum= sheet.getFirstRowNum()+2;  
	                //获得当前sheet的结束行  
	                int lastRowNum = sheet.getLastRowNum();  
	                
	                Row rowtemp = sheet.getRow(firstRowNum);
	                if(rowtemp != null){  
	                	//获得当前行的开始列  
	                    int firstCellNum = rowtemp.getFirstCellNum();  
	                    //获得当前行的列数  
	                    int lastCellNum = rowtemp.getPhysicalNumberOfCells(); 
	                    xgdTxlService.deleteAllComment();
	                    XgdCommentsEntity entity;
	                    for (int i = firstCellNum; i < lastCellNum; i++) {
	                    	entity  = new XgdCommentsEntity(String.valueOf(i),getCellValue(rowtemp.getCell(i)));
	                    	xgdTxlService.insertComment(entity);
						}
	                    
	                    
	                }else{
	                	
	                }
	                
	                
	              //循环除了第一行的所有行  
	                for(int rowNum = firstRowNum+1;rowNum <= lastRowNum;rowNum++){  
	                	 //获得当前行  
	                    Row row = sheet.getRow(rowNum);  
	                    if(row == null){  
	                        continue;  
	                    } 
	                    //获得当前行的开始列  
	                    int firstCellNum = row.getFirstCellNum();  
	                    //获得当前行的列数  
	                    int lastCellNum = row.getPhysicalNumberOfCells(); 
	                    
	                    if(StringUtil.isEmpty(getCellValue(row.getCell(0))) && StringUtil.isEmpty(getCellValue(row.getCell(1)))
	                    		&& StringUtil.isEmpty(getCellValue(row.getCell(2))) && StringUtil.isEmpty(getCellValue(row.getCell(3)))
	                    		&& StringUtil.isEmpty(getCellValue(row.getCell(4))) && StringUtil.isEmpty(getCellValue(row.getCell(5)))
	                    		&& StringUtil.isEmpty(getCellValue(row.getCell(6))) && StringUtil.isEmpty(getCellValue(row.getCell(7)))
                    		)	continue;
	                    if(StringUtil.isEmpty(getCellValue(row.getCell(0))) || StringUtil.isEmpty(getCellValue(row.getCell(1)))
	                    		|| StringUtil.isEmpty(getCellValue(row.getCell(2)))){
	                    	this.setErrorMessage((rowNum+1) + "行第1，2，3列值不能为空，请检查后重新上传文件！");
	                    	getValueStack().set(DATA, getMessage());
	                    	return DATA;
	                    }
	                    
	                    XGDTxlQuery txlQuery = new XGDTxlQuery();
	                    /*if(!StringUtils.isNumeric(getCellValue(row.getCell(0)))){
	                    	this.setErrorMessage((rowNum+1) + "行存在第1列不为数值的值，请仔细检查！");
	        				getValueStack().set(DATA, getMessage());
	        				return DATA;
	                    }*/
//	                    if(!isNumeric(getCellValue(row.getCell(0)))){
//	                    	this.setErrorMessage((rowNum+1) + "行第1列数值不是数值，请重新输入！");
//	        				getValueStack().set(DATA, getMessage());
//	        				return DATA;
//	                    }
	                    if(getCellValue(row.getCell(0)).length() > 20){
	                    	this.setErrorMessage((rowNum+1) + "行第1列数值太长，请重新输入！");
	        				getValueStack().set(DATA, getMessage());
	        				return DATA;
	                    }
						txlQuery.setDh(getCellValue(row.getCell(0)));
						if(getCellValue(row.getCell(1)).length() > 50){
	                    	this.setErrorMessage((rowNum+1) + "行第2列数值太长，请重新输入！");
	        				getValueStack().set(DATA, getMessage());
	        				return DATA;
	                    }
						txlQuery.setBm(getCellValue(row.getCell(1)));
						if(getCellValue(row.getCell(2)).length() > 100){
	                    	this.setErrorMessage((rowNum+1) + "行第3列数值太长，请重新输入！");
	        				getValueStack().set(DATA, getMessage());
	        				return DATA;
	                    }
						txlQuery.setKs(getCellValue(row.getCell(2)));
						if(getCellValue(row.getCell(3)).length() > 200){
	                    	this.setErrorMessage((rowNum+1) + "行第4列数值太长，请重新输入！");
	        				getValueStack().set(DATA, getMessage());
	        				return DATA;
	                    }
						txlQuery.setZyyw(getCellValue(row.getCell(3)));
						if(getCellValue(row.getCell(4)).length() > 100){
	                    	this.setErrorMessage((rowNum+1) + "行第5列数值太长，请重新输入！");
	        				getValueStack().set(DATA, getMessage());
	        				return DATA;
	                    }
						txlQuery.setBgdd1(getCellValue(row.getCell(4)));
						if(getCellValue(row.getCell(5)).length() > 100){//此列值需要做更新sql,原来是50
	                    	this.setErrorMessage((rowNum+1) + "行第6列数值太长，请重新输入！");
	        				getValueStack().set(DATA, getMessage());
	        				return DATA;
	                    }
						txlQuery.setBgdd2(getCellValue(row.getCell(5)));
						if(getCellValue(row.getCell(6)).length() > 100){
	                    	this.setErrorMessage((rowNum+1) + "行第7列数值太长，请重新输入！");
	        				getValueStack().set(DATA, getMessage());
	        				return DATA;
	                    }
						txlQuery.setBmbc(getCellValue(row.getCell(6)));
						if(getCellValue(row.getCell(7)).length() > 100){
	                    	this.setErrorMessage((rowNum+1) + "行第8列数值太长，请重新输入！");
	        				getValueStack().set(DATA, getMessage());
	        				return DATA;
	                    }
						txlQuery.setChuanzhen(getCellValue(row.getCell(7)));
						queryList.add(txlQuery);
						//xgdTxlService.insert(txlQuery);
	                }
				}
				if(queryList != null && queryList.size() > 0){
					xgdTxlService.deleteAll();
					for(XGDTxlQuery entity : queryList){
						xgdTxlService.insert(entity);
					}
				}
				
			}else{
				this.setErrorMessage("导入失败！");
				getValueStack().set(DATA, getMessage());
				return DATA;
				/*this.getResponse().getWriter().print("<script>alert('导入失败！')</script>");
				return null;*/
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			this.setErrorMessage("导入失败！");
			getValueStack().set(DATA, getMessage());
			return DATA;
/*			try {
				this.getResponse().getWriter().print("<script>alert('导入失败！')</script>");
				return null;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
*/		} 
		this.setSuccessMessage("导入成功！");
		getValueStack().set(DATA, getMessage());
		return DATA;
			//this.getResponse().getWriter().print("<script>var callback = function(){location.reload();};openAlert('发布成功', 's','auto', callback);</script>");
			//this.getResponse().getWriter().print("<script>alert('导入成功！点击确定重新刷新页面');parent.location.href='txl/txl_upload.html';</script>");
	}
	
	 public static String getCellValue(Cell cell){  
	        String cellValue = "";  
	        if(cell == null){  
	            return cellValue;  
	        }  
	        //把数字当成String来读，避免出现1读成1.0的情况  
	        if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){  
	            cell.setCellType(Cell.CELL_TYPE_STRING);  
	        }  
	        //判断数据的类型  
	        switch (cell.getCellType()){  
	            case Cell.CELL_TYPE_NUMERIC: //数字  
	                cellValue = String.valueOf(cell.getNumericCellValue());  
	                break;  
	            case Cell.CELL_TYPE_STRING: //字符串  
	                cellValue = String.valueOf(cell.getStringCellValue());  
	                break;  
	            case Cell.CELL_TYPE_BOOLEAN: //Boolean  
	                cellValue = String.valueOf(cell.getBooleanCellValue());  
	                break;  
	            case Cell.CELL_TYPE_FORMULA: //公式  
	                cellValue = String.valueOf(cell.getCellFormula());  
	                break;  
	            case Cell.CELL_TYPE_BLANK: //空值   
	                cellValue = "";  
	                break;  
	            case Cell.CELL_TYPE_ERROR: //故障  
	                cellValue = "非法字符";  
	                break;  
	            default:  
	                cellValue = "未知类型";  
	                break;  
	        }  
	        return cellValue;  
	    } 

	 
	 public String impExcel(){
		if(StringUtil.isEmpty(filename)){
			this.setErrorMessage("请先导入文件！");
			getValueStack().set(DATA, getMessage());
			return DATA;
		}
		if(!filename.endsWith("xlsx") && !filename.endsWith("xls")){
			this.setErrorMessage("您导入的不是excel文件，请导入excel文件！");
			getValueStack().set(DATA, getMessage());
			return DATA;
		}
		
		System.out.println(file.getName());
		
		this.setSuccessMessage("导入成功！");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	public IXGDTxlService getXgdTxlService() {
		return xgdTxlService;
	}

	public void setXgdTxlService(IXGDTxlService xgdTxlService) {
		this.xgdTxlService = xgdTxlService;
	}

	public PageList<XGDTxl> getPageList() {
		return pageList;
	}

	public void setPageList(PageList<XGDTxl> pageList) {
		this.pageList = pageList;
	}

	public XGDTxlQuery getQuery() {
		return query;
	}

	public void setQuery(XGDTxlQuery query) {
		this.query = query;
	}

	public List<Map<String, List<XGDTxl>>> getList() {
		return list;
	}

	public void setList(List<Map<String, List<XGDTxl>>> list) {
		this.list = list;
	}

	public void setModel(XGDTxl model) {
		this.model = model;
	}

	public XGDTxl getModel() {
		return model;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilename() {
		return filename;
	}

	public void setCommentsList(List<XgdCommentsEntity> commentsList) {
		this.commentsList = commentsList;
	}

	public List<XgdCommentsEntity> getCommentsList() {
		return commentsList;
	}
	
	
	
	
}
