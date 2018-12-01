package com.zfsoft.mobile.peEvaluation.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.xmlbeans.impl.store.CharUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
//import com.ibm.icu.text.SimpleDateFormat;
//import com.ibm.icu.util.Calendar;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.mobile.news.entity.News;
import com.zfsoft.mobile.peEvaluation.entity.ClassInfoEntity;
import com.zfsoft.mobile.peEvaluation.entity.DataAnalEntity;
import com.zfsoft.mobile.peEvaluation.entity.ExportEntity;
import com.zfsoft.mobile.peEvaluation.entity.InstituteInfoEntity;
import com.zfsoft.mobile.peEvaluation.entity.PEDataEntity;
import com.zfsoft.mobile.peEvaluation.entity.StudentInfoEntity;
import com.zfsoft.mobile.peEvaluation.entity.StudentScoreCountEntity;
import com.zfsoft.mobile.peEvaluation.query.ClassInfoQuery;
import com.zfsoft.mobile.peEvaluation.query.ClassScoreQuery;
import com.zfsoft.mobile.peEvaluation.query.PEDataQuery;
import com.zfsoft.mobile.peEvaluation.query.StudentInfoQuery;
import com.zfsoft.mobile.peEvaluation.service.IPEEvaluationService;
import com.zfsoft.mobile.peEvaluation.until.AntexionUntil;
import com.zfsoft.mobile.peEvaluation.until.FiftyRunUntil;
import com.zfsoft.mobile.peEvaluation.until.GeneralUtil;
import com.zfsoft.mobile.peEvaluation.until.JumpUntil;
import com.zfsoft.mobile.peEvaluation.until.PullUpUntil;
import com.zfsoft.mobile.peEvaluation.until.PulmonaryUntil;
import com.zfsoft.mobile.peEvaluation.until.RunUntil;
import com.zfsoft.mobile.peEvaluation.until.SitUpUntil;
import com.zfsoft.mobile.servlet.entity.ListEntity;
import com.zfsoft.mobile.servlet.entity.ResultEntity;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.CodeUtil;
import com.zfsoft.util.base.StringUtil;

/**
 * 体育测评action
 * 
 */
public class HtPEEvaluationAction extends HrmAction {
	private static Logger logger = Logger.getLogger(HtPEEvaluationAction.class);

	private IPEEvaluationService peEvaluationService;

	private StudentInfoQuery query = new StudentInfoQuery();

	private PEDataQuery peDataQuery = new PEDataQuery();

	private String schoolNumber = "";

	private String dataType = "";

	private PageList<StudentInfoEntity> stupageList;

	private PageList<PEDataEntity> datapageList;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private File file;

	private String filename;

	// 后台学生基本信息列表
	public String htStuList() {

		stupageList = peEvaluationService.selectHtStudentList(query);
		this.getValueStack().set("list", stupageList);
		this.getValueStack().set("studentInfoQuery", query);
		return "stulist";
	}

	// 根据学籍号删除学生信息
	public String delStu() {
		peEvaluationService.delStuBySchoolNumber(schoolNumber);

		this.setSuccessMessage("操作成功");
		this.getValueStack().set("data", this.getMessage());
		return "data";
	}

	// 根据学籍号查出学生信息，去修改页面
	public String edit() {

		StudentInfoEntity studentInfoEntity = peEvaluationService
				.getStudentInfo(query);
		this.getValueStack().set("studentInfoQuery", studentInfoEntity);
		// 查询学院列表
		List<InstituteInfoEntity> schools = peEvaluationService
				.selectInstituteInfoEntities();
		this.getValueStack().set("schools", schools);
		List<ClassInfoEntity> classInfoEntities = Collections.emptyList();
		if (studentInfoEntity.getInstituteId() != null) {
			classInfoEntities = peEvaluationService
					.selectClassInfoByInsId(studentInfoEntity.getInstituteId());
		}
		this.getValueStack().set("classes", classInfoEntities);
		return "edit";
	}

	// 保存学生信息
	public String saveStu() {

		if (StringUtils.isBlank(query.getSchoolNumber())) {
			this.setErrorMessage("数据有误,请重试");
			this.getValueStack().set("data", this.getMessage());
			return "data";
		}
		peEvaluationService.updateStuByPrimaryKey(query);
		this.setSuccessMessage("操作成功");
		this.getValueStack().set("data", this.getMessage());
		return "data";
	}

	// 后台录入测评信息
	public String htSubmitData() {

		// 先计算分数，再更新到数据库
		// 肺活量
		if (peDataQuery.getPulmonary() != null
				&& !"".equals(peDataQuery.getPulmonary())) {
			peDataQuery.setPulmonaryScore(""
					+ PulmonaryUntil.countPulmonaryScore(
							Integer.parseInt(peDataQuery.getPulmonary()),
							peDataQuery.getGender()));
		}
		// 50m
		if (peDataQuery.getFiftyRun() != null
				&& !"".equals(peDataQuery.getFiftyRun())) {
			peDataQuery.setFiftyRunScore(""
					+ FiftyRunUntil.countFiftyRunScore(
							Double.parseDouble(peDataQuery.getFiftyRun()),
							peDataQuery.getGender()));
		}
		// 立定跳远
		if (peDataQuery.getJump() != null && !"".equals(peDataQuery.getJump())) {
			peDataQuery.setJumpScore(""
					+ JumpUntil.countJumpScore(
							Integer.parseInt(peDataQuery.getJump()),
							peDataQuery.getGender()));
		}
		// 坐位体前屈
		if (peDataQuery.getAntexion() != null
				&& !"".equals(peDataQuery.getAntexion())) {
			peDataQuery.setAntexionScore(""
					+ AntexionUntil.countAntexionScore(
							Double.parseDouble(peDataQuery.getAntexion()),
							peDataQuery.getGender()));
		}
		// 仰卧起坐
		if (peDataQuery.getSitUp() != null
				&& !"".equals(peDataQuery.getSitUp())) {
			peDataQuery.setSitUpScore(""
					+ SitUpUntil.countSitUpScore(Integer.parseInt(peDataQuery
							.getSitUp())));
		}
		// 引体向上
		if (peDataQuery.getPullUp() != null
				&& !"".equals(peDataQuery.getPullUp())) {
			peDataQuery.setPullUpScore(""
					+ PullUpUntil.countPullUpScore(Integer.parseInt(peDataQuery
							.getPullUp())));
		}
		// 800m
		if (peDataQuery.getEightHundredRun() != null
				&& !"".equals(peDataQuery.getEightHundredRun())) {
			peDataQuery.setEightHundredRunScore(""
					+ RunUntil.countRunScore(Double.parseDouble(peDataQuery
							.getEightHundredRun()), peDataQuery.getGender()));
		}
		// 1000m
		if (peDataQuery.getOneThousandRun() != null
				&& !"".equals(peDataQuery.getOneThousandRun())) {
			peDataQuery
					.setOneThousandRunScore(""
							+ RunUntil.countRunScore(Double
									.parseDouble(peDataQuery
											.getOneThousandRun()), peDataQuery
									.getGender()));
		}

		peDataQuery.setYear("" + Calendar.getInstance().get(Calendar.YEAR));
		peDataQuery.setCreateTime(sdf.format(new Date()));
		// 计算综合评定
		String general = GeneralUtil.getGeneral(peDataQuery,
				peDataQuery.getGender());
		peDataQuery.setGeneralScore(general);
		if (peEvaluationService.checkHaveData(peDataQuery) > 0) {
			peEvaluationService.updateData(peDataQuery);
		} else {
			peEvaluationService.insertData(peDataQuery);
		}

		this.setSuccessMessage("操作成功");
		this.getValueStack().set("data", this.getMessage());
		return "data";
	}

	public String addStuData() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		String dataType = request.getParameter("dataType");
		peDataQuery.setSchoolNumber(id);
		peDataQuery.setDataType(dataType);
		PEDataEntity studentScore = peEvaluationService
				.queryStudentScore(peDataQuery);
		StudentInfoQuery studentInfoQuery = new StudentInfoQuery();
		studentInfoQuery.setSchoolNumber(id);
		StudentInfoEntity studentInfo = peEvaluationService
				.getStudentInfo(studentInfoQuery);

		peDataQuery.setGender(studentInfo.getGender());

		getValueStack().set("peDataQuery", peDataQuery);
		getValueStack().set("studentScore", studentScore);
		return "addStuData";
	}

	// 页面跳转
	// 去体育测评页面
	public String tycpdataList() {
		if (StringUtils.isBlank(peDataQuery.getDataType())) {
			peDataQuery.setDataType("0");
		}
		datapageList = peEvaluationService.selectHtPeDataEntities(peDataQuery);
		this.getValueStack().set("list", datapageList);
		this.getValueStack().set("peDataQuery", peDataQuery);

		return "tycpdata";
	}

	// 页面跳转
	// 去计算后分数页面
	public String jshfsList() {
		if (StringUtils.isBlank(peDataQuery.getDataType())) {
			peDataQuery.setDataType("0");
		}
		datapageList = peEvaluationService.selectHtPeDataEntities(peDataQuery);
		this.getValueStack().set("list", datapageList);
		this.getValueStack().set("peDataQuery", peDataQuery);

		return "tycpScore";
	}

	// 页面跳转
	// 去数据分析页面
	public String dataAnal() {

		List<DataAnalEntity> list;
		if ("1".equals(dataType)) {
			list = peEvaluationService.selectDataAnal("1");
		} else {
			list = peEvaluationService.selectDataAnal("0");
		}

		this.getValueStack().set("list", list);
		this.getValueStack().set("dataType", dataType);
		return "dataAnal";
	}

	// 导出国家模板数据分析Excel
	public void exportCouExcel() throws IOException {
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

		/* String path = getClass().getResource("/").getPath()+time+".xls"; */
		String path = filePath + "/" + time + ".xls";
		response.setHeader("Content-Disposition", "attachment; filename="
				+ time + ".xls");

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("国家体测成绩表");
		HSSFRow row = sheet.createRow((int) 0);
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("年级编号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("班级编号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("班级名称");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("学籍号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("民族代码");
		cell.setCellStyle(style);
		cell = row.createCell((short) 5);
		cell.setCellValue("姓名");
		cell.setCellStyle(style);
		cell = row.createCell((short) 6);
		cell.setCellValue("性别");
		cell.setCellStyle(style);
		cell = row.createCell((short) 7);
		cell.setCellValue("出生日期");
		cell.setCellStyle(style);
		cell = row.createCell((short) 8);
		cell.setCellValue("家庭住址");
		cell.setCellStyle(style);
		cell = row.createCell((short) 9);
		cell.setCellValue("身高");
		cell.setCellStyle(style);
		cell = row.createCell((short) 10);
		cell.setCellValue("体重");
		cell.setCellStyle(style);
		cell = row.createCell((short) 11);
		cell.setCellValue("肺活量");
		cell.setCellStyle(style);
		cell = row.createCell((short) 12);
		cell.setCellValue("50米跑");
		cell.setCellStyle(style);
		cell = row.createCell((short) 13);
		cell.setCellValue("立定跳远");
		cell.setCellStyle(style);
		cell = row.createCell((short) 14);
		cell.setCellValue("坐位体前屈");
		cell.setCellStyle(style);
		cell = row.createCell((short) 15);
		cell.setCellValue("800米跑");
		cell.setCellStyle(style);
		cell = row.createCell((short) 16);
		cell.setCellValue("1000米跑");
		cell.setCellStyle(style);
		cell = row.createCell((short) 17);
		cell.setCellValue("一分钟仰卧起坐");
		cell.setCellStyle(style);
		cell = row.createCell((short) 18);
		cell.setCellValue("引体向上");
		cell.setCellStyle(style);

		// 查询数据
		List<ExportEntity> list = peEvaluationService.selectExportList("1");

		// 导出数据
		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow((int) i + 1);
			ExportEntity exportEntity = (ExportEntity) list.get(i);
			// 第四步，创建单元格，并设置值
			row.createCell((short) 0).setCellValue(exportEntity.getGradenum());
			row.createCell((short) 1).setCellValue(exportEntity.getClassId());
			row.createCell((short) 2)
					.setCellValue(exportEntity.getSclassName());
			row.createCell((short) 3).setCellValue(
					exportEntity.getSchoolNumber());
			row.createCell((short) 4).setCellValue(exportEntity.getNation());
			row.createCell((short) 5).setCellValue(exportEntity.getName());
			cell = row.createCell(6);
			if ("1".equals(exportEntity.getGender())) {
				cell.setCellValue("男");
			} else {
				cell.setCellValue("女");
			}
			row.createCell((short) 7).setCellValue(exportEntity.getBirthday());
			row.createCell((short) 8).setCellValue(exportEntity.getHomeAddr());
			row.createCell((short) 9).setCellValue(exportEntity.getHeight());
			row.createCell((short) 10).setCellValue(exportEntity.getWeight());
			row.createCell((short) 11)
					.setCellValue(exportEntity.getPulmonary());
			row.createCell((short) 12).setCellValue(exportEntity.getFiftyRun());
			row.createCell((short) 13).setCellValue(exportEntity.getJump());
			row.createCell((short) 14).setCellValue(exportEntity.getAntexion());
			row.createCell((short) 15).setCellValue(
					exportEntity.getEightHundredRun());
			row.createCell((short) 16).setCellValue(
					exportEntity.getOneThousandRun());
			row.createCell((short) 17).setCellValue(exportEntity.getSitUp());
			row.createCell((short) 18).setCellValue(exportEntity.getPullUp());
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

	// 导出省级模板数据分析Excel
	public void exportProExcel() throws IOException {
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

		/* String path = getClass().getResource("/").getPath()+time+".xls"; */
		String path = filePath + "/" + time + ".xls";
		response.setHeader("Content-Disposition", "attachment; filename="
				+ time + ".xls");
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("省级体测成绩表");
		HSSFRow row = sheet.createRow((int) 0);
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("大学名称");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("学号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("姓名");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("身份证号码");
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("毕业中学名称");
		cell.setCellStyle(style);
		cell = row.createCell((short) 5);
		cell.setCellValue("身高");
		cell.setCellStyle(style);
		cell = row.createCell((short) 6);
		cell.setCellValue("体重");
		cell.setCellStyle(style);
		cell = row.createCell((short) 7);
		cell.setCellValue("肺活量");
		cell.setCellStyle(style);
		cell = row.createCell((short) 8);
		cell.setCellValue("50米跑");
		cell.setCellStyle(style);
		cell = row.createCell((short) 9);
		cell.setCellValue("立定跳远");
		cell.setCellStyle(style);
		cell = row.createCell((short) 10);
		cell.setCellValue("坐位体前屈");
		cell.setCellStyle(style);
		cell = row.createCell((short) 11);
		cell.setCellValue("800米跑");
		cell.setCellStyle(style);
		cell = row.createCell((short) 12);
		cell.setCellValue("1000米跑");
		cell.setCellStyle(style);
		cell = row.createCell((short) 13);
		cell.setCellValue("一分钟仰卧起坐");
		cell.setCellStyle(style);
		cell = row.createCell((short) 14);
		cell.setCellValue("引体向上");
		cell.setCellStyle(style);

		// 查询数据
		List<ExportEntity> list = peEvaluationService.selectExportList("1");

		// 导出数据
		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow((int) i + 1);
			ExportEntity exportEntity = (ExportEntity) list.get(i);
			// 第四步，创建单元格，并设置值
			row.createCell((short) 0).setCellValue("金华职业技术学院");
			row.createCell((short) 1).setCellValue(
					exportEntity.getSchoolNumber());
			row.createCell((short) 2).setCellValue(exportEntity.getName());
			row.createCell((short) 3).setCellValue(exportEntity.getIdNumber());
			row.createCell((short) 4).setCellValue(
					exportEntity.getHighSchoolName());
			row.createCell((short) 5).setCellValue(exportEntity.getHeight());
			row.createCell((short) 6).setCellValue(exportEntity.getWeight());
			row.createCell((short) 7).setCellValue(exportEntity.getPulmonary());
			row.createCell((short) 8).setCellValue(exportEntity.getFiftyRun());
			row.createCell((short) 9).setCellValue(exportEntity.getJump());
			row.createCell((short) 10).setCellValue(exportEntity.getAntexion());
			row.createCell((short) 11).setCellValue(
					exportEntity.getEightHundredRun());
			row.createCell((short) 12).setCellValue(
					exportEntity.getOneThousandRun());
			row.createCell((short) 13).setCellValue(exportEntity.getSitUp());
			row.createCell((short) 14).setCellValue(exportEntity.getPullUp());
		}

		OutputStream outputStream = new FileOutputStream(path);
		wb.write(outputStream);
		// outputStream.write(FileUtils.readFileToByteArray(new File(path)));
		outputStream.flush();
		outputStream.close();

		ServletOutputStream out = response.getOutputStream();
		// 输出
		out.write(FileUtils.readFileToByteArray(new File(path)));
		out.flush();
		out.close();
	}

	public String impExcel() {

		if (StringUtil.isEmpty(filename)) {
			this.setErrorMessage("请先导入文件！");
			getValueStack().set(DATA, getMessage());
			return DATA;
		}
		if (!filename.endsWith("xlsx") && !filename.endsWith("xls")) {
			this.setErrorMessage("您导入的不是excel文件，请导入excel文件！");
			getValueStack().set(DATA, getMessage());
			return DATA;
		}

		System.out.println(file.getName());
		String year = ""+Calendar.getInstance().get(Calendar.YEAR);
		SimpleDateFormat sdf = new SimpleDateFormat();
		try {
			Workbook workbook = getWorkBook(file, filename);
			if (workbook != null) {
				Sheet sheet = workbook.getSheetAt(0);

				for (Row r : sheet) {
					if (r.getRowNum() < 1) {
						continue;
					}
					/*for (int a = 0; a < 12; a++) {
						//System.out.println(a + "=" + getCellValue(r.getCell(a)));
						
					}*/
					//插入数据
					PEDataQuery peDataEntityQry  = new PEDataQuery();
					peDataEntityQry.setYear(year);
					peDataEntityQry.setCreateTime(sdf.format(new Date()));
					peDataEntityQry.setSchoolNumber(getCellValue(r.getCell(0)));
					peDataEntityQry.setName(getCellValue(r.getCell(1)));
					peDataEntityQry.setGender(getCellValue(r.getCell(2)));
					peDataEntityQry.setDataType(getCellValue(r.getCell(3)));
					peDataEntityQry.setPulmonary(getCellValue(r.getCell(4)));
					peDataEntityQry.setFiftyRun(getCellValue(r.getCell(5)));
					peDataEntityQry.setJump(getCellValue(r.getCell(6)));
					peDataEntityQry.setAntexion(getCellValue(r.getCell(7)));
					peDataEntityQry.setSitUp(getCellValue(r.getCell(8)));
					peDataEntityQry.setPullUp(getCellValue(r.getCell(9)));
					peDataEntityQry.setEightHundredRun(getCellValue(r.getCell(10)));
					peDataEntityQry.setOneThousandRun(getCellValue(r.getCell(11)));
					
					//先计算分数，再更新到数据库
					//肺活量
					if(peDataEntityQry.getPulmonary()!=null&&!"".equals(peDataEntityQry.getPulmonary())){
						peDataEntityQry.setPulmonaryScore(""+PulmonaryUntil.countPulmonaryScore(Integer.parseInt(peDataEntityQry.getPulmonary()), peDataEntityQry.getGender()));
					}
					//50m
					if(peDataEntityQry.getFiftyRun()!=null&&!"".equals(peDataEntityQry.getFiftyRun())){
						peDataEntityQry.setFiftyRunScore(""+FiftyRunUntil.countFiftyRunScore(Double.parseDouble(peDataEntityQry.getFiftyRun()),peDataEntityQry.getGender()));
					} 
					//立定跳远
					if(peDataEntityQry.getJump()!=null&&!"".equals(peDataEntityQry.getJump())){
						peDataEntityQry.setJumpScore(""+JumpUntil.countJumpScore(Integer.parseInt(peDataEntityQry.getJump()), peDataEntityQry.getGender()));
					}
					//坐位体前屈
					if(peDataEntityQry.getAntexion()!=null&&!"".equals(peDataEntityQry.getAntexion())){
						peDataEntityQry.setAntexionScore(""+AntexionUntil.countAntexionScore(Double.parseDouble(peDataEntityQry.getAntexion()), peDataEntityQry.getGender()));
					}
					//仰卧起坐
					if(peDataEntityQry.getSitUp()!=null&&!"".equals(peDataEntityQry.getSitUp())){
						peDataEntityQry.setSitUpScore(""+SitUpUntil.countSitUpScore(Integer.parseInt(peDataEntityQry.getSitUp())));
					}
					//引体向上
					if(peDataEntityQry.getPullUp()!=null&&!"".equals(peDataEntityQry.getPullUp())){
						peDataEntityQry.setPullUpScore(""+PullUpUntil.countPullUpScore(Integer.parseInt(peDataEntityQry.getPullUp())));
					}
					//800m
					if(peDataEntityQry.getEightHundredRun()!=null&&!"".equals(peDataEntityQry.getEightHundredRun())){
						peDataEntityQry.setEightHundredRunScore(""+RunUntil.countRunScore(Double.parseDouble(peDataEntityQry.getEightHundredRun()),peDataEntityQry.getGender()));
					}
					//1000m
					if(peDataEntityQry.getOneThousandRun()!=null&&!"".equals(peDataEntityQry.getOneThousandRun())){
						peDataEntityQry.setOneThousandRunScore(""+RunUntil.countRunScore(Double.parseDouble(peDataEntityQry.getOneThousandRun()),peDataEntityQry.getGender()));
					}
					
					//计算综合评定
					String general = GeneralUtil.getGeneral(peDataEntityQry, peDataEntityQry.getGender());
					peDataEntityQry.setGeneralScore(general);
					
					peEvaluationService.insertData(peDataEntityQry);
					System.out.println(peDataEntityQry.getName() + "导入成功");

				}
			}

		} catch (Exception e) {
			logger.error("导入失败");
		}

		this.setSuccessMessage("导入成功！");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	/*
	 * ==========================================================================
	 * =================
	 */

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

	/**
	 * 对Excel的各个单元格的格式进行判断并转换
	 */
	private String getCellValue(Cell cell) {
		String cellValue = "";
		DecimalFormat df = new DecimalFormat("#");
		if (cell == null || cell.equals("")
				|| cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
			return cellValue;
		}
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			cellValue = cell.getRichStringCellValue().getString().trim();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			cellValue = df.format(cell.getNumericCellValue()).toString();
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			cellValue = String.valueOf(cell.getBooleanCellValue()).trim();
			break;
		default:
			cellValue = "";
		}
		return cellValue;
	}

	/*
	 * ==========================================================================
	 * =================
	 */

	public IPEEvaluationService getPeEvaluationService() {
		return peEvaluationService;
	}

	public void setPeEvaluationService(IPEEvaluationService peEvaluationService) {
		this.peEvaluationService = peEvaluationService;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		HtPEEvaluationAction.logger = logger;
	}

	public StudentInfoQuery getQuery() {
		return query;
	}

	public void setQuery(StudentInfoQuery query) {
		this.query = query;
	}

	public String getSchoolNumber() {
		return schoolNumber;
	}

	public void setSchoolNumber(String schoolNumber) {
		this.schoolNumber = schoolNumber;
	}

	public PEDataQuery getPeDataQuery() {
		return peDataQuery;
	}

	public void setPeDataQuery(PEDataQuery peDataQuery) {
		this.peDataQuery = peDataQuery;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public PageList<StudentInfoEntity> getStupageList() {
		return stupageList;
	}

	public void setStupageList(PageList<StudentInfoEntity> stupageList) {
		this.stupageList = stupageList;
	}

	public PageList<PEDataEntity> getDatapageList() {
		return datapageList;
	}

	public void setDatapageList(PageList<PEDataEntity> datapageList) {
		this.datapageList = datapageList;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

}
