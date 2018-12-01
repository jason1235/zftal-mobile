package com.zfsoft.hrm.print.service.impl;

import java.util.List;

import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.print.dao.IPrintTemplateDao;
import com.zfsoft.hrm.print.entity.PrintTemplateEntity;
import com.zfsoft.hrm.print.service.IPrintTemplateService;

/**
 * 
 * @author Administrator
 *
 */
public class PrintTemplateServiceImpl implements IPrintTemplateService {
    
    private IPrintTemplateDao printTemplateDao;

    /**
     * @return the printTemplateDao
     */
    public IPrintTemplateDao getPrintTemplateDao() {
        return printTemplateDao;
    }

    /**
     * @param printTemplateDao the printTemplateDao to set
     */
    public void setPrintTemplateDao(IPrintTemplateDao printTemplateDao) {
        this.printTemplateDao = printTemplateDao;
    }

    /**
     * 新建模板
     */
    @Override
    public void addTemplate(PrintTemplateEntity model) {
        List<PrintTemplateEntity> mbs = printTemplateDao.findByParam(model);
        if (mbs != null && mbs.size() > 0) {
            throw new RuleException("存在相同模板（同名、同标识、同路径）");
        }
        printTemplateDao.addTemplate(model);
    }

    /**
     * 修改模板
     */
    @Override
    public void modifyTemplate(PrintTemplateEntity model) {
        printTemplateDao.modifyTemplate(model);
    }

    /**
     * 取得模板
     */
    @Override
    public List<PrintTemplateEntity> getPrintTemplateList() {
        return printTemplateDao.getPrintTemplateList();
    }

    /**
     * 取得模板
     */
    @Override
    public PrintTemplateEntity findById(String id) {
        return printTemplateDao.findById(id);
    }

    /**
     * 删除
     */
    @Override
    public void remove(String id) {
        printTemplateDao.reSort(id);
        printTemplateDao.remove(id);
    }

    /**
     * 取得模板
     */
    @Override
    public List<PrintTemplateEntity> findByParam(PrintTemplateEntity model) {
        return printTemplateDao.findByParam(model);
    }

}
