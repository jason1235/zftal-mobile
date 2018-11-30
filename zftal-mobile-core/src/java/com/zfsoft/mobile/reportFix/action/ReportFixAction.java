package com.zfsoft.mobile.reportFix.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.common.system.BaseHolder;
import com.zfsoft.dao.entities.YhglModel;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.core.util.Byte_File_Object;
import com.zfsoft.mobile.ballot.entity.Ballot;
import com.zfsoft.mobile.group.query.PushGroupQuery;
import com.zfsoft.mobile.reportFix.entity.FixType;
import com.zfsoft.mobile.reportFix.entity.FixTypeQuery;
import com.zfsoft.mobile.reportFix.entity.ReportFixCountEntity;
import com.zfsoft.mobile.reportFix.entity.ReportFixEntity;
import com.zfsoft.mobile.reportFix.entity.ReportFixPicsEntity;
import com.zfsoft.mobile.reportFix.entity.ReportFixQuery;
import com.zfsoft.mobile.reportFix.service.IReportFixService;
import com.zfsoft.mobile.servlet.entity.ListEntity;
import com.zfsoft.mobile.servlet.entity.ResultEntity;
import com.zfsoft.mobile.vote.entity.VoteMainEntity;
import com.zfsoft.service.svcinterface.IYhglService;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.CodeUtil;
import com.zfsoft.util.base.StringUtil;

public class ReportFixAction extends HrmAction{
    private static Logger logger = Logger.getLogger(ReportFixAction.class);
	
	private IReportFixService reportFixService;
	private ReportFixQuery query = new ReportFixQuery();
	private ReportFixEntity model = new ReportFixEntity();
	private YhglModel yhglQuery=new YhglModel();
	private PushGroupQuery groupQuery=new PushGroupQuery();
	private FixTypeQuery fixTypeQuery = new FixTypeQuery();
	
	//获取报修列表数据
	public void list(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String username=null;
		String start = null;
		String size = null;
		String status = null;
		String haveEvaluate = null;
		String apptoken = null;
		
		Gson gson = new Gson();
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try{
			username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
			start = new String(request.getParameter("start").getBytes("ISO8859-1"), "UTF-8");
			size = new String(request.getParameter("size").getBytes("ISO8859-1"), "UTF-8");
			status = new String(request.getParameter("status")==null? "".getBytes("ISO8859-1"):request.getParameter("status").getBytes("ISO8859-1"), "UTF-8");
			haveEvaluate = new String(request.getParameter("haveEvaluate")==null? "".getBytes("ISO8859-1"):request.getParameter("haveEvaluate").getBytes("ISO8859-1"), "UTF-8");
		 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			
			if(!ApptokenUtils.compare(apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			try {
				username     = CodeUtil.decode(username, apptoken);
				start        = CodeUtil.decode(start, apptoken);
				size         = CodeUtil.decode(size, apptoken);
				status       = CodeUtil.decode(status, apptoken);
				haveEvaluate = CodeUtil.decode(haveEvaluate, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
		
			query.setUserId(username);	
			query.setStatus(status);
			query.setHaveEvaluate(haveEvaluate);
			query.setToPage(Integer.valueOf(start));
			query.setPerPageSize(Integer.valueOf(size));
			List<ReportFixEntity> list = reportFixService.getList(query);
			
			Map<String,Object> resultMap = new HashMap<String,Object>();
			
			ListEntity<ReportFixEntity> resultList = new ListEntity<ReportFixEntity>();
			resultList.setItemList(list);
			if(list == null || list.size() < Integer.valueOf(size))	{
				resultList.setOvered(true);
			}else{
				resultList.setOvered(false);
			}
			
			resultMap.put("list", resultList);
			
			//获取统计信息
			List<ReportFixCountEntity> countList = reportFixService.getCountAmount();
			Map<String,Object> countMap = new HashMap<String,Object>();
			for (int i = 0; i < countList.size(); i++) {
				ReportFixCountEntity reportFixCountEntity = countList.get(i);
				if(reportFixCountEntity!=null){
					if("weibaoxiu".equals(reportFixCountEntity.getName())){
						countMap.put("weibaoxiu", reportFixCountEntity.getCountAmount());
					}else if("baoxiuzhong".equals(reportFixCountEntity.getName())){
						countMap.put("baoxiuzhong", reportFixCountEntity.getCountAmount());
					}else if("yibaoxiu".equals(reportFixCountEntity.getName())){
						countMap.put("yibaoxiu", reportFixCountEntity.getCountAmount());
					}else if("weipingjia".equals(reportFixCountEntity.getName())){
						countMap.put("weipingjia", reportFixCountEntity.getCountAmount());
					}
				}
			}
			
			resultMap.put("count", countMap);
			
	        ResultEntity<Map<String,Object>> result = new ResultEntity<Map<String,Object>>(1, "成功", resultMap);
			
	        out.write(gson.toJson(result));
	        out.flush();
	        out.close();
		}catch(Exception ex){
			ResultEntity<String> result = new ResultEntity<String>(0, "失败", "后台异常，获取数据失败");
			out.write(gson.toJson(result));
			out.flush();
			out.close();
			ex.printStackTrace();
		}
	}
	
	
	public void insertReportFix(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		PrintWriter out = null;  
		
		String username = null;
		String problem = null;
		String telephone = null;
		String status = "0"; //默认未报修
		Date createTime = new Date();//创建时间
		
		String apptoken = null;
		String fixId = null;
		
		Gson gson = new Gson();
		ReportFixEntity reportFixEntity = new ReportFixEntity();
		ReportFixPicsEntity pictureEntity = new ReportFixPicsEntity();
   	 	try {
	   	 	out = response.getWriter();
	   	 	
	   	    FileItemFactory factory = new DiskFileItemFactory();  
		    ServletFileUpload upload = new ServletFileUpload(factory);  
		    List<FileItem> items = new ArrayList<FileItem>();
		    items = upload.parseRequest(request);
		    
	        // 得到所有的文件  
	        Iterator<FileItem> it = items.iterator();  
	        while (it.hasNext()) {  
	            FileItem fItem = (FileItem) it.next();  
	            String paramName = "";  
	            Object fValue = null;  
	            if (fItem.isFormField()) { // 普通文本框的值  
	            	paramName = fItem.getFieldName();  
	                fValue = fItem.getString("UTF-8");  
	                if(paramName.equals("username")){
	                	username = fValue.toString();
	                }else if(paramName.equals("problem")){
	                	problem = fValue.toString();
	                }else if(paramName.equals("telephone")){
	                	telephone = fValue.toString();
	                }else if(paramName.equals("apptoken")){
	                	apptoken = fValue.toString();
	                	
	                	if(!StringUtil.isEmpty(username)
	                			&& !StringUtil.isEmpty(problem)
	                			&& !StringUtil.isEmpty(telephone)
	                			&& !StringUtil.isEmpty(apptoken)){
	                		username       	= CodeUtil.decode(username, apptoken);
	                		problem       	= CodeUtil.decode(problem, apptoken);
	                		telephone       = CodeUtil.decode(telephone, apptoken);
	                		
	                		reportFixEntity.setUserId(username);
	                		reportFixEntity.setTelephone(telephone);
	                		reportFixEntity.setStatus(status);
	                		reportFixEntity.setProblem(problem);
	                		reportFixEntity.setCreateTime(createTime);
	                		
	                		//插入数据
	                		reportFixService.insertReportFix(reportFixEntity);
	                		fixId = reportFixEntity.getId();
	                	}
	                }
	            } else { // 获取上传文件的值  
		            	logger.error("进入建议反馈图片上传方法");
		            	paramName = fItem.getFieldName();//userfile  
		                fValue = fItem.getInputStream(); 
		                String filename = fItem.getName();//路径  
		                if(!StringUtil.isEmpty(filename)) {  
		                    InputStream is = fItem.getInputStream();  
		                    
		                    byte[] fileContent = null;
		                    int size = is.available();
		                    if(is != null && size != 0){
		                    	fileContent = Byte_File_Object.getBytesFromFile(is);
		                    }
		                    pictureEntity = new ReportFixPicsEntity();
		                    pictureEntity.setFixId(fixId);
		                    pictureEntity.setContent(fileContent);
		                    pictureEntity.setName(filename);
		                    pictureEntity.setType("1");
		                    
		                    String filePath = request.getSession().getServletContext().getRealPath("/") + "reportFix"; 
		        			filePath = filePath.replace("\\", "/");
		        			File newFile = new File(filePath);
		        			if (!newFile.exists()) {
		        				newFile.mkdir();
		        			}
		        			
		        			File outFile = new File(filePath, filename);
		        			if (!outFile.exists()) {
		        				outFile.createNewFile();
		        			} else {
		        				outFile.delete();
		        				outFile.createNewFile();
		        			}
		        			
		        			FileOutputStream fileOutputStream = new FileOutputStream(outFile);
		        			if(problem != null){
		        				fileOutputStream.write(fileContent, 0, fileContent.length);
		        			}else{
		        				fileOutputStream.write(pictureEntity.getContent(), 0, pictureEntity.getContent().length);
		        			}
		        			fileOutputStream.close();
		        			is.close();
		        			String url = BaseHolder.getPropertiesValue("suploadPath");
		        			pictureEntity.setPath(url+"reportFix/"+filename);
		        			
		        			reportFixService.insertReportFixPicture(pictureEntity);
		                }  
		            }  
		        }
		        
		        ResultEntity<String> result = new ResultEntity<String>(1, "发布成功", "发布成功");
		        out.write(gson.toJson(result));
		        out.flush();
		        out.close();
   	 	} catch (Exception e) {
   	 		e.printStackTrace(); 
            logger.error("memo upload------: exception");  
            ResultEntity<String> result = new ResultEntity<String>(0, "操作失败", "操作失败");
            out.write(gson.toJson(result));
            out.flush();
            out.close();
         // 数据处理  
	 	}
	}
	
	//提交评价
	public void updateEvaluateById(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String username=null;
		String fixId = null;
		String evaluate = null;
		String score = null;
		String picPath = null;
		String apptoken = null;
		
		Gson gson = new Gson();
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try{
			username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
			fixId = new String(request.getParameter("fixId").getBytes("ISO8859-1"), "UTF-8");
			evaluate = new String(request.getParameter("evaluate").getBytes("ISO8859-1"), "UTF-8");
			score = new String(request.getParameter("score").getBytes("ISO8859-1"), "UTF-8");
			picPath = new String(request.getParameter("picPath").getBytes("ISO8859-1"), "UTF-8");
		 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			
			if(!ApptokenUtils.compare(apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			try {
				username     = CodeUtil.decode(username, apptoken);
				fixId        = CodeUtil.decode(fixId, apptoken);
				evaluate     = CodeUtil.decode(evaluate, apptoken);
				score        = CodeUtil.decode(score, apptoken);
				picPath        = CodeUtil.decode(picPath, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
		
			ReportFixEntity reportFixEntity = new ReportFixEntity();
			reportFixEntity.setId(fixId);
			reportFixEntity.setEvaluate(evaluate);
			reportFixEntity.setScore(score);
			
			reportFixService.updateEvaluateById(reportFixEntity);
			
	        ResultEntity<String> result = new ResultEntity<String>(1, "提交成功", "提交成功");
	        
	        ReportFixPicsEntity pictureEntity = new ReportFixPicsEntity();
            pictureEntity.setFixId(fixId);
            //pictureEntity.setContent(fileContent);
            pictureEntity.setName(picPath);
            pictureEntity.setType("2");
            pictureEntity.setPath(picPath);
			reportFixService.insertReportFixPicture(pictureEntity);
			
	        out.write(gson.toJson(result));
	        out.flush();
	        out.close();
		}catch(Exception ex){
			ResultEntity<String> result = new ResultEntity<String>(0, "失败", "后台异常，获取数据失败");
			out.write(gson.toJson(result));
			out.flush();
			out.close();
			ex.printStackTrace();
		}
	}
	
	
	//更新状态
	public void updateStatusById(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		
		Gson gson = new Gson();
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try{
			String id = new String(request.getParameter("id").getBytes("ISO8859-1"), "UTF-8");
			String status = new String(request.getParameter("status").getBytes("ISO8859-1"), "UTF-8");
		
			ReportFixEntity reportFixEntity = new ReportFixEntity();
			reportFixEntity.setId(id);
			reportFixEntity.setStatus(status);
			
			reportFixService.updateStatusById(reportFixEntity);
			
	        ResultEntity<String> result = new ResultEntity<String>(1, "提交成功", "提交成功");
			
	        out.write(gson.toJson(result));
	        out.flush();
	        out.close();
		}catch(Exception ex){
			ResultEntity<String> result = new ResultEntity<String>(0, "失败", "后台异常，获取数据失败");
			out.write(gson.toJson(result));
			out.flush();
			out.close();
			ex.printStackTrace();
		}
	}
	
	/**
	 * 该注释以上全部是给移动端的接口，以下全部是给移动后台的接口
	 */
	
	//后台报修列表
	public String htReportFixList(){
		PageList<ReportFixEntity> list = reportFixService.getYdhtList(query);
		getValueStack().set("list", list);
		return "htlist";
	}
	
	//跳转到修改页面
	public String toUpdate(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String fixId = request.getParameter("fixId");
		getValueStack().set("fixId", fixId);
		
		//根据fixId获取报修详情
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("fixId", fixId);
		ReportFixEntity reportFixEntity = reportFixService.getReportFixById(params);
		getValueStack().set("reportFixEntity", reportFixEntity);
		
		//获取相关图片
		query.setId(fixId);
		List<ReportFixPicsEntity> fixPicsEntities = reportFixService.getFixPictures(query);
		getValueStack().set("fixPicsEntities", fixPicsEntities);
		
		return "toUpdate";
	}
	
	//跳转到详情页面
	public String toDetail(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String fixId = request.getParameter("fixId");
		getValueStack().set("fixId", fixId);
		
		//根据fixId获取报修详情
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("fixId", fixId);
		ReportFixEntity reportFixEntity = reportFixService.getReportFixById(params);
		getValueStack().set("reportFixEntity", reportFixEntity);
		
		//获取相关图片
		/*
		query.setId(fixId);
		List<ReportFixPicsEntity> fixPicsEntities = reportFixService.getFixPictures(query);
		*/
		if( reportFixEntity!=null ){
			
			String picpaths = reportFixEntity.getPicPath();
			
			if( !StringUtils.isEmpty(picpaths) ){
				List<String> picpathList = Arrays.asList(picpaths.split(","));
				getValueStack().set("picpathList", picpathList);
			}
			
			String evapics = reportFixEntity.getEvaPic();
			
			if( !StringUtils.isEmpty(evapics) ){
				List<String> evapicList = Arrays.asList(evapics.split(","));
				getValueStack().set("evapicList", evapicList);
			}
		}
		
		return "toDetail";
	}
	
	
	//后台报修删除
	public void htReportFixRemove(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		
		Gson gson = new Gson();
		PrintWriter out = null;
   	 	try {
   	 	    out = response.getWriter();
   	 	   
	   	    String fixId = request.getParameter("fixId");
	   	    Map<String,Object> params = new HashMap<String,Object>();
	   	    params.put("fixId", fixId);
	   	    int rows = reportFixService.deleteReportFixByReportFixId(params);
        	
	        ResultEntity<String> result = new ResultEntity<String>(1, "success", String .valueOf(rows));
	        out.write(gson.toJson(result));
	        out.flush();
	        out.close();
   	 	} catch (Exception e) {
   	 		e.printStackTrace(); 
            logger.error("memo upload------: exception");  
            ResultEntity<String> result = new ResultEntity<String>(0, "操作失败", "error");
            out.write(gson.toJson(result));
            out.flush();
            out.close();
         // 数据处理  
	 	}
	}
	
	
	
	
	public String personList(){
		yhglQuery.getQueryModel().setShowCount(groupQuery.getPerPageSize());
		yhglQuery.getQueryModel().setCurrentPage(groupQuery.getToPage());
		IYhglService yhglService = SpringHolder.getBean("yhglService",IYhglService.class);
		PageList<YhglModel> pageList = new PageList<YhglModel>();
		pageList.addAll(yhglService.getPagedList(yhglQuery));
		pageList.setPaginator(yhglQuery.getQueryModel());
		getValueStack().set("list",pageList );
		HttpServletRequest request = ServletActionContext.getRequest();
		String fixId = request.getParameter("fixId");
		getValueStack().set("fixId", fixId);
		return "personList";
	}
	
	//设置维修人
	public String serRepair(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String fixId = request.getParameter("fixId");
		String repairId = request.getParameter("repairId");
		reportFixService.serRepair(fixId,repairId);
		
		this.setSuccessMessage("操作成功");
		this.getValueStack().set("data", this.getMessage());
		return "data";
	}
	
	
	//后台报修类型维护
	public String htFixTypeList(){
		List<FixType> list = reportFixService.selectHtFixTypeList(fixTypeQuery);
		this.getValueStack().set("list", list);
		return "typelist";
	}
	
	//类型删除
	public String deleteFixType(){
		reportFixService.deleteFixType(fixTypeQuery.getId());
		this.setSuccessMessage("操作成功");
		this.getValueStack().set("data", this.getMessage());
		return "data";
	}
	
	public String toUpdateType(){
		FixType fixType = reportFixService.getFixTypeById(fixTypeQuery.getId());
		this.getValueStack().set("fixType", fixType);
		return "typeedit";
	}
	
	public String toAdd(){
		return "typeedit";
	}
	
	public String saveOrUpdate(){
		if (StringUtils.isNotBlank(fixTypeQuery.getId())) {
			reportFixService.updateFixTypeById(fixTypeQuery);
		}else {
			reportFixService.insertReportFixType(fixTypeQuery);
		}
		
		this.setSuccessMessage("操作成功");
		this.getValueStack().set("data", this.getMessage());
		return "data";
	}
	
	
	
	
	public void htExpReportFixList() throws IOException {
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();

		long time = new Date().getTime();
		String filePath = request.getSession().getServletContext()
				.getRealPath("/")
				+ "export";
		filePath = filePath.replace("\\", "/");
		File newFile = new File(filePath);
		if (!newFile.exists()) {
			newFile.mkdir();
		}

		String path = filePath + "/" + time + ".xls";
		response.setHeader("Content-Disposition", "attachment; filename=" + time + ".xls");
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("保修信息");
		HSSFRow row = sheet.createRow((int) 0);
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		
		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("维修人");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("报修标题");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("报修类型");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("报修地址");
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("联系方式");
		cell.setCellStyle(style);
		cell = row.createCell((short) 5);
		cell.setCellValue("报修时间");
		cell.setCellStyle(style);
		cell = row.createCell((short) 6);
		cell.setCellValue("评价");
		cell.setCellStyle(style);
		
		List<ReportFixEntity> list = reportFixService.selectExpReportFixList();
		
		if( list!=null && list.size()>0 ){
			
			for (int i = 0; i < list.size(); i++) {
				row = sheet.createRow((int) i + 1);
				ReportFixEntity entity = list.get(i);
				row.createCell((short) 0).setCellValue(entity.getRepairName());
				row.createCell((short) 1).setCellValue(entity.getProblem());
				row.createCell((short) 2).setCellValue(entity.getStatus());
				row.createCell((short) 3).setCellValue(entity.getAddress());
				row.createCell((short) 4).setCellValue(entity.getTelephone());
				row.createCell((short) 5).setCellValue(entity.getCreateTime());
				row.createCell((short) 6).setCellValue(entity.getEvaluate());
			}
		}
		
		OutputStream outputStream = new FileOutputStream(path);
		wb.write(outputStream);
		outputStream.flush();
		outputStream.close();

		ServletOutputStream out = response.getOutputStream();
		// 输出
		out.write(FileUtils.readFileToByteArray(new File(path)));
		out.flush();
		out.close();
		
	}
	
	
	
	
	
	
	
	
	
	
	
	public static Logger getLogger() {
		return logger;
	}
	public static void setLogger(Logger logger) {
		ReportFixAction.logger = logger;
	}
	public IReportFixService getReportFixService() {
		return reportFixService;
	}
	public void setReportFixService(IReportFixService reportFixService) {
		this.reportFixService = reportFixService;
	}
	public ReportFixQuery getQuery() {
		return query;
	}
	public void setQuery(ReportFixQuery query) {
		this.query = query;
	}
	public ReportFixEntity getModel() {
		return model;
	}
	public void setModel(ReportFixEntity model) {
		this.model = model;
	}
	public YhglModel getYhglQuery() {
		return yhglQuery;
	}
	public void setYhglQuery(YhglModel yhglQuery) {
		this.yhglQuery = yhglQuery;
	}
	public PushGroupQuery getGroupQuery() {
		return groupQuery;
	}
	public void setGroupQuery(PushGroupQuery groupQuery) {
		this.groupQuery = groupQuery;
	}


	public FixTypeQuery getFixTypeQuery() {
		return fixTypeQuery;
	}


	public void setFixTypeQuery(FixTypeQuery fixTypeQuery) {
		this.fixTypeQuery = fixTypeQuery;
	}
	
}
