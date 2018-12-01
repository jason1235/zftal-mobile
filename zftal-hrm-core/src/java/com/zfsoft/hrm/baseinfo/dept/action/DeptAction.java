package com.zfsoft.hrm.baseinfo.dept.action;

import java.io.File;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.common.service.BaseLog;
import com.zfsoft.hrm.baseinfo.code.entities.Item;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.baseinfo.data.entity.InfoType;
import com.zfsoft.hrm.baseinfo.data.util.DataProcessInfoUtil;
import com.zfsoft.hrm.baseinfo.dyna.business.bizinterface.IDynaBeanBusiness;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.org.entities.OrgInfo;
import com.zfsoft.hrm.baseinfo.org.query.OrgQuery;
import com.zfsoft.hrm.baseinfo.org.service.svcinterface.IOrgService;
import com.zfsoft.hrm.baseinfo.org.util.OrgUtil;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.config.Type;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.core.util.DownloadFilenameUtil;
import com.zfsoft.service.impl.LogEngineImpl;
import com.zfsoft.util.base.StringUtil;

public class DeptAction extends HrmAction {

    /**
     * 
     */
    private static final long serialVersionUID = 4799209114725252910L;

    private File file;
    private String fileContentType;
    private IDynaBeanBusiness dynaBeanBusiness;
    private IOrgService orgService;
    private BaseLog baseLog = LogEngineImpl.getInstance();
    
    /**
     * 导入
     * @return
     */
    public String upload() {
        return "upload";
    }
    
    /**
     * 模板下载
     * @return
     * @throws Exception
     */
    public String downloadTemplate() throws Exception{
        getResponse().reset();
        getResponse().setCharacterEncoding("utf-8");
        getResponse().setContentType("application/vnd.ms-excel");
        String useragent = getRequest().getHeader("user-agent");
        String disposition = DownloadFilenameUtil.fileDisposition(useragent, "一级部门.xls");
        getResponse().setHeader("Content-Disposition", disposition);
        WritableWorkbook wwb = Workbook.createWorkbook( getResponse().getOutputStream() );
        WritableSheet sheet1 = wwb.createSheet("一级部门", 0);
        
        int i = 0;
        sheet1.addCell(generateTheadLabel(i, 0, "序号", "num"));
        i++;
        sheet1.addCell(generateTheadLabel(i, 0, "部门代码(必填)", "bmdm"));
        i++;
        sheet1.addCell(generateTheadLabel(i, 0, "部门名称(必填)", "bmmc"));
        i++;
        sheet1.addCell(generateTheadLabel(i, 0, "部门类别(必填)", "bmlb"));
        i++;
        sheet1.addCell(generateTheadLabel(i, 0, "上级部门", "sjbm"));
        i++;
        sheet1.addCell(generateTheadLabel(i, 0, "主管工号", "zggh"));
        i++;
        sheet1.addCell(generateTheadLabel(i, 0, "负责人工号", "fzrgh"));
        i++;
        sheet1.addCell(generateTheadLabel(i, 0, "顺序码", "sxm"));
        i++;
        sheet1.addCell(generateTheadLabel(i, 0, "备注", "bz"));
        i++;
        sheet1.addCell(generateTheadLabel(i, 0, "单位全称", "dwqc"));
        i++;
        sheet1.addCell(generateTheadLabel(i, 0, "所属总支", "sszz"));
        i++;
        sheet1.addCell(generateTheadLabel(i, 0, "编制数", "bzs"));
        
        wwb.write();
        wwb.close();
        return null;
    }
    
    /**
     * 数据导入
     * @return
     * @throws Exception
     */
    public String dataImport() {
        
        int maxValidateError = 20;
        try{
            DataProcessInfoUtil.setInfo("导入开始，请耐心等待", null);
            checkFile();
            Workbook wb = Workbook.getWorkbook(file);
            Sheet sheet = wb.getSheet(0);
            DataProcessInfoUtil.setInfo("数据头校验开始...", null);
            int columns = sheet.getColumns();
            int bmdmCol = 0;
            int bmmcCol = 0;
            int bmlbCol = 0;
            int sjbmCol = 0;
            int zgghCol = 0;
            int fzrghCol = 0;
            int sxmCol = 0;
            int bzCol = 0;
            int dwqcCol = 0;
            int sszzCol = 0;
            int bzsCol = 0;
            
            for (int i = 1; i < columns; i++) {
                String field = getComment(sheet.getCell(i, 0));
                if (bmdmCol == 0 && "bmdm".equals(field)) {
                    DataProcessInfoUtil.setInfo("已定位部门代码所在列：[" + i +"]", null);
                    bmdmCol = i;
                } else if (bmmcCol == 0 && "bmmc".equals(field)) {
                    DataProcessInfoUtil.setInfo("已定位部门名称所在列：[" + i + "]", null);
                    bmmcCol = i;
                } else if(bmlbCol == 0 && "bmlb".equals(field)){
                    DataProcessInfoUtil.setInfo("已定位部门类别所在列：[" + i + "]", null);
                    bmlbCol = i;
                } else if(sjbmCol == 0 && "sjbm".equals(field)){
                    DataProcessInfoUtil.setInfo("已定位上级部门所在列：[" + i + "]", null);
                    sjbmCol = i;
                } else if(zgghCol == 0 && "zggh".equals(field)){
                    DataProcessInfoUtil.setInfo("已定位主管工号所在列：[" + i + "]", null);
                    zgghCol = i;
                } else if(fzrghCol == 0 && "fzrgh".equals(field)){
                    DataProcessInfoUtil.setInfo("已定位负责人工号所在列：[" + i + "]", null);
                    fzrghCol = i;
                } else if(sxmCol == 0 && "sxm".equals(field)){
                    DataProcessInfoUtil.setInfo("已定位顺序码所在列：[" + i + "]", null);
                    sxmCol = i;
                } else if(bzCol == 0 && "bz".equals(field)){
                    DataProcessInfoUtil.setInfo("已定位备注所在列：[" + i + "]", null);
                    bzCol = i;
                } else if(dwqcCol == 0 && "dwqc".equals(field)){
                    DataProcessInfoUtil.setInfo("已定位单位全称所在列：[" + i + "]", null);
                    dwqcCol = i;
                } else if(sszzCol == 0 && "sszz".equals(field)){
                    DataProcessInfoUtil.setInfo("已定位所属总支所在列：[" + i + "]", null);
                    sszzCol = i;
                } else if(bzsCol == 0 && "bzs".equals(field)){
                    DataProcessInfoUtil.setInfo("已定位编制数所在列：[" + i + "]", null);
                    bzsCol = i;
                }
            }
            if (bmdmCol == 0) {
                throw new RuleException("部门代码所在列未能成功定位!");
            }
            if (bmmcCol == 0) {
                throw new RuleException("部门名称所在列未能成功定位!");
            }
            if (bmlbCol == 0) {
                throw new RuleException("部门类别所在列未能成功定位!");
            }
            
            DataProcessInfoUtil.setInfo("数据头校验结束", null);
            DataProcessInfoUtil.setInfo("数据内容校验开始...", null);
            
            int rows = sheet.getRows();
            int cnt = 0;
            if(rows == 1){
                DataProcessInfoUtil.setInfo("数据内容行数为0行", InfoType.WARN);
            }
            int step = 0;
            DataProcessInfoUtil.setInfo("数据导入中...", null);
            String bmdm;
            String bmmc;
            String bmlb;
            String bmlbcd;
            String sjbm;
            String sjbmcd;
            String zggh;
            String fzrgh;
            String sxm;
            String bz;
            String dwqc;
            String sszz;
            String sszzcd;
            String bzs;
            Cell cell;
            OrgQuery q;
            List<Type> types = OrgUtil.getTypeList();
            List<OrgInfo> infos;
            List<DynaBean> beanlist;
            DynaBeanQuery byQuery = new DynaBeanQuery(InfoClassCache.getOverallInfoClass());
            OrgInfo drinfo;
            OrgInfo parent;
            
            for (int m = 1; m < rows; m++) {
                try{
                    DataProcessInfoUtil.setStep("数据入库:", ++step, rows - 1);
                    bmdm = null;
                    bmmc = null;
                    bmlb = null;
                    bmlbcd = null;
                    sjbm = null;
                    sjbmcd = null;
                    zggh = null;
                    fzrgh = null;
                    sxm = null;
                    bz = null;
                    dwqc = null;
                    sszz = null;
                    sszzcd = null;
                    bzs = null;
                    drinfo = new OrgInfo();
                    parent = new OrgInfo();

                    // 部门代码
                    if (bmdmCol != 0) {
                        cell = sheet.getCell(bmdmCol, m);
                        bmdm = cell.getContents();
                    }
                    
                    if (StringUtils.isEmpty(bmdm)) {
                        throw new RuleException("列[" + m + "]:部门代码必须填写！");
                    } else {
                        bmdm = bmdm.trim();
                    }
                    
                    if (!StringUtils.isNumeric(bmdm) || bmdm.length() > 20) {
                        throw new RuleException("列[" + m + "]:部门代码必须为数字格式，且不超过20位！");
                    }
                    
                    if (orgService.getById(bmdm) != null) {
                        throw new RuleException("列[" + m + "]:部门代码为" + bmdm + "已存在！");
                    }
                    drinfo.setOid(bmdm);
                    
                    // 部门名称
                    if (bmmcCol != 0) {
                        cell = sheet.getCell(bmmcCol, m);
                        bmmc = cell.getContents();
                    }
                    
                    if (StringUtils.isEmpty(bmmc)) {
                        throw new RuleException("列[" + m + "]:部门名称必须填写！");
                    } else {
                        drinfo.setName(bmmc.trim());
                    }
                    
                    // 部门类别
                    if (bmlbCol != 0) {
                        cell = sheet.getCell(bmlbCol, m);
                        bmlb = cell.getContents();
                    }
                    
                    if (StringUtils.isEmpty(bmlb)) {
                        throw new RuleException("列[" + m + "]:部门类别必须填写！");
                    } else {
                        bmlb = bmlb.trim();
                    }
                    
                    for (Type t : types) {
                        if (t.getText().equals(bmlb)) {
                            bmlbcd = t.getName();
                            break;
                        }
                    }
                    
                    if (StringUtils.isEmpty(bmlbcd)) {
                        throw new RuleException("列[" + m + "]:部门类别不存在！");
                    }
                    drinfo.setType(bmlbcd);
                    
                    // 上级部门
                    if (sjbmCol != 0) {
                        cell = sheet.getCell(sjbmCol, m);
                        sjbm = cell.getContents();
                    }
                    
                    if (!StringUtils.isEmpty(sjbm)) {
                        sjbm = sjbm.trim();
                        q = new OrgQuery();
                        q.setType(bmlbcd);
                        infos = orgService.getList(q);
                        for (OrgInfo infop : infos) {
                            if (infop.getName().equals(sjbm)) {
                                sjbmcd = infop.getOid();
                                break;
                            }
                        }
                        if (StringUtils.isEmpty(sjbmcd)) {
                            throw new RuleException("列[" + m + "]:上级部门不存在！");
                        }
                        parent.setOid(sjbmcd);
                        drinfo.setParent(parent);
                    }
                    
                    // 主管工号
                    if (zgghCol != 0) {
                        cell = sheet.getCell(zgghCol, m);
                        zggh = cell.getContents();
                    }
                    
                    if (!StringUtils.isEmpty(zggh)) {
                        zggh = zggh.trim();
                        byQuery.setParam("gh", zggh);
                        byQuery.setExpress(" dqztm = '11' and gh = #{params.gh} ");
                        beanlist = dynaBeanBusiness.queryBeans(byQuery);
                        if (beanlist.isEmpty()) {
                            throw new RuleException("列[" + m + "]:工号为" + zggh + "的用户不在系统中！");
                        } else if(beanlist.size() > 1) {
                            throw new RuleException("列[" + m + "]:系统中存在多个工号为" + zggh + "的用户！");
                        }
                        drinfo.setManager(zggh);
                    }
                    
                    // 负责人工号
                    if (fzrghCol != 0) {
                        cell = sheet.getCell(fzrghCol, m);
                        fzrgh = cell.getContents();
                    }
                    
                    if (!StringUtils.isEmpty(fzrgh)) {
                        fzrgh = fzrgh.trim();
                        byQuery.setParam("gh", fzrgh);
                        byQuery.setExpress(" dqztm = '11' and gh = #{params.gh} ");
                        beanlist = dynaBeanBusiness.queryBeans(byQuery);
                        if (beanlist.isEmpty()) {
                            throw new RuleException("列[" + m + "]:工号为" + fzrgh + "的用户不在系统中！");
                        } else if(beanlist.size() > 1) {
                            throw new RuleException("列[" + m + "]:系统中存在多个工号为" + fzrgh + "的用户！");
                        }
                        drinfo.setPrin(fzrgh);
                    }
                    
                    // 顺序码
                    if (sxmCol != 0) {
                        cell = sheet.getCell(sxmCol, m);
                        sxm = cell.getContents();
                    }
                    
                    if (StringUtils.isEmpty(sxm)) {
                        sxm = bmdm;
                    } else {
                        sxm = sxm.trim();
                        if (!StringUtils.isNumeric(sxm) || sxm.length() > 20) {
                            throw new RuleException("列[" + m + "]:顺序码必须为数字格式，且不超过20位！");
                        }
                    }
                    drinfo.setOrderCode(sxm);
                    
                    // 备注
                    if (bzCol != 0) {
                        cell = sheet.getCell(bzCol, m);
                        bz = cell.getContents();
                    }
                    
                    if (!StringUtils.isEmpty(bz)) {
                        bz = bz.trim();
                        drinfo.setRemark(bz);
                    }
                    
                    // 单位全称
                    if (dwqcCol != 0) {
                        cell = sheet.getCell(dwqcCol, m);
                        dwqc = cell.getContents();
                    }
                    
                    if (!StringUtils.isEmpty(dwqc)) {
                        dwqc = dwqc.trim();
                        drinfo.setDwqc(dwqc);
                    }
                    
                    // 所属总支
                    if (sszzCol != 0) {
                        cell = sheet.getCell(sszzCol, m);
                        sszz = cell.getContents();
                    }
                    
                    if (!StringUtils.isEmpty(sszz)) {
                        sszz = sszz.trim();
                        sszzcd = codeProcess(sszz, null, "DM_DEF_SSZZ");
                        if (StringUtils.isEmpty(sszzcd)) {
                            throw new RuleException("列[" + m + "]:所属总支不存在！");
                        }
                        drinfo.setSszz(sszzcd);
                    }
                    
                    // 编制数
                    if (bzsCol != 0) {
                        cell = sheet.getCell(bzsCol, m);
                        bzs = cell.getContents();
                    }
                    
                    if (!StringUtils.isEmpty(bzs)) {
                        bzs = bzs.trim();
                        if (!StringUtils.isNumeric(bzs) || bzs.length() > 20) {
                            throw new RuleException("列[" + m + "]:编制数必须为数字格式，且不超过20位！");
                        }
                        drinfo.setBzs(Integer.valueOf(bzs));
                    }
                    
                    setMsg(orgService.add(drinfo), "新增部门");
                    baseLog.insert("新增组织机构", "组织机构管理", "新增组织机构 : " + drinfo.getName() + "(部门代码 : " + drinfo.getOid() + ")");
                }catch (Exception e) {
                    if(e instanceof RuleException){
                        DataProcessInfoUtil.setInfo("序号:"+m+"  导入失败,原因:"+e.getMessage(), InfoType.ERROR);
                        cnt++;
                        if(cnt>=maxValidateError){
                            throw new RuleException("校验失败次数达到上限"+maxValidateError+"次");    
                        }
                    }else{
                        e.printStackTrace();
                        throw new RuleException("导入时发生未知异常",e);
                    }
                }
            }
            DataProcessInfoUtil.setInfo(" 导入完成", null);
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
    
    private String codeProcess(String value, String fvalue, String code)throws Exception{
        // 代码库字段数据，不必填为空时直接返回
        if(StringUtils.isEmpty(value)){
            return value;
        }
        List<Item> list = CodeUtil.getAvailableItems(code);
        String result = null;
        for(Item i : list){
            if (StringUtils.isEmpty(fvalue)) {
                if(i.getDescription().equals(value)){
                    result = i.getGuid();
                    break;
                }
            } else {
                if(i.getDescription().equals(value) && i.getParentId().equals(fvalue)){
                    result = i.getGuid();
                    break;
                }
            }
            
        }
        if(StringUtils.isEmpty(result)){
            throw new RuleException("代码库转换失败["+value+"]");
        }
        return result;
    }
    
    private String getComment(Cell c){
        if(c.getCellFeatures()!=null){
            return c.getCellFeatures().getComment();
        }else{
            return null;
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
    
    private void setMsg(boolean exp, String text){
        text = !StringUtil.isEmpty(text) ? text : "操作";
        if(exp){
            setSuccessMessage(text + "成功");
        }
        else{
            setErrorMessage(text + "失败");
        }
        getValueStack().set(DATA, getMessage());
    }

    /**
     * @return the file
     */
    public File getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * @return the fileContentType
     */
    public String getFileContentType() {
        return fileContentType;
    }

    /**
     * @param fileContentType the fileContentType to set
     */
    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    /**
     * @return the dynaBeanBusiness
     */
    public IDynaBeanBusiness getDynaBeanBusiness() {
        return dynaBeanBusiness;
    }

    /**
     * @param dynaBeanBusiness the dynaBeanBusiness to set
     */
    public void setDynaBeanBusiness(IDynaBeanBusiness dynaBeanBusiness) {
        this.dynaBeanBusiness = dynaBeanBusiness;
    }

    /**
     * @return the orgService
     */
    public IOrgService getOrgService() {
        return orgService;
    }

    /**
     * @param orgService the orgService to set
     */
    public void setOrgService(IOrgService orgService) {
        this.orgService = orgService;
    }
    
}
