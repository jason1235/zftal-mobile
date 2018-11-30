package com.zfsoft.hrm.print.service;

import java.util.List;

import com.zfsoft.hrm.print.entity.PrintTemplateEntity;

/**
 * 
 * @author Administrator
 *
 */
public interface IPrintTemplateService {
    
    /**
     * 新建模板
     * @param model
     */
    public void addTemplate(PrintTemplateEntity model);

    /**
     * 修改模板
     * @param model
     */
    public void modifyTemplate(PrintTemplateEntity model);

    /**
     * 取得模板
     * @return
     */
    public List<PrintTemplateEntity> getPrintTemplateList();

    /**
     * 取得模板
     * @param id
     * @return
     */
    public PrintTemplateEntity findById(String id);

    /**
     * 删除
     * @param id
     */
    public void remove(String id);

    /**
     * 取得模板
     * @return
     */
    public List<PrintTemplateEntity> findByParam(PrintTemplateEntity model);

}
