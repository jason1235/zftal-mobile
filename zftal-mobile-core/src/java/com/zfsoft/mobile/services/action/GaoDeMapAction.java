package com.zfsoft.mobile.services.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.mobile.services.entity.GaoDeMaoEntity;
import com.zfsoft.mobile.services.service.IGaoDeMapService;
import com.zfsoft.mobile.txl.query.XGDTxlQuery;
import com.zfsoft.util.base.StringUtil;

public class GaoDeMapAction  extends HrmAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1254785984533979896L;
	private File file;
	private String filename;
	private PageList<GaoDeMaoEntity> pageList;
	
	private GaoDeMaoEntity query = new GaoDeMaoEntity();

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	/**
	 * 返回上传页面
	 * @return
	 */
	public String upload() {
		setPageList(gaoDeMapService.getPageList(query));
		
		return "upload";
	}
	public GaoDeMaoEntity getQuery() {
		return query;
	}

	public void setQuery(GaoDeMaoEntity query) {
		this.query = query;
	}
	private IGaoDeMapService gaoDeMapService;
	
	public String excelDownload(){
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("application/vnd.ms-excel;");
			response.setHeader("Content-Disposition", new String("attachment;filename=baidumap.xls"));
			HttpServletRequest request = ServletActionContext.getRequest();
			String path = request.getSession().getServletContext().getRealPath("/")+"upload/"+"baidumap.xls";
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
        
        return null;  
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
	
	
	/**
	 * 处理Excel上传
	 * @return
	 */
	public String imp(){
		if(StringUtil.isEmpty(filename)){
			this.setErrorMessage("请先导入文件！");
			getValueStack().set(DATA, getMessage());
			return DATA;
			/*try {
				this.getResponse().getWriter().print("<script>alert('请先导入文件！')</script>");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;*/
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
		List<GaoDeMaoEntity> queryList = new ArrayList<GaoDeMaoEntity>();
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
	                int firstRowNum  = sheet.getFirstRowNum()+2;  
	                //获得当前sheet的结束行  
	                int lastRowNum = sheet.getLastRowNum();  
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
	                    		&& StringUtil.isEmpty(getCellValue(row.getCell(2))))
	                    continue;
	                    
	                    if(StringUtil.isEmpty(getCellValue(row.getCell(0))) || StringUtil.isEmpty(getCellValue(row.getCell(1)))
	                    		|| StringUtil.isEmpty(getCellValue(row.getCell(2)))){
	                    	this.setErrorMessage((rowNum+1) + "行第1，2，3列值不能为空，请检查！");
	                    	getValueStack().set(DATA, getMessage());
	                    	return DATA;
	                    }
	                    
	                    GaoDeMaoEntity gaoDeQuery = new GaoDeMaoEntity();
	                    if(getCellValue(row.getCell(0)).length() > 100){
	                    	this.setErrorMessage((rowNum+1) + "行第1列数值太长，请重新输入！");
	        				getValueStack().set(DATA, getMessage());
	        				return DATA;
	                    }
	                    gaoDeQuery.setName(getCellValue(row.getCell(0)));
	                    if(getCellValue(row.getCell(1)).length() > 1000){
	                    	this.setErrorMessage((rowNum+1) + "行第2列数值太长，请重新输入！");
	        				getValueStack().set(DATA, getMessage());
	        				return DATA;
	                    }
	                    gaoDeQuery.setLongitude(getCellValue(row.getCell(1)));
	                    if(getCellValue(row.getCell(2)).length() > 1000){
	                    	this.setErrorMessage((rowNum+1) + "行第3列数值太长，请重新输入！");
	        				getValueStack().set(DATA, getMessage());
	        				return DATA;
	                    }
	                    gaoDeQuery.setLatitude(getCellValue(row.getCell(2)));
						queryList.add(gaoDeQuery);
						//xgdTxlService.insert(txlQuery);
	                }
				}
				if(queryList != null && queryList.size() > 0){
					gaoDeMapService.deleteAll();
					for(GaoDeMaoEntity entity : queryList){
						gaoDeMapService.insert(entity);
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
			/*try {
				this.getResponse().getWriter().print("<script>alert('导入失败！')</script>");
				return null;
			} catch (IOException e1) {
				e1.printStackTrace();
			}*/
		} 
		this.setSuccessMessage("导入成功！");
		getValueStack().set(DATA, getMessage());
		return DATA;
		/*try {
			//this.getResponse().getWriter().print("<script>var callback = function(){location.reload();};openAlert('发布成功', 's','auto', callback);</script>");
			this.getResponse().getWriter().print("<script>alert('导入成功！点击确定重新刷新页面');parent.location.href='serviceManager/gaoDeMap_upload.html';</script>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;*/
		
	}
	
	/**
	 * 处理Excel上传
	 * @return
	 */
	/*public String imp1() {
		//System.out.println(file.getName());
		List<GaoDeMaoEntity> queryList = new ArrayList<GaoDeMaoEntity>();
		try {
			gaoDeMapService.deleteAll();
			HttpServletRequest request = ServletActionContext.getRequest();
			String path = request.getSession().getServletContext().getRealPath("/")+"upload/"+"baidumap.xls";
			WritableWorkbook writebook = Workbook.createWorkbook(new File(path));  
			//生成名为“第一页”的工作表，参数0表示这是第一页   
			WritableSheet writesheet = writebook.createSheet("第一页", 0);  
			Workbook workbook = Workbook.getWorkbook(file);
			Label label = null;
			label = new Label(0,0,"校区");  
			writesheet.addCell(label);
			label = new Label(1,0,"经度");  
			writesheet.addCell(label);
			label = new Label(2,0,"纬度");  
			writesheet.addCell(label);
			
			Sheet sheet = workbook.getSheet(0);
			int cols = sheet.getColumns();
			int rows = sheet.getRows();
			for (int i = 1; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					GaoDeMaoEntity entity = new GaoDeMaoEntity();
					String col1 = sheet.getCell(j++, i).getContents();
					if(StringUtil.isEmpty(col1)){
						this.getResponse().getWriter().print("<script>alert('记录中存在名称为空的行！')</script>");
					}
					entity.setName(col1);
					String col2 = sheet.getCell(j++, i).getContents();
					if(StringUtil.isEmpty(col2)){
						this.getResponse().getWriter().print("<script>alert('记录中"+col1+"存在经度为空的行！')</script>");
					}
					entity.setLongitude(col2);
					String col3 = sheet.getCell(j++, i).getContents();
					entity.setLatitude(col3);
					if(StringUtil.isEmpty(col3)){
						this.getResponse().getWriter().print("<script>alert('记录中"+col1+"存在纬度为空的行！')</script>");
					}
					int longitudeLen = col2.split(",").length;
					int latitudeLen  = col2.split(",").length;
					if(longitudeLen != latitudeLen){
						this.getResponse().getWriter().print("<script>alert('记录中"+col1+"的经度与纬度的数量不对应！')</script>");
					}
					queryList.add(entity);
					gaoDeMapService.insert(entity);
					
					int temp =  j - 3;
					label = new Label(temp++,i,col1);  
					writesheet.addCell(label);
					label = new Label(temp++,i,col2);  
					writesheet.addCell(label);
					label = new Label(temp++,i,col3);  
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

	public void setGaoDeMapService(IGaoDeMapService gaoDeMapService) {
		this.gaoDeMapService = gaoDeMapService;
	}

	public IGaoDeMapService getGaoDeMapService() {
		return gaoDeMapService;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilename() {
		return filename;
	}

	public void setPageList(PageList<GaoDeMaoEntity> pageList) {
		this.pageList = pageList;
	}

	public PageList<GaoDeMaoEntity> getPageList() {
		return pageList;
	}
}
