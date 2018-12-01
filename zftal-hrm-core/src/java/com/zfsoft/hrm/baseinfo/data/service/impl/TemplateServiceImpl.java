package com.zfsoft.hrm.baseinfo.data.service.impl;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.baseinfo.data.dao.ITemplateDao;
import com.zfsoft.hrm.baseinfo.data.entity.Template;
import com.zfsoft.hrm.baseinfo.data.service.ITemplateService;

public class TemplateServiceImpl implements ITemplateService {
	private ITemplateDao templateDao;
	public void setTemplateDao(ITemplateDao templateDao) {
		this.templateDao = templateDao;
	}
	@Override
	public PageList<Template> getPagedTemplate(Template template) {
		PageList<Template> pageList = new PageList<Template>();
		pageList.addAll(templateDao.getPagedTemplate(template));
		Paginator paginator = new Paginator();
		paginator.setItemsPerPage(template.getShowCount());
		paginator.setPage(template.getCurrentPage());
		paginator.setItems(template.getTotalResult());
		pageList.setPaginator(paginator);
		return pageList;
	}
	@Override
	public void add(Template template) {
		templateDao.insert(template);
	}
	@Override
	public void delete(String id) {
		templateDao.delete(id);
	}
	@Override
	public void modify(Template template) {
		templateDao.update(template);
	}
	@Override
	public Template getById(String id) {
		return templateDao.getById(id);
	}
}
