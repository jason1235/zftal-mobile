package com.zfsoft.hrm.baseinfo.data.service;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.data.entity.Template;

public interface ITemplateService {
	public PageList<Template> getPagedTemplate(Template template);
	
	public void add(Template template);
	
	public void delete(String id);
	
	public void modify(Template template);
	
	public Template getById(String id);
}
