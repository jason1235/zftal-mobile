package com.zfsoft.hrm.contract.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.data.entity.InfoType;
import com.zfsoft.hrm.baseinfo.data.util.DataProcessInfoUtil;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.config.ICodeConstants;
import com.zfsoft.hrm.contract.entity.CategoryConfig;
import com.zfsoft.hrm.contract.entity.Contract;
import com.zfsoft.hrm.contract.entity.Fields;
import com.zfsoft.hrm.contract.entity.ImportContractValidator;
import com.zfsoft.hrm.contract.entity.StatusEnum;
import com.zfsoft.hrm.contract.service.ICategoryConfigService;
import com.zfsoft.hrm.contract.service.IContractService;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.core.util.DownloadFilenameUtil;
import com.zfsoft.orcus.lang.TimeUtil;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.date.DateTimeUtil;

/**
 * 
 * @author
 * 2014-2-28
 */
public class ContractAction extends HrmAction implements ModelDriven<Contract> {

	private static final long serialVersionUID = 1L;
	private Contract model = new Contract();
	private List<Contract> list = new ArrayList<Contract>();
	private List<String> ids = new ArrayList<String>();
	private PageList<Contract> pageList;
	private boolean bol;
	private String path;
	private String category ;
	
	private IContractService contractService;
	private ICategoryConfigService categoryConfigService;
	private File file;
	private String fileContentType;
	
	public String staffList(){
		pageList = contractService.getStaffList(model);
		this.setInActionContext("paginator", pageList.getPaginator());
		getValueStack().set("code", loadCodeInPage());
		getValueStack().set("typeList", categoryConfigService.getPagedList(new CategoryConfig()));
		return "listStaff";
	}
	
	public String view() {
		return "view";
	}
	
	private void list(){
		pageList = contractService.getList(model);
		this.setInActionContext("paginator", pageList.getPaginator());
		getValueStack().set("code", loadCodeInPage());
		getValueStack().set("typeList", categoryConfigService.getPagedList(new CategoryConfig()));
		getValueStack().set("statusList", StatusEnum.values());
	}
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String importContract(){
		return "import";
	}
	
	/**
	 * 合同导入模版下载
	 */
	public String templateDown() throws Exception{
		List<Fields> contractProperty = contractService.getColumns();		
		getResponse().reset();
		getResponse().setCharacterEncoding("utf-8");
		getResponse().setContentType("application/vnd.ms-excel");
		String useragent = getRequest().getHeader("user-agent");
		String disposition = DownloadFilenameUtil.fileDisposition(useragent, "合同导入模版"+".xls");
		getResponse().setHeader("Content-Disposition", disposition);
		WritableWorkbook wwb = Workbook.createWorkbook( getResponse().getOutputStream() );
		WritableSheet sheet1 = wwb.createSheet("合同导入模版", 0);
		
		int i=0;
		for(Fields field:contractProperty){
			if(i == 0){
				sheet1.addCell(generateTheadLabel(i,0,"序号",field.getColumn_name()));
			}
			else if(i == 1){
				sheet1.addCell(generateTheadLabel(i,0,"职工号（必填）",field.getColumn_name()));
			}else{
				sheet1.addCell(generateTheadLabel(i,0,field.getComments(),field.getColumn_name()));
			}	
			i++;
		}
		wwb.write();
		wwb.close();
		return null;
	}

	/**
	 * 合同导入
	 * @param: 
	 * @return:
	 */
	public String inport(){
		try{
			DataProcessInfoUtil.setInfo(" 导入开始，请耐心等待", null);
			checkFile();
			ImportContractValidator icv = new ImportContractValidator();
			icv.checkContent(file);
			Map<String,HashMap<String,String>> dataMap = icv.getDataMap();
			DataProcessInfoUtil.setInfo(" 数据转换完成，共"+dataMap.size()+"条", null);
			contractService.doContractImport(icv);
		}catch (Exception e) {
			DataProcessInfoUtil.setInfo(" "+e.getMessage(), InfoType.ERROR);
			DataProcessInfoUtil.setInfo(" 导入终止", null);
		}finally{
			DataProcessInfoUtil.setInfo("-1", null);
			try {
				Thread.sleep(2*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			DataProcessInfoUtil.clear();
		}
		return null;
	}
	
	public String info() throws Exception{
		return "info";
	}
	/**
	 * 检查文件
	 * @throws Exception
	 */
	private void checkFile() throws Exception{
		if(file == null){
			throw new RuleException("文件没有接收成功");
		}
		if(file.length()==0){
			throw new RuleException("数据文件内容为空");
		}
		if(!fileContentType.equals("application/vnd.ms-excel")){
			throw new RuleException("请确保数据文件格式为excel文件");
		}
	}
	/**
	 * 组装表头，标注
	 * @param column
	 * @param row
	 * @param cellContext
	 * @param cellComment
	 * @return
	 */
	private Label generateTheadLabel(int column,int row, String cellContext,String cellComment){
		WritableCellFeatures f = new WritableCellFeatures();
		f.setComment(cellComment);
		Label label = new Label(column,row,cellContext);
		label.setCellFeatures(f);
		return label;
	}
	
	public String changeList(){
		model.setCondition("h.zt in ('" + StatusEnum.SIGNATURE.getKey() + "') and h.SFZF = 0 and h.SYQJZRQ is null");
		list();
		return "listChange";
	}
	
	public String probationList(){
//		model.setCondition("h.zt in ('" + StatusEnum.SIGNATURE.getKey() + "') and h.SFZF = 0 and h.SYQJZRQ is not null");
		PageList<CategoryConfig> configs = categoryConfigService.getPagedList(new CategoryConfig());
		StringBuffer condition = new StringBuffer();
		condition.append("(");
		for(CategoryConfig config : configs){
			String type = config.getHtzldm();
			int num = categoryConfigService.getById(type).getXqtxts();
			Calendar calendar=Calendar.getInstance();
//			calendar.add(Calendar.DATE, num);
			Date date = calendar.getTime();
			condition.append("(h.syqjzrq >= to_date('"+DateTimeUtil.getFormatDate(date, "yyyy-MM-dd")+"','yyyy-MM-dd') and to_date('"+DateTimeUtil.getFormatDate(date, "yyyy-MM-dd")+"','yyyy-MM-dd') >= h.syqjzrq - " + num + "  and h.HTZLBM = '" + type + "')");
			condition.append(" or ");
		}
		condition.append("(1=0)) and h.zt in ('" + StatusEnum.SIGNATURE.getKey() + "') and h.SFZF = 0 and h.SYQJZRQ is not null");
		model.setCondition(condition.toString());

		list();
		return "listProbation";
	}
	
	public String historyList(){
		model.setCondition("h.zt in ('" + StatusEnum.TERMINATE.getKey() + "','" + StatusEnum.RELEASE.getKey() + "')");
		list();
		return "listHistory";
	}
	
	public String signatureList(){
		model.setCondition("h.zt in ('" + StatusEnum.SIGNATURE.getKey() + "') and h.SFZF = 0");
		list();
		return "listSignature";
	}
	
	public String willList(){
		if(StringUtils.isEmpty(model.getType())){
			PageList<CategoryConfig> configs = categoryConfigService.getPagedList(new CategoryConfig());
			StringBuffer condition = new StringBuffer();
			condition.append("(");
			for(CategoryConfig config : configs){
				String type = config.getHtzldm();
				int num = categoryConfigService.getById(type).getDqtxts();
				Calendar calendar=Calendar.getInstance();
//				calendar.add(Calendar.DATE, num);
				Date date = calendar.getTime();
				condition.append("(h.HTZZRQ >= to_date('"+DateTimeUtil.getFormatDate(date, "yyyy-MM-dd")+"','yyyy-MM-dd') and to_date('"+DateTimeUtil.getFormatDate(date, "yyyy-MM-dd")+"','yyyy-MM-dd') >= h.HTZZRQ - " + num + "  and h.HTZLBM = '" + type + "')");
				condition.append(" or ");
			}
			condition.append("(1=0)) and h.zt in ('" + StatusEnum.SIGNATURE.getKey() + "') and h.SFZF = 0");
			model.setCondition(condition.toString());
		}else{
			CategoryConfig categoryConfig = categoryConfigService.getById(model.getType());
			Calendar calendar=Calendar.getInstance();
//			calendar.add(Calendar.DATE, categoryConfig.getDqtxts());
			Date date = calendar.getTime();
			model.setCondition("h.zt in ('" + StatusEnum.SIGNATURE.getKey() + "') and h.HTZZRQ >= to_date('"+DateTimeUtil.getFormatDate(date, "yyyy-MM-dd")+"','yyyy-MM-dd') and to_date('"+DateTimeUtil.getFormatDate(date, "yyyy-MM-dd")+"','yyyy-MM-dd') >= h.HTZZRQ - " + categoryConfig.getDqtxts() + "  and h.SFZF = 0");
		}
		list();
		return "listWill";
	}
	
	public String sequelList(){
		if(StringUtils.isEmpty(model.getType())){
			PageList<CategoryConfig> configs = categoryConfigService.getPagedList(new CategoryConfig());
			StringBuffer condition = new StringBuffer();
			condition.append("(");
			for(CategoryConfig config : configs){
				String type = config.getHtzldm();
				int num = categoryConfigService.getById(type).getXqtxts();
				Calendar calendar=Calendar.getInstance();
//				calendar.add(Calendar.DATE, num);
				Date date = calendar.getTime();
				condition.append("(h.HTZZRQ >= to_date('"+DateTimeUtil.getFormatDate(date, "yyyy-MM-dd")+"','yyyy-MM-dd') and to_date('"+DateTimeUtil.getFormatDate(date, "yyyy-MM-dd")+"','yyyy-MM-dd') >= h.HTZZRQ - " + num + "  and h.HTZLBM = '" + type + "')");
				condition.append(" or ");
			}
			condition.append("(1=0)) and h.zt in ('" + StatusEnum.SIGNATURE.getKey() + "') and h.SFZF = 0");
			model.setCondition(condition.toString());
		}else{
			CategoryConfig categoryConfig = categoryConfigService.getById(model.getType());
			Calendar calendar=Calendar.getInstance();
//			calendar.add(Calendar.DATE, categoryConfig.getXqtxts());
			Date date = calendar.getTime();
			model.setCondition("h.zt in ('" + StatusEnum.SIGNATURE.getKey() + "') and h.HTZZRQ >= to_date('"+DateTimeUtil.getFormatDate(date, "yyyy-MM-dd")+"','yyyy-MM-dd') and to_date('"+DateTimeUtil.getFormatDate(date, "yyyy-MM-dd")+"','yyyy-MM-dd') >= h.HTZZRQ - " + categoryConfig.getXqtxts() + "  and h.SFZF = 0");
		}
		list();
		return "listSequel";
	}
	
	public String overList(){
		Calendar calendar=Calendar.getInstance();
		Date date = calendar.getTime();
		model.setCondition("h.zt in ('" + StatusEnum.SIGNATURE.getKey() + "') and h.HTZZRQ < to_date('"+DateTimeUtil.getFormatDate(date, "yyyy-MM-dd")+"','yyyy-MM-dd') and h.YQJSSJ is null and h.SFZF = 0");
		list();
		return "listOver";
	}
	
	public String allList(){
		list();
		return "listAll";
	}
	
	public String sign(){
		model.setCreator(getUser().getYhm());
		model.setStatus(StatusEnum.SIGNATURE.getKey());
		model.setDisuse(false);
		contractService.doSign(model);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String modify(){
		model.setMender(getUser().getYhm());
		try {
			contractService.doModify(model);
		} catch (Exception e) {
			setErrorMessage(e.getMessage());
		}
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	public String sequel(){
		model.setMender(getUser().getYhm());
		model.setCreator(getUser().getYhm());
		try {
			contractService.doSequel(model);
		} catch (Exception e) {
			setErrorMessage(e.getMessage());
		}
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	public String operate(){
		model.setMender(getUser().getYhm());
		try {
			contractService.doOperate(model,ids);
		} catch (Exception e) {
			setErrorMessage(e.getMessage());
		}
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String toSign(){
//		model.setStatus(StatusEnum.TERMINATE.getKey() + "','" + StatusEnum.RELEASE.getKey());
		if(bol){
			model.setStatus(StatusEnum.SIGNATURE.getKey());
		}
		model = contractService.getStaff(model);
		String start = TimeUtil.year()+"";
		String ghStr = contractService.findMaxBh("^"+start+"[0-9]{4}$");
		if(StringUtil.isEmpty(ghStr)){
			model.setNumber(start+"0001");
		}else{
			int gh = Integer.valueOf(ghStr.substring(ghStr.length()-4));
			if (gh<9999) {
				gh++;
				String defGh=gh+"";
				for (; defGh.length()<4;) {
					defGh="0"+defGh;
				}
				model.setNumber(start+defGh);
			}
		}
		if(StringUtils.isEmpty(category)){
			historyList();
		}else{
			getValueStack().set("code", loadCodeInPage());
			getValueStack().set("typeList", categoryConfigService.getPagedList(new CategoryConfig()));
			getValueStack().set("statusList", StatusEnum.values());
		}
		return "signature";
	}
	
	public String toOver(){
		model.setStatus(StatusEnum.TERMINATE.getKey());
		return "over";
	}
	
	public String toDelay(){
		return "delay";
	}
	
	public String toRavel(){
		model.setStatus(StatusEnum.RELEASE.getKey());
		return "ravel";
	}
	
	public String toRegular() {
		try {
			contractService.doRegular(ids);
		}catch (Exception e) {
			setErrorMessage(e.getMessage());
		}
		getValueStack().set("forward", getMessage());
		return "forward";
	}
	
	public String regular(){
		return "regular";
	}
	
	@Override
	public Contract getModel() {
		return model;
	}

	private Map<String,String> loadCodeInPage(){
		Map<String,String> map = new HashMap<String,String>();
		map.put("department", ICodeConstants.DM_DEF_ORG);
		return map;
	}

	/**
	 * @return the list
	 */
	public List<Contract> getList() {
		return list;
	}



	/**
	 * @param list the list to set
	 */
	public void setList(List<Contract> list) {
		this.list = list;
	}



	/**
	 * @return the pageList
	 */
	public PageList<Contract> getPageList() {
		return pageList;
	}



	/**
	 * @param pageList the pageList to set
	 */
	public void setPageList(PageList<Contract> pageList) {
		this.pageList = pageList;
	}



	/**
	 * @return the contractService
	 */
	public IContractService getContractService() {
		return contractService;
	}



	/**
	 * @param contractService the contractService to set
	 */
	public void setContractService(IContractService contractService) {
		this.contractService = contractService;
	}



	/**
	 * @param model the model to set
	 */
	public void setModel(Contract model) {
		this.model = model;
	}

	/**
	 * @return the categoryConfigService
	 */
	public ICategoryConfigService getCategoryConfigService() {
		return categoryConfigService;
	}

	/**
	 * @param categoryConfigService the categoryConfigService to set
	 */
	public void setCategoryConfigService(
			ICategoryConfigService categoryConfigService) {
		this.categoryConfigService = categoryConfigService;
	}

	/**
	 * @return the bol
	 */
	public boolean isBol() {
		return bol;
	}

	/**
	 * @param bol the bol to set
	 */
	public void setBol(boolean bol) {
		this.bol = bol;
	}

	/**
	 * @return the ids
	 */
	public List<String> getIds() {
		return ids;
	}

	/**
	 * @param ids the ids to set
	 */
	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

}
